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
 * @GetAttributeNameWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.io.AttributeKeyNameFactory;
import com.wisii.wisedoc.io.wsd.attribute.ConditionItemCollectionWriter;
import com.wisii.wisedoc.io.wsd.attribute.GroupWriter;
import com.wisii.wisedoc.io.wsd.attribute.LogicalExpressionWriter;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-28
 */
public class XSLAttributeNameWriter implements AttributeKeyNameFactory
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.AttributeKeyNameFactory#getKey(java.lang.String)
	 */
	public int getKey(String name)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeKeyNameFactory#getKeyName(int)
	 */
	public String getKeyName(int key)
	{
		String attrName = "";
		if (attribute.containsKey(key))
		{
			String keymap = attribute.get(key);
			attrName = keymap;
			if ("".equalsIgnoreCase(keymap))
			{
				LogUtil.debug("传入的参数\"" + key + "\"在属性map中获取到的属性名为空");
			}
		} else
		{
			LogUtil.debug("传入的参数\"" + key + "\"在属性map中找不到该key");
		}
		// System.out.println("属性名:" + attrName);
		return attrName;
	}

	// 整数key和属性名之间对应关系
	protected Map<Integer, String> attribute = new HashMap<Integer, String>();
	{
		attribute.put(Constants.PR_ABSOLUTE_POSITION,
				FoXsltConstants.ABSOLUTE_POSITION);
		attribute.put(Constants.PR_ACTIVE_STATE, FoXsltConstants.ACTIVE_STATE);
		attribute.put(Constants.PR_ALIGNMENT_ADJUST,
				FoXsltConstants.ALIGNMENT_ADJUST);
		attribute.put(Constants.PR_ALIGNMENT_BASELINE,
				FoXsltConstants.ALIGNMENT_BASELINE);
		attribute.put(Constants.PR_AUTO_RESTORE, FoXsltConstants.AUTO_RESTORE);
		attribute.put(Constants.PR_AZIMUTH, FoXsltConstants.AZIMUTH);
		attribute.put(Constants.PR_BACKGROUND, FoXsltConstants.BACKGROUND);
		attribute.put(Constants.PR_BACKGROUND_ATTACHMENT,
				FoXsltConstants.BACKGROUND_ATTACHMENT);
		attribute.put(Constants.PR_BACKGROUND_COLOR,
				FoXsltConstants.BACKGROUND_COLOR);
		attribute.put(Constants.PR_BACKGROUND_IMAGE,
				FoXsltConstants.BACKGROUND_IMAGE);
		attribute.put(Constants.PR_BACKGROUND_POSITION,
				FoXsltConstants.BACKGROUND_POSITION);
		attribute.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
				FoXsltConstants.BACKGROUND_POSITION_HORIZONTAL);
		attribute.put(Constants.PR_BACKGROUND_POSITION_VERTICAL,
				FoXsltConstants.BACKGROUND_POSITION_VERTICAL);
		attribute.put(Constants.PR_BACKGROUND_REPEAT,
				FoXsltConstants.BACKGROUND_REPEAT);
		attribute.put(Constants.PR_BASELINE_SHIFT,
				FoXsltConstants.BASELINE_SHIFT);
		attribute.put(Constants.PR_BLANK_OR_NOT_BLANK,
				FoXsltConstants.BLANK_OR_NOT_BLANK);
		attribute.put(Constants.PR_BORDER, FoXsltConstants.BORDER);
		attribute.put(Constants.PR_BORDER_AFTER_COLOR,
				FoXsltConstants.BORDER_AFTER_COLOR);
		attribute.put(Constants.PR_BORDER_AFTER_PRECEDENCE,
				FoXsltConstants.BORDER_AFTER_PRECEDENCE);
		attribute.put(Constants.PR_BORDER_AFTER_STYLE,
				FoXsltConstants.BORDER_AFTER_STYLE);
		attribute.put(Constants.PR_BORDER_AFTER_WIDTH,
				FoXsltConstants.BORDER_AFTER_WIDTH);
		attribute.put(Constants.PR_BORDER_COLOR, FoXsltConstants.BORDER_COLOR);
		attribute.put(Constants.PR_BORDER_STYLE, FoXsltConstants.BORDER_STYLE);
		attribute.put(Constants.PR_BORDER_WIDTH, FoXsltConstants.BORDER_WIDTH);
		attribute.put(Constants.PR_BORDER_BEFORE_COLOR,
				FoXsltConstants.BORDER_BEFORE_COLOR);
		attribute.put(Constants.PR_BORDER_BEFORE_PRECEDENCE,
				FoXsltConstants.BORDER_BEFORE_PRECEDENCE);
		attribute.put(Constants.PR_BORDER_BEFORE_STYLE,
				FoXsltConstants.BORDER_BEFORE_STYLE);
		attribute.put(Constants.PR_BORDER_BEFORE_WIDTH,
				FoXsltConstants.BORDER_BEFORE_WIDTH);
		attribute
				.put(Constants.PR_BORDER_BOTTOM, FoXsltConstants.BORDER_BOTTOM);
		attribute.put(Constants.PR_BORDER_BOTTOM_COLOR,
				FoXsltConstants.BORDER_BOTTOM_COLOR);
		attribute.put(Constants.PR_BORDER_BOTTOM_STYLE,
				FoXsltConstants.BORDER_BOTTOM_STYLE);
		attribute.put(Constants.PR_BORDER_BOTTOM_WIDTH,
				FoXsltConstants.BORDER_BOTTOM_WIDTH);
		attribute.put(Constants.PR_BORDER_COLLAPSE,
				FoXsltConstants.BORDER_COLLAPSE);
		attribute.put(Constants.PR_BORDER_END_COLOR,
				FoXsltConstants.BORDER_END_COLOR);
		attribute.put(Constants.PR_BORDER_END_PRECEDENCE,
				FoXsltConstants.BORDER_END_PRECEDENCE);
		attribute.put(Constants.PR_BORDER_END_STYLE,
				FoXsltConstants.BORDER_END_STYLE);
		attribute.put(Constants.PR_BORDER_END_WIDTH,
				FoXsltConstants.BORDER_END_WIDTH);
		attribute.put(Constants.PR_BORDER_LEFT, FoXsltConstants.BORDER_LEFT);
		attribute.put(Constants.PR_BORDER_LEFT_COLOR,
				FoXsltConstants.BORDER_LEFT_COLOR);
		attribute.put(Constants.PR_BORDER_LEFT_STYLE,
				FoXsltConstants.BORDER_LEFT_STYLE);
		attribute.put(Constants.PR_BORDER_LEFT_WIDTH,
				FoXsltConstants.BORDER_LEFT_WIDTH);
		attribute.put(Constants.PR_BORDER_RIGHT, FoXsltConstants.BORDER_RIGHT);
		attribute.put(Constants.PR_BORDER_RIGHT_COLOR,
				FoXsltConstants.BORDER_RIGHT_COLOR);
		attribute.put(Constants.PR_BORDER_RIGHT_STYLE,
				FoXsltConstants.BORDER_RIGHT_STYLE);
		attribute.put(Constants.PR_BORDER_RIGHT_WIDTH,
				FoXsltConstants.BORDER_RIGHT_WIDTH);
		attribute.put(Constants.PR_BORDER_SEPARATION,
				FoXsltConstants.BORDER_SEPARATION);
		attribute.put(Constants.PR_BORDER_SPACING,
				FoXsltConstants.BORDER_SPACING);
		attribute.put(Constants.PR_BORDER_START_COLOR,
				FoXsltConstants.BORDER_START_COLOR);
		attribute.put(Constants.PR_BORDER_START_PRECEDENCE,
				FoXsltConstants.BORDER_START_PRECEDENCE);
		attribute.put(Constants.PR_BORDER_START_STYLE,
				FoXsltConstants.BORDER_START_STYLE);
		attribute.put(Constants.PR_BORDER_START_WIDTH,
				FoXsltConstants.BORDER_START_WIDTH);
		attribute.put(Constants.PR_BORDER_STYLE, FoXsltConstants.BORDER_STYLE);
		attribute.put(Constants.PR_BORDER_TOP, FoXsltConstants.BORDER_TOP);
		attribute.put(Constants.PR_BORDER_TOP_COLOR,
				FoXsltConstants.BORDER_TOP_COLOR);
		attribute.put(Constants.PR_BORDER_TOP_STYLE,
				FoXsltConstants.BORDER_TOP_STYLE);
		attribute.put(Constants.PR_BORDER_TOP_WIDTH,
				FoXsltConstants.BORDER_TOP_WIDTH);
		attribute.put(Constants.PR_BORDER_WIDTH, FoXsltConstants.BORDER_WIDTH);
		attribute.put(Constants.PR_BOTTOM, FoXsltConstants.BOTTOM);
		attribute.put(Constants.PR_BREAK_AFTER, FoXsltConstants.BREAK_AFTER);
		attribute.put(Constants.PR_BREAK_BEFORE, FoXsltConstants.BREAK_BEFORE);
		attribute.put(Constants.PR_CAPTION_SIDE, FoXsltConstants.CAPTION_SIDE);
		attribute.put(Constants.PR_CASE_NAME, FoXsltConstants.CASE_NAME);
		attribute.put(Constants.PR_CASE_TITLE, FoXsltConstants.CASE_TITLE);
		attribute.put(Constants.PR_CHARACTER, FoXsltConstants.CHARACTER);
		attribute.put(Constants.PR_CLEAR, FoXsltConstants.CLEAR);
		attribute.put(Constants.PR_CLIP, FoXsltConstants.CLIP);
		attribute.put(Constants.PR_COLOR, FoXsltConstants.COLOR);
		attribute.put(Constants.PR_COLOR_PROFILE_NAME,
				FoXsltConstants.COLOR_PROFILE_NAME);
		attribute.put(Constants.PR_COLUMN_COUNT, FoXsltConstants.COLUMN_COUNT);
		attribute.put(Constants.PR_COLUMN_GAP, FoXsltConstants.COLUMN_GAP);
		attribute
				.put(Constants.PR_COLUMN_NUMBER, FoXsltConstants.COLUMN_NUMBER);
		attribute.put(Constants.PR_COLUMN_WIDTH, FoXsltConstants.COLUMN_WIDTH);
		attribute.put(Constants.PR_CONTENT_HEIGHT,
				FoXsltConstants.CONTENT_HEIGHT);
		attribute.put(Constants.PR_CONTENT_TYPE, FoXsltConstants.CONTENT_TYPE);
		attribute
				.put(Constants.PR_CONTENT_WIDTH, FoXsltConstants.CONTENT_WIDTH);
		attribute.put(Constants.PR_COUNTRY, FoXsltConstants.COUNTRY);
		attribute.put(Constants.PR_CUE, FoXsltConstants.CUE);
		attribute.put(Constants.PR_CUE_AFTER, FoXsltConstants.CUE_AFTER);
		attribute.put(Constants.PR_CUE_BEFORE, FoXsltConstants.CUE_BEFORE);
		attribute.put(Constants.PR_DESTINATION_PLACEMENT_OFFSET,
				FoXsltConstants.DESTINATION_PLACEMENT_OFFSET);
		attribute.put(Constants.PR_DIRECTION, FoXsltConstants.DIRECTION);
		attribute
				.put(Constants.PR_DISPLAY_ALIGN, FoXsltConstants.DISPLAY_ALIGN);
		attribute.put(Constants.PR_DOMINANT_BASELINE,
				FoXsltConstants.DOMINANT_BASELINE);
		attribute.put(Constants.PR_ELEVATION, FoXsltConstants.ELEVATION);
		attribute.put(Constants.PR_EMPTY_CELLS, FoXsltConstants.EMPTY_CELLS);
		attribute.put(Constants.PR_END_INDENT, FoXsltConstants.END_INDENT);
		attribute.put(Constants.PR_ENDS_ROW, FoXsltConstants.ENDS_ROW);
		attribute.put(Constants.PR_EXTENT, FoXsltConstants.EXTENT);
		attribute.put(Constants.PR_EXTERNAL_DESTINATION,
				FoXsltConstants.EXTERNAL_DESTINATION);
		attribute.put(Constants.PR_FLOAT, FoXsltConstants.FLOAT_ATT);
		attribute.put(Constants.PR_FLOW_NAME, FoXsltConstants.FLOW_NAME);
		attribute.put(Constants.PR_FONT, FoXsltConstants.FONT);
		attribute.put(Constants.PR_FONT_FAMILY, FoXsltConstants.FONT_FAMILY);
		attribute.put(Constants.PR_FONT_SELECTION_STRATEGY,
				FoXsltConstants.FONT_SELECTION_STRATEGY);
		attribute.put(Constants.PR_FONT_SIZE, FoXsltConstants.FONT_SIZE);
		attribute.put(Constants.PR_FONT_SIZE_ADJUST,
				FoXsltConstants.FONT_SIZE_ADJUST);
		attribute.put(Constants.PR_FONT_STRETCH, FoXsltConstants.FONT_STRETCH);
		attribute.put(Constants.PR_FONT_STYLE, FoXsltConstants.FONT_STYLE);
		attribute.put(Constants.PR_FONT_VARIANT, FoXsltConstants.FONT_VARIANT);
		attribute.put(Constants.PR_FONT_WEIGHT, FoXsltConstants.FONT_WEIGHT);
		attribute.put(Constants.PR_FORCE_PAGE_COUNT,
				FoXsltConstants.FORCE_PAGE_COUNT);
		attribute.put(Constants.PR_FORMAT, FoXsltConstants.FORMAT);
		attribute.put(Constants.PR_GLYPH_ORIENTATION_HORIZONTAL,
				FoXsltConstants.GLYPH_ORIENTATION_HORIZONTAL);
		attribute.put(Constants.PR_GLYPH_ORIENTATION_VERTICAL,
				FoXsltConstants.GLYPH_ORIENTATION_VERTICAL);
		attribute.put(Constants.PR_GROUPING_SEPARATOR,
				FoXsltConstants.GROUPING_SEPARATOR);
		attribute
				.put(Constants.PR_GROUPING_SIZE, FoXsltConstants.GROUPING_SIZE);
		attribute.put(Constants.PR_HEIGHT, FoXsltConstants.HEIGHT);
		attribute.put(Constants.PR_HYPHENATE, FoXsltConstants.HYPHENATE);
		attribute.put(Constants.PR_HYPHENATION_CHARACTER,
				FoXsltConstants.HYPHENATION_CHARACTER);
		attribute.put(Constants.PR_HYPHENATION_KEEP,
				FoXsltConstants.HYPHENATION_KEEP);
		attribute.put(Constants.PR_HYPHENATION_LADDER_COUNT,
				FoXsltConstants.HYPHENATION_LADDER_COUNT);
		attribute.put(Constants.PR_HYPHENATION_PUSH_CHARACTER_COUNT,
				FoXsltConstants.HYPHENATION_PUSH_CHARACTER_COUNT);
		attribute.put(Constants.PR_HYPHENATION_REMAIN_CHARACTER_COUNT,
				FoXsltConstants.HYPHENATION_REMAIN_CHARACTER_COUNT);
		attribute.put(Constants.PR_ID, FoXsltConstants.ID);
		attribute.put(Constants.PR_INDICATE_DESTINATION,
				FoXsltConstants.INDICATE_DESTINATION);
		attribute.put(Constants.PR_INITIAL_PAGE_NUMBER,
				FoXsltConstants.INITIAL_PAGE_NUMBER);
		attribute.put(Constants.PR_INTERNAL_DESTINATION,
				FoXsltConstants.INTERNAL_DESTINATION);
		attribute.put(Constants.PR_LANGUAGE, FoXsltConstants.LANGUAGE);
		attribute.put(Constants.PR_LAST_LINE_END_INDENT,
				FoXsltConstants.LAST_LINE_END_INDENT);
		attribute.put(Constants.PR_LEADER_ALIGNMENT,
				FoXsltConstants.LEADER_ALIGNMENT);
		attribute
				.put(Constants.PR_LEADER_LENGTH, FoXsltConstants.LEADER_LENGTH);
		attribute.put(Constants.PR_LEADER_PATTERN,
				FoXsltConstants.LEADER_PATTERN);
		attribute.put(Constants.PR_LEADER_PATTERN_WIDTH,
				FoXsltConstants.LEADER_PATTERN_WIDTH);
		attribute.put(Constants.PR_LEFT, FoXsltConstants.LEFT);
		attribute.put(Constants.PR_LETTER_SPACING,
				FoXsltConstants.LETTER_SPACING);
		attribute.put(Constants.PR_LETTER_VALUE, FoXsltConstants.LETTER_VALUE);
		attribute.put(Constants.PR_LINEFEED_TREATMENT,
				FoXsltConstants.LINEFEED_TREATMENT);
		attribute.put(Constants.PR_LINE_HEIGHT_SHIFT_ADJUSTMENT,
				FoXsltConstants.LINE_HEIGHT_SHIFT_ADJUSTMENT);
		attribute.put(Constants.PR_LINE_STACKING_STRATEGY,
				FoXsltConstants.LINE_STACKING_STRATEGY);
		attribute.put(Constants.PR_MARGIN, FoXsltConstants.MARGIN);
		attribute
				.put(Constants.PR_MARGIN_BOTTOM, FoXsltConstants.MARGIN_BOTTOM);
		attribute.put(Constants.PR_MARGIN_LEFT, FoXsltConstants.MARGIN_LEFT);
		attribute.put(Constants.PR_MARGIN_RIGHT, FoXsltConstants.MARGIN_RIGHT);
		attribute.put(Constants.PR_MARGIN_TOP, FoXsltConstants.MARGIN_TOP);
		attribute.put(Constants.PR_MARKER_CLASS_NAME,
				FoXsltConstants.MARKER_CLASS_NAME);
		attribute.put(Constants.PR_MASTER_NAME, FoXsltConstants.MASTER_NAME);
		attribute.put(Constants.PR_MASTER_REFERENCE,
				FoXsltConstants.MASTER_REFERENCE);
		attribute.put(Constants.PR_MAX_HEIGHT, FoXsltConstants.MAX_HEIGHT);
		attribute.put(Constants.PR_MAXIMUM_REPEATS,
				FoXsltConstants.MAXIMUM_REPEATS);
		attribute.put(Constants.PR_MAX_WIDTH, FoXsltConstants.MAX_WIDTH);
		attribute.put(Constants.PR_MEDIA_USAGE, FoXsltConstants.MEDIA_USAGE);
		attribute.put(Constants.PR_MIN_HEIGHT, FoXsltConstants.MIN_HEIGHT);
		attribute.put(Constants.PR_MIN_WIDTH, FoXsltConstants.MIN_WIDTH);
		attribute.put(Constants.PR_NUMBER_COLUMNS_REPEATED,
				FoXsltConstants.NUMBER_COLUMNS_REPEATED);
		attribute.put(Constants.PR_NUMBER_COLUMNS_SPANNED,
				FoXsltConstants.NUMBER_COLUMNS_SPANNED);
		attribute.put(Constants.PR_NUMBER_ROWS_SPANNED,
				FoXsltConstants.NUMBER_ROWS_SPANNED);
		attribute.put(Constants.PR_ODD_OR_EVEN, FoXsltConstants.ODD_OR_EVEN);
		attribute.put(Constants.PR_ORPHANS, FoXsltConstants.ORPHANS);
		attribute.put(Constants.PR_OVERFLOW, FoXsltConstants.OVERFLOW);
		attribute.put(Constants.PR_PADDING, FoXsltConstants.PADDING);
		attribute
				.put(Constants.PR_PADDING_AFTER, FoXsltConstants.PADDING_AFTER);
		attribute.put(Constants.PR_PADDING_BEFORE,
				FoXsltConstants.PADDING_BEFORE);
		attribute.put(Constants.PR_PADDING_BOTTOM,
				FoXsltConstants.PADDING_BOTTOM);
		attribute.put(Constants.PR_PADDING_END, FoXsltConstants.PADDING_END);
		attribute.put(Constants.PR_PADDING_LEFT, FoXsltConstants.PADDING_LEFT);
		attribute
				.put(Constants.PR_PADDING_RIGHT, FoXsltConstants.PADDING_RIGHT);
		attribute
				.put(Constants.PR_PADDING_START, FoXsltConstants.PADDING_START);
		attribute.put(Constants.PR_PADDING_TOP, FoXsltConstants.PADDING_TOP);
		attribute.put(Constants.PR_PAGE_BREAK_AFTER,
				FoXsltConstants.PAGE_BREAK_AFTER);
		attribute.put(Constants.PR_PAGE_BREAK_BEFORE,
				FoXsltConstants.PAGE_BREAK_BEFORE);
		attribute.put(Constants.PR_PAGE_BREAK_INSIDE,
				FoXsltConstants.PAGE_BREAK_INSIDE);
		attribute.put(Constants.PR_PAGE_HEIGHT, FoXsltConstants.PAGE_HEIGHT);
		attribute
				.put(Constants.PR_PAGE_POSITION, FoXsltConstants.PAGE_POSITION);
		attribute.put(Constants.PR_PAGE_WIDTH, FoXsltConstants.PAGE_WIDTH);
		attribute.put(Constants.PR_PAUSE, FoXsltConstants.PAUSE);
		attribute.put(Constants.PR_PAUSE_AFTER, FoXsltConstants.PAUSE_AFTER);
		attribute.put(Constants.PR_PAUSE_BEFORE, FoXsltConstants.PAUSE_BEFORE);
		attribute.put(Constants.PR_PITCH, FoXsltConstants.PITCH);
		attribute.put(Constants.PR_PITCH_RANGE, FoXsltConstants.PITCH_RANGE);
		attribute.put(Constants.PR_PLAY_DURING, FoXsltConstants.PLAY_DURING);
		attribute.put(Constants.PR_POSITION, FoXsltConstants.POSITION);
		attribute.put(Constants.PR_PRECEDENCE, FoXsltConstants.PRECEDENCE);
		attribute.put(Constants.PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS,
				FoXsltConstants.PROVISIONAL_DISTANCE_BETWEEN_STARTS);
		attribute.put(Constants.PR_PROVISIONAL_LABEL_SEPARATION,
				FoXsltConstants.PROVISIONAL_LABEL_SEPARATION);
		attribute.put(Constants.PR_REFERENCE_ORIENTATION,
				FoXsltConstants.REFERENCE_ORIENTATION);
		attribute.put(Constants.PR_REF_ID, FoXsltConstants.REF_ID);
		attribute.put(Constants.PR_REGION_NAME, FoXsltConstants.REGION_NAME);
		attribute.put(Constants.PR_RELATIVE_ALIGN,
				FoXsltConstants.RELATIVE_ALIGN);
		attribute.put(Constants.PR_RELATIVE_POSITION,
				FoXsltConstants.RELATIVE_POSITION);
		attribute.put(Constants.PR_RENDERING_INTENT,
				FoXsltConstants.RENDERING_INTENT);
		attribute.put(Constants.PR_RETRIEVE_BOUNDARY,
				FoXsltConstants.RETRIEVE_BOUNDARY);
		attribute.put(Constants.PR_RETRIEVE_CLASS_NAME,
				FoXsltConstants.RETRIEVE_CLASS_NAME);
		attribute.put(Constants.PR_RETRIEVE_POSITION,
				FoXsltConstants.RETRIEVE_POSITION);
		attribute.put(Constants.PR_RICHNESS, FoXsltConstants.RICHNESS);
		attribute.put(Constants.PR_RIGHT, FoXsltConstants.RIGHT);
		attribute.put(Constants.PR_ROLE, FoXsltConstants.ROLE);
		attribute.put(Constants.PR_RULE_STYLE, FoXsltConstants.RULE_STYLE);
		attribute.put(Constants.PR_RULE_THICKNESS,
				FoXsltConstants.RULE_THICKNESS);
		attribute.put(Constants.PR_SCALING, FoXsltConstants.SCALING);
		attribute.put(Constants.PR_SCALING_METHOD,
				FoXsltConstants.SCALING_METHOD);
		attribute.put(Constants.PR_SCORE_SPACES, FoXsltConstants.SCORE_SPACES);
		attribute.put(Constants.PR_SCRIPT, FoXsltConstants.SCRIPT);
		attribute.put(Constants.PR_SHOW_DESTINATION,
				FoXsltConstants.SHOW_DESTINATION);
		attribute.put(Constants.PR_SIZE, FoXsltConstants.SIZE);
		attribute.put(Constants.PR_SOURCE_DOCUMENT,
				FoXsltConstants.SOURCE_DOCUMENT);
		attribute.put(Constants.PR_SPAN, FoXsltConstants.SPAN);
		attribute.put(Constants.PR_SPEAK, FoXsltConstants.SPEAK);
		attribute.put(Constants.PR_SPEAK_HEADER, FoXsltConstants.SPEAK_HEADER);
		attribute
				.put(Constants.PR_SPEAK_NUMERAL, FoXsltConstants.SPEAK_NUMERAL);
		attribute.put(Constants.PR_SPEAK_PUNCTUATION,
				FoXsltConstants.SPEAK_PUNCTUATION);
		attribute.put(Constants.PR_SPEECH_RATE, FoXsltConstants.SPEECH_RATE);
		attribute.put(Constants.PR_SRC, FoXsltConstants.SRC);
		attribute.put(Constants.PR_SRC_TYPE, FoXsltConstants.SRC_TYPE);
		attribute.put(Constants.PR_APHLA, FoXsltConstants.ALPHA);
		attribute.put(Constants.PR_START_INDENT, FoXsltConstants.START_INDENT);
		attribute.put(Constants.PR_STARTING_STATE,
				FoXsltConstants.STARTING_STATE);
		attribute.put(Constants.PR_STARTS_ROW, FoXsltConstants.STARTS_ROW);
		attribute.put(Constants.PR_STRESS, FoXsltConstants.STRESS);
		attribute.put(Constants.PR_SUPPRESS_AT_LINE_BREAK,
				FoXsltConstants.SUPPRESS_AT_LINE_BREAK);
		attribute.put(Constants.PR_SWITCH_TO, FoXsltConstants.SWITCH_TO);
		attribute.put(Constants.PR_TABLE_LAYOUT, FoXsltConstants.TABLE_LAYOUT);
		attribute.put(Constants.PR_TABLE_OMIT_FOOTER_AT_BREAK,
				FoXsltConstants.TABLE_OMIT_FOOTER_AT_BREAK);
		attribute.put(Constants.PR_TABLE_OMIT_HEADER_AT_BREAK,
				FoXsltConstants.TABLE_OMIT_HEADER_AT_BREAK);
		attribute.put(Constants.PR_TARGET_PRESENTATION_CONTEXT,
				FoXsltConstants.TARGET_PRESENTATION_CONTEXT);
		attribute.put(Constants.PR_TARGET_PROCESSING_CONTEXT,
				FoXsltConstants.TARGET_PROCESSING_CONTEXT);
		attribute.put(Constants.PR_TARGET_STYLESHEET,
				FoXsltConstants.TARGET_STYLESHEET);
		attribute.put(Constants.PR_TEXT_ALIGN, FoXsltConstants.TEXT_ALIGN);
		attribute.put(Constants.PR_TEXT_ALIGN_LAST,
				FoXsltConstants.TEXT_ALIGN_LAST);
		attribute
				.put(Constants.PR_TEXT_ALTITUDE, FoXsltConstants.TEXT_ALTITUDE);
		attribute.put(Constants.PR_TEXT_DECORATION,
				FoXsltConstants.TEXT_DECORATION);
		attribute.put(Constants.PR_TEXT_DEPTH, FoXsltConstants.TEXT_DEPTH);
		attribute.put(Constants.PR_TEXT_INDENT, FoXsltConstants.TEXT_INDENT);
		attribute.put(Constants.PR_TEXT_SHADOW, FoXsltConstants.TEXT_SHADOW);
		attribute.put(Constants.PR_TEXT_TRANSFORM,
				FoXsltConstants.TEXT_TRANSFORM);
		attribute.put(Constants.PR_TOP, FoXsltConstants.TOP);
		attribute.put(Constants.PR_TREAT_AS_WORD_SPACE,
				FoXsltConstants.TREAT_AS_WORD_SPACE);
		attribute.put(Constants.PR_UNICODE_BIDI, FoXsltConstants.UNICODE_BIDI);
		attribute.put(Constants.PR_VERTICAL_ALIGN,
				FoXsltConstants.VERTICAL_ALIGN);
		attribute.put(Constants.PR_VISIBILITY, FoXsltConstants.VISIBILITY);
		attribute.put(Constants.PR_VOICE_FAMILY, FoXsltConstants.VOICE_FAMILY);
		attribute.put(Constants.PR_VOLUME, FoXsltConstants.VOLUME);
		attribute.put(Constants.PR_WHITE_SPACE, FoXsltConstants.WHITE_SPACE);
		attribute.put(Constants.PR_WHITE_SPACE_COLLAPSE,
				FoXsltConstants.WHITE_SPACE_COLLAPSE);
		attribute.put(Constants.PR_WHITE_SPACE_TREATMENT,
				FoXsltConstants.WHITE_SPACE_TREATMENT);
		attribute.put(Constants.PR_WIDOWS, FoXsltConstants.WIDOWS);
		attribute.put(Constants.PR_WIDTH, FoXsltConstants.WIDTH);
		attribute.put(Constants.PR_WORD_SPACING, FoXsltConstants.WORD_SPACING);
		attribute.put(Constants.PR_WRAP_OPTION, FoXsltConstants.WRAP_OPTION);
		attribute.put(Constants.PR_WRITING_MODE, FoXsltConstants.WRITING_MODE);
		attribute.put(Constants.PR_XML_LANG, FoXsltConstants.XML_LANG);
		attribute.put(Constants.PR_Z_INDEX, FoXsltConstants.Z_INDEX);
		attribute.put(Constants.PR_INTRUSION_DISPLACE,
				FoXsltConstants.INTRUSION_DISPLACE);
		attribute.put(Constants.PR_INDEX_CLASS, FoXsltConstants.INDEX_CLASS);
		attribute.put(Constants.PR_INDEX_KEY, FoXsltConstants.INDEX_KEY);
		attribute
				.put(Constants.PR_GRAPHIC_LAYER, FoXsltConstants.GRAPHIC_LAYER);
		attribute.put(Constants.PR_BACKGROUNDGRAPHIC_LAYER,
				FoXsltConstants.BACKGROUNDIMAGE_LAYER);
		attribute.put(Constants.PR_HANGING_INDENT, "hanging_indent");
		// attribute.put(Constants.PR_RETRIEVE_BOUNDARY_WITHIN_TABLE,
		// FoXsltConstants.RETRIEVE_BOUNDARY_WITHIN_TABLE);
		// attribute.put(Constants.PR_RETRIEVE_POSITION_WITHIN_TABLE,
		// FoXsltConstants.RETRIEVE_POSITION_WITHIN_TABLE);
		// FOV
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_FOV_XPATH,
				FoXsltConstants.FOV_XPATH);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_FOV_EDITMODE,
				FoXsltConstants.FOV_EDITMODE);
		// XSL
		attribute
				.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_DISABLE_OUTPUT_ESCAPING,
						FoXsltConstants.DISABLE_OUTPUT_ESCAPING);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_INDENT,
				FoXsltConstants.INDENT);
		attribute.put(
				com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_CASE_ORDER,
				FoXsltConstants.CASE_ORDER);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_ORDER,
				FoXsltConstants.ORDER);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_DATE_TYPE,
				FoXsltConstants.DATE_TYPE);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_VERSION,
				FoXsltConstants.VERSION);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_METHOD,
				FoXsltConstants.METHOD);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_ENCODING,
				FoXsltConstants.ENCODING);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAME,
				FoXsltConstants.NAME);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_MODE,
				FoXsltConstants.MODE);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_MATCH,
				FoXsltConstants.MATCH);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_VALUE,
				FoXsltConstants.VALUE);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_FORMAT,
				FoXsltConstants.FORMAT);
		attribute
				.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_DECIMAL_SEPARETOR,
						FoXsltConstants.DECIMAL_SEPARATOR);
		attribute
				.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_GROUPING_SEPARATOR,
						FoXsltConstants.GROUPING_SEPARATOR);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAN,
				FoXsltConstants.NAN);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_PERCENT,
				FoXsltConstants.PERCENT);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_PER_MILLE,
				FoXsltConstants.PER_MILLE);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_INFINITY,
				FoXsltConstants.INFINITY);
		attribute.put(
				com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_MINUS_SIGN,
				FoXsltConstants.MINUS_SIGN);
		attribute.put(
				com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_ZERO_DIGIT,
				FoXsltConstants.ZERO_DIGIT);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_DIGIT,
				FoXsltConstants.DIGIT);
		attribute
				.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_PATTERN_SEPARATOR,
						FoXsltConstants.PATTERN_SEPARATOR);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT,
				FoXsltConstants.SELECT);
		attribute.put(com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_TEST,
				FoXsltConstants.TEST);
		//
		attribute
				.put(Constants.PR_KEEP_TOGETHER, FoXsltConstants.KEEP_TOGETHER);
		attribute.put(Constants.PR_KEEP_WITH_NEXT,
				FoXsltConstants.KEEP_WITH_NEXT);
		attribute.put(Constants.PR_KEEP_WITH_PREVIOUS,
				FoXsltConstants.KEEP_WITH_PREVIOUS);
		attribute.put(Constants.PR_SPACE_AFTER, FoXsltConstants.SPACE_AFTER);
		attribute.put(Constants.PR_SPACE_BEFORE, FoXsltConstants.SPACE_BEFORE);
		attribute.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
				FoXsltConstants.INLINE_PROGRESSION_DIMENSION);
		attribute.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
				FoXsltConstants.BLOCK_PROGRESSION_DIMENSION);
		attribute.put(Constants.PR_LINE_HEIGHT, FoXsltConstants.LINE_HEIGHT);
		attribute.put(Constants.PR_SPACE_END, FoXsltConstants.SPACE_END);
		attribute.put(Constants.PR_SPACE_START, FoXsltConstants.SPACE_START);
		// add by zq
		attribute.put(Constants.PR_EDITMODE, FoXsltConstants.EDITMODE);
		// add end
		attribute.put(Constants.PR_X_BLOCK_PROGRESSION_UNIT,
				"x-block-progression-unit");
		attribute.put(Constants.PR_XPATH, FoXsltConstants.XPATH);
		attribute.put(Constants.PR_TRANSLATEURL, "translataurl");
		attribute.put(Constants.PR_HIDENAME, FoXsltConstants.HIDE);
		attribute.put(Constants.PR_SIMPLE_PAGE_MASTER,
				FoXsltConstants.SIMPLE_PAGE_MASTER);
		attribute.put(Constants.PR_PAGE_SEQUENCE_MASTER,
				FoXsltConstants.PAGESEQUENCEMASTER);
		attribute.put(Constants.PR_BARCODE_TYPE, "barcode-type");
		attribute.put(Constants.PR_BARCODE_CONTENT, "barcode-content");
		attribute.put(Constants.PR_BARCODE_VALUE, "barcode-value");
		attribute.put(Constants.PR_BARCODE_HEIGHT, "barcode-height");
		attribute.put(Constants.PR_BARCODE_MODULE, "barcode-module");
		attribute.put(Constants.PR_BARCODE_FONT_HEIGHT, "barcode-font-height");
		attribute.put(Constants.PR_BARCODE_FONT_FAMILY, "barcode-font-family");
		attribute.put(Constants.PR_BARCODE_QUIET_HORIZONTAL,
				"barcode-quiet-horizontal");
		attribute.put(Constants.PR_BARCODE_QUIET_VERTICAL,
				"barcode-quiet-vertical");
		attribute.put(Constants.PR_BARCODE_ORIENTATION, "barcode-orientation");
		attribute.put(Constants.PR_BARCODE_ADDCHECKSUM, "barcode-addchecksum");
		attribute.put(Constants.PR_BARCODE_WIDE_TO_NARROW,
				"barcode-wide-to-narrow");
		attribute.put(Constants.PR_BARCODE_TEXT_CHAR_SPACE,
				"barcode-text-char-space");
		attribute.put(Constants.PR_BARCODE_STRING, "barcode-string");
		attribute.put(Constants.PR_BARCODE_PRINT_TEXT, "barcode-print-text");
		attribute.put(Constants.PR_BARCODE_TEXT_BLOCK, "barcode-text-block");
		attribute.put(Constants.PR_BARCODE_CODE_TYPE, "barcode-code-type");
		attribute.put(Constants.PR_BARCODE_SUBSET, "barcode-sunset");
		attribute.put(Constants.PR_BARCODE_MAKEUCC, "barcode-makeucc");
		attribute.put(Constants.PR_DATETIMEFORMAT, "datetime-format");
		attribute.put(Constants.PR_NUMBERFORMAT, "number-format");
		attribute.put(Constants.PR_POSITION_NUMBER_TYPE, "format");
		attribute.put(Constants.PR_ISSHOWTOTAL, "isshowtotal");
		attribute.put(Constants.PR_NUMBERTEXT_TYPE, "numbertext-type");
		attribute.put(Constants.PR_LAWLESSDATAPROCESS, "errordataprocess");
		attribute.put(Constants.PR_ENDOFALL, "endofall");
		attribute.put(Constants.PR_CONDTION,
				LogicalExpressionWriter.CONDITIONTAG);
		attribute.put(Constants.PR_GROUP, GroupWriter.GROUPTAG);
		// add by zq 增加了图形的相关属性常量
		attribute.put(Constants.PR_X, FoXsltConstants.X);
		attribute.put(Constants.PR_Y, FoXsltConstants.Y);
		attribute.put(Constants.PR_X1, FoXsltConstants.X1);
		attribute.put(Constants.PR_Y1, FoXsltConstants.Y1);
		attribute.put(Constants.PR_X2, FoXsltConstants.X2);
		attribute.put(Constants.PR_Y2, FoXsltConstants.Y2);
		attribute.put(Constants.PR_CX, FoXsltConstants.CX);
		attribute.put(Constants.PR_CY, FoXsltConstants.CY);
		attribute.put(Constants.PR_R, FoXsltConstants.R);
		attribute.put(Constants.PR_RX, FoXsltConstants.RX);
		attribute.put(Constants.PR_RY, FoXsltConstants.RY);
		attribute.put(Constants.PR_FILL, FoXsltConstants.FILL);
		attribute.put(Constants.PR_STROKE_WIDTH, FoXsltConstants.STROKE_WIDTH);
		attribute.put(Constants.PR_SVG_LINE_TYPE,
				FoXsltConstants.STROKE_DASHARRAY);
		attribute.put(Constants.PR_SVG_STROKE_LINEJOIN,
				FoXsltConstants.STROKE_LINEJOIN);
		attribute.put(Constants.PR_SVG_ARROW_START_TYPE,"linestarttype");
		attribute.put(Constants.PR_SVG_ARROW_END_TYPE,"lineendtype");
		attribute.put(Constants.PR_SVG_ORIENTATION, "svg-orientation");
		attribute.put(Constants.PR_SVG_TEXT_CONTENT, "svg-text");
		attribute.put(Constants.PR_DYNAMIC_STYLE,
				ConditionItemCollectionWriter.DYNAMICSTYLETAG);
		attribute.put(Constants.PR_BLOCK_STYLE, "paragraphstyle");
		attribute.put(Constants.PR_BLOCK_STYLE_COLLECTION, "parastylecollection");
		// 目录相关的属性名
		attribute.put(Constants.PR_BLOCK_LEVEL,"paragraphlevel");
		attribute.put(Constants.PR_BOOKMARK_TITLE, "bookmarktitle");
		attribute.put(Constants.PR_BLOCK_REF_NUMBER, "blockrefnumber");
		attribute.put(Constants.PR_BLOCK_REF_RIGHT_ALIGN, "blockrefrightalign");
		attribute.put(Constants.PR_BLOCK_REF_SHOW_LEVEL, "blockrefshoelevel");
		attribute.put(Constants.PR_BLOCK_REF_STYLES, "blockrefstyles");
		attribute.put(Constants.PR_BARCODE_EC_LEVEL, "barcodeeclevel");
		attribute.put(Constants.PR_BARCODE_COLUMNS, "barcodecolumns");
		attribute.put(Constants.PR_BARCODE_MIN_COLUMNS, "barcodemincolumns");
		attribute.put(Constants.PR_BARCODE_MAX_COLUMNS, "barcodemaxcolumns");
		attribute.put(Constants.PR_BARCODE_MIN_ROWS, "barcodeminrows");
		attribute.put(Constants.PR_BARCODE_MAX_ROWS, "barcodemaxrows");
        //统计图相关属性		
		attribute.put(Constants.PR_CHART_TYPE, "charttype");
		attribute.put(Constants.PR_TITLE, "title");
		attribute.put(Constants.PR_TITLE_FONTFAMILY, "titlefontfamily");
		attribute.put(Constants.PR_TITLE_FONTSTYLE, "titlefontstyle");
		attribute.put(Constants.PR_TITLE_FONTSIZE, "titlefontsize");
		attribute.put(Constants.PR_TITLE_COLOR, "titlecolor");
		attribute.put(Constants.PR_TITLE_ALIGNMENT, "titlealignment");
		attribute.put(Constants.PR_BACKGROUNDIMAGE_ALAPH, "backgroundimagealaph");
		attribute.put(Constants.PR_FOREGROUND_ALAPH, "foregroundalaph");
		attribute.put(Constants.PR_VALUE_COUNT, "valuecount");
		attribute.put(Constants.PR_SERIES_COUNT, "seriescount");
		attribute.put(Constants.PR_VALUE_COLOR, "valuecolor");
		attribute.put(Constants.PR_VALUE_LABEL, "valuelabel");
		attribute.put(Constants.PR_VALUE_LABEL_FONTFAMILY, "valuelabelfontfamily");
		attribute.put(Constants.PR_VALUE_LABEL_FONTSTYLE, "valuelabelfontstyle");
		attribute.put(Constants.PR_VALUE_LABEL_FONTSIZE, "valuelabelfontsize");
		attribute.put(Constants.PR_VALUE_LABEL_COLOR, "valuelabelcolor");
		attribute.put(Constants.PR_BACKGROUNDIMAGE_ALAPH, "backgroundimagealaph");
		attribute.put(Constants.PR_FOREGROUND_ALAPH, "foregroundalaph");
		attribute.put(Constants.PR_VALUE_COUNT, "valuecount");
		attribute.put(Constants.PR_SERIES_COUNT, "seriescount");
		attribute.put(Constants.PR_VALUE_COLOR, "valuecolor");
		attribute.put(Constants.PR_VALUE_LABEL, "valuelabel");
		attribute.put(Constants.PR_VALUE_LABEL_FONTFAMILY, "valuelabelfontfamily");
		attribute.put(Constants.PR_VALUE_LABEL_FONTSTYLE, "valuelabelfontstyle");
		attribute.put(Constants.PR_VALUE_LABEL_FONTSIZE, "valuelabelfontsize");
		attribute.put(Constants.PR_VALUE_LABEL_COLOR, "valuelabelcolor");
		attribute.put(Constants.PR_SERIES_VALUE, "seriesvalue");
		attribute.put(Constants.PR_SERIES_LABEL, "serieslabel");
		attribute.put(Constants.PR_SERIES_LABEL_FONTFAMILY, "serieslabelfontfamily");
		attribute.put(Constants.PR_SERIES_LABEL_FONTSTYLE, "serieslabelfontstyle");
		attribute.put(Constants.PR_SERIES_LABEL_FONTSIZE, "serieslabelfontsize");
		attribute.put(Constants.PR_SERIES_LABEL_COLOR, "serieslabelcolor");
		attribute.put(Constants.PR_SERIES_LABEL_ORIENTATION, "serieslabelorientation");
		attribute.put(Constants.PR_DOMAINAXIS_LABEL, "domainaxislabel");
		attribute.put(Constants.PR_RANGEAXIS_LABEL, "rangeaxislabel");
		attribute.put(Constants.PR_DOMAINAXIS_LABEL_FONTFAMILY, "domainaxislabelfontfamily");
		attribute.put(Constants.PR_DOMAINAXIS_LABEL_FONTSTYLE, "domainaxislabelfontstyle");
		attribute.put(Constants.PR_DOMAINAXIS_LABEL_FONTSIZE, "domainaxislabelfontsize");
		attribute.put(Constants.PR_DOMAINAXIS_LABEL_COLOR, "domainaxislabelcolor");
		attribute.put(Constants.PR_DOMAINAXIS_LABEL_ALIGNMENT, "domainaxislabelalignment");
		attribute.put(Constants.PR_RANGEAXIS_LABEL_FONTFAMILY, "rangeaxislabelfontfamily");
		attribute.put(Constants.PR_RANGEAXIS_LABEL_FONTSTYLE, "rangeaxislabelfontstyle");
		attribute.put(Constants.PR_RANGEAXIS_LABEL_FONTSIZE, "rangeaxislabelfontsize");
		attribute.put(Constants.PR_RANGEAXIS_LABEL_COLOR, "rangeaxislabelcolor");
		attribute.put(Constants.PR_RANGEAXIS_LABEL_ALIGNMENT, "rangeaxislabelalignment");
		attribute.put(Constants.PR_CHART_ORIENTATION, "chartorientation");
		attribute.put(Constants.PR_RANGEAXIS_PRECISION, "rangeaxisprecision");
		attribute.put(Constants.PR_DOMIANLINE_VISABLE, "domianlinevisable");
		attribute.put(Constants.PR_RANGELINE_VISABLE, "rangelinevisable");
		attribute.put(Constants.PR_ZERORANGELINE_VISABLE, "zerorangelinevisable");
		attribute.put(Constants.PR_RANGEAXIS_UNITINCREMENT, "rangeaxisunitincrement");
		attribute.put(Constants.PR_RANGEAXIS_MINUNIT, "rangeaxisminunit");
		attribute.put(Constants.PR_RANGEAXIS_MAXUNIT, "rangeaxismaxunit");
		attribute.put(Constants.PR_3DENABLE, "enable3d");
		attribute.put(Constants.PR_3DDEPNESS, "depness3d");
		attribute.put(Constants.PR_LENGEND_LABEL_VISABLE, "lengendlabelvisable");
		attribute.put(Constants.PR_LENGEND_LABEL_LOCATION, "lengendlabellocation");
		attribute.put(Constants.PR_LENGEND_LABEL_FONTFAMILY, "lengendlabelfontfamily");
		attribute.put(Constants.PR_LENGEND_LABEL_FONTSTYLE, "lengendlabelfontstyle");
		attribute.put(Constants.PR_LENGEND_LABEL_FONTSIZE, "lengendlabelfontsize");
		attribute.put(Constants.PR_LENGEND_LABEL_COLOR, "lengendlabelcolor");
		attribute.put(Constants.PR_LENGEND_LABLE_ALIGNMENT, "lengendlablealignment");
		attribute.put(Constants.PR_VALUE_LABLEVISABLE, "valuelablevisable");
		attribute.put(Constants.PR_CHART_VALUE_FONTFAMILY, "chartvaluefontfamily");
		attribute.put(Constants.PR_CHART_VALUE_FONTSTYLE, "chartvaluefontstyle");
		attribute.put(Constants.PR_CHART_VALUE_FONTSIZE, "chartvaluefontsize");
		attribute.put(Constants.PR_CHART_VALUE_COLOR, "chartvaluecolor");
		attribute.put(Constants.PR_CHART_VALUE_OFFSET, "chartvalueoffset");
		attribute.put(Constants.PR_ZEROVALUE_VISABLE, "zerovaluevisable");
		attribute.put(Constants.PR_NULLVALUE_VISABLE, "nullvaluevisable");
		attribute.put(Constants.PR_PIECHART_STARTANGLE, "piechartstartangle");
		attribute.put(Constants.PR_PIECHART_DIRECTION, "piechartdirection");
		attribute.put(Constants.PR_PERCENTVALUE_VISABLE, "percentvaluevisable");
		attribute.put(Constants.PR_PIECHARTLENGENDLABEL_VISABLE, "piechartlengendlabelvisable");
		attribute.put(Constants.PR_PIE_FACT_VALUE_VISABLE, "piefactvaluevisable");

		// zq add end
	}
}
