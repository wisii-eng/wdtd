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
 * @AbstractAttributeWriter.java
 *                               汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.att;

import com.wisii.wisedoc.document.Constants;

/**
 * 类功能描述：抽象的属性writer类，实现一些属性相关的抽象方法
 * 
 * 作者：zhangqiang 创建日期：2008-9-19
 */
public abstract class AbstractAttributeWriter implements AttributeWriter
{
	protected String getKeyName(int key)
	{
		String keyname = null;
		switch (key)
		{
		case Constants.PR_FONT_FAMILY:
			keyname = "font-family";
			break;
		case Constants.PR_FONT_STYLE:
			keyname = "font-style";
			break;
		case Constants.PR_FONT_VARIANT:
			keyname = "font-variant";
			break;
		case Constants.PR_FONT_WEIGHT:
			keyname = "font-weight";
			break;
		case Constants.PR_FONT_SIZE:
			keyname = "font-size";
			break;
		case Constants.PR_COLOR:
			keyname = "color";
			break;
		case Constants.PR_BACKGROUND_COLOR:
			keyname = "background-color";
			break;
		case Constants.PR_BACKGROUND_IMAGE:
			keyname = "background-image";
			break;
		case Constants.PR_BACKGROUND_REPEAT:
			keyname = "background-repeat";
			break;
		case Constants.PR_BACKGROUND_ATTACHMENT:
			keyname = "background-attachment";
			break;
		case Constants.PR_BACKGROUND_POSITION:
			keyname = "background-position";
			break;
		case Constants.PR_WORD_SPACING:
			keyname = "word-spacing";
			break;
		case Constants.PR_LETTER_SPACING:
			keyname = "letter-spacing";
			break;
		case Constants.PR_TEXT_DECORATION:
			keyname = "text-decoration";
			break;
		case Constants.PR_TEXT_ALIGN:
			keyname = "text-align";
			break;
		case Constants.PR_TEXT_INDENT:
			keyname = "text-indent";
			break;
		case Constants.PR_LINE_HEIGHT:
			keyname = "line-height";
			break;
		case Constants.PR_MARGIN_TOP:
			keyname = "margin-top";
			break;
		case Constants.PR_MARGIN_LEFT:
			keyname = "margin-left";
			break;
		case Constants.PR_MARGIN_RIGHT:
			keyname = "margin-right";
			break;
		case Constants.PR_MARGIN_BOTTOM:
			keyname = "margin-bottom";
			break;
		case Constants.PR_PADDING_TOP:
			keyname = "padding-top";
			break;
		case Constants.PR_PADDING_LEFT:
			keyname = "padding-left";
			break;
		case Constants.PR_PADDING_RIGHT:
			keyname = "padding-right";
			break;
		case Constants.PR_PADDING_BOTTOM:
			keyname = "padding-bottom";
			break;
		case Constants.PR_PADDING_BEFORE:
			keyname = "padding-top";
			break;
		case Constants.PR_PADDING_START:
			keyname = "padding-left";
			break;
		case Constants.PR_PADDING_END:
			keyname = "padding-right";
			break;
		case Constants.PR_SPACE_BEFORE:
			keyname = "margin-top";
			break;
		case Constants.PR_SPACE_AFTER:
			keyname = "margin-bottom";
			break;
		case Constants.PR_PADDING_AFTER:
			keyname = "padding-bottom";
			break;
		case Constants.PR_BORDER_TOP_WIDTH:
			keyname = "border-top-width";
			break;
		case Constants.PR_BORDER_BEFORE_WIDTH:
			keyname = "border-top-width";
			break;
		case Constants.PR_BORDER_BOTTOM_WIDTH:
			keyname = "border-bottom-width";
			break;
		case Constants.PR_BORDER_AFTER_WIDTH:
			keyname = "border-bottom-width";
			break;
		case Constants.PR_BORDER_LEFT_WIDTH:
			keyname = "border-left-width";
			break;
		case Constants.PR_BORDER_START_WIDTH:
			keyname = "border-left-width";
			break;
		case Constants.PR_BORDER_RIGHT_WIDTH:
			keyname = "border-right-width";
			break;
		case Constants.PR_BORDER_END_WIDTH:
			keyname = "border-right-width";
			break;
		case Constants.PR_WIDTH:
			keyname = "width";
			break;
		case Constants.PR_HEIGHT:
			keyname = "height";
			break;
		case Constants.PR_BLOCK_PROGRESSION_DIMENSION:
			keyname = "height";
			break;
		case Constants.PR_INLINE_PROGRESSION_DIMENSION:
			keyname = "width";
			break;
		case Constants.PR_COLUMN_WIDTH:
			keyname = "width";
			break;
		case Constants.PR_POSITION:
			keyname = "position";
			break;
		case Constants.PR_ABSOLUTE_POSITION:
			keyname = "position";
			break;
		case Constants.PR_WHITE_SPACE_TREATMENT:
			keyname = "white-space";
			break;
		case Constants.PR_TOP:
			keyname = "top";
			break;
		case Constants.PR_LEFT:
			keyname = "left";
			break;
		default:
			break;
		}
		return keyname;
	}

	public String getString(int key, Object value)
	{
		String keyname = getKeyName(key);
		if (keyname == null)
		{
			return "";
		}
		return keyname + ": " + getValue(value) + ";";
	}
}
