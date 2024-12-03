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
 * @EditStructureAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;

/**
 * 类功能描述：编辑数据结构事件类
 *
 * 作者：zhangqiang
 * 创建日期：2008-6-2
 */
public class EditStructureAction extends BaseAction
{

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public EditStructureAction()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public EditStructureAction(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public EditStructureAction(String name, Icon icon)
	{
		super(name, icon);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.action.BaseAction#doAction(java.awt.event.ActionEvent)
	 */
	@Override
	public void doAction(ActionEvent e)
	{   
//		获得已有树模型
		DataStructureTreeModel oldmodel = SystemManager.getCurruentDocument().getDataStructure();
////		获得新的转换设置
//		Object[] datas=DataUtil.getNewDataSet(oldmodel.getDataSet(),oldmodel.getDataType());
//        if(datas[1] != null) 
//        {
//			DataStructureTreeModel xmlmodel;
//			try
//			{
////				生成新的结构树
//				xmlmodel = new XMLReader()		
//				.readStructure(new ByteArrayInputStream((byte[]) datas[1]));
//				DataStructureTreeModel newmodel = new DataStructureTreeModel((TreeNode) xmlmodel.getRoot(),
//						false, datas[0],oldmodel.getDataType());
//				tree.setModel(newmodel);
//			}
//			catch (Exception e1)
//			{
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//        }
	}

}
