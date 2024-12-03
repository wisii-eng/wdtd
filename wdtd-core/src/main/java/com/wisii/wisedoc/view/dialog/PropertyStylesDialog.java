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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

/**
 * 类功能描述：属性设置用对话框。
 * 
 * 作者：李晓光 创建日期：2009-3-19
 */
@SuppressWarnings("serial")
class PropertyStylesDialog extends AbstractWisedocDialog {
	public PropertyStylesDialog(final JDialog parent, final Map<Integer, Object> map){
		super(parent, TITLE, true);
		initDialog();
	}
	
	/**
	 * 获得在当前对话框中设置的样式集合【Map】
	 * @return	{@link Map}		返回当前设置的样式集合
	 */
	public Map<Integer, Object> getStyles(){
		if(isNull(propertyPanel)) {
			return null;
		}
		return propertyPanel.getSetting();
	}
	
	public void setMainPanel(final JComponent comp){
		final JPanel mainPanel = new JPanel(new BorderLayout());
		final Dimension dim = comp.getPreferredSize();
		setSize(dim);
		mainPanel.add(comp, BorderLayout.CENTER);
		add(mainPanel, BorderLayout.CENTER);
		if(comp instanceof PropertyPanelInterface){
			propertyPanel = (PropertyPanelInterface)comp;
		}
	}
	
	private void initDialog(){
		setPreferredSize(new Dimension(600, 450));
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
	/* 定义确认按钮 */
	private final JButton btnOK = new JButton(TEXT_OK_BUTTON);
	/* 定义撤销按钮 */
	private final JButton btnCancel = new JButton(TEXT_CANCEL_BUTTON);
	/* 定义对话标题 */
	private final JComponent btnPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
	/* 定义Panel的边框 */
	private final Border ROUND_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	PropertyPanelInterface propertyPanel = null;
	/* 定义放置确认、取消按钮的Panel */
	private final static String TITLE = "属性设置";
	/* 定义按钮监听 */
	private final ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
			final Component comp = (Component) e.getSource();
			if (comp == btnOK){
				if(isNull(propertyPanel)/* && !propertyPanel.isValidate()*/) {
					return;
				}
				setResult(DialogResult.OK);
			}
			
			PropertyStylesDialog.this.dispose();
		}
	};
}
