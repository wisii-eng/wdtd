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
 * @AbstractElementsWriter.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.ConditionItemCollection;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.document.attribute.edit.ButtonGroup;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserInfo;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.io.AttributeIOFactory;
import com.wisii.wisedoc.io.AttributeWriter;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-12-27
 */
public abstract class AbstractElementsWriter implements ElementWriter
{
	// 动态数据节点和id映射关系map
	static Map<BindNode, String> datanodemap = new HashMap<BindNode, String>();
	protected static List<LogicalExpression> logicallist = new ArrayList<LogicalExpression>();
	protected static List<Attributes> attributelist = new ArrayList<Attributes>();
	protected static List<Group> grouplist = new ArrayList<Group>();
	protected static List<ConditionItemCollection> dynamicstylelist = new ArrayList<ConditionItemCollection>();
	protected static List<ParagraphStyles> paragraphstylelist = new ArrayList<ParagraphStyles>();
	protected static List<ChartDataList> chartdatallist = new ArrayList<ChartDataList>();
	protected static List<DataSource> datasourcelist = new ArrayList<DataSource>();
	protected static List<ConnWith> connwithlist = new ArrayList<ConnWith>();
	protected static List<GroupUI> groupUIlist = new ArrayList<GroupUI>();
	protected static List<SelectInfo> selectinfolist = new ArrayList<SelectInfo>();
	protected static List<PopupBrowserInfo> popupbrowserinfolist = new ArrayList<PopupBrowserInfo>();
	protected static List<List<ButtonGroup>> buttongropulist = new ArrayList<List<ButtonGroup>>();
	private final String ATTRIBUTES = "attributes";
	private final String LOGICALS = "logicals";
	private final String GROUPS = "groups";
	private final String DYNAMICSTYLES = "dynamicstyles";
	private final String PARAGRAPHSTYLES = "paragraphstyles";
	private final String CHARTDATALISTS = "chartdatalists";
	private final String DATASOURCES = "datasources";
	private final String SELECTINFOS = "selectinfos";
	private final String POPUPBROWSERINFOS = "popupbrowserinfos";
	private final String CONNWITHS = "connwiths";
	private final String GROUPUIS = "groupuis";
	public static final String BUTTONGROUPLISTES = "bslistes";
	private final String ATTRIBUTE = "attribute";
	final String ATTID = "id";
   protected void clear()
	{
		attributelist.clear();
		datanodemap.clear();
		logicallist.clear();
		dynamicstylelist.clear();
		grouplist.clear();
		paragraphstylelist.clear();
		chartdatallist.clear();
		datasourcelist.clear();
		selectinfolist.clear();
		popupbrowserinfolist.clear();
		connwithlist.clear();
		groupUIlist.clear();
		buttongropulist.clear();
	}
	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getAttributesID(Attributes attributes)
	{
		if (attributes == null
				|| (attributes != null && attributes.getAttributes().isEmpty()))
		{
			return null;
		}
		int index = attributelist.indexOf(attributes);
		if (index == -1)
		{
			attributelist.add(attributes);
			index = attributelist.size() - 1;
			ConditionItemCollection dynamicstyles = (ConditionItemCollection) attributes
					.getAttribute(Constants.PR_DYNAMIC_STYLE);
			if (dynamicstyles != null)
			{
				for (ConditionItem dynamicstyle : dynamicstyles)
				{
					if (dynamicstyle != null)
					{
						Attributes atts = dynamicstyle.getAttributes();
						if (atts != null && !attributelist.contains(atts))
						{
							attributelist.add(atts);
						}
						LogicalExpression le = dynamicstyle.getCondition();
						if (le != null && !logicallist.contains(le))
						{
							logicallist.add(le);
						}
					}
				}
				if (!dynamicstylelist.contains(dynamicstyles))
				{
					dynamicstylelist.add(dynamicstyles);
				}
			}
			SelectInfo selectinfo = (SelectInfo) attributes
					.getAttribute(Constants.PR_SELECT_INFO);
			if (selectinfo != null && !selectinfolist.contains(selectinfo))
			{
				DataSource datasource = selectinfo.getDatasource();
				if (datasource != null && !datasourcelist.contains(datasource))
				{
					datasourcelist.add(datasource);
				}
				selectinfolist.add(selectinfo);
			}
			PopupBrowserInfo popupbrowserinfo = (PopupBrowserInfo) attributes
					.getAttribute(Constants.PR_POPUPBROWSER_INFO);
			if (popupbrowserinfo != null && !popupbrowserinfolist.contains(popupbrowserinfo))
			{
				DataSource datasource = popupbrowserinfo.getDatasource();
				if (datasource != null && !datasourcelist.contains(datasource))
				{
					datasourcelist.add(datasource);
				}
				popupbrowserinfolist.add(popupbrowserinfo);
			}
			
			DataSource datasource = (DataSource) attributes
					.getAttribute(Constants.PR_TRANSFORM_TABLE);
			if (datasource != null && !datasourcelist.contains(datasource))
			{
				datasourcelist.add(datasource);
			}
			DataSource dic = (DataSource) attributes
					.getAttribute(Constants.PR_DATA_SOURCE);
			if (dic != null && !datasourcelist.contains(dic))
			{
				datasourcelist.add(dic);
			}
			ConnWith connwith = (ConnWith) attributes
					.getAttribute(Constants.PR_CONN_WITH);
			if (connwith != null && !connwithlist.contains(connwith))
			{
				connwithlist.add(connwith);
			}
			List<ButtonGroup> buttongroups = (List<ButtonGroup>) attributes
					.getAttribute(Constants.PR_EDIT_BUTTON);
			if (buttongroups != null && !buttongropulist.contains(buttongroups)
					&& isButtonGroupRight(buttongroups))
			{
				buttongropulist.add(buttongroups);
			}
		}
		return "" + index;
	}

