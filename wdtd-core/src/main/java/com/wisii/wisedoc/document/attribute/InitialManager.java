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
 * @InitialManager.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.datatype.LengthBase;

/**
 * 类功能描述：默认值管理类 所有对象的属性的默认值都通过该类获得
 * 
 * 作者：zhangqiang 创建日期：2008-8-25
 */
public final class InitialManager
{

	public static FixedLength MINLEN = new FixedLength(0.1d, "cm");

	private final static Map<Integer, Object> chartMap = new ConcurrentHashMap<Integer, Object>();

	private final static Map<Integer, Object> initmap = new ConcurrentHashMap<Integer, Object>();
	static
	{
		final EnumProperty auto = new EnumProperty(Constants.EN_AUTO, "AUTO");
		final EnumProperty hidden = new EnumProperty(Constants.EN_HIDDEN, "");
		initmap.put(Constants.PR_SOURCE_DOCUMENT, Constants.EN_NONE);
		initmap.put(Constants.PR_ROLE, Constants.EN_NONE);
		initmap.put(Constants.PR_TEXT_TRANSFORM, Constants.EN_NONE);
		initmap.put(Constants.PR_ABSOLUTE_POSITION, auto);
		initmap.put(Constants.PR_TOP, new EnumLength(Constants.EN_AUTO, null));
		initmap
				.put(Constants.PR_RIGHT,
						new EnumLength(Constants.EN_AUTO, null));
		initmap.put(Constants.PR_BOTTOM,
				new EnumLength(Constants.EN_AUTO, null));
		initmap.put(Constants.PR_LEFT, new EnumLength(Constants.EN_AUTO, null));
		initmap.put(Constants.PR_BACKGROUND_ATTACHMENT, new EnumProperty(
				Constants.EN_SCROLL, ""));
		initmap.put(Constants.PR_COLOR, new WiseDocColor(Color.black, 0));
		// 初始化边框样式（四个边）
		initmap.put(Constants.PR_BORDER_BEFORE_STYLE, new EnumProperty(
				Constants.EN_NONE, "NONE"));
		initmap.put(Constants.PR_BORDER_AFTER_STYLE, new EnumProperty(
				Constants.EN_NONE, "NONE"));
		initmap.put(Constants.PR_BORDER_START_STYLE, new EnumProperty(
				Constants.EN_NONE, "NONE"));
		initmap.put(Constants.PR_BORDER_END_STYLE, new EnumProperty(
				Constants.EN_NONE, "NONE"));

		initmap.put(Constants.PR_TEXT_DECORATION, 0);
		final EnumProperty enump = new EnumProperty(Constants.EN_FALSE, "");
		initmap.put(Constants.PR_HYPHENATE, enump);
		initmap.put(Constants.PR_HYPHENATION_CHARACTER, (char) 0x2010);
		initmap.put(Constants.PR_HYPHENATION_PUSH_CHARACTER_COUNT,
				new EnumNumber(-1, 2));
		initmap.put(Constants.PR_HYPHENATION_REMAIN_CHARACTER_COUNT,
				new EnumNumber(-1, 2));
		// initmap.put(Constants.PR_BACKGROUND_COLOR, Constants.EN_Tran);
		// initmap.put(Constants.PR_BACKGROUND_IMAGE,
		// Constants.DEFAULT_BACKGROUND_IMAGE);
		initmap.put(Constants.PR_BACKGROUND_REPEAT, new EnumProperty(
				Constants.EN_NOREPEAT, ""));
		final PercentLength poshlen = new PercentLength(0.5d, new LengthBase(
				LengthBase.IMAGE_BACKGROUND_POSITION_HORIZONTAL));
		final PercentLength posvlen = new PercentLength(0.5d, new LengthBase(
				LengthBase.IMAGE_BACKGROUND_POSITION_VERTICAL));
		initmap.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, poshlen);
		initmap.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, posvlen);
		// 默认长度为零
		final FixedLength length = new FixedLength(0d, "pt");
		final CondLengthProperty conlength = new CondLengthProperty(
				new FixedLength(0.0d, "mm"), false);
		initmap.put(Constants.PR_PADDING_BEFORE, conlength);
		initmap.put(Constants.PR_PADDING_AFTER, conlength);
		initmap.put(Constants.PR_PADDING_START, conlength);
		initmap.put(Constants.PR_PADDING_END, conlength);
		initmap.put(Constants.PR_PADDING_TOP, conlength);
		initmap.put(Constants.PR_PADDING_BOTTOM, conlength);
		initmap.put(Constants.PR_PADDING_LEFT, conlength);
		initmap.put(Constants.PR_PADDING_RIGHT, conlength);
		initmap.put(Constants.PR_FONT_FAMILY, "宋体");
		initmap.put(Constants.PR_FONT_SELECTION_STRATEGY, auto);
		initmap.put(Constants.PR_FONT_SIZE, new FixedLength(12d, "pt"));
		initmap.put(Constants.PR_FONT_STRETCH, Constants.EN_NORMAL);
		initmap.put(Constants.PR_FONT_STYLE, Constants.EN_NORMAL);
		initmap.put(Constants.PR_FONT_VARIANT, Constants.EN_NORMAL);
		initmap.put(Constants.PR_FONT_WEIGHT, Constants.EN_NORMAL);
		initmap.put(Constants.PR_MARGIN_TOP, length);
		initmap.put(Constants.PR_MARGIN_BOTTOM, length);
		initmap.put(Constants.PR_MARGIN_LEFT, length);
		initmap.put(Constants.PR_MARGIN_RIGHT, length);
		// initmap.put(Constants.PR_PADDING_BEFORE, length);
		final EnumProperty conditionality = new EnumProperty(
				Constants.EN_DISCARD, "");
		final EnumNumber precedence = new EnumNumber(-1, 0);
		final SpaceProperty space = new SpaceProperty(length, precedence,
				conditionality);
		final EnumLength enumlength = new EnumLength(Constants.EN_AUTO, null);
		initmap.put(Constants.PR_SPACE_BEFORE, space);
		initmap.put(Constants.PR_SPACE_AFTER, space);
		initmap.put(Constants.PR_SPACE_END, space);
		initmap.put(Constants.PR_SPACE_START, space);
		initmap.put(Constants.PR_START_INDENT, length);
		initmap.put(Constants.PR_END_INDENT, length);
		initmap.put(Constants.PR_RELATIVE_POSITION, Constants.EN_STATIC);
		initmap.put(Constants.PR_ALIGNMENT_ADJUST, enumlength);
		initmap.put(Constants.PR_ALIGNMENT_BASELINE, auto);
		initmap.put(Constants.PR_BASELINE_SHIFT, enumlength);
		initmap.put(Constants.PR_DISPLAY_ALIGN, auto);
		initmap.put(Constants.PR_DOMINANT_BASELINE, auto);
		initmap.put(Constants.PR_RELATIVE_ALIGN, Constants.EN_BEFORE);
		initmap.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION, enumlength);
		initmap.put(Constants.PR_CONTENT_HEIGHT, enumlength);
		initmap.put(Constants.PR_CONTENT_WIDTH, enumlength);
		initmap.put(Constants.PR_HEIGHT, enumlength);
		initmap.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, enumlength);
		initmap.put(Constants.PR_APHLA, 0);
		initmap.put(Constants.PR_MIN_HEIGHT, length);
		initmap.put(Constants.PR_SCALING, new EnumProperty(
				Constants.EN_UNIFORM, ""));
		initmap.put(Constants.PR_SCALING_METHOD, auto);
		initmap.put(Constants.PR_WIDTH, enumlength);
		initmap.put(Constants.PR_HYPHENATION_KEEP, auto);
		initmap.put(Constants.PR_HYPHENATION_LADDER_COUNT, new EnumNumber(
				Constants.EN_NO_LIMIT, -1));
		initmap.put(Constants.PR_LAST_LINE_END_INDENT, length);
		// 这个line_height和line_stacking_strategy的初始值设置好像有问题，暂时不用；用下面的,
		// FOP中这个line_height用normal值，虽然最后也转换成12×1.2了
		/*
		 * initmap.put(Constants.PR_LINE_HEIGHT,new SpaceProperty(new
		 * FixedLength(12d 1.2,"pt"),precedence,conditionality));
		 * initmap.put(Constants.PR_LINE_STACKING_STRATEGY,Constants.EN_NORMAL);
		 */
		initmap.put(Constants.PR_LINE_HEIGHT, new SpaceProperty(
				new PercentLength(1, new LengthBase(new FixedLength(12d * 1.2,
						"pt"), LengthBase.FONTSIZE)), precedence,
				conditionality));
		initmap.put(Constants.PR_LINE_STACKING_STRATEGY,
				Constants.EN_MAX_HEIGHT);
		initmap.put(Constants.PR_LINEFEED_TREATMENT,
				Constants.EN_TREAT_AS_SPACE);
		initmap.put(Constants.PR_WHITE_SPACE_TREATMENT, Constants.EN_PRESERVE);
		final EnumProperty startenum = new EnumProperty(Constants.EN_START, "");
		initmap.put(Constants.PR_TEXT_ALIGN, startenum);
		initmap.put(Constants.PR_TEXT_ALIGN_LAST, startenum);
		initmap.put(Constants.PR_TEXT_INDENT, length);
		initmap.put(Constants.PR_WHITE_SPACE_COLLAPSE, Constants.EN_FALSE);
		initmap.put(Constants.PR_WRAP_OPTION, Constants.EN_WRAP);
		initmap.put(Constants.PR_LETTER_SPACING, new EnumProperty(
				Constants.EN_NORMAL, ""));
		initmap.put(Constants.PR_SUPPRESS_AT_LINE_BREAK, auto);
		initmap.put(Constants.PR_TREAT_AS_WORD_SPACE, auto);
		initmap.put(Constants.PR_WORD_SPACING, new EnumProperty(
				Constants.EN_NORMAL, ""));
		initmap.put(Constants.PR_RENDERING_INTENT, auto);
		initmap.put(Constants.PR_INTRUSION_DISPLACE, auto);
		initmap.put(Constants.PR_BREAK_AFTER, auto);
		initmap.put(Constants.PR_BREAK_BEFORE, auto);
		// 初始化keep属性
		final EnumProperty enmu = new EnumProperty(Constants.EN_AUTO, "");
		final KeepProperty keep = new KeepProperty(enmu, enmu, enmu);
		initmap.put(Constants.PR_KEEP_TOGETHER, keep);
		initmap.put(Constants.PR_KEEP_WITH_NEXT, keep);
		initmap.put(Constants.PR_KEEP_WITH_PREVIOUS, keep);
		initmap.put(Constants.PR_WIDOWS, 2);
		initmap.put(Constants.PR_ORPHANS, 2);
		initmap.put(Constants.PR_CLIP, auto);
		initmap.put(Constants.PR_OVERFLOW, hidden);
		initmap.put(Constants.PR_REFERENCE_ORIENTATION, 0);
		initmap.put(Constants.PR_SPAN, Constants.EN_NONE);
		initmap.put(Constants.PR_LEADER_PATTERN, new EnumProperty(
				Constants.EN_SPACE, ""));
		initmap.put(Constants.PR_LEADER_PATTERN_WIDTH, new EnumLength(
				Constants.EN_USE_FONT_METRICS, null));
		initmap.put(Constants.PR_LEADER_ALIGNMENT, new EnumProperty(
				Constants.EN_NONE, ""));
		initmap.put(Constants.PR_RULE_STYLE, new EnumProperty(
				Constants.EN_SOLID, ""));
		initmap.put(Constants.PR_RULE_THICKNESS, new FixedLength(1d, "pt"));
		initmap.put(Constants.PR_AUTO_RESTORE, Constants.EN_FALSE);
		initmap.put(Constants.PR_DESTINATION_PLACEMENT_OFFSET, length);
		initmap.put(Constants.PR_EXTERNAL_DESTINATION, "");
		initmap.put(Constants.PR_INDICATE_DESTINATION, Constants.EN_FALSE);
		initmap.put(Constants.PR_INTERNAL_DESTINATION, "");
		// initmap.put(Constants.PR_SHOW_DESTINATION, Constants.EN_REP);
		initmap.put(Constants.PR_STARTING_STATE, Constants.EN_SHOW);
		// initmap.put(Constants.PR_SWITCH_TO, Constants.EN_);
		// initmap.put(Constants.PR_TARGET_PROCESSING_CONTEXT, Constants.EN_D);
		// initmap.put(Constants.PR_TARGET_PRESENTATION_CONTEXT,
		// Constants.EN_SHOW);
		// initmap.put(Constants.PR_TARGET_STYLESHEET, Constants.EN_US);
		initmap.put(Constants.PR_INDEX_CLASS, "");
		// initmap.put(Constants.PR_PAG, Constants.EN_SHOW);
		// initmap.put(Constants.PR_MERGE, Constants.EN_SHOW);
		initmap.put(Constants.PR_MARKER_CLASS_NAME, "");
		initmap.put(Constants.PR_RETRIEVE_CLASS_NAME, "");
		// initmap.put(Constants.PR_RETRIEVE_POSITION, Constants.EN_F);
		initmap.put(Constants.PR_RETRIEVE_BOUNDARY, Constants.EN_PAGE_SEQUENCE);
		initmap.put(Constants.PR_FORMAT, "1");
		initmap.put(Constants.PR_LETTER_VALUE, auto);
		initmap.put(Constants.PR_BLANK_OR_NOT_BLANK, Constants.EN_ANY);
		initmap.put(Constants.PR_COLUMN_COUNT, 1);
		initmap.put(Constants.PR_COLUMN_GAP, new FixedLength(12d, "pt"));
		initmap.put(Constants.PR_EXTENT, length);
		initmap.put(Constants.PR_FORCE_PAGE_COUNT, auto);
		initmap.put(Constants.PR_INITIAL_PAGE_NUMBER, auto);
		initmap.put(Constants.PR_MASTER_NAME, "");
		initmap.put(Constants.PR_MASTER_REFERENCE, "");
		initmap.put(Constants.PR_MAXIMUM_REPEATS, Constants.EN_NO_LIMIT);
		initmap.put(Constants.PR_MEDIA_USAGE, auto);
		initmap.put(Constants.PR_ODD_OR_EVEN, Constants.EN_ANY);
		// 目前默认值是这个,但是在程序中指定了默认值，所以不读这个默认值
		initmap.put(Constants.PR_PAGE_HEIGHT, auto);
		initmap.put(Constants.PR_PAGE_WIDTH, auto);

		initmap.put(Constants.PR_PAGE_POSITION, Constants.EN_ANY);
		initmap.put(Constants.PR_PRECEDENCE, Constants.EN_FALSE);
		initmap.put(Constants.PR_CAPTION_SIDE, Constants.EN_BEFORE);
		initmap.put(Constants.PR_ENDS_ROW, Constants.EN_FALSE);
		initmap.put(Constants.PR_NUMBER_COLUMNS_REPEATED, 1);
		initmap.put(Constants.PR_NUMBER_COLUMNS_SPANNED, 1);
		initmap.put(Constants.PR_NUMBER_ROWS_SPANNED, 1);
		initmap.put(Constants.PR_STARTS_ROW, Constants.EN_FALSE);
		initmap
				.put(Constants.PR_TABLE_OMIT_HEADER_AT_BREAK,
						Constants.EN_FALSE);
		initmap
				.put(Constants.PR_TABLE_OMIT_FOOTER_AT_BREAK,
						Constants.EN_FALSE);
		initmap.put(Constants.PR_TABLE_LAYOUT, auto);
		initmap.put(Constants.PR_DIRECTION, Constants.EN_LTR);
		// initmap.put(Constants.PR_GLYPH_ORIENTATION_HORIZONTAL,
		// Constants.EN_FALSE);
		// initmap.put(Constants.PR_GLYPH_ORIENTATION_VERTICAL,
		// Constants.EN_FALSE);
		initmap.put(Constants.PR_TEXT_ALTITUDE, Constants.EN_USE_FONT_METRICS);
		initmap.put(Constants.PR_TEXT_DEPTH, Constants.EN_USE_FONT_METRICS);
		initmap.put(Constants.PR_UNICODE_BIDI, Constants.EN_NORMAL);
		initmap.put(Constants.PR_WRITING_MODE, new EnumProperty(
				Constants.EN_LR_TB, ""));
		// initmap.put(Constants.PR_CH, Constants.EN_USE_FONT_METRICS);
		initmap.put(Constants.PR_CONTENT_TYPE, auto);
		initmap.put(Constants.PR_TEXT_ALTITUDE, Constants.EN_USE_FONT_METRICS);
		// initmap.put(Constants.PR_InT, Constants.EN_USE_FONT_METRICS);
		// initmap.put(Constants.PR_PAGE_CI, Constants.EN_USE_FONT_METRICS);
		initmap.put(Constants.PR_PROVISIONAL_LABEL_SEPARATION, new FixedLength(
				6d, "pt"));
		initmap.put(Constants.PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS,
				new FixedLength(24d, "pt"));
		// initmap.put(Constants.PR_SCALE_OPTION, new FixedLength(6d,"pt"));
		initmap.put(Constants.PR_SCORE_SPACES, Constants.EN_TRUE);
		initmap.put(Constants.PR_VISIBILITY, Constants.EN_VISIBLE);
		initmap.put(Constants.PR_Z_INDEX, auto);

		/* 【添加：START】 by 李晓光 2008-10-7 */
		initmap.put(Constants.PR_BORDER_COLLAPSE, new EnumProperty(
				Constants.EN_SEPARATE, "SEPARATE"));
		/*
		 * initmap.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, new
		 * LengthRangeProperty(new FixedLength(6, "cm")));
		 */
		initmap.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
				new LengthRangeProperty(enumlength));
		initmap.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
				new LengthRangeProperty(enumlength));
		/*
		 * initmap.put(Constants.PR_COLUMN_WIDTH, new TableColLength(1.0,
		 * null));
		 */
		initmap.put(Constants.PR_COLUMN_NUMBER, new NumberProperty(1));
		initmap.put(Constants.PR_BORDER_SEPARATION, new LengthPairProperty(
				new FixedLength(0), new FixedLength(0)));
		initmap.put(Constants.PR_EMPTY_CELLS, new EnumProperty(
				Constants.EN_SHOW, "SHOW"));
		/* 【添加：END】 by 李晓光 2008-10-7 */
		// add by zq 增加条码相关的默认属性值
		initmap.put(Constants.PR_BARCODE_TYPE, new EnumProperty(
				Constants.EN_CODE39, ""));
		initmap.put(Constants.PR_DATETIMEFORMAT, new WisedocDateTimeFormat(
				DateInfo.DEFAULTDATE, null, DateInfo.DEFAULTDATE, null, null,
				null));
		initmap.put(Constants.PR_INITIAL_PAGE_NUMBER, new EnumNumber(
				Constants.EN_AUTO, 1));
		initmap.put(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
				Constants.EN_WORDARTTEXT_TYPE_LINE, ""));
		initmap.put(Constants.PR_WORDARTTEXT_LETTERSPACE, new FixedLength(5d,
				"pt"));
		initmap.put(Constants.PR_WORDARTTEXT_CONTENT, "北京汇智互联软件技术有限公司");
		// 统计图相关的属性
		chartMap.put(Constants.PR_TITLE, new BarCodeText("统计图"));
		chartMap.put(Constants.PR_CHART_TYPE, new EnumProperty(
				Constants.EN_BARCHART, ""));
		chartMap.put(Constants.PR_SERIES_COUNT, 1);
		chartMap.put(Constants.PR_VALUE_COUNT, 6);
		chartMap.put(Constants.PR_CHART_ORIENTATION, new EnumProperty(
				Constants.EN_VERTICAL, ""));
		chartMap.put(Constants.PR_RANGEAXIS_LABEL, new BarCodeText("y"));
		chartMap.put(Constants.PR_DOMAINAXIS_LABEL, new BarCodeText("x"));

		final EnumProperty isShow = new EnumProperty(Constants.EN_FALSE, "");
		chartMap.put(Constants.PR_ZERORANGELINE_VISABLE, isShow);
		chartMap.put(Constants.PR_DOMIANLINE_VISABLE, isShow);
		chartMap.put(Constants.PR_RANGELINE_VISABLE, isShow);
		chartMap.put(Constants.PR_3DENABLE, isShow);
		chartMap.put(Constants.PR_VALUE_LABLEVISABLE, isShow);
		chartMap.put(Constants.PR_NULLVALUE_VISABLE, isShow);
		chartMap.put(Constants.PR_ZEROVALUE_VISABLE, isShow);

		chartMap.put(Constants.PR_SERIES_LABEL_ORIENTATION, 0);
		final FixedLength len = new FixedLength(0D, "mm");
