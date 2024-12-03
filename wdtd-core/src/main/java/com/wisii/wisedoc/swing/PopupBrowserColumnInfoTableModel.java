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
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserColumnInfo;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-8-31
 */
public class PopupBrowserColumnInfoTableModel extends AbstractTableModel {

	private List<PopupBrowserColumnInfo> browserColumninfos = new ArrayList<PopupBrowserColumnInfo>();

	public PopupBrowserColumnInfoTableModel(List<PopupBrowserColumnInfo> columninfos) {
		if (columninfos != null) {
			for (PopupBrowserColumnInfo columninfo : columninfos) {
				this.browserColumninfos.add(new PopupBrowserColumnInfo(columninfo.getName(),columninfo.getOptionpath()));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 2;
	}

	public boolean HaveOptionPath() {
		boolean flg = false;
		if (browserColumninfos != null && !browserColumninfos.isEmpty())
		{
			for (PopupBrowserColumnInfo columninfo : browserColumninfos)
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
	public int getRowCount() {
		return browserColumninfos.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		PopupBrowserColumnInfo columnitem = browserColumninfos.get(rowIndex);
		if (columnIndex == 0) {
			return columnitem.getName();
		} else {
			return columnitem.getOptionpath();
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		PopupBrowserColumnInfo columnitem = browserColumninfos.get(rowIndex);
		if (columnIndex == 0) {
			columnitem.setName((String)value);
		}  else {
			columnitem.setOptionpath((BindNode) value);
		}
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "数据项";
		}  else {
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
	public List<PopupBrowserColumnInfo> getPopupBrowserColumnInfos() {

		return browserColumninfos;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		return super.getColumnClass(columnIndex);

	}
	
}