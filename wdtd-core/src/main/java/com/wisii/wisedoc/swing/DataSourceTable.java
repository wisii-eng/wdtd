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
 * @DataSourceTable.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.transform.FileSource;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.swing.DataSourceTableModel.ColumnItem;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.component.FileEditorComponent;
import com.wisii.wisedoc.view.component.TextAndButtonComponent;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.ribbon.edit.DataSourceSettingDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：数据源选择表类
 * 
 * 作者：zhangqiang 创建日期：2009-8-29
 */
public final class DataSourceTable extends JTable
{

	private final FileEditor fileeditor = new FileEditor();

	private final ComboxEditor typeeditor = new ComboxEditor(new String[]
	{ RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_STRUCTURE_TABLE1,
			RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_STRUCTURE_TABLE2 });

	private final NodeChooseEditor rooteditor = new NodeChooseEditor();

	private final ColumnEditor columneditor = new ColumnEditor();

	private final TypeRenderer typerender = new TypeRenderer();

	public DataSourceTable(List<FileSource> filesources)
	{
		super(new DataSourceTableModel(filesources));
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column)
	{
		if (column == 0)
		{
			return fileeditor;
		} else if (column == 1)
		{
			return typeeditor;
		} else if (column == 2)
		{
			return rooteditor;
		} else
		{
			return columneditor;
		}
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column)
	{
		if (column == 1)
		{
			return typerender;
		} else
		{
			return super.getCellRenderer(row, column);
		}
	}

	/**
	 * 类功能说明：自定Table的渲染器。
	 * 
	 * 作者：李晓光 创建日期：2007-08-23
	 */
	class TypeRenderer extends DefaultTableCellRenderer
	{

		public TypeRenderer()
		{
		}

		public void setValue(Object value)
		{
			int type = (Integer) value;
			String values = RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_STRUCTURE_TABLE1;
			if (type == Constants.EN_TABLE2)
			{
				values = RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_STRUCTURE_TABLE2;
			}
			super.setValue(values);
		}
	}

	private class FileEditor extends javax.swing.AbstractCellEditor implements
			TableCellEditor, PropertyChangeListener
	{

		FileEditorComponent com;;

		private FileEditor()
		{
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			com = new FileEditorComponent((String) value);
			com.addPropertyChangeListener(this);
			return com;
		}

		public Object getCellEditorValue()
		{
			return com.getValue();
		}

		public void propertyChange(PropertyChangeEvent evt)
		{
			stopCellEditing();
			DataSourceTable.this.repaint();
		}
	}

	class ComboxEditor extends DefaultCellEditor
	{

		private JComboBox box;

		ComboxEditor(String[] model)
		{
			super(new JComboBox(model));
			box = (JComboBox) editorComponent;

		}

		public Object getCellEditorValue()
		{
			int index = box.getSelectedIndex();
			if (index == 0)
			{
				return Constants.EN_TABLE1;
			} else
			{
				return Constants.EN_TABLE2;
			}
		}
		@Override
	    public boolean stopCellEditing()
		{
			boolean isstop = super.stopCellEditing();
			DataSourceTable.this.repaint();
			return isstop;
		}
	}

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

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			node = (BindNode) value;
			return super.getTableCellEditorComponent(table, value, isSelected,
					row, column);
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
			if (node != null)
			{
				BindNode root = node;
				while (root.getParent() != null)
				{
					root = root.getParent();
				}
				tree.setModel(new DefaultTreeModel(root));

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
				DataSourceTable.this.repaint();
				tree.removeTreeSelectionListener(this);
				tree.clearSelection();
				pop.setVisible(false);
				pop = null;
			}
		}
	}

	private class ColumnEditor extends javax.swing.AbstractCellEditor implements
			TableCellEditor, PropertyChangeListener
	{

		ColumnComponent com;;

		private ColumnEditor()
		{
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			com = new ColumnComponent((List<ColumnItem>) value);
			com.addPropertyChangeListener(this);
			return com;
		}

		public Object getCellEditorValue()
		{
			return com.getValue();
		}

		public void propertyChange(PropertyChangeEvent evt)
		{
			stopCellEditing();
		}
	}

	private class ColumnComponent extends TextAndButtonComponent
	{

		List<ColumnItem> columnitems;

		private ColumnComponent(List<ColumnItem> columnitems)
		{
			this.columnitems = columnitems;
			txtComp.setEditable(false);
			setValue(columnitems);
			btnComp.addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent e)
				{
					Container parent = DataSourceTable.this.getParent();
					while (parent != null
							&& !(parent instanceof DataSourceSettingDialog))
					{
						parent = parent.getParent();
					}
					ColumnSettingDialog dtsetdia = new ColumnSettingDialog(
							(Dialog) parent, ColumnComponent.this.columnitems);
					DialogResult result = dtsetdia.showDialog();
					// System.out.println("result:" + result);
					if (DialogResult.OK.equals(result))
					{
						// System.out.println("sdsdssdds");
						setValue(dtsetdia.getColumnitems());
					}
				}
			});
		}

	}

	private class ColumnSettingDialog extends AbstractWisedocDialog
	{

		private JButton okbutton = new JButton(
				getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

		private JButton cancelbutton = new JButton(
				getMessage(MessageConstants.DIALOG_COMMON
						+ MessageConstants.CANCEL));

		private ColumnSetTable columnsettable;

		private List<ColumnItem> columnitems;

		private ColumnSettingDialog(Dialog parent, List<ColumnItem> columnitems)
		{
			super(parent, RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_COLUMN,
					true);
			this.columnitems = columnitems;
			setSize(400, 300);
			JPanel mainpanel = new JPanel(new BorderLayout());
			columnsettable = new ColumnSetTable(columnitems);
			JScrollPane js = new JScrollPane(columnsettable);
			JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING,
					0, 0));
			buttonpanel.add(okbutton);
			buttonpanel.add(cancelbutton);
			mainpanel.add(js, BorderLayout.CENTER);
			mainpanel.add(buttonpanel, BorderLayout.SOUTH);
			getContentPane().add(mainpanel);
			setOkButton(okbutton);
			setCancelButton(cancelbutton);
			DialogSupport.centerOnScreen(this);
			okbutton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (columnsettable.getCellEditor() != null)
					{
						columnsettable.getCellEditor().stopCellEditing();
					}
					ColumnSettingDialog.this.columnitems = columnsettable
							.getColumnItems();
					distroy(DialogResult.OK);
				}
			});
			cancelbutton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					distroy(DialogResult.Cancel);
				}
			});

		}

		/**
		 * @返回 columnitems变量的值
		 */
		private final List<ColumnItem> getColumnitems()
		{
			return columnitems;
		}

		private void distroy(DialogResult result)
		{
			this.result = result;
			setVisible(false);
			dispose();
		}
	}

	public void addRow()
	{
		int rowselect = getSelectedRow();
		if (rowselect < 0)
		{
			rowselect = getRowCount();
		}
		((DataSourceTableModel) getModel()).addRow(rowselect);
		updateUI();
	}

	public void delRow(int rowindex)
	{
		((DataSourceTableModel) getModel()).delRow(rowindex);
		updateUI();
	}

	public List<FileSource> getFileSources()
	{
		return ((DataSourceTableModel) getModel()).getFileSources();
	}

	public boolean isAllSetOk()
	{
		return ((DataSourceTableModel) getModel()).isAllSetOk();
	}
}
