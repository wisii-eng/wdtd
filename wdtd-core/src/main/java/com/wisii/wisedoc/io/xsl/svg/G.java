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
 * @G.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.svg;

import java.awt.Color;

import com.wisii.wisedoc.io.xsl.attribute.ColorWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-5
 */
public class G
{
	// 线的颜色
	Color lineColor = new Color(0, 0, 0);

	// 旋转
	Transform transform;

	public G(Color linecolor, Transform transf)
	{
		lineColor = linecolor;
		this.setTransform(transf);
	}

	public G(Color linecolor)
	{
		lineColor = linecolor;
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

	/**
	 * 
	 * 元素svg:g代码头部的输出
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public String write()
	{
		StringBuffer output = new StringBuffer();
		output.append("<");
		output.append(FoXsltConstants.SVG_G);
		output.append(ElementUtil.outputAttributes(FoXsltConstants.STROKE,
				new ColorWriter().write(lineColor)));
		if (transform != null)
		{
			output.append(transform.write());
		}
		output.append(">");
		return output.toString();
	}

	/**
	 * 
	 * 元素svg:g代码结束部分的输出
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public String writeEnd()
	{
		return ElementUtil.endElement(FoXsltConstants.SVG_G);
	}
}
