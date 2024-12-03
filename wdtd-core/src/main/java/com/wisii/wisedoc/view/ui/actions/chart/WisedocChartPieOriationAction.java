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
 * @WisedocChartPieOriationAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.event.ActionEvent;
import java.util.List;

import org.jvnet.flamingo.common.JCommandToggleButton;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.ribbon.chart.PieChartBasicBand;

/**
 * 类功能描述：用于设置饼状图的绘制方向
 * 
 * 作者：李晓光 创建日期：2009-5-22
 */
public class WisedocChartPieOriationAction extends Actions {
	private final int CONSTANTS_KEY = Constants.PR_PIECHART_DIRECTION;

	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof JCommandToggleButton) {
			final JCommandToggleButton btn = (JCommandToggleButton)e.getSource();
			EnumProperty prop = null;
			if(btn.getActionModel().isSelected()){
				prop = new EnumProperty(Constants.EN_INVERT, "");
			}else{
				prop = new EnumProperty(Constants.EN_CLOCKWISE, "");
			}
			setFOProperty(CONSTANTS_KEY, prop);
		}
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		if (hasPropertyKey(CONSTANTS_KEY)) {
			if (uiComponent instanceof JCommandToggleButton) {
				final JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				EnumProperty value = null;
				int flag = Constants.EN_CLOCKWISE;
				if (evt.getNewValue() instanceof EnumProperty) {
					value = ((EnumProperty)evt.getNewValue());
					flag = value.getEnum();
				}
				ui.getActionModel().setSelected(flag == Constants.EN_INVERT);
				if(ui.getActionModel().isSelected()){
					ui.setIcon(MediaResource.getResizableIcon(PieChartBasicBand.INVERT));
				} else {
					ui.setIcon(MediaResource.getResizableIcon(PieChartBasicBand.CLOCKWISE));
				}
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
		return (prop.getEnum() == Constants.EN_PIECHART);
	}
}
