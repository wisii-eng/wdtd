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
 * @ColumnSetTable.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import com.wisii.wisedoc.swing.DataSourceTableModel.ColumnItem;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-1
 */
public class ColumnSetTable extends JTable
{
	private TableCellEditor columneditor=null;

	public ColumnSetTable(List<ColumnItem> columnitems)
	{
		int size = 0;
		if (columnitems != null)
		{
			size = columnitems.size();
		}
		Object[][] datas = new Object[size][2];
		String[] columnnams = new String[size];
		for (int i = 0; i < size; i++)
		{
			ColumnItem item = columnitems.get(i);
			columnnams[i] = item.getColumnname();
			datas[i][0] = item.getColumnname();
			datas[i][1] = item.isKey();
		}
		columneditor = new DefaultCellEditor(new JComboBox(columnnams));
		super.setModel(new DefaultTableModel(datas, new String[]
		{ RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_COLUMN_NAME,
				RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_COLUMN_ISKEY }));
		getTableHeader().setReorderingAllowed(false);
	}
    public void reInitTable(List<ColumnItem> columnitems)
    {
		int size = 0;
		if (columnitems != null)
		{
			size = columnitems.size();
		}
		Object[][] datas = new Object[size][2];
		String[] columnnams = new String[size];
		for (int i = 0; i < size; i++)
		{
			ColumnItem item = columnitems.get(i);
			columnnams[i] = item.getColumnname();
			datas[i][0] = item.getColumnname();
			datas[i][1] = item.isKey();
		}
		columneditor = new DefaultCellEditor(new JComboBox(columnnams));
		setModel(new DefaultTableModel(datas, new String[]
		{ RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_COLUMN_NAME,
				RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_COLUMN_ISKEY }));
	}
	@Override
	public Class<?> getColumnClass(int column)
	{
		if (column == 0)
		{
			return super.getColumnClass(column);
		} else
		{
			return Boolean.class;
		}
	}
    @Override
    public TableCellEditor getCellEditor(int row, int column)
    {
		if (column == 0)
		{
			return columneditor;
		} else
		{
			return super.getCellEditor(row, column);
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column)
	{
		if (column == 1)
		{
			super.setValueAt(value, row, column);
		} else
		{
			String oldname = (String) getValueAt(row, column);
			String columnname = (String) value;
			TableModel model = getModel();
			int rowcount = model.getRowCount();
			for (int i = 0; i < rowcount; i++)
			{
				String cn = (String) model.getValueAt(i, 0);
				if (cn.equals(columnname))
				{
					model.setValueAt(oldname, i, 0);
				}
			}
			model.setValueAt(columnname, row, 0);
		}
	}

	public List<ColumnItem> getColumnItems()
	{
		int rowcount = getRowCount();
		List<ColumnItem> items = new ArrayList<ColumnItem>();
		for (int i = 0; i < rowcount; i++)
		{
			items.add(new ColumnItem((String) getValueAt(i, 0),
					(Boolean) getValueAt(i, 1)));
		}
		if(items.isEmpty())
		{
			items = null;
		}
		return items;
	}
}
