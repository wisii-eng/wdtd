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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.util.UiUtil;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel;
import com.wisii.wisedoc.view.ui.parts.common.BackGroundPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 页脚属性详细设置对话框面板
 * 
 * @author 闫舒寰
 * @version 1.0 2009/01/20
 */
public class FooterDiaPanel extends JPanel implements UpdateSPMProperty
{

	private JComboBox comboBox_4;

	// private JComboBox comboBox_3;
	private FixedLengthSpinner spinner;

	private JComboBox comboBox_2;

	private JComboBox comboBox_1;

	private JComboBox comboBox;

	JCheckBox checkBox;

	// 有关背景设置属性面板
	public static BackGroundPanel regionBackgroundPanel;

	private SimplePageMasterModel spmm = SinglePagelayoutModel.Instance
			.getSinglePageLayoutModel();

	@Override
	public void update()
	{

		if (spmm.getRegionAfterModel() != null)
		{
			UiUtil.setComponentsEnabled(this, true);
		} else
		{
			UiUtil.setComponentsEnabled(this, false);
		}

		refOrientationAction.setDefaultState();
		displayAlignAction.setDefaultState();
		ea.setDefaultState();
		oa.setDefaultState();
		pa.setDefaultState();
	}

	RefOrientationAction refOrientationAction = new RefOrientationAction();

	DisplayAlignAction displayAlignAction = new DisplayAlignAction();

	ExtentAction ea = new ExtentAction();

	OverflowAction oa = new OverflowAction();

	PrecedenceAction pa = new PrecedenceAction();

	/**
	 * Create the panel
	 */
	public FooterDiaPanel()
	{
		super();

		setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel label;
		label = new JLabel();
		label.setText("页脚显示方向：");

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(
				UiText.PAGE_BODY_ORIENTATION_LIST));

		comboBox.addActionListener(refOrientationAction);
		refOrientationAction.setDefaultState();

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText("页脚文字方向：");

		comboBox_1 = new JComboBox(new DefaultComboBoxModel(
				UiText.REGION_TEXT_DIRECTION_LIST));

		WritingModeAction wma = new WritingModeAction();
		comboBox_1.addActionListener(wma);
		wma.setDefaultState();

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText("页脚内容对齐方式：");

		comboBox_2 = new JComboBox(new DefaultComboBoxModel(
				UiText.PAGE_BODY_DISPLAY_ALIGN_LIST));

		comboBox_2.addActionListener(displayAlignAction);
		displayAlignAction.setDefaultState();

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText("页脚高度：");

		spinner = new FixedLengthSpinner();

		spinner.addActionListener(ea);
		ea.setDefaultState();

		/*
		 * JLabel label_4; label_4 = new JLabel(); label_4.setText("单位：");
		 */

		// comboBox_3 = new JComboBox(new
		// DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));

		JLabel label_5;
		label_5 = new JLabel();
		label_5.setText("溢出处理方式：");

		comboBox_4 = new JComboBox(new DefaultComboBoxModel(new String[]
		{ "auto", "visible", "hidden", "scroll" }));

		comboBox_4.addActionListener(oa);
		oa.setDefaultState();

		checkBox = new JCheckBox();
		checkBox.setText("优先显示页脚");

		checkBox.addActionListener(pa);
		pa.setDefaultState();

