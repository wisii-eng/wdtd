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
 * @Transform.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.svg;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-5
 */
public class Transform
{

	// 旋转点横坐标
	FixedLength rotationX = new FixedLength(0d, "mm");

	// 旋转点纵坐标
	FixedLength rotationY = new FixedLength(0d, "mm");

	// 旋转角度
	int rotationAngle = 0;

	public Transform(FixedLength rotationx, FixedLength rotationy,
			int rotationangle)
	{
		rotationX = rotationx;
		rotationY = rotationy;
		rotationAngle = rotationangle;
	}

	public Transform(int rotationangle)
	{
		rotationAngle = rotationangle;
	}

	/**
	 * @返回 rotationX变量的值
	 */
	public FixedLength getRotationX()
	{
		return rotationX;
	}

	/**
	 * @param rotationX
	 *            设置rotationX成员变量的值 值约束说明
	 */
	public void setRotationX(FixedLength rotationX)
	{
		this.rotationX = rotationX;
	}

	/**
	 * @返回 rotationY变量的值
	 */
	public FixedLength getRotationY()
	{
		return rotationY;
	}

	/**
	 * @param rotationY
	 *            设置rotationY成员变量的值 值约束说明
	 */
	public void setRotationY(FixedLength rotationY)
	{
		this.rotationY = rotationY;
	}

	/**
	 * 
	 * 属性transform赋值语句的输出
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public String write()
	{
		int coefficient = IoXslUtil.getCoefficient();
		String rotationx = IoXslUtil.getValueOfCm(rotationX
				.getLengthValueString(), rotationX.getUnits(), coefficient);
		String rotationy = IoXslUtil.getValueOfCm(rotationY
				.getLengthValueString(), rotationY.getUnits(), coefficient);
		StringBuffer output = new StringBuffer();
		output.append(" ");
		output.append(FoXsltConstants.TRANSFORM);
		output.append("=\"");
		output.append(FoXsltConstants.TRANSLATE);
		output.append("(");
		output.append(rotationx);
		output.append(" ");
		output.append(rotationy);
		output.append(")");
		output.append(" ");
		output.append(FoXsltConstants.ROTATE);
		output.append("(");
		output.append(rotationAngle);
		output.append(")");
		output.append("\"");
		return output.toString();
	}
}
