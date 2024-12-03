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
 * @AbstractElementReader.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.BarCodeInline;
import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.BasicLink;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.BookmarkTree;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.ChartInline;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.Declarations;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.FOText;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.Leader;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.PageBreakMark;
import com.wisii.wisedoc.document.PageNumber;
import com.wisii.wisedoc.document.PageNumberCitation;
import com.wisii.wisedoc.document.PageNumberCitationLast;
import com.wisii.wisedoc.document.PositionInline;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.QianZhangInline;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableColumn;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.TableFooter;
import com.wisii.wisedoc.document.TableHeader;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.TotalPageNumber;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.document.svg.WordArtText;
import com.wisii.wisedoc.io.wsd.TableCellWriter;
import com.wisii.wisedoc.io.wsd.TableContentsWriter;
import com.wisii.wisedoc.io.wsd.TableRowWriter;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-12-28
 */
public abstract class AbstractElementReader extends AbstractHandler
{

	protected Stack<CellElement> _elementstack = new Stack<CellElement>();
	protected final Map<String, Class> _enamemap = new HashMap<String, Class>();
	protected final String BINDINGDATAREFID = "datarefid";
	protected final String TEXT = "text";
	protected final String TEXTTAG = "textinline";
	private TextInlineReader _textreader = new TextInlineReader();
	protected AbstractHandler _curentreader;
	private Map<String, CellElement> tablecellmap = new HashMap<String, CellElement>();

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public AbstractElementReader()
	{
		init();
	}

	protected void init()
	{
		_enamemap.put("image", ExternalGraphic.class);
		_enamemap.put("barcode", BarCode.class);
		_enamemap.put("block", Block.class);
		_enamemap.put("blockcontainer", BlockContainer.class);
		_enamemap.put("dtinline", DateTimeInline.class);
		_enamemap.put("checkboxline", CheckBoxInline.class);
		_enamemap.put("bookmarktree", BookmarkTree.class);
		_enamemap.put("declarations", Declarations.class);
		_enamemap.put("flow", Flow.class);
		_enamemap.put("staticcontent", StaticContent.class);
		_enamemap.put("imageinline", ImageInline.class);
		_enamemap.put("barcodeinline", BarCodeInline.class);
		_enamemap.put("inline", Inline.class);
		_enamemap.put("numbertext", NumberTextInline.class);
		_enamemap.put("positioninline", PositionInline.class);
		_enamemap.put("pagenumbercition", PageNumberCitation.class);
		_enamemap.put("pagenumbercitionlast", PageNumberCitationLast.class);
		_enamemap.put("totalpagenumber", TotalPageNumber.class);
		_enamemap.put("pagebreakmark", PageBreakMark.class);
		_enamemap.put("pagenumber", PageNumber.class);
		_enamemap.put("table", Table.class);
		_enamemap.put("tablebody", TableBody.class);
		_enamemap.put("tablecell", TableCell.class);
		_enamemap.put("tablecolumn", TableColumn.class);
		_enamemap.put("tablefooter", TableFooter.class);
		_enamemap.put("tableheader", TableHeader.class);
		_enamemap.put("tablerow", TableRow.class);
		_enamemap.put("text", FOText.class);
		_enamemap.put(TEXTTAG, TextInline.class);
		_enamemap.put("svgbox", SVGContainer.class);
		_enamemap.put("canvas", Canvas.class);
		_enamemap.put("line", Line.class);
		_enamemap.put("rect", Rectangle.class);
		_enamemap.put("ellipse", Ellipse.class);
		_enamemap.put("groupelement", Group.class);
		_enamemap.put("basiclink", BasicLink.class);
		_enamemap.put("leader", Leader.class);
		_enamemap.put("chartinline", ChartInline.class);
		_enamemap.put("chart", Chart.class);
		_enamemap.put("wordarttext", WordArtText.class);
		//_enamemap.put("zimoban",ZiMoban.class);
		_enamemap.put("qianzhang",QianZhang.class);
		_enamemap.put("qianzhanginline",QianZhangInline.class);
		_elementstack.clear();
		tablecellmap.clear();
	}

	protected void ininHandler(AbstractElementsHandler handler)
	{
		super.ininHandler(handler);
		_textreader.ininHandler(this.wsdhandler);
	}

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (TEXTTAG.equals(qname))
		{
			_curentreader = _textreader;
			_curentreader.startElement(uri, localname, qname, atts);
		} else
		{
			_curentreader = null;
			Class clazs = _enamemap.get(qname);
			if (clazs != null)
			{
				try
				{
					String tablecellrefid = atts.getValue(TableRowWriter.REFID);
					if (tablecellrefid != null && !tablecellrefid.isEmpty())
					{
	
						CellElement celle = tablecellmap.get(tablecellrefid);
						_elementstack.push(celle);
					} else
					{
						Constructor c = clazs.getConstructor(null);
						CellElement celle = (CellElement) c.newInstance(null);
						_elementstack.push(celle);
						String id = atts.getValue(ATTRIBUTEREFID);
						if (id != null)
						{
							com.wisii.wisedoc.document.attribute.Attributes attributes = wsdhandler.getAttributes(id);
							if (attributes != null)
							{
								celle.setAttributes(attributes.getAttributes(),
										true);
							}
						}
						String dateid = atts.getValue(BINDINGDATAREFID);
						if (dateid != null)
						{
							BindNode node = wsdhandler.getNode(dateid);
							if (dateid != null)
							{
								InlineContent content = null;
								if (celle instanceof BarCodeInline)
								{
									content = new BarCodeText(node);
								} else
								{
									content = new Text(node);
								}
								celle.setAttribute(Constants.PR_INLINE_CONTENT,
										content);
							}
						}
						String text = atts.getValue(TEXT);
						if (text != null&&!"".equals(text))
						{
	
							InlineContent content = new BarCodeText(text);
							celle.setAttribute(Constants.PR_INLINE_CONTENT,
									content);
						}
						String tablecellid = atts.getValue(TableCellWriter.ID);
						if (tablecellid != null && !tablecellid.isEmpty())
						{
							tablecellmap.put(tablecellid, celle);
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
					LogUtil.debugException("解析" + qname + "节点时出错", e);
				}
			}
			else if(TableContentsWriter.TABLECONTENTSTAG.equals(qname))
			{
				_elementstack.push(new TableContents(false));
			}
			else if("zimoban".equals(qname))
			{
				String id = atts.getValue(ATTRIBUTEREFID);
				String name = null;
				Map<Integer,Object> zimobanatts=null;
				if (id != null)
				{
					com.wisii.wisedoc.document.attribute.Attributes attributes = wsdhandler.getAttributes(id);
					
					if (attributes != null)
					{
						name = (String) attributes
								.getAttribute(Constants.PR_ZIMOBAN_NAME);
						zimobanatts = attributes.getAttributes();
						zimobanatts.remove(Constants.PR_ZIMOBAN_NAME);
					}
				}
				if (name != null)
				{
					ZiMoban zimoban = ZiMoban.getInstanceof(name);
					if(zimobanatts!=null&&!zimobanatts.isEmpty())
					{
						zimoban.setAttributes(zimobanatts, false);
					}
					_elementstack.push(zimoban);
				} else
				{
					throw new SAXException("模板名为：{"+name + "}的子模板读取不成功");
				}
			}
			else
			{
				throw new SAXException(qname + "节点为非法节点");
			}
		}
	}

	public void characters(char ch[], int start, int length)
			throws SAXException
	{
		if (_curentreader != null)
		{
			_curentreader.characters(ch, start, length);
		}
	}

}