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

package com.wisii.wisedoc.view.ui.ribbon.pagetask;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;

public class PageSequenceMasterPanel extends JPanel
{

	public PageSequenceMasterPanel()
	{
		super();
		this.setLayout(new GridLayout(2, 2));
		JCheckBox first = new JCheckBox("首页不同");
		RibbonUIManager.getInstance().bind(Page.ADD_FIRST_LAYOUT_ACTION, first);

		JCheckBox last = new JCheckBox("尾页不同");
		RibbonUIManager.getInstance().bind(Page.ADD_LAST_LAYOUT_ACTION, last);

		JCheckBox oddandeven = new JCheckBox("奇偶不同");
		RibbonUIManager.getInstance().bind(Page.ADD_ODD_AND_EVEN_LAYOUT_ACTION,
				oddandeven);

		JCheckBox all = new JCheckBox("应用于所有章节");
		RibbonUIManager.getInstance().bind(Page.ADD_ALL_PAGE_SEQUENCE_ACTION,
				all);

		this.add(first);
		this.add(oddandeven);
		this.add(last);
		this.add(all);
	}
}
