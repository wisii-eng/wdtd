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
 * @WisedocChartValueCountAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.NumberContent;
import com.wisii.wisedoc.document.attribute.ChartData2D;
import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：设置值个数
 * 
 * 作者：李晓光 创建日期：2009-5-21
 */
@SuppressWarnings("serial")
public class WisedocChartValueCountAction extends Actions
{

	private boolean flag = Boolean.FALSE;

	@Override
	public void doAction(final ActionEvent e)
	{
		if (e.getSource() instanceof WiseSpinner)
		{
			final WiseSpinner value = (WiseSpinner) e.getSource();

			final int dValue = (Integer) value.getValue();
			
			updateProperty(dValue);
			
			if(true)
				return;
			
			Map<Integer, Object> currentmap = RibbonUIModel.getReadCompletePropertiesByType().get(this.getActionType());
			
			/*currentmap = RibbonUIModel.getCurrentPropertiesByType().get(ActionType.Chart);*/
			
			ChartDataList list = (ChartDataList) currentmap
					.get(Constants.PR_SERIES_VALUE);
			List<BarCodeText> text = (List<BarCodeText>) currentmap
					.get(Constants.PR_VALUE_LABEL);
			boolean flg = ((EnumProperty) currentmap
					.get(Constants.PR_CHART_TYPE)).getEnum() == Constants.EN_PIECHART;
			List<Color> color = null;
			if (flg)
			{
				color = (List<Color>) currentmap.get(Constants.PR_VALUE_COLOR);
			}

			int size = list != null ? list.size() : 0;
			if (list != null)
			{
				if (dValue < size)
				{
					for (int i = dValue; i < size; i++)
					{
						list.remove(i);
						text.remove(i);
						if (flg)
						{
							color.remove(i);
						}
					}
				} else
				{
					ChartData2D add = list.get(size - 1);
					int row = add.getIndex1d();
					int column = add.getIndex2d();
					NumberContent nc = add.getNumbercontent();
					BarCodeText addtext = text.get(size - 1);
					Color cc = color.get(size - 1);
					for (int i = size; i < dValue; i++)
					{
						ChartData2D current = new ChartData2D(row, i - size + 1
								+ column, nc);
						list.add(current);
						text.add(addtext);
						color.add(cc);
					}
				}
				setFOProperty(Constants.PR_SERIES_VALUE, list);
				setFOProperty(Constants.PR_VALUE_LABEL, text);
				setFOProperty(Constants.PR_VALUE_COLOR, color);
			}
		}
	}
	
	private void updateProperty(int newValue){
		Map<Integer, Object> currentmap = RibbonUIModel.getReadCompletePropertiesByType().get(this.getActionType());
		Integer oldValue = (Integer)currentmap.get(Constants.PR_VALUE_COUNT);
		if(oldValue == newValue)
			return;
		/* 判定当前控件是否为饼状图 */
		if(newValue > oldValue){
			setFOProperty(Constants.PR_VALUE_COUNT, newValue);
			return;
		}
		ChartDataList list = (ChartDataList) currentmap.get(Constants.PR_SERIES_VALUE);
		List<BarCodeText> text = (List<BarCodeText>) currentmap.get(Constants.PR_VALUE_LABEL);
		List<Color> color = (List<Color>) currentmap.get(Constants.PR_VALUE_COLOR);
		boolean flg = ((EnumProperty) currentmap.get(Constants.PR_CHART_TYPE)).getEnum() == Constants.EN_PIECHART;
		
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(Constants.PR_VALUE_COUNT, newValue);
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getIndex2d() >= newValue)
					list.remove(i);
			}
			map.put(Constants.PR_SERIES_VALUE, list);
		}
		if(text != null){
			for (int i = newValue; i < text.size(); i++)	{
				text.remove(i);
			}
			map.put(Constants.PR_VALUE_LABEL, text);
		}
		if(flg){
			color = color.subList(0, newValue);
			map.put(Constants.PR_VALUE_COLOR, color);
		}
		
		setFOProperties(map);
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_VALUE_COUNT))
		{
			if (!(uiComponent instanceof WiseSpinner))
			{
				return;
			}
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			final Object value = evt.getNewValue();
			if (value instanceof Integer)
			{
				ui.initValue(value);
			} else
			{
				ui.initValue(6);
			}
		}
		/*
		 * super.uiStateChange(evt); if (!(uiComponent instanceof WiseSpinner))
		 * { return; } final WiseSpinner ui = (WiseSpinner) uiComponent; Object
		 * value = null; if (hasPropertyKey(Constants.PR_VALUE_COUNT)) { value =
		 * evt.getNewValue(); ui.initValue(value); } else if (!flag) { value =
		 * InitialManager.getInitialValue(Constants.PR_VALUE_COUNT, null);
		 * ui.initValue(value); flag = Boolean.TRUE; }
		 */
	}

	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);

		if (uiComponent instanceof WiseSpinner)
		{
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(null);
		}
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (uiComponent instanceof WiseSpinner) {
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(6);
		}
		/*super.setDefaultState(null);
		if (uiComponent instanceof WiseSpinner)
		{
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(0.0);
		}*/
	}

	@Override
	public boolean isAvailable()
	{
		return super.isAvailable();
	}
}
