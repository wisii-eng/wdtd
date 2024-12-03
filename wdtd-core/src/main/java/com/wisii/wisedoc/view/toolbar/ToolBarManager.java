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
 * @ToolBarManager.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.toolbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.util.WisedocUtil;



/**
 * 类功能描述：创建工具栏
 * 
 * 作者：李晓光 创建日期：2008-08-22
 */
public class ToolBarManager
{
	/**
	 * 指定配置文件
	 */
	private static final String fileName = "com/wisii/wisedoc/view/toolbar/configure/WiseDoc_ToolBar.xml";

	/* 初始化时工具栏用配置文件 */
//	private static final String startupFileName = "com/wisii/wisedoc/toolbar/Wisedoc_Startup_ToolBar.xml";

	/**
	 * 指定Action集合
	 */
//	private static final ActionManager actionManager = ActionManager
//			.getInstance();

	public static final String STARTUP = "startup";

	public static final String NORMAL = "normal";

	public static Map<String, JComponent> TOOLBARLIST = new HashMap<String, JComponent>();
	static
	{
//		TOOLBARLIST.put(STARTUP, createToolBarPanel(startupFileName));
		TOOLBARLIST.put(NORMAL, createToolBarPanel(fileName));
	}

	enum XmlElement
	{
		toolbar, floatable, name, separator, item, icon, tooltip, action, param, yes, no, kind, General, Insert, Toogle, Location
	}

	enum Location
	{
		Top, Bottom
	}

	/**
	 * 
	 * 根据默认的配置文件，返回设置的工具栏的JPanel
	 * 
	 * @return JPanel 根据配置文件配置的ToolBar并将ToolBar加载到JPanel上返回。
	 * @exception 指定的文件不存在、对xml文件的解析错误等时产生异常。
	 */
	public static JComponent createToolBarPanel()
	{
		JComponent comp = null;
//		if (LayoutManager.getCurrentLayout() == null)
//			comp = TOOLBARLIST.get(STARTUP);
//		else
			comp = TOOLBARLIST.get(NORMAL);
		return comp;
	}

	/**
	 * 
	 * 根据指定的配置文件信息，返回加载了工具栏的JPanel
	 * 
	 * @param fileName
	 *            指定配置文件所在的位置。
	 * @return JPanel 根据配置文件配置的ToolBar并将ToolBar加载到JPanel上返回。
	 */
	public static JComponent createToolBarPanel(String fileName)
	{
		Reader reader = null;
		try
		{
			reader = WisedocUtil.getXmlReader(fileName);

			return (JPanel) readFromXML(reader);
		} catch (FileNotFoundException e)
		{

		} catch (IOException e)
		{

		} catch (SAXException e)
		{

		} catch (ParserConfigurationException e)
		{

		}
		return null;
	}

