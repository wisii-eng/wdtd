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
 * @WSDAttributeIOFactory.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.ConditionItemCollection;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.document.attribute.LengthPairProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.NumberFormat;
import com.wisii.wisedoc.document.attribute.NumberProperty;
import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.document.attribute.transform.DataTransformTable;
import com.wisii.wisedoc.document.attribute.transform.MultiSource;
import com.wisii.wisedoc.document.attribute.transform.OutInterface;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserInfo;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserSource;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.document.attribute.transform.SwingDS;
import com.wisii.wisedoc.document.attribute.transform.TransformTable;
import com.wisii.wisedoc.io.AttributeIOFactory;
import com.wisii.wisedoc.io.AttributeKeyNameFactory;
import com.wisii.wisedoc.io.AttributeReader;
import com.wisii.wisedoc.io.AttributeWriter;
import com.wisii.wisedoc.io.wsd.attribute.BackGroundImageWriter;
import com.wisii.wisedoc.io.wsd.attribute.BarCodeTextListWriter;
import com.wisii.wisedoc.io.wsd.attribute.BarCodeTextWriter;
import com.wisii.wisedoc.io.wsd.attribute.BooleanWriter;
import com.wisii.wisedoc.io.wsd.attribute.ButtonGroupListAttWriter;
import com.wisii.wisedoc.io.wsd.attribute.ButtonGroupListWriter;
import com.wisii.wisedoc.io.wsd.attribute.CharacterWriter;
import com.wisii.wisedoc.io.wsd.attribute.ChartDataListWriter;
import com.wisii.wisedoc.io.wsd.attribute.ColorListWriter;
import com.wisii.wisedoc.io.wsd.attribute.CondLengthPropertyWriter;
import com.wisii.wisedoc.io.wsd.attribute.ConditionItemCollectionWriter;
import com.wisii.wisedoc.io.wsd.attribute.ConnWithWriter;
import com.wisii.wisedoc.io.wsd.attribute.ConnwithAttWriter;
import com.wisii.wisedoc.io.wsd.attribute.DataSourceAttWriter;
import com.wisii.wisedoc.io.wsd.attribute.DataSourceWriter;
import com.wisii.wisedoc.io.wsd.attribute.DoubleWriter;
import com.wisii.wisedoc.io.wsd.attribute.EnumLengthWriter;
import com.wisii.wisedoc.io.wsd.attribute.EnumNumberWriter;
import com.wisii.wisedoc.io.wsd.attribute.EnumPropertyWriter;
import com.wisii.wisedoc.io.wsd.attribute.FixedLengthWriter;
import com.wisii.wisedoc.io.wsd.attribute.FixedareaWriter;
import com.wisii.wisedoc.io.wsd.attribute.GroupUIAttWriter;
import com.wisii.wisedoc.io.wsd.attribute.GroupUIWriter;
import com.wisii.wisedoc.io.wsd.attribute.GroupWriter;
import com.wisii.wisedoc.io.wsd.attribute.IntegerAttributeWriter;
import com.wisii.wisedoc.io.wsd.attribute.KeepPropertyWriter;
import com.wisii.wisedoc.io.wsd.attribute.LengthPairPropertyWriter;
import com.wisii.wisedoc.io.wsd.attribute.LengthRangePropertyWriter;
import com.wisii.wisedoc.io.wsd.attribute.LogicalExpressionWriter;
import com.wisii.wisedoc.io.wsd.attribute.MixFillWriter;
import com.wisii.wisedoc.io.wsd.attribute.MixLengthWriter;
import com.wisii.wisedoc.io.wsd.attribute.NextWriter;
import com.wisii.wisedoc.io.wsd.attribute.NumberFormatWriter;
import com.wisii.wisedoc.io.wsd.attribute.NumberPropertyWriter;
import com.wisii.wisedoc.io.wsd.attribute.ParagraphStylesWriter;
import com.wisii.wisedoc.io.wsd.attribute.PercentLengthWriter;
import com.wisii.wisedoc.io.wsd.attribute.PopupBrowserInFOAttWriter;
import com.wisii.wisedoc.io.wsd.attribute.PopupBrowserInfoWirter;
import com.wisii.wisedoc.io.wsd.attribute.SelectInFOAttWriter;
import com.wisii.wisedoc.io.wsd.attribute.SelectInfoWirter;
import com.wisii.wisedoc.io.wsd.attribute.SpacePropertyWriter;
import com.wisii.wisedoc.io.wsd.attribute.StringWriter;
import com.wisii.wisedoc.io.wsd.attribute.TableContentsStyleWriter;
import com.wisii.wisedoc.io.wsd.attribute.ValidationWriter;
import com.wisii.wisedoc.io.wsd.attribute.WiseDocColorWriter;
import com.wisii.wisedoc.io.wsd.attribute.WisedocDateTimeFormatWriter;
import com.wisii.wisedoc.io.wsd.reader.AbstractElementsHandler;
import com.wisii.wisedoc.io.wsd.reader.BackGroundImageReader;
import com.wisii.wisedoc.io.wsd.reader.BarCodeTextListReader;
import com.wisii.wisedoc.io.wsd.reader.BarCodeTextReader;
import com.wisii.wisedoc.io.wsd.reader.BooleanReader;
import com.wisii.wisedoc.io.wsd.reader.ButtonGroupListAttReader;
import com.wisii.wisedoc.io.wsd.reader.CharacterReader;
import com.wisii.wisedoc.io.wsd.reader.ChartDataListReader;
import com.wisii.wisedoc.io.wsd.reader.ColorListReader;
import com.wisii.wisedoc.io.wsd.reader.CondLengthPropertyReader;
import com.wisii.wisedoc.io.wsd.reader.ConditionItemCollectionReader;
import com.wisii.wisedoc.io.wsd.reader.ConnWithAttReader;
import com.wisii.wisedoc.io.wsd.reader.DataSourceAttributeReader;
import com.wisii.wisedoc.io.wsd.reader.DoubleReader;
import com.wisii.wisedoc.io.wsd.reader.EnumLengthReader;
import com.wisii.wisedoc.io.wsd.reader.EnumNumberReader;
import com.wisii.wisedoc.io.wsd.reader.EnumPropertyReader;
import com.wisii.wisedoc.io.wsd.reader.FixedLengthReader;
import com.wisii.wisedoc.io.wsd.reader.FixedareaReader;
import com.wisii.wisedoc.io.wsd.reader.GroupReader;
import com.wisii.wisedoc.io.wsd.reader.GroupUIAttReader;
import com.wisii.wisedoc.io.wsd.reader.IntegerAttributeReader;
import com.wisii.wisedoc.io.wsd.reader.KeepPropertyReader;
import com.wisii.wisedoc.io.wsd.reader.LengthPairPropertyReader;
import com.wisii.wisedoc.io.wsd.reader.LengthRangePropertyReader;
import com.wisii.wisedoc.io.wsd.reader.LogicalExpressionReader;
import com.wisii.wisedoc.io.wsd.reader.MixFillReader;
import com.wisii.wisedoc.io.wsd.reader.MixLengthReader;
import com.wisii.wisedoc.io.wsd.reader.NextReader;
import com.wisii.wisedoc.io.wsd.reader.NumberFormatReader;
import com.wisii.wisedoc.io.wsd.reader.NumberPropertyReader;
import com.wisii.wisedoc.io.wsd.reader.ParagraphStyleReader;
import com.wisii.wisedoc.io.wsd.reader.PopupBrowserInfoAttReader;
import com.wisii.wisedoc.io.wsd.reader.SelectInfoAttReader;
import com.wisii.wisedoc.io.wsd.reader.SpacePropertyReader;
import com.wisii.wisedoc.io.wsd.reader.StringReader;
import com.wisii.wisedoc.io.wsd.reader.TableContentsStyleReader;
import com.wisii.wisedoc.io.wsd.reader.ValidationReader;
import com.wisii.wisedoc.io.wsd.reader.WiseDocColorReader;
import com.wisii.wisedoc.io.wsd.reader.WisedocDateTimeFormatReader;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-14
 */
public class WSDAttributeIOFactory implements AttributeIOFactory
{
	private Map<Integer, AttributeWriter> writermap = new HashMap<Integer, AttributeWriter>();
	private Map<Integer, AttributeReader> readermap = new HashMap<Integer, AttributeReader>();
	private Map<Class, AttributeWriter> classwritermap = new HashMap<Class, AttributeWriter>();
	private AttributeWriter defaultwriter = new IntegerAttributeWriter();
	private AttributeReader defaultreader = new IntegerAttributeReader();
	public WSDAttributeIOFactory()
	{
		init();
	}

