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
 * @WsdSubWriter.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.zhumoban;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.banding.SchemaRefNode;
import com.wisii.wisedoc.banding.SqlBindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.WiseDocDocument;
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
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.io.AttributeIOFactory;
import com.wisii.wisedoc.io.AttributeWriter;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.wsd.BindNodeWriter;
import com.wisii.wisedoc.io.wsd.DefaultBindNodeWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;
import com.wisii.wisedoc.view.ui.model.paragraphstyles.ParagraphStylesModel;

/**
 * 类功能描述：
 *子模板输出类
 * 作者：zhangqiang
 * 创建日期：2010-10-21
 */
public class ZimobanWriter implements ElementWriter
{
	// 动态数据节点和id映射关系map
	private static Map<BindNode, String> datanodemap = new HashMap<BindNode, String>();
	// 所有文档中的条件属性
	private static List<LogicalExpression> logicallist = new ArrayList<LogicalExpression>();
	// 所有文档中的条件属性
	private static List<Attributes> attributelist = new ArrayList<Attributes>();
	// 文档中的所有组属性
	private static List<Group> grouplist = new ArrayList<Group>();
	// 文档中的所有条件样式属性
	private static List<ConditionItemCollection> dynamicstylelist = new ArrayList<ConditionItemCollection>();
	// 文档中的所有段落样式属性
	private static List<ParagraphStyles> paragraphstylelist = new ArrayList<ParagraphStyles>();
	// 统计图数据属性
	private static List<ChartDataList> chartdatallist = new ArrayList<ChartDataList>();
	// 数据源属性
	private static List<DataSource> datasourcelist = new ArrayList<DataSource>();
	// 关联属性
	private static List<ConnWith> connwithlist = new ArrayList<ConnWith>();
	private static List<GroupUI> groupUIlist = new ArrayList<GroupUI>(); 
	// 下拉列表设置属性
	private static List<SelectInfo> selectinfolist = new ArrayList<SelectInfo>();
	// 重复组操作属性
	private static List<List<ButtonGroup>> buttongropulist = new ArrayList<List<ButtonGroup>>();
	private final String ATTRIBUTES = "attributes";
	private final String LOGICALS = "logicals";
	private final String GROUPS = "groups";
	private final String DYNAMICSTYLES = "dynamicstyles";
	private final String PARAGRAPHSTYLES = "paragraphstyles";
	private final String CHARTDATALISTS = "chartdatalists";
	private final String DATASOURCES = "datasources";
	private final String SELECTINFOS = "selectinfos";
	private final String CONNWITHS = "connwiths";
	private final String GROUPUIS = "groupuis";
	public static final String BUTTONGROUPLISTES = "bslistes";
	private final String ATTRIBUTE = "attribute";
	private final String DATAS = "datas";
	private final String ZIMOBAN = "zimoban";
	private final String TABLECONTENTS = "tablecontents";
	private final String ATTID = "id";
	protected static Map<Class, BindNodeWriter> datanodewritermap = new HashMap<Class, BindNodeWriter>();
	private static int DATANODEINDEX;

	public ZimobanWriter()
	{
		init();
	}

