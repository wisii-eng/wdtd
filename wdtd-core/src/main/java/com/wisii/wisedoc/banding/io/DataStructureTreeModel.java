/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * @DataStructureTreeModel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding.io;

import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import com.wisii.db.Setting;
import com.wisii.wisedoc.banding.BindNode;

/**
 * 数据结构模型
 * 
 * 作者：zhangqiang 创建日期：2007-6-11
 */
public class DataStructureTreeModel extends DefaultTreeModel
{
	// 用来存储数据转换设置，数据结构文件为txt|csv|dbf时使用
	private Object _dataset;
	private String _type = "xml";
	//数据库设置信息
	private Setting dbsetting;
	private List<NameSpace> spaces;
	private String datapath;//记录导入文件的文件路径和文件名。

	public DataStructureTreeModel(BindNode root, boolean flag,List<NameSpace> spaces)
	{
		super(root, flag);
		if(spaces!=null)
		{
			this.spaces=new ArrayList<NameSpace>(spaces);
		}

	}
	public DataStructureTreeModel(Setting dbsetting,BindNode root, boolean flag)
	{
		super(root, flag);
		this.dbsetting = dbsetting;

	}
	public DataStructureTreeModel(Setting dbsetting,BindNode root, boolean flag,List<NameSpace> spaces)
	{
		this(dbsetting, root, flag);
		if(spaces!=null)
		{
			this.spaces=new ArrayList<NameSpace>(spaces);
		}
	}
	public DataStructureTreeModel(BindNode root, boolean flag, Object dataset,String type)
	{
		super(root, flag);
		_dataset = dataset;
		if("xml".equalsIgnoreCase(type)||"xsd".equalsIgnoreCase(type)||"dtd".equalsIgnoreCase(type)) 
		{
			_type = type;
		}	
	}

	public Object getDataSet()
	{
		return _dataset;
	}

	public boolean isOtherdata()
	{

		return !_type.equals("xml");
	}

	/**
	 * 
	 * 获得数据结构类型，默认为xml
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return "xml"|DataUtil.CSV|DataUtil.TXT|DataUtil.DBF
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public String getDataType()
	{
		return _type;
	}
	/**
	 * @返回  dbsetting变量的值
	 */
	public Setting getDbsetting()
	{
		return dbsetting;
	}
	public List<NameSpace> getSpaces() {
		if(spaces==null)
		{
			return null;
		}
		return new ArrayList<NameSpace>(spaces);
	}
	public String getNameSpaceOfPre(String pre)
	{
		if(spaces==null||pre==null)
		{
			return null;
		}
		for(NameSpace space:spaces)
		{
			if(space.attribute.equals("xmlns:"+pre))
			{
				return space.attributeValue;
			}
		}
		return null;
	}
	public String getDatapath() {
		return datapath;
	}
	public void setDatapath(String datapath) {
		this.datapath = datapath;
	}
	

}
