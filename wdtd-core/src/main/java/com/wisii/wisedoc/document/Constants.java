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

package com.wisii.wisedoc.document;

/**
 * Definition of constants used throughout FOV. There are sets of constants
 * describing:
 * <ul>
 * <li>Input and output formats</li>
 * <li>Formatting objects</li>
 * <li>Formatting properties</li>
 * <li>Enumerated values used in formatting properties</li>
 * </ul>
 */
public interface Constants
{

	/* 背景图片默认值none 【添加】 by 李晓光 2008-09-03 */
	String DEFAULT_BACKGROUND_IMAGE = "none";

	/* 配置文件中获得单位【CM、MM】的KEY */
	String DEFAULT_UNIT = "DEFAULT_UNIT";

	/* 数据处理时因子 【添加】 by 李晓光 2008-09-09 */
	/** 数据处理时因子1000F */
	float PRECISION = 1000F;

	/** not set */
	int NOT_SET = 0;

	// element constants
	/** FObj base class */
	int FO_UNKNOWN_NODE = 0;

	/** FO element constant */
	int FO_BASIC_LINK = 1;

	/** FO element constant */
	int FO_BIDI_OVERRIDE = 2;

	/** FO element constant */
	int FO_BLOCK = 3;

	/** FO element constant */
	int FO_BLOCK_CONTAINER = 4;

	/** FO element constant */
	int FO_CHARACTER = 5;

	/** FO element constant */
	int FO_COLOR_PROFILE = 6;

	/** FO element constant */
	int FO_CONDITIONAL_PAGE_MASTER_REFERENCE = 7;

	/** FO element constant */
	int FO_DECLARATIONS = 8;

	/** FO element constant */
	int FO_EXTERNAL_GRAPHIC = 9;

	/** FO element constant */
	int FO_FLOAT = 10;

	/** FO element constant */
	int FO_FLOW = 11;

	/** FO element constant */
	int FO_FOOTNOTE = 12;

	/** FO element constant */
	int FO_FOOTNOTE_BODY = 13;

	/** FO element constant */
	int FO_INITIAL_PROPERTY_SET = 14;

	/** FO element constant */
	int FO_INLINE = 15;

	/** FO element constant */
	int FO_INLINE_CONTAINER = 16;

	/** FO element constant */
	int FO_INSTREAM_FOREIGN_OBJECT = 17;

	/** FO element constant */
	int FO_LAYOUT_MASTER_SET = 18;

	/** FO element constant */
	int FO_LEADER = 19;

	/** FO element constant */
	int FO_LIST_BLOCK = 20;

	/** FO element constant */
	int FO_LIST_ITEM = 21;

	/** FO element constant */
	int FO_LIST_ITEM_BODY = 22;

	/** FO element constant */
	int FO_LIST_ITEM_LABEL = 23;

	/** FO element constant */
	int FO_MARKER = 24;

	/** FO element constant */
	int FO_MULTI_CASE = 25;

	/** FO element constant */
	int FO_MULTI_PROPERTIES = 26;

	/** FO element constant */
	int FO_MULTI_PROPERTY_SET = 27;

	/** FO element constant */
	int FO_MULTI_SWITCH = 28;

	/** FO element constant */
	int FO_MULTI_TOGGLE = 29;

	/** FO element constant */
	int FO_PAGE_NUMBER = 30;

	/** FO element constant */
	int FO_PAGE_NUMBER_CITATION = 31;

	/** FO element constant */
	int FO_PAGE_SEQUENCE = 32;

	/** FO element constant */
	int FO_PAGE_SEQUENCE_MASTER = 33;

	/** FO element constant */
	int FO_REGION_AFTER = 34;

	/** FO element constant */
	int FO_REGION_BEFORE = 35;

	/** FO element constant */
	int FO_REGION_BODY = 36;

	/** FO element constant */
	int FO_REGION_END = 37;

	/** FO element constant */
	int FO_REGION_START = 38;

	/** FO element constant */
	int FO_REPEATABLE_PAGE_MASTER_ALTERNATIVES = 39;

	/** FO element constant */
	int FO_REPEATABLE_PAGE_MASTER_REFERENCE = 40;

	/** FO element constant */
	int FO_RETRIEVE_MARKER = 41;

	/** FO element constant */
	int FO_ROOT = 42;

	/** FO element constant */
	int FO_SIMPLE_PAGE_MASTER = 43;

	/** FO element constant */
	int FO_SINGLE_PAGE_MASTER_REFERENCE = 44;

	/** FO element constant */
	int FO_STATIC_CONTENT = 45;

	/** FO element constant */
	int FO_TABLE = 46;

	/** FO element constant */
	int FO_TABLE_AND_CAPTION = 47;

	/** FO element constant */
	int FO_TABLE_BODY = 48;

	/** FO element constant */
	int FO_TABLE_CAPTION = 49;

	/** FO element constant */
	int FO_TABLE_CELL = 50;

	/** FO element constant */
	int FO_TABLE_COLUMN = 51;

	/** FO element constant */
	int FO_TABLE_FOOTER = 52;

	/** FO element constant */
	int FO_TABLE_HEADER = 53;

	/** FO element constant */
	int FO_TABLE_ROW = 54;

	/** FO element constant */
	int FO_TITLE = 55;

	/** FO element constant */
	int FO_WRAPPER = 56;

	/** FO element constant - XSL 1.1 */
	int FO_BOOKMARK_TREE = 57;

	/** FO element constant - XSL 1.1 */
	int FO_BOOKMARK = 58;

	/** FO element constant - XSL 1.1 */
	int FO_BOOKMARK_TITLE = 59;

	/** FO element constant - XSL 1.1 */
	int FO_PAGE_SEQUENCE_WRAPPER = 60;

	/** FO element constant - XSL 1.1 */
	int FO_PAGE_NUMBER_CITATION_LAST = 61;

	// add by zq,Inline内容
	int FO_CONTENT = 62;

	/** Number of FO element constants defined */
	int FRM_OBJ_COUNT = 62;

	// Masks
	/**
	 * For compound properties the property constant value is shifted by this
	 * amount. The low order bits hold the constant for the component property.
	 */
	int COMPOUND_SHIFT = 9;

	/**
	 * Mask that when applied to a compound property returns the constant of the
	 * component property.
	 */
	int PROPERTY_MASK = (1 << COMPOUND_SHIFT) - 1;

	/**
	 * Mask that when applied to a compound property returns the constant of the
	 * compound property.
	 */
	int COMPOUND_MASK = ~PROPERTY_MASK;

	/** Number of compund properties defined */
	int COMPOUND_COUNT = 11;

	// property constants
	/** Property constant */
	int PR_ABSOLUTE_POSITION = 1;

	/** Property constant */
	int PR_ACTIVE_STATE = 2;

	/** Property constant */
	int PR_ALIGNMENT_ADJUST = 3;

	/** Property constant */
	int PR_ALIGNMENT_BASELINE = 4;

	/** Property constant */
	int PR_AUTO_RESTORE = 5;

	/** Property constant */
	int PR_AZIMUTH = 6;

	/** Property constant */
	int PR_BACKGROUND = 7;

	/** Property constant */
	int PR_BACKGROUND_ATTACHMENT = 8;

	/** Property constant */
	int PR_BACKGROUND_COLOR = 9;

	/** Property constant */
	int PR_BACKGROUND_IMAGE = 10;

	/** Property constant */
	int PR_BACKGROUND_POSITION = 11;

	/** Property constant */
	int PR_BACKGROUND_POSITION_HORIZONTAL = 12;

	/** Property constant */
	int PR_BACKGROUND_POSITION_VERTICAL = 13;

	/** Property constant */
	int PR_BACKGROUND_REPEAT = 14;

	/** Property constant */
	int PR_BASELINE_SHIFT = 15;

	/** Property constant */
	int PR_BLANK_OR_NOT_BLANK = 16;

	/** Property constant */
	int PR_BLOCK_PROGRESSION_DIMENSION = 17;

	/** Property constant */
	int PR_BORDER = 18;

	/** Property constant */
	int PR_BORDER_AFTER_COLOR = 19;

