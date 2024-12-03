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
 * @WSDAttribueKeyNameFactory.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.HashMap;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.io.wsd.attribute.ConditionItemCollectionWriter;
import com.wisii.wisedoc.io.wsd.attribute.GroupWriter;
import com.wisii.wisedoc.io.wsd.attribute.LogicalExpressionWriter;
import com.wisii.wisedoc.io.xsl.XSLAttributeNameWriter;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：WSDAttribueKeyNameFactory
 * 
 * 作者：zhangqiang 创建日期：2008-8-14
 */
public class WSDAttribueKeyNameFactory extends XSLAttributeNameWriter {
	private HashMap<String, Integer> keyattribute = new HashMap<String, Integer>();

	public WSDAttribueKeyNameFactory() {
		init();
	}

	private void init()
	{
		attribute.put(Constants.PR_ID, "itid");
		//编辑相关的属性
		attribute.put(Constants.PR_EDITTYPE, "edittype");
		attribute.put(Constants.PR_AUTHORITY, "authority");
		attribute.put(Constants.PR_ISRELOAD, "isreload");
		attribute.put(Constants.PR_APPEARANCE, "appearance");
		attribute.put(Constants.PR_EDIT_WIDTH, "editwidth");
		attribute.put(Constants.PR_EDIT_HEIGHT, "editheight");
		attribute.put(Constants.PR_HINT, "hint");
		attribute.put(Constants.PR_DEFAULT_VALUE, "defaultvalue");
		attribute.put(Constants.PR_ONBLUR, "onblur");
		attribute.put(Constants.PR_ONSELECTED, "onselected");
		attribute.put(Constants.PR_ONKEYPRESS, "onkeypress");
		attribute.put(Constants.PR_ONKEYDOWN, "onkeydown");
		attribute.put(Constants.PR_ONKEYUP, "onkeyup");
		attribute.put(Constants.PR_ONCHANGE, "onchange");
		attribute.put(Constants.PR_ONCLICK, "onclick");
		attribute.put(Constants.PR_ONEDIT, "onedit");
		attribute.put(Constants.PR_ONRESULT, "onresult");
		attribute.put(Constants.PR_INPUT_TYPE, "inputtype");
		attribute.put(Constants.PR_INPUT_MULTILINE, "inputsize");
		attribute.put(Constants.PR_INPUT_WRAP, "inputwrap");
		attribute.put(Constants.PR_INPUT_FILTER, "inputfilter");
		attribute.put(Constants.PR_INPUT_FILTERMSG, "inputfiltermsg");
		attribute.put(Constants.PR_DATE_TYPE, "datetype");
		attribute.put(Constants.PR_DATE_FORMAT, "dateformat");
		attribute.put(Constants.PR_RADIO_CHECK_VALUE, "radiocheckvalue");
		attribute.put(Constants.PR_CHECKBOX_UNSELECT_VALUE, "checkunselectvalue");
		attribute.put(Constants.PR_CHECKBOX_BOXSTYLE,"checkboxstyle");
		attribute.put(Constants.PR_CHECKBOX_TICKMARK,"checkboxtickmark");
		attribute.put(Constants.PR_RADIO_CHECK_CHECKED, "radiocheckchecked");
		attribute.put(Constants.PR_SELECT_TYPE,"selecttype");
		attribute.put(Constants.PR_SELECT_MULTIPLE, "selectmultiple");
		attribute.put(Constants.PR_SELECT_LINES, "selectlines");
		attribute.put(Constants.PR_SELECT_ISEDIT, "selectisedit");
		attribute.put(Constants.PR_SELECT_NEXT, "selectnext");
		attribute.put(Constants.PR_SELECT_INFO, "selectinfo");
		attribute.put(Constants.PR_POPUPBROWSER_INFO, "popupbrowserinfo");
		attribute.put(Constants.PR_SELECT_NAME, "selectname");
		attribute.put(Constants.PR_SELECT_SHOWLIST, "selectshowlist");
		attribute.put(Constants.PR_TRANSFORM_TABLE, "transformtable");
		attribute.put(Constants.PR_DATA_SOURCE, "datasource");
		attribute.put(Constants.PR_CONN_WITH,"connwith");
		attribute.put(Constants.PR_XPATH_POSITION,"xpathposition");
		attribute.put(Constants.PR_EDIT_BUTTON,"editbutton");
		attribute.put(Constants.PR_WORDARTTEXT_TYPE,"wordarttexttype");
		attribute.put(Constants.PR_WORDARTTEXT_PATHVISABLE, "wordarttextpathvisable");
		attribute.put(Constants.PR_WORDARTTEXT_ROTATION, "wordarttextrotation");
		attribute.put(Constants.PR_WORDARTTEXT_STARTPOSITION, "wordarttextstartpos");
		attribute.put(Constants.PR_WORDARTTEXT_LETTERSPACE, "wordarttextletspa");
		attribute.put(Constants.PR_WORDARTTEXT_CONTENT,"wordarttextcon");
		attribute.put(Constants.PR_CONTENT_TREAT,"contenttreat");
		attribute.put(Constants.PR_GROUP_REFERANCE,"groupreferance");
		attribute.put(Constants.PR_VIRTUAL_MASTER_NAME,"virtual-master-name");
		attribute.put(Constants.PR_ZIMOBAN_NAME,"zimobanname");
		attribute.put(Constants.PR_DBTYPE,"dbtype");
		attribute.put(Constants.PR_POSITION_NUMBER_TYPE,"position-number-format");
		keyattribute.put(FoXsltConstants.ABSOLUTE_POSITION,
				Constants.PR_ABSOLUTE_POSITION);
		keyattribute.put(FoXsltConstants.ACTIVE_STATE,
				Constants.PR_ACTIVE_STATE);
		keyattribute.put(FoXsltConstants.ALIGNMENT_ADJUST,
				Constants.PR_ALIGNMENT_ADJUST);
		keyattribute.put(FoXsltConstants.ALIGNMENT_BASELINE,
				Constants.PR_ALIGNMENT_BASELINE);
		keyattribute.put(FoXsltConstants.AUTO_RESTORE,
				Constants.PR_AUTO_RESTORE);
		keyattribute.put(FoXsltConstants.AZIMUTH, Constants.PR_AZIMUTH);
		keyattribute.put(FoXsltConstants.BACKGROUND, Constants.PR_BACKGROUND);
		keyattribute.put(FoXsltConstants.BACKGROUND_ATTACHMENT,
				Constants.PR_BACKGROUND_ATTACHMENT);
		keyattribute.put(FoXsltConstants.BACKGROUND_COLOR,
				Constants.PR_BACKGROUND_COLOR);
		keyattribute.put(FoXsltConstants.BACKGROUND_IMAGE,
				Constants.PR_BACKGROUND_IMAGE);
		keyattribute.put(FoXsltConstants.BACKGROUND_POSITION,
				Constants.PR_BACKGROUND_POSITION);
		keyattribute.put(FoXsltConstants.BACKGROUND_POSITION_HORIZONTAL,
				Constants.PR_BACKGROUND_POSITION_HORIZONTAL);
		keyattribute.put(FoXsltConstants.BACKGROUND_POSITION_VERTICAL,
				Constants.PR_BACKGROUND_POSITION_VERTICAL);
		keyattribute.put(FoXsltConstants.BACKGROUND_REPEAT,
				Constants.PR_BACKGROUND_REPEAT);
		keyattribute.put(FoXsltConstants.BASELINE_SHIFT,
				Constants.PR_BASELINE_SHIFT);
		keyattribute.put(FoXsltConstants.BLANK_OR_NOT_BLANK,
				Constants.PR_BLANK_OR_NOT_BLANK);
		keyattribute.put(FoXsltConstants.BORDER, Constants.PR_BORDER);
		keyattribute.put(FoXsltConstants.BORDER_AFTER_COLOR,
				Constants.PR_BORDER_AFTER_COLOR);
		keyattribute.put(FoXsltConstants.BORDER_AFTER_PRECEDENCE,
				Constants.PR_BORDER_AFTER_PRECEDENCE);
		keyattribute.put(FoXsltConstants.BORDER_AFTER_STYLE,
				Constants.PR_BORDER_AFTER_STYLE);
		keyattribute.put(FoXsltConstants.BORDER_AFTER_WIDTH,
				Constants.PR_BORDER_AFTER_WIDTH);
		keyattribute.put(FoXsltConstants.BORDER_BEFORE_COLOR,
				Constants.PR_BORDER_BEFORE_COLOR);
		keyattribute.put(FoXsltConstants.BORDER_BEFORE_PRECEDENCE,
				Constants.PR_BORDER_BEFORE_PRECEDENCE);
		keyattribute.put(FoXsltConstants.BORDER_BEFORE_STYLE,
				Constants.PR_BORDER_BEFORE_STYLE);
		keyattribute.put(FoXsltConstants.BORDER_BEFORE_WIDTH,
				Constants.PR_BORDER_BEFORE_WIDTH);
		keyattribute.put(FoXsltConstants.BORDER_BOTTOM,
				Constants.PR_BORDER_BOTTOM);
		keyattribute.put(FoXsltConstants.BORDER_BOTTOM_COLOR,
				Constants.PR_BORDER_BOTTOM_COLOR);
		keyattribute.put(FoXsltConstants.BORDER_BOTTOM_STYLE,
				Constants.PR_BORDER_BOTTOM_STYLE);
		keyattribute.put(FoXsltConstants.BORDER_BOTTOM_WIDTH,
				Constants.PR_BORDER_BOTTOM_WIDTH);
		keyattribute.put(FoXsltConstants.BORDER_COLLAPSE,
				Constants.PR_BORDER_COLLAPSE);
		keyattribute.put(FoXsltConstants.BORDER_END_COLOR,
				Constants.PR_BORDER_END_COLOR);
		keyattribute.put(FoXsltConstants.BORDER_END_PRECEDENCE,
				Constants.PR_BORDER_END_PRECEDENCE);
		keyattribute.put(FoXsltConstants.BORDER_END_STYLE,
				Constants.PR_BORDER_END_STYLE);
		keyattribute.put(FoXsltConstants.BORDER_END_WIDTH,
				Constants.PR_BORDER_END_WIDTH);
		keyattribute.put(FoXsltConstants.BORDER_LEFT, Constants.PR_BORDER_LEFT);
		keyattribute.put(FoXsltConstants.BORDER_LEFT_COLOR,
				Constants.PR_BORDER_LEFT_COLOR);
		keyattribute.put(FoXsltConstants.BORDER_LEFT_STYLE,
				Constants.PR_BORDER_LEFT_STYLE);
		keyattribute.put(FoXsltConstants.BORDER_LEFT_WIDTH,
				Constants.PR_BORDER_LEFT_WIDTH);
		keyattribute.put(FoXsltConstants.BORDER_RIGHT,
				Constants.PR_BORDER_RIGHT);
		keyattribute.put(FoXsltConstants.BORDER_RIGHT_COLOR,
				Constants.PR_BORDER_RIGHT_COLOR);
		keyattribute.put(FoXsltConstants.BORDER_RIGHT_STYLE,
				Constants.PR_BORDER_RIGHT_STYLE);
		keyattribute.put(FoXsltConstants.BORDER_RIGHT_WIDTH,
				Constants.PR_BORDER_RIGHT_WIDTH);
		keyattribute.put(FoXsltConstants.BORDER_SEPARATION,
				Constants.PR_BORDER_SEPARATION);
		keyattribute.put(FoXsltConstants.BORDER_SPACING,
				Constants.PR_BORDER_SPACING);
		keyattribute.put(FoXsltConstants.BORDER_START_COLOR,
				Constants.PR_BORDER_START_COLOR);
		keyattribute.put(FoXsltConstants.BORDER_START_PRECEDENCE,
				Constants.PR_BORDER_START_PRECEDENCE);
		keyattribute.put(FoXsltConstants.BORDER_START_STYLE,
				Constants.PR_BORDER_START_STYLE);
		keyattribute.put(FoXsltConstants.BORDER_START_WIDTH,
				Constants.PR_BORDER_START_WIDTH);
		keyattribute.put(FoXsltConstants.BORDER_STYLE,
				Constants.PR_BORDER_STYLE);
		keyattribute.put(FoXsltConstants.BORDER_TOP, Constants.PR_BORDER_TOP);
		keyattribute.put(FoXsltConstants.BORDER_TOP_COLOR,
				Constants.PR_BORDER_TOP_COLOR);
		keyattribute.put(FoXsltConstants.BORDER_TOP_STYLE,
				Constants.PR_BORDER_TOP_STYLE);
		keyattribute.put(FoXsltConstants.BORDER_TOP_WIDTH,
				Constants.PR_BORDER_TOP_WIDTH);
		keyattribute.put(FoXsltConstants.BORDER_WIDTH,
				Constants.PR_BORDER_WIDTH);
		keyattribute.put(FoXsltConstants.BOTTOM, Constants.PR_BOTTOM);
		keyattribute.put(FoXsltConstants.BREAK_AFTER, Constants.PR_BREAK_AFTER);
		keyattribute.put(FoXsltConstants.BREAK_BEFORE,
				Constants.PR_BREAK_BEFORE);
		keyattribute.put(FoXsltConstants.CAPTION_SIDE,
				Constants.PR_CAPTION_SIDE);
		keyattribute.put(FoXsltConstants.CASE_NAME, Constants.PR_CASE_NAME);
		keyattribute.put(FoXsltConstants.CASE_TITLE, Constants.PR_CASE_TITLE);
		keyattribute.put(FoXsltConstants.CHARACTER, Constants.PR_CHARACTER);
		keyattribute.put(FoXsltConstants.CLEAR, Constants.PR_CLEAR);
		keyattribute.put(FoXsltConstants.CLIP, Constants.PR_CLIP);
		keyattribute.put(FoXsltConstants.COLOR, Constants.PR_COLOR);
		keyattribute.put(FoXsltConstants.COLOR_PROFILE_NAME,
				Constants.PR_COLOR_PROFILE_NAME);
		keyattribute.put(FoXsltConstants.COLUMN_COUNT,
				Constants.PR_COLUMN_COUNT);
		keyattribute.put(FoXsltConstants.COLUMN_GAP, Constants.PR_COLUMN_GAP);
		keyattribute.put(FoXsltConstants.COLUMN_NUMBER,
				Constants.PR_COLUMN_NUMBER);
		keyattribute.put(FoXsltConstants.COLUMN_WIDTH,
				Constants.PR_COLUMN_WIDTH);
		keyattribute.put(FoXsltConstants.CONTENT_HEIGHT,
				Constants.PR_CONTENT_HEIGHT);
		keyattribute.put(FoXsltConstants.CONTENT_TYPE,
				Constants.PR_CONTENT_TYPE);
		keyattribute.put(FoXsltConstants.CONTENT_WIDTH,
				Constants.PR_CONTENT_WIDTH);
		keyattribute.put(FoXsltConstants.COUNTRY, Constants.PR_COUNTRY);
		keyattribute.put(FoXsltConstants.CUE, Constants.PR_CUE);
		keyattribute.put(FoXsltConstants.CUE_AFTER, Constants.PR_CUE_AFTER);
		keyattribute.put(FoXsltConstants.CUE_BEFORE, Constants.PR_CUE_BEFORE);
		keyattribute.put(FoXsltConstants.DESTINATION_PLACEMENT_OFFSET,
				Constants.PR_DESTINATION_PLACEMENT_OFFSET);
		keyattribute.put(FoXsltConstants.DIRECTION, Constants.PR_DIRECTION);
		keyattribute.put(FoXsltConstants.DISPLAY_ALIGN,
				Constants.PR_DISPLAY_ALIGN);
		keyattribute.put(FoXsltConstants.DOMINANT_BASELINE,
				Constants.PR_DOMINANT_BASELINE);
		keyattribute.put(FoXsltConstants.ELEVATION, Constants.PR_ELEVATION);
		keyattribute.put(FoXsltConstants.EMPTY_CELLS, Constants.PR_EMPTY_CELLS);
		keyattribute.put(FoXsltConstants.END_INDENT, Constants.PR_END_INDENT);
		keyattribute.put(FoXsltConstants.ENDS_ROW, Constants.PR_ENDS_ROW);
		keyattribute.put(FoXsltConstants.EXTENT, Constants.PR_EXTENT);
		keyattribute.put(FoXsltConstants.EXTERNAL_DESTINATION,
				Constants.PR_EXTERNAL_DESTINATION);
		keyattribute.put(FoXsltConstants.FLOAT, Constants.PR_FLOAT);
		keyattribute.put(FoXsltConstants.FLOW_NAME, Constants.PR_FLOW_NAME);
		keyattribute.put(FoXsltConstants.FONT, Constants.PR_FONT);
		keyattribute.put(FoXsltConstants.FONT_FAMILY, Constants.PR_FONT_FAMILY);
		keyattribute.put(FoXsltConstants.FONT_SELECTION_STRATEGY,
				Constants.PR_FONT_SELECTION_STRATEGY);
		keyattribute.put(FoXsltConstants.FONT_SIZE, Constants.PR_FONT_SIZE);
		keyattribute.put(FoXsltConstants.FONT_SIZE_ADJUST,
				Constants.PR_FONT_SIZE_ADJUST);
		keyattribute.put(FoXsltConstants.FONT_STRETCH,
				Constants.PR_FONT_STRETCH);
		keyattribute.put(FoXsltConstants.FONT_STYLE, Constants.PR_FONT_STYLE);
		keyattribute.put(FoXsltConstants.FONT_VARIANT,
				Constants.PR_FONT_VARIANT);
		keyattribute.put(FoXsltConstants.FONT_WEIGHT, Constants.PR_FONT_WEIGHT);
		keyattribute.put(FoXsltConstants.FORCE_PAGE_COUNT,
				Constants.PR_FORCE_PAGE_COUNT);
		keyattribute.put(FoXsltConstants.FORMAT, Constants.PR_FORMAT);
		keyattribute.put(FoXsltConstants.GLYPH_ORIENTATION_HORIZONTAL,
				Constants.PR_GLYPH_ORIENTATION_HORIZONTAL);
		keyattribute.put(FoXsltConstants.GLYPH_ORIENTATION_VERTICAL,
				Constants.PR_GLYPH_ORIENTATION_VERTICAL);
		keyattribute.put(FoXsltConstants.GROUPING_SEPARATOR,
				Constants.PR_GROUPING_SEPARATOR);
		keyattribute.put(FoXsltConstants.GROUPING_SIZE,
				Constants.PR_GROUPING_SIZE);
		keyattribute.put(FoXsltConstants.HEIGHT, Constants.PR_HEIGHT);
		keyattribute.put(FoXsltConstants.HYPHENATE, Constants.PR_HYPHENATE);
		keyattribute.put(FoXsltConstants.HYPHENATION_CHARACTER,
				Constants.PR_HYPHENATION_CHARACTER);
		keyattribute.put(FoXsltConstants.HYPHENATION_KEEP,
				Constants.PR_HYPHENATION_KEEP);
		keyattribute.put(FoXsltConstants.HYPHENATION_LADDER_COUNT,
				Constants.PR_HYPHENATION_LADDER_COUNT);
		keyattribute.put(FoXsltConstants.HYPHENATION_PUSH_CHARACTER_COUNT,
				Constants.PR_HYPHENATION_PUSH_CHARACTER_COUNT);
		keyattribute.put(FoXsltConstants.HYPHENATION_REMAIN_CHARACTER_COUNT,
				Constants.PR_HYPHENATION_REMAIN_CHARACTER_COUNT);
		keyattribute.put("itid", Constants.PR_ID);
		keyattribute.put(FoXsltConstants.INDICATE_DESTINATION,
				Constants.PR_INDICATE_DESTINATION);
		keyattribute.put(FoXsltConstants.INITIAL_PAGE_NUMBER,
				Constants.PR_INITIAL_PAGE_NUMBER);
		keyattribute.put(FoXsltConstants.INTERNAL_DESTINATION,
				Constants.PR_INTERNAL_DESTINATION);
		keyattribute.put(FoXsltConstants.LANGUAGE, Constants.PR_LANGUAGE);
		keyattribute.put(FoXsltConstants.LAST_LINE_END_INDENT,
				Constants.PR_LAST_LINE_END_INDENT);
		keyattribute.put(FoXsltConstants.LEADER_ALIGNMENT,
				Constants.PR_LEADER_ALIGNMENT);
		keyattribute.put(FoXsltConstants.LEADER_LENGTH,
				Constants.PR_LEADER_LENGTH);
		keyattribute.put(FoXsltConstants.LEADER_PATTERN,
				Constants.PR_LEADER_PATTERN);
		keyattribute.put(FoXsltConstants.LEADER_PATTERN_WIDTH,
				Constants.PR_LEADER_PATTERN_WIDTH);
		keyattribute.put(FoXsltConstants.LEFT, Constants.PR_LEFT);
		keyattribute.put(FoXsltConstants.LETTER_SPACING,
				Constants.PR_LETTER_SPACING);
		keyattribute.put(FoXsltConstants.LETTER_VALUE,
				Constants.PR_LETTER_VALUE);
		keyattribute.put(FoXsltConstants.LINEFEED_TREATMENT,
				Constants.PR_LINEFEED_TREATMENT);
		keyattribute.put(FoXsltConstants.LINE_HEIGHT_SHIFT_ADJUSTMENT,
				Constants.PR_LINE_HEIGHT_SHIFT_ADJUSTMENT);
		keyattribute.put(FoXsltConstants.LINE_STACKING_STRATEGY,
				Constants.PR_LINE_STACKING_STRATEGY);
		keyattribute.put(FoXsltConstants.MARGIN, Constants.PR_MARGIN);
		keyattribute.put(FoXsltConstants.MARGIN_BOTTOM,
				Constants.PR_MARGIN_BOTTOM);
		keyattribute.put(FoXsltConstants.MARGIN_LEFT, Constants.PR_MARGIN_LEFT);
		keyattribute.put(FoXsltConstants.MARGIN_RIGHT,
				Constants.PR_MARGIN_RIGHT);
		keyattribute.put(FoXsltConstants.MARGIN_TOP, Constants.PR_MARGIN_TOP);
		keyattribute.put(FoXsltConstants.MARKER_CLASS_NAME,
				Constants.PR_MARKER_CLASS_NAME);
		keyattribute.put(FoXsltConstants.MASTER_NAME, Constants.PR_MASTER_NAME);
		keyattribute.put("virtual-master-name", Constants.PR_VIRTUAL_MASTER_NAME);
		keyattribute.put(FoXsltConstants.MASTER_REFERENCE,
				Constants.PR_MASTER_REFERENCE);
		keyattribute.put(FoXsltConstants.MAX_HEIGHT, Constants.PR_MAX_HEIGHT);
		keyattribute.put(FoXsltConstants.MAXIMUM_REPEATS,
				Constants.PR_MAXIMUM_REPEATS);
		keyattribute.put(FoXsltConstants.MAX_WIDTH, Constants.PR_MAX_WIDTH);
		keyattribute.put(FoXsltConstants.MEDIA_USAGE, Constants.PR_MEDIA_USAGE);
		keyattribute.put(FoXsltConstants.MIN_HEIGHT, Constants.PR_MIN_HEIGHT);
		keyattribute.put(FoXsltConstants.MIN_WIDTH, Constants.PR_MIN_WIDTH);
		keyattribute.put(FoXsltConstants.NUMBER_COLUMNS_REPEATED,
				Constants.PR_NUMBER_COLUMNS_REPEATED);
		keyattribute.put(FoXsltConstants.NUMBER_COLUMNS_SPANNED,
				Constants.PR_NUMBER_COLUMNS_SPANNED);
		keyattribute.put(FoXsltConstants.NUMBER_ROWS_SPANNED,
				Constants.PR_NUMBER_ROWS_SPANNED);
		keyattribute.put(FoXsltConstants.ODD_OR_EVEN, Constants.PR_ODD_OR_EVEN);
		keyattribute.put(FoXsltConstants.ORPHANS, Constants.PR_ORPHANS);
		keyattribute.put(FoXsltConstants.OVERFLOW, Constants.PR_OVERFLOW);
		keyattribute.put(FoXsltConstants.PADDING, Constants.PR_PADDING);
		keyattribute.put(FoXsltConstants.PADDING_AFTER,
				Constants.PR_PADDING_AFTER);
		keyattribute.put(FoXsltConstants.PADDING_BEFORE,
				Constants.PR_PADDING_BEFORE);
		keyattribute.put(FoXsltConstants.PADDING_BOTTOM,
				Constants.PR_PADDING_BOTTOM);
		keyattribute.put(FoXsltConstants.PADDING_END, Constants.PR_PADDING_END);
		keyattribute.put(FoXsltConstants.PADDING_LEFT,
				Constants.PR_PADDING_LEFT);
		keyattribute.put(FoXsltConstants.PADDING_RIGHT,
				Constants.PR_PADDING_RIGHT);
		keyattribute.put(FoXsltConstants.PADDING_START,
				Constants.PR_PADDING_START);
		keyattribute.put(FoXsltConstants.PADDING_TOP, Constants.PR_PADDING_TOP);
		keyattribute.put(FoXsltConstants.PAGE_BREAK_AFTER,
				Constants.PR_PAGE_BREAK_AFTER);
		keyattribute.put(FoXsltConstants.PAGE_BREAK_BEFORE,
				Constants.PR_PAGE_BREAK_BEFORE);
		keyattribute.put(FoXsltConstants.PAGE_BREAK_INSIDE,
				Constants.PR_PAGE_BREAK_INSIDE);
		keyattribute.put(FoXsltConstants.PAGE_HEIGHT, Constants.PR_PAGE_HEIGHT);
		keyattribute.put(FoXsltConstants.PAGE_POSITION,
				Constants.PR_PAGE_POSITION);
		keyattribute.put(FoXsltConstants.PAGE_WIDTH, Constants.PR_PAGE_WIDTH);
		keyattribute.put(FoXsltConstants.PAUSE, Constants.PR_PAUSE);
		keyattribute.put(FoXsltConstants.PAUSE_AFTER, Constants.PR_PAUSE_AFTER);
		keyattribute.put(FoXsltConstants.PAUSE_BEFORE,
				Constants.PR_PAUSE_BEFORE);
		keyattribute.put(FoXsltConstants.PITCH, Constants.PR_PITCH);
		keyattribute.put(FoXsltConstants.PITCH_RANGE, Constants.PR_PITCH_RANGE);
		keyattribute.put(FoXsltConstants.PLAY_DURING, Constants.PR_PLAY_DURING);
		keyattribute.put(FoXsltConstants.POSITION, Constants.PR_POSITION);
		keyattribute.put(FoXsltConstants.PRECEDENCE, Constants.PR_PRECEDENCE);
		keyattribute.put(FoXsltConstants.PROVISIONAL_DISTANCE_BETWEEN_STARTS,
				Constants.PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS);
		keyattribute.put(FoXsltConstants.PROVISIONAL_LABEL_SEPARATION,
				Constants.PR_PROVISIONAL_LABEL_SEPARATION);
		keyattribute.put(FoXsltConstants.REFERENCE_ORIENTATION,
				Constants.PR_REFERENCE_ORIENTATION);
		keyattribute.put(FoXsltConstants.REF_ID, Constants.PR_REF_ID);
		keyattribute.put(FoXsltConstants.REGION_NAME, Constants.PR_REGION_NAME);
		keyattribute.put(FoXsltConstants.RELATIVE_ALIGN,
				Constants.PR_RELATIVE_ALIGN);
		keyattribute.put(FoXsltConstants.RELATIVE_POSITION,
				Constants.PR_RELATIVE_POSITION);
		keyattribute.put(FoXsltConstants.RENDERING_INTENT,
				Constants.PR_RENDERING_INTENT);
		keyattribute.put(FoXsltConstants.RETRIEVE_BOUNDARY,
				Constants.PR_RETRIEVE_BOUNDARY);
		keyattribute.put(FoXsltConstants.RETRIEVE_CLASS_NAME,
				Constants.PR_RETRIEVE_CLASS_NAME);
		keyattribute.put(FoXsltConstants.RETRIEVE_POSITION,
				Constants.PR_RETRIEVE_POSITION);
		keyattribute.put(FoXsltConstants.RICHNESS, Constants.PR_RICHNESS);
		keyattribute.put(FoXsltConstants.RIGHT, Constants.PR_RIGHT);
		keyattribute.put(FoXsltConstants.ROLE, Constants.PR_ROLE);
		keyattribute.put(FoXsltConstants.RULE_STYLE, Constants.PR_RULE_STYLE);
		keyattribute.put(FoXsltConstants.RULE_THICKNESS,
				Constants.PR_RULE_THICKNESS);
		keyattribute.put(FoXsltConstants.SCALING, Constants.PR_SCALING);
		keyattribute.put(FoXsltConstants.SCALING_METHOD,
				Constants.PR_SCALING_METHOD);
		keyattribute.put(FoXsltConstants.SCORE_SPACES,
				Constants.PR_SCORE_SPACES);
		keyattribute.put(FoXsltConstants.SCRIPT, Constants.PR_SCRIPT);
		keyattribute.put(FoXsltConstants.SHOW_DESTINATION,
				Constants.PR_SHOW_DESTINATION);
		keyattribute.put(FoXsltConstants.SIZE, Constants.PR_SIZE);
		keyattribute.put(FoXsltConstants.SOURCE_DOCUMENT,
				Constants.PR_SOURCE_DOCUMENT);
		keyattribute.put(FoXsltConstants.SPAN, Constants.PR_SPAN);
		keyattribute.put(FoXsltConstants.SPEAK, Constants.PR_SPEAK);
		keyattribute.put(FoXsltConstants.SPEAK_HEADER,
				Constants.PR_SPEAK_HEADER);
		keyattribute.put(FoXsltConstants.SPEAK_NUMERAL,
				Constants.PR_SPEAK_NUMERAL);
		keyattribute.put(FoXsltConstants.SPEAK_PUNCTUATION,
				Constants.PR_SPEAK_PUNCTUATION);
		keyattribute.put(FoXsltConstants.SPEECH_RATE, Constants.PR_SPEECH_RATE);
		keyattribute.put(FoXsltConstants.SRC, Constants.PR_SRC);
		keyattribute.put(FoXsltConstants.SRC_TYPE, Constants.PR_SRC_TYPE);
		keyattribute.put(FoXsltConstants.ALPHA, Constants.PR_APHLA);
		keyattribute.put(FoXsltConstants.START_INDENT,
				Constants.PR_START_INDENT);
		keyattribute.put(FoXsltConstants.STARTING_STATE,
				Constants.PR_STARTING_STATE);
		keyattribute.put(FoXsltConstants.STARTS_ROW, Constants.PR_STARTS_ROW);
		keyattribute.put(FoXsltConstants.STRESS, Constants.PR_STRESS);
		keyattribute.put(FoXsltConstants.SUPPRESS_AT_LINE_BREAK,
				Constants.PR_SUPPRESS_AT_LINE_BREAK);
		keyattribute.put(FoXsltConstants.SWITCH_TO, Constants.PR_SWITCH_TO);
		keyattribute.put(FoXsltConstants.TABLE_LAYOUT,
				Constants.PR_TABLE_LAYOUT);
		keyattribute.put(FoXsltConstants.TABLE_OMIT_FOOTER_AT_BREAK,
				Constants.PR_TABLE_OMIT_FOOTER_AT_BREAK);
		keyattribute.put(FoXsltConstants.TABLE_OMIT_HEADER_AT_BREAK,
				Constants.PR_TABLE_OMIT_HEADER_AT_BREAK);
		keyattribute.put(FoXsltConstants.TARGET_PRESENTATION_CONTEXT,
				Constants.PR_TARGET_PRESENTATION_CONTEXT);
		keyattribute.put(FoXsltConstants.TARGET_PROCESSING_CONTEXT,
				Constants.PR_TARGET_PROCESSING_CONTEXT);
		keyattribute.put(FoXsltConstants.TARGET_STYLESHEET,
				Constants.PR_TARGET_STYLESHEET);
		keyattribute.put(FoXsltConstants.TEXT_ALIGN, Constants.PR_TEXT_ALIGN);
		keyattribute.put(FoXsltConstants.TEXT_ALIGN_LAST,
				Constants.PR_TEXT_ALIGN_LAST);
		keyattribute.put(FoXsltConstants.TEXT_ALTITUDE,
				Constants.PR_TEXT_ALTITUDE);
		keyattribute.put(FoXsltConstants.TEXT_DECORATION,
				Constants.PR_TEXT_DECORATION);
		keyattribute.put(FoXsltConstants.TEXT_DEPTH, Constants.PR_TEXT_DEPTH);
		keyattribute.put(FoXsltConstants.TEXT_INDENT, Constants.PR_TEXT_INDENT);
		keyattribute.put(FoXsltConstants.TEXT_SHADOW, Constants.PR_TEXT_SHADOW);
		keyattribute.put(FoXsltConstants.TEXT_TRANSFORM,
				Constants.PR_TEXT_TRANSFORM);
		keyattribute.put(FoXsltConstants.TOP, Constants.PR_TOP);
		keyattribute.put(FoXsltConstants.TREAT_AS_WORD_SPACE,
				Constants.PR_TREAT_AS_WORD_SPACE);
		keyattribute.put(FoXsltConstants.UNICODE_BIDI,
				Constants.PR_UNICODE_BIDI);
		keyattribute.put(FoXsltConstants.VERTICAL_ALIGN,
				Constants.PR_VERTICAL_ALIGN);
		keyattribute.put(FoXsltConstants.VISIBILITY, Constants.PR_VISIBILITY);
		keyattribute.put(FoXsltConstants.VOICE_FAMILY,
				Constants.PR_VOICE_FAMILY);
		keyattribute.put(FoXsltConstants.VOLUME, Constants.PR_VOLUME);
		keyattribute.put(FoXsltConstants.WHITE_SPACE, Constants.PR_WHITE_SPACE);
		keyattribute.put(FoXsltConstants.WHITE_SPACE_COLLAPSE,
				Constants.PR_WHITE_SPACE_COLLAPSE);
		keyattribute.put(FoXsltConstants.WHITE_SPACE_TREATMENT,
				Constants.PR_WHITE_SPACE_TREATMENT);
		keyattribute.put(FoXsltConstants.WIDOWS, Constants.PR_WIDOWS);
		keyattribute.put(FoXsltConstants.WIDTH, Constants.PR_WIDTH);
		keyattribute.put(FoXsltConstants.WORD_SPACING,
				Constants.PR_WORD_SPACING);
		keyattribute.put(FoXsltConstants.WRAP_OPTION, Constants.PR_WRAP_OPTION);
		keyattribute.put(FoXsltConstants.WRITING_MODE,
				Constants.PR_WRITING_MODE);
		keyattribute.put(FoXsltConstants.XML_LANG, Constants.PR_XML_LANG);
		keyattribute.put(FoXsltConstants.Z_INDEX, Constants.PR_Z_INDEX);
		keyattribute.put(FoXsltConstants.INTRUSION_DISPLACE,
				Constants.PR_INTRUSION_DISPLACE);
		keyattribute.put(FoXsltConstants.INDEX_CLASS, Constants.PR_INDEX_CLASS);
		keyattribute.put(FoXsltConstants.INDEX_KEY, Constants.PR_INDEX_KEY);
		keyattribute.put(FoXsltConstants.GRAPHIC_LAYER,
				Constants.PR_GRAPHIC_LAYER);
		keyattribute.put(FoXsltConstants.BACKGROUNDIMAGE_LAYER,
				Constants.PR_BACKGROUNDGRAPHIC_LAYER);
		keyattribute.put("hanging_indent", Constants.PR_HANGING_INDENT);
		keyattribute.put(FoXsltConstants.KEEP_TOGETHER,
				Constants.PR_KEEP_TOGETHER);
		keyattribute.put(FoXsltConstants.KEEP_WITH_NEXT,
				Constants.PR_KEEP_WITH_NEXT);
		keyattribute.put(FoXsltConstants.KEEP_WITH_PREVIOUS,
				Constants.PR_KEEP_WITH_PREVIOUS);
		keyattribute.put(FoXsltConstants.SPACE_AFTER, Constants.PR_SPACE_AFTER);
		keyattribute.put(FoXsltConstants.SPACE_BEFORE,
				Constants.PR_SPACE_BEFORE);
		keyattribute.put(FoXsltConstants.INLINE_PROGRESSION_DIMENSION,
				Constants.PR_INLINE_PROGRESSION_DIMENSION);
		keyattribute.put(FoXsltConstants.BLOCK_PROGRESSION_DIMENSION,
				Constants.PR_BLOCK_PROGRESSION_DIMENSION);
		keyattribute.put(FoXsltConstants.LINE_HEIGHT, Constants.PR_LINE_HEIGHT);
		keyattribute.put(FoXsltConstants.SPACE_END, Constants.PR_SPACE_END);
		keyattribute.put(FoXsltConstants.SPACE_START, Constants.PR_SPACE_START);

		keyattribute.put("x-block-progression-unit",
				Constants.PR_X_BLOCK_PROGRESSION_UNIT);
		keyattribute.put(FoXsltConstants.EDITMODE, Constants.PR_EDITMODE);
		keyattribute.put(FoXsltConstants.XPATH, Constants.PR_XPATH);
		keyattribute.put("translataurl", Constants.PR_TRANSLATEURL);
		keyattribute.put(FoXsltConstants.HIDE, Constants.PR_HIDENAME);
		keyattribute.put(FoXsltConstants.SIMPLE_PAGE_MASTER,
				Constants.PR_SIMPLE_PAGE_MASTER);
		keyattribute.put(FoXsltConstants.PAGESEQUENCEMASTER,
				Constants.PR_PAGE_SEQUENCE_MASTER);
		keyattribute.put("barcode-type", Constants.PR_BARCODE_TYPE);
		keyattribute.put("barcode-content", Constants.PR_BARCODE_CONTENT);
		keyattribute.put("barcode-value", Constants.PR_BARCODE_VALUE);
		keyattribute.put("barcode-height", Constants.PR_BARCODE_HEIGHT);
		keyattribute.put("barcode-module", Constants.PR_BARCODE_MODULE);
		keyattribute.put("barcode-font-height",
				Constants.PR_BARCODE_FONT_HEIGHT);
		keyattribute.put("barcode-font-family",
				Constants.PR_BARCODE_FONT_FAMILY);
		keyattribute.put("barcode-quiet-horizontal",
				Constants.PR_BARCODE_QUIET_HORIZONTAL);
		keyattribute.put("barcode-quiet-vertical",
				Constants.PR_BARCODE_QUIET_VERTICAL);
		keyattribute.put("barcode-orientation",
				Constants.PR_BARCODE_ORIENTATION);
		keyattribute.put("barcode-addchecksum",
				Constants.PR_BARCODE_ADDCHECKSUM);
		keyattribute.put("barcode-wide-to-narrow",
				Constants.PR_BARCODE_WIDE_TO_NARROW);
		keyattribute.put("barcode-text-char-space",
				Constants.PR_BARCODE_TEXT_CHAR_SPACE);
		keyattribute.put("barcode-string", Constants.PR_BARCODE_STRING);
		keyattribute.put("barcode-print-text", Constants.PR_BARCODE_PRINT_TEXT);
		keyattribute.put("barcode-text-block", Constants.PR_BARCODE_TEXT_BLOCK);
		keyattribute.put("barcode-code-type", Constants.PR_BARCODE_CODE_TYPE);
		keyattribute.put("barcode-sunset", Constants.PR_BARCODE_SUBSET);
		keyattribute.put("barcode-makeucc", Constants.PR_BARCODE_MAKEUCC);
		keyattribute.put("datetime-format", Constants.PR_DATETIMEFORMAT);
		keyattribute.put("number-format", Constants.PR_NUMBERFORMAT);
		keyattribute.put("position-number-format",Constants.PR_POSITION_NUMBER_TYPE);
		keyattribute.put("isshowtotal", Constants.PR_ISSHOWTOTAL);
		keyattribute.put("numbertext-type", Constants.PR_NUMBERTEXT_TYPE);
		keyattribute.put("errordataprocess", Constants.PR_LAWLESSDATAPROCESS);
		keyattribute.put("endofall", Constants.PR_ENDOFALL);
		keyattribute.put(LogicalExpressionWriter.CONDITIONTAG,
				Constants.PR_CONDTION);
		keyattribute.put(GroupWriter.GROUPTAG, Constants.PR_GROUP);
		keyattribute.put(FoXsltConstants.X, Constants.PR_X);
		keyattribute.put(FoXsltConstants.Y, Constants.PR_Y);
		keyattribute.put(FoXsltConstants.X1, Constants.PR_X1);
		keyattribute.put(FoXsltConstants.Y1, Constants.PR_Y1);
		keyattribute.put(FoXsltConstants.X2, Constants.PR_X2);
		keyattribute.put(FoXsltConstants.Y2, Constants.PR_Y2);
		keyattribute.put(FoXsltConstants.CX, Constants.PR_CX);
		keyattribute.put(FoXsltConstants.CY, Constants.PR_CY);
		keyattribute.put(FoXsltConstants.R, Constants.PR_R);
		keyattribute.put(FoXsltConstants.RX, Constants.PR_RX);
		keyattribute.put(FoXsltConstants.RY, Constants.PR_RY);
		keyattribute.put(FoXsltConstants.FILL, Constants.PR_FILL);
		keyattribute.put(FoXsltConstants.STROKE_WIDTH,
				Constants.PR_STROKE_WIDTH);
		keyattribute.put(FoXsltConstants.STROKE_DASHARRAY,
				Constants.PR_SVG_LINE_TYPE);
		keyattribute.put(FoXsltConstants.STROKE_LINEJOIN,
				Constants.PR_SVG_STROKE_LINEJOIN);
		keyattribute.put("linestarttype", Constants.PR_SVG_ARROW_START_TYPE);
		keyattribute.put("lineendtype", Constants.PR_SVG_ARROW_END_TYPE);
		keyattribute.put("svg-orientation", Constants.PR_SVG_ORIENTATION);
		keyattribute.put("svg-text", Constants.PR_SVG_TEXT_CONTENT);
		keyattribute.put(ConditionItemCollectionWriter.DYNAMICSTYLETAG,
				Constants.PR_DYNAMIC_STYLE);
		keyattribute.put("paragraphstyle", Constants.PR_BLOCK_STYLE);
		keyattribute.put("parastylecollection",
				Constants.PR_BLOCK_STYLE_COLLECTION);
		// 目录相关的属性名
		keyattribute.put("paragraphlevel", Constants.PR_BLOCK_LEVEL);
		keyattribute.put("bookmarktitle", Constants.PR_BOOKMARK_TITLE);
		keyattribute.put("blockrefnumber", Constants.PR_BLOCK_REF_NUMBER);
		keyattribute.put("blockrefrightalign",
				Constants.PR_BLOCK_REF_RIGHT_ALIGN);
		keyattribute
				.put("blockrefshoelevel", Constants.PR_BLOCK_REF_SHOW_LEVEL);
		keyattribute.put("blockrefstyles", Constants.PR_BLOCK_REF_STYLES);
		keyattribute.put("barcodeeclevel", Constants.PR_BARCODE_EC_LEVEL);
		keyattribute.put("barcodecolumns", Constants.PR_BARCODE_COLUMNS);
		keyattribute.put("barcodemincolumns", Constants.PR_BARCODE_MIN_COLUMNS);
		keyattribute.put("barcodemaxcolumns", Constants.PR_BARCODE_MAX_COLUMNS);
		keyattribute.put("barcodeminrows", Constants.PR_BARCODE_MIN_ROWS);
		keyattribute.put("barcodemaxrows", Constants.PR_BARCODE_MAX_ROWS);
		// 统计图相关属性名
		keyattribute.put("charttype", Constants.PR_CHART_TYPE);
		keyattribute.put("title", Constants.PR_TITLE);
		keyattribute.put("titlefontfamily", Constants.PR_TITLE_FONTFAMILY);
		keyattribute.put("titlefontstyle", Constants.PR_TITLE_FONTSTYLE);
		keyattribute.put("titlefontsize", Constants.PR_TITLE_FONTSIZE);
		keyattribute.put("titlecolor", Constants.PR_TITLE_COLOR);
		keyattribute.put("titlealignment", Constants.PR_TITLE_ALIGNMENT);
		keyattribute.put("backgroundimagealaph",
				Constants.PR_BACKGROUNDIMAGE_ALAPH);
		keyattribute.put("foregroundalaph", Constants.PR_FOREGROUND_ALAPH);
		keyattribute.put("valuecount", Constants.PR_VALUE_COUNT);
		keyattribute.put("seriescount", Constants.PR_SERIES_COUNT);
		keyattribute.put("valuecolor", Constants.PR_VALUE_COLOR);
		keyattribute.put("valuelabel", Constants.PR_VALUE_LABEL);
		keyattribute.put("valuelabelfontfamily",
				Constants.PR_VALUE_LABEL_FONTFAMILY);
		keyattribute.put("valuelabelfontstyle",
				Constants.PR_VALUE_LABEL_FONTSTYLE);
		keyattribute.put("valuelabelfontsize",
				Constants.PR_VALUE_LABEL_FONTSIZE);
		keyattribute.put("valuelabelcolor", Constants.PR_VALUE_LABEL_COLOR);
		keyattribute.put("backgroundimagealaph",
				Constants.PR_BACKGROUNDIMAGE_ALAPH);
		keyattribute.put("foregroundalaph", Constants.PR_FOREGROUND_ALAPH);
		keyattribute.put("valuecount", Constants.PR_VALUE_COUNT);
		keyattribute.put("seriescount", Constants.PR_SERIES_COUNT);
		keyattribute.put("valuecolor", Constants.PR_VALUE_COLOR);
		keyattribute.put("valuelabel", Constants.PR_VALUE_LABEL);
		keyattribute.put("valuelabelfontfamily",
				Constants.PR_VALUE_LABEL_FONTFAMILY);
		keyattribute.put("valuelabelfontstyle",
				Constants.PR_VALUE_LABEL_FONTSTYLE);
		keyattribute.put("valuelabelfontsize",
				Constants.PR_VALUE_LABEL_FONTSIZE);
		keyattribute.put("valuelabelcolor", Constants.PR_VALUE_LABEL_COLOR);
		keyattribute.put("seriesvalue", Constants.PR_SERIES_VALUE);
		keyattribute.put("serieslabel", Constants.PR_SERIES_LABEL);
		keyattribute.put("serieslabelfontfamily",
				Constants.PR_SERIES_LABEL_FONTFAMILY);
		keyattribute.put("serieslabelfontstyle",
				Constants.PR_SERIES_LABEL_FONTSTYLE);
		keyattribute.put("serieslabelfontsize",
				Constants.PR_SERIES_LABEL_FONTSIZE);
		keyattribute.put("serieslabelcolor", Constants.PR_SERIES_LABEL_COLOR);
		keyattribute.put("serieslabelorientation",
				Constants.PR_SERIES_LABEL_ORIENTATION);
		keyattribute.put("domainaxislabel", Constants.PR_DOMAINAXIS_LABEL);
		keyattribute.put("rangeaxislabel", Constants.PR_RANGEAXIS_LABEL);
		keyattribute.put("domainaxislabelfontfamily",
				Constants.PR_DOMAINAXIS_LABEL_FONTFAMILY);
		keyattribute.put("domainaxislabelfontstyle",
				Constants.PR_DOMAINAXIS_LABEL_FONTSTYLE);
		keyattribute.put("domainaxislabelfontsize",
				Constants.PR_DOMAINAXIS_LABEL_FONTSIZE);
		keyattribute.put("domainaxislabelcolor",
				Constants.PR_DOMAINAXIS_LABEL_COLOR);
		keyattribute.put("domainaxislabelalignment",
				Constants.PR_DOMAINAXIS_LABEL_ALIGNMENT);
		keyattribute.put("rangeaxislabelfontfamily",
				Constants.PR_RANGEAXIS_LABEL_FONTFAMILY);
		keyattribute.put("rangeaxislabelfontstyle",
				Constants.PR_RANGEAXIS_LABEL_FONTSTYLE);
		keyattribute.put("rangeaxislabelfontsize",
				Constants.PR_RANGEAXIS_LABEL_FONTSIZE);
		keyattribute.put("rangeaxislabelcolor",
				Constants.PR_RANGEAXIS_LABEL_COLOR);
		keyattribute.put("rangeaxislabelalignment",
				Constants.PR_RANGEAXIS_LABEL_ALIGNMENT);
		keyattribute.put("chartorientation", Constants.PR_CHART_ORIENTATION);
		keyattribute
				.put("rangeaxisprecision", Constants.PR_RANGEAXIS_PRECISION);
		keyattribute.put("domianlinevisable", Constants.PR_DOMIANLINE_VISABLE);
		keyattribute.put("rangelinevisable", Constants.PR_RANGELINE_VISABLE);
		keyattribute.put("zerorangelinevisable",
				Constants.PR_ZERORANGELINE_VISABLE);
		keyattribute.put("rangeaxisunitincrement",
				Constants.PR_RANGEAXIS_UNITINCREMENT);
		keyattribute.put("rangeaxisminunit", Constants.PR_RANGEAXIS_MINUNIT);
		keyattribute.put("rangeaxismaxunit", Constants.PR_RANGEAXIS_MAXUNIT);
		keyattribute.put("enable3d", Constants.PR_3DENABLE);
		keyattribute.put("depness3d", Constants.PR_3DDEPNESS);
		keyattribute.put("lengendlabelvisable",
				Constants.PR_LENGEND_LABEL_VISABLE);
		keyattribute.put("lengendlabellocation",
				Constants.PR_LENGEND_LABEL_LOCATION);
		keyattribute.put("lengendlabelfontfamily",
				Constants.PR_LENGEND_LABEL_FONTFAMILY);
		keyattribute.put("lengendlabelfontstyle",
				Constants.PR_LENGEND_LABEL_FONTSTYLE);
		keyattribute.put("lengendlabelfontsize",
				Constants.PR_LENGEND_LABEL_FONTSIZE);
		keyattribute.put("lengendlabelcolor", Constants.PR_LENGEND_LABEL_COLOR);
		keyattribute.put("lengendlablealignment",
				Constants.PR_LENGEND_LABLE_ALIGNMENT);
		keyattribute.put("valuelablevisable", Constants.PR_VALUE_LABLEVISABLE);
		keyattribute.put("chartvaluefontfamily",
				Constants.PR_CHART_VALUE_FONTFAMILY);
		keyattribute.put("chartvaluefontstyle",
				Constants.PR_CHART_VALUE_FONTSTYLE);
		keyattribute.put("chartvaluefontsize",
				Constants.PR_CHART_VALUE_FONTSIZE);
		keyattribute.put("chartvaluecolor", Constants.PR_CHART_VALUE_COLOR);
		keyattribute.put("chartvalueoffset", Constants.PR_CHART_VALUE_OFFSET);
		keyattribute.put("zerovaluevisable", Constants.PR_ZEROVALUE_VISABLE);
		keyattribute.put("nullvaluevisable", Constants.PR_NULLVALUE_VISABLE);
		keyattribute
				.put("piechartstartangle", Constants.PR_PIECHART_STARTANGLE);
		keyattribute.put("piechartdirection", Constants.PR_PIECHART_DIRECTION);
		keyattribute.put("percentvaluevisable",
				Constants.PR_PERCENTVALUE_VISABLE);
		keyattribute.put("piechartlengendlabelvisable",
				Constants.PR_PIECHARTLENGENDLABEL_VISABLE);
		keyattribute.put("piefactvaluevisable",
				Constants.PR_PIE_FACT_VALUE_VISABLE);
		//编辑相关的属性
		keyattribute.put("edittype",Constants.PR_EDITTYPE);
		keyattribute.put("authority",Constants.PR_AUTHORITY);
		keyattribute.put("isreload",Constants.PR_ISRELOAD);
		keyattribute.put("appearance",Constants.PR_APPEARANCE);
		keyattribute.put("editwidth",Constants.PR_EDIT_WIDTH);
		keyattribute.put("editheight",Constants.PR_EDIT_HEIGHT);
		keyattribute.put("hint",Constants.PR_HINT);
		keyattribute.put("defaultvalue",Constants.PR_DEFAULT_VALUE);
		keyattribute.put("onblur",Constants.PR_ONBLUR);
		keyattribute.put("onselected",Constants.PR_ONSELECTED);
		keyattribute.put("onkeypress",Constants.PR_ONKEYPRESS);
		keyattribute.put("onkeydown",Constants.PR_ONKEYDOWN);
		keyattribute.put("onkeyup",Constants.PR_ONKEYUP);
		keyattribute.put("onchange",Constants.PR_ONCHANGE);
		keyattribute.put("onclick",Constants.PR_ONCLICK);
		keyattribute.put("onedit",Constants.PR_ONEDIT);
		keyattribute.put("onresult",Constants.PR_ONRESULT);
		keyattribute.put("inputtype",Constants.PR_INPUT_TYPE);
		keyattribute.put("inputsize",Constants.PR_INPUT_MULTILINE);
		keyattribute.put("inputwrap",Constants.PR_INPUT_WRAP);
		keyattribute.put("inputfilter",Constants.PR_INPUT_FILTER);
		keyattribute.put("inputfiltermsg",Constants.PR_INPUT_FILTERMSG);
		keyattribute.put("datetype",Constants.PR_DATE_TYPE);
		keyattribute.put("dateformat",Constants.PR_DATE_FORMAT);
		keyattribute.put("radiocheckvalue",Constants.PR_RADIO_CHECK_VALUE);
		keyattribute.put("checkunselectvalue",Constants.PR_CHECKBOX_UNSELECT_VALUE);
		keyattribute.put("checkboxstyle",Constants.PR_CHECKBOX_BOXSTYLE);
		keyattribute.put("checkboxtickmark",Constants.PR_CHECKBOX_TICKMARK);
		keyattribute.put("selecttype",Constants.PR_SELECT_TYPE);
		keyattribute.put("radiocheckchecked",Constants.PR_RADIO_CHECK_CHECKED);
		keyattribute.put("selectmultiple",Constants.PR_SELECT_MULTIPLE);
		keyattribute.put("selectlines",Constants.PR_SELECT_LINES);
		keyattribute.put("selectisedit",Constants.PR_SELECT_ISEDIT);
		keyattribute.put("selectnext",Constants.PR_SELECT_NEXT);
		keyattribute.put("selectinfo",Constants.PR_SELECT_INFO);
		keyattribute.put("popupbrowserinfo",Constants.PR_POPUPBROWSER_INFO);
		keyattribute.put("selectname",Constants.PR_SELECT_NAME);
		keyattribute.put("selectshowlist",Constants.PR_SELECT_SHOWLIST);
		keyattribute.put("transformtable",Constants.PR_TRANSFORM_TABLE);
		keyattribute.put("datasource",Constants.PR_DATA_SOURCE);
		keyattribute.put("connwith",Constants.PR_CONN_WITH);
		keyattribute.put("xpathposition",Constants.PR_XPATH_POSITION);
		keyattribute.put("editbutton",Constants.PR_EDIT_BUTTON);
		keyattribute.put("wordarttexttype",Constants.PR_WORDARTTEXT_TYPE);
		keyattribute.put("wordarttextpathvisable",Constants.PR_WORDARTTEXT_PATHVISABLE);
		keyattribute.put("wordarttextrotation",Constants.PR_WORDARTTEXT_ROTATION);
		keyattribute.put("wordarttextstartpos",Constants.PR_WORDARTTEXT_STARTPOSITION);
		keyattribute.put("wordarttextletspa",Constants.PR_WORDARTTEXT_LETTERSPACE);
		keyattribute.put("wordarttextcon",Constants.PR_WORDARTTEXT_CONTENT);
		keyattribute.put("contenttreat",Constants.PR_CONTENT_TREAT);
		keyattribute.put("groupreferance",Constants.PR_GROUP_REFERANCE);
		keyattribute.put("zimobanname",Constants.PR_ZIMOBAN_NAME);
		keyattribute.put("dbtype",Constants.PR_DBTYPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.AttributeKeyNameFactory#getKey(java.lang.String)
	 */
	public int getKey(String name)
	{
		// 如果找到对应的属性，则返回，否则返回-1
		if (keyattribute.containsKey(name))
		{
			return keyattribute.get(name);
		} else
		{
			return -1;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeKeyNameFactory#getKeyName(int)
	 */
	public String getKeyName(int key) {
		String name = super.getKeyName(key);
		return name;
	}

}
