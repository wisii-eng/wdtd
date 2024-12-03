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
 * @SeriesStypeDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.NumberContent;
import com.wisii.wisedoc.document.attribute.ChartData2D;
import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.component.ForEachEditorComponent;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：在弹出对话框【菜单中】，设置系列样式。 【系列对应的颜色、系列的文本【可能来自绑定节点】】 作者：李晓光 创建日期：2009-5-23
 */
public class SeriesStypeDialog extends AbstractWisedocDialog {
	public SeriesStypeDialog(final int rows, final int columns,
			final Map<Integer, Object> map) {
		final JPanel main = new JPanel(new BorderLayout());
		Object chart = Constants.EN_BARCHART;
		if (map != null) {
			chart = map.get(Constants.PR_CHART_TYPE);
		}
		if (chart == null) {
			type = Constants.EN_BARCHART;
		} else {
			final EnumProperty pro = (EnumProperty) chart;
			type = pro.getEnum();
		}
		table = createTable(rows, columns);
		final JScrollPane scr = new JScrollPane(table);
		final JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		btnPanel.add(btnOK);
		btnPanel.add(btnCancel);

		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(20);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(Boolean.TRUE);
		table.setColumnSelectionAllowed(Boolean.TRUE);

		main.add(scr, BorderLayout.CENTER);
		main.add(btnPanel, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(550, 200));
		setOkButton(btnOK);
		setCancelButton(btnCancel);
		add(main);
		setTitle(TITLE);
		pack();
		setAttributes(map);

		initActions();
	}

	private void initActions() {
		final Actions action = new Actions() {
			@Override
			public void doAction(final ActionEvent e) {
				if (e.getSource() == btnOK) {
					final TableCellEditor editor = table.getCellEditor();
					if (editor != null) {
						editor.stopCellEditing();
					}
					result = DialogResult.OK;
				}
				dispose();
			}
		};
		btnOK.addActionListener(action);
		btnCancel.addActionListener(action);
	}

	public Map<Integer, Object> getAttributes() {
		final Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(Constants.PR_SERIES_VALUE, getValues());
		map.put(Constants.PR_VALUE_LABEL, getTypes());
		map.put(Constants.PR_SERIES_LABEL, getSeries());
		map.put(Constants.PR_VALUE_COLOR, getColors());
		return map;
	}

	private List<Color> getColors() {
		final List<Color> list = new ArrayList<Color>();
		if (type != Constants.EN_PIECHART) {// EN_BARCHART
			for (int i = 1, rows = table.getRowCount(), col = table
					.getColumnCount() - 1; i < rows; i++) {
				list.add((Color) table.getValueAt(i, col));
			}
		} else {
			for (int i = 2, cols = table.getColumnCount(), row = table
					.getRowCount() - 1; i < cols; i++) {
				list.add((Color) table.getValueAt(row, i));
			}
		}
		return list;
	}

	private List<BarCodeText> getTypes() {
		Object value = null;
		final List<BarCodeText> list = new ArrayList<BarCodeText>();
		BarCodeText text = null;
		int column = table.getColumnCount();
		if (type != Constants.EN_PIECHART) {// EN_BARCHART
			column--;
		}
		for (int i = 2/* , column = table.getColumnCount() */; i < column; i++) {
			value = table.getValueAt(0, i);
			if (value instanceof BindNode) {
				text = new BarCodeText((BindNode) value);
			} else if (value != null) {
				text = new BarCodeText(value + "");
			}
			list.add(text);
		}
		return list;
	}

	private List<BarCodeText> getSeries() {
		Object value = null;
		final List<BarCodeText> list = new ArrayList<BarCodeText>();
		BarCodeText text = null;
		int row = table.getRowCount();
		if (type == Constants.EN_PIECHART) {
			row--;
		}
		for (int i = 1/* , row = table.getRowCount() */; i < row; i++) {
			value = table.getValueAt(i, 1);
			if (value instanceof BindNode) {
				text = new BarCodeText((BindNode) value);
			} else if (value != null) {
				text = new BarCodeText(value + "");
			}
			list.add(text);
		}
		return list;
	}