	private void init()
	{
		AttributeWriter enumlengthwriter = new EnumLengthWriter();
		AttributeWriter colorwriter = new WiseDocColorWriter();
		AttributeWriter bgiwriter = new BackGroundImageWriter();
		AttributeWriter lengthrangewriter = new LengthRangePropertyWriter();
		AttributeWriter enumwriter = new EnumPropertyWriter();
		AttributeWriter numberwriter = new NumberPropertyWriter();
		AttributeWriter conlengthwriter = new CondLengthPropertyWriter();
		AttributeWriter charwriter = new CharacterWriter();
		AttributeWriter stringwriter = new StringWriter();
		AttributeWriter spacewriter = new SpacePropertyWriter();
		AttributeWriter lengthpairwriter = new LengthPairPropertyWriter();
		AttributeWriter fixlengthwriter = new FixedLengthWriter();
		AttributeWriter keepwriter = new KeepPropertyWriter();
		AttributeWriter enumnumberwriter = new EnumNumberWriter();
		AttributeWriter numberfarmatwriter = new NumberFormatWriter();
		AttributeWriter datetimefarmatwriter = new WisedocDateTimeFormatWriter();
		AttributeWriter conditionwriter = new LogicalExpressionWriter();
		AttributeWriter groupwriter = new GroupWriter();
		AttributeWriter booleanwriter = new BooleanWriter();
		AttributeWriter doublewriter = new DoubleWriter();
		AttributeWriter barcodetextwriter = new BarCodeTextWriter();
		AttributeWriter perlen = new PercentLengthWriter();
		AttributeWriter mixlenwriter = new MixLengthWriter();
		AttributeWriter mixfillwriter = new MixFillWriter();
		AttributeWriter dynamicwriter = new ConditionItemCollectionWriter();
		AttributeWriter paragraphstylewriter = new ParagraphStylesWriter();
		AttributeWriter tcsstylewriter = new TableContentsStyleWriter();
		// AttributeWriter numbercontentwriter = new NumberContentWriter();
		AttributeWriter barcodetextlistwriter = new BarCodeTextListWriter();
		AttributeWriter chartdatalistwriter = new ChartDataListWriter();
		AttributeWriter colorlistwriter = new ColorListWriter();
		AttributeWriter validationwriter = new ValidationWriter();
		AttributeWriter selectinfowriter = new SelectInFOAttWriter();
		AttributeWriter popupbrowserinfowriter = new PopupBrowserInFOAttWriter();
		AttributeWriter connwithwriter = new ConnwithAttWriter();
		AttributeWriter datasourcewriter = new DataSourceAttWriter();
		AttributeWriter bslistwriter = new ButtonGroupListAttWriter();
		AttributeWriter fixedareawriter = new FixedareaWriter();
		AttributeWriter groupuiwriter = new GroupUIAttWriter();
		
		AttributeWriter nextwriter = new NextWriter();
		writermap.put(Constants.PR_ABSOLUTE_POSITION, enumwriter);
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_ACTIVE_STATE, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_ALIGNMENT_ADJUST, enumlengthwriter);
		// writermap.put(Constants.PR_ALIGNMENT_BASELINE, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_AUTO_RESTORE, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_AZIMUTH, new IntegerAttributeWriter());
		// writermap.put(Constants.PR_BACKGROUND, new IntegerAttributeWriter());
		writermap.put(Constants.PR_BACKGROUND_ATTACHMENT, enumwriter);
		writermap.put(Constants.PR_BACKGROUND_COLOR, colorwriter);
		writermap.put(Constants.PR_BACKGROUND_IMAGE, bgiwriter);
		// ???
		// writermap.put(Constants.PR_BACKGROUND_POSITION, new
		// IntegerAttributeWriter());
		// ???????
		writermap
				.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, mixlenwriter);
		writermap.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, mixlenwriter);
		writermap.put(Constants.PR_BACKGROUND_REPEAT, enumwriter);
		writermap.put(Constants.PR_BASELINE_SHIFT, enumlengthwriter);
		// writermap.put(Constants.PR_BLANK_OR_NOT_BLANK,
		// new IntegerAttributeWriter());
		writermap.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
				lengthrangewriter);
		// writermap.put(Constants.PR_BORDER, new IntegerAttributeWriter());
		writermap.put(Constants.PR_BORDER_AFTER_COLOR, colorwriter);
		writermap.put(Constants.PR_BORDER_AFTER_PRECEDENCE, numberwriter);
		writermap.put(Constants.PR_BORDER_AFTER_STYLE, enumwriter);
		writermap.put(Constants.PR_BORDER_AFTER_WIDTH, conlengthwriter);
		writermap.put(Constants.PR_BORDER_BEFORE_COLOR, colorwriter);
		writermap.put(Constants.PR_BORDER_BEFORE_PRECEDENCE, numberwriter);
		writermap.put(Constants.PR_BORDER_BEFORE_STYLE, enumwriter);
		writermap.put(Constants.PR_BORDER_BEFORE_WIDTH, conlengthwriter);
		// ?????
		// writermap.put(Constants.PR_BORDER_BOTTOM, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_BORDER_BOTTOM_COLOR, colorwriter);
		writermap.put(Constants.PR_BORDER_BOTTOM_STYLE, enumwriter);
		writermap.put(Constants.PR_BORDER_BOTTOM_WIDTH, conlengthwriter);

		// writermap.put(Constants.PR_BORDER_COLLAPSE, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_BORDER_COLOR, colorwriter);
		writermap.put(Constants.PR_BORDER_END_COLOR, colorwriter);
		writermap.put(Constants.PR_BORDER_END_PRECEDENCE, numberwriter);
		writermap.put(Constants.PR_BORDER_END_STYLE, enumwriter);
		writermap.put(Constants.PR_BORDER_END_WIDTH, conlengthwriter);
		// writermap.put(Constants.PR_BORDER_LEFT, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_BORDER_LEFT_COLOR, colorwriter);
		writermap.put(Constants.PR_BORDER_LEFT_STYLE, enumwriter);
		writermap.put(Constants.PR_BORDER_LEFT_WIDTH, conlengthwriter);
		// writermap.put(Constants.PR_BORDER_RIGHT, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_BORDER_RIGHT_COLOR, colorwriter);
		writermap.put(Constants.PR_BORDER_RIGHT_STYLE, enumwriter);
		writermap.put(Constants.PR_BORDER_RIGHT_WIDTH, conlengthwriter);
		// writermap.put(Constants.PR_BORDER_SEPARATION, new
		// LengthPairPropertyWriter());
		// ??????
		writermap.put(Constants.PR_BORDER_SPACING, lengthpairwriter);
		writermap.put(Constants.PR_BORDER_START_COLOR, colorwriter);
		writermap.put(Constants.PR_BORDER_START_PRECEDENCE, numberwriter);
		writermap.put(Constants.PR_BORDER_START_STYLE, enumwriter);
		writermap.put(Constants.PR_BORDER_START_WIDTH, conlengthwriter);
		writermap.put(Constants.PR_BORDER_STYLE, enumwriter);
		// writermap.put(Constants.PR_BORDER_TOP, new IntegerAttributeWriter());
		writermap.put(Constants.PR_BORDER_TOP_COLOR, colorwriter);
		writermap.put(Constants.PR_BORDER_TOP_STYLE, enumwriter);
		writermap.put(Constants.PR_BORDER_TOP_WIDTH, conlengthwriter);
		writermap.put(Constants.PR_BORDER_WIDTH, conlengthwriter);
		// writermap.put(Constants.PR_BOTTOM, new IntegerAttributeWriter());
		writermap.put(Constants.PR_BREAK_AFTER, enumwriter);
		writermap.put(Constants.PR_BREAK_BEFORE, enumwriter);
		// writermap.put(Constants.PR_CAPTION_SIDE, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_CASE_NAME, new IntegerAttributeWriter());
		// writermap.put(Constants.PR_CASE_TITLE, new IntegerAttributeWriter());
		writermap.put(Constants.PR_CHARACTER, charwriter);

		writermap.put(Constants.PR_CLEAR, enumwriter);
		// writermap.put(Constants.PR_CLIP, new IntegerAttributeWriter());
		writermap.put(Constants.PR_COLOR, colorwriter);
		writermap.put(Constants.PR_COLOR_PROFILE_NAME, stringwriter);
		// writermap.put(Constants.PR_COLUMN_COUNT, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_COLUMN_GAP, fixlengthwriter);
		// writermap.put(Constants.PR_COLUMN_NUMBER, new
		// IntegerAttributeWriter());
		// ?????
		writermap.put(Constants.PR_COLUMN_WIDTH, fixlengthwriter);
		writermap.put(Constants.PR_CONTENT_HEIGHT, enumlengthwriter);
		writermap.put(Constants.PR_CONTENT_TYPE, stringwriter);
		writermap.put(Constants.PR_CONTENT_WIDTH, enumlengthwriter);
		writermap.put(Constants.PR_COUNTRY, stringwriter);
		// writermap.put(Constants.PR_CUE, new IntegerAttributeWriter());
		// writermap.put(Constants.PR_CUE_AFTER, new IntegerAttributeWriter());
		// writermap.put(Constants.PR_CUE_BEFORE, new IntegerAttributeWriter());
		// writermap.put(Constants.PR_DESTINATION_PLACEMENT_OFFSET, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_DIRECTION, enumwriter);
		writermap.put(Constants.PR_DISPLAY_ALIGN, enumwriter);
		// writermap.put(Constants.PR_DOMINANT_BASELINE, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_ELEVATION, new IntegerAttributeWriter());
		writermap.put(Constants.PR_EMPTY_CELLS, enumwriter);
		// ????
		writermap.put(Constants.PR_END_INDENT, mixlenwriter);
		writermap.put(Constants.PR_ENDS_ROW, enumwriter);
		// ?????
		writermap.put(Constants.PR_EXTENT, fixlengthwriter);
		writermap.put(Constants.PR_EXTERNAL_DESTINATION, stringwriter);
		writermap.put(Constants.PR_FLOAT, enumwriter);
		writermap.put(Constants.PR_FLOW_NAME, stringwriter);
		// writermap.put(Constants.PR_FONT, new IntegerAttributeWriter());
		writermap.put(Constants.PR_FONT_FAMILY, stringwriter);
		writermap.put(Constants.PR_FONT_SELECTION_STRATEGY, enumwriter);
		writermap.put(Constants.PR_FONT_SIZE, mixlenwriter);
		writermap.put(Constants.PR_FONT_SIZE_ADJUST, numberwriter);
		// writermap.put(Constants.PR_FONT_STRETCH, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_FONT_STYLE, enumwriter);
		// writermap.put(Constants.PR_FONT_VARIANT, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_FONT_WEIGHT, enumwriter);
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_FORCE_PAGE_COUNT, enumwriter);
		writermap.put(Constants.PR_FORMAT, stringwriter);
		// 没实现
		// writermap.put(Constants.PR_GLYPH_ORIENTATION_HORIZONTAL, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_GLYPH_ORIENTATION_VERTICAL, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_GROUPING_SEPARATOR, charwriter);
		// writermap.put(Constants.PR_GROUPING_SIZE, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_HEIGHT, fixlengthwriter);
		writermap.put(Constants.PR_HYPHENATE, enumwriter);
		writermap.put(Constants.PR_HYPHENATION_CHARACTER, charwriter);
		// writermap.put(Constants.PR_HYPHENATION_KEEP, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_HYPHENATION_LADDER_COUNT, numberwriter);
		writermap.put(Constants.PR_HYPHENATION_PUSH_CHARACTER_COUNT,
				numberwriter);
		writermap.put(Constants.PR_HYPHENATION_REMAIN_CHARACTER_COUNT,
				numberwriter);
		writermap.put(Constants.PR_ID, stringwriter);
		// 没实现
		// writermap.put(Constants.PR_INDICATE_DESTINATION, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_INITIAL_PAGE_NUMBER, enumnumberwriter);
		writermap.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
				lengthrangewriter);
		writermap.put(Constants.PR_INTERNAL_DESTINATION, stringwriter);
		writermap.put(Constants.PR_KEEP_TOGETHER, keepwriter);
		writermap.put(Constants.PR_KEEP_WITH_NEXT, keepwriter);
		writermap.put(Constants.PR_KEEP_WITH_PREVIOUS, keepwriter);
		writermap.put(Constants.PR_LANGUAGE, stringwriter);
		writermap.put(Constants.PR_LAST_LINE_END_INDENT, fixlengthwriter);
		writermap.put(Constants.PR_LEADER_ALIGNMENT, enumwriter);
		writermap.put(Constants.PR_LEADER_LENGTH, lengthrangewriter);
		writermap.put(Constants.PR_LEADER_PATTERN, enumwriter);
		// IntegerAttributeWriter());
		// 不确定
		// writermap.put(Constants.PR_LEADER_PATTERN_WIDTH, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_LEFT, fixlengthwriter);
		writermap.put(Constants.PR_LETTER_SPACING, spacewriter);
		// writermap.put(Constants.PR_LETTER_VALUE, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_LINEFEED_TREATMENT, new
		// IntegerAttributeWriter());
		// 不确定
		writermap.put(Constants.PR_LINE_HEIGHT, spacewriter);
		// writermap.put(Constants.PR_LINE_HEIGHT_SHIFT_ADJUSTMENT, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_LINE_STACKING_STRATEGY, new
		// IntegerAttributeWriter());
		// 不确定
		// writermap.put(Constants.PR_MARGIN, new IntegerAttributeWriter());
		writermap.put(Constants.PR_MARGIN_BOTTOM, fixlengthwriter);
		writermap.put(Constants.PR_MARGIN_LEFT, fixlengthwriter);
		writermap.put(Constants.PR_MARGIN_RIGHT, fixlengthwriter);
		writermap.put(Constants.PR_MARGIN_TOP, fixlengthwriter);
		writermap.put(Constants.PR_MARKER_CLASS_NAME, stringwriter);
		writermap.put(Constants.PR_MASTER_NAME, stringwriter);
		writermap.put(Constants.PR_VIRTUAL_MASTER_NAME, stringwriter);
		writermap.put(Constants.PR_MASTER_REFERENCE, stringwriter);
		// 不确定
		// writermap.put(Constants.PR_MAX_HEIGHT, new IntegerAttributeWriter());
		writermap.put(Constants.PR_MAXIMUM_REPEATS, enumnumberwriter);
		// 不确定
		// writermap.put(Constants.PR_MAX_WIDTH, new IntegerAttributeWriter());
		writermap.put(Constants.PR_MEDIA_USAGE, stringwriter);
		// 不确定
		// writermap.put(Constants.PR_MIN_HEIGHT, new IntegerAttributeWriter());
		// 不确定
		// writermap.put(Constants.PR_MIN_WIDTH, new IntegerAttributeWriter());
		// writermap.put(Constants.PR_NUMBER_COLUMNS_REPEATED, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_NUMBER_COLUMNS_SPANNED, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_NUMBER_ROWS_SPANNED, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_ODD_OR_EVEN, enumwriter);
		writermap.put(Constants.PR_BLANK_OR_NOT_BLANK, enumwriter);
		// writermap.put(Constants.PR_ORPHANS, new IntegerAttributeWriter());
		writermap.put(Constants.PR_OVERFLOW, enumwriter);
		// 不确定
		// writermap.put(Constants.PR_PADDING, new IntegerAttributeWriter());
		writermap.put(Constants.PR_PADDING_AFTER, conlengthwriter);
		writermap.put(Constants.PR_PADDING_BEFORE, conlengthwriter);
		writermap.put(Constants.PR_PADDING_BOTTOM, conlengthwriter);
		writermap.put(Constants.PR_PADDING_END, conlengthwriter);
		writermap.put(Constants.PR_PADDING_LEFT, conlengthwriter);
		writermap.put(Constants.PR_PADDING_RIGHT, conlengthwriter);
		writermap.put(Constants.PR_PADDING_START, conlengthwriter);
		writermap.put(Constants.PR_PADDING_TOP, conlengthwriter);
		writermap.put(Constants.PR_PAGE_BREAK_AFTER, enumwriter);
		writermap.put(Constants.PR_PAGE_BREAK_BEFORE, enumwriter);
		writermap.put(Constants.PR_PAGE_BREAK_INSIDE, enumwriter);
		writermap.put(Constants.PR_PAGE_HEIGHT, fixlengthwriter);
		writermap.put(Constants.PR_PAGE_POSITION, enumwriter);
		writermap.put(Constants.PR_PAGE_WIDTH, fixlengthwriter);
		// 没实现
		// writermap.put(Constants.PR_PAUSE, new IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_PAUSE_AFTER, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_PAUSE_BEFORE, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_PITCH, new IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_PITCH_RANGE, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_PLAY_DURING, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_POSITION, enumwriter);
		writermap.put(Constants.PR_PRECEDENCE, enumwriter);
		// 不确定
		writermap.put(Constants.PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS,
				fixlengthwriter);
		// 不确定
		writermap.put(Constants.PR_PROVISIONAL_LABEL_SEPARATION,
				fixlengthwriter);
		writermap.put(Constants.PR_REFERENCE_ORIENTATION, numberwriter);
		writermap.put(Constants.PR_REF_ID, stringwriter);
		writermap.put(Constants.PR_REGION_NAME, stringwriter);
		writermap.put(Constants.PR_RELATIVE_ALIGN, enumwriter);
		// writermap.put(Constants.PR_RELATIVE_POSITION, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_RENDERING_INTENT, enumwriter);
		writermap.put(Constants.PR_RETRIEVE_BOUNDARY, enumwriter);
		writermap.put(Constants.PR_RETRIEVE_CLASS_NAME, stringwriter);
		writermap.put(Constants.PR_RETRIEVE_POSITION, enumwriter);
		// 没实现
		// writermap.put(Constants.PR_RICHNESS, new IntegerAttributeWriter());
		writermap.put(Constants.PR_RIGHT, fixlengthwriter);
		writermap.put(Constants.PR_ROLE, stringwriter);
		writermap.put(Constants.PR_RULE_STYLE, enumwriter);
		writermap.put(Constants.PR_RULE_THICKNESS, fixlengthwriter);
		writermap.put(Constants.PR_SCALING, enumwriter);
		writermap.put(Constants.PR_SCALING_METHOD, enumwriter);
		writermap.put(Constants.PR_SCORE_SPACES, enumwriter);
		writermap.put(Constants.PR_SCRIPT, stringwriter);
		// 没实现
		// writermap.put(Constants.PR_SHOW_DESTINATION, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_SIZE, new IntegerAttributeWriter());
		writermap.put(Constants.PR_SOURCE_DOCUMENT, stringwriter);
		writermap.put(Constants.PR_SPACE_AFTER, spacewriter);
		writermap.put(Constants.PR_SPACE_BEFORE, spacewriter);
		writermap.put(Constants.PR_SPACE_END, spacewriter);
		writermap.put(Constants.PR_SPACE_START, spacewriter);
		// writermap.put(Constants.PR_SPAN, new IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_SPEAK, new IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_SPEAK_HEADER, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_SPEAK_NUMERAL, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_SPEAK_PUNCTUATION, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_SPEECH_RATE, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_SRC, bgiwriter);
		// 不确定
		writermap.put(Constants.PR_START_INDENT, mixlenwriter);
		writermap.put(Constants.PR_STARTING_STATE, enumwriter);
		writermap.put(Constants.PR_STARTS_ROW, enumwriter);
		// 没实现
		// writermap.put(Constants.PR_STRESS, new IntegerAttributeWriter());
		writermap.put(Constants.PR_SUPPRESS_AT_LINE_BREAK, enumwriter);
		// 没实现
		// writermap.put(Constants.PR_SWITCH_TO, new IntegerAttributeWriter());
		// writermap.put(Constants.PR_TABLE_LAYOUT, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_TABLE_OMIT_FOOTER_AT_BREAK, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_TABLE_OMIT_HEADER_AT_BREAK, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_TARGET_PRESENTATION_CONTEXT, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_TARGET_PROCESSING_CONTEXT, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_TARGET_STYLESHEET, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_TEXT_ALIGN, enumwriter);
		writermap.put(Constants.PR_TEXT_ALIGN_LAST, enumwriter);
		// 不确定
		writermap.put(Constants.PR_TEXT_ALTITUDE, fixlengthwriter);
		// writermap.put(Constants.PR_TEXT_DECORATION, new
		// IntegerAttributeWriter());
		// 不确定
		writermap.put(Constants.PR_TEXT_DEPTH, fixlengthwriter);
		// 不确定
		writermap.put(Constants.PR_TEXT_INDENT, mixlenwriter);
		// 没实现
		// writermap.put(Constants.PR_TEXT_SHADOW, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_TEXT_TRANSFORM, enumwriter);
		// 不确定
		writermap.put(Constants.PR_TOP, fixlengthwriter);
		writermap.put(Constants.PR_TREAT_AS_WORD_SPACE, enumwriter);
		writermap.put(Constants.PR_UNICODE_BIDI, enumwriter);
		// 不确定
		writermap.put(Constants.PR_VERTICAL_ALIGN, enumlengthwriter);
		writermap.put(Constants.PR_VISIBILITY, enumwriter);
		// 没实现
		// writermap.put(Constants.PR_VOICE_FAMILY, new
		// IntegerAttributeWriter());
		// 没实现
		// writermap.put(Constants.PR_VOLUME, new IntegerAttributeWriter());
		writermap.put(Constants.PR_WHITE_SPACE, enumwriter);
		// writermap.put(Constants.PR_WHITE_SPACE_COLLAPSE, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_WHITE_SPACE_TREATMENT, new
		// IntegerAttributeWriter());
		// writermap.put(Constants.PR_WIDOWS, new IntegerAttributeWriter());
		writermap.put(Constants.PR_WIDTH, fixlengthwriter);
		writermap.put(Constants.PR_WORD_SPACING, spacewriter);
		// writermap.put(Constants.PR_WRAP_OPTION, new
		// IntegerAttributeWriter());
