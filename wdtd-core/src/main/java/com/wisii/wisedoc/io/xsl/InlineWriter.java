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
 * @InlineWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.document.attribute.edit.ButtonGroup;
import com.wisii.wisedoc.document.attribute.transform.DataTransformTable;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-2
 */
public class InlineWriter extends AbsElementWriter
{

	protected Inline inline;

	protected int number;

	protected boolean flg = false;

	protected boolean outFov = false;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public InlineWriter(CellElement element, int n, Map<Integer, Object> map)
	{
		super(element);
		setAttributemap(map);
		inline = (Inline) element;
		number = n;
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public InlineWriter(CellElement element, int n)
	{
		super(element);
		inline = (Inline) element;
		number = n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.xsl.AbsElementWriter#write(com.wisii.wisedoc.document
	 * .CellElement)
	 */
	public String write(CellElement element)
	{
		StringBuffer output = new StringBuffer();
		Inline inlne = (Inline) foElement;
		InlineContent textin = inlne.getContent();
		if (textin == null)
		{
			output.append("<");
			output.append(FoXsltConstants.INLINE);
			output.append("/>");
		} else
		{
			output.append(beforeDeal());
			if (textin instanceof Text)
			{
				Text textinLine = (Text) textin;
				boolean flg = textinLine.isBindContent();
				if (flg)
				{
					output.append(getCode());
				} else
				{
					output.append(textNoBinding());
				}
			}
		}
		output.append(endDeal());
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getElementName()
	 */
	public String getElementName()
	{
		return FoXsltConstants.INLINE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getCode()
	 */
	public String getCode()
	{
		StringBuffer output = new StringBuffer();
		Inline inlne = (Inline) foElement;
		Text textinLine = (Text) inlne.getContent();
		String yuanshiPath = IoXslUtil.getXSLXpath(textinLine.getBindNode());
		if (IoXslUtil.isXmlEditable() == Constants.EN_UNEDITABLE)
		{
			setFlg(false);
			output.append(getCodebasic());
		} else if (IoXslUtil.isXmlEditable() == Constants.EN_EDITABLE)
		{

			setFlg(true);
			output.append(getBindVarable(yuanshiPath));
			output.append(getCodebasic());
		} else if (IoXslUtil.isXmlEditable() == Constants.EN_EDITVARIBLE)
		{
			boolean oldflg = isFlg();
			setFlg(true);
			String setVarible = getBindVarable(yuanshiPath);
			setFlg(oldflg);
			if (isOutFov())
			{
				output.append(ElementUtil.startElement(FoXsltConstants.CHOOSE));
				String test = "contains($XmlEditable,'yes') and contains('yes',$XmlEditable)";
				output.append(ElementUtil.startElementWI(FoXsltConstants.WHEN,
						test));
				setFlg(true);
				output.append(setVarible);
				output.append(getCodebasic());
				output.append(ElementUtil.endElement(FoXsltConstants.WHEN));
				output.append(ElementUtil
						.startElement(FoXsltConstants.OTHERWISE));
				setFlg(false);
				output.append(getCodebasic());
				output
						.append(ElementUtil
								.endElement(FoXsltConstants.OTHERWISE));
				output.append(ElementUtil.endElement(FoXsltConstants.CHOOSE));
			} else
			{
				setFlg(false);
				output.append(getCodebasic());
			}
		}
		return output.toString();
	}

	/**
	 * 
	 * 获得元素的代码部分，不包括对元素代码部分用到的变量的定义。
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getCodebasic()
	{
		StringBuffer code = new StringBuffer();
		code.append(getHeaderElement());
		code.append(getContent());
		code.append(getEndElement());
		return code.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getHeaderElement()
	 */
	@SuppressWarnings("unchecked")
	public String getHeaderElement()
	{
		StringBuffer code = new StringBuffer();
		Inline inlne = (Inline) foElement;
		Text textinLine = (Text) inlne.getContent();
		code.append(getAttributeVariable());

		boolean flg = textinLine!=null&&textinLine.isBindContent();
		if (flg)
		{
			String yuanshiPath = IoXslUtil
					.getXSLXpath(textinLine.getBindNode());
			String relativePath = IoXslUtil.compareCurrentPath(yuanshiPath);
			// 加[]里的东西
			if (attributemap.containsKey(Constants.PR_XPATH_POSITION)
					&& !".".equals(relativePath))
			{
				Group currentgroup = Group.Instanceof(textinLine.getBindNode(),
						null, null, null, null);
				IoXslUtil.addXpath(currentgroup);
				LogicalExpression xposition = (LogicalExpression) attributemap
						.get(Constants.PR_XPATH_POSITION);
				String xpathadd = ElementUtil.returnStringIf(xposition, true);

				if (xpathadd.startsWith("position()="))
				{
					int index = xpathadd.indexOf("=");
					xpathadd = xpathadd.substring(index + 1);
				}
				relativePath = relativePath + "[" + xpathadd + "]";
				IoXslUtil.deleteXpath();
			}
			code.append(ElementUtil.selectAssignment(FoXsltConstants.VARIABLE,
					FoXsltConstants.CONTENT + number, new StringBuffer(
							relativePath)));
		}
		// else
		// {
		if (attributemap.containsKey(Constants.PR_EDIT_BUTTON))
		{
			List<ButtonGroup> buttongroup = (List<ButtonGroup>) attributemap
					.get(Constants.PR_EDIT_BUTTON);
			dealButtonGroup(buttongroup);
			if (buttongroup != null && buttongroup.size() > 0)
			{
				for (int i = 0; i < buttongroup.size(); i++)
				{
					ButtonGroup current = buttongroup.get(i);
					if (current != null)
					{
						String currentcode = current.toStringNoId();
						if (!"".equals(currentcode) && i > 0
								&& !"".equals(buttons))
						{
							buttons = buttons + ",',',";
						}
						buttons = buttons + currentcode;
					}
				}
			}
			String hascode = foElement.hashCode() + "e";
			String add = "";
			if (IoXslUtil.getXpath() != null)
			{
				int number = IoXslUtil.getXpath().size() - 1;
				if (number > -1)
				{
					add = ",$n" + number;
				}
			}
			code.append(ElementUtil.selectAssignment(FoXsltConstants.VARIABLE,
					FoXsltConstants.XPATH + number, new StringBuffer(
							"concat('wdems-start:'" + "," + buttons
									+ ",':wdems-end','" + hascode + "'" + add
									+ ")")));
			// }

			// attributemap.remove(Constants.PR_EDIT_BUTTON);
		}
		// }

		code.append(getHeaderStart());
		code.append(getAttributes());
		if (attributemap.containsKey(Constants.PR_EDIT_BUTTON))
		{
			code.append(ElementUtil.outputAttributes(FoXsltConstants.ID, "{$"
					+ FoXsltConstants.XPATH + number + "}"));
		}
		if (flg)
		{
			code.append(getFovAttributes());
		}
		code.append(getHeaderEnd());
		return code.toString();
	}

	/**
	 * 
	 * 获得内容部分的代码，包括逻辑。
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */

	public String getContent()
	{
		StringBuffer output = new StringBuffer();

		DataTransformTable translateTable = attributemap
				.containsKey(Constants.PR_TRANSFORM_TABLE) ? (DataTransformTable) attributemap
				.get(Constants.PR_TRANSFORM_TABLE)
				: null;
		if (translateTable != null)
		{
			// String tablename = IoXslUtil.addDatasource(translateTable);
			// String tablename = translateTable.hashCode() + "";
			String tablename = translateTable.hashCode()
					+ IoXslUtil.getFilename();
			IoXslUtil.addDataTransformTable(translateTable);
			// String rootname = translateTable.getDate().getName();
			String rootname = "root";
			String select = "document('')/xsl:stylesheet/wdems:dataSource[@name='"
					+ tablename
					+ "']/wdems:data/"
					+ rootname
					+ "/item[@key=$"
					+ FoXsltConstants.CONTENT + number + "]/@value";
			output.append(ElementUtil.elementVaribleSimple("value", select));
			output.append(ElementUtil.startElement(FoXsltConstants.CHOOSE));
			output.append(ElementUtil.startElementWI(FoXsltConstants.WHEN,
					"$value!=''"));
			// int flg = translateTable.isDeleteBlank();
			output.append(ElementUtil.ElementValueOf("$value"));
			output.append(ElementUtil.endElement(FoXsltConstants.WHEN));
			output.append(ElementUtil.startElement(FoXsltConstants.OTHERWISE));
			int type = translateTable.getDatatreat();
			if (type == DataTransformTable.SETSAME)
			{
				output.append(ElementUtil.ElementValueOf("$"
						+ FoXsltConstants.CONTENT + number));
			} else if (type == DataTransformTable.SETNULL)
			{
				output.append(ElementUtil.ElementValueOf("''"));
			} else if (type == DataTransformTable.SETSTRING)
			{
				String text = translateTable.getText() != null ? translateTable
						.getText() : "";
				output.append(ElementUtil.ElementValueOf("'" + text + "'"));
			}
			output.append(ElementUtil.endElement(FoXsltConstants.OTHERWISE));
			output.append(ElementUtil.endElement(FoXsltConstants.CHOOSE));
			NameSpace wdwens = new NameSpace(FoXsltConstants.SPACENAMEWDWE,
					FoXsltConstants.WDWE);
			IoXslUtil.addNameSpace(wdwens);
		} else
		{
			output.append(getFormatContent());
		}

		return output.toString();
	}

	/**
	 * 
	 * 获得fov属性的赋值代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getFovAttributes()
	{
		String code = "";
		if (isOutFov())
		{
			code = code
					+ ElementUtil.outputAttributes(FoXsltConstants.ID, "{$"
							+ FoXsltConstants.XPATH + number + "}");
		}
		return code;
	}

	public String getBindVarable(String yuanshiPath)
	{
		StringBuffer output = new StringBuffer();
		output.append(getAttributeVariable());
		String hascode = foElement.hashCode() + "e";
		if (!edituiname.isEmpty())
		{

			String XPath = IoXslUtil.getAbsoluteXPath(yuanshiPath);

			String authority = editauthority.isEmpty()? "" : "',"
					+ editauthority+"',";
			if(!defaultvalue.isEmpty())
			{
				if(authority.isEmpty())
				{
					authority=authority+"',,"+defaultvalue+"',";
				}
				else{
				authority=authority+"',"+defaultvalue+"',";
				}
			}
			String add = "";
			if (IoXslUtil.getXpath() != null)
			{
				int number = IoXslUtil.getXpath().size() - 1;
				if (number > -1)
				{
					add = ",$n" + number;
				}
			}
			if ("".equals(buttons))
			{
				output.append(ElementUtil.selectAssignment(
						FoXsltConstants.VARIABLE, FoXsltConstants.XPATH
								+ number, new StringBuffer(
								"concat('wdems-start:" + edituiname + "(',"
										+ XPath + "," + authority
										+ "'):wdems-end','" + hascode + "'"
										+ add + ")")));
			} else
			{
				output.append(ElementUtil.selectAssignment(
						FoXsltConstants.VARIABLE, FoXsltConstants.XPATH
								+ number, new StringBuffer(
								"concat('wdems-start:" + edituiname + "(',"
										+ XPath + "," + authority + "')',"
										+ "','," + buttons + ",':wdems-end','"
										+ hascode + "'" + add + ")")));
			}
			setOutFov(true);
		}

		return output.toString();
	}

	/**
	 * 
	 * 获得inline内容的非转换的代码，根据inline的类型确定该处的输出（普通文本，格式化数字，日期时间格式等）
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getFormatContent()
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementUtil.ElementValueOf("$" + FoXsltConstants.CONTENT
				+ number));
		return output.toString();
	}

	public String textNoBinding()
	{
		StringBuffer output = new StringBuffer();
		TextInline inlne = (TextInline) foElement;
		Text textinLine = inlne.getContent();
		output.append(getHeaderElement());
		String text = IoXslUtil.getXmlText(textinLine.getText());
		String textnew = new String(text);
		textnew = textnew.trim();
		if (text.equals(textnew))
		{
			output.append(text);
		} else
		{
			output.append(ElementUtil.ElementValueOf(text));
		}
		output.append(ElementUtil.endElement(FoXsltConstants.INLINE));
		return output.toString();
	}

	public void setAttributemap(Map<Integer, Object> map)
	{
		if (map != null)
		{
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

	public boolean isOutFov()
	{
		return outFov;
	}

	public void setOutFov(boolean outFov)
	{
		this.outFov = outFov;
	}

	public boolean isFlg()
	{
		return flg;
	}

	public void setFlg(boolean flg)
	{
		this.flg = flg;
	}
}