	/** Property constant */
	int PR_BORDER_AFTER_PRECEDENCE = 20;

	/** Property constant */
	int PR_BORDER_AFTER_STYLE = 21;

	/** Property constant */
	int PR_BORDER_AFTER_WIDTH = 22;

	/** Property constant */
	int PR_BORDER_BEFORE_COLOR = 23;

	/** Property constant */
	int PR_BORDER_BEFORE_PRECEDENCE = 24;

	/** Property constant */
	int PR_BORDER_BEFORE_STYLE = 25;

	/** Property constant */
	int PR_BORDER_BEFORE_WIDTH = 26;

	/** Property constant */
	int PR_BORDER_BOTTOM = 27;

	/** Property constant */
	int PR_BORDER_BOTTOM_COLOR = 28;

	/** Property constant */
	int PR_BORDER_BOTTOM_STYLE = 29;

	/** Property constant */
	int PR_BORDER_BOTTOM_WIDTH = 30;

	/** Property constant */
	int PR_BORDER_COLLAPSE = 31;

	/** Property constant */
	int PR_BORDER_COLOR = 32;

	/** Property constant */
	int PR_BORDER_END_COLOR = 33;

	/** Property constant */
	int PR_BORDER_END_PRECEDENCE = 34;

	/** Property constant */
	int PR_BORDER_END_STYLE = 35;

	/** Property constant */
	int PR_BORDER_END_WIDTH = 36;

	/** Property constant */
	int PR_BORDER_LEFT = 37;

	/** Property constant */
	int PR_BORDER_LEFT_COLOR = 38;

	/** Property constant */
	int PR_BORDER_LEFT_STYLE = 39;

	/** Property constant */
	int PR_BORDER_LEFT_WIDTH = 40;

	/** Property constant */
	int PR_BORDER_RIGHT = 41;

	/** Property constant */
	int PR_BORDER_RIGHT_COLOR = 42;

	/** Property constant */
	int PR_BORDER_RIGHT_STYLE = 43;

	/** Property constant */
	int PR_BORDER_RIGHT_WIDTH = 44;

	/** Property constant */
	int PR_BORDER_SEPARATION = 45;

	/** Property constant */
	int PR_BORDER_SPACING = 46;

	/** Property constant */
	int PR_BORDER_START_COLOR = 47;

	/** Property constant */
	int PR_BORDER_START_PRECEDENCE = 48;

	/** Property constant */
	int PR_BORDER_START_STYLE = 49;

	/** Property constant */
	int PR_BORDER_START_WIDTH = 50;

	/** Property constant */
	int PR_BORDER_STYLE = 51;

	/** Property constant */
	int PR_BORDER_TOP = 52;

	/** Property constant */
	int PR_BORDER_TOP_COLOR = 53;

	/** Property constant */
	int PR_BORDER_TOP_STYLE = 54;

	/** Property constant */
	int PR_BORDER_TOP_WIDTH = 55;

	/** Property constant */
	int PR_BORDER_WIDTH = 56;

	/** Property constant */
	int PR_BOTTOM = 57;

	/** Property constant */
	int PR_BREAK_AFTER = 58;

	/** Property constant */
	int PR_BREAK_BEFORE = 59;

	/** Property constant */
	int PR_CAPTION_SIDE = 60;

	/** Property constant */
	int PR_CASE_NAME = 61;

	/** Property constant */
	int PR_CASE_TITLE = 62;

	/** Property constant */
	int PR_CHARACTER = 63;

	/** Property constant */
	int PR_CLEAR = 64;

	/** Property constant */
	int PR_CLIP = 65;

	/** Property constant */
	int PR_COLOR = 66;

	/** Property constant */
	int PR_COLOR_PROFILE_NAME = 67;

	/** Property constant */
	int PR_COLUMN_COUNT = 68;

	/** Property constant */
	int PR_COLUMN_GAP = 69;

	/** Property constant */
	int PR_COLUMN_NUMBER = 70;

	/** Property constant */
	int PR_COLUMN_WIDTH = 71;

	/** Property constant */
	int PR_CONTENT_HEIGHT = 72;

	/** Property constant */
	int PR_CONTENT_TYPE = 73;

	/** Property constant */
	int PR_CONTENT_WIDTH = 74;

	/** Property constant */
	int PR_COUNTRY = 75;

	/** Property constant */
	int PR_CUE = 76;

	/** Property constant */
	int PR_CUE_AFTER = 77;

	/** Property constant */
	int PR_CUE_BEFORE = 78;

	/** Property constant */
	int PR_DESTINATION_PLACEMENT_OFFSET = 79;

	/** Property constant */
	int PR_DIRECTION = 80;

	/** Property constant */
	int PR_DISPLAY_ALIGN = 81;

	/** Property constant */
	int PR_DOMINANT_BASELINE = 82;

	/** Property constant */
	int PR_ELEVATION = 83;

	/** Property constant */
	int PR_EMPTY_CELLS = 84;

	/** Property constant */
	int PR_END_INDENT = 85;

	/** Property constant */
	int PR_ENDS_ROW = 86;

	/** Property constant */
	int PR_EXTENT = 87;

	/** Property constant */
	int PR_EXTERNAL_DESTINATION = 88;

	/** Property constant */
	int PR_FLOAT = 89;

	/** Property constant */
	int PR_FLOW_NAME = 90;

	/** Property constant */
	int PR_FONT = 91;

	/** Property constant */
	int PR_FONT_FAMILY = 92;

	/** Property constant */
	int PR_FONT_SELECTION_STRATEGY = 93;

	/** Property constant */
	int PR_FONT_SIZE = 94;

	/** Property constant */
	int PR_FONT_SIZE_ADJUST = 95;

	/** Property constant */
	int PR_FONT_STRETCH = 96;

	/** Property constant */
	int PR_FONT_STYLE = 97;

	/** Property constant */
	int PR_FONT_VARIANT = 98;

	/** Property constant */
	int PR_FONT_WEIGHT = 99;

	/** Property constant */
	int PR_FORCE_PAGE_COUNT = 100;

	/** Property constant */
	int PR_FORMAT = 101;

	/** Property constant */
	int PR_GLYPH_ORIENTATION_HORIZONTAL = 102;

	/** Property constant */
	int PR_GLYPH_ORIENTATION_VERTICAL = 103;

	/** Property constant */
	int PR_GROUPING_SEPARATOR = 104;

	/** Property constant */
	int PR_GROUPING_SIZE = 105;

	/** Property constant */
	int PR_HEIGHT = 106;

	/** Property constant */
	int PR_HYPHENATE = 107;

	/** Property constant */
	int PR_HYPHENATION_CHARACTER = 108;

	/** Property constant */
	int PR_HYPHENATION_KEEP = 109;

	/** Property constant */
	int PR_HYPHENATION_LADDER_COUNT = 110;

	/** Property constant */
	int PR_HYPHENATION_PUSH_CHARACTER_COUNT = 111;

	/** Property constant */
	int PR_HYPHENATION_REMAIN_CHARACTER_COUNT = 112;

	/** Property constant */
	int PR_ID = 113;

	/** Property constant */
	int PR_INDICATE_DESTINATION = 114;

	/** Property constant */
	int PR_INITIAL_PAGE_NUMBER = 115;

	/** Property constant */
	int PR_INLINE_PROGRESSION_DIMENSION = 116;

	/** Property constant */
	int PR_INTERNAL_DESTINATION = 117;

	/** Property constant */
	int PR_KEEP_TOGETHER = 118;

	/** Property constant */
	int PR_KEEP_WITH_NEXT = 119;

	/** Property constant */
	int PR_KEEP_WITH_PREVIOUS = 120;

	/** Property constant */
	int PR_LANGUAGE = 121;

	/** Property constant */
	int PR_LAST_LINE_END_INDENT = 122;

	/** Property constant */
	int PR_LEADER_ALIGNMENT = 123;

	/** Property constant */
	int PR_LEADER_LENGTH = 124;

	/** Property constant */
	int PR_LEADER_PATTERN = 125;

	/** Property constant */
	int PR_LEADER_PATTERN_WIDTH = 126;

	/** Property constant */
	int PR_LEFT = 127;

