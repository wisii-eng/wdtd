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
 * @AbstractEnumAttribyteWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.att;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：抽象的枚举类型的writer，为枚举类型的writer提供公共功能
 * 
 * 作者：zhangqiang 创建日期：2008-9-19
 */
public abstract class AbstractEnumAttribyteWriter extends
		AbstractAttributeWriter {
	// 枚举值和枚举字符串的对应关系map
	private final Map<Integer, String> _map = new HashMap<Integer, String>();

	public AbstractEnumAttribyteWriter() {
		init();
	}

	/*
	 * 
	 * 初始化枚举map属性
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private void init() {
		_map.put(Constants.EN_BIDI_OVERRIDE, FoXsltConstants.BIDI_OVERRIDE);
		_map.put(Constants.EN_BOUNDED_IN_ONE_DIMENSION, FoXsltConstants.BOUNDED_IN_ONE_DIMENSION);
		_map.put(Constants.EN_COLLAPSE, FoXsltConstants.COLLAPSE);
		_map.put(Constants.EN_COLLAPSE_WITH_PRECEDENCE, FoXsltConstants.COLLAPSE_WITH_PRECEDENCE);
		_map.put(Constants.EN_FIC, FoXsltConstants.FIC);
		_map.put(Constants.EN_FORCE, FoXsltConstants.FORCE);
		_map.put(Constants.EN_FSWP, FoXsltConstants.FSWP);
		_map.put(Constants.EN_INDEFINITE, FoXsltConstants.INDEFINITE);
		_map.put(Constants.EN_INHERIT, FoXsltConstants.INHERIT);
		_map.put(Constants.EN_LARGER, FoXsltConstants.LARGER);
		_map.put(Constants.EN_LEWP, FoXsltConstants.LEWP);
		_map.put(Constants.EN_LSWP, FoXsltConstants.LSWP);
		_map.put(Constants.EN_NO_BLINK, FoXsltConstants.NO_BLINK);
		_map.put(Constants.EN_NO_LINE_THROUGH, FoXsltConstants.NO_LINE_THROUGH);
		_map.put(Constants.EN_NO_OVERLINE, FoXsltConstants.NO_OVERLINE);
		_map.put(Constants.EN_NO_UNDERLINE, FoXsltConstants.NO_UNDERLINE);
		_map.put(Constants.EN_NOREPEAT, FoXsltConstants.NOREPEAT);
		_map.put(Constants.EN_PAGINATE, FoXsltConstants.PAGINATE);
		_map.put(Constants.EN_SCALE_TO_FIT, FoXsltConstants.SCALE_TO_FIT);
		_map.put(Constants.EN_SEPARATE, FoXsltConstants.SEPARATE);
		_map.put(Constants.EN_SMALLER, FoXsltConstants.SMALLER);
		_map.put(Constants.EN_SUPPRESS, FoXsltConstants.SUPPRESS);
		_map.put(Constants.EN_UNBOUNDED, FoXsltConstants.UNBOUNDED);
		_map.put(Constants.EN_X_FILL, FoXsltConstants.X_FILL);
		_map.put(Constants.EN_X_DISTRIBUTE, FoXsltConstants.X_DISTRIBUTE);
		_map.put(Constants.EN_PRE, FoXsltConstants.PRE);
		_map.put(Constants.EN_CAPTION, FoXsltConstants.CAPTION);
		_map.put(Constants.EN_ICON, FoXsltConstants.ICON);
		_map.put(Constants.EN_MENU, FoXsltConstants.MENU);
		_map.put(Constants.EN_MESSAGE_BOX, FoXsltConstants.MESSAGE_BOX);
		_map.put(Constants.EN_SMALL_CAPTION, FoXsltConstants.SMALL_CAPTION);
		_map.put(Constants.EN_STATUS_BAR, FoXsltConstants.STATUS_BAR);
		_map.put(Constants.EN_NORMAL, FoXsltConstants.NORMAL);
		_map.put(Constants.EN_DISCARD, FoXsltConstants.DISCARD);
		_map.put(Constants.EN_RETAIN, FoXsltConstants.RETAIN);
		_map.put(Constants.EN_CAPITALIZE, FoXsltConstants.CAPITALIZE);
		_map.put(Constants.EN_UPPERCASE, FoXsltConstants.UPPERCASE);
		_map.put(Constants.EN_LOWERCASE, FoXsltConstants.LOWERCASE);
		_map.put(Constants.EN_AUTO, FoXsltConstants.AUTO);
		_map.put(Constants.EN_AUTO_ODD, FoXsltConstants.AUTO_ODD);
		_map.put(Constants.EN_AUTO_EVEN, FoXsltConstants.AUTO_EVEN);
		_map.put(Constants.EN_COLUMN, FoXsltConstants.COLUMN);
		_map.put(Constants.EN_EVEN, FoXsltConstants.EVEN);
		_map.put(Constants.EN_END_ON_EVEN, FoXsltConstants.END_ON_EVEN);
		_map.put(Constants.EN_END_ON_ODD, FoXsltConstants.END_ON_ODD);
		_map.put(Constants.EN_NO_FORCE, FoXsltConstants.NO_FORCE);
		_map.put(Constants.EN_NO_LIMIT, FoXsltConstants.NO_LIMIT);
		_map.put(Constants.EN_BOLDER, FoXsltConstants.BOLDER);
		_map.put(Constants.EN_LIGHTER, FoXsltConstants.LIGHTER);
		_map.put(Constants.EN_100, FoXsltConstants.PR100);
		_map.put(Constants.EN_200, FoXsltConstants.PR200);
		_map.put(Constants.EN_300, FoXsltConstants.PR300);
		_map.put(Constants.EN_400, FoXsltConstants.PR400);
		_map.put(Constants.EN_500, FoXsltConstants.PR500);
		_map.put(Constants.EN_600, FoXsltConstants.PR600);
		_map.put(Constants.EN_700, FoXsltConstants.PR700);
		_map.put(Constants.EN_800, FoXsltConstants.PR800);
		_map.put(Constants.EN_900, FoXsltConstants.PR900);
		_map.put(Constants.EN_ABSOLUTE, FoXsltConstants.ABSOLUTE);
		_map.put(Constants.EN_FIXED, FoXsltConstants.FIXED);
		_map.put(Constants.EN_BASELINE, FoXsltConstants.BASELINE);
		_map.put(Constants.EN_BEFORE_EDGE, FoXsltConstants.BEFORE_EDGE);
		_map
				.put(Constants.EN_TEXT_BEFORE_EDGE,
						FoXsltConstants.TEXT_BEFORE_EDGE);
		_map.put(Constants.EN_MIDDLE, FoXsltConstants.MIDDLE);
		_map.put(Constants.EN_CENTRAL, FoXsltConstants.CENTRAL);
		_map.put(Constants.EN_AFTER_EDGE, FoXsltConstants.AFTER_EDGE);
		_map.put(Constants.EN_TEXT_AFTER_EDGE, FoXsltConstants.TEXT_AFTER_EDGE);
		_map.put(Constants.EN_IDEOGRAPHIC, FoXsltConstants.IDEOGRAPHIC);
		_map.put(Constants.EN_ALPHABETIC, FoXsltConstants.ALPHABETIC);
		_map.put(Constants.EN_HANGING, FoXsltConstants.HANGING);
		_map.put(Constants.EN_MATHEMATICAL, FoXsltConstants.MATHEMATICAL);
		_map.put(Constants.EN_SCROLL, FoXsltConstants.SCROLL);
		_map.put(Constants.EN_REPEAT, FoXsltConstants.REPEAT);
		_map.put(Constants.EN_REPEATX, FoXsltConstants.REPEATX);
		_map.put(Constants.EN_REPEATY, FoXsltConstants.REPEATY);
		_map.put(Constants.EN_BLANK, FoXsltConstants.BLANK);
		_map.put(Constants.EN_NOT_BLANK, FoXsltConstants.NOT_BLANK);
		_map.put(Constants.EN_ANY, FoXsltConstants.ANY);
		_map.put(Constants.EN_TRUE, FoXsltConstants.TRUE);
		_map.put(Constants.EN_FALSE, FoXsltConstants.FALSE);
		_map.put(Constants.EN_SOLID, FoXsltConstants.SOLID);
		_map.put(Constants.EN_DOTTED, FoXsltConstants.DOTTED);
		_map.put(Constants.EN_DASHED, FoXsltConstants.DASHED);
		_map.put(Constants.EN_GROOVE, FoXsltConstants.GROOVE);
		_map.put(Constants.EN_DOUBLE, FoXsltConstants.DOUBLE);
		_map.put(Constants.EN_RIDGE, FoXsltConstants.RIDGE);
		_map.put(Constants.EN_INSET, FoXsltConstants.INSET);
		_map.put(Constants.EN_OUTSET, FoXsltConstants.OUTSET);
		_map.put(Constants.EN_PAGE, FoXsltConstants.PAGE);
		_map.put(Constants.EN_ODD_PAGE, FoXsltConstants.ODD_PAGE);
		_map.put(Constants.EN_EVEN_PAGE, FoXsltConstants.EVEN_PAGE);
		_map.put(Constants.EN_BEFORE, FoXsltConstants.TOP);
		_map.put(Constants.EN_AFTER, FoXsltConstants.BOTTOM);
		_map.put(Constants.EN_START, FoXsltConstants.LEFT);
		_map.put(Constants.EN_END, FoXsltConstants.RIGHT);
		_map.put(Constants.EN_TOP, FoXsltConstants.TOP);
		_map.put(Constants.EN_BOTTOM, FoXsltConstants.BOTTOM);
		_map.put(Constants.EN_LEFT, FoXsltConstants.LEFT);
		_map.put(Constants.EN_RIGHT, FoXsltConstants.RIGHT);
		_map.put(Constants.EN_INSIDE, FoXsltConstants.INSIDE);
		_map.put(Constants.EN_OUTSIDE, FoXsltConstants.OUTSIDE);
		_map.put(Constants.EN_BOTH, FoXsltConstants.BOTH);
		_map.put(Constants.EN_NONE, FoXsltConstants.NONE);
		_map.put(Constants.EN_LTR, FoXsltConstants.LTR);
		_map.put(Constants.EN_RTL, FoXsltConstants.RTL);
		_map.put(Constants.EN_CENTER, FoXsltConstants.CENTER);
		_map.put(Constants.EN_USE_SCRIPT, FoXsltConstants.USE_SCRIPT);
		_map.put(Constants.EN_NO_CHANGE, FoXsltConstants.NO_CHANGE);
		_map.put(Constants.EN_RESET_SIZE, FoXsltConstants.RESET_SIZE);
		_map.put(Constants.EN_SHOW, FoXsltConstants.SHOW);
		_map.put(Constants.EN_HIDE, FoXsltConstants.HIDE);
		_map.put(Constants.EN_LR_TB, FoXsltConstants.LR_TB);
		_map.put(Constants.EN_TB_RL, FoXsltConstants.TB_RL);
		_map.put(Constants.EN_RL_TB, FoXsltConstants.RL_TB);
		_map.put(Constants.EN_IGNORE, FoXsltConstants.IGNORE);
		_map.put(Constants.EN_PRESERVE, FoXsltConstants.PRE);
		_map.put(Constants.EN_IGNORE_IF_BEFORE_LINEFEED,
				FoXsltConstants.IGNORE_IF_BEFORE_LINEFEED);
		_map.put(Constants.EN_IGNORE_IF_AFTER_LINEFEED,
				FoXsltConstants.IGNORE_IF_AFTER_LINEFEED);
		_map.put(Constants.EN_IGNORE_IF_SURROUNDING_LINEFEED,
				FoXsltConstants.IGNORE_IF_SURROUNDING_LINEFEED);
		_map.put(Constants.EN_CHARACTER_BY_CHARACTER,
				FoXsltConstants.CHARACTER_BY_CHARACTER);
		_map.put(Constants.EN_WIDER, FoXsltConstants.WIDER);
		_map.put(Constants.EN_NARROWER, FoXsltConstants.NARROWER);
		_map.put(Constants.EN_ULTRA_CONDENSED, FoXsltConstants.ULTRA_CONDENSED);
		_map.put(Constants.EN_EXTRA_CONDENSED, FoXsltConstants.EXTRA_CONDENSED);
		_map.put(Constants.EN_CONDENSED, FoXsltConstants.CONDENSED);
		_map.put(Constants.EN_SEMI_CONDENSED, FoXsltConstants.SEMI_CONDENSED);
		_map.put(Constants.EN_SEMI_EXPANDED, FoXsltConstants.SEMI_EXPANDED);
		_map.put(Constants.EN_EXPANDED, FoXsltConstants.EXPANDED);
		_map.put(Constants.EN_EXTRA_EXPANDED, FoXsltConstants.EXTRA_EXPANDED);
		_map.put(Constants.EN_ULTRA_EXPANDED, FoXsltConstants.ULTRA_EXPANDED);
		_map.put(Constants.EN_ITALIC, FoXsltConstants.ITALIC);
		_map.put(Constants.EN_OBLIQUE, FoXsltConstants.OBLIQUE);
		_map.put(Constants.EN_BACKSLANT, FoXsltConstants.BACKSLANT);
		_map.put(Constants.EN_SMALL_CAPS, FoXsltConstants.SMALL_CAPS);
		_map.put(Constants.EN_REFERENCE_AREA, FoXsltConstants.REFERENCE_AREA);
		_map.put(Constants.EN_SPACE, FoXsltConstants.SPACE);
		_map.put(Constants.EN_RULE, FoXsltConstants.RULE);
		_map.put(Constants.EN_DOTS, FoXsltConstants.DOTS);
		_map.put(Constants.EN_USE_CONTENT, FoXsltConstants.USE_CONTENT);
		_map.put(Constants.EN_TRADITIONAL, FoXsltConstants.TRADITIONAL);
		_map.put(Constants.EN_TREAT_AS_SPACE, FoXsltConstants.TREAT_AS_SPACE);
		_map.put(Constants.EN_TREAT_AS_ZERO_WIDTH_SPACE,
				FoXsltConstants.TREAT_AS_ZERO_WIDTH_SPACE);
		_map.put(Constants.EN_CONSIDER_SHIFTS, FoXsltConstants.CONSIDER_SHIFTS);
		_map
				.put(Constants.EN_DISREGARD_SHIFTS,
						FoXsltConstants.DISREGARD_SHIFTS);
		_map.put(Constants.EN_LINE_HEIGHT, FoXsltConstants.LINE_HEIGHT);
		_map.put(Constants.EN_FONT_HEIGHT, FoXsltConstants.FONT_HEIGHT);
		_map.put(Constants.EN_MAX_HEIGHT, FoXsltConstants.MAX_HEIGHT);
		_map.put(Constants.EN_ODD, FoXsltConstants.ODD);
		_map.put(Constants.EN_VISIBLE, FoXsltConstants.VISIBLE);
		_map.put(Constants.EN_HIDDEN, FoXsltConstants.HIDDEN);
		_map.put(Constants.EN_ERROR_IF_OVERFLOW,
				FoXsltConstants.ERROR_IF_OVERFLOW);
		_map.put(Constants.EN_AVOID, FoXsltConstants.AVOID);
		_map.put(Constants.EN_ALWAYS, FoXsltConstants.ALWAYSA);
		_map.put(Constants.EN_STATIC, FoXsltConstants.STATIC);
		_map.put(Constants.EN_FIRST, FoXsltConstants.FIRST);
		_map.put(Constants.EN_REST, FoXsltConstants.REST);
		_map.put(Constants.EN_LAST, FoXsltConstants.LAST);
		_map.put(Constants.EN_PERCEPTUAL, FoXsltConstants.PERCEPTUAL);
		_map.put(Constants.EN_RELATIVE_COLOMETRIC,
				FoXsltConstants.RELATIVE_COLOMETRIC);
		_map.put(Constants.EN_SATURATION, FoXsltConstants.SATURATION);
		_map.put(Constants.EN_ABSOLUTE_COLORMETRIC,
				FoXsltConstants.ABSOLUTE_COLORMETRIC);
		_map.put(Constants.EN_PAGE_SEQUENCE, FoXsltConstants.PAGE_SEQUENCE);
		_map.put(Constants.EN_DOCUMENT, FoXsltConstants.DOCUMENT);
		_map.put(Constants.EN_INTEGER_PIXELS, FoXsltConstants.INTEGER_PIXELS);
		_map.put(Constants.EN_RESAMPLE_ANY_METHOD,
				FoXsltConstants.RESAMPLE_ANY_METHOD);
		_map.put(Constants.EN_UNIFORM, FoXsltConstants.UNIFORM);
		_map.put(Constants.EN_NON_UNIFORM, FoXsltConstants.NON_UNIFORM);
		_map.put(Constants.EN_ALL, FoXsltConstants.ALL);
		_map.put(Constants.EN_JUSTIFY, FoXsltConstants.JUSTIFY);
		_map.put(Constants.EN_UNDERLINE, FoXsltConstants.UNDERLINE);
		_map.put(Constants.EN_OVERLINE, FoXsltConstants.OVERLINE);
		_map.put(Constants.EN_LINE_THROUGH, FoXsltConstants.LINE_THROUGH);
		_map.put(Constants.EN_BLINK, FoXsltConstants.BLINK);
		_map.put(Constants.EN_SUB, FoXsltConstants.SUB);
		_map.put(Constants.EN_SUPER, FoXsltConstants.SUPER);
		_map.put(Constants.EN_TEXT_TOP, FoXsltConstants.TEXT_TOP);
		_map.put(Constants.EN_TEXT_BOTTOM, FoXsltConstants.TEXT_BOTTOM);
		_map.put(Constants.EN_NO_WRAP, FoXsltConstants.NO_WRAP);
		_map.put(Constants.EN_WRAP, FoXsltConstants.WRAP);
		_map.put(Constants.EN_LINE, FoXsltConstants.LINEVALUE);
		_map.put(Constants.EN_INDENT, FoXsltConstants.INDENT);
		_map.put(Constants.EN_BLOCK, FoXsltConstants.BLOCKVALUE);
		_map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_FUNC_BY_PARAM,
				FoXsltConstants.FUNC_BY_PARAM);
		_map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_BIN_DATA_STR,
				FoXsltConstants.BIN_DATA_STR);
		_map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_ASCENDING,
				FoXsltConstants.ASCENDING);
		_map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_DESCENDING,
				FoXsltConstants.DESCENDING);
		_map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_TEXT,
				FoXsltConstants.TEXTPR);
		_map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_NUMBER,
				FoXsltConstants.NUMBERPR);
		_map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_LOWER_FIRST,
				FoXsltConstants.LOWER_FIRST);
		_map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_UPPER_FIRST,
				FoXsltConstants.UPPER_FIRST);
		_map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_YES,
				FoXsltConstants.YES);
		_map.put(com.wisii.wisedoc.io.xsl.util.Constants.EN_XSL_NO,
				FoXsltConstants.NO);
		_map.put(Constants.EN_IGNORE, FoXsltConstants.NORMAL);
		_map.put(Constants.EN_TREAT_AS_SPACE, FoXsltConstants.TREAT_AS_SPACE);
		_map.put(Constants.EN_TREAT_AS_ZERO_WIDTH_SPACE,
				FoXsltConstants.TREAT_AS_ZERO_WIDTH_SPACE);
		// map.put(Constants.EN_MIX, FoXsltConstants.MIX);
		_map.put(Constants.EN_REPEAT, FoXsltConstants.REPEAT);
		_map.put(Constants.EN_UNEDITABLE, FoXsltConstants.REPEAT);
		_map.put(Constants.EN_REPEAT, FoXsltConstants.REPEAT);
		_map.put(Constants.EN_UNEDITABLE, FoXsltConstants.EN_UNEDITABLE);
		_map.put(Constants.EN_EDITABLE, FoXsltConstants.EN_EDITABLE);
		_map.put(Constants.EN_DATANODE, FoXsltConstants.EN_DATANODE);
		_map.put(Constants.EN_CHINESELOWERCASE, "chineslow");
		_map.put(Constants.EN_CHINESECAPITAL, "chinescap");
		_map.put(Constants.EN_CODE128, FoXsltConstants.CODE128);
		_map.put(Constants.EN_CODE39, "code39");
		_map.put(Constants.EN_EAN128, "ean128");
		_map.put(Constants.EN_2OF5, "20f5");
		_map.put(Constants.EN_A, "subset_a");
		_map.put(Constants.EN_B, "subset_b");
		_map.put(Constants.EN_C, "subset_c");
		_map.put(Constants.EN_EAN13, "ean13");
		_map.put(Constants.EN_EAN8, "ean8");
		_map.put(Constants.EN_UPCA, "upca");
		_map.put(Constants.EN_UPCE, "upce");
		_map.put(Constants.EN_EDITVARIBLE, "editvarible");
		_map.put(Constants.EN_NOADDANDDELETE,"noaddanddelete");
		_map.put(Constants.EN_CANADD, "canadd");
		_map.put(Constants.EN_CANDELETE, "candelete");
		_map.put(Constants.EN_CANADDANDDELETE, "canaddanddelete");
		_map.put(Constants.EN_ONLY, "only");
		_map.put(Constants.EN_PDF417, "pdf147");
		_map.put(Constants.EN_BARCHART, "barchart");
		_map.put(Constants.EN_PIECHART, "piechart");
		_map.put(Constants.EN_LINECHART, "linechart");
		_map.put(Constants.EN_STACKBARCHART, "stackbarchart");
		_map.put(Constants.EN_AREACHART, "areachart");
		_map.put(Constants.EN_VERTICAL, "vertical");
		_map.put(Constants.EN_HORIZONTAL, "horizontal");
		_map.put(Constants.EN_CLOCKWISE, "clockwise");
		_map.put(Constants.EN_INVERT, "invert");
		//编辑相关的枚举值
		_map.put(Constants.EN_INPUT, "input");
		_map.put(Constants.EN_SELECT, "select");
		_map.put(Constants.EN_POPUPBROWSER, "popupbrowser");
		_map.put(Constants.EN_DATE, "date");
		_map.put(Constants.EN_CHECKBOX, "checkbox");
		_map.put(Constants.EN_GRAPHIC, "graphic");
		_map.put(Constants.EN_TEXT, "text");
		_map.put(Constants.EN_PASSWORD, "password");
		_map.put(Constants.EN_SORT_P, "sortp");
		_map.put(Constants.EN_SORT_N, "sortn");
		_map.put(Constants.EN_SORT_C, "sortc");
		_map.put(Constants.EN_DATE_TYPE_C, "datetypec");
		_map.put(Constants.EN_DATE_TYPE_T, "datetypet");
		_map.put(Constants.EN_FULL, "full");
		_map.put(Constants.EN_COMPACT, "compact");
		_map.put(Constants.EN_MINIMAL, "minimal");
		_map.put(Constants.EN_CHECKBOX_BOXSTYLE_CIRCLE,"boxstyle_circle");
		_map.put(Constants.EN_CHECKBOX_BOXSTYLE_SQUARE,"boxstyle_square");
		_map.put(Constants.EN_CHECKBOX_TICKMARK_TICK,"tickmark_tick");
		_map.put(Constants.EN_CHECKBOX_TICKMARK_DOT,"tickmark_dot");
		_map.put(Constants.EN_WORDARTTEXT_TYPE_LINE,"line");
		_map.put(Constants.EN_WORDARTTEXT_TYPE_ZHEXIAN2,"zhexian2");
		_map.put(Constants.EN_WORDARTTEXT_TYPE_ZHEXIAN3,"zhexian3");
		_map.put(Constants.EN_WORDARTTEXT_TYPE_BEZIERCURVES2,"beziercurves2");
		_map.put(Constants.EN_WORDARTTEXT_TYPE_BEZIERCURVES3,"beziercurves3");
		_map.put(Constants.EN_WORDARTTEXT_TYPE_ELLIPSEINNER,"ellipseinner");
		_map.put(Constants.EN_WORDARTTEXT_TYPE_ELLIPSEUP,"ellipseup");
		_map.put(Constants.EN_WORDARTTEXT_TYPE_ELLIPSEDOWN,"ellipsedown");
		_map.put(Constants.EN_WORDARTTEXT_TYPE_ELLIPSE,"ellipse");
		_map.put(Constants.EN_CHINESECAPITAL_ZC,"chinesecapital_zc");
		_map.put(Constants.EN_CHINESECAPITAL_ZC_ADDZHENG,"chinesecapital_zc_addzheng");
		_map.put(Constants.EN_CHINESELOWERCASE_ADDZHENG,"chineslow_addzheng");
		_map.put(Constants.EN_CHINESECAPITAL_ADDZHENG,"chinesecapital_addzheng");
		_map.put(Constants.EN_CHECK,"check");
		_map.put(Constants.EN_UNCHECK,"uncheck");
		_map.put(Constants.EN_POSITION_NUMBER_1, FoXsltConstants.POSITION_NUMBER_1);
		_map.put(Constants.EN_POSITION_NUMBER_a, FoXsltConstants.POSITION_NUMBER_a);
		_map.put(Constants.EN_POSITION_NUMBER_A, FoXsltConstants.POSITION_NUMBER_A);
		_map.put(Constants.EN_POSITION_NUMBER_i, FoXsltConstants.POSITION_NUMBER_i);
		_map.put(Constants.EN_POSITION_NUMBER_I, FoXsltConstants.POSITION_NUMBER_I);


	}

	/**
	 * 
	 * 根据枚举值获得枚举字符串
	 * 
	 * @param enumkey
	 *            ：枚举值
	 * @return 枚举字符串
	 * @exception
	 */
	protected String getEnumString(int enumkey) {
		return _map.get(enumkey);
	}

}
