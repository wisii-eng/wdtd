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

package com.wisii.wisedoc.document.attribute.transform;
import java.util.List;

public final class TableInfo
{

	List<Column> columns;

	public TableInfo(List<Column> list)
	{
		if (list != null)
		{
			columns = list;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableInfo other = (TableInfo) obj;
		if (columns == null)
		{
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		return true;
	}

	// public String toString()
	// {
	// String code = "";
	// code = code + ElementUtil.startElement(DataSource.WDWENS + "tableinfo");
	// if (columns != null)
	// {
	// for (Column current : columns)
	// {
	// code = code + current.toString();
	// }
	// }
	// code = code + ElementUtil.endElement(DataSource.WDWENS + "tableinfo");
	// return code;
	// }
	//
	// public List<Column> getColumns()
	// {
	// return columns;
	// }
	//
	// public TableInfo clone()
	// {
	// TableInfo info = new TableInfo();
	// if (columns != null)
	// {
	// List<Column> columnslist = new ArrayList<Column>();
	// for (Column current : columns)
	// {
	// Column currentclone=current.clone();
	// columnslist.add(currentclone);
	// }
	// info.setColumns(columnslist);
	// }
	// return info;
	//
	// }
}
