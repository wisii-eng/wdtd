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
package com.wisii.wisedoc.view.ui.model.paragraphstyles;

import java.awt.BorderLayout;
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
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.StringValuePair;
import org.jvnet.flamingo.common.icon.DecoratedResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import org.jvnet.flamingo.ribbon.resize.CoreRibbonResizePolicies;
import org.jvnet.flamingo.utils.RenderingUtils;

import com.sun.istack.internal.NotNull;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.parts.styles.ParagraphStylePropertyPanel;
import com.wisii.wisedoc.view.ui.parts.styles.ParagraphStylesList;
import com.wisii.wisedoc.view.ui.parts.styles.ParagraphStylesPanel;
import com.wisii.wisedoc.view.ui.ribbon.basicband.ParagraphStylesBand;

/**
 * 段落样式模型层
 * @author 闫舒寰
 * @version 1.0 2009/03/25
 */
public enum ParagraphStylesModel {
	
	Instance;
	
	public Map<Integer, Object> getPsProMap(String name) {
		Map<Integer, Object> temp = null;
		
		for (ParagraphStyles ps : psList) {
			if (ps.getStyleName().equals(name)) {
				temp = ps.getStyleProperty();
			}
		}
		return temp;
	}
	
	private List<ParagraphStyles> psList = new ArrayList<ParagraphStyles>();
	
	public void setParagraphStylesList(@NotNull List<ParagraphStyles> psList) {
		this.psList = psList;
	}

	public @NotNull List<ParagraphStyles> getParagraphStylesList() {
		return psList;
	}

	public List<String> getPsName() {
		
		List<String> psName = new ArrayList<String>();
		
		if (psList.size() == 0) {
			//TODO 把默认的名字搞出来
			String[] listItems = {
		            "Chris", "Joshua", "Daniel"};
			
			for (String name : listItems) {
				psList.add(new ParagraphStyles(name, new HashMap<Integer, Object>()));
				psName.add(name);
			}
			
		} else {
			for (ParagraphStyles ps : psList) {
				psName.add(ps.getStyleName());
			}
		}
		
		return psName;
	}

	private ParagraphStylesList psl;
	
	public void setPropertise(Map<Integer, Object> properties) {
		getPsProMap((String)ParagraphStylesList.getList().getSelectedValue()).putAll(properties);
		UpdateUI();
	}
	
	public @NotNull Map<Integer, Object> getProperty() {
		
		Map<Integer, Object> temp = getPsProMap((String)ParagraphStylesList.getList().getSelectedValue());
		
		if (temp == null) {
			temp = new HashMap<Integer, Object>();
		}
		
		return temp;
	}
	
	ParagraphStylePropertyPanel pspp;
	
	JSplitPane jsp;
	
	ParagraphStylesPanel psp;
	
	JPanel propertyPanel;
	
