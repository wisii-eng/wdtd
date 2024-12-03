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
package com.wisii.wisedoc.view.ui.ribbon.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jvnet.flamingo.common.popup.PopupPanelManager;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 动态显示插入表格个数的面板
 * @author 闫舒寰
 * @version 1.0 2009/01/19
 */
public class RibbonTableListPanel extends JPanel{
	
	JPanel tablePanel;
	JPanel infoPanel;
	RibbonTableCellItem colItem;
	boolean selectOnMouseOver;
	List<RibbonTableCellItem> tableCells = new ArrayList<RibbonTableCellItem>();
	JLabel text;

	public RibbonTableListPanel() {

		setLayout(new BorderLayout());
		
		infoPanel = new JPanel();
		tablePanel = new TablePanel();
		text = new JLabel("插入表格");
		infoPanel.add(text);
		
		add(infoPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
	}
	
	private class TablePanel extends JPanel{
		
		public TablePanel() {
			setLayout(new GridLayout(8, 10, 0, 0));
			
			setPreferredSize(new Dimension(250, 200));		
			
			tablePanel =this;
			colItem = null;
			selectOnMouseOver = false;

			for (int i = 0; i < 10; i++) {
				tableCells.add(i, null);
			}

			for (int i = 0; i < 80; i++) {
				tableCells.add(new RibbonTableCellItem(new Color(255, 255, 255)));
			}

			Iterator<RibbonTableCellItem> iterator = tableCells.iterator();
			for (int i = 0; i < 10; i++) {
				iterator.next();
			}
			while (iterator.hasNext()) {
				add((RibbonTableCellItem) iterator.next());
			}

			addMouseMotionListener(new TableMouse());
			addMouseListener(new TableMouse());
		}
	}
	
	private class TableMouse extends MouseAdapter {
		
		String columns;
		String rows;
		
		Iterator<RibbonTableCellItem> iterator;
		
		public void mouseMoved(MouseEvent e) {
			
			Component comp = tablePanel.getComponentAt(e.getPoint());
			if (comp != null) {
				if (comp instanceof RibbonTableCellItem) {

					String index = Integer.toString(tableCells
							.indexOf((RibbonTableCellItem) comp));
					
					columns = Character.toString(index.charAt(1));
					rows = Character.toString(index.charAt(0));
					
					iterator = tableCells.iterator();
					
					/*for (int i = 0; i <= 10; i++) {
						iterator.next();
					}
					while (iterator.hasNext()) {
						((RibbonTableCellItem) iterator.next()).setOver(false);
					}*/
					
					/*for (int i = 1; i <= Integer.valueOf(rows); i++) {
						for (int j = 0; j <= Integer.valueOf(columns); j++) {
							int cell = i*10 + j;
							colItem = tableCells.get(cell);
							colItem.setOver(true);
						}
					}*/
					
					//更新图标边框放到了EDT线程中
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							
							for (int i = 0; i <= 10; i++) {
								if (iterator.hasNext()) {
									iterator.next();
								}
							}
							while (iterator.hasNext()) {
								((RibbonTableCellItem) iterator.next()).setOver(false);
							}
							
							for (int i = 1; i <= Integer.valueOf(rows); i++) {
								for (int j = 0; j <= Integer.valueOf(columns); j++) {
									int cell = i*10 + j;
									colItem = tableCells.get(cell);
									if (!colItem.isOver()) {
										colItem.setOver(true);
									}
								}
							}
						}
						
					});
					
					int colum = Integer.valueOf(columns) + 1;
					String tableInfo = colum + "×" + rows + "表格";					
					setInfo(tableInfo);
				}
			}
		}
		
		public void mousePressed(MouseEvent e) {
			
			Component comp = tablePanel.getComponentAt(e.getPoint());
            
            if (comp != null) {
                if (comp instanceof RibbonTableCellItem) {
                	String index = Integer.toString(tableCells.indexOf((RibbonTableCellItem) comp));
                	
                	int value = Integer.valueOf(index) + 1;
                	
                	if (Integer.valueOf(String.valueOf(index.charAt(index.length()-1))) == 9) {
                		//处理10个单元格这种情况
                		addTable(Integer.valueOf(String.valueOf(index.charAt(0))),
	                			/*Integer.valueOf(index.charAt(0)+"0")*/10);
					} else {
						addTable(Integer.valueOf(Character.toString(Integer.toString(value).charAt(0))),
	                			Integer.valueOf(Character.toString(Integer.toString(value).charAt(1))));
					}
                	
                	PopupPanelManager.defaultManager().hideLastPopup();
                }
            }
		}
	}
	
	private void addTable(int row, int column)
	{
		Table table = new Table(false, false, row, column, null);
		List<CellElement> inserts = new ArrayList<CellElement>();
		inserts.add(table);
		Document doc = SystemManager.getCurruentDocument();
		AbstractEditComponent docpanel = RibbonUpdateManager.Instance
				.getCurrentEditPanel();
		DocumentPositionRange range = docpanel.getSelectionModel()
				.getSelectionCell();

		if (range != null)
		{
			doc.replaceElements(range, inserts);
		} else
		{
			DocumentPosition pos = docpanel.getCaretPosition();
			if (pos != null)
			{
				doc.insertElements(inserts, pos);
			}
		}
		//【添加：START】 by 李晓光 	2010-1-27
    	updateCaret(table);
    	//【添加：END】 by 李晓光 	2010-1-27
	}
	//【添加：START】 by 李晓光 	2010-1-27
	private void updateCaret(Table table){
		DocumentPosition pos = new DocumentPosition(table.getFirstCell());
		RibbonUpdateManager.Instance.getCurrentEditPanel().setCaretPosition(pos);
	}
	//【添加：END】 by 李晓光 	2010-1-27
	private void setInfo(String info){
		text.setText(info);
	}
}
