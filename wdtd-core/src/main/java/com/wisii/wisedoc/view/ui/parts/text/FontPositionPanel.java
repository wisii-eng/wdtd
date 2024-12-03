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
package com.wisii.wisedoc.view.ui.parts.text;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.render.awt.viewer.WisedocConfigurePreviewPanel;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 字体位置设置对话框面板
 * @author 闫舒寰
 * @version 1.0 2009/01/16
 */
public class FontPositionPanel extends JPanel {

	/*private JComboBox comboBox_7;*/
	private JSpinner fontWordSpaceSpinner;
	private JComboBox fontWordSpaceComboBox;

	private JComboBox fontStretchComboBox;
	/*private JComboBox comboBox_2;*/
	private JSpinner fontSpaceValueSpinner;
	private JComboBox fontSpaceCombobox;
	/*private JComboBox comboBox_1;*/
	private JSpinner fontPositionValueSpinner;
	private JComboBox fontPositionCombox;
	
	//下拉菜单中的文字
	//正常、上标和下标
	private String[] fontPosition = UiText.FONT_POSITION_LIST; 
	//"磅", "厘米", "像素", "英尺"
	private String[] fontMeasurement = UiText.COMMON_MEASUREMENT_LIST;
	
	//"标准", "加宽", "紧缩"
	private String[] fontStretch = UiText.FONT_STRETCH_LIST;
	//文字间距下拉菜单： 正常、自定义
	private String[] fontSpace = UiText.FONT_SPACE_LIST;
	
	//tab中的文字
	//数值：
	String value = UiText.COMMON_VALUE_LABEL;
	//单位：
	String measurement = UiText.COMMON_MEASUREMENT_LABEL;
	
