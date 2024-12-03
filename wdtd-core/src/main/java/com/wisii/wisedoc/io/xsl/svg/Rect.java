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
 * @Rect.java
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
public class Rect
{

	// 起始点横坐标
	FixedLength startX = new FixedLength(0d, "mm");

	// 起始点纵坐标
	FixedLength startY = new FixedLength(0d, "mm");

	// 宽度
	FixedLength rectWidth;

	// 高度
	FixedLength rectHeight;

	// 线宽
	FixedLength lineWidth = new FixedLength(1, FoXsltConstants.PT);

	// 线的颜色
	Color lineColor = new Color(0, 0, 0);

	// 矩形区域的填充颜色
	Color fillColor = null;

	// 旋转
	Transform transform;

	public Rect(FixedLength x, FixedLength y, FixedLength width,
			FixedLength height, FixedLength linewidth, Color linecolor,
			Color fillcolor)
	{
		startX = x;
		startY = y;
		rectWidth = width;
		rectHeight = height;
		lineWidth = linewidth;
		lineColor = linecolor;
		fillColor = fillcolor;
	}

	public Rect(FixedLength x, FixedLength y, FixedLength width,
			FixedLength height, FixedLength linewidth, Color linecolor)
	{
		startX = x;
		startY = y;
		rectWidth = width;
		rectHeight = height;
		lineWidth = linewidth;
		lineColor = linecolor;
	}

	/**
	 * 
	 * 获取svg:rect元素的代码
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
		output.append(FoXsltConstants.SVG_RECT);
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
		String startx = IoXslUtil.getValueOfCm(startX.getLengthValueString(),
				startX.getUnits(), coefficient);
		String starty = IoXslUtil.getValueOfCm(startY.getLengthValueString(),
				startY.getUnits(), coefficient);
		String rectwidth = IoXslUtil.getValueOfCm(rectWidth
				.getLengthValueString(), rectWidth.getUnits(), coefficient);
		String rectheight = IoXslUtil.getValueOfCm(rectHeight
				.getLengthValueString(), rectHeight.getUnits(), coefficient);
		String linewidth = IoXslUtil.getValueOfCm(lineWidth
				.getLengthValueString(), lineWidth.getUnits(), coefficient);
		output.append(ElementUtil.outputAttributes(FoXsltConstants.X, startx));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.Y, starty));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.WIDTH,
				rectwidth));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.HEIGHT,
				rectheight));
		output.append(ElementUtil.outputAttributes(
				FoXsltConstants.STROKE_WIDTH, linewidth));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.STROKE,
				new ColorWriter().write(lineColor)));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.FILL,
				new ColorWriter().write(fillColor)));
		if (transform != null)
		{
			output.append(transform.write());
		}
		return output.toString();
	}

	/**
	 * @返回 transform变量的值
	 */
	public Transform getTransform()
	{
		return transform;
	}

	/**
	 * @param transform
	 *            设置transform成员变量的值 值约束说明
	 */
	public void setTransform(Transform transform)
	{
		this.transform = transform;
	}
}
