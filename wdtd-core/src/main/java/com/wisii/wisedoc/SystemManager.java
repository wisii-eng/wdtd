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
 * @SystemManager.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc;

import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.view.WisedocFrame;
import com.wisii.wisedoc.view.WisedocMainFrame;

/**
 * 类功能描述：Designer的系统管理器,负责维护主窗体，文档等公共资源
 * 
 * 作者：zhangqiang 创建日期：2008-9-11
 */
public class SystemManager
{
	// 系统中文档列表，为多文档支持预留的字段
	private static List<Document> _documents = new ArrayList<Document>();
	// 当前正在操作的文档
	private static Document _currentdoc;
	// 系统主窗体
	private static WisedocMainFrame _mainframe;

	/**
	 * @返回 _mainframe变量的值
	 */
	public static WisedocFrame getMainframe()
	{
		if (_mainframe != null) {
			return _mainframe.getMainFrame();
		}
		return null;
	}

	/**
	 * @param _mainframe
	 *            设置_mainframe成员变量的值 值约束说明
	 */
	public static void setMainframe(WisedocMainFrame mainframe)
	{
		_mainframe = mainframe;
	}

	/**
	 * 
	 * 获得当前正在操作的文档
	 * 
	 * @param
	 * @return 当前正在操作的文档
	 * @exception
	 */
	public static Document getCurruentDocument()
	{
		return _currentdoc;
	}

	/**
	 * 
	 * 设置当前文档，文档切换时需调用该方法
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static void setCurruentDocument(Document document)
	{
		if (document != null && !_documents.contains(document))
		{
			_documents.add(document);
		}
		if (_currentdoc != document)
		{
			_currentdoc = document;
		}
	}

	/**
	 * 
	 * 返回系统的所有文档对象
	 * 
	 * @param!
	 * @return
	 * @exception
	 */
	public static List<Document> getDocuments()
	{
		return new ArrayList<Document>(_documents);
	}
}
