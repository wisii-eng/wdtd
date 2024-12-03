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
 * @OutInterfaceDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.OutInterface;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-21
 */
public class OutInterfaceDialog extends AbstractWisedocDialog
 {

	OutInterfacePanel outinterfacepanel;

	private JButton okbutton = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

	private JButton cancelbutton = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));

	public OutInterfaceDialog(AbstractWisedocDialog parent,
			OutInterface datasource) {
		super(parent, RibbonUIText.EDIT_DATASOURCE_OUTINTERFACE, true);
		outinterfacepanel = new OutInterfacePanel(datasource);
		JPanel mainpanel = new JPanel(new BorderLayout());
		mainpanel.add(outinterfacepanel, BorderLayout.CENTER);
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		buttonpanel.add(okbutton);
		buttonpanel.add(cancelbutton);
		mainpanel.add(buttonpanel, BorderLayout.SOUTH);
		getContentPane().add(mainpanel);
		setSize(400, 200);
		initAction();
	}

	private void initAction() {
		okbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!outinterfacepanel.isAllSettingRight()) {
					return;
				}
				result = DialogResult.OK;
				dispose();
			}
		});
		cancelbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public DataSource getDataSource() {
		return outinterfacepanel.getDataSource();
	}
}