	private ChartDataList getValues() {
		final ChartDataList list = new ChartDataList();
		Object value = null;
		NumberContent content = null;
		int row = table.getRowCount();
		int column = table.getColumnCount();
		if (type != Constants.EN_PIECHART) {// EN_BARCHART
			column--;
		} else {
			row--;
		}
		for (int i = 1/* , row = table.getRowCount() */; i < row; i++) {
			for (int j = 2/* , column = table.getColumnCount() */; j < column; j++) {
				value = table.getValueAt(i, j);
				if (value == null || "".equals(value)) {
					content = null;
					continue;
				}
				if (value instanceof BindNode) {
					content = new NumberContent((BindNode) value);
				} else if (value != null && !"".equals(value)) {
					content = new NumberContent(getNumber(value + ""));
				}
				list.add(new ChartData2D((i - 1), (j - 2), content));
			}
		}
		Collections.sort(list);
		return list;
	}

	private Number getNumber(final String value) {
		return WisedocUtil.getDouble(value);
	}

	@SuppressWarnings("unchecked")
	protected void setAttributes(final Map<Integer, Object> map) {
		if (map.isEmpty()) {
			return;
		}
		setSeries((List<BarCodeText>) map.get(Constants.PR_SERIES_LABEL));
		setValues((ChartDataList) map.get(Constants.PR_SERIES_VALUE));
		setTypes((List<BarCodeText>) map.get(Constants.PR_VALUE_LABEL));
		setColors((List<Color>) map.get(Constants.PR_VALUE_COLOR));
	}

	private void setColors(final List<Color> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		if (type != Constants.EN_PIECHART) {// EN_BARCHART
			int index = 0;
			for (int i = 1, rows = table.getRowCount(), col = table
					.getColumnCount() - 1, size = list.size(); i < rows; i++) {
				if (index >= size) {
					break;
				}
				table.setValueAt(list.get(index), i, col);
				index++;
			}
		} else {
			int index = 0;
			for (int i = 2, cols = table.getColumnCount(), row = table
					.getRowCount() - 1, size = list.size(); i < cols; i++) {
				if (index >= size) {
					break;
				}
				table.setValueAt(list.get(index), row, i);
				index++;
			}
		}
	}

	private void setValues(final ChartDataList list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		NumberContent cont = null;
		for (final ChartData2D data : list) {
			cont = data.getNumbercontent();
			table.setValueAt(cont.isBindContent() ? cont.getBindNode() : cont
					.getNumber(), data.getIndex1d() + 1, data.getIndex2d() + 2);
		}
	}

	private void setSeries(final List<BarCodeText> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		int row = 1;
		for (final BarCodeText text : list) {
			if (text == null) {
				row++;
				continue;
			}
			table.setValueAt(text.isBindContent() ? text.getBindNode() : text
					.getText(), row++, 1);
		}
	}

	private void setTypes(final List<BarCodeText> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		int column = 2;
		for (final BarCodeText text : list) {
			if (text == null) {
				column++;
				continue;
			}
			table.setValueAt(text.isBindContent() ? text.getBindNode() : text
					.getText(), 0, column++);
		}
	}

	@SuppressWarnings("serial")
	private JTable createTable(int rows, int columns) {
		if (type != Constants.EN_PIECHART) {// EN_BARCHART
			columns += 1;
		} else {
			rows += 1;
		}
		final JTable table = new JTable(new DefaultTableModel(rows + 1,
				columns + 2) {
			@Override
			public boolean isCellEditable(final int row, final int column) {
				boolean flag = ((column == 0) || (column == 1 && row == 0));
				if (type != Constants.EN_PIECHART) {// EN_BARCHART
					flag |= ((row == 0) && column == getColumnCount() - 1);
				} else {
					flag |= ((column == 1) && row == getRowCount() - 1);
				}
				return !flag;
			}
		}) {

			@Override
			public TableCellEditor getCellEditor(final int row, final int column) {
				if ((column == 0) || (column == 1 && row == 0)) {
					return super.getCellEditor(row, column);
				} else if (type != Constants.EN_PIECHART
						&& (column == getColumnCount() - 1)) {// EN_BARCHART
					if (row > 0) {
						return COLOR_EDITOR;
					} else {
						return super.getCellEditor(row, column);
					}
				} else if (type == Constants.EN_PIECHART
						&& (row == getRowCount() - 1)) {
					if (column > 1) {
						return COLOR_EDITOR;
					} else {
						return super.getCellEditor(row, column);
					}
				} else {
					return editor;
				}
			}

			@Override
			public TableCellRenderer getCellRenderer(final int row,
					final int column) {

				if (column == 0) {
					return NUMBER_RENDER/* new RowNumber() */;
				} else if (type != Constants.EN_PIECHART
						&& (column == getColumnCount() - 1)) {// EN_BARCHART
					return COLOR_RENDER/* new ColorRender() */;
				} else if (type == Constants.EN_PIECHART
						&& (row == getRowCount() - 1)) {
					return COLOR_RENDER/* new ColorRender() */;
				}
				return super.getCellRenderer(row, column);
			}
		};
		final DefaultTableModel model = (DefaultTableModel) table.getModel();
		final DefaultTableColumnModel colModel = (DefaultTableColumnModel) table
				.getTableHeader().getColumnModel();

		updateTableHeader(colModel);

		updateColoum1(model);
		updateTypes(model);
		updateSeries(model);

		return table;
	}

