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
 * @XslElementObj.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-27
 */
public class XslElementObj implements XslElement, XslElementIFWriter
{

	// 子元素，为空表示没子元素。
	List<XslElementObj> children;

	// 属性map
	Map<Integer, Object> attributeMap;

	String content = "";

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	// 元素名
	String elementName;

	// 属性Writer选择对象
	SelectOutputAttributeWriter writerFactory = new SelectOutputAttributeWriter();

	public XslElementObj(Map<Integer, Object> attributemap, String elementname)
	{
		children = new ArrayList<XslElementObj>();
		attributeMap = attributemap;
		elementName = elementname;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.XslElementObj#getChildren()
	 */
	public List<XslElementObj> getChildren()
	{
		return children;
	}

	/**
	 * @返回 attributeMap变量的值
	 */
	public Map<Integer, Object> getAttributeMap()
	{
		return attributeMap;
	}

	/**
	 * @param attributeMap
	 *            设置attributeMap成员变量的值 值约束说明
	 */
	public void setAttributeMap(Map<Integer, Object> attributeMap)
	{
		this.attributeMap = attributeMap;
	}

	/**
	 * @返回 elementName变量的值
	 */
	public String getElementName()
	{
		return elementName;
	}

	/**
	 * @param elementName
	 *            设置elementName成员变量的值 值约束说明
	 */
	public void setElementName(String elementName)
	{
		this.elementName = elementName;
	}

	public void add(XslElementObj newChild)
	{
		if (newChild != null)
		{
			children.add(newChild);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.XslElement#add(java.util.List)
	 */
	public void add(List<XslElementObj> newChildren)
	{
		if (newChildren != null)
		{
			int n = newChildren.size();
			for (int i = 0; i < n; i++)
			{
				children.add(newChildren.get(i));
			}
		}

	}

	public String getAttibute(int key, Object value)
	{
		return writerFactory.write(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getAttributes()
	 */
	public String getAttributes()
	{
		String code = "";
		Map<Integer, Object> attributemap = getAttributeMap();
		if (attributemap != null)
		{
			Object[] keys = attributemap.keySet().toArray();
			int size = keys.length;
			for (int i = 0; i < size; i++)
			{
				int key = (Integer) keys[i];
				if (key == 0)
				{
					code = code
							+ ElementUtil.outputAttributes(
									FoXsltConstants.NAME, attributemap.get(key)
											.toString());
				} else
				{
					code = code + getAttibute(key, attributemap.get(key));
				}
			}
		}
		return code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.ElementObject#getChildCode(java.util.List)
	 */
	public String getChildCode()
	{
		String output = "";

		if (children != null)
		{
			int size = children.size();
			for (int i = 0; i < size; i++)
			{
				XslElementObj obj = children.get(i);
				output = output + obj.getCode();
			}
		}

		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.ElementObject#getCode()
	 */
	public String getCode()
	{
		String code = "";
		code = code + getHeaderElement();
		code = code + getChildCode();
		code = code + getContent();
		code = code + getEndElement();
		return code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.ElementObject#getEndElement()
	 */
	public String getEndElement()
	{
		String output = ElementWriter.TAGENDSTART;
		output = output + getElementName();
		output = output + ElementWriter.TAGEND;
		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.ElementObject#getHeaderElement()
	 */
	public String getHeaderElement()
	{
		String code = "";
		code = code + getHeaderStart();
		code = code + getAttributes();
		code = code + getHeaderEnd();
		return code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.ElementObject#getHeaderEnd()
	 */
	public String getHeaderEnd()
	{
		return ElementWriter.TAGEND;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.ElementObject#getHeaderStart()
	 */
	public String getHeaderStart()
	{
		String code = ElementWriter.TAGBEGIN;
		code = code + getElementName();
		return code;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof XslElementObj))
		{
			return false;
		}
		XslElementObj xslobj = (XslElementObj) obj;
		// return this.attributeMap.equals(xslobj.getAttributeMap())
		return equalsMap(this.attributeMap, xslobj.getAttributeMap())
		// && this.elementName.equalsIgnoreCase(xslobj.getElementName())
				&& this.children.equals(xslobj.getChildren());

	}

	public boolean equalsMap(Map<Integer, Object> map1,
			Map<Integer, Object> map2)
	{
		boolean flg = true;
		int size1 = map1.size();
		int size2 = map2.size();
		if (size1 == size2)
		{
			Object[] keys = map1.keySet().toArray();

			for (int i = 0; i < size1; i++)
			{
				int key = (Integer) keys[i];
				if (map1.containsKey(key) && map2.containsKey(key))
				{
					if (!map1.get(key).equals(map2.get(key)))
					{
						flg = false;
						break;
					}
				} else
				{
					flg = false;
					break;
				}
			}
		} else
		{
			flg = false;
		}
		return flg;
	}
}