	/**
	 * Create the panel
	 */
	public FontPositionPanel() {
		super();
//		setPreferredSize(new Dimension(237, 140));
		//文字位置下拉菜单
		JLabel fontPositionLabel;
		fontPositionLabel = new JLabel();
		fontPositionLabel.setText(UiText.FONT_POSITION_LABEL);

		fontPositionCombox = new WiseCombobox();
		fontPositionCombox.setModel(new DefaultComboBoxModel(fontPosition));
		
		
		//文字位置值
		JLabel fontPositionValueLabel;
		fontPositionValueLabel = new JLabel();
		fontPositionValueLabel.setText(value);
		
		fontPositionValueSpinner = new FixedLengthSpinner();//new JSpinner();
		fontPositionValueSpinner.setModel(new SpinnerFixedLengthModel(new FixedLength(0d,"pt"), new FixedLength(-1000d,"pt"), new FixedLength(1000d,"pt"), -1));
		
		
		//文字位置单位
		/*JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(measurement);*/

		/*comboBox_1 = new WiseCombobox();
		comboBox_1.setModel(new DefaultComboBoxModel(fontMeasurement));*/
		
		//字符间距
		JLabel fontSpaceLabel;
		fontSpaceLabel = new JLabel();
		fontSpaceLabel.setText(UiText.FONT_SPACE_LABEL);

		fontSpaceCombobox = new WiseCombobox();
		fontSpaceCombobox.setModel(new DefaultComboBoxModel(fontSpace));

		JLabel fontSpaceValueLabel;
		fontSpaceValueLabel = new JLabel();
		fontSpaceValueLabel.setText(value);

		fontSpaceValueSpinner = new FixedLengthSpinner();//new JSpinner();
		fontSpaceValueSpinner.setModel(new SpinnerFixedLengthModel(new FixedLength(0d,"pt"), new FixedLength(-1000d,"pt"), new FixedLength(1000d,"pt"), -1));

		/*JLabel label_5;
		label_5 = new JLabel();
		label_5.setText(measurement);*/

		/*comboBox_2 = new WiseCombobox();
		comboBox_2.setModel(new DefaultComboBoxModel(fontMeasurement));*/
		
		//字符缩放
		JLabel fontStretchLabel;
		fontStretchLabel = new JLabel();
		fontStretchLabel.setText(UiText.FONT_STRETCH_LABEL);
		
		fontStretchComboBox = new WiseCombobox();
		fontStretchComboBox.setModel(new DefaultComboBoxModel(fontStretch));
		
		
		//位置样式
		
		//这里应该注意绑定的前后顺序，要先绑定数值和单位控件(带input的action），这样当选择自定义的时候他们会被正确的引用
		RibbonUIManager.getInstance().bind(Font.POSITION_INPUT_ACTION, ActionCommandType.DIALOG_ACTION, fontPositionValueSpinner);//, comboBox_1
		RibbonUIManager.getInstance().bind(Font.POSITION_ACTION, ActionCommandType.DIALOG_ACTION, fontPositionCombox);
		
		
		//词间距监听器
		RibbonUIManager.getInstance().bind(Font.SPACE_INPUT_ACTION, ActionCommandType.DIALOG_ACTION, fontSpaceValueSpinner);//, comboBox_2
		RibbonUIManager.getInstance().bind(Font.SPACE_ACTION, ActionCommandType.DIALOG_ACTION, fontSpaceCombobox);
		
		
		//字符缩放监听器
		//目前FOV没有实现，所以没有加到最后的对话框中
		fontStretchComboBox.addActionListener(ActionFactory.getAction(Font.STRETCH_ACTION));
		
		
		//词间距
		fontWordSpaceComboBox = new JComboBox();
		fontWordSpaceComboBox.setModel(new DefaultComboBoxModel(UiText.FONT_WORD_SPACE_LIST));

		JLabel fontWordSpaceValueLabel;
		fontWordSpaceValueLabel = new JLabel();
		fontWordSpaceValueLabel.setText(UiText.COMMON_VALUE_LABEL);

		fontWordSpaceSpinner = new FixedLengthSpinner();//new JSpinner();
		fontWordSpaceSpinner.setModel(new SpinnerFixedLengthModel(new FixedLength(0d,"pt"), new FixedLength(-1000d,"pt"), new FixedLength(1000d,"pt"), -1));

		/*JLabel label_5_1;
		label_5_1 = new JLabel();
		label_5_1.setText(UiText.COMMON_MEASUREMENT_LABEL);*/

		JLabel label_6_1;
		label_6_1 = new JLabel();
		label_6_1.setText(UiText.FONT_WORD_SPACE_LABEL);

		/*comboBox_7 = new JComboBox();
		comboBox_7.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));*/
		
		//词间距监听器
		RibbonUIManager.getInstance().bind(Font.WORD_SPACE_INPUT_ACTION, ActionCommandType.DIALOG_ACTION, fontWordSpaceSpinner);//
		RibbonUIManager.getInstance().bind(Font.WORD_SPACE_ACTION, ActionCommandType.DIALOG_ACTION, fontWordSpaceComboBox);

		//预览面板
		JPanel panel;
		panel = new JPanel();
		panel.setBorder(new TitledBorder(UiText.PREVIEW_BORDER_LABEL));
		panel.setLayout(new BorderLayout());
		WisedocConfigurePreviewPanel previewPanel = WisedocConfigurePreviewPanel.getInstance();//new WisedocConfigurePreviewPanel();
		Map<Integer, Object> map = RibbonUIModel.getCurrentPropertiesByType().get(ActionType.INLINE);
		FontPropertyModel.addPropertyChangeListener(previewPanel);
		previewPanel.initValues(map, ActionType.INLINE, Boolean.TRUE);
		panel.add(previewPanel, BorderLayout.CENTER);
		
		int i = 40;
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
						.addComponent(panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(fontPositionLabel)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(fontPositionCombox, GroupLayout.PREFERRED_SIZE, 120 + i, GroupLayout.PREFERRED_SIZE))//60->75
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(fontSpaceLabel)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(fontSpaceCombobox, GroupLayout.PREFERRED_SIZE, 120 + i, GroupLayout.PREFERRED_SIZE)))//60->75
							.addGap(10, 10, 10)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(fontPositionValueLabel)
								.addComponent(fontSpaceValueLabel))
							.addGap(1, 1, 1)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(fontSpaceValueSpinner, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)//48->70
								.addComponent(fontPositionValueSpinner, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))//48->70
							/*.addGap(10, 10, 10)*/
							/*.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)*/
								/*.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)*/
								/*.addComponent(label_5))*/
							/*.addGap(1, 1, 1)*/
							/*.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)*/
								/*.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)*///49->70
								/*.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))*/)//49->70
						.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(label_6_1)//【删除】by 李晓光【, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE】
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(fontWordSpaceComboBox, GroupLayout.PREFERRED_SIZE, 120 + i, GroupLayout.PREFERRED_SIZE)////60->75
							.addGap(10, 10, 10)
							.addComponent(fontWordSpaceValueLabel)//【删除】by 李晓光2009-1-6 【, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE】
							.addGap(1, 1, 1)
							.addComponent(fontWordSpaceSpinner, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)//48->70
							/*.addGap(10, 10, 10)*/
							/*.addComponent(label_5_1)*///【删除】by 李晓光2009-1-6 【, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE】
							/*.addGap(1, 1, 1)*///(15, 15, 15)
							/*.addComponent(comboBox_7, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)*/)//49->70
						/*.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(font_stretch)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))*/)
					.addContainerGap(36, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(fontPositionCombox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fontPositionValueLabel)
						/*.addComponent(label_2)*/
						/*.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)*/
						.addComponent(fontPositionValueSpinner, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(fontPositionLabel))
					.addGap(20, 20, 20)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(fontSpaceCombobox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fontSpaceValueLabel)
						/*.addComponent(label_5)*/
						/*.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)*/
						.addComponent(fontSpaceValueSpinner, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(fontSpaceLabel))
					.addGap(20, 20, 20)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(fontWordSpaceComboBox)//【删除】by 李晓光2009-1-6 【, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE】
							.addComponent(label_6_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3, 3, 3)
							.addComponent(fontWordSpaceValueLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addComponent(fontWordSpaceSpinner, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3, 3, 3)
							/*.addComponent(label_5_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)*/)
						/*.addComponent(comboBox_7, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)*/)//20->22
					.addGap(15, 15, 15)//【修改】 by 李晓光 2009-1-7 【20->15】
					/*.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(font_stretch)
						.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))*/
					/*.addGap(20, 20, 20)*///【删除】by 李晓光 2009-1-7
					.addComponent(panel)//【删除】by 李晓光2009-1-7 【, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE】
					.addContainerGap(GroupLayout.DEFAULT_SIZE, 5))
					/* 【删除：START】 by 李晓光 2009-1-7 */
					/*.addContainerGap(33, Short.MAX_VALUE))*/
					/* 【删除：END】 by 李晓光 2009-1-7 */
		);
		setLayout(groupLayout);
	}

}
