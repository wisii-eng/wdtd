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
 * @ReMoveStructureAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.ChartInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ChartData2D;
import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.document.attribute.ConditionItemCollection;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;

/**
 * 类功能描述：删除数据结构事件类
 * 
 * 作者：zhangqiang 创建日期：2008-6-2
 */
public class ReMoveStructureAction extends BaseAction
{

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public ReMoveStructureAction()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public ReMoveStructureAction(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public ReMoveStructureAction(String name, Icon icon)
	{
		super(name, icon);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.action.BaseAction#doAction(java.awt.event.ActionEvent)
	 */
	@Override
	public void doAction(ActionEvent e)
	{
		SystemManager.getCurruentDocument().setDataStructure(null);
		BrowseAction.setDatafileurl(null);
		clearDocument(SystemManager.getCurruentDocument());

	}

	private void clearDocument(Document doc)
	{
		clearElement((Element) doc, doc);
	}

	private void clearElement(Element element, Document doc)
	{
		//如果是子模板，则不需要清除子模板中的动态数据节点
		if(element instanceof ZiMoban)
		{
			return;
		}
		Attributes att = element.getAttributes();
		if (att != null)
		{
			Map<Integer, Object> newatts = new HashMap<Integer, Object>();
			Object bgimage = att.getAttribute(Constants.PR_BACKGROUND_IMAGE);
			if (bgimage instanceof BindNode)
			{
				newatts.put(Constants.PR_BACKGROUND_IMAGE, null);
			}
			Group group = (Group) att.getAttribute(Constants.PR_GROUP);
			if (group != null)
			{
				newatts.put(Constants.PR_GROUP, null);
			}
			LogicalExpression condition = (LogicalExpression) att
					.getAttribute(Constants.PR_CONDTION);
			if (condition != null)
			{
				newatts.put(Constants.PR_CONDTION, null);
			}
			ConnWith conwith = (ConnWith) att
					.getAttribute(Constants.PR_CONN_WITH);
			if (conwith != null)
			{
				newatts.put(Constants.PR_CONN_WITH, null);
			}
			SelectInfo selectinfo = (SelectInfo) att
			.getAttribute(Constants.PR_SELECT_INFO);
			// 关联属性是否正常
			if (selectinfo!=null)
			{
				newatts.put(Constants.PR_SELECT_INFO, null);
			}
			ConditionItemCollection dynamicStyle = (ConditionItemCollection) att
					.getAttribute(Constants.PR_DYNAMIC_STYLE);
			if (dynamicStyle != null)
			{
				newatts.put(Constants.PR_DYNAMIC_STYLE, null);
			}
			if (!newatts.isEmpty())
			{
				if (element instanceof CellElement)
				{
					doc.setElementAttributes((CellElement) element, newatts,
							false);
				} else
				{
					element.setAttributes(newatts, false);
				}
			}
			if (element instanceof Inline)
			{
				clearInline((Inline) element, doc);
			}
			List<CellElement> children = element.getAllChildren();
			if (children != null && !children.isEmpty())
			{
				for (CellElement child : children)
				{
					clearElement(child, doc);
				}
			}
		}
	}

	private void clearInline(Inline element, Document doc)
	{
		if (element instanceof TextInline)
		{
			InlineContent content = element.getContent();
			if (content != null && content.isBindContent())
			{
				List<CellElement> elements = new ArrayList<CellElement>();
				elements.add(element);
				doc.deleteElements(elements);
			}
		}
		else if(element instanceof ImageInline)
		{
			ExternalGraphic image = (ExternalGraphic) element.getChildAt(0);
			if (image == null)
			{
				return;
			}
			Object src = image.getAttribute(Constants.PR_SRC);
			if (src instanceof BindNode)
			{
				List<CellElement> elements = new ArrayList<CellElement>();
				elements.add(element);
				doc.deleteElements(elements);
			}
		}
		else if (element instanceof ChartInline)
		{
			Chart chart = (Chart) element.getChildAt(0);
			if (chart == null)
			{
				return;
			}
			Map<Integer, Object> tosetattrs = new HashMap<Integer, Object>();
			Attributes attrs = chart.getAttributes();
			BarCodeText title = (BarCodeText) attrs
					.getAttribute(Constants.PR_TITLE);
			if (title != null && title.isBindContent())
			{
				tosetattrs.put(Constants.PR_TITLE, null);
			}
			List<BarCodeText> valuelabellist = (List<BarCodeText>) attrs
					.getAttribute(Constants.PR_VALUE_LABEL);
			if (valuelabellist != null && !valuelabellist.isEmpty())
			{

				tosetattrs.put(Constants.PR_VALUE_LABEL, null);
			}
			List<BarCodeText> serieslabellist = (List<BarCodeText>) attrs
					.getAttribute(Constants.PR_SERIES_LABEL);
			if (!isBarCodeTextListOk(serieslabellist))
			{
				tosetattrs.put(Constants.PR_SERIES_LABEL, null);
			}
			BarCodeText domlabel = (BarCodeText) attrs
					.getAttribute(Constants.PR_DOMAINAXIS_LABEL);
			if (domlabel != null && domlabel.isBindContent())
			{
				tosetattrs.put(Constants.PR_DOMAINAXIS_LABEL, null);
			}

			BarCodeText rangeaxislabel = (BarCodeText) attrs
					.getAttribute(Constants.PR_RANGEAXIS_LABEL);
			if (rangeaxislabel != null && rangeaxislabel.isBindContent())
			{
				tosetattrs.put(Constants.PR_RANGEAXIS_LABEL, null);
			}
			ChartDataList seriesvalue = (ChartDataList) attrs
					.getAttribute(Constants.PR_SERIES_VALUE);
			if (!isChartDataListOk(seriesvalue))
			{
				tosetattrs.put(Constants.PR_SERIES_VALUE, null);
			}
			if (!tosetattrs.isEmpty())
			{
				doc.setElementAttributes(chart, tosetattrs, false);
			}
		} else
		{
			InlineContent content = element.getContent();
			if (content != null && content.isBindContent())
			{
				Map<Integer, Object> attrs = new HashMap<Integer, Object>();
				attrs.put(Constants.PR_INLINE_CONTENT, null);
				doc.setElementAttributes(element, attrs, false);
			}
		}
	}

	private boolean isChartDataListOk(ChartDataList seriesvalue)
	{
		if (seriesvalue == null || seriesvalue.isEmpty())
		{
			return true;
		}
		for (ChartData2D chd2d : seriesvalue)
		{
			if (chd2d != null && chd2d.getNumbercontent().isBindContent())
			{
				return false;
			}
		}
		return true;
	}

	private boolean isBarCodeTextListOk(List<BarCodeText> barcodetextlist)
	{
		if (barcodetextlist == null || barcodetextlist.isEmpty())
		{
			return true;
		}
		for (BarCodeText barcodetext : barcodetextlist)
		{
			if (barcodetext != null && barcodetext.isBindContent())
			{
				return false;
			}
		}
		return true;
	}
}
