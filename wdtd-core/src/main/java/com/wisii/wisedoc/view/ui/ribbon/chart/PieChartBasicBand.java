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
 * @PieChartBasicBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：用于提供对饼状图必要属性设置【具有自身特点的】
 * 通用的属性设置，已经在ChartBasicTask中定义了。
 * 
 * 作者：李晓光 创建日期：2009-5-21
 */
public class PieChartBasicBand {
	private final static String TITLE = UiText.PIE_BASIC_BAND_TITLE;//MessageResource.getMessage("wisedoc.chart.pie.title");
	private final static String ORI = UiText.PIE_BASIC_BAND_ORI;//MessageResource.getMessage("wisedoc.chart.pie.ori");
	private final static String SHOW_LABEL = UiText.PIE_BASIC_BAND_SHOW_LABEL;//MessageResource.getMessage("wisedoc.chart.pie.show.label");
	private final static String SHOW_PER = UiText.PIE_BASIC_BAND_SHOW_PER;//MessageResource.getMessage("wisedoc.chart.pie.show.per");
	private final static String SHOW_FACT = UiText.PIE_BASIC_BAND_SHOW_FACT;//MessageResource.getMessage("wisedoc.chart.pie.show.fact");
	private final static String START_DEGREE = UiText.PIE_BASIC_BAND_SHOW_START_DEGREE;//MessageResource.getMessage("wisedoc.chart.pie.start.degree");
	private final static String ZERO = UiText.SHOW_OR_HIDDEN_BAND_AXIS_ZERO;//MessageResource.getMessage("wisedoc.chart.show.hidden.zero");
	private final static String NULL = UiText.SHOW_OR_HIDDEN_BAND_AXIS_NULL;//MessageResource.getMessage("wisedoc.chart.show.hidden.null");
	public static final String SHOW = "09378.ico";
	public static final String HIDDEN = "09379.ico";
	public static final String CLOCKWISE = "01377.ico";
	public static final String INVERT = "01378.ico";
	public JRibbonBand getBand(){
		final JRibbonBand band = new JRibbonBand(TITLE, new format_justify_left(), createListener());
		//饼状图起始角度
		final WiseSpinner degreevalue = new WiseSpinner();
		degreevalue.setPreferredSize(new Dimension(70, 20));
		final SpinnerNumberModel bottomModel = new SpinnerNumberModel(90, 0, 360, 5);
		degreevalue.setModel(bottomModel);
		RibbonUIManager.getInstance().bind(WisedocChart.START_DEGREE_ACTION, degreevalue);
		
		//值坐标轴方向
		final JCommandToggleButton btnOri = new JCommandToggleButton(ORI, MediaResource.getResizableIcon("01377.ico"));
		/*updateIcon(btnOri, "01378.ico", "01377.ico");*/
		RibbonUIManager.getInstance().bind(WisedocChart.PIE_ORIATION_ACTION, btnOri);
		btnOri.setDisplayState(CommandButtonDisplayState.BIG);
		
		//鼠标浮动显示解释说明
		final RichTooltip pbTooltip = new RichTooltip();
		pbTooltip.setTitle(RibbonUIText.PASTE_BUTTON_TITLE);
		pbTooltip.addDescriptionSection(RibbonUIText.PASTE_BUTTON_DESCRIPTION);
		btnOri.setActionRichTooltip(pbTooltip);
		
		//是否显示标签
		final JCommandToggleButton btnLabel = new JCommandToggleButton(SHOW_LABEL, MediaResource.getResizableIcon("09379.ico"));
		/*updateIcon(btnLabel, "09378.ico", "09379.ico");*/
		RibbonUIManager.getInstance().bind(WisedocChart.PIE_SHOW_LABEL_ACTION, btnLabel);
		
		//是否显示百分比值
		final JCommandToggleButton btnPercent = new JCommandToggleButton(SHOW_PER, MediaResource.getResizableIcon("09379.ico"));
		/*updateIcon(btnPercent, "09378.ico", "09379.ico");*/
		RibbonUIManager.getInstance().bind(WisedocChart.PIE_SHOW_PERCENT_ACTION, btnPercent);
		
		//是否显示实际值
		final JCommandToggleButton btnFact = new JCommandToggleButton(SHOW_FACT, MediaResource.getResizableIcon("09379.ico"));
		/*updateIcon(btnFact, "09378.ico", "09379.ico");*/
		RibbonUIManager.getInstance().bind(WisedocChart.PIE_SHOW_FACT_VALUE_ACTION, btnFact);
		
		//是否显示0值
		final JCommandToggleButton btnZero = new JCommandToggleButton(ZERO, MediaResource.getResizableIcon("09379.ico"));
		/*updateIcon(btnZero, "09378.ico", "09379.ico");*/
		RibbonUIManager.getInstance().bind(WisedocChart.SHOW_ZERO_VALUE_ACTION, btnZero);
		
		//是否显示Null值
		final JCommandToggleButton btnNull = new JCommandToggleButton(NULL, MediaResource.getResizableIcon("09379.ico"));
		/*updateIcon(btnNull, "09378.ico", "09379.ico");*/
		RibbonUIManager.getInstance().bind(WisedocChart.SHOW_NULL_VALUE_ACTION, btnNull);
		
		final JRibbonComponent degree = new JRibbonComponent(degreevalue);
		band.startGroup();
		band.addRibbonComponent(degree);
		band.startGroup();
		band.addCommandButton(btnOri, RibbonElementPriority.MEDIUM);
		band.addCommandButton(btnPercent, RibbonElementPriority.MEDIUM);
		band.addCommandButton(btnFact, RibbonElementPriority.MEDIUM);
		band.addCommandButton(btnLabel, RibbonElementPriority.MEDIUM);
		
		band.addCommandButton(btnZero, RibbonElementPriority.MEDIUM);
		band.addCommandButton(btnNull, RibbonElementPriority.MEDIUM);
		return band;
	}
	private void updateIcon(final JCommandToggleButton btn, final String selectIcon, final String unSelectIcon){
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(final ActionEvent e) {
				if(btn.getActionModel().isSelected()) {
					btn.setIcon(MediaResource.getResizableIcon(selectIcon));
				}else{
					btn.setIcon(MediaResource.getResizableIcon(unSelectIcon));
				}
			}
		});
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
	/*	list.add(Constants.PR_SERIES_LABEL_FONTFAMILY);		
		list.add(Constants.PR_SERIES_LABEL_FONTSIZE);		
		list.add(Constants.PR_SERIES_LABEL_FONTSTYLE);
		list.add(Constants.PR_SERIES_LABEL_COLOR);*/
		
		//tooltip 
		list.add(Constants.PR_CHART_VALUE_FONTFAMILY);		
		list.add(Constants.PR_CHART_VALUE_FONTSIZE);		
		list.add(Constants.PR_CHART_VALUE_FONTSTYLE);
		list.add(Constants.PR_CHART_VALUE_COLOR);
		
		return list;
	}
}
