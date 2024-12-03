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
package com.wisii.wisedoc.view.ui.parts.pagelayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel;
import com.wisii.wisedoc.view.ui.parts.common.BackGroundPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 版心区域全部属性设置面板
 * @author 闫舒寰
 * @version 1.0 2009/01/20
 */
public class RBMPanel extends JPanel implements UpdateSPMProperty {

//	private JComboBox marginMeasurement;
	private FixedLengthSpinner rightMargin;
	private FixedLengthSpinner leftMargin;
	private FixedLengthSpinner bottomMargin;
	private FixedLengthSpinner topMargin;
	private JComboBox bodyContentOverflowComboBox;
	private JComboBox comboBox_3;
//	private JComboBox comboBox_2;
	private FixedLengthSpinner spinner_1;
	private JSpinner spinner;
	private JComboBox comboBox_1;
	private JComboBox comboBox;
	
	PageBodyMarginAction pageBodyMarginAction = new PageBodyMarginAction();
	
	//有关背景设置属性面板
	public static BackGroundPanel regionBodyBackgroundPanel;
	
//	private SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
	private SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getMainSPMM();
	
	/**
	 * Create the panel
	 */
	public RBMPanel() {
		super();

		setBorder(BorderFactory.createLineBorder(Color.black));
		
		regionBodyBackgroundPanel = null;
		
		JLabel label;
		label = new JLabel();
		label.setText(UiText.PAGE_BODY_ORIENTATION_LABEL);

		comboBox = new JComboBox(new DefaultComboBoxModel(UiText.PAGE_BODY_ORIENTATION_LIST));
		PageRefOrientationAction pageRefOrientationACtion = new PageRefOrientationAction();
		comboBox.addActionListener(pageRefOrientationACtion);
		pageRefOrientationACtion.setDefaultState();

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(UiText.PAGE_TEXT_DIRECTION_LABEL);

		comboBox_1 = new JComboBox(new DefaultComboBoxModel(UiText.REGION_TEXT_DIRECTION_LIST));
		PageWritingModeAction pageWritingModeAction = new PageWritingModeAction();
		comboBox_1.addActionListener(pageWritingModeAction);
		pageWritingModeAction.setDefaultState();

		/************************************分栏和栏间距**************************/
		
		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(UiText.PAGE_COLUMN_COUNT_LABEL);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, null, 1));
		

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText(UiText.PAGE_COLUMN_GAP_LABEL);

		spinner_1 = new FixedLengthSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0.0, null, null, 1));
		
		PageColumnGapAction pageColumnGapAction = new PageColumnGapAction();
		spinner_1.addActionListener(pageColumnGapAction);
		pageColumnGapAction.setDefaultState();
		
		PageColumnCountAction pageColumnCountAction = new PageColumnCountAction();
		spinner.addChangeListener(pageColumnCountAction);
		pageColumnCountAction.setDefaultState();

		/*JLabel label_4;
		label_4 = new JLabel();
		label_4.setText("单位：");*/

//		comboBox_2 = new JComboBox(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		
		
		/************************************分栏和栏间距**************************/

		JLabel label_5;
		label_5 = new JLabel();
		label_5.setText(UiText.PAGE_BODY_DISPLAY_ALIGN_LABEL);//"版心内容对齐方式："

		comboBox_3 = new JComboBox(new DefaultComboBoxModel(UiText.PAGE_BODY_DISPLAY_ALIGN_LIST));
		PageDisplayAlignAction pageDisplayAlignAction = new PageDisplayAlignAction();
		comboBox_3.addActionListener(pageDisplayAlignAction);
		pageDisplayAlignAction.setDefaultState();

		JLabel label_6;
		label_6 = new JLabel();
		label_6.setText(UiText.PAGE_BODY_CONTENT_OVERFLOW_LABEL);

		bodyContentOverflowComboBox = new JComboBox(new DefaultComboBoxModel(UiText.PAGE_BODY_CONTENT_OVERFLOW_LIST));
		PageOverflowAction poa = new PageOverflowAction();
		bodyContentOverflowComboBox.addActionListener(poa);
		poa.setDefaultState();

		/*****************页边距 开始**************/
		JPanel panel;
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, UiText.PAGE_BODY_MARGINS_LABEL, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

		JLabel label_7;
		label_7 = new JLabel();
		label_7.setText(UiText.PAGE_MARGIN_TOP_LABEL);

		topMargin = new FixedLengthSpinner();
		topMargin.setModel(new SpinnerNumberModel(0.0, null, null, 1.0));

		JLabel label_8;
		label_8 = new JLabel();
		label_8.setText(UiText.PAGE_MARGIN_BOTTOM_LABEL);

		bottomMargin = new FixedLengthSpinner();
		bottomMargin.setModel(new SpinnerNumberModel(0.0, null, null, 1.0));

		JLabel label_9;
		label_9 = new JLabel();
		label_9.setText(UiText.PAGE_MARGIN_LEFT_LABEL);

		leftMargin = new FixedLengthSpinner();
		leftMargin.setModel(new SpinnerNumberModel(0.0, null, null, 1.0));

		JLabel label_10;
		label_10 = new JLabel();
		label_10.setText(UiText.PAGE_MARGIN_RIGHT_LABEL);

		rightMargin = new FixedLengthSpinner();
		rightMargin.setModel(new SpinnerNumberModel(0.0, null, null, 1.0));

		/*JLabel label_11;
		label_11 = new JLabel();
		label_11.setText("单位：");*/

