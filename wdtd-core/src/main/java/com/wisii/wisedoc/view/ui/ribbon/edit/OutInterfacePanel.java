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
/**
 * @OutInterfacePanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import com.wisii.wisedoc.document.attribute.transform.OutInterface;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-21
 */
public class OutInterfacePanel extends JPanel
 {

	JLabel classnamelabel = new JLabel(
			RibbonUIText.EDIT_DATASOURCE_OUTINTERFACE_CLASSNAME);

	WiseTextField classnamecom = new WiseTextField(30);
	JLabel columncountlabel = new JLabel(
			RibbonUIText.EDIT_DATASOURCE_OUTINTERFACE_COLUMNCOUNT);
	WiseSpinner columncountspiner = new WiseSpinner(new SpinnerNumberModel(2,
			2, 100, 1));

	public OutInterfacePanel(OutInterface outinterface) {
		setLayout(new FlowLayout(FlowLayout.LEADING));
		add(classnamelabel);
		add(classnamecom);
		add(columncountlabel);
		add(columncountspiner);
		if (outinterface != null) {
			classnamecom.setText(outinterface.getClassname());
			columncountspiner.initValue(outinterface.getCloumncount());
		}
	}

	public boolean isAllSettingRight() {
		String classname = (String) classnamecom.getText();
		if (classname == null || classname.trim().isEmpty()) {
			classnamecom.requestFocus();
			return false;
		}
		return true;
	}

	public OutInterface getDataSource() {
		String classname = (String) classnamecom.getText();
		if (classname == null) {
			return null;
		}
		classname = classname.trim();
		if (classname.isEmpty()) {
			return null;
		}
		if (!classname.endsWith(".class")) {
			classname = classname + ".class";
		}
		return new OutInterface(classname,
				(Integer) columncountspiner.getValue());

	}
}
