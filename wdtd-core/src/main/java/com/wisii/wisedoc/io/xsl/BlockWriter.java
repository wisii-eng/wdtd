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
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.ChartInline;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.EncryptTextInline;
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
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class BlockWriter extends AbsElementWriter
{

	public BlockWriter(CellElement element)
	{
		super(element);
	}

	public String getElementName()
	{
		return FoXsltConstants.BLOCK;
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
					} else if (obj instanceof Block)
					{
						output.append(outputListCondition(inlinelist));
						inlinelist.clear();
						output.append(new BlockWriter(obj).write(obj));
					} else if (obj instanceof ZiMoban)
					{
						output.append(outputListCondition(inlinelist));
						inlinelist.clear();
						output.append(ewf.getWriter(obj).write(obj));
					}

					else if (obj instanceof Inline)
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
					output.append(new QianZhangInlineWriter(obj, n).write(obj));

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
				else if (obj instanceof EncryptTextInline)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new EncryptTextInlineWriter(obj).write(obj));
				}

				else if (obj instanceof ExternalGraphic)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new ExternalGraphicWriter(obj).write(obj));
				}
				else if(obj instanceof QianZhang)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new QianZhangWriter(obj).write(obj));
				}
				else if (obj instanceof ChartInline)
				{
					output.append(outInlineList(inlinelist));
					inlinelist.clear();
					output.append(new ChartInlineWriter(obj).write(obj));
				} else if (obj instanceof CheckBoxInline)
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
					output.append(ewf.getWriter(obj).write(obj));
				}
			}
			output.append(outInlineList(inlinelist));
		}
		return output.toString();
	}

	/**
	 * 
	 * TextInline的代码输出，属性相同，并且内容为静态字符的TextInline合并。
	 * 
	 * @param attrmap
	 *            属性map
	 * @param path
	 *            数据节点
	 * @param editable
	 *            编辑方式
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */

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
						output.append(outNoBandingInlineList(
								getBeforeFlg(nobinding), nobinding));
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
								output.append(outNoBandingInlineList(
										getBeforeFlg(nobinding), nobinding));
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
								output.append(outNoBandingInlineList(
										getBeforeFlg(nobinding), nobinding));
								nobinding.clear();
								attbefore = attcurrent;
								nobinding.add(item);
							}
						}
					}
				}
				output.append(outNoBandingInlineList(getBeforeFlg(nobinding),
						nobinding));
				nobinding.clear();
			}
		}
		return output.toString();
	}

	public int getBeforeFlg(List<TextInline> inlinelist)
	{
		int flg = 0;
		if (inlinelist != null && !inlinelist.isEmpty())
		{
			TextInline inline = inlinelist.get(0);
			Attributes Attributes = inline.getAttributes();
			Map<Integer, Object> attcurrent = Attributes != null ? Attributes
					.getAttributes() : null;
			if (attcurrent != null && !attcurrent.isEmpty()
					&& attcurrent.get(Constants.PR_LETTER_SPACING) != null)
			{
				String text = inline.getText();
				char chartext = text.charAt(0);
				boolean textflg = isChineseCharacters(chartext);
				if (textflg)
				{
					flg = 1;
				} else
				{
					flg = 2;
				}
			}
		}
		return flg;
	}

	public String outNoBandingInlineList(int beforeflg,
			List<TextInline> inlinelist)
	{
		StringBuffer output = new StringBuffer();
		if (inlinelist.size() > 0)
		{
			TextInline inline = inlinelist.get(0);
			Attributes Attributes = inline.getAttributes();
			Map<Integer, Object> attcurrent = Attributes != null ? Attributes
					.getAttributes() : null;
			boolean flg = true;

			if (attcurrent != null && !attcurrent.isEmpty()
					&& attcurrent.get(Constants.PR_LETTER_SPACING) != null)
			{
				Object letterspacing = attcurrent
						.get(Constants.PR_LETTER_SPACING);
				if (letterspacing instanceof SpaceProperty)
				{
					flg = false;
				}
			}
			InlineWriter writer = new InlineWriter(inline, inline.getParent()
					.getIndex(inline));
			output.append(writer.beforeDeal());

			if (flg)
			{
				InlineWriter writertwo = new InlineWriter(inline, inline
						.getParent().getIndex(inline));

				output.append(writertwo.getHeaderElement());
				String text = "";
				for (TextInline current : inlinelist)
				{
					text = text + current.getText();
				}
				String[] textarry = text.split("'");
				for (int i = 0; i < textarry.length; i++)
				{
					String current = textarry[i];

					current = IoXslUtil.getXmlText(current);

					String textnew = new String(current);
					textnew = textnew.trim();
					if (current.equals(textnew))
					{
						output.append(current);
					} else
					{
						output.append(ElementUtil.ElementValueOf("'" + current
								+ "'"));
					}
					if (i < textarry.length - 1)
					{
						output.append("'");
					}
				}
				output.append(ElementUtil.endElement(FoXsltConstants.INLINE));
			} else
			{
				output.append(dealSpaceStart(inlinelist, beforeflg));
			}
			output.append(writer.endDeal());
		}
		return output.toString();
	}

	public String dealSpaceStart(List<TextInline> inlinelist, int beforeflg)
	{
		StringBuffer output = new StringBuffer();
		if (inlinelist != null && !inlinelist.isEmpty())
		{
			boolean textflg = true;
			List<TextInline> currentlist = new ArrayList<TextInline>();
			boolean beforechange = false;
			int before = beforeflg;
			int after = 0;
			int size = inlinelist.size();
			for (int i = 0; i < size; i++)
			{
				TextInline currentinline = inlinelist.get(i);
				String text = currentinline.getText();
				char chartext = text.charAt(0);
				boolean currentflg = isChineseCharacters(chartext);

				if (currentlist.isEmpty())
				{
					textflg = currentflg;
					currentlist.add(currentinline);
				} else
				{
					if (textflg == currentflg)
					{
						currentlist.add(currentinline);
					} else
					{
						if (i < size - 1)
						{
							TextInline nextinline = inlinelist.get(i + 1);
							String nexttext = nextinline.getText();
							char nextchartext = nexttext.charAt(0);
							boolean nextflg = isChineseCharacters(nextchartext);
							if (nextflg)
							{
								after = 1;
							} else
							{
								after = 2;
							}
						} else
						{
							after = 0;
						}
						if (textflg)
						{
							output.append(dealChineseSpaceStart(currentlist,
									before, after));
							before = 1;
							beforechange = true;
						} else
						{
							if (beforechange)
							{
								output.append(dealEnglishSpaceStart(
										currentlist, 0));
							} else
							{
								output.append(dealEnglishSpaceStart(
										currentlist, beforeflg));
							}
							before = 2;
							beforechange = true;
						}
						currentlist.clear();
						currentlist.add(currentinline);
						textflg = currentflg;
					}
				}
			}
			after = 0;
			if (textflg)
			{
				output
						.append(dealChineseSpaceStart(currentlist, before,
								after));
			} else
			{
				if (beforechange)
				{
					output.append(dealEnglishSpaceStart(currentlist, 0));
				} else
				{
					output
							.append(dealEnglishSpaceStart(currentlist,
									beforeflg));
				}
			}
		}
		return output.toString();
	}

	/**
	 * 判定指定的字符是否为汉字【包括日文汉字，韩国的汉字】
	 * 
	 * @param ch
	 *            char 指定被判定的字符
	 * @return boolean 如果是汉字：True 否则 False
	 */
	public boolean isChineseCharacters(final char ch)
	{
		return (ch > 0x4E00) && (ch < 0x9FBF);
	}

	public String dealEnglishSpaceStart(List<TextInline> inlinelist, int before)
	{
		StringBuffer output = new StringBuffer();
		int size = inlinelist.size();
		if (size > 0)
		{
			TextInline inline = inlinelist.get(0);
			InlineWriter writer = new InlineWriter(inline, inline.getParent()
					.getIndex(inline));
			output.append(writer.beforeDeal());
			output.append(writer.getHeaderElement());
			String text = "";
			if (before > 0)
			{
				TextInline only = inlinelist.get(0);
				Map<Integer, Object> attcurrent = only.getAttributes()
						.getAttributes();
				Object lettetspace = attcurrent
						.get(Constants.PR_LETTER_SPACING);
				attcurrent.put(Constants.PR_SPACE_START, lettetspace);
				output.append(getOneText(only, attcurrent));
				if (size > 1)
				{
					for (int i = 1; i < size; i++)
					{
						TextInline current = inlinelist.get(i);
						text = text + current.getText();
					}
				}
			} else
			{
				for (TextInline current : inlinelist)
				{
					text = text + current.getText();
				}
			}
			if (!"".equals(text))
			{
				String[] textarry = text.split("'");
				for (int i = 0; i < textarry.length; i++)
				{
					String current = textarry[i];

					current = IoXslUtil.getXmlText(current);

					String textnew = new String(current);
					textnew = textnew.trim();
					if (current.equals(textnew))
					{
						output.append(current);
					} else
					{
						output.append(ElementUtil.ElementValueOf("'" + current
								+ "'"));
					}
					if (i < textarry.length - 1)
					{
						output.append("'");
					}
				}
			}
			output.append(ElementUtil.endElement(FoXsltConstants.INLINE));
			output.append(writer.endDeal());
		}
		return output.toString();
	}

	public String dealChineseSpaceStart(List<TextInline> inlinelist,
			int before, int after)
	{
		StringBuffer output = new StringBuffer();
		if (inlinelist != null && !inlinelist.isEmpty())
		{
			int size = inlinelist.size();
			TextInline inline = inlinelist.get(0);
			Map<Integer, Object> attcurrent = inline.getAttributes()
					.getAttributes();
			Object lettetspace = attcurrent.get(Constants.PR_LETTER_SPACING);
			attcurrent.remove(Constants.PR_LETTER_SPACING);

			if (size == 1)
			{
				TextInline firstinline = inlinelist.get(0);
				if (before == 2)
				{
					attcurrent.put(Constants.PR_SPACE_START, lettetspace);
				}
				if (after == 2)
				{
					attcurrent.put(Constants.PR_SPACE_END, lettetspace);
				}
				output.append(getOneText(firstinline, attcurrent));
			} else if (size == 2)
			{
				TextInline firstinline = inlinelist.get(0);
				if (before == 2)
				{
					attcurrent.put(Constants.PR_SPACE_START, lettetspace);
					attcurrent.put(Constants.PR_SPACE_END, lettetspace);
					output.append(getOneText(firstinline, attcurrent));
					attcurrent.remove(Constants.PR_SPACE_START);
					attcurrent.remove(Constants.PR_SPACE_END);
				} else
				{
					attcurrent.put(Constants.PR_SPACE_END, lettetspace);
					output.append(getOneText(firstinline, attcurrent));
					attcurrent.remove(Constants.PR_SPACE_END);
				}
				TextInline lastinline = inlinelist.get(1);
				if (after == 2)
				{
					attcurrent.put(Constants.PR_SPACE_END, lettetspace);
				}
				output.append(getOneText(lastinline, attcurrent));
			} else
			{
				TextInline firstinline = inlinelist.get(0);
				if (before == 2)
				{
					attcurrent.put(Constants.PR_SPACE_START, lettetspace);
					attcurrent.put(Constants.PR_SPACE_END, lettetspace);
					output.append(getOneText(firstinline, attcurrent));
					attcurrent.remove(Constants.PR_SPACE_START);
					attcurrent.remove(Constants.PR_SPACE_END);
				} else
				{
					attcurrent.put(Constants.PR_SPACE_END, lettetspace);
					output.append(getOneText(firstinline, attcurrent));
					attcurrent.remove(Constants.PR_SPACE_END);
				}
				attcurrent.put(Constants.PR_SPACE_END, lettetspace);
				for (int i = 1; i < size - 1; i++)
				{
					output.append(getOneText(inlinelist.get(i), attcurrent));
				}
				attcurrent.remove(Constants.PR_SPACE_END);
				TextInline lastinline = inlinelist.get(size - 1);
				if (after == 2)
				{
					attcurrent.put(Constants.PR_SPACE_END, lettetspace);
				}
				output.append(getOneText(lastinline, attcurrent));
			}
		}
		return output.toString();
	}

	public String getOneText(TextInline inline, Map<Integer, Object> map)
	{
		StringBuffer output = new StringBuffer();
		InlineWriter writer = new InlineWriter(inline, inline.getParent()
				.getIndex(inline), map);
		// output.append(writer.beforeDeal());
		output.append(writer.getHeaderElement());
		String text = inline.getText();
		String current = IoXslUtil.getXmlText(text);
		output.append(ElementUtil.ElementValueOf("'" + current + "'"));
		output.append(ElementUtil.endElement(FoXsltConstants.INLINE));
		// output.append(writer.endDeal());
		return output.toString();
	}
}
