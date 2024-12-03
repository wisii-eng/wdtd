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
 * @SwingUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-30
 */
public class SwingUtil
{
	// 线条类型
	/* 点线 */
	public static int DOTTEDLINE = 1;
	/* 虚线 */
	public static int DASGEDLINE = 2;
	/* 凹线 */
	public static int GROOVELINE = 3;
	/* 凸线 */
	public static int RIDGELINE = 4;

	/**
	 * 根据线条类型获得画笔Stroke .如实现Stroke,虚线Stroke等
	 * 
	 * @param：type：线条类型 height:画笔粗细
	 * 
	 * @return
	 * @exception
	 */
	public static Stroke getStrokefromLineType(int type, float height)
	{
		// 如果式点线
		if (type == DOTTEDLINE)
		{
			return new BasicStroke(height, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, (float) 1.0, new float[]
					{ 1, 1 }, 0);
		}
		// 如果是虚线
		else if (type == DASGEDLINE)
		{
			return new BasicStroke(height, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, (float) 1.0, new float[]
					{ 6, 1 }, 0);
		}
		// 如果是凹线
		else if (type == GROOVELINE)
		{
			return new BasicStroke(height, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, (float) 1.0, new float[]
					{ 3, 0, 1, 0 }, 0);
		}
		// 如果是凸线
		else if (type == RIDGELINE)
		{
			return new BasicStroke(height, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, (float) 1.0, new float[]
					{ 1, 0, 3, 0 }, 0);
		}
		// 否则返回实线
		else
		{
			return new BasicStroke(height);
		}
	}
}