	/** Property constant */
	int PR_LETTER_SPACING = 128;

	/** Property constant */
	int PR_LETTER_VALUE = 129;

	/** Property constant */
	int PR_LINEFEED_TREATMENT = 130;

	/** Property constant */
	int PR_LINE_HEIGHT = 131;

	/** Property constant */
	int PR_LINE_HEIGHT_SHIFT_ADJUSTMENT = 132;

	/** Property constant */
	int PR_LINE_STACKING_STRATEGY = 133;

	/** Property constant */
	int PR_MARGIN = 134;

	/** Property constant */
	int PR_MARGIN_BOTTOM = 135;

	/** Property constant */
	int PR_MARGIN_LEFT = 136;

	/** Property constant */
	int PR_MARGIN_RIGHT = 137;

	/** Property constant */
	int PR_MARGIN_TOP = 138;

	/** Property constant */
	int PR_MARKER_CLASS_NAME = 139;

	/** Property constant */
	int PR_MASTER_NAME = 140;

	/** Property constant */
	int PR_MASTER_REFERENCE = 141;

	/** Property constant */
	int PR_MAX_HEIGHT = 142;

	/** Property constant */
	int PR_MAXIMUM_REPEATS = 143;

	/** Property constant */
	int PR_MAX_WIDTH = 144;

	/** Property constant */
	int PR_MEDIA_USAGE = 145;

	/** Property constant */
	int PR_MIN_HEIGHT = 146;

	/** Property constant */
	int PR_MIN_WIDTH = 147;

	/** Property constant */
	int PR_NUMBER_COLUMNS_REPEATED = 148;

	/** Property constant */
	int PR_NUMBER_COLUMNS_SPANNED = 149;

	/** Property constant */
	int PR_NUMBER_ROWS_SPANNED = 150;

	/** Property constant */
	int PR_ODD_OR_EVEN = 151;

	/** Property constant */
	int PR_ORPHANS = 152;

	/** Property constant */
	int PR_OVERFLOW = 153;

	/** Property constant */
	int PR_PADDING = 154;

	/** Property constant */
	int PR_PADDING_AFTER = 155;

	/** Property constant */
	int PR_PADDING_BEFORE = 156;

	/** Property constant */
	int PR_PADDING_BOTTOM = 157;

	/** Property constant */
	int PR_PADDING_END = 158;

	/** Property constant */
	int PR_PADDING_LEFT = 159;

	/** Property constant */
	int PR_PADDING_RIGHT = 160;

	/** Property constant */
	int PR_PADDING_START = 161;

	/** Property constant */
	int PR_PADDING_TOP = 162;

	/** Property constant */
	int PR_PAGE_BREAK_AFTER = 163;

	/** Property constant */
	int PR_PAGE_BREAK_BEFORE = 164;

	/** Property constant */
	int PR_PAGE_BREAK_INSIDE = 165;

	/** Property constant */
	int PR_PAGE_HEIGHT = 166;

	/** Property constant */
	int PR_PAGE_POSITION = 167;

	/** Property constant */
	int PR_PAGE_WIDTH = 168;

	/** Property constant */
	int PR_PAUSE = 169;

	/** Property constant */
	int PR_PAUSE_AFTER = 170;

	/** Property constant */
	int PR_PAUSE_BEFORE = 171;

	/** Property constant */
	int PR_PITCH = 172;

	/** Property constant */
	int PR_PITCH_RANGE = 173;

	/** Property constant */
	int PR_PLAY_DURING = 174;

	/** Property constant */
	int PR_POSITION = 175;

	/** Property constant */
	int PR_PRECEDENCE = 176;

	/** Property constant */
	int PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS = 177;

	/** Property constant */
	int PR_PROVISIONAL_LABEL_SEPARATION = 178;

	/** Property constant */
	int PR_REFERENCE_ORIENTATION = 179;

	/** Property constant */
	int PR_REF_ID = 180;

	/** Property constant */
	int PR_REGION_NAME = 181;

	/** Property constant */
	int PR_RELATIVE_ALIGN = 182;

	/** Property constant */
	int PR_RELATIVE_POSITION = 183;

	/** Property constant */
	int PR_RENDERING_INTENT = 184;

	/** Property constant */
	int PR_RETRIEVE_BOUNDARY = 185;

	/** Property constant */
	int PR_RETRIEVE_CLASS_NAME = 186;

	/** Property constant */
	int PR_RETRIEVE_POSITION = 187;

	/** Property constant */
	int PR_RICHNESS = 188;

	/** Property constant */
	int PR_RIGHT = 189;

	/** Property constant */
	int PR_ROLE = 190;

	/** Property constant */
	int PR_RULE_STYLE = 191;

	/** Property constant */
	int PR_RULE_THICKNESS = 192;

	/** Property constant */
	int PR_SCALING = 193;

	/** Property constant */
	int PR_SCALING_METHOD = 194;

	/** Property constant */
	int PR_SCORE_SPACES = 195;

	/** Property constant */
	int PR_SCRIPT = 196;

	/** Property constant */
	int PR_SHOW_DESTINATION = 197;

	/** Property constant */
	int PR_SIZE = 198;

	/** Property constant */
	int PR_SOURCE_DOCUMENT = 199;

	/** Property constant */
	int PR_SPACE_AFTER = 200;

	/** Property constant */
	int PR_SPACE_BEFORE = 201;

	/** Property constant */
	int PR_SPACE_END = 202;

	/** Property constant */
	int PR_SPACE_START = 203;

	/** Property constant */
	int PR_SPAN = 204;

	/** Property constant */
	int PR_SPEAK = 205;

	/** Property constant */
	int PR_SPEAK_HEADER = 206;

	/** Property constant */
	int PR_SPEAK_NUMERAL = 207;

	/** Property constant */
	int PR_SPEAK_PUNCTUATION = 208;

	/** Property constant */
	int PR_SPEECH_RATE = 209;

	/** Property constant */
	int PR_SRC = 210;

	/** Property constant */
	int PR_START_INDENT = 211;

	/** Property constant */
	int PR_STARTING_STATE = 212;

	/** Property constant */
	int PR_STARTS_ROW = 213;

	/** Property constant */
	int PR_STRESS = 214;

	/** Property constant */
	int PR_SUPPRESS_AT_LINE_BREAK = 215;

	/** Property constant */
	int PR_SWITCH_TO = 216;

	/** Property constant */
	int PR_TABLE_LAYOUT = 217;

	/** Property constant */
	int PR_TABLE_OMIT_FOOTER_AT_BREAK = 218;

	/** Property constant */
	int PR_TABLE_OMIT_HEADER_AT_BREAK = 219;

	/** Property constant */
	int PR_TARGET_PRESENTATION_CONTEXT = 220;

	/** Property constant */
	int PR_TARGET_PROCESSING_CONTEXT = 221;

	/** Property constant */
	int PR_TARGET_STYLESHEET = 222;

	/** Property constant */
	int PR_TEXT_ALIGN = 223;

	/** Property constant */
	int PR_TEXT_ALIGN_LAST = 224;

	/** Property constant */
	int PR_TEXT_ALTITUDE = 225;

	/** Property constant */
	int PR_TEXT_DECORATION = 226;

	/** Property constant */
	int PR_TEXT_DEPTH = 227;

	/** Property constant */
	int PR_TEXT_INDENT = 228;

	/** Property constant */
	int PR_TEXT_SHADOW = 229;

	/** Property constant */
	int PR_TEXT_TRANSFORM = 230;

	/** Property constant */
	int PR_TOP = 231;

	/** Property constant */
	int PR_TREAT_AS_WORD_SPACE = 232;

	/** Property constant */
	int PR_UNICODE_BIDI = 233;

	/** Property constant */
	int PR_VERTICAL_ALIGN = 234;

	/** Property constant */
	int PR_VISIBILITY = 235;

	/** Property constant */
	int PR_VOICE_FAMILY = 236;

	/** Property constant */
	int PR_VOLUME = 237;

	/** Property constant */
	int PR_WHITE_SPACE = 238;

	/** Property constant */
	int PR_WHITE_SPACE_COLLAPSE = 239;

