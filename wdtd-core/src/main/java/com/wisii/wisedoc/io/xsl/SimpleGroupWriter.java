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

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Group;

public class SimpleGroupWriter extends BlockWriter
{

	public SimpleGroupWriter(CellElement element)
	{
		super(element);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getElementName()
	{
		return "";
	}

	public String write(CellElement element)
	{
		StringBuffer code = new StringBuffer();
		code.append(beforeDeal());
		code.append(getChildCode());
		code.append(endDeal());
		return code.toString();
	}

	@SuppressWarnings("unchecked")
	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (foElement != null
				&& (foElement.children() != null || foElement.getChildNodes() != null))
		{
			Enumeration<CellElement> listobj = foElement.children();

			if (listobj.hasMoreElements())
			{
				List<CellElement> inlinelist = new ArrayList<CellElement>();
				while (listobj.hasMoreElements())
				{
					CellElement obj = listobj.nextElement();
					if (obj instanceof Group)
					{
						output.append(new SimpleGroupWriter(obj).write(obj));
					} else
					{
						if (obj instanceof Block)
						{
							output
									.append(new SimpleBlockWriter(obj)
											.write(obj));
						}
					}
				}
				output.append(outputListCondition(inlinelist));
			}
		}
		return output.toString();
	}
}
