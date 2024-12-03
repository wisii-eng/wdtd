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
import javax.swing.SpinnerNumberModel;
import com.borland.jbcl.layout.XYConstraints;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;

@SuppressWarnings("serial")
public class PicturePanel extends OnlyLayoutPanel
{

	String path = "路径";

	String function = "回调函数";

	JCheckBox old = new JCheckBox("保持原图片大小", true);

	JCheckBox hv = new JCheckBox("保持纵横比", true);

	WiseCombobox type = new WiseCombobox();
	{
		type.addItem(path);
		type.addItem(function);
		type.setSelectedItem(path);
	}

	WiseSpinner transparency = new WiseSpinner();
	{
		SpinnerNumberModel data = new SpinnerNumberModel(0, 0, 1, 0.01);
		transparency.setModel(data);
	}

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

	public PicturePanel()
	{
		super();

		this.add(old, new XYConstraints(80, 60, 150, 20));
		this.add(hv, new XYConstraints(80, 100, 150, 20));

		this.add(new JLabel("图片类型"), new XYConstraints(80, 140, 60, 20));
		this.add(type, new XYConstraints(140, 140, 80, 20));

		this.add(new JLabel("透明度"), new XYConstraints(80, 180, 60, 20));
		this.add(transparency, new XYConstraints(140, 180, 80, 20));

		this.add(other, new XYConstraints(160, 240, 100, 20));
	}
}
