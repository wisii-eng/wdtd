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

package com.wisii.wisedoc.view.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.CellEditor;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.attribute.Sort;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class SortEditorPanel extends JPanel
{

	/* 对话框用【确认、撤销按钮】用Panel */
	private JPanel panelConfirm = new JPanel(new FlowLayout(FlowLayout.CENTER));

	/* 对话框用【添加】按钮 */
	private JButton btnAdd = new JButton(UiText.ADD
	/* getMessage("Wisedoc.Condition.Button.Add") */);

	/* 对话框用【删除】按钮 */
	private JButton btnDelete = new JButton(UiText.DELETE
	/* getMessage("Wisedoc.Condition.Button.Delete") */);

	/* 编辑后产生的组对象 */
	private List<Sort> sorts;

	/* Sort对象主编辑区放置Table对象的 滚动Panel */
	private JScrollPane scrPanelSortCenter = new JScrollPane();

	/* Sort对象编辑区Table */
	private JTable sortTable = new WisedocSortTable();

	/**
	 * 创建编辑用对话框
	 * 
	 * @param 无
	 */
	public SortEditorPanel()
	{
		setSorts(new ArrayList<Sort>());
		this.setPreferredSize(new Dimension(600, 300));
		initComponents();
	}

	public SortEditorPanel(List<Sort> sorts)
	{
		setSorts(sorts);
		this.setPreferredSize(new Dimension(600, 300));
		initComponents();
	}

	/**
	 * 初始化对话框上的所有控件
	 * 
	 * @param 无
	 * @return void 无
	 * @throws ParseException
	 */
	private void initComponents()
	{
		this.setPreferredSize(new Dimension(600, 300));
		scrPanelSortCenter.setPreferredSize(new Dimension(600, 260));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(scrPanelSortCenter);
		scrPanelSortCenter.getViewport().add(sortTable);
		panelConfirm.setPreferredSize(new Dimension(600, 30));
		panelConfirm.add(btnAdd);
		panelConfirm.add(btnDelete);
		this.add(panelConfirm);

		sortTable.getTableHeader().setReorderingAllowed(false);
		sortTable.setSurrendersFocusOnKeystroke(true);

		/* 为对话框中的按钮添加事件 */
		initActions();
	}

	/**
	 * 为对话框上按钮添加Action
	 * 
	 * @param 无
	 * 
	 * @return void 无
	 * 
	 */
	private void initActions()
	{
		btnAdd.addActionListener(new ActionListener()
		{

			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e)
			{

				CellEditor editor = sortTable.getCellEditor();
				if (editor != null)
					editor.stopCellEditing();
				DefaultTableModel model = (DefaultTableModel) sortTable
						.getModel();
				Vector current = new Vector();
				current.add(null);
				// current.add("en");
				current.add(Sort.TEXT);
				current.add(Sort.DESCENDING);
				current.add(Sort.LOWERFIRST);
				model.insertRow(0, current);
				sortTable.changeSelection(0, 0, false, false);
			}
		});
		btnDelete.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				// setRowCount(false, sortTable);

				CellEditor editor = sortTable.getCellEditor();
				if (editor != null)
					editor.stopCellEditing();
				DefaultTableModel model = (DefaultTableModel) sortTable
						.getModel();
				int rowCount = model.getRowCount();
				int selRow = sortTable.getSelectedRow();
				if (rowCount <= 0 || selRow == -1)
				{
					return;
				}
				model.removeRow(selRow);
				if (selRow == 0)
					selRow = 0;
				else
					selRow -= 1;
				sortTable.changeSelection(selRow, 0, false, false);
			}
		});
	}

	/**
	 * 设置当前Sort表的行数
	 * 
	 * @param isAdd
	 *            是添加行：True 否则：False
	 * @param table
	 *            指定Talbe
	 * @return void 无
	 */
	@SuppressWarnings("unused")
	private void setRowCount(boolean isAdd, JTable table)
	{
		CellEditor editor = table.getCellEditor();
		if (editor != null)
			editor.stopCellEditing();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();
		if (isAdd)
		{
			model.setRowCount(rowCount + 1);
			table.changeSelection(rowCount, 0, false, false);
			return;
		}
		int selRow = table.getSelectedRow();
		if (rowCount <= 0 || selRow == -1)
			return;
		model.removeRow(selRow);
		if (selRow == 0)
			selRow = 0;
		else
			selRow -= 1;
		table.changeSelection(selRow, 0, false, false);
	}

	/**
	 * 根据SortTable的信息产生Sort对象集合
	 * 
	 * @param table
	 *            指定用于编辑Sort对象的JTable
	 * @return {@link List} 返回Sort对象的集合
	 */
	private List<Sort> createSortList(JTable table)
	{
		List<Sort> result = null;
		if (table == null)
			return result;
		result = new ArrayList<Sort>();
		for (int row = 0; row < table.getRowCount(); row++)
		{
			BindNode node = (BindNode) table.getValueAt(row, 0);
			Sort temp = Sort.instance(node, "en", (Integer) table.getValueAt(
					row, 1), (Integer) table.getValueAt(row, 2),
					(Integer) table.getValueAt(row, 3));
			if (temp == null)
			{
				table.clearSelection();
				table.setRowSelectionInterval(row, row);
				return null;
			}
			result.add(temp);
		}

		return result;
	}

	/**
	 * 根据枚举值获得实际定义的值
	 * 
	 * @param value
	 *            指定当前值
	 * @return String 如果当前值是枚举值，获得Sort对象中定义的对应的值
	 */
	@SuppressWarnings(
	{ "unused", "unchecked" })
	private String getSortValue(Object value)
	{
		String result = "";
		if (!(value instanceof Enum))
			return result = "" + value;
		Enum type = (Enum) value;
		int index = type.ordinal();
		return result;
	}

	/**
	 * 根据指定Group对象初始化对话框中所有组件的值
	 * 
	 * @param group
	 *            指定group对象
	 * @return void 无
	 */
	private void initComponentValue(List<Sort> sorts)
	{
		if (sorts == null)
			return;
		initSortTable(sortTable, sorts);
	}

	/**
	 * 初始化SortTable中的数据
	 * 
	 * @param table
	 *            指定SortTable 对象
	 * @param list
	 *            指定Sort集合对象
	 * @return void 无
	 */
	private void initSortTable(JTable table, List<Sort> list)
	{
		if (list == null)
			return;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = list.size(), column = 0;

		model.setRowCount(rowCount);
		for (int row = 0; row < rowCount; row++)
		{
			Sort sort = list.get(row);
			column = 0;
			table.setValueAt(sort.getNode(), row, column++);
			// table.setValueAt(sort.getLanguage(), row, column++);
			table.setValueAt(sort.getDataType(), row, column++);
			table.setValueAt(sort.getOrder(), row, column++);
			table.setValueAt(sort.getCaseOrder(), row, column++);
		}
	}

	/**
	 * 设置当前要表示的Group对象
	 * 
	 * @param group
	 *            指定组对象
	 * @return void 无
	 */
	public void setSorts(List<Sort> sorts)
	{
		initComponentValue(sorts);
		this.sorts = sorts;
	}
    public boolean isSettingOK()
    {
    	if(sortTable.getRowCount()==0)
    	{
    		sorts = null;
    		return true;
    	}
    	else
    	{
    		if (sortTable.getCellEditor() != null)
    		{
    			sortTable.getCellEditor().stopCellEditing();
    		}
    		sorts = createSortList(sortTable);
    		return sorts!=null;
    	}
    }
	/**
	 * 设置当前要表示的Group对象
	 * 
	 * @param group
	 *            指定组对象
	 * @return void 无
	 */
	public List<Sort> getSorts()
	{
	

		// if (nsorts != null && !nsorts.isEmpty())
		// {
		// if (nsorts.equals(sorts))
		// {
		// sorts = null;
		// } else
		// {
		// sorts = nsorts;
		// }
		// }

		return sorts;
	}

	public void setButtonEnabled(boolean flg)
	{
		btnAdd.setEnabled(flg);
		btnDelete.setEnabled(flg);
	}

}

