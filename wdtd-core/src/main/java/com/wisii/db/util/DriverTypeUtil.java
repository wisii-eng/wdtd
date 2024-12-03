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

package com.wisii.db.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.view.data.DriverInfomation;

public class DriverTypeUtil
{

	private static Map<String, DriverInfomation> driverinfos = null;

	private static DriverInfomation defaultdriverinfomation = null;

	public static String getDriverClassName(String type)
	{
		Map<String, DriverInfomation> driverclasss = getDrivers();
		if (driverclasss != null)
		{
			DriverInfomation drin = driverclasss.get(type);
			if (drin != null)
			{
				return drin.getDriverclass();
			}
		}
		return "";
	}

	public static DriverInfomation getDriverInfomation(String type)
	{
		Map<String, DriverInfomation> driverclasss = getDrivers();
		if (driverclasss != null)
		{
			DriverInfomation current = driverclasss.get(type);
			if (current != null)
			{
				return current;
			}
		}
		return getDefaultdriverinfomation();
	}

	private static void initDriverClass()
	{
		Map<String, DriverInfomation> driverclasss = new HashMap<String, DriverInfomation>();
		driverclasss = new HashMap<String, DriverInfomation>();
		String rootpath = System.getProperty("user.dir") + File.separator
				+ "configuration" + File.separator + "dbdrivers.xml";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document document = null;
		try
		{
			builder = factory.newDocumentBuilder();
			document = builder.parse(new File(rootpath));
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
		if (document != null)
		{
			NodeList elements = document.getElementsByTagName("driver");
			int number = elements.getLength();
			if (number > 0)
			{
				for (int i = 0; i < number; i++)
				{
					Node current = elements.item(i);
					if (current instanceof Element)
					{
						NodeList items = current.getChildNodes();
						int size = items.getLength();
						if (size > 0)
						{
							String datype = "";
							String url = "";
							String driverclass = "";
							for (int j = 0; j < size; j++)
							{
								Node currentitem = items.item(j);
								if (currentitem instanceof Element)
								{
									Element ele = (Element) currentitem;
									String name = ele.getNodeName();
									if ("name".equals(name))
									{
										datype = ele.getAttribute("value");
									} else if ("url".equals(name))
									{
										url = ele.getAttribute("value");
									} else if ("classname".equals(name))
									{
										driverclass = ele.getAttribute("value");
									}
								}
								if (!"".equals(datype))
								{
									DriverInfomation currentdi = new DriverInfomation(
											datype, url, driverclass);
									driverclasss.put(datype, currentdi);
									if (i == 0)
									{
										setDefaultdriverinfomation(currentdi);
									}
								}
							}
						}
					}
				}
			}
		}
		setDrivers(driverclasss);
	}

	public static Map<String, DriverInfomation> getDrivers()
	{
		if (driverinfos == null)
		{
			initDriverClass();
		}
		return driverinfos;
	}

	public static void setDrivers(Map<String, DriverInfomation> drivers)
	{
		DriverTypeUtil.driverinfos = drivers;
	}

	public static DriverInfomation getDefaultdriverinfomation()
	{
		if (defaultdriverinfomation == null)
		{
			initDriverClass();
			if (defaultdriverinfomation == null)
			{
				return new DriverInfomation("", "", "");
			}
		}
		return defaultdriverinfomation;
	}

	public static void setDefaultdriverinfomation(
			DriverInfomation defaultdriverinfomation)
	{
		DriverTypeUtil.defaultdriverinfomation = defaultdriverinfomation;
	}
}
