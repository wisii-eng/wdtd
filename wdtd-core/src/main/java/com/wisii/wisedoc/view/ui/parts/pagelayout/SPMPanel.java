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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * simple-page-master属性设置主面板
 * 
 * @author 闫舒寰
 * @version 1.0 2009/01/20
 */
@SuppressWarnings("serial")
public class SPMPanel extends JPanel
{

	// private JComboBox comboBox_3;
	private FixedLengthSpinner rightMargin;

	private FixedLengthSpinner leftMargin;

	private FixedLengthSpinner bottomMargin;

	private FixedLengthSpinner topMargin;

	// private JComboBox comboBox_2;
	private FixedLengthSpinner heightValue;

	private FixedLengthSpinner widthValue;

	private WiseCombobox comboBox_1;

	private JComboBox comboBox;

	private JRadioButton vertical;

	private JRadioButton parallel;

	// private SimplePageMasterModel spmm =
	// SinglePagelayoutModel.Instance.getSinglePageLayoutModel();

	private SimplePageMasterModel spmm = SinglePagelayoutModel.Instance
			.getMainSPMM();

	/**
	 * Create the panel
	 */
	public SPMPanel()
	{
		super();

		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(800, 600));

		JLabel label;
		label = new JLabel();
		label.setText(UiText.PAGE_ORIENTATION_LABEL);

		ButtonGroup buttonGroup = new ButtonGroup();

		vertical = new JRadioButton();
		vertical.setText(UiText.PAGE_PROTRAIT_BUTTON);
		buttonGroup.add(vertical);

		parallel = new JRadioButton();
		parallel.setText(UiText.PAGE_LANDSCAPE_BUTTON);
		buttonGroup.add(parallel);

		PageOrientationAction pageOrientation = new PageOrientationAction();
		vertical.addActionListener(pageOrientation);
		parallel.addActionListener(pageOrientation);
		pageOrientation.setDefaultState();

		/***************** 文字方向 开始 **************/
		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(UiText.PAGE_TEXT_DIRECTION_LABEL);

		comboBox = new JComboBox(new DefaultComboBoxModel(
				UiText.PAGE_TEXT_DIRECTION_LIST));

		PageWritingModeAction pageWritingModeAction = new PageWritingModeAction();
		comboBox.addActionListener(pageWritingModeAction);
		pageWritingModeAction.setDefaultState();
		/***************** 文字方向 结束 **************/

		/***************** 纸张大小 开始 **************/
		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(UiText.PAGE_PAPER_FORMAT_LABEL);

		comboBox_1 = new WiseCombobox(new DefaultComboBoxModel(
				UiText.PAGE_PAPER_FORMAT_LIST));
		setDefaultState();

		PagePaperSizeAction pagePaperSizeAction = new PagePaperSizeAction();
		comboBox_1.addItemListener(pagePaperSizeAction);

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText(UiText.PAGE_WIDTH_LABEL);

		widthValue = new FixedLengthSpinner();
		widthValue.setModel(new SpinnerFixedLengthModel(null,
				InitialManager.MINLEN, null, -1));
		widthValue.initValue((FixedLength) spmm.getPageWidth());
		PageWidthInputAction widthAction = new PageWidthInputAction();
		widthValue.addChangeListener(widthAction);

		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText(UiText.PAGE_HEIGHT_LABEL);

		heightValue = new FixedLengthSpinner();
		heightValue.setModel(new SpinnerFixedLengthModel(null,
				InitialManager.MINLEN, null, -1));
		heightValue.initValue((FixedLength) spmm.getPageHeight());
		PageHeightInputAction heightAction = new PageHeightInputAction();
		heightValue.addChangeListener(heightAction);

		/***************** 纸张大小 结束 **************/

