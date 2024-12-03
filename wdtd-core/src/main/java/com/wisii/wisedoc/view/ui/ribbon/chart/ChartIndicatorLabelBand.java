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
 * @ChartIndicatorLabelBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：用于设置指示标签相关信息，如：指示标签文本【可能来自绑定对象】
 * 指示标签的字体信息，指示标签的代表颜色。指示标签相对统计图的位置、对齐方式。
 * 
 * 作者：李晓光 创建日期：2009-5-21
 */
public class ChartIndicatorLabelBand {
	private final static String TITLE = UiText.INDICATOR_LABEL_BAND_TITLE;//MessageResource.getMessage("wisedoc.chart.indicator.title");
	private final static String LOCATION = UiText.INDICATOR_LABEL_BAND_LOCATION;//MessageResource.getMessage("wisedoc.chart.indicator.location");
	public JRibbonBand getBand(){
		final JRibbonBand band = new JRibbonBand(TITLE, new format_justify_left(), createListener());
		
		final JPanel alignPanel = createAlignPanel();		
		final JRibbonComponent compAlign = new JRibbonComponent(alignPanel);
		band.addRibbonComponent(compAlign);
		
		return band;
	}
	private JPanel createAlignPanel(){
		final JPanel main = new JPanel(new BorderLayout(0, 5));
		//top
		final JPanel top = new JPanel(new BorderLayout(0, 0));
		JToggleButton btn = createButton("↖", IndicatorLabelType.WEST_NORTH); 
		top.add(btn, BorderLayout.WEST);
		/*RibbonUIManager.getInstance().bind(WisedocChart.INDICATOR_ACTION, btn);*/
		
		final JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		btn = createButton("↑", IndicatorLabelType.NORTH);
		center.add(btn);
		/*RibbonUIManager.getInstance().bind(WisedocChart.INDICATOR_ACTION, btn);*/
		
		top.add(center, BorderLayout.CENTER);
		btn = createButton("↗", IndicatorLabelType.EAST_NORTH);
		top.add(btn, BorderLayout.EAST);
		main.add(top, BorderLayout.NORTH);
		/*RibbonUIManager.getInstance().bind(WisedocChart.INDICATOR_ACTION, btn);*/
		//left
		final JPanel left = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		btn = createButton("←", IndicatorLabelType.WEST);
		left.add(btn);
		main.add(left, BorderLayout.WEST);
		/*RibbonUIManager.getInstance().bind(WisedocChart.INDICATOR_ACTION, btn);*/
		
		//bottom
		final JPanel bottom = new JPanel(new BorderLayout(0, 0));
		btn = createButton("↙", IndicatorLabelType.WEST_SOUTH);
		bottom.add(btn, BorderLayout.WEST);
		/*RibbonUIManager.getInstance().bind(WisedocChart.INDICATOR_ACTION, btn);*/
		
		final JPanel middel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		btn = createButton("↓", IndicatorLabelType.SOUTH);
		btn.setSelected(Boolean.TRUE);
		middel.add(btn);
		/*RibbonUIManager.getInstance().bind(WisedocChart.INDICATOR_ACTION, btn);*/
		bottom.add(middel, BorderLayout.CENTER);
		btn = createButton("↘", IndicatorLabelType.EAST_SOUTH);
		/*RibbonUIManager.getInstance().bind(WisedocChart.INDICATOR_ACTION, btn);*/
		bottom.add(btn, BorderLayout.EAST);
		main.add(bottom, BorderLayout.SOUTH);
		//right
		final JPanel right = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		btn = createButton("→", IndicatorLabelType.EAST);
		right.add(btn);
		final List<AbstractButton> btns = new ArrayList<AbstractButton>();
		for (final Enumeration<AbstractButton> e = group.getElements(); e.hasMoreElements();) {
			btns.add(e.nextElement());
		}
		RibbonUIManager.getInstance().bind(WisedocChart.INDICATOR_ACTION, btns.toArray());
		main.add(right, BorderLayout.EAST);
		//center
		final JLabel labLocation = new JLabel(LOCATION);
		/*labLocation.setBorder(BorderFactory.createLineBorder(Color.BLACK));*/
		main.add(labLocation, BorderLayout.CENTER);
		
		return main;
	}
	private JToggleButton createButton(final String s, final IndicatorLabelType value){
		final WisedocToggleButton btn = new WisedocToggleButton(s);
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.setValue(value);
		group.add(btn);
		return btn;
	}
	public ButtonGroup getGroup(){
		return group;
	}
	private ActionListener createListener(){
		final Actions lis = new Actions(){
			@Override
			public void doAction(final ActionEvent e) {
				//TODO 设置解释标签。可能通过绑定的方式来实现。【想通过弹出对话框的方式来实现】
//				System.out.println("ChartIndicatorLabelBand" + "设置解释标签【指示标签】内容");
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
		list.add(Constants.PR_LENGEND_LABEL_FONTFAMILY);		
		list.add(Constants.PR_LENGEND_LABEL_FONTSIZE);		
		list.add(Constants.PR_LENGEND_LABEL_FONTSTYLE);
		list.add(Constants.PR_LENGEND_LABEL_COLOR);		
		return list;
	}
	public static class WisedocToggleButton extends JToggleButton{
		public WisedocToggleButton(final String text){
			super(text);
		}
		IndicatorLabelType value = null;

		public IndicatorLabelType getValue() {
			return value;
		}

		public void setValue(final IndicatorLabelType value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return value.toString();
		}
	}
	/**
	 * 定义指示标签的枚举方向
	 * @author 李晓光
	 */
	public static enum IndicatorLabelType{
		NORTH(Constants.EN_TOP, Constants.EN_CENTER),
		SOUTH(Constants.EN_BOTTOM, Constants.EN_CENTER),
		WEST(Constants.EN_LEFT, Constants.EN_CENTER),
		EAST(Constants.EN_RIGHT, Constants.EN_CENTER),
		
		WEST_SOUTH(Constants.EN_LEFT, Constants.EN_BOTTOM),
		/*SOUTH_WEST(Constants.EN_BOTTOM, Constants.EN_LEFT),*/
		
		WEST_NORTH(Constants.EN_LEFT, Constants.EN_TOP),
		/*NORTH_WEST(Constants.EN_TOP, Constants.EN_LEFT),*/
		
		EAST_SOUTH(Constants.EN_RIGHT, Constants.EN_BOTTOM),
		/*SOUTH_EAST(Constants.EN_BOTTOM, Constants.EN_RIGHT),*/
		
		EAST_NORTH(Constants.EN_RIGHT, Constants.EN_TOP),
		/*NORTH_EAST(Constants.EN_TOP, Constants.EN_RIGHT),*/
		NONE(-1, -1);
		private IndicatorLabelType(final int ori, final int align){
			this.ori = ori;
			this.align = align;
		}
		private int ori = -1;
		private int align = -1;	
		public int getOri() {
			return ori;
		}
		public int getAlign() {
			return align;
		}
		public static final IndicatorLabelType getLabelType(final int ori, final int align){
			final IndicatorLabelType[] values = IndicatorLabelType.values();
			for (final IndicatorLabelType type : values) {
				if(isSame(type, ori, align)) {
					return type;
				}
			}
			return NONE;
		}
		private static boolean isSame(final IndicatorLabelType type, final int argOri, final int argAlgin){
			return (type.ori == argOri) && ( type.align == argAlgin);
		}
	}
	private final static ButtonGroup group = new ButtonGroup();
}
