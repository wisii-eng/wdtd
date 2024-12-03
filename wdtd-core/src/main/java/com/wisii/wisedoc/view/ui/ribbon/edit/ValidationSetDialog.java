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
 * @ValidationSetDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.attribute.edit.Validation;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：验证设置对话框
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-17
 */
public class ValidationSetDialog extends AbstractWisedocDialog
{
	private ValidationSetPanel validationsetpanel;
	/* 确定 按钮 */
	private JButton btnOK = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

	/* 取消 按钮 */
	private JButton btnCancel = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));
	private Validation validation;

	public ValidationSetDialog(Validation validation)
	{
		super(SystemManager.getMainframe(), RibbonUIText.VALIDATION, true);
		validationsetpanel = new ValidationSetPanel(validation);
		Container mainpanel = getContentPane();
		mainpanel.add(validationsetpanel, BorderLayout.CENTER);
		JPanel buttonspanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		buttonspanel.add(btnOK);
		buttonspanel.add(btnCancel);
		mainpanel.add(buttonspanel, BorderLayout.SOUTH);
		initActions();
		setPreferredSize(new Dimension(600, 400));
		pack();
	}

	private void initActions()
	{
		btnOK.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (validationsetpanel.ValidateSet())
				{
					validation = validationsetpanel.getValidation();
					distroy(DialogResult.OK);
				}

			}

		});
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				distroy(DialogResult.Cancel);
			}
		});

	}

	/**
	 * 
	 * 设置窗体返回状态，关闭窗口，释放窗体资源。
	 * 
	 * @param result
	 *            指定窗体的返回状态
	 * @return void 无
	 */
	private void distroy(DialogResult result)
	{
		this.result = result;
		setVisible(false);
		dispose();
	}

	/**
	 * @返回 validation变量的值
	 */
	public final Validation getValidation()
	{
		return validation;
	}
}
