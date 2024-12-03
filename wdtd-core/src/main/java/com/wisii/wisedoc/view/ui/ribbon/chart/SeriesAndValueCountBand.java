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
 * @SeriesAndValueCoundBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 用于设置统计图值个数、系列数，个系列采用的颜色、文本及样式。
 * 
 * 作者：李晓光 创建日期：2009-5-20
 */
public class SeriesAndValueCountBand {
	private final static String TITLE = UiText.COUNT_BAND_TITLE;//MessageResource.getMessage("wisedoc.chart.series.value.style.title");
	private final static String VALUE_COUNT = UiText.COUNT_BAND_VALUE;//MessageResource.getMessage("wisedoc.chart.value.count");
	private final static String SERIES_COUNT = UiText.COUNT_BAND_SERIES;//MessageResource.getMessage("wisedoc.chart.series.count");
	public JRibbonBand getBand(){
		final JRibbonBand band = new JRibbonBand(TITLE, new format_justify_left(), new ChartStyleAction());
		
		value.setPreferredSize(new Dimension(70, 20));
		final SpinnerNumberModel valueModel = new SpinnerNumberModel(6, 1, 255, 1);
		value.setModel(valueModel);
		RibbonUIManager.getInstance().bind(WisedocChart.VALUE_COUNT_ACTION, value);
		
		series.setPreferredSize(new Dimension(70, 20));
		final SpinnerNumberModel seriesModel = new SpinnerNumberModel(1, 1, 255, 1);
		series.setModel(seriesModel);
		RibbonUIManager.getInstance().bind(WisedocChart.SERIES_COUNT_ACTION, series);
		JPanel valuepane = new JPanel(new FlowLayout(FlowLayout.LEADING,0,0));
		valuepane.add(new JLabel(VALUE_COUNT));
		valuepane.add(value);
		JPanel seriespane = new JPanel(new FlowLayout(FlowLayout.LEADING,0,0));
		seriespane.add(new JLabel(SERIES_COUNT));
		seriespane.add(series);
		final WiseSpinner degreevalue = new WiseSpinner(new SpinnerNumberModel(0, 0, 360, 5));
		RibbonUIManager.getInstance().bind(WisedocChart.SERIES_LABEL_DEGREE_ACTION, degreevalue);
		final JButton btnFont = new JButton("A");
		btnFont.setMargin(new Insets(0, 0, 0, 0));
		final ActionListener lis = createListener();
		btnFont.addActionListener(lis);	
		JPanel serieslabelpane = new JPanel(new FlowLayout(FlowLayout.LEADING,0,0));
		serieslabelpane.add(new JLabel(UiText.SERIES_STYLE_BAND_DEGREE));
		serieslabelpane.add(degreevalue);
		serieslabelpane.add(btnFont);
		JPanel mainpanel = new JPanel(new GridLayout(3,1,0,0));
		mainpanel.add(valuepane);
		mainpanel.add(seriespane);
		mainpanel.add(serieslabelpane);
		final JRibbonComponent mainWrapper = new JRibbonComponent(mainpanel);
		band.addRibbonComponent(mainWrapper);
		return band;
	}
	private class ChartStyleAction extends Actions{
		@Override
		public void doAction(final ActionEvent e) {
			// TODO 弹出对话框，用于设计系内的详细属性，系列颜色、系列文本信息、样式。
			final int rows = (Integer)series.getValue();
			final int columns = (Integer)value.getValue();
			final Map<Integer, Object> map = RibbonUIModel.getCurrentPropertiesByType().get(ActionType.Chart);
			final SeriesStypeDialog dialog = new SeriesStypeDialog(rows, columns, map);			
			final DialogResult result =  dialog.showDialog();
			if(result == DialogResult.Cancel) {
				return;
			}
			this.actionType = ActionType.Chart;
			setFOProperties(dialog.getAttributes());
		}
	}
	private ActionListener createListener(){
		final Actions lis = new Actions(){
			@Override
			public void doAction(final ActionEvent e) {
				final Map<Integer, Object> map = RibbonUIModel.getCurrentPropertiesByType().get(ActionType.Chart);
				final FontStyleDialog dialog = new FontStyleDialog(createKeyList(), map);
				final DialogResult result = dialog.showDialog();
				if(result == DialogResult.Cancel) {
					return;
				}
				this.actionType = ActionType.Chart;
				setFOProperties(dialog.getAttributes());
			}
		};
		return lis;
	}
	private List<Integer> createKeyList(){
		/* Key的顺序是按界面顺序给定的，不能打乱了顺序。 */
		final List<Integer> list = new ArrayList<Integer>();
				
		//系列标签
		list.add(Constants.PR_SERIES_LABEL_FONTFAMILY);		
		list.add(Constants.PR_SERIES_LABEL_FONTSIZE);		
		list.add(Constants.PR_SERIES_LABEL_FONTSTYLE);
		list.add(Constants.PR_SERIES_LABEL_COLOR);
		
		//tooltip 
		/*list.add(Constants.PR_CHART_VALUE_FONTFAMILY);		
		list.add(Constants.PR_CHART_VALUE_FONTSIZE);		
		list.add(Constants.PR_CHART_VALUE_FONTSTYLE);
		list.add(Constants.PR_CHART_VALUE_COLOR);*/
		
		return list;
	}
	private final WiseSpinner series = new WiseSpinner();
	private final WiseSpinner value = new WiseSpinner();
}
