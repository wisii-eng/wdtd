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

package com.wisii.wisedoc.io.xsl.util;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.BasicLink;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.Leader;
import com.wisii.wisedoc.document.PageNumber;
import com.wisii.wisedoc.document.PageNumberCitation;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableColumn;
import com.wisii.wisedoc.document.TableFooter;
import com.wisii.wisedoc.document.TableHeader;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.document.attribute.LengthPairProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.NumberProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.RegionAfter;
import com.wisii.wisedoc.document.attribute.RegionBefore;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.RegionEnd;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.LengthBase;

public class DefaultValueMap
{

	static boolean flg = false;

	static Map<Integer, Object> initmap = new HashMap<Integer, Object>();

	public static void setInitMap()
	{
		final EnumProperty auto = new EnumProperty(Constants.EN_AUTO, "AUTO");
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
		initmap.put(Constants.PR_COLOR, null);
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
		initmap.put(Constants.PR_BACKGROUND_REPEAT, new EnumProperty(
				Constants.EN_REPEAT, ""));
		final PercentLength poshlen = new PercentLength(0.0d, new LengthBase(
				LengthBase.IMAGE_BACKGROUND_POSITION_HORIZONTAL));
		final PercentLength posvlen = new PercentLength(0.0d, new LengthBase(
				LengthBase.IMAGE_BACKGROUND_POSITION_VERTICAL));
		initmap.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, poshlen);
		initmap.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, posvlen);
		final FixedLength length = new FixedLength(0.0d, "pt");
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
		initmap.put(Constants.PR_BASELINE_SHIFT, new EnumLength(
				Constants.EN_BASELINE, null));
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
		initmap.put(Constants.PR_LINE_HEIGHT, new EnumProperty(
				Constants.EN_NORMAL, ""));
		initmap.put(Constants.PR_LINE_STACKING_STRATEGY,
				Constants.EN_MAX_HEIGHT);
		initmap.put(Constants.PR_LINEFEED_TREATMENT,
				Constants.EN_TREAT_AS_SPACE);
		initmap.put(Constants.PR_WHITE_SPACE_TREATMENT,
				Constants.EN_IGNORE_IF_SURROUNDING_LINEFEED);
		final EnumProperty startenum = new EnumProperty(Constants.EN_START, "");
		initmap.put(Constants.PR_TEXT_ALIGN, startenum);
		initmap.put(Constants.PR_TEXT_ALIGN_LAST, startenum);
		initmap.put(Constants.PR_TEXT_INDENT, length);
		initmap.put(Constants.PR_WHITE_SPACE_COLLAPSE, Constants.EN_TRUE);
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
		final EnumProperty enmu = new EnumProperty(Constants.EN_AUTO, "");
		final KeepProperty keep = new KeepProperty(enmu, enmu, enmu);
		initmap.put(Constants.PR_KEEP_TOGETHER, keep);
		initmap.put(Constants.PR_KEEP_WITH_NEXT, keep);
		initmap.put(Constants.PR_KEEP_WITH_PREVIOUS, keep);
		initmap.put(Constants.PR_WIDOWS, 2);
		initmap.put(Constants.PR_ORPHANS, 2);
		initmap.put(Constants.PR_CLIP, auto);
		initmap.put(Constants.PR_OVERFLOW, auto);
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
		initmap.put(Constants.PR_STARTING_STATE, Constants.EN_SHOW);
		initmap.put(Constants.PR_INDEX_CLASS, "");
		initmap.put(Constants.PR_MARKER_CLASS_NAME, "");
		initmap.put(Constants.PR_RETRIEVE_CLASS_NAME, "");
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
		initmap.put(Constants.PR_TEXT_ALTITUDE, Constants.EN_USE_FONT_METRICS);
		initmap.put(Constants.PR_TEXT_DEPTH, Constants.EN_USE_FONT_METRICS);
		initmap.put(Constants.PR_UNICODE_BIDI, Constants.EN_NORMAL);
		initmap.put(Constants.PR_WRITING_MODE, new EnumProperty(
				Constants.EN_LR_TB, ""));
		initmap.put(Constants.PR_CONTENT_TYPE, auto);
		initmap.put(Constants.PR_TEXT_ALTITUDE, Constants.EN_USE_FONT_METRICS);
		initmap.put(Constants.PR_PROVISIONAL_LABEL_SEPARATION, new FixedLength(
				6d, "pt"));
		initmap.put(Constants.PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS,
				new FixedLength(24d, "pt"));
		initmap.put(Constants.PR_SCORE_SPACES, Constants.EN_TRUE);
		initmap.put(Constants.PR_VISIBILITY, Constants.EN_VISIBLE);
		initmap.put(Constants.PR_Z_INDEX, auto);
		initmap.put(Constants.PR_BORDER_COLLAPSE, new EnumProperty(
				Constants.EN_COLLAPSE, "SEPARATE"));
		initmap.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
				new LengthRangeProperty(enumlength));
		initmap.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
				new LengthRangeProperty(enumlength));
		initmap.put(Constants.PR_COLUMN_NUMBER, new NumberProperty(1));
		initmap.put(Constants.PR_BORDER_SEPARATION, new LengthPairProperty(
				new FixedLength(0), new FixedLength(0)));
		initmap.put(Constants.PR_EMPTY_CELLS, new EnumProperty(
				Constants.EN_SHOW, "SHOW"));
		initmap.put(Constants.PR_INITIAL_PAGE_NUMBER, new EnumNumber(
				Constants.EN_AUTO, 1));
		setMapType();
	}

	static Map<Integer, Object> mappage_sequence;

	static int[] keypage_sequence =
	{ Constants.PR_COUNTRY, /* Constants.PR_FLOW_MAP_REFERENCE, */
	Constants.PR_FORMAT, Constants.PR_LANGUAGE, Constants.PR_LETTER_VALUE,
			Constants.PR_GROUPING_SEPARATOR, Constants.PR_GROUPING_SIZE,
			Constants.PR_ID, Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_INITIAL_PAGE_NUMBER, Constants.PR_FORCE_PAGE_COUNT,
			Constants.PR_MASTER_REFERENCE, Constants.PR_REFERENCE_ORIENTATION,
			Constants.PR_WRITING_MODE };

	static Map<Integer, Object> mapsimple_page_master;

	static int[] keysimple_page_master =
	{ Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_MARGIN_RIGHT,
			Constants.PR_SPACE_BEFORE, Constants.PR_SPACE_AFTER,
			Constants.PR_START_INDENT, Constants.PR_END_INDENT,
			Constants.PR_MASTER_NAME, Constants.PR_PAGE_HEIGHT,
			Constants.PR_PAGE_WIDTH, Constants.PR_REFERENCE_ORIENTATION,
			Constants.PR_WRITING_MODE };

	static Map<Integer, Object> mapregion_body;

	static int[] keyregion_body =
	{ Constants.PR_BACKGROUND_ATTACHMENT, Constants.PR_BACKGROUND_COLOR,
			Constants.PR_BACKGROUND_IMAGE, Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_MARGIN_RIGHT,
			Constants.PR_SPACE_BEFORE, Constants.PR_SPACE_AFTER,
			Constants.PR_START_INDENT, Constants.PR_END_INDENT,
			Constants.PR_CLIP, Constants.PR_COLUMN_COUNT,
			Constants.PR_COLUMN_GAP, Constants.PR_DISPLAY_ALIGN,
			Constants.PR_OVERFLOW, Constants.PR_REGION_NAME,
			Constants.PR_REFERENCE_ORIENTATION, Constants.PR_WRITING_MODE };

	static Map<Integer, Object> mapregion_before;

	static int[] keyregion_before =
	{ Constants.PR_BACKGROUND_ATTACHMENT, Constants.PR_BACKGROUND_COLOR,
			Constants.PR_BACKGROUND_IMAGE, Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_CLIP, Constants.PR_DISPLAY_ALIGN, Constants.PR_EXTENT,
			Constants.PR_OVERFLOW, Constants.PR_PRECEDENCE,
			Constants.PR_REGION_NAME, Constants.PR_REFERENCE_ORIENTATION,
			Constants.PR_WRITING_MODE };

	static Map<Integer, Object> mapregion_after;

	static int[] keyregion_after =
	{ Constants.PR_BACKGROUND_ATTACHMENT, Constants.PR_BACKGROUND_COLOR,
			Constants.PR_BACKGROUND_IMAGE, Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_CLIP, Constants.PR_DISPLAY_ALIGN, Constants.PR_EXTENT,
			Constants.PR_OVERFLOW, Constants.PR_PRECEDENCE,
			Constants.PR_REGION_NAME, Constants.PR_REFERENCE_ORIENTATION,
			Constants.PR_WRITING_MODE };

	static Map<Integer, Object> mapregion_start;

	static int[] keyregion_start =
	{ Constants.PR_BACKGROUND_ATTACHMENT, Constants.PR_BACKGROUND_COLOR,
			Constants.PR_BACKGROUND_IMAGE, Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_CLIP, Constants.PR_DISPLAY_ALIGN, Constants.PR_EXTENT,
			Constants.PR_OVERFLOW, Constants.PR_PRECEDENCE,
			Constants.PR_REGION_NAME, Constants.PR_REFERENCE_ORIENTATION,
			Constants.PR_WRITING_MODE };

	static Map<Integer, Object> mapregion_end;

	static int[] keyregion_end =
	{ Constants.PR_BACKGROUND_ATTACHMENT, Constants.PR_BACKGROUND_COLOR,
			Constants.PR_BACKGROUND_IMAGE, Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_CLIP, Constants.PR_DISPLAY_ALIGN, Constants.PR_EXTENT,
			Constants.PR_OVERFLOW, Constants.PR_PRECEDENCE,
			Constants.PR_REGION_NAME, Constants.PR_REFERENCE_ORIENTATION,
			Constants.PR_WRITING_MODE };

	static Map<Integer, Object> mapdocument;

	static int[] keydocument =
	{ Constants.PR_WHITE_SPACE_COLLAPSE, Constants.PR_WHITE_SPACE_TREATMENT,
			Constants.PR_LINEFEED_TREATMENT };

	static Map<Integer, Object> mapblock;

	static int[] keyblock =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_FONT_FAMILY, Constants.PR_FONT_SELECTION_STRATEGY,
			Constants.PR_FONT_SIZE, Constants.PR_FONT_STRETCH,
			Constants.PR_FONT_SIZE_ADJUST, Constants.PR_FONT_STYLE,
			Constants.PR_FONT_VARIANT, Constants.PR_FONT_WEIGHT,
			Constants.PR_COUNTRY, Constants.PR_LANGUAGE, Constants.PR_SCRIPT,
			Constants.PR_HYPHENATE,
			/*
			 * Constants.PR_HYPHENATE_CHARACTER,
			 * Constants.PR_HYPHENATE_PUSH_CHARACTER_COUNT,
			 * Constants.PR_HYPHENATE_REMAIN_CHARACTER_COUNT,
			 */
			Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_MARGIN_RIGHT,
			Constants.PR_SPACE_BEFORE, Constants.PR_SPACE_AFTER,
			Constants.PR_START_INDENT, Constants.PR_END_INDENT,
			Constants.PR_TOP, Constants.PR_RIGHT, Constants.PR_BOTTOM,
			Constants.PR_LEFT, Constants.PR_BREAK_BEFORE, Constants.PR_CLEAR,
			Constants.PR_COLOR, Constants.PR_TEXT_DEPTH,
			Constants.PR_TEXT_ALTITUDE, Constants.PR_HYPHENATION_KEEP,
			Constants.PR_HYPHENATION_LADDER_COUNT, Constants.PR_ID,
			Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_INTRUSION_DISPLACE, Constants.PR_KEEP_TOGETHER,
			Constants.PR_KEEP_WITH_NEXT, Constants.PR_KEEP_WITH_PREVIOUS,
			Constants.PR_LAST_LINE_END_INDENT, Constants.PR_LINEFEED_TREATMENT,
			Constants.PR_LINE_HEIGHT,
			Constants.PR_LINE_HEIGHT_SHIFT_ADJUSTMENT,
			Constants.PR_LINE_STACKING_STRATEGY, Constants.PR_ORPHANS,
			Constants.PR_WHITE_SPACE_TREATMENT, Constants.PR_SPAN,
			Constants.PR_TEXT_ALIGN, Constants.PR_TEXT_ALIGN_LAST,
			Constants.PR_TEXT_INDENT, Constants.PR_VISIBILITY,
			Constants.PR_WHITE_SPACE_COLLAPSE, Constants.PR_WIDOWS,
			Constants.PR_WRAP_OPTION };

	static Map<Integer, Object> mapblockcontainer;

	static int[] keyblockcontainer =
	{ Constants.PR_ABSOLUTE_POSITION, Constants.PR_TOP, Constants.PR_RIGHT,
			Constants.PR_BOTTOM, Constants.PR_LEFT,
			Constants.PR_BACKGROUND_ATTACHMENT, Constants.PR_BACKGROUND_COLOR,
			Constants.PR_BACKGROUND_IMAGE, Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_MARGIN_RIGHT,
			Constants.PR_SPACE_BEFORE, Constants.PR_SPACE_AFTER,
			Constants.PR_START_INDENT, Constants.PR_END_INDENT,
			Constants.PR_BLOCK_PROGRESSION_DIMENSION, Constants.PR_BREAK_AFTER,
			Constants.PR_BREAK_BEFORE, Constants.PR_CLEAR, Constants.PR_CLIP,
			Constants.PR_DISPLAY_ALIGN, Constants.PR_HEIGHT, Constants.PR_ID,
			Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_INLINE_PROGRESSION_DIMENSION,
			Constants.PR_INTRUSION_DISPLACE, Constants.PR_KEEP_TOGETHER,
			Constants.PR_KEEP_WITH_NEXT, Constants.PR_KEEP_WITH_PREVIOUS,
			Constants.PR_OVERFLOW, Constants.PR_REFERENCE_ORIENTATION,
			Constants.PR_SPAN, Constants.PR_WIDTH, Constants.PR_WRITING_MODE,
			Constants.PR_Z_INDEX };

	static Map<Integer, Object> mapexternal_graphic;

	static int[] keyexternal_graphic =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_MARGIN_RIGHT,
			Constants.PR_SPACE_END, Constants.PR_SPACE_START, Constants.PR_TOP,
			Constants.PR_RIGHT, Constants.PR_BOTTOM, Constants.PR_LEFT,
			Constants.PR_ALIGNMENT_ADJUST, Constants.PR_ALIGNMENT_BASELINE,
			/*
			 * Constants.PR_ALLOWED_HEIGHT_SCALE,
			 * Constants.PR_ALLOWED_WIDTH_SCALE,
			 */Constants.PR_BASELINE_SHIFT,
			Constants.PR_BLOCK_PROGRESSION_DIMENSION, Constants.PR_CLIP,
			Constants.PR_CONTENT_HEIGHT, Constants.PR_CONTENT_TYPE,
			Constants.PR_CONTENT_WIDTH, Constants.PR_DISPLAY_ALIGN,
			Constants.PR_DOMINANT_BASELINE, Constants.PR_HEIGHT,
			Constants.PR_ID, Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_INLINE_PROGRESSION_DIMENSION,
			Constants.PR_KEEP_WITH_NEXT, Constants.PR_KEEP_WITH_PREVIOUS,
			Constants.PR_LINE_HEIGHT, Constants.PR_OVERFLOW,
			Constants.PR_SCALING, Constants.PR_SCALING_METHOD,
			Constants.PR_SRC, Constants.PR_TEXT_ALIGN, Constants.PR_WIDTH };
	static Map<Integer, Object> mapqianzhang;

	static int[] keyqianzhang =
	{ 
			Constants.PR_SRC };
	static Map<Integer, Object> mapinline;

	static int[] keyinline =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_FONT_FAMILY, Constants.PR_FONT_SELECTION_STRATEGY,
			Constants.PR_FONT_SIZE, Constants.PR_FONT_STRETCH,
			Constants.PR_FONT_SIZE_ADJUST, Constants.PR_FONT_STYLE,
			Constants.PR_FONT_VARIANT, Constants.PR_FONT_WEIGHT,
			Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_MARGIN_RIGHT,
			Constants.PR_SPACE_END, Constants.PR_SPACE_START, Constants.PR_TOP,
			Constants.PR_RIGHT, Constants.PR_BOTTOM, Constants.PR_LEFT,
			Constants.PR_ALIGNMENT_ADJUST, Constants.PR_ALIGNMENT_BASELINE,
			Constants.PR_BASELINE_SHIFT,
			Constants.PR_BLOCK_PROGRESSION_DIMENSION, Constants.PR_COLOR,
			Constants.PR_DOMINANT_BASELINE, Constants.PR_HEIGHT,
			Constants.PR_ID, Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_INLINE_PROGRESSION_DIMENSION,
			Constants.PR_KEEP_TOGETHER, Constants.PR_KEEP_WITH_NEXT,
			Constants.PR_KEEP_WITH_PREVIOUS, Constants.PR_LINE_HEIGHT,
			Constants.PR_TEXT_DECORATION, Constants.PR_VISIBILITY,
			Constants.PR_WIDTH, Constants.PR_WRAP_OPTION };

	static Map<Integer, Object> mapleader;

	static int[] keyleader =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_FONT_FAMILY, Constants.PR_FONT_SELECTION_STRATEGY,
			Constants.PR_FONT_SIZE, Constants.PR_FONT_STRETCH,
			Constants.PR_FONT_SIZE_ADJUST, Constants.PR_FONT_STYLE,
			Constants.PR_FONT_VARIANT, Constants.PR_FONT_WEIGHT,
			Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_MARGIN_RIGHT,
			Constants.PR_SPACE_END, Constants.PR_SPACE_START, Constants.PR_TOP,
			Constants.PR_RIGHT, Constants.PR_BOTTOM, Constants.PR_LEFT,
			Constants.PR_ALIGNMENT_ADJUST, Constants.PR_ALIGNMENT_BASELINE,
			Constants.PR_BASELINE_SHIFT, Constants.PR_COLOR,
			Constants.PR_DOMINANT_BASELINE, Constants.PR_TEXT_DEPTH,
			Constants.PR_TEXT_ALTITUDE, Constants.PR_ID,
			Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_KEEP_WITH_NEXT, Constants.PR_KEEP_WITH_PREVIOUS,
			Constants.PR_LEADER_ALIGNMENT, Constants.PR_LEADER_LENGTH,
			Constants.PR_LEADER_PATTERN, Constants.PR_LEADER_PATTERN_WIDTH,
			Constants.PR_RULE_STYLE, Constants.PR_RULE_THICKNESS,
			Constants.PR_LETTER_SPACING, Constants.PR_LINE_HEIGHT,
			Constants.PR_TEXT_SHADOW, Constants.PR_VISIBILITY,
			Constants.PR_WORD_SPACING };

	static Map<Integer, Object> mappage_number;

	static int[] keypage_number =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_FONT_FAMILY, Constants.PR_FONT_SELECTION_STRATEGY,
			Constants.PR_FONT_SIZE, Constants.PR_FONT_STRETCH,
			Constants.PR_FONT_SIZE_ADJUST, Constants.PR_FONT_STYLE,
			Constants.PR_FONT_VARIANT, Constants.PR_FONT_WEIGHT,
			Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_MARGIN_RIGHT,
			Constants.PR_SPACE_END, Constants.PR_SPACE_START, Constants.PR_TOP,
			Constants.PR_RIGHT, Constants.PR_BOTTOM, Constants.PR_LEFT,
			Constants.PR_ALIGNMENT_ADJUST, Constants.PR_ALIGNMENT_BASELINE,
			Constants.PR_BASELINE_SHIFT, Constants.PR_DOMINANT_BASELINE,
			Constants.PR_ID, Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_KEEP_WITH_NEXT, Constants.PR_KEEP_WITH_PREVIOUS,
			Constants.PR_LETTER_SPACING, Constants.PR_LINE_HEIGHT,
			Constants.PR_SCORE_SPACES, Constants.PR_TEXT_ALTITUDE,
			Constants.PR_TEXT_DECORATION, Constants.PR_TEXT_DEPTH,
			Constants.PR_TEXT_SHADOW, Constants.PR_TEXT_TRANSFORM,
			Constants.PR_VISIBILITY, Constants.PR_WORD_SPACING,
			Constants.PR_WRAP_OPTION };

	static Map<Integer, Object> mappage_number_citation;

	static int[] keypage_number_citation =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_FONT_FAMILY, Constants.PR_FONT_SELECTION_STRATEGY,
			Constants.PR_FONT_SIZE, Constants.PR_FONT_STRETCH,
			Constants.PR_FONT_SIZE_ADJUST, Constants.PR_FONT_STYLE,
			Constants.PR_FONT_VARIANT, Constants.PR_FONT_WEIGHT,
			Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_MARGIN_RIGHT,
			Constants.PR_SPACE_END, Constants.PR_SPACE_START, Constants.PR_TOP,
			Constants.PR_RIGHT, Constants.PR_BOTTOM, Constants.PR_LEFT,
			Constants.PR_ALIGNMENT_ADJUST, Constants.PR_ALIGNMENT_BASELINE,
			Constants.PR_BASELINE_SHIFT, Constants.PR_DOMINANT_BASELINE,
			Constants.PR_ID, Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_KEEP_WITH_NEXT, Constants.PR_KEEP_WITH_PREVIOUS,
			Constants.PR_LETTER_SPACING, Constants.PR_LINE_HEIGHT,
			Constants.PR_REF_ID, Constants.PR_SCORE_SPACES,
			Constants.PR_TEXT_ALTITUDE, Constants.PR_TEXT_DECORATION,
			Constants.PR_TEXT_DEPTH, Constants.PR_TEXT_SHADOW,
			Constants.PR_TEXT_TRANSFORM, Constants.PR_VISIBILITY,
			Constants.PR_WORD_SPACING, Constants.PR_WRAP_OPTION };

	static Map<Integer, Object> maptable;

	static int[] keytable =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_MARGIN_RIGHT,
			Constants.PR_SPACE_BEFORE, Constants.PR_SPACE_AFTER,
			Constants.PR_START_INDENT, Constants.PR_END_INDENT,
			Constants.PR_TOP, Constants.PR_RIGHT, Constants.PR_BOTTOM,
			Constants.PR_LEFT, Constants.PR_BLOCK_PROGRESSION_DIMENSION,
			Constants.PR_BORDER_AFTER_PRECEDENCE,
			Constants.PR_BORDER_BEFORE_PRECEDENCE,
			Constants.PR_BORDER_COLLAPSE, Constants.PR_BORDER_END_PRECEDENCE,
			Constants.PR_BORDER_SEPARATION,
			Constants.PR_BORDER_START_PRECEDENCE, Constants.PR_BREAK_AFTER,
			Constants.PR_BREAK_BEFORE, Constants.PR_CLEAR, Constants.PR_ID,
			Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_INLINE_PROGRESSION_DIMENSION,
			Constants.PR_INTRUSION_DISPLACE, Constants.PR_HEIGHT,
			Constants.PR_KEEP_TOGETHER, Constants.PR_KEEP_WITH_NEXT,
			Constants.PR_KEEP_WITH_PREVIOUS, Constants.PR_TABLE_LAYOUT,
			Constants.PR_TABLE_OMIT_FOOTER_AT_BREAK,
			Constants.PR_TABLE_OMIT_HEADER_AT_BREAK, Constants.PR_WIDTH,
			Constants.PR_WRITING_MODE };

	static Map<Integer, Object> maptable_column;

	static int[] keytable_column =
	{ Constants.PR_BORDER_AFTER_PRECEDENCE,
			Constants.PR_BORDER_BEFORE_PRECEDENCE,
			Constants.PR_BORDER_END_PRECEDENCE,
			Constants.PR_BORDER_START_PRECEDENCE, Constants.PR_COLUMN_NUMBER,
			Constants.PR_COLUMN_WIDTH, Constants.PR_NUMBER_COLUMNS_REPEATED,
			Constants.PR_NUMBER_COLUMNS_SPANNED, Constants.PR_VISIBILITY };

	static Map<Integer, Object> maptable_hearder;

	static int[] keytable_hearder =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_TOP, Constants.PR_RIGHT, Constants.PR_BOTTOM,
			Constants.PR_LEFT, Constants.PR_BORDER_AFTER_PRECEDENCE,
			Constants.PR_BORDER_BEFORE_PRECEDENCE,
			Constants.PR_BORDER_END_PRECEDENCE,
			Constants.PR_BORDER_START_PRECEDENCE, Constants.PR_ID,
			Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_VISIBILITY };

	static Map<Integer, Object> maptable_footer;

	static int[] keytable_footer =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_TOP, Constants.PR_RIGHT, Constants.PR_BOTTOM,
			Constants.PR_LEFT, Constants.PR_BORDER_AFTER_PRECEDENCE,
			Constants.PR_BORDER_BEFORE_PRECEDENCE,
			Constants.PR_BORDER_END_PRECEDENCE,
			Constants.PR_BORDER_START_PRECEDENCE, Constants.PR_ID,
			Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_VISIBILITY };

	static Map<Integer, Object> maptable_body;

	static int[] keytable_body =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_TOP, Constants.PR_RIGHT, Constants.PR_BOTTOM,
			Constants.PR_LEFT, Constants.PR_BORDER_AFTER_PRECEDENCE,
			Constants.PR_BORDER_BEFORE_PRECEDENCE,
			Constants.PR_BORDER_END_PRECEDENCE,
			Constants.PR_BORDER_START_PRECEDENCE, Constants.PR_ID,
			Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_VISIBILITY };

	static Map<Integer, Object> maptable_row;

	static int[] keytable_row =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_TOP, Constants.PR_RIGHT, Constants.PR_BOTTOM,
			Constants.PR_LEFT, Constants.PR_BORDER_AFTER_PRECEDENCE,
			Constants.PR_BORDER_BEFORE_PRECEDENCE,
			Constants.PR_BORDER_END_PRECEDENCE,
			Constants.PR_BORDER_START_PRECEDENCE, Constants.PR_BREAK_AFTER,
			Constants.PR_BREAK_BEFORE, Constants.PR_ID,
			Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_HEIGHT, Constants.PR_KEEP_TOGETHER,
			Constants.PR_KEEP_WITH_NEXT, Constants.PR_KEEP_WITH_PREVIOUS,
			Constants.PR_VISIBILITY };

	static Map<Integer, Object> maptable_cell;

	static int[] keytable_cell =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_TOP, Constants.PR_RIGHT, Constants.PR_BOTTOM,
			Constants.PR_LEFT, Constants.PR_BORDER_AFTER_PRECEDENCE,
			Constants.PR_BORDER_BEFORE_PRECEDENCE,
			Constants.PR_BORDER_END_PRECEDENCE,
			Constants.PR_BORDER_START_PRECEDENCE,
			Constants.PR_BLOCK_PROGRESSION_DIMENSION,
			Constants.PR_COLUMN_NUMBER, Constants.PR_DISPLAY_ALIGN,
			Constants.PR_RELATIVE_ALIGN, Constants.PR_EMPTY_CELLS,
			/* Constants.PR_EMPTY_ROW, */Constants.PR_HEIGHT, Constants.PR_ID,
			Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_INLINE_PROGRESSION_DIMENSION,
			Constants.PR_NUMBER_COLUMNS_SPANNED,
			Constants.PR_NUMBER_ROWS_SPANNED, Constants.PR_STARTS_ROW,
			Constants.PR_WIDTH };

	static Map<Integer, Object> mapbasic_link;

	static int[] keybasic_link =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_TOP, Constants.PR_RIGHT, Constants.PR_BOTTOM,
			Constants.PR_LEFT, Constants.PR_MARGIN_TOP,
			Constants.PR_MARGIN_BOTTOM, Constants.PR_MARGIN_LEFT,
			Constants.PR_MARGIN_RIGHT, Constants.PR_SPACE_END,
			Constants.PR_SPACE_START, Constants.PR_ALIGNMENT_ADJUST,
			Constants.PR_ALIGNMENT_BASELINE, Constants.PR_BASELINE_SHIFT,
			Constants.PR_DESTINATION_PLACEMENT_OFFSET,
			Constants.PR_DOMINANT_BASELINE, Constants.PR_EXTERNAL_DESTINATION,
			Constants.PR_ID, Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_INDICATE_DESTINATION,
			Constants.PR_INTERNAL_DESTINATION, Constants.PR_KEEP_TOGETHER,
			Constants.PR_KEEP_WITH_NEXT, Constants.PR_KEEP_WITH_PREVIOUS,
			Constants.PR_LINE_HEIGHT, Constants.PR_SHOW_DESTINATION,
			Constants.PR_TARGET_PROCESSING_CONTEXT,
			Constants.PR_TARGET_PRESENTATION_CONTEXT,
			Constants.PR_TARGET_STYLESHEET };

	static Map<Integer, Object> mapinstreamforeignobject;

	static int[] keyinstreamforeignobject =
	{ Constants.PR_SOURCE_DOCUMENT, Constants.PR_ROLE, Constants.PR_AZIMUTH,
			Constants.PR_CUE_AFTER, Constants.PR_CUE_BEFORE,
			Constants.PR_ELEVATION, Constants.PR_PAUSE_AFTER,
			Constants.PR_PAUSE_BEFORE, Constants.PR_PITCH,
			Constants.PR_PITCH_RANGE, Constants.PR_PLAY_DURING,
			Constants.PR_RICHNESS, Constants.PR_SPEAK,
			Constants.PR_SPEAK_HEADER, Constants.PR_SPEAK_NUMERAL,
			Constants.PR_SPEAK_PUNCTUATION, Constants.PR_SPEECH_RATE,
			Constants.PR_STRESS, Constants.PR_VOICE_FAMILY,
			Constants.PR_VOLUME, Constants.PR_BACKGROUND_ATTACHMENT,
			Constants.PR_BACKGROUND_COLOR, Constants.PR_BACKGROUND_IMAGE,
			Constants.PR_BACKGROUND_REPEAT,
			Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
			Constants.PR_BACKGROUND_POSITION_VERTICAL,
			Constants.PR_BORDER_BEFORE_COLOR, Constants.PR_BORDER_BEFORE_STYLE,
			Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_BORDER_AFTER_COLOR,
			Constants.PR_BORDER_AFTER_STYLE, Constants.PR_BORDER_AFTER_WIDTH,
			Constants.PR_BORDER_START_COLOR, Constants.PR_BORDER_START_STYLE,
			Constants.PR_BORDER_START_WIDTH, Constants.PR_BORDER_END_COLOR,
			Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
			Constants.PR_BORDER_TOP_COLOR, Constants.PR_BORDER_TOP_STYLE,
			Constants.PR_BORDER_TOP_WIDTH, Constants.PR_BORDER_BOTTOM_COLOR,
			Constants.PR_BORDER_BOTTOM_STYLE, Constants.PR_BORDER_BOTTOM_WIDTH,
			Constants.PR_BORDER_LEFT_COLOR, Constants.PR_BORDER_LEFT_STYLE,
			Constants.PR_BORDER_LEFT_WIDTH, Constants.PR_BORDER_RIGHT_COLOR,
			Constants.PR_BORDER_RIGHT_STYLE, Constants.PR_BORDER_RIGHT_WIDTH,
			Constants.PR_PADDING_BEFORE, Constants.PR_PADDING_AFTER,
			Constants.PR_PADDING_START, Constants.PR_PADDING_END,
			Constants.PR_PADDING_TOP, Constants.PR_PADDING_BOTTOM,
			Constants.PR_PADDING_LEFT, Constants.PR_PADDING_RIGHT,
			Constants.PR_MARGIN_TOP, Constants.PR_MARGIN_BOTTOM,
			Constants.PR_MARGIN_LEFT, Constants.PR_ALIGNMENT_ADJUST,
			Constants.PR_ALIGNMENT_BASELINE, /*
											 * Constants.PR_ALLOWED_HEIGHT_SCALE,
											 * Constants.PR_ALLOWED_WIDTH_SCALE,
											 */Constants.PR_BASELINE_SHIFT,
			Constants.PR_BLOCK_PROGRESSION_DIMENSION, Constants.PR_CLIP,
			Constants.PR_CONTENT_HEIGHT, Constants.PR_CONTENT_TYPE,
			Constants.PR_CONTENT_WIDTH, Constants.PR_DISPLAY_ALIGN,
			Constants.PR_DOMINANT_BASELINE, Constants.PR_HEIGHT,
			Constants.PR_ID, Constants.PR_INDEX_CLASS, Constants.PR_INDEX_KEY,
			Constants.PR_INLINE_PROGRESSION_DIMENSION,
			Constants.PR_KEEP_WITH_NEXT, Constants.PR_KEEP_WITH_PREVIOUS,
			Constants.PR_LINE_HEIGHT, Constants.PR_OVERFLOW,
			Constants.PR_SCALING, Constants.PR_SCALING_METHOD,
			Constants.PR_TEXT_ALIGN, Constants.PR_WIDTH };

	static Map<Integer, Object> mapchart;

	static int[] keychart =
	{ Constants.PR_TITLE, Constants.PR_CHART_TYPE, Constants.PR_SERIES_COUNT,
			Constants.PR_VALUE_COUNT, Constants.PR_CHART_ORIENTATION,
			Constants.PR_RANGEAXIS_LABEL, Constants.PR_DOMAINAXIS_LABEL,
			Constants.PR_SERIES_LABEL_ORIENTATION, Constants.PR_MARGIN_TOP,
			Constants.PR_MARGIN_BOTTOM, Constants.PR_MARGIN_LEFT,
			Constants.PR_MARGIN_RIGHT, Constants.PR_RANGEAXIS_MINUNIT,
			Constants.PR_RANGEAXIS_MAXUNIT,
			Constants.PR_RANGEAXIS_UNITINCREMENT,
			Constants.PR_RANGEAXIS_PRECISION, Constants.PR_CHART_VALUE_OFFSET,
			Constants.PR_PIECHART_STARTANGLE, Constants.PR_PIECHART_DIRECTION,
			Constants.PR_PIECHARTLENGENDLABEL_VISABLE,
			Constants.PR_PIE_FACT_VALUE_VISABLE,
			Constants.PR_PERCENTVALUE_VISABLE, Constants.PR_CHART_TYPE,
			Constants.EN_BARCHART, Constants.PR_LENGEND_LABEL_LOCATION,
			Constants.PR_LENGEND_LABLE_ALIGNMENT };

	@SuppressWarnings("unchecked")
	public static void setDifferentAttributes()
	{
		setInitMap();
		setMapKey();
		setMapType();
		Object[] keysclass = maptype.keySet().toArray();
		for (int j = 0; j < keysclass.length; j++)
		{
			Class classkey = (Class) keysclass[j];
			Map<Integer, Object> check = maptype.get(classkey);
			check = new HashMap<Integer, Object>();
			int[] keys = mapkey.get(classkey);
			if (keys != null)
			{
				for (int i = 0; i < keys.length; i++)
				{
					int key = keys[i];
					Object editinit = InitialManager.getInitialValue(key, null);
					Object foinit = DefaultValueMap.initmap.get(key);
					if (foinit != null)
					{
						if (!foinit.equals(editinit))
						{
							check.put(key, editinit);
						}
					} else if (editinit != null)
					{
						check.put(key, editinit);
					}
				}
				maptype.put(classkey, check);
			}
		}
		flg = true;
	}

	@SuppressWarnings("unchecked")
	public static Map<Integer, Object> getDifferentAttributes(Class classname)
	{
		if (!flg)
		{
			setDifferentAttributes();
		}
		return maptype.get(classname);
	}

	@SuppressWarnings("unchecked")
	static Map<Class, int[]> mapkey = new HashMap<Class, int[]>();

	public static void setMapKey()
	{
		mapkey.put(BasicLink.class, keybasic_link);
		mapkey.put(Block.class, keyblock);
		mapkey.put(BlockContainer.class, keyblockcontainer);
		mapkey.put(ExternalGraphic.class, keyexternal_graphic);
		mapkey.put(QianZhang.class, keyqianzhang);
		mapkey.put(Inline.class, keyinline);
		mapkey.put(BarCode.class, keyinstreamforeignobject);
		mapkey.put(Leader.class, keyleader);
		mapkey.put(PageNumber.class, keypage_number);
		mapkey.put(PageNumberCitation.class, keypage_number_citation);
		mapkey.put(PageSequence.class, keypage_sequence);
		mapkey.put(RegionAfter.class, keyregion_after);
		mapkey.put(RegionBefore.class, keyregion_before);
		mapkey.put(RegionBody.class, keyregion_body);
		mapkey.put(RegionStart.class, keyregion_start);
		mapkey.put(RegionEnd.class, keyregion_end);
		mapkey.put(WiseDocDocument.class, keydocument);
		mapkey.put(SimplePageMaster.class, keysimple_page_master);
		mapkey.put(Table.class, keytable);
		mapkey.put(TableColumn.class, keytable_column);
		mapkey.put(TableRow.class, keytable_row);
		mapkey.put(TableCell.class, keytable_cell);
		mapkey.put(TableHeader.class, keytable_hearder);
		mapkey.put(TableFooter.class, keytable_footer);
		mapkey.put(TableBody.class, keytable_body);
		mapkey.put(Chart.class, keychart);
	}

	@SuppressWarnings("unchecked")
	static Map<Class, Map<Integer, Object>> maptype = new HashMap<Class, Map<Integer, Object>>();

	public static void setMapType()
	{
		maptype.put(BasicLink.class, mapbasic_link);
		maptype.put(Block.class, mapblock);
		maptype.put(BlockContainer.class, mapblockcontainer);
		maptype.put(ExternalGraphic.class, mapexternal_graphic);
		maptype.put(QianZhang.class, mapqianzhang);
		maptype.put(Inline.class, mapinline);
		maptype.put(BarCode.class, mapinstreamforeignobject);
		maptype.put(Leader.class, mapleader);
		maptype.put(PageNumber.class, mappage_number);
		maptype.put(PageNumberCitation.class, mappage_number_citation);
		maptype.put(PageSequence.class, mappage_sequence);
		maptype.put(RegionAfter.class, mapregion_after);
		maptype.put(RegionBefore.class, mapregion_before);
		maptype.put(RegionBody.class, mapregion_body);
		maptype.put(RegionStart.class, mapregion_start);
		maptype.put(RegionEnd.class, mapregion_end);
		maptype.put(WiseDocDocument.class, mapdocument);
		maptype.put(SimplePageMaster.class, mapsimple_page_master);
		maptype.put(Table.class, maptable);
		maptype.put(TableColumn.class, maptable_column);
		maptype.put(TableRow.class, maptable_row);
		maptype.put(TableCell.class, maptable_cell);
		maptype.put(TableHeader.class, maptable_hearder);
		maptype.put(TableFooter.class, maptable_footer);
		maptype.put(TableBody.class, maptable_body);
		maptype.put(Chart.class, mapchart);
		setMapKey();
	}

	public static void clearCompleteAttributes()
	{
		initmap.clear();
		if (mapbasic_link != null)
		{
			mapbasic_link.clear();
		}
		if (mapblock != null)
		{
			mapblock.clear();
		}
		if (mapblockcontainer != null)
		{
			mapblockcontainer.clear();
		}
		if (mapexternal_graphic != null)
		{
			mapexternal_graphic.clear();
		}
		if (mapqianzhang != null)
		{
			mapqianzhang.clear();
		}
		if (mapinline != null)
		{
			mapinline.clear();
		}
		if (mapinstreamforeignobject != null)
		{
			mapinstreamforeignobject.clear();
		}
		if (mapleader != null)
		{
			mapleader.clear();
		}
		if (mappage_number != null)
		{
			mappage_number.clear();
		}
		if (mappage_number_citation != null)
		{
			mappage_number_citation.clear();
		}
		if (mappage_sequence != null)
		{
			mappage_sequence.clear();
		}
		if (mapregion_after != null)
		{
			mapregion_after.clear();
		}
		if (mapregion_before != null)
		{
			mapregion_before.clear();
		}
		if (mapregion_body != null)
		{
			mapregion_body.clear();
		}
		if (mapregion_start != null)
		{
			mapregion_start.clear();
		}
		if (mapregion_end != null)
		{
			mapregion_end.clear();
		}
		if (mapsimple_page_master != null)
		{
			mapsimple_page_master.clear();
		}
		if (mapdocument != null)
		{
			mapdocument.clear();
		}
		if (maptable != null)
		{
			maptable.clear();
		}
		if (maptable_column != null)
		{
			maptable_column.clear();
		}
		if (maptable_row != null)
		{
			maptable_row.clear();
		}
		if (maptable_cell != null)
		{
			maptable_cell.clear();
		}
		if (maptable_hearder != null)
		{
			maptable_hearder.clear();
		}
		if (maptable_footer != null)
		{
			maptable_footer.clear();
		}
		if (maptable_body != null)
		{
			maptable_body.clear();
		}
		if (maptype != null)
		{
			maptype.clear();
		}
		if (mapchart != null)
		{
			mapchart.clear();
		}
		flg = false;
	}
}
