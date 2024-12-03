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
 * @SelectionModel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 类功能描述：元素选中模型类
 * 
 * 作者：zhangqiang 创建日期：2008-9-1
 */
public class SelectionModel {

	// 单选.
	public static final int SINGLE_ELEMENT_SELECTION = 1;

	// 多选
	public static final int MULTIPLE_ELEMENT_SELECTION = 4;

	// 选择属性
	public static final String SELECTION_MODE_PROPERTY = "selectionMode";

	// 表示选中状态的属性值
	public static final int SELECTED = -1;

	// 表示非选中状态的属性值
	public static final Integer UNSELECTED = new Integer(0);

	// 选中事件监听器
	protected EventListenerList listenerList = new EventListenerList();

	// 选择模式
	protected int selectionMode;

	// 选中的对象列表
	protected List<DocumentPositionRange> selection = new ArrayList<DocumentPositionRange>();
	/* 表、单元格、行选时，把相应的table、tablecell、tablerow对象保存到该list中 */
	protected List<CellElement> objSelection = new ArrayList<CellElement>();
	// 当前正在操作的选中区域
	private DocumentPositionRange _currentselcell;

	public SelectionModel() {
	}

	/**
	 * 设置选择模式
	 */
	public void setSelectionMode(int mode) {
		int oldMode = selectionMode;

		selectionMode = mode;
		if (selectionMode != MULTIPLE_ELEMENT_SELECTION
				&& selectionMode != SINGLE_ELEMENT_SELECTION)
			selectionMode = MULTIPLE_ELEMENT_SELECTION;

	}

	/**
	 * 返回选择模式
	 * 
	 * 
	 */
	public int getSelectionMode() {
		return selectionMode;
	}

	/**
	 * 设置对象为选择状态
	 * 
	 * @param cell
	 *            the cell to select
	 */
	public void setSelection(DocumentPositionRange cell) {
		if (cell == null)
			setSelections(null, null);
		else {
			List<DocumentPositionRange> newSelection = new ArrayList<DocumentPositionRange>();
			newSelection.add(cell);
			setSelections(newSelection, cell);
		}
	}

	/**
	 * 设置多个对象为选择状态
	 * 
	 * 
	 * 
	 * 
	 * @param cells
	 *            要选中的对象
	 */
	private void setSelections(List<DocumentPositionRange> cells,
			DocumentPositionRange addcell) {
		if (cells != null) {
			clearObjectSelection();
			List<DocumentPositionRange> newSelection = new ArrayList<DocumentPositionRange>();
			// 如果是单选模式，则只取最后一个对象作为选中对象
			int size = cells.size();
			if (size > 0) {
				if (selectionMode == SINGLE_ELEMENT_SELECTION) {
					newSelection.add(cells.get(size - 1));
				} else {
					Collections.sort(cells);
					newSelection.add(cells.get(0));
					for (int i = 1; i < size; i++) {
						DocumentPositionRange ccell = cells.get(i);
						if (ccell != null) {
							DocumentPosition starposi = ccell
									.getStartPosition();
							DocumentPositionRange precell = newSelection
									.get(newSelection.size() - 1);
							DocumentPosition starposipre = precell
									.getStartPosition();
							DocumentPosition endposipre = precell
									.getEndPosition();
							// 一下算法是为了保证所有的选中区域是合并后的
							// 如果后一个选择区域的起始点在上一个选择区域之间的话，则合并两区域
							if (starposi.compareTo(starposipre) >= 0
									&& starposi.compareTo(endposipre) <= 0) {
								newSelection.remove(precell);
								DocumentPosition endpositon = ccell
										.getEndPosition();
								if (endpositon.compareTo(endposipre) < 0) {
									endpositon = endposipre;
								}
								DocumentPositionRange selectcell = DocumentPositionRange
										.creatSelectCell(starposipre,
												endpositon);
								newSelection.add(selectcell);
								// 如果新添加的cell被合并了，则修改addcell的引用
								if (ccell.equals(addcell)) {
									addcell = ccell;
								}
							}
							// 否则直接添加
							else {
								newSelection.add(ccell);
							}
						}
					}
				}
			}
			// 如果新的选中状态和之前的选中状态不相等，在更新选中状态，并发相应的通知
			if (!selection.equals(newSelection)) {
				Vector change = new Vector();
				Iterator it = selection.iterator();
				while (it.hasNext()) {
					Object cell = it.next();
					change.addElement(new ElementPlaceHolder(cell, false));
				}
				Iterator newit = newSelection.iterator();
				while (newit.hasNext()) {
					Object cell = newit.next();
					change.addElement(new ElementPlaceHolder(cell, true));
				}
				selection = newSelection;
				notifyCellChange(change);
			}
			_currentselcell = addcell;
		}
	}

