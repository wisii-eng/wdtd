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
 * @WisedocChartMaxAduateAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.event.ActionEvent;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：设置值坐标轴的最大刻度
 * 
 * 作者：李晓光 创建日期：2009-5-22
 */
public class WisedocChartMaxAduateAction extends Actions {
	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof WiseSpinner) {
			final WiseSpinner value = (WiseSpinner) e.getSource();

			final Double dValue = (Double) value.getValue();
			setFOProperty(Constants.PR_RANGEAXIS_MAXUNIT, dValue);
		}
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_RANGEAXIS_MAXUNIT)) {
			if (uiComponent instanceof WiseSpinner) {
				final WiseSpinner ui = (WiseSpinner) uiComponent;
				Object maxunit = evt.getNewValue();
				if (maxunit instanceof Double)
				{
					ui.initValue(maxunit);
				}
				else
				{
					ui.initValue(10.0);
				}
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);

		if (uiComponent instanceof WiseSpinner) {
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(Double.NaN);
		}
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof WiseSpinner) {
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(10.0);
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
		return (prop.getEnum() != Constants.EN_PIECHART) && chart.getAttribute(Constants.PR_RANGEAXIS_MAXUNIT) != null;
	}
}
