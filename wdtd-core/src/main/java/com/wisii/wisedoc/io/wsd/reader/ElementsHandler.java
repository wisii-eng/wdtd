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
 * @ElementHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.BarCodeInline;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.EncryptTextInline;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.QianZhangInline;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-6
 */
public class ElementsHandler extends AbstractElementReader
{
	List<CellElement> elements = new ArrayList<CellElement>();

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
						if (!_elementstack.isEmpty())
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
						} else
						{
							elements.addAll(inlines);
						}
					}
				}
			} else
			{
				throw new SAXException("textinline 元素不能嵌套元素(" + qname + ")");
			}
		} else
		{
			// 当前元素出棧
			CellElement celle = _elementstack.pop();
			if (!isElementValid(celle))
			{
				return;
			}
			if (!_elementstack.isEmpty())
			{
				CellElement pcelle = _elementstack.peek();
				// 将当前元素添加到父节点对象上
				if (celle != null && pcelle != null)
				{
					pcelle.add(celle);
				}
			} else
			{
				elements.add(celle);

			}
		}

	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private boolean isElementValid(CellElement celle)
	{
		//清除掉动态绑定节点为空的元素
		if (celle instanceof Inline
				&& (celle instanceof NumberTextInline
						|| celle instanceof EncryptTextInline
						|| celle instanceof DateTimeInline || celle instanceof CheckBoxInline))
		{
			if (celle.getAttribute(Constants.PR_INLINE_CONTENT) == null)
			{

				return true;
			} else
			{

			}
		}
		if ((celle instanceof ExternalGraphic||celle instanceof QianZhang)
				&& celle.getAttribute(Constants.PR_SRC) == null)
		{
			return false;
		}
		if (celle instanceof BarCode
				&& celle.getAttribute(Constants.PR_BARCODE_CONTENT) == null)
		{
			return false;
		}
		if (celle instanceof ImageInline || celle instanceof BarCodeInline||celle instanceof QianZhangInline)
		{
			if (celle.getChildCount() == 0)
			{
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public List<CellElement> getObject()
	{
		return elements;
	}

}
