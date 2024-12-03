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
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.dialog.GetProfileValue;

@SuppressWarnings("serial")
public class BlockPanel extends OnlyLayoutPanel
{

	WiseTextField startindent = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	WiseTextField endindent = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	WiseTextField textalign = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	WiseTextField textalignlast = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	WiseTextField textindent = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	WiseTextField widows = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	WiseTextField orphans = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	WiseTextField lineheight = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	WiseTextField linespace = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	WiseTextField wordsapce = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	WiseTextField letterspace = new WiseTextField(GetProfileValue
			.getKey(Constants.PR_START_INDENT));

	JCheckBox linefeed = new JCheckBox("自动换行", true);

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

	public BlockPanel()
	{
		super();
		this.add(new JLabel("左缩进"), new XYConstraints(80, 20, 60, 20));
		this.add(startindent, new XYConstraints(160, 20, 100, 20));

		this.add(new JLabel("单位：MM"), new XYConstraints(280, 20, 100, 20));

		this.add(new JLabel("右缩进"), new XYConstraints(80, 60, 60, 20));
		this.add(endindent, new XYConstraints(160, 60, 100, 20));

		this.add(new JLabel("对齐方式"), new XYConstraints(80, 100, 60, 20));
		this.add(textalign, new XYConstraints(160, 100, 100, 20));

		this.add(new JLabel("尾行对齐方式"), new XYConstraints(80, 140, 80, 20));
		this.add(textalignlast, new XYConstraints(160, 140, 100, 20));

		this.add(new JLabel("首行缩进"), new XYConstraints(80, 180, 60, 20));
		this.add(textindent, new XYConstraints(160, 180, 100, 20));

		this.add(new JLabel("前页孤行数"), new XYConstraints(80, 220, 80, 20));
		this.add(widows, new XYConstraints(160, 220, 100, 20));

		this.add(new JLabel("后页孤行数"), new XYConstraints(80, 260, 80, 20));
		this.add(orphans, new XYConstraints(160, 260, 100, 20));

		this.add(new JLabel("行高"), new XYConstraints(80, 300, 60, 20));
		this.add(lineheight, new XYConstraints(160, 300, 100, 20));

		this.add(new JLabel("行空"), new XYConstraints(80, 340, 60, 20));
		this.add(linespace, new XYConstraints(160, 340, 100, 20));

		this.add(new JLabel("词间空"), new XYConstraints(80, 380, 60, 20));
		this.add(wordsapce, new XYConstraints(160, 380, 100, 20));

		this.add(new JLabel("字符间空"), new XYConstraints(80, 420, 60, 20));
		this.add(letterspace, new XYConstraints(160, 420, 100, 20));

		linefeed.setBackground(getBackground());
		this.add(linefeed, new XYConstraints(100, 460, 100, 20));

		this.add(other, new XYConstraints(160, 520, 100, 20));
	}
}
