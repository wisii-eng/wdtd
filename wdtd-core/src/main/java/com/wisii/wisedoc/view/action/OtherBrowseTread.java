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

package com.wisii.wisedoc.view.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.wisii.db.ConnectionSeting;
import com.wisii.db.model.SQLItem;
import com.wisii.db.model.Setting;
import com.wisii.db.model.SqlItemFactory;
import com.wisii.db.reader.XMLGenerate;
import com.wisii.db.reader.ZhuMobanXMLGenerate;
import com.wisii.db.util.DBUtil;
import com.wisii.db.util.DriverTypeUtil;
import com.wisii.exception.ZimobanFileNotFoundException;
import com.wisii.io.zhumoban.MainXSLWriter;
import com.wisii.io.zhumoban.FixedFileUtil.WSDXKind;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.xsl.XslWrite;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.SetParamDialog;

public class OtherBrowseTread extends Thread
{

	File xsl;

	File xml;

	public OtherBrowseTread()
	{
	}

	public void run()
	{
		List<String> params = new ArrayList<String>();
		File xsl;
		try
		{
			xsl = File.createTempFile("xsl", ".tmp");
			List<Setting> sqls = new ArrayList<Setting>();
			Map<String, String> parammapresult = new HashMap<String, String>();
			Document doc = SystemManager.getCurruentDocument();
			DataStructureTreeModel model = doc.getDataStructure();
			com.wisii.db.Setting docsetting = model != null ? model
					.getDbsetting() : null;
			Setting setting = docsetting != null ? new Setting(docsetting
					.getSqlitem(), null) : null;
			Connection con = null;
			sqls.add(setting);
			boolean havazmb = ifHaveZimoban();
			if (havazmb)
			{
				deal(doc, xsl.getName(), new FileOutputStream(xsl), sqls);
				for (Setting currentsql : sqls)
				{
					getParammap(generateDBSetting(currentsql), params);
				}
			} else
			{
				getParammap(generateDBSetting(setting), params);
				new XslWrite().write(new FileOutputStream(xsl), doc);
			}
			Map<String, String> parammap = new HashMap<String, String>();

			Map<String, String> beforeparammap = SetParamDialog
					.getBeforeParams();
			boolean flg = beforeparammap != null && !beforeparammap.isEmpty();
			for (String current : params)
			{
				if (flg && beforeparammap.containsKey(current))
				{
					parammap.put(current, beforeparammap.get(current));
				} else
				{
					parammap.put(current, "");
				}
			}
			String dsname = SetParamDialog.getDatasourcename();
			SetParamDialog dia = new SetParamDialog(dsname, parammap);
			if (dia.showDialog() == DialogResult.OK)
			{
				parammapresult = dia.getParams();
				String dn = dia.getDBConectionName();
				con = getConnection(DBUtil.getConnectionSeting(dn));
				SetParamDialog.setDatasourcename(dn);
			} else
			{
				return;
			}
			File xml = File.createTempFile("xml", ".tmp");
			String content = null;
			if (con != null)
			{
				if (havazmb)
				{
					content = ZhuMobanXMLGenerate.generateXML(sqls,
							parammapresult, con);
				} else
				{
					content = XMLGenerate.generateXMLofItems(setting
							.getSqlitem(), parammapresult, con);
				}
			} else
			{
				WiseDocOptionPane.showMessageDialog(SystemManager
						.getMainframe(), MessageResource
						.getMessage(MessageConstants.CONNECTIONAGAIN));
				return;
			}
			FileOutputStream outstream = new FileOutputStream(xml);
			if (content == null || content.equals(""))
			{
				content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root/>";
			}
			byte[] bts = content.getBytes("UTF-8");
			outstream.write(bts);
			outstream.flush();
			outstream.close();
			String fopPath = "lib" + File.separator + "wdems" + File.separator
					+ "wdems.bat";
			String command = fopPath + " -xml "
					+ BrowseAction.URLEncode(xml.getAbsolutePath()) + " -xsl "
					+ BrowseAction.URLEncode(xsl.getAbsolutePath()) + " awt";
			// 采用另外一根线程调用预览程序，这样预览时还可以进行其他操作

			Process p = null;
			try
			{
				StringTokenizer st = new StringTokenizer(command);
				String[] cmdarray = new String[st.countTokens()];
				for (int i = 0; st.hasMoreTokens(); i++)
					cmdarray[i] = st.nextToken();
				ProcessBuilder pb = new ProcessBuilder(cmdarray);
				pb.directory(new File(System.getProperty("user.dir")));
				// 该方法使得错误输出流和普通输出流采用一个流，这样可以捕捉到错误消息，从而使得在有异常时也能预览
				pb.redirectErrorStream(true);
				p = pb.start();
				BufferedReader input = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				// 必须得度这个流，否则批处理程序不执行
				String line = input.readLine();
				while (line != null)
				{
					line = input.readLine();
				}
				input.close();
				xsl.delete();
				xml.delete();

			} catch (Exception e1)
			{
				if (p != null)
				{
					p.destroy();
				}
				e1.printStackTrace();
			}
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}

	}

