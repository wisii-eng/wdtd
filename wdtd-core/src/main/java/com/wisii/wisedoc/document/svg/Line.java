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
 * @Line.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.svg;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.Attributes;

/**
 * 类功能描述：直线类
 *
 * 作者：zhangqiang
 * 创建日期：2009-2-26
 */
public class Line extends AbstractSVG
{

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public Line()
	{
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public Line(final Map<Integer, Object> attributes)
	{
		super(attributes);
	}
    public List<Element> getSvgElements(org.w3c.dom.Document doc)
    {
		List<Element> svgelements = new ArrayList<Element>();
		Element lineel = doc.createElementNS(getNamespaceURI(), "line");
		Attributes attribute = getAttributes();
		if (attribute != null)
		{
			SVGUtil.initSVGAttributes(lineel, attribute);
			Map<Integer, Object> attsmap = attribute.getAttributes();
			if (attsmap.containsKey(Constants.PR_SVG_ARROW_START_TYPE))
			{
				int type = (Integer) attsmap
						.get(Constants.PR_SVG_ARROW_START_TYPE);
				Element marker = doc.createElementNS(getNamespaceURI(),
						"marker");
				SVGUtil.initSVGMarkerAttributes(doc, marker, type,
						(Color) attsmap.get(Constants.PR_COLOR), true);
				String idstring = hashCode() + "@0";
				marker.setAttribute("id", idstring);
				svgelements.add(marker);
				lineel.setAttribute("marker-start", "url(#" + idstring + ")");
			}
			if (attsmap.containsKey(Constants.PR_SVG_ARROW_END_TYPE))
			{
				int type = (Integer) attsmap
						.get(Constants.PR_SVG_ARROW_END_TYPE);
				Element marker = doc.createElementNS(getNamespaceURI(),
						"marker");
				SVGUtil.initSVGMarkerAttributes(doc, marker, type,
						(Color) attsmap.get(Constants.PR_COLOR), false);
				String idstring = hashCode() + "@1";
				marker.setAttribute("id", idstring);
				svgelements.add(marker);
				lineel.setAttribute("marker-end", "url(#" + idstring + ")");
			}
		}
		svgelements.add(lineel);
		return svgelements;
	}
}
