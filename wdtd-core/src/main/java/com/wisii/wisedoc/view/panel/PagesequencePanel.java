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

package com.wisii.wisedoc.view.panel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JLabel;
import com.borland.jbcl.layout.XYConstraints;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.dialog.ConfigurationInformationDialog;
import com.wisii.wisedoc.view.dialog.GetProfileValue;

@SuppressWarnings("serial")
public class PagesequencePanel extends OnlyLayoutPanel
{

	String one = "1";

	String auto = "自动";

	String odd = "奇数";

	String even = "偶数";

	String number = "指定数值";

	String all_odd = "共奇数页";

	String all_even = "共偶数页";

	String end_odd = "最后一页页码是奇数";

	String end_even = "最后一页页码是偶数";

	String no_force = "不限制";

	WiseCombobox comboBoxIPN = new WiseCombobox();
	{
		comboBoxIPN.addItem(one);
		comboBoxIPN.addItem(auto);
		comboBoxIPN.addItem(odd);
		comboBoxIPN.addItem(even);
		// comboBoxIPN.addItem(number);
		comboBoxIPN.setEditable(true);
		int value = GetProfileValue
				.getIntValue(Constants.PR_INITIAL_PAGE_NUMBER);
		if (value == 1)
		{
			comboBoxIPN.setSelectedItem(one);
		} else if (value == Constants.EN_AUTO_EVEN)
		{
			comboBoxIPN.setSelectedItem(even);
		} else if (value == Constants.EN_AUTO_ODD)
		{
			comboBoxIPN.setSelectedItem(odd);
		} else if (value == Constants.EN_AUTO)
		{
			comboBoxIPN.setSelectedItem(auto);
		} else
		{
			comboBoxIPN.addItem(value + "");
			comboBoxIPN.setSelectedItem(value + "");
		}

		comboBoxIPN.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					comboBoxIPN.setSelectedItem(current);
					if (current.equalsIgnoreCase(auto))
					{
						ConfigurationInformationDialog
								.addProFileItem(
										GetProfileValue
												.getKey(Constants.PR_INITIAL_PAGE_NUMBER),
										Constants.EN_AUTO + "");
					} else if (current.equalsIgnoreCase(odd))
					{
						ConfigurationInformationDialog
								.addProFileItem(
										GetProfileValue
												.getKey(Constants.PR_INITIAL_PAGE_NUMBER),
										Constants.EN_AUTO_ODD + "");
					} else if (current.equalsIgnoreCase(even))
					{
						ConfigurationInformationDialog
								.addProFileItem(
										GetProfileValue
												.getKey(Constants.PR_INITIAL_PAGE_NUMBER),
										Constants.EN_AUTO_EVEN + "");
					} else
					{
						ConfigurationInformationDialog
								.addProFileItem(
										GetProfileValue
												.getKey(Constants.PR_INITIAL_PAGE_NUMBER),
										current);
					}
				}
			}
		});

	}

	WiseCombobox comboBoxFPC = new WiseCombobox();
	{
		comboBoxFPC.addItem(auto);
		comboBoxFPC.addItem(all_odd);
		comboBoxFPC.addItem(all_even);
		comboBoxFPC.addItem(end_odd);
		comboBoxFPC.addItem(end_even);
		comboBoxFPC.addItem(no_force);
		int value = GetProfileValue.getIntValue(Constants.PR_FORCE_PAGE_COUNT);
		if (value == Constants.EN_END_ON_ODD)
		{
			comboBoxFPC.setSelectedItem(end_odd);
		} else if (value == Constants.EN_END_ON_EVEN)
		{
			comboBoxFPC.setSelectedItem(end_even);
		} else if (value == Constants.EN_EVEN)
		{
			comboBoxFPC.setSelectedItem(all_even);
		} else if (value == Constants.EN_ODD)
		{
			comboBoxFPC.setSelectedItem(all_odd);
		} else if (value == Constants.EN_NO_FORCE)
		{
			comboBoxFPC.setSelectedItem(no_force);
		} else
		{
			comboBoxFPC.setSelectedItem(auto);
		}

		comboBoxFPC.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					comboBoxFPC.setSelectedItem(current);
					if (current.equalsIgnoreCase(auto))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue
										.getKey(Constants.PR_FORCE_PAGE_COUNT),
								Constants.EN_AUTO + "");
					} else if (current.equalsIgnoreCase(end_odd))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue
										.getKey(Constants.PR_FORCE_PAGE_COUNT),
								Constants.EN_END_ON_ODD + "");
					} else if (current.equalsIgnoreCase(end_even))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue
										.getKey(Constants.PR_FORCE_PAGE_COUNT),
								Constants.EN_END_ON_EVEN + "");
					} else if (current.equalsIgnoreCase(all_odd))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue
										.getKey(Constants.PR_FORCE_PAGE_COUNT),
								Constants.EN_ODD + "");
					} else if (current.equalsIgnoreCase(all_even))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue
										.getKey(Constants.PR_FORCE_PAGE_COUNT),
								Constants.EN_EVEN + "");
					} else if (current.equalsIgnoreCase(no_force))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue
										.getKey(Constants.PR_FORCE_PAGE_COUNT),
								Constants.EN_NO_FORCE + "");
					}
				}
			}
		});

	}

	public PagesequencePanel()
	{
		super();
		this.add(new JLabel("初始页码"), new XYConstraints(100, 220, 100, 20));
		this.add(comboBoxIPN, new XYConstraints(200, 220, 150, 20));
		this.add(new JLabel("强制页数"), new XYConstraints(100, 260, 100, 20));
		this.add(comboBoxFPC, new XYConstraints(200, 260, 150, 20));

	}

}
