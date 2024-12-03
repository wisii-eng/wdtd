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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.RegionAfter;
import com.wisii.wisedoc.document.attribute.RegionBefore;
import com.wisii.wisedoc.document.attribute.RegionEnd;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.util.UiUtil;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.MultiPagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel.SPMLayoutType;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmlist.TreePanelSPMList;
import com.wisii.wisedoc.view.ui.parts.pagelayout.FooterDiaPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.HeaderDiaPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.LeftRegionDiaPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.RBMPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.RightRegionDiaPanel;
import com.wisii.wisedoc.view.ui.text.UiText;
import com.wisii.wisedoc.view.ui.update.RegionDocumentManager;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 页布局详细属性设置对话框
 * @author 闫舒寰
 * @version 1.0 2009/01/20
 */
public class SimplePageMasterDialog extends AbstractWisedocDialog {
	
	SPMLayoutType layoutType;
	
	public static SimplePageMasterDialog simplePageMasterDialog;
	
	public SimplePageMasterDialog(final SPMLayoutType actionType){
		super();
		
		this.layoutType = actionType;
		simplePageMasterDialog = this;
		this.setTitle(UiText.PAGE_PROPERTY_TITLE);
		
		setPreferredSize(new Dimension(705, 650));
		setLayout(new BorderLayout());
		
		//当弹出这个对话框的时候需要把主编辑区界面的针对当前章节的引用设放到一个安全的地方
		RibbonUpdateManager.Instance.initialMainElementList();
		
		//读取当前页布局，或者创建一个默认页布局
		SinglePagelayoutModel.Instance.readSimplePageMasterModel(layoutType);
		
		//主要属性面板
		add(new SimplePageLayoutPanel(), BorderLayout.CENTER);
		//按钮面板
		add(new ButtonPanel(), BorderLayout.SOUTH);
		
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosed(final WindowEvent e) {
				super.windowClosed(e);
				UiUtil.returnRibbonUI();
				RegionDocumentManager.Instance.clearRegionDocs();
			}
			
			@Override
			public void windowClosing(final WindowEvent e) {
				super.windowClosing(e);
				UiUtil.returnRibbonUI();
				RegionDocumentManager.Instance.clearRegionDocs();
			}
			
		});
		
		pack();
		//让该对话框出现在该面板所在屏幕的中央
		this.setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private class ButtonPanel extends JPanel {

		/**
		 * Create the panel
		 */
		public ButtonPanel() {
			super();
			
			final ButtonAction ba = new ButtonAction();
			
			final JButton yesButton = new JButton(UiText.DIALOG_OK);
			final JButton cancelButton = new JButton(UiText.DIALOG_CANCEL);
			final JButton helpButton = new JButton(UiText.DIALOG_HELP);
			final JButton restoreButton = new JButton(UiText.DIALOG_DEFAULT);
			
			yesButton.addActionListener(ba);
			cancelButton.addActionListener(ba);
			helpButton.addActionListener(ba);
						
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addContainerGap(32, Short.MAX_VALUE)
						.addComponent(yesButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(cancelButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(helpButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(restoreButton)
						.addContainerGap())
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(restoreButton)
							.addComponent(helpButton)
							.addComponent(cancelButton)
							.addComponent(yesButton))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);

			/* 添加ESC、ENTER健处理 */
			setOkButton(yesButton);
			setCancelButton(cancelButton);
			
			setLayout(groupLayout);
		}
	}
	
	private class ButtonAction extends Actions{
		
		public ButtonAction() {
			this.actionType = ActionType.LAYOUT;
		}
	
		@Override
		public void doAction(final ActionEvent e) {
			final String cmd = e.getActionCommand();
			
			if(cmd.equals(UiText.DIALOG_OK)){
				
				UiUtil.returnRibbonUI();
				
				if (layoutType == SPMLayoutType.setFO) {
					
					checkRegionBodyBackground();
					checkFourRegionsBackgroundProperty();
					RegionDocumentManager.Instance.setSPMRegionContent();
					setRegionProperty();
					
//					System.out.println(SinglePagelayoutModel.Instance.getMainSPMM().getRegionAfterModel().getRegionName());
					
					setFOProperty(Constants.PR_SIMPLE_PAGE_MASTER, SinglePagelayoutModel.Instance./*getSinglePageLayoutModel*/getMainSPMM().getSimplePageMaster());
					
				} else if (layoutType == SPMLayoutType.addLayout) {
//					System.out.println("add");
					checkRegionBodyBackground();
					checkFourRegionsBackgroundProperty();
					
					//for test start
//					setSPMRegionContent();
					//设置四区域的内容
					RegionDocumentManager.Instance.setSPMRegionContent();
					//设置四区域的属性到当前simple page master的相对应四个区域上
					setRegionProperty();
					//test end
					
					final SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
					MultiPagelayoutModel.MultiPageLayout.addSimplePageMasterModel(spmm);
					
				} else if (layoutType == SPMLayoutType.editLayout) {
//					System.out.println("edit");
					checkRegionBodyBackground();
					checkFourRegionsBackgroundProperty();
					
					//for test start
//					setSPMRegionContent();
					//设置四区域的内容
					RegionDocumentManager.Instance.setSPMRegionContent();
					//设置四区域的属性到当前simple page master的相对应四个区域上
					setRegionProperty();
					//test end
					
//					SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
//					MultiPagelayoutModel.MultiPageLayout.addSimplePageMasterModel(spmm);
				}
				
//				UiUtil.returnRibbonUI();
				
				RegionDocumentManager.Instance.clearRegionDocs();
				
				simplePageMasterDialog.dispose();
			}else if(cmd.equals(UiText.DIALOG_CANCEL)){
				UiUtil.returnRibbonUI();
				RegionDocumentManager.Instance.clearRegionDocs();
				simplePageMasterDialog.dispose();
			}else if(cmd.equals(UiText.DIALOG_HELP)){
				JOptionPane.showMessageDialog(null, "别着急，快轮到做帮助菜单了", "帮助菜单", JOptionPane.INFORMATION_MESSAGE);
//				borderDialog.dispose();
			}
		}

		
		private void checkRegionBodyBackground(){
			if (RBMPanel.regionBodyBackgroundPanel != null) {
				
//				SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
				
				//old normal one
//				SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getMainSPMM();
				
				//XXX for test
				SimplePageMasterModel spmm = null;
				
				final Map<Integer, Object> psProperty = RibbonUpdateManager.Instance.getMainPSProperty();
				
				if (psProperty.get(Constants.PR_SIMPLE_PAGE_MASTER) != null) {
					spmm = SinglePagelayoutModel.Instance.getMainSPMM();
				} else if (psProperty.get(Constants.PR_PAGE_SEQUENCE_MASTER) != null) {
					final JList list = TreePanelSPMList.getInstance().getList();
					spmm = MultiPagelayoutModel.MultiPageLayout.getSimplePageMaster((String)list.getSelectedValue());
				} else {
					return;
				}
				
				//页布局的背景相关属性需要通过simple-page-master来设置，所以需要特殊对待
				final Map<Integer, Object> bgProperties = RBMPanel.regionBodyBackgroundPanel.getProperties();
				
				if (bgProperties == null) {
					return;
				}
				
				if (bgProperties.get(Constants.PR_BACKGROUND_COLOR) != null) {
					spmm.getRegionBodyModel().setBodyBackgroundColor((Color)bgProperties.get(Constants.PR_BACKGROUND_COLOR));
				}
				
				if (bgProperties.get(Constants.PR_BACKGROUND_IMAGE) != null) {
					spmm.getRegionBodyModel().setBodyBackgroundImage(bgProperties.get(Constants.PR_BACKGROUND_IMAGE));
				}
				
				if (bgProperties.get(Constants.PR_BACKGROUND_REPEAT) != null) {
					spmm.getRegionBodyModel().setBodyBackgroundImageRepeat((EnumProperty)bgProperties.get(Constants.PR_BACKGROUND_REPEAT));
				}
				
				if (bgProperties.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL) != null) {
					spmm.getRegionBodyModel().setBodyBackgroundPositionHorizontal((Length)bgProperties.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
				}
				
				if (bgProperties.get(Constants.PR_BACKGROUND_POSITION_VERTICAL) != null) {
					spmm.getRegionBodyModel().setBodyBackgroundPositionVertical((Length)bgProperties.get(Constants.PR_BACKGROUND_POSITION_VERTICAL));
				}
			}
		}
		
		private void setRegionProperty(){
			final Map<Integer, Document> regionMap = RegionDocumentManager.Instance.getRegionDocumentsMap();
			
//			SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
			//normal one
//			SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getMainSPMM();
			
			//XXX for test
			SimplePageMasterModel spmm = null;
			
			final Map<Integer, Object> psProperty = RibbonUpdateManager.Instance.getMainPSProperty();
			
			if (psProperty.get(Constants.PR_SIMPLE_PAGE_MASTER) != null) {
				spmm = SinglePagelayoutModel.Instance.getMainSPMM();
			} else if (psProperty.get(Constants.PR_PAGE_SEQUENCE_MASTER) != null) {
				final JList list = TreePanelSPMList.getInstance().getList();
				spmm = MultiPagelayoutModel.MultiPageLayout.getSimplePageMaster((String)list.getSelectedValue());
			} else {
				return;
			}
			
			if (regionMap == null) {
				return;
			}
			
			final Set<Integer> keys =  regionMap.keySet();
			
			for (final Integer i : keys) {
//				System.out.println(i);
				switch (i) {
				case Constants.FO_REGION_BEFORE:
					final RegionBefore regionBefore = (RegionBefore)getRegion(regionMap, Constants.FO_REGION_BEFORE);
//					System.out.println("region before: " + regionBefore);
					spmm.setRegionBefore(regionBefore);
					if (regionBefore != null) {
						//为了四个区域不和版心区域互相覆盖所以需要提取四个区域的高度然后设置到region-body中，不过这里region-body需要更改的margin就无效了
						spmm.getRegionBodyModel().setMarginTop(regionBefore.getExtent());
					}
					break;
				case Constants.FO_REGION_AFTER:
					final RegionAfter regionAfter = (RegionAfter)getRegion(regionMap, Constants.FO_REGION_AFTER);
					spmm.setRegionAfter(regionAfter);
					if (regionAfter != null) {
						spmm.getRegionBodyModel().setMarginBottom(regionAfter.getExtent());
					}
					break;
				case Constants.FO_REGION_START:
					final RegionStart regionStart = (RegionStart)getRegion(regionMap, Constants.FO_REGION_START);
					spmm.setRegionStart(regionStart);
					if (regionStart != null) {
						spmm.getRegionBodyModel().setMarginLeft(regionStart.getExtent());
					}
					break;
				case Constants.FO_REGION_END:
					final RegionEnd regionEnd = (RegionEnd)getRegion(regionMap, Constants.FO_REGION_END);
					spmm.setRegionEnd(regionEnd);
					if (regionEnd != null) {
						spmm.getRegionBodyModel().setMarginRight(regionEnd.getExtent());
					}
					
					break;

				default:
					break;
				}
			}
			
			regionMap.clear();
		}
		
		private Object getRegion(final Map<Integer, Document> regionMap, final int regionID){
			
//			PageSequence ps = RibbonUpdateManager.Instance.getMainPageSequence();
			
			if (regionMap.get(regionID) == null) {
				return null;
			}
			
			final PageSequence ps = (PageSequence)regionMap.get(regionID).getChildAt(0);
			final SimplePageMaster spm = (SimplePageMaster)ps.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);
//			spm.getRegion(regionID);
			return spm.getRegion(regionID);
		}
		
		private void checkFourRegionsBackgroundProperty(){
			
			//页眉区域的背景属性
			if (HeaderDiaPanel.regionBackgroundPanel != null) {
				
//				SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
				// for normal old one
//				SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getMainSPMM();
				
				//XXX for test
				SimplePageMasterModel spmm = null;
				
				final Map<Integer, Object> psProperty = RibbonUpdateManager.Instance.getMainPSProperty();
				
				if (psProperty.get(Constants.PR_SIMPLE_PAGE_MASTER) != null) {
					spmm = SinglePagelayoutModel.Instance.getMainSPMM();
				} else if (psProperty.get(Constants.PR_PAGE_SEQUENCE_MASTER) != null) {
					final JList list = TreePanelSPMList.getInstance().getList();
					spmm = MultiPagelayoutModel.MultiPageLayout.getSimplePageMaster((String)list.getSelectedValue());
				} else {
					return;
				}
				
				//页布局的背景相关属性需要通过simple-page-master来设置，所以需要特殊对待
				final Map<Integer, Object> bgProperties = HeaderDiaPanel.regionBackgroundPanel.getProperties();
				
				if (bgProperties != null) {
					if (bgProperties.get(Constants.PR_BACKGROUND_COLOR) != null) {
						spmm.getRegionBeforeModel().setBodyBackgroundColor((Color)bgProperties.get(Constants.PR_BACKGROUND_COLOR));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_IMAGE) != null) {
						spmm.getRegionBeforeModel().setBodyBackgroundImage(bgProperties.get(Constants.PR_BACKGROUND_IMAGE));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_REPEAT) != null) {
						spmm.getRegionBeforeModel().setBodyBackgroundImageRepeat((EnumProperty)bgProperties.get(Constants.PR_BACKGROUND_REPEAT));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL) != null) {
						spmm.getRegionBeforeModel().setBodyBackgroundPositionHorizontal((Length)bgProperties.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_POSITION_VERTICAL) != null) {
						spmm.getRegionBeforeModel().setBodyBackgroundPositionVertical((Length)bgProperties.get(Constants.PR_BACKGROUND_POSITION_VERTICAL));
					}
				}
			}
			
			//页脚区域的背景属性
			if (FooterDiaPanel.regionBackgroundPanel != null) {
				
				final SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
				
				//页布局的背景相关属性需要通过simple-page-master来设置，所以需要特殊对待
				final Map<Integer, Object> bgProperties = FooterDiaPanel.regionBackgroundPanel.getProperties();
				
				if (bgProperties != null) {
					if (bgProperties.get(Constants.PR_BACKGROUND_COLOR) != null) {
						spmm.getRegionAfterModel().setBodyBackgroundColor((Color)bgProperties.get(Constants.PR_BACKGROUND_COLOR));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_IMAGE) != null) {
						spmm.getRegionAfterModel().setBodyBackgroundImage(bgProperties.get(Constants.PR_BACKGROUND_IMAGE));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_REPEAT) != null) {
						spmm.getRegionAfterModel().setBodyBackgroundImageRepeat((EnumProperty)bgProperties.get(Constants.PR_BACKGROUND_REPEAT));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL) != null) {
						spmm.getRegionAfterModel().setBodyBackgroundPositionHorizontal((Length)bgProperties.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_POSITION_VERTICAL) != null) {
						spmm.getRegionAfterModel().setBodyBackgroundPositionVertical((Length)bgProperties.get(Constants.PR_BACKGROUND_POSITION_VERTICAL));
					}
				}
			}
			
			//左区域的背景属性
			if (LeftRegionDiaPanel.regionBackgroundPanel != null) {
				
				final SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
				
				//页布局的背景相关属性需要通过simple-page-master来设置，所以需要特殊对待
				final Map<Integer, Object> bgProperties = LeftRegionDiaPanel.regionBackgroundPanel.getProperties();
				
				if (bgProperties != null) {
					if (bgProperties.get(Constants.PR_BACKGROUND_COLOR) != null) {
						spmm.getRegionStartModel().setBodyBackgroundColor((Color)bgProperties.get(Constants.PR_BACKGROUND_COLOR));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_IMAGE) != null) {
						spmm.getRegionStartModel().setBodyBackgroundImage(bgProperties.get(Constants.PR_BACKGROUND_IMAGE));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_REPEAT) != null) {
						spmm.getRegionStartModel().setBodyBackgroundImageRepeat((EnumProperty)bgProperties.get(Constants.PR_BACKGROUND_REPEAT));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL) != null) {
						spmm.getRegionStartModel().setBodyBackgroundPositionHorizontal((Length)bgProperties.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_POSITION_VERTICAL) != null) {
						spmm.getRegionStartModel().setBodyBackgroundPositionVertical((Length)bgProperties.get(Constants.PR_BACKGROUND_POSITION_VERTICAL));
					}
				}
			}
			
			//右区域的背景属性
			if (RightRegionDiaPanel.regionBackgroundPanel != null) {
				
				final SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
				
				//页布局的背景相关属性需要通过simple-page-master来设置，所以需要特殊对待
				final Map<Integer, Object> bgProperties = RightRegionDiaPanel.regionBackgroundPanel.getProperties();
				
				if (bgProperties != null) {
					if (bgProperties.get(Constants.PR_BACKGROUND_COLOR) != null) {
						spmm.getRegionEndModel().setBodyBackgroundColor((Color)bgProperties.get(Constants.PR_BACKGROUND_COLOR));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_IMAGE) != null) {
						spmm.getRegionEndModel().setBodyBackgroundImage(bgProperties.get(Constants.PR_BACKGROUND_IMAGE));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_REPEAT) != null) {
						spmm.getRegionEndModel().setBodyBackgroundImageRepeat((EnumProperty)bgProperties.get(Constants.PR_BACKGROUND_REPEAT));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL) != null) {
						spmm.getRegionEndModel().setBodyBackgroundPositionHorizontal((Length)bgProperties.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
					}
					
					if (bgProperties.get(Constants.PR_BACKGROUND_POSITION_VERTICAL) != null) {
						spmm.getRegionEndModel().setBodyBackgroundPositionVertical((Length)bgProperties.get(Constants.PR_BACKGROUND_POSITION_VERTICAL));
					}
				}
			}
		}
	}
}
