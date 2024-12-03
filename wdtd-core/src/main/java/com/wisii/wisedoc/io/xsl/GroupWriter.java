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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.svg.Canvas;

public class GroupWriter extends BlockWriter
{

	public GroupWriter(CellElement element)
	{
		super(element);
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
					if (obj instanceof Canvas || obj instanceof Group
							|| obj instanceof Inline)
					{
						if (obj instanceof Canvas)
						{
							output.append(outputListCondition(inlinelist));
							inlinelist.clear();
							output.append(new CanvasWriter((Canvas) obj)
									.write());
						} else if (obj instanceof Group)
						{
							output.append(outputListCondition(inlinelist));
							inlinelist.clear();
							output.append(new GroupWriter(obj).write(obj));
						} else
						{
							inlinelist.add((Inline) obj);
						}
					} else
					{
						if (obj instanceof Block)
						{
							output.append(new BlockWriter(obj).write(obj));
						} else if (obj instanceof ExternalGraphic)
						{
							output.append(new ExternalGraphicWriter(obj)
									.write(obj));
						} else if (obj instanceof QianZhang)
						{
							output.append(new QianZhangWriter(obj)
									.write(obj));
						} else if (obj instanceof BarCode)
						{
							output.append(new InstreamForeignObjectWriter(obj)
									.write(obj));
						} else if (obj instanceof TableCell)
						{
							if (obj.getParent().equals(foElement))
							{
								output.append(ewf.getWriter(obj).write(obj));
							} else
							{
								Map<Integer, Object> map = new HashMap<Integer, Object>();
								map.put(Constants.PR_ID, obj.getId());
								Attributes attributes = new Attributes(map);
								TableCell linshiCell = new TableCell(attributes
										.getAttributes());
								linshiCell.setParent(foElement);
								output.append(ewf.getWriter(linshiCell).write(
										linshiCell));
							}
						} else
						{
							output.append(ewf.getWriter(obj).write(obj));
						}
					}
				}
				output.append(outputListCondition(inlinelist));
			}
		}
		return output.toString();
	}
}
