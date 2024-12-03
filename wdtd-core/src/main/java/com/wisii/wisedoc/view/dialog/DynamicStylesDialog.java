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
 * @DynamicStylesDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.dialog;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.CellEditor;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.AttributeContainer;
import com.wisii.wisedoc.document.attribute.AttributeManager;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.ConditionItemCollection;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.component.EditorBaseComponent;
import com.wisii.wisedoc.view.component.TextAndButtonComponent;

/**
 * 类功能描述：动态样式设置用对话框。
 * 
 * 作者：李晓光 创建日期：2009-3-17
 */
@SuppressWarnings("serial")
public class DynamicStylesDialog extends AbstractWisedocDialog
{

	/**
	 * 定义动态样式设置的位置类型
	 */
	public static enum DynamicStylesClass
	{
		/* 当前文本 */
		Text(com.wisii.wisedoc.view.ui.parts.text.FontPropertyPanel.class),
		/* 当前段落 */
		Paragraph(com.wisii.wisedoc.view.dialog.ParagraphPanel.class),
		/* 单元格 */
		Cell(com.wisii.wisedoc.view.ui.parts.table.TableCellPropertyPanel.class),
		/* 行 */
		Row(com.wisii.wisedoc.view.ui.parts.table.TableRowPropertyPanel.class),
		/* 表 */
		Table(com.wisii.wisedoc.view.ui.parts.table.TablePropertyPanel.class),
		/* SVG图 */
		SVG,
		/* Container */
		Container(com.wisii.wisedoc.view.ui.parts.blockcontainer.BlockContainerPropertyPanel.class),
		/* 条码 */
		Barcod(com.wisii.wisedoc.view.ui.parts.barcode.BarcodePropertyPanel.class),
		/* 图片 */
		Image(com.wisii.wisedoc.view.ui.parts.picture.PicturePropertyPanel.class),
		/* 日期时间 */
		Datetime(com.wisii.wisedoc.view.ui.parts.text.FontPropertyPanel.class),
		/* 格式化数字 */
		Number(com.wisii.wisedoc.view.ui.parts.text.FontPropertyPanel.class),
		/* 当前章节 */
		Chapter(com.wisii.wisedoc.view.dialog.PageSequencePanel.class),
		/* 当前文档 */
		Document(com.wisii.wisedoc.view.dialog.PageSequencePanel.class),
		/* 不设置 */
		None;

		private DynamicStylesClass()
		{
		}

		private DynamicStylesClass(Class clazzName)
		{
			this.clazzName = clazzName;
		}

		/**
		 * 获得编辑控件的类名称。
		 * 
		 * @return {@link String} 返回指定的编辑控件的泪名称。
		 */
		public Class getClassName()
		{
			return clazzName;
		}

		/**
		 * 根据指定的对话框拥有者，创建对话框。
		 * 
		 * @param parent
		 *            指定对话框的拥有者。
		 * @return {@link AbstractWisedocDialog} 返回创建好的对话框。
		 */
		public AbstractWisedocDialog createDialog(JDialog parent,
				Map<Integer, Object> map)
		{
			PropertyStylesDialog dialog = new PropertyStylesDialog(parent, map);
			JComponent comp = buildCenterUI();
			if ((comp instanceof PropertyPanelInterface))
			{
				PropertyPanelInterface panel = (PropertyPanelInterface) comp;
				panel.install(map);
			}
			dialog.setMainPanel(comp);
			return dialog;
		}

		/**
		 * 构建当前类型的编辑控件
		 * 
		 * @return {@link Component} 返回当前类型的编辑控件。
		 */
		private JComponent buildCenterUI()
		{
			Class name = getClassName();
			if (name==null)
				return null;
			try
			{
				Object comp = WisedocUtil.newInstance(name);
				if (!(comp instanceof JComponent))
					return null;

				return (JComponent) comp;
			} catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}

		/** 指定控件的类名称 */
		private Class clazzName = null;
	}

	@SuppressWarnings("unchecked")
	public DynamicStylesDialog(JFrame fr)
	{
		this((Map) null, DynamicStylesClass.Container);
	}

