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
 * @DateTimeSetDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.dateformat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-6-29
 */
public class DateTimeSetDialog extends AbstractWisedocDialog
{
	private DateTimeSetPanel setpanel;
	private JButton okbutton = new JButton(MessageResource.
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));
	private JButton cancelbutton = new JButton(MessageResource.
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));
	private WisedocDateTimeFormat olddatetimeformat;
	private WisedocDateTimeFormat datetimeformat;

	DateTimeSetDialog(WisedocDateTimeFormat datetimeformat,boolean isinput)
	{
		super(SystemManager.getMainframe(), "格式设置", true);
		setSize(600, 400);
		setpanel = new DateTimeSetPanel(datetimeformat,isinput);
		JPanel mainpanel = new JPanel(new BorderLayout());
		mainpanel.add(setpanel, BorderLayout.CENTER);
		JPanel okPanel = new JPanel();
		okPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 0));
		okPanel.add(okbutton);
		okPanel.add(cancelbutton);
		mainpanel.add(okPanel, BorderLayout.SOUTH);
		getContentPane().add(mainpanel);
		setOkButton(okbutton);
		setCancelButton(cancelbutton);
		initAction();
	}

	private void initAction()
	{
		cancelbutton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		okbutton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setpanel.stopEdit();
				result = DialogResult.OK;
				datetimeformat = setpanel.getDateTimeFormat();
				if (datetimeformat.equals(olddatetimeformat))
				{
					datetimeformat = null;
				}
				dispose();
			}
		});
	}

	/**
	 * @返回 datetimeformat变量的值
	 */
	public final WisedocDateTimeFormat getDatetimeformat()
	{
		return datetimeformat;
	}
}