	private void updateTableHeader(final DefaultTableColumnModel model) {
		final List<Object> temp = new ArrayList<Object>();
		for (int i = 0, size = model.getColumnCount(); i < size; i++) {
			final TableColumn col = model.getColumn(i);
			temp.add(col.getHeaderValue());
		}
		temp.add(0, "");
		for (int i = 0, size = model.getColumnCount(); i < size; i++) {
			final TableColumn col = model.getColumn(i);
			col.setHeaderValue(temp.get(i));
		}
	}

	private void updateTypes(final DefaultTableModel model) {
		final String s = "种类";
		for (int i = 2, column = model.getColumnCount(); i < column; i++) {
			if (i == column - 1 && type != Constants.EN_PIECHART) {// EN_BARCHART
				model.setValueAt(SERIES_COLOR, 0, i);
				continue;
			}
			model.setValueAt(s + (i - 1), 0, i);
		}
	}

	private void updateSeries(final DefaultTableModel model) {
		for (int i = 1, rows = model.getRowCount(); i < rows; i++) {
			if (i == rows - 1 && type == Constants.EN_PIECHART) {
				model.setValueAt(VALUE_COLOR, i, 1);
				continue;
			}
			model.setValueAt(i + "", i, 1);
		}
	}

	private void updateColoum1(final DefaultTableModel model) {
		final int rowCount = model.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			model.setValueAt((i + 1), i, 0);
		}
	}

	/* 放在行首用于表示行号 */
	@SuppressWarnings("serial")
	private class RowNumber extends JButton implements TableCellRenderer {

		RowNumber() {
			setMargin(new Insets(0, 0, 0, 0));
			setUI(new BasicButtonUI());
		}

		@Override
		public Component getTableCellRendererComponent(final JTable table,
				final Object value, final boolean isSelected,
				final boolean hasFocus, final int row, final int column) {
			String s = "";
			if (!isNull(value)) {
				s += value;
			}
			setText(s);
			return this;
		}
	}

	/* 定义表的列 */
	private JTable table = null;
	private final static JTextField text = new JTextField();
	{
		text.setBorder(null);
	}
	/*private final NodeChooseEditor editor = new NodeChooseEditor(text);*/
	private final ParmEditor editor = new ParmEditor();
	private final ColorEditor COLOR_EDITOR = new ColorEditor();
	private final ColorRender COLOR_RENDER = new ColorRender();
	private final RowNumber NUMBER_RENDER = new RowNumber();
	private final JButton btnOK = new JButton(UiText.DIALOG_OK/*
															 * MessageResource.getMessage
															 * (
															 * "wsd.view.gui.dialog.ok"
															 * )
															 */);
	private final JButton btnCancel = new JButton(UiText.DIALOG_CANCEL/*
																	 * MessageResource.
																	 * getMessage
																	 * (
																	 * "wsd.view.gui.dialog.cancel"
																	 * )
																	 */);
	private int type = Constants.EN_BARCHART;// EN_PIECHART【饼状图】;
	private final static String TITLE = UiText.SERIES_STYLE_DIALOG_TITLE;// MessageResource.getMessage("wsd.series.style.dialog.title");//"值设置";
	private final static String VALUE_COLOR = UiText.SERIES_STYLE_DIALOG_VALUE_COLOR;// MessageResource.getMessage("wsd.series.style.dialog.value.color");//值颜色
	private final static String SERIES_COLOR = UiText.SERIES_STYLE_DIALOG_SERIES_COLOR;// MessageResource.getMessage("wsd.series.style.dialog.series.color");//系列颜色

	@SuppressWarnings("serial")
	private class ColorEditor extends DefaultCellEditor {
		ColorComboBox colorEditor = null;
		private Color color = null;
		private final JTable table = null;

		ColorEditor() {
			super(new JTextField());
			setClickCountToStart(1);
			try {
				colorEditor = new ColorComboBox();
			} catch (final IncompatibleLookAndFeelException e) {
				e.printStackTrace();
			}
			colorEditor.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					color = (Color) colorEditor.getSelectedItem();
					stopCellEditing();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(final JTable table,
				final Object value, final boolean isSelected, final int row,
				final int column) {
			if ((value instanceof Color)) {
				color = (Color) value;
			}
			colorEditor.setSelectedItem(color);

			return colorEditor;
		}

		@Override
		public Object getCellEditorValue() {
			return color;
		}
	}

	@SuppressWarnings("serial")
	private class ColorRender extends DefaultTableCellRenderer {
		Color color = null;

		@Override
		public Component getTableCellRendererComponent(final JTable table,
				final Object value, final boolean isSelected,
				final boolean hasFocus, final int row, final int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			if (value instanceof Color) {
				color = (Color) value;
				setText("");
			} else {
				color = null;
				final String s = (value == null) ? "" : value + "";
				setText(s);
			}
			/*
			 * if (isSelected) { setBackground(table.getSelectionBackground());
			 * setForeground(table.getSelectionForeground()); } else {
			 * setBackground(table.getBackground());
			 * setForeground(table.getForeground()); }
			 */
			return this;
		}

		@Override
		public void paint(final Graphics g) {
			super.paint(g);
			if (color == null) {
				return;
			}
			final Rectangle bound = this.getBounds();
			int x = 0;
			int y = 0;
			int w = bound.width;
			int h = bound.height;
			final int offsetX = 15;
			final int offsetY = 5;
			x += offsetX;
			y += offsetY;
			w -= (2 * offsetX);
			h -= (2 * offsetY);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, w, h);
			g.setColor(color);
			g.fillRect(x, y, w, h);
		}
	}

	private class ParmEditor extends javax.swing.AbstractCellEditor implements
			TableCellEditor, PropertyChangeListener {

		ForEachEditorComponent com;;

		private ParmEditor() {
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			com = new ForEachEditorComponent();
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

	/* 主对话框的编辑用表 */
	@SuppressWarnings("serial")
	private class NodeChooseEditor extends DefaultCellEditor implements
			FocusListener, TreeSelectionListener {
		private JPopupMenu pop;
		WiseDocTree tree;
		private BindNode node;
		JScrollPane scrpanel;
		private boolean flag = Boolean.FALSE;

		private NodeChooseEditor(final JTextField text) {
			super(text);

			init();
		}

		@Override
		public boolean isCellEditable(final EventObject e) {
			if (e instanceof MouseEvent) {
				final MouseEvent mouse = (MouseEvent) e;
				return mouse.getClickCount() > 0;
			} else {
				return Boolean.TRUE;
			}
		}

		private void init() {
			editorComponent.addFocusListener(this);
			tree = new WiseDocTree();
			tree.setCellRenderer(new DataStructureCellRender());
			scrpanel = new JScrollPane();
			scrpanel.getViewport().setView(tree);
			editorComponent.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent e) {
					if (e.getButton() != MouseEvent.BUTTON3) {
						return;
					}

					tree.clearSelection();
					tree.addTreeSelectionListener(NodeChooseEditor.this);
					pop = new JPopupMenu();
					int width = editorComponent.getWidth();
					if (width < 180) {
						width = 180;
					}
					pop.setPreferredSize(new Dimension(width, 200));
					pop.add(scrpanel);
					final int height = editorComponent.getHeight();
					final Document doc = SystemManager.getCurruentDocument();
					if (doc != null && doc.getDataStructure() != null) {
						tree.setModel(doc.getDataStructure());
						pop.validate();
						pop.show(editorComponent, 0, height);
					}
				}
			});
		}

		public void focusGained(final FocusEvent e) {
			/*
			 * tree.clearSelection(); tree.addTreeSelectionListener(this); pop =
			 * new JPopupMenu(); int width = editorComponent.getWidth();
			 * if(width < 180) { width = 180; } pop.setPreferredSize(new
			 * Dimension(width, 200)); pop.add(scrpanel); final int height =
			 * editorComponent.getHeight(); final Document doc =
			 * SystemManager.getCurruentDocument(); if (doc != null &&
			 * doc.getDataStructure() != null) {
			 * tree.setModel(doc.getDataStructure()); pop.validate();
			 * pop.show(editorComponent, 0, height); }
			 */
		}

		public void focusLost(final FocusEvent e) {

		}

		@Override
		public Object getCellEditorValue() {
			if (flag) {
				flag = Boolean.FALSE;
				return node;
			} else {
				final JTextField txt = (JTextField) editorComponent;
				return txt.getText();
			}
		}

		public void valueChanged(final TreeSelectionEvent e) {
			final TreePath path = tree.getSelectionPath();
			if (path != null) {
				flag = Boolean.TRUE;
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