	/** Property constant */
	int PR_WHITE_SPACE_TREATMENT = 240;

	/** Property constant */
	int PR_WIDOWS = 241;

	/** Property constant */
	int PR_WIDTH = 242;

	/** Property constant */
	int PR_WORD_SPACING = 243;

	/** Property constant */
	int PR_WRAP_OPTION = 244;

	/** Property constant */
	int PR_WRITING_MODE = 245;

	/** Property constant */
	int PR_XML_LANG = 246;

	/** Property constant */
	int PR_Z_INDEX = 247;

	/** Property constant */
	int PR_INTRUSION_DISPLACE = 248;

	/** Property constant - XSL 1.1 */
	int PR_INDEX_CLASS = 249;

	/** Property constant - XSL 1.1 */
	int PR_INDEX_KEY = 250;

	/** Property constant - Custom extension */
	int PR_X_BLOCK_PROGRESSION_UNIT = 251;

	int PR_EDITMODE = 252;// add by zkl.

	int PR_XPATH = 253;// ADD by zkl.

	int PR_TRANSLATEURL = 254;// add by zkl.

	int PR_HIDENAME = 255; // add by zkl.

	// add by lzy
	int PR_SRC_TYPE = 256;

	int PR_APHLA = 257;

	// add end
	// add by zq;现在page_Matser作为的PageSquence的属性
	int PR_SIMPLE_PAGE_MASTER = 258;

	int PR_PAGE_SEQUENCE_MASTER = 259;

	// add end

	// add by zhongyajun
	// 条码类别
	int PR_BARCODE_TYPE = 260;

	// 条码内容
	int PR_BARCODE_CONTENT = 261;

	// 条码的值（模板参数）

	int PR_BARCODE_VALUE = 262;

	// 条码的条的高度（模板参数）
	int PR_BARCODE_HEIGHT = 263;

	// 基本单元（条/空）的宽度（模板参数）
	int PR_BARCODE_MODULE = 264;

	// 供人识别字符的字号（模板参数）
	int PR_BARCODE_FONT_HEIGHT = 265;

	// 供人识别字符的字体家族（模板参数）
	int PR_BARCODE_FONT_FAMILY = 266;

	// 条码左右两端外侧空白区大小（模板参数）
	int PR_BARCODE_QUIET_HORIZONTAL = 267;

	// 条码字符上下外侧空白区大小（模板参数）
	int PR_BARCODE_QUIET_VERTICAL = 268;

	// 条码方向，与水平方向的逆时针角度（模板参数）
	int PR_BARCODE_ORIENTATION = 269;

	// 是否添加校验码（模板参数）
	int PR_BARCODE_ADDCHECKSUM = 270;

	// 宽窄比（模板参数）
	int PR_BARCODE_WIDE_TO_NARROW = 271;

	// 供人识别字符中各字符之间的间距大小（模板参数）
	int PR_BARCODE_TEXT_CHAR_SPACE = 272;

	// 供人识别字符（模板参数）
	int PR_BARCODE_STRING = 273;

	// 供人识别字符是否打印（模板参数）
	int PR_BARCODE_PRINT_TEXT = 274;

	// 条码字符与供人识别字符间隔大小（模板参数）
	int PR_BARCODE_TEXT_BLOCK = 275;

	// 生成的商品条码类型（模板参数）
	int PR_BARCODE_CODE_TYPE = 276;

	// 使用的字符集（模板参数）
	int PR_BARCODE_SUBSET = 277;

	// 是否按UCC/EAN编码（模板参数）
	int PR_BARCODE_MAKEUCC = 278;

	// add end
	// add by zq
	// 日期时间格式化属性 add by zq
	int PR_DATETIMEFORMAT = 279;

	// 数字格式化对象属性
	int PR_NUMBERFORMAT = 280;

	// 总页码是否连续计数，即是否记所有份数的总页码，属性值为EnumProperty类型的值。可取EN_TRUE和EN_FALSE
	int PR_ISSHOWTOTAL = 281;

	// zq add end

	// add by zhongyajun
	// 格式化数字的类型
	int PR_NUMBERTEXT_TYPE = 282;

	// 将非法数据处理
	int PR_LAWLESSDATAPROCESS = 283;

	// 是否是文档的末页页码
	int PR_ENDOFALL = 284;

	// zhongyajun add end
	// add by zq
	// 条件属性
	int PR_CONDTION = 285;

	// 组属性
	int PR_GROUP = 286;

	// 组属性
	int PR_INLINE_CONTENT = 287;

	// xsl文档的编辑属性（总开关）
	int PR_XMLEDIT = 288;

	// 图片对象的层属性
	int PR_GRAPHIC_LAYER = 289;

	// 图片图片的层属性
	int PR_BACKGROUNDGRAPHIC_LAYER = 290;

	/** Property constant */
	// 悬挂缩进
	int PR_HANGING_INDENT = 291;

	// 以下是SVG图形的相关属性
	// X坐标
	int PR_X = 292;

	// Y坐标
	int PR_Y = 293;

	// x1坐标,直线的起始X坐标
	int PR_X1 = 294;

	// y1坐标,直线的起始Y坐标
	int PR_Y1 = 295;

	// x2坐标,直线的结束y坐标
	int PR_X2 = 296;

	// y1坐标,直线的起始Y坐标
	int PR_Y2 = 297;

	// 圆心横坐标
	int PR_CX = 298;

	// 圆心纵坐标
	int PR_CY = 299;

	// 圆半径
	int PR_R = 300;

	// 椭圆X半径
	int PR_RX = 301;

	// 椭圆Y半径
	int PR_RY = 302;

	// 图形的填充属性
	int PR_FILL = 303;

	// 线条粗细
	int PR_STROKE_WIDTH = 304;

	// 表示svg的画布svg.Canvas
	int PR_SVG_CANVAS = 305;

	// 表示svg的绝对定位容器svg.SVGContainer
	int PR_SVG_CONTAINER = 306;

	// 线的类型
	int PR_SVG_LINE_TYPE = 307;

	// 拐角类型
	int PR_SVG_STROKE_LINEJOIN = 308;

	// 线段起始端箭头类型
	int PR_SVG_ARROW_START_TYPE = 309;

	// 线段末端箭头类型
	int PR_SVG_ARROW_END_TYPE = 310;

	// svg图形内文字设置
	int PR_SVG_TEXT_CONTENT = 311;

	// svg图形方向
	int PR_SVG_ORIENTATION = 312;

	// 动态样式
	int PR_DYNAMIC_STYLE = 313;

	// 段落样式
	int PR_BLOCK_STYLE = 314;

	// 【添加】 by 李晓光 2009-4-7【StringProperty】
	int PR_BOOKMARK_TITLE = 315;

	// 大纲级别 取值为从0开始的整数
	int PR_BLOCK_LEVEL = 316;

	// 段落样式集合属性
	int PR_BLOCK_STYLE_COLLECTION = 317;

	// 是否显示目录的页码 取值为布尔值
	int PR_BLOCK_REF_NUMBER = 318;

	// 目录页码是否右对齐 取值为布尔值
	int PR_BLOCK_REF_RIGHT_ALIGN = 319;

	// 目录页码前导符的样式 取值为从0开始的整型
	// 这里用PR_LEADER_PATTERN
	// int PR_BLOCK_REF_LEADING_STYLE = 326;
	// 目录显示级别 取值为从0开始的整数
	int PR_BLOCK_REF_SHOW_LEVEL = 320;

	// 目录级别的所有属性设置 以List<IndexStyles>作为存储介质，第0位代表最高级一级，依次降低级别
	int PR_BLOCK_REF_STYLES = 321;

	// 为支持二维条形码而设置的属性
	// 定义错误纠正等级 范围：int 0-8
	int PR_BARCODE_EC_LEVEL = 322;

	// 定义条码字符数据区列数 范围：int 0-30
	int PR_BARCODE_COLUMNS = 323;

	// 定义最小数据区列数 范围：int 1-30
	int PR_BARCODE_MIN_COLUMNS = 324;

	// 定义最大数据区列数 范围：int 1-30
	int PR_BARCODE_MAX_COLUMNS = 325;

