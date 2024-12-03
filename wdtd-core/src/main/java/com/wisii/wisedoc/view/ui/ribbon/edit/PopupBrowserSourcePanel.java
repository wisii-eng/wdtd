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
 * @DataSourcePanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserSource;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.swing.PopupBrowserDataTable;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.view.component.FileEditorComponent;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-8-31
 */
public class PopupBrowserSourcePanel extends JPanel {

	private DataSource datasource;

	BrowserSourcePanel browserSourcePanel;

	public PopupBrowserSourcePanel(DataSource datasource) {
		this.datasource = datasource;
		setLayout(new BorderLayout());
		JPanel typepanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		add(typepanel, BorderLayout.NORTH);
		browserSourcePanel = new BrowserSourcePanel();
		add(browserSourcePanel, BorderLayout.CENTER);
	}

	private class BrowserSourcePanel extends JPanel {

		JLabel filelabel = new JLabel(RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE);// 文件

		FileEditorComponent filecom;

		PopupBrowserDataTable columnsettable;

		String file;

		public String getFile() {
			return file;
		}

		private BrowserSourcePanel() {
			setLayout(new BorderLayout());
			JPanel filepanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			String filepath = null;
			List<PopupBrowserSource> columnitems = null;
			int id = -1;
			if (datasource != null && datasource instanceof PopupBrowserSource) {
				filepath = ((PopupBrowserSource) datasource).getFile();
				columnitems = createColumnInfos();
				id = ((PopupBrowserSource) datasource).getId();
			}
			filecom = new FileEditorComponent(filepath);
			filecom.getBtnComp().setText("选择文件");
			filecom.setPreferredSize(new Dimension(140, 22));
			filepanel.add(filelabel);
			filepanel.add(filecom);
			add(filepanel, BorderLayout.NORTH);
			columnsettable = new PopupBrowserDataTable(columnitems);
			JScrollPane js = new JScrollPane(columnsettable);
			if (id != -1) {
				columnsettable.setRowSelectionInterval(id, id);
			}
			add(js, BorderLayout.CENTER);
			initAction();
		}

		private void initAction() {
			filecom.addPropertyChangeListener(new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					file = (String) filecom.getValue();
					if (file != null) {
						file = file.trim();
					}
					if (file == null || file.isEmpty()) {
						return;
					} else {
						try {
							List<PopupBrowserSource> parserXml = parserXml(file);
							columnsettable.reInitColumnInfoes(parserXml);
						} catch (Exception e) {
							e.printStackTrace();
							WiseDocOptionPane.showMessageDialog(PopupBrowserSourcePanel.this, "所选择文件不是一个合法的配置文件");
							LogUtil.debugException("导入的数据文件不可读或结构有错", e);
						}
					}
				}
			});
		}

		private boolean isAllSetOk() {
			String filepath = (String) filecom.getValue();
			if (filepath == null) {
				filecom.requestFocus();
				return false;
			}
			filepath = filepath.trim();
			if (filepath.isEmpty()) {
				filecom.requestFocus();
				return false;
			}
			return true;
		}

		private DataSource getDataSource() {
			return datasource;
		}

	}

	private List<PopupBrowserSource> createColumnInfos() {
		if (datasource != null && datasource instanceof PopupBrowserSource) {
			PopupBrowserSource source = (PopupBrowserSource) datasource;
			String file = source.getFile();
			// String code = source.getCode();
			// String name = source.getName();
			// String data = source.getData();
			// String para = source.getPara();
			// String url = source.getUrl();
			// if (name != null && !name.isEmpty()) {
			// List<PopupBrowserSource> cts = new
			// ArrayList<PopupBrowserSource>();
			// String[] viewname = data.split(",");
			// int size = viewname.length;
			// for (int i = 0; i < size; i++) {
			// cts.add(new PopupBrowserSource(file, code, viewname[i], data,
			// para, url));
			// }
			// return cts;
			// }
			// List<PopupBrowserSource> cts = new
			// ArrayList<PopupBrowserSource>();
			// cts.add(new PopupBrowserSource(file, code, name, data, para,
			// url));
			List<PopupBrowserSource> parserXml = parserXml(file);
			return parserXml;
		}
		return null;
	}

	public DataSource getDataSource() {
		int row = browserSourcePanel.columnsettable.getSelectedRow();
		String file = null;
		if (datasource != null && datasource instanceof PopupBrowserSource) {
			PopupBrowserSource source = (PopupBrowserSource) datasource;
			file = source.getFile();
		} else {
			file = browserSourcePanel.getFile();
		}
		String code = (String) browserSourcePanel.columnsettable.getValueAt(row, 1);
		String name = (String) browserSourcePanel.columnsettable.getValueAt(row, 2);
		String data = (String) browserSourcePanel.columnsettable.getValueAt(row, 3);
		String para = (String) browserSourcePanel.columnsettable.getValueAt(row, 4);
		String url = (String) browserSourcePanel.columnsettable.getValueAt(row, 5);
		if (data != null && !data.isEmpty()) {
			datasource = new PopupBrowserSource(file, row, code, name, data, para, url);
		}
		return datasource;
	}

	public boolean isAllSettingRight() {
		int row = browserSourcePanel.columnsettable.getSelectedRow();

		return row != -1;
	}

	public List<PopupBrowserSource> parserXml(String fileName) {
		List<PopupBrowserSource> list = new ArrayList<PopupBrowserSource>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(fileName);
			NodeList users = document.getChildNodes();
			for (int i = 0; i < users.getLength(); i++) {
				Node user = users.item(i);
				NodeList userInfo = user.getChildNodes();

				for (int j = 0; j < userInfo.getLength(); j++) {
					Map<Integer, String> map = new HashMap<Integer, String>();
					int a = 0;
					Node node = userInfo.item(j);
					NodeList userMeta = node.getChildNodes();

					for (int k = 0; k < userMeta.getLength(); k++) {
						if (userMeta.item(k).getNodeName() != "#text" && userMeta.item(k).getNodeName() != "#comment") {
							// System.out.println(userMeta.item(k).getTextContent());
							map.put(a++, userMeta.item(k).getTextContent());
						}
					}
					PopupBrowserSource info = getInfo(a, map);
					info.setId(list.size());
					if (info != null && info.getCode() != null) {
						list.add(info);
					}
				}
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public PopupBrowserSource getInfo(int a, Map<Integer, String> map) {
		PopupBrowserSource p = new PopupBrowserSource();
		for (int i = 0; i <= a; i++) {

			switch (i) {

			case 0:
				p.setCode(map.get(0));
				break;
			case 1:
				p.setName(map.get(1));
				break;
			case 2:
				p.setData(map.get(2).replaceAll("@!#", ""));
				break;
			case 3:
				p.setPara(map.get(3));
				break;
			case 4:
				p.setUrl(map.get(4));
				break;
			default:
				break;
			}
		}
		if (datasource != null && datasource instanceof PopupBrowserSource) {
			String file = ((PopupBrowserSource) datasource).getFile();
			p.setFile(file);
		}
		return p;

	}
}