//		chartMap.put(Constants.PR_MARGIN_TOP, len);
//		chartMap.put(Constants.PR_MARGIN_BOTTOM, len);
//		chartMap.put(Constants.PR_MARGIN_LEFT, len);
//		chartMap.put(Constants.PR_MARGIN_RIGHT, len);

		// chartMap.put(Constants.PR_RANGEAXIS_MINUNIT, 0);
		// chartMap.put(Constants.PR_RANGEAXIS_MAXUNIT, 10);
		// chartMap.put(Constants.PR_RANGEAXIS_UNITINCREMENT, 2.5);
		// chartMap.put(Constants.PR_RANGEAXIS_PRECISION, 2);
		chartMap.put(Constants.PR_CHART_VALUE_OFFSET, len);

		chartMap.put(Constants.PR_PIECHART_STARTANGLE, 90);// 角度
		chartMap.put(Constants.PR_PIECHART_DIRECTION, new EnumProperty(
				Constants.EN_CLOCKWISE, ""));
		chartMap.put(Constants.PR_PIECHARTLENGENDLABEL_VISABLE,
				new EnumProperty(Constants.EN_FALSE, ""));
		chartMap.put(Constants.PR_PIE_FACT_VALUE_VISABLE, new EnumProperty(
				Constants.EN_FALSE, ""));
		chartMap.put(Constants.PR_PERCENTVALUE_VISABLE, new EnumProperty(
				Constants.EN_FALSE, ""));

		// 统计图类型
		chartMap.put(Constants.PR_CHART_TYPE, new EnumProperty(
				Constants.EN_BARCHART, ""));

		chartMap.put(Constants.PR_LENGEND_LABEL_LOCATION, new EnumProperty(
				Constants.EN_BOTTOM, ""));
		chartMap.put(Constants.PR_LENGEND_LABLE_ALIGNMENT, new EnumProperty(
				Constants.EN_CENTER, ""));

		initmap.putAll(chartMap);
	}

	public final static Object getInitialValue(final int key,
			final Element element)
	{
		return initmap.get(key);
	}

	public final static Attributes getInitalAttributes()
	{
		return new Attributes(initmap);
	}

	public final static Map<Integer, Object> getChartMap()
	{
		return chartMap;
	}
}