	/**
	 * 添加新的对象到选中状态
	 * 
	 * @param cell
	 *            ：要添加到选中状态的对象
	 * 
	 */
	public void addSelectionCell(DocumentPositionRange cell) {
		if (cell != null) {
			addSelectionCells(new DocumentPositionRange[] { cell }, cell);
		}
	}

	/**
	 * 添加对象到选中状态
	 * 
	 * 
	 * 
	 */
	public void addSelectionCells(DocumentPositionRange[] cells,
			DocumentPositionRange cell) {
		if (cells != null) {
			List<DocumentPositionRange> cellsn = new ArrayList<DocumentPositionRange>();
			if (selection != null) {
				cellsn.addAll(selection);
			}
			for (int i = 0; i < cells.length; i++) {
				if (cells[i] != null) {
					cellsn.add(cells[i]);
				}
			}
			setSelections(cellsn, cell);

		}
	}

	/**
	 * 删除指定对象的选中状态
	 * 
	 * @param cell
	 *            ：要删除的对象
	 * 
	 */
	public void removeSelectionCell(DocumentPositionRange cell) {
		if (cell != null)
			removeSelectionCells(new DocumentPositionRange[] { cell });
	}

	/**
	 * 
	 *用新的选中区域替换oldcell老选中区域 如果newcell为空，则等同于删除oldcell
	 * 
	 * @param oldcell
	 *            ：要替换的区域 newcell：替换后的区域
	 * @return
	 * @exception
	 */
	public void replaceSelectionCell(DocumentPositionRange oldcell,
			DocumentPositionRange newcell) {
		if (oldcell != null && oldcell.equals(newcell)) {
			return;
		}
		List<DocumentPositionRange> nselection = new ArrayList<DocumentPositionRange>(
				selection);
		int index = nselection.indexOf(oldcell);
		nselection.remove(oldcell);
		if (newcell == null) {
			if (index > -1) {
				int size = nselection.size();
				if (!nselection.isEmpty()) {
					if (index == size) {
						newcell = nselection.get(index - 1);
					} else {
						newcell = nselection.get(index);
					}
				}
			}
		} else {
			nselection.add(newcell);
		}
		setSelections(nselection, newcell);

	}

	/**
	 * 删除指定多个对象的选中状态
	 * 
	 * @param cells
	 *            ：要删除的多个对象
	 * 
	 */
	public void removeSelectionCells(DocumentPositionRange[] cells) {
		if (cells != null) {
			Vector change = new Vector();
			for (int i = 0; i < cells.length; i++) {
				if (cells[i] != null) {
					boolean removed = selection.remove(cells[i]);
					if (cells[i] == _currentselcell) {
						_currentselcell = null;
					}
					if (removed) {
						change.addElement(new ElementPlaceHolder(cells[i],
								false));
					}
				}
			}

			if (change.size() > 0)
				notifyCellChange(change);
			// 对当前选中进行重新赋值
			int size = selection.size();
			if (size > 0) {
				_currentselcell = selection.get(size - 1);
			}
		}
	}

	/**
	 * 获得选中对象：返回第一格选中的对象
	 * 
	 */
	public DocumentPositionRange getSelectionCell() {
		return _currentselcell;
	}

	/**
	 * 返回选中的所有对象
	 * 
	 */
	public List<DocumentPositionRange> getSelectionCells() {
		if (selection != null)
			return new ArrayList<DocumentPositionRange>(selection);
		return null;
	}

	/**
	 * 
	 * 返回选择的对象【表、单元格、行】
	 */
	public List<CellElement> getObjectSelection() {
		if (objSelection == null)
			return objSelection;

		return new ArrayList<CellElement>(objSelection);
	}