//		writermap.put(Constants.PR_WRITING_MODE, );
		// 没实现
		// writermap.put(Constants.PR_XML_LANG, new IntegerAttributeWriter());
		writermap.put(Constants.PR_Z_INDEX, numberwriter);
		// writermap.put(Constants.PR_INTRUSION_DISPLACE, new
		// IntegerAttributeWriter());
		writermap.put(Constants.PR_INDEX_CLASS, stringwriter);
		writermap.put(Constants.PR_INDEX_KEY, stringwriter);
		writermap.put(Constants.PR_X_BLOCK_PROGRESSION_UNIT, fixlengthwriter);
		writermap.put(Constants.PR_EDITMODE, enumwriter);
		writermap.put(Constants.PR_XPATH, stringwriter);
		writermap.put(Constants.PR_TRANSLATEURL, stringwriter);
		writermap.put(Constants.PR_HIDENAME, stringwriter);
		writermap.put(Constants.PR_SRC_TYPE, stringwriter);
		writermap.put(Constants.PR_APHLA, numberwriter);

		writermap.put(Constants.PR_BARCODE_TYPE, enumwriter);
		writermap.put(Constants.PR_BARCODE_CONTENT, barcodetextwriter);
		// writermap.put(Constants.PR_BARCODE_VALUE,"barcode-value");
		writermap.put(Constants.PR_BARCODE_HEIGHT, fixlengthwriter);
		writermap.put(Constants.PR_BARCODE_MODULE, fixlengthwriter);
		writermap.put(Constants.PR_BARCODE_FONT_HEIGHT, fixlengthwriter);
		writermap.put(Constants.PR_BARCODE_FONT_FAMILY, stringwriter);
		writermap.put(Constants.PR_BARCODE_QUIET_HORIZONTAL, fixlengthwriter);
		writermap.put(Constants.PR_BARCODE_QUIET_VERTICAL, fixlengthwriter);
		//writermap.put(Constants.PR_BARCODE_ORIENTATION,"barcode-orientation");
		writermap.put(Constants.PR_BARCODE_ADDCHECKSUM, booleanwriter);
		writermap.put(Constants.PR_BARCODE_WIDE_TO_NARROW, doublewriter);
		writermap.put(Constants.PR_BARCODE_TEXT_CHAR_SPACE, fixlengthwriter);
		writermap.put(Constants.PR_BARCODE_STRING, stringwriter);
		writermap.put(Constants.PR_BARCODE_PRINT_TEXT, enumwriter);
		writermap.put(Constants.PR_BARCODE_TEXT_BLOCK, fixlengthwriter);
		writermap.put(Constants.PR_BARCODE_CODE_TYPE, enumwriter);
		writermap.put(Constants.PR_BARCODE_SUBSET, enumwriter);
		writermap.put(Constants.PR_BARCODE_MAKEUCC, enumwriter);
		// writermap.put(Constants.PR_ISSHOWTOTAL,"isshowtotal");
		// writermap.put(Constants.PR_NUMBERTEXT_TYPE,"numbertext-type");
		// writermap.put(Constants.PR_LAWLESSDATAPROCESS,"errordataprocess");
		writermap.put(Constants.PR_ENDOFALL, enumwriter);

		writermap.put(Constants.PR_NUMBERFORMAT, numberfarmatwriter);
		writermap.put(Constants.PR_POSITION_NUMBER_TYPE, enumwriter);
		writermap.put(Constants.PR_DATETIMEFORMAT, datetimefarmatwriter);
		writermap.put(Constants.PR_CONDTION, defaultwriter);
		writermap.put(Constants.PR_GROUP, defaultwriter);
		writermap.put(Constants.PR_NUMBERTEXT_TYPE, enumwriter);
		writermap.put(Constants.PR_HANGING_INDENT, mixlenwriter);
		writermap.put(Constants.PR_X, fixlengthwriter);
		writermap.put(Constants.PR_Y, fixlengthwriter);
		writermap.put(Constants.PR_X1, fixlengthwriter);
		writermap.put(Constants.PR_X2, fixlengthwriter);
		writermap.put(Constants.PR_Y1, fixlengthwriter);
		writermap.put(Constants.PR_Y2, fixlengthwriter);
		writermap.put(Constants.PR_CX, fixlengthwriter);
		writermap.put(Constants.PR_CY, fixlengthwriter);
		writermap.put(Constants.PR_R, fixlengthwriter);
		writermap.put(Constants.PR_RX, fixlengthwriter);
		writermap.put(Constants.PR_RY, fixlengthwriter);
		writermap.put(Constants.PR_FILL, mixfillwriter);
		writermap.put(Constants.PR_STROKE_WIDTH, fixlengthwriter);
		writermap.put(Constants.PR_SVG_STROKE_LINEJOIN, stringwriter);
		writermap.put(Constants.PR_SVG_TEXT_CONTENT, stringwriter);
		writermap.put(Constants.PR_DYNAMIC_STYLE, defaultwriter);
		writermap.put(Constants.PR_BLOCK_STYLE, defaultwriter);
		writermap.put(Constants.PR_BOOKMARK_TITLE, stringwriter);
		writermap.put(Constants.PR_BLOCK_LEVEL, defaultwriter);
		writermap.put(Constants.PR_BLOCK_REF_NUMBER, booleanwriter);
		writermap.put(Constants.PR_BLOCK_REF_RIGHT_ALIGN, booleanwriter);
		writermap.put(Constants.PR_BLOCK_REF_SHOW_LEVEL, defaultwriter);
		writermap.put(Constants.PR_BLOCK_REF_STYLES, tcsstylewriter);
		writermap.put(Constants.PR_BARCODE_EC_LEVEL, defaultwriter);
		writermap.put(Constants.PR_BARCODE_COLUMNS, defaultwriter);
		writermap.put(Constants.PR_BARCODE_MIN_COLUMNS, defaultwriter);
		writermap.put(Constants.PR_BARCODE_MAX_COLUMNS, defaultwriter);
		writermap.put(Constants.PR_BARCODE_MIN_ROWS, defaultwriter);
		writermap.put(Constants.PR_BARCODE_MAX_ROWS, defaultwriter);

		writermap.put(Constants.PR_CHART_TYPE, enumwriter);
		writermap.put(Constants.PR_TITLE, barcodetextwriter);
		writermap.put(Constants.PR_TITLE_FONTFAMILY, stringwriter);
		writermap.put(Constants.PR_TITLE_FONTSTYLE, enumwriter);
		writermap.put(Constants.PR_TITLE_FONTSIZE, fixlengthwriter);
		writermap.put(Constants.PR_TITLE_COLOR, colorwriter);
		writermap.put(Constants.PR_TITLE_ALIGNMENT, enumwriter);
		writermap.put(Constants.PR_BACKGROUNDIMAGE_ALAPH, doublewriter);
		writermap.put(Constants.PR_FOREGROUND_ALAPH, doublewriter);
		writermap.put(Constants.PR_VALUE_COUNT, defaultwriter);
		writermap.put(Constants.PR_SERIES_COUNT, defaultwriter);
		writermap.put(Constants.PR_VALUE_COLOR, colorlistwriter);
		writermap.put(Constants.PR_VALUE_LABEL, barcodetextlistwriter);
		writermap.put(Constants.PR_VALUE_LABEL_FONTFAMILY, stringwriter);
		writermap.put(Constants.PR_VALUE_LABEL_FONTSTYLE, enumwriter);
		writermap.put(Constants.PR_VALUE_LABEL_FONTSIZE, fixlengthwriter);
		writermap.put(Constants.PR_VALUE_LABEL_COLOR, colorwriter);
		writermap.put(Constants.PR_SERIES_VALUE, defaultwriter);
		writermap.put(Constants.PR_SERIES_LABEL, barcodetextlistwriter);
		writermap.put(Constants.PR_SERIES_LABEL_FONTFAMILY, stringwriter);
		writermap.put(Constants.PR_SERIES_LABEL_FONTSTYLE, enumwriter);
		writermap.put(Constants.PR_SERIES_LABEL_FONTSIZE, fixlengthwriter);
		writermap.put(Constants.PR_SERIES_LABEL_COLOR, colorwriter);
		writermap.put(Constants.PR_SERIES_LABEL_ORIENTATION, defaultwriter);
		writermap.put(Constants.PR_DOMAINAXIS_LABEL, barcodetextwriter);
		writermap.put(Constants.PR_RANGEAXIS_LABEL, barcodetextwriter);
		writermap.put(Constants.PR_DOMAINAXIS_LABEL_FONTFAMILY, stringwriter);
		writermap.put(Constants.PR_DOMAINAXIS_LABEL_FONTSTYLE, enumwriter);
		writermap.put(Constants.PR_DOMAINAXIS_LABEL_FONTSIZE, fixlengthwriter);
		writermap.put(Constants.PR_DOMAINAXIS_LABEL_COLOR, colorwriter);
		writermap.put(Constants.PR_DOMAINAXIS_LABEL_ALIGNMENT, enumwriter);
		writermap.put(Constants.PR_RANGEAXIS_LABEL_FONTFAMILY, stringwriter);
		writermap.put(Constants.PR_RANGEAXIS_LABEL_FONTSTYLE, enumwriter);
		writermap.put(Constants.PR_RANGEAXIS_LABEL_FONTSIZE, fixlengthwriter);
		writermap.put(Constants.PR_RANGEAXIS_LABEL_COLOR, colorwriter);
		writermap.put(Constants.PR_RANGEAXIS_LABEL_ALIGNMENT, enumwriter);
		writermap.put(Constants.PR_CHART_ORIENTATION, enumwriter);
		writermap.put(Constants.PR_RANGEAXIS_PRECISION, defaultwriter);
		writermap.put(Constants.PR_DOMIANLINE_VISABLE, enumwriter);
		writermap.put(Constants.PR_RANGELINE_VISABLE, enumwriter);
		writermap.put(Constants.PR_ZERORANGELINE_VISABLE, enumwriter);
		// writermap.put(Constants.PR_RANGEAXIS_UNITINCREMENT,
		// numbercontentwriter);
		// writermap.put(Constants.PR_RANGEAXIS_MINUNIT, numbercontentwriter);
		// writermap.put(Constants.PR_RANGEAXIS_MAXUNIT, numbercontentwriter);
		writermap.put(Constants.PR_3DENABLE, enumwriter);
		writermap.put(Constants.PR_3DDEPNESS, fixlengthwriter);
		writermap.put(Constants.PR_LENGEND_LABEL_VISABLE, enumwriter);
		writermap.put(Constants.PR_LENGEND_LABEL_LOCATION, enumwriter);
		writermap.put(Constants.PR_LENGEND_LABEL_FONTFAMILY, stringwriter);
		writermap.put(Constants.PR_LENGEND_LABEL_FONTSTYLE, enumwriter);
		writermap.put(Constants.PR_LENGEND_LABEL_FONTSIZE, fixlengthwriter);
		writermap.put(Constants.PR_LENGEND_LABEL_COLOR, colorwriter);
		writermap.put(Constants.PR_LENGEND_LABLE_ALIGNMENT, enumwriter);
		writermap.put(Constants.PR_VALUE_LABLEVISABLE, enumwriter);
		writermap.put(Constants.PR_CHART_VALUE_FONTFAMILY, stringwriter);
		writermap.put(Constants.PR_CHART_VALUE_FONTSTYLE, enumwriter);
		writermap.put(Constants.PR_CHART_VALUE_FONTSIZE, fixlengthwriter);
		writermap.put(Constants.PR_CHART_VALUE_COLOR, colorwriter);
		writermap.put(Constants.PR_CHART_VALUE_OFFSET, fixlengthwriter);
		writermap.put(Constants.PR_ZEROVALUE_VISABLE, enumwriter);
		writermap.put(Constants.PR_NULLVALUE_VISABLE, enumwriter);
		writermap.put(Constants.PR_PIECHART_STARTANGLE, defaultwriter);
		writermap.put(Constants.PR_PIECHART_DIRECTION, enumwriter);
		writermap.put(Constants.PR_PERCENTVALUE_VISABLE, enumwriter);
		writermap.put(Constants.PR_PIECHARTLENGENDLABEL_VISABLE, enumwriter);
		writermap.put(Constants.PR_PIE_FACT_VALUE_VISABLE, enumwriter);
		writermap.put(Constants.PR_EDITTYPE, enumwriter);
		writermap.put(Constants.PR_AUTHORITY, stringwriter);
		writermap.put(Constants.PR_ISRELOAD, enumwriter);
		writermap.put(Constants.PR_APPEARANCE, enumwriter);
		writermap.put(Constants.PR_EDIT_WIDTH, fixlengthwriter);
		writermap.put(Constants.PR_EDIT_HEIGHT, fixlengthwriter);
		writermap.put(Constants.PR_HINT, stringwriter);
		writermap.put(Constants.PR_DEFAULT_VALUE, stringwriter);
		writermap.put(Constants.PR_INPUT_TYPE, enumwriter);
		writermap.put(Constants.PR_INPUT_MULTILINE, enumwriter);
		writermap.put(Constants.PR_INPUT_WRAP, enumwriter);
		writermap.put(Constants.PR_INPUT_FILTER, stringwriter);
		writermap.put(Constants.PR_INPUT_FILTERMSG, stringwriter);
		writermap.put(Constants.PR_DATE_TYPE, enumwriter);
		writermap.put(Constants.PR_DATE_FORMAT, stringwriter);
		writermap.put(Constants.PR_RADIO_CHECK_VALUE, stringwriter);
		writermap.put(Constants.PR_CHECKBOX_UNSELECT_VALUE, stringwriter);
		writermap.put(Constants.PR_CHECKBOX_BOXSTYLE, enumwriter);
		writermap.put(Constants.PR_CHECKBOX_TICKMARK, enumwriter);
		writermap.put(Constants.PR_RADIO_CHECK_CHECKED, enumwriter);
		writermap.put(Constants.PR_SELECT_TYPE, enumwriter);
		writermap.put(Constants.PR_SELECT_ISEDIT, enumwriter);
		writermap.put(Constants.PR_SELECT_NEXT, nextwriter);
		writermap.put(Constants.PR_SELECT_SHOWLIST, enumwriter);
		writermap.put(Constants.PR_ONBLUR, validationwriter);
		writermap.put(Constants.PR_ONSELECTED, validationwriter);
		writermap.put(Constants.PR_ONKEYPRESS, validationwriter);
		writermap.put(Constants.PR_ONKEYDOWN, validationwriter);
		writermap.put(Constants.PR_ONKEYUP, validationwriter);
		writermap.put(Constants.PR_ONCHANGE, validationwriter);
		writermap.put(Constants.PR_ONCLICK, validationwriter);
		writermap.put(Constants.PR_ONEDIT, validationwriter);
		writermap.put(Constants.PR_ONRESULT, validationwriter);
		writermap.put(Constants.PR_SELECT_INFO, selectinfowriter);
		writermap.put(Constants.PR_POPUPBROWSER_INFO, popupbrowserinfowriter);
		writermap.put(Constants.PR_SELECT_NAME, stringwriter);
		writermap.put(Constants.PR_CONN_WITH, connwithwriter);
		writermap.put(Constants.PR_TRANSFORM_TABLE, datasourcewriter);
		writermap.put(Constants.PR_DATA_SOURCE, datasourcewriter);
		writermap.put(Constants.PR_XPATH_POSITION, defaultwriter);
		writermap.put(Constants.PR_EDIT_BUTTON, bslistwriter);
		writermap.put(Constants.PR_WORDARTTEXT_TYPE, enumwriter);
		writermap.put(Constants.PR_WORDARTTEXT_PATHVISABLE, enumwriter);
