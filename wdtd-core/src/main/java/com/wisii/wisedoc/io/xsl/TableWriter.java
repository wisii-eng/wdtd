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

package com.wisii.wisedoc.io.xsl;

import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableColumn;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

public class TableWriter extends AbsElementWriter
{

	private static Stack<Boolean> tablecellgroup = new Stack<Boolean>();

	public TableWriter(CellElement element)
	{
		super(element);
	}

	@Override
	public String getElementName()
	{
		return FoXsltConstants.TABLE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (foElement != null)
		{
			Table table = (Table) foElement;
			boolean currentflg = haveCellGroup();
			tablecellgroup.push(currentflg);
			if (!currentflg)
			{
				List columns = table.getColumns();
				if (columns != null && !columns.isEmpty())
				{
					int size = columns.size();
					for (int i = 0; i < size; i++)
					{
						TableColumn currentcolumn = (TableColumn) columns
								.get(i);
						output.append(ElementWriter.TAGBEGIN);
						output.append(FoXsltConstants.TABLE_COLUMN);
						output.append(" ");
						if (i == size - 1)
						{
							output.append(FoXsltConstants.COLUMN_WIDTH
									+ "=\"0pt\"");
						} else
						{
							output
									.append(getAttribute(
											Constants.PR_COLUMN_WIDTH,
											currentcolumn
													.getAttribute(Constants.PR_COLUMN_WIDTH)));
						}
						output.append(ElementWriter.NOCHILDTAGEND);
					}
				}
			}
			TableBody header = table.getTableHeader();
			if (header != null)
			{
				output.append(ewf.getWriter(header).write(header));
			}
			TableBody footer = table.getTableFooter();
			if (footer != null)
			{
				output.append(ewf.getWriter(footer).write(footer));
			}
			ListIterator<TableBody> bodys = table.getTableBody();

			if (bodys != null)
			{
				while (bodys.hasNext())
				{
					TableBody obj = bodys.next();
					output.append(ewf.getWriter(obj).write(obj));
				}
			}
		}
		tablecellgroup.pop();
		return output.toString();
	}

	private boolean haveCellGroup()
	{
		Table table = (Table) foElement;
		TableBody header = table.getTableHeader();
		if (havaCellGroup(header))
		{
			return true;
		}
		TableBody footer = table.getTableFooter();
		if (havaCellGroup(footer))
		{
			return true;
		}
		ListIterator<TableBody> bodys = table.getTableBody();
		if (bodys != null)
		{
			while (bodys.hasNext())
			{
				TableBody body = bodys.next();
				if (havaCellGroup(body))
				{
					return true;
				}
			}
		}
		return false;
	}

	private boolean havaCellGroup(TableBody tablebody)
	{
		if (tablebody != null)
		{
			Enumeration<CellElement> rows = tablebody.children();
			if (rows.hasMoreElements())
			{
				while (rows.hasMoreElements())
				{
					TableRow row = (TableRow) rows.nextElement();
					if (havaCellGroup(row))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean havaCellGroup(TableRow tablerow)
	{
		if (tablerow != null)
		{
			Enumeration<CellElement> rows = tablerow.children();
			if (rows.hasMoreElements())
			{
				while (rows.hasMoreElements())
				{
					CellElement cell = rows.nextElement();
					if (cell.getAttribute(Constants.PR_GROUP) != null
							|| cell.getAttribute(Constants.PR_CONDTION) != null)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean getCurrentStatic()
	{
		return tablecellgroup.peek();
	}
}
