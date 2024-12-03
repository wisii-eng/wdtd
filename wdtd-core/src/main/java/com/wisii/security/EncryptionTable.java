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
package com.wisii.security;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;

public class EncryptionTable extends JTable
{
	private NodeChooseEditor nodeeditor = new NodeChooseEditor();

	public TableCellEditor getCellEditor(int row, int column)
	{
		return nodeeditor;
	}

	@SuppressWarnings("serial")
	class NodeChooseEditor extends DefaultCellEditor implements FocusListener,
			TreeSelectionListener
	{

		private JPopupMenu pop;

		WiseDocTree tree;

		private BindNode node;

		JScrollPane scrpanel;

		NodeChooseEditor()
		{
			super(new JTextField());

			init();
		}

		private void init()
		{
			editorComponent.addFocusListener(this);
			tree = new WiseDocTree();
			tree.setCellRenderer(new DataStructureCellRender());
			scrpanel = new JScrollPane();
			scrpanel.getViewport().setView(tree);

		}

		public void focusGained(FocusEvent e)
		{
			tree.clearSelection();
			tree.addTreeSelectionListener(this);
			pop = new JPopupMenu();
			pop
					.setPreferredSize(new Dimension(editorComponent.getWidth(),
							100));
			pop.add(scrpanel);
			Document doc = SystemManager.getCurruentDocument();
			if (doc != null && doc.getDataStructure() != null)
			{
				tree.setModel(doc.getDataStructure());

				pop.show(editorComponent, 0, 0);
			}

		}

		public void focusLost(FocusEvent e)
		{

		}

		public Object getCellEditorValue()
		{
			return node;
		}

		public void valueChanged(TreeSelectionEvent e)
		{
			TreePath path = tree.getSelectionPath();
			if (path != null)
			{
				node = (BindNode) tree.getSelectionPath()
						.getLastPathComponent();
				stopCellEditing();
				tree.removeTreeSelectionListener(this);
				tree.clearSelection();
				pop.setVisible(false);
				pop = null;

			}

		}
	}
}