//		writermap.put(Constants.PR_WORDARTTEXT_ROTATION, defaultwriter);
//		writermap.put(Constants.PR_WORDARTTEXT_STARTPOSITION, defaultwriter);
		writermap.put(Constants.PR_WORDARTTEXT_LETTERSPACE, fixlengthwriter);
		writermap.put(Constants.PR_WORDARTTEXT_CONTENT, stringwriter);
		writermap.put(Constants.PR_CONTENT_TREAT, fixedareawriter);
		writermap.put(Constants.PR_GROUP_REFERANCE, groupuiwriter);
		writermap.put(Constants.PR_ZIMOBAN_NAME, stringwriter);
		writermap.put(Constants.PR_DBTYPE, stringwriter);
		// 以下属性还没处理

		// // 转换表
		//
		// // 节点pisition
		// int PR_XPATH_POSITION = 433;
		//
		//
		// int PR_BUTTON_TYPE = 435;
		//
		// int PR_BUTTON_INSERT_POSITION = 436;
		// writermap.put(Constants.PR_CHARACTER, new IntegerAttributeWriter());

		AttributeReader enumlengthreader = new EnumLengthReader();
		AttributeReader colorreader = new WiseDocColorReader();
		AttributeReader bgireader = new BackGroundImageReader();
		AttributeReader lengthrangereader = new LengthRangePropertyReader();
		AttributeReader enumreader = new EnumPropertyReader();
		AttributeReader numberreader = new NumberPropertyReader();
		AttributeReader conlengthreader = new CondLengthPropertyReader();
		AttributeReader charreader = new CharacterReader();
		AttributeReader stringreader = new StringReader();
		AttributeReader spacereader = new SpacePropertyReader();
		AttributeReader lengthpairreader = new LengthPairPropertyReader();
		AttributeReader fixlengthreader = new FixedLengthReader();
		AttributeReader keepreader = new KeepPropertyReader();
		AttributeReader enumnumbereader = new EnumNumberReader();

		AttributeReader numberformatreader = new NumberFormatReader();
		AttributeReader datetimeinforeader = new WisedocDateTimeFormatReader();
		AttributeReader conditionreader = new LogicalExpressionReader();
		AttributeReader groupreader = new GroupReader();
		AttributeReader booleanreader = new BooleanReader();
		AttributeReader doublereader = new DoubleReader();
		AttributeReader barcodetextreader = new BarCodeTextReader();
		AttributeReader mixlenreader = new MixLengthReader();
		AttributeReader mixfillreader = new MixFillReader();
		AttributeReader dynamicreader = new ConditionItemCollectionReader();
		AttributeReader paragraphstylereader = new ParagraphStyleReader();
		AttributeReader tcsstylereader = new TableContentsStyleReader();
		// AttributeReader numbercontentreader = new NumberContentReader();
		AttributeReader barcodetextlistreader = new BarCodeTextListReader();
		AttributeReader chartdatalistreader = new ChartDataListReader();
		AttributeReader colorlistreader = new ColorListReader();
		AttributeReader validationreader = new ValidationReader();
		AttributeReader datasourcereader = new DataSourceAttributeReader();
		AttributeReader selectinforeader = new SelectInfoAttReader();
		AttributeReader popupbrowserinforeader = new PopupBrowserInfoAttReader();
		AttributeReader connwithreader = new ConnWithAttReader();
		AttributeReader bglistreader = new ButtonGroupListAttReader();
		AttributeReader fixedareareader = new FixedareaReader();
		AttributeReader groupuireader = new GroupUIAttReader();
		AttributeReader nextreader = new NextReader();
		readermap.put(Constants.PR_ABSOLUTE_POSITION, enumreader);
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_ACTIVE_STATE, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_ALIGNMENT_ADJUST, enumlengthreader);
		// readermap.put(Constants.PR_ALIGNMENT_BASELINE, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_AUTO_RESTORE, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_AZIMUTH, new IntegerAttributeReader());
		// readermap.put(Constants.PR_BACKGROUND, new IntegerAttributeReader());
		readermap.put(Constants.PR_BACKGROUND_ATTACHMENT, enumreader);
		readermap.put(Constants.PR_BACKGROUND_COLOR, colorreader);
		readermap.put(Constants.PR_BACKGROUND_IMAGE, bgireader);
		// ???
		// readermap.put(Constants.PR_BACKGROUND_POSITION, new
		// IntegerAttributeReader());
		// ???????
		readermap
				.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, mixlenreader);
		readermap.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, mixlenreader);
		readermap.put(Constants.PR_BACKGROUND_REPEAT, enumreader);
		readermap.put(Constants.PR_BASELINE_SHIFT, enumlengthreader);
		// readermap.put(Constants.PR_BLANK_OR_NOT_BLANK,
		// new IntegerAttributeReader());
		readermap.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
				lengthrangereader);
		// readermap.put(Constants.PR_BORDER, new IntegerAttributeReader());
		readermap.put(Constants.PR_BORDER_AFTER_COLOR, colorreader);
		readermap.put(Constants.PR_BORDER_AFTER_PRECEDENCE, numberreader);
		readermap.put(Constants.PR_BORDER_AFTER_STYLE, enumreader);
		readermap.put(Constants.PR_BORDER_AFTER_WIDTH, conlengthreader);
		readermap.put(Constants.PR_BORDER_BEFORE_COLOR, colorreader);
		readermap.put(Constants.PR_BORDER_BEFORE_PRECEDENCE, numberreader);
		readermap.put(Constants.PR_BORDER_BEFORE_STYLE, enumreader);
		readermap.put(Constants.PR_BORDER_BEFORE_WIDTH, conlengthreader);
		// ?????
		// readermap.put(Constants.PR_BORDER_BOTTOM, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_BORDER_BOTTOM_COLOR, colorreader);
		readermap.put(Constants.PR_BORDER_BOTTOM_STYLE, enumreader);
		readermap.put(Constants.PR_BORDER_BOTTOM_WIDTH, conlengthreader);

		// readermap.put(Constants.PR_BORDER_COLLAPSE, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_BORDER_COLOR, colorreader);
		readermap.put(Constants.PR_BORDER_END_COLOR, colorreader);
		readermap.put(Constants.PR_BORDER_END_PRECEDENCE, numberreader);
		readermap.put(Constants.PR_BORDER_END_STYLE, enumreader);
		readermap.put(Constants.PR_BORDER_END_WIDTH, conlengthreader);
		// readermap.put(Constants.PR_BORDER_LEFT, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_BORDER_LEFT_COLOR, colorreader);
		readermap.put(Constants.PR_BORDER_LEFT_STYLE, enumreader);
		readermap.put(Constants.PR_BORDER_LEFT_WIDTH, conlengthreader);
		// readermap.put(Constants.PR_BORDER_RIGHT, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_BORDER_RIGHT_COLOR, colorreader);
		readermap.put(Constants.PR_BORDER_RIGHT_STYLE, enumreader);
		readermap.put(Constants.PR_BORDER_RIGHT_WIDTH, conlengthreader);
		// readermap.put(Constants.PR_BORDER_SEPARATION, new
		// LengthPairPropertyReader());
		// ??????
		readermap.put(Constants.PR_BORDER_SPACING, lengthpairreader);
		readermap.put(Constants.PR_BORDER_START_COLOR, colorreader);
		readermap.put(Constants.PR_BORDER_START_PRECEDENCE, numberreader);
		readermap.put(Constants.PR_BORDER_START_STYLE, enumreader);
		readermap.put(Constants.PR_BORDER_START_WIDTH, conlengthreader);
		readermap.put(Constants.PR_BORDER_STYLE, enumreader);
		// readermap.put(Constants.PR_BORDER_TOP, new IntegerAttributeReader());
		readermap.put(Constants.PR_BORDER_TOP_COLOR, colorreader);
		readermap.put(Constants.PR_BORDER_TOP_STYLE, enumreader);
		readermap.put(Constants.PR_BORDER_TOP_WIDTH, conlengthreader);
		readermap.put(Constants.PR_BORDER_WIDTH, conlengthreader);
		// readermap.put(Constants.PR_BOTTOM, new IntegerAttributeReader());
		readermap.put(Constants.PR_BREAK_AFTER, enumreader);
		readermap.put(Constants.PR_BREAK_BEFORE, enumreader);
		// readermap.put(Constants.PR_CAPTION_SIDE, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_CASE_NAME, new IntegerAttributeReader());
		// readermap.put(Constants.PR_CASE_TITLE, new IntegerAttributeReader());
		readermap.put(Constants.PR_CHARACTER, charreader);

		readermap.put(Constants.PR_CLEAR, enumreader);
		// readermap.put(Constants.PR_CLIP, new IntegerAttributeReader());
		readermap.put(Constants.PR_COLOR, colorreader);
		readermap.put(Constants.PR_COLOR_PROFILE_NAME, stringreader);
		// readermap.put(Constants.PR_COLUMN_COUNT, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_COLUMN_GAP, fixlengthreader);
		// readermap.put(Constants.PR_COLUMN_NUMBER, new
		// IntegerAttributeReader());
		// ?????
		readermap.put(Constants.PR_COLUMN_WIDTH, fixlengthreader);
		readermap.put(Constants.PR_CONTENT_HEIGHT, enumlengthreader);
		readermap.put(Constants.PR_CONTENT_TYPE, stringreader);
		readermap.put(Constants.PR_CONTENT_WIDTH, enumlengthreader);
		readermap.put(Constants.PR_COUNTRY, stringreader);
		// readermap.put(Constants.PR_CUE, new IntegerAttributeReader());
		// readermap.put(Constants.PR_CUE_AFTER, new IntegerAttributeReader());
		// readermap.put(Constants.PR_CUE_BEFORE, new IntegerAttributeReader());
		// readermap.put(Constants.PR_DESTINATION_PLACEMENT_OFFSET, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_DIRECTION, enumreader);
		readermap.put(Constants.PR_DISPLAY_ALIGN, enumreader);
		// readermap.put(Constants.PR_DOMINANT_BASELINE, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_ELEVATION, new IntegerAttributeReader());
		readermap.put(Constants.PR_EMPTY_CELLS, enumreader);
		// ????
		readermap.put(Constants.PR_END_INDENT, mixlenreader);
		readermap.put(Constants.PR_ENDS_ROW, enumreader);
		// ?????
		readermap.put(Constants.PR_EXTENT, fixlengthreader);
		readermap.put(Constants.PR_EXTERNAL_DESTINATION, stringreader);
		readermap.put(Constants.PR_FLOAT, enumreader);
		readermap.put(Constants.PR_FLOW_NAME, stringreader);
		// readermap.put(Constants.PR_FONT, new IntegerAttributeReader());
		readermap.put(Constants.PR_FONT_FAMILY, stringreader);
		readermap.put(Constants.PR_FONT_SELECTION_STRATEGY, enumreader);
		readermap.put(Constants.PR_FONT_SIZE, mixlenreader);
		readermap.put(Constants.PR_FONT_SIZE_ADJUST, numberreader);
		// readermap.put(Constants.PR_FONT_STRETCH, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_FONT_STYLE, enumreader);
		// readermap.put(Constants.PR_FONT_VARIANT, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_FONT_WEIGHT, enumreader);
		// IntegerAttributeReader());
		readermap.put(Constants.PR_FORCE_PAGE_COUNT, enumreader);
		readermap.put(Constants.PR_FORMAT, stringreader);
		// 没实现
		// readermap.put(Constants.PR_GLYPH_ORIENTATION_HORIZONTAL, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_GLYPH_ORIENTATION_VERTICAL, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_GROUPING_SEPARATOR, charreader);
		// readermap.put(Constants.PR_GROUPING_SIZE, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_HEIGHT, fixlengthreader);
		readermap.put(Constants.PR_HYPHENATE, enumreader);
		readermap.put(Constants.PR_HYPHENATION_CHARACTER, charreader);
		// readermap.put(Constants.PR_HYPHENATION_KEEP, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_HYPHENATION_LADDER_COUNT, numberreader);
		readermap.put(Constants.PR_HYPHENATION_PUSH_CHARACTER_COUNT,
				numberreader);
		readermap.put(Constants.PR_HYPHENATION_REMAIN_CHARACTER_COUNT,
				numberreader);
		readermap.put(Constants.PR_ID, stringreader);
		// 没实现
		// readermap.put(Constants.PR_INDICATE_DESTINATION, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_INITIAL_PAGE_NUMBER, enumnumbereader);
		readermap.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
				lengthrangereader);
		readermap.put(Constants.PR_INTERNAL_DESTINATION, stringreader);
		readermap.put(Constants.PR_KEEP_TOGETHER, keepreader);
		readermap.put(Constants.PR_KEEP_WITH_NEXT, keepreader);
		readermap.put(Constants.PR_KEEP_WITH_PREVIOUS, keepreader);
		readermap.put(Constants.PR_LANGUAGE, stringreader);
		readermap.put(Constants.PR_LAST_LINE_END_INDENT, fixlengthreader);
		readermap.put(Constants.PR_LEADER_ALIGNMENT, enumreader);
		readermap.put(Constants.PR_LEADER_LENGTH, lengthrangereader);
		readermap.put(Constants.PR_LEADER_PATTERN, enumreader);
		// IntegerAttributeReader());
		// 不确定
		// readermap.put(Constants.PR_LEADER_PATTERN_WIDTH, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_LEFT, fixlengthreader);
		readermap.put(Constants.PR_LETTER_SPACING, spacereader);
		// readermap.put(Constants.PR_LETTER_VALUE, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_LINEFEED_TREATMENT, new
		// IntegerAttributeReader());
		// 不确定
		readermap.put(Constants.PR_LINE_HEIGHT, spacereader);
		// readermap.put(Constants.PR_LINE_HEIGHT_SHIFT_ADJUSTMENT, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_LINE_STACKING_STRATEGY, new
		// IntegerAttributeReader());
		// 不确定
		// readermap.put(Constants.PR_MARGIN, new IntegerAttributeReader());
		readermap.put(Constants.PR_MARGIN_BOTTOM, fixlengthreader);
		readermap.put(Constants.PR_MARGIN_LEFT, fixlengthreader);
		readermap.put(Constants.PR_MARGIN_RIGHT, fixlengthreader);
		readermap.put(Constants.PR_MARGIN_TOP, fixlengthreader);
		readermap.put(Constants.PR_MARKER_CLASS_NAME, stringreader);
		readermap.put(Constants.PR_MASTER_NAME, stringreader);
		readermap.put(Constants.PR_VIRTUAL_MASTER_NAME, stringreader);
		readermap.put(Constants.PR_MASTER_REFERENCE, stringreader);
		// 不确定
		// readermap.put(Constants.PR_MAX_HEIGHT, new IntegerAttributeReader());
		readermap.put(Constants.PR_MAXIMUM_REPEATS, enumnumbereader);
		// 不确定
		// readermap.put(Constants.PR_MAX_WIDTH, new IntegerAttributeReader());
		readermap.put(Constants.PR_MEDIA_USAGE, stringreader);
		// 不确定
		// readermap.put(Constants.PR_MIN_HEIGHT, new IntegerAttributeReader());
		// 不确定
		// readermap.put(Constants.PR_MIN_WIDTH, new IntegerAttributeReader());
		// readermap.put(Constants.PR_NUMBER_COLUMNS_REPEATED, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_NUMBER_COLUMNS_SPANNED, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_NUMBER_ROWS_SPANNED, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_ODD_OR_EVEN, enumreader);
		readermap.put(Constants.PR_BLANK_OR_NOT_BLANK, enumreader);
		// readermap.put(Constants.PR_ORPHANS, new IntegerAttributeReader());
		readermap.put(Constants.PR_OVERFLOW, enumreader);
		// 不确定
		// readermap.put(Constants.PR_PADDING, new IntegerAttributeReader());
		readermap.put(Constants.PR_PADDING_AFTER, conlengthreader);
		readermap.put(Constants.PR_PADDING_BEFORE, conlengthreader);
		readermap.put(Constants.PR_PADDING_BOTTOM, conlengthreader);
		readermap.put(Constants.PR_PADDING_END, conlengthreader);
		readermap.put(Constants.PR_PADDING_LEFT, conlengthreader);
		readermap.put(Constants.PR_PADDING_RIGHT, conlengthreader);
		readermap.put(Constants.PR_PADDING_START, conlengthreader);
		readermap.put(Constants.PR_PADDING_TOP, conlengthreader);
		readermap.put(Constants.PR_PAGE_BREAK_AFTER, enumreader);
		readermap.put(Constants.PR_PAGE_BREAK_BEFORE, enumreader);
		readermap.put(Constants.PR_PAGE_BREAK_INSIDE, enumreader);
		readermap.put(Constants.PR_PAGE_HEIGHT, fixlengthreader);
		readermap.put(Constants.PR_PAGE_POSITION, enumreader);
		readermap.put(Constants.PR_PAGE_WIDTH, fixlengthreader);
		// 没实现
		// readermap.put(Constants.PR_PAUSE, new IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_PAUSE_AFTER, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_PAUSE_BEFORE, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_PITCH, new IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_PITCH_RANGE, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_PLAY_DURING, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_POSITION, enumreader);
		readermap.put(Constants.PR_PRECEDENCE, enumreader);
		// 不确定
		readermap.put(Constants.PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS,
				fixlengthreader);
		// 不确定
		readermap.put(Constants.PR_PROVISIONAL_LABEL_SEPARATION,
				fixlengthreader);
		readermap.put(Constants.PR_REFERENCE_ORIENTATION, numberreader);
		readermap.put(Constants.PR_REF_ID, stringreader);
		readermap.put(Constants.PR_REGION_NAME, stringreader);
		readermap.put(Constants.PR_RELATIVE_ALIGN, enumreader);
		// readermap.put(Constants.PR_RELATIVE_POSITION, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_RENDERING_INTENT, enumreader);
		readermap.put(Constants.PR_RETRIEVE_BOUNDARY, enumreader);
		readermap.put(Constants.PR_RETRIEVE_CLASS_NAME, stringreader);
		readermap.put(Constants.PR_RETRIEVE_POSITION, enumreader);
		// 没实现
		// readermap.put(Constants.PR_RICHNESS, new IntegerAttributeReader());
		readermap.put(Constants.PR_RIGHT, fixlengthreader);
		readermap.put(Constants.PR_ROLE, stringreader);
		readermap.put(Constants.PR_RULE_STYLE, enumreader);
		readermap.put(Constants.PR_RULE_THICKNESS, fixlengthreader);
		readermap.put(Constants.PR_SCALING, enumreader);
		readermap.put(Constants.PR_SCALING_METHOD, enumreader);
		readermap.put(Constants.PR_SCORE_SPACES, enumreader);
		readermap.put(Constants.PR_SCRIPT, stringreader);
		// 没实现
		// readermap.put(Constants.PR_SHOW_DESTINATION, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_SIZE, new IntegerAttributeReader());
		readermap.put(Constants.PR_SOURCE_DOCUMENT, stringreader);
		readermap.put(Constants.PR_SPACE_AFTER, spacereader);
		readermap.put(Constants.PR_SPACE_BEFORE, spacereader);
		readermap.put(Constants.PR_SPACE_END, spacereader);
		readermap.put(Constants.PR_SPACE_START, spacereader);
		// readermap.put(Constants.PR_SPAN, new IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_SPEAK, new IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_SPEAK_HEADER, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_SPEAK_NUMERAL, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_SPEAK_PUNCTUATION, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_SPEECH_RATE, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_SRC, bgireader);
		// 不确定
		readermap.put(Constants.PR_START_INDENT, mixlenreader);
		readermap.put(Constants.PR_STARTING_STATE, enumreader);
		readermap.put(Constants.PR_STARTS_ROW, enumreader);
		// 没实现
		// readermap.put(Constants.PR_STRESS, new IntegerAttributeReader());
		readermap.put(Constants.PR_SUPPRESS_AT_LINE_BREAK, enumreader);
		// 没实现
		// readermap.put(Constants.PR_SWITCH_TO, new IntegerAttributeReader());
		// readermap.put(Constants.PR_TABLE_LAYOUT, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_TABLE_OMIT_FOOTER_AT_BREAK, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_TABLE_OMIT_HEADER_AT_BREAK, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_TARGET_PRESENTATION_CONTEXT, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_TARGET_PROCESSING_CONTEXT, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_TARGET_STYLESHEET, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_TEXT_ALIGN, enumreader);
		readermap.put(Constants.PR_TEXT_ALIGN_LAST, enumreader);
		// 不确定
		readermap.put(Constants.PR_TEXT_ALTITUDE, fixlengthreader);
		// readermap.put(Constants.PR_TEXT_DECORATION, new
		// IntegerAttributeReader());
		// 不确定
		readermap.put(Constants.PR_TEXT_DEPTH, fixlengthreader);
		// 不确定
		readermap.put(Constants.PR_TEXT_INDENT, mixlenreader);
		// 没实现
		// readermap.put(Constants.PR_TEXT_SHADOW, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_TEXT_TRANSFORM, enumreader);
		// 不确定
		readermap.put(Constants.PR_TOP, fixlengthreader);
		readermap.put(Constants.PR_TREAT_AS_WORD_SPACE, enumreader);
		readermap.put(Constants.PR_UNICODE_BIDI, enumreader);
		// 不确定
		readermap.put(Constants.PR_VERTICAL_ALIGN, enumlengthreader);
		readermap.put(Constants.PR_VISIBILITY, enumreader);
		// 没实现
		// readermap.put(Constants.PR_VOICE_FAMILY, new
		// IntegerAttributeReader());
		// 没实现
		// readermap.put(Constants.PR_VOLUME, new IntegerAttributeReader());
		readermap.put(Constants.PR_WHITE_SPACE, enumreader);
		// readermap.put(Constants.PR_WHITE_SPACE_COLLAPSE, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_WHITE_SPACE_TREATMENT, new
		// IntegerAttributeReader());
		// readermap.put(Constants.PR_WIDOWS, new IntegerAttributeReader());
		readermap.put(Constants.PR_WIDTH, fixlengthreader);
		readermap.put(Constants.PR_WORD_SPACING, spacereader);
		// readermap.put(Constants.PR_WRAP_OPTION, new
		// IntegerAttributeReader());