	protected String generateConnwithListString()
	{
		String returns = "";
		if (!groupUIlist.isEmpty())
		{
			for (GroupUI gUI : groupUIlist)
			{
				ConnWith connwith = gUI.getConnwith();
				if (connwith != null)
				{
					connwithlist.add(connwith);
				}
			}
		}
		if (!connwithlist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + CONNWITHS + TAGEND + LINEBREAK;
			for (ConnWith connwith : connwithlist)
			{
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(connwith.getClass());
				returns = returns
						+ attrwriter.write(Constants.PR_CONN_WITH, connwith);
			}
			returns = returns + TAGENDSTART + CONNWITHS + TAGEND + LINEBREAK;
		}
		return returns;
	}

	protected String generateGroupUIListString()
	{
		String returns = "";
		if (!groupUIlist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + GROUPUIS + TAGEND + LINEBREAK;
			for (GroupUI gUI : groupUIlist)
			{
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(gUI.getClass());
				returns = returns
						+ attrwriter.write(Constants.PR_GROUP_REFERANCE, gUI);
			}
			returns = returns + TAGENDSTART + GROUPUIS + TAGEND + LINEBREAK;
		}
		return returns;
	}

	protected String generateDataSourceListString()
	{
		String returns = "";
		if (!datasourcelist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + DATASOURCES + TAGEND + LINEBREAK;
			for (DataSource datasource : datasourcelist)
			{
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(datasource.getClass());
				returns = returns
						+ attrwriter.write(Constants.PR_TRANSFORM_TABLE,
								datasource);
			}
			returns = returns + TAGENDSTART + DATASOURCES + TAGEND + LINEBREAK;
		}
		return returns;
	}

	protected String generateSelectInfoListString()
	{
		String returns = "";
		if (!selectinfolist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + SELECTINFOS + TAGEND + LINEBREAK;
			for (SelectInfo selectInfo : selectinfolist)
			{
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(selectInfo.getClass());
				returns = returns
						+ attrwriter
								.write(Constants.PR_SELECT_INFO, selectInfo);
			}
			returns = returns + TAGENDSTART + SELECTINFOS + TAGEND + LINEBREAK;
		}
		return returns;
	}
	protected String generatePopupBrowserInfoListString()
	{
		String returns = "";
		if (!popupbrowserinfolist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + POPUPBROWSERINFOS + TAGEND + LINEBREAK;
			for (PopupBrowserInfo popupbrowserInfo : popupbrowserinfolist)
			{
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(popupbrowserInfo.getClass());
				returns = returns
						+ attrwriter
						.write(Constants.PR_POPUPBROWSER_INFO, popupbrowserInfo);
			}
			returns = returns + TAGENDSTART + POPUPBROWSERINFOS + TAGEND + LINEBREAK;
		}
		return returns;
	}