	public void initial(ParagraphStylesPanel psp) {
		
		this.psp = psp;
		
		//用它来初始化默认名字
		getPsName();
		
		psl = new ParagraphStylesList();
		pspp = new ParagraphStylePropertyPanel();
		
		propertyPanel = new JPanel();
		propertyPanel.setLayout(new BorderLayout());
		propertyPanel.add(pspp);
		
		jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, psl, propertyPanel);
	}
	
	/**
	 * 更新段落样式对话框中的属性样式
	 */
	public void UpdateUI() {
		
		pspp = new ParagraphStylePropertyPanel();
		pspp.setPName((String)ParagraphStylesList.getList().getSelectedValue());
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				propertyPanel.removeAll();
				propertyPanel.add(pspp, BorderLayout.CENTER);
				propertyPanel.updateUI();
			}
		});
	}
	
	/**
	 * 更新Ribbon界面菜单中的按钮
	 */
	public void upDateGalleryButtons() {
		List<JCommandToggleButton> defaultGalleryButtonsList = ParagraphStylesBand.getDefaultGalleryButtonsList();
		List<JCommandToggleButton> customGalleryButtonsList = ParagraphStylesBand.getCustomGalleryButtonsList();
		/*
		for (int i = 0; i < 20; i++) {
			final int index = i;
			ResizableIcon fontIcon = new font_x_generic();
			ResizableIcon finalIcon = new DecoratedResizableIcon(fontIcon,
					new DecoratedResizableIcon.IconDecorator() {
						@Override
						public void paintIconDecoration(Component c,
								Graphics g, int x, int y, int width, int height) {
							Graphics2D g2d = (Graphics2D) g.create();
							g2d.setColor(Color.black);
							RenderingUtils.installDesktopHints(g2d);
							g2d.setFont(UIManager.getFont("Label.font"));
							g2d.drawString("" + index, x + 2, y + height - 2);
							g2d.dispose();
						}
					});
			
			JCommandToggleButton jrb = new JCommandToggleButton("Style " + i,
					fontIcon);
			if (i == 1) {
				jrb.getActionModel().setSelected(true);
			}
			jrb.setName("Index " + i);
			jrb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Invoked action on " + index);
				}
			});
			if (i < 5) {
				defaultGalleryButtonsList.add(jrb);
			} else {
				customGalleryButtonsList.add(jrb);
			}
		}*/
		
		for (String name : getPsName()) {
			ResizableIcon fontIcon = /*new font_x_generic()*/MediaResource.getResizableIcon("00062.ico");
			
			JCommandToggleButton jrb = new JCommandToggleButton(name, fontIcon);
//				jrb.getActionModel().setSelected(true);
			jrb.setName(name);
			jrb.addActionListener(new CommandActions());
			defaultGalleryButtonsList.add(jrb);
//			customGalleryButtonsList.add(jrb);
		}
		
		
	}
	
	private class CommandActions extends Actions {
		@Override
		public void doAction(ActionEvent e) {
			this.actionType = ActionType.BLOCK;
			
			if (e.getSource() instanceof JCommandToggleButton) {
				JCommandToggleButton jtb = (JCommandToggleButton) e.getSource();
				String name = jtb.getName();
				
				Map<Integer, Object> blockProperty = RibbonUIModel.getReadPropertiesByType().get(ActionType.BLOCK);
				
				Map<Integer, Object> styleProperty = getPsProMap(name);
				
				
				Map<Integer, Object> temp = new HashMap<Integer, Object>();
				temp.put(Constants.PR_BLOCK_STYLE, new ParagraphStyles(name, styleProperty));
				
				Set<Integer> setyleSet = styleProperty.keySet();
				
				for (Integer key : setyleSet) {
					if (styleProperty.get(key).equals(blockProperty.get(key))) {
						blockProperty.put(key, null);
					}
				}
				
				temp.putAll(blockProperty);
				
				setFOProperties(temp);
			}
		}
	}
	
	//用于更行
	public void upDateGalleryButtonss() {
		
		//生成段落样式对象
		if (psList == null) {
			psList = new ArrayList<ParagraphStyles>();
		} else {
			psList.clear();
		}
		/*Set<String> names = psMap.keySet();
		for (String name : names) {
			psList.add(new ParagraphStyles(name, psMap.get(name)));
		}*/
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
//				List<JCommandToggleButton> jtbList = ParagraphStylesBand.getDefaultGalleryButtonsList();
//
//				JCommandToggleButton[] jtb = jtbList.toArray(new JCommandToggleButton[jtbList.size()]);
//
//
//				ParagraphStylesBand.getPStyleBand().removeRibbonGalleryButtons("默认", jtb);
//				ParagraphStylesModel.Instance.updatePRibbonBand();
			}
		});
		
	}
	
	private void updatePRibbonBand() {
		
		JRibbonBand quickStylesBand = ParagraphStylesBand.getPStyleBand();
		
		quickStylesBand.setResizePolicies(CoreRibbonResizePolicies
				.getCorePoliciesRestrictive(quickStylesBand));

		Map<RibbonElementPriority, Integer> stylesGalleryVisibleButtonCounts = new HashMap<RibbonElementPriority, Integer>();
		stylesGalleryVisibleButtonCounts.put(RibbonElementPriority.LOW, 1);
		stylesGalleryVisibleButtonCounts.put(RibbonElementPriority.MEDIUM, 2);
		stylesGalleryVisibleButtonCounts.put(RibbonElementPriority.TOP, 3);

		List<StringValuePair<List<JCommandToggleButton>>> stylesGalleryButtons = new ArrayList<StringValuePair<List<JCommandToggleButton>>>();
		
//		ParagraphStylesModel.Instance.upDateGalleryButtons();
		
		List<JCommandToggleButton> defaultGalleryButtonsList = ParagraphStylesBand.getDefaultGalleryButtonsList();
		List<JCommandToggleButton> customGalleryButtonsList = ParagraphStylesBand.getCustomGalleryButtonsList();
		
		defaultGalleryButtonsList.clear();
		customGalleryButtonsList.clear();
		
		for (int i = 0; i < 10; i++) {
			final int index = i;
			ResizableIcon fontIcon = /*new font_x_generic()*/MediaResource.getResizableIcon("00062.ico");
			ResizableIcon finalIcon = new DecoratedResizableIcon(fontIcon,
					new DecoratedResizableIcon.IconDecorator() {
						@Override
						public void paintIconDecoration(Component c,
								Graphics g, int x, int y, int width, int height) {
							Graphics2D g2d = (Graphics2D) g.create();
							g2d.setColor(Color.black);
							RenderingUtils.installDesktopHints(g2d);
							g2d.setFont(UIManager.getFont("Label.font"));
							g2d.drawString("" + index, x + 2, y + height - 2);
							g2d.dispose();
						}
					});
			JCommandToggleButton jrb = new JCommandToggleButton("Style " + i,
					finalIcon);
			if (i == 1) {
				jrb.getActionModel().setSelected(true);
			}
			jrb.setName("Index " + i);
			jrb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Invoked action on " + index);
				}
			});
			if (i < 5) {
				defaultGalleryButtonsList.add(jrb);
			} else {
				customGalleryButtonsList.add(jrb);
			}
		}
		

		stylesGalleryButtons.add(new StringValuePair<List<JCommandToggleButton>>("默认",
						defaultGalleryButtonsList));
		
		stylesGalleryButtons.add(new StringValuePair<List<JCommandToggleButton>>(
						"自定义", customGalleryButtonsList));

		quickStylesBand.addRibbonGallery("Styles", stylesGalleryButtons,
				stylesGalleryVisibleButtonCounts, 3, 3,	RibbonElementPriority.TOP);
		

	}
	
	
	public JComponent getPsPanel() {
		return jsp;
	}

}
