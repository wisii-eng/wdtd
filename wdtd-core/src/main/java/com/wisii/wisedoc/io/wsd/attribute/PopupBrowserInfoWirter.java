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
 * @SelectInfoWirter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.util.List;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserColumnInfo;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserInfo;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.wsd.DocumentWirter;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-3
 */
public class PopupBrowserInfoWirter extends AbstractAttributeWriter
{
	public static final String TYPE = "type";
	public static final String SORT = "sort";
	public static final String SEARCHABLE = "searchable";
	public static final String DATASOURCE = "datasource";
	public static final String SELECTINFO = "popupbrowserinfo";
	public static final String COLUMNINFO = "columninfo";
	public static final String INDEX = "index";
	public static final String VIEWABLE = "viewable";
	public static final String SORTORDER = "sortorder";
	public static final String SEARCHORDER = "searchorder";
	public static final String VIEWNAME = "viewname";
	public static final String DATATYPE = "datatype";
	public static final String OPTIONNODE = "option";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (!(value instanceof PopupBrowserInfo))
		{
			return "";
		}
		PopupBrowserInfo selectinfo = (PopupBrowserInfo) value;
		String returns = "";
		returns = returns + ElementWriter.TAGBEGIN + SELECTINFO;
		DataSource datasource = selectinfo.getDatasource();
		if (datasource != null)
		{
			returns = returns + SPACETAG + DATASOURCE + EQUALTAG + QUOTATIONTAG
					+ DocumentWirter.getDataSourceID(datasource) + QUOTATIONTAG;
		}
		returns = returns + ElementWriter.TAGEND;
		List<PopupBrowserColumnInfo> columninfos = selectinfo.getColumninfos();
		if (columninfos != null && !columninfos.isEmpty())
		{
			returns = returns + getColumninfosString(columninfos);
		}
		returns = returns + ElementWriter.TAGENDSTART + SELECTINFO
				+ ElementWriter.TAGEND;
		return returns;
	}

	private String getColumninfosString(List<PopupBrowserColumnInfo> columninfos)
	{
		String returns = "";
		for (PopupBrowserColumnInfo columninfo : columninfos)
		{
			returns = returns + ElementWriter.TAGBEGIN + COLUMNINFO;
			BindNode option = columninfo.getOptionpath();
			if (option != null)
			{
				returns = returns + SPACETAG + OPTIONNODE + EQUALTAG
						+ QUOTATIONTAG + DocumentWirter.getDataNodeID(option)
						+ QUOTATIONTAG;
			}
			String viewname = XMLUtil.getXmlText(columninfo.getName());
			returns = returns + SPACETAG + VIEWNAME + EQUALTAG + QUOTATIONTAG
					+ viewname + QUOTATIONTAG;
			returns = returns + SPACETAG + DATATYPE + EQUALTAG + QUOTATIONTAG
					+ "" + QUOTATIONTAG + ElementWriter.NOCHILDTAGEND;
		}
		return returns;
	}

}
