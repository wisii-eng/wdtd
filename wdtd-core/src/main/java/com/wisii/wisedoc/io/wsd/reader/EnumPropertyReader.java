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
 * @EnumPropertyReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.HashMap;
import java.util.Map;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-9
 */
public class EnumPropertyReader extends SingleAttributeReader {

	// 枚举值和枚举字符串的对应关系map
	private final Map<String, Integer> _map = new HashMap<String, Integer>();

	public EnumPropertyReader() {
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
		_map.put(FoXsltConstants.BIDI_OVERRIDE,Constants.EN_BIDI_OVERRIDE);
		_map.put(FoXsltConstants.BOUNDED_IN_ONE_DIMENSION,Constants.EN_BOUNDED_IN_ONE_DIMENSION);
		_map.put(FoXsltConstants.COLLAPSE,Constants.EN_COLLAPSE);
		_map.put(FoXsltConstants.COLLAPSE_WITH_PRECEDENCE,Constants.EN_COLLAPSE_WITH_PRECEDENCE);
		_map.put(FoXsltConstants.FIC,Constants.EN_FIC);
		_map.put(FoXsltConstants.FORCE,Constants.EN_FORCE);
		_map.put(FoXsltConstants.FSWP,Constants.EN_FSWP);
		_map.put(FoXsltConstants.INDEFINITE,Constants.EN_INDEFINITE);
		_map.put(FoXsltConstants.INHERIT,Constants.EN_INHERIT);
		_map.put(FoXsltConstants.LARGER,Constants.EN_LARGER);
		_map.put(FoXsltConstants.LEWP,Constants.EN_LEWP);
		_map.put(FoXsltConstants.LSWP,Constants.EN_LSWP);
		_map.put(FoXsltConstants.NO_BLINK,Constants.EN_NO_BLINK);
		_map.put(FoXsltConstants.NO_LINE_THROUGH,Constants.EN_NO_LINE_THROUGH);
		_map.put(FoXsltConstants.NO_OVERLINE,Constants.EN_NO_OVERLINE);
		_map.put(FoXsltConstants.NO_UNDERLINE,Constants.EN_NO_UNDERLINE);
		_map.put(FoXsltConstants.NOREPEAT,Constants.EN_NOREPEAT);
		_map.put(FoXsltConstants.PAGINATE,Constants.EN_PAGINATE);
		_map.put(FoXsltConstants.SCALE_TO_FIT,Constants.EN_SCALE_TO_FIT);
		_map.put(FoXsltConstants.SEPARATE,Constants.EN_SEPARATE);
		_map.put(FoXsltConstants.SMALLER,Constants.EN_SMALLER);
		_map.put(FoXsltConstants.SUPPRESS,Constants.EN_SUPPRESS);
		_map.put(FoXsltConstants.UNBOUNDED,Constants.EN_UNBOUNDED);
		_map.put(FoXsltConstants.X_FILL,Constants.EN_X_FILL);
		_map.put(FoXsltConstants.X_DISTRIBUTE,Constants.EN_X_DISTRIBUTE);
		_map.put(FoXsltConstants.PRE,Constants.EN_PRE);
		_map.put(FoXsltConstants.CAPTION,Constants.EN_CAPTION);
		_map.put(FoXsltConstants.ICON,Constants.EN_ICON);
		_map.put(FoXsltConstants.MENU,Constants.EN_MENU);
		_map.put(FoXsltConstants.MESSAGE_BOX,Constants.EN_MESSAGE_BOX);
		_map.put(FoXsltConstants.SMALL_CAPTION,Constants.EN_SMALL_CAPTION);
		_map.put(FoXsltConstants.NORMAL, Constants.EN_NORMAL);
		_map.put(FoXsltConstants.DISCARD, Constants.EN_DISCARD);
		_map.put(FoXsltConstants.RETAIN, Constants.EN_RETAIN);
		_map.put(FoXsltConstants.CAPITALIZE, Constants.EN_CAPITALIZE);
		_map.put(FoXsltConstants.UPPERCASE, Constants.EN_UPPERCASE);
		_map.put(FoXsltConstants.LOWERCASE, Constants.EN_LOWERCASE);
		_map.put(FoXsltConstants.AUTO, Constants.EN_AUTO);
		_map.put(FoXsltConstants.AUTO_ODD,Constants.EN_AUTO_ODD);
		_map.put(FoXsltConstants.AUTO_EVEN,Constants.EN_AUTO_EVEN);
		_map.put(FoXsltConstants.COLUMN, Constants.EN_COLUMN);
		_map.put(FoXsltConstants.EVEN, Constants.EN_EVEN);
		_map.put(FoXsltConstants.END_ON_EVEN, Constants.EN_END_ON_EVEN);
		_map.put(FoXsltConstants.END_ON_ODD, Constants.EN_END_ON_ODD);
		_map.put(FoXsltConstants.NO_FORCE, Constants.EN_NO_FORCE);
		_map.put(FoXsltConstants.NO_LIMIT, Constants.EN_NO_LIMIT);
		_map.put(FoXsltConstants.BOLDER, Constants.EN_BOLDER);
		_map.put(FoXsltConstants.LIGHTER, Constants.EN_LIGHTER);
		_map.put(FoXsltConstants.PR100, Constants.EN_100);
		_map.put(FoXsltConstants.PR200, Constants.EN_200);
		_map.put(FoXsltConstants.PR300, Constants.EN_300);
		_map.put(FoXsltConstants.PR400, Constants.EN_400);
		_map.put(FoXsltConstants.PR500, Constants.EN_500);
		_map.put(FoXsltConstants.PR600, Constants.EN_600);
		_map.put(FoXsltConstants.PR700, Constants.EN_700);
		_map.put(FoXsltConstants.PR800, Constants.EN_800);
		_map.put(FoXsltConstants.PR900, Constants.EN_900);
		_map.put(FoXsltConstants.ABSOLUTE, Constants.EN_ABSOLUTE);
		_map.put(FoXsltConstants.FIXED, Constants.EN_FIXED);
		_map.put(FoXsltConstants.BASELINE, Constants.EN_BASELINE);
		_map.put(FoXsltConstants.BEFORE_EDGE, Constants.EN_BEFORE_EDGE);
		_map.put(FoXsltConstants.TEXT_BEFORE_EDGE,
				Constants.EN_TEXT_BEFORE_EDGE);
		_map.put(FoXsltConstants.MIDDLE, Constants.EN_MIDDLE);
		_map.put(FoXsltConstants.CENTRAL, Constants.EN_CENTRAL);
		_map.put(FoXsltConstants.AFTER_EDGE, Constants.EN_AFTER_EDGE);
		_map.put(FoXsltConstants.TEXT_AFTER_EDGE, Constants.EN_TEXT_AFTER_EDGE);
		_map.put(FoXsltConstants.IDEOGRAPHIC, Constants.EN_IDEOGRAPHIC);
		_map.put(FoXsltConstants.ALPHABETIC, Constants.EN_ALPHABETIC);
		_map.put(FoXsltConstants.HANGING, Constants.EN_HANGING);
		_map.put(FoXsltConstants.MATHEMATICAL, Constants.EN_MATHEMATICAL);
		_map.put(FoXsltConstants.SCROLL, Constants.EN_SCROLL);
		_map.put(FoXsltConstants.REPEAT, Constants.EN_REPEAT);
		_map.put(FoXsltConstants.REPEATX, Constants.EN_REPEATX);
		_map.put(FoXsltConstants.REPEATY, Constants.EN_REPEATY);
		_map.put(FoXsltConstants.BLANK, Constants.EN_BLANK);
		_map.put(FoXsltConstants.NOT_BLANK, Constants.EN_NOT_BLANK);
		_map.put(FoXsltConstants.ANY, Constants.EN_ANY);
		_map.put(FoXsltConstants.TRUE, Constants.EN_TRUE);
		_map.put(FoXsltConstants.FALSE, Constants.EN_FALSE);
		_map.put(FoXsltConstants.SOLID, Constants.EN_SOLID);
		_map.put(FoXsltConstants.DOTTED, Constants.EN_DOTTED);
		_map.put(FoXsltConstants.DASHED, Constants.EN_DASHED);
		_map.put(FoXsltConstants.GROOVE, Constants.EN_GROOVE);
		_map.put(FoXsltConstants.DOUBLE, Constants.EN_DOUBLE);
		_map.put(FoXsltConstants.RIDGE, Constants.EN_RIDGE);
		_map.put(FoXsltConstants.INSET, Constants.EN_INSET);
		_map.put(FoXsltConstants.OUTSET, Constants.EN_OUTSET);
		_map.put(FoXsltConstants.PAGE, Constants.EN_PAGE);
		_map.put(FoXsltConstants.ODD_PAGE, Constants.EN_ODD_PAGE);
		_map.put(FoXsltConstants.EVEN_PAGE, Constants.EN_EVEN_PAGE);
		_map.put(FoXsltConstants.BEFORE, Constants.EN_BEFORE);
		_map.put(FoXsltConstants.AFTER, Constants.EN_AFTER);
		_map.put(FoXsltConstants.START, Constants.EN_START);
		_map.put(FoXsltConstants.END, Constants.EN_END);
		_map.put(FoXsltConstants.TOP, Constants.EN_TOP);
		_map.put(FoXsltConstants.BOTTOM, Constants.EN_BOTTOM);
		_map.put(FoXsltConstants.LEFT, Constants.EN_LEFT);
		_map.put(FoXsltConstants.RIGHT, Constants.EN_RIGHT);
		_map.put(FoXsltConstants.INSIDE, Constants.EN_INSIDE);
		_map.put(FoXsltConstants.OUTSIDE, Constants.EN_OUTSIDE);
		_map.put(FoXsltConstants.BOTH, Constants.EN_BOTH);
		_map.put(FoXsltConstants.NONE, Constants.EN_NONE);
		_map.put(FoXsltConstants.LTR, Constants.EN_LTR);
		_map.put(FoXsltConstants.RTL, Constants.EN_RTL);
		_map.put(FoXsltConstants.CENTER, Constants.EN_CENTER);
		_map.put(FoXsltConstants.USE_SCRIPT, Constants.EN_USE_SCRIPT);
		_map.put(FoXsltConstants.NO_CHANGE, Constants.EN_NO_CHANGE);
		_map.put(FoXsltConstants.RESET_SIZE, Constants.EN_RESET_SIZE);
		_map.put(FoXsltConstants.SHOW, Constants.EN_SHOW);
		_map.put(FoXsltConstants.HIDE, Constants.EN_HIDE);
		_map.put(FoXsltConstants.LR_TB, Constants.EN_LR_TB);
		_map.put(FoXsltConstants.TB_RL, Constants.EN_TB_RL);
		_map.put(FoXsltConstants.RL_TB, Constants.EN_RL_TB);
		_map.put(FoXsltConstants.IGNORE, Constants.EN_IGNORE);
		_map.put(FoXsltConstants.PRESERVE, Constants.EN_PRESERVE);
		_map.put(FoXsltConstants.IGNORE_IF_BEFORE_LINEFEED,
				Constants.EN_IGNORE_IF_BEFORE_LINEFEED);
		_map.put(FoXsltConstants.IGNORE_IF_AFTER_LINEFEED,
				Constants.EN_IGNORE_IF_AFTER_LINEFEED);
		_map.put(FoXsltConstants.IGNORE_IF_SURROUNDING_LINEFEED,
				Constants.EN_IGNORE_IF_SURROUNDING_LINEFEED);
		_map.put(FoXsltConstants.CHARACTER_BY_CHARACTER,
				Constants.EN_CHARACTER_BY_CHARACTER);
		_map.put(FoXsltConstants.WIDER, Constants.EN_WIDER);
		_map.put(FoXsltConstants.NARROWER, Constants.EN_NARROWER);
		_map.put(FoXsltConstants.ULTRA_CONDENSED, Constants.EN_ULTRA_CONDENSED);
		_map.put(FoXsltConstants.EXTRA_CONDENSED, Constants.EN_EXTRA_CONDENSED);
		_map.put(FoXsltConstants.CONDENSED, Constants.EN_CONDENSED);
		_map.put(FoXsltConstants.SEMI_CONDENSED, Constants.EN_SEMI_CONDENSED);
		_map.put(FoXsltConstants.SEMI_EXPANDED, Constants.EN_SEMI_EXPANDED);
		_map.put(FoXsltConstants.EXPANDED, Constants.EN_EXPANDED);
		_map.put(FoXsltConstants.EXTRA_EXPANDED, Constants.EN_EXTRA_EXPANDED);
		_map.put(FoXsltConstants.ULTRA_EXPANDED, Constants.EN_ULTRA_EXPANDED);
		_map.put(FoXsltConstants.ITALIC, Constants.EN_ITALIC);
		_map.put(FoXsltConstants.OBLIQUE, Constants.EN_OBLIQUE);
		_map.put(FoXsltConstants.BACKSLANT, Constants.EN_BACKSLANT);
		_map.put(FoXsltConstants.SMALL_CAPS, Constants.EN_SMALL_CAPS);
		_map.put(FoXsltConstants.REFERENCE_AREA, Constants.EN_REFERENCE_AREA);
		_map.put(FoXsltConstants.SPACE, Constants.EN_SPACE);
		_map.put(FoXsltConstants.RULE, Constants.EN_RULE);
		_map.put(FoXsltConstants.DOTS, Constants.EN_DOTS);
		_map.put(FoXsltConstants.USE_CONTENT, Constants.EN_USE_CONTENT);
		_map.put(FoXsltConstants.TRADITIONAL, Constants.EN_TRADITIONAL);
		_map.put(FoXsltConstants.TREAT_AS_SPACE, Constants.EN_TREAT_AS_SPACE);
		_map.put(FoXsltConstants.TREAT_AS_ZERO_WIDTH_SPACE,
				Constants.EN_TREAT_AS_ZERO_WIDTH_SPACE);
		_map.put(FoXsltConstants.CONSIDER_SHIFTS, Constants.EN_CONSIDER_SHIFTS);
		_map.put(FoXsltConstants.DISREGARD_SHIFTS,
				Constants.EN_DISREGARD_SHIFTS);
		_map.put(FoXsltConstants.LINE_HEIGHT, Constants.EN_LINE_HEIGHT);
		_map.put(FoXsltConstants.FONT_HEIGHT, Constants.EN_FONT_HEIGHT);
		_map.put(FoXsltConstants.MAX_HEIGHT, Constants.EN_MAX_HEIGHT);
		_map.put(FoXsltConstants.ODD, Constants.EN_ODD);
		_map.put(FoXsltConstants.VISIBLE, Constants.EN_VISIBLE);
		_map.put(FoXsltConstants.HIDDEN, Constants.EN_HIDDEN);
		_map.put(FoXsltConstants.ERROR_IF_OVERFLOW,
				Constants.EN_ERROR_IF_OVERFLOW);
		_map.put(FoXsltConstants.AVOID, Constants.EN_AVOID);
		_map.put(FoXsltConstants.ALWAYSA, Constants.EN_ALWAYS);
		_map.put(FoXsltConstants.STATIC, Constants.EN_STATIC);
		_map.put(FoXsltConstants.RELATIVE, Constants.EN_RELATIVE);
		_map.put(FoXsltConstants.FIRST, Constants.EN_FIRST);
		_map.put(FoXsltConstants.REST, Constants.EN_REST);
		_map.put(FoXsltConstants.LAST, Constants.EN_LAST);
		_map.put(FoXsltConstants.PERCEPTUAL, Constants.EN_PERCEPTUAL);
		_map.put(FoXsltConstants.RELATIVE_COLOMETRIC,
				Constants.EN_RELATIVE_COLOMETRIC);
		_map.put(FoXsltConstants.SATURATION, Constants.EN_SATURATION);
		_map.put(FoXsltConstants.ABSOLUTE_COLORMETRIC,
				Constants.EN_ABSOLUTE_COLORMETRIC);
		_map.put(FoXsltConstants.PAGE_SEQUENCE, Constants.EN_PAGE_SEQUENCE);
		_map.put(FoXsltConstants.DOCUMENT, Constants.EN_DOCUMENT);
		_map.put(FoXsltConstants.INTEGER_PIXELS, Constants.EN_INTEGER_PIXELS);
		_map.put(FoXsltConstants.RESAMPLE_ANY_METHOD,
				Constants.EN_RESAMPLE_ANY_METHOD);
		_map.put(FoXsltConstants.UNIFORM, Constants.EN_UNIFORM);
		_map.put(FoXsltConstants.NON_UNIFORM, Constants.EN_NON_UNIFORM);
		_map.put(FoXsltConstants.ALL, Constants.EN_ALL);
		_map.put(FoXsltConstants.JUSTIFY, Constants.EN_JUSTIFY);
		_map.put(FoXsltConstants.UNDERLINE, Constants.EN_UNDERLINE);
		_map.put(FoXsltConstants.OVERLINE, Constants.EN_OVERLINE);
		_map.put(FoXsltConstants.LINE_THROUGH, Constants.EN_LINE_THROUGH);
		_map.put(FoXsltConstants.BLINK, Constants.EN_BLINK);
		_map.put(FoXsltConstants.SUB, Constants.EN_SUB);
		_map.put(FoXsltConstants.SUPER, Constants.EN_SUPER);
		_map.put(FoXsltConstants.TEXT_TOP, Constants.EN_TEXT_TOP);
		_map.put(FoXsltConstants.TEXT_BOTTOM, Constants.EN_TEXT_BOTTOM);
		_map.put(FoXsltConstants.NO_WRAP, Constants.EN_NO_WRAP);
		_map.put(FoXsltConstants.WRAP, Constants.EN_WRAP);
		_map.put(FoXsltConstants.LINE, Constants.EN_LINE);
		_map.put(FoXsltConstants.INDENT, Constants.EN_INDENT);
		_map.put(FoXsltConstants.BLOCK, Constants.EN_BLOCK);
		_map.put(FoXsltConstants.IGNORE, Constants.EN_IGNORE);
		_map.put(FoXsltConstants.PRESERVE, Constants.EN_PRESERVE);
		_map.put(FoXsltConstants.TREAT_AS_SPACE, Constants.EN_TREAT_AS_SPACE);
		_map.put(FoXsltConstants.TREAT_AS_ZERO_WIDTH_SPACE,
				Constants.EN_TREAT_AS_ZERO_WIDTH_SPACE);
		// map.put(FoXsltConstants.MIX,Constants.EN_MIX);
		_map.put(FoXsltConstants.REPEAT, Constants.EN_REPEAT);
		_map.put(FoXsltConstants.EN_UNEDITABLE, Constants.EN_UNEDITABLE);
		_map.put(FoXsltConstants.EN_EDITABLE, Constants.EN_EDITABLE);
		_map.put(FoXsltConstants.EN_DATANODE, Constants.EN_DATANODE);
		_map.put("chineslow",Constants.EN_CHINESELOWERCASE);
		_map.put("chinescap",Constants.EN_CHINESECAPITAL);
		_map.put( FoXsltConstants.CODE128,Constants.EN_CODE128);
		_map.put("code39",Constants.EN_CODE39);
		_map.put("ean128",Constants.EN_EAN128);
		_map.put("20f5",Constants.EN_2OF5);
		_map.put( "subset_a",Constants.EN_A);
		_map.put("subset_b",Constants.EN_B);
		_map.put("subset_c",Constants.EN_C);
		_map.put("ean8",Constants.EN_EAN8);
		_map.put("ean13",Constants.EN_EAN13);
		_map.put("upca",Constants.EN_UPCA);
		_map.put("upce",Constants.EN_UPCE);
		_map.put("canedit",Constants.EN_EDITABLE);
		_map.put("notedit",Constants.EN_UNEDITABLE);
		_map.put("editvarible",Constants.EN_EDITVARIBLE);
		_map.put("noaddanddelete",Constants.EN_NOADDANDDELETE);
		_map.put("canadd",Constants.EN_CANADD);
		_map.put("candelete",Constants.EN_CANDELETE);
		_map.put("canaddanddelete",Constants.EN_CANADDANDDELETE);
		_map.put("only",Constants.EN_ONLY);
		_map.put("pdf147",Constants.EN_PDF417);
		_map.put("barchart",Constants.EN_BARCHART);
		_map.put("piechart",Constants.EN_PIECHART);
		_map.put("linechart",Constants.EN_LINECHART);
		_map.put("stackbarchart",Constants.EN_STACKBARCHART);
		_map.put("areachart",Constants.EN_AREACHART);
		_map.put("vertical",Constants.EN_VERTICAL);
		_map.put("horizontal",Constants.EN_HORIZONTAL);
		_map.put("clockwise",Constants.EN_CLOCKWISE);
		_map.put("invert",Constants.EN_INVERT);
		//编辑相关的枚举值
		_map.put("input",Constants.EN_INPUT);
		_map.put("select",Constants.EN_SELECT);
		_map.put("popupbrowser",Constants.EN_POPUPBROWSER);
		_map.put("date",Constants.EN_DATE);
		_map.put("checkbox",Constants.EN_CHECKBOX);
		_map.put("graphic",Constants.EN_GRAPHIC);
		_map.put("text",Constants.EN_TEXT);
		_map.put("password",Constants.EN_PASSWORD);
		_map.put("sortp",Constants.EN_SORT_P);
		_map.put("sortn",Constants.EN_SORT_N);
		_map.put("sortc",Constants.EN_SORT_C);
		_map.put("datetypec",Constants.EN_DATE_TYPE_C);
		_map.put("datetypet",Constants.EN_DATE_TYPE_T);
		_map.put("full",Constants.EN_FULL);
		_map.put("compact",Constants.EN_COMPACT);
		_map.put("minimal",Constants.EN_MINIMAL);
		_map.put("boxstyle_circle",Constants.EN_CHECKBOX_BOXSTYLE_CIRCLE);
		_map.put("boxstyle_square",Constants.EN_CHECKBOX_BOXSTYLE_SQUARE);
		_map.put("tickmark_tick",Constants.EN_CHECKBOX_TICKMARK_TICK);
		_map.put("tickmark_dot",Constants.EN_CHECKBOX_TICKMARK_DOT);
		_map.put("line",Constants.EN_WORDARTTEXT_TYPE_LINE);
		_map.put("zhexian2",Constants.EN_WORDARTTEXT_TYPE_ZHEXIAN2);
		_map.put("zhexian3",Constants.EN_WORDARTTEXT_TYPE_ZHEXIAN3);
		_map.put("beziercurves2",Constants.EN_WORDARTTEXT_TYPE_BEZIERCURVES2);
		_map.put("beziercurves3",Constants.EN_WORDARTTEXT_TYPE_BEZIERCURVES3);
		_map.put("ellipseinner",Constants.EN_WORDARTTEXT_TYPE_ELLIPSEINNER);
		_map.put("ellipseup",Constants.EN_WORDARTTEXT_TYPE_ELLIPSEUP);
		_map.put("ellipsedown",Constants.EN_WORDARTTEXT_TYPE_ELLIPSEDOWN);
		_map.put("ellipse",Constants.EN_WORDARTTEXT_TYPE_ELLIPSE);
		_map.put("chinesecapital_zc",Constants.EN_CHINESECAPITAL_ZC);
		_map.put("chinesecapital_zc_addzheng",Constants.EN_CHINESECAPITAL_ZC_ADDZHENG);
		_map.put("chineslow_addzheng",Constants.EN_CHINESELOWERCASE_ADDZHENG);
		_map.put("chinesecapital_addzheng",Constants.EN_CHINESECAPITAL_ADDZHENG);
		_map.put("check",Constants.EN_CHECK);
		_map.put("uncheck",Constants.EN_UNCHECK);
		
		_map.put(FoXsltConstants.POSITION_NUMBER_1,Constants.EN_POSITION_NUMBER_1);
		_map.put(FoXsltConstants.POSITION_NUMBER_a,Constants.EN_POSITION_NUMBER_a);
		_map.put(FoXsltConstants.POSITION_NUMBER_A,Constants.EN_POSITION_NUMBER_A);
		_map.put(FoXsltConstants.POSITION_NUMBER_i,Constants.EN_POSITION_NUMBER_i);
		_map.put(FoXsltConstants.POSITION_NUMBER_I,Constants.EN_POSITION_NUMBER_I);
		_map.put("autofontsize",Constants.EN_AUTOFONTSIZE);
		// map.put(FoXsltConstants.XSL_PRECEDING,Constants.EN_XSL_PRECEDING);
		// map.put(FoXsltConstants.XSL_FOLLOWING,Constants.EN_XSL_FOLLOWING);
		// map.put(FoXsltConstants.XSL_ANY,Constants.EN_XSL_ANY);
		// map.put(FoXsltConstants.USE_TARGET_PROCESSING_CONTEXT,
		// FoXsltConstants.USE_TARGET_PROCESSING_CONTEXT);
		// map.put(FoXsltConstants.USE_NORMAL_STYLESHEET,
		// FoXsltConstants.USE_NORMAL_STYLESHEET);
		// map.put(FoXsltConstants.BOLD,Constants.EN_BOLD);
		// map.put(FoXsltConstants.SPELL_OUT,Constants.EN_SPELL_OUT);
		// map.put(FoXsltConstants.CODE,Constants.EN_CODE);
		// map.put(FoXsltConstants.DIGITS,Constants.EN_DIGITS);
		// map.put(FoXsltConstants.CONTINUOUS,Constants.EN_CONTINUOUS);
		// map.put(FoXsltConstants.ONCE,Constants.EN_ONCE);
		// map.put(FoXsltConstants.NO,Constants.EN_NO);
		// map.put(FoXsltConstants.REPLACE,Constants.EN_REPLACE);
		// map.put(FoXsltConstants.NEW,Constants.EN_NEW);
		// map.put(FoXsltConstants.FIRST_STARTING,Constants.EN_FIRST_STARTING);
		// map.put(FoXsltConstants.FIRST_INCLUDING_CARRYOVER,
		// FoXsltConstants.FIRST_INCLUDING_CARRYOVER);
		// map.put(FoXsltConstants.LAST_STARTING,Constants.EN_LAST_STARTING);
		// map.put(FoXsltConstants.LAST_ENDING,Constants.EN_LAST_ENDING);
		// map.put(FoXsltConstants.FIRST_STARTING_WITHIN_PAGE,
		// FoXsltConstants.FIRST_STARTING_WITHIN_PAGE);
		// map.put(FoXsltConstants.FIRST_INCLUDING_CARRYOVER,
		// FoXsltConstants.FIRST_INCLUDING_CARRYOVER);
		// map.put(FoXsltConstants.LAST_STARTING_WITHIN_PAGE,
		// FoXsltConstants.LAST_STARTING_WITHIN_PAGE);
		// map.put(FoXsltConstants.LAST_ENDING_WITHIN_PAGE,
		// FoXsltConstants.LAST_ENDING_WITHIN_PAGE);
		// map.put(FoXsltConstants.TABLE,Constants.EN_TABLEPR);
		// map.put(FoXsltConstants.TABLE_FRAGMENT,Constants.EN_TABLE_FRAGMENT);
		// map.put(FoXsltConstants.ONLY,Constants.EN_ONLY);
		// map.put(FoXsltConstants.NO_REPEAT,Constants.EN_NO_REPEAT);
		// map.put(FoXsltConstants.LINK,Constants.EN_LINK);
		// map.put(FoXsltConstants.VISITED,Constants.EN_VISITED);
		// map.put(FoXsltConstants.ACTIVE,Constants.EN_ACTIVE);
		// map.put(FoXsltConstants.HOVER,Constants.EN_HOVER);
		// map.put(FoXsltConstants.FOCUS,Constants.EN_FOCUS);
		// map.put(FoXsltConstants.TB_LR,Constants.EN_TB_LR);
		// map.put(FoXsltConstants.BT_LR,Constants.EN_BT_LR);
		// map.put(FoXsltConstants.BT_RL,Constants.EN_BT_RL);
		// map.put(FoXsltConstants.LR_BT,Constants.EN_LR_BT);
		// map.put(FoXsltConstants.RL_BT,Constants.EN_RL_BT);
		// map.put(FoXsltConstants.LR_ALTERNATING_RL_BT,
		// FoXsltConstants.LR_ALTERNATING_RL_BT);
		// map.put(FoXsltConstants.LR_ALTERNATING_RL_TB,
		// FoXsltConstants.LR_ALTERNATING_RL_TB);
		// map.put(FoXsltConstants.LR_INVERTING_RL_BT,
		// FoXsltConstants.LR_INVERTING_RL_BT);
		// map.put(FoXsltConstants.LR_INVERTING_RL_TB,
		// FoXsltConstants.LR_INVERTING_RL_TB);
		// map.put(FoXsltConstants.TB_LR_IN_LR_PAIRS,
		// FoXsltConstants.TB_LR_IN_LR_PAIRS);
		// map.put(FoXsltConstants.LR,Constants.EN_LR);
		// map.put(FoXsltConstants.RL,Constants.EN_RL);
		// map.put(FoXsltConstants.TB,Constants.EN_TB);

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
	int getEnumKey(String enums) {
//		Sysout
		Integer key = _map.get(enums);
		if (key == null) {
			key = -1;
		}
		return key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.SingleAttributeReader#read(int,
	 * java.lang.String)
	 */
	public Object read(int key, String value)
	{
		//原来版本的条码字符集A和序号中的序号类型小写字母均存为了“a”，因此需要特殊处理
		if (key == Constants.PR_BARCODE_SUBSET)
		{
			if ("a".equals(value))
			{
				return new EnumProperty(Constants.EN_A, value);
			} else if ("b".equals(value))
			{
				return new EnumProperty(Constants.EN_B, value);
			} else if ("c".equals(value))
			{
				return new EnumProperty(Constants.EN_C, value);
			} else
			{
				int keyvalue = getEnumKey(value);
				if (keyvalue > -1)
				{
					return new EnumProperty(keyvalue, value);
				}
			}
		} else
		{
			int keyvalue = getEnumKey(value);
			if (keyvalue > -1)
			{
				return new EnumProperty(keyvalue, value);
			}
		}
		LogUtil.debug("找不到枚举变量\"" + value + "\"对应的枚举常量");
		return null;
	}

}
