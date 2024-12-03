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
 * @CommonBorderPaddingBackgroundWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.attribute;

import java.awt.Color;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground.BorderInfo;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.io.xsl.SelectOutputAttributeWriter;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-8
 */
public class CommonBorderPaddingBackgroundWriter extends OutputAttributeWriter
{

	SelectOutputAttributeWriter writerFactory = new SelectOutputAttributeWriter();

	/**
	 * 
	 * 获得指定边框的属性输出代码
	 * 
	 * @param style
	 *            边框类型
	 * @param border
	 *            边框
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getBorder(int style, BorderInfo border)
	{
		StringBuffer output = new StringBuffer();
		int borderStyle = -1;
		int borderColor = -1;
		int borderWidth = -1;
		int bstyle = border.getStyle();
		Color color = border.getColor();
		CondLengthProperty width = border.getWidth();
		if (width.getLengthValue() != 0.0D)
		{
			switch (style)
			{
				case CommonBorderPaddingBackground.BEFORE:
				{
					borderStyle = Constants.PR_BORDER_BEFORE_STYLE;
					borderColor = Constants.PR_BORDER_BEFORE_COLOR;
					borderWidth = Constants.PR_BORDER_BEFORE_WIDTH;
					break;
				}
				case CommonBorderPaddingBackground.AFTER:
				{
					borderStyle = Constants.PR_BORDER_AFTER_STYLE;
					borderColor = Constants.PR_BORDER_AFTER_COLOR;
					borderWidth = Constants.PR_BORDER_AFTER_WIDTH;
					break;
				}
				case CommonBorderPaddingBackground.START:
				{
					borderStyle = Constants.PR_BORDER_START_STYLE;
					borderColor = Constants.PR_BORDER_START_COLOR;
					borderWidth = Constants.PR_BORDER_START_WIDTH;
					break;
				}
				case CommonBorderPaddingBackground.END:
				{
					borderStyle = Constants.PR_BORDER_END_STYLE;
					borderColor = Constants.PR_BORDER_END_COLOR;
					borderWidth = Constants.PR_BORDER_END_WIDTH;
					break;
				}
				default:
					break;
			}
			output.append(writerFactory.write(borderStyle, bstyle));
			output.append(writerFactory.write(borderColor, color));
			output.append(writerFactory.write(borderWidth, width));
		}
		return output.toString();
	}

	/**
	 * 
	 * 获得缩进属性代码
	 * 
	 * @param style
	 *            缩进方向
	 * @param pad
	 *            缩进
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getPading(int style, CondLengthProperty pad)
	{
		StringBuffer output = new StringBuffer();
		int padding = -1;
		FixedLength paddingWidth = (FixedLength) pad.getLength();
		switch (style)
		{
			case CommonBorderPaddingBackground.BEFORE:
			{
				padding = Constants.PR_PADDING_BEFORE;
				break;
			}
			case CommonBorderPaddingBackground.AFTER:
			{
				padding = Constants.PR_PADDING_AFTER;
				break;
			}
			case CommonBorderPaddingBackground.START:
			{
				padding = Constants.PR_PADDING_START;
				break;
			}
			case CommonBorderPaddingBackground.END:
			{
				padding = Constants.PR_PADDING_END;
				break;
			}
			default:
				break;
		}
		output.append(writerFactory.write(padding, paddingWidth));
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter#write(java.lang
	 * .Object)
	 */
	public String write(Object value)
	{
		StringBuffer output = new StringBuffer();
		CommonBorderPaddingBackground borderPaddingBackground = (CommonBorderPaddingBackground) value;
		if (!(borderPaddingBackground.getBackgroundAttachment() == Constants.EN_SCROLL))
		{
			output.append(writerFactory.write(
					Constants.PR_BACKGROUND_ATTACHMENT, new EnumProperty(
							borderPaddingBackground.getBackgroundAttachment(),
							"")));
		}
		Color color = borderPaddingBackground.getBackgroundColor();
		if (color != null)
		{
			output.append(writerFactory.write(Constants.PR_BACKGROUND_COLOR,
					color));
		}
		String image = borderPaddingBackground.getBackgroundImage();
		if (image != null && !"".equals(image))
		{
			output.append(writerFactory.write(Constants.PR_BACKGROUND_IMAGE,
					image));
			int repeat = borderPaddingBackground.getBackgroundRepeat();
			if (repeat != Constants.EN_REPEAT)
			{
				output.append(writerFactory.write(
						Constants.PR_BACKGROUND_REPEAT, new EnumProperty(
								borderPaddingBackground.getBackgroundRepeat(),
								"")));
			}
			Length hlength = borderPaddingBackground
					.getBackgroundPositionHorizontal();
			boolean flgh = true;
			if (hlength instanceof FixedLength)
			{
				FixedLength fixedlength = (FixedLength) hlength;
				if (fixedlength.getValue() == 0)
				{
					flgh = false;
				}
			} else if (hlength instanceof PercentLength)
			{
				PercentLength percentlength = (PercentLength) hlength;
				double factor = percentlength.value();
				if (factor == 0)
				{
					flgh = false;
				}
			}
			if (flgh)
			{
				output.append(writerFactory.write(
						Constants.PR_BACKGROUND_POSITION_HORIZONTAL, hlength));
			}
			Length vlength = borderPaddingBackground
					.getBackgroundPositionVertical();
			boolean flgv = true;
			if (vlength instanceof FixedLength)
			{
				FixedLength fixedlength = (FixedLength) vlength;
				if (fixedlength.getValue() == 0)
				{
					flgv = false;
				}
			} else if (vlength instanceof PercentLength)
			{
				PercentLength percentlength = (PercentLength) vlength;
				double factor = percentlength.value();
				if (factor == 0)
				{
					flgv = false;
				}
			}
			if (flgv)
			{
				output.append(writerFactory.write(
						Constants.PR_BACKGROUND_POSITION_VERTICAL, vlength));
			}
		}
		BorderInfo borderBefore = borderPaddingBackground
				.getBorderInfo(CommonBorderPaddingBackground.BEFORE);
		if (borderBefore != null)
		{
			output.append(getBorder(CommonBorderPaddingBackground.BEFORE,
					borderBefore));
		}
		BorderInfo borderAfter = borderPaddingBackground
				.getBorderInfo(CommonBorderPaddingBackground.AFTER);
		if (borderAfter != null)
		{
			output
					.append(getBorder(
							CommonBorderPaddingBackground.AFTER,
							borderPaddingBackground
									.getBorderInfo(CommonBorderPaddingBackground.AFTER)));
		}
		BorderInfo borderStart = borderPaddingBackground
				.getBorderInfo(CommonBorderPaddingBackground.START);
		if (borderStart != null)
		{
			output
					.append(getBorder(
							CommonBorderPaddingBackground.START,
							borderPaddingBackground
									.getBorderInfo(CommonBorderPaddingBackground.START)));
		}
		BorderInfo borderEnd = borderPaddingBackground
				.getBorderInfo(CommonBorderPaddingBackground.END);
		if (borderEnd != null)
		{
			output.append(getBorder(CommonBorderPaddingBackground.END,
					borderPaddingBackground
							.getBorderInfo(CommonBorderPaddingBackground.END)));
		}
		CondLengthProperty padBefore = borderPaddingBackground
				.getPaddingLengthProperty(CommonBorderPaddingBackground.BEFORE);

		if (padBefore != null && padBefore.getLengthValue() != 0.0d)
		{
			output.append(getPading(CommonBorderPaddingBackground.BEFORE,
					padBefore));
		}
		CondLengthProperty padAfter = borderPaddingBackground
				.getPaddingLengthProperty(CommonBorderPaddingBackground.AFTER);
		if (padAfter != null && padAfter.getLengthValue() != 0.0d)
		{
			output.append(getPading(CommonBorderPaddingBackground.AFTER,
					padAfter));
		}
		CondLengthProperty padStart = borderPaddingBackground
				.getPaddingLengthProperty(CommonBorderPaddingBackground.START);

		if (padStart != null && padStart.getLengthValue() != 0.0d)
		{
			output.append(getPading(CommonBorderPaddingBackground.START,
					padStart));
		}
		CondLengthProperty padEnd = borderPaddingBackground
				.getPaddingLengthProperty(CommonBorderPaddingBackground.END);
		if (padEnd != null && padEnd.getLengthValue() != 0.0d)
		{
			output.append(getPading(CommonBorderPaddingBackground.END, padEnd));
		}
		return output.toString();
	}
}
