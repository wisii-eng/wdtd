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
 * @DataStructureTree.java * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.TransferHandler;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.listener.DataStructureChangeListener;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.basic.WsdTransferable;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.action.ReMoveStructureAction;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 数据结构树类
 * 
 * 作者：zhangqiang 创建日期：2007-6-11
 */
public class DataStructureTree extends JTree implements
		DataStructureChangeListener
{

	// 导入xml数据结构事件
	private Action LoadAction;
	// 导入数据库表数据结构事件
	private Action LoadSQLAction;

	// 编辑数据结构事件
	private Action EditAction;

	// 清除数据结构事件
	private Action ClearAction;

	public static final String NODETAG = "path";

	public static final String ROOTNODETAG = "paths";

	public static final String INDEXTAG = "index";

	protected class MyTransferHandler extends TransferHandler
	{

		protected Transferable createTransferable(JComponent c)
		{
			JTree tree = (JTree) c;
			TreePath path = tree.getSelectionPath();
			BindNode node = (BindNode) path.getLastPathComponent();
			return new WsdTransferable(node);
		}

		public int getSourceActions(JComponent c)
		{
			return TransferHandler.COPY_OR_MOVE;
		}

		public boolean importData(JComponent comp, Transferable t)
		{
			return true;
		}

		protected MyTransferHandler()
		{
		}
	}

	JPopupMenu _popupmenu;

	public DataStructureTree()
	{
		this(null);
	};

	/**
	 * 
	 * @param structuremodel
	 *            :数据结构模型
	 * @exception
	 */
	public DataStructureTree(DataStructureTreeModel structuremodel)
	{
		setModel(structuremodel);
		setDragEnabled(true);
		setTransferHandler(new MyTransferHandler());
		initPopupMenu();
		setSize(300, 200);
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		ToolTipManager.sharedInstance().registerComponent(DataStructureTree.this);
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent mouseevent)
			{
				if (SwingUtilities.isRightMouseButton(mouseevent))
				{
					// 设置右键菜单的可用性
					TreeModel model = getModel();
					ClearAction.setEnabled(model != null);
					boolean enableedit = false;
					if (model != null)
					{
						DataStructureTreeModel datamodel = (DataStructureTreeModel) model;
						enableedit = datamodel.isOtherdata();
					}
					EditAction.setEnabled(enableedit);
					// 弹出右键菜单
					_popupmenu.show(DataStructureTree.this, mouseevent
							.getPoint().x, mouseevent.getPoint().y);
				} else if (SwingUtilities.isLeftMouseButton(mouseevent)
						&& mouseevent.getClickCount() == 2)
				{
					TreePath path = DataStructureTree.this.getSelectionPath();
					if (path != null)
					{
						BindNode node = (BindNode) path.getLastPathComponent();
						Document doc = SystemManager.getCurruentDocument();

						if (doc != null)
						{
							AbstractEditComponent editor = RibbonUpdateManager.Instance
									.getCurrentEditPanel();
							DocumentPosition pos = editor.getCaretPosition();
							List<CellElement> cells = editor
									.getSelectionModel().getObjectSelection();
							if (pos != null)
							{
								CellElement el = pos.getLeafElement();
								Attributes attrs = null;
								Map<Integer, Object> map = null;
								if (el instanceof TextInline)
								{
									attrs = el.getAttributes();
								}
								if (attrs != null)
								{
									map = attrs.getAttributes();
								}
								doc.insertString(node, pos, pos
										.getInlineAttriute());
							} else if (cells.size() == 1)
							{
								CellElement element = cells.get(0);
								if (element instanceof AbstractGraphics)
								{
									element = (CellElement) element.getParent();
								}
								if (element instanceof Inline)
								{
									Map<Integer, Object> attrs = new HashMap<Integer, Object>();
									attrs.put(Constants.PR_INLINE_CONTENT,
											new Text(node));
									doc.setElementAttributes(element, attrs,
											false);
								}
							}
						}
					}
				}
			}
		});
		setCellRenderer(new DataStructureCellRender());
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
		JMenuItem loadsqlitem = new JMenuItem();
		loadsqlitem.setAction(LoadSQLAction);
		edititem.setAction(EditAction);
		removeitem.setAction(ClearAction);
		_popupmenu.add(loaditem);
		_popupmenu.add(loadsqlitem);
		_popupmenu.add(edititem);
		_popupmenu.addSeparator();
		_popupmenu.add(removeitem);

	}

	/*
	 * 初始化所有右键菜单事件类
	 */
	private void initAction()
	{
		LoadAction = ActionFactory.getAction(Defalult.LOAD_STRUCTURE_ACTION);
		LoadAction.putValue(Action.NAME, MessageResource
				.getMessage(MessageConstants.DSMESSAGECONSTANTS
						+ MessageConstants.LOADSTRUCTURE));
		LoadSQLAction = ActionFactory
				.getAction(Defalult.LOAD_SQL_STRUCTURE_ACTION);
		LoadSQLAction.putValue(Action.NAME, MessageResource
				.getMessage(MessageConstants.DSMESSAGECONSTANTS
						+ MessageConstants.LOADSQLSTRUCTURE));

		EditAction = ActionFactory.getAction(Defalult.EDIT_STRUCTURE_ACTION);
		EditAction.putValue(Action.NAME, MessageResource
				.getMessage(MessageConstants.DSMESSAGECONSTANTS
						+ MessageConstants.EDITSTRUCTURE));
		ClearAction = new ReMoveStructureAction(MessageResource
				.getMessage(MessageConstants.DSMESSAGECONSTANTS
						+ MessageConstants.REMOVESTRUCTURE));
	}

	/**
	 * 
	 * 获得根节点路径
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public String getRootpath()
	{
		BindNode root;
		String s = "";
		if (getModel() != null && getModel().getRoot() != null)
		{
			root = (BindNode) getModel().getRoot();
			for (int i = 0; i < root.getChildCount(); i++)
			{

				if (root.getChildAt(i) instanceof BindNode)
				{
					continue;
				} else
				{
					s = s + root.toString() + "/"
							+ root.getChildAt(i).toString();
					break;
				}
			}
		}
		return s;
	}

	public void DataStructureChanged()
	{
		Document doc = SystemManager.getCurruentDocument();
		setModel(doc.getDataStructure());
	}

}