		JPanel regionBgPanel;
		regionBgPanel = new JPanel();
		regionBgPanel.setBorder(new TitledBorder(null,
				UiText.PAGE_BODY_BACKGROUND_LABEL,
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		regionBgPanel.setLayout(new BorderLayout());
		regionBgPanel.add(getRegionBgPanel(), BorderLayout.CENTER);

		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				label)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				comboBox,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				25,
																				25,
																				25)
																		.addComponent(
																				label_1)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				comboBox_1,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				25,
																				25,
																				25)
																		.addComponent(
																				label_2)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				comboBox_2,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				label_3)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				spinner,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		// .addGap(27,
																		// 27,
																		// 27)
																		// .addComponent(label_4)
																		// .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
																		// .addComponent(comboBox_3,
																		// GroupLayout.PREFERRED_SIZE,
																		// GroupLayout.DEFAULT_SIZE,
																		// GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				43,
																				43,
																				43)
																		.addComponent(
																				label_5)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				comboBox_4,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(checkBox)
														.addComponent(
																regionBgPanel,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap(121, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addGroup(
						groupLayout.createParallelGroup(
								GroupLayout.Alignment.BASELINE).addComponent(
								label).addComponent(comboBox,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addComponent(
								label_1).addComponent(comboBox_1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addComponent(
								label_2).addComponent(comboBox_2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addGap(32, 32, 32)
						.addGroup(
								groupLayout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(label_3).addComponent(
												spinner,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										// .addComponent(label_4)
										// .addComponent(comboBox_3,
										// GroupLayout.PREFERRED_SIZE,
										// GroupLayout.DEFAULT_SIZE,
										// GroupLayout.PREFERRED_SIZE)
										.addComponent(label_5).addComponent(
												comboBox_4,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addGap(32, 32, 32).addComponent(checkBox).addGap(32,
								32, 32).addComponent(regionBgPanel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap(
								215, Short.MAX_VALUE)));
		setLayout(groupLayout);
		//

		// 检查是否有页脚，如果没有则空间不可用
		if (spmm.getRegionBeforeModel() != null)
		{
			UiUtil.setComponentsEnabled(this, true);
		} else
		{
			UiUtil.setComponentsEnabled(this, false);
		}
	}

	private JPanel getRegionBgPanel()
	{
		regionBackgroundPanel = new BackGroundPanel(
				getRegionBodyBgProperties(), ActionType.LAYOUT);
		return regionBackgroundPanel;
	}

	private Map<Integer, Object> getRegionBodyBgProperties()
	{

		if (spmm.getRegionAfterModel() == null)
		{
			return null;
		}

		Map<Integer, Object> temp = new HashMap<Integer, Object>();

		temp.put(Constants.PR_BACKGROUND_COLOR, spmm.getRegionAfterModel()
				.getBodyBackgroundColor());
		temp.put(Constants.PR_BACKGROUND_IMAGE, spmm.getRegionAfterModel()
				.getBodyBackgroundImage());
		temp.put(Constants.PR_BACKGROUND_REPEAT, spmm.getRegionAfterModel()
				.getBodyBackgroundImageRepeat());
		temp.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, spmm
				.getRegionAfterModel().getBodyBackgroundPositionHorizontal());
		temp.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, spmm
				.getRegionAfterModel().getBodyBackgroundPositionVertical());

		return temp;
	}

	/**
	 * 文字方向动作
	 * 
	 * @author 闫舒寰
	 * 
	 */
	private class RefOrientationAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof JComboBox)
			{
				JComboBox ui = (JComboBox) e.getSource();
				switch (ui.getSelectedIndex())
				{
					case 0:
						spmm.getRegionAfterModel().setReferenceOrientation(0);
						break;
					case 1:
						spmm.getRegionAfterModel().setReferenceOrientation(90);
						break;
					case 2:
						spmm.getRegionAfterModel().setReferenceOrientation(180);
						break;
					case 3:
						spmm.getRegionAfterModel().setReferenceOrientation(270);
						break;
					case 4:
						spmm.getRegionAfterModel().setReferenceOrientation(-90);
						break;
					case 5:
						spmm.getRegionAfterModel()
								.setReferenceOrientation(-180);
						break;
					case 6:
						spmm.getRegionAfterModel()
								.setReferenceOrientation(-270);
						break;

					default:
						break;
				}
			}
		}

		public void setDefaultState()
		{

			if (spmm.getRegionAfterModel() == null)
			{
				return;
			}

			switch (spmm.getRegionAfterModel().getReferenceOrientation())
			{
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
	 * 
	 * @author 闫舒寰
	 * 
	 */
	private class WritingModeAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof JComboBox)
			{
				JComboBox textDirection = (JComboBox) e.getSource();
				switch (textDirection.getSelectedIndex())
				{
					case 0:
						spmm.getRegionAfterModel().setWritingMode(
								Constants.EN_LR_TB);
						break;
					case 1:
						spmm.getRegionAfterModel().setWritingMode(
								Constants.EN_RL_TB);
						break;
					case 2:
						spmm.getRegionAfterModel().setWritingMode(
								Constants.EN_TB_RL);
						break;
					case 3:
						// FOP中没有bt-rl
						spmm.getRegionAfterModel().setWritingMode(-1);
						break;
					// case 4:
					// //也没有lr-bt
					// // setFOProperty(propertyType, Constants.PR_WRITING_MODE,
					// Constants.EN_LR_b);
					// break;
					// default:
					// spmm.getRegionAfterModel().setWritingMode(Constants.EN_LR_TB);
					// break;
				}
			}
		}

		public void setDefaultState()
		{

			if (spmm.getRegionAfterModel() == null)
			{
				return;
			}

			int trValue = spmm.getRegionAfterModel().getWritingMode();
			JComboBox ui = comboBox_1;

			if (trValue == Constants.EN_LR_TB)
			{
				ui.setSelectedIndex(0);
			} else if (trValue == Constants.EN_RL_TB)
			{
				ui.setSelectedIndex(1);
			} else if (trValue == Constants.EN_TB_RL)
			{
				ui.setSelectedIndex(2);
			} else
			{
				ui.setSelectedIndex(3);
			}
		}
	}

	/**
	 * 内容对齐方式
	 * 
	 * @author 闫舒寰
	 * 
	 */
	private class DisplayAlignAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			setProperty(comboBox_2.getSelectedIndex());
		}

		private void setProperty(int index)
		{
			switch (index)
			{
				case 0:
					spmm.getRegionAfterModel()
							.setDisplayAlign(Constants.EN_TOP);
					break;
				case 1:
					spmm.getRegionAfterModel().setDisplayAlign(
							Constants.EN_CENTER);
					break;
				case 2:
					spmm.getRegionAfterModel().setDisplayAlign(
							Constants.EN_AFTER);
					break;

				default:
					break;
			}
		}

		public void setDefaultState()
		{

			if (spmm.getRegionAfterModel() == null)
			{
				return;
			}

			int trValue = spmm.getRegionAfterModel().getDisplayAlign();
			if (trValue == Constants.EN_TOP)
			{
				comboBox_2.setSelectedIndex(0);
			} else if (trValue == Constants.EN_CENTER)
			{
				comboBox_2.setSelectedIndex(1);
			} else if (trValue == Constants.EN_AFTER)
			{
				comboBox_2.setSelectedIndex(2);
			}
		}
	}

	/**
	 * 溢出动作
	 * 
	 * @author 闫舒寰
	 * 
	 */
	private class OverflowAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			setProperty(comboBox_4.getSelectedIndex());
		}

		private void setProperty(int index)
		{
			switch (index)
			{
				case 0:
					spmm.getRegionAfterModel().setOverflow(Constants.EN_AUTO);
					break;
				case 1:
					spmm.getRegionAfterModel()
							.setOverflow(Constants.EN_VISIBLE);
					break;
				case 2:
					spmm.getRegionAfterModel().setOverflow(Constants.EN_HIDDEN);
					break;
				case 3:
					spmm.getRegionAfterModel().setOverflow(Constants.EN_SCROLL);
					break;

				default:
					break;
			}
		}

		public void setDefaultState()
		{

			if (spmm.getRegionAfterModel() == null)
			{
				return;
			}

			int trValue = spmm.getRegionAfterModel().getOverflow();
			if (trValue == Constants.EN_AUTO)
			{
				comboBox_4.setSelectedIndex(0);
			} else if (trValue == Constants.EN_VISIBLE)
			{
				comboBox_4.setSelectedIndex(1);
			} else if (trValue == Constants.EN_HIDDEN)
			{
				comboBox_4.setSelectedIndex(2);
			} else if (trValue == Constants.EN_SCROLL)
			{
				comboBox_4.setSelectedIndex(3);
			}
		}
	}

	/**
	 * 高度动作
	 * 
	 * @author 闫舒寰
	 * 
	 */
	private class ExtentAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			spmm.getRegionAfterModel().setExtent(spinner.getValue());
			// 为了不出现版心区域和页眉部分互相覆盖的情况，需要同时把版心区域的margin设置成和页眉高度一样的值
			// 因此版心区域的margin的更新还是一个bug
			spmm.getRegionBodyModel().setMarginBottom(spinner.getValue());
		}

		public void setDefaultState()
		{

			if (spmm.getRegionAfterModel() == null)
			{
				return;
			}

			spinner.initValue(spmm.getRegionAfterModel().getExtent());
		}
	}

	/**
	 * 优先级
	 * 
	 * @author 闫舒寰
	 * 
	 */
	private class PrecedenceAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			if (checkBox.isSelected())
			{
				spmm.getRegionAfterModel().setPrecedence(Constants.EN_TRUE);
			} else
			{
				spmm.getRegionAfterModel().setPrecedence(Constants.EN_FALSE);
			}
		}

		public void setDefaultState()
		{

			if (spmm.getRegionAfterModel() == null)
			{
				return;
			}

			int temp = spmm.getRegionAfterModel().getPrecedence();
			if (temp == Constants.EN_TRUE)
			{

				checkBox.setSelected(true);
			} else
			{
				checkBox.setSelected(false);
			}
		}
	}

}