	public void clearObjectSelection() {
		if (objSelection == null||objSelection.isEmpty())
			return;
		objSelection.clear();
		notifyTableCellChanged();
	}
	public void clearObjectSelection(List<CellElement> elements){
		if((objSelection == null) || elements == null)
			return;
		objSelection.removeAll(elements);
		notifyTableCellChanged();
	}
	public void clearObjectSelection(CellElement...elements){
		if((objSelection == null) || elements == null)
			return;
		clearObjectSelection(Arrays.asList(elements));
	}
	public void addObjectSelection(CellElement element) {
		if (objSelection == null)
			objSelection = new ArrayList<CellElement>();
		if(element == null)
			return;
		if (!clearRepeatElemet(element)){
			objSelection.remove(element);//lxg 2008-12-19
			objSelection.add(element);
		}
		
		clearRepeatRange(element);
		notifyTableCellChanged();
	}
	//选中传入的元素(清除掉原来的选中状态)
	public void setObjectSelecttion(CellElement element)
	{
		if (element == null)
		{
			return;
		}
		if (objSelection == null)
			objSelection = new ArrayList<CellElement>();
		else
		{
			objSelection.clear();
		}
		objSelection.add(element);
		clearRepeatRange(element);
		notifyTableCellChanged();
	}
	public void addObjectSelections(List<CellElement> elements) {
		if (objSelection == null)
			objSelection = new ArrayList<CellElement>();
		
		clearRepeatRange(elements.toArray(new CellElement[]{}));
		objSelection.removeAll(elements);//添加 lxg 2008-12-19
		objSelection.addAll(elements);
		notifyTableCellChanged();
	}
	private void clearRepeatRange(CellElement...elements){
		if(selection == null || selection.size() == 0)
			return;
		if(elements == null || elements.length == 0)
			return;
		List<DocumentPositionRange> temp = new ArrayList<DocumentPositionRange>();
		for (CellElement element : elements) {
			for (DocumentPositionRange range : selection) {
				TableCell cell = TableOperationHandler.getCurrentCell(range.getStartPosition());
				if(isContaint(element, cell))
					temp.add(range);
			}
		}
		selection.removeAll(temp);
	}
	private boolean isContaint(CellElement ele, TableCell cell){
		if(cell == null || ele == null)
			return Boolean.FALSE;
		TableRow row = null;
		if(ele instanceof TableCell){
			return (ele == cell);
		}else if(ele instanceof TableRow){
			row = (TableRow)cell.getParent();
			return (ele == row);
		}else if(ele instanceof Table){
			Table table = TableOperationHandler.getCurrentTable(cell);
			return (ele == table);
		}
		return Boolean.FALSE;
	}
	protected void notifyTableCellChanged() {
		if (objSelection == null)
			return;
//		add by zq清空光标
		if(!objSelection.isEmpty())
		{
			RibbonUpdateManager.Instance.getCurrentEditPanel().setCaretPosition(null);
		}
		CellElement[] elemetns = objSelection.toArray(new CellElement[] {})
				.clone();
		boolean[] b = new boolean[elemetns.length];
		Arrays.fill(b, Boolean.FALSE);
		ElementSelectionEvent event = new ElementSelectionEvent(this, elemetns,
				b);

		fireValueChanged(event);
	}

	private boolean clearRepeatElemet(CellElement element) {
		if (objSelection == null)
			return Boolean.FALSE;

		if (element instanceof TableCell) {
			TableCell cell = (TableCell) element;
			return hasTableCell(cell);
		}

		return objSelection.contains(element);
	}

	/**
	 * 
	 * 判断指定的TableCell是否被选中
	 * 
	 * @param cell
	 *            指定TableCell
	 * @return {@link Boolean} 如果被选中：True 否则：False
	 */
	public boolean hasTableCell(TableCell cell) {
		if (objSelection == null || cell == null)
			return Boolean.FALSE;
		boolean flag = objSelection.contains(cell);
		if (!flag)
			flag = objSelection.contains(cell.getParent());
		return flag;
	}

