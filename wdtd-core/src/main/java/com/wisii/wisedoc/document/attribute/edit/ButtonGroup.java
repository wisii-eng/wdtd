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

package com.wisii.wisedoc.document.attribute.edit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.xsl.attribute.edit.EditUI;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.view.dialog.ButtonNoDataNode;

public class ButtonGroup
{

	CellElement cellment;

	List<Button> buttons = new ArrayList<Button>();;

	private List<Integer> cellindexs;

	public ButtonGroup(CellElement obj)
	{
		cellment = obj;
	}

	public ButtonGroup(List<Integer> cellindexs, List<Button> buttons)
	{
		this.cellindexs = cellindexs;
		if (buttons != null)
		{
			this.buttons.addAll(buttons);
		}
	}

	public CellElement getCellment()
	{
		return cellment;
	}

	public void setCellment(CellElement cellment)
	{
		cellindexs = null;
		this.cellment = cellment;
	}

	public List<Button> getButtons()
	{
		return new ArrayList<Button>(buttons);
	}

	public void setButtons(List<Button> buttons)
	{
		this.buttons = buttons;
	}

	/**
	 * @返回 cellindexs变量的值
	 */
	public final List<Integer> getCellindexs()
	{
		return cellindexs;
	}

//	public String toStringHaveUI()
//	{
//		String result = "";
//
//		if (buttons != null && buttons.size() > 0)
//		{
//			
//			for (int i = 0; i < buttons.size(); i++)
//			{
//				Button current = buttons.get(i);
//				Map<Integer, Object> editmap = new HashMap<Integer, Object>();
//				String type = current.getType();
//				if (type.equals("addbefore"))
//				{
//					editmap.put(Constants.PR_BUTTON_TYPE, "add");
//					editmap.put(Constants.PR_BUTTON_INSERT_POSITION, "before");
//				} else if (type.equals("addafter"))
//				{
//					editmap.put(Constants.PR_BUTTON_TYPE, "add");
//					editmap.put(Constants.PR_BUTTON_INSERT_POSITION, "after");
//				} else if (type.equals("delete"))
//				{
//					editmap.put(Constants.PR_BUTTON_TYPE, type);
//				}
//
//				EditUI currentui = new EditUI(new EnumProperty(
//						Constants.EN_BUTTON, ""), editmap);
//				String edituiname = IoXslUtil.addEditUI(currentui);
//
//				if (i > 0)
//				{
//					result = result + ",";
//				}
//				result = result + "',";
//				result = result + edituiname;
//				result = result + "('";
//				result = result + ",";
//
//				Group group = (Group) this.getCellment().getAttribute(
//						Constants.PR_GROUP);
//				result = result
//						+ IoXslUtil.getButtonAbsoluteXPath(this.getCellment());
//				result = result + ",'";
//				String before = current.getAuty();
//				if (!"".equals(before))
//				{
//					result = result + ",";
//				}
//				result = result + before;
//				result = result + ")'";
//				NameSpace wdwens = new NameSpace(FoXsltConstants.SPACENAMEWDWE,
//						FoXsltConstants.WDWE);
//				IoXslUtil.addNameSpace(wdwens);
//			}
//		}
//
//		return result;
//	}

	public String toStringNoId()
	{
		String result = "";

		if (buttons != null && buttons.size() > 0)
		{
			String xpath = IoXslUtil.getButtonAbsoluteXPath(this.getCellment());
			if (!"".equals(xpath))
			{
				for (int i = 0; i < buttons.size(); i++)
				{
					Button current = buttons.get(i);
					Map<Integer, Object> editmap = new HashMap<Integer, Object>();
					String type = current.getType();
					String nodataxpth="";
					if (type.startsWith("add")) {
						ButtonNoDataNode dataContent = current.getDataContent();
						List contents = null;
						if (dataContent != null) {
							contents = dataContent.getContents();
							for (Object object : contents) {
								if (object instanceof BindNode) {
									String xPath2 = ((BindNode) object).getXPath();
									if ("".equals(nodataxpth)) {
										nodataxpth = nodataxpth + xPath2;
									} else {
										nodataxpth = nodataxpth + "," + xPath2;
									}
								}
							}
						}
					}
					if (type.equals("addbefore"))
					{
						editmap.put(Constants.PR_BUTTON_TYPE, "add");
						editmap.put(Constants.PR_BUTTON_INSERT_POSITION,
								"before");
						editmap.remove(Constants.PR_BUTTON_NODATAXPTH);
						editmap.put(Constants.PR_BUTTON_NODATAXPTH, nodataxpth);
					} else if (type.equals("addafter"))
					{
						editmap.put(Constants.PR_BUTTON_TYPE, "add");
						editmap.put(Constants.PR_BUTTON_INSERT_POSITION,
								"after");
						editmap.remove(Constants.PR_BUTTON_NODATAXPTH);
						editmap.put(Constants.PR_BUTTON_NODATAXPTH, nodataxpth);
					} else if (type.equals("delete"))
					{
						editmap.put(Constants.PR_BUTTON_TYPE, type);
					}
					EditUI currentui = new EditUI(new EnumProperty(
							Constants.EN_BUTTON, ""), editmap);
					String edituiname = IoXslUtil.addEditUI(currentui);

					if (i > 0 && !"".equals(result))
					{
						result = result + ",',',";
					}
					result = result + "'" + edituiname;
					result = result + "('";
					result = result + ",";
					result = result + xpath;
					String before = current.getAuty();
					if (!"".equals(before))
					{
						result = result + ",',','";
						result = result + before;
						result = result + "'";
					}
					result = result + ",')'";
					NameSpace wdwens = new NameSpace(
							FoXsltConstants.SPACENAMEWDWE, FoXsltConstants.WDWE);
					IoXslUtil.addNameSpace(wdwens);
				}
			}
		}

		return result;
	}

	public void addButton(Button button)
	{
		if (buttons == null)
		{
			buttons = new ArrayList<Button>();
		}
		buttons.add(button);
	}

	public void removeButton(Button button)
	{
		if (buttons != null && !buttons.isEmpty())
		{
			buttons.remove(button);
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
		result = prime * result + ((buttons == null) ? 0 : buttons.hashCode());
		result = prime * result
				+ ((cellment == null) ? 0 : cellment.hashCode());
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
		ButtonGroup other = (ButtonGroup) obj;
		if (buttons == null)
		{
			if (other.buttons != null)
				return false;
		} else if (!buttons.equals(other.buttons))
			return false;
		if (cellment == null)
		{
			if (other.cellment != null)
				return false;
		} else if (!cellment.equals(other.cellment))
			return false;
		return true;
	}

}