	// 定义条码字符最小行数 范围：int 3-90
	int PR_BARCODE_MIN_ROWS = 326;

	// 定义条码字符最大行数 范围：int 3-90
	int PR_BARCODE_MAX_ROWS = 327;

	// SVG图形的相关属性结束

	// 统计图相关属性开始
	// 统计图类型，支持EN_BARCHART/EN_PIECHART等;
	int PR_CHART_TYPE = 328;

	// 标题
	int PR_TITLE = 329;

	// 标题字体名
	int PR_TITLE_FONTFAMILY = 330;

	// 标题字体style,"粗体"，“斜体“等
	int PR_TITLE_FONTSTYLE = 331;

	// 标题字号
	int PR_TITLE_FONTSIZE = 332;

	// 标题颜色
	int PR_TITLE_COLOR = 333;

	// 标题水平放上上对齐方式，支持"左"，”居中“，”右“
	int PR_TITLE_ALIGNMENT = 334;

	// 背景图片的透明度
	int PR_BACKGROUNDIMAGE_ALAPH = 335;

	// 统计图本上的透明度
	int PR_FOREGROUND_ALAPH = 336;

	// 值个数
	int PR_VALUE_COUNT = 337;

	// 系列数
	int PR_SERIES_COUNT = 338;

	// 值颜色，属性值是一个颜色list，用来指定相应顺序的颜色值
	int PR_VALUE_COLOR = 339;

	// 值标签 ，属性值是一个标签list。
	int PR_VALUE_LABEL = 340;

	// 值标签字体名
	int PR_VALUE_LABEL_FONTFAMILY = 341;

	// 值标签字体style,"粗体"，“斜体“等
	int PR_VALUE_LABEL_FONTSTYLE = 342;

	// 值标签字号
	int PR_VALUE_LABEL_FONTSIZE = 343;

	// 值标签颜色
	int PR_VALUE_LABEL_COLOR = 344;

	// 标签 ，属性值是一个标签list。
	int PR_SERIES_LABEL = 345;

	// 系列标签字体名
	int PR_SERIES_LABEL_FONTFAMILY = 346;

	// 系列标签字体style,"粗体"，“斜体“等
	int PR_SERIES_LABEL_FONTSTYLE = 347;

	// 系列标签字号
	int PR_SERIES_LABEL_FONTSIZE = 348;

	// 系列标签颜色
	int PR_SERIES_LABEL_COLOR = 349;

	// 系列标签方向（0～360）
	int PR_SERIES_LABEL_ORIENTATION = 350;

	// 系列值 ,ChartDataList类型的属性值
	int PR_SERIES_VALUE = 351;

	// 域坐标轴标签
	int PR_DOMAINAXIS_LABEL = 352;

	// 值坐标轴标签
	int PR_RANGEAXIS_LABEL = 353;

	// 域坐标标签字体名
	int PR_DOMAINAXIS_LABEL_FONTFAMILY = 354;

	// 域坐标标签字体style,"粗体"，“斜体“等
	int PR_DOMAINAXIS_LABEL_FONTSTYLE = 355;

	// 域坐标标签字号
	int PR_DOMAINAXIS_LABEL_FONTSIZE = 356;

	// 域坐标标签颜色
	int PR_DOMAINAXIS_LABEL_COLOR = 357;

	// 域坐标标签对齐方式，有：左/中/右三个值
	int PR_DOMAINAXIS_LABEL_ALIGNMENT = 358;

	// 域坐标标签字体名
	int PR_RANGEAXIS_LABEL_FONTFAMILY = 359;

	// 值坐标标签字体style,"粗体"，“斜体“等
	int PR_RANGEAXIS_LABEL_FONTSTYLE = 360;

	// 值坐标标签字号
	int PR_RANGEAXIS_LABEL_FONTSIZE = 361;

	// 值坐标标签颜色
	int PR_RANGEAXIS_LABEL_COLOR = 362;

	// 值坐标标签对齐方式，有：上/中/下三个值
	int PR_RANGEAXIS_LABEL_ALIGNMENT = 363;

	// 图表方向，横向/和纵向两个值
	int PR_CHART_ORIENTATION = 364;

	// 值坐标轴刻度精度
	int PR_RANGEAXIS_PRECISION = 365;

	// 是否显示域对齐线
	int PR_DOMIANLINE_VISABLE = 366;

	// 是否显示值对齐线
	int PR_RANGELINE_VISABLE = 367;

	// 是否显示0值刻度线
	int PR_ZERORANGELINE_VISABLE = 368;

	// 值坐标轴坐标跨度
	int PR_RANGEAXIS_UNITINCREMENT = 369;

	// 值坐标轴的最小刻度
	int PR_RANGEAXIS_MINUNIT = 370;

	// 值坐标轴的最大刻度
	int PR_RANGEAXIS_MAXUNIT = 371;

	// 是否显示3d效果
	int PR_3DENABLE = 372;

	// 3D深度
	int PR_3DDEPNESS = 373;

	// 解释标签是否显示
	int PR_LENGEND_LABEL_VISABLE = 374;

	// 解释标签的显示位置
	int PR_LENGEND_LABEL_LOCATION = 375;

	// 解释标签的字体名
	int PR_LENGEND_LABEL_FONTFAMILY = 376;

	// 解释标签字体style,"粗体"，“斜体“等
	int PR_LENGEND_LABEL_FONTSTYLE = 377;

	// 解释标签字号
	int PR_LENGEND_LABEL_FONTSIZE = 378;

	// 解释标签颜色
	int PR_LENGEND_LABEL_COLOR = 379;

	// 解释标签的对齐方式
	int PR_LENGEND_LABLE_ALIGNMENT = 380;

	// 是否显示值
	int PR_VALUE_LABLEVISABLE = 381;

	// 值的字体名
	int PR_CHART_VALUE_FONTFAMILY = 382;

	// 值字体style,"粗体"，“斜体“等
	int PR_CHART_VALUE_FONTSTYLE = 383;

	// 值字号
	int PR_CHART_VALUE_FONTSIZE = 384;

	// 值颜色
	int PR_CHART_VALUE_COLOR = 385;

	// 值标签据统计图元素的偏移量，fixedlength类型的属性值
	int PR_CHART_VALUE_OFFSET = 386;

	// 0值是否可视
	int PR_ZEROVALUE_VISABLE = 387;

	// null值是否可视
	int PR_NULLVALUE_VISABLE = 388;

	// 饼状图起始角度
	int PR_PIECHART_STARTANGLE = 389;

	// 饼状图方向，顺时针/逆时针
	int PR_PIECHART_DIRECTION = 390;

	// 饼状图是否显示百分比值
	int PR_PERCENTVALUE_VISABLE = 391;

	// 是否显示饼状图标签
	int PR_PIECHARTLENGENDLABEL_VISABLE = 392;

	// 是否显示饼状图的实际值
	int PR_PIE_FACT_VALUE_VISABLE = 393;

	// 统计图相关属性结束

	// zq add end

	// wdems2.0编辑相关的属性
	// 编辑类型
	int PR_EDITTYPE = 394;

	// 权限，字符串类型的值
	int PR_AUTHORITY = 395;

	//
	int PR_ISRELOAD = 396;

	// ui的样式枚举值（）
	int PR_APPEARANCE = 397;

	// 编辑控件宽度
	int PR_EDIT_WIDTH = 398;

	// 编辑控件高度
	int PR_EDIT_HEIGHT = 399;

	// 规范里面是字符串类型的，个人觉得还有可能是动态节点
	int PR_HINT = 400;

	int PR_ONBLUR = 401;

	int PR_ONSELECTED = 402;

	int PR_ONKEYPRESS = 403;

	int PR_ONKEYDOWN = 404;

	int PR_ONKEYUP = 405;

	int PR_ONCHANGE = 406;

	int PR_ONCLICK = 407;

	int PR_ONEDIT = 408;

	int PR_ONRESULT = 409;

	// 输入框的类型,枚举类型的值
	int PR_INPUT_TYPE = 410;

	// 输入框大小，整形值
	int PR_INPUT_MULTILINE = 411;

	// 换行属性（枚举值）
	int PR_INPUT_WRAP = 412;

