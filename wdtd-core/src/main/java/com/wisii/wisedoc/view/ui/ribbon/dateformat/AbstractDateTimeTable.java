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
 * @AbstractDateTimeTable.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.dateformat;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.wisii.wisedoc.document.attribute.AbstractDateTimeInfo;
import com.wisii.wisedoc.document.attribute.DateTimeItem;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DateTimeType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DefaultType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.MonthType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.YearType;
import com.wisii.wisedoc.swing.ui.WiseCombobox;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-6-26
 */
public abstract class AbstractDateTimeTable extends JTable
{
	private TableCellEditor digiteditor = new DigitEditor();
	private TableCellEditor vieweditor = new ViewEditor();

	public AbstractDateTimeTable(AbstractDateTimeInfo info, boolean isdate)
	{
		super(new DateTimeInfoTableModel(info, isdate));
		getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public DateTimeInfoTableModel getModel()
	{
		return (DateTimeInfoTableModel) super.getModel();
	}

	public abstract AbstractDateTimeInfo getInfo();

	protected List<DateTimeItem> getDateTimeItems()
	{
		return getModel().getItems();
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column)
	{
		if (column == 1)
		{
			return digiteditor;
		} else if (column == 2)
		{
			return vieweditor;
		} else
		{
			return super.getCellEditor(row, column);
		}
	}
    public void upSelectItem()
    {
    	int selectrow = getSelectedRow();
    	if(selectrow > 0)
    	{
    		getModel().convertrow(selectrow, selectrow-1);
    		setRowSelectionInterval(selectrow-1, selectrow-1);
    	}
    	
    }
    public void downSelectItem()
    {
    	int selectrow = getSelectedRow();
    	if(selectrow < getRowCount()-1)
    	{
    		getModel().convertrow(selectrow, selectrow+1);
    		setRowSelectionInterval(selectrow+1, selectrow+1);
    	}
    }
	private class DigitEditor extends javax.swing.AbstractCellEditor implements
			TableCellEditor, ActionListener
	{
		WiseCombobox com;;
		boolean isdefaultdigit = false;

		private DigitEditor()
		{
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			com = new WiseCombobox();
			DateTimeItem item = getModel().getDateTimeItem(row);
			DateTimeType type = item.getType();
			if (type == DateTimeType.Year)
			{
				com.setModel(new DefaultComboBoxModel(new String[]
				{ "不输出", "全部", "第一位", "第二位", "第三位", "第四位", "前两位", "中间两位",
						"后两位", "前三位", "后三位" }));
			}

			else if (type == DateTimeType.Hour)
			{
				com.setModel(new DefaultComboBoxModel(new String[]
				{ "不输出", "不补位24时制", "不补位am,pm", "不补位上午,下午", "不补位12时制",
						"补位24时制", "补位am,pm", "补位上午下午", "补位12时制" }));
			} else
			{
				isdefaultdigit = true;
				com.setModel(new DefaultComboBoxModel(new String[]
				{ "不输出", "不补位", "补位" }));
			}
			com.InitValue(value);
			com.addActionListener(this);
			return com;
		}

		public Object getCellEditorValue()
		{
			int index = com.getSelectedIndex();
			if(index==0)
			{
				return -1;
			}
			if (!isdefaultdigit)
			{
				return index - 1;
			}
			return index;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			stopCellEditing();
			AbstractDateTimeTable.this.repaint();
		}
	}

	private class ViewEditor extends javax.swing.AbstractCellEditor implements
			TableCellEditor, ActionListener
	{
		WiseCombobox com;;

		private ViewEditor()
		{
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			com = new WiseCombobox();
			DateTimeItem item = getModel().getDateTimeItem(row);
			DateTimeType type = item.getType();
			if (type == DateTimeType.Year)
			{
				com.setModel(new DefaultComboBoxModel(YearType.values()));
			}

			else if (type == DateTimeType.Month)
			{
				com.setModel(new DefaultComboBoxModel(MonthType.values()));
			} else
			{
				com.setModel(new DefaultComboBoxModel(DefaultType.values()));
			}
			com.InitValue(value);
			com.addActionListener(this);
			return com;
		}

		public Object getCellEditorValue()
		{
			return com.getSelectedItem();
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			stopCellEditing();
		}
	}
}
