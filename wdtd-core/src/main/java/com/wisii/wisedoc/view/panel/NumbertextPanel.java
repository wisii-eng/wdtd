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
import javax.swing.JLabel;

import com.borland.jbcl.layout.XYConstraints;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;

@SuppressWarnings("serial")
public class NumbertextPanel extends OnlyLayoutPanel
{

	String no = "无";

	String hasunit = "壹拾壹元壹角壹分";

	String nounit = "壹壹壹壹";

	WiseCombobox type = new WiseCombobox();
	{
		type.addItem(no);
		type.addItem(hasunit);
		type.addItem(nounit);
		type.setSelectedItem(no);
	}

	WiseTextField number = new WiseTextField("2");

	WiseTextField thousand = new WiseTextField(",");

	WiseTextField point = new WiseTextField(".");

	WiseTextField error = new WiseTextField("NaN");

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

	public NumbertextPanel()
	{
		super();
		this.add(new JLabel("中文数字设置"), new XYConstraints(80, 60, 100, 20));
		this.add(type, new XYConstraints(180, 60, 120, 20));

		this.add(new JLabel("小数位数"), new XYConstraints(80, 100, 100, 20));
		this.add(number, new XYConstraints(180, 100, 80, 20));

		this.add(new JLabel("千位分隔符"), new XYConstraints(80, 140, 100, 20));
		this.add(thousand, new XYConstraints(180, 140, 80, 20));

		this.add(new JLabel("小数分隔符"), new XYConstraints(80, 180, 100, 20));
		this.add(point, new XYConstraints(180, 180, 80, 20));

		this.add(new JLabel("非法数据处理"), new XYConstraints(80, 220, 100, 20));
		this.add(error, new XYConstraints(180, 220, 80, 20));

		this.add(other, new XYConstraints(160, 280, 100, 20));

	}
}