	// 过滤字符
	int PR_INPUT_FILTER = 413;

	// 过滤字符的解释
	int PR_INPUT_FILTERMSG = 414;

	// 时间类型，枚举值
	int PR_DATE_TYPE = 415;

	// 日期类型的格式
	int PR_DATE_FORMAT = 416;

	// 单选复选按钮选定的内容
	int PR_RADIO_CHECK_VALUE = 417;

	// 未选中时值
	int PR_CHECKBOX_UNSELECT_VALUE = 418;

	// 外框轮廓
	int PR_CHECKBOX_BOXSTYLE = 419;

	// 选中符号
	int PR_CHECKBOX_TICKMARK = 420;

	// 枚举值（true，false）
	int PR_RADIO_CHECK_CHECKED = 421;

	int PR_SELECT_TYPE = 422;

	// 下拉列表的最大可选个数。整形值
	int PR_SELECT_MULTIPLE = 423;

	// 一页显示的列表行数，（整形，最小值为5）
	int PR_SELECT_LINES = 424;

	// 下拉列表是否可以自行输入内容(枚举true，false)
	int PR_SELECT_ISEDIT = 425;

	// 下级菜单的名字,String类型的值
	int PR_SELECT_NEXT = 426;

	// 外观形式
	int PR_SELECT_SHOWLIST = 427;

	int PR_SELECT_INFO = 428;

	int PR_SELECT_NAME = 429;
	//编辑时，输入为空时，可添加的默认值
	int PR_DEFAULT_VALUE = 430;
	//数据字典属性，输入域等的输入内容可以从该数据域中选择
	int PR_DATA_SOURCE = 431;

	/** Number of property constants defined */

	// 转换表
	int PR_TRANSFORM_TABLE = 432;

	// 节点pisition
	int PR_XPATH_POSITION = 433;

	// 节点pisition
	int PR_EDIT_BUTTON = 434;

	int PR_BUTTON_TYPE = 435;

	int PR_BUTTON_INSERT_POSITION = 436;

	int PR_CONN_WITH = 437;

	int PR_GROUP_REFERANCE = 438;

	int PR_GROUP_MAX_SELECTNUMBER = 439;

	int PR_GROUP_MIN_SELECTNUMBER = 440;

	int PR_GROUP_NONE_SELECT_VALUE = 441;

	// 艺术字类型
	int PR_WORDARTTEXT_TYPE = 442;

	// 绘制路径是否显示
	int PR_WORDARTTEXT_PATHVISABLE = 443;

	// 旋转角度。0-360整形值
	int PR_WORDARTTEXT_ROTATION = 444;

	// 起始位置，0-100整形值
	int PR_WORDARTTEXT_STARTPOSITION = 445;

	// 字符间距
	int PR_WORDARTTEXT_LETTERSPACE = 446;

	// 艺术字内容
	int PR_WORDARTTEXT_CONTENT = 447;

	int PR_ENCRYPT = 448;

	int PR_CONTENT_TREAT = 449;

	int PR_CURRENT_SIMPLE_PAGE_MASTER = 450;

	int PR_VIRTUAL_MASTER_NAME = 451;

	// 子模板文件名
	int PR_ZIMOBAN_NAME = 452;

	int PR_DBTYPE = 453;
	//序号类型支持阿拉伯数字，罗马数字等
	int PR_POSITION_NUMBER_TYPE = 454;
	//编辑控件，弹出浏览器控件
	int PR_POPUPBROWSER_INFO = 455;
	//按纽重复节点增加重复项时不带默认值
	int PR_BUTTON_NODATAXPTH = 456;
	// int PROPERTY_COUNT = 255; //.
	int PROPERTY_COUNT = 456; // .

	// compound property constants

	/** Property constant for compund property */
	int CP_BLOCK_PROGRESSION_DIRECTION = 1 << COMPOUND_SHIFT;

	/** Property constant for compund property */
	int CP_CONDITIONALITY = 2 << COMPOUND_SHIFT;

	/** Property constant for compund property */
	int CP_INLINE_PROGRESSION_DIRECTION = 3 << COMPOUND_SHIFT;

	/** Property constant for compund property */
	int CP_LENGTH = 4 << COMPOUND_SHIFT;

	/** Property constant for compund property */
	int CP_MAXIMUM = 5 << COMPOUND_SHIFT;

	/** Property constant for compund property */
	int CP_MINIMUM = 6 << COMPOUND_SHIFT;

	/** Property constant for compund property */
	int CP_OPTIMUM = 7 << COMPOUND_SHIFT;

	/** Property constant for compund property */
	int CP_PRECEDENCE = 8 << COMPOUND_SHIFT;

	/** Property constant for compund property */
	int CP_WITHIN_COLUMN = 9 << COMPOUND_SHIFT;

	/** Property constant for compund property */
	int CP_WITHIN_LINE = 10 << COMPOUND_SHIFT;

	/** Property constant for compund property */
	int CP_WITHIN_PAGE = 11 << COMPOUND_SHIFT;

	// Enumeration constants
	/** Enumeration constant */
	int EN_ABSOLUTE = 1;

	/** Enumeration constant */
	int EN_ABSOLUTE_COLORMETRIC = 2;

	/** Enumeration constant */
	int EN_AFTER = 3;

	/** Enumeration constant */
	int EN_AFTER_EDGE = 4;

	/** Enumeration constant */
	int EN_ALL = 5;

	/** Enumeration constant */
	int EN_ALPHABETIC = 6;

	/** Enumeration constant */
	int EN_ALWAYS = 7;

	/** Enumeration constant */
	int EN_ANY = 8;

	/** Enumeration constant */
	int EN_AUTO = 9;

	/** Enumeration constant */
	int EN_AUTO_EVEN = 10;

	/** Enumeration constant */
	int EN_AUTO_ODD = 11;

	/** Enumeration constant */
	int EN_BASELINE = 12;

	/** Enumeration constant */
	int EN_BEFORE = 13;

	/** Enumeration constant */
	int EN_BEFORE_EDGE = 14;

	/** Enumeration constant */
	int EN_BIDI_OVERRIDE = 15;

	/** Enumeration constant */
	int EN_BLANK = 16;

	/** Enumeration constant */
	int EN_BLINK = 17;

	/** Enumeration constant */
	int EN_BLOCK = 18;

	/** Enumeration constant */
	int EN_BOTH = 19;

	/** Enumeration constant */
	int EN_BOTTOM = 20;

	/** Enumeration constant */
	int EN_BOUNDED_IN_ONE_DIMENSION = 21;

	/** Enumeration constant */
	int EN_CAPITALIZE = 22;

	/** Enumeration constant */
	int EN_CENTER = 23;

	/** Enumeration constant */
	int EN_CENTRAL = 24;

	/** Enumeration constant */
	int EN_CHARACTER_BY_CHARACTER = 25;

	/** Enumeration constant */
	int EN_COLLAPSE = 26;

	/** Enumeration constant */
	int EN_COLLAPSE_WITH_PRECEDENCE = 27;

	/** Enumeration constant */
	int EN_COLUMN = 28;

	/** Enumeration constant */
	int EN_CONDENSED = 29;

	/** Enumeration constant */
	int EN_CONSIDER_SHIFTS = 30;

	/** Enumeration constant */
	int EN_DASHED = 31;

	/** Enumeration constant */
	int EN_DISCARD = 32;

	/** Enumeration constant */
	int EN_DISREGARD_SHIFTS = 33;

	/** Enumeration constant */
	int EN_DOCUMENT = 34;

	/** Enumeration constant */
	int EN_DOTS = 35;

	/** Enumeration constant */
	int EN_DOTTED = 36;

	/** Enumeration constant */
	int EN_DOUBLE = 37;

	/** Enumeration constant */
	int EN_EMBED = 38;

	/** Enumeration constant */
	int EN_END = 39;

	/** Enumeration constant */
	int EN_END_ON_EVEN = 40;

	/** Enumeration constant */
	int EN_END_ON_ODD = 41;

	/** Enumeration constant */
	int EN_ERROR_IF_OVERFLOW = 42;

	/** Enumeration constant */
	int EN_EVEN = 43;

