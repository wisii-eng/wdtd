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

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.text.UiText;

public class PageSet extends JPanel
{

	private ButtonGroup buttonGroup = new ButtonGroup();

	private JComboBox measurement_2;

	private JComboBox comboBox_3;

	private JSpinner spinner_6;

	private JSpinner spinner_5;

	private JSpinner spinner_4;

	private JSpinner spinner_3;

	private JSpinner spinner_2;

	private JComboBox comboBox_2;

	private FixedLengthSpinner spinner_1;

	private FixedLengthSpinner spinner;

	private JComboBox comboBox_1;

	private JComboBox comboBox;

	/**
	 * Create the panel
	 */

	public PageSet()
	{
		super();

		// 纸张方向
		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(UiText.PAGE_ORIENTATION_LABEL);

		JRadioButton radioButton;
		radioButton = new JRadioButton();
		buttonGroup.add(radioButton);
		radioButton.setText(UiText.PAGE_PROTRAIT_BUTTON);

		JRadioButton radioButton_1;
		radioButton_1 = new JRadioButton();
		buttonGroup.add(radioButton_1);
		radioButton_1.setText(UiText.PAGE_LANDSCAPE_BUTTON);

		/*
		 * 把这一组有关纸张方向的按钮放到一个list中，其中第0个元素是buttonGroup，第一个元素是radioButton（纵向）按钮，第二个元素是radioButton_1
		 * （横向） 并添加相应动作
		 */
		RibbonUIManager.getInstance().bind(Page.ORIENTATION_ACTION,
				ActionCommandType.DIALOG_ACTION, radioButton, radioButton_1);

		/***************** 文字方向 开始 **************/
		JLabel pageTextDirectionLabel;
		pageTextDirectionLabel = new JLabel();
		pageTextDirectionLabel.setText(UiText.PAGE_TEXT_DIRECTION_LABEL);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(
				UiText.PAGE_TEXT_DIRECTION_LIST));
		RibbonUIManager.getInstance().bind(Page.TEXT_DIRECTION_ACTION,
				ActionCommandType.DIALOG_ACTION, comboBox_1);

		/***************** 文字方向 结束 **************/

