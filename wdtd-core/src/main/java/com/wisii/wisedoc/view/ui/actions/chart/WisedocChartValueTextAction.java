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
 * @WisedocChartValueTextAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.event.ActionEvent;
import java.util.List;

import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.ribbon.chart.ChartAxisLabelBand.WisedocTextField;

/**
 * 类功能描述：用于设置统计图值坐标轴标签。
 * 
 * 作者：李晓光 创建日期	2009-5-22
 */
public class WisedocChartValueTextAction extends Actions {
	private boolean flag = Boolean.FALSE;
	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof WisedocTextField) {
			final WisedocTextField value = (WisedocTextField) e.getSource();
			BarCodeText text = new BarCodeText(value.getText());
			if(value.isBindNode()) {
				text = value.getValue();
			}
			setFOProperty(Constants.PR_RANGEAXIS_LABEL, text/*value.getValue()*/);
		}
	}
	
	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		
		if (hasPropertyKey(Constants.PR_RANGEAXIS_LABEL)) {
			if (uiComponent instanceof WisedocTextField)
			{
				final Object obj = evt.getNewValue();
				final WisedocTextField ui = (WisedocTextField) uiComponent;
				String text = "";
				if (obj instanceof BarCodeText)
				{
					text = ((BarCodeText) obj).toString();
				}
				ui.setText(text);
			}
		}else if(!flag){
			final WisedocTextField ui = (WisedocTextField) uiComponent;
			final Object obj = InitialManager.getInitialValue(Constants.PR_RANGEAXIS_LABEL, null);
			String text = "";
			if (obj instanceof BarCodeText)
			{
				text = ((BarCodeText) obj).toString();
			}
			ui.setText(text);
			flag = Boolean.TRUE;
		}
	}
	
	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_RANGEAXIS_LABEL)) {
			if (uiComponent instanceof WisedocTextField) {
				final WisedocTextField ui = (WisedocTextField) uiComponent;
				ui.setText("");
			}
		}
	}
	
	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof WisedocTextField) {
			final WisedocTextField ui = (WisedocTextField) uiComponent;
			ui.setText("");
		}
	}
	@Override
	public boolean isAvailable() {
		if(!super.isAvailable())
		{
			return false;
		}
		final List<CellElement> cells = getObjectSelects();
		if(cells == null || cells.isEmpty()) {
			return Boolean.FALSE;
		}
		Chart chart = null;
		for (final CellElement cell : cells) {
			if (cell instanceof Chart) {
				chart = (Chart) cell;
				break;
			}
		}
		if (chart == null) {
			return Boolean.FALSE;
		}
		final EnumProperty prop = (EnumProperty)chart.getAttribute(Constants.PR_CHART_TYPE);
		if(prop == null) {
			return Boolean.FALSE;
		}
		return (prop.getEnum() != Constants.EN_PIECHART);
	}
}
