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
 * @Canvas.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.svg;

import java.util.List;
import java.util.Map;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;

import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：画布
 * 
 * 作者：zhangqiang 创建日期：2009-2-26
 */
public class Canvas extends AbstractGraphics
{

	public static final String NAMESPACEURI = SVGDOMImplementation.SVG_NAMESPACE_URI;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public Canvas()
	{
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public Canvas(final Map<Integer, Object> attributes)
	{
		super(attributes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.AbstractGraphics#getIntrinsicHeight()
	 */
	public int getIntrinsicHeight()
	{
		Object height = getAttribute(Constants.PR_HEIGHT);
		if (height instanceof Length)
		{
			return ((Length) height).getValue();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.AbstractGraphics#getIntrinsicWidth()
	 */
	public int getIntrinsicWidth()
	{
		Object width = getAttribute(Constants.PR_WIDTH);
		if (width instanceof Length)
		{
			return ((Length) width).getValue();
		}
		return 0;
	}

	public String getNamespaceURI()
	{
		return NAMESPACEURI;
	}

	/** @see com.wisii.fov.fo.FONode#getNormalNamespacePrefix() */
	public String getNormalNamespacePrefix()
	{
		return "svg";
	}

	/**
	 * 
	 * 获得svg文档对象
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public org.w3c.dom.Document getSVGDocument()
	{
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		org.w3c.dom.Document doc = impl.createDocument(svgNS, "svg", null);
		buildSVGElement(doc);
		return doc;
	}

	protected void buildSVGElement(org.w3c.dom.Document doc)
	{
		if (doc != null)
		{
			org.w3c.dom.Element root = doc.getDocumentElement();
			root.setAttribute("overflow", "visible");
			SVGUtil.initSVGAttributes(root, getAttributes());
			int childsize = getChildCount();
			for (int i = 0; i < childsize; i++)
			{
				CellElement child = getChildAt(i);
				if (child instanceof AbstractSVG)
				{
					AbstractSVG svg = (AbstractSVG) child;
					List<org.w3c.dom.Element> svgelements = svg
							.getSvgElements(doc);
					if (svgelements != null && !svgelements.isEmpty())
					{
						int sesize = svgelements.size();
						for (int j = 0; j < sesize; j++)
						{
							org.w3c.dom.Element svgelement = svgelements.get(j);
							root.appendChild(svgelement);
						}
					}
				}
			}
		}

	}

	/**
	 * 
	 * 判断是否可以添加子对象 如果对子对象的类型等有要求，需要在子类中进行覆写
	 * 
	 * @param newChild
	 *            ：要添加的子对象
	 * @return 是否可添加该子对象的布尔值
	 * @exception
	 */
	public boolean iscanadd(CellElement newChild)
	{
		return true;
	}

	/**
	 * 
	 * 判断是否可以在指定位置添加子对象 对插入对象有特殊要求，可覆写该方法
	 * 
	 * @param newChild
	 *            ：要插入的子对象 childIndex：要插入的位置
	 * @return 是否可在指定位置添加子对象的布尔值
	 * @exception
	 */
	public boolean iscaninsert(CellElement newChild, int childIndex)
	{
		return true;
	}

	/**
	 * 
	 * 判断是否可以在指定位置添加子对象 对插入对象有特殊要求，可覆写该方法
	 * 
	 * @param newChild
	 *            ：要插入的子对象 childIndex：要插入的位置
	 * @return 是否可在指定位置添加子对象的布尔值
	 * @exception
	 */
	public boolean iscaninsert(List<CellElement> children, int childIndex)
	{
		return true;
	}

	/**
	 * 
	 * 是否可删除某个子对象
	 * 
	 * 
	 * @param child
	 *            ：要删除的子对象
	 * @return 是否可删除该子对象的布尔值
	 * @exception
	 */
	public boolean iscanRemove(CellElement child)
	{
		return true;
	}

	/**
	 * 
	 * 是否可删除指定的子对象集合
	 * 
	 * 
	 * @param children
	 *            ：要删除的子对象集合
	 * @return true
	 * @exception
	 */
	public boolean iscanRemove(List<CellElement> children)
	{
		return true;
	}

	/**
	 * 
	 * 是否可删除所有的子对象
	 * 
	 * @param
	 * @return true表示可删除所有的子对象，false表示不能删除
	 * @exception
	 */
	public boolean iscanremoveall()
	{
		return true;
	}
}
