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
 * @XMLReaderHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;
import java.util.List;

import org.xml.sax.Attributes;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.BindNodeUtil;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.CellElement;

/**
 * 类功能描述：读取粘贴板内容，根据粘贴版内容解析生成对象的功能
 * 
 * 作者：zhangqiang 创建日期：2008-10-6
 */
public final class CopyReaderHandler extends AbstractElementsHandler
{
	private static final String ROOTNODETAG = "elements";
	private final DataStructureTreeModel datamodel;

	public CopyReaderHandler(DataStructureTreeModel datamodel)
	{
		this.datamodel = datamodel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractElementsHandler#init()
	 */
	@Override
	protected void init()
	{
		// TODO Auto-generated method stub
		super.init();
		ElementsHandler _pseshandler = new ElementsHandler();
		_handlermap.put(ROOTNODETAG, _pseshandler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractElementsHandler#dealStartElement
	 * (java.lang.String, java.lang.String, java.lang.String,
	 * org.xml.sax.Attributes)
	 */
	@Override
	protected boolean dealStartElement(String uri, String localName,
			String qName, Attributes atts)
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractElementsHandler#dealEndElement
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected boolean dealEndElement(String uri, String localName, String qName)
	{
		if (ROOTNODETAG.equals(qName))
		{
			return false;
		}
		return true;
	}

	@Override
	public BindNode getNode(String id)
	{
		// TODO Auto-generated method stub
		return BindNodeUtil.getNodeOfPath(id, datamodel);
	}

	public List<CellElement> getElements()
	{
		return (List<CellElement>) _handlermap.get(ROOTNODETAG).getObject();
	}
}
