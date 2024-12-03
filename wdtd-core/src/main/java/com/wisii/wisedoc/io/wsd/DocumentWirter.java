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
 * @DocumentWirter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.db.Setting;
import com.wisii.db.model.SQLItem;
import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.banding.SchemaRefNode;
import com.wisii.wisedoc.banding.SqlBindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;
import com.wisii.wisedoc.view.ui.model.paragraphstyles.ParagraphStylesModel;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-14
 */
public class DocumentWirter extends AbstractElementsWriter
{
	private final String DATAS = "datas";
	// 数据库链接相关的设置信息节点名
	private final String DBSET = "dbset";
	private final String NAMESPACES = "namespaces";
	private final String NAMESPACE = "namespace";
	private final String PAGESEQUENCES = "pagesequences";
	private final String TABLECONTENTS = "tablecontents";
	private static Map<Class, BindNodeWriter> datanodewritermap = new HashMap<Class, BindNodeWriter>();
	private int DATANODEINDEX;

	public DocumentWirter()
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
			String childrenstring = generatePageSequenceString(document
					.getAllChildren(), document);
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
			String popupbrowserinfoliststring = generatePopupBrowserInfoListString();
			String connwithstring = generateConnwithListString();
			String groupuistring = generateGroupUIListString();
			String bgliststring = generateButtonGrouplistString();
			returns = datastring + logicalliststring + groupliststring
					+ chartdataliststring + datasourceliststring
					+ selectinfoliststring + popupbrowserinfoliststring + connwithstring + groupuistring
					+ bgliststring + attstring + dynamicstyleliststring
					+ paragraphStylesliststring + tablecontentstring
					+ childrenstring;
		}
		clear();
		return returns;
	}

	private String generatePageSequenceString(
			List<CellElement> children, Document document)
	{
		String returns = "";
		Attributes atts = document.getAttributes();
		String attsrefid = "";
		// 生成属性引用代码
		String refid = DocumentWirter.getAttributesID(atts);
		if (atts != null && refid != null)
		{
			attsrefid = " attrefid=\"" + refid + "\"";
		}
		returns = returns + TAGBEGIN + PAGESEQUENCES + attsrefid + TAGEND
				+ LINEBREAK;
		// 遍历生成所有PageSequence的代码
		returns = returns + generateElementsStrng(children);
		returns = returns + TAGENDSTART + PAGESEQUENCES + TAGEND + LINEBREAK;
		return returns;
	}

	private String generateDataStrString(DataStructureTreeModel datamodel)
	{
		String returns = "";
		if (datamodel != null)
		{
			returns = returns + TAGBEGIN + DATAS + " datapath=\"" +datamodel.getDatapath() + "\""+ TAGEND + LINEBREAK;

			Setting dbsetting = datamodel.getDbsetting();

			if (dbsetting != null)
			{
				returns = returns + generateDBSetting(dbsetting);

			}
			List<NameSpace> namespaces = datamodel.getSpaces();
			if(namespaces!=null&&!namespaces.isEmpty())
			{
				returns = returns + generateNameSpaces(namespaces);
			}
			DATANODEINDEX = 0;
			returns = returns + generateNodes((BindNode) datamodel.getRoot());
			returns = returns + TAGENDSTART + DATAS + TAGEND + LINEBREAK;
		}

		return returns;

	}

	

	private String generateNameSpaces(List<NameSpace> namespaces) {
		StringBuffer sb = new StringBuffer();
		sb.append(TAGBEGIN);
		sb.append(NAMESPACES);
		sb.append(TAGEND);
		for (NameSpace namespace : namespaces)
		{
			sb.append(TAGBEGIN);
			sb.append(NAMESPACE);
			sb.append(' ');
			sb.append("key=\"");
			sb.append(namespace.getAttribute());
			sb.append('"');
			sb.append(' ');
			sb.append("value=\"");
			sb.append(namespace.getAttributeValue());
			sb.append('"');
			sb.append(NOCHILDTAGEND);
		}
		sb.append(TAGENDSTART);
		sb.append(NAMESPACES);
		sb.append(TAGEND);
		return sb.toString();
	}

	/**
	 * 生成数据库链接信息
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	String generateDBSetting(Setting dbsetting)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(TAGBEGIN);
		sb.append(DBSET);
		sb.append(' ');
		sb.append("dbname=\"");
		sb.append(XMLUtil.getXmlText(dbsetting.getConnname()));
		sb.append('\"');
		sb.append(TAGEND);
		sb.append(LINEBREAK);
		List<SQLItem> sqlitems = dbsetting.getSqlitem();
		if (sqlitems != null && !sqlitems.isEmpty())
		{
			for (SQLItem sqlitem : sqlitems)
			{
				sb.append(generateSQLItem(sqlitem));
			}
		}
		sb.append(TAGENDSTART);
		sb.append(DBSET); 
		sb.append(TAGEND);
		return sb.toString();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private String generateSQLItem(SQLItem sqlitem)
	{
		String returns = "";
		if (sqlitem != null)
		{
			returns = returns + TAGBEGIN + "sqlitem" + " ";
			returns = returns + "sql=\"" + XMLUtil.getXmlText(sqlitem.getSql())
					+ "\"";
			returns = returns + TAGEND + LINEBREAK;
			List<SQLItem> sqlitems = sqlitem.getChildren();
			if (sqlitems != null && !sqlitems.isEmpty())
			{
				for (SQLItem childsqlitem : sqlitems)
				{
					returns = returns + generateSQLItem(childsqlitem);
				}
			}
			returns = returns + TAGENDSTART + "sqlitem" + TAGEND + LINEBREAK;
		}
		return returns;
	}

	String generateNodes(BindNode node)
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

	protected String generatetablecontentstring(TableContents tablecontents)
	{
		String returns = "";
		if (tablecontents != null)
		{
			returns = returns + TAGBEGIN + TABLECONTENTS + " ";
			returns = returns
					+ generateAttributeString(tablecontents.getAttributes())
					+ TAGEND;
			returns = returns + generateElementsStrng(tablecontents.getAllChildren());
			returns = returns + TAGENDSTART + TABLECONTENTS + TAGEND
					+ LINEBREAK;
		}
		return returns;
	}
}
