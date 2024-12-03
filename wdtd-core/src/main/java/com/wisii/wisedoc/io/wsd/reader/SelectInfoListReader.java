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
 * @SelectInfoListReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.SelectColumnInFO;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.document.attribute.transform.Column.ColumnType;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.wsd.attribute.SelectInfoWirter;
import com.wisii.wisedoc.log.LogUtil;
/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-4
 */
public class SelectInfoListReader extends AbstractHandler
{
	private List<SelectInfo> selectinfolists = new ArrayList<SelectInfo>();
	List<SelectColumnInFO> columninfolist;
	private int datasourcetype = -1;
	private DataSource datasource;
	private int sorttype = -1;
	private boolean isSearchable;
	void init()
	{
		selectinfolists = new ArrayList<SelectInfo>();
	}

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (SelectInfoWirter.SELECTINFO.equals(qname))
		{
			try
			{
				String sortstring = atts.getValue(SelectInfoWirter.SORT);
				sorttype = Integer.parseInt(sortstring);
				String dsid = atts.getValue(SelectInfoWirter.DATASOURCE);
				datasource = wsdhandler.getDataSource(dsid);
				if (datasource == null)
				{
					return;
				}
				String dstypestring = atts.getValue(SelectInfoWirter.TYPE);
				datasourcetype = Integer.parseInt(dstypestring);
				isSearchable = Boolean.parseBoolean(atts
						.getValue(SelectInfoWirter.SEARCHABLE));
				columninfolist = new ArrayList<SelectColumnInFO>();
			} catch (Exception e)
			{
				LogUtil.debugException("解析SELECTINFO时出错", e);
			}
		} else if (SelectInfoWirter.COLUMNINFO.equals(qname))
		{
			if (columninfolist != null)
			{
				try
				{
					String indexstring = atts.getValue(SelectInfoWirter.INDEX);
					int index = Integer.parseInt(indexstring);
					boolean visable = Boolean.parseBoolean(atts
							.getValue(SelectInfoWirter.VIEWABLE));
					String viewname = XMLUtil.fromXmlText(atts.getValue(SelectInfoWirter.VIEWNAME));
					int sortorder = -1;
					String sortorderstring = atts
							.getValue(SelectInfoWirter.SORTORDER);
					if (sortorderstring != null)
					{
						sortorder = Integer.parseInt(sortorderstring);
					}
					int searchorder = -1;
					String searchorderstring = atts
							.getValue(SelectInfoWirter.SEARCHORDER);
					if (searchorderstring != null)
					{
						searchorder = Integer.parseInt(searchorderstring);
					}
					ColumnType columntype = ColumnType.valueOf(atts
							.getValue(SelectInfoWirter.DATATYPE));
					BindNode optionnode = wsdhandler.getNode(atts
							.getValue(SelectInfoWirter.OPTIONNODE));
					columninfolist.add(new SelectColumnInFO(index, visable,
							optionnode, searchorder, sortorder, viewname,
							columntype));
				} catch (Exception e)
				{
					LogUtil.debugException("COLUMNINFO", e);
				}
			}
		} else
		{
			throw new SAXException("chartdatalists 节点下的" + qname + "节点为非法");
		}
	}

	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (SelectInfoWirter.SELECTINFO.equals(qname))
		{
			if (columninfolist != null && !columninfolist.isEmpty())
			{
				selectinfolists.add(new SelectInfo(columninfolist, datasource,
						datasourcetype, isSearchable, sorttype));
			} else
			{
				selectinfolists.add(null);
			}
			columninfolist = null;
			datasourcetype = -1;
			datasource = null;
			sorttype = -1;
			isSearchable = false;
		}
	}

	public List<SelectInfo> getObject()
	{
		return selectinfolists;
	}

}
