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
 * @AbstractSVG.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.svg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DefaultElement;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;

/**
 * 类功能描述：抽象的图形类，是所有图形类的父类
 *
 * 作者：zhangqiang
 * 创建日期：2009-2-26
 */
public abstract class AbstractSVG extends DefaultElement
{

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public AbstractSVG()
	{
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public AbstractSVG(final Map<Integer,Object> attributes)
	{
		super(attributes);
	}
    public String getNamespaceURI()
	{
		return Canvas.NAMESPACEURI;
	}

	/**
	 * 
	 * 获得Svg文档元素
	 * 
	 * @param 
	 * @return
	 * @exception 
	 */
	public List<org.w3c.dom.Element> getSvgElements(org.w3c.dom.Document doc)
	{
		return null;
	}
	public void setAttributes(Map<Integer, Object> atts, boolean isreplace)
	{
		Element parent = getParent();
		// 如果是绝对定位图形，则需要将位置信息设置到其父对象SVGContainer中
		if (parent instanceof SVGContainer
				&& atts != null
				&& (atts.containsKey(Constants.PR_WIDTH)
						|| atts.containsKey(Constants.PR_HEIGHT)
						|| atts.containsKey(Constants.PR_RX)
						|| atts.containsKey(Constants.PR_RY)
						|| atts.containsKey(Constants.PR_X1)
						|| atts.containsKey(Constants.PR_X2)
						|| atts.containsKey(Constants.PR_Y1) 
						|| atts.containsKey(Constants.PR_GRAPHIC_LAYER)
						|| atts.containsKey(Constants.PR_Y2)))
		{
			Map<Integer, Object> svgcontaineratt = new HashMap<Integer, Object>();
			if (atts.containsKey(Constants.PR_WIDTH))
			{
				Object width = atts.get(Constants.PR_WIDTH);
				if (width instanceof FixedLength)
				{
					width = new LengthRangeProperty((LengthProperty) width);
				}
				if (svgcontaineratt == null)
				{
					svgcontaineratt = new HashMap<Integer, Object>();
				}
				svgcontaineratt.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
						width);
			}
			if(atts.containsKey(Constants.PR_GRAPHIC_LAYER))
			{
				if (svgcontaineratt == null)
				{
					svgcontaineratt = new HashMap<Integer, Object>();
				}
				svgcontaineratt.put(Constants.PR_GRAPHIC_LAYER,
						atts.get(Constants.PR_GRAPHIC_LAYER));
				atts.remove(Constants.PR_GRAPHIC_LAYER);
			}
			if (atts.containsKey(Constants.PR_RX) && this instanceof Ellipse)
			{
				Object width = atts.get(Constants.PR_RX);
				if (width instanceof FixedLength)
				{
					FixedLength oldwidth = (FixedLength) width;
					if (!atts.containsKey(Constants.PR_CX))
					{
						atts.put(Constants.PR_CX, oldwidth);
					}
					width = new LengthRangeProperty(new FixedLength(oldwidth
							.getInnerLengthValue() * 2, oldwidth.getUnits(),
							oldwidth.getPrecision()));
				}
				if (svgcontaineratt == null)
				{
					svgcontaineratt = new HashMap<Integer, Object>();
				}
				svgcontaineratt.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
						width);
			}
			if (atts.containsKey(Constants.PR_HEIGHT))
			{
				Object height = atts.get(Constants.PR_HEIGHT);
				if (height instanceof FixedLength)
				{
					height = new LengthRangeProperty((LengthProperty) height);
				}
				if (svgcontaineratt == null)
				{
					svgcontaineratt = new HashMap<Integer, Object>();
				}
				svgcontaineratt.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
						height);
			}
			if (atts.containsKey(Constants.PR_RY) && this instanceof Ellipse)
			{
				Object height = atts.get(Constants.PR_RY);
				if (height instanceof FixedLength)
				{
					FixedLength oldheight = (FixedLength) height;
					if (!atts.containsKey(Constants.PR_CY))
					{
						atts.put(Constants.PR_CY, oldheight);
					}
					height = new LengthRangeProperty(new FixedLength(oldheight
							.getInnerLengthValue() * 2, oldheight.getUnits(),
							oldheight.getPrecision()));
				}
				if (svgcontaineratt == null)
				{
					svgcontaineratt = new HashMap<Integer, Object>();
				}
				svgcontaineratt.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
						height);
			}
			if ((this instanceof Line)
					&& (atts.containsKey(Constants.PR_X1) || atts
							.containsKey(Constants.PR_X2)))
			{
				FixedLength x1 = (FixedLength) atts.get(Constants.PR_X1);
				if (x1 == null)
				{
					x1 = (FixedLength) getAttribute(Constants.PR_X1);
				}
				FixedLength x2 = (FixedLength) atts.get(Constants.PR_X2);
				if (x2 == null)
				{
					x2 = (FixedLength) getAttribute(Constants.PR_X2);
				}
				FixedLength newwidth = new FixedLength(Math.abs(x2.getValue()
						- x1.getValue()), x1.getPrecision());
				if (svgcontaineratt == null)
				{
					svgcontaineratt = new HashMap<Integer, Object>();
				}
				svgcontaineratt.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
						new LengthRangeProperty(newwidth));
			}
			if ((this instanceof Line)
					&& (atts.containsKey(Constants.PR_Y1) || atts
							.containsKey(Constants.PR_Y2)))
			{
				FixedLength y1 = (FixedLength) atts.get(Constants.PR_Y1);
				if (y1 == null)
				{
					y1 = (FixedLength) getAttribute(Constants.PR_Y1);
				}
				FixedLength y2 = (FixedLength) atts.get(Constants.PR_Y2);
				if (y2 == null)
				{
					y2 = (FixedLength) getAttribute(Constants.PR_Y2);
				}
				FixedLength newheight = new FixedLength(Math.abs(y2.getValue()
						- y1.getValue()), y1.getPrecision());
				if (svgcontaineratt == null)
				{
					svgcontaineratt = new HashMap<Integer, Object>();
				}
				svgcontaineratt.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
						new LengthRangeProperty(newheight));
			}
			if (svgcontaineratt != null && !svgcontaineratt.isEmpty())
			{
				parent.setAttributes(svgcontaineratt, false);
			}
		}
		if (atts != null && !atts.isEmpty())
		{
			super.setAttributes(atts, isreplace);
		}
	}
}
