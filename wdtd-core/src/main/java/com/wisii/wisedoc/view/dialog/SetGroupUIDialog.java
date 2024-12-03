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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.ribbon.edit.ConnectWithDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class SetGroupUIDialog extends AbstractWisedocDialog
{

	int position = -1;

	private GroupUI groupui = null;

	JTextField name = new JTextField();

	WiseSpinner maxnumber = new WiseSpinner();

	WiseSpinner minnumber = new WiseSpinner();

	JTextField nonvalue = new JTextField();

	ConnWith connwith;

	public SetGroupUIDialog()
	{
		super();
		groupui = null;
		this.setTitle(RibbonUIText.CHECKBOX_BUTTONGROUP_CREATE_DIALOG);
		this.setSize(400, 300);
		this.setLayout(null);
		initComponents();
	}

	public SetGroupUIDialog(GroupUI group)
	{
		super();
		setGroup(group);
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
		JLabel namelabel = new JLabel("名称：");
		namelabel.setBounds(50, 5, 100, 25);

		String namestr = groupui != null ? groupui.getName() : "";
		Map<Integer, Object> map = groupui != null ? groupui.getAttr()
				: new HashMap<Integer, Object>();
		name.setText(namestr);
		name.setBounds(150, 5, 100, 25);

		JLabel maxnumberlabel = new JLabel("最大选择个数：");
		maxnumberlabel.setBounds(50, 50, 100, 25);

		int number = 100;

		maxnumber.setBounds(150, 50, 100, 25);
		SpinnerNumberModel maxModel = new SpinnerNumberModel(1, 0, number, 1);
		maxnumber.setModel(maxModel);
		if (map != null)
		{
			Integer max = (Integer) map
					.get(Constants.PR_GROUP_MAX_SELECTNUMBER);
			if (max != null)
			{
				maxnumber.initValue(max);
			}
		}
		JLabel minnumberlabel = new JLabel("最小选择个数：");
		minnumberlabel.setBounds(50, 95, 100, 25);

		minnumber.setBounds(150, 95, 100, 25);
		SpinnerNumberModel minModel = new SpinnerNumberModel(1, 0, number, 1);
		minnumber.setModel(minModel);
		if (map != null)
		{
			Integer min = (Integer) map
					.get(Constants.PR_GROUP_MIN_SELECTNUMBER);
			if (min != null)
			{
				minnumber.initValue(min);
			}
		}
		JLabel nonvaluelabel = new JLabel("全不选时值：");
		nonvaluelabel.setBounds(50, 140, 100, 25);

		String nonvaluestr = (String) map
				.get(Constants.PR_GROUP_NONE_SELECT_VALUE);
		if (nonvaluestr != null)
		{
			nonvalue.setText(nonvaluestr);
		}
		nonvalue.setBounds(150, 140, 100, 25);

		JButton setconnwith = new JButton("设置关联");

		setconnwith.setBounds(100, 185, 100, 25);

		setconnwith.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				ConnectWithDialog dialog = new ConnectWithDialog(connwith);
				DialogResult result = dialog.showDialog();
				if (result == DialogResult.OK)
				{
					connwith = dialog.getConwith();
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
				boolean flg = setGroupui();
				if (flg)
				{
					result = DialogResult.OK;
					SetGroupUIDialog.this.dispose();
				}
			}
		});
		cancle.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				SetGroupUIDialog.this.dispose();
			}
		});
		ok.setBounds(150, 230, 80, 25);
		cancle.setBounds(250, 230, 80, 25);
		panel.add(namelabel);
		panel.add(name);
		panel.add(maxnumberlabel);
		panel.add(maxnumber);
		panel.add(minnumberlabel);
		panel.add(minnumber);
		panel.add(nonvaluelabel);
		panel.add(nonvalue);
		panel.add(nonvaluelabel);
		panel.add(setconnwith);
		panel.add(ok);
		panel.add(cancle);
		this.add(panel);
	}

	public void setGroup(GroupUI group)
	{
		groupui = group;
		if (group != null)
		{
			position = ((WiseDocDocument)SystemManager.getCurruentDocument()).getGroupUIPosition(group);
			connwith = groupui.getConnwith();
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
		setVisible(true);
		return result;
	}

	public GroupUI getGroupui()
	{
		return groupui;
	}

	public boolean setGroupui()
	{
		boolean flg = true;

		String groupname = name.getText();
		if (groupname == null || groupname.trim().equals(""))
		{
			name.requestFocus();
			return false;
		} else
		{
			List<String> names = ((WiseDocDocument)SystemManager.getCurruentDocument()).getGroupNames();
			if (position == -1)
			{
				for (String current : names)
				{
					if (current.equals(groupname.trim()))
					{
						name.requestFocus();
						return false;
					}
				}
			} else
			{
				for (int i = 0; i < names.size(); i++)
				{
					String current = names.get(i);
					if (i != position)
					{
						if (current.equals(groupname.trim()))
						{
							name.requestFocus();
							return false;
						}
					}
				}
			}
		}
		int max = (Integer) maxnumber.getValue();
		int min = (Integer) minnumber.getValue();
		if (max == 0)
		{
			if (min > max)
			{
				maxnumber.requestFocus();
				return false;
			}
		} else
		{
			if (min > max)
			{
				minnumber.requestFocus();
				return false;
			}
		}
		String groupnonvalue = nonvalue.getText();
		if (groupnonvalue == null || groupnonvalue.trim().equals(""))
		{
			nonvalue.requestFocus();
			return false;
		}
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(Constants.PR_GROUP_MAX_SELECTNUMBER, max);
		map.put(Constants.PR_GROUP_MIN_SELECTNUMBER, min);
		map.put(Constants.PR_GROUP_NONE_SELECT_VALUE, groupnonvalue);
		if (groupui == null)
		{
			groupui = new GroupUI(new EnumProperty(Constants.EN_GROUP, ""), map);
		} else
		{
			groupui.setAttr(map);
		}
		groupui.setConnwith(connwith);
		// map.put(Constants.PR_CONN_WITH, groupui.getConnwith());
		groupui.setName(groupname);
		return flg;
	}
}
