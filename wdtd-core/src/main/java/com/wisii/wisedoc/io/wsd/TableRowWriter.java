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
 * 
 */
package com.wisii.wisedoc.io.wsd;

import java.util.Enumeration;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;

/**
 * @author HP_Administrator
 * 
 */
public class TableRowWriter extends DefaultElementWriter
{
	private TableRow currentrow;
	public static String REFID = "cellref";

	public String write(CellElement element)
	{
		String returns="";
		if (element instanceof TableRow)
		{
			currentrow = (TableRow) element;
			returns =  super.write(currentrow);
			currentrow=null;
		} 
		return returns;
	}

	protected String getChildrenString(Enumeration<CellElement> children)
	{
		String returns = "";
		// 遍历子元素，生成子元素的代码
		while (children != null && children.hasMoreElements())
		{
			TableCell child = (TableCell) children.nextElement();
			if (currentrow.equals(child.getParent()))
			{
				returns = returns + ewf.getWriter(child).write(child);
			} else
			{
				returns = returns + generateRowSpanTableCell(child);
			}
		}
		return returns;
	}

	private String generateRowSpanTableCell(TableCell tablecell)
	{
		String returns = "";
		if (tablecell != null)
		{
			String elementname = getElementName(tablecell);
			String id = tablecell.getId();
			String attsrefid = SPACETAG + REFID + EQUALTAG + QUOTATIONTAG + id + QUOTATIONTAG;
			// 生成元素头代码
			returns = returns + TAGBEGIN + elementname + SPACETAG + attsrefid
					+ NOCHILDTAGEND + LINEBREAK;

		}
		return returns;
	}
}
