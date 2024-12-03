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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class TableContentsWriter extends AbsSimpleElementWriter
{

	// public static String indexcode = "";

	List<Group> grouplist = new ArrayList<Group>();

	public TableContentsWriter(CellElement element)
	{
		foElement = ElementUtil.getXSLTableContens(SystemManager
				.getCurruentDocument(), (TableContents) element);
		setAttributemap();
	}

	@Override
	public String getElementName()
	{
		return FoXsltConstants.BLOCK_CONTAINER;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (foElement != null && foElement.children() != null)
		{
			Enumeration<CellElement> listobj = foElement.children();
			while (listobj.hasMoreElements())
			{
				CellElement obj = listobj.nextElement();
				if (obj instanceof Block)
				{
					output.append(new SimpleBlockWriter(obj).write(obj));
				}
			}
		}
		return output.toString();
	}

	public String write(CellElement element)
	{
		// if (TableContentsWriter.indexcode.equals(""))
		// {
		return getCode();
		// } else
		// {
		// return TableContentsWriter.indexcode;
		// }
	}

	public String getCode()
	{
		setGrouplist();
		IoXslUtil.clearXpath();
		StringBuffer code = new StringBuffer();
		code.append(getHeaderElement());
		code.append(getChildCode());
		code.append(getEndElement());
		getGrouplist();
		return code.toString();
	}

	public void getGrouplist()
	{
		for (Group current : grouplist)
		{
			IoXslUtil.addXpath(current);
		}
	}

	public void setGrouplist()
	{
		List<Group> linshi = IoXslUtil.getXpath();
		for (Group current : linshi)
		{
			grouplist.add(current);
		}
	}
}
