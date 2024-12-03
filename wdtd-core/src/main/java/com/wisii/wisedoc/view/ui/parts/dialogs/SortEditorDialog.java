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
 * @GroupEditorDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.parts.dialogs;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
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
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
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
import javax.swing.text.MaskFormatter;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.attribute.Sort;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;

/**
 * 类功能描述：组对象编辑用对话框
 * 
 * 作者：李晓光 创建日期：2007-08-22
 */
public class SortEditorDialog extends AbstractWisedocDialog
{

	/* 对话框用【确认、撤销按钮】用Panel */
	private final JPanel panelConfirm = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	/* 对话框用【确定】按钮 */
	private final JButton btnOK = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

	/* 对话框用【撤销】按钮 */
	private final JButton btnCancel = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));

	private final int INITINDEX = 0;

	private final MaskFormatter format = null;

	/* 对话框用【添加】按钮 */
	private final JButton btnAdd = new JButton("添加"
	/* getMessage("Wisedoc.Condition.Button.Add") */);

	/* 对话框用【删除】按钮 */
	private final JButton btnDelete = new JButton("删除"
	/* getMessage("Wisedoc.Condition.Button.Delete") */);

	/* 定义窗体的返回状态【确认按钮的触发使得窗体返回：DialogResult.OK】 */
	private DialogResult result = DialogResult.Cancel;

	/* 编辑后产生的组对象 */
	private List<Sort> sorts;

	/* 主Panel 对话框上的所有控件均加载到该Panel上面 */
	private final JPanel mainPanel = new JPanel();

	/* Sort对象主编辑区放置Table对象的 滚动Panel */
	private final JScrollPane scrPanelSortCenter = new JScrollPane();

	/* Sort对象编辑区Table */
	private final JTable sortTable = new WiseDocSortTable();

	/**
	 * 创建编辑用对话框
	 * 
	 * @param 无
	 */
	public SortEditorDialog()
	{
		this(SystemManager.getMainframe(), "排序设置", true);
	}

	public SortEditorDialog(final List<Sort> sorts)
	{
		this();
		setSorts(sorts);
	}

	/**
	 * 创建指定了对话框拥有者、标题、模式的编辑对话框
	 * 
	 * @param owner
	 *            指定对话框的拥有者
	 * @param title
	 *            指定对话框的标题
	 * @param modal
	 *            指定对话框的模式
	 */
	public SortEditorDialog(final Frame owner, final String title, final boolean modal)
	{
		super(owner, title, modal);

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
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		final Container content = getContentPane();
		scrPanelSortCenter.setPreferredSize(new Dimension(400, 100));
		content.add(mainPanel, java.awt.BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(scrPanelSortCenter, java.awt.BorderLayout.CENTER);
		scrPanelSortCenter.getViewport().add(sortTable);
		panelConfirm.add(btnAdd);
		panelConfirm.add(btnDelete);
		panelConfirm.add(btnOK);
		panelConfirm.add(btnCancel);
		mainPanel.add(panelConfirm, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(400, 300));
		sortTable.getTableHeader().setReorderingAllowed(false);
		sortTable.setSurrendersFocusOnKeystroke(true);
		/* 添加ESC、ENTER健处理 */
		setOkButton(btnOK);
		setCancelButton(btnCancel);
		
		pack();

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
		btnOK.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent e)
			{
				if (sortTable.getRowCount() > 0
						&& sortTable.getCellEditor() != null) {
					sortTable.getCellEditor().stopCellEditing();
				}
				final List<Sort> nsorts = createSortList(sortTable);
				if (nsorts != null && !nsorts.isEmpty())
				{
					if (nsorts.equals(sorts))
					{
						sorts = null;
					} else
					{
						sorts = nsorts;
					}
					result = DialogResult.OK;
					dispose();
				}
			}
		});
		btnCancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent e)
			{
				dispose();
			}
		});
		btnAdd.addActionListener(new ActionListener()
		{

			@SuppressWarnings("unchecked")
			public void actionPerformed(final ActionEvent e)
			{
				// setRowCount(true, sortTable);

				final CellEditor editor = sortTable.getCellEditor();
				if (editor != null) {
					editor.stopCellEditing();
				}
				final DefaultTableModel model = (DefaultTableModel) sortTable
						.getModel();
				final Vector current = new Vector();
				current.add(null);
				current.add("en");
				current.add(Sort.TEXT);
				current.add(Sort.DESCENDING);
				current.add(Sort.LOWERFIRST);
				model.insertRow(0, current);
				sortTable.changeSelection(0, 0, false, false);
			}
		});
		btnDelete.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent e)
			{
				// setRowCount(false, sortTable);

				final CellEditor editor = sortTable.getCellEditor();
				if (editor != null) {
					editor.stopCellEditing();
				}
				final DefaultTableModel model = (DefaultTableModel) sortTable
						.getModel();
				final int rowCount = model.getRowCount();
				int selRow = sortTable.getSelectedRow();
				if (rowCount <= 0 || selRow == -1)
				{
					return;
				}
				model.removeRow(selRow);
				if (selRow == 0) {
					selRow = 0;
				} else {
					selRow -= 1;
				}
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
	private void setRowCount(final boolean isAdd, final JTable table)
	{
		final CellEditor editor = table.getCellEditor();
		if (editor != null) {
			editor.stopCellEditing();
		}
		final DefaultTableModel model = (DefaultTableModel) table.getModel();
		final int rowCount = model.getRowCount();
		if (isAdd)
		{
			model.setRowCount(rowCount + 1);
			table.changeSelection(rowCount, 0, false, false);
			return;
		}
		int selRow = table.getSelectedRow();
		if (rowCount <= 0 || selRow == -1) {
			return;
		}
		model.removeRow(selRow);
		if (selRow == 0) {
			selRow = 0;
		} else {
			selRow -= 1;
		}
		table.changeSelection(selRow, 0, false, false);
	}

	/**
	 * 根据SortTable的信息产生Sort对象集合
	 * 
	 * @param table
	 *            指定用于编辑Sort对象的JTable
	 * @return {@link List} 返回Sort对象的集合
	 */
	private List<Sort> createSortList(final JTable table)
	{
		List<Sort> result = null;
		if (table == null) {
			return result;
		}
		result = new ArrayList<Sort>();
		for (int row = 0; row < table.getRowCount(); row++)
		{
			final BindNode node = (BindNode) table.getValueAt(row, 0);
			final Sort temp = Sort.instance(node, (String) table.getValueAt(row, 1),
					(Integer) table.getValueAt(row, 2), (Integer) table
							.getValueAt(row, 3), (Integer) table.getValueAt(
							row, 4));
			if (temp == null)
			{
				table.clearSelection();
				table.setRowSelectionInterval(row, row);
				table.setColumnSelectionInterval(0, 0);
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
	private String getSortValue(final Object value)
	{
		String result = "";
		if (!(value instanceof Enum)) {
			return result = "" + value;
		}
		final Enum type = (Enum) value;
		final int index = type.ordinal();
		// TODO CHANGE
		/*
		 * if (type instanceof DataType) result = DATATYPE[index]; else if (type
		 * instanceof Order) result = ORDER[index]; else if (type instanceof
		 * Priority) result = PRIORITY[index];
		 */
		return result;
	}

	/**
	 * 根据指定Group对象初始化对话框中所有组件的值
	 * 
	 * @param group
	 *            指定group对象
	 * @return void 无
	 */
	private void initComponentValue(final List<Sort> sorts)
	{
		if (sorts == null) {
			return;
		}

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
	private void initSortTable(final JTable table, final List<Sort> list)
	{
		if (list == null) {
			return;
		}
		final DefaultTableModel model = (DefaultTableModel) table.getModel();
		final int rowCount = list.size();
		int column = 0;

		model.setRowCount(rowCount);
		for (int row = 0; row < rowCount; row++)
		{
			final Sort sort = list.get(row);
			column = 0;
			table.setValueAt(sort.getNode(), row, column++);
			table.setValueAt(sort.getLanguage(), row, column++);
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
	public void setSorts(final List<Sort> sorts)
	{
		initComponentValue(sorts);
		this.sorts = sorts;
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
		return sorts;
	}

	/**
	 * 显示对话框
	 * 
	 * @param 无
	 * @return {@link DialogResult} 表示对话框的返回情况
	 */
	@Override
	public DialogResult showDialog()
	{
		if (SystemManager.getCurruentDocument().getDataStructure() == null)
		{
			JOptionPane.showMessageDialog(SystemManager.getMainframe(),
			/* getMessage("Wisedoc.Condition.Warn.Info") */"XML",
			/* getMessage("prompt") */"XML", JOptionPane.WARNING_MESSAGE);
			dispose();
		} else
		{
			DialogSupport.centerOnScreen(this);
			setVisible(true);
		}

		return result;
	}

}

/**
 * 类功能说明：重写Table对象，自定义获取编辑器、渲染器方法
 * 
 * 作者：李晓光 创建日期：2007-08-23
 */
@SuppressWarnings("serial")
class WiseDocSortTable extends JTable
{

	private final Object[] colSortName =
	{ "XML节点", "语言", "数据类型", "升/降序", "优先设置" };

	private final String[] types = new String[]
	{ "文本", "数字", "限定名" };

	private final String[] orders = new String[]
	{ "升序", "降序" };

	private final String[] caseorders = new String[]
	{ "大写优先", "小写优先" };

	NodeChooseEditor nodeeditor = new NodeChooseEditor();

	TableCellEditor typeeditor = new ComboxEditor(types);

	TableCellEditor ordereditor = new ComboxEditor(orders);

	TableCellEditor caseordereditor = new ComboxEditor(caseorders);

	TableCellEditor langeditor;

	public WiseDocSortTable()
	{
		super();
		try
		{
			langeditor = new DefaultCellEditor(new JFormattedTextField(
					new MaskFormatter("??")));
		} catch (final ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setModel(new DefaultTableModel(colSortName, 0));
	}

	@Override
	public TableCellEditor getCellEditor(final int row, final int column)
	{
		if (column == 0)
		{
			return nodeeditor;
		} else if (column == 1)
		{
			return langeditor;
		} else if (column == 2)
		{
			return typeeditor;
		} else if (column == 3)
		{
			return ordereditor;
		} else
		{
			return caseordereditor;
		}
	}

	@Override
	public TableCellRenderer getCellRenderer(final int row, final int column)
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

		public SortTableRenderer(final int column)
		{
			this.column = column;
		}

		@Override
		public void setValue(Object value)
		{
			final String key = "";
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
		private Object getRealValue(final int column, final Object value)
		{
			if (value == null)
			{
				return null;
			}
			if (column == 0 || column == 1)
			{
				return value;
			} else
			{
				if (value instanceof Integer)
				{
					final int index = (Integer) value;
					if (column == 2)
					{
						return types[index];
					} else if (column == 3)
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

	public void focusGained(final FocusEvent e)
	{
		tree.clearSelection();
		tree.addTreeSelectionListener(this);
		pop = new JPopupMenu();
		pop.setPreferredSize(new Dimension(editorComponent.getWidth(), 100));
		pop.add(scrpanel);
		final Document doc = SystemManager.getCurruentDocument();
		if (doc != null && doc.getDataStructure() != null)
		{
			tree.setModel(doc.getDataStructure());

			pop.show(editorComponent, 0, 0);
		}

	}

	public void focusLost(final FocusEvent e)
	{

	}

	@Override
	public Object getCellEditorValue()
	{
		return node;
	}

	public void valueChanged(final TreeSelectionEvent e)
	{
		final TreePath path = tree.getSelectionPath();
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

class ComboxEditor extends DefaultCellEditor
{

	private final JComboBox box;

	ComboxEditor(final String[] model)
	{
		super(new JComboBox(model));
		box = (JComboBox) editorComponent;

	}

	@Override
	public Object getCellEditorValue()
	{
		return box.getSelectedIndex();
	}
}
