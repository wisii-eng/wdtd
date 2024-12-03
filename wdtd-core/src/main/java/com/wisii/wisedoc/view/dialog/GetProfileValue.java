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

package com.wisii.wisedoc.view.dialog;

import java.awt.Color;
import java.util.HashMap;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.document.attribute.LengthPairProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.NumberProperty;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.attribute.WiseDocColor;

public class GetProfileValue
{

	private final static HashMap<Integer, String> enumproperty = new HashMap<Integer, String>();
	static
	{
		enumproperty
				.put(Constants.PR_ABSOLUTE_POSITION, "PR_ABSOLUTE_POSITION");
		enumproperty.put(Constants.PR_FONT_SELECTION_STRATEGY,
				"PR_FONT_SELECTION_STRATEGY");
		enumproperty.put(Constants.PR_ALIGNMENT_BASELINE,
				"PR_ALIGNMENT_BASELINE");
		enumproperty.put(Constants.PR_DISPLAY_ALIGN, "PR_DISPLAY_ALIGN");
		enumproperty
				.put(Constants.PR_DOMINANT_BASELINE, "PR_DOMINANT_BASELINE");
		enumproperty.put(Constants.PR_SCALING_METHOD, "PR_SCALING_METHOD");
		enumproperty.put(Constants.PR_HYPHENATION_KEEP, "PR_HYPHENATION_KEEP");
		enumproperty.put(Constants.PR_SUPPRESS_AT_LINE_BREAK,
				"PR_SUPPRESS_AT_LINE_BREAK");
		enumproperty.put(Constants.PR_TREAT_AS_WORD_SPACE,
				"PR_TREAT_AS_WORD_SPACE");
		enumproperty.put(Constants.PR_RENDERING_INTENT, "PR_RENDERING_INTENT");
		enumproperty.put(Constants.PR_INTRUSION_DISPLACE,
				"PR_INTRUSION_DISPLACE");
		enumproperty.put(Constants.PR_BREAK_AFTER, "PR_BREAK_AFTER");
		enumproperty.put(Constants.PR_BREAK_BEFORE, "PR_BREAK_BEFORE");
		enumproperty.put(Constants.PR_OVERFLOW, "PR_OVERFLOW");
		enumproperty.put(Constants.PR_CLIP, "PR_CLIP");
		enumproperty.put(Constants.PR_LETTER_VALUE, "PR_LETTER_VALUE");
		enumproperty.put(Constants.PR_FORCE_PAGE_COUNT, "PR_FORCE_PAGE_COUNT");
		enumproperty.put(Constants.PR_INITIAL_PAGE_NUMBER,
				"PR_INITIAL_PAGE_NUMBER");
		enumproperty.put(Constants.PR_FORCE_PAGE_COUNT, "PR_FORCE_PAGE_COUNT");
		enumproperty.put(Constants.PR_MEDIA_USAGE, "PR_MEDIA_USAGE");
		enumproperty.put(Constants.PR_PAGE_HEIGHT, "PR_PAGE_HEIGHT");
		enumproperty.put(Constants.PR_PAGE_WIDTH, "PR_PAGE_WIDTH");
		enumproperty.put(Constants.PR_TABLE_LAYOUT, "PR_TABLE_LAYOUT");
		enumproperty.put(Constants.PR_CONTENT_TYPE, "PR_CONTENT_TYPE");
		enumproperty.put(Constants.PR_Z_INDEX, "PR_Z_INDEX");
		//
		enumproperty.put(Constants.PR_BACKGROUND_ATTACHMENT,
				"PR_BACKGROUND_ATTACHMENT");
		enumproperty.put(Constants.PR_BORDER_BEFORE_STYLE,
				"PR_BORDER_BEFORE_STYLE");

		enumproperty.put(Constants.PR_BORDER_AFTER_STYLE,
				"PR_BORDER_AFTER_STYLE");
		enumproperty.put(Constants.PR_BORDER_START_STYLE,
				"PR_BORDER_START_STYLE");
		enumproperty.put(Constants.PR_BORDER_END_STYLE, "PR_BORDER_END_STYLE");
		enumproperty.put(Constants.PR_HYPHENATE, "PR_HYPHENATE");
		//
		// enumproperty.put(Constants.PR_FONT_STYLE, "PR_FONT_STYLE");
		// enumproperty.put(Constants.PR_FONT_WEIGHT, "PR_FONT_WEIGHT");
		enumproperty.put(Constants.PR_LETTER_SPACING, "PR_LETTER_SPACING");
		enumproperty.put(Constants.PR_WORD_SPACING, "PR_WORD_SPACING");
		enumproperty.put(Constants.PR_WRITING_MODE, "PR_WRITING_MODE");
		enumproperty.put(Constants.PR_BORDER_COLLAPSE, "PR_BORDER_COLLAPSE");
		enumproperty.put(Constants.PR_EMPTY_CELLS, "PR_EMPTY_CELLS");
		enumproperty.put(Constants.PR_BARCODE_TYPE, "PR_BARCODE_TYPE");
		enumproperty.put(Constants.PR_XMLEDIT, "xmlEdit");
		enumproperty.put(Constants.PR_EDITMODE, "PR_EDITMODE");
	}

