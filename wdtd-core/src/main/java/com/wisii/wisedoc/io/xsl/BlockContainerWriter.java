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

import java.awt.Font;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.Fixedarea;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class BlockContainerWriter extends AbsElementWriter
{

	Fixedarea overflowtreat = null;
	private boolean hasautosize=false; 

	public BlockContainerWriter(CellElement element)
	{
		super(element);
	}

	@Override
	public String getElementName() {
		return FoXsltConstants.BLOCK_CONTAINER;
	}

	@Override
	public String beforeDeal() {
		StringBuffer output = new StringBuffer();
		if (attributemap != null)
		{
			if (attributemap.containsKey(Constants.PR_GROUP))
			{
				Group group = (Group) attributemap.get(Constants.PR_GROUP);
				output.append(ElementUtil.startGroup(group));
			}
			if (attributemap.containsKey(Constants.PR_CONDTION))
			{
				LogicalExpression condition = (LogicalExpression) attributemap
						.get(Constants.PR_CONDTION);
				output.append(ElementUtil.startElementIf(condition));
			}
			int index = foElement.hashCode();
			boolean hasoverflow=false;
			if (attributemap.containsKey(Constants.PR_CONTENT_TREAT))
			{
				Object overflow = attributemap.get(Constants.PR_CONTENT_TREAT);
				if (overflow instanceof Fixedarea)
				{
					hasoverflow=true;
					overflowtreat = (Fixedarea) overflow;
					int onelinemaxnumber = overflowtreat.getOnelinemaxnumber();

					NameSpace textlinesNameSpace = new NameSpace(
							"xmlns:textlines",
							"com.wisii.wisedoc.io.xsl.util.TextLines");
					IoXslUtil.addNameSpace(textlinesNameSpace);

					output.append(ElementWriter.TAGBEGIN);
					output.append(FoXsltConstants.VARIABLE);
					output.append(ElementUtil.outputAttributes("name",
							"container" + index));
					output.append(ElementWriter.TAGEND);
					String select = getAllContent(foElement);
					output.append(select);
					output.append(ElementWriter.TAGENDSTART);
					output.append(FoXsltConstants.VARIABLE);
					output.append(ElementWriter.TAGEND);
					output.append(ElementUtil.elementVaribleSimple("L" + index,
							"textlines:getLines($container" + index + ","
									+ onelinemaxnumber + ")"));
				}
			}
			if(attributemap.containsKey(Constants.PR_OVERFLOW))
			{
				EnumProperty autosize=(EnumProperty) attributemap.get(Constants.PR_OVERFLOW);
				CellElement inline=null;
				CellElement child = foElement.getChildAt(0);
				if(child!=null)
				{
					inline=child.getChildAt(0);
				}
				if(autosize!=null&&autosize.getEnum()==Constants.EN_AUTOFONTSIZE)
				{
					attributemap.remove(Constants.PR_OVERFLOW);
					if (inline != null)
					{
						hasautosize = true;
						NameSpace namespace = new NameSpace(
								"xmlns:wisiiextend",
								"com.wisii.wisedoc.io.xsl.util.WisiiXslUtil");
						IoXslUtil.addNameSpace(namespace);
						if (!hasoverflow)
						{
							output.append(ElementWriter.TAGBEGIN);
							output.append(FoXsltConstants.VARIABLE);
							output.append(ElementUtil.outputAttributes("name",
									"container" + index));
							output.append(ElementWriter.TAGEND);
							String select = getAllContent(foElement);
							output.append(select);
							output.append(ElementWriter.TAGENDSTART);
							output.append(FoXsltConstants.VARIABLE);
							output.append(ElementWriter.TAGEND);
						}
						LengthRangeProperty width = ((BlockContainer) foElement)
								.getInlineProgressionDimension();
						int mptwidth = width.getOptimum(null).getLength()
								.getValue();
						String fontname = (String) inline
								.getAttribute(Constants.PR_FONT_FAMILY);
						Length fontsize = (Length) inline
								.getAttribute(Constants.PR_FONT_SIZE);
						int fontStyle = (Integer) inline
								.getAttribute(Constants.PR_FONT_STYLE);
						int fontweight = (Integer) inline
								.getAttribute(Constants.PR_FONT_WEIGHT);
						int style = Font.PLAIN;
						if (fontweight == Constants.EN_700)
						{
							style = Font.BOLD;
						}
						if (fontStyle == Constants.EN_ITALIC)
						{
							style |= Font.ITALIC;
						}
						output.append(ElementUtil.elementVaribleSimple("FS"
								+ index, "wisiiextend:getSize($container"
								+ index + "," + fontname + "," + style + ","
								+ mptwidth + "," + fontsize.getValue() + ")"));
					}

				}
			}
			if (attributemap.containsKey(Constants.PR_BACKGROUND_IMAGE))
			{
				Object backgroundimage = attributemap
						.get(Constants.PR_BACKGROUND_IMAGE);

				output.append(ElementWriter.TAGBEGIN);
				output.append(FoXsltConstants.VARIABLE);
				int indexb = foElement.getParent().getIndex(foElement);
				output.append(ElementUtil.outputAttributes("name", "bgi"
						+ indexb));
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
		}
		return output.toString();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected String getChildCode() {
		StringBuffer output = new StringBuffer();
		if (foElement != null && foElement.children() != null)
		{
			Enumeration<CellElement> listobj = foElement.children();
			if (!listobj.hasMoreElements())
			{
				output.append(ElementWriter.TAGBEGIN);
				output.append(FoXsltConstants.BLOCK);
				output.append(ElementWriter.TAGEND);
				output.append(ElementWriter.TAGENDSTART);
				output.append(FoXsltConstants.BLOCK);
				output.append(ElementWriter.TAGEND);
			} else
			{

				boolean startflg = attributemap
						.containsKey(Constants.PR_START_INDENT)
						|| attributemap.containsKey(Constants.PR_PADDING_START);
				boolean endflg = attributemap
						.containsKey(Constants.PR_END_INDENT)
						|| attributemap.containsKey(Constants.PR_PADDING_END);
				while (listobj.hasMoreElements())
				{
					if (startflg || endflg)
					{
						output.append("<fo:block");
						if (startflg)
						{
							output.append(" start-indent=\"0pt\"");
						}
						if (endflg)
						{
							output.append(" end-indent=\"0pt\"");
						}
						output.append(">");
					}
					CellElement obj = listobj.nextElement();
					output.append(ewf.getWriter(obj).write(obj));
					if (startflg || endflg)
					{
						output.append("</fo:block>");
					}
				}
			}
		}
		StringBuffer result = new StringBuffer();
		if (overflowtreat != null)
		{
			int index = foElement.hashCode();
			int lines = overflowtreat.getLines();
			int caozuofu = overflowtreat.getCaozuofu();
			StringBuffer shuxing = new StringBuffer();
			if (foElement != null && foElement.children() != null)
			{
				List<CellElement> all = DocumentUtil.getElements(foElement,
						TextInline.class);
				if (all != null && !all.isEmpty())
				{
					CellElement objsun = all.get(0);
					if (objsun != null)
					{
						Map<Integer, Object> inlineAtts = objsun
								.getAttributes().getAttributes();

						inlineAtts.remove(Constants.PR_ENDOFALL);
						inlineAtts.remove(Constants.PR_ID);
						// 清除掉条件和组属性动态样式
						inlineAtts.remove(Constants.PR_CONDTION);
						inlineAtts.remove(Constants.PR_GROUP);
						inlineAtts.remove(Constants.PR_DYNAMIC_STYLE);
						inlineAtts.remove(Constants.PR_TRANSFORM_TABLE);
						if (inlineAtts.get(Constants.PR_EDITTYPE) != null)
						{
							for (int i = Constants.PR_EDITTYPE; i < Constants.PR_GROUP_NONE_SELECT_VALUE; i++)
							{
								inlineAtts.remove(i);
							}
						}
						Object[] keys = inlineAtts.keySet().toArray();
						for (int i = 0; i < keys.length; i++)
						{
							int key = (Integer) keys[i];
							if (key == Constants.PR_FONT_FAMILY)
							{
								shuxing.append(writerFactory.write(key, "'"
										+ objsun.getAttribute(key) + "'"));
							} else
							{
								shuxing.append(writerFactory.write(key,
										objsun.getAttribute(key)));
							}
						}
					}
				}
			}
			String nullas = overflowtreat.getNullas();
			String add = overflowtreat.getBeforeadd();
			int indexoutput = output.indexOf(">");
			String before = output.substring(0, indexoutput + 1);
			String after = output.substring(indexoutput + 1, output.length());
			if (caozuofu == Fixedarea.LESS)
			{
				result.append(ElementUtil.startElement(FoXsltConstants.CHOOSE));
				result.append(ElementUtil.startElementWI(FoXsltConstants.WHEN,
						"$L" + index + "&gt;=" + lines));

				if (nullas == null)
				{
					result.append("<fo:block/>");
				} else
				{
					result.append("<fo:block " + shuxing + ">"
							+ IoXslUtil.getXmlText(nullas) + "</fo:block>");
				}
				result.append(ElementUtil.endElement(FoXsltConstants.WHEN));
				result.append(ElementUtil
						.startElement(FoXsltConstants.OTHERWISE));
				if (add == null)
				{
					result.append(output);
				} else
				{
					result.append(before + "<fo:inline " + shuxing + ">"
							+ IoXslUtil.getXmlText(add) + "</fo:inline>"
							+ after);
				}
				result.append(ElementUtil.endElement(FoXsltConstants.OTHERWISE));
				result.append(ElementUtil.endElement(FoXsltConstants.CHOOSE));
			} else if (caozuofu == Fixedarea.THANANDEQUAL)
			{
				result.append(ElementUtil.startElement(FoXsltConstants.CHOOSE));
				result.append(ElementUtil.startElementWI(FoXsltConstants.WHEN,
						"$L" + index + "&lt;" + lines));
				if (nullas == null)
				{
					result.append("<fo:block/>");
				} else
				{
					result.append("<fo:block " + shuxing + ">"
							+ IoXslUtil.getXmlText(nullas) + "</fo:block>");
				}
				result.append(ElementUtil.endElement(FoXsltConstants.WHEN));
				result.append(ElementUtil
						.startElement(FoXsltConstants.OTHERWISE));

				if (add == null)
				{
					result.append(output);
				} else
				{
					result.append(before + "<fo:inline " + shuxing + ">"
							+ IoXslUtil.getXmlText(add) + "</fo:inline>"
							+ after);
				}
				result.append(ElementUtil.endElement(FoXsltConstants.OTHERWISE));
				result.append(ElementUtil.endElement(FoXsltConstants.CHOOSE));
			}
		} else
		{
			return output.toString();
		}
		return result.toString();
	}

	@Override
	public String write(CellElement element) {
		StringBuffer result = new StringBuffer();
		result.append(beforeDeal());
		String output = getCode();
		if (overflowtreat != null)
		{
			int index = foElement.hashCode();
			boolean view = overflowtreat.isIsview();
			int lines = overflowtreat.getLines();
			int caozuofu = overflowtreat.getCaozuofu();
			String nullas = overflowtreat.getNullas();
			if (caozuofu == Fixedarea.LESS)
			{
				if (view && (nullas == null || "".equals(nullas)))
				{
					result.append(ElementUtil
							.startElement(FoXsltConstants.CHOOSE));
					result.append(ElementUtil.startElementWI(
							FoXsltConstants.WHEN, "$L" + index + "&gt;="
									+ lines));
					result.append("<fo:block/>");
					result.append(ElementUtil.endElement(FoXsltConstants.WHEN));
					result.append(ElementUtil
							.startElement(FoXsltConstants.OTHERWISE));
					result.append(output);
					result.append(ElementUtil
							.endElement(FoXsltConstants.OTHERWISE));
					result.append(ElementUtil
							.endElement(FoXsltConstants.CHOOSE));
				} else
				{
					result.append(output);
				}
			} else if (caozuofu == Fixedarea.THANANDEQUAL)
			{
				if (view && (nullas == null || "".equals(nullas)))
				{
					result.append(ElementUtil
							.startElement(FoXsltConstants.CHOOSE));
					result.append(ElementUtil
							.startElementWI(FoXsltConstants.WHEN, "$L" + index
									+ "&lt;" + lines));
					result.append("<fo:block/>");
					result.append(ElementUtil.endElement(FoXsltConstants.WHEN));
					result.append(ElementUtil
							.startElement(FoXsltConstants.OTHERWISE));
					result.append(output);
					result.append(ElementUtil
							.endElement(FoXsltConstants.OTHERWISE));
					result.append(ElementUtil
							.endElement(FoXsltConstants.CHOOSE));
				} else
				{
					result.append(output);
				}
			}
		} else
		{
			result.append(output);
		}
		result.append(endDeal());
		return result.toString();
	}
	@Override
	public String getAttributes() {
		String atts=super.getAttributes();
		if(hasautosize)
		{
			int index = foElement.hashCode();
			atts+=ElementUtil.outputAttributes(FoXsltConstants.FONT_SIZE,"{$FS"+index+"}");
		}
		return atts;
	}
}
