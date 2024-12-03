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
package com.wisii.wisedoc.view.ui.parts.text;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTabbedPane;

import com.sun.istack.internal.NotNull;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.parts.AbstractWisePropertyPanel;
import com.wisii.wisedoc.view.ui.parts.common.BackGroundPanel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 文字属性设置面板
 * @author 闫舒寰
 * @version 1.0 2009/03/20
 */
public class FontPropertyPanel extends AbstractWisePropertyPanel {
	
	JTabbedPane tablepane;
	BorderPanel borderPanel;
	BackGroundPanel backgroundpanel;
	
	private static Map<Integer, Object> initialPro;
	
	ActionType propertyType = ActionType.INLINE;

	@Override
	protected void initialPanel() {
		setPreferredSize(new Dimension(512, 420));
		initialPro = super.getInitialProMap();

		setLayout(new BorderLayout());
		
		tablepane = new JTabbedPane();
		
		tablepane.add(UiText.FONT_DIALOG_TAB_TITLE_FONT, new FontPanelDynamic());
		tablepane.add(UiText.FONT_DIALOG_TAB_TITLE_POSITION, new FontPositionPanelDynamic());

		borderPanel = new BorderPanel(getInitialProMap(), propertyType);
		tablepane.add(UiText.BORDER, borderPanel);
		
		backgroundpanel = new BackGroundPanel(getInitialProMap(), propertyType);
		tablepane.add(UiText.SHADING, backgroundpanel);
		
		add(tablepane, BorderLayout.CENTER);
	}
	
	@Override
	protected void initialPanel(PropertyPanelType ppt) {
		setPreferredSize(new Dimension(512, 420));
		initialPro = super.getInitialProMap();

		setLayout(new BorderLayout());
		
		tablepane = new JTabbedPane();
		
		tablepane.add(UiText.FONT_DIALOG_TAB_TITLE_FONT, new FontPanelDynamic());
		tablepane.add(UiText.FONT_DIALOG_TAB_TITLE_POSITION, new FontPositionPanelDynamic());

		add(tablepane, BorderLayout.CENTER);
	}
	
	@Override
	protected Map<Integer, Object> getPanelProperties() {
		
		setFOProperties(FontPropertyModel.getFinalProperties());
		
		
		return getChangedMap();
	}
	
	public static @NotNull Map<Integer, Object> getInitialPropertise(){
		
		if (initialPro == null) {
			initialPro = new HashMap<Integer, Object>();
		}
		
		return initialPro;
	}

	@Override
	public boolean isValidate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Map<Integer, Object> getChangedMap() {
		
		if (borderPanel == null && backgroundpanel == null) {
			return new HashMap<Integer, Object>();
		}
		
		Map<Integer, Object> borderattris = null;
		
		if (borderPanel != null) {
			borderattris = borderPanel.getProperties();
		}
		
		Map<Integer, Object> bgattris = backgroundpanel.getProperties();
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

}
