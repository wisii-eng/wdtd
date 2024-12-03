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
 * @Svg.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.svg;

import java.awt.Color;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-5
 */
public class Svg
{

	// svg宽度
	FixedLength svgWidth;

	// svg高度
	FixedLength svgHeight;

	public Svg(FixedLength svgwidth, FixedLength svgheight)
	{
		svgWidth = svgwidth;
		svgHeight = svgheight;
	}

	/**
	 * 
	 * 获取svg:svg元素的头部代码
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public String write()
	{
		StringBuffer output = new StringBuffer();
		output.append("<");
		output.append(FoXsltConstants.SVG_SVG);
		output.append(getAttributes());
		output.append(">");
		return output.toString();
	}

	/**
	 * 
	 * 获取svg:svg元素的尾部代码
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public String writeEnd()
	{
		return ElementUtil.endElement(FoXsltConstants.SVG_SVG);
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
		StringBuffer output = new StringBuffer();
		output.append(ElementUtil.outputAttributes(FoXsltConstants.HEIGHT,
				svgHeight.getLengthValueString() + svgHeight.getUnits()));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.WIDTH,
				svgWidth.getLengthValueString() + svgWidth.getUnits()));
		output.append(getViewbox());
		return output.toString();
	}

	/**
	 * 
	 * 获取Viewbox属性部分的代码
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public String getViewbox()
	{
		int coefficient = IoXslUtil.getCoefficient();
		StringBuffer output = new StringBuffer();
		output.append(" ");
		output.append(FoXsltConstants.VIEWBOX);
		output.append("=\"");
		String width = IoXslUtil.getValueOfCm(svgWidth.getLengthValueString(),
				svgWidth.getUnits(), coefficient);
		String height = IoXslUtil.getValueOfCm(
				svgHeight.getLengthValueString(), svgHeight.getUnits(),
				coefficient);
		output.append("0 0 ");
		output.append(width);
		output.append(" ");
		output.append(height);
		output.append("\"");
		return output.toString();
	}

	public static void main(String[] args)
	{
		// Svg
		// FixedLength svgwidth = new FixedLength(20, "cm");
		// FixedLength svgheight = new FixedLength(10, "cm");
		// Svg svg = new Svg(svgwidth, svgheight);
		// String svgstr = svg.write();
		// System.out.println(svgstr);
		// Line
		// FixedLength linewidth = new FixedLength(1, "pt");
		// FixedLength x0 = new FixedLength(0, "");
		// FixedLength x = new FixedLength(10, "cm");
		// Color black = new Color(0, 0, 0);
		// Line line = new Line(x0, x0, x, x, linewidth, black);
		// String linestr = line.write();
		// System.out.println(linestr);
		// Circle
		// FixedLength linewidth = new FixedLength(1, "pt");
		// FixedLength yuanxin = new FixedLength(5, "cm");
		// FixedLength r = new FixedLength(4, "cm");
		// Color black = new Color(0, 0, 0);
		// Circle circle = new Circle(yuanxin, yuanxin, r, linewidth, black);
		// String circlestr = circle.write();
		// System.out.println(circlestr);
		// Ellipse
		// FixedLength linewidth = new FixedLength(1, "pt");
		// FixedLength yuanxin = new FixedLength(5, "cm");
		// FixedLength r = new FixedLength(4, "cm");
		// Color black = new Color(0, 0, 0);
		// Ellipse ellipse = new Ellipse(yuanxin, yuanxin, r, r, linewidth,
		// black);
		// String ellipsestr = ellipse.write();
		// System.out.println(ellipsestr);
		// Rect
		// FixedLength linewidth = new FixedLength(1, "pt");
		// FixedLength yuanxin = new FixedLength(5, "cm");
		// FixedLength r = new FixedLength(4, "cm");
		// Color black = new Color(0, 0, 0);
		// Rect rect = new Rect(yuanxin, yuanxin, r, r, linewidth, black);
		// String rectstr = rect.write();
		// System.out.println(rectstr);
		// Transform
		// FixedLength yuanxin = new FixedLength(5, "cm");
		// Transform transform = new Transform(yuanxin, yuanxin,90);
		// String transformstr = transform.write();
		// System.out.println(transformstr);
		// G
		FixedLength yuanxin = new FixedLength(5, "cm");
		Transform transform = new Transform(yuanxin, yuanxin, 90);
		Color color = new Color(255, 255, 0);
		G g = new G(color);
		g.setTransform(transform);
		// String gstr = g.write();
		// System.out.println(gstr);
	}
}