		JPanel panel;
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, UiText.PAGE_MARGINS_LABEL,
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));

		JLabel label_6;
		label_6 = new JLabel();
		label_6.setText(UiText.PAGE_MARGIN_TOP_LABEL);

		topMargin = new FixedLengthSpinner();
		topMargin.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		JLabel label_7;
		label_7 = new JLabel();
		label_7.setText(UiText.PAGE_MARGIN_BOTTOM_LABEL);

		bottomMargin = new FixedLengthSpinner();
		bottomMargin.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		JLabel label_8;
		label_8 = new JLabel();
		label_8.setText(UiText.PAGE_MARGIN_LEFT_LABEL);

		leftMargin = new FixedLengthSpinner();
		leftMargin.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		JLabel label_9;
		label_9 = new JLabel();
		label_9.setText(UiText.PAGE_MARGIN_RIGHT_LABEL);

		rightMargin = new FixedLengthSpinner();
		rightMargin.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		/*
		 * JLabel label_10; label_10 = new JLabel(); label_10.setText("单位：");
		 */

		// comboBox_3 = new JComboBox(new
		// DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		PageMarginAction pageMarginAction = new PageMarginAction();
		topMargin.addActionListener(pageMarginAction);
		bottomMargin.addActionListener(pageMarginAction);
		leftMargin.addActionListener(pageMarginAction);
		rightMargin.addActionListener(pageMarginAction);
		// comboBox_3.addActionListener(pageMarginAction);
		pageMarginAction.setDefaultState();

		final GroupLayout groupLayout_1 = new GroupLayout((JComponent) panel);
		groupLayout_1
				.setHorizontalGroup(groupLayout_1
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout_1
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																groupLayout_1
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout_1
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								groupLayout_1
																										.createSequentialGroup()
																										.addComponent(
																												label_6)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												topMargin,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								groupLayout_1
																										.createSequentialGroup()
																										.addComponent(
																												label_8)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												leftMargin,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGap(
																				60,
																				60,
																				60)
																		.addGroup(
																				groupLayout_1
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								groupLayout_1
																										.createSequentialGroup()
																										.addComponent(
																												label_7)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												bottomMargin,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								groupLayout_1
																										.createSequentialGroup()
																										.addComponent(
																												label_9)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												rightMargin,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))))
										/*
										 * .addGroup(groupLayout_1.createSequentialGroup
										 * () .addComponent(label_10)
										 * .addPreferredGap
										 * (LayoutStyle.ComponentPlacement
										 * .RELATED) .addComponent(comboBox_3,
										 * GroupLayout.PREFERRED_SIZE,
										 * GroupLayout.DEFAULT_SIZE,
										 * GroupLayout.PREFERRED_SIZE))
										 */).addContainerGap(100,
												Short.MAX_VALUE)));
		groupLayout_1.setVerticalGroup(groupLayout_1.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				groupLayout_1.createSequentialGroup().addContainerGap()
						.addGroup(
								groupLayout_1.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(label_6).addComponent(
												topMargin,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(label_7).addComponent(
												bottomMargin,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addGap(26, 26, 26).addGroup(
								groupLayout_1.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(label_8).addComponent(
												leftMargin,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(label_9).addComponent(
												rightMargin,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						// .addGap(26, 26, 26)
						/*
						 * .addGroup(groupLayout_1.createParallelGroup(GroupLayout
						 * .Alignment.BASELINE) .addComponent(label_10)
						 * .addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE,
						 * GroupLayout.DEFAULT_SIZE,
						 * GroupLayout.PREFERRED_SIZE))
						 */
						.addContainerGap(10, Short.MAX_VALUE)));
		panel.setLayout(groupLayout_1);

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
														.addComponent(
																panel,
																GroupLayout.PREFERRED_SIZE,
																519,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				label)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				vertical)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				parallel)
																		.addGap(
																				30,
																				30,
																				30)
																		.addComponent(
																				label_1)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				comboBox,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				label_2)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				comboBox_1,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				label_3)
																		.addGap(
																				1,
																				1,
																				1)
																		.addComponent(
																				widthValue,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				label_4)
																		.addGap(
																				1,
																				1,
																				1)
																		.addComponent(
																				heightValue,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
														// .addComponent(label_5)
														// .addGap(3, 3, 3)
														/*
														 * .addComponent(comboBox_2
														 * ,GroupLayout.
														 * PREFERRED_SIZE,
														 * GroupLayout
														 * .DEFAULT_SIZE,
														 * GroupLayout
														 * .PREFERRED_SIZE)
														 */)).addContainerGap(
												123, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addGroup(
						groupLayout.createParallelGroup(
								GroupLayout.Alignment.BASELINE).addComponent(
								label).addComponent(vertical).addComponent(
								parallel).addComponent(label_1).addComponent(
								comboBox, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addGap(26, 26, 26)
						.addGroup(
								groupLayout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(label_2).addComponent(
												comboBox_1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(label_3).addComponent(
												widthValue,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(label_4).addComponent(
												heightValue,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
						// .addComponent(label_5)
						/*
						 * .addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE,
						 * GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						 */).addGap(20, 20, 20).addComponent(panel,
								GroupLayout.PREFERRED_SIZE, 181,
								GroupLayout.PREFERRED_SIZE).addContainerGap(62,
								Short.MAX_VALUE)));
		setLayout(groupLayout);
		//
	}

	/**
	 * 纸张方向动作
	 * 
	 * @author 闫舒寰
	 * 
	 */
	private class PageOrientationAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (vertical.isSelected())
			{
				spmm.setPageOrientation(0);
			} else if (parallel.isSelected())
			{
				spmm.setPageOrientation(90);
			}
		}

		public void setDefaultState()
		{
			if (spmm.getPageOrientation() == 0)
			{
				vertical.setSelected(true);
			} else
			{
				parallel.setSelected(true);
			}
		}
	}

	/**
	 * 版心文字方向动作
	 * 
	 * @author 闫舒寰
	 * 
	 */
	private class PageWritingModeAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			switch (comboBox.getSelectedIndex())
			{
				case 0:
					spmm.setPageWritingMode(Constants.EN_LR_TB);
					break;
				case 1:
					spmm.setPageWritingMode(Constants.EN_RL_TB);
					break;
				case 2:
					spmm.setPageWritingMode(Constants.EN_TB_RL);
					break;
				case 3:
					// FOP中没有bt-rl
					spmm.setPageWritingMode(-1);
					break;
//				case 4:
//					// 也没有lr-bt
//					// setFOProperty(propertyType, Constants.PR_WRITING_MODE,
//					// Constants.EN_LR_b);
//					break;
//				default:
//					spmm.setPageWritingMode(Constants.EN_LR_TB);
//					break;
			}
		}

		public void setDefaultState()
		{
			int trValue = spmm.getPageWritingMode();

			/*
			 * if (value.equals(Constants.NULLOBJECT)) { JTextComponent editor =
			 * (JTextComponent) comboBox.getEditor().getEditorComponent();
			 * editor.setText(""); } else
			 */{
				if (trValue == Constants.EN_LR_TB)
				{
					comboBox.setSelectedIndex(0);
				} else if (trValue == Constants.EN_RL_TB)
				{
					comboBox.setSelectedIndex(1);
				} else if (trValue == Constants.EN_TB_RL)
				{
					comboBox.setSelectedIndex(2);
				} else
				{
					comboBox.setSelectedIndex(3);
				}
			}
		}
	}

	private class PagePaperSizeAction implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				int index = comboBox_1.getSelectedIndex();
				setWidthAndHeight(index);
			}
		}
	}

	/**
	 * 纸张大小输入动作
	 * 
	 * @author 闫舒寰
	 * 
	 */
	private class PageWidthInputAction implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e)
		{
			FixedLength width = ((FixedLengthSpinner) e.getSource()).getValue();
			FixedLength height = heightValue.getValue();
			int currentwidth = width.getValue();
			int currentheight = height.getValue();
			comboBox_1.initIndex(getIndexSelect(currentwidth, currentheight));
			spmm.setPageWidth(width);
		}
	}

	private class PageHeightInputAction implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e)
		{
			FixedLength width = widthValue.getValue();
			FixedLength height = ((FixedLengthSpinner) e.getSource())
					.getValue();
			int currentwidth = width.getValue();
			int currentheight = height.getValue();
			comboBox_1.initIndex(getIndexSelect(currentwidth, currentheight));
			spmm.setPageHeight(height);
		}

	}

	public void setDefaultState()
	{
		FixedLength width = (FixedLength) spmm.getPageWidth();
		FixedLength height = (FixedLength) spmm.getPageHeight();
		int currentwidth = width.getValue();
		int currentheight = height.getValue();
		comboBox_1.initIndex(getIndexSelect(currentwidth, currentheight));
	}

	public void setWidthAndHeight(int index)
	{
		double width = 0d;
		double height = 0d;
		switch (index)
		{
			case 0:
			{
				width = 21.59d;
				height = 27.94d;
				break;
			}
			case 1:
			{
				width = 27.94d;
				height = 43.17d;
				break;
			}
			case 2:
			{
				width = 43.17d;
				height = 27.94d;
				break;
			}
			case 3:
			{
				width = 29.7d;
				height = 42d;
				break;
			}
			case 4:
			{
				width = 21d;
				height = 29.7d;
				break;
			}
			case 5:
			{
				width = 14.8d;
				height = 21d;
				break;
			}
			case 6:
			{
				width = 25.7d;
				height = 36.4d;
				break;
			}
			case 7:
			{
				width = 18.2d;
				height = 25.7d;
				break;
			}
			case 8:
			{
				width = 18.4d;
				height = 26d;
				break;
			}
			case 9:
			{
				width = 13d;
				height = 18.4d;
				break;
			}
			case 10:
			{
				width = 14d;
				height = 20.3d;
				break;
			}
		}
		if (index != 11)
		{
			FixedLength widthLength = new FixedLength(width, "cm");
			FixedLength heightLength = new FixedLength(height, "cm");
			widthValue.initValue(widthLength);
			heightValue.initValue(heightLength);
		}
	}

	public int getIndexSelect(int width, int height)
	{
		int value = 0;
		if (isApproximately(width, 21.59d) && isApproximately(height, 27.94d))
		{
			value = 0;
		} else if (isApproximately(width, 27.94d)
				&& isApproximately(height, 43.17d))
		{
			value = 1;
		} else if (isApproximately(width, 43.17d)
				&& isApproximately(height, 27.94d))
		{
			value = 2;
		} else if (isApproximately(width, 29.7d)
				&& isApproximately(height, 42d))
		{
			value = 3;
		} else if (isApproximately(width, 21d)
				&& isApproximately(height, 29.7d))
		{
			value = 4;
		} else if (isApproximately(width, 14.8d)
				&& isApproximately(height, 21d))
		{
			value = 5;
		} else if (isApproximately(width, 25.7d)
				&& isApproximately(height, 36.4d))
		{
			value = 6;
		} else if (isApproximately(width, 18.2d)
				&& isApproximately(height, 25.7d))
		{
			value = 7;
		} else if (isApproximately(width, 18.4d)
				&& isApproximately(height, 26d))
		{
			value = 8;
		} else if (isApproximately(width, 13d)
				&& isApproximately(height, 18.4d))
		{
			value = 9;
		} else if (isApproximately(width, 14d)
				&& isApproximately(height, 20.3d))
		{
			value = 10;
		} else
		{
			value = 11;
		}
		return value;
	}

	public boolean isApproximately(int length, double compare)
	{
		if (length > 0)
		{
			int current = getIntValue(compare);
			if (Math.abs(length - current) < 100)
			{
				return true;
			}
		}
		return false;
	}

	public int getIntValue(double length)
	{
		int result = 0;
		if (length == 21.59d)
		{
			result = 612000;
		} else if (length == 27.94d)
		{
			result = 792000;
		} else if (length == 43.17d)
		{
			result = 1223716;
		} else if (length == 21d)
		{
			result = 595275;
		} else if (length == 29.7d)
		{
			result = 841889;
		} else if (length == 42d)
		{
			result = 1190551;
		} else if (length == 14.8d)
		{
			result = 419527;
		} else if (length == 25.7d)
		{
			result = 728503;
		} else if (length == 36.4d)
		{
			result = 1031811;
		} else if (length == 18.2d)
		{
			result = 515905;
		} else if (length == 18.4d)
		{
			result = 521574;
		} else if (length == 26d)
		{
			result = 737007;
		} else if (length == 13d)
		{
			result = 368503;
		} else if (length == 14d)
		{
			result = 396850;
		} else if (length == 20.3d)
		{
			result = 575433;
		}
		return result;
	}

	private void setSize(double width, double height)
	{
		FixedLength widthLength = new FixedLength(width, "cm");
		FixedLength heightLength = new FixedLength(height, "cm");
		widthValue.setValue(widthLength);
		heightValue.setValue(heightLength);
		spmm.setPageWidth(widthLength);
		spmm.setPageHeight(heightLength);
	}

	/**
	 * 页边距动作
	 * 
	 * @author 闫舒寰
	 * 
	 */
	private class PageMarginAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			setMarginProperty();
		}

		private void setMarginProperty()
		{
			spmm.setPageMarginTop(topMargin.getValue());
			spmm.setPageMarginBottom(bottomMargin.getValue());
			spmm.setPageMarginLeft(leftMargin.getValue());
			spmm.setPageMarginRight(rightMargin.getValue());
		}

		public void setDefaultState()
		{

			// FixedLength tempTop = (FixedLength) spmm.getPageSpaceBefore()
			// .getOptimum(null);
			// FixedLength tempBottom = (FixedLength) spmm.getPageSpaceAfter()
			// .getOptimum(null);
			//
			// topMargin.initValue(tempTop);
			// bottomMargin.initValue(tempBottom);
			topMargin.initValue((FixedLength) spmm.getPageMarginTop());
			bottomMargin.initValue((FixedLength) spmm.getPageMarginBottom());
			leftMargin.initValue((FixedLength) spmm.getPageMarginLeft());
			rightMargin.initValue((FixedLength) spmm.getPageMarginRight());
		}
	}
}