//		readermap.put(Constants.PR_WRITING_MODE, enumreader);
		// 没实现
		// readermap.put(Constants.PR_XML_LANG, new IntegerAttributeReader());
		readermap.put(Constants.PR_Z_INDEX, numberreader);
		// readermap.put(Constants.PR_INTRUSION_DISPLACE, new
		// IntegerAttributeReader());
		readermap.put(Constants.PR_INDEX_CLASS, stringreader);
		readermap.put(Constants.PR_INDEX_KEY, stringreader);
		readermap.put(Constants.PR_X_BLOCK_PROGRESSION_UNIT, fixlengthreader);
		readermap.put(Constants.PR_EDITMODE, enumreader);
		readermap.put(Constants.PR_XPATH, stringreader);
		readermap.put(Constants.PR_TRANSLATEURL, stringreader);
		readermap.put(Constants.PR_HIDENAME, stringreader);
		readermap.put(Constants.PR_SRC_TYPE, stringreader);
		readermap.put(Constants.PR_APHLA, numberreader);

		readermap.put(Constants.PR_BARCODE_TYPE, enumreader);
		readermap.put(Constants.PR_BARCODE_CONTENT, barcodetextreader);
		// readermap.put(Constants.PR_BARCODE_VALUE,"barcode-value");
		readermap.put(Constants.PR_BARCODE_HEIGHT, fixlengthreader);
		readermap.put(Constants.PR_BARCODE_MODULE, fixlengthreader);
		readermap.put(Constants.PR_BARCODE_FONT_HEIGHT, fixlengthreader);
		readermap.put(Constants.PR_BARCODE_FONT_FAMILY, stringreader);
		readermap.put(Constants.PR_BARCODE_QUIET_HORIZONTAL, fixlengthreader);
		readermap.put(Constants.PR_BARCODE_QUIET_VERTICAL, fixlengthreader);
		//readermap.put(Constants.PR_BARCODE_ORIENTATION,"barcode-orientation");
		readermap.put(Constants.PR_BARCODE_ADDCHECKSUM, booleanreader);
		readermap.put(Constants.PR_BARCODE_WIDE_TO_NARROW, doublereader);
		readermap.put(Constants.PR_BARCODE_TEXT_CHAR_SPACE, fixlengthreader);
		readermap.put(Constants.PR_BARCODE_STRING, stringreader);
		readermap.put(Constants.PR_BARCODE_PRINT_TEXT, enumreader);
		readermap.put(Constants.PR_BARCODE_TEXT_BLOCK, fixlengthreader);
		readermap.put(Constants.PR_BARCODE_CODE_TYPE, enumreader);
		readermap.put(Constants.PR_BARCODE_SUBSET, enumreader);
		readermap.put(Constants.PR_BARCODE_MAKEUCC, enumreader);
		// readermap.put(Constants.PR_ISSHOWTOTAL,"isshowtotal");
		// readermap.put(Constants.PR_NUMBERTEXT_TYPE,"numbertext-type");
		// readermap.put(Constants.PR_LAWLESSDATAPROCESS,"errordataprocess");
		readermap.put(Constants.PR_ENDOFALL, enumreader);

		readermap.put(Constants.PR_NUMBERFORMAT, numberformatreader);
		readermap.put(Constants.PR_POSITION_NUMBER_TYPE, enumreader);
		readermap.put(Constants.PR_DATETIMEFORMAT, datetimeinforeader);
		readermap.put(Constants.PR_CONDTION, conditionreader);
		readermap.put(Constants.PR_GROUP, groupreader);
		readermap.put(Constants.PR_NUMBERTEXT_TYPE, enumreader);
		readermap.put(Constants.PR_HANGING_INDENT, mixlenreader);
		readermap.put(Constants.PR_X, fixlengthreader);
		readermap.put(Constants.PR_Y, fixlengthreader);
		readermap.put(Constants.PR_X1, fixlengthreader);
		readermap.put(Constants.PR_X2, fixlengthreader);
		readermap.put(Constants.PR_Y1, fixlengthreader);
		readermap.put(Constants.PR_Y2, fixlengthreader);
		readermap.put(Constants.PR_CX, fixlengthreader);
		readermap.put(Constants.PR_CY, fixlengthreader);
		readermap.put(Constants.PR_R, fixlengthreader);
		readermap.put(Constants.PR_RX, fixlengthreader);
		readermap.put(Constants.PR_RY, fixlengthreader);
		readermap.put(Constants.PR_FILL, mixfillreader);
		readermap.put(Constants.PR_STROKE_WIDTH, fixlengthreader);
		readermap.put(Constants.PR_SVG_STROKE_LINEJOIN, stringreader);
		readermap.put(Constants.PR_SVG_TEXT_CONTENT, stringreader);
		readermap.put(Constants.PR_DYNAMIC_STYLE, dynamicreader);
		readermap.put(Constants.PR_BLOCK_STYLE, paragraphstylereader);
		readermap.put(Constants.PR_BOOKMARK_TITLE, stringreader);
		readermap.put(Constants.PR_BLOCK_LEVEL, defaultreader);
		readermap.put(Constants.PR_BLOCK_REF_NUMBER, booleanreader);
		readermap.put(Constants.PR_BLOCK_REF_RIGHT_ALIGN, booleanreader);
		readermap.put(Constants.PR_BLOCK_REF_SHOW_LEVEL, defaultreader);
		readermap.put(Constants.PR_BLOCK_REF_STYLES, tcsstylereader);
		readermap.put(Constants.PR_BARCODE_EC_LEVEL, defaultreader);
		readermap.put(Constants.PR_BARCODE_COLUMNS, defaultreader);
		readermap.put(Constants.PR_BARCODE_MIN_COLUMNS, defaultreader);
		readermap.put(Constants.PR_BARCODE_MAX_COLUMNS, defaultreader);
		readermap.put(Constants.PR_BARCODE_MIN_ROWS, defaultreader);
		readermap.put(Constants.PR_BARCODE_MAX_ROWS, defaultreader);
		// 统计图相关属性
		readermap.put(Constants.PR_CHART_TYPE, enumreader);
		readermap.put(Constants.PR_TITLE, barcodetextreader);
		readermap.put(Constants.PR_TITLE_FONTFAMILY, stringreader);
		readermap.put(Constants.PR_TITLE_FONTSTYLE, enumreader);
		readermap.put(Constants.PR_TITLE_FONTSIZE, fixlengthreader);
		readermap.put(Constants.PR_TITLE_COLOR, colorreader);
		readermap.put(Constants.PR_TITLE_ALIGNMENT, enumreader);
		readermap.put(Constants.PR_BACKGROUNDIMAGE_ALAPH, doublereader);
		readermap.put(Constants.PR_FOREGROUND_ALAPH, doublereader);
		readermap.put(Constants.PR_VALUE_COUNT, defaultreader);
		readermap.put(Constants.PR_SERIES_COUNT, defaultreader);
		readermap.put(Constants.PR_VALUE_COLOR, colorlistreader);
		readermap.put(Constants.PR_VALUE_LABEL, barcodetextlistreader);
		readermap.put(Constants.PR_VALUE_LABEL_FONTFAMILY, stringreader);
		readermap.put(Constants.PR_VALUE_LABEL_FONTSTYLE, enumreader);
		readermap.put(Constants.PR_VALUE_LABEL_FONTSIZE, fixlengthreader);
		readermap.put(Constants.PR_VALUE_LABEL_COLOR, colorreader);
		readermap.put(Constants.PR_SERIES_VALUE, chartdatalistreader);
		readermap.put(Constants.PR_SERIES_LABEL, barcodetextlistreader);
		readermap.put(Constants.PR_SERIES_LABEL_FONTFAMILY, stringreader);
		readermap.put(Constants.PR_SERIES_LABEL_FONTSTYLE, enumreader);
		readermap.put(Constants.PR_SERIES_LABEL_FONTSIZE, fixlengthreader);
		readermap.put(Constants.PR_SERIES_LABEL_COLOR, colorreader);
		readermap.put(Constants.PR_SERIES_LABEL_ORIENTATION, defaultreader);
		readermap.put(Constants.PR_DOMAINAXIS_LABEL, barcodetextreader);
		readermap.put(Constants.PR_RANGEAXIS_LABEL, barcodetextreader);
		readermap.put(Constants.PR_DOMAINAXIS_LABEL_FONTFAMILY, stringreader);
		readermap.put(Constants.PR_DOMAINAXIS_LABEL_FONTSTYLE, enumreader);
		readermap.put(Constants.PR_DOMAINAXIS_LABEL_FONTSIZE, fixlengthreader);
		readermap.put(Constants.PR_DOMAINAXIS_LABEL_COLOR, colorreader);
		readermap.put(Constants.PR_DOMAINAXIS_LABEL_ALIGNMENT, enumreader);
		readermap.put(Constants.PR_RANGEAXIS_LABEL_FONTFAMILY, stringreader);
		readermap.put(Constants.PR_RANGEAXIS_LABEL_FONTSTYLE, enumreader);
		readermap.put(Constants.PR_RANGEAXIS_LABEL_FONTSIZE, fixlengthreader);
		readermap.put(Constants.PR_RANGEAXIS_LABEL_COLOR, colorreader);
		readermap.put(Constants.PR_RANGEAXIS_LABEL_ALIGNMENT, enumreader);
		readermap.put(Constants.PR_CHART_ORIENTATION, enumreader);
		readermap.put(Constants.PR_RANGEAXIS_PRECISION, defaultreader);
		readermap.put(Constants.PR_DOMIANLINE_VISABLE, enumreader);
		readermap.put(Constants.PR_RANGELINE_VISABLE, enumreader);
		readermap.put(Constants.PR_ZERORANGELINE_VISABLE, enumreader);
		// readermap.put(Constants.PR_RANGEAXIS_UNITINCREMENT,
		// numbercontentreader);
		// readermap.put(Constants.PR_RANGEAXIS_MINUNIT, numbercontentreader);
		// readermap.put(Constants.PR_RANGEAXIS_MAXUNIT, numbercontentreader);
		readermap.put(Constants.PR_3DENABLE, enumreader);
		readermap.put(Constants.PR_3DDEPNESS, fixlengthreader);
		readermap.put(Constants.PR_LENGEND_LABEL_VISABLE, enumreader);
		readermap.put(Constants.PR_LENGEND_LABEL_LOCATION, enumreader);
		readermap.put(Constants.PR_LENGEND_LABEL_FONTFAMILY, stringreader);
		readermap.put(Constants.PR_LENGEND_LABEL_FONTSTYLE, enumreader);
		readermap.put(Constants.PR_LENGEND_LABEL_FONTSIZE, fixlengthreader);
		readermap.put(Constants.PR_LENGEND_LABEL_COLOR, colorreader);
		readermap.put(Constants.PR_LENGEND_LABLE_ALIGNMENT, enumreader);
		readermap.put(Constants.PR_VALUE_LABLEVISABLE, enumreader);
		readermap.put(Constants.PR_CHART_VALUE_FONTFAMILY, stringreader);
		readermap.put(Constants.PR_CHART_VALUE_FONTSTYLE, enumreader);
		readermap.put(Constants.PR_CHART_VALUE_FONTSIZE, fixlengthreader);
		readermap.put(Constants.PR_CHART_VALUE_COLOR, colorreader);
		readermap.put(Constants.PR_CHART_VALUE_OFFSET, fixlengthreader);
		readermap.put(Constants.PR_ZEROVALUE_VISABLE, enumreader);
		readermap.put(Constants.PR_NULLVALUE_VISABLE, enumreader);
		readermap.put(Constants.PR_PIECHART_STARTANGLE, defaultreader);
		readermap.put(Constants.PR_PIECHART_DIRECTION, enumreader);
		readermap.put(Constants.PR_PERCENTVALUE_VISABLE, enumreader);
		readermap.put(Constants.PR_PIECHARTLENGENDLABEL_VISABLE, enumreader);
		readermap.put(Constants.PR_PIE_FACT_VALUE_VISABLE, enumreader);
		readermap.put(Constants.PR_EDITTYPE, enumreader);
		readermap.put(Constants.PR_AUTHORITY, stringreader);
		readermap.put(Constants.PR_ISRELOAD, enumreader);
		readermap.put(Constants.PR_APPEARANCE, enumreader);
		readermap.put(Constants.PR_EDIT_WIDTH, fixlengthreader);
		readermap.put(Constants.PR_EDIT_HEIGHT, fixlengthreader);
		readermap.put(Constants.PR_HINT, stringreader);
		readermap.put(Constants.PR_DEFAULT_VALUE, stringreader);
		readermap.put(Constants.PR_INPUT_TYPE, enumreader);
		readermap.put(Constants.PR_INPUT_MULTILINE, enumreader);
		readermap.put(Constants.PR_INPUT_WRAP, enumreader);
		readermap.put(Constants.PR_INPUT_FILTER, stringreader);
		readermap.put(Constants.PR_INPUT_FILTERMSG, stringreader);
		readermap.put(Constants.PR_DATE_TYPE, enumreader);
		readermap.put(Constants.PR_DATE_FORMAT, stringreader);
		readermap.put(Constants.PR_RADIO_CHECK_VALUE, stringreader);
		readermap.put(Constants.PR_CHECKBOX_UNSELECT_VALUE, stringreader);
		readermap.put(Constants.PR_CHECKBOX_BOXSTYLE, enumreader);
		readermap.put(Constants.PR_CHECKBOX_TICKMARK, enumreader);
		readermap.put(Constants.PR_RADIO_CHECK_CHECKED, enumreader);
		readermap.put(Constants.PR_SELECT_TYPE, enumreader);
		readermap.put(Constants.PR_SELECT_ISEDIT, enumreader);
		readermap.put(Constants.PR_SELECT_NEXT, nextreader);
		readermap.put(Constants.PR_SELECT_SHOWLIST, enumreader);
		readermap.put(Constants.PR_ONBLUR, validationreader);
		readermap.put(Constants.PR_ONSELECTED, validationreader);
		readermap.put(Constants.PR_ONKEYPRESS, validationreader);
		readermap.put(Constants.PR_ONKEYDOWN, validationreader);
		readermap.put(Constants.PR_ONKEYUP, validationreader);
		readermap.put(Constants.PR_ONCHANGE, validationreader);
		readermap.put(Constants.PR_ONCLICK, validationreader);
		readermap.put(Constants.PR_ONEDIT, validationreader);
		readermap.put(Constants.PR_ONRESULT, validationreader);
		readermap.put(Constants.PR_SELECT_INFO, selectinforeader);
		readermap.put(Constants.PR_POPUPBROWSER_INFO, popupbrowserinforeader);
		readermap.put(Constants.PR_SELECT_NAME, stringreader);
		readermap.put(Constants.PR_TRANSFORM_TABLE, datasourcereader);
		readermap.put(Constants.PR_DATA_SOURCE, datasourcereader);
		readermap.put(Constants.PR_CONN_WITH, connwithreader);
		readermap.put(Constants.PR_XPATH_POSITION, conditionreader);
		readermap.put(Constants.PR_EDIT_BUTTON, bglistreader);
		readermap.put(Constants.PR_WORDARTTEXT_TYPE, enumreader);
		readermap.put(Constants.PR_WORDARTTEXT_PATHVISABLE, enumreader);