	/** Enumeration constant */
	int EN_EVEN_PAGE = 44;

	/** Enumeration constant */
	int EN_EXPANDED = 45;

	/** Enumeration constant */
	int EN_EXTRA_CONDENSED = 46;

	/** Enumeration constant */
	int EN_EXTRA_EXPANDED = 47;

	/** Enumeration constant */
	int EN_FALSE = 48;

	/** Enumeration constant */
	int EN_FIC = 49;

	/** Enumeration constant */
	int EN_FIRST = 50;

	/** Enumeration constant */
	int EN_FIXED = 51;

	/** Enumeration constant */
	int EN_FONT_HEIGHT = 52;

	/** Enumeration constant */
	int EN_FORCE = 53;

	/** Enumeration constant */
	int EN_FSWP = 54;

	/** Enumeration constant */
	int EN_GROOVE = 55;

	/** Enumeration constant */
	int EN_HANGING = 56;

	/** Enumeration constant */
	int EN_HIDDEN = 57;

	/** Enumeration constant */
	int EN_HIDE = 58;

	/** Enumeration constant */
	int EN_IDEOGRAPHIC = 59;

	/** Enumeration constant */
	int EN_IGNORE = 60;

	/** Enumeration constant */
	int EN_IGNORE_IF_AFTER_LINEFEED = 61;

	/** Enumeration constant */
	int EN_IGNORE_IF_BEFORE_LINEFEED = 62;

	/** Enumeration constant */
	int EN_IGNORE_IF_SURROUNDING_LINEFEED = 63;

	/** Enumeration constant */
	int EN_INDEFINITE = 64;

	/** Enumeration constant */
	int EN_INDENT = 65;

	/** Enumeration constant */
	int EN_INHERIT = 66;

	/** Enumeration constant */
	int EN_INSET = 67;

	/** Enumeration constant */
	int EN_INSIDE = 68;

	/** Enumeration constant */
	int EN_INTEGER_PIXELS = 69;

	/** Enumeration constant */
	int EN_JUSTIFY = 70;

	/** Enumeration constant */
	int EN_LARGER = 71;

	/** Enumeration constant */
	int EN_LAST = 72;

	/** Enumeration constant */
	int EN_LEFT = 73;

	/** Enumeration constant */
	int EN_LEWP = 74;

	/** Enumeration constant */
	int EN_LINE = 75;

	/** Enumeration constant */
	int EN_LINE_HEIGHT = 76;

	/** Enumeration constant */
	int EN_LINE_THROUGH = 77;

	/** Enumeration constant */
	int EN_LOWERCASE = 78;

	/** Enumeration constant */
	int EN_LR_TB = 79;

	/** Enumeration constant */
	int EN_LTR = 80;

	/** Enumeration constant */
	int EN_LSWP = 81;

	/** Enumeration constant */
	int EN_MATHEMATICAL = 82;

	/** Enumeration constant */
	int EN_MAX_HEIGHT = 83;

	/** Enumeration constant */
	int EN_MIDDLE = 84;

	/** Enumeration constant */
	int EN_NARROWER = 85;

	/** Enumeration constant */
	int EN_NO_BLINK = 86;

	/** Enumeration constant */
	int EN_NO_CHANGE = 87;

	/** Enumeration constant */
	int EN_NO_FORCE = 88;

	/** Enumeration constant */
	int EN_NO_LIMIT = 89;

	/** Enumeration constant */
	int EN_NO_LINE_THROUGH = 90;

	/** Enumeration constant */
	int EN_NO_OVERLINE = 91;

	/** Enumeration constant */
	int EN_NO_UNDERLINE = 92;

	/** Enumeration constant */
	int EN_NO_WRAP = 93;

	/** Enumeration constant */
	int EN_NON_UNIFORM = 94;

	/** Enumeration constant */
	int EN_NONE = 95;

	/** Enumeration constant */
	int EN_NOREPEAT = 96;

	/** Enumeration constant */
	int EN_NORMAL = 97;

	/** Enumeration constant */
	int EN_NOT_BLANK = 98;

	/** Enumeration constant */
	int EN_ODD = 99;

	/** Enumeration constant */
	int EN_ODD_PAGE = 100;

	/** Enumeration constant */
	int EN_OUTSET = 101;

	/** Enumeration constant */
	int EN_OUTSIDE = 102;

	/** Enumeration constant */
	int EN_OVERLINE = 103;

	/** Enumeration constant */
	int EN_PAGE = 104;

	/** Enumeration constant */
	int EN_PAGE_SEQUENCE = 105;

	/** Enumeration constant */
	int EN_PAGINATE = 106;

	/** Enumeration constant */
	int EN_PERCEPTUAL = 107;

	/** Enumeration constant */
	int EN_PRESERVE = 108;

	/** Enumeration constant */
	int EN_REFERENCE_AREA = 109;

	/** Enumeration constant */
	int EN_RELATIVE = 110;

	/** Enumeration constant */
	int EN_RELATIVE_COLOMETRIC = 111;

	/** Enumeration constant */
	int EN_REPEAT = 112;

	/** Enumeration constant */
	int EN_REPEATX = 113;

	/** Enumeration constant */
	int EN_REPEATY = 114;

	/** Enumeration constant */
	int EN_RESAMPLE_ANY_METHOD = 115;

	/** Enumeration constant */
	int EN_RESET_SIZE = 116;

	/** Enumeration constant */
	int EN_REST = 117;

	/** Enumeration constant */
	int EN_RETAIN = 118;

	/** Enumeration constant */
	int EN_RIDGE = 119;

	/** Enumeration constant */
	int EN_RIGHT = 120;

	/** Enumeration constant */
	int EN_RL_TB = 121;

	/** Enumeration constant */
	int EN_RTL = 122;

	/** Enumeration constant */
	int EN_RULE = 123;

	/** Enumeration constant */
	int EN_SATURATION = 124;

	/** Enumeration constant */
	int EN_SCALE_TO_FIT = 125;

	/** Enumeration constant */
	int EN_SCROLL = 126;

	/** Enumeration constant */
	int EN_SEMI_CONDENSED = 127;

	/** Enumeration constant */
	int EN_SEMI_EXPANDED = 128;

	/** Enumeration constant */
	int EN_SEPARATE = 129;

	/** Enumeration constant */
	int EN_SHOW = 130;

	/** Enumeration constant */
	int EN_SMALL_CAPS = 131;

	/** Enumeration constant */
	int EN_SMALLER = 132;

	/** Enumeration constant */
	int EN_SOLID = 133;

	/** Enumeration constant */
	int EN_SPACE = 134;

	/** Enumeration constant */
	int EN_START = 135;

	/** Enumeration constant */
	int EN_STATIC = 136;

	/** Enumeration constant */
	int EN_SUB = 137;

	/** Enumeration constant */
	int EN_SUPER = 138;

	/** Enumeration constant */
	int EN_SUPPRESS = 139;

	/** Enumeration constant */
	int EN_TB_RL = 140;

	/** Enumeration constant */
	int EN_TEXT_AFTER_EDGE = 141;

	/** Enumeration constant */
	int EN_TEXT_BEFORE_EDGE = 142;

	/** Enumeration constant */
	int EN_TEXT_BOTTOM = 143;

	/** Enumeration constant */
	int EN_TEXT_TOP = 144;

	/** Enumeration constant */
	int EN_TOP = 145;

	/** Enumeration constant */
	int EN_TRADITIONAL = 146;

	/** Enumeration constant */
	int EN_TREAT_AS_SPACE = 147;

	/** Enumeration constant */
	int EN_TREAT_AS_ZERO_WIDTH_SPACE = 148;

	/** Enumeration constant */
	int EN_TRUE = 149;

	/** Enumeration constant */
	int EN_ULTRA_CONDENSED = 150;

	/** Enumeration constant */
	int EN_ULTRA_EXPANDED = 151;

	/** Enumeration constant */
	int EN_UNBOUNDED = 152;

	/** Enumeration constant */
	int EN_UNDERLINE = 153;

	/** Enumeration constant */
	int EN_UNIFORM = 154;

	/** Enumeration constant */
	int EN_UPPERCASE = 155;

