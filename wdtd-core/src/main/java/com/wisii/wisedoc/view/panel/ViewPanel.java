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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import com.borland.jbcl.layout.XYConstraints;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.dialog.ConfigurationInformationDialog;
import com.wisii.wisedoc.view.dialog.GetProfileValue;

@SuppressWarnings("serial")
public class ViewPanel extends OnlyLayoutPanel
{

	JCheckBox ruler = new JCheckBox("显示标尺");

	String allPage = "整页";

	String pageWidth = "页宽";

	String p25 = "25%";

	String p50 = "50%";

	String p75 = "75%";

	String p100 = "100%";

	String p150 = "150%";

	String p200 = "200%";

	String p500 = "500%";

	WiseCombobox showFactor = new WiseCombobox();
	{
		showFactor.addItem(allPage);
		showFactor.addItem(pageWidth);
		showFactor.addItem(p25);
		showFactor.addItem(p50);
		showFactor.addItem(p75);
		showFactor.addItem(p100);
		showFactor.addItem(p150);
		showFactor.addItem(p200);
		showFactor.addItem(p500);
		String value = GetProfileValue.getValue("showFactor");
		if (value != null)
		{
			showFactor.setSelectedItem(value);
		} else
		{
			showFactor.setSelectedItem(p100);
		}
		showFactor.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					showFactor.setSelectedItem(current);
					ConfigurationInformationDialog.addProFileItem("showFactor",
							current);
				}
			}
		});
	}

	JCheckBox showNetCell = new JCheckBox("显示网格");

	WiseTextField cellWidth = new WiseTextField("5mm");

	String p60 = "60%";

	String p70 = "70%";

	String p80 = "80%";

	String p81 = "81%";

	String p82 = "82%";

	String p83 = "83%";

	String p84 = "84%";

	String p85 = "85%";

	String p86 = "86%";

	String p87 = "87%";

	String p88 = "88%";

	String p89 = "89%";

	String p90 = "90%";

	String p95 = "95%";

	WiseCombobox widthFactor = new WiseCombobox();
	{
		widthFactor.addItem(p50);
		widthFactor.addItem(p60);
		widthFactor.addItem(p70);
		widthFactor.addItem(p75);
		widthFactor.addItem(p80);
		widthFactor.addItem(p81);
		widthFactor.addItem(p82);
		widthFactor.addItem(p83);
		widthFactor.addItem(p84);
		widthFactor.addItem(p85);
		widthFactor.addItem(p86);
		widthFactor.addItem(p87);
		widthFactor.addItem(p88);
		widthFactor.addItem(p89);
		widthFactor.addItem(p90);
		widthFactor.addItem(p95);
		widthFactor.addItem(p100);
		String value = GetProfileValue.getValue("widthFactor");
		if (value != null)
		{
			widthFactor.setSelectedItem(value);
		} else
		{
			widthFactor.setSelectedItem(p88);
		}
		widthFactor.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					widthFactor.setSelectedItem(current);
					ConfigurationInformationDialog.addProFileItem(
							"widthFactor", current);
				}
			}
		});
	}

	WiseTextField width = new WiseTextField("210");

	WiseTextField height = new WiseTextField("160");

	JCheckBox showState = new JCheckBox("显示状态栏");

	JCheckBox showHScoll = new JCheckBox("显示水平方向的滚动条");

	JCheckBox showVScoll = new JCheckBox("显示垂直方向的滚动条");

	public ViewPanel()
	{
		super();

		this.add(ruler, new XYConstraints(200, 20, 150, 20));

		this.add(new JLabel("显示比例"), new XYConstraints(200, 50, 60, 20));
		this.add(showFactor, new XYConstraints(265, 50, 80, 20));

		this.add(showNetCell, new XYConstraints(200, 80, 150, 20));

		this.add(new JLabel("网格宽度"), new XYConstraints(200, 110, 60, 20));
		this.add(cellWidth, new XYConstraints(265, 110, 80, 20));
		this.add(new JLabel("单位：MM"), new XYConstraints(400, 110, 80, 20));

		this.add(new JLabel("编辑区占界面宽度的比例"),
				new XYConstraints(200, 140, 150, 20));
		this.add(widthFactor, new XYConstraints(355, 140, 80, 20));

		this.add(new JLabel("初始窗体的宽"), new XYConstraints(200, 170, 80, 20));
		this.add(width, new XYConstraints(285, 170, 80, 20));

		this.add(new JLabel("初始窗体的高"), new XYConstraints(200, 200, 80, 20));
		this.add(height, new XYConstraints(285, 200, 80, 20));

		this.add(showState, new XYConstraints(200, 230, 200, 20));

		this.add(showHScoll, new XYConstraints(200, 260, 200, 20));

		this.add(showVScoll, new XYConstraints(200, 290, 200, 20));

	}
}
