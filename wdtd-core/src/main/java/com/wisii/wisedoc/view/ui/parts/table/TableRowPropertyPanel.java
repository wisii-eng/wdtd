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
package com.wisii.wisedoc.view.ui.parts.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.parts.AbstractWisePropertyPanel;
import com.wisii.wisedoc.view.ui.parts.common.BackGroundPanel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 表格行方向属性设置面板
 * @author 闫舒寰
 * @version 1.0 2009/03/20
 */
public class TableRowPropertyPanel extends AbstractWisePropertyPanel {
	
	JTabbedPane tablepane;
	BorderPanel borderPanel;
	BackGroundPanel backgroundpanel;
	
	PropertyPanel propertyPanel;
	
	ActionType propertyType = ActionType.TABLE_ROW;

	@Override
	protected void initialPanel() {
		setPreferredSize(new Dimension(512, 400));
		setLayout(new BorderLayout());
		
		tablepane = new JTabbedPane();
		
//		propertyPanel = new PropertyPanel();
//		tablepane.add(UiText.BLOCK_CONTAINER_PROPERTY, propertyPanel);

//		borderPanel = new BorderPanel(getInitialProMap(), propertyType);
//		tablepane.add(UiText.BORDER, borderPanel);
		
		backgroundpanel = new BackGroundPanel(getInitialProMap(), propertyType);
		tablepane.add(UiText.SHADING, backgroundpanel);
		
		add(tablepane, BorderLayout.CENTER);
	}
	
	@Override
	protected Map<Integer, Object> getPanelProperties() {
		return getChangedMap();
	}

	@Override
	public boolean isValidate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Map<Integer, Object> getChangedMap() {
		Map<Integer, Object> borderattris = null;
		if (borderPanel != null) {
			borderattris = borderPanel.getProperties();
		}
		Map<Integer, Object> bgattris = backgroundpanel
				.getProperties();
		if (borderattris != null || bgattris != null) {
			Map<Integer, Object> attris = new HashMap<Integer, Object>();
			if (borderattris != null) {
				attris.putAll(borderattris);
			}
			if (bgattris != null) {
				attris.putAll(bgattris);
			}
			return attris;
		}
		
		return new HashMap<Integer, Object>();
	}

	private class PropertyPanel extends JPanel {}
	
}