	/**
	 * 判断指定集合的TableCell中是否有被选中的Cell
	 * 
	 * @param cells
	 *            指定Cell集合。
	 * @return 如果指定Cell集合中有被选择的Cell：True 否则：False
	 */
	public boolean hasTableCells(List<TableCell> cells) {
		if (objSelection == null || cells == null)
			return Boolean.FALSE;
		for (TableCell cell : cells) {
			if (hasTableCell(cell))
				return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public void removeObjectSelection(CellElement element) {
		if (objSelection == null)
			return;
		objSelection.remove(element);
	}

	public boolean isObjectSelectionEmpty() {
		if (objSelection == null)
			return Boolean.TRUE;
		return objSelection.isEmpty();
	}

	/**
	 * 判断是否没有选中对象
	 */
	public boolean isSelectionEmpty() {
		return (selection.isEmpty());
	}

	/**
	 * 清除所有对象的选中状态
	 * 
	 */
	public void clearSelection() {
		if (selection != null && !selection.isEmpty()) {
			Vector change = new Vector();
			Iterator it = selection.iterator();
			while (it.hasNext()) {
				Object cell = it.next();
				change.addElement(new ElementPlaceHolder(cell, false));
			}
			selection.clear();
			if (change.size() > 0)
				notifyCellChange(change);
		}
		/* 【添加：START】by 李晓光 2008-10-27 */
		clearObjectSelection();
		/* 【添加：END】by 李晓光 2008-10-27 */
		_currentselcell = null;

	}

	/**
	 * 添加选中事件监听器
	 * 
	 * @param x
	 *            ：选中监听器
	 * 
	 */
	public void addElementSelectionListener(ElementSelectionListener x) {
		listenerList.add(ElementSelectionListener.class, x);
	}

	/**
	 * 删除指定选中监听器
	 */
	public void removeElementSelectionListener(ElementSelectionListener x) {
		listenerList.remove(ElementSelectionListener.class, x);
	}

	/**
	 * 触发选中事件
	 */
	protected void fireValueChanged(ElementSelectionEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ElementSelectionListener.class) {
				((ElementSelectionListener) listeners[i + 1]).valueChanged(e);
			}
		}
	}

	/**
	 * 获得指定监听器类型的监听器对象
	 */
	public EventListener[] getListeners(Class listenerType) {
		return listenerList.getListeners(listenerType);
	}

	/**
	 * 
	 * 出发选中事件
	 */
	protected void notifyCellChange(Vector changedCells) {
		int cCellCount = changedCells.size();
		boolean[] newness = new boolean[cCellCount];
		Object[] cells = new Object[cCellCount];
		ElementPlaceHolder placeholder;

		for (int counter = 0; counter < cCellCount; counter++) {
			placeholder = (ElementPlaceHolder) changedCells.elementAt(counter);
			newness[counter] = placeholder.isNew;
			cells[counter] = placeholder.cell;
		}

		ElementSelectionEvent event = new ElementSelectionEvent(this, cells,
				newness);

		fireValueChanged(event);
	}

	/**
	 * Returns a clone of this object with the same selection. This method does
	 * not duplicate selection listeners and property listeners.
	 * 
	 * @exception CloneNotSupportedException
	 *                never thrown by instances of this class
	 */
	public Object clone() throws CloneNotSupportedException {
		SelectionModel clone = (SelectionModel) super.clone();
		if (selection != null)
			clone.selection = new ArrayList<DocumentPositionRange>();
		clone.selection.addAll(selection);
		if (objSelection != null) {
			ArrayList<CellElement> list = new ArrayList<CellElement>(
					objSelection);
			clone.objSelection = list;
		}
		clone.listenerList = new EventListenerList();
		return clone;
	}

	/**
	 * 选中对象状态容器类
	 */
	protected class ElementPlaceHolder {
		protected boolean isNew;

		protected Object cell;

		protected ElementPlaceHolder(Object cell, boolean isNew) {
			this.cell = cell;
			this.isNew = isNew;
		}

		/**
		 * 返回对象
		 * 
		 */
		public Object getCell() {
			return cell;
		}

		/**
		 * 返回事件状态
		 */
		public boolean isNew() {
			return isNew;
		}

		/**
		 * 设置对象
		 */
		public void setCell(Object cell) {
			this.cell = cell;
		}

		/**
		 * 设置事件状态
		 */
		public void setNew(boolean isNew) {
			this.isNew = isNew;
		}

	}

	/**
	 * 
	 * 获得所有的选中对象，包括对象方式选中的对象 和DocumentPositionRange方式选中的对象
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public List<CellElement> getSelectedObject() {
		List<CellElement> list = null;
		List<DocumentPositionRange> ranges = getSelectionCells();
		List<CellElement> elements = getObjectSelection();
		Document doc = SystemManager.getCurruentDocument();
		if (doc != null && (ranges != null && !ranges.isEmpty())
				|| (elements != null && !elements.isEmpty())) {
			list = new ArrayList<CellElement>();
			if (ranges != null) {
				List<CellElement> cells = doc.getElements(ranges);
				if (cells != null) {
					list.addAll(cells);
				}

			}
			if (elements != null) {
				list.addAll(elements);
			}
		}
		return list;
	}

}
