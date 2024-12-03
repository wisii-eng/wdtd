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
 * @SelectColumnInFOesTable.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.wisii.wisedoc.document.attribute.transform.PopupBrowserColumnInfo;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserInfo;
import com.wisii.wisedoc.view.component.ForEachEditorComponent;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-8-31
 */
public class PopupBrowserColumnInfoTable extends JTable {
	private NodeChooseEditor nodeeditor = new NodeChooseEditor();

	public PopupBrowserColumnInfoTable(List<PopupBrowserColumnInfo> columninfos) {
		super(new PopupBrowserColumnInfoTableModel(columninfos));
	}

	public void reInitColumnInfoes(List<PopupBrowserColumnInfo> columninfos) {
		setModel(new PopupBrowserColumnInfoTableModel(columninfos));
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		if (column == 1) {
			return nodeeditor;
		} else {
			return super.getCellEditor(row, column);
		}
	}

	private class NodeChooseEditor extends javax.swing.AbstractCellEditor implements TableCellEditor, PropertyChangeListener {
		ForEachEditorComponent com;;

		private NodeChooseEditor() {
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			com = new ForEachEditorComponent();
			com.setValue(value);
			com.addPropertyChangeListener(this);
			return com;
		}

		public Object getCellEditorValue() {
			Object value = com.getValue();
			if (value instanceof String) {
				value = null;
			}
			return value;
		}

		public void propertyChange(PropertyChangeEvent evt) {
			stopCellEditing();
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
	public List<PopupBrowserColumnInfo> getSelectColumnInFOs() {

		return ((PopupBrowserColumnInfoTableModel) getModel()).getPopupBrowserColumnInfos();
	}
	
}