	protected String generateChartDataListesString()
	{
		String returns = "";
		if (!chartdatallist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + CHARTDATALISTS + TAGEND + LINEBREAK;
			for (ChartDataList chartdatalist : chartdatallist)
			{
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(chartdatalist.getClass());
				returns = returns
						+ attrwriter.write(Constants.PR_SERIES_VALUE,
								chartdatalist);
			}
			returns = returns + TAGENDSTART + CHARTDATALISTS + TAGEND
					+ LINEBREAK;
		}
		return returns;
	}

	protected String generateAttributesesString()
	{
		String returns = "";
		returns = returns + TAGBEGIN + ATTRIBUTES + TAGEND + LINEBREAK;
		int size = attributelist.size();
		for (int i = 0; i < size; i++)
		{
			Attributes attributes = attributelist.get(i);
			returns = returns + generateAttributesString(attributes, i);
		}
		returns = returns + TAGENDSTART + ATTRIBUTES + TAGEND + LINEBREAK;
		return returns;
	}

	protected String generateAttributesString(Attributes attributes, int index)
	{
		String returns = "";
		returns = returns + TAGBEGIN + ATTRIBUTE + " " + ATTID + "=\"" + index
				+ "\"" + " ";
		returns = returns + generateAttributeString(attributes);
		returns = returns + NOCHILDTAGEND + LINEBREAK;
		return returns;
	}

	protected String generateAttributeString(Attributes attributes)
	{
		String returns = "";
		if (attributes != null)
		{
			Map<Integer, Object> map = attributes.getAttributes();
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			Object[] keys = map.keySet().toArray();
			// Integer[] keys = (Integer[]) map.keySet().toArray();
			int keysize = keys.length;
			for (int i = 0; i < keysize; i++)
			{
				Object atti = map.get(keys[i]);
				if (atti instanceof LogicalExpression)
				{
					logicallist.add((LogicalExpression) atti);
					atti = logicallist.size() - 1;
				} else if (atti instanceof Group)
				{
					Group group = (Group) atti;
					grouplist.add(group);
					LogicalExpression lohical = group.getFliterCondition();
					if (lohical != null)
					{
						logicallist.add(lohical);
					}
					atti = grouplist.size() - 1;
				} else if (atti instanceof ConditionItemCollection)
				{
					atti = dynamicstylelist.indexOf(atti);
				} else if (atti instanceof ParagraphStyles)
				{
					atti = paragraphstylelist.indexOf(atti);
				} else if (atti instanceof ChartDataList)
				{
					ChartDataList chartdatalist = (ChartDataList) atti;
					chartdatallist.add(chartdatalist);
					atti = chartdatallist.size() - 1;
				}
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter((Integer) keys[i]);
				returns = returns + attrwriter.write((Integer) keys[i], atti);
			}
		}
		return returns;
	}

	protected String generateElementsStrng(List<CellElement> children)
	{
		String returns = "";
		if (children != null)
		{
			for (CellElement child : children)
			{
				ElementWriter psw = IOFactory.getElementWriterFactory(
						IOFactory.WSD).getWriter(child);
				returns = returns + psw.write(child);
			}
		}
		return returns;
	}

