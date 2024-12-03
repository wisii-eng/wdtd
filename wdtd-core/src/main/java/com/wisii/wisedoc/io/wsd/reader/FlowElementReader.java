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
 * @DefaultElementReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.List;
import org.xml.sax.SAXException;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Inline;

/**
 * 类功能描述：普通元素reader类，包括flow，block，table，table相关等
 * 
 * 作者：zhangqiang 创建日期：2008-10-10
 */
public class FlowElementReader extends AbstractElementReader
{
	private final String FLOW = "flow";
	private final String STATICCONTENT = "staticcontent";
	Flow flow;

	FlowElementReader()
	{
	}

	/* (non-Javadoc)
	* @see com.wisii.wisedoc.io.wsd.reader.AbstractElementReader#init()
	*/
	@Override
	protected void init()
	{
		// TODO Auto-generated method stub
		super.init();
		flow = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#endElement(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (TEXTTAG.equals(qname))
		{
			if (_curentreader != null)
			{
				_curentreader.endElement(uri, localname, qname);
				if (_curentreader instanceof TextInlineReader)
				{
					TextInlineReader tr = (TextInlineReader) _curentreader;
					List<Inline> inlines = tr.getObject();
					if (inlines != null && !inlines.isEmpty())
					{
						CellElement pcelle = _elementstack.peek();
						if (pcelle != null)
						{
							int size = inlines.size();
							for (int i = 0; i < size; i++)
							{
								pcelle.add(inlines.get(i));
							}
						}
					}
				}
			} else
			{
				throw new SAXException("textinline 元素不能嵌套元素(" + qname + ")");
			}
		} else
		{
			if (FLOW.equals(qname) || STATICCONTENT.equals(qname))
			{
				flow = (Flow) _elementstack.peek();
			} else
			{
				// 当前元素出棧
				CellElement celle = _elementstack.pop();
				if (!_elementstack.isEmpty())
				{
					CellElement pcelle = _elementstack.peek();
					// 将当前元素添加到父节点对象上
					if (celle != null && pcelle != null)
					{
						pcelle.add(celle);
					}
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public CellElement getObject()
	{
		return flow;
	}

}
