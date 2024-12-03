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

package com.wisii.wisedoc.view.ui.ribbon.wisedoc;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.DB_TYPE;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WiseDocument;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;

public class DataSourceBand implements WiseBand
{

	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand datasourceBand = new JRibbonBand(DB_TYPE, MediaResource
				.getResizableIcon("09379.ico"), null);

		JPanel mainpanel = new JPanel();
		mainpanel.setPreferredSize(new Dimension(110, 60));
		WiseCombobox datasource = getDataSource();
		com.wisii.wisedoc.document.Document document = SystemManager
				.getCurruentDocument();
		if (document != null)
		{
			String dbtype = document.getAttributes() == null ? null
					: (String) document.getAttributes().getAttribute(
							Constants.PR_DBTYPE);
			datasource.setSelectedItem(dbtype);
		}
		RibbonUIManager.getInstance().bind(WiseDocument.DBTYPE, datasource);
		mainpanel.add(datasource);
		JRibbonComponent mainPanelWrapper = new JRibbonComponent(mainpanel);
		datasourceBand.addRibbonComponent(mainPanelWrapper);

		return datasourceBand;
	}

	private WiseCombobox getDataSource()
	{
		String itemfiles = System.getProperty("user.dir") + File.separator
				+ "configuration" + File.separator + "DBTYPE.xml";
		WiseCombobox datasource = new WiseCombobox();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try
		{
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(itemfiles));
		} catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		} catch (SAXException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		if (doc != null)
		{
			Element root = doc.getDocumentElement();
			NodeList elements = root.getChildNodes();
			int number = elements.getLength();
			if (number > 0)
			{
				for (int i = 0; i < number; i++)
				{
					Node currentitem = elements.item(i);
					if (currentitem instanceof Element)
					{
						Element ele = (Element) currentitem;
						String name = ele.getNodeName();
						if ("name".equals(name))
						{
							String dbname = ele.getAttribute("value");
							if (dbname != null && !dbname.trim().equals(""))
							{
								datasource.addItem(dbname);
							}
						}
					}
				}
			}
		}
		return datasource;
	}
}
