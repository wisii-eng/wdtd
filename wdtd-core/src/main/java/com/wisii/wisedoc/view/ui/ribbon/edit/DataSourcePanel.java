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
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.banding.io.XMLReader;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.FileSource;
import com.wisii.wisedoc.document.attribute.transform.MultiSource;
import com.wisii.wisedoc.document.attribute.transform.TreeSource;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.swing.ColumnSetTable;
import com.wisii.wisedoc.swing.DataSourceTable;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.swing.WiseDocTree;
import com.wisii.wisedoc.swing.DataSourceTableModel.ColumnItem;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.FileEditorComponent;
import com.wisii.wisedoc.view.component.TextAndButtonComponent;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-8-31
 */
public class DataSourcePanel extends JPanel
{

	private DataSource datasource;

	JLabel typelabel = new JLabel(RibbonUIText.EDIT_DATASOURCE_STRUCTURE);

	private WiseCombobox typebox = new WiseCombobox(new String[]
	{ RibbonUIText.EDIT_DATASOURCE_STRUCTURE_TABLE,
			RibbonUIText.EDIT_DATASOURCE_STRUCTURE_TREE });

	private CardLayout cardlayout = new CardLayout();

	TableSourcePanel tablesourcepanel;

	TreeSourcePanel treesourcepanel;

	JPanel cardpanel;