	public DynamicStylesDialog(JDialog parent)
	{
		super(parent, TITLE, true);
	}

	/**
	 * 根据指定的属性集合、指定的设置类型，初始化对话框
	 * 
	 * @param map
	 *            指定属性结合。
	 * @param kind
	 *            指定设置类型【是设置当前文本、当前段落、当前文档、当前章节】
	 */
	public DynamicStylesDialog(Map<Integer, Object> map, DynamicStylesClass kind)
	{
		super();
		setTitle(TITLE);
		initDialog(map, kind);
	}

	/* 初始化当前对话框的必要信息 */
	private void initDialog(Map<Integer, Object> map, DynamicStylesClass kind)
	{
		setSize(600, 400);
		setLayout(new BorderLayout());
		setKind(kind);
		setAttributes(map);

		/* btnPanel.setBorder(ROUND_BORDER); */
		btnPanel.add(eastPanel);
		btnPanel.add(btnOK);
		btnPanel.add(btnCancel);
		setOkButton(btnOK);
		setCancelButton(btnCancel);
		/* table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); */
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(20);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		/* table.setSurrendersFocusOnKeystroke(true); */
		eastPanel.add(btnAdd);
		eastPanel.add(btnSubtract);
		eastPanel.add(btnUP);
		eastPanel.add(btnDown);

		JPanel temp = new JPanel(new BorderLayout());
		temp.setBorder(ROUND_BORDER);
		temp.add(eastPanel, BorderLayout.CENTER);
		temp.add(btnPanel, BorderLayout.EAST);
		mainPanel.add(pane, BorderLayout.CENTER);
		add(mainPanel, BorderLayout.CENTER);
		add(temp, BorderLayout.SOUTH);
		initActions();
		initTable(map);
	}

	/* 初始化当前表 */
	private void initTable(Map<Integer, Object> map) {
		if (isNull(map))
			return;
		ConditionItemCollection items = collectItems(map);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rows = model.getRowCount();
		for (ConditionItem item : items)
		{
			map = item.getStyles();
			if (isNull(map) || map.isEmpty())
				continue;
			model.setRowCount(++rows);
			model.setValueAt(item.getCondition(), rows - 1, 1);
			model.setValueAt(item.getStyles(), rows - 1, 2);
		}
		updateColoum1(model);
	}

	private ConditionItemCollection collectItems(Map<Integer, Object> map) {
		ConditionItemCollection items = new ConditionItemCollection();
		if (isNull(map))
			return items;
		Object obj = map.get(Constants.PR_DYNAMIC_STYLE);
		if (isNull(obj))
			return items;
		if (!(obj instanceof List))
			return items;
		items = (ConditionItemCollection) obj;
		return items;
	}

	/* 根据指定的对象属性Map，包含动态样式KEY，提取出用于初始化属性编辑界面的Map */
	private Map<Integer, Object> createInitializeMap(Map<Integer, Object> map,
			Map<Integer, Object> styles) {
		Map<Integer, Object> result = new HashMap<Integer, Object>();
		if (!isNull(map) && !map.isEmpty())
			result.putAll(map);
		result.remove(Constants.PR_DYNAMIC_STYLE);
		updateValues(result, styles);
		return result;
	}