	/**
	 * 
	 * 将生成的工具栏加载到JPnale上
	 * 
	 * @param reader
	 *            配置文件的读取流
	 * @return JPanel {返回参数说明}
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	private static JComponent readFromXML(Reader reader) throws SAXException,
			ParserConfigurationException, IOException
	{
		Element root = WisedocUtil.getXmlElement(reader);

		JPanel result = new JPanel(new BorderLayout(0, 0));
		JPanel second = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		for (int index = 0; index < root.getChildNodes().getLength(); index++)
		{
			Node itemNode = root.getChildNodes().item(index);
			Node location = null;
			if (itemNode.hasAttributes())
			{
				location = itemNode.getAttributes().getNamedItem(
						XmlElement.Location.name());				
			}
			if (XmlElement.toolbar.name().equalsIgnoreCase(
					itemNode.getNodeName()))
			{
				if (location == null
						|| location.getNodeValue().equalsIgnoreCase(
								Location.Top.name()))
				{
					toolbarPanel.add(loadToolBar(itemNode)); // loadToolBarToPanel
					
				} else if (location.getNodeValue().equalsIgnoreCase(
						Location.Bottom.name()))
				{
					second.add(loadToolBar(itemNode)); // loadToolBar(itemNode)
				}
			}
		}
		result.add(toolbarPanel, BorderLayout.NORTH);
		result.add(second, BorderLayout.CENTER);

		return result;
	}

	/**
	 * 
	 * 根据配置文件的一个ToolBar节点，生成相应的工具栏
	 * 
	 * @param child
	 *            工具栏节点
	 * @return JToolBar 根据配置文件中Toolbar节点的信息，产生相应的工具栏。
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	private static JToolBar loadToolBar(Node child)
	{
		JToolBar toolBar = null;
		if (!child.hasChildNodes())
			return toolBar;
		toolBar = new JToolBar();
		NamedNodeMap nodeMap = child.getAttributes();
		if (nodeMap != null)
		{
			Node namePro = nodeMap.getNamedItem(XmlElement.name.name());
			if (namePro != null && namePro.getNodeValue() != "")
			{
				toolBar.setName(MessageResource.getMessage(namePro
						.getNodeValue()));
			}
			Node floatablePro = child.getAttributes().getNamedItem(
					XmlElement.floatable.name());
			if (floatablePro != null
					&& XmlElement.yes.name().equalsIgnoreCase(
							floatablePro.getNodeValue()))
			{
				toolBar.setFloatable(true);
			} else
			{
				toolBar.setFloatable(false);
			}
		}
		for (int i = 0; i < child.getChildNodes().getLength(); i++)
		{
			Node itemNode = child.getChildNodes().item(i);
			if (XmlElement.item.name().equalsIgnoreCase(itemNode.getNodeName()))
			{
				AbstractButton toolbarButton;
				if (itemNode.getAttributes().getNamedItem(
						XmlElement.kind.name()) != null
						&& XmlElement.Toogle.name().equalsIgnoreCase(
								itemNode.getAttributes().getNamedItem(
										XmlElement.kind.name()).getNodeValue()))
				{
					toolbarButton = new WiseDocToolBarToggleButton();
				} else
				{
					toolbarButton = new WiseDocToolBarButton();
				}

				initToolBarButton(toolbarButton, itemNode);
				toolBar.add(toolbarButton);
				toolbarButton.updateUI();
			} else if (XmlElement.separator.name().equalsIgnoreCase(
					itemNode.getNodeName()))
			{
				JSeparator separator = new javax.swing.JToolBar.Separator(
						new Dimension(4, 24));
				separator.setBorder(BorderFactory.createEtchedBorder(1));
				toolBar.add(separator);
			}
		}
		toolBar.setMargin(new Insets(0, 0, 0, 0));
		toolBar.setRollover(false);
		return toolBar;
	}

	/**
	 * 
	 * 根据工具栏的配置文件，加载工具栏到一个Panel上
	 * 
	 * @param child
	 *            配置文件中工具栏节点
	 * @return JPanel 加载了工具栏的Panel
	 */
	private static JPanel loadToolBarToPanel(Node child)
	{
		JToolBar toolBar = null;
		JPanel panel = null;

		if (!child.hasChildNodes())
			return panel;

		panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		toolBar = loadToolBar(child);

		panel.add(toolBar);

		return panel;
	}

	/**
	 * 
	 * 加载xml文件中配置的工具栏按钮属性信息。
	 * 
	 * @param toolBarButton
	 *            工具栏上的一个按钮
	 * @param itemNode
	 *            工具栏中按钮在配置文件中的节点
	 * @return void
	 */
	private static void initToolBarButton(AbstractButton toolBarButton,
			Node itemNode)
	{
		if (itemNode == null || !itemNode.hasAttributes())
			return;

		NamedNodeMap nodeMap = itemNode.getAttributes();
		for (int index = 0; index < nodeMap.getLength(); index++)
		{
			String nodeName = nodeMap.item(index).getNodeName();
			String nodeValue = nodeMap.item(index).getNodeValue();
			if (XmlElement.action.name().equalsIgnoreCase(nodeName))
			{
				Action action = null;//actionManager.getAction(nodeValue);
				// (Action) WiseDocUtil.newInstance(nodeValue);
				toolBarButton.setAction(action);
			} else if (XmlElement.name.name().equalsIgnoreCase(nodeName))
			{
				if (nodeValue != "")
					toolBarButton
							.setText(MessageResource.getMessage(nodeValue));
			} else if (XmlElement.icon.name().equalsIgnoreCase(nodeName))
			{
				ImageIcon icon = MediaResource.getImageIcon(nodeValue);
				if (icon != null)
					toolBarButton.setIcon(icon);
			} else if (XmlElement.tooltip.name().equalsIgnoreCase(nodeName))
			{
				toolBarButton.setToolTipText(MessageResource
						.getMessage(nodeValue));
			}
		}
	}
}
