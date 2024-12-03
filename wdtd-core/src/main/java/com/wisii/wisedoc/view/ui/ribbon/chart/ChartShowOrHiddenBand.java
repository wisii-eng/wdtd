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
 * @ChartShowOrHiddenBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：用于设置显示/隐藏坐标线、零基线、3D效果、值。
 * 
 * 
 * 作者：李晓光 创建日期：2009-5-20
 */
public class ChartShowOrHiddenBand {
	private final static String TITLE = UiText.SHOW_OR_HIDDEN_BAND_TITLE;//MessageResource.getMessage("wisedoc.chart.show.hidden.title");
	private final static String BASELINE = UiText.SHOW_OR_HIDDEN_BAND_BASELINE;//MessageResource.getMessage("wisedoc.chart.show.hidden.baseline");
	private final static String STYLE_3D = UiText.SHOW_OR_HIDDEN_BAND_3D;//MessageResource.getMessage("wisedoc.chart.show.hidden.3d");
	private final static String VALUE = UiText.SHOW_OR_HIDDEN_BAND_VALUE;//MessageResource.getMessage("wisedoc.chart.show.hidden.value");
	private final static String AXIS_Y = UiText.SHOW_OR_HIDDEN_BAND_AXIS_Y;//MessageResource.getMessage("wisedoc.chart.show.hidden.axis.y");
	private final static String AXIS_X = UiText.SHOW_OR_HIDDEN_BAND_AXIS_X;//MessageResource.getMessage("wisedoc.chart.show.hidden.axis.x");
	private final static String DEPTH_3D = UiText.SHOW_OR_HIDDEN_BAND_AXIS_DEPTH;//MessageResource.getMessage("wisedoc.chart.show.hidden.depth");
//	private final static String ZERO = UiText.SHOW_OR_HIDDEN_BAND_AXIS_ZERO;//MessageResource.getMessage("wisedoc.chart.show.hidden.zero");
//	private final static String NULL = UiText.SHOW_OR_HIDDEN_BAND_AXIS_NULL;//MessageResource.getMessage("wisedoc.chart.show.hidden.null");
	
	public JRibbonBand getBand(){
		final JRibbonBand band = new JRibbonBand(TITLE, new format_justify_left(), null);
		final JCheckBox checkBaseLine = new JCheckBox(BASELINE);
		final JCheckBox check3D = new JCheckBox(STYLE_3D);
		final JCheckBox checkValue = new JCheckBox(VALUE);
		final JCheckBox checkValueAxis = new JCheckBox(AXIS_Y);
		final JCheckBox checkSeriesAxis = new JCheckBox(AXIS_X);
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(border);
		final JLabel labDepth = new JLabel(DEPTH_3D);
		final FixedLengthSpinner txtDepth = new FixedLengthSpinner();
		txtDepth.setPreferredSize(new Dimension(68, 21));
		panel.add(labDepth, BorderLayout.WEST);
		panel.add(txtDepth, BorderLayout.CENTER);
		check3D.addActionListener(createListener(txtDepth));
		/*check3D.doClick();*/
		
		//是否显示0值
		/*final JCommandToggleButton btnZero = new JCommandToggleButton(ZERO, MediaResource.getResizableIcon("09378.ico"));
		updateIcon(btnZero, "09379.ico", "09378.ico");
		RibbonUIManager.getInstance().bind(WisedocChart.SHOW_ZERO_VALUE_ACTION, btnZero);*/
		
		//是否显示Null值
		/*final JCommandToggleButton btnNull = new JCommandToggleButton(NULL, MediaResource.getResizableIcon("09378.ico"));
		updateIcon(btnNull, "09379.ico", "09378.ico");
		RibbonUIManager.getInstance().bind(WisedocChart.SHOW_NULL_VALUE_ACTION, btnNull);*/
		
		RibbonUIManager.getInstance().bind(WisedocChart.SHOW_BASE_LINE_ACTION, checkBaseLine);
		RibbonUIManager.getInstance().bind(WisedocChart.SHOW_3D_ACTION, check3D);
		RibbonUIManager.getInstance().bind(WisedocChart.SHOW_AXIS_X_ACTION, checkSeriesAxis);
		RibbonUIManager.getInstance().bind(WisedocChart.SHOW_AXIS_Y_ACTION, checkValueAxis);
		RibbonUIManager.getInstance().bind(WisedocChart.SHOW_VALUE_ACTION, checkValue);
		RibbonUIManager.getInstance().bind(WisedocChart.DEPTH3D_ACTION, txtDepth);
		
		final JRibbonComponent compBaseLine = new JRibbonComponent(checkBaseLine);
		final JRibbonComponent compValue = new JRibbonComponent(checkValue);
		final JRibbonComponent comp3D = new JRibbonComponent(check3D);
		final JRibbonComponent compValueAxis = new JRibbonComponent(checkValueAxis);
		final JRibbonComponent compSeriesAxis = new JRibbonComponent(checkSeriesAxis);
		final JRibbonComponent comp3DDepth = new JRibbonComponent(panel);
		
		band.addRibbonComponent(compBaseLine);
		band.addRibbonComponent(compValue);
		band.addRibbonComponent(comp3D);
		band.addRibbonComponent(compValueAxis);
		band.addRibbonComponent(compSeriesAxis);
		band.addRibbonComponent(comp3DDepth);
		
		/*band.startGroup();
		band.addCommandButton(btnZero, RibbonElementPriority.MEDIUM);
		band.addCommandButton(btnNull, RibbonElementPriority.MEDIUM);*/
		
		return band;
	}
	private ActionListener createListener(final FixedLengthSpinner spinner){
		final ActionListener lis = new ActionListener(){
			@Override
			public void actionPerformed(final ActionEvent e) {
				final JCheckBox box = (JCheckBox)e.getSource();
				spinner.setEnabled(box.isSelected());
			}
		};
		return lis;
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
	private final Border border = BorderFactory.createEmptyBorder(1, 5, 0, 0);
}
