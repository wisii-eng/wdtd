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

package com.wisii.wisedoc.view.panel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.listener.DataStructureChangeListener;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.view.action.EditStructureAction;
import com.wisii.wisedoc.view.action.LoadStructureAction;
import com.wisii.wisedoc.view.action.ReMoveStructureAction;

/**
 * 数据结构树类
 * 
 * 作者：zhangqiang 创建日期：2007-6-11
 */
@SuppressWarnings("serial")
public class ProfileTree extends JTree implements DataStructureChangeListener
{

	// 导入xml数据结构事件
	private Action LoadAction;

	// 编辑数据结构事件
	private Action EditAction;

	// 清除数据结构事件
	private Action ClearAction;

	JPopupMenu _popupmenu;

	public ProfileTree()
	{
		this(null);
	};

	/**
	 * 
	 * @param structuremodel
	 *            :数据结构模型
	 * @exception
	 */
	public ProfileTree(DefaultTreeModel structuremodel)
	{
		setModel(structuremodel);
		setDragEnabled(true);
		setTransferHandler(new TransferHandler(""));
		initPopupMenu();
		setSize(300, 200);
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	/*
	 * 初始化右键菜单
	 */
	private void initPopupMenu()
	{
		initAction();
		_popupmenu = new JPopupMenu();
		_popupmenu.setSize(200, 100);
		JMenuItem loaditem = new JMenuItem();
		JMenuItem edititem = new JMenuItem();

		JMenuItem removeitem = new JMenuItem();
		loaditem.setAction(LoadAction);
		edititem.setAction(EditAction);
		removeitem.setAction(ClearAction);
		_popupmenu.add(loaditem);
		_popupmenu.add(edititem);
		_popupmenu.addSeparator();
		_popupmenu.add(removeitem);

	}

	/*
	 * 初始化所有右键菜单事件类
	 */
	private void initAction()
	{
		LoadAction = new LoadStructureAction(MessageResource
				.getMessage(MessageConstants.DSMESSAGECONSTANTS
						+ MessageConstants.LOADSTRUCTURE));
		EditAction = new EditStructureAction(MessageResource
				.getMessage(MessageConstants.DSMESSAGECONSTANTS
						+ MessageConstants.EDITSTRUCTURE));
		ClearAction = new ReMoveStructureAction(MessageResource
				.getMessage(MessageConstants.DSMESSAGECONSTANTS
						+ MessageConstants.REMOVESTRUCTURE));
	}

	public void DataStructureChanged()
	{
		Document doc = SystemManager.getCurruentDocument();
		setModel(doc.getDataStructure());
	}

	public TreeCellRenderer getCellRenderer()
	{
		return new ProfileCellRender();
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Color old = g.getColor();
		g.setColor(Color.WHITE);
		g.fillRect(8, 15, 1, 9);
		g.setColor(old);
	}

}
