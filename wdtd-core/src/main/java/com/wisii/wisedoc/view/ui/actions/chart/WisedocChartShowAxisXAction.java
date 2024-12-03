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
 * @WisedocChartShowAxisXAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JCheckBox;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能说明：用于控制统计图，是否显示域坐标轴
 * 
 * @author 李晓光
 * @version 1.0 2009-5-22
 */
public class WisedocChartShowAxisXAction extends Actions {
	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof JCheckBox) {
			final JCheckBox value = (JCheckBox) e.getSource();
			final Boolean b = value.isSelected();
			final EnumProperty cond = new EnumProperty(b ? Constants.EN_TRUE:Constants.EN_FALSE, b.toString());
			setFOProperty(Constants.PR_DOMIANLINE_VISABLE, cond);
		}
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_DOMIANLINE_VISABLE)) {
			if (uiComponent instanceof JCheckBox)
			{
				final JCheckBox ui = (JCheckBox) uiComponent;
				final Object cond = evt.getNewValue();
				ui
						.setSelected(cond instanceof EnumProperty
								&& ((EnumProperty) cond).getEnum() == Constants.EN_TRUE);
			}
		}
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof JCheckBox) {
			final JCheckBox ui = (JCheckBox) uiComponent;
			ui.setSelected(false);
		}
	}

	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_DOMIANLINE_VISABLE)) {
			if (uiComponent instanceof JCheckBox) {
				final JCheckBox ui = (JCheckBox) uiComponent;
				ui.setSelected(false);
			}
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
