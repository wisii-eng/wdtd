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
 * @WisedocChartAutoAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;

import javax.swing.JCheckBox;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionID;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：用于处理最大值、最小值，跨度值的处理方式，可以选择自动【随即数据】
 * 手动设置两种方式，该对象是切换开关。
 * 
 * 作者：李晓光 
 * 创建日期：2009-11-3
 */
@SuppressWarnings("serial")
public class WisedocChartAutoAction extends Actions {
	@Override
	public void doAction(ActionEvent e) {
		Object source = e.getSource();
		if(!(source instanceof JCheckBox))
			return;
		JCheckBox box = (JCheckBox)source;
		String command = box.getActionCommand();
		if(command == null || "".equals(command))
			return;
		int key = Integer.parseInt(command);
		
		if(box.isSelected()){
			setFOProperty(key, null);
		}else{
			Object value = getRibbonComponents(key);
			if(value == null)
				return;
			setFOProperty(key, value);
		}
	}
	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		updateState(uiComponents);
	}
	private void updateState(List<Object> obj){
		final List<CellElement> cells = getObjectSelects();
		if(cells == null || cells.isEmpty()) {
			return;
		}
		Chart chart = null;
		for (final CellElement cell : cells) {
			if (cell instanceof Chart) {
				chart = (Chart) cell;
				break;
			}
		}
		if(chart == null)
			return;
		for (Object o : obj) {
			if(!(o instanceof JCheckBox))
				continue;
			JCheckBox box = (JCheckBox)o;
			int key = Integer.parseInt(box.getActionCommand());
			box.setSelected(chart.getAttribute(key) == null);
		}
	}
	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof JCheckBox) {
			final JCheckBox ui = (JCheckBox) uiComponent;
			final List<CellElement> cells = getObjectSelects();
			if(cells == null || cells.isEmpty()) {
				return;
			}
			Chart chart = null;
			for (final CellElement cell : cells) {
				if (cell instanceof Chart) {
					chart = (Chart) cell;
					break;
				}
			}
			int key = Integer.parseInt(ui.getActionCommand());
			ui.setSelected(chart.getAttribute(key) == null);
		}
	}
	private Object getRibbonComponents(int key){
		ActionID id = WisedocChart.MIN_ADUATE_ACTION;
		if(key == Constants.PR_RANGEAXIS_MINUNIT){
			id = WisedocChart.MIN_ADUATE_ACTION;
		}else if(key == Constants.PR_RANGEAXIS_MAXUNIT){
			id = WisedocChart.MAX_ADUATE_ACTION;
		}else if(key == Constants.PR_RANGEAXIS_UNITINCREMENT){
			id = WisedocChart.STEP_ADUATE_ACTION;
		}else if(key == Constants.PR_RANGEAXIS_PRECISION){
			id = WisedocChart.ADUATE_PRECISION_ACTION;
		}else{
			return null;
		}
			
		final Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(id).get(ActionCommandType.RIBBON_ACTION);
		
		for (final List<Object> list : comSet) {
			for (final Object uiComponents : list) {
				if (uiComponents instanceof WiseSpinner) {
					final WiseSpinner ui = (WiseSpinner) uiComponents;
					return ui.getValue();
				}
			}
		}
		return null;
	}
}