	private static boolean isButtonGroupRight(List<ButtonGroup> buttongroups)
	{
		if (buttongroups == null || buttongroups.isEmpty())
		{
			return false;
		} else
		{
			for (ButtonGroup buttongroup : buttongroups)
			{
				CellElement goup = buttongroup.getCellment();
				if (goup == null)
				{
					return false;
				}
				Element element = goup;
				Element parent = element.getParent();
				while (parent != null)
				{
					int index = parent.getIndex(element);
					if (index < 0)
					{
						return false;
					}
					element = parent;
					parent = parent.getParent();
				}
				if (!(element instanceof Document))
				{
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getLogicalExpressionID(LogicalExpression logical)
	{
		int index = logicallist.indexOf(logical);
		if (index > -1)
		{
			return "" + index;
		}
		return null;
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getDynamicstyleID(ConditionItemCollection dynamicstyle)
	{
		return "" + dynamicstylelist.indexOf(dynamicstyle);
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getParagraphStylesID(ParagraphStyles paragraphstyles)
	{
		return "" + paragraphstylelist.indexOf(paragraphstyles);
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getDataSourceID(DataSource datasource)
	{
		return "" + datasourcelist.indexOf(datasource);
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getSelectInfoID(SelectInfo selectinfo)
	{
		return "" + selectinfolist.indexOf(selectinfo);
	}
	public static String getPopupBrowserInfoID(PopupBrowserInfo popupbrowserinfo)
	{
		return "" + popupbrowserinfolist.indexOf(popupbrowserinfo);
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getConnWithID(ConnWith conwith)
	{
		return "" + connwithlist.indexOf(conwith);
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getButtonGroupListID(List<ButtonGroup> buttongrouplist)
	{
		return "" + buttongropulist.indexOf(buttongrouplist);
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getGroupUIID(GroupUI groupui)
	{
		return "" + groupUIlist.indexOf(groupui);
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public AbstractElementsWriter()
	{
		super();
	}

	public String write(CellElement element)
	{
		// TODO Auto-generated method stub
		return null;
	}

	protected String generateLogicallistString()
	{
		String returns = "";
		if (!logicallist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + LOGICALS + TAGEND + LINEBREAK;
			int size = logicallist.size();
			for (int i = 0; i < size; i++)
			{
				LogicalExpression logical = logicallist.get(i);
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(logical.getClass());
				returns = returns
						+ attrwriter.write(Constants.PR_CONDTION, logical);
			}
			returns = returns + TAGENDSTART + LOGICALS + TAGEND + LINEBREAK;
		}
		return returns;
	}

	protected String generateGrouplistString()
	{
		String returns = "";
		if (!grouplist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + GROUPS + TAGEND + LINEBREAK;
			int size = grouplist.size();
			for (int i = 0; i < size; i++)
			{
				Group group = grouplist.get(i);
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(group.getClass());
				returns = returns + attrwriter.write(Constants.PR_GROUP, group);
			}
			returns = returns + TAGENDSTART + GROUPS + TAGEND + LINEBREAK;
		}
		return returns;
	}

	protected String generateDynamicStylelistString()
	{
		String returns = "";
		if (!dynamicstylelist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + DYNAMICSTYLES + TAGEND + LINEBREAK;
			for (ConditionItemCollection dynamicstyle : dynamicstylelist)
			{
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(dynamicstyle.getClass());
				returns = returns
						+ attrwriter.write(Constants.PR_DYNAMIC_STYLE,
								dynamicstyle);
			}
			returns = returns + TAGENDSTART + DYNAMICSTYLES + TAGEND
					+ LINEBREAK;
		}
		return returns;
	}

	protected String generateParagraphStyleslistString()
	{
		String returns = "";
		if (!paragraphstylelist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + PARAGRAPHSTYLES + TAGEND + LINEBREAK;
			for (ParagraphStyles paragraphstyle : paragraphstylelist)
			{
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(paragraphstyle.getClass());
				returns = returns
						+ attrwriter.write(Constants.PR_BLOCK_STYLE,
								paragraphstyle);
			}
			returns = returns + TAGENDSTART + PARAGRAPHSTYLES + TAGEND
					+ LINEBREAK;
		}
		return returns;
	}

	protected String generateButtonGrouplistString()
	{
		String returns = "";
		if (!buttongropulist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + BUTTONGROUPLISTES + TAGEND
					+ LINEBREAK;
			for (List<ButtonGroup> bglist : buttongropulist)
			{
				AttributeWriter attrwriter = attriofactory
						.getAttributeWriter(bglist.getClass());
				returns = returns
						+ attrwriter.write(Constants.PR_EDIT_BUTTON, bglist);
			}
			returns = returns + TAGENDSTART + BUTTONGROUPLISTES + TAGEND
					+ LINEBREAK;
		}
		return returns;
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getDataNodeID(BindNode node)
	{
		if (node == null)
		{
			return "";
		}
		String id = datanodemap.get(node);
		if (id == null)
		{
			id = node.getXPath();
		}
		return id;
	}
}