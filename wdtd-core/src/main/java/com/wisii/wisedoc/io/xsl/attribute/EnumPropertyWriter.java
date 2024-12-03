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
 * @InttoStringWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.attribute;

import java.util.HashMap;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-25
 */
public class EnumPropertyWriter extends OutputAttributeWriter
{

	protected HashMap<Integer, String> map = new HashMap<Integer, String>();

	public EnumPropertyWriter()
	{
		map.put(Constants.EN_NORMAL, FoXsltConstants.NORMAL);
		map.put(Constants.EN_DISCARD, FoXsltConstants.DISCARD);
		map.put(Constants.EN_RETAIN, FoXsltConstants.RETAIN);
		map.put(Constants.EN_CAPITALIZE, FoXsltConstants.CAPITALIZE);
		map.put(Constants.EN_UPPERCASE, FoXsltConstants.UPPERCASE);
		map.put(Constants.EN_LOWERCASE, FoXsltConstants.LOWERCASE);
		map.put(Constants.EN_AUTO, FoXsltConstants.AUTO);
		map.put(Constants.EN_COLUMN, FoXsltConstants.COLUMN);
		map.put(Constants.EN_EVEN, FoXsltConstants.EVEN);
		map.put(Constants.EN_END_ON_EVEN, FoXsltConstants.END_ON_EVEN);
		map.put(Constants.EN_END_ON_ODD, FoXsltConstants.END_ON_ODD);
		map.put(Constants.EN_NO_FORCE, FoXsltConstants.NO_FORCE);
		map.put(Constants.EN_BOLDER, FoXsltConstants.BOLDER);
		map.put(Constants.EN_LIGHTER, FoXsltConstants.LIGHTER);
		map.put(Constants.EN_100, FoXsltConstants.PR100);
		map.put(Constants.EN_200, FoXsltConstants.PR200);
		map.put(Constants.EN_300, FoXsltConstants.PR300);
		map.put(Constants.EN_400, FoXsltConstants.PR400);
		map.put(Constants.EN_500, FoXsltConstants.PR500);
		map.put(Constants.EN_600, FoXsltConstants.PR600);
		map.put(Constants.EN_700, FoXsltConstants.PR700);
		map.put(Constants.EN_800, FoXsltConstants.PR800);
		map.put(Constants.EN_900, FoXsltConstants.PR900);
		map.put(Constants.EN_ABSOLUTE, FoXsltConstants.ABSOLUTE);
		map.put(Constants.EN_FIXED, FoXsltConstants.FIXED);
		map.put(Constants.EN_BASELINE, FoXsltConstants.BASELINE);
		map.put(Constants.EN_BEFORE_EDGE, FoXsltConstants.BEFORE_EDGE);
		map
				.put(Constants.EN_TEXT_BEFORE_EDGE,
						FoXsltConstants.TEXT_BEFORE_EDGE);
		map.put(Constants.EN_MIDDLE, FoXsltConstants.MIDDLE);
		map.put(Constants.EN_CENTRAL, FoXsltConstants.CENTRAL);
		map.put(Constants.EN_AFTER_EDGE, FoXsltConstants.AFTER_EDGE);
		map.put(Constants.EN_TEXT_AFTER_EDGE, FoXsltConstants.TEXT_AFTER_EDGE);
		map.put(Constants.EN_IDEOGRAPHIC, FoXsltConstants.IDEOGRAPHIC);
		map.put(Constants.EN_ALPHABETIC, FoXsltConstants.ALPHABETIC);
		map.put(Constants.EN_HANGING, FoXsltConstants.HANGING);
		map.put(Constants.EN_MATHEMATICAL, FoXsltConstants.MATHEMATICAL);
		map.put(Constants.EN_SCROLL, FoXsltConstants.SCROLL);
		map.put(Constants.EN_REPEAT, FoXsltConstants.REPEAT);
		map.put(Constants.EN_REPEATX, FoXsltConstants.REPEATX);
		map.put(Constants.EN_REPEATY, FoXsltConstants.REPEATY);
		map.put(Constants.EN_BLANK, FoXsltConstants.BLANK);
		map.put(Constants.EN_NOT_BLANK, FoXsltConstants.NOT_BLANK);
		map.put(Constants.EN_ANY, FoXsltConstants.ANY);
		map.put(Constants.EN_TRUE, FoXsltConstants.TRUE);
		map.put(Constants.EN_FALSE, FoXsltConstants.FALSE);
		map.put(Constants.EN_SOLID, FoXsltConstants.SOLID);
		map.put(Constants.EN_DOTTED, FoXsltConstants.DOTTED);
		map.put(Constants.EN_DASHED, FoXsltConstants.DASHED);
		map.put(Constants.EN_GROOVE, FoXsltConstants.GROOVE);
		map.put(Constants.EN_DOUBLE, FoXsltConstants.DOUBLE);
		map.put(Constants.EN_RIDGE, FoXsltConstants.RIDGE);
		map.put(Constants.EN_INSET, FoXsltConstants.INSET);
		map.put(Constants.EN_OUTSET, FoXsltConstants.OUTSET);
		map.put(Constants.EN_PAGE, FoXsltConstants.PAGE);
		map.put(Constants.EN_ODD_PAGE, FoXsltConstants.ODD_PAGE);
		map.put(Constants.EN_EVEN_PAGE, FoXsltConstants.EVEN_PAGE);
		map.put(Constants.EN_BEFORE, FoXsltConstants.BEFORE);
		map.put(Constants.EN_AFTER, FoXsltConstants.AFTER);
		map.put(Constants.EN_START, FoXsltConstants.START);
		map.put(Constants.EN_END, FoXsltConstants.END);
		map.put(Constants.EN_TOP, FoXsltConstants.TOP);
		map.put(Constants.EN_BOTTOM, FoXsltConstants.BOTTOM);
		map.put(Constants.EN_LEFT, FoXsltConstants.LEFT);
		map.put(Constants.EN_RIGHT, FoXsltConstants.RIGHT);
		map.put(Constants.EN_INSIDE, FoXsltConstants.INSIDE);
		map.put(Constants.EN_OUTSIDE, FoXsltConstants.OUTSIDE);
		map.put(Constants.EN_BOTH, FoXsltConstants.BOTH);
		map.put(Constants.EN_NONE, FoXsltConstants.NONE);
		map.put(Constants.EN_LTR, FoXsltConstants.LTR);
		map.put(Constants.EN_RTL, FoXsltConstants.RTL);
		map.put(Constants.EN_CENTER, FoXsltConstants.CENTER);
		map.put(Constants.EN_USE_SCRIPT, FoXsltConstants.USE_SCRIPT);
		map.put(Constants.EN_NO_CHANGE, FoXsltConstants.NO_CHANGE);
		map.put(Constants.EN_RESET_SIZE, FoXsltConstants.RESET_SIZE);
		map.put(Constants.EN_SHOW, FoXsltConstants.SHOW);
		map.put(Constants.EN_HIDE, FoXsltConstants.HIDE);
		map.put(Constants.EN_LR_TB, FoXsltConstants.LR_TB);
		map.put(Constants.EN_TB_RL, FoXsltConstants.TB_RL);
		map.put(Constants.EN_RL_TB, FoXsltConstants.RL_TB);
		map.put(Constants.EN_IGNORE, FoXsltConstants.IGNORE);
		map.put(Constants.EN_PRESERVE, FoXsltConstants.PRESERVE);
		map.put(Constants.EN_IGNORE_IF_BEFORE_LINEFEED,
				FoXsltConstants.IGNORE_IF_BEFORE_LINEFEED);
		map.put(Constants.EN_IGNORE_IF_AFTER_LINEFEED,
				FoXsltConstants.IGNORE_IF_AFTER_LINEFEED);
		map.put(Constants.EN_IGNORE_IF_SURROUNDING_LINEFEED,
				FoXsltConstants.IGNORE_IF_SURROUNDING_LINEFEED);
		map.put(Constants.EN_CHARACTER_BY_CHARACTER,
				FoXsltConstants.CHARACTER_BY_CHARACTER);
		map.put(Constants.EN_WIDER, FoXsltConstants.WIDER);
		map.put(Constants.EN_NARROWER, FoXsltConstants.NARROWER);
		map.put(Constants.EN_ULTRA_CONDENSED, FoXsltConstants.ULTRA_CONDENSED);
		map.put(Constants.EN_EXTRA_CONDENSED, FoXsltConstants.EXTRA_CONDENSED);
		map.put(Constants.EN_CONDENSED, FoXsltConstants.CONDENSED);
		map.put(Constants.EN_SEMI_CONDENSED, FoXsltConstants.SEMI_CONDENSED);
		map.put(Constants.EN_SEMI_EXPANDED, FoXsltConstants.SEMI_EXPANDED);
		map.put(Constants.EN_EXPANDED, FoXsltConstants.EXPANDED);
		map.put(Constants.EN_EXTRA_EXPANDED, FoXsltConstants.EXTRA_EXPANDED);
		map.put(Constants.EN_ULTRA_EXPANDED, FoXsltConstants.ULTRA_EXPANDED);
		map.put(Constants.EN_ITALIC, FoXsltConstants.ITALIC);
		map.put(Constants.EN_OBLIQUE, FoXsltConstants.OBLIQUE);
		map.put(Constants.EN_BACKSLANT, FoXsltConstants.BACKSLANT);
		map.put(Constants.EN_SMALL_CAPS, FoXsltConstants.SMALL_CAPS);
		map.put(Constants.EN_REFERENCE_AREA, FoXsltConstants.REFERENCE_AREA);
		map.put(Constants.EN_SPACE, FoXsltConstants.TREAT_AS_SPACE);
		map.put(Constants.EN_RULE, FoXsltConstants.RULE);
		map.put(Constants.EN_DOTS, FoXsltConstants.DOTS);
		map.put(Constants.EN_USE_CONTENT, FoXsltConstants.USE_CONTENT);
		map.put(Constants.EN_TRADITIONAL, FoXsltConstants.TRADITIONAL);
		map.put(Constants.EN_TREAT_AS_SPACE, FoXsltConstants.TREAT_AS_SPACE);
		map.put(Constants.EN_TREAT_AS_ZERO_WIDTH_SPACE,
				FoXsltConstants.TREAT_AS_ZERO_WIDTH_SPACE);
		map.put(Constants.EN_CONSIDER_SHIFTS, FoXsltConstants.CONSIDER_SHIFTS);
		map
				.put(Constants.EN_DISREGARD_SHIFTS,
						FoXsltConstants.DISREGARD_SHIFTS);
		map.put(Constants.EN_LINE_HEIGHT, FoXsltConstants.LINE_HEIGHT);
		map.put(Constants.EN_FONT_HEIGHT, FoXsltConstants.FONT_HEIGHT);
		map.put(Constants.EN_MAX_HEIGHT, FoXsltConstants.MAX_HEIGHT);
		map.put(Constants.EN_ODD, FoXsltConstants.ODD);
		map.put(Constants.EN_VISIBLE, FoXsltConstants.VISIBLE);
		map.put(Constants.EN_HIDDEN, FoXsltConstants.HIDDEN);
		map.put(Constants.EN_ERROR_IF_OVERFLOW,
				FoXsltConstants.ERROR_IF_OVERFLOW);
		map.put(Constants.EN_AVOID, FoXsltConstants.AVOID);
		map.put(Constants.EN_ALWAYS, FoXsltConstants.ALWAYSA);
		map.put(Constants.EN_STATIC, FoXsltConstants.STATIC);
		map.put(Constants.EN_RELATIVE, FoXsltConstants.RELATIVE);
		map.put(Constants.EN_FIRST, FoXsltConstants.FIRST);
		map.put(Constants.EN_REST, FoXsltConstants.REST);
		map.put(Constants.EN_LAST, FoXsltConstants.LAST);
		map.put(Constants.EN_PERCEPTUAL, FoXsltConstants.PERCEPTUAL);
		map.put(Constants.EN_RELATIVE_COLOMETRIC,
				FoXsltConstants.RELATIVE_COLOMETRIC);
		map.put(Constants.EN_SATURATION, FoXsltConstants.SATURATION);
		map.put(Constants.EN_ABSOLUTE_COLORMETRIC,
				FoXsltConstants.ABSOLUTE_COLORMETRIC);
		map.put(Constants.EN_PAGE_SEQUENCE, FoXsltConstants.PAGE_SEQUENCE);
		map.put(Constants.EN_DOCUMENT, FoXsltConstants.DOCUMENT);
		map.put(Constants.EN_INTEGER_PIXELS, FoXsltConstants.INTEGER_PIXELS);
		map.put(Constants.EN_RESAMPLE_ANY_METHOD,
				FoXsltConstants.RESAMPLE_ANY_METHOD);
		map.put(Constants.EN_UNIFORM, FoXsltConstants.UNIFORM);
		map.put(Constants.EN_NON_UNIFORM, FoXsltConstants.NON_UNIFORM);
		map.put(Constants.EN_ALL, FoXsltConstants.ALL);
		map.put(Constants.EN_JUSTIFY, FoXsltConstants.JUSTIFY);
		map.put(Constants.EN_UNDERLINE, FoXsltConstants.UNDERLINE);
		map.put(Constants.EN_OVERLINE, FoXsltConstants.OVERLINE);
		map.put(Constants.EN_LINE_THROUGH, FoXsltConstants.LINE_THROUGH);
		map.put(Constants.EN_BLINK, FoXsltConstants.BLINK);
		map.put(Constants.EN_SUB, FoXsltConstants.SUB);
		map.put(Constants.EN_SUPER, FoXsltConstants.SUPER);
		map.put(Constants.EN_TEXT_TOP, FoXsltConstants.TEXT_TOP);
		map.put(Constants.EN_TEXT_BOTTOM, FoXsltConstants.TEXT_BOTTOM);
		map.put(Constants.EN_NO_WRAP, FoXsltConstants.NO_WRAP);
		map.put(Constants.EN_WRAP, FoXsltConstants.WRAP);
		map.put(Constants.EN_LINE, FoXsltConstants.LINEVALUE);
		map.put(Constants.EN_INDENT, FoXsltConstants.INDENT);
		map.put(Constants.EN_BLOCK, FoXsltConstants.BLOCKVALUE);
		map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_FUNC_BY_PARAM,
				FoXsltConstants.FUNC_BY_PARAM);
		map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_BIN_DATA_STR,
				FoXsltConstants.BIN_DATA_STR);
		map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_ASCENDING,
				FoXsltConstants.ASCENDING);
		map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_DESCENDING,
				FoXsltConstants.DESCENDING);
		map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_TEXT,
				FoXsltConstants.TEXTPR);
		map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_NUMBER,
				FoXsltConstants.NUMBERPR);
		map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_LOWER_FIRST,
				FoXsltConstants.LOWER_FIRST);
		map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_UPPER_FIRST,
				FoXsltConstants.UPPER_FIRST);
		map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_YES,
				FoXsltConstants.YES);
		map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_NO,
				FoXsltConstants.NO);
		map.put(Constants.EN_IGNORE, FoXsltConstants.IGNORE);
		map.put(Constants.EN_PRESERVE, FoXsltConstants.PRESERVE);
		map.put(Constants.EN_TREAT_AS_SPACE, FoXsltConstants.TREAT_AS_SPACE);
		map.put(Constants.EN_TREAT_AS_ZERO_WIDTH_SPACE,
				FoXsltConstants.TREAT_AS_ZERO_WIDTH_SPACE);
		// map.put(Constants.EN_MIX, FoXsltConstants.MIX);
