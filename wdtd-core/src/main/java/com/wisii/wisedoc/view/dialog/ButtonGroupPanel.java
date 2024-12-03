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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.edit.Button;
import com.wisii.wisedoc.document.attribute.edit.ButtonGroup;

@SuppressWarnings("serial")
public class ButtonGroupPanel extends JPanel
{

	ButtonGroup buttongroup;

	JCheckBox addbefore = new JCheckBox("添加按钮（前插）");

	JCheckBox addafter = new JCheckBox("添加按钮（后插）");

	JCheckBox delete = new JCheckBox("删除按钮");
	
	JTextField addbeforeauty = new JTextField();

	JTextField addafterauty = new JTextField();

	JTextField deleteauty = new JTextField();

	JLabel autyaddbf = new JLabel("权限");
	JLabel autyaddaf = new JLabel("权限");
	JLabel autydel = new JLabel("权限");
	
	List<Button> buttonslist = new ArrayList<Button>();
	
	BindNode bindNode;
	ButtonNoDataNode btnNoDataNode;

	public ButtonGroupPanel(ButtonGroup current, int index,ButtonNoDataNode btnNoDataNode)
	{
		super();
		this.btnNoDataNode = btnNoDataNode;
		buttongroup = current;
		this.setLayout(null);
		int x = (index % 2) * 300;
		int y = (index / 2) * 120 + 5;
		this.setBounds(x, y, 300, 120);
		String name = ((Group) buttongroup.getCellment().getAttribute(
				Constants.PR_GROUP)).getNode().getXPath();

		addbeforeauty.setEnabled(false);
		addafterauty.setEnabled(false);
		deleteauty.setEnabled(false);
		List<Button> buttons = buttongroup.getButtons();
		if (buttons != null)
		{
			for (Button currentbutton : buttons)
			{
				String type = currentbutton.getType();
				if (type.equals("delete"))
				{
					delete.setSelected(true);
					deleteauty.setEnabled(true);
					deleteauty.setText(currentbutton.getAuty());
				} else if (type.equals("addbefore"))
				{
					addbefore.setSelected(true);
					addbeforeauty.setEnabled(true);
					addbeforeauty.setText(currentbutton.getAuty());
				} else if (type.equals("addafter"))
				{
					addafter.setSelected(true);
					addafterauty.setEnabled(true);
					addafterauty.setText(currentbutton.getAuty());
				}
			}
		}
		addbefore.setBounds(5, 20, 130, 25);
		addafter.setBounds(5, 50, 130, 25);
		delete.setBounds(5, 80, 130, 25);
		this.add(addbefore);
		this.add(addafter);
		this.add(delete);
		autyaddbf.setBounds(140, 20, 30, 25);
		autyaddaf.setBounds(140, 50, 30, 25);
		autydel.setBounds(140, 80, 30, 25);
		this.add(autyaddbf);
		this.add(autyaddaf);
		this.add(autydel);
		addbeforeauty.setBounds(170, 20, 100, 25);
		addafterauty.setBounds(170, 50, 100, 25);
		deleteauty.setBounds(170, 80, 100, 25);
		this.add(addbeforeauty);
		this.add(addafterauty);
		this.add(deleteauty);
		addbefore.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (addbefore.isSelected())
				{
					addbeforeauty.setEnabled(true);
				} else
				{
					addbeforeauty.setEnabled(false);
				}
			}
		});
		addafter.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (addafter.isSelected())
				{
					addafterauty.setEnabled(true);
				} else
				{
					addafterauty.setEnabled(false);
				}
			}
		});
		delete.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (delete.isSelected())
				{
					deleteauty.setEnabled(true);
				} else
				{
					deleteauty.setEnabled(false);
				}
			}
		});

		
		this.setBorder(new TitledBorder(new LineBorder(Color.GRAY), name
				+ "按钮组", TitledBorder.CENTER, TitledBorder.TOP));
	}

	public ButtonGroup getButtonGroup()
	{
		buttonslist.clear();
		if (addbefore.isSelected())
		{
			String auty = addbeforeauty.getText() != null ? addbeforeauty
					.getText() : "";
			setStatic("addbefore", auty,btnNoDataNode);
		}
		if (addafter.isSelected())
		{
			String auty = addafterauty.getText() != null ? addafterauty
					.getText() : "";
			setStatic("addafter", auty,btnNoDataNode);
		}
		if (delete.isSelected())
		{
			String auty = deleteauty.getText() != null ? deleteauty.getText()
					: "";
			setStatic("delete", auty,btnNoDataNode);
		}
		buttongroup.setButtons(buttonslist);
		if (buttonslist.isEmpty())
		{
			return null;
		}
		return buttongroup;
	}

	public void setStatic(String type, String auty,ButtonNoDataNode dataContent)
	{
		Button current = new Button(type);
		current.setAuty(auty);
		current.setDataContent(dataContent);
		buttonslist.add(current);
	}
}
