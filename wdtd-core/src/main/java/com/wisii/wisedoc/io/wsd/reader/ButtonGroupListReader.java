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
 * @GroupButtonListReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.edit.Button;
import com.wisii.wisedoc.document.attribute.edit.ButtonGroup;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.Reader;
import com.wisii.wisedoc.io.wsd.WSDAttributeIOFactory;
import com.wisii.wisedoc.io.wsd.attribute.ButtonGroupListWriter;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.nio.WSDIOUtil;
import com.wisii.wisedoc.swing.basic.WSDData;
import com.wisii.wisedoc.view.dialog.ButtonNoDataNode;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-4
 */
public class ButtonGroupListReader extends AbstractHandler
{
	private List<List<ButtonGroup>> buttongrouplists=new ArrayList<List<ButtonGroup>>();
	List<ButtonGroup> buttongroups;
	private static Document DOC;
	void init()
	{
		buttongrouplists = new ArrayList<List<ButtonGroup>>();
	}

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (ButtonGroupListWriter.BUTTONGROUPLIST.equals(qname))
		{
			buttongroups = new ArrayList<ButtonGroup>();
		} else if (ButtonGroupListWriter.BUTTONGROUP.equals(qname))
		{
			String cellstring = atts
					.getValue(ButtonGroupListWriter.CELLELEMENT);
			if (cellstring != null)
			{
				String[] indexs = cellstring.split(",");
				List<Integer> eindexs = new ArrayList<Integer>();
				try
				{
					for (String is : indexs)
					{
						eindexs.add(Integer.parseInt(is));
					}
				} catch (Exception e)
				{
					LogUtil.debugException("解析BUTTONGROUP出错", e);
				}
				String buttonstring = atts
						.getValue(ButtonGroupListWriter.BUTTONS);
				if (buttonstring != null)
				{
					String[] bses = buttonstring
							.split(ButtonGroupListWriter.LINESPLIT);
					List<Button> buttons = new ArrayList<Button>();
					for (String bs : bses)
					{
						String[] bus = bs
								.split(ButtonGroupListWriter.TEXTSPLIT);
						String type = bus[0];
						String aut = null;
						if (bus.length == 2)
						{
							aut = bus[1];
						}
						String datastr = atts.getValue(ButtonGroupListWriter.NODATANODE);
						String[] datas = datastr.split(ButtonGroupListWriter.DATANODESPLIT);
						Map<String, BindNode> nodemap = WsdReaderHandler.getNodemap();
						List a = new ArrayList();
						for (int i = 0; i < datas.length; i++) {
							BindNode bindNode = nodemap.get(datas[i]);
							a.add(bindNode);
						}
						ButtonNoDataNode n = new ButtonNoDataNode(null, a);
						
						buttons.add(new Button(type, aut,n));
					}
					buttongroups.add(new ButtonGroup(eindexs, buttons));
				}
			}
		} else
		{
			throw new SAXException("chartdatalists 节点下的" + qname + "节点为非法");
		}
	}

	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (ButtonGroupListWriter.BUTTONGROUPLIST.equals(qname))
		{
			if (buttongroups != null && !buttongroups.isEmpty())
			{
				buttongrouplists.add(buttongroups);
			} else
			{
				buttongrouplists.add(null);
			}
			buttongroups = null;
		}
	}

	public List<List<ButtonGroup>> getObject()
	{
		return buttongrouplists;
	}

}
