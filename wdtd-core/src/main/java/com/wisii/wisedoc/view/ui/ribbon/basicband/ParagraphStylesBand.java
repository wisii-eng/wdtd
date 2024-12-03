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
package com.wisii.wisedoc.view.ui.ribbon.basicband;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.StringValuePair;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import org.jvnet.flamingo.ribbon.resize.CoreRibbonResizePolicies;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.paragraphstyles.ParagraphStylesModel;
import com.wisii.wisedoc.view.ui.parts.styles.ParagraphStylesDialog;
import com.wisii.wisedoc.view.ui.svg.transcoded.preferences_desktop_theme;

/**
 * 段落样式设置面板
 * @author 闫舒寰
 * @version 1.0 2009/03/25
 */
public class ParagraphStylesBand {
	
	public static final List<JCommandToggleButton> defaultGalleryButtonsList = new ArrayList<JCommandToggleButton>();
	public static List<JCommandToggleButton> getDefaultGalleryButtonsList() {
		return defaultGalleryButtonsList;
	}

	public static final List<JCommandToggleButton> customGalleryButtonsList = new ArrayList<JCommandToggleButton>();
	
	public static List<JCommandToggleButton> getCustomGalleryButtonsList() {
		return customGalleryButtonsList;
	}
	
	public static JRibbonBand pStyleBand;
	public static JRibbonBand getPStyleBand() {
		return pStyleBand;
	}

	public JRibbonBand getQuickStylesBand() {
		JRibbonBand quickStylesBand = new JRibbonBand("段落样式",
				new preferences_desktop_theme());
		pStyleBand = quickStylesBand;
		
		quickStylesBand.setCollapsedStateKeyTip("ZS");

		quickStylesBand.setResizePolicies(CoreRibbonResizePolicies
				.getCorePoliciesRestrictive(quickStylesBand));

		Map<RibbonElementPriority, Integer> stylesGalleryVisibleButtonCounts = new HashMap<RibbonElementPriority, Integer>();
		stylesGalleryVisibleButtonCounts.put(RibbonElementPriority.LOW, 1);
		stylesGalleryVisibleButtonCounts.put(RibbonElementPriority.MEDIUM, 2);
		stylesGalleryVisibleButtonCounts.put(RibbonElementPriority.TOP, 3);

		List<StringValuePair<List<JCommandToggleButton>>> stylesGalleryButtons = new ArrayList<StringValuePair<List<JCommandToggleButton>>>();
		
		ParagraphStylesModel.Instance.upDateGalleryButtons();

		stylesGalleryButtons.add(new StringValuePair<List<JCommandToggleButton>>("默认",
						defaultGalleryButtonsList));
		
		stylesGalleryButtons.add(new StringValuePair<List<JCommandToggleButton>>(
						"自定义", customGalleryButtonsList));

		quickStylesBand.addRibbonGallery("Styles", stylesGalleryButtons,
				stylesGalleryVisibleButtonCounts, 3, 3,	RibbonElementPriority.TOP);
		
		/*quickStylesBand.setRibbonGalleryPopupCallback("Styles",
				new JRibbonBand.RibbonGalleryPopupCallback() {
					public void popupToBeShown(JCommandPopupMenu menu) {
						JCommandMenuButton addPStyle = new JCommandMenuButton(
								"添加样式", new EmptyResizableIcon(16));
						addPStyle
								.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										System.out.println("Save Selection activated");
									}
								});
						addPStyle.setActionKeyTip("SS");
						menu.addMenuButton(addPStyle);
						
						JCommandMenuButton deletePStyle = new JCommandMenuButton(
								"删除样式", new EmptyResizableIcon(16));
						deletePStyle
								.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										System.out
												.println("Save Selection activated");
									}
								});
						deletePStyle.setActionKeyTip("SS");
						menu.addMenuButton(deletePStyle);

						JCommandMenuButton changeStyle = new JCommandMenuButton(
								"修改样式", new EmptyResizableIcon(16));
						changeStyle
								.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										System.out
												.println("Clear Selection activated");
									}
								});
						changeStyle.setActionKeyTip("SC");
						menu.addMenuButton(changeStyle);

						menu.addMenuSeparator();
						JCommandMenuButton applyStylesButton = new JCommandMenuButton(
								"Apply Styles", new font_x_generic());
						applyStylesButton
								.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										System.out
												.println("Apply Styles activated");
									}
								});
						applyStylesButton.setActionKeyTip("SA");
						menu.addMenuButton(applyStylesButton);
					}
				});*/
		quickStylesBand.setRibbonGalleryExpandKeyTip("Styles", "L");

		JCommandButton stylesButton1 = new JCommandButton("编辑样式", MediaResource.getResizableIcon("00062.ico"));
		stylesButton1.setActionKeyTip("SA");
//		stylesButton1.setPopupCallback(new StyleActionList());
		stylesButton1.setPopupRichTooltip(new RichTooltip("对齐","页面内容的对齐方式"));
		stylesButton1.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		stylesButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ParagraphStylesDialog(ActionType.BLOCK);
			}
		});
		
		quickStylesBand.addCommandButton(stylesButton1, RibbonElementPriority.TOP);
		

		/*JCommandButton styles2Button = new JCommandButton("Styles2",
				new image_x_generic());
		styles2Button.setActionKeyTip("SB");
		quickStylesBand.addCommandButton(styles2Button,
				RibbonElementPriority.MEDIUM);
		styles2Button.setEnabled(false);

		JCommandButton styles3Button = new JCommandButton("Styles3",
				new text_html());
		styles3Button.setActionKeyTip("SC");
		quickStylesBand.addCommandButton(styles3Button,
				RibbonElementPriority.MEDIUM);*/

		return quickStylesBand;
	}
	
/*	public class StyleActionList extends JCommandPopupMenu implements PopupPanelCallback {
		
		public StyleActionList() {
			JCommandMenuButton auto = new JCommandMenuButton("自动", MediaResource.getResizableIcon("00666.ico"));
			JCommandMenuButton fix = new JCommandMenuButton("固定", MediaResource.getResizableIcon("00667.ico"));
			
			
			this.addMenuButton(auto);
			this.addMenuButton(fix);
		}
		
		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new StyleActionList();
		}
	}*/

}
