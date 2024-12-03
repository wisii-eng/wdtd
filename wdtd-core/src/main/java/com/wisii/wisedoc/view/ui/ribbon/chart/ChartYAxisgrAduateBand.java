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
 * @ChartYAxisgrAduateBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
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
 * 类功能描述：用于设置值轴上刻度的范围，即最大值、最小值。
 * 刻度的步长，柱状图中数据相对于柱状图顶部的偏移量。坐标精度【小数点位数】
 * 
 * 作者：李晓光 创建日期：2009-5-20
 */
public class ChartYAxisgrAduateBand {
	private final static String TITLE = UiText.YAXIS_ADUATE_BAND_TITLE;//MessageResource.getMessage("wisedoc.chart.axisy.title");
	private final static String MIN = UiText.YAXIS_ADUATE_BAND_MIN;//MessageResource.getMessage("wisedoc.chart.aduate.min");
	private final static String MAX = UiText.YAXIS_ADUATE_BAND_MAX;//MessageResource.getMessage("wisedoc.chart.aduate.max");
	private final static String STEP = UiText.YAXIS_ADUATE_BAND_STEP;//MessageResource.getMessage("wisedoc.chart.aduate.step");
	private final static String RANGE = UiText.YAXIS_ADUATE_BAND_RANGE;//MessageResource.getMessage("wisedoc.chart.aduate.range");
	private final static String OFFSET = UiText.YAXIS_ADUATE_BAND_OFFSET;//MessageResource.getMessage("wisedoc.chart.aduate.offset");
	private final static String ORI = UiText.YAXIS_ADUATE_BAND_ORI;//MessageResource.getMessage("wisedoc.chart.aduate.ori");
	private final static String AUTO = UiText.YAXIS_ADUATE_AUTO;
	public JRibbonBand getBand(){
		final JRibbonBand band = new JRibbonBand(TITLE, new format_justify_left(), createListener());
		//最小刻度、最大刻度、刻度跨度
		final JLabel labMin = new JLabel(MIN);
		final SpinnerNumberModel modelMin = new SpinnerNumberModel(0, MIN_VALUE, MAX_VALUE, 0.5);
		final WiseSpinner spiMin = new WiseSpinner(modelMin);
		
		final JLabel labMax = new JLabel(MAX);
		final SpinnerNumberModel modelMax = new SpinnerNumberModel(10.0, MIN_VALUE, MAX_VALUE, 0.5);
		final WiseSpinner spiMax = new WiseSpinner(modelMax);
		
		final JLabel labStep = new JLabel(STEP);
		final SpinnerNumberModel modelStep = new SpinnerNumberModel(2.5, 0.0, MAX_VALUE, 0.5);
		final WiseSpinner spiStep = new WiseSpinner(modelStep);
		
		
		final JPanel minPanel = createPanel(labMin, spiMin);
		final JPanel maxPanel = createPanel(labMax, spiMax);
		final JPanel stepPanel = createPanel(labStep, spiStep);
		
		final JCheckBox chkMin = new JCheckBox(AUTO);
		final JCheckBox chkMax = new JCheckBox(AUTO);
		final JCheckBox chkStep = new JCheckBox(AUTO);

		
		
		/*chkMin.addActionListener(new Actions(){
			@Override
			public void doAction(final ActionEvent e) {
				if(chkMin.isSelected()){
					this.actionType = ActionType.Chart;
					setFOProperty(Constants.PR_RANGEAXIS_MINUNIT, null);
				} else {
					setFOProperty(Constants.PR_RANGEAXIS_MINUNIT, spiMin.getValue());
				}
			}
		});
		chkMax.addActionListener(new Actions(){
			@Override
			public void doAction(final ActionEvent e) {
				if(chkMax.isSelected()){
					this.actionType = ActionType.Chart;
					setFOProperty(Constants.PR_RANGEAXIS_MAXUNIT, null);
				} else {
					setFOProperty(Constants.PR_RANGEAXIS_MAXUNIT, spiMax.getValue());
				}
			}
		});
		chkStep.addActionListener(new Actions(){
			@Override
			public void doAction(final ActionEvent e) {
				if(chkStep.isSelected()){
					this.actionType = ActionType.Chart;
					setFOProperty(Constants.PR_RANGEAXIS_UNITINCREMENT, null);
				} else {
					setFOProperty(Constants.PR_RANGEAXIS_UNITINCREMENT, spiStep.getValue());
				}
			}
		});	*/
		//精度、偏移量
		final JLabel labRange = new JLabel(RANGE);
		final SpinnerNumberModel modelRange = new SpinnerNumberModel(2, 0, MAX_RANGE, 1);
		final WiseSpinner spiRange = new WiseSpinner(modelRange);
		
		final JLabel labOffset = new JLabel(OFFSET);
		final FixedLengthSpinner txtOffset = new FixedLengthSpinner(new SpinnerFixedLengthModel(new FixedLength(0d,"mm"),new FixedLength(-300000),null,-1));
		
		
		/*final JLabel labFont = new JLabel("字体：");
		final JTextField txtFont = new JTextField();
		txtFont.setPreferredSize(new Dimension(94, 20));
		txtFont.setEditable(Boolean.FALSE);*/
		
		final JPanel rangePanel = createPanel(labRange, spiRange);
		final JCheckBox chkRange = new JCheckBox(AUTO);
		rangePanel.add(chkRange, BorderLayout.EAST);
		final JPanel offsetPanel = createPanel(labOffset, txtOffset);
		/*final JPanel fontPanel = new JPanel(new BorderLayout());
		fontPanel.add(labFont, BorderLayout.WEST);*/
		/*fontPanel.add(txtFont, BorderLayout.CENTER);*/
		/*chkRange.addActionListener(new Actions(){
			@Override
			public void doAction(final ActionEvent e) {
				if(chkRange.isSelected()){
					this.actionType = ActionType.Chart;
					setFOProperty(Constants.PR_RANGEAXIS_PRECISION, null);
				} else {
					setFOProperty(Constants.PR_RANGEAXIS_PRECISION, spiRange.getValue());
				}
			}
		});*/
		/*spiRange.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				if(evt.getPropertyName() != "enabled") {
					return;
				}
				chkRange.setEnabled((Boolean)evt.getNewValue());
			}
		});*/
		
		chkMin.setActionCommand(Constants.PR_RANGEAXIS_MINUNIT + "");
		chkMax.setActionCommand(Constants.PR_RANGEAXIS_MAXUNIT + "");
		chkStep.setActionCommand(Constants.PR_RANGEAXIS_UNITINCREMENT + "");
		chkRange.setActionCommand(Constants.PR_RANGEAXIS_PRECISION + "");
		//值坐标轴方向
		final JCommandToggleButton btnOri = new JCommandToggleButton(ORI, MediaResource.getResizableIcon("01144.ico")/*MediaResource.getResizableIcon("Paste.gif")*/);
		
		//设置按钮的种类和显示状态
		btnOri.setDisplayState(CommandButtonDisplayState.BIG);
						
		//Bind Action
		RibbonUIManager.getInstance().bind(WisedocChart.MIN_ADUATE_ACTION, spiMin);
		RibbonUIManager.getInstance().bind(WisedocChart.MAX_ADUATE_ACTION, spiMax);
		RibbonUIManager.getInstance().bind(WisedocChart.STEP_ADUATE_ACTION, spiStep);
		RibbonUIManager.getInstance().bind(WisedocChart.ADUATE_PRECISION_ACTION, spiRange);
		RibbonUIManager.getInstance().bind(WisedocChart.OFFSET_ACTION, txtOffset);
		RibbonUIManager.getInstance().bind(WisedocChart.CHART_ORIENTATION, btnOri);
		Object[] boxs = {chkMin, chkMax, chkStep, chkRange};
		RibbonUIManager.getInstance().bind(WisedocChart.AUTO_ADUATE_ACTION, boxs);
				
		
		final JRibbonComponent compMin = new JRibbonComponent(minPanel);
		final JRibbonComponent compMax = new JRibbonComponent(maxPanel);
		final JRibbonComponent compStep = new JRibbonComponent(stepPanel);
		
		final JRibbonComponent compRange = new JRibbonComponent(rangePanel);
		final JRibbonComponent compOffset = new JRibbonComponent(offsetPanel);
		/*final JRibbonComponent compFont = new JRibbonComponent(fontPanel);*/
		
		final JRibbonComponent compAotuMin = new JRibbonComponent(chkMin);
		final JRibbonComponent compAotuMax = new JRibbonComponent(chkMax);
		final JRibbonComponent compAotuStep = new JRibbonComponent(chkStep);
		
		band.addRibbonComponent(compMin);
		band.addRibbonComponent(compMax);
		band.addRibbonComponent(compStep);
		
		band.addRibbonComponent(compAotuMin);
		band.addRibbonComponent(compAotuMax);
		band.addRibbonComponent(compAotuStep);
		
		band.startGroup();
		band.addRibbonComponent(compRange);
		band.addRibbonComponent(compOffset);
		/*band.addRibbonComponent(compFont);*/
		
		band.startGroup();
		band.addCommandButton(btnOri, RibbonElementPriority.MEDIUM);
		return band;
	}
	
	private JPanel createPanel(final JLabel lab, final JComponent spi){
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(lab, BorderLayout.WEST);
		panel.add(spi, BorderLayout.CENTER);
		
		return panel;
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
		//值标签
		list.add(Constants.PR_VALUE_LABEL_FONTFAMILY);
		list.add(Constants.PR_VALUE_LABEL_FONTSIZE);
		list.add(Constants.PR_VALUE_LABEL_FONTSTYLE);
		list.add(Constants.PR_VALUE_LABEL_COLOR);
		
		return list;
	}

	private final static double MAX_VALUE = Short.MAX_VALUE;
	private final static double MIN_VALUE = Short.MIN_VALUE;
	private final static int MAX_RANGE = Byte.MAX_VALUE;
	public static final String AXISY = "01144.ico";
	public static final String	AXISX = "01142.ico";
}