//		map.put(Constants.EN_REPEAT, FoXsltConstants.REPEAT);
		map.put(Constants.EN_ONLY, FoXsltConstants.ONLY);

		// map.put(Constants.EN_XSL_PRECEDING, FoXsltConstants.XSL_PRECEDING);
		// map.put(Constants.EN_XSL_FOLLOWING, FoXsltConstants.XSL_FOLLOWING);
		// map.put(Constants.EN_XSL_ANY, FoXsltConstants.XSL_ANY);
		// map.put(Constants.EN_USE_TARGET_PROCESSING_CONTEXT,
		// FoXsltConstants.USE_TARGET_PROCESSING_CONTEXT);
		// map.put(Constants.EN_USE_NORMAL_STYLESHEET,
		// FoXsltConstants.USE_NORMAL_STYLESHEET);
		// map.put(Constants.EN_BOLD, FoXsltConstants.BOLD);
		// map.put(Constants.EN_SPELL_OUT, FoXsltConstants.SPELL_OUT);
		// map.put(Constants.EN_CODE, FoXsltConstants.CODE);
		// map.put(Constants.EN_DIGITS, FoXsltConstants.DIGITS);
		// map.put(Constants.EN_CONTINUOUS, FoXsltConstants.CONTINUOUS);
		// map.put(Constants.EN_ONCE, FoXsltConstants.ONCE);
		// map.put(Constants.EN_NO, FoXsltConstants.NO);
		// map.put(Constants.EN_REPLACE, FoXsltConstants.REPLACE);
		// map.put(Constants.EN_NEW, FoXsltConstants.NEW);
		// map.put(Constants.EN_FIRST_STARTING, FoXsltConstants.FIRST_STARTING);
		// map.put(Constants.EN_FIRST_INCLUDING_CARRYOVER,
		// FoXsltConstants.FIRST_INCLUDING_CARRYOVER);
		// map.put(Constants.EN_LAST_STARTING, FoXsltConstants.LAST_STARTING);
		// map.put(Constants.EN_LAST_ENDING, FoXsltConstants.LAST_ENDING);
		// map.put(Constants.EN_FIRST_STARTING_WITHIN_PAGE,
		// FoXsltConstants.FIRST_STARTING_WITHIN_PAGE);
		// map.put(Constants.EN_FIRST_INCLUDING_CARRYOVER,
		// FoXsltConstants.FIRST_INCLUDING_CARRYOVER);
		// map.put(Constants.EN_LAST_STARTING_WITHIN_PAGE,
		// FoXsltConstants.LAST_STARTING_WITHIN_PAGE);
		// map.put(Constants.EN_LAST_ENDING_WITHIN_PAGE,
		// FoXsltConstants.LAST_ENDING_WITHIN_PAGE);
		// map.put(Constants.EN_TABLE, FoXsltConstants.TABLEPR);
		// map.put(Constants.EN_TABLE_FRAGMENT, FoXsltConstants.TABLE_FRAGMENT);
		// map.put(Constants.EN_ONLY, FoXsltConstants.ONLY);
		map.put(Constants.EN_NOREPEAT, FoXsltConstants.NO_REPEAT);
		map.put(Constants.EN_POSITION_NUMBER_1, FoXsltConstants.POSITION_NUMBER_1);
		map.put(Constants.EN_POSITION_NUMBER_a, FoXsltConstants.POSITION_NUMBER_a);
		map.put(Constants.EN_POSITION_NUMBER_A, FoXsltConstants.POSITION_NUMBER_A);
		map.put(Constants.EN_POSITION_NUMBER_i, FoXsltConstants.POSITION_NUMBER_i);
		map.put(Constants.EN_POSITION_NUMBER_I, FoXsltConstants.POSITION_NUMBER_I);
		// map.put(Constants.EN_LINK, FoXsltConstants.LINK);
		// map.put(Constants.EN_VISITED, FoXsltConstants.VISITED);
		// map.put(Constants.EN_ACTIVE, FoXsltConstants.ACTIVE);
		// map.put(Constants.EN_HOVER, FoXsltConstants.HOVER);
		// map.put(Constants.EN_FOCUS, FoXsltConstants.FOCUS);
		// map.put(Constants.EN_TB_LR, FoXsltConstants.TB_LR);
		// map.put(Constants.EN_BT_LR, FoXsltConstants.BT_LR);
		// map.put(Constants.EN_BT_RL, FoXsltConstants.BT_RL);
		// map.put(Constants.EN_LR_BT, FoXsltConstants.LR_BT);
		// map.put(Constants.EN_RL_BT, FoXsltConstants.RL_BT);
		// map.put(Constants.EN_LR_ALTERNATING_RL_BT,
		// FoXsltConstants.LR_ALTERNATING_RL_BT);
		// map.put(Constants.EN_LR_ALTERNATING_RL_TB,
		// FoXsltConstants.LR_ALTERNATING_RL_TB);
		// map.put(Constants.EN_LR_INVERTING_RL_BT,
		// FoXsltConstants.LR_INVERTING_RL_BT);
		// map.put(Constants.EN_LR_INVERTING_RL_TB,
		// FoXsltConstants.LR_INVERTING_RL_TB);
		// map.put(Constants.EN_TB_LR_IN_LR_PAIRS,
		// FoXsltConstants.TB_LR_IN_LR_PAIRS);
		// map.put(Constants.EN_LR, FoXsltConstants.LR);
		// map.put(Constants.EN_RL, FoXsltConstants.RL);
		// map.put(Constants.EN_TB, FoXsltConstants.TB);
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
		String attributeValue = "";
		if (value instanceof EnumProperty)
		{
			EnumProperty intvalue = (EnumProperty) value;
			int strint = intvalue.getEnum();
			if (map.containsKey(strint))
			{
				attributeValue = map.get(strint);
			} else
			{
				LogUtil.debug("传入的参数\"" + value + "\"在属性值map中获取不到相应的值");
			}
		} else if (value instanceof Integer)
		{
			if (map.containsKey(value))
			{
				attributeValue = map.get(value);
			} else
			{
				LogUtil.debug("传入的参数\"" + value + "\"在属性值map中获取不到相应的值");
			}
		}
		return attributeValue;
	}

	public String getEnumValue(int key)
	{
		return map.get(key);
	}
}
