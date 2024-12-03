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
 * @DataSourceSettingDialog.java
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

import com.wisii.wisedoc.document.attribute.transform.PopupBrowserInfo;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;

/**
 * 类功能描述：下拉列表数据设置对话框
 * 
 * 作者：zhangqiang 创建日期：2009-8-29
 */
public class PopupBrowserSettingDialog extends AbstractWisedocDialog
{

	PopupBrowserSettingPanel popupBrowserpanel;
	private JButton okbutton = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));
	private JButton cancelbutton = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));
	private PopupBrowserInfo popupbrowserinfo;

	public PopupBrowserSettingDialog(PopupBrowserInfo popupbrowserinfo)
	{
		this.popupbrowserinfo = popupbrowserinfo;
		JPanel mainpanel = new JPanel(new BorderLayout());
		popupBrowserpanel = new PopupBrowserSettingPanel(this, popupbrowserinfo);
		mainpanel.add(popupBrowserpanel, BorderLayout.CENTER);
		JPanel okpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		okpanel.add(okbutton);
		okpanel.add(cancelbutton);
		mainpanel.add(BorderLayout.SOUTH, okpanel);
		getContentPane().add(mainpanel);
		setSize(600, 400);
		setTitle("网页回填数据设置");
		initActions();
	}

	private void initActions()
	{
		okbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!popupBrowserpanel.isAllSettingRight())
				{
					return;
				}
				popupbrowserinfo = popupBrowserpanel.getPopupBrowserInFo();
				result = DialogResult.OK;
				dispose();
			}
		});
		cancelbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
	}

	public PopupBrowserInfo getPopupBrowserInFo()
	{
		return popupbrowserinfo;
	}
}
