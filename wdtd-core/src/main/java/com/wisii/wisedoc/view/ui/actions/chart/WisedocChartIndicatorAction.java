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
 * @WisedocChartIndicatorAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.ribbon.chart.ChartIndicatorLabelBand.IndicatorLabelType;
import com.wisii.wisedoc.view.ui.ribbon.chart.ChartIndicatorLabelBand.WisedocToggleButton;

/**
 * 类功能描述：用于设置统计图解释标签的位置。
 * 
 * 作者：李晓光 创建日期 2009-5-22
 */
public class WisedocChartIndicatorAction extends Actions {
	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof WisedocToggleButton) {
			final WisedocToggleButton btn = (WisedocToggleButton) e.getSource();
			final IndicatorLabelType type = btn.getValue();
			final EnumProperty propOri = new EnumProperty(type.getOri(), "");
			final EnumProperty propAlign = new EnumProperty(type.getAlign(), "");
			final Map<Integer, Object> map = new HashMap<Integer, Object>();
			map.put(Constants.PR_LENGEND_LABEL_LOCATION, propOri);
			map.put(Constants.PR_LENGEND_LABLE_ALIGNMENT, propAlign);
			setFOProperties(map);
		}
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_LENGEND_LABEL_LOCATION)
				|| hasPropertyKey(Constants.PR_LENGEND_LABLE_ALIGNMENT))
		{
			EnumProperty value = null;
			Map<ActionType, Map<Integer, Object>> pmap = RibbonUIModel
					.getReadCompletePropertiesByType();
			if (pmap == null)
			{
				return;
			}
			final Map<Integer, Object> map = pmap.get(ActionType.GRAPH);
			if (map == null)
				return;
			value = (EnumProperty) map.get(Constants.PR_LENGEND_LABEL_LOCATION);
			if(value==null)
			{
				return;
			}
			final int ori = value.getEnum();
			value = (EnumProperty) map
					.get(Constants.PR_LENGEND_LABLE_ALIGNMENT);
			if(value==null)
			{
				return;
			}
			final int align = value.getEnum();
			final IndicatorLabelType type = IndicatorLabelType.getLabelType(
					ori, align);
			final List<WisedocToggleButton> temp = getComponents();
			for (final WisedocToggleButton button : temp)
			{
				final WisedocToggleButton ui = button;
				ui.setSelected(button.getValue() == type);
			}
		}
	}

	private List<WisedocToggleButton> getComponents() {
		final Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(WisedocChart.INDICATOR_ACTION).get(ActionCommandType.RIBBON_ACTION);

		final List<WisedocToggleButton> temp = new ArrayList<WisedocToggleButton>();

		for (final List<Object> list : comSet) {
			for (final Object uiComponents : list) {
				if (uiComponents instanceof WisedocToggleButton) {
					final WisedocToggleButton ui = (WisedocToggleButton) uiComponents;
					temp.add(ui);
				}
			}
		}
		return temp;
	}
}