	public DataSourcePanel(DataSource datasource)
	{
		this.datasource = datasource;
		setLayout(new BorderLayout());
		JPanel typepanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		typepanel.add(typelabel);
		typepanel.add(typebox);
		add(typepanel, BorderLayout.NORTH);
		cardpanel = new JPanel(cardlayout);
		tablesourcepanel = new TableSourcePanel();
		treesourcepanel = new TreeSourcePanel();
		cardpanel.add(tablesourcepanel,
				RibbonUIText.EDIT_DATASOURCE_STRUCTURE_TABLE);
		cardpanel.add(treesourcepanel,
				RibbonUIText.EDIT_DATASOURCE_STRUCTURE_TREE);
		add(cardpanel, BorderLayout.CENTER);
		typebox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				cardlayout.show(cardpanel, (String) typebox.getSelectedItem());
			}
		});
		if (datasource != null)
		{
			if (datasource instanceof MultiSource)
			{
				cardlayout.show(cardpanel,
						RibbonUIText.EDIT_DATASOURCE_STRUCTURE_TABLE);
				typebox.initIndex(0);
			} else
			{
				cardlayout.show(cardpanel,
						RibbonUIText.EDIT_DATASOURCE_STRUCTURE_TREE);
				typebox.initIndex(1);
			}
		}

	}

	private class TableSourcePanel extends JPanel
	{

		private JButton addbutton = new JButton(
				RibbonUIText.EDIT_DATASOURCE_FILESOURCE_ADD);

		private JButton delbutton = new JButton(
				RibbonUIText.EDIT_DATASOURCE_FILESOURCE_DEl);

		private DataSourceTable datasourcetable;

		private JLabel bondlabel = new JLabel(
				RibbonUIText.EDIT_DATASOURCE_MULTISOURCE_BOND);

		private WiseCombobox bondbox = new WiseCombobox(new String[]
		{ RibbonUIText.EDIT_DATASOURCE_MULTISOURCE_BOND_SQ,
				RibbonUIText.EDIT_DATASOURCE_MULTISOURCE_BOND_EQ,
				RibbonUIText.EDIT_DATASOURCE_MULTISOURCE_BOND_VERT });

		private TableSourcePanel()
		{
			setLayout(new BorderLayout());
			JPanel bondpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			bondpanel.add(bondlabel);
			bondpanel.add(bondbox);
			add(bondpanel, BorderLayout.NORTH);
			List<FileSource> sources = null;
			if (datasource != null && datasource instanceof MultiSource)
			{
				sources = ((MultiSource) datasource).getFilesources();
				int bondtype = ((MultiSource) datasource).getBond();
				if (bondtype == Constants.EN_DATASOURCE_EQ)
				{
					bondbox.initIndex(1);
				} else if (bondtype == Constants.EN_DATASOURCE_VERT)
				{
					bondbox.initIndex(2);
				} else
				{
					bondbox.initIndex(0);
				}
			}
			datasourcetable = new DataSourceTable(sources);
			JScrollPane js = new JScrollPane(datasourcetable);
			add(js, BorderLayout.CENTER);
			JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.LEADING,
					0, 0));
			buttonpanel.add(addbutton);
			buttonpanel.add(delbutton);
			add(buttonpanel, BorderLayout.SOUTH);
			initAction();
		}

		private void initAction()
		{
			addbutton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					datasourcetable.addRow();

				}
			});
			delbutton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					int rowselect = datasourcetable.getSelectedRow();
					if (rowselect > -1)
					{
						datasourcetable.delRow(rowselect);
					}

				}
			});

		}

		private boolean isAllSetOk()
		{
			if (datasourcetable.getCellEditor() != null)
			{
				datasourcetable.getCellEditor().stopCellEditing();
			}
			return datasourcetable.isAllSetOk();
		}

		private DataSource getDataSource()
		{
			List<FileSource> filesources = datasourcetable.getFileSources();
			int bondtype = -1;
			int selectindex = bondbox.getSelectedIndex();
			if (selectindex == 1)
			{
				bondtype = Constants.EN_DATASOURCE_EQ;
			} else if (selectindex == 2)
			{
				bondtype = Constants.EN_DATASOURCE_VERT;
			} else
			{
				bondtype = Constants.EN_DATASOURCE_SQ;
			}
			return new MultiSource(bondtype, filesources);
		}

	}

	private class TreeSourcePanel extends JPanel
	{

		JLabel filelabel = new JLabel(
				RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE);

		FileEditorComponent filecom;

		JLabel rootlabel = new JLabel(
				RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_ROOT);

		NodeEditorComponent rootcom = new NodeEditorComponent(null);

		ColumnSetTable columnsettable;

		private TreeSourcePanel()
		{
			setLayout(new BorderLayout());
			JPanel filepanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			String filepath = null;
			List<ColumnItem> columnitems = null;
			if (datasource != null && datasource instanceof TreeSource)
			{
				filepath = ((TreeSource) datasource).getFile();
				String nodepath = ((TreeSource) datasource).getRoot();
				if (filepath != null && nodepath != null)
				{
					rootcom.setValue(createRootnode(filepath, nodepath));
				}
				columnitems = createColumnInfos();
			}
			filecom = new FileEditorComponent(filepath);
			filecom.setPreferredSize(new Dimension(100, 22));
			rootcom.setPreferredSize(new Dimension(100, 22));
			filepanel.add(filelabel);
			filepanel.add(filecom);
			filepanel.add(rootlabel);
			filepanel.add(rootcom);
			add(filepanel, BorderLayout.NORTH);
			columnsettable = new ColumnSetTable(columnitems);
			JScrollPane js = new JScrollPane(columnsettable);
			add(js, BorderLayout.CENTER);
			initAction();
		}

		private void initAction()
		{
			filecom.addPropertyChangeListener(new PropertyChangeListener()
			{

				@Override
				public void propertyChange(PropertyChangeEvent evt)
				{
					String file = (String) filecom.getValue();
					if (file != null)
					{
						file = file.trim();
					}
					if (file == null || file.isEmpty())
					{
						return;
					} else
					{
						try
						{
							DataStructureTreeModel datamodel = new XMLReader()
									.readStructure(file);

							BindNode root = (BindNode) datamodel.getRoot();
							List<ColumnItem> cts = createColumnItems(root);
							if (cts != null)
							{
								rootcom.setValue(root);
								columnsettable.reInitTable(cts);
							} else
							{
								Object oldvalue = evt.getOldValue();
								filecom.initValue((String)oldvalue);
								WiseDocOptionPane.showMessageDialog(
										DataSourcePanel.this,
										"所选择文件不是一个合法的转换表文件");
							}
						} catch (Exception e)
						{
							e.printStackTrace();
							WiseDocOptionPane.showMessageDialog(
									DataSourcePanel.this, "所选择文件不是一个合法的转换表文件");
							LogUtil.debugException("导入的数据文件不可读或结构有错", e);
						}
					}
				}
			});
			rootcom.addPropertyChangeListener(new PropertyChangeListener()
			{

				@Override
				public void propertyChange(PropertyChangeEvent evt)
				{
					BindNode root = (BindNode) rootcom.getValue();
					List<ColumnItem> cts = createColumnItems(root);
					if (cts != null)
					{
						columnsettable.reInitTable(cts);
					} else
					{
						WiseDocOptionPane.showMessageDialog(
								DataSourcePanel.this, "该根节点不是合法的节点");
					}
				}
			});

		}

		private List<ColumnItem> createColumnItems(BindNode node)
		{
			if (node != null && node.getChildCount() > 0)
			{
				BindNode mapnode = node.getChildAt(0);
				while (mapnode.getChildCount() > 0)
				{
					mapnode = mapnode.getChildAt(0);
				}
				mapnode = mapnode.getParent();
				List<ColumnItem> cis = new ArrayList<ColumnItem>();
				int size = mapnode.getChildCount();
				for (int i = 0; i < size; i++)
				{
					BindNode childnode = mapnode.getChildAt(i);
					if (childnode instanceof AttributeBindNode)
					{
						String columnname = ((AttributeBindNode) childnode)
								.getNodeName();
						cis.add(new ColumnItem(columnname, false));
					}
				}
				if (cis.isEmpty())
				{
					cis = null;
				}
				return cis;

			}
			return null;
		}

		private BindNode createRootnode(String file, String nodepath)
		{
			DataStructureTreeModel datamodel;
			try
			{
				datamodel = new XMLReader().readStructure(
						file);
				if (datamodel == null)
				{
					return null;
				}
				BindNode root = (BindNode) datamodel.getRoot();
				if (nodepath == null)
				{
					return null;
				}
				return findRootNodeOfNodename(root, nodepath);
			} catch (Exception e)
			{
				LogUtil.debug("", e);
				return null;
			}

		}

		private BindNode findRootNodeOfNodename(BindNode root, String nodepath)
		{
			if (root != null)
			{
				while (root != null)
				{
					if (root.getXPath().equals(nodepath))
					{
						return root;
					}
					if (root.getChildCount() == 0)
					{
						return null;
					}
					root = root.getChildAt(0);
				}
			}
			return null;
		}

		private boolean isAllSetOk()
		{
			String filepath = (String) filecom.getValue();
			if (filepath == null)
			{
				filecom.requestFocus();
				return false;
			}
			filepath = filepath.trim();
			if (filepath.isEmpty())
			{
				filecom.requestFocus();
				return false;
			}
			BindNode root = (BindNode) rootcom.getValue();
			if (root == null)
			{
				rootcom.requestFocus();
				return false;
			}
			List<ColumnItem> cis = columnsettable.getColumnItems();
			if (cis == null)
			{
				return false;
			}
			return true;
		}

		private DataSource getDataSource()
		{
			String file = (String) filecom.getValue();
			if (file == null)
			{
				return null;
			}
			BindNode root = (BindNode) rootcom.getValue();
			if (root == null)
			{
				return null;
			}
			List<ColumnItem> cis = columnsettable.getColumnItems();
			if (cis == null || cis.isEmpty())
			{
				return null;
			}
			List<String> columnnames = new ArrayList<String>();
			List<Integer> valuenumbers = new ArrayList<Integer>();
			int size = cis.size();
			for (int i = 0; i < size; i++)
			{
				ColumnItem columnitem = cis.get(i);
				if (columnitem != null)
				{
					columnnames.add(columnitem.getColumnname());
					if (columnitem.isKey())
					{
						valuenumbers.add(i);
					}
				}
			}
			if (columnnames.isEmpty())
			{
				return null;
			}
			if (valuenumbers.isEmpty())
			{
				valuenumbers = null;
			}
			return new TreeSource(file, root.getXPath(), columnnames,
					valuenumbers);
		}

		public class NodeEditorComponent extends TextAndButtonComponent
		{

			private JPopupMenu pop;

			WiseDocTree tree;

			JScrollPane scrpanel;

			public NodeEditorComponent(BindNode node)
			{
				setValue(node);
				txtComp.setEditable(false);
				btnComp.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent e)
					{
						tree = new WiseDocTree();
						tree.setCellRenderer(new DataStructureCellRender());
						tree
								.addTreeSelectionListener(new TreeSelectionListener()
								{

									public void valueChanged(
											TreeSelectionEvent e)
									{
										TreePath path = tree.getSelectionPath();
										if (path != null)
										{
											BindNode node = (BindNode) tree
													.getSelectionPath()
													.getLastPathComponent();
											setValue(node);
											pop.setVisible(false);
											pop = null;
										}
									}
								});
						scrpanel = new JScrollPane();
						scrpanel.getViewport().setView(tree);
						pop = new JPopupMenu();
						pop.add(scrpanel);
						BindNode node = (BindNode) getValue();
						if (node != null)
						{
							BindNode root = node;
							while (root.getParent() != null)
							{
								root = root.getParent();
							}
							tree.setModel(new DefaultTreeModel(root));
							pop.show(btnComp, 0, 0);
						}
					}
				});
			}
		}

	}

	private List<ColumnItem> createColumnInfos()
	{
		if (datasource != null && datasource instanceof TreeSource)
		{
			TreeSource source = (TreeSource) datasource;
			List<String> columnnames = source.getColumninfo();
			List<Integer> valuenumbers = source.getValuenumber();
			if (columnnames != null && !columnnames.isEmpty())
			{
				List<ColumnItem> cts = new ArrayList<ColumnItem>();
				int size = columnnames.size();
				for (int i = 0; i < size; i++)
				{
					String columnname = columnnames.get(i);
					boolean iskey = valuenumbers != null
							&& valuenumbers.contains(i);
					cts.add(new ColumnItem(columnname, iskey));
				}
				return cts;
			}
		}
		return null;
	}

	public boolean isAllSettingRight()
	{
		int selectindex = typebox.getSelectedIndex();
		if (selectindex == 0)
		{
			return tablesourcepanel.isAllSetOk();
		} else
		{
			return treesourcepanel.isAllSetOk();
		}

	}

	public DataSource getDataSource()
	{
		int selectindex = typebox.getSelectedIndex();
		if (selectindex == 0)
		{
			return tablesourcepanel.getDataSource();
		} else
		{
			return treesourcepanel.getDataSource();
		}

	}
}
