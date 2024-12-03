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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.BarCodeInline;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.PageNumber;
import com.wisii.wisedoc.document.PageNumberCitation;
import com.wisii.wisedoc.document.PositionInline;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.QianZhangInline;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class LinkWriter extends AbsSimpleElementWriter
{

	public LinkWriter(CellElement element)
	{
		super(element);
	}

	@Override
	public String getElementName()
	{
		return FoXsltConstants.BASIC_LINK;
	}

	@SuppressWarnings("unchecked")
	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (foElement != null
				&& (foElement.children() != null || foElement.getChildNodes() != null))
		{
			Enumeration<CellElement> listobj = foElement.children();
			if (listobj.hasMoreElements())
			{
				List<CellElement> inlinelist = new ArrayList<CellElement>();
				while (listobj.hasMoreElements())
				{
					CellElement obj = listobj.nextElement();
					if (obj instanceof Canvas)
					{
						output.append(outputListCondition(inlinelist));
						inlinelist.clear();
						output.append(new CanvasWriter((Canvas) obj).write());
					} else if (obj instanceof Group)
					{
						output.append(outputListCondition(inlinelist));
						inlinelist.clear();
						output.append(new GroupWriter(obj).write(obj));
					} else
					{
						inlinelist.add((Inline) obj);
					}
				}
				output.append(outputListCondition(inlinelist));
			} else
			{
				output.append(ElementWriter.TAGBEGIN);
				output.append(FoXsltConstants.BLOCK_CONTAINER);
				if (attributemap.containsKey(Constants.PR_LINE_HEIGHT))
				{
					output.append(getAttribute(Constants.PR_HEIGHT,
							attributemap.get(Constants.PR_LINE_HEIGHT)));
				} else
				{
					output.append(getAttribute(Constants.PR_HEIGHT,
							new FixedLength(14.4d, "pt")));
				}
				output.append(ElementWriter.TAGEND);
				output.append(ElementWriter.TAGBEGIN);
				output.append(FoXsltConstants.BLOCK);
				output.append(ElementWriter.NOCHILDTAGEND);
				output.append(ElementWriter.TAGENDSTART);
				output.append(FoXsltConstants.BLOCK_CONTAINER);
				output.append(ElementWriter.TAGEND);

			}
		}
		return output.toString();
	}

	public String outputListCondition(List<CellElement> list)
	{
		StringBuffer output = new StringBuffer();
		List<TextInline> inlinelist = new ArrayList<TextInline>();
		int size = list.size();
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
			{
				CellElement obj = list.get(i);
				int n = obj.getParent().getIndex(obj);
				if (obj instanceof ImageInline)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new ImageInlineWriter(obj, n).write(obj));
				} else if (obj instanceof BarCodeInline)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new BarCodeInlineWriter(obj, n).write(obj));

				}
				else if (obj instanceof QianZhangInline)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
				}
				else if (obj instanceof DateTimeInline)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new DateTimeInlineWriter(obj, n).write(obj));
				} else if (obj instanceof NumberTextInline)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output
							.append(new NumberTextInlineWriter(obj, n)
									.write(obj));
				}
				else if (obj instanceof PositionInline)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output
							.append(new PositionInlineWriter(obj, n)
									.write(obj));
				}
				else if (obj instanceof ExternalGraphic)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new ExternalGraphicWriter(obj).write(obj));
				}
				else if (obj instanceof QianZhang)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
				}
				else if (obj instanceof CheckBoxInline)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new CheckBoxInlineWriter(obj, n).write(obj));
				} else if (obj instanceof TextInline)
				{
					inlinelist.add((TextInline) obj);
				} else if (obj instanceof PageNumber)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new PageNumberWriter(obj).write(obj));
				} else if (obj instanceof PageNumberCitation)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new PageNumberCitationWriter(obj).write(obj));
				} else if (obj instanceof Inline)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new SvgInlineWriter(obj).write(obj));
				} else
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new SelectElementWriterFactory().getWriter(
							obj).write(obj));
				}
			}
			output.append(outInlineList(inlinelist));
		}
		return output.toString();
	}

	public String outInlineList(List<TextInline> inlinelist)
	{
		StringBuffer output = new StringBuffer();
		if (inlinelist != null)
		{
			int size = inlinelist.size();
			if (size > 0)
			{
				List<TextInline> nobinding = new ArrayList<TextInline>();
				Map<Integer, Object> attbefore = null;
				for (int i = 0; i < size; i++)
				{
					TextInline item = inlinelist.get(i);
					InlineContent textin = item.getContent();
					if (textin.isBindContent())
					{
						output.append(outNoBandingInlineList(nobinding));
						nobinding.clear();
						output.append(new InlineWriter(item, item.getParent()
								.getIndex(item)).write(item));
					} else
					{
						Attributes Attributes = item.getAttributes();
						Map<Integer, Object> attcurrent = Attributes != null ? Attributes
								.getAttributes()
								: null;
						if (attbefore == null)
						{
							if (attcurrent == null)
							{
								nobinding.add(item);
							} else
							{
								output
										.append(outNoBandingInlineList(nobinding));
								nobinding.clear();
								attbefore = attcurrent;
								nobinding.add(item);
							}
						} else
						{
							if (attbefore.equals(attcurrent))
							{
								nobinding.add(item);
							} else
							{
								output
										.append(outNoBandingInlineList(nobinding));
								nobinding.clear();
								attbefore = attcurrent;
								nobinding.add(item);
							}
						}
					}
				}
				output.append(outNoBandingInlineList(nobinding));
				nobinding.clear();
			}
		}
		return output.toString();
	}

	public String outNoBandingInlineList(List<TextInline> inlinelist)
	{
		StringBuffer output = new StringBuffer();
		if (inlinelist.size() > 0)
		{
			TextInline inline = inlinelist.get(0);
			InlineWriter writer = new InlineWriter(inline, inline.getParent()
					.getIndex(inline));
			output.append(writer.getHeaderElement());
			String text = "";
			for (TextInline current : inlinelist)
			{
				text = text + current.getText();
			}
			output.append(IoXslUtil.getXmlText(text));
			output.append(ElementUtil.endElement(FoXsltConstants.INLINE));
			output.append(writer.endDeal());
		}
		return output.toString();
	}
}