	private void init()
	{
		BindNodeWriter nodewriter = new DefaultBindNodeWriter();
		datanodewritermap.put(DefaultBindNode.class, nodewriter);
		datanodewritermap.put(AttributeBindNode.class, nodewriter);
		datanodewritermap.put(SqlBindNode.class, nodewriter);
		datanodewritermap.put(SchemaRefNode.class, nodewriter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.ElementWriter#write()
	 */
	public String write(Document document)
	{
		String returns = "";
		if (document != null)
		{
			if (document instanceof WiseDocDocument)
			{
				List<GroupUI> documentgui = ((WiseDocDocument) document)
						.getListEditUI();
				if (documentgui != null && !documentgui.isEmpty())
				{
					groupUIlist.addAll(documentgui);
				}
			}
			String datastring = generateDataStrString(document
					.getDataStructure());
			String childrenstring = generateFlow(((PageSequence) document
					.getChildAt(0)).getMainFlow().children());
			List<ParagraphStyles> pstyles = ParagraphStylesModel.Instance
					.getParagraphStylesList();
			if (pstyles != null)
			{
				paragraphstylelist.addAll(pstyles);
			}
			for (ParagraphStyles parastyle : paragraphstylelist)
			{
				Attributes attributes = parastyle.getAttributes();
				if (attributes != null && !attributes.getAttributes().isEmpty()
						&& !attributelist.contains(attributes))
				{
					attributelist.add(attributes);
				}
			}
			TableContents tablecontents = document.getTableContents();
			if (tablecontents != null)
			{
				List<Attributes> indexstyles = (List<Attributes>) tablecontents
						.getAttribute(Constants.PR_BLOCK_REF_STYLES);
				if (indexstyles != null && !indexstyles.isEmpty())
				{
					for (Attributes indexstyle : indexstyles)
					{
						if (!attributelist.contains(indexstyle))
						{
							attributelist.add(indexstyle);
						}
					}
				}
			}
			String tablecontentstring = generatetablecontentstring(tablecontents);
			String attstring = generateAttributesesString();
			String logicalliststring = generateLogicallistString();
			String groupliststring = generateGrouplistString();
			String dynamicstyleliststring = generateDynamicStylelistString();
			String paragraphStylesliststring = generateParagraphStyleslistString();
			String chartdataliststring = generateChartDataListesString();
			String datasourceliststring = generateDataSourceListString();
			String selectinfoliststring = generateSelectInfoListString();
			String connwithstring = generateConnwithListString();
			String groupuistring = generateGroupUIListString();
			String bgliststring = generateButtonGrouplistString();
			returns = datastring + logicalliststring + groupliststring
					+ chartdataliststring + datasourceliststring
					+ selectinfoliststring + connwithstring + groupuistring
					+ bgliststring + attstring + dynamicstyleliststring
					+ paragraphStylesliststring + tablecontentstring
					+ childrenstring;
		}
		attributelist.clear();
		datanodemap.clear();
		logicallist.clear();
		dynamicstylelist.clear();
		grouplist.clear();
		paragraphstylelist.clear();
		chartdatallist.clear();
		datasourcelist.clear();
		selectinfolist.clear();
		connwithlist.clear();
		groupUIlist.clear();
		buttongropulist.clear();
		return returns;
	}

	private String generateConnwithListString()
	{
		String returns = "";
		if(!groupUIlist.isEmpty())
		{
			for(GroupUI gUI:groupUIlist)
			{
				ConnWith connwith = gUI.getConnwith();
				if(connwith!=null)
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
	private String generateGroupUIListString()
	{
		String returns = "";
		if (!groupUIlist.isEmpty())
		{
			AttributeIOFactory attriofactory = IOFactory
					.getAttributeIOFactory(IOFactory.WSD);
			returns = returns + TAGBEGIN + GROUPUIS + TAGEND + LINEBREAK;
			for (GroupUI gUI:groupUIlist)
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
	private String generateDataSourceListString()
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

	private String generateSelectInfoListString()
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

	private String generateChartDataListesString()
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

	/*
	 * 生成所有的属性集合字符串，所有的属性集合在文档头生成，具体生成对象时只生成属性的id的引用
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
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

	/*
	 * 
	 * 生成属性集合字符串
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	protected String generateAttributesString(Attributes attributes, int index)
	{
		String returns = "";
		returns = returns + TAGBEGIN + ATTRIBUTE + " " + ATTID + "=\"" + index
				+ "\"" + " ";
		returns = returns + generateAttributeString(attributes);
		returns = returns + NOCHILDTAGEND + LINEBREAK;
		return returns;
	}

	/*
	 * 
	 * 生成属性值的字符串
	 * 
	 * @param
	 * 
	 * @return String:
	 * 
	 * @exception
	 */
	private String generateAttributeString(Attributes attributes)
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

	private String generateFlow(Enumeration<CellElement> children)
	{
		String returns = "";
		returns = returns + TAGBEGIN + ZIMOBAN + TAGEND + LINEBREAK;
		// 遍历生成所有PageSequence的代码
		returns = returns + generateElementsStrng(children);
		returns = returns + TAGENDSTART + ZIMOBAN + TAGEND + LINEBREAK;
		return returns;
	}

	private String generateElementsStrng(Enumeration<CellElement> children)
	{
		String returns = "";
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				CellElement ps = children.nextElement();
				ElementWriter psw = IOFactory.getElementWriterFactory(
						IOFactory.WSD).getWriter(ps);
				returns = returns + psw.write(ps);
			}
		}
		return returns;
	}

	/*
	 * 
	 * 生成数据结构相关的数据
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	protected String generateDataStrString(DataStructureTreeModel datamodel)
	{
		String returns = "";
		returns = returns + TAGBEGIN + DATAS + TAGEND + LINEBREAK;
		if (datamodel != null)
		{
			DATANODEINDEX = 0;
			returns = returns + generateNodes((BindNode) datamodel.getRoot());
		}
		returns = returns + TAGENDSTART + DATAS + TAGEND + LINEBREAK;
		return returns;

	}

	private String generateNodes(BindNode node)
	{
		String s = "";
		if (node != null)
		{
			BindNodeWriter writer = datanodewritermap.get(node.getClass());
			if (writer != null)
			{
				s = s + writer.writebegin(node, DATANODEINDEX);
				datanodemap.put(node, "" + DATANODEINDEX++);
				if(!(node instanceof SchemaRefNode)){
				int size = node.getChildCount();
				for (int i = 0; i < size; i++)
				{
					BindNode cnode = node.getChildAt(i);
					s = s + generateNodes(cnode);
				}
				}
				s = s + writer.writeend();
			}
		}
		return s;
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
			DataSource datasource = (DataSource) attributes
					.getAttribute(Constants.PR_TRANSFORM_TABLE);
			if (datasource != null && !datasourcelist.contains(datasource))
			{
				datasourcelist.add(datasource);
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
	public static String getDataNodeID(BindNode node)
	{
		return datanodemap.get(node);
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
	public String write(CellElement element)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 
	 * 输出所有条件属性
	 * 
	 * @param
	 * 
	 * @return {返回参数名} {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
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

	/*
	 * 
	 * 生成所有组属性
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private String generateGrouplistString()
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

	/*
	 * 
	 * 生成动态样式属性
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private String generateDynamicStylelistString()
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

	/*
	 * 
	 * 生成段落样式属性
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private String generateParagraphStyleslistString()
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

	/*
	 * 
	 * 生成段落样式属性
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private String generateButtonGrouplistString()
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

	/*
	 * 
	 * 生成目录对象的输出内容
	 * 
	 * @param tablecontents: 目录对象
	 * 
	 * @return String: {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	private String generatetablecontentstring(TableContents tablecontents)
	{
		String returns = "";
		if (tablecontents != null)
		{
			returns = returns + TAGBEGIN + TABLECONTENTS + " ";
			returns = returns
					+ generateAttributeString(tablecontents.getAttributes())
					+ TAGEND;
			returns = returns + generateElementsStrng(tablecontents.children());
			returns = returns + TAGENDSTART + TABLECONTENTS + TAGEND
					+ LINEBREAK;
		}
		return returns;
	}

}