	/* 把条件中的属性值，设置到用于初始化属性界面Map中 */
	private Map<Integer, Object> updateValues(Map<Integer, Object> result, Map<Integer, Object> styles) {
		if (isNull(styles))
			return result;
		Set<Integer> set = styles.keySet();
		Object value = null;
		for (Integer key : set) {
			value = styles.get(key);
			if (isNull(value))
				continue;
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 初始化该对话框中的按钮的Action
	 * 
	 */
	private void initActions()
	{
		InputMap im = btnAdd.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = btnAdd.getActionMap();
		String ADD_ACTION = "ADD", SUBTRACT_ACTION = "SUBTRACT";
		// 添加行按钮
		btnAdd.setMnemonic(KeyEvent.VK_ADD);
		im.put(KeyStroke.getKeyStroke("ctrl L"), ADD_ACTION);
		am.put(ADD_ACTION, new AbstractAction()
		{

			public void actionPerformed(ActionEvent e)
			{
				btnAdd.doClick();
			}
		});
		// 删除行按钮
		btnSubtract.setMnemonic(KeyEvent.VK_SUBTRACT);
		im = btnSubtract.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		am = btnSubtract.getActionMap();
		im.put(KeyStroke.getKeyStroke("ctrl K"), SUBTRACT_ACTION);
		am.put(SUBTRACT_ACTION, new AbstractAction()
		{

			public void actionPerformed(ActionEvent e)
			{
				btnSubtract.doClick();
			}
		});
		// 确认按钮
		btnOK.addActionListener(listener);
		// 取消按钮
		btnCancel.addActionListener(listener);
		btnAdd.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				CellEditor editor = table.getCellEditor();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int rowCount = model.getRowCount();
				if (editor != null)
					editor.stopCellEditing();

				model.setRowCount(rowCount + 1);
				table.changeSelection(rowCount, 0, false, false);
				updateColoum1(model);
			}
		});
		btnSubtract.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				CellEditor editor = table.getCellEditor();
				if (editor != null)
					editor.stopCellEditing();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int rowCount = model.getRowCount();
				int selRow = table.getSelectedRow();
				if (rowCount <= 0 || selRow == -1)
				{
					return;
				}
				model.removeRow(selRow);
				if (selRow == 0)
				{
					selRow = 0;
				} else if (rowCount <= selRow + 1)
					selRow -= 1;
				table.changeSelection(selRow, 0, false, false);
				updateColoum1(model);
			}
		});
		btnUP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				upOrDownRow(-1, table);
			}
		});
		btnDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				upOrDownRow(1, table);
			}
		});
	}

	private void updateColoum1(DefaultTableModel model)
	{
		int rowCount = model.getRowCount();
		for (int i = 0; i < rowCount; i++)
		{
			table.setValueAt((i + 1), i, 0);
		}
	}

	/* step > 0 表示向下移动，否则表示向上移动当前行。 */
	private void upOrDownRow(int step, JTable table)
	{
		int index = table.getSelectedRow();
		if (step == 0 || index == -1)
			return;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int row = (index + step);
		if (row >= model.getRowCount() || row < 0)
			return;
		model.moveRow(index, index, row);
		table.changeSelection(row, 0, false, false);
		updateColoum1(model);
	}

	/**
	 * 返回设置好的属性集合。【仅包含改动项目】
	 * 
	 * @return {@link Map} 返回设置好的属性集合
	 */
	public Map<Integer, Object> getAttributes()
	{
		return buildResultMap(this.attributes);
	}

	@SuppressWarnings("unchecked")
	private Map<Integer, Object> buildResultMap(Map<Integer, Object> map)
	{
		int rows = table.getRowCount();
		ConditionItemCollection items = new ConditionItemCollection(rows);
		Object cond = null, prop = null;
		for (int i = 0; i < rows; i++)
		{
			cond = table.getValueAt(i, 1);
			prop = table.getValueAt(i, 2);
			if (isNull(cond) || isNull(prop))
				continue;
			items.add(new ConditionItem((LogicalExpression) cond, getAttrubuteWithMap(getKind(), (Map<Integer, Object>) prop)));
		}
		map = new HashMap<Integer, Object>();
		map.put(Constants.PR_DYNAMIC_STYLE, items);
		return map;
	}

	/* 把Map封装为Attributes */
	private Attributes getAttrubuteWithMap(DynamicStylesClass clazz,
			Map<Integer, Object> map)
	{
		Class key = getClazz(clazz);
		AttributeContainer cont = AttributeManager.getAttributeContainer(key);
		return cont.getAttribute(map);
	}

	/* 把当前的类型转换为相应的Class类型 */
	private Class getClazz(DynamicStylesClass clazz)
	{
		Class c = Object.class;
		switch (clazz)
		{
			case Barcod:
			case Datetime:
			case Number:
				c = Inline.class;
				break;
			case Paragraph:
				c = Block.class;
				break;
			case Cell:
				c = TableCell.class;
				break;
			case Row:
				c = TableRow.class;
				break;
			case Table:
				c = Table.class;
				break;
			case Text:
				c = TextInline.class;
				break;
			case Image:
				c = ImageInline.class;
				break;
			case Chapter:
				c = PageSequence.class;
				break;
		}
		return c;
	}

	/**
	 * 根据设置的不同【当前文本、当前段落、当前章节……】 指定不同的初始化属性集合。 初始化样式设置控件。
	 * 
	 * @param attributes
	 *            指定属性集合。
	 */
	public void setAttributes(Map<Integer, Object> attributes)
	{
		this.attributes = attributes;
	}

	/**
	 * 获得当前的数据样式来源类型。
	 * 
	 * @return {@link DynamicStylesClass} 返回获得的数据样式来源类型。
	 */
	public DynamicStylesClass getKind()
	{
		return kind;
	}

	/**
	 * 指定当前设置动态样式的数据样式来源类型【当前文本、当前段落，当前章节】
	 * 
	 * @param kind
	 *            指定数据样式来源类型。
	 */
	public void setKind(DynamicStylesClass kind)
	{
		this.kind = kind;
	}

	public void setMainPanel(JComponent comp)
	{
		mainPanel = comp;
	}

	public void setBtuttonPanel(JComponent comp)
	{
		btnPanel = comp;
	}

	/* 用于初始对话的属性集合 */
	private Map<Integer, Object> attributes = null;

	/* 定义当前的类型 */
	private DynamicStylesClass kind = DynamicStylesClass.None;

	/* 定义确认按钮 */
	private JButton btnOK = new JButton(TEXT_OK_BUTTON);

	/* 定义撤销按钮 */
	private JButton btnCancel = new JButton(TEXT_CANCEL_BUTTON);

	/* 定义放置确认、取消按钮的Panel */
	private JComponent btnPanel = new JPanel(
			new FlowLayout(FlowLayout.TRAILING));

	/* 定义放置确认、取消按钮的Panel */
	private JComponent mainPanel = new JPanel(new BorderLayout());

	private final static String ADD_LINE = getMessage("wisedoc.dynamic.style.dialog.add");

	private final static String SUBTRACT_LINE = getMessage("wisedoc.dynamic.style.dialog.sub");

	private final static String UP_LINE = getMessage("wisedoc.dynamic.style.dialog.up");

	private final static String DOWN_LINE = getMessage("wisedoc.dynamic.style.dialog.down");

	/* 添加行按钮 */
	private JButton btnAdd = new JButton(ADD_LINE);// 添加行

	/* 删除行按钮 */
	private JButton btnSubtract = new JButton(SUBTRACT_LINE);// 删除行

	/* 上移当前行 */
	private JButton btnUP = new JButton(UP_LINE);

	/* 下移当前行 */
	private JButton btnDown = new JButton(DOWN_LINE);

	private JComponent eastPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	/* 定义当前对话框的标题 */
	private final static String TITLE = MessageResource
			.getMessage("wisedoc.dynamic.style.dialog.title");

	/* 定义Panel的边框 */
	private Border ROUND_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private final static String NUMBER = getMessage("wisedoc.dynamic.style.dialog.number");

	private final static String CONDITION = getMessage("wisedoc.dynamic.style.dialog.condition");

	private final static String STYLES = getMessage("wisedoc.dynamic.style.dialog.style");

	/* 定义表的列 */
	private Object[] colNames =
	{ NUMBER, CONDITION, STYLES };

	private static String getMessage(String key)
	{
		return MessageResource.getMessage(key);
	}

	/* 主对话框的编辑用表 */
	private JTable table = new JTable(new DefaultTableModel(colNames, 0)
	{

		public boolean isCellEditable(int row, int column)
		{
			return (column != 0);
		}
	})
	{

		public TableCellEditor getCellEditor(int row, int column)
		{
			if (column > 0)
				return new ConditionEditor();
			else
				return super.getCellEditor(row, column);
		}

		public TableCellRenderer getCellRenderer(int row, int column)
		{
			if (column == 0)
				return new RowNumber();
			return super.getCellRenderer(row, column);
		}
	};

	/* 放在行首用于表示行号 */
	private class RowNumber extends JButton implements TableCellRenderer
	{

		RowNumber()
		{
			setMargin(new Insets(0, 0, 0, 0));
			setUI(new BasicButtonUI());
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column)
		{
			String s = "";
			if (!isNull(value))
				s += value;
			setText(s);
			return this;
		}
	}

	private JScrollPane pane = new JScrollPane(table);

	/* 定义按钮监听 */
	private ActionListener listener = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			Component comp = (Component) e.getSource();
			if (comp == btnOK)
			{
				setResult(DialogResult.OK);
				int rows = table.getRowCount();
				for (int i = 0; i < rows; i++)
				{
					if (isNull(table.getValueAt(i, 1))
							|| isNull(table.getValueAt(i, 2)))
					{
						table.setRowSelectionInterval(i, i);
						table.setColumnSelectionInterval(1, 1);
						return;
					}
				}
			}
			DynamicStylesDialog.this.dispose();
		}
	};

	/* 定义JTable的编辑器 */
	private class ConditionEditor extends javax.swing.AbstractCellEditor
			implements TableCellEditor, PropertyChangeListener
	{

		EditorBaseComponent com;;

		private ConditionEditor()
		{
		}

		@SuppressWarnings("unchecked")
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (column == 1)
				com = new ConditionEditorComponent((LogicalExpression) value);
			else if (column == 2) {

				com = new PropertyStylesEditorComponent((Map<Integer, Object>) value);
			}
			com.setValue(value);
			com.addPropertyChangeListener(this);
			return com;
		}

		public Object getCellEditorValue() {
			return com.getValue();
		}

		public void propertyChange(PropertyChangeEvent evt) {
			stopCellEditing();
		}
	}

	/* 条件设置列编辑器控件 */
	private class ConditionEditorComponent extends TextAndButtonComponent
	{

		private ConditionEditorComponent(LogicalExpression exp) {
			txtComp.setEditable(false);
			init(exp);
		}

		private void init(final LogicalExpression exp)
		{
			btnComp.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ConditionEditorDialog dialog = new ConditionEditorDialog(
							exp);
					dialog.setLocationRelativeTo(DynamicStylesDialog.this);
					DialogResult result = dialog.showDialog();
					if (result == DialogResult.OK)
					{
						setValue(dialog.getLogicalExpression());
					} else
						setCancel();
				}
			});
		}
	}

	/* 样式设置列编辑器控件 */
	private class PropertyStylesEditorComponent extends TextAndButtonComponent {
		PropertyStylesEditorComponent(Map<Integer, Object> map) {
			txtComp.setEditable(false);
			init(map);
		}

		private void init(final Map<Integer, Object> map) {
			btnComp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DynamicStylesClass kind = getKind();
					Map<Integer, Object> temp = createInitializeMap(DynamicStylesDialog.this.attributes, map);
					PropertyStylesDialog dialog = (PropertyStylesDialog) kind.createDialog(DynamicStylesDialog.this, temp);
					DialogResult result = dialog.showDialog();
					if (result == DialogResult.OK) {
						Map<Integer, Object> m = dialog.getStyles();
						if (isNull(m) || m.isEmpty())
							setCancel();
						else {
							Map<Integer,Object> newmap = new HashMap<Integer, Object>();
							if (!isNull(map) && !map.isEmpty()){
								newmap.putAll(map);
							}
							newmap.putAll(m);
							setValue(newmap);
						}
					} else
						setCancel();
				}
			});
		}
	}

	public static void main(String[] args)
	{
		try
		{
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e)
		{
			LogUtil.errorException(e.getMessage(), e);
		}
		final JFrame fr = new JFrame();
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(600, 400);
		JPanel panel = new JPanel();
		JButton btnOK = new JButton("确定");
		panel.add(btnOK);
		fr.add(panel);
		btnOK.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DynamicStylesDialog dialog = new DynamicStylesDialog(fr);
				DialogResult result = dialog.showDialog();
			}
		});
		Map<Integer, String> map = new HashMap<Integer, String>(3);
		map.put(1, "first");
		map.put(2, "second");
		map.put(3, "third");
		System.out.println("map = " + map);
		fr.setVisible(true);
	}
}
