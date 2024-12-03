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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import com.borland.jbcl.layout.XYConstraints;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;

@SuppressWarnings("serial")
public class TablePanel extends OnlyLayoutPanel
{

	String cellRow = "行+单元格";

	String columnRow = "行+列";

	JCheckBox heard = new JCheckBox("添加表头", true);

	JCheckBox footer = new JCheckBox("添加表尾", true);

	JCheckBox showHeardall = new JCheckBox("表头每页显示",
			false);

	JCheckBox showFooterall = new JCheckBox("表尾每页显示",
			false);

	WiseCombobox tableFixed = new WiseCombobox();
	{
		tableFixed.addItem(cellRow);
		tableFixed.addItem(columnRow);
		tableFixed.setSelectedItem(cellRow);
	}

	JCheckBox cellHeight = new JCheckBox("单元格固定大小", false);

	WiseTextField columnWidth = new WiseTextField("0.0");

	JButton other = new JButton("其它属性");
	{
		other.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				other.doClick();
			}
		});
	}

	public TablePanel()
	{
		super();
		this.add(heard, new XYConstraints(160, 20, 100, 20));

		this.add(footer, new XYConstraints(160, 60, 100, 20));

		this.add(showHeardall, new XYConstraints(160, 100, 150, 20));

		this.add(showFooterall, new XYConstraints(160, 140, 150, 20));

		this.add(new JLabel("表结构"), new XYConstraints(110, 180, 50, 20));
		this.add(tableFixed, new XYConstraints(160, 180, 100, 20));

		this.add(cellHeight, new XYConstraints(160, 220, 150, 20));

		this.add(new JLabel("列宽"), new XYConstraints(120, 260, 40, 20));
		this.add(columnWidth, new XYConstraints(160, 260, 100, 20));

		this.add(other, new XYConstraints(200, 340, 100, 20));
	}
}
