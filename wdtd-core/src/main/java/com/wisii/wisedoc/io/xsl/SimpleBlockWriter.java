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

package com.wisii.wisedoc.io.xsl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.wisii.wisedoc.document.BasicLink;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Leader;
import com.wisii.wisedoc.document.PageNumberCitation;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.xsl.attribute.LengthPropertyWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class SimpleBlockWriter extends BlockWriter
{

	boolean indexitemflg = false;

	public SimpleBlockWriter(CellElement element)
	{
		super(element);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (foElement != null && foElement.children() != null)
		{
			Enumeration<CellElement> listobj = foElement.children();
			while (listobj.hasMoreElements())
			{
				CellElement obj = listobj.nextElement();
				if (obj instanceof Block)
				{
					output.append(new SimpleBlockWriter(obj).write(obj));
				} else if (obj instanceof Leader)
				{
					output.append(new LeaderWriter(obj).write(obj));
				} else if (obj instanceof BasicLink)
				{
					output.append(new LinkWriter(obj).write(obj));
				} else if (obj instanceof PageNumberCitation)
				{
					output.append(new SimplePageNumberCitationWriter(obj)
							.write(obj));
				} else if (obj instanceof com.wisii.wisedoc.document.Group)
				{
					output.append(new SimpleGroupWriter(obj).write(obj));
				}
			}
		}
		return output.toString();
	}

	@Override
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

		int indexthis = foElement.getParent().getIndex(foElement);
		if (!attributemap.containsKey(Constants.PR_DYNAMIC_STYLE))
		{
			for (int i = 0; i < size; i++)
			{
				int key = (Integer) keys[i];

				if (key == Constants.PR_FONT_FAMILY)
				{
					code.append(getAttribute(key, "'" + attributemap.get(key)
							+ "'"));
				} else if (key == Constants.PR_DATETIMEFORMAT
						|| key == Constants.PR_NUMBERFORMAT
						|| key == Constants.PR_POSITION_NUMBER_TYPE
						|| key == Constants.PR_CONDTION
						|| key == Constants.PR_GROUP
						|| key == Constants.PR_HANGING_INDENT
						|| key == Constants.PR_EDITMODE
						|| key == Constants.PR_SRC
						|| key == Constants.PR_DYNAMIC_STYLE
						|| key == Constants.PR_BLOCK_LEVEL)
				{
				} else if (key == Constants.PR_START_INDENT)
				{
					LengthPropertyWriter writer = new LengthPropertyWriter();
					if (attributemap.containsKey(Constants.PR_HANGING_INDENT))
					{
						code
								.append(ElementUtil
										.outputAttributes(
												FoXsltConstants.START_INDENT,
												writer.write(attributemap
														.get(key))
														+ " + "
														+ writer
																.write(attributemap
																		.get(Constants.PR_HANGING_INDENT))));
					} else
					{
						code.append(ElementUtil.outputAttributes(
								FoXsltConstants.START_INDENT, writer
										.write(attributemap.get(key))));
					}
				} else if (key == Constants.PR_TEXT_INDENT)
				{
					LengthPropertyWriter writer = new LengthPropertyWriter();
					if (attributemap.containsKey(Constants.PR_HANGING_INDENT))
					{
						code
								.append(ElementUtil
										.outputAttributes(
												FoXsltConstants.TEXT_INDENT,
												writer.write(attributemap
														.get(key))
														+ " - "
														+ writer
																.write(attributemap
																		.get(Constants.PR_HANGING_INDENT))));
					} else
					{
						code.append(ElementUtil.outputAttributes(
								FoXsltConstants.TEXT_INDENT, writer
										.write(attributemap.get(key))));
					}

				} else if (key == Constants.PR_BORDER_BEFORE_COLOR
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
					code.append(getAttribute(key, "{$i"
							+ foElement.getParent().getIndex(foElement) + "}"));
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
						code.append(getAttribute(key, IoXslUtil.getId(indexthis
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
							|| key == Constants.PR_SRC)
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
		return code.toString();
	}

}
