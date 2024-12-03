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
 * @ButtonGroupListWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.util.List;

import javax.swing.tree.TreeNode;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.attribute.edit.Button;
import com.wisii.wisedoc.document.attribute.edit.ButtonGroup;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.wsd.DocumentWirter;
import com.wisii.wisedoc.view.dialog.ButtonNoDataNode;
import com.wisii.wisedoc.view.ui.svg.transcoded.system_log_out;
/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-4
 */
public class ButtonGroupListWriter extends AbstractAttributeWriter
{
	public static final String BUTTONGROUPLIST = "bslist";
	public static final String BUTTONGROUP = "buttongroup";
	public static final String LINESPLIT = "@l@!#,";
	public static final String DATANODESPLIT = "@@##,";
	public static final String CELLELEMENT = "cellelement";
	public static final String BUTTONS = "buttons";
	public static final String NODATANODE = "nodatanode";
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof List))
		{
			return "";
		}
		List<ButtonGroup> bglist = (List<ButtonGroup>) value;
		if (bglist.isEmpty())
		{
			return "";
		}
		String returns = "";
		returns = returns + ElementWriter.TAGBEGIN + BUTTONGROUPLIST
				+ ElementWriter.TAGEND;
		for (ButtonGroup buttongroup : bglist)
		{
			returns = returns + getbuttonGroupString(buttongroup);
		}
		returns = returns + ElementWriter.TAGENDSTART + BUTTONGROUPLIST
				+ ElementWriter.TAGEND;
		return returns;
	}

	private String getbuttonGroupString(ButtonGroup buttongroup)
	{
		String returns = "";
		CellElement goup = buttongroup.getCellment();
		if (goup == null)
		{
			return "";
		}
		Element element = goup;
		Element parent = element.getParent();
		String indexstring = "";
		while (parent != null)
		{
			int index = parent.getIndex(element);
			if (index < 0)
			{
				return "";
			}
			if (indexstring.equals(""))
			{
				indexstring = indexstring + index;
			} else
			{
				indexstring = index + "," + indexstring;
			}
			element = parent;
			parent = parent.getParent();
		}
		if (!(element instanceof Document))
		{
			return "";
		}
		List<Button> buttons = buttongroup.getButtons();
		String buttonstring = "";
		String ds = "";
		boolean aaa = true;
		for (Button button : buttons)
		{
			String bs = button.getType();
			String aut = button.getAuty();
			ButtonNoDataNode dataContent = button.getDataContent();
			if("addafter".equals(bs) || "addbefore".equals(bs)){
				if(dataContent!=null){
					if(aaa){
						List contents = dataContent.getContents();
						for (Object content : contents) {
							if (content instanceof BindNode)
							{
								String dataNodeID = DocumentWirter.getDataNodeID((BindNode)content);
								if("".equals(ds)){
									ds = ds + dataNodeID;
								}else{
									ds = ds + DATANODESPLIT + dataNodeID;
								}
							}
						}
					}
				}
			}
			if (aut != null && !aut.isEmpty())
			{
				bs = bs + TEXTSPLIT + aut;
			}
			if (buttonstring.equals(""))
			{
				buttonstring = buttonstring + bs;
			} else
			{
				buttonstring = buttonstring + LINESPLIT + bs;
			}
			aaa=false;
		}
		returns = returns + ElementWriter.TAGBEGIN + BUTTONGROUP;
		returns = returns + SPACETAG + CELLELEMENT + EQUALTAG + QUOTATIONTAG
				+ indexstring + QUOTATIONTAG;
		
		
		returns = returns + SPACETAG + NODATANODE + EQUALTAG + QUOTATIONTAG
				 + ds + QUOTATIONTAG ;
		
		returns = returns + SPACETAG + BUTTONS + EQUALTAG + QUOTATIONTAG
				+ buttonstring + QUOTATIONTAG + ElementWriter.NOCHILDTAGEND;
		return returns;
	}

}
