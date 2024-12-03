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
package com.wisii.wisedoc.swing.treetable;

/*
 * %W% %E%
 *
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *   
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution. 
 *   
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.  
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,   
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER  
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS 
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * This example shows how to create a simple JTreeTable component, by using a
 * JTree as a renderer (and editor) for the cells in a particular column in the
 * JTable.
 * 
 * @version %I% %G%
 * 
 * @author Philip Milne
 * @author Scott Violet
 */

public class JTreeTable extends JTable
{
	protected TreeTableCellRenderer tree;

	public JTreeTable(TreeTableModel treeTableModel)
	{
		super();
		setRowSelectionAllowed(false);
		// Create the tree. It will be used as a renderer and editor.
		tree = new TreeTableCellRenderer(treeTableModel);

		// Install a tableModel representing the visible rows in the tree.
		super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

		// Force the JTable and JTree to share their row selection models.
		tree.setSelectionModel(new DefaultTreeSelectionModel()
		{
			// Extend the implementation of the constructor, as if:
			/* public this() */{
				setSelectionModel(listSelectionModel);
			}
		});
		// Make the tree and table row heights the same.
		tree.setRowHeight(getRowHeight());
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Install the tree editor renderer and editor.
		setDefaultRenderer(TreeTableModel.class, tree);
		setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());

		setShowGrid(false);
		setIntercellSpacing(new Dimension(0, 0));
	}

	/*
	 * Workaround for BasicTableUI anomaly. Make sure the UI never tries to
	 * paint the editor. The UI currently uses different techniques to paint the
	 * renderers and editors and overriding setBounds() below is not the right
	 * thing to do for an editor. Returning -1 for the editing row in this case,
	 * ensures the editor is never painted.
	 */
	public int getEditingRow()
	{
		return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1
				: editingRow;
	}

	// 
	// The renderer used to display the tree nodes, a JTree.
	//

	public class TreeTableCellRenderer extends JTree implements
			TableCellRenderer
	{

		protected int visibleRow;

		public TreeTableCellRenderer(TreeModel model)
		{
			super(model);
		}

		public void setBounds(int x, int y, int w, int h)
		{
			super.setBounds(x, 0, w, JTreeTable.this.getHeight());
		}

		public int getRowCount()
		{
			return super.getRowCount();
		}

		public void paint(Graphics g)
		{
			g.translate(0, -visibleRow * getRowHeight());
			super.paint(g);
			visibleRow = 0;
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column)
		{
			visibleRow = row;
			if (isSelected)
				setBackground(table.getSelectionBackground());
			else
				setBackground(table.getBackground());
			return this;
		}
	}

	// 
	// The editor used to interact with tree nodes, a JTree.
	//

	class TreeTableCellEditor extends AbstractCellEditor implements
			TableCellEditor
	{
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int r, int c)
		{
			return tree;
		}
	}
	public void setSelectLastChildNode(Node lenode)
	{
		if (lenode != null&&lenode.getChildCount()>0)
		{
			Node childnode = lenode.getChildAt(lenode.getChildCount()-1);
			if(childnode!=null){
			explandNode(childnode);
			updateUI();
			}
		}
	}

	public void setNodeSelect(Node node)
	{
		if (node != null && node.getParent() != null)
		{
			explandNode(node);
			updateUI();
		}

	}

	public void explandNode(Node node)
	{
		if (node != null)
		{
			List<Node> pathobects = new ArrayList<Node>();
			pathobects.add(node);
			Node pnode = node.getParent();
			while (pnode != null)
			{
				pathobects.add(0, pnode);
				pnode = pnode.getParent();
			}
			TreePath path = new TreePath(pathobects.toArray());
			tree.expandPath(path);
			tree.setSelectionPath(path);
			
		}
	}
	public void setTreeTableModel(TreeTableModel treeTableModel)
	{
		if (treeTableModel != null)
		{
			tree.setModel(treeTableModel);
			setModel(new TreeTableModelAdapter(treeTableModel, tree));
		}
	}
}
