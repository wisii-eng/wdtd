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
 * @StructureReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding.io;

import java.io.FileNotFoundException;
import com.wisii.wisedoc.exception.DataStructureException;
import com.wisii.wisedoc.view.action.BrowseAction;

/**
 * 数据源文档结构读取工具类
 * 
 * 作者：zhangqiang 创建日期：2007-6-11
 */
public class StructureReaderUtil
{

	/**
	 * 
	 * 根据指定文件返回数据结构
	 * 
	 * @param xmlfile
	 *            ：文件名
	 * @return DataStructureTreeModel：文档结构
	 * @exception FileNotFoundException抛出文件不存在异常
	 */
	public static DataStructureTreeModel readStructure(String xmlfile)
			throws FileNotFoundException, DataStructureException
	{
//		FileInputStream in = new FileInputStream(xmlfile);
		StructureReader reader = getReaderForFile(xmlfile);
		if (reader != null)
		{
			return reader.readStructure(xmlfile);
		}
		return null;

	}

	/*
	 * 
	 * 根据文件名获得
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * 
	 * @return {返回参数名} {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	private static StructureReader getReaderForFile(String xmlfile)
	{
		StructureReader reader = null;
		if (xmlfile != null)
		{
			// 获得文件类型
			String ftype = xmlfile.substring(xmlfile.lastIndexOf(".") + 1)
					.toLowerCase();
			if ("xml".equals(ftype))
			{
				reader = new XMLReader();
			} else if ("dtd".equals(ftype))
			{
				reader = new DTDReader();
			} else if ("xsd".equals(ftype))
			{
				reader = new XSDReader();
			} 
//			else if ("txt".equals(ftype))
//			{
//				reader = new TxtReader(xmlfile);
//			} else if ("csv".equals(ftype))
//			{
//				reader = new CSVReader(xmlfile);
//			} else if ("dbf".equals(ftype))
//			{
//				reader = new DBFReader(xmlfile);
//			}
			BrowseAction.setDatafileurl(xmlfile);
		}
		return reader;
	}

}
