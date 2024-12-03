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
import java.util.ListIterator;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

public class TableRowWriter extends AbsElementWriter
{

	public TableRowWriter(CellElement element)
	{
		super(element);
	}

	@Override
	public String getElementName()
	{
		return FoXsltConstants.TABLE_ROW;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (foElement != null && foElement.children() != null)
		{

			if (TableWriter.getCurrentStatic())
			{
				Enumeration<CellElement> listobj = foElement.children();
				if (listobj.hasMoreElements())
				{
					while (listobj.hasMoreElements())
					{
						CellElement obj = listobj.nextElement();
						if (obj.getParent().equals(foElement))
						{
							output.append(ewf.getWriter(obj).write(obj));
						} else
						{
							output.append(new TableCellWriter(obj).write(obj));
						}
					}
				}
			} else
			{
				ListIterator childrens = foElement.getChildNodes();
				if (childrens != null)
				{
					while (childrens.hasNext())
					{
						CellElement obj = (CellElement) childrens.next();
						output.append(ewf.getWriter(obj).write(obj));
					}
				}
			}
		}
		return output.toString();
	}
}
