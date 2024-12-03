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
 * @Circle.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.svg;

import java.awt.Color;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.io.xsl.attribute.ColorWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-4
 */
public class Circle
{

	// 圆心横坐标
	FixedLength circleX = new FixedLength(0d, "mm");

	// 圆心纵坐标
	FixedLength circleY = new FixedLength(0d, "mm");

	// 圆半径
	FixedLength circleRadius = new FixedLength(0d, "mm");

	// 线宽
	FixedLength lineWidth = new FixedLength(1, FoXsltConstants.PT);

	// 线的颜色
	Color lineColor = new Color(0, 0, 0);

	// 圆区域的填充颜色
	Color fillColor = null;

	public Circle(FixedLength circlex, FixedLength circley, FixedLength radius,
			FixedLength linewidth, Color linecolor, Color fillcolor)
	{
		circleX = circlex;
		circleY = circley;
		circleRadius = radius;
		lineWidth = linewidth;
		lineColor = linecolor;
		fillColor = fillcolor;
	}

	public Circle(FixedLength circlex, FixedLength circley, FixedLength radius,
			FixedLength linewidth, Color linecolor)
	{
		circleX = circlex;
		circleY = circley;
		circleRadius = radius;
		lineWidth = linewidth;
		lineColor = linecolor;
	}

	public Circle(FixedLength radius, FixedLength linewidth, Color linecolor,
			Color fillcolor)
	{
		circleRadius = radius;
		lineWidth = linewidth;
		lineColor = linecolor;
		fillColor = fillcolor;
	}

	public Circle(FixedLength radius, FixedLength linewidth, Color linecolor)
	{
		circleRadius = radius;
		lineWidth = linewidth;
		lineColor = linecolor;
	}

	/**
	 * 
	 * 获取svg:circle元素的代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String write()
	{
		StringBuffer output = new StringBuffer();
		output.append("<");
		output.append(FoXsltConstants.SVG_CIRCLE);
		output.append(getAttributes());
		output.append("/>");
		return output.toString();
	}

	/**
	 * 
	 * 获取属性部分的代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getAttributes()
	{
		int coefficient = IoXslUtil.getCoefficient();
		StringBuffer output = new StringBuffer();
		String circlex = IoXslUtil.getValueOfCm(circleX.getLengthValueString(),
				circleX.getUnits(), coefficient);
		String circley = IoXslUtil.getValueOfCm(circleY.getLengthValueString(),
				circleY.getUnits(), coefficient);
		String circleradius = IoXslUtil.getValueOfCm(circleRadius
				.getLengthValueString(), circleRadius.getUnits(), coefficient);
		String linewidth = IoXslUtil.getValueOfCm(lineWidth
				.getLengthValueString(), lineWidth.getUnits(), coefficient);
		output
				.append(ElementUtil.outputAttributes(FoXsltConstants.CX,
						circlex));
		output
				.append(ElementUtil.outputAttributes(FoXsltConstants.CY,
						circley));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.R,
				circleradius));
		output.append(ElementUtil.outputAttributes(
				FoXsltConstants.STROKE_WIDTH, linewidth));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.STROKE,
				new ColorWriter().write(lineColor)));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.FILL,
				new ColorWriter().write(fillColor)));
		return output.toString();
	}
}
