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
package com.wisii.wisedoc.view.ui.parts.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground.BorderInfo;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.LayerComboBox;
import com.wisii.wisedoc.swing.ui.LayerComboBoxModel;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBoxModel;
import com.wisii.wisedoc.view.ui.actions.dialog.BorderPreviewAction;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 边框设置的预览区
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/01
 */
public class BorderPanel extends JPanel
{

	private ButtonGroup buttonGroup = new ButtonGroup();

	List<FixedLengthSpinner> widthValue;

	List<WiseCombobox> style;

	List<ColorComboBox> color;

	List<LayerComboBox> colorlayer;

	private ColorComboBox colorTop, colorBottom, colorLeft, colorRight;

	private LayerComboBox layer_top, layer_bottom, layer_left, layer_right;

	private WiseCombobox style_top, style_bottom, style_left, style_right;

	private FixedLengthSpinner spinner_top, spinner_bottom, spinner_left,
			spinner_right;

	JCheckBox checkBox;

	JCheckBox checkBox_1;

	// 预览区
	BorderStylePreviewPanel borderPreviewPanel;

	/**
	 * Create the panel
	 */
	public BorderPanel(Map<Integer, Object> attrs, ActionType propertyType)
	{
		super();
		setBorder(new EtchedBorder());

		JPanel panel;
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setSize(626, 620);

		/*
		 * final JLabel label = new JLabel(); label.setBounds(28, 10, 260, 105);
		 * label.setFont(new Font("微软雅黑", Font.PLAIN, 36));
		 * label.setText("        预览区"); panel.add(label);
		 */

		borderPreviewPanel = new BorderStylePreviewPanel();

		/*
		 * JPanel tt = new JPanel(); tt.add(new JLabel("hihi")); panel.add(tt,
		 * BorderLayout.CENTER);
		 */

		panel.add(borderPreviewPanel);

		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		checkBox = new JCheckBox(UiText.BORDER_SYNCHRONOUS);
		buttonGroup.add(checkBox);

		checkBox_1 = new JCheckBox(UiText.BORDER_ASYNCHRONOUS);
		buttonGroup.add(checkBox_1);

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(UiText.COMMON_WIDTH);

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(UiText.COMMON_STYLE);

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText(UiText.COMMON_COLOR);

		spinner_top = new FixedLengthSpinner(getModel());

		style_top = new WiseCombobox();
		style_top.setModel(new DefaultComboBoxModel(
				UiText.PARAGRAPH_BORDER_STYLE_LIST));

		// color_top = new JComboBox();
		try
		{
			colorTop = new ColorComboBox();
		} catch (IncompatibleLookAndFeelException e1)
		{
			e1.printStackTrace();
		}
		// color_top.setModel(new DefaultComboBoxModel(new String[]
		// {"colors"}));

		JLabel label_1_1;
		label_1_1 = new JLabel();
		label_1_1.setText(UiText.COMMON_WIDTH);

		JLabel label_2_1;
		label_2_1 = new JLabel();
		label_2_1.setText(UiText.COMMON_STYLE);

		JLabel label_3_1;
		label_3_1 = new JLabel();
		label_3_1.setText(UiText.COMMON_COLOR);

		spinner_left = new FixedLengthSpinner(getModel());

		style_left = new WiseCombobox();
		style_left.setModel(new DefaultComboBoxModel(
				UiText.PARAGRAPH_BORDER_STYLE_LIST));

		// color_left = new JComboBox();
		try
		{
			colorLeft = new ColorComboBox();
		} catch (IncompatibleLookAndFeelException e1)
		{
			e1.printStackTrace();
		}
		// color_left.setModel(new DefaultComboBoxModel(new String[]
		// {"colors"}));

		JLabel label_1_1_1;
		label_1_1_1 = new JLabel();
		label_1_1_1.setText(UiText.COMMON_WIDTH);

		spinner_right = new FixedLengthSpinner(getModel());

		JLabel label_2_1_1;
		label_2_1_1 = new JLabel();
		label_2_1_1.setText(UiText.COMMON_STYLE);

		style_right = new WiseCombobox();
		style_right.setModel(new DefaultComboBoxModel(
				UiText.PARAGRAPH_BORDER_STYLE_LIST));

		JLabel label_3_1_1;
		label_3_1_1 = new JLabel();
		label_3_1_1.setText(UiText.COMMON_COLOR);

		// color_right = new JComboBox();
		try
		{
			colorRight = new ColorComboBox();
		} catch (IncompatibleLookAndFeelException e1)
		{
			e1.printStackTrace();
		}
		// color_right.setModel(new DefaultComboBoxModel(new String[]
		// {"colors"}));
		layer_bottom = new LayerComboBox();
		layer_left = new LayerComboBox();
		layer_right = new LayerComboBox();
		layer_top = new LayerComboBox();
		JLabel label_3_2;
		label_3_2 = new JLabel();
		label_3_2.setText(UiText.COMMON_COLOR);

		// color_bottom = new JComboBox();
		try
		{
			colorBottom = new ColorComboBox();
		} catch (IncompatibleLookAndFeelException e1)
		{
			e1.printStackTrace();
		}
		// color_bottom.setModel(new DefaultComboBoxModel(new String[]
		// {"colors"}));

		JLabel label_1_2;
		label_1_2 = new JLabel();
		label_1_2.setText(UiText.COMMON_WIDTH);

		JLabel label_2_2;
		label_2_2 = new JLabel();
		label_2_2.setText(UiText.COMMON_STYLE);

		style_bottom = new WiseCombobox();
		style_bottom.setModel(new DefaultComboBoxModel(
				UiText.PARAGRAPH_BORDER_STYLE_LIST));

		spinner_bottom = new FixedLengthSpinner(getModel());

		// 把面板中的按钮分类，统一按照上、下、左、右，放到相应的list中
		widthValue = new ArrayList<FixedLengthSpinner>();
		widthValue.add(spinner_top);
		widthValue.add(spinner_bottom);
		widthValue.add(spinner_left);
		widthValue.add(spinner_right);
		style = new ArrayList<WiseCombobox>();
		style.add(style_top);
		style.add(style_bottom);
		style.add(style_left);
		style.add(style_right);
		color = new ArrayList<ColorComboBox>();
		color.add(colorTop);
		color.add(colorBottom);
		color.add(colorLeft);
		color.add(colorRight);
		colorlayer = new ArrayList<LayerComboBox>();
		colorlayer.add(layer_top);
		colorlayer.add(layer_bottom);
		colorlayer.add(layer_left);
		colorlayer.add(layer_right);

		// 为预览区添加预览动作
		new BorderPreviewAction(this);

		checkBox.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if (checkBox.isSelected())
				{
					checkboxselect();
				}
			}

		});

		checkBox_1.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if (checkBox_1.isSelected())
				{
					checkbox1Select();
				}
			}

		});
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
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
																												checkBox)
																										.addGap(
																												23,
																												23,
																												23)
																										.addComponent(
																												checkBox_1))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(
																												10,
																												10,
																												10)
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																label_1_1)
																														.addComponent(
																																spinner_left,
																																GroupLayout.PREFERRED_SIZE,
																																70,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																style_left,
																																GroupLayout.PREFERRED_SIZE,
																																55,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																label_3_1,
																																GroupLayout.PREFERRED_SIZE,
																																40,// 33-40
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																colorLeft,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																layer_left,
																																GroupLayout.DEFAULT_SIZE,
																																50,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																label_2_1))
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												panel,
																												GroupLayout.PREFERRED_SIZE,
																												316,
																												GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												10,
																												10,
																												10)
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																label_1_1_1,
																																GroupLayout.PREFERRED_SIZE,
																																40,// 33-40
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																spinner_right,
																																GroupLayout.PREFERRED_SIZE,
																																70,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																label_2_1_1,
																																GroupLayout.PREFERRED_SIZE,
																																50,// 33-50
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																style_right,
																																GroupLayout.PREFERRED_SIZE,
																																55,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																label_3_1_1,
																																GroupLayout.PREFERRED_SIZE,
																																40,// 33-40
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																colorRight,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																layer_right,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)))))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(
																				90,
																				90,
																				90)
																		.addComponent(
																				label_2)
																		.addGap(
																				5,
																				5,
																				5)
																		.addComponent(
																				style_top,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				14,
																				14,
																				14)
																		.addComponent(
																				label_1)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				spinner_top,
																				GroupLayout.PREFERRED_SIZE,
																				70,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				16,
																				16,
																				16)
																		.addComponent(
																				label_3)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				colorTop,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				4,
																				4,
																				4)
																		.addComponent(
																				layer_top,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(
																				90,
																				90,
																				90)
																		.addComponent(
																				label_2_2,
																				GroupLayout.PREFERRED_SIZE,
																				40,// 33-40
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				5,
																				5,
																				5)
																		.addComponent(
																				style_bottom,
																				GroupLayout.PREFERRED_SIZE,
																				55,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				14,
																				14,
																				14)
																		.addComponent(
																				label_1_2,
																				GroupLayout.PREFERRED_SIZE,
																				40,// 33-40
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				4,
																				4,
																				4)
																		.addComponent(
																				spinner_bottom,
																				GroupLayout.PREFERRED_SIZE,
																				70,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				16,
																				16,
																				16)
																		.addComponent(
																				label_3_2,
																				GroupLayout.PREFERRED_SIZE,
																				40,// 33-40
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				4,
																				4,
																				4)
																		.addComponent(
																				colorBottom,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				4,
																				4,
																				4)
																		.addComponent(
																				layer_bottom,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(checkBox)
														.addComponent(
																checkBox_1))
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
																								label_2)
																						.addComponent(
																								label_1)
																						.addComponent(
																								spinner_top,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								style_top,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								label_3)
																						.addComponent(
																								colorTop,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addGap(
																								6,
																								6,
																								6)
																						.addComponent(
																								layer_top,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												label_2_1,
																												GroupLayout.PREFERRED_SIZE,
																												14,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												style_left,
																												GroupLayout.PREFERRED_SIZE,
																												20,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												label_1_1,
																												GroupLayout.PREFERRED_SIZE,
																												14,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												spinner_left,
																												GroupLayout.PREFERRED_SIZE,
																												20,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												label_3_1,
																												GroupLayout.PREFERRED_SIZE,
																												14,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												colorLeft,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												6,
																												6,
																												6)
																										.addComponent(
																												layer_left,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												panel,
																												GroupLayout.PREFERRED_SIZE,
																												146,
																												GroupLayout.PREFERRED_SIZE))))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(
																				55,
																				55,
																				55)
																		.addComponent(
																				label_2_1_1,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				6,
																				6,
																				6)
																		.addComponent(
																				style_right,
																				GroupLayout.PREFERRED_SIZE,
																				20,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				6,
																				6,
																				6)
																		.addComponent(
																				label_1_1_1,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				6,
																				6,
																				6)
																		.addComponent(
																				spinner_right,
																				GroupLayout.PREFERRED_SIZE,
																				20,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				6,
																				6,
																				6)
																		.addComponent(
																				label_3_1_1,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				6,
																				6,
																				6)
																		.addComponent(
																				colorRight,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				6,
																				6,
																				6)
																		.addComponent(
																				layer_right,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
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
																				3,
																				3,
																				3)
																		.addComponent(
																				label_2_2,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																style_bottom,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(
																				3,
																				3,
																				3)
																		.addComponent(
																				label_1_2,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																spinner_bottom,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(
																				3,
																				3,
																				3)
																		.addComponent(
																				label_3_2,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																colorBottom,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addGap(6, 6, 6)
														.addComponent(
																layer_bottom,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(33, Short.MAX_VALUE)));
		setLayout(groupLayout);
		initValue(attrs, propertyType);
	}

	private void initValue(Map<Integer, Object> attrs, ActionType propertyType)
	{

		if (attrs != null && !attrs.isEmpty())
		{
			// 选中多个对象时，是否这些对象的属性不同
			boolean ishasdifferentatt = false;
			Map<Integer, Object> newatt = new HashMap<Integer, Object>();
			Iterator<Integer> keyit = attrs.keySet().iterator();
			while (keyit.hasNext())
			{
				Integer key = keyit.next();
				// 去除掉其中非表框的属性
				if (key < Constants.PR_BORDER
						|| key > Constants.PR_BORDER_WIDTH)
				{
					continue;
				}
				Object value = attrs.get(key);
				// 如果有边框属性不同
				if (value == Constants.NULLOBJECT)
				{
					ishasdifferentatt = true;
					break;
				}
				newatt.put(key, value);
			}
			// 如果没有不相等的属性
			if (!ishasdifferentatt)
			{
				CommonBorderPaddingBackground borderppr = new CommonBorderPaddingBackground(
						newatt);
				BorderInfo beforeborder = borderppr
						.getBorderInfo(CommonBorderPaddingBackground.BEFORE);
				BorderInfo afterborder = borderppr
						.getBorderInfo(CommonBorderPaddingBackground.AFTER);
				BorderInfo startborder = borderppr
						.getBorderInfo(CommonBorderPaddingBackground.START);
				BorderInfo endborder = borderppr
						.getBorderInfo(CommonBorderPaddingBackground.END);
				if (beforeborder == null && afterborder == null
						&& startborder == null && endborder == null)
				{
					checkBox.setSelected(true);
					checkboxselect();
					spinner_top.initValue(new FixedLength(0.5, "pt"));
					style_top.initIndex(0);
					colorTop.setSelectedItem(Color.black);
					layer_top.initIndex(0);
				} else
				{
					// 是否所有的边框都相等
					boolean isallboderequal = (beforeborder != null
							&& beforeborder.equals(afterborder)
							&& afterborder.equals(startborder) && beforeborder
							.equals(endborder));
					if (isallboderequal)
					{
						checkBox.setSelected(true);
						checkboxselect();
						initBorder(spinner_top, style_top, colorTop, layer_top,
								beforeborder);
					} else
					{
						checkBox_1.setSelected(true);
						checkbox1Select();
						initBorder(spinner_top, style_top, colorTop, layer_top,
								beforeborder);
						initBorder(spinner_bottom, style_bottom, colorBottom,
								layer_bottom, afterborder);
						initBorder(spinner_left, style_left, colorLeft,
								layer_left, startborder);
						initBorder(spinner_right, style_right, colorRight,
								layer_right, endborder);

					}
				}
			}
			// 如果有不相等的边框属性，则显示默认属性，有些软件是取第一个元素的属性
			else
			{
				initBorder(spinner_top, style_top, colorTop, layer_top, null);
				checkBox.setSelected(true);
				checkboxselect();
			}
		}
		// 如果传过来的属性为空（null或empty）则显示默认属性
		else
		{
			initBorder(spinner_top, style_top, colorTop, layer_top, null);
			checkBox.setSelected(true);
			checkboxselect();
		}
	}

	private final FixedLength DEFAULT_WIDTH = new FixedLength(0.5d, "pt");

	private final Color DEFAULT_COLOR = Color.black;

	private final int DEFAULT_STYLE_INDEX = 0;

	private final int DEFAULT_LAYER_INDEX = 0;

	private void initBorder(FixedLengthSpinner lengthspiner,
			WiseCombobox stylebox, ColorComboBox colorbox,
			LayerComboBox layerbox, BorderInfo borderinfo)
	{
		lengthspiner.initValue(DEFAULT_WIDTH);
		stylebox.initIndex(DEFAULT_STYLE_INDEX);
		colorbox.setSelectedItem(DEFAULT_COLOR);
		layerbox.initIndex(DEFAULT_LAYER_INDEX);
		if (borderinfo != null)
		{
			CondLengthProperty width = borderinfo.getWidth();
			if (width != null)
			{
				if (width.getLength() instanceof FixedLength)
				{
					lengthspiner.initValue(width.getLength());
				}
			} else
			{
				lengthspiner.initValue(null);
			}
			Color color = borderinfo.getColor();
			if (color != null)
			{
				colorbox.setSelectedItem(new Color(color.getRGB()));
				if (color instanceof WiseDocColor)
				{
					layerbox.initIndex(((WiseDocColor) color).getLayer());
				}
			}
			int style = borderinfo.getStyle();
			switch (style)
			{
			case Constants.EN_SOLID:
				stylebox.initIndex(1);
				break;
			case Constants.EN_DOTTED:
				stylebox.initIndex(2);
				break;
			// The border is a single line segment.
			case Constants.EN_DASHED:
				stylebox.initIndex(3);
				break;
			// The border is tow solid lines. The sum of the two lines and
			// the
			// space between them equals the values of 'border-width'.
			case Constants.EN_DOUBLE:
				stylebox.initIndex(4);
				break;
			// The border looks as though it were carved into the canvas.
			case Constants.EN_GROOVE:
				stylebox.initIndex(5);
				break;
			// The opposite of 'groove': the border looks as though it were
			// coming out of the canvas.
			case Constants.EN_RIDGE:
				stylebox.initIndex(6);
				break;
			// The border makes the entire box look as though it were
			// embedded
			// in the canvas
			case Constants.EN_INSET:
				stylebox.initIndex(7);
				break;
			// The opposite of 'inst': the border makes th eentire box look
			// as
			// though it were coming out of the canvas
			case Constants.EN_OUTSET:
				stylebox.initIndex(8);
				break;

			default:
				stylebox.initIndex(0);
				break;
			}

		}
	}

	public List<FixedLengthSpinner> getWidthValue()
	{
		return widthValue;
	}

	public List<WiseCombobox> getStyle()
	{
		return style;
	}

	public List<ColorComboBox> getColor()
	{
		return color;
	}

	public JPanel getBorderPreviewPanel()
	{
		return borderPreviewPanel;
	}

	/**
	 * 
	 * 返回null表示没有进行属性设置
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public Map<Integer, Object> getProperties()
	{

		Map<Integer, Object> properties = new HashMap<Integer, Object>();
		int beforestyleindex = style.get(0).getSelectedIndex();
		EnumProperty beforestyle;
		CondLengthProperty beforewidth;
		WiseDocColor beforecolor;
		FixedLength beforewid = widthValue.get(0).getValue();
		if (beforestyleindex <= 0 || beforewid == null
				|| beforewid.getValue() < 10)
		{
			beforestyle = null;
			beforewidth = null;
			beforecolor = null;
		} else
		{
			beforestyle = convertStyle(style.get(0));
			beforewidth = convertLength(widthValue.get(0));
			beforecolor = convertColor(color.get(0), colorlayer.get(0));
		}
		properties.put(Constants.PR_BORDER_BEFORE_WIDTH, beforewidth);
		properties.put(Constants.PR_BORDER_BEFORE_STYLE, beforestyle);
		properties.put(Constants.PR_BORDER_BEFORE_COLOR, beforecolor);
		int afterstyleindex = style.get(1).getSelectedIndex();
		EnumProperty afterstyle;
		CondLengthProperty afterwidth;
		WiseDocColor aftercolor;
		FixedLength afterwid = widthValue.get(1).getValue();
		// 如果边框类型是none，则清除该边框属性
		if (afterstyleindex <= 0 || afterwid == null
				|| afterwid.getValue() < 10)
		{
			afterstyle = null;
			afterwidth = null;
			aftercolor = null;
		} else
		{
			afterstyle = convertStyle(style.get(1));
			afterwidth = convertLength(widthValue.get(1));
			aftercolor = convertColor(color.get(1), colorlayer.get(1));
		}
		properties.put(Constants.PR_BORDER_AFTER_WIDTH, afterwidth);
		properties.put(Constants.PR_BORDER_AFTER_STYLE, afterstyle);
		properties.put(Constants.PR_BORDER_AFTER_COLOR, aftercolor);
		int startstyleindex = style.get(2).getSelectedIndex();
		EnumProperty startstyle;
		CondLengthProperty startwidth;
		WiseDocColor startcolor;
		FixedLength startwid = widthValue.get(2).getValue();
		if (startstyleindex <= 0 || startwid == null
				|| startwid.getValue() < 10)
		{
			startstyle = null;
			startwidth = null;
			startcolor = null;
		} else
		{
			startstyle = convertStyle(style.get(2));
			startwidth = convertLength(widthValue.get(2));
			startcolor = convertColor(color.get(2), colorlayer.get(2));
		}
		properties.put(Constants.PR_BORDER_START_WIDTH, startwidth);
		properties.put(Constants.PR_BORDER_START_STYLE, startstyle);
		properties.put(Constants.PR_BORDER_START_COLOR, startcolor);
		int endstyleindex = style.get(3).getSelectedIndex();
		EnumProperty endstyle;
		CondLengthProperty endwidth;
		WiseDocColor endcolor;
		FixedLength endwid = widthValue.get(3).getValue();
		if (endstyleindex <= 0 || endwid == null || endwid.getValue() < 10)
		{
			endstyle = null;
			endwidth = null;
			endcolor = null;
		} else
		{
			endstyle = convertStyle(style.get(3));
			endwidth = convertLength(widthValue.get(3));
			endcolor = convertColor(color.get(3), colorlayer.get(3));
		}
		properties.put(Constants.PR_BORDER_END_WIDTH, endwidth);
		properties.put(Constants.PR_BORDER_END_STYLE, endstyle);
		properties.put(Constants.PR_BORDER_END_COLOR, endcolor);
		return properties;
		// setFOProperties(propertyType, properties);
	}

	public Map<Integer, Object> getPropertie()
	{
		Map<Integer, Object> mapnew = new HashMap<Integer, Object>(
				getProperties());
		Object[] keys = mapnew.keySet().toArray();
		int size = mapnew.size();
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
			{
				int key = (Integer) keys[i];
				if (mapnew.get(key) == null)
				{
					mapnew.remove(key);
				}
			}
		}
		return mapnew;
	}

	private CondLengthProperty convertLength(FixedLengthSpinner widthValue)
	{
		FixedLength fixedLength = widthValue.getValue();
		CondLengthProperty length = new CondLengthProperty(fixedLength, true);
		return length;
	}

	private EnumProperty convertStyle(WiseCombobox style)
	{

		int styleId = -1;
		/*
		 * 边框的具体效果，详见XSL规范7.8.20
		 */
		switch (style.getSelectedIndex())
		{
		case 0:
			styleId = Constants.EN_NONE;
			break;
		case 1:
			styleId = Constants.EN_SOLID;
			break;
		// The border is a series of short line segments
		case 2:
			styleId = Constants.EN_DOTTED;
			break;
		// The border is a single line segment.
		case 3:
			styleId = Constants.EN_DASHED;
			break;
		// The border is tow solid lines. The sum of the two lines and the
		// space between them equals the values of 'border-width'.
		case 4:
			styleId = Constants.EN_DOUBLE;
			break;
		// The border looks as though it were carved into the canvas.
		case 5:
			styleId = Constants.EN_GROOVE;
			break;
		// The opposite of 'groove': the border looks as though it were
		// coming out of the canvas.
		case 6:
			styleId = Constants.EN_RIDGE;
			break;
		// The border makes the entire box look as though it were embedded
		// in the canvas
		case 7:
			styleId = Constants.EN_INSET;
			break;
		// The opposite of 'inst': the border makes th eentire box look as
		// though it were coming out of the canvas
		case 8:
			styleId = Constants.EN_OUTSET;
			break;

		default:
			break;
		}

		EnumProperty ep = new EnumProperty(styleId, "NONE");
		return ep;
	}

	private WiseDocColor convertColor(ColorComboBox colorComboBox,
			LayerComboBox Layerbox)
	{

		if (colorComboBox instanceof ColorComboBox)
		{
			ColorComboBox colorCom = colorComboBox;
			if (colorCom.getSelectedItem() instanceof Color)
			{
				return new WiseDocColor((Color) colorCom.getSelectedItem(),
						Layerbox.getLayer());
			}
		}
		return null;
	}

	private void checkboxselect()
	{
		boolean isfirst = true;
		Iterator<FixedLengthSpinner> spinnerit;
		spinnerit = widthValue.iterator();
		FixedLengthSpinner topspinner = spinnerit.next();
		topspinner.setEnabled(true);
		// SpinnerFixedLengthModel model = new
		// SpinnerFixedLengthModel(topspinner.getValue(),null,null,5);
		SpinnerFixedLengthModel model = getModel();
		FixedLength oldlength = topspinner.getValue();
		if (oldlength != null)
		{
			model.setValue(oldlength);
		}
		topspinner.setModel(model);
		while (spinnerit.hasNext())
		{
			FixedLengthSpinner js = spinnerit.next();
			js.setModel(model);
			if (!isfirst)
			{
				js.setEnabled(false);
			}
			isfirst = false;
		}
		Iterator<?> it = style.iterator();
		DefaultComboBoxModel deBoxModel = new DefaultComboBoxModel(
				UiText.PARAGRAPH_BORDER_STYLE_LIST);
		int selectindex = style_top.getSelectedIndex();
		if (selectindex > -1)
		{
			deBoxModel
					.setSelectedItem(UiText.PARAGRAPH_BORDER_STYLE_LIST[selectindex]);
		}
		isfirst = true;
		while (it.hasNext())
		{
			WiseCombobox style = (WiseCombobox) it.next();
			style.setModel(deBoxModel);
			if (!isfirst)
			{
				style.setEnabled(false);
			}
			isfirst = false;
		}
		it = color.iterator();
		ColorComboBoxModel colorModel = new ColorComboBoxModel();
		Object oldcolor = colorTop.getSelectedItem();
		if (oldcolor != null)
		{
			colorModel.setSelectedItem(oldcolor);
		}
		isfirst = true;
		while (it.hasNext())
		{
			ColorComboBox color = (ColorComboBox) it.next();
			color.setModel(colorModel);
			if (!isfirst)
			{
				color.setEnabled(false);
			}
			isfirst = false;
		}
		it = colorlayer.iterator();
		LayerComboBoxModel layerModel = new LayerComboBoxModel();
		Object oldlayer = layer_top.getSelectedItem();
		if (oldlayer != null)
		{
			layerModel.setSelectedItem(oldlayer);
		}
		isfirst = true;
		while (it.hasNext())
		{
			LayerComboBox layer = (LayerComboBox) it.next();
			layer.setModel(layerModel);
			if (!isfirst)
			{
				layer.setEnabled(false);
			}
			isfirst = false;
		}
		borderPreviewPanel.updateStyle(getProperties());
	}

	private void checkbox1Select()
	{
		Iterator<?> it;
		it = widthValue.iterator();

		while (it.hasNext())
		{
			FixedLengthSpinner js = (FixedLengthSpinner) it.next();
			SpinnerFixedLengthModel model = getModel();
			FixedLength oldvalue = js.getValue();
			if (oldvalue != null)
			{
				model.setValue(oldvalue);
			}
			js.setModel(model);
			js.setEnabled(true);
		}
		it = style.iterator();
		while (it.hasNext())
		{
			WiseCombobox style = (WiseCombobox) it.next();
			int oldindex = style.getSelectedIndex();
			style.setModel(new DefaultComboBoxModel(
					UiText.PARAGRAPH_BORDER_STYLE_LIST));
			if (oldindex > -1)
			{
				style.initIndex(oldindex);
			}
			style.setEnabled(true);
		}
		it = color.iterator();
		while (it.hasNext())
		{
			ColorComboBox color = (ColorComboBox) it.next();
			Object oldcolor = color.getSelectedItem();
			color.setModel(new ColorComboBoxModel());
			if (oldcolor != null)
			{
				color.InitValue(oldcolor);
			}
			color.setEnabled(true);
		}
		it = colorlayer.iterator();
		while (it.hasNext())
		{
			LayerComboBox layer = (LayerComboBox) it.next();
			int oldindex = layer.getSelectedIndex();
			layer.setModel(new LayerComboBoxModel());
			if (oldindex > -1)
			{
				layer.initIndex(oldindex);
			}
			layer.setEnabled(true);
		}
	}

	// 【添加：START】 by 李晓光 2010-1-27
	private SpinnerFixedLengthModel getModel()
	{
		FixedLength min = new FixedLength(0.1, "pt");
		FixedLength max = new FixedLength(100.0, "pt");
		FixedLength def = new FixedLength(0.5, "pt");
		return new SpinnerFixedLengthModel(def, min, max, 5);
	}
	// 【添加：END】 by 李晓光 2010-1-27
}
