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
 * @Paths.java
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
public class Paths
{

	// 点集
	List<Point> points = new ArrayList<Point>();

	// 线宽
	FixedLength lineWidth;

	// 线的颜色
	Color lineColor = null;

	// 区域的填充颜色
	Color fillColor = null;

	// id属性值
	String idName = FoXsltConstants.ID + FoXsltConstants.PATH;

	// 是否封闭
	boolean flg;

	public Paths(List<Point> pointlist, FixedLength linewidth, Color linecolor,
			Color fillcolor, String idname, boolean fengbi)
	{
		points = pointlist;
		lineWidth = linewidth;
		lineColor = linecolor;
		fillColor = fillcolor;
		idName = idname;
		flg = fengbi;
	}

	public Paths(List<Point> pointlist, String idname)
	{
		points = pointlist;
		idName = idname;
	}

	public Paths(List<Point> pointlist, FixedLength linewidth, Color linecolor,
			String idname, boolean fengbi)
	{
		points = pointlist;
		lineWidth = linewidth;
		lineColor = linecolor;
		idName = idname;
		flg = fengbi;
	}

	/**
	 * 
	 * 获取svg:path元素的代码
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public String write()
	{
		StringBuffer output = new StringBuffer();
		output.append("<");
		output.append(FoXsltConstants.PATH);
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
		output.append(ElementUtil.outputAttributes(FoXsltConstants.ID, idName));
		if (lineWidth != null)
		{
			output.append(ElementUtil.outputAttributes(
					FoXsltConstants.STROKE_WIDTH, ""
							+ new Double(lineWidth.getLengthValueString())
									.doubleValue() * coefficient));
		}
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
			output.append(FoXsltConstants.D);
			output.append("=\"");
			int size = points.size();
			for (int i = 0; i < size; i++)
			{
				Point pointitem = points.get(i);
				double pointx = pointitem.getX() * coefficient;
				double pointy = pointitem.getY() * coefficient;
				if (i == 0)
				{
					output.append("M");
					output.append(" ");
					output.append(pointx);
					output.append(" ");
					output.append(pointy);
				} else
				{
					output.append(" ");
					output.append("L");
					output.append(" ");
					output.append(pointx);
					output.append(" ");
					output.append(pointy);
				}
				if (i == size - 1 && flg == true)
				{
					output.append(" ");
					output.append("z");
				}
			}
			output.append("\"");
		}
		return output.toString();
	}
}
