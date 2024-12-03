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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.jvnet.flamingo.common.icon.ResizableIcon;

import com.l2fprod.common.swing.JButtonBar;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.model.CustomizeSimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class SetPageLayoutPanel extends JPanel
{

	CustomizeSimplePageMasterModel simplepagemaster;

	JButtonBar toolbar = new JButtonBar(JButtonBar.VERTICAL);

	ButtonGroup group = new ButtonGroup();

	JToggleButton page = new LayoutButton(UiText.PAGE_LAYOUT_TITLE,
			MediaResource.getResizableIcon("03705.ico"), true);

	JToggleButton body = new LayoutButton(UiText.PAGE_BODY_TITLE, MediaResource
			.getResizableIcon("03466.ico"), true);

	JToggleButton before = new LayoutButton(UiText.PAGE_HEADER_TITLE,
			MediaResource.getResizableIcon("09442.ico"), true);

	JToggleButton after = new LayoutButton(UiText.PAGE_FOOTER_TITLE,
			MediaResource.getResizableIcon("09443.ico"), true);

	JToggleButton start = new LayoutButton(UiText.PAGE_START_TITLE,
			MediaResource.getResizableIcon("09443.ico"), true);

	JToggleButton end = new LayoutButton(UiText.PAGE_END_TITLE, MediaResource
			.getResizableIcon("09443.ico"), true);

	CardLayout cardlayout = new CardLayout();

	PagePanel pagepanel;

	BodyPanel bodypanel;

	BeforePanel beforepanel;

	AfterPanel afterpanel;

	StartPanel startpanel;

	EndPanel endpanel;

	JPanel layoutpanel = new JPanel();

	public SetPageLayoutPanel()
	{
		super();
		initComponents();
	}

	public SetPageLayoutPanel(CustomizeSimplePageMasterModel pagemaster)
	{
		super();
		simplepagemaster = pagemaster;
		initComponents();
	}

	private void initComponents()
	{
		this.setLayout(new BorderLayout());

		toolbar.setPreferredSize(new Dimension(100, 500));
		addButton(page);
		addButton(body);
		addButton(before);
		addButton(after);
		addButton(start);
		addButton(end);
		layoutpanel.setLayout(cardlayout);

		addPanel();
		initPanel();
		this.add(toolbar, BorderLayout.WEST);
		this.add(layoutpanel, BorderLayout.CENTER);
		setSimplePageMaster(simplepagemaster);
	}

	public void initPanel()
	{
		pagepanel.setSimplePageMaster(simplepagemaster);
		bodypanel.setRegionModel(simplepagemaster);
		beforepanel.setRegionModel(simplepagemaster);
		afterpanel.setRegionModel(simplepagemaster);
		startpanel.setRegionModel(simplepagemaster);
		endpanel.setRegionModel(simplepagemaster);
	}

	public void addPanel()
	{
		pagepanel = new PagePanel();
		bodypanel = new BodyPanel();
		beforepanel = new BeforePanel();
		afterpanel = new AfterPanel();
		startpanel = new StartPanel();
		endpanel = new EndPanel();
		layoutpanel.add(pagepanel, UiText.PAGE_LAYOUT_TITLE);
		layoutpanel.add(bodypanel, UiText.PAGE_BODY_TITLE);
		layoutpanel.add(beforepanel, UiText.PAGE_HEADER_TITLE);
		layoutpanel.add(afterpanel, UiText.PAGE_FOOTER_TITLE);
		layoutpanel.add(startpanel, UiText.PAGE_START_TITLE);
		layoutpanel.add(endpanel, UiText.PAGE_END_TITLE);
	}

	public void addButton(JToggleButton button)
	{
		toolbar.add(button);
		group.add(button);
	}

	public void setSimplePageMaster(CustomizeSimplePageMasterModel pagemaster)
	{
		simplepagemaster = pagemaster;
		initPanel();
	}

	public SimplePageMaster getSimplePageMaster()
	{
		return simplepagemaster != null ? simplepagemaster
				.getSimplePageMaster() : null;
	}

	public SimplePageMasterModel getSimplePageMasterModel()
	{
		return simplepagemaster.getSpm();
	}

	class LayoutButton extends JToggleButton implements ActionListener
	{

		public LayoutButton(String title, ResizableIcon ico, boolean flg)
		{
			super(title, ico, flg);
			this.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String current = LayoutButton.this.getText();
			// getSimplePageMaster();
			if (UiText.PAGE_LAYOUT_TITLE.equals(current))
			{
				pagepanel.setSimplePageMaster(simplepagemaster);
			} else if (UiText.PAGE_BODY_TITLE.equals(current))
			{
				bodypanel.setRegionModel(simplepagemaster);
			} else if (UiText.PAGE_HEADER_TITLE.equals(current))
			{
				beforepanel.setRegionModel(simplepagemaster);
			} else if (UiText.PAGE_FOOTER_TITLE.equals(current))
			{
				afterpanel.setRegionModel(simplepagemaster);
			} else if (UiText.PAGE_START_TITLE.equals(current))
			{
				startpanel.setRegionModel(simplepagemaster);
			} else if (UiText.PAGE_END_TITLE.equals(current))
			{
				endpanel.setRegionModel(simplepagemaster);
			}
			cardlayout.show(layoutpanel, current);
		}
	}

}
