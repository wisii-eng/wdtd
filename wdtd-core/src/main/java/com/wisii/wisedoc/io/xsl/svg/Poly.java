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
 * @Poly.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.svg;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.io.xsl.attribute.ColorWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-5
 */
public class Poly
{

	// 点集
	List<Point> points = new ArrayList<Point>();

	// 线宽
	FixedLength lineWidth = new FixedLength(1, FoXsltConstants.PT);

	// 线的颜色
	Color lineColor = new Color(0, 0, 0);

	// 区域的填充颜色
	Color fillColor = null;

	// 元素名
	String elementName = FoXsltConstants.SVG_POLYLINE;

	public Poly(List<Point> pointlist, FixedLength linewidth, Color linecolor,
			Color fillcolor, String name)
	{
		points = pointlist;
		lineWidth = linewidth;
		lineColor = linecolor;
		fillColor = fillcolor;
		elementName = name;
	}

	public Poly(List<Point> pointlist, FixedLength linewidth, Color linecolor,
			Color fillcolor)
	{
		points = pointlist;
		lineWidth = linewidth;
		lineColor = linecolor;
		fillColor = fillcolor;
	}

	public Poly(List<Point> pointlist, FixedLength linewidth, Color linecolor,
			String name)
	{
		points = pointlist;
		lineWidth = linewidth;
		lineColor = linecolor;
		elementName = name;
	}

	public Poly(List<Point> pointlist, FixedLength linewidth, Color linecolor)
	{
		points = pointlist;
		lineWidth = linewidth;
		lineColor = linecolor;
	}

	/**
	 * 
	 * 获取svg:polyline或者svg:polygon元素的代码
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public String write()
	{
		StringBuffer output = new StringBuffer();
		output.append("<");
		output.append(elementName);
		output.append(getAttributes());
		output.append("/>");
		return output.toString();
	}

	/**
	 * 
	 * 获取属性部分的代码
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public String getAttributes()
	{
		int coefficient = IoXslUtil.getCoefficient();
		StringBuffer output = new StringBuffer();
		output.append(getPointCode());
		output.append(ElementUtil.outputAttributes(
				FoXsltConstants.STROKE_WIDTH, ""
						+ new Double(lineWidth.getLengthValueString())
								.doubleValue() * coefficient));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.STROKE,
				new ColorWriter().write(lineColor)));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.FILL,
				new ColorWriter().write(fillColor)));
		return output.toString();
	}

	/**
	 * 
	 * 获取点集的代码
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */

	public String getPointCode()
	{
		StringBuffer output = new StringBuffer();
		int coefficient = IoXslUtil.getCoefficient();
		if (points != null)
		{
			output.append(" ");
			output.append(FoXsltConstants.POINTS);
			output.append("=\"");
			int size = points.size();
			for (int i = 0; i < size; i++)
			{
				Point pointitem = points.get(i);
				double pointx = pointitem.getX() * coefficient;
				double pointy = pointitem.getY() * coefficient;
				if (i > 0)
				{
					output.append(" ");
				}
				output.append(pointx + "," + pointy);
			}
			output.append("\"");
		}
		return output.toString();
	}
}
