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
 * @SelectColumnInFOesTableModel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.transform.SelectColumnInFO;
import com.wisii.wisedoc.document.attribute.transform.Column.ColumnType;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-8-31
 */
public class SelectColumnInFOesTableModel extends AbstractTableModel
{

	private List<SelectColumnInFO> columninfos = new ArrayList<SelectColumnInFO>();

	public SelectColumnInFOesTableModel(List<SelectColumnInFO> columninfos)
	{
		if (columninfos != null)
		{
			for (SelectColumnInFO columninfo : columninfos)
			{
				this.columninfos.add(new SelectColumnInFO(columninfo
						.getDatacolumnindex(), columninfo.isIsviewable(),
						columninfo.getOptionpath(),
						columninfo.getSearchorder(), columninfo.getSortorder(),
						columninfo.getViewname(), columninfo.getColumntype()));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount()
	{
		// TODO Auto-generated method stub
		return 7;
	}

	public boolean HaveOptionPath()
	{
		boolean flg = false;
		if (columninfos != null && !columninfos.isEmpty())
		{
			for (SelectColumnInFO columninfo : columninfos)
			{
				if (columninfo.getOptionpath() != null)
				{
					return true;
				}
			}
		} else
		{
			return true;
		}
		return flg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount()
	{
		// TODO Auto-generated method stub
		return columninfos.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		SelectColumnInFO columnitem = columninfos.get(rowIndex);
		if (columnIndex == 0)
		{
			return columnitem.getDatacolumnindex();
		} else if (columnIndex == 1)
		{
			return columnitem.isIsviewable();
		} else if (columnIndex == 2)
		{
			return columnitem.getViewname();
		} else if (columnIndex == 3)
		{
			return columnitem.getColumntype();
		} else if (columnIndex == 4)
		{
			return columnitem.getSortorder();
		} else if (columnIndex == 5)
		{
			return columnitem.getSearchorder();
		} else
		{
			return columnitem.getOptionpath();
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		SelectColumnInFO columnitem = columninfos.get(rowIndex);
		if (columnIndex == 0)
		{
		} else if (columnIndex == 1)
		{
			columnitem.setIsviewable((Boolean) value);
		} else if (columnIndex == 2)
		{
			columnitem.setViewname((String) value);
		} else if (columnIndex == 3)
		{
			columnitem.setColumntype((ColumnType) value);
		} else if (columnIndex == 4)
		{
			columnitem.setSortorder((Integer) value);
		} else if (columnIndex == 5)
		{
			columnitem.setSearchorder((Integer) value);
		} else
		{
			columnitem.setOptionpath((BindNode) value);
		}
	}

	@Override
	public String getColumnName(int column)
	{
		if (column == 0)
		{
			return RibbonUIText.EDIT_SELECTINFO_COLUMN_INDEX;
		} else if (column == 1)
		{
			return RibbonUIText.EDIT_SELECTINFO_COLUMN_ISVIEWABLE;
		} else if (column == 2)
		{
			return RibbonUIText.EDIT_SELECTINFO_COLUMN_VIEWNAME;
		} else if (column == 3)
		{
			return RibbonUIText.EDIT_SELECTINFO_COLUMN_COLUMNTYPE;
		} else if (column == 4)
		{
			return RibbonUIText.EDIT_SELECTINFO_COLUMN_SORTORDER;
		} else if (column == 5)
		{
			return RibbonUIText.EDIT_SELECTINFO_COLUMN_SEARCHORDER;
		} else
		{
			return RibbonUIText.EDIT_SELECTINFO_COLUMN_OPTIONPATH;
		}
	}

	/**
	 * 
	 * {方法的功能/动作描述}:
	 * 
	 * @param 引入参数
	 *            : {引入参数说明}
	 * @return List<FileSource>: {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public List<SelectColumnInFO> getSelectColumnInFOs()
	{

		return columninfos;
	}

	@Override
	public boolean isCellEditable(int row, int column)
	{
		if (column == 0)
		{
			return false;
		} else
		{
			return true;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		if (columnIndex == 1)
		{
			return Boolean.class;
		} else
		{
			return super.getColumnClass(columnIndex);
		}
	}
}
