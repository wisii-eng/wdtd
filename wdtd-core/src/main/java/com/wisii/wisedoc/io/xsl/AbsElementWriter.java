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
 * @SimpleXsltElement.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableFooter;
import com.wisii.wisedoc.document.TableHeader;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.DateInfo;
import com.wisii.wisedoc.document.attribute.DateTimeItem;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.document.attribute.TimeInfo;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DateTimeType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DigitType;
import com.wisii.wisedoc.document.attribute.edit.ButtonGroup;
import com.wisii.wisedoc.document.attribute.edit.Validation;
import com.wisii.wisedoc.document.svg.SVGUtil;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.ElementWriterFactory;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.xsl.attribute.edit.EditUI;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-4-25
 */
public abstract class AbsElementWriter implements FoElementIFWriter
{

	protected static ElementWriterFactory ewf = IOFactory
			.getElementWriterFactory(IOFactory.XSL);

	SelectOutputAttributeWriter writerFactory = new SelectOutputAttributeWriter();

	CellElement foElement;

	Map<Integer, List<ConditionAndValue>> dongtaiMap = new HashMap<Integer, List<ConditionAndValue>>();

	Map<Integer, Object> attributemap = new HashMap<Integer, Object>();

	String edituiname = "";

	protected String editauthority = "";
	protected String defaultvalue="";

