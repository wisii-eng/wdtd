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
 * @DefaultWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.BarCodeInline;
import com.wisii.wisedoc.document.BasicLink;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.BookmarkTree;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.ChartInline;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.Declarations;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.FOText;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
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
import com.wisii.wisedoc.document.TableFooter;
import com.wisii.wisedoc.document.TableHeader;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.TotalPageNumber;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.document.svg.WordArtText;

/**
 * 类功能描述：元素
 * 
 * 作者：zhangqiang 创建日期：2008-9-17
 */
public class DefaultElementWriter extends AbstractElementWriter {
	// 元素和元素节点名对应关系map
	private final Map<Class, String> _enamemap = new HashMap<Class, String>();
	protected final String DATAREFID = "datarefid";
	protected final String TEXT = "text";
	public DefaultElementWriter() {
		init();
	}

	private void init() {
		_enamemap.put(ExternalGraphic.class, "image");
		_enamemap.put(Block.class, "block");
		_enamemap.put(BlockContainer.class, "blockcontainer");
		_enamemap.put(DateTimeInline.class, "dtinline");
		_enamemap.put(CheckBoxInline.class, "checkboxline");
		_enamemap.put(BookmarkTree.class, "bookmarktree");
		_enamemap.put(Declarations.class, "declarations");
		_enamemap.put(Flow.class, "flow");
		_enamemap.put(StaticContent.class, "staticcontent");
		_enamemap.put(ImageInline.class, "imageinline");
		_enamemap.put(BarCode.class, "barcode");
		_enamemap.put(Inline.class, "inline");
		_enamemap.put(NumberTextInline.class, "numbertext");
		_enamemap.put(PositionInline.class, "positioninline");
		_enamemap.put(PageBreakMark.class, "pagebreakmark");
		_enamemap.put(PageNumber.class, "pagenumber");
		_enamemap.put(PageNumberCitation.class, "pagenumbercition");
		_enamemap.put(PageNumberCitationLast.class, "pagenumbercitionlast");
		_enamemap.put(TotalPageNumber.class, "totalpagenumber");
		_enamemap.put(Table.class, "table");
		_enamemap.put(TableBody.class, "tablebody");
		_enamemap.put(TableCell.class, "tablecell");
		_enamemap.put(TableColumn.class, "tablecolumn");
		_enamemap.put(TableFooter.class, "tablefooter");
		_enamemap.put(TableHeader.class, "tableheader");
		_enamemap.put(TableRow.class, "tablerow");
		_enamemap.put(TextInline.class, "textinline");
		_enamemap.put(FOText.class, "text");
		_enamemap.put(BarCodeInline.class, "barcodeinline");
		_enamemap.put(SVGContainer.class, "svgbox");
		_enamemap.put(Canvas.class, "canvas");
		_enamemap.put(Line.class, "line");
		_enamemap.put(Rectangle.class, "rect");
		_enamemap.put(Ellipse.class, "ellipse");
		_enamemap.put(Group.class, "groupelement");
		_enamemap.put(BasicLink.class, "basiclink");
		_enamemap.put(Leader.class, "leader");
		_enamemap.put(ChartInline.class, "chartinline");
		_enamemap.put(Chart.class, "chart");
		_enamemap.put(WordArtText.class, "wordarttext");
		_enamemap.put(ZiMoban.class, "zimoban");
		_enamemap.put(QianZhang.class, "qianzhang");
		_enamemap.put(QianZhangInline.class, "qianzhanginline");
		// _enamemap.put(Title.class, "title");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.ElementWriter#write(com.wisii.wisedoc.document.Element
	 * )
	 */
	public String write(CellElement element)
	{

		String returns = "";
		if (element != null)
		{
			// 如果是绑定动态数据节点的文本
			if (element instanceof Inline
					&& !(element instanceof BarCodeInline)
					&& ((Inline) element).getContent() != null)
			{
				returns = returns + generateBindingText((Inline) element);
			} else
			{
				Attributes atts = element.getAttributes();
				String attsrefid = "";
				// 生成属性引用代码
				String refid = DocumentWirter.getAttributesID(atts);
				if (atts != null && refid != null)
				{
					attsrefid = attsrefid + ATTRIBUTEREFID + EQUALTAG
							+ QUOTATIONTAG + refid + QUOTATIONTAG;
				}
				String elementname = getElementName(element);
				// 生成元素头代码
				returns = returns + TAGBEGIN + elementname + SPACETAG
						+ attsrefid + TAGEND + LINEBREAK;
				Enumeration<CellElement> children = element.children();
				// 生成子元素代码
				returns = returns + getChildrenString(children);
				// 生成元素结束代码
				returns = returns + TAGENDSTART + elementname + TAGEND
						+ LINEBREAK;
			}
		}
		return returns;
	}
	/*
	 * 
	 * 生成带有动态数据的文本节点
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * 
	 * @return {返回参数名} {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	private String generateBindingText(Inline inlne)
	{
		String returns = "";
		if (inlne != null && inlne.getContent() != null)
		{
			if(inlne.getContent().isBindContent()){
			String ptextname = getElementName(inlne);
			Attributes atts = inlne.getAttributes();
			String attsrefid = "";
			// 生成属性引用代码
			String refid = DocumentWirter.getAttributesID(atts);
			if (atts != null && refid != null)
			{
				attsrefid = SPACETAG + attsrefid + ATTRIBUTEREFID + EQUALTAG
						+ QUOTATIONTAG + refid + QUOTATIONTAG + SPACETAG;
			}
			BindNode node = inlne.getContent().getBindNode();
			String dataid = DocumentWirter.getDataNodeID(node);
			String dataids = "";
			if (node != null && dataid != null)
			{
				dataids = SPACETAG + dataids + DATAREFID + EQUALTAG
						+ QUOTATIONTAG + dataid + QUOTATIONTAG + SPACETAG;
			}
			returns = returns + TAGBEGIN + ptextname + attsrefid + dataids
					+ NOCHILDTAGEND;
			}
			else
			{
				String ptextname = getElementName(inlne);
				Attributes atts = inlne.getAttributes();
				String attsrefid = "";
				// 生成属性引用代码
				String refid = DocumentWirter.getAttributesID(atts);
				if (atts != null && refid != null)
				{
					attsrefid = SPACETAG + attsrefid + ATTRIBUTEREFID + EQUALTAG
							+ QUOTATIONTAG + refid + QUOTATIONTAG + SPACETAG;
				}
				String  text = inlne.getContent().getText();
				String texts = "";
				if (!"".equals(text))
				{
					texts = SPACETAG + texts + TEXT + EQUALTAG
							+ QUOTATIONTAG + text + QUOTATIONTAG + SPACETAG;
				}
				returns = returns + TAGBEGIN + ptextname + attsrefid + texts
						+ NOCHILDTAGEND;
			}
		}
		return returns;
	}
	protected String getChildrenString(Enumeration<CellElement> children) {
		String returns = "";
		// 遍历子元素，生成子元素的代码
		while (children != null && children.hasMoreElements()) {
			CellElement child = children.nextElement();
			returns = returns + ewf.getWriter(child).write(child);
		}
		return returns;
	}

	protected String getElementName(CellElement element) {
		return _enamemap.get(element.getClass());
	}
}