		/***************** 纸张大小 开始 **************/
		JLabel pageSizeLabel;
		pageSizeLabel = new JLabel();
		pageSizeLabel.setText(UiText.PAGE_PAPER_FORMAT_LABEL);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(
				UiText.PAGE_PAPER_FORMAT_LIST));
		comboBox.setSelectedIndex(4);

		JLabel pageWidthLabel;
		pageWidthLabel = new JLabel();
		pageWidthLabel.setText(UiText.PAGE_WIDTH_LABEL);

		spinner = new FixedLengthSpinner();
		spinner.setModel(new SpinnerFixedLengthModel(null,
				InitialManager.MINLEN, null, -1));

		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText(UiText.PAGE_HEIGHT_LABEL);

		spinner_1 = new FixedLengthSpinner();
		spinner_1.setModel(new SpinnerFixedLengthModel(null,
				InitialManager.MINLEN, null, -1));


		JLabel measurement_1;
		measurement_1 = new JLabel();
		measurement_1.setText(UiText.COMMON_MEASUREMENT_LABEL);

		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(
				UiText.COMMON_MEASUREMENT_LIST));
		comboBox_2.setSelectedIndex(1);

		/*
		 * 把这一组有关纸张大小的按钮放到一个list中，其中第0个元素是comboBox（纸张的下拉列表），
		 * 第一个元素是spinner（宽度输入框），第二个元素是spinner_1高度输入框，第三个元素是comboBox_2单位下拉菜单
		 * 并添加相应动作
		 */
		RibbonUIManager.getInstance()
				.bind(Page.PAPER_SIZE_INPUT_ACTION,
						ActionCommandType.DIALOG_ACTION, spinner, spinner_1,
						comboBox_2);
		RibbonUIManager.getInstance().bind(Page.PAPER_SIZE_ACTION,
				ActionCommandType.DIALOG_ACTION, comboBox);

		/***************** 纸张大小 结束 **************/

		/***************** 页边距 开始 **************/

		// SpinnerModel spinnerModel = new SpinnerNumberModel(0.0, null, null,
		// 1);

		JLabel label_6;
		label_6 = new JLabel();
		label_6.setText("页边距：");

		JLabel label_7;
		label_7 = new JLabel();
		label_7.setText("上：");

		spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		JLabel label_8;
		label_8 = new JLabel();
		label_8.setText("下：");

		spinner_3 = new JSpinner();
		spinner_3.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		JLabel label_9;
		label_9 = new JLabel();
		label_9.setText("左：");

		spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		JLabel label_10;
		label_10 = new JLabel();
		label_10.setText("右：");

		spinner_5 = new JSpinner();
		spinner_5.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		JLabel label_11;
		label_11 = new JLabel();
		label_11.setText("装订线：");

		spinner_6 = new JSpinner();
		spinner_6.setEnabled(false);

		JLabel label_12;
		label_12 = new JLabel();
		label_12.setText("装订线位置：");

		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[]
		{ "左", "上" }));
		comboBox_3.setEnabled(false);

		JLabel label_13;
		label_13 = new JLabel();
		label_13.setText(UiText.COMMON_MEASUREMENT_LABEL);

		measurement_2 = new JComboBox();
		measurement_2.setModel(new DefaultComboBoxModel(
				UiText.COMMON_MEASUREMENT_LIST));

		/*
		 * 这个是页边距设定值的相关按钮，List中第0个元素是单位（measurement_2），第一个到第四个分别是
		 * 上（spinner_2）、下(spinner_3)、左(spinner_4)、右(spinner_5)
		 */
		RibbonUIManager.getInstance().bind(Page.BODY_MARGIN_ACTION,
				ActionCommandType.DIALOG_ACTION, measurement_2, spinner_2,
				spinner_3, spinner_4, spinner_5);

		/***************** 页边距 结束 **************/

		JSeparator separator;
		separator = new JSeparator();

		JSeparator separator_1;
		separator_1 = new JSeparator();

		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(0, 0, 0)
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				label_6)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								label_11)
																						.addComponent(
																								label_7)
																						.addComponent(
																								label_9)
																						.addComponent(
																								label_13))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																spinner_2,
																																GroupLayout.PREFERRED_SIZE,
																																40,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																spinner_4,
																																GroupLayout.PREFERRED_SIZE,
																																40,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																spinner_6,
																																GroupLayout.PREFERRED_SIZE,
																																40,
																																GroupLayout.PREFERRED_SIZE))
																										.addGap(
																												22,
																												22,
																												22)
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																label_12)
																														.addComponent(
																																label_8)
																														.addComponent(
																																label_10))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																spinner_5,
																																GroupLayout.PREFERRED_SIZE,
																																40,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																spinner_3,
																																GroupLayout.PREFERRED_SIZE,
																																40,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																comboBox_3,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)))
																						.addComponent(
																								measurement_2,
																								GroupLayout.PREFERRED_SIZE,
																								40,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																groupLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.TRAILING,
																				false)
																		.addComponent(
																				separator_1,
																				GroupLayout.Alignment.LEADING)
																		.addComponent(
																				separator,
																				GroupLayout.Alignment.LEADING,
																				GroupLayout.DEFAULT_SIZE,
																				353,
																				Short.MAX_VALUE)
																		.addGroup(
																				GroupLayout.Alignment.LEADING,
																				groupLayout
																						.createSequentialGroup()
																						.addComponent(
																								pageTextDirectionLabel)
																						.addPreferredGap(
																								LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(
																								comboBox_1,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGroup(
																				GroupLayout.Alignment.LEADING,
																				groupLayout
																						.createSequentialGroup()
																						.addComponent(
																								measurement_1)
																						.addPreferredGap(
																								LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(
																								comboBox_2,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGroup(
																				GroupLayout.Alignment.LEADING,
																				groupLayout
																						.createSequentialGroup()
																						.addComponent(
																								label_1)
																						.addPreferredGap(
																								LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(
																								radioButton)
																						.addGap(
																								12,
																								12,
																								12)
																						.addComponent(
																								radioButton_1))
																		.addGroup(
																				GroupLayout.Alignment.LEADING,
																				groupLayout
																						.createSequentialGroup()
																						.addGroup(
																								groupLayout
																										.createParallelGroup(
																												GroupLayout.Alignment.TRAILING)
																										.addGroup(
																												groupLayout
																														.createSequentialGroup()
																														.addComponent(
																																pageWidthLabel)
																														.addGap(
																																28,
																																28,
																																28))
																										.addGroup(
																												groupLayout
																														.createSequentialGroup()
																														.addComponent(
																																pageSizeLabel)
																														.addPreferredGap(
																																LayoutStyle.ComponentPlacement.RELATED)))
																						.addGroup(
																								groupLayout
																										.createParallelGroup(
																												GroupLayout.Alignment.TRAILING)
																										.addComponent(
																												comboBox,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)
																										.addGroup(
																												groupLayout
																														.createSequentialGroup()
																														.addComponent(
																																spinner,
																																GroupLayout.PREFERRED_SIZE,
																																40,
																																GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(
																																LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																label_4)
																														.addPreferredGap(
																																LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																spinner_1,
																																GroupLayout.PREFERRED_SIZE,
																																40,
																																GroupLayout.PREFERRED_SIZE))))))
										.addContainerGap(285, Short.MAX_VALUE)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(label_1)
														.addComponent(
																radioButton)
														.addComponent(
																radioButton_1))
										.addGap(13, 13, 13)
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																pageTextDirectionLabel)
														.addComponent(
																comboBox_1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(separator,
												GroupLayout.PREFERRED_SIZE, 2,
												GroupLayout.PREFERRED_SIZE)
										.addGap(11, 11, 11)
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																comboBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																pageSizeLabel))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(
																				25,
																				25,
																				25)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								measurement_1)
																						.addComponent(
																								comboBox_2,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																groupLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				spinner_1,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				label_4)
																		.addComponent(
																				spinner,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				pageWidthLabel)))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(separator_1,
												GroupLayout.PREFERRED_SIZE, 2,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																spinner_2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_8)
														.addComponent(
																spinner_3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_7)
														.addComponent(label_6))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																spinner_4,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_10)
														.addComponent(
																spinner_5,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_9))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(label_11)
														.addComponent(
																spinner_6,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_12)
														.addComponent(
																comboBox_3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(label_13)
														.addComponent(
																measurement_2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(225, Short.MAX_VALUE)));
		setLayout(groupLayout);
		//
	}

	/**
	 * 测试用
	 * 
	 * @param args
	 */
	public static void createAndShowGUI()
	{
		// TODO Auto-generated method stub
		JFrame fr = new JFrame("测试用");
		// fr.setSize(800, 600);

		PageSet tui = new PageSet();

		fr.add(tui);
		// System.out.println(fr.getComponent(0).getPreferredSize());
		fr.setSize(fr.getComponent(0).getPreferredSize());
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
	}

	public static void main(String[] args)
	{

		SwingUtilities.invokeLater(new Runnable()
		{

			public void run()
			{
				createAndShowGUI();
			}
		});

	}

}