	String buttons = "";

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @excetion 说明在某情况下,将发生什么异常}
	 */
	public AbsElementWriter(CellElement element)
	{
		foElement = element;
		edituiname = "";
		editauthority = "";
		defaultvalue="";
		buttons = "";
		setAttributemap();
	}

	public String write(CellElement element)
	{
		StringBuffer result = new StringBuffer();
		result.append(beforeDeal());
		String output = "";
		if (!(foElement instanceof com.wisii.wisedoc.document.Group))
		{
			output = output + getCode();
		}
		result.append(output);
		result.append(endDeal());
		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getAttibute(java.lang.Object,
	 * java.lang.Object)
	 */
	public String getAttribute(int key, Object value)
	{
		return writerFactory.write(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getAttributes()
	 */
	public String getAttributes()
	{
		StringBuffer code = new StringBuffer();
		Object[] keys = attributemap.keySet().toArray();
		int size = keys.length;
		List<Color> borderColor = new ArrayList<Color>();
		List<EnumProperty> borderStyle = new ArrayList<EnumProperty>();
		List<CondLengthProperty> borderWidth = new ArrayList<CondLengthProperty>();
		String bordercolor = "";
		String borderstyle = "";
		String borderwidth = "";
		List<CondLengthProperty> paddingList = new ArrayList<CondLengthProperty>();
		String padding = "";
		CellElement ps = foElement;
		while (!(ps instanceof PageSequence))
		{
			if (ps.getParent() != null)
			{
				ps = (CellElement) ps.getParent();
			} else
			{
				break;
			}
		}

		int index = ps.getParent() != null ? ps.getParent().getIndex(ps) : 0;
		Element parent = foElement.getParent();
		int indexthis = parent != null ? parent.getIndex(foElement) : 0;

		if (!attributemap.containsKey(Constants.PR_DYNAMIC_STYLE))
		{
			for (int i = 0; i < size; i++)
			{
				int key = (Integer) keys[i];
				if (key == Constants.PR_FONT_FAMILY)
				{
					if (!attributemap
							.containsKey(Constants.PR_CHECKBOX_BOXSTYLE))
					{
						code.append(getAttribute(key, "'"
								+ attributemap.get(key) + "'"));
					}
				} else if (key == Constants.PR_BACKGROUND_IMAGE)
				{
					int indexbgi = foElement.getParent().getIndex(foElement);
					code.append(getAttribute(key, "{$bgi" + indexbgi + "}"));
				} else if (key == Constants.PR_DATETIMEFORMAT
						|| key == Constants.PR_NUMBERFORMAT
						|| key == Constants.PR_POSITION_NUMBER_TYPE
						|| key == Constants.PR_CONDTION
						|| key == Constants.PR_GROUP
						|| key == Constants.PR_EDITMODE
						|| key == Constants.PR_SRC
						|| key == Constants.PR_BLOCK_LEVEL
						|| key == Constants.PR_EDIT_BUTTON
						// 因为checkbox的编辑和常规属性用的同样的key，所以不能再处理完编辑部分就将这些key去掉了
						|| key == Constants.PR_INPUT_TYPE
						|| key == Constants.PR_INPUT_MULTILINE
						|| key == Constants.PR_INPUT_WRAP
						|| key == Constants.PR_INPUT_FILTER
						|| key == Constants.PR_DATE_TYPE
						|| key == Constants.PR_DATE_FORMAT
						|| key == Constants.PR_RADIO_CHECK_VALUE
						|| key == Constants.PR_RADIO_CHECK_CHECKED
						|| key == Constants.PR_SELECT_MULTIPLE
						|| key == Constants.PR_SELECT_LINES
						|| key == Constants.PR_SELECT_ISEDIT
						|| key == Constants.PR_SELECT_NEXT
						|| key == Constants.PR_SELECT_SHOWLIST
						|| key == Constants.PR_ISRELOAD
						|| key == Constants.PR_APPEARANCE
						|| key == Constants.PR_EDIT_WIDTH
						|| key == Constants.PR_EDIT_HEIGHT
						|| key == Constants.PR_HINT
						|| key == Constants.PR_ONBLUR
						|| key == Constants.PR_ONSELECTED
						|| key == Constants.PR_ONKEYPRESS
						|| key == Constants.PR_ONKEYDOWN
						|| key == Constants.PR_ONKEYUP
						|| key == Constants.PR_ONCHANGE
						|| key == Constants.PR_ONCLICK
						|| key == Constants.PR_ONEDIT
						|| key == Constants.PR_ONRESULT
						|| key == Constants.PR_BUTTON_TYPE
						|| key == Constants.PR_BUTTON_NODATAXPTH
						|| key == Constants.PR_BUTTON_INSERT_POSITION
						|| key == Constants.PR_CONN_WITH
						|| key == Constants.PR_CHECKBOX_UNSELECT_VALUE
						|| key == Constants.PR_CHECKBOX_TICKMARK
						|| key == Constants.PR_SELECT_TYPE
						|| key == Constants.PR_SELECT_INFO
						|| key == Constants.PR_POPUPBROWSER_INFO
						|| key == Constants.PR_GROUP_REFERANCE
						|| key == Constants.PR_INPUT_FILTERMSG
						|| key == Constants.PR_SELECT_NAME
						|| key == Constants.PR_COLUMN_NUMBER
						|| key == Constants.PR_DATA_SOURCE)
				{
				} else if (key == Constants.PR_CHECKBOX_BOXSTYLE)
				{
					code.append(ElementUtil.outputAttributes(
							FoXsltConstants.FONT_FAMILY, "'Wingdings 2'"));
					EnumProperty cbstyle = (EnumProperty) attributemap
							.get(Constants.PR_CHECKBOX_BOXSTYLE);
					boolean addflg = true;
					if (cbstyle != null)
					{
						int value = cbstyle.getEnum();
						if (value == Constants.EN_CHECKBOX_BOXSTYLE_CIRCLE)
						{
							addflg = false;
						}
					}
					if (addflg)
					{
						code.append(ElementUtil.outputAttributes(
								FoXsltConstants.BORDER_STYLE,
								FoXsltConstants.SOLID));
						code.append(ElementUtil.outputAttributes(
								FoXsltConstants.BORDER_WIDTH, "0.5pt"));
						String layer = "0";
						if (attributemap
								.containsKey(Constants.PR_GRAPHIC_LAYER))
						{
							layer = attributemap
									.get(Constants.PR_GRAPHIC_LAYER).toString();
						}
						code.append(ElementUtil.outputAttributes(
								FoXsltConstants.BORDER_COLOR, "rgb(0,0,0,"
										+ layer + ")"));
					}
				} else if (key == Constants.PR_GRAPHIC_LAYER)
				{
					if (!(foElement instanceof CheckBoxInline))
					{
						code.append(getAttribute(key, attributemap.get(key)));
					}
				}

				else if (key == Constants.PR_BORDER_BEFORE_COLOR
						|| key == Constants.PR_BORDER_AFTER_COLOR
						|| key == Constants.PR_BORDER_START_COLOR
						|| key == Constants.PR_BORDER_END_COLOR)
				{
					Color color = (Color) attributemap.get(key);
					bordercolor = bordercolor + getAttribute(key, color);
					borderColor.add(color);
				} else if (key == Constants.PR_BORDER_BEFORE_STYLE
						|| key == Constants.PR_BORDER_AFTER_STYLE
						|| key == Constants.PR_BORDER_START_STYLE
						|| key == Constants.PR_BORDER_END_STYLE)
				{
					EnumProperty style = (EnumProperty) attributemap.get(key);
					borderstyle = borderstyle + getAttribute(key, style);
					borderStyle.add(style);
				} else if (key == Constants.PR_BORDER_BEFORE_WIDTH
						|| key == Constants.PR_BORDER_AFTER_WIDTH
						|| key == Constants.PR_BORDER_START_WIDTH
						|| key == Constants.PR_BORDER_END_WIDTH)
				{
					CondLengthProperty width = (CondLengthProperty) attributemap
							.get(key);
					borderwidth = borderwidth + getAttribute(key, width);
					borderWidth.add(width);
				} else if (key == Constants.PR_PADDING_BEFORE
						|| key == Constants.PR_PADDING_AFTER
						|| key == Constants.PR_PADDING_START
						|| key == Constants.PR_PADDING_END)
				{
					CondLengthProperty paddingLength = (CondLengthProperty) attributemap
							.get(key);
					padding = padding + getAttribute(key, paddingLength);
					paddingList.add(paddingLength);
				} else if (key == Constants.PR_ID || key == Constants.PR_REF_ID
						|| key == Constants.PR_INTERNAL_DESTINATION)
				{
					code.append(getAttribute(key, "{$i" + indexthis + "}"));

				} else if (key == Constants.PR_NUMBER_COLUMNS_SPANNED)
				{
					if (!TableWriter.getCurrentStatic())
					{
						code.append(getAttribute(key, attributemap.get(key)));
					}
				} else
				{
					code.append(getAttribute(key, attributemap.get(key)));
				}
			}
			if (colorEqual(borderColor))
			{
				code.append(getAttribute(Constants.PR_BORDER_COLOR, borderColor
						.get(0)));
			} else
			{
				code.append(bordercolor);
			}
			if (styleEqual(borderStyle))
			{
				code.append(getAttribute(Constants.PR_BORDER_STYLE, borderStyle
						.get(0)));
			} else
			{
				code.append(borderstyle);
			}
			if (widthEqual(borderWidth))
			{
				code.append(getAttribute(Constants.PR_BORDER_WIDTH, borderWidth
						.get(0)));
			} else
			{
				code.append(borderwidth);
			}
			if (widthEqual(paddingList))
			{
				code.append(getAttribute(Constants.PR_PADDING, paddingList
						.get(0)));
			} else
			{
				code.append(padding);
			}

		} else
		{
			for (int i = 0; i < size; i++)
			{
				int key = (Integer) keys[i];
				if (!dongtaiMap.containsKey(key))
				{
					if (key == Constants.PR_ID)
					{
						String id = attributemap.get(key).toString();
						code.append(getAttribute(key, IoXslUtil.getId(index
								+ id)));
					} else if (key == Constants.PR_FONT_FAMILY)
					{
						code.append(getAttribute(key, "'"
								+ attributemap.get(key) + "'"));
					} else if (key == Constants.PR_DATETIMEFORMAT
							|| key == Constants.PR_NUMBERFORMAT
							|| key == Constants.PR_POSITION_NUMBER_TYPE
							|| key == Constants.PR_CONDTION
							|| key == Constants.PR_GROUP
							|| key == Constants.PR_HANGING_INDENT
							|| key == Constants.PR_EDITMODE
							|| key == Constants.PR_SRC
							|| key == Constants.PR_DYNAMIC_STYLE
							|| key == Constants.PR_EDIT_BUTTON
							|| key == Constants.PR_COLUMN_NUMBER)
					{
					} else
					{
						code.append(getAttribute(key, attributemap.get(key)));
					}
				}
			}
			Object[] keysdong = dongtaiMap.keySet().toArray();
			for (int j = 0; j < dongtaiMap.size(); j++)
			{
				int key = (Integer) keysdong[j];
				code.append(getAttribute(key, "{$" + "n" + indexthis + "a"
						+ key + "}"));
			}
		}
		if ((foElement instanceof TableBody)
				|| (foElement instanceof TableHeader)
				|| (foElement instanceof TableFooter))
		{
			FixedLength zero = new FixedLength(0d, "pt");
			if (parent.getAttribute(Constants.PR_START_INDENT) != null)
			{
				code.append(getAttribute(Constants.PR_START_INDENT, zero));
			}
			if (parent.getAttribute(Constants.PR_END_INDENT) != null)
			{
				code.append(getAttribute(Constants.PR_END_INDENT, zero));
			}
		}
		if ((foElement instanceof TableCell)
				&& attributemap.containsKey(Constants.PR_NUMBER_ROWS_SPANNED))
		{
			int rownum = (Integer) attributemap
					.get(Constants.PR_NUMBER_ROWS_SPANNED);
			if (rownum > 1)
			{
				if (attributemap.containsKey(Constants.PR_GROUP))
				{
					code.append(ElementUtil
							.outputAttributes(FoXsltConstants.ID, "{$"
									+ FoXsltConstants.ID + "}"));
				} else
				{
					code.append(ElementUtil.outputAttributes(
							FoXsltConstants.ID, "'" + foElement.hashCode()
									+ "'"));
				}
			}
		}
		return code.toString();
	}

	public boolean colorEqual(List<Color> lists)
	{
		boolean flg = false;
		int size = lists.size();
		if (size == 4)
		{
			if (lists.get(0).equals(lists.get(1))
					&& lists.get(0).equals(lists.get(2))
					&& lists.get(0).equals(lists.get(3)))
			{
				flg = true;
			}
		}
		return flg;
	}

	public boolean styleEqual(List<EnumProperty> lists)
	{
		boolean flg = false;
		int size = lists.size();
		if (size == 4)
		{
			if (lists.get(0).equals(lists.get(1))
					&& lists.get(0).equals(lists.get(2))
					&& lists.get(0).equals(lists.get(3)))
			{
				flg = true;
			}
		}
		return flg;
	}

	public boolean widthEqual(List<CondLengthProperty> lists)
	{
		boolean flg = false;
		int size = lists.size();
		if (size == 4)
		{
			if (lists.get(0).equals(lists.get(1))
					&& lists.get(0).equals(lists.get(2))
					&& lists.get(0).equals(lists.get(3)))
			{
				flg = true;
			}
		}
		return flg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getChildCode(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	protected String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (foElement != null && foElement.children() != null)
		{
			Enumeration<CellElement> listobj = foElement.children();
			if (!listobj.hasMoreElements())
			{
				if (foElement instanceof Flow || foElement instanceof TableCell)
				{
					output.append(ElementWriter.TAGBEGIN);
					output.append(FoXsltConstants.BLOCK);
					output.append(ElementWriter.TAGEND);
					output.append(ElementWriter.TAGENDSTART);
					output.append(FoXsltConstants.BLOCK);
					output.append(ElementWriter.TAGEND);
				} else if (foElement instanceof Block)
				{
					output.append(" ");
				}
			}
			while (listobj.hasMoreElements())
			{
				CellElement obj = listobj.nextElement();
				if (obj instanceof ZiMoban)
				{
					output.append(new ZiMobanWriter(obj).write(obj));
				} else
				{
					output.append(ewf.getWriter(obj).write(obj));
				}
			}
		}
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getCode()
	 */
	public String getCode()
	{
		StringBuffer code = new StringBuffer();
		code.append(getAttributeVariable());
		code.append(getHeaderElement());
		code.append(getChildCode());
		code.append(getEndElement());
		return code.toString();
	}

	@SuppressWarnings("unchecked")
	public String getAttributeVariable()
	{
		StringBuffer code = new StringBuffer();
		if (attributemap.containsKey(Constants.PR_EDITTYPE)
				&& IoXslUtil.isXmlEditable() != Constants.EN_UNEDITABLE)
		{
			EnumProperty type = (EnumProperty) attributemap
					.get(Constants.PR_EDITTYPE);
			if (type != null)
			{
				Map<Integer, Object> editmap = getEditMap();
				if (type.getEnum() == Constants.EN_DATE)
				{
					WisedocDateTimeFormat format = (WisedocDateTimeFormat) attributemap
							.get(Constants.PR_DATETIMEFORMAT);
					String inputformat = getFormat(format);
					editmap.put(Constants.PR_DATE_FORMAT, inputformat);
				}
				EditUI current = new EditUI(type, editmap);
				edituiname = IoXslUtil.addEditUI(current);
				NameSpace wdwens = new NameSpace(FoXsltConstants.SPACENAMEWDWE,
						FoXsltConstants.WDWE);
				IoXslUtil.addNameSpace(wdwens);
			}
		}
		Element parent = foElement.getParent();
		int index = parent != null ? parent.getIndex(foElement) : 0;
		if (attributemap.containsKey(Constants.PR_DYNAMIC_STYLE))
		{
			List<ConditionItem> conditions = (List<ConditionItem>) attributemap
					.get(Constants.PR_DYNAMIC_STYLE);
			for (ConditionItem current : conditions)
			{
				Map<Integer, Object> styles = current.getStyles();
				if (foElement instanceof BlockContainer)
				{
					if (styles.containsKey(Constants.PR_PADDING_START)
							|| styles.containsKey(Constants.PR_PADDING_END)
							|| styles
									.containsKey(Constants.PR_INLINE_PROGRESSION_DIMENSION))
					{
						CondLengthProperty paddingstart;
						if (styles.containsKey(Constants.PR_PADDING_START))
						{
							paddingstart = (CondLengthProperty) styles
									.get(Constants.PR_PADDING_START);
							FixedLength psfl = (FixedLength) paddingstart
									.getLength();
							FixedLength startindent = null;
							if (styles.containsKey(Constants.PR_START_INDENT))
							{
								startindent = (FixedLength) styles
										.get(Constants.PR_START_INDENT);
							}
							int hi = psfl.getValue();
							int si = startindent == null ? 0 : startindent
									.getValue();
							FixedLength newtextindent = new FixedLength(si + hi);
							styles
									.put(Constants.PR_START_INDENT,
											newtextindent);
						} else
						{
							paddingstart = (CondLengthProperty) attributemap
									.get(Constants.PR_PADDING_START);
						}
						CondLengthProperty paddingend;
						if (styles.containsKey(Constants.PR_PADDING_END))
						{
							paddingend = (CondLengthProperty) styles
									.get(Constants.PR_PADDING_END);
							FixedLength psfl = (FixedLength) paddingend
									.getLength();
							FixedLength startindent = null;
							if (styles.containsKey(Constants.PR_END_INDENT))
							{
								startindent = (FixedLength) styles
										.get(Constants.PR_END_INDENT);
							}
							int hi = psfl.getValue();
							int si = startindent == null ? 0 : startindent
									.getValue();
							FixedLength newtextindent = new FixedLength(si + hi);
							styles.put(Constants.PR_END_INDENT, newtextindent);
						} else
						{
							paddingend = (CondLengthProperty) attributemap
									.get(Constants.PR_PADDING_END);
						}
						LengthRangeProperty width;
						if (styles
								.containsKey(Constants.PR_INLINE_PROGRESSION_DIMENSION))
						{
							width = (LengthRangeProperty) styles
									.get(Constants.PR_INLINE_PROGRESSION_DIMENSION);
						} else
						{
							width = (LengthRangeProperty) attributemap
									.get(Constants.PR_INLINE_PROGRESSION_DIMENSION);
						}
						styles.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
								IoXslUtil.getLengthRangeProperty(paddingstart,
										paddingend, width));
					}
					if (styles.containsKey(Constants.PR_PADDING_BEFORE)
							|| styles.containsKey(Constants.PR_PADDING_AFTER)
							|| styles
									.containsKey(Constants.PR_BLOCK_PROGRESSION_DIMENSION))
					{

						CondLengthProperty paddingbefore;
						if (styles.containsKey(Constants.PR_PADDING_BEFORE))
						{
							paddingbefore = (CondLengthProperty) styles
									.get(Constants.PR_PADDING_BEFORE);
						} else
						{
							paddingbefore = (CondLengthProperty) attributemap
									.get(Constants.PR_PADDING_BEFORE);
						}
						CondLengthProperty paddingafter;
						if (styles.containsKey(Constants.PR_PADDING_AFTER))
						{
							paddingafter = (CondLengthProperty) styles
									.get(Constants.PR_PADDING_AFTER);
						} else
						{
							paddingafter = (CondLengthProperty) attributemap
									.get(Constants.PR_PADDING_AFTER);
						}
						LengthRangeProperty height;
						if (styles
								.containsKey(Constants.PR_BLOCK_PROGRESSION_DIMENSION))
						{
							height = (LengthRangeProperty) styles
									.get(Constants.PR_BLOCK_PROGRESSION_DIMENSION);
						} else
						{
							height = (LengthRangeProperty) attributemap
									.get(Constants.PR_BLOCK_PROGRESSION_DIMENSION);
						}
						styles.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
								IoXslUtil.getLengthRangeProperty(paddingbefore,
										paddingafter, height));
					}
				}

				int stylessize = styles.size();
				Object[] keysstyles = styles.keySet().toArray();
				for (int i = 0; i < stylessize; i++)
				{
					int key = (Integer) keysstyles[i];
					if (key == Constants.PR_START_INDENT)
					{
						LengthProperty startindent = (LengthProperty) styles
								.get(key);
						LengthProperty hanging = null;
						if (styles.containsKey(Constants.PR_HANGING_INDENT))
						{
							hanging = (LengthProperty) styles
									.get(Constants.PR_HANGING_INDENT);
						} else if (attributemap
								.containsKey(Constants.PR_HANGING_INDENT))
						{
							hanging = (LengthProperty) attributemap
									.get(Constants.PR_HANGING_INDENT);
						}
						FixedLength afterstartindent = SVGUtil.getSumLength(
								startindent, hanging);
						if (dongtaiMap.containsKey(key))
						{
							List<ConditionAndValue> currentlist = (List<ConditionAndValue>) dongtaiMap
									.get(key);
							ConditionAndValue currentcandv = new ConditionAndValue(
									current.getCondition(), afterstartindent);
							currentlist.add(currentcandv);
							dongtaiMap.remove(key);
							dongtaiMap.put(key, currentlist);
						} else
						{
							List<ConditionAndValue> currentlist = new ArrayList<ConditionAndValue>();
							ConditionAndValue currentcandv = new ConditionAndValue(
									current.getCondition(), afterstartindent);
							currentlist.add(currentcandv);
							dongtaiMap.put(key, currentlist);
						}
					} else if (key == Constants.PR_TEXT_INDENT)
					{
						LengthProperty textindent = (LengthProperty) styles
								.get(key);
						LengthProperty hanging = null;
						if (styles.containsKey(Constants.PR_HANGING_INDENT))
						{
							hanging = (LengthProperty) styles
									.get(Constants.PR_HANGING_INDENT);
						} else if (attributemap
								.containsKey(Constants.PR_HANGING_INDENT))
						{
							hanging = (LengthProperty) attributemap
									.get(Constants.PR_HANGING_INDENT);
						}
						FixedLength aftertextindent = SVGUtil.getSubLength(
								textindent, hanging);
						if (dongtaiMap.containsKey(key))
						{
							List<ConditionAndValue> currentlist = (List<ConditionAndValue>) dongtaiMap
									.get(key);
							ConditionAndValue currentcandv = new ConditionAndValue(
									current.getCondition(), aftertextindent);
							currentlist.add(currentcandv);
							dongtaiMap.put(key, currentlist);
						} else
						{
							List<ConditionAndValue> currentlist = new ArrayList<ConditionAndValue>();
							ConditionAndValue currentcandv = new ConditionAndValue(
									current.getCondition(), aftertextindent);
							currentlist.add(currentcandv);
							dongtaiMap.put(key, currentlist);
						}
					} else if (key == Constants.PR_NUMBERTEXT_TYPE
							|| key == Constants.PR_DATETIMEFORMAT
							|| key == Constants.PR_NUMBERFORMAT
							|| key == Constants.PR_POSITION_NUMBER_TYPE
							|| key == Constants.PR_CONDTION
							|| key == Constants.PR_GROUP
							|| key == Constants.PR_HANGING_INDENT
							|| key == Constants.PR_EDITMODE
							|| key == Constants.PR_SRC)
					{
					} else
					{
						if (dongtaiMap.containsKey(key))
						{
							List<ConditionAndValue> currentlist = (List<ConditionAndValue>) dongtaiMap
									.get(key);
							ConditionAndValue currentcandv = new ConditionAndValue(
									current.getCondition(), styles.get(key));
							currentlist.add(currentcandv);
							dongtaiMap.put(key, currentlist);
						} else
						{
							List<ConditionAndValue> currentlist = new ArrayList<ConditionAndValue>();
							ConditionAndValue currentcandv = new ConditionAndValue(
									current.getCondition(), styles.get(key));
							currentlist.add(currentcandv);
							dongtaiMap.put(key, currentlist);
						}
					}
				}
			}
			Object[] keysdongtaiMap = dongtaiMap.keySet().toArray();
			int dongtaiMapsize = dongtaiMap.size();
			for (int i = 0; i < dongtaiMapsize; i++)
			{
				int key = (Integer) keysdongtaiMap[i];
				List<ConditionAndValue> current = dongtaiMap.get(key);
				if (current != null && current.size() > 0)
				{
					code.append(ElementWriter.TAGBEGIN
							+ FoXsltConstants.VARIABLE + " ");
					code.append(ElementUtil.outputAttributes(
							FoXsltConstants.NAME, "n" + index + "a" + key));
					code.append(ElementWriter.TAGEND);
					code.append(ElementUtil
							.startElement(FoXsltConstants.CHOOSE));

					for (int j = 0; j < current.size(); j++)
					{
						ConditionAndValue currentitem = current.get(j);
						LogicalExpression condition = currentitem
								.getCondition();
						Object value = currentitem.getValue();
						if (condition != null)
						{
							code.append(ElementWriter.TAGBEGIN
									+ FoXsltConstants.WHEN + " ");
							code.append(ElementUtil.outputAttributes(
									FoXsltConstants.TEST, ElementUtil
											.returnStringIf(condition, true)));
							code.append(ElementWriter.TAGEND);
							String keyandvalue = getAttribute(key, value);
							if (keyandvalue.equals(""))
							{
								code.append(ElementUtil.ElementValueOf("'"
										+ keyandvalue + "'"));
							} else
							{
								String valueStr = "";
								String[] arr = keyandvalue.split("=");
								valueStr = arr[1].replaceAll("\"", "");
								if (key == Constants.PR_BACKGROUND_IMAGE
										&& value != null
										&& value instanceof BindNode)
								{
									code.append(ElementUtil
											.ElementValueOf(valueStr));
								} else
								{
									code.append(ElementUtil.ElementValueOf("'"
											+ valueStr + "'"));
								}
							}
							code.append(ElementUtil
									.endElement(FoXsltConstants.WHEN));
						}
					}
					code.append(ElementUtil
							.startElement(FoXsltConstants.OTHERWISE));
					String keyandvalue = getAttribute(key, attributemap
							.get(key));
					if (keyandvalue.equals(""))
					{
						code.append(ElementUtil.ElementValueOf("'"
								+ keyandvalue + "'"));
					} else
					{
						String valueStr = "";
						String[] arr = keyandvalue.split("=");
						valueStr = arr[1].replaceAll("\"", "");
						code.append(ElementUtil.ElementValueOf("'" + valueStr
								+ "'"));
					}
					code.append(ElementUtil
							.endElement(FoXsltConstants.OTHERWISE));
					code.append(ElementUtil.endElement(FoXsltConstants.CHOOSE));
					code.append(ElementUtil
							.endElement(FoXsltConstants.VARIABLE));
				}
			}
		}
		if ((foElement instanceof TableCell)
				&& attributemap.containsKey(Constants.PR_NUMBER_ROWS_SPANNED))
		{
			int rownum = (Integer) attributemap
					.get(Constants.PR_NUMBER_ROWS_SPANNED);
			if (rownum > 1 && attributemap.containsKey(Constants.PR_GROUP))
			{
				String idvalue = "concat('" + foElement.hashCode()
						+ "',position())";
				code.append(ElementUtil.elementVaribleSimple(
						FoXsltConstants.ID, idvalue));
			}
		}
		return code.toString();
	}

	@SuppressWarnings("unchecked")
	private Map<Integer, Object> getEditMap()
 {
		Map<Integer, Object> editmap = new HashMap<Integer, Object>();

		EnumProperty type = (EnumProperty) attributemap
				.get(Constants.PR_EDITTYPE);

		int value = type.getEnum();
		boolean containkey = true;
		if (value != Constants.EN_CHECKBOX) {
			attributemap.remove(Constants.PR_EDITTYPE);
			containkey=false;
		}

		if (attributemap.containsKey(Constants.PR_AUTHORITY)) {
			Object auth = attributemap.get(Constants.PR_AUTHORITY);
			if (auth != null) {
				editauthority = auth.toString();
			}
			attributemap.remove(Constants.PR_AUTHORITY);
		}
		Object defaultv = attributemap.get(Constants.PR_DEFAULT_VALUE);
		if(defaultv!=null)
		{
			defaultvalue=defaultv.toString();
			attributemap.remove(Constants.PR_DEFAULT_VALUE);
		}
		if (attributemap.containsKey(Constants.PR_EDIT_BUTTON)) {
			List<ButtonGroup> buttongroup = (List<ButtonGroup>) attributemap
					.get(Constants.PR_EDIT_BUTTON);
			dealButtonGroup(buttongroup);
			if (buttongroup != null && buttongroup.size() > 0) {
				for (int i = 0; i < buttongroup.size(); i++) {
					ButtonGroup current = buttongroup.get(i);
					String currentcode = current.toStringNoId();
					if (!"".equals(currentcode) && i > 0) {
						buttons = buttons + ",',',";
					}
					buttons = buttons + currentcode;
				}
			}
			attributemap.remove(Constants.PR_EDIT_BUTTON);
		}
		int[] keys = { Constants.PR_INPUT_TYPE, Constants.PR_INPUT_MULTILINE,
				Constants.PR_INPUT_WRAP, Constants.PR_INPUT_FILTER,
				Constants.PR_DATE_TYPE, Constants.PR_DATE_FORMAT,
				Constants.PR_RADIO_CHECK_VALUE,
				Constants.PR_RADIO_CHECK_CHECKED, Constants.PR_SELECT_MULTIPLE,
				Constants.PR_SELECT_LINES, Constants.PR_SELECT_ISEDIT,
				Constants.PR_SELECT_NEXT, Constants.PR_SELECT_SHOWLIST,
				Constants.PR_ISRELOAD, Constants.PR_APPEARANCE,
				Constants.PR_EDIT_WIDTH, Constants.PR_EDIT_HEIGHT,
				Constants.PR_HINT, Constants.PR_ONBLUR,
				Constants.PR_ONSELECTED, Constants.PR_ONKEYPRESS,
				Constants.PR_ONKEYDOWN, Constants.PR_ONKEYUP,
				Constants.PR_ONCHANGE, Constants.PR_ONCLICK,
				Constants.PR_ONEDIT, Constants.PR_ONRESULT,
				Constants.PR_BUTTON_TYPE, Constants.PR_BUTTON_NODATAXPTH,Constants.PR_BUTTON_INSERT_POSITION,
				Constants.PR_CONN_WITH, Constants.PR_CHECKBOX_UNSELECT_VALUE,
				Constants.PR_CHECKBOX_BOXSTYLE, Constants.PR_CHECKBOX_TICKMARK,
				Constants.PR_SELECT_TYPE, Constants.PR_SELECT_INFO,Constants.PR_POPUPBROWSER_INFO,
				Constants.PR_GROUP_REFERANCE, Constants.PR_INPUT_FILTERMSG,
				Constants.PR_SELECT_NAME,Constants.PR_DATA_SOURCE};

		for (int i = 0; i < keys.length; i++) {
			int key = keys[i];
			if (attributemap.containsKey(key)) {
				if (key == Constants.PR_ONEDIT || key == Constants.PR_ONRESULT
						|| key == Constants.PR_ONBLUR) {
					Validation validation = (Validation) attributemap.get(key);
					String name = IoXslUtil.addValidation(validation);
					editmap.put(key, name);
				} else {
					editmap.put(key, attributemap.get(key));
				}
				if (!containkey) {
					attributemap.remove(key);
				}
			}
		}
		return editmap;
	}

	public void dealButtonGroup(List<ButtonGroup> buttongroup)
	{
		if (buttongroup != null && !buttongroup.isEmpty())
		{
			for (ButtonGroup current : buttongroup)
			{

				if (current.getCellment().getAttribute(Constants.PR_GROUP) == null)
				{
					buttongroup.remove(current);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getEndElement()
	 */
	public String getEndElement()
	{
		StringBuffer code = new StringBuffer();
		code.append(ElementWriter.TAGENDSTART);
		code.append(getElementName());
		code.append(ElementWriter.TAGEND);
		return code.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getHeaderElement()
	 */
	public String getHeaderElement()
	{
		StringBuffer code = new StringBuffer();
		if (attributemap.containsKey(Constants.PR_ID))
		{
			Element parent = foElement.getParent();
			int index = parent != null ? parent.getIndex(foElement) : 0;
			String id = getId(index, attributemap.get(Constants.PR_ID)
					.toString());
			code.append(id);
		} else if (attributemap.containsKey(Constants.PR_REF_ID))
		{
			int index = foElement.getParent().getIndex(foElement);
			code.append(getId(index, attributemap.get(Constants.PR_REF_ID)
					.toString()));
		}
		if (attributemap.containsKey(Constants.PR_INTERNAL_DESTINATION))
		{
			int index = foElement.getParent().getIndex(foElement);
			code.append(getId(index, attributemap.get(
					Constants.PR_INTERNAL_DESTINATION).toString()));
		}
		code.append(getHeaderStart());
		code.append(getAttributes());
		code.append(getHeaderEnd());
		return code.toString();
	}

	public String getId(int index, String id)
	{
		StringBuffer code = new StringBuffer();
		code.append(ElementWriter.TAGBEGIN);
		code.append(FoXsltConstants.VARIABLE);
		code.append(ElementUtil.outputAttributes(FoXsltConstants.NAME, "i"
				+ index));
		code.append(ElementWriter.TAGEND);
		code.append(IoXslUtil.getCurrentId(id));
		code.append(ElementWriter.TAGENDSTART);
		code.append(FoXsltConstants.VARIABLE);
		code.append(ElementWriter.TAGEND);
		return code.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getHeaderEnd()
	 */
	public String getHeaderEnd()
	{
		return ElementWriter.TAGEND;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getHeaderStart()
	 */
	public String getHeaderStart()
	{
		StringBuffer code = new StringBuffer();
		code.append(ElementWriter.TAGBEGIN);
		code.append(getElementName());
		return code.toString();
	}

	/**
	 * 
	 * 输出实际内容之前的条件和组属性的处理
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String beforeDeal()
	{
		StringBuffer output = new StringBuffer();
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_GROUP))
		{
			Group group = (Group) attributemap.get(Constants.PR_GROUP);
			output.append(ElementUtil.startGroup(group));
		}
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_CONDTION))
		{
			LogicalExpression condition = (LogicalExpression) attributemap
					.get(Constants.PR_CONDTION);
			output.append(ElementUtil.startElementIf(condition));
		}
		int index = foElement.getParent().getIndex(foElement);
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_BACKGROUND_IMAGE))
		{
			Object backgroundimage = attributemap
					.get(Constants.PR_BACKGROUND_IMAGE);

			output.append(ElementWriter.TAGBEGIN);
			output.append(FoXsltConstants.VARIABLE);
			output.append(ElementUtil.outputAttributes("name", "bgi" + index));
			String bginame = "";
			if (backgroundimage != null)
			{
				if (backgroundimage instanceof String)
				{
					String bgig = (String) backgroundimage;
					bginame = "'" + IoXslUtil.getXmlText(bgig) + "'";
				} else if (backgroundimage instanceof BindNode)
				{
					BindNode node = (BindNode) backgroundimage;
					bginame = IoXslUtil.compareCurrentPath(IoXslUtil
							.getXSLXpath(node));
				}
			}
			output.append(ElementUtil.outputAttributes("select", bginame));
			output.append(ElementWriter.NOCHILDTAGEND);

		}
		return output.toString();
	}

	@SuppressWarnings("unchecked")
	public String getAllContent(CellElement element)
	{
		StringBuffer output = new StringBuffer();
		if (element != null && element.children() != null)
		{
			Enumeration<CellElement> listobj = element.children();
			while (listobj.hasMoreElements())
			{
				CellElement obj = listobj.nextElement();

				Map<Integer, Object> elementmap = obj.getAttributes()
						.getAttributes();
				Group group = null;
				if (elementmap != null
						&& elementmap.containsKey(Constants.PR_GROUP))
				{
					group = (Group) elementmap.get(Constants.PR_GROUP);
					output.append(ElementUtil.startSimpleGroup(group));
				}
				LogicalExpression condition;
				if (elementmap != null
						&& elementmap.containsKey(Constants.PR_CONDTION))
				{
					condition = (LogicalExpression) elementmap
							.get(Constants.PR_CONDTION);
					output.append(ElementUtil.startElementIf(condition));
				}
				if (obj instanceof Block)
				{
					output.append(getAllContent(obj));
					output.append("\n");

				} else if (obj instanceof Inline)
				{
					if (obj instanceof TextInline)
					{
						TextInline textinline = (TextInline) obj;
						Text text = textinline.getContent();
						if (text.isBindContent())
						{
							BindNode binnode = text.getBindNode();
							output.append(ElementUtil.ElementValueOf(IoXslUtil
									.compareCurrentPath(IoXslUtil
											.getXSLXpath(binnode))));
						} else
						{
							output.append(text.getText());
						}
					}
				} else
				{
					output.append(getAllContent(obj));
				}

				if (elementmap != null
						&& elementmap.containsKey(Constants.PR_CONDTION))
				{
					output.append(ElementUtil.endElement(FoXsltConstants.IF));
				}
				if (elementmap != null
						&& elementmap.containsKey(Constants.PR_GROUP))
				{
					output.append(ElementUtil.endGroup(group));
				}
			}
		}
		return output.toString();
	}

	/**
	 * 
	 * 输出实际内容之前的条件和组属性的处理的尾部代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String endDeal()
	{
		StringBuffer output = new StringBuffer();
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_CONDTION))
		{
			output.append(ElementUtil.endElement(FoXsltConstants.IF));
		}
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_GROUP))
		{
			Group group = (Group) attributemap.get(Constants.PR_GROUP);
			output.append(ElementUtil.endGroup(group));
		}
		return output.toString();
	}

	/**
	 * 
	 * 输出组属性处理的尾部
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String endGroup()
	{
		return ElementUtil.endElement(FoXsltConstants.FOR_EACH);
	}

	/**
	 * 
	 * 输出条件属性的尾部
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String enddealIf()
	{
		return ElementUtil.endElement(FoXsltConstants.IF);
	}

	public Map<Integer, Object> getAttributemap()
	{
		return attributemap;
	}

	public void setAttributemap()
	{
		Map<Integer, Object> map = null;
		Attributes attributes = foElement.getAttributes();
		if (attributes != null)
		{
			map = attributes.getAttributes();
			// 处理悬挂缩进，因为fo中没有悬挂缩进，所以要处理成段首缩进，值为已有的段首缩进值减去悬挂缩进值。
			if (map.containsKey(Constants.PR_HANGING_INDENT))
			{
				FixedLength hangingindent = (FixedLength) map
						.get(Constants.PR_HANGING_INDENT);
				FixedLength textindent = null;
				if (map.containsKey(Constants.PR_TEXT_INDENT))
				{
					textindent = (FixedLength) map
							.get(Constants.PR_TEXT_INDENT);
				}
				int hi = hangingindent.getValue();
				int si = textindent == null ? 0 : textindent.getValue();
				FixedLength newtextindent = new FixedLength(si - hi);
				map.remove(Constants.PR_HANGING_INDENT);
				map.put(Constants.PR_TEXT_INDENT, newtextindent);
			}
			// 处理左边距，因为fo在处理左边距时向外扩展，因此需要相应的缩进，值为已有的左缩进值加上左边距值。
			if (foElement instanceof BlockContainer)
			{
				BlockContainer bc = (BlockContainer) foElement;
				if (map.containsKey(Constants.PR_PADDING_START))
				{
					CondLengthProperty paddingstart = (CondLengthProperty) map
							.get(Constants.PR_PADDING_START);
					FixedLength psfl = (FixedLength) paddingstart.getLength();
					FixedLength startindent = null;
					if (map.containsKey(Constants.PR_START_INDENT))
					{
						startindent = (FixedLength) map
								.get(Constants.PR_START_INDENT);
					}
					int hi = psfl.getValue();
					int si = startindent == null ? 0 : startindent.getValue();
					FixedLength newtextindent = new FixedLength(si + hi);
					map.put(Constants.PR_START_INDENT, newtextindent);
				}
				if (map.containsKey(Constants.PR_PADDING_END))
				{
					CondLengthProperty paddingstart = (CondLengthProperty) map
							.get(Constants.PR_PADDING_END);
					FixedLength psfl = (FixedLength) paddingstart.getLength();
					FixedLength startindent = null;
					if (map.containsKey(Constants.PR_END_INDENT))
					{
						startindent = (FixedLength) map
								.get(Constants.PR_END_INDENT);
					}
					int hi = psfl.getValue();
					int si = startindent == null ? 0 : startindent.getValue();
					FixedLength newtextindent = new FixedLength(si + hi);
					map.put(Constants.PR_END_INDENT, newtextindent);
				}
				map.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, bc
						.getInlineProgressionDimension());
				map.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION, bc
						.getBlockProgressionDimension());
			}
			else if(foElement instanceof Inline)
			{
				CellElement grafather=(CellElement) foElement.getParent().getParent();
				if(grafather instanceof BlockContainer)
				{
					//自动字体时，由于在BlockContainer上已经设置了字体大小，故此处删除
					EnumProperty autosize=(EnumProperty) grafather.getAttribute(Constants.PR_OVERFLOW);
					if(autosize!=null&&autosize.getEnum()==Constants.EN_AUTOFONTSIZE)
					{
						map.remove(Constants.PR_FONT_SIZE);
					}
				}
				if(foElement instanceof ImageInline)
				{
					ImageInline inlne = (ImageInline) foElement;
					Enumeration<CellElement> child = inlne.children();
					while (child.hasMoreElements()) {
						CellElement nextElement = child.nextElement();
						Attributes attributes2 = nextElement.getAttributes();
						Map<Integer, Object> map2 = attributes2.getAttributes();
						Set<Integer> keySet = map2.keySet();
						for (Integer integer : keySet) {
							map.put(integer, map2.get(integer));
						}
					}
				}
			}
			
		}
		Map<Integer, Object> mapnew = new HashMap<Integer, Object>(map);
		if (map != null && map.containsKey(Constants.PR_BLOCK_STYLE))
		{
			ParagraphStyles blockstyle = (ParagraphStyles) map
					.get(Constants.PR_BLOCK_STYLE);
			Map<Integer, Object> blockstylemap = blockstyle.getStyleProperty();
			Object[] keys = blockstylemap.keySet().toArray();
			for (int i = 0; i < keys.length; i++)
			{
				int key = (Integer) keys[i];
				if (!mapnew.containsKey(key))
				{
					mapnew.put(key, blockstylemap.get(key));
				}
			}
			mapnew.remove(Constants.PR_BLOCK_STYLE);
		}
		attributemap = ElementUtil.getCompleteAttributes(mapnew, foElement
				.getClass());
		attributemap = ElementUtil.clearMap(attributemap);
	}

	public String getFormat(WisedocDateTimeFormat format)
	{
		StringBuffer style = new StringBuffer();
		DateInfo datein = format != null ? format.getDatein() : null;
		TimeInfo timein = format != null ? format.getTimein() : null;
		String spin = format != null && format.getInputseperate() != null ? format
				.getInputseperate()
				: "";
		if (!"".equals(spin))
		{
			spin = "'" + spin + "'";
		}
		StringBuffer dateformat = new StringBuffer();
		if (datein != null)
		{
			List<DateTimeItem> dtidatein = datein.getDateitems();
			for (DateTimeItem item : dtidatein)
			{
				if (item == null)
				{
					continue;
				}
				DateTimeType type = item.getType();
				switch (type)
				{
					case Year:
						if (item.getDigit() == DigitType.TF.ordinal())// item.
						{
							dateformat.append("yy");
						} else
						{
							dateformat.append("yyyy");
						}
						break;
					case Month:
						dateformat.append(getAppendInfo(item.getDigit(), "M"));
						break;
					case Day:
						dateformat.append(getAppendInfo(item.getDigit(), "d"));
						break;
				}
				String separator = item.getSeparator();
				if (separator != null)
				{
					dateformat.append(separator);
				}
			}
		}
		StringBuffer timeformat = new StringBuffer();
		if (timein != null)
		{
			List<DateTimeItem> dtidatein = timein.getDateitems();
			for (DateTimeItem item : dtidatein)
			{
				if (item == null)
				{
					continue;
				}
				DateTimeType type = item.getType();
				switch (type)
				{
					case Hour:
						timeformat.append("hh");
						break;
					case Minute:
						timeformat.append("mm");
						break;
					case Second:
						timeformat.append("ss");
						break;
				}
				String separator = item.getSeparator();
				if (separator != null)
				{
					timeformat.append(separator);
				}
			}
		}
		boolean firstin = format != null ? format.isIndatefirst() : true;
		if (firstin)
		{
			style.append(dateformat + spin + timeformat);
		} else
		{
			style.append(timeformat + spin + dateformat);
		}
		return style.toString();
	}

	private String getAppendInfo(int count, String repeat)
	{
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < count; i++)
		{
			result.append(repeat);
		}
		return result.toString();
	}
}
