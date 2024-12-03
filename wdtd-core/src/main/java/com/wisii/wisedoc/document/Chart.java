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
 * @Chart.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.image.BufferedImage;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.FixedLength;
/**
 * 类功能描述：统计图
 *
 * 作者：zhangqiang
 * 创建日期：2009-5-18
 */
public class Chart extends AbstractGraphics
{
	private BufferedImage image;

	public Chart()
	{
	}

	public Chart(final Map<Integer, Object> attributes)
	{
		super(attributes);
		initImageData();
	}

	private void initImageData()
	{
		image = ChartUtil.getChartImage(getAttributes().getAttributes());
	}

	public Element clone()
	{
		Chart chart = (Chart) super.clone();
		chart.initImageData();
		return chart;
	}

	@Override
	public int getIntrinsicHeight()
	{
		return ((FixedLength)getAttribute(Constants.PR_HEIGHT)).getValue();
	}

	@Override
	public int getIntrinsicWidth()
	{
		return ((FixedLength)getAttribute(Constants.PR_WIDTH)).getValue();
	}

	@Override
	public void setAttributes(Map<Integer, Object> atts, boolean isreplace)
	{
		super.setAttributes(atts, isreplace);
		initImageData();
	}

	public BufferedImage getImage()
	{
		if (image == null)
		{
			initImageData();
		}
		return image;
	}
}
