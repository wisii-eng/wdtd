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

package com.wisii.wisedoc.view.data;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.wisii.db.ConnectionSeting;
import com.wisii.db.util.DBUtil;
import com.wisii.wisedoc.document.listener.DataStructureChangeListener;
import com.wisii.wisedoc.view.data.DataSourceNode.NodeType;

@SuppressWarnings("serial")
public class DataSourceTree extends JTree implements
		DataStructureChangeListener
{

	private static Map<String, ConnectionSeting> cons = new HashMap<String, ConnectionSeting>();

	public static int chushihuaflg = 0;

	JPopupMenu popupmenu;

	JPopupMenu popupmenuroot;

	Point currentpoint;

	JMenuItem rootadditem;

	JMenuItem alldeleteitem;

	JMenuItem additem;

	JMenuItem deleteitem;

	Control control;

	public DataSourceTree()
	{
		super();
	}

	/**
	 * 
	 * @param structuremodel
	 *            :数据结构模型
	 * @exception
	 */
	public DataSourceTree(DataSourceTreeModel structuremodel)
	{
		setModel(structuremodel);
		setDragEnabled(true);
		setTransferHandler(new TransferHandler(""));
		initPopupMenu();
		setSize(180, 290);
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		getSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener()
				{

					@Override
					public void valueChanged(TreeSelectionEvent e)
					{
						TreePath path = e.getPath();
						control.getresult();
						control.setCurrentnode((DataSourceNode) path
								.getLastPathComponent());
					}
				});
		addMouseListener(new MouseAdapter()
		{

			public void mousePressed(MouseEvent mouseevent)
			{

				if (mouseevent.getButton() == MouseEvent.BUTTON3)
				{
					DataSourceNode currentnode = control.getCurrentnode();
					if (currentnode != null)
					{
						Point current = mouseevent.getPoint();
						currentpoint = new Point(current.x, current.y);
						NodeType type = currentnode.getNodetype();
						if (type == NodeType.ROOT)
						{
							popupmenuroot.show(DataSourceTree.this, current.x,
									current.y);
						} else if (type == NodeType.DATASOUREITEM)
						{
							popupmenu.show(DataSourceTree.this, current.x,
									current.y);

						}
					}
				}
			}
		});
		DataSourceTree.this.updateUI();
	}

	public TreeCellRenderer getCellRenderer()
	{
		return new DataSourceCellRender();
	}

	public void initPopupMenu()
	{
		popupmenuroot = new JPopupMenu();
		popupmenuroot.setSize(200, 100);

		rootadditem = new JMenuItem("添加数据源");
		RootAddDataSourceAction rootadd = new RootAddDataSourceAction();
		rootadditem.addActionListener(rootadd);

		alldeleteitem = new JMenuItem("删除所有数据源");
		AlldeleteAction alldeleteAction = new AlldeleteAction();
		alldeleteitem.addActionListener(alldeleteAction);

		popupmenuroot.add(rootadditem);
		popupmenuroot.add(alldeleteitem);

		popupmenu = new JPopupMenu();
		popupmenu.setSize(200, 100);

		additem = new JMenuItem("添加数据源");
		AddAction addAction = new AddAction();
		additem.addActionListener(addAction);

		deleteitem = new JMenuItem("删除当前数据源");
		DeleteAction deleteAction = new DeleteAction();
		deleteitem.addActionListener(deleteAction);

		popupmenu.add(additem);
		popupmenu.add(deleteitem);
	}

	class RootAddDataSourceAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			ConnectionSeting current = getDefaultConnectionSeting((ConnectionSeting) DBUtil
					.getDefaultcs());
			DataSourceNode newnode = new DataSourceNodeReferece(current);
			addDataSource(current.getName(), current);
			control.getCurrentnode().addChild(newnode, 0);
			DataSourceTree.this.updateUI();
			DataSourceTree.this.setSelectionRow(1);
			chushihuaflg++;
		}
	}

	class AlldeleteAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			control.getCurrentnode().reMoveAllChildren();
			DataSourceTree.this.updateUI();
			getCons().clear();
			DataSourceTree.this.setSelectionRow(0);
		}
	}

	class AddAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			ConnectionSeting current = getDefaultConnectionSeting((ConnectionSeting) DBUtil
					.getDefaultcs());
			DataSourceNode newnode = new DataSourceNodeReferece(current);
			addDataSource(current.getName(), current);
			DataSourceNode node = control.getCurrentnode();
			int currentindex = node.getParent().getIndex(node);
			node.getParent().addChild(newnode, currentindex + 1);
			DataSourceTree.this.updateUI();
			DataSourceTree.this.setSelectionRow(currentindex + 2);
			chushihuaflg++;
		}
	}

	class DeleteAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			DataSourceNode node = control.getCurrentnode();
			int currentindex = node.getParent().getIndex(node);
			node.getParent().reMoveChildren(node);
			DataSourceTree.this.setSelectionRow(currentindex);
			reMoveDataSource(node.getValue().getName());
			DataSourceTree.this.updateUI();
		}
	}

	@Override
	public void DataStructureChanged()
	{

	}

	public Control getControl()
	{
		return control;
	}

	public void setControl(Control control)
	{
		this.control = control;
	}

	public Map<String, ConnectionSeting> getDataSources()
	{
		control.getresult();
		return getCons();
	}

	public static Map<String, ConnectionSeting> getCons()
	{
		return cons;
	}

	public static void setCons(Map<String, ConnectionSeting> cons)
	{
		DataSourceTree.cons = cons;
	}

	public static void addDataSource(String name, ConnectionSeting datasource)
	{
		Map<String, ConnectionSeting> map = getCons();
		map.put(name, datasource);
	}

	public static void reMoveDataSource(String name)
	{
		Map<String, ConnectionSeting> map = getCons();
		map.remove(name);
	}

	public ConnectionSeting getDefaultConnectionSeting(ConnectionSeting cons)
	{
		String name = new String(cons.getName());
		if ("".equals(name.trim()))
		{
			name = DataSourceNodeReferece.DATASOURCEITEM + chushihuaflg;
		}
		while (isHaveName(name))
		{
			chushihuaflg++;
			name = DataSourceNodeReferece.DATASOURCEITEM + chushihuaflg;
		}
		String driverclass = new String(cons.getDriverclassType());
		String username = new String(cons.getUsername());
		String password = new String(cons.getPassword());
		String url = new String(cons.getUrl());
		ConnectionSeting clonecs = new ConnectionSeting(name, driverclass, url,
				username, password);
		return clonecs;
	}

	public static boolean isHaveName(String name)
	{
		Map<String, ConnectionSeting> map = getCons();
		return map.containsKey(name);
	}

}
