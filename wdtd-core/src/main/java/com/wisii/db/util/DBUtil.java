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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wisii.db.ConnectionSeting;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.view.data.DriverInfomation;

public class DBUtil
{

	private static String path = System.getProperty("user.dir")
			+ File.separator + "configuration" + File.separator
			+ "datasource.xml";

	private static Map<String, ConnectionSeting> connectionsetings = null;

	private static ConnectionSeting defaultconnectionseting = null;

	private static void initConnectionSeting()
	{
		Map<String, ConnectionSeting> cons = new HashMap<String, ConnectionSeting>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try
		{
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(path));
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
					Node current = elements.item(i);
					if (current instanceof Element)
					{
						NodeList items = current.getChildNodes();
						int size = items.getLength();
						if (size > 0)
						{
							String dbname = "";
							String datype = "";
							String url = "";
							String user = "";
							String password = "";
							for (int j = 0; j < size; j++)
							{
								Node currentitem = items.item(j);
								if (currentitem instanceof Element)
								{
									Element ele = (Element) currentitem;
									String name = ele.getNodeName();
									if ("name".equals(name))
									{
										dbname = ele.getAttribute("value");
									} else if ("url".equals(name))
									{
										url = ele.getAttribute("value");
									} else if ("type".equals(name))
									{
										datype = ele.getAttribute("value");
									} else if ("user".equals(name))
									{
										user = ele.getAttribute("value");
									} else if ("ps".equals(name))
									{
										password = ele.getAttribute("value");
									}
								}
							}
							if (!"".equals(dbname))
							{
								ConnectionSeting currentcs = new ConnectionSeting(
										dbname, datype, url, user, password);
								cons.put(dbname, currentcs);
							}
						}
					}
				}
			}
		}
		setConnectionSetings(cons);
	}

	public static ConnectionSeting getConnectionSeting(String csname)
	{
		Map<String, ConnectionSeting> cons = getConnectionSetings();
		if (cons != null && !cons.isEmpty() && cons.containsKey(csname))
		{
			ConnectionSeting currentcs = cons.get(csname);
			if (currentcs != null)
			{
				return currentcs;
			}
		}
		return getDefaultcs();
	}

	public static Map<String, ConnectionSeting> getConnectionSetings()
	{
		if (connectionsetings == null)
		{
			initConnectionSeting();

		}
		return connectionsetings;
	}

	public static void setConnectionSetings(
			Map<String, ConnectionSeting> connectionSetings)
	{
		connectionsetings = connectionSetings;
	}

	public static void addConnectionSetings(String name,
			ConnectionSeting connectionSeting)
	{
		Map<String, ConnectionSeting> connectionSetings = getConnectionSetings();
		connectionSetings.put(name, connectionSeting);
	}

	public static void writeConnectionSetings()
	{
		Map<String, ConnectionSeting> connectionSetings = getConnectionSetings();
		if (connectionSetings != null)
		{
			connectionSetings = new HashMap<String, ConnectionSeting>();
			try
			{
				FileOutputStream outstream = new FileOutputStream(path);
				StringBuffer content = new StringBuffer();
				content.append("<DataSources>");
				Map<String, ConnectionSeting> csmap = getConnectionSetings();
				if (csmap != null && !csmap.isEmpty())
				{
					Object[] keys = csmap.keySet().toArray();
					for (Object current : keys)
					{
						ConnectionSeting setting = csmap.get(current);
						if (setting != null)
						{
							String name = setting.getName();
							String type = setting.getDriverclassType();
							String user = setting.getUsername();
							String password = setting.getPassword();
							String url = setting.getUrl();
							content.append("<DS>");

							content.append("<name ");
							content.append("value=\"" + name + "\"");
							content.append("/>");

							content.append("<type ");
							content.append("value=\"" + type + "\"");
							content.append("/>");

							content.append("<url ");
							content.append("value=\"" + url + "\"");
							content.append("/>");

							content.append("<user ");
							content.append("value=\"" + user + "\"");
							content.append("/>");

							content.append("<ps ");
							content.append("value=\"" + password + "\"");
							content.append("/>");

							content.append("</DS>");
						}
					}
				}

				content.append("</DataSources>");
				if (content != null && !content.equals(""))
				{
					byte[] bts = content.toString().getBytes("UTF-8");
					outstream.write(IOUtil.encrypt(bts));
					outstream.flush();
				}
				outstream.close();
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (UnsupportedEncodingException e)
			{
				WiseDocOptionPane.showMessageDialog(SystemManager
						.getMainframe(), MessageResource
						.getMessage(MessageConstants.FILE
								+ MessageConstants.FILESAVEFAILED),
						MessageResource
								.getMessage(MessageConstants.DIALOG_COMMON
										+ MessageConstants.INFORMATIONTITLE),
						WiseDocOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static List<String> getDataSourceNames()
	{
		List<String> lists = new ArrayList<String>();
		Map<String, ConnectionSeting> csmap = getConnectionSetings();
		if (csmap != null && !csmap.isEmpty())
		{
			Object[] keys = csmap.keySet().toArray();
			for (Object current : keys)
			{
				lists.add(current.toString());
			}
		}
		return lists;
	}

	public static List<String> getAvailableDataSourceNames()
	{
		
//		List<String> lists = new ArrayList<String>();
//		Map<String, ConnectionSeting> csmap = getConnectionSetings();
//		if (csmap != null && !csmap.isEmpty())
//		{
//			Object[] keys = csmap.keySet().toArray();
//			for (Object current : keys)
//			{
//				if (testConnection(csmap.get(current)))
//				{
//					lists.add(current.toString());
//				}
//			}
//		}
		return getDataSourceNames();
	}

	public static boolean testConnection(ConnectionSeting cs)
	{
		if (cs != null)
		{
			String driverclass = DriverTypeUtil.getDriverClassName(cs
					.getDriverclassType());
			try
			{
				Class.forName(driverclass);
			} catch (ClassNotFoundException e1)
			{
				return false;
			}
			String url = cs.getUrl();
			String username = cs.getUsername();
			String password = cs.getPassword();
			Connection con = null;
			try
			{
				con = DriverManager.getConnection(url, username, password);
			} catch (SQLException e)
			{
				LogUtil.debugException(MessageResource
						.getMessage(MessageConstants.DRIVERNOTFOUND), e);
				return false;
			}
			return con != null;
		}
		return false;
	}

	public static ConnectionSeting getDefaultcs()
	{
		if (defaultconnectionseting == null)
		{
			String datype = "";
			String url = "";
			DriverInfomation defaultinfo = DriverTypeUtil
					.getDefaultdriverinfomation();
			if (defaultinfo != null)
			{
				datype = defaultinfo.getDatabasetype();
				url = defaultinfo.getConnectionurl();
			}
			setDefaultcs(new ConnectionSeting("", datype, url, "", ""));
		}
		return defaultconnectionseting;
	}

	public static void setDefaultcs(ConnectionSeting defaultcs)
	{
		DBUtil.defaultconnectionseting = defaultcs;
	}
}