	private final static HashMap<Integer, String> enumlength = new HashMap<Integer, String>();
	static
	{
		enumlength.put(Constants.PR_TOP, "PR_TOP");
		enumlength.put(Constants.PR_RIGHT, "PR_RIGHT");
		enumlength.put(Constants.PR_BOTTOM, "PR_BOTTOM");
		enumlength.put(Constants.PR_LEFT, "PR_LEFT");
		enumlength.put(Constants.PR_ALIGNMENT_ADJUST, "PR_ALIGNMENT_ADJUST");
		enumlength.put(Constants.PR_BASELINE_SHIFT, "PR_BASELINE_SHIFT");
		enumlength.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
				"PR_BLOCK_PROGRESSION_DIMENSION");
		enumlength.put(Constants.PR_CONTENT_HEIGHT, "PR_CONTENT_HEIGHT");
		enumlength.put(Constants.PR_CONTENT_WIDTH, "PR_CONTENT_WIDTH");
		enumlength.put(Constants.PR_HEIGHT, "PR_HEIGHT");
		enumlength.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
				"PR_INLINE_PROGRESSION_DIMENSION");
		enumlength.put(Constants.PR_WIDTH, "PR_WIDTH");
	}

	private final static HashMap<Integer, String> integer = new HashMap<Integer, String>();
	static
	{
		integer.put(Constants.PR_SOURCE_DOCUMENT, "PR_SOURCE_DOCUMENT");
		integer.put(Constants.PR_ROLE, "PR_ROLE");
		integer.put(Constants.PR_TEXT_TRANSFORM, "PR_TEXT_TRANSFORM");
		integer.put(Constants.PR_TEXT_DECORATION, "PR_TEXT_DECORATION");
		integer.put(Constants.PR_BACKGROUND_REPEAT, "PR_BACKGROUND_REPEAT");
		integer.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
				"PR_BACKGROUND_POSITION_HORIZONTAL");
		integer.put(Constants.PR_BACKGROUND_POSITION_VERTICAL,
				"PR_BACKGROUND_POSITION_VERTICAL");
		integer.put(Constants.PR_FONT_STRETCH, "PR_FONT_STRETCH");
		integer.put(Constants.PR_FONT_VARIANT, "PR_FONT_VARIANT");
		integer.put(Constants.PR_RELATIVE_POSITION, "PR_RELATIVE_POSITION");
		integer.put(Constants.PR_RELATIVE_ALIGN, "PR_RELATIVE_ALIGN");
		integer.put(Constants.PR_APHLA, "PR_APHLA");
		integer.put(Constants.PR_SCALING, "PR_SCALING");
		integer.put(Constants.PR_LINE_STACKING_STRATEGY,
				"PR_LINE_STACKING_STRATEGY");
		integer.put(Constants.PR_LINEFEED_TREATMENT, "PR_LINEFEED_TREATMENT");
		integer.put(Constants.PR_WHITE_SPACE_TREATMENT,
				"PR_WHITE_SPACE_TREATMENT");
		integer.put(Constants.PR_TEXT_ALIGN, "PR_TEXT_ALIGN");
		integer.put(Constants.PR_TEXT_ALIGN_LAST, "PR_TEXT_ALIGN_LAST");
		integer.put(Constants.PR_WHITE_SPACE_COLLAPSE,
				"PR_WHITE_SPACE_COLLAPSE");
		integer.put(Constants.PR_WRAP_OPTION, "PR_WRAP_OPTION");
		integer.put(Constants.PR_WIDOWS, "PR_WIDOWS");
		integer.put(Constants.PR_ORPHANS, "PR_ORPHANS");
		integer.put(Constants.PR_REFERENCE_ORIENTATION,
				"PR_REFERENCE_ORIENTATION");
		integer.put(Constants.PR_SPAN, "PR_SPAN");
		integer.put(Constants.PR_LEADER_PATTERN, "PR_LEADER_PATTERN");
		integer.put(Constants.PR_LEADER_PATTERN_WIDTH,
				"PR_LEADER_PATTERN_WIDTH");
		integer.put(Constants.PR_RULE_STYLE, "PR_RULE_STYLE");
		integer.put(Constants.PR_AUTO_RESTORE, "PR_AUTO_RESTORE");
		integer.put(Constants.PR_INDICATE_DESTINATION,
				"PR_INDICATE_DESTINATION");
		integer.put(Constants.PR_STARTING_STATE, "PR_STARTING_STATE");
		integer.put(Constants.PR_RETRIEVE_BOUNDARY, "PR_RETRIEVE_BOUNDARY");
		integer.put(Constants.PR_BLANK_OR_NOT_BLANK, "PR_BLANK_OR_NOT_BLANK");
		integer.put(Constants.PR_COLUMN_COUNT, "PR_COLUMN_COUNT");
		integer.put(Constants.PR_MAXIMUM_REPEATS, "PR_MAXIMUM_REPEATS");
		integer.put(Constants.PR_ODD_OR_EVEN, "PR_ODD_OR_EVEN");
		integer.put(Constants.PR_PAGE_POSITION, "PR_PAGE_POSITION");
		integer.put(Constants.PR_PRECEDENCE, "PR_PRECEDENCE");
		integer.put(Constants.PR_CAPTION_SIDE, "PR_CAPTION_SIDE");
		integer.put(Constants.PR_ENDS_ROW, "PR_ENDS_ROW");
		integer.put(Constants.PR_NUMBER_COLUMNS_REPEATED,
				"PR_NUMBER_COLUMNS_REPEATED");
		integer.put(Constants.PR_NUMBER_COLUMNS_SPANNED,
				"PR_NUMBER_COLUMNS_SPANNED");
		integer.put(Constants.PR_NUMBER_ROWS_SPANNED, "PR_NUMBER_ROWS_SPANNED");
		integer.put(Constants.PR_STARTS_ROW, "PR_STARTS_ROW");
		integer.put(Constants.PR_TABLE_OMIT_HEADER_AT_BREAK,
				"PR_TABLE_OMIT_HEADER_AT_BREAK");
		integer.put(Constants.PR_TABLE_OMIT_FOOTER_AT_BREAK,
				"PR_TABLE_OMIT_FOOTER_AT_BREAK");
		integer.put(Constants.PR_DIRECTION, "PR_DIRECTION");
		integer.put(Constants.PR_TEXT_ALTITUDE, "PR_TEXT_ALTITUDE");
		integer.put(Constants.PR_TEXT_DEPTH, "PR_TEXT_DEPTH");
		integer.put(Constants.PR_UNICODE_BIDI, "PR_UNICODE_BIDI");
		integer.put(Constants.PR_TEXT_ALTITUDE, "PR_TEXT_ALTITUDE");
		integer.put(Constants.PR_SCORE_SPACES, "PR_SCORE_SPACES");
		integer.put(Constants.PR_VISIBILITY, "PR_VISIBILITY");
		integer.put(Constants.PR_FONT_STYLE, "PR_FONT_STYLE");
		integer.put(Constants.PR_FONT_WEIGHT, "PR_FONT_WEIGHT");
	}

	private final static HashMap<Integer, String> wisedoccolor = new HashMap<Integer, String>();
	static
	{
		wisedoccolor.put(Constants.PR_COLOR, "PR_COLOR");
	}

	private final static HashMap<Integer, String> fixedlength = new HashMap<Integer, String>();
	static
	{
		fixedlength.put(Constants.PR_MARGIN_TOP, "PR_MARGIN_TOP");
		fixedlength.put(Constants.PR_MARGIN_BOTTOM, "PR_MARGIN_BOTTOM");
		fixedlength.put(Constants.PR_MARGIN_LEFT, "PR_MARGIN_LEFT");
		fixedlength.put(Constants.PR_MARGIN_RIGHT, "PR_MARGIN_RIGHT");
		fixedlength.put(Constants.PR_EXTENT, "PR_EXTENT");
		fixedlength.put(Constants.PR_DESTINATION_PLACEMENT_OFFSET,
				"PR_DESTINATION_PLACEMENT_OFFSET");
		fixedlength.put(Constants.PR_TEXT_INDENT, "PR_TEXT_INDENT");
		fixedlength.put(Constants.PR_LAST_LINE_END_INDENT,
				"PR_LAST_LINE_END_INDENT");
		fixedlength.put(Constants.PR_MIN_HEIGHT, "PR_MIN_HEIGHT");
		fixedlength.put(Constants.PR_END_INDENT, "PR_END_INDENT");
		fixedlength.put(Constants.PR_START_INDENT, "PR_START_INDENT");
		fixedlength.put(Constants.PR_FONT_SIZE, "PR_FONT_SIZE");
		fixedlength.put(Constants.PR_RULE_THICKNESS, "PR_RULE_THICKNESS");
		fixedlength.put(Constants.PR_COLUMN_GAP, "PR_COLUMN_GAP");
		fixedlength.put(Constants.PR_PROVISIONAL_LABEL_SEPARATION,
				"PR_PROVISIONAL_LABEL_SEPARATION");
		fixedlength.put(Constants.PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS,
				"PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS");
		fixedlength.put(Constants.PR_MARGIN_TOP, "PR_MARGIN_TOP");

	}

	private final static HashMap<Integer, String> condlengthproperty = new HashMap<Integer, String>();
	static
	{
		condlengthproperty
				.put(Constants.PR_PADDING_BEFORE, "PR_PADDING_BEFORE");
		condlengthproperty.put(Constants.PR_PADDING_AFTER, "PR_PADDING_AFTER");
		condlengthproperty.put(Constants.PR_PADDING_START, "PR_PADDING_START");
		condlengthproperty.put(Constants.PR_PADDING_END, "PR_PADDING_END");
		condlengthproperty.put(Constants.PR_PADDING_TOP, "PR_PADDING_TOP");
		condlengthproperty
				.put(Constants.PR_PADDING_BOTTOM, "PR_PADDING_BOTTOM");
		condlengthproperty.put(Constants.PR_PADDING_LEFT, "PR_PADDING_LEFT");
		condlengthproperty.put(Constants.PR_PADDING_RIGHT, "PR_PADDING_RIGHT");
	}

	private final static HashMap<Integer, String> string = new HashMap<Integer, String>();
	static
	{
		string.put(Constants.PR_FONT_FAMILY, "PR_FONT_FAMILY");
		string
				.put(Constants.PR_EXTERNAL_DESTINATION,
						"PR_EXTERNAL_DESTINATION");
		string
				.put(Constants.PR_INTERNAL_DESTINATION,
						"PR_INTERNAL_DESTINATION");
		string.put(Constants.PR_INDEX_CLASS, "PR_INDEX_CLASS");
		string.put(Constants.PR_MARKER_CLASS_NAME, "PR_MARKER_CLASS_NAME");
		string.put(Constants.PR_RETRIEVE_CLASS_NAME, "PR_RETRIEVE_CLASS_NAME");
		string.put(Constants.PR_FORMAT, "PR_FORMAT");
		string.put(Constants.PR_MASTER_NAME, "PR_MASTER_NAME");
		string.put(Constants.PR_MASTER_REFERENCE, "PR_MASTER_REFERENCE");
	}

	private final static HashMap<Integer, String> enumnumber = new HashMap<Integer, String>();
	static
	{

		enumnumber.put(Constants.PR_HYPHENATION_PUSH_CHARACTER_COUNT,
				"PR_HYPHENATION_PUSH_CHARACTER_COUNT");
		enumnumber.put(Constants.PR_HYPHENATION_REMAIN_CHARACTER_COUNT,
				"PR_HYPHENATION_REMAIN_CHARACTER_COUNT");
		enumnumber.put(Constants.PR_HYPHENATION_LADDER_COUNT,
				"PR_HYPHENATION_LADDER_COUNT");
	}

	private final static HashMap<Integer, String> onechar = new HashMap<Integer, String>();
	static
	{
		onechar.put(Constants.PR_HYPHENATION_CHARACTER,
				"PR_HYPHENATION_CHARACTER");
	}

	private final static HashMap<Integer, String> spaceproperty = new HashMap<Integer, String>();
	static
	{
		spaceproperty.put(Constants.PR_SPACE_BEFORE, "PR_SPACE_BEFORE");
		spaceproperty.put(Constants.PR_SPACE_AFTER, "PR_SPACE_AFTER");
		spaceproperty.put(Constants.PR_SPACE_END, "PR_SPACE_END");
		spaceproperty.put(Constants.PR_SPACE_START, "PR_SPACE_START");
		spaceproperty.put(Constants.PR_LINE_HEIGHT, "PR_LINE_HEIGHT");
	}

	private final static HashMap<Integer, String> lengthrangeproperty = new HashMap<Integer, String>();
	static
	{
		lengthrangeproperty.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
				"PR_INLINE_PROGRESSION_DIMENSION");
		lengthrangeproperty.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
				"PR_BLOCK_PROGRESSION_DIMENSION");

	}

	private final static HashMap<Integer, String> keepproperty = new HashMap<Integer, String>();
	static
	{
		keepproperty.put(Constants.PR_KEEP_TOGETHER, "PR_KEEP_TOGETHER");
		keepproperty.put(Constants.PR_KEEP_WITH_NEXT, "PR_KEEP_WITH_NEXT");
		keepproperty.put(Constants.PR_KEEP_WITH_PREVIOUS,
				"PR_KEEP_WITH_PREVIOUS");
	}

	private final static HashMap<Integer, String> lengthpairproperty = new HashMap<Integer, String>();
	static
	{
		lengthpairproperty.put(Constants.PR_BORDER_SEPARATION,
				"PR_BORDER_SEPARATION");
	}

	private final static HashMap<Integer, String> numberproperty = new HashMap<Integer, String>();
	static
	{
		numberproperty.put(Constants.PR_COLUMN_NUMBER, "PR_COLUMN_NUMBER");
	}

	private final static HashMap<Integer, String> datetimeinfo = new HashMap<Integer, String>();
	static
	{
		datetimeinfo.put(Constants.PR_DATETIMEFORMAT, "PR_DATETIMEFORMAT");
	}

	public static NumberProperty createNumberProperty(String numberproperty)
	{
		if (numberproperty.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			String[] number = numberproperty.split(",");
			NumberProperty np = new NumberProperty(new Integer(number[0])
					.intValue());
			return np;
		}
	}

	public static LengthPairProperty createLengthPairProperty(
			String lengthpairproperty)
	{
		if (lengthpairproperty.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			String[] number = lengthpairproperty.split(",");
			FixedLength length1 = new FixedLength(new Integer(number[0])
					.intValue(), number[1]);
			FixedLength length2 = new FixedLength(new Integer(number[2])
					.intValue(), number[3]);
			LengthPairProperty lpp = new LengthPairProperty(length1, length2);
			return lpp;
		}
	}

	public static KeepProperty createKeepProperty(String keepproperty)
	{
		if (keepproperty.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			String[] number = keepproperty.split(",");
			EnumProperty enmu1 = new EnumProperty(new Integer(number[0])
					.intValue(), "");
			EnumProperty enmu2 = new EnumProperty(new Integer(number[1])
					.intValue(), "");
			EnumProperty enmu3 = new EnumProperty(new Integer(number[2])
					.intValue(), "");
			KeepProperty sp = new KeepProperty(enmu1, enmu2, enmu3);

			return sp;
		}
	}

	public static LengthRangeProperty createLengthRangeProperty(
			String lengthrangeproperty)
	{
		if (lengthrangeproperty.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			String[] number = lengthrangeproperty.split(",");
			EnumLength min = new EnumLength(new Integer(number[0]).intValue(),
					new FixedLength(new Integer(number[1]).intValue(),
							number[2]));
			LengthRangeProperty lrp = new LengthRangeProperty(
					createEnumLength(lengthrangeproperty));
			if (number.length == 3)
			{
				lrp = new LengthRangeProperty(min);
			} else
			{

				EnumLength option = new EnumLength(new Integer(number[3])
						.intValue(), new FixedLength(new Integer(number[4])
						.intValue(), number[5]));
				EnumLength max = new EnumLength(new Integer(number[6])
						.intValue(), new FixedLength(new Integer(number[7])
						.intValue(), number[8]));
				lrp = new LengthRangeProperty(min, option, max);
			}

			return lrp;
		}
	}

	public static SpaceProperty createSpaceProperty(String spaceproperty)
	{
		if (spaceproperty.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			String[] number = spaceproperty.split(",");
			FixedLength fl = new FixedLength(new Integer(number[0]).intValue(),
					number[1]);
			EnumNumber precedence = new EnumNumber(new Integer(number[2])
					.intValue(), new Integer(number[3]).intValue());
			EnumProperty conditionality = new EnumProperty(new Integer(
					number[4]).intValue(), "");
			SpaceProperty sp = new SpaceProperty(fl, precedence, conditionality);

			return sp;
		}
	}

	public static char createChar(String onechar)
	{
		if (onechar.equalsIgnoreCase(""))
		{
			return (char) 0x2010;
		} else
		{
			return onechar.charAt(0);
		}
	}

	public static EnumNumber createEnumNumber(String enumnumber)
	{
		if (enumnumber.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			String[] number = enumnumber.split(",");
			int num = new Integer(number[1]).intValue();
			int enumber = new Integer(number[0]).intValue();
			EnumNumber enumNumber = new EnumNumber(enumber, num);
			return enumNumber;
		}
	}

	public static WiseDocColor createWisedocColor(String wisedoccolor)
	{
		if (wisedoccolor.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			String[] number = wisedoccolor.split(",");
			Color color = new Color(new Integer(number[0]).intValue(),
					new Integer(number[1]).intValue(), new Integer(number[2])
							.intValue());
			WiseDocColor wcolor = new WiseDocColor(color,
					new Integer(number[3]).intValue());
			return wcolor;
		}
	}

	public static EnumProperty createEnumProperty(String enumproperty)
	{
		if (enumproperty.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			EnumProperty ep = new EnumProperty(new Integer(enumproperty)
					.intValue(), "");
			return ep;
		}
	}

	public static EnumLength createEnumLength(String enumlength)
	{
		if (enumlength.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			EnumLength el = null;
			String[] number = enumlength.split(",");
			if (number.length == 1)
			{
				el = new EnumLength(new Integer(number[0]).intValue(), null);
			} else
			{
				el = new EnumLength(new Integer(number[0]).intValue(),
						new FixedLength(new Integer(number[1]).intValue(),
								number[2]));
			}
			return el;
		}
	}

	public static int createInteger(String strbunber)
	{
		if (strbunber.equalsIgnoreCase(""))
		{
			return 0;
		} else
		{
			return new Integer(strbunber).intValue();
		}
	}

	public static FixedLength createFixedLength(String fixedlength)
	{
		if (fixedlength.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			String[] number = fixedlength.split(",");
			FixedLength fl = null;

			if (number.length == 1)
			{
				fl = new FixedLength(new Integer(number[0]).intValue());
			} else
			{
				fl = new FixedLength(new Integer(number[0]).intValue(),
						number[1]);
			}
			return fl;
		}
	}

	public static CondLengthProperty createCondLengthProperty(
			String condlengthproperty)
	{
		if (condlengthproperty.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			String[] number = condlengthproperty.split(",");
			FixedLength fl = new FixedLength(new Integer(number[0]).intValue(),
					number[1]);
			boolean bl = true;
			if ("false".equalsIgnoreCase(number[2]))
			{
				bl = false;
			}
			CondLengthProperty clp = new CondLengthProperty(fl, bl);
			return clp;
		}
	}

	public static Object getDefaultValue(int key)
	{
		String keyStr = "";
		String valueStr = "";
		if (enumproperty.containsKey(key))
		{
			keyStr = enumproperty.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);
			return createEnumProperty(valueStr);
		} else if (enumlength.containsKey(key))
		{
			keyStr = enumlength.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createEnumLength(valueStr);
		} else if (integer.containsKey(key))
		{
			keyStr = integer.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createInteger(valueStr);
		} else if (wisedoccolor.containsKey(key))
		{
			keyStr = wisedoccolor.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createWisedocColor(valueStr);
		} else if (fixedlength.containsKey(key))
		{
			keyStr = fixedlength.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createFixedLength(valueStr);
		} else if (condlengthproperty.containsKey(key))
		{
			keyStr = condlengthproperty.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createCondLengthProperty(valueStr);
		} else if (string.containsKey(key))
		{
			keyStr = string.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return string.get(valueStr);
		} else if (enumnumber.containsKey(key))
		{
			keyStr = enumnumber.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createEnumNumber(valueStr);
		} else if (onechar.containsKey(key))
		{
			keyStr = onechar.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createChar(valueStr);
		} else if (spaceproperty.containsKey(key))
		{
			keyStr = spaceproperty.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createSpaceProperty(valueStr);
		} else if (lengthrangeproperty.containsKey(key))
		{
			keyStr = lengthrangeproperty.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createLengthRangeProperty(valueStr);
		} else if (keepproperty.containsKey(key))
		{
			keyStr = keepproperty.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createKeepProperty(valueStr);
		} else if (lengthpairproperty.containsKey(key))
		{
			keyStr = lengthpairproperty.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createLengthPairProperty(valueStr);
		} else if (numberproperty.containsKey(key))
		{
			keyStr = numberproperty.get(key);
			valueStr = ConfigurationInformationDialog.getConfigureItem(keyStr);

			return createNumberProperty(valueStr);
		}
		return null;
	}

	public static String getDefaultValue(String key)
	{
		return ConfigurationInformationDialog.getConfigureItem(key);
	}

	public static String getKey(int key)
	{
		if (enumproperty.containsKey(key))
		{
			return enumproperty.get(key);
		} else if (enumlength.containsKey(key))
		{
			return enumlength.get(key);
		} else if (integer.containsKey(key))
		{
			return integer.get(key);
		} else if (wisedoccolor.containsKey(key))
		{
			return wisedoccolor.get(key);
		} else if (fixedlength.containsKey(key))
		{
			return fixedlength.get(key);
		} else if (condlengthproperty.containsKey(key))
		{
			return condlengthproperty.get(key);
		} else if (string.containsKey(key))
		{
			return string.get(key);
		} else if (enumnumber.containsKey(key))
		{
			return enumnumber.get(key);
		} else if (onechar.containsKey(key))
		{
			return onechar.get(key);
		} else if (spaceproperty.containsKey(key))
		{
			return spaceproperty.get(key);
		} else if (lengthrangeproperty.containsKey(key))
		{
			return lengthrangeproperty.get(key);
		} else if (keepproperty.containsKey(key))
		{
			return keepproperty.get(key);
		} else if (lengthpairproperty.containsKey(key))
		{
			return lengthpairproperty.get(key);
		} else if (numberproperty.containsKey(key))
		{
			return numberproperty.get(key);
		} else if (datetimeinfo.containsKey(key))
		{
			return datetimeinfo.get(key);
		}
		return null;
	}

	public static String getValue(int key)
	{
		return ConfigurationInformationDialog.getConfigureItem(getKey(key));
	}

	public static String getValue(String key)
	{
		return ConfigurationInformationDialog.getConfigureItem(key);
	}

	public static int getIntValue(int key)
	{
		// System.out.println("key" + key);
		String value = ConfigurationInformationDialog
				.getConfigureItem(getKey(key));
		if (value != null && !"".equalsIgnoreCase(value))
		{
			return new Integer(value).intValue();
		}
		return -1;
	}

	public static int getIntValue(String key)
	{
		String value = ConfigurationInformationDialog.getConfigureItem(key);
		if (value != null)
		{
			return new Integer(value).intValue();
		}
		return -1;
	}

	// public static void main(String[] args)
	// {
	// EnumNumber nnn = new GetProfileValue().createEnumNumber("-1,2");
	// System.out.println("num:" + nnn.getValue());
	// System.out.println("enum:" + nnn.getEnum());
	//
	// }
}