/**
 * 类功能说明：重写Table对象，自定义获取编辑器、渲染器方法
 * 
 * 作者：李晓光 创建日期：2007-08-23
 */
@SuppressWarnings("serial")
class WisedocSortTable extends JTable
{

	private Object[] colSortName =
	// { "XML节点", "语言", "数据类型", "升/降序", "优先设置" };
	{ "XML节点", "数据类型", "升/降序", "优先设置" };

	private String[] types = new String[]
	{ "文本", "数字" };

	private String[] orders = new String[]
	{ "升序", "降序" };

	private String[] caseorders = new String[]
	{ "大写优先", "小写优先" };

	NodeChooseEditor nodeeditor = new NodeChooseEditor();

	TableCellEditor typeeditor = new ComboxEditor(types);

	TableCellEditor ordereditor = new ComboxEditor(orders);

	TableCellEditor caseordereditor = new ComboxEditor(caseorders);

	// TableCellEditor langeditor;

	public WisedocSortTable()
	{
		super();
		// try
		// {
		// // langeditor = new DefaultCellEditor(new JFormattedTextField(
		// // new MaskFormatter("??")));
		// } catch (ParseException e)
		// {
		// e.printStackTrace();
		// }
		setModel(new DefaultTableModel(colSortName, 0));
	}

	public TableCellEditor getCellEditor(int row, int column)
	{
		if (column == 0)
		{
			return nodeeditor;
		}
		// else if (column == 1)
		// {
		// return langeditor;
		// }
		else if (column == 1)
		{
			return typeeditor;
		} else if (column == 2)
		{
			return ordereditor;
		} else
		{
			return caseordereditor;
		}
	}

	public TableCellRenderer getCellRenderer(int row, int column)
	{
		return new SortTableRenderer(column);
	}

	/**
	 * 类功能说明：自定Table的渲染器。
	 * 
	 * 作者：李晓光 创建日期：2007-08-23
	 */
	class SortTableRenderer extends DefaultTableCellRenderer
	{

		private int column = 0;

		public SortTableRenderer(int column)
		{
			this.column = column;
		}

		public void setValue(Object value)
		{
			value = getRealValue(column, value);
			super.setValue(value);
		}

		/**
		 * 把字符串转换为枚举对象的枚举值
		 * 
		 * @param column
		 *            指定当前的列
		 * @param value
		 *            指定当前值
		 * @return 如果是枚举类型返回对应的枚举对象，否则返回本身。
		 */
		private Object getRealValue(int column, Object value)
		{
			if (value == null)
			{
				return null;
			}
			if (column == 0)
			{
				return value;
			} else
			{
				if (value instanceof Integer)
				{
					int index = (Integer) value;
					if (column == 1)
					{
						return types[index];
					} else if (column == 2)
					{
						return orders[index];
					} else
					{
						return caseorders[index];
					}
				}
				return null;
			}
		}

	}
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
		pop.setPreferredSize(new Dimension(editorComponent.getWidth(), 100));
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
			node = (BindNode) tree.getSelectionPath().getLastPathComponent();
			stopCellEditing();
			tree.removeTreeSelectionListener(this);
			tree.clearSelection();
			pop.setVisible(false);
			pop = null;

		}

	}
}

@SuppressWarnings("serial")
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
		return box.getSelectedIndex();
	}
}