	/**
	 * 
	 * 判断指定的对象是否为Null
	 * 
	 * @param o
	 *            指定被判断的对象
	 * @return boolean 如果o==null：True否则：False
	 * @throws ZimobanFileNotFoundException
	 */
	public void dealSub(String filepath, String subname, StringBuffer xsl,
			List<Setting> sqls, List<String> temp)
			throws ZimobanFileNotFoundException
	{
		FileInputStream fileinsub = null;
		File zifile = new File(filepath);
		try
		{
			fileinsub = new FileInputStream(zifile);
			ZipInputStream zipsub = new ZipInputStream(fileinsub);
			ZipEntry entrysub = zipsub.getNextEntry();
			try
			{
				for (; entrysub != null; entrysub = zipsub.getNextEntry())
				{
					if (entrysub.isDirectory())
					{
						continue;
					}
					if (WSDXKind.XSLTEMPLATE.getName().equals(
							entrysub.getName()))
					{
						InputStreamReader isr = new InputStreamReader(zipsub,
								"UTF-8");
						int c = 0;
						while ((c = isr.read()) != -1)
						{
							xsl.append((char) c);
						}
					} else if (WSDXKind.SQL.getName()
							.equals(entrysub.getName()))
					{
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						int c = -1;
						byte[] reads = new byte[1024];
						while ((c = zipsub.read(reads)) != -1)
						{
							out.write(reads, 0, c);
						}
						ByteArrayInputStream in = new ByteArrayInputStream(out
								.toByteArray());
						sqls.add(getSetting(in, subname));
						in.close();
					} else if (WSDXKind.DATASOURCE.getName().equals(
							entrysub.getName()))
					{
						InputStreamReader isr = new InputStreamReader(zipsub,
								"UTF-8");
						int c = 0;
						while ((c = isr.read()) != -1)
						{
							xsl.append((char) c);
						}
					} else if (WSDXKind.FCTEMPLATE.getName().equals(
							entrysub.getName()))
					{
						StringBuffer names = new StringBuffer();
						InputStreamReader isr = new InputStreamReader(zipsub);
						int c = 0;
						while ((c = isr.read()) != -1)
						{
							names.append((char) c);
						}
						String[] namesarry = names.toString().split(",");
						if (namesarry.length > 0)
						{
							for (String current : namesarry)
							{
								if (!"".equals(current)
										&& !temp.contains(current))
								{
									temp.add(current);
								}
							}
						}
					}
					zipsub.closeEntry();
				}
			} finally
			{
				zipsub.close();
				fileinsub.close();
			}
		} catch (FileNotFoundException e)
		{
			throw new ZimobanFileNotFoundException(subname);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private Setting getSetting(InputStream in, String name)
	{

		List<SQLItem> sqlitems = new ArrayList<SQLItem>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		org.w3c.dom.Document doc = null;
		try
		{
			builder = factory.newDocumentBuilder();
			doc = builder.parse(in);
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
			Element rootelement = doc.getDocumentElement();
			NodeList elements = rootelement.getChildNodes();
			int number = elements.getLength();
			if (number > 0)
			{
				for (int i = 0; i < number; i++)
				{
					Node current = elements.item(i);
					if (current instanceof Element)
					{
						SQLItem item = getSQLItem((Element) current);
						sqlitems.add(item);
					}
				}
			}
		}
		return new Setting(sqlitems, name);

	}

	private boolean ifHaveZimoban(CellElement element)
	{
		if (element != null)
		{
			if (element instanceof ZiMoban)
			{
				return true;
			}
			List<CellElement> listele = element.getAllChildren();
			if (listele != null)
			{
				for (CellElement current : listele)
				{
					boolean flg = ifHaveZimoban(current);
					if (flg)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean ifHaveZimoban()
	{
		// ZiMoban
		Document document = SystemManager.getCurruentDocument();
		if (document != null)
		{
			List<CellElement> listele = document.getAllChildren();
			if (listele != null)
			{
				for (CellElement current : listele)
				{
					boolean flg = ifHaveZimoban(current);
					if (flg)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public void deal(Document doc, String name, OutputStream stream,
			List<Setting> sqls) throws IOException
	{
		String rootpath = System.getProperty("user.dir");
		String subrootpath = rootpath + File.separator + "zimobans";
		String fucrootpath = rootpath + File.separator + "xslt";
		name = name.replaceFirst(".tmp", "");
		MainXSLWriter mw = new MainXSLWriter();
		// 主模版的头和内容部分
		mw.writeNoClose(stream, doc, name);

		StringBuffer otherxsl = new StringBuffer();
		// 功能模板链表
		List<String> temp = new ArrayList<String>();
		// 子模板
		String subs = mw.getZimobannames();
		String[] subsarry = subs.toString().split(",");
		if (subsarry.length > 0)
		{
			for (String current : subsarry)
			{
				String currentzh = current;
				if (current.contains("_wisiifs_"))
				{
					String[] paths = current.split("_wisiifs_");
					currentzh = "";
					for (int i = 0; i < paths.length; i++)
					{
						currentzh += paths[i];
						if (i < paths.length - 1)
						{
							currentzh += File.separator;
						}
					}
				}
				if (!"".equals(currentzh))
				{
					try
					{
						dealSub(subrootpath + File.separator + currentzh
								+ ".wsdx", current, otherxsl, sqls, temp);
					} catch (ZimobanFileNotFoundException e)
					{
						throw new ZimobanFileNotFoundException(current);
					}
				}
			}
		}
		byte[] subbytes = otherxsl.toString().getBytes("UTF-8");
		stream.write(subbytes);
		String datasourceanddecimal = mw.getDatasource();
		byte[] ddbytes = datasourceanddecimal.getBytes("UTF-8");
		stream.write(ddbytes);
		String names = mw.getFunctiontemplate();
		String[] namesarry = names.split(",");
		if (namesarry.length > 0)
		{
			for (String current : namesarry)
			{
				if (!"".equals(current) && !temp.contains(current))
				{
					temp.add(current);
				}
			}
		}
		if (!temp.isEmpty())
		{
			int size = temp.size();
			for (int i = 0; i < size; i++)
			{
				String profileName = temp.get(i);
				String filePath = fucrootpath + File.separator + profileName
						+ ".xml";
				InputStream isr = new FileInputStream(filePath);
				int c = 0;
				while ((c = isr.read()) != -1)
				{
					stream.write(c);
				}
				isr.close();
			}
		}
		String end = "</xsl:stylesheet>";
		byte[] endb = end.getBytes("UTF-8");
		stream.write(endb);
		stream.close();
	}

	private String generateDBSetting(Setting dbsetting)
	{
		String returns = "";
		if (dbsetting != null)
		{
			returns = returns + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			returns = returns + "<DS>";
			List<SQLItem> sqlitems = dbsetting.getSqlitem();
			if (sqlitems != null && !sqlitems.isEmpty())
			{
				for (SQLItem sqlitem : sqlitems)
				{
					returns = returns + generateSQLItem(sqlitem);
				}
			}
			returns = returns + "</DS>";
		}
		return returns;
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private String generateSQLItem(SQLItem sqlitem)
	{
		String returns = "";
		if (sqlitem != null)
		{
			returns = returns + ElementWriter.TAGBEGIN + "i" + " ";
			returns = returns + "sql=\"" + XMLUtil.getXmlText(sqlitem.getSql())
					+ "\"";
			returns = returns + ElementWriter.TAGEND + ElementWriter.LINEBREAK;
			List<SQLItem> sqlitems = sqlitem.getChildren();
			if (sqlitems != null && !sqlitems.isEmpty())
			{
				for (SQLItem childsqlitem : sqlitems)
				{
					returns = returns + generateSQLItem(childsqlitem);
				}
			}
			returns = returns + ElementWriter.TAGENDSTART + "i"
					+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
		}
		return returns;
	}

	private void getParammap(String sql, List<String> list)
	{
		if (sql == null || "".equals(sql))
		{
			return;
		}
		char[] chares = sql.toCharArray();
		int count = chares.length;
		boolean isinparam = false;
		StringBuffer param = new StringBuffer();
		for (int i = 0; i < count; i++)
		{
			char c = chares[i];
			if (c == '#')
			{
				if (isinparam)
				{
					String current = param.toString().trim();
					if (!"".equals(current) && !list.contains(current))
					{
						list.add(current);
					}
				}
				isinparam = true;
				param = new StringBuffer();
			} else
			{
				if (isinparam)
				{
					if (c >= '0' && c <= '9')
					{
						param.append(c);
					} else
					{
						String current = param.toString().trim();
						if (!"".equals(current) && !list.contains(current))
						{
							list.add(current);
						}
						isinparam = false;
					}
				}
			}
		}
		if (isinparam)
		{
			String current = param.toString().trim();
			if (!"".equals(current) && !list.contains(current))
			{
				list.add(current);
			}
		}
	}

	private Connection getConnection(ConnectionSeting cs)
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
				return null;
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
			}
			return con;
		}
		return null;
	}

	private SQLItem getSQLItem(Element element)
	{
		String sql = element.getAttributeNode("sql").getNodeValue();
		List<SQLItem> sqlitems = null;
		NodeList elements = element.getElementsByTagName("i");
		int number = elements.getLength();
		if (number > 0)
		{
			sqlitems = new ArrayList<SQLItem>();
			for (int i = 0; i < number; i++)
			{
				Node current = elements.item(i);
				if (current instanceof Element)
				{
					SQLItem item = getSQLItem((Element) current);
					sqlitems.add(item);
				}
			}
		}
		if (sqlitems == null)
		{
			return SqlItemFactory.getSqlItem(sql);
		} else
		{
			SQLItem item = SqlItemFactory.getSqlItem(sql);
			item.setChildren(sqlitems);
			return item;
		}
	}
}
