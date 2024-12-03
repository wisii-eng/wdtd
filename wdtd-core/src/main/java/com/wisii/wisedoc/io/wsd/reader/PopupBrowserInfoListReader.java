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
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserInfo;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserColumnInfo;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserInfo;
import com.wisii.wisedoc.document.attribute.transform.Column.ColumnType;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.wsd.attribute.PopupBrowserInfoWirter;
import com.wisii.wisedoc.log.LogUtil;
/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-4
 */
public class PopupBrowserInfoListReader extends AbstractHandler
{
	private List<PopupBrowserInfo> popupbrowserinfolists = new ArrayList<PopupBrowserInfo>();
	List<PopupBrowserColumnInfo> columninfolist;
	private int datasourcetype = -1;
	private DataSource datasource;
	private int sorttype = -1;
	private boolean isSearchable;
	void init()
	{
		popupbrowserinfolists = new ArrayList<PopupBrowserInfo>();
	}

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (PopupBrowserInfoWirter.SELECTINFO.equals(qname))
		{
			try
			{
				String dsid = atts.getValue(PopupBrowserInfoWirter.DATASOURCE);
				datasource = wsdhandler.getDataSource(dsid);
				if (datasource == null)
				{
					return;
				}
				columninfolist = new ArrayList<PopupBrowserColumnInfo>();
				
			} catch (Exception e)
			{
				LogUtil.debugException("解析SELECTINFO时出错", e);
			}
		} else if (PopupBrowserInfoWirter.COLUMNINFO.equals(qname))
		{
			if (columninfolist != null)
			{
				try
				{
					String viewname = XMLUtil.fromXmlText(atts.getValue(PopupBrowserInfoWirter.VIEWNAME));
					BindNode optionnode = wsdhandler.getNode(atts
							.getValue(PopupBrowserInfoWirter.OPTIONNODE));
					columninfolist.add(new PopupBrowserColumnInfo(viewname,optionnode));
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
		if (PopupBrowserInfoWirter.SELECTINFO.equals(qname))
		{
			if (columninfolist != null && !columninfolist.isEmpty())
			{
				popupbrowserinfolists.add(new PopupBrowserInfo( datasource,columninfolist));
			} else
			{
				popupbrowserinfolists.add(null);
			}
			columninfolist = null;
			datasourcetype = -1;
			datasource = null;
			sorttype = -1;
			isSearchable = false;
		}
	}

	public List<PopupBrowserInfo> getObject()
	{
		return popupbrowserinfolists;
	}

}
