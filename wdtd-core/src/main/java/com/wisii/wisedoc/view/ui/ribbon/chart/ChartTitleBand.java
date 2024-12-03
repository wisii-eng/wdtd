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
 * @ChartTitleBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：用于设置统计图的标题。
 * 
 * 作者：李晓光 创建日期：2009-5-19
 */
public class ChartTitleBand {
	private final static String TITLE = UiText.TITLE_BAND_TITLE;//MessageResource.getMessage("wisedoc.chart.title");
	private final static String CHART_BAR = UiText.TITLE_BAND_BAR;//MessageResource.getMessage("wisedoc.chart.type.bar");
	private final static String CHART_PIE = UiText.TITLE_BAND_PIE;//MessageResource.getMessage("wisedoc.chart.type.pie");
	private final static String CHART_LINE = UiText.TITLE_BAND_LINE;//"折线图";
	private final static String CHART_STACKBAR = UiText.TITLE_BAND_STACKBAR;//"堆积图";
	private final static String CHART_AREA = UiText.TITLE_BAND_AREA;//"面积图";
	private final static String TYPE = UiText.TITLE_BAND_AREA;
	public static enum ChartType{
		/* 柱装图 208*/
		BAR(Constants.EN_BARCHART, CHART_BAR),
		/* 饼状图209 */
		PIE(Constants.EN_PIECHART, CHART_PIE),
		/* 折线图210 */
		LINE(Constants.EN_LINECHART, CHART_LINE),
		/* 堆积图211 */
		STACK_BAR(Constants.EN_STACKBARCHART, CHART_STACKBAR),
		/* 面积图212 */
		AREA(Constants.EN_AREACHART, CHART_AREA);
		private int value = -1;
		private String name = "";
		private ChartType(final int value, final String name){
			this.value = value;
			this.name = name;
		}
		public int getValue(){
			return this.value;
		}
		@Override
		public String toString() {
			return name;
		}
		public final static ChartType getType(final int value){
			for (final ChartType chart : ChartType.values()) {
				if(chart.getValue() == value) {
					return chart;
				}
			}
			return null;
		}
	}
	@SuppressWarnings("serial")
	public JRibbonBand getBand(){
		final ActionListener lis = createListener();
		final JRibbonBand band = new JRibbonBand(TITLE, MediaResource.getResizableIcon("chart.gif"), lis);
		
		final JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		final JTextField txtTitle = new JTextField(10);
		
		final JButton btnFont = new JButton("A");
		btnFont.setMargin(new Insets(0, 2, 0, 2));
		txtTitle.setLayout(new BorderLayout());
		txtTitle.add(btnFont, BorderLayout.EAST);
		btnFont.addActionListener(lis);		
		btnFont.setCursor(Cursor.getDefaultCursor());
		RibbonUIManager.getInstance().bind(WisedocChart.TITLE_ACTION, txtTitle);
		
		/*final String[] type = {CHART_BAR, CHART_PIE, CHART_LINE, CHART_STACKBAR, CHART_AREA};*/
		final WiseCombobox combox = new WiseCombobox(ChartType.values());
		combox.setSelectedIndex(0);
		RibbonUIManager.getInstance().bind(WisedocChart.TYPE_ACTION, combox);
		titlePanel.add(new JLabel(UiText.CHART_TITLE));
		titlePanel.add(txtTitle);
		JPanel typepanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		typepanel.add(new JLabel(UiText.CHART_TYPE));
		typepanel.add(combox);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		final JRibbonComponent titleWrapper = new JRibbonComponent(titlePanel);
		final JRibbonComponent typeWrapper = new JRibbonComponent(typepanel);
		band.addRibbonComponent(typeWrapper);
		band.addRibbonComponent(titleWrapper);
		
		return band;
	}
	@SuppressWarnings("serial")
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
		list.add(Constants.PR_TITLE_FONTFAMILY);		
		list.add(Constants.PR_TITLE_FONTSIZE);		
		list.add(Constants.PR_TITLE_FONTSTYLE);
		list.add(Constants.PR_TITLE_COLOR);
		return list;
	}
}
