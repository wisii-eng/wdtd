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
package com.wisii.wisedoc.view.ui.parts.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.l2fprod.common.demo.ButtonBarMain;
import com.l2fprod.common.swing.JButtonBar;
import com.l2fprod.common.swing.plaf.blue.BlueishButtonBarUI;
import com.l2fprod.common.util.ResourceManager;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.parts.pagelayout.RBMPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.RegionAfterDiaPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.RegionBeforeDiaPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.RegionEndDiaPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.RegionStartDiaPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.SimplePageMasterPropertyPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.UpdateSPMProperty;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 页布局详细属性设置面板
 * @author 闫舒寰
 * @version 1.0 2009/01/20
 */
public class SimplePageLayoutPanel extends JPanel {
	
	static ResourceManager RESOURCE = ResourceManager.get(ButtonBarMain.class);

	public SimplePageLayoutPanel() {
		setLayout(new BorderLayout());
		JButtonBar toolbar = new JButtonBar(JButtonBar.VERTICAL);
		toolbar.setUI(new BlueishButtonBarUI());
		this.add(new ButtonBarPanel(toolbar), BorderLayout.CENTER);
	}

	static class ButtonBarPanel extends JPanel {

		private Component currentComponent;

		public ButtonBarPanel(JButtonBar toolbar) {
			setLayout(new BorderLayout());

			add("West", toolbar);

			ButtonGroup group = new ButtonGroup();

			addButton(UiText.PAGE_LAYOUT_TITLE, "03705.ico", new SimplePageMasterPropertyPanel(), toolbar, group);
			addButton(UiText.PAGE_BODY_TITLE, "03466.ico", new RBMPanel(), toolbar, group);
			addButton(UiText.PAGE_HEADER_TITLE, "09442.ico", /*new HeaderDiaPanel()*/new RegionBeforeDiaPanel(), toolbar, group);
			addButton(UiText.PAGE_FOOTER_TITLE, "09443.ico", /*new FooterDiaPanel()*/new RegionAfterDiaPanel(), toolbar, group);
			addButton(UiText.PAGE_START_TITLE, "09443.ico", /*new LeftRegionDiaPanel()*/new RegionStartDiaPanel(), toolbar, group);
			addButton(UiText.PAGE_END_TITLE, "09443.ico", /*new RightRegionDiaPanel()*/new RegionEndDiaPanel(), toolbar, group);
		}


		private void show(Component component) {
			if (currentComponent != null) {
				remove(currentComponent);
			}
			add("Center", currentComponent = component);
			
			if (component instanceof UpdateSPMProperty) {
				UpdateSPMProperty temp = (UpdateSPMProperty) component;
				temp.update();
			}
			
			revalidate();
			repaint();
		}

		@SuppressWarnings("serial")
		private void addButton(String title, String iconUrl,
				final Component component, JButtonBar bar, ButtonGroup group) {
			Action action = new AbstractAction(title, MediaResource.getResizableIcon(iconUrl)) {
				public void actionPerformed(ActionEvent e) {
					show(component);
				}
			};

			JToggleButton button = new JToggleButton(action);
			bar.add(button);

			group.add(button);

			if (group.getSelection() == null) {
				button.setSelected(true);
				show(component);
			}
		}
	}
}
