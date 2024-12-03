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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.dialog.ConfigurationInformationDialog;
import com.wisii.wisedoc.view.dialog.GetProfileValue;

@SuppressWarnings("serial")
public class LayoutPanel extends OnlyLayoutPanel
{

	JCheckBox standard = new JCheckBox("标准");
	{
		String value = GetProfileValue.getValue("standardlayout");
		if (value.equalsIgnoreCase("true"))
		{
			standard.setSelected(true);
		} else
		{
			standard.setSelected(false);
		}
		standard.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				custom.setSelected(!standard.isSelected());
				before.setEditable(custom.isSelected());
				after.setEditable(custom.isSelected());
				if (standard.isSelected())
				{
					ConfigurationInformationDialog.addProFileItem(
							"standardlayout", "true");
				} else
				{
					ConfigurationInformationDialog.addProFileItem(
							"standardlayout", "false");
				}
			}
		});
	}

	JCheckBox custom = new JCheckBox("自定义");
	{
		custom.setSelected(!standard.isSelected());
		custom.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				standard.setSelected(!custom.isSelected());
				before.setEditable(custom.isSelected());
				after.setEditable(custom.isSelected());
				if (standard.isSelected())
				{
					ConfigurationInformationDialog.addProFileItem(
							"standardlayout", "true");
				} else
				{
					ConfigurationInformationDialog.addProFileItem(
							"standardlayout", "false");
				}
			}
		});
	}

	WiseTextField before = new WiseTextField("before");

	{
		before.setEditable(custom.isSelected());
	}

	WiseTextField after = new WiseTextField("after");
	{
		after.setEditable(custom.isSelected());
	}

	final String all = "全部保留";

	final String one = "只保留一个";

	WiseCombobox numberofspaces = new WiseCombobox();
	{
		numberofspaces.addItem(all);
		numberofspaces.addItem(one);
		numberofspaces.setSelectedItem(all);
	}

	String ignore = "忽略";

	String preserve = "保留";

	String ignoreB = "换行符之前忽略";

	String ignoreA = "换行符之后忽略";

	String ignoreR = "换行符前后忽略";

	WiseCombobox spacesTreat = new WiseCombobox();
	{
		spacesTreat.addItem(preserve);
		spacesTreat.addItem(ignore);
		spacesTreat.addItem(ignoreB);
		spacesTreat.addItem(ignoreA);
		spacesTreat.addItem(ignoreR);
		spacesTreat.setSelectedItem(preserve);
	}

	String asSpace = "作为空格处理";

	WiseCombobox linefeed = new WiseCombobox();
	{
		linefeed.addItem(preserve);
		linefeed.addItem(ignore);
		linefeed.addItem(asSpace);
		linefeed.setSelectedItem(preserve);
	}

	WiseTextField nullData = new WiseTextField();

	public LayoutPanel()
	{
		super();
		this.add(standard, new XYConstraints(20, 20, 100, 20));

		this.add(custom, new XYConstraints(160, 20, 100, 20));

		this.add(new JLabel("后置标点"), new XYConstraints(10, 50, 50, 20));
		this.add(after, new XYConstraints(60, 50, 520, 20));

		this.add(new JLabel("前置标点"), new XYConstraints(10, 80, 50, 20));
		this.add(before, new XYConstraints(60, 80, 520, 20));

		JPanel spSymPanel = new JPanel();
		spSymPanel.setSize(490, 260);
		XYLayout specialSymbolsLayout = new XYLayout();
		specialSymbolsLayout.setWidth(490);
		specialSymbolsLayout.setHeight(260);
		spSymPanel.setLayout(specialSymbolsLayout);
		spSymPanel.setBackground(getBackground());

		spSymPanel
				.add(new JLabel("连续空格的处理"), new XYConstraints(50, 0, 100, 20));
		spSymPanel.add(numberofspaces, new XYConstraints(155, 0, 120, 20));

		spSymPanel.add(new JLabel("空格的处理"), new XYConstraints(50, 30, 100, 20));
		spSymPanel.add(spacesTreat, new XYConstraints(155, 30, 120, 20));

		spSymPanel
				.add(new JLabel("换行符的处理"), new XYConstraints(50, 60, 100, 20));
		spSymPanel.add(linefeed, new XYConstraints(155, 60, 120, 20));

		spSymPanel
				.add(new JLabel("空数据的处理"), new XYConstraints(50, 90, 100, 20));
		spSymPanel.add(nullData, new XYConstraints(155, 90, 120, 20));

		spSymPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"特殊符号的处理", TitledBorder.CENTER, TitledBorder.TOP));

		this.add(spSymPanel, new XYConstraints(20, 150, 355, 150));
	}
}