//		marginMeasurement = new JComboBox(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		
//		PageBodyMarginAction pageBodyMarginAction = new PageBodyMarginAction();
		topMargin.addActionListener(pageBodyMarginAction);
		bottomMargin.addActionListener(pageBodyMarginAction);
		leftMargin.addActionListener(pageBodyMarginAction);
		rightMargin.addActionListener(pageBodyMarginAction);
//		marginMeasurement.addActionListener(pageBodyMarginAction);
		pageBodyMarginAction.setDefaultState();
		
		/*****************页边距 结束**************/
		
		final GroupLayout groupLayout_1 = new GroupLayout((JComponent) panel);
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout_1.createSequentialGroup()
							.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout_1.createSequentialGroup()
									.addComponent(label_7)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(topMargin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout_1.createSequentialGroup()
									.addComponent(label_9)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(leftMargin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(58, 58, 58)
							.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout_1.createSequentialGroup()
									.addComponent(label_8)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(bottomMargin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout_1.createSequentialGroup()
									.addComponent(label_10)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(rightMargin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						/*.addGroup(groupLayout_1.createSequentialGroup()
							.addComponent(label_11)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(marginMeasurement, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))*/)
					.addContainerGap(224, Short.MAX_VALUE))
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_7)
						.addComponent(topMargin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_8)
						.addComponent(bottomMargin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24, 24, 24)
					.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_9)
						.addComponent(leftMargin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_10)
						.addComponent(rightMargin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(21, 21, 21)
					/*.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_11)
						.addComponent(marginMeasurement, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))*/
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(groupLayout_1);
		
		
		JPanel regionBodyBgPanel;
		regionBodyBgPanel = new JPanel();
		regionBodyBgPanel.setBorder(new TitledBorder(null, UiText.PAGE_BODY_BACKGROUND_LABEL, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		regionBodyBgPanel.setLayout(new BorderLayout());
		regionBodyBgPanel.add(getRegionBodyBgPanel(), BorderLayout.CENTER);
		/*final GroupLayout groupLayout_2 = new GroupLayout((JComponent) regionBodyBgPanel);
		groupLayout_2.setHorizontalGroup(
			groupLayout_2.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGap(0, 510, Short.MAX_VALUE)
		);
		groupLayout_2.setVerticalGroup(
			groupLayout_2.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGap(0, 165, Short.MAX_VALUE)
		);
		regionBodyBgPanel.setLayout(groupLayout_2);*/
		
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
						.addComponent(regionBodyBgPanel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
						.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(23, 23, 23)
							.addComponent(label_1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(label_2)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(24, 24, 24)
							.addComponent(label_3)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							/*.addGap(25, 25, 25)
							.addComponent(label_4)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)*/)
						.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(label_5)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(20, 20, 20)
							.addComponent(label_6)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(bodyContentOverflowComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(63, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(30, 30, 30)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						/*.addComponent(label_4)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)*/)
					.addGap(29, 29, 29)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_5)
						.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_6)
						.addComponent(bodyContentOverflowComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27, 27, 27)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(24, 24, 24)
					.addComponent(regionBodyBgPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(54, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
	
	private JPanel getRegionBodyBgPanel(){
		regionBodyBackgroundPanel = new BackGroundPanel(getRegionBodyBgProperties(), ActionType.LAYOUT);
		return regionBodyBackgroundPanel;
	}
	
	private Map<Integer, Object> getRegionBodyBgProperties(){
		Map<Integer, Object> temp = new HashMap<Integer, Object>();
		
		temp.put(Constants.PR_BACKGROUND_COLOR, spmm.getRegionBodyModel().getBodyBackgroundColor());
		temp.put(Constants.PR_BACKGROUND_IMAGE, spmm.getRegionBodyModel().getBodyBackgroundImage());
		temp.put(Constants.PR_BACKGROUND_REPEAT, spmm.getRegionBodyModel().getBodyBackgroundImageRepeat());
		temp.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, spmm.getRegionBodyModel().getBodyBackgroundPositionHorizontal());
		temp.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, spmm.getRegionBodyModel().getBodyBackgroundPositionVertical());
		
		return temp;
	}
	
	/**
	 * 版心方向动作
	 * @author 闫舒寰
	 */
	private class PageRefOrientationAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JComboBox) {
				JComboBox ui = (JComboBox) e.getSource();
				switch (ui.getSelectedIndex()) {
				case 0:
					spmm.getRegionBodyModel().setBodyReferenceOrientation(0);
					break;
				case 1:
					spmm.getRegionBodyModel().setBodyReferenceOrientation(90);
					break;
				case 2:
					spmm.getRegionBodyModel().setBodyReferenceOrientation(180);
					break;
				case 3:
					spmm.getRegionBodyModel().setBodyReferenceOrientation(270);
					break;
				case 4:
					spmm.getRegionBodyModel().setBodyReferenceOrientation(-90);
					break;
				case 5:
					spmm.getRegionBodyModel().setBodyReferenceOrientation(-180);
					break;
				case 6:
					spmm.getRegionBodyModel().setBodyReferenceOrientation(-270);
					break;
					
				default:
					break;
				}
			}
		}
		
		public void setDefaultState(){
			
			switch (spmm.getRegionBodyModel().getBodyReferenceOrientation()) {
			case 0:
				comboBox.setSelectedIndex(0);
				break;
			case 90:
				comboBox.setSelectedIndex(1);
				break;
			case 180:
				comboBox.setSelectedIndex(2);
				break;
			case 270:
				comboBox.setSelectedIndex(3);
				break;
			case -90:
				comboBox.setSelectedIndex(4);
				break;
			case -180:
				comboBox.setSelectedIndex(5);
				break;
			case -270:
				comboBox.setSelectedIndex(6);
				break;	

			default:
				break;
			}
		}
	}
	
	/**
	 * 版心文字方向动作
	 * @author 闫舒寰
	 *
	 */
	private class PageWritingModeAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JComboBox) {
				JComboBox textDirection = (JComboBox) e.getSource();
				switch (textDirection.getSelectedIndex()) {
				case 0:
					spmm.getRegionBodyModel().setWritingMode(Constants.EN_LR_TB);
					break;
				case 1:
					spmm.getRegionBodyModel().setWritingMode(Constants.EN_RL_TB);
					break;
				case 2:
					spmm.getRegionBodyModel().setWritingMode(Constants.EN_TB_RL);
					break;
				case 3:
					//FOP中没有bt-rl
					spmm.getRegionBodyModel().setWritingMode(-1);
					break;
//				case 4:
//					//也没有lr-bt
////					setFOProperty(propertyType, Constants.PR_WRITING_MODE, Constants.EN_LR_b);
//					break;
//				default:
//					spmm.getRegionBodyModel().setWritingMode(Constants.EN_LR_TB);
//					break;
				}
			}
		}
		
		public void setDefaultState(){
			
			int trValue = spmm.getRegionBodyModel().getWritingMode();
			JComboBox ui = comboBox_1;
			
			if (trValue == Constants.EN_LR_TB) {
				ui.setSelectedIndex(0);
			} else if (trValue == Constants.EN_RL_TB){
				ui.setSelectedIndex(1);
			} else if (trValue == Constants.EN_TB_RL){
				ui.setSelectedIndex(2);
			} else {
				ui.setSelectedIndex(3);
			}
		}
	}
	
	/**
	 * 版心页边距动作
	 * @author 闫舒寰
	 *
	 */
	private class PageBodyMarginAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setMarginProperty();
		}
		
		private void setMarginProperty() {
			
			spmm.getRegionBodyModel().setMarginTop(topMargin.getValue());
			spmm.getRegionBodyModel().setMarginBottom(bottomMargin.getValue());
			spmm.getRegionBodyModel().setMarginLeft(leftMargin.getValue());
			spmm.getRegionBodyModel().setMarginRight(rightMargin.getValue());
		}
		
		public void setDefaultState(){
			
			FixedLength tempTop = (FixedLength)spmm.getRegionBodyModel().getSpaceBefore().getOptimum(null);
			topMargin.initValue(tempTop);
			FixedLength tempBottom = (FixedLength)spmm.getRegionBodyModel().getSpaceAfter().getOptimum(null);
			bottomMargin.initValue(tempBottom);
			FixedLength tempLeft = (FixedLength)spmm.getRegionBodyModel().getMarginLeft();
			leftMargin.initValue(tempLeft);
			FixedLength tempRight = (FixedLength)spmm.getRegionBodyModel().getMarginRight();
			rightMargin.initValue(tempRight);
			
		}
	}
	
	/**
	 * 版心区域分栏个数动作
	 * @author 闫舒寰
	 *
	 */
	private class PageColumnCountAction implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			spmm.getRegionBodyModel().setColumnCount((Integer) spinner.getValue());
			if ((Integer) spinner.getValue() == 1) {
				spinner_1.setEnabled(false);
			} else {
				spinner_1.setEnabled(true);
			}
		}
		
		public void setDefaultState(){
			spinner.setValue(spmm.getRegionBodyModel().getColumnCount());
			if (spmm.getRegionBodyModel().getColumnCount() == 1) {
				spinner_1.setEnabled(false);
			} else {
				spinner_1.setEnabled(true);
			}
		}
	}
	
	/**
	 * 版心区域栏间距动作
	 * @author 闫舒寰
	 *
	 */
	private class PageColumnGapAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setProperty();
		}
		
		private void setProperty(){
			spmm.getRegionBodyModel().setColumnGap(spinner_1.getValue());
		}
		
		public void setDefaultState(){
			FixedLength temp = (FixedLength)spmm.getRegionBodyModel().getRegionBody().getColumnGapLength();
			spinner_1.initValue(temp);
		}
	}
	
	/**
	 * 版心区域内容对齐方式
	 * @author 闫舒寰
	 *
	 */
	private class PageDisplayAlignAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setProperty(comboBox_3.getSelectedIndex());
		}
		
		private void setProperty(int index){
			switch (index) {
			case 0:
				spmm.getRegionBodyModel().setDisplayAlign(Constants.EN_TOP);
				break;
			case 1:
				spmm.getRegionBodyModel().setDisplayAlign(Constants.EN_CENTER);
				break;
			case 2:
				spmm.getRegionBodyModel().setDisplayAlign(Constants.EN_AFTER);
				break;
				
			default:
				break;
			}
		}
		
		public void setDefaultState(){
			int trValue = spmm.getRegionBodyModel().getDisplayAlign();
			if (trValue == Constants.EN_TOP) {
				comboBox_3.setSelectedIndex(0);
			} else if (trValue == Constants.EN_CENTER){
				comboBox_3.setSelectedIndex(1);
			} else if (trValue == Constants.EN_AFTER){
				comboBox_3.setSelectedIndex(2);
			}
		}
	}
	
	/**
	 * 版心溢出方式的设置
	 * 当段落设置成“本段不跨页”时，该属性有实际的作用
	 */
	private class PageOverflowAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setProperty(bodyContentOverflowComboBox.getSelectedIndex());
		}
		
		private void setProperty(int index){
			switch (index) {
			case 0:
				spmm.getRegionBodyModel().setOverflow(Constants.EN_AUTO);
				break;
			case 1:
				spmm.getRegionBodyModel().setOverflow(Constants.EN_VISIBLE);
				break;
			case 2:
				spmm.getRegionBodyModel().setOverflow(Constants.EN_HIDDEN);
				break;
			case 3:
				spmm.getRegionBodyModel().setOverflow(Constants.EN_SCROLL);
				break;
				
			default:
				break;
			}
		}
		
		public void setDefaultState(){
			int trValue = spmm.getRegionBodyModel().getOverflow();
			if (trValue == Constants.EN_AUTO) {
				bodyContentOverflowComboBox.setSelectedIndex(0);
			} else if (trValue == Constants.EN_VISIBLE){
				bodyContentOverflowComboBox.setSelectedIndex(1);
			} else if (trValue == Constants.EN_HIDDEN){
				bodyContentOverflowComboBox.setSelectedIndex(2);
			} else if (trValue == Constants.EN_SCROLL){
				bodyContentOverflowComboBox.setSelectedIndex(3);
			}
		}
	}

	@Override
	public void update() {
		pageBodyMarginAction.setDefaultState();
	}
}



