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
 * @AbstractBrowseAction.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
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
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.SetParamDialog;
import com.wisii.wisedoc.view.dialog.XMLSelectDialog;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-6-5
 */
public abstract class AbstractBrowseAction extends BaseAction
{
	private static String datafileurl = null;
	/**
	 * 
	 * 用默认的字符串和默认的图标定义一个Action对象。
	 */
	public AbstractBrowseAction() {
		super();
	}

	/**
	 * 用指定描述字符串和默认图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 * 
	 */
	public AbstractBrowseAction(String name) {
		super(name);
	}

	/**
	 * 用指定描述字符串指定图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 * @param icon
	 *            指定Action的图标。
	 */
	public AbstractBrowseAction(String name, Icon icon) {
		super(name, icon);
	}
	/**
	 * 
	 * 预览Action接口方法实现 update by csy
	 * 
	 * @para源对数据的封装。
	 * @return void 无返回值
	 */
	public void doAction(ActionEvent e) {
	    //目前还不支持混合模式，机子模板中即有xml，又有数据库的方式的。
	    //这种情况下，虽然不报错，但显示效果不对
		List<Setting> sqls = new ArrayList<Setting>();
		boolean ishavezimoban = IOFactory.ifHaveZimoban();
		FileItem xslfileitem = generateXSL(sqls, ishavezimoban);
		FileItem xmlfileitem = generateXML(sqls, ishavezimoban);
		browse(xslfileitem,xmlfileitem);
	}
	/**
	 * {方法的功能/动作描述}
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	
	protected abstract FileItem generateXSL(List<Setting> sqls, boolean ishavezimoban);
	/**
	 * {方法的功能/动作描述}
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	
	protected abstract  void browse(
			FileItem xslfileitem,
			FileItem xmlfileitem);
	private FileItem generateXML(List<Setting> sqls, boolean ishavezimoba) {
		File xml = null;
		boolean isneeddel = false;
		//只要主模板或子模板中有一个是SQL方式的，则弹出的是数据库选择对话框
		if (!sqls.isEmpty()) {
			List<String> params = new ArrayList<String>();
			for (Setting currentsql : sqls) {
				getParammap(generateDBSetting(currentsql), params);
			}
			Map<String, String> parammap = new HashMap<String, String>();

			Map<String, String> beforeparammap = SetParamDialog
					.getBeforeParams();
			boolean flg = beforeparammap != null && !beforeparammap.isEmpty();
			for (String current : params) {
				if (flg && beforeparammap.containsKey(current)) {
					parammap.put(current, beforeparammap.get(current));
				} else {
					parammap.put(current, "");
				}
			}
			String dsname = SetParamDialog.getDatasourcename();
			SetParamDialog dia = new SetParamDialog(dsname, parammap);
			Map<String, String> parammapresult = new HashMap<String, String>();
			Connection con = null;
			if (dia.showDialog() == DialogResult.OK) {
				parammapresult = dia.getParams();
				String dn = dia.getDBConectionName();
				con = getConnection(DBUtil.getConnectionSeting(dn));
				SetParamDialog.setDatasourcename(dn);
			} else {
				return null;
			}
			try {
				xml = File.createTempFile("xml", ".tmp");
				String content = null;
				if (con != null) {
					if (ishavezimoba) {
						content = ZhuMobanXMLGenerate.generateXML(sqls,
								parammapresult, con);
					} else {
						content = XMLGenerate.generateXMLofItems(sqls.get(0)
								.getSqlitem(), parammapresult, con);
					}
				} else {
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), MessageResource
							.getMessage(MessageConstants.CONNECTIONAGAIN));
					return null;
				}
				FileOutputStream outstream = new FileOutputStream(xml);
				if (content == null || content.equals("")) {
					content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root/>";
				}
				byte[] bts = content.getBytes("UTF-8");
				outstream.write(bts);
				outstream.flush();
				outstream.close();
				isneeddel = true;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		} else {
			XMLSelectDialog dia = new XMLSelectDialog(datafileurl);
			DialogResult res = dia.showDialog();
			/**
			 * / * 如果用户选择了去掉，则直接返回
			 */
			if (res == DialogResult.Cancel) {
				return null;
			}
			String xmlpath = dia.getfile();
			setDatafileurl(xmlpath);
			xml = new File(xmlpath);
		}
		return new FileItem(xml, isneeddel);
	}
	public static String getDatafileurl() {
		return datafileurl;
	}

	public static void setDatafileurl(String datafileurl) {
		AbstractBrowseAction.datafileurl = datafileurl;
	}
	protected class FileItem {
		private File file;
		private boolean isneeddel;

		public FileItem(File file, boolean isneeddel) {
			super();
			this.file = file;
			this.isneeddel = isneeddel;
		}

		public File getFile() {
			return file;
		}

		public boolean isIsneeddel() {
			return isneeddel;
		}

	}
	private Connection getConnection(ConnectionSeting cs) {

		if (cs != null) {
			String driverclass = DriverTypeUtil.getDriverClassName(cs
					.getDriverclassType());
			try {
				Class.forName(driverclass);
			} catch (ClassNotFoundException e1) {
				return null;
			}
			String url = cs.getUrl();
			String username = cs.getUsername();
			String password = cs.getPassword();
			Connection con = null;
			try {
				con = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
			}
			return con;
		}
		return null;
	}
	protected Setting getSetting(InputStream in, String name) {

		List<SQLItem> sqlitems = new ArrayList<SQLItem>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		org.w3c.dom.Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(in);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (doc != null) {
			Element rootelement = doc.getDocumentElement();
			NodeList elements = rootelement.getChildNodes();
			int number = elements.getLength();
			if (number > 0) {
				for (int i = 0; i < number; i++) {
					Node current = elements.item(i);
					if (current instanceof Element) {
						SQLItem item = getSQLItem((Element) current);
						sqlitems.add(item);
					}
				}
			}
		}
		return new Setting(sqlitems, name);

	}

	private SQLItem getSQLItem(Element element) {
		String sql = element.getAttributeNode("sql").getNodeValue();
		List<SQLItem> sqlitems = null;
		NodeList elements = element.getElementsByTagName("i");
		int number = elements.getLength();
		if (number > 0) {
			sqlitems = new ArrayList<SQLItem>();
			for (int i = 0; i < number; i++) {
				Node current = elements.item(i);
				if (current instanceof Element) {
					SQLItem item = getSQLItem((Element) current);
					sqlitems.add(item);
				}
			}
		}
		if (sqlitems == null) {
			return SqlItemFactory.getSqlItem(sql);
		} else {
			SQLItem item = SqlItemFactory.getSqlItem(sql);
			item.setChildren(sqlitems);
			return item;
		}
	}

	private String generateDBSetting(Setting dbsetting) {
		String returns = "";
		if (dbsetting != null) {
			List<SQLItem> sqlitems = dbsetting.getSqlitem();
			if (sqlitems != null && !sqlitems.isEmpty()) {
				for (SQLItem sqlitem : sqlitems) {
					returns = returns + generateSQLItem(sqlitem);
				}
			}
		}
		return returns;
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private String generateSQLItem(SQLItem sqlitem)
	{
		String returns = "";
		if (sqlitem != null)
		{
			returns = returns + sqlitem.getSql() + " ";
			List<SQLItem> sqlitems = sqlitem.getChildren();
			if (sqlitems != null && !sqlitems.isEmpty())
			{
				for (SQLItem childsqlitem : sqlitems)
				{
					returns = returns + generateSQLItem(childsqlitem);
				}
			}
		}
		return returns;
	}

	private void getParammap(String sql, List<String> list) {
		if (sql == null || "".equals(sql)) {
			return;
		}
		char[] chares = sql.toCharArray();
		int count = chares.length;
		boolean isinparam = false;
		StringBuffer param = new StringBuffer();
		for (int i = 0; i < count; i++) {
			char c = chares[i];
			if (c == '#') {
				if (isinparam) {
					String current = param.toString().trim();
					if (!"".equals(current) && !list.contains(current)) {
						list.add(current);
					}
				}
				isinparam = true;
				param = new StringBuffer();
			} else {
				if (isinparam) {
					if (c >= '0' && c <= '9') {
						param.append(c);
					} else {
						String current = param.toString().trim();
						if (!"".equals(current) && !list.contains(current)) {
							list.add(current);
						}
						isinparam = false;
					}
				}
			}
		}
		if (isinparam) {
			String current = param.toString().trim();
			if (!"".equals(current) && !list.contains(current)) {
				list.add(current);
			}
		}
	}
}
