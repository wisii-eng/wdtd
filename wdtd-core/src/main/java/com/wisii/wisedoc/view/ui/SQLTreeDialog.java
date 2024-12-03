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
 * @SQLTreeDialog.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jsyntaxpane.DefaultSyntaxKit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.wisii.db.Setting;
import com.wisii.db.exception.DBConnectException;
import com.wisii.db.exception.FieldNotExistExceptin;
import com.wisii.db.exception.NoSQLException;
import com.wisii.db.exception.NotLeafShouldbeOneSQL;
import com.wisii.db.exception.OutOfOrderException;
import com.wisii.db.exception.SqlExecuteException;
import com.wisii.db.model.SQLItem;
import com.wisii.db.reader.StrutReader;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.swing.treetable.Node;
import com.wisii.wisedoc.swing.treetable.TreeTableModelAdapter;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2010-11-8
 */
public final class SQLTreeDialog extends AbstractWisedocDialog implements
		ButtonContainer
{

	private Setting olddbsetting;

	/* sql树控件 */
	private SQLTreeTable sqltreetable;

	/* sql语句输入文本区域 */
	private JEditorPane sqltextpane;

	/* 确定按钮 */
	private JButton btnOK = new JButton(UiText.DIALOG_OK);

	/* 取消按钮 */
	private JButton btnCancel = new JButton(UiText.DIALOG_CANCEL);

	private JButton btnaddsub = new JButton(UiText.SQL_ADDSUB);

	private JButton btnaddnext = new JButton(UiText.SQL_ADDNEXT);

	private JButton btnremove = new JButton(UiText.SQL_DEL);
	private JButton btnimport = new JButton(UiText.SQL_IMPORT);
	private ConnectSettingPanel consetpanel;

	private int oldselect = 1;

	// 是否处于操作状态，点击以上btnaddsub，btnaddnext，btnremove处于操作状态，这种情况下引起的表格选择事件不处理
	private boolean isinopertion;

	private DataStructureTreeModel model;

	public SQLTreeDialog(Setting dbsetting)
	{
		olddbsetting = dbsetting;
		init();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private void init()
	{
		setTitle(UiText.SQL_TITLE);
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		DefaultSyntaxKit.initKit(); 
		sqltextpane = new JEditorPane();
		JScrollPane sqlpanel = new JScrollPane(sqltextpane);
		sqlpanel.setPreferredSize(new Dimension(800, 100));
		con.add(sqlpanel, BorderLayout.NORTH);
		sqltextpane.setContentType("text/sql"); 
		//需要设置字体，否则显示中文有问题，估计是sqltextpane设置了一个不支持中文的字体
		sqltextpane.setFont(new Font("Serif", Font.PLAIN, 15));
		JPanel mainpanel = new JPanel(new BorderLayout());
		mainpanel.setSize(800, 460);
		List<SQLItem> sqlitems = null;
		if (olddbsetting != null)
		{
			sqlitems = olddbsetting.getSqlitem();
		}
		SQLTreeTableModel treeTableModel = new SQLTreeTableModel(sqlitems);
		sqltreetable = new SQLTreeTable(treeTableModel);
		SQLItemNode node = (SQLItemNode) treeTableModel.getRoot().getChildAt(0);
		sqltreetable.setNodeSelect(node);
		sqltextpane.setText(node.getSql());
		sqltreetable.setVisible(true);
		JScrollPane sqltreetablesp = new JScrollPane(sqltreetable);
		// sqltreetablesp.getViewport().add(sqltreetable);
		sqltreetablesp.setPreferredSize(new Dimension(800, 300));
		sqltreetable.getTableHeader().setReorderingAllowed(false);
		mainpanel.add(sqltreetablesp, BorderLayout.NORTH);
		JPanel sqloperpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		sqloperpanel.add(btnaddsub);
		sqloperpanel.add(btnaddnext);
		sqloperpanel.add(btnremove);
		sqloperpanel.add(btnimport);
		sqloperpanel.setPreferredSize(new Dimension(800, 40));
		mainpanel.add(sqloperpanel, BorderLayout.CENTER);
		String connectname = null;
		if (olddbsetting != null)
		{
			connectname = olddbsetting.getConnname();
		}
		consetpanel = new ConnectSettingPanel(connectname, this);
		consetpanel.setPreferredSize(new Dimension(800, 90));
		mainpanel.add(consetpanel, BorderLayout.SOUTH);
		con.add(mainpanel, BorderLayout.CENTER);
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		buttonpanel.add(btnOK);
		buttonpanel.add(btnCancel);
		buttonpanel.setPreferredSize(new Dimension(800, 40));
		con.add(buttonpanel, BorderLayout.SOUTH);
		setOkButton(btnOK);
		setCancelButton(btnCancel);
		initAction();
		reinitButton();
		setSize(800, 600);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private void initAction()
	{
		btnaddsub.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				isinopertion = true;
				commitcurrentSQL();
				int selectrow = sqltreetable.getSelectedRow();
				TreeTableModelAdapter model = (TreeTableModelAdapter) sqltreetable
						.getModel();
				SQLItemNode sqlitemnode = (SQLItemNode) model
						.nodeForRow(selectrow);
				Node toaddnode = new SQLItemNode();
				sqlitemnode.addNode(toaddnode);
				sqltreetable.setNodeSelect(toaddnode);
				sqltextpane.setText("");
				isinopertion = false;
			}
		});
		btnaddnext.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				isinopertion = true;
				commitcurrentSQL();
				int selectrow = sqltreetable.getSelectedRow();
				TreeTableModelAdapter model = (TreeTableModelAdapter) sqltreetable
						.getModel();
				SQLItemNode sqlitemnode = (SQLItemNode) model
						.nodeForRow(selectrow);
				Node parent = sqlitemnode.getParent();
				Node toaddnode = new SQLItemNode();
				parent.addNode(toaddnode, parent.getIndex(sqlitemnode) + 1);
				sqltreetable.setNodeSelect(toaddnode);
				sqltextpane.setText("");
				isinopertion = false;
			}
		});
		btnremove.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectrow = sqltreetable.getSelectedRow();
				TreeTableModelAdapter model = (TreeTableModelAdapter) sqltreetable
						.getModel();
				SQLItemNode sqlitemnode = (SQLItemNode) model
						.nodeForRow(selectrow);
				Node parent = sqlitemnode.getParent();
				parent.remove(sqlitemnode);
				if (selectrow > 0)
				{
					if (selectrow >= sqltreetable.getRowCount())
					{
						sqltreetable.setRowSelectionInterval(sqltreetable
								.getRowCount() - 1,
								sqltreetable.getRowCount() - 1);
					} else
					{
						sqltreetable.setRowSelectionInterval(selectrow,
								selectrow);
					}
				}
				sqltreetable.updateUI();
			}
		});
		btnimport.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser filechooser = DialogSupport
						.getDialog(new WiseDocFileFilter("*.dmt", "dmt"));
				int result = filechooser.showOpenDialog(SQLTreeDialog.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					File file = filechooser.getSelectedFile();
					if (!file.exists())
					{
						return;
					}
					if (!file.canRead())
					{
						WiseDocOptionPane.showMessageDialog(SystemManager
								.getMainframe(), MessageResource
								.getMessage(MessageConstants.FILE
										+ MessageConstants.FILECANNOTREAD));
						return;
					}
					try
					{
						Document doc = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder().parse(file);
						Element root = doc.getDocumentElement();
						SQLItemNode node = getSQLItemNode(root);
						if (node != null)
						{
							sqltreetable
									.setTreeTableModel(new SQLTreeTableModel(
											node));
						}
					} catch (SAXException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParserConfigurationException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			private SQLItemNode getSQLItemNode(Element root)
			{
				if (root == null)
				{
					return null;
				}
				NodeList settingroot = root.getElementsByTagName("setting");
				if (settingroot != null)
				{
					SQLItemNode rootnode = new SQLItemNode();
					initchildnodes(settingroot.item(0).getChildNodes(),
							rootnode);
					return rootnode;
				}
				return null;
			}

			private void initchildnodes(NodeList childNodes,
					SQLItemNode rootnode)
			{
				if (childNodes != null)
				{
					int len = childNodes.getLength();
					for (int i = 0; i < len; i++)
					{
						Element childelement = (Element) childNodes.item(i);
						SQLItemNode childnode = new SQLItemNode();
						childnode.setSql(childelement.getAttribute("sql"));
						initchildnodes(childelement.getChildNodes(), childnode);
						rootnode.addNode(childnode);
					}
				}

			}
		});
		btnOK.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				commitcurrentSQL();
				TableModel tablemodel = sqltreetable.getModel();
				if (tablemodel instanceof TreeTableModelAdapter)
				{
					TreeTableModelAdapter ttma = (TreeTableModelAdapter) tablemodel;
					SQLTreeTableModel lemodel = (SQLTreeTableModel) ttma
							.getTreeTableModel();
					SQLItemNode root = lemodel.getRoot();
					try
					{
						model = StrutReader.getDataStrutture(root, consetpanel
								.getConnname());
					} catch (DBConnectException e1)
					{
						e1.printStackTrace();
						WiseDocOptionPane.showConfirmDialog(SQLTreeDialog.this,
								"数据库链接不可用，请检查数据连接设置");
						return;
					} catch (SqlExecuteException e1)
					{
						e1.printStackTrace();
						SQLItemNode node = e1.getNode();
						sqltreetable.setNodeSelect(node);
						String text = sqltextpane.getText();
						String errsql = e1.getErrorsql();
						if (errsql != null)
						{
							int index = text.indexOf(errsql);
							sqltextpane.select(index, index + errsql.length());
						}
						return;
					} catch (NotLeafShouldbeOneSQL e1)
					{
						e1.printStackTrace();
						SQLItemNode node = e1.getNode();
						sqltreetable.setNodeSelect(node);
						return;
					} catch (FieldNotExistExceptin e1)
					{
						e1.printStackTrace();
						SQLItemNode node = e1.getNode();
						sqltreetable.setNodeSelect(node);
						String text = sqltextpane.getText();
						String errsql = e1.getSql();
						if (errsql != null)
						{
							int index = text.indexOf(errsql);
							sqltextpane.select(index + e1.getIndex(), index
									+ e1.getIndex() + e1.getField().length());
						}
						return;
					} catch (OutOfOrderException e1)
					{
						e1.printStackTrace();
						SQLItemNode node = e1.getNode();
						sqltreetable.setNodeSelect(node);
						String text = sqltextpane.getText();
						String errsql = e1.getSql();
						if (errsql != null)
						{
							int index = text.indexOf(errsql);
							sqltextpane.select(index + e1.getIndex(), index
									+ e1.getIndex() + e1.getField().length());
						}
						return;
					} catch (NoSQLException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
						SQLItemNode node = e1.getNode();
						sqltreetable.setNodeSelect(node);
						return;
					}
					result = DialogResult.OK;
					SQLTreeDialog.this.dispose();
				}

			}
		});
		btnCancel.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				SQLTreeDialog.this.dispose();

			}
		});

		sqltreetable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener()
				{

					@Override
					public void valueChanged(ListSelectionEvent e)
					{

						int newselect = sqltreetable.getSelectedRow();
						if (!isinopertion)
						{

							commitcurrentSQL();
							if (newselect > 0)
							{
								TreeTableModelAdapter model = (TreeTableModelAdapter) sqltreetable
										.getModel();
								SQLItemNode sqlitemnode = (SQLItemNode) model
										.nodeForRow(newselect);
								sqltextpane.setText(sqlitemnode.getSql());
							} else
							{
								sqltextpane.setText("");
							}
						}
						oldselect = newselect;
						reinitButton();
					}
				});
	}

	/**
	 * 
	 * 提交当前sql数据框中的sql
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private void commitcurrentSQL()
	{
		if (oldselect < 1)
		{
			return;
		}
		String sql = sqltextpane.getText();
		if (sql != null)
		{
			sql = sql.trim();
			if (!sql.isEmpty())
			{
				TreeTableModelAdapter model = (TreeTableModelAdapter) sqltreetable
						.getModel();
				SQLItemNode sqlitemnode = (SQLItemNode) model
						.nodeForRow(oldselect);
				if (sqlitemnode == null)
				{
					return;
				}
				sqltreetable.setValueAt(sql, oldselect, 1);
			}
		}
		return;
	}

	public void reinitButton()
	{
		if (!consetpanel.isConnectSelect())
		{
			btnaddsub.setEnabled(false);
			btnaddsub.setEnabled(false);
			btnaddnext.setEnabled(false);
			btnremove.setEnabled(false);
			sqltextpane.setEditable(false);
			sqltreetable.setEnabled(false);
		} else
		{
			int selectrow = sqltreetable.getSelectedRow();

			btnaddsub.setEnabled(selectrow > -1);
			boolean selectother = selectrow > 0;
			btnaddnext.setEnabled(selectother);
			btnremove.setEnabled(selectother);
			sqltextpane.setEditable(selectother);
			sqltreetable.setEnabled(true);
		}
	}

	/**
	 * @返回 model变量的值
	 */
	public DataStructureTreeModel getDataStructureModel()
	{
		return model;
	}

	public static void main(String[] args)
	{
		new SQLTreeDialog(null).show();
	}
}