	/** Enumeration constant */
	int EN_USE_FONT_METRICS = 156;

	/** Enumeration constant */
	int EN_USE_SCRIPT = 157;

	/** Enumeration constant */
	int EN_USE_CONTENT = 158;

	/** Enumeration constant */
	int EN_VISIBLE = 159;

	/** Enumeration constant */
	int EN_WIDER = 160;

	/** Enumeration constant */
	int EN_WRAP = 161;

	/** Enumeration constant - non-standard for display-align */
	int EN_X_FILL = 162;

	/** Enumeration constant - non-standard for display-align */
	int EN_X_DISTRIBUTE = 163;

	/** Enumeration constant */
	int EN_ITALIC = 164;

	/** Enumeration constant */
	int EN_OBLIQUE = 165;

	/** Enumeration constant */
	int EN_BACKSLANT = 166;

	/** Enumeration constant */
	int EN_BOLDER = 167;

	/** Enumeration constant */
	int EN_LIGHTER = 168;

	/** Enumeration constant */
	int EN_100 = 168;

	/** Enumeration constant */
	int EN_200 = 169;

	/** Enumeration constant */
	int EN_300 = 170;

	/** Enumeration constant */
	int EN_400 = 171;

	/** Enumeration constant */
	int EN_500 = 172;

	/** Enumeration constant */
	int EN_600 = 173;

	/** Enumeration constant */
	int EN_700 = 174;

	/** Enumeration constant */
	int EN_800 = 175;

	/** Enumeration constant */
	int EN_900 = 176;

	/** Enumeration constant -- page-break-shorthand */
	int EN_AVOID = 177;

	/** Enumeration constant -- white-space shorthand */
	int EN_PRE = 178;

	/** Enumeration constant -- font shorthand */
	int EN_CAPTION = 179;

	/** Enumeration constant -- font shorthand */
	int EN_ICON = 180;

	/** Enumeration constant -- font shorthand */
	int EN_MENU = 181;

	/** Enumeration constant -- font shorthand */
	int EN_MESSAGE_BOX = 182;

	/** Enumeration constant -- font shorthand */
	int EN_SMALL_CAPTION = 183;

	/** Enumeration constant -- font shorthand */
	int EN_STATUS_BAR = 184;

	// add by zq
	// 数据节点编辑属性
	// 不可编辑
	int EN_UNEDITABLE = 185;

	// 可编辑
	int EN_EDITABLE = 186;

	// 由数据节点确定
	int EN_DATANODE = 187;

	// add end

	// add by zhongyajun
	// code39
	int EN_CODE39 = 188;

	// code128
	int EN_CODE128 = 189;

	// codeEAN
	int EN_EAN128 = 190;

	// code 2 of 5
	int EN_2OF5 = 191;

	int EN_A = 192;

	int EN_B = 193;

	int EN_C = 194;

	int EN_EAN13 = 195;

	int EN_EAN8 = 196;

	int EN_UPCA = 197;

	int EN_UPCE = 198;

	// 中文数字(不带元角分)
	int EN_CHINESELOWERCASE = 199;

	// 中文数字(带元角分)
	int EN_CHINESECAPITAL = 200;

	// 由变量决定
	int EN_EDITVARIBLE = 201;

	// 不可添加不可删除
	int EN_NOADDANDDELETE = 202;

	// 可添加
	int EN_CANADD = 203;

	// 可删除
	int EN_CANDELETE = 204;

	// 可添加删除
	int EN_CANADDANDDELETE = 205;

	/** Enumeration constant */
	int EN_ONLY = 206;

	// 二维条码PDF417类型
	int EN_PDF417 = 207;

	// 统计图类型
	// 柱状图
	int EN_BARCHART = 208;

	// 饼状图
	int EN_PIECHART = 209;

	// 折线图
	int EN_LINECHART = 210;

	// 堆积图
	int EN_STACKBARCHART = 211;

	// 面积图
	int EN_AREACHART = 212;

	// 垂直方向
	int EN_VERTICAL = 213;

	// 水平方向
	int EN_HORIZONTAL = 214;

	// 顺时针
	int EN_CLOCKWISE = 215;

	// 逆时针
	int EN_INVERT = 216;

	// add end

	// 编辑相关的枚举值开始，add by zq
	// 输入框
	int EN_INPUT = 217;

	// 下拉列表
	int EN_SELECT = 218;

	// 日期时间控件
	int EN_DATE = 219;

	// 选择按钮
	int EN_CHECKBOX = 220;

	// 文本域
	int EN_TEXT = 221;

	// 密码域
	int EN_PASSWORD = 222;

	// 正序
	int EN_SORT_P = 223;

	// 逆序
	int EN_SORT_N = 224;

	// 数据序（默认）
	int EN_SORT_C = 225;

	// 选择型
	int EN_DATE_TYPE_C = 226;

	// 手动输入型
	int EN_DATE_TYPE_T = 227;

	//
	int EN_FULL = 228;

	int EN_COMPACT = 229;

	int EN_MINIMAL = 230;

	int EN_CHECKBOX_BOXSTYLE_CIRCLE = 231;

	int EN_CHECKBOX_BOXSTYLE_SQUARE = 232;

	int EN_CHECKBOX_TICKMARK_TICK = 233;

	int EN_CHECKBOX_TICKMARK_DOT = 234;

	// 按钮
	int EN_BUTTON = 235;

	// table1型数据，wdems相关属性
	int EN_TABLE1 = 236;

	// table2型数据，wdems相关属性
	int EN_TABLE2 = 237;

	// 树形型数据，wdems相关属性
	int EN_TREE = 238;

	// datasource的bond属性的sq值
	int EN_DATASOURCE_SQ = 239;

	// datasource的bond属性的eq值
	int EN_DATASOURCE_EQ = 240;

	// datasource的bond属性的vert值
	int EN_DATASOURCE_VERT = 241;

	// 列表类型：combobox枚举值
	int EN_COMBOBOX = 242;

	// 列表类型：list枚举值
	int EN_LIST = 243;

	int EN_GROUP = 244;

	int EN_WORDARTTEXT_TYPE_LINE = 245;

	int EN_WORDARTTEXT_TYPE_ZHEXIAN2 = 246;

	int EN_WORDARTTEXT_TYPE_ZHEXIAN3 = 247;

	int EN_WORDARTTEXT_TYPE_BEZIERCURVES2 = 248;

	int EN_WORDARTTEXT_TYPE_BEZIERCURVES3 = 249;

	int EN_WORDARTTEXT_TYPE_ELLIPSEINNER = 250;

	int EN_WORDARTTEXT_TYPE_ELLIPSEUP = 251;

	int EN_WORDARTTEXT_TYPE_ELLIPSEDOWN = 252;

	int EN_WORDARTTEXT_TYPE_ELLIPSE = 253;

	int EN_CHINESECAPITAL_ZC = 254;

	int EN_CHECK = 255;

	int EN_UNCHECK = 256;
	//TableCell,BlockContainer等没有设置边框时，默认显示的边框类型，以显示格线
	int EN_NOBORDER = 257;
	//序号类型，阿拉伯数字
	int EN_POSITION_NUMBER_1 = 258;
	//小写字母
	int EN_POSITION_NUMBER_a = 259;
	//大写字母
	int EN_POSITION_NUMBER_A = 260;
	//罗马数字
	int EN_POSITION_NUMBER_i = 261;
	//罗马大写数字
	int EN_POSITION_NUMBER_I = 262;
	//带整字不带单位的货币表示法
	int EN_CHINESELOWERCASE_ADDZHENG=263;
	//带整字带货币单位的货币表示法
	int EN_CHINESECAPITAL_ADDZHENG=264;
	//带整字带货币单位带进制单位的货币表示法
	int EN_CHINESECAPITAL_ZC_ADDZHENG=265;
	//溢出时自动缩小字号
	int EN_AUTOFONTSIZE=266;
	//弹出网页控件
	int EN_POPUPBROWSER = 267;
	//图片可编辑
	int EN_GRAPHIC = 268;
	// 编辑相关的枚举值结束
	/** Number of enumeration constants defined */
	int ENUM_COUNT = 268;

	Object NULLOBJECT = new Object();
}
