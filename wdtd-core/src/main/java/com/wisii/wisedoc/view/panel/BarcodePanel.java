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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import com.borland.jbcl.layout.XYConstraints;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.component.WiseTextField;

@SuppressWarnings("serial")
public class BarcodePanel extends OnlyLayoutPanel
{

	String code39 = "CODE39";

	String code128 = "CODE128";

	String ean128 = "EAN128";

	String code2of5 = "2OF5";

	String ean_13 = "EAN-13";

	String ean_8 = "EAN-8";

	String upca = "UPCA";

	String upce = "UPCE";

	WiseSpinner height = new WiseSpinner();
	{
		SpinnerNumberModel data = new SpinnerNumberModel(12.7, 5.0, 100, 0.1);
		height.setModel(data);
	}

	WiseSpinner module = new WiseSpinner();
	{
		SpinnerNumberModel data = new SpinnerNumberModel(0.305, 0.25, 0.43,
				0.005);
		module.setModel(data);
	}

	JButton font = new JButton("字体设置");

	WiseTextField quiet_horizontal = new WiseTextField("0.0");

	WiseTextField quiet_vertical = new WiseTextField("0.0");

	WiseSpinner orientation = new WiseSpinner();
	{
		SpinnerNumberModel data = new SpinnerNumberModel(0, -270, 270, 90);
		orientation.setModel(data);
	}

	JCheckBox addCheckFlg = new JCheckBox("添加校验码", true);

	WiseTextField wide_to_narrow = new WiseTextField("0.0");

	WiseTextField text_char_space = new WiseTextField("0.0");

	WiseTextField text_block = new WiseTextField("0.0");

	WiseCombobox codeType = new WiseCombobox();
	{
		codeType.addItem(code39);
		codeType.addItem(code128);
		codeType.addItem(ean128);
		codeType.addItem(code2of5);
		codeType.addItem(ean_13);
		codeType.addItem(ean_8);
		codeType.addItem(upca);
		codeType.addItem(upce);
		codeType.setSelectedItem(code39);
	}

	JCheckBox printText = new JCheckBox("打印供人识别字符", true);

	WiseTextField text = new WiseTextField("");

	public BarcodePanel()
	{
		super();
		this.add(new JLabel("条码的条的高度"), new XYConstraints(60, 20, 100, 20));
		this.add(height, new XYConstraints(160, 20, 80, 20));

		this.add(new JLabel("基本单元的宽度"), new XYConstraints(60, 60, 100, 20));
		this.add(module, new XYConstraints(160, 60, 80, 20));

		this.add(font, new XYConstraints(80, 100, 100, 20));

		this.add(new JLabel("条码左右两端外侧空白区大小"), new XYConstraints(60, 140, 180,
				20));
		this.add(quiet_horizontal, new XYConstraints(240, 140, 80, 20));

		this.add(new JLabel("条码字符上下外侧空白区大小"), new XYConstraints(60, 180, 180,
				20));
		this.add(quiet_vertical, new XYConstraints(240, 180, 80, 20));

		this.add(new JLabel("条码方向"), new XYConstraints(80, 220, 80, 20));
		this.add(orientation, new XYConstraints(160, 220, 80, 20));

		this.add(addCheckFlg, new XYConstraints(100, 260, 150, 20));

		this.add(new JLabel("宽窄比"), new XYConstraints(80, 300, 80, 20));
		this.add(wide_to_narrow, new XYConstraints(160, 300, 80, 20));

		this.add(new JLabel("供人识别字符间距"), new XYConstraints(60, 340, 150, 20));
		this.add(text_char_space, new XYConstraints(210, 340, 80, 20));

		this.add(new JLabel("条码字符与供人识别字符间隔"), new XYConstraints(60, 380, 180,
				20));
		this.add(text_block, new XYConstraints(240, 380, 80, 20));

		this.add(new JLabel("条码类型"), new XYConstraints(80, 420, 80, 20));
		this.add(codeType, new XYConstraints(160, 420, 80, 20));

		this.add(printText, new XYConstraints(100, 460, 150, 20));

		this.add(new JLabel("供人识别字符"), new XYConstraints(80, 500, 80, 20));
		this.add(codeType, new XYConstraints(160, 500, 80, 20));

	}
}
