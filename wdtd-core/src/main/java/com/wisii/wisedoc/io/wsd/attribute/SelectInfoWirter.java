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
import com.wisii.wisedoc.document.attribute.transform.SelectColumnInFO;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.document.attribute.transform.Column.ColumnType;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.wsd.DocumentWirter;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-3
 */
public class SelectInfoWirter extends AbstractAttributeWriter
{
	public static final String TYPE = "type";
	public static final String SORT = "sort";
	public static final String SEARCHABLE = "searchable";
	public static final String DATASOURCE = "datasource";
	public static final String SELECTINFO = "selectinfo";
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
		if (!(value instanceof SelectInfo))
		{
			return "";
		}
		SelectInfo selectinfo = (SelectInfo) value;
		String returns = "";
		returns = returns + ElementWriter.TAGBEGIN + SELECTINFO;
		int sorttype = selectinfo.getSorttype();
		returns = returns + SPACETAG + SORT + EQUALTAG + QUOTATIONTAG
				+ sorttype + QUOTATIONTAG;
		int type = selectinfo.getDatasourcetype();
		returns = returns + SPACETAG + TYPE + EQUALTAG + QUOTATIONTAG + type
				+ QUOTATIONTAG;
		boolean searchable = selectinfo.isSearchable();
		returns = returns + SPACETAG + SEARCHABLE + EQUALTAG + QUOTATIONTAG
				+ searchable + QUOTATIONTAG;
		DataSource datasource = selectinfo.getDatasource();
		if (datasource != null)
		{
			returns = returns + SPACETAG + DATASOURCE + EQUALTAG + QUOTATIONTAG
					+ DocumentWirter.getDataSourceID(datasource) + QUOTATIONTAG;
		}
		returns = returns + ElementWriter.TAGEND;
		List<SelectColumnInFO> columninfos = selectinfo.getColumninfos();
		if (columninfos != null && !columninfos.isEmpty())
		{
			returns = returns + getColumninfosString(columninfos);
		}
		returns = returns + ElementWriter.TAGENDSTART + SELECTINFO
				+ ElementWriter.TAGEND;
		return returns;
	}

	private String getColumninfosString(List<SelectColumnInFO> columninfos)
	{
		String returns = "";
		for (SelectColumnInFO columninfo : columninfos)
		{
			returns = returns + ElementWriter.TAGBEGIN + COLUMNINFO;
			int datacolumnindex = columninfo.getDatacolumnindex();
			returns = returns + SPACETAG + INDEX + EQUALTAG + QUOTATIONTAG
					+ datacolumnindex + QUOTATIONTAG;
			boolean viewable = columninfo.isIsviewable();
			returns = returns + SPACETAG + VIEWABLE + EQUALTAG + QUOTATIONTAG
					+ viewable + QUOTATIONTAG;
			int sortorder = columninfo.getSortorder();
			if (sortorder > -1)
			{
				returns = returns + SPACETAG + SORTORDER + EQUALTAG
						+ QUOTATIONTAG + sortorder + QUOTATIONTAG;
			}
			int searchorder = columninfo.getSortorder();
			if (searchorder > -1)
			{
				returns = returns + SPACETAG + SEARCHORDER + EQUALTAG
						+ QUOTATIONTAG + searchorder + QUOTATIONTAG;
			}
			BindNode option = columninfo.getOptionpath();
			if (option != null)
			{
				returns = returns + SPACETAG + OPTIONNODE + EQUALTAG
						+ QUOTATIONTAG + DocumentWirter.getDataNodeID(option)
						+ QUOTATIONTAG;
			}
			String viewname = XMLUtil.getXmlText(columninfo.getViewname());
			returns = returns + SPACETAG + VIEWNAME + EQUALTAG + QUOTATIONTAG
					+ viewname + QUOTATIONTAG;
			ColumnType datatype = columninfo.getColumntype();
			returns = returns + SPACETAG + DATATYPE + EQUALTAG + QUOTATIONTAG
					+ datatype + QUOTATIONTAG + ElementWriter.NOCHILDTAGEND;

		}
		return returns;
	}

}
