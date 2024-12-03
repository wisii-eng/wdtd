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

package com.wisii.wisedoc.view.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class TreatGroupDialog extends AbstractWisedocDialog
{

	private GroupUI groupui = null;

	JLabel label = new JLabel(RibbonUIText.CURRENT_GROUP_UI);

	WiseCombobox groupbox = new WiseCombobox();

	JButton setGroup = new JButton("设置选择框组");

	public TreatGroupDialog(GroupUI group)
	{
		super();
		groupui = group;
		this.setTitle(RibbonUIText.CHECKBOX_BUTTONGROUP);
		this.setSize(400, 300);
		this.setLayout(null);
		initComponents();
	}

	private void initComponents()
	{
		final JPanel panel = new JPanel();
		panel.setSize(400, 300);
		panel.setLayout(null);
		label.setBounds(50, 5, 80, 25);
		setGroupbox();
		groupbox.setBounds(130, 5, 120, 25);

		groupbox.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				String item = (String) groupbox.getSelectedItem();
				if (item != null)
				{
					if (item.equals("无"))
					{
						setGroup.setEnabled(false);
					} else
					{
						groupui = (GroupUI) ((WiseDocDocument)SystemManager.getCurruentDocument()).getGroupUI(item);
						setGroup.setEnabled(true);
					}
				}
			}
		});

		setGroup.setBounds(100, 50, 120, 25);
		setGroup.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				SetGroupUIDialog dialog = new SetGroupUIDialog(groupui);
				DialogResult result = dialog.showDialog();
				if (result == DialogResult.OK)
				{
					groupui = dialog.getGroupui();
					setGroupbox();
				}
			}
		});
		JButton ok = new JButton(UiText.OK);
		JButton cancle = new JButton(UiText.CANCLE);
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				setRealGroup();
				result = DialogResult.OK;
				TreatGroupDialog.this.dispose();
			}
		});
		cancle.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				TreatGroupDialog.this.dispose();
			}
		});
		ok.setBounds(150, 230, 80, 25);
		cancle.setBounds(250, 230, 80, 25);

		panel.add(label);
		panel.add(groupbox);
		panel.add(setGroup);
		panel.add(ok);
		panel.add(cancle);
		this.add(panel);
	}

	public void setGroupbox()
	{
		groupbox.removeAllItems();
		groupbox.addItem("无");
		WiseDocDocument doc = (WiseDocDocument) SystemManager.getCurruentDocument();
		List<String> names = doc.getGroupNames();
		if (!names.isEmpty())
		{
			for (String current : names)
			{
				groupbox.addItem(current);
			}
		}
		if (groupui != null)
		{
			groupbox.setSelectedItem(groupui.getName());
		} else
		{
			groupbox.setSelectedItem("无");
			setGroup.setEnabled(false);
		}
	}

	public void setGroup(GroupUI group)
	{
		groupui = group;
	}

	public void setRealGroup()
	{
		String current = (String) groupbox.getSelectedItem();
		if (current.equals("无"))
		{
			groupui = null;
		} else
		{
			groupui = (GroupUI) ((WiseDocDocument)SystemManager.getCurruentDocument()).getGroupUI(current);
		}
	}

	public static void main(String[] args)
	{
		SetGroupUIDialog dia = new SetGroupUIDialog();
		dia.setVisible(true);
	}

	public DialogResult showDialog()
	{
		DialogSupport.centerOnScreen(this);
		this.setVisible(true);
		return result;
	}

	public GroupUI getGroupui()
	{
		return groupui;
	}
}
