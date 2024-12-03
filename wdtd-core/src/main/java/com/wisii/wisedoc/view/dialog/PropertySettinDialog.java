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
 * @PropertySettinDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.dialog;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.Border;

import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

/**
 * 类功能描述：属性设置用对话框。
 * 
 * 作者：李晓光 创建日期：2009-3-19
 */
@SuppressWarnings("serial")
class PropertySettinDialog extends AbstractWisedocDialog {
	public PropertySettinDialog(JDialog parent){
		super(parent, TITLE, true);
		initDialog();
	}
	private void initDialog(){
		setSize(400, 300);
		setLayout(new BorderLayout());
		btnOK.addActionListener(listener);
		btnCancel.addActionListener(listener);
		btnPanel.add(btnOK);
		btnPanel.add(btnCancel);
		setOkButton(btnOK);
		setCancelButton(btnCancel);
		btnPanel.setBorder(ROUND_BORDER);
		add(btnPanel, BorderLayout.SOUTH);
	}
	public void setMainPanel(JComponent comp){
		JComponent c = comp;
		if(!(comp instanceof JViewport))
			c = new JScrollPane(c);
		add(c, BorderLayout.CENTER);
		if(c instanceof PropertyPanelInterface){
			propertyPanel = (PropertyPanelInterface)c;
		}
	}

	/* 定义确认按钮 */
	private JButton btnOK = new JButton(TEXT_OK_BUTTON);
	/* 定义撤销按钮 */
	private JButton btnCancel = new JButton(TEXT_CANCEL_BUTTON);
	/* 定义对话标题 */
	private JComponent btnPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
	/* 定义Panel的边框 */
	private Border ROUND_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	PropertyPanelInterface propertyPanel = null;
	/* 定义放置确认、取消按钮的Panel */
	private final static String TITLE = "属性设置";
	/* 定义按钮监听 */
	private ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Component comp = (Component) e.getSource();
			if (comp == btnOK){
				if(!isNull(propertyPanel) && !propertyPanel.isValidate())
					return;
				setResult(DialogResult.OK);
			}
			
			PropertySettinDialog.this.dispose();
		}
	};
}
