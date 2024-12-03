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
 * @WisedocChartMarginAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jvnet.flamingo.ribbon.JRibbonBand;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.pagelayout.DefaultSimplePageMasterActions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.model.PagePropertyModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.parts.dialogs.ParagraphAllPropertiesDialog;

/**
 * 类功能描述：用于设置统计图的内边距
 * 
 * 作者：李晓光 创建日期：2009-5-21
 */
public class WisedocChartMarginAction extends DefaultSimplePageMasterActions {
	private FixedLengthSpinner top, bottom, left, right;
	private FixedLengthSpinner topMargin, bottomMargin, leftMargin, rightMargin;

	private void getComponents(){
		
		final Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(WisedocChart.MARGIN_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		final List<FixedLengthSpinner> temp = new ArrayList<FixedLengthSpinner>();

		for (final List<Object> list : comSet) {
			for (final Object uiComponents : list) {
				if (uiComponents instanceof FixedLengthSpinner) {
					final FixedLengthSpinner ui = (FixedLengthSpinner) uiComponents;
					temp.add(ui);
				}
			}
		}
		
		this.top = temp.get(0);
		this.bottom = temp.get(1);
		this.left = temp.get(2);
		this.right = temp.get(3);
	}
	
	private void getRibbonComponents(){
		final Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(WisedocChart.MARGIN_ACTION).get(ActionCommandType.RIBBON_ACTION);
		
		final List<FixedLengthSpinner> temp = new ArrayList<FixedLengthSpinner>();
		
		for (final List<Object> list : comSet) {
			for (final Object uiComponents : list) {
				if (uiComponents instanceof FixedLengthSpinner) {
					final FixedLengthSpinner ui = (FixedLengthSpinner) uiComponents;
					temp.add(ui);
				}
			}
		}
		
		this.topMargin = temp.get(0);
		this.bottomMargin = temp.get(1);
		this.leftMargin = temp.get(2);
		this.rightMargin = temp.get(3);
	}

	@Override
	public void doAction(final ActionEvent e) {
		
		if (e.getSource() instanceof FixedLengthSpinner) {
			final FixedLengthSpinner jb = (FixedLengthSpinner) e.getSource();
			
			Component temp = jb.getParent();
			if (temp != null) {
				while (temp.getParent() != null) {
					if (temp instanceof ParagraphAllPropertiesDialog) {
						setProperty();
					} else if (temp instanceof JRibbonBand) {
						setMarginProperty();
					}
					temp = temp.getParent();
				}
			}
		}
	}

	
	private void setProperty() {
		getComponents();
		final Map<Integer, Object> properties = new HashMap<Integer, Object>();
		
		properties.put(Constants.PR_MARGIN_TOP, topMargin.getValue());
		properties.put(Constants.PR_MARGIN_BOTTOM, bottomMargin.getValue());
		properties.put(Constants.PR_MARGIN_LEFT, leftMargin.getValue());
		properties.put(Constants.PR_MARGIN_RIGHT, rightMargin.getValue());
		PagePropertyModel.setFOProperties(properties);
	}
	
	private void setMarginProperty() {
		getRibbonComponents();
		final Map<Integer, Object> properties = new HashMap<Integer, Object>();
				
		properties.put(Constants.PR_MARGIN_TOP, topMargin.getValue());
		properties.put(Constants.PR_MARGIN_BOTTOM, bottomMargin.getValue());
		properties.put(Constants.PR_MARGIN_LEFT, leftMargin.getValue());
		properties.put(Constants.PR_MARGIN_RIGHT, rightMargin.getValue());
		
		setFOProperties(properties);
	}
	
	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		
		getRibbonComponents();
		if (hasPropertyKey(Constants.PR_MARGIN_TOP)) {
			final Object value = evt.getNewValue();
			topMargin.initValue(value);
		}
		if(hasPropertyKey(Constants.PR_MARGIN_BOTTOM)) {
			final Object value = evt.getNewValue();
			bottomMargin.initValue(value);
		}
		if(hasPropertyKey(Constants.PR_MARGIN_LEFT)) {
			final Object value = evt.getNewValue();
			leftMargin.initValue(value);
		}
		if(hasPropertyKey(Constants.PR_MARGIN_RIGHT)) {
			final Object value = evt.getNewValue();
			rightMargin.initValue(value);
		}
	}
	
	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(actionCommand);
		
		if (actionCommand == ActionCommandType.RIBBON_ACTION) {
			getRibbonComponents();
			final FixedLength length = new FixedLength(0D, "pt");
			topMargin.initValue(length);
			bottomMargin.initValue(length);
			leftMargin.initValue(length);
			rightMargin.initValue(length);			
		}
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			getComponents();
			final FixedLength length = new FixedLength(0D, "pt");
			topMargin.initValue(length);
			bottomMargin.initValue(length);
			leftMargin.initValue(length);
			rightMargin.initValue(length);
		}
	}
	@Override
	public boolean isAvailable() {
		return Boolean.TRUE;
	}
}
