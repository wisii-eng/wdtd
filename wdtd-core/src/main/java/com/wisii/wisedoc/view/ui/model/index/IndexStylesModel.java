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
package com.wisii.wisedoc.view.ui.model.index;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.sun.istack.internal.NotNull;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.view.ui.parts.index.IndexListPanel;
import com.wisii.wisedoc.view.ui.parts.index.IndexStylePropertyPanel;
import com.wisii.wisedoc.view.ui.parts.index.IndexStyles;
import com.wisii.wisedoc.view.ui.parts.index.IndexStylesPanel;
import com.wisii.wisedoc.view.ui.text.UiText;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 段落样式模型层
 * @author 闫舒寰
 * @version 1.0 2009/03/25
 */
public enum IndexStylesModel {
	
	Instance;
	
	/**
	 * 目录索引样式列表
	 */
	private List<IndexStyles> indexSList;
	
	/**
	 * 目录样式列表
	 */
	private IndexListPanel indexListPanel;
	
	private void iniIndexList() {
		
		indexSList = new ArrayList<IndexStyles>();
		
		//XXX 把默认的名字搞出来
		String[] listItems = UiText.INDEX_LEVEL;
		
		Document doc = RibbonUpdateManager.Instance.getCurrentEditPanel().getDocument();
		
		TableContents tc = doc.getTableContents();
		
		if (tc != null) {
			for (int i = 0; i < listItems.length; i++) {
				indexSList.add(new IndexStyles(listItems[i], tc.getAttributesofLevel(i).getAttributes()));
			}
		} else {
			for (String name : listItems) {
				indexSList.add(new IndexStyles(name, new HashMap<Integer, Object>()));
			}
		}
	}
	
	/**
	 * 获得目录样式名字集合，这里面有初始化目录级别的过程
	 * @return 目录名字集合
	 */
	public List<String> getPsName() {
		
		if (indexSList.size() == 0) {
			iniIndexList();
		}
		
		List<String> psName = new ArrayList<String>();
		for (IndexStyles ps : indexSList) {
			psName.add(ps.getStyleName());
		}
		
		return psName;
	}
	
	/**
	 * 设置属性
	 * @param properties
	 */
	public void setPropertise(Map<Integer, Object> properties) {
//		System.out.println((String)IndexList.getList().getSelectedValue() + " property: " + properties);
		if (IndexListPanel.getList().getSelectedValue() != null) {
			getPsProMap((String)IndexListPanel.getList().getSelectedValue()).putAll(properties);
			UpdateUI();
		}
	}
	
	/**
	 * 根据名字取得样式集合
	 * @param name	目录级别样式
	 * @return 该目录的样式
	 */
	public Map<Integer, Object> getPsProMap(String name) {
		Map<Integer, Object> temp = null;
		
		for (IndexStyles ps : indexSList) {
			if (ps.getStyleName().equals(name)) {
				temp = ps.getStyleProperty();
			}
		}
		return temp;
	}

	/**
	 * 获得当前选定属目录的属性
	 * @return
	 */
	public @NotNull Map<Integer, Object> getProperty() {
		
		Map<Integer, Object> temp = getPsProMap((String)IndexListPanel.getList().getSelectedValue());
		
		if (temp == null) {
			temp = new HashMap<Integer, Object>();
		}
		
		return temp;
	}
	
	/*********** 初始化各个面板开始 ***************/
	IndexStylePropertyPanel pspp;
	
	JSplitPane jsp;
	
	IndexStylesPanel psp;
	
	JPanel propertyPanel;
	
	public void initial(IndexStylesPanel psp) {
		
		this.psp = psp;
		
		//用它来初始化目录属性和名字
		IndexStylesModel.Instance.iniIndexList();
		
		indexListPanel = new IndexListPanel();
		pspp = new IndexStylePropertyPanel();
		
		propertyPanel = new JPanel();
		propertyPanel.setLayout(new BorderLayout());
		propertyPanel.add(pspp);
		
		jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, indexListPanel, propertyPanel);
	}
	
	/**
	 * 更新段落样式对话框中的属性样式
	 */
	public void UpdateUI() {
		
		pspp.setPName((String)IndexListPanel.getList().getSelectedValue());
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				propertyPanel.updateUI();
			}
		});
	}
	
	public JComponent getPsPanel() {
		return jsp;
	}
	/*********** 初始化各个面板结束 ***************/
	
	public void setIndexStylesList(@NotNull List<IndexStyles> psList) {
		this.indexSList = psList;
	}

	public @NotNull List<IndexStyles> getIndexStylesList() {
		return indexSList;
	}
	
	//以下是用于收录和设置目录 属性用的
	/**
	 * 收录用户设置的有关目录的四个属性
	 */
	private static Map<Integer, Object> indexProperties = new HashMap<Integer, Object>();
	
	/**
	 * 供设置属性结束时调用，这个方法会清空该属性集合
	 * @return
	 */
	public static Map<Integer, Object> getFinalProperties() {
		Map<Integer, Object> temp = new HashMap<Integer, Object>();
		temp.putAll(indexProperties);
		indexProperties.clear();
		return temp;
	}
	
	/**
	 * 清空当前设置的属性，当用户点击“取消”按钮的时候调用
	 */
	public static void clearProperty(){
		indexProperties.clear();
	}

	public static void setFOProperties(Map<Integer, Object> indexProperties) {
		IndexStylesModel.indexProperties.putAll(indexProperties);
	}
	
	public static void setFOProperty(int propertyId, Object propertyValue) {
		IndexStylesModel.indexProperties.put(propertyId, propertyValue);
	}
	
	public static Object getCurrentProperty(int propertyID) {
		if (indexProperties.get(propertyID) == null) {
//			System.out.println(RibbonUIModel.getReadCompletePropertiesByType());
			Document doc = RibbonUpdateManager.Instance.getCurrentEditPanel().getDocument();
			
			TableContents tc = doc.getTableContents();
			
			if (tc != null) {
				Attributes attr = doc.getTableContents().getAttributes();
				
				if (attr != null) {
					indexProperties.put(propertyID, attr.getAttribute(propertyID));
				}
			}
		}
		return indexProperties.get(propertyID);
	}
	
	/**
	 * 添加foProperty到
	 * @param paragraphProperties
	 */
	public static void addFOProperties(Map<Integer, Object> paragraphProperties){
		IndexStylesModel.indexProperties.putAll(paragraphProperties);
	}
}
