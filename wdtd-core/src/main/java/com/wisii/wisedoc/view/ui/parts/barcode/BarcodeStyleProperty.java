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

package com.wisii.wisedoc.view.ui.parts.barcode;

import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 条形码属性样式面板
 * 
 * @author 闫舒寰
 * @version 1.0 2009/03/18
 */
public class BarcodeStyleProperty extends JPanel
{

	private WiseCombobox bLayer;

	private FixedLengthSpinner bBeforeAfter;

	private FixedLengthSpinner bStartEnd;

	private WiseSpinner bWidetoNarro;

	private FixedLengthSpinner bWidth;

	private WiseSpinner bOrientation;

	private FixedLengthSpinner bHeight;

	JCheckBox bChecksum;

	JCheckBox bUCCEAN;

	/**
	 * Create the panel
	 */
	public BarcodeStyleProperty()
	{
		super();

		JLabel label;
		label = new JLabel();
		label.setText(RibbonUIText.BARCODE_HEIGHT_LABEL);

		bHeight = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(RibbonUIText.BARCODE_DIRECTION_BAND);

		bOrientation = new WiseSpinner(new SpinnerNumberModel(0, 0, 270, 90));

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(RibbonUIText.BARCODE_WIDTH_LABEL);

		bWidth = new FixedLengthSpinner(
				new SpinnerFixedLengthModel(new FixedLength(0.305, "mm"), new FixedLength(0.00001d, "mm"), /*new FixedLength(1, "mm")*/null, 1));

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText(RibbonUIText.BARCODE_WIDE_NARROW_BAND);

		bWidetoNarro = new WiseSpinner(new SpinnerNumberModel(3.0, 2.0, 3.0,
				0.1));

		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText(RibbonUIText.BARCODE_START_LABEL);

		bStartEnd = new FixedLengthSpinner();

		JLabel label_5;
		label_5 = new JLabel();
		label_5.setText(RibbonUIText.BARCODE_TOP_LABEL);

		bBeforeAfter = new FixedLengthSpinner();

		bChecksum = new JCheckBox();
		bChecksum.setText(RibbonUIText.BARCODE_CHECK_SUM);

		bUCCEAN = new JCheckBox();
		bUCCEAN.setText(RibbonUIText.BARCODE_UCC_EAN);

		JLabel label_6;
		label_6 = new JLabel();
		label_6.setText(RibbonUIText.BARCODE_COLOR_LAYER);

		bLayer = new WiseCombobox(new DefaultComboBoxModel(
				UiText.COMMON_COLOR_LAYER));

		final GroupLayout groupLayout = new GroupLayout(this);
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
																																label)
																														.addComponent(
																																label_2)
																														.addComponent(
																																label_4))
																										.addGap(
																												9,
																												9,
																												9)
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																bStartEnd,
																																90,
																																90,
																																90)
																														.addComponent(
																																bWidth,
																																90,
																																90,
																																90)
																														.addComponent(
																																bHeight,
																																90,
																																90,
																																90)))
																						.addComponent(
																								bChecksum))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				12)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createParallelGroup(
																												GroupLayout.Alignment.LEADING,
																												false)
																										.addGroup(
																												groupLayout
																														.createSequentialGroup()
																														.addGroup(
																																groupLayout
																																		.createParallelGroup(
																																				GroupLayout.Alignment.LEADING)
																																		.addComponent(
																																				label_3)
																																		.addComponent(
																																				label_1)
																																		.addComponent(
																																				label_5))
																														.addGap(
																																9,
																																9,
																																9)
																														.addGroup(
																																groupLayout
																																		.createParallelGroup(
																																				GroupLayout.Alignment.LEADING)
																																		.addComponent(
																																				bWidetoNarro,
																																				90,
																																				90,
																																				90)
																																		.addComponent(
																																				bOrientation,
																																				90,
																																				90,
																																				90)
																																		.addComponent(
																																				bBeforeAfter,
																																				90,
																																				90,
																																				90))))
																						.addComponent(
																								bUCCEAN))
																		.addContainerGap(
																				21,
																				Short.MAX_VALUE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				label_6)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				bLayer,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addContainerGap(
																				168,
																				Short.MAX_VALUE)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addGroup(
						groupLayout.createParallelGroup(
								GroupLayout.Alignment.BASELINE).addComponent(
								label).addComponent(bHeight,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addComponent(
								bOrientation, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addComponent(
								label_1)).addGap(23, 23, 23).addGroup(
						groupLayout.createParallelGroup(
								GroupLayout.Alignment.BASELINE).addComponent(
								label_2).addComponent(bWidth,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addComponent(
								bWidetoNarro, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addComponent(
								label_3)).addGap(29, 29, 29).addGroup(
						groupLayout.createParallelGroup(
								GroupLayout.Alignment.BASELINE).addComponent(
								label_4).addComponent(label_5).addComponent(
								bBeforeAfter, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addComponent(
								bStartEnd, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addGap(25, 25, 25)
						.addGroup(
								groupLayout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(bChecksum).addComponent(
												bUCCEAN)).addGap(26, 26, 26)
						.addGroup(
								groupLayout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(label_6).addComponent(
												bLayer,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		setLayout(groupLayout);
		//
	}

	public Map<Integer, Object> getProperties()
	{

		Map<Integer, Object> temp = new HashMap<Integer, Object>();

		temp.put(Constants.PR_BARCODE_HEIGHT, bHeight.getValue());
		temp.put(Constants.PR_BARCODE_MODULE, bWidth.getValue());
		temp.put(Constants.PR_BARCODE_WIDE_TO_NARROW, bWidetoNarro.getValue());
		temp.put(Constants.PR_BARCODE_ADDCHECKSUM, bChecksum.isSelected());

		temp.put(Constants.PR_BARCODE_QUIET_HORIZONTAL, bStartEnd.getValue());
		temp.put(Constants.PR_BARCODE_QUIET_VERTICAL, bBeforeAfter.getValue());

		if (bUCCEAN.isSelected())
		{
			temp.put(Constants.PR_BARCODE_MAKEUCC, new EnumProperty(
					Constants.EN_TRUE, ""));
		} else
		{
			temp.put(Constants.PR_BARCODE_MAKEUCC, new EnumProperty(
					Constants.EN_FALSE, ""));
		}

		int dValue = (Integer) bOrientation.getValue();
		if (dValue == 90 || dValue == 180 || dValue == 270 || dValue == 0)
		{
			temp.put(Constants.PR_BARCODE_ORIENTATION, dValue);
		}
		return temp;
	}

	public void initialPanelProperty(Map<Integer, Object> pro)
	{

		bHeight.initValue(pro.get(Constants.PR_BARCODE_HEIGHT));
		bWidth.initValue(pro.get(Constants.PR_BARCODE_MODULE));

		bStartEnd.initValue(pro.get(Constants.PR_BARCODE_QUIET_HORIZONTAL));
		bBeforeAfter.initValue(pro.get(Constants.PR_BARCODE_QUIET_VERTICAL));

		Object o1 = pro.get(Constants.PR_BARCODE_ADDCHECKSUM);
		if (o1 instanceof Boolean)
		{
			Boolean b = (Boolean) o1;
			bChecksum.setSelected(b);
		}

		Object o2 = pro.get(Constants.PR_BARCODE_MAKEUCC);
		if (o2 instanceof EnumProperty)
		{
			EnumProperty b = (EnumProperty) o2;
			if (b.equals(new EnumProperty(Constants.EN_TRUE, "")))
			{
				bUCCEAN.setSelected(true);
			} else if (b.equals(new EnumProperty(Constants.EN_FALSE, "")))
			{
				bUCCEAN.setSelected(false);
			}
		}

		Object o4 = pro.get(Constants.PR_BARCODE_ORIENTATION);
		if (o4 instanceof Integer)
		{
			Integer in = (Integer) o4;
			bOrientation.initValue(in);
		}

		Object o5 = pro.get(Constants.PR_BARCODE_WIDE_TO_NARROW);
		if (o5 instanceof Number)
		{
			Number in = (Number) o5;
			bWidetoNarro.initValue(in);
		}

	}

}