//		readermap.put(Constants.PR_WORDARTTEXT_ROTATION, enumreader);
//		readermap.put(Constants.PR_WORDARTTEXT_STARTPOSITION, enumreader);
		readermap.put(Constants.PR_WORDARTTEXT_LETTERSPACE, fixlengthreader);
		readermap.put(Constants.PR_WORDARTTEXT_CONTENT, stringreader);
		readermap.put(Constants.PR_CONTENT_TREAT, fixedareareader);
		readermap.put(Constants.PR_GROUP_REFERANCE, groupuireader);
		readermap.put(Constants.PR_ZIMOBAN_NAME, stringreader);
		readermap.put(Constants.PR_DBTYPE, stringreader);
		// readermap.put(Constants.PR_CHARACTER, new IntegerAttributeReader());

		classwritermap.put(Character.class, charwriter);
		classwritermap.put(EnumNumber.class, enumnumberwriter);
		classwritermap.put(EnumLength.class, enumlengthwriter);
		classwritermap.put(Color.class, colorwriter);
		classwritermap.put(WiseDocColor.class, colorwriter);
		classwritermap.put(LengthRangeProperty.class, lengthrangewriter);
		classwritermap.put(EnumProperty.class, enumwriter);
		classwritermap.put(NumberProperty.class, numberwriter);
		classwritermap.put(CondLengthProperty.class, conlengthwriter);
		classwritermap.put(String.class, stringwriter);
		classwritermap.put(SpaceProperty.class, spacewriter);
		classwritermap.put(LengthPairProperty.class, lengthpairwriter);
		classwritermap.put(FixedLength.class, fixlengthwriter);
		classwritermap.put(KeepProperty.class, keepwriter);
		classwritermap.put(NumberFormat.class, numberfarmatwriter);
		classwritermap.put(WisedocDateTimeFormat.class, datetimefarmatwriter);
		classwritermap.put(LogicalExpression.class, conditionwriter);
		classwritermap.put(Group.class, groupwriter);
		classwritermap.put(PercentLength.class, perlen);
		classwritermap.put(ConditionItemCollection.class, dynamicwriter);
		classwritermap.put(ParagraphStyles.class, paragraphstylewriter);
		classwritermap.put(ChartDataList.class, chartdatalistwriter);
		DataSourceWriter datasourceclasswriter = new DataSourceWriter();
		classwritermap.put(MultiSource.class, datasourceclasswriter);
		classwritermap.put(TransformTable.class, datasourceclasswriter);
		classwritermap.put(DataTransformTable.class, datasourceclasswriter);
		classwritermap.put(TransformTable.class, datasourceclasswriter);
		classwritermap.put(OutInterface.class, datasourceclasswriter);
		classwritermap.put(SwingDS.class, datasourceclasswriter);
		classwritermap.put(PopupBrowserSource.class, datasourceclasswriter);
		
		classwritermap.put(ConnWith.class, new ConnWithWriter());
		classwritermap.put(SelectInfo.class, new SelectInfoWirter());
		classwritermap.put(PopupBrowserInfo.class, new PopupBrowserInfoWirter());
		ButtonGroupListWriter bglistwriter = new ButtonGroupListWriter();
		classwritermap.put(List.class, bglistwriter);
		classwritermap.put(ArrayList.class, bglistwriter);
		classwritermap.put(GroupUI.class, new GroupUIWriter());
	}

	public AttributeReader getAttributeReader(int key)
	{
		AttributeReader reader = readermap.get(key);
		if (reader == null)
		{
			reader = defaultreader;
		}
		return reader;
	}

	public AttributeWriter getAttributeWriter(int key)
	{
		AttributeWriter writer = writermap.get(key);
		if (writer == null)
		{
			writer = defaultwriter;
		}
		return writer;
	}

	/**
	 * 
	 * 根据属性class类型返回属性writer，在生成复合属性的代码是调用
	 * 
	 * @param clazs
	 *            :属性的class
	 * @return
	 * @exception
	 */
	public AttributeWriter getAttributeWriter(Class clazs)
	{
		AttributeWriter writer = classwritermap.get(clazs);
		if (writer == null)
		{
			writer = defaultwriter;
		}
		return writer;
	}

	public AttributeKeyNameFactory getAttributeKeyNameFactory()
	{
		// TODO Auto-generated method stub
		return new WSDAttribueKeyNameFactory();
	}
    public void initWsdHander(AbstractElementsHandler handler)
    {
    	for(AttributeReader reader:readermap.values())
    	{
    		reader.initWsdHandler(handler);
    	}
    }
}
