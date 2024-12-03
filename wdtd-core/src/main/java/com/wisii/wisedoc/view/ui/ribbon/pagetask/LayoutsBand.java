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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jvnet.flamingo.common.CommandToggleButtonGroup;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.StringValuePair;
import org.jvnet.flamingo.common.icon.DecoratedResizableIcon;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.svg.transcoded.preferences_desktop_theme;

public class LayoutsBand {
	
	public JRibbonBand getLayoutsBand(){
		
		JRibbonBand layoutsBand = new JRibbonBand("当前章节页布局", MediaResource.getResizableIcon("09379.ico"), null);
		
		CommandToggleButtonGroup layoutGroup = new CommandToggleButtonGroup();
		
		JCommandToggleButton normalLayout = new JCommandToggleButton("正常页布局", MediaResource.getResizableIcon("01826.ico"));
		normalLayout.setActionRichTooltip(new RichTooltip("对齐","页面内容的对齐方式"));
//		normalLayout.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Page.ADD_SIMPLE_MASTER_LAYOUT_ACTION, normalLayout);
		layoutsBand.addCommandButton(normalLayout, RibbonElementPriority.TOP);
		
		JCommandToggleButton customLayout = new JCommandToggleButton("自定义页布局", MediaResource.getResizableIcon("01826.ico"));
		customLayout.setActionRichTooltip(new RichTooltip("对齐","页面内容的对齐方式"));
//		customLayout.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		layoutsBand.addCommandButton(customLayout, RibbonElementPriority.TOP);
		RibbonUIManager.getInstance().bind(Page.ADD_SEQUENCE_MASTER_LAYOUT_ACTION, customLayout);
		
		layoutGroup.add(normalLayout);
		layoutGroup.add(customLayout);
		
		/*JCommandButton oddLayout = new JCommandButton("奇偶不同页布局", MediaResource.getResizableIcon("01826.ico"));
		oddLayout.setActionRichTooltip(new RichTooltip("对齐","页面内容的对齐方式"));
		oddLayout.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		layoutsBand.addCommandButton(oddLayout, RibbonElementPriority.TOP);
		oddLayout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new Dialog1();
			}
		});*/
		
		/*pageLayout.add(normalLayout);
		pageLayout.add(oddLayout);
		pageLayout.add(evenLayout);*/
		
		return layoutsBand;
		
	}
	
	public JRibbonBand getQuickStylesBand() {
		JRibbonBand quickStylesBand = new JRibbonBand("多种页布局", new preferences_desktop_theme());

		Map<RibbonElementPriority, Integer> stylesGalleryVisibleButtonCounts = new HashMap<RibbonElementPriority, Integer>();
		stylesGalleryVisibleButtonCounts.put(RibbonElementPriority.LOW, 1);
		stylesGalleryVisibleButtonCounts.put(RibbonElementPriority.MEDIUM, 2);
		stylesGalleryVisibleButtonCounts.put(RibbonElementPriority.TOP, 2);

		List<StringValuePair<List<JCommandToggleButton>>> stylesGalleryButtons = new ArrayList<StringValuePair<List<JCommandToggleButton>>>();
		List<JCommandToggleButton> stylesGalleryButtonsList = new ArrayList<JCommandToggleButton>();
		List<JCommandToggleButton> stylesGalleryButtonsList2 = new ArrayList<JCommandToggleButton>();
		for (int i = 0; i < 20; i++) {
			final int index = i;
			ResizableIcon fontIcon = /*new font_x_generic()*/MediaResource.getResizableIcon("00062.ico");
			ResizableIcon finalIcon = new DecoratedResizableIcon(fontIcon,
					new DecoratedResizableIcon.IconDecorator() {
						@Override
						public void paintIconDecoration(Component c,
								Graphics g, int x, int y, int width, int height) {
							Graphics2D g2d = (Graphics2D) g.create();
							g2d.setColor(Color.black);
							g2d.drawString("" + index, x + 2, y + height - 2);
						}
					});
			JCommandToggleButton jrb = new JCommandToggleButton("样式 " + i,
					finalIcon);
			if (i == 1) {
				jrb.getActionModel().setSelected(true);
			}
			jrb.setName("Index " + i);
			jrb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
//					System.out.println("Invoked action on " + index);
				}
			});
			if (i < 10) {
				stylesGalleryButtonsList.add(jrb);
			} else {
				stylesGalleryButtonsList2.add(jrb);
			}
		}
		
		/*JCommandButton normalLayout = new JCommandButton("正常页布局", MediaResource.getResizableIcon("01826.ico"));
		normalLayout.setPopupRichTooltip(new RichTooltip("对齐","页面内容的对齐方式"));
		normalLayout.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		
		JCommandButton oddLayout = new JCommandButton("奇数页页布局", MediaResource.getResizableIcon("01826.ico"));
		oddLayout.setPopupRichTooltip(new RichTooltip("对齐","页面内容的对齐方式"));
		oddLayout.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		
		JCommandButton evenLayout = new JCommandButton("偶数页页布局", MediaResource.getResizableIcon("01826.ico"));
		evenLayout.setPopupRichTooltip(new RichTooltip("对齐","页面内容的对齐方式"));
		evenLayout.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);*/

		//点出菜单的名称
		stylesGalleryButtons
				.add(new StringValuePair<List<JCommandToggleButton>>("页面样式",
						stylesGalleryButtonsList));
		stylesGalleryButtons
				.add(new StringValuePair<List<JCommandToggleButton>>(
						"扩展样式", stylesGalleryButtonsList2));

		//把Gallery加入到quickStyleBand
		quickStylesBand.addRibbonGallery("Styles", stylesGalleryButtons,
				stylesGalleryVisibleButtonCounts, 3, 3,
				RibbonElementPriority.TOP);
		//Gallery下面的按钮
		quickStylesBand.setRibbonGalleryPopupCallback("Styles",
				new JRibbonBand.RibbonGalleryPopupCallback() {
					public void popupToBeShown(JCommandPopupMenu menu) {
						menu.addMenuButton(new JCommandMenuButton(
								"保存选择", new EmptyResizableIcon(16)));
						menu.addMenuButton(new JCommandMenuButton(
								"清除选择", new EmptyResizableIcon(16)));
						menu.addMenuSeparator();
						menu.addMenuButton(new JCommandMenuButton(
								"应用样式", MediaResource.getResizableIcon("00062.ico")));
					}
				});

		
		JCommandButton stylesButton1 = new JCommandButton("更改样式",
				/*new font_x_generic()*/MediaResource.getResizableIcon("00062.ico"));
		quickStylesBand.addCommandButton(stylesButton1,
				RibbonElementPriority.MEDIUM);

		/*JCommandButton styles2Button = new JCommandButton("Styles2",
				new image_x_generic());
		quickStylesBand.addCommandButton(styles2Button,
				RibbonElementPriority.MEDIUM);
		styles2Button.setEnabled(false);

		JCommandButton styles3Button = new JCommandButton("Styles3",
				new text_html());
		quickStylesBand.addCommandButton(styles3Button,
				RibbonElementPriority.MEDIUM);*/

		return quickStylesBand;
	}

}
