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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.StaticContentManeger;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class SimpleMasterDialog extends AbstractWisedocDialog
{

	Object pagemaster;

	boolean issimple;

	CardLayout psmlayout = new CardLayout();

	SimpleMainPanel simplemainpanel;

	public SimpleMasterDialog(Map<String, StaticContent> map)
	{
		super();
		StaticContentManeger.setScmap(map);
		this.setTitle(UiText.SET_SIMPLE_PAGE_SEQUENCE_PSM);
		this.setSize(700, 650);
		this.setLayout(null);
		initComponents();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	public SimpleMasterDialog(Object pagemaster, Map<String, StaticContent> map)
	{
		super();
		StaticContentManeger.setScmap(map);
		this.pagemaster = pagemaster;
		this.setTitle(UiText.SET_SIMPLE_PAGE_SEQUENCE_PSM);
		this.setSize(700, 650);
		this.setLayout(null);
		initComponents();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	private void initComponents()
	{
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setSize(680, 600);

		issimple = IsSimple();
		simplemainpanel = new SimpleMainPanel(pagemaster, issimple);

		JPanel buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 40, 5));
		JButton ok = new JButton(UiText.OK);
		JButton reset = new JButton(UiText.RESET);
		JButton cancle = new JButton(UiText.CANCLE);
		buttonpanel.add(ok);
		buttonpanel.add(reset);
		buttonpanel.add(cancle);
		mainpanel.add(buttonpanel, BorderLayout.SOUTH);
		mainpanel.add(simplemainpanel, BorderLayout.CENTER);
		ok.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.OK;
				setMaster();
				SimpleMasterDialog.this.dispose();
			}
		});
		reset.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setInitialState();
			}
		});
		cancle.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				SimpleMasterDialog.this.dispose();
			}
		});

		this.add(mainpanel);
	}

	public void setMaster()
	{
		simplemainpanel.setMaster();
		pagemaster = simplemainpanel.getSimplePageMaster();
	}

	public Object getMaster()
	{
		return pagemaster;
	}

	public void setInitialState()
	{
		simplemainpanel.setInitialState();
	}

	public boolean IsSimple()
	{
		boolean flg = false;
		if (pagemaster == null)
		{
			flg = true;
		} else
		{
			if (pagemaster instanceof SimplePageMaster)
			{
				flg = true;
			} else if (pagemaster instanceof PageSequenceMaster)
			{
				PageSequenceMaster currentpsm = (PageSequenceMaster) pagemaster;
				List<SubSequenceSpecifier> subss = currentpsm
						.getSubsequenceSpecifiers();
				if (subss != null && !subss.isEmpty() && subss.size() == 1)
				{
					SubSequenceSpecifier onlyone = subss.get(0);
					if (onlyone instanceof RepeatablePageMasterAlternatives)
					{
						flg = true;
					}
				}
			}
		}
		return flg;
	}

	public DialogResult showDialog()
	{
		return result;
	}

}
