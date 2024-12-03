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
 * @NextDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import com.wisii.wisedoc.document.attribute.edit.Next;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：设置下拉列表的级联属性的对话框
 * 
 * 作者：zhangqiang 创建日期：2009-12-16
 */
public class NextDialog extends AbstractWisedocDialog
{
	NextPanel nextpanel;
	private JButton okbutton = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

	private JButton cancelbutton = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));

	private Next next;

	public NextDialog(Next next)
	{
		super((Frame)null, RibbonUIText.SELECT_NEXT, true);
		this.next = next;
		nextpanel = new NextPanel(next);
		JPanel mainpanel = new JPanel(new BorderLayout());
		mainpanel.add(nextpanel, BorderLayout.CENTER);
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		buttonpanel.add(okbutton);
		buttonpanel.add(cancelbutton);
		mainpanel.add(buttonpanel, BorderLayout.SOUTH);
		getContentPane().add(mainpanel);
		setSize(300, 200);
		initAction();
	}

	private void initAction()
	{
		okbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!nextpanel.isAllSettingRight())
				{
					return;
				}
				next = nextpanel.getNext();
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

	public Next getNext()
	{
		return next;
	}

	private class NextPanel extends JPanel
	{
		private JLabel namelabel = new JLabel(
				RibbonUIText.EDIT_SELECT_NEXT_NAME);
		private JLabel colunmlabel = new JLabel(
				RibbonUIText.EDIT_SELECT_NEXT_COLUMNTHIS);
		private JLabel colunmnextlabel = new JLabel(
				RibbonUIText.EDIT_SELECT_NEXT_COLUMNNEXT);
		WiseTextField nametext = new WiseTextField(10);
		WiseSpinner columnspinner = new WiseSpinner(new SpinnerNumberModel(0,
				0, Integer.MAX_VALUE, 1));
		WiseSpinner columnnextspinner = new WiseSpinner(new SpinnerNumberModel(
				0, 0, Integer.MAX_VALUE, 1));

		private NextPanel(Next next)
		{
			super(new FlowLayout(FlowLayout.LEFT,0,0));
			JPanel namepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			namepanel.add(namelabel);
			namepanel.add(nametext);
			JPanel columnpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			columnpanel.add(colunmlabel);
			columnpanel.add(columnspinner);
			JPanel columnnextpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			columnnextpanel.add(colunmnextlabel);
			columnnextpanel.add(columnnextspinner);
			if (next != null)
			{
				nametext.setText(next.getName());
				columnspinner.initValue(next.getColumn());
				columnnextspinner.initValue(next.getNextcolumn());
			}
			Dimension size = new Dimension(300,30);
			namepanel.setPreferredSize(size);
			columnpanel.setPreferredSize(size);
			columnnextpanel.setPreferredSize(size);
			add(namepanel);
			add(columnpanel);
			add(columnnextpanel);
		}

		private Next getNext()
		{
			String nextname = nametext.getText().trim();
			return new Next(nextname, (Integer) columnspinner.getValue(),
					(Integer) columnnextspinner.getValue());
		}

		private boolean isAllSettingRight()
		{
			String nextname = nametext.getText();
			if (nextname == null || nextname.trim().isEmpty())
			{
				nametext.requestFocus();
				nametext.selectAll();
				return false;

			}
			return true;
		}
	}
}
