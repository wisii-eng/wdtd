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
package com.wisii.wisedoc.view.ui.parts.dialogs.psmtree;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.wisii.wisedoc.view.ui.model.psmnode.ConditionalPageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.model.psmnode.PageSequenceMasterModel;
import com.wisii.wisedoc.view.ui.model.psmnode.RepeatablePageMasterAlternativesModel;
import com.wisii.wisedoc.view.ui.model.psmnode.RepeatablePageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.model.psmnode.SinglePageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmlist.TreePanelSPMList;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.ConditionalPMRPanel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.MasterReferenceLabel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.PageSequenceMasterPropertyPanel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.RepeatablePageMasterAlternativesPropertyPanel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.RepeatablePageMasterReferencePropertyPanel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.SinglePageMasterReferencePropertyPanel;

/**
 * 管理页布局序列
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public enum PSMPanelManager {
	
	Manager;
	
	//中间放置页布局序列各个孩子的属性面板
	private static JPanel propertyPanel;
	
	private static JPanel currentPropertyPanel;
	
	private static Object masterReference;
	
	public static JPanel getInitialPropertyPanel(){
		propertyPanel = new JPanel();
        propertyPanel.setLayout(new BorderLayout());
        
        return propertyPanel;
	}
	
	public static JPanel getPropertyPanel() {
		return propertyPanel;
	}
	
	public static void updatePropertyUI(final Object userObject){
		if (userObject instanceof PageSequenceMasterModel) {
    		javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                	setCurrentPropertyPanel(userObject, new PageSequenceMasterPropertyPanel());
                }
            });
		} else if (userObject instanceof SinglePageMasterReferenceModel) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	setCurrentPropertyPanel(userObject, new SinglePageMasterReferencePropertyPanel());
	            }
	        });
		} else if (userObject instanceof RepeatablePageMasterReferenceModel) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	setCurrentPropertyPanel(userObject, new RepeatablePageMasterReferencePropertyPanel());
	            }
	        });
		} else if (userObject instanceof RepeatablePageMasterAlternativesModel) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	setCurrentPropertyPanel(userObject, new RepeatablePageMasterAlternativesPropertyPanel());
	            }
	        });
		} else if (userObject instanceof ConditionalPageMasterReferenceModel) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	setCurrentPropertyPanel(userObject, new ConditionalPMRPanel());
	            }
	        });
		}
	}
	
	private static final JList list = TreePanelSPMList.getInstance().getList();
	private static ListSelectionListener listListener;
	private static void setCurrentPropertyPanel(Object userObject, JPanel panel){
		
		currentPropertyPanel = panel;
		if (currentPropertyPanel instanceof MasterReferenceLabel) {
			MasterReferenceLabel ui = (MasterReferenceLabel) currentPropertyPanel;
			masterReference = ui.getMasterReference();
			
			list.setEnabled(true);
			
			if (listListener != null) {
				list.removeListSelectionListener(listListener);
			}
			
			if (list != null) {
				listListener = new PSMPanelManager.LSListener();
				list.addListSelectionListener(listListener);
			}
		} else {
			list.setEnabled(false);
			if (listListener != null) {
				list.removeListSelectionListener(listListener);
			}
		}
		
		updateUIProperty(panel, userObject);
		
		//更新到主界面上
		getPropertyPanel().removeAll();
		getPropertyPanel().add(getCurrentPropertyPanel());
    	getPropertyPanel().updateUI();
	}
	
	//用于更新属性面板上的属性
	private static void updateUIProperty(JPanel proPanel, Object userObject){
		
		if (userObject instanceof PageSequenceMasterModel) {
			PageSequenceMasterModel model = (PageSequenceMasterModel) userObject;
			if (proPanel instanceof PageSequenceMasterPropertyPanel) {
				PageSequenceMasterPropertyPanel ui = (PageSequenceMasterPropertyPanel) proPanel;
				ui.setMasterName(model.getMasterName());
			}
		} else if (userObject instanceof SinglePageMasterReferenceModel) {
			SinglePageMasterReferenceModel model = (SinglePageMasterReferenceModel) userObject;
			if (proPanel instanceof SinglePageMasterReferencePropertyPanel) {
				SinglePageMasterReferencePropertyPanel ui = (SinglePageMasterReferencePropertyPanel) proPanel;
				ui.setReferenceName(model.getMasterReference());
				//从master reference更新simple page master列表
				list.setSelectedValue(model.getMasterReference(), true);
			}
		} else if (userObject instanceof RepeatablePageMasterReferenceModel) {
			RepeatablePageMasterReferenceModel model = (RepeatablePageMasterReferenceModel)userObject;
			if (proPanel instanceof RepeatablePageMasterReferencePropertyPanel) {
				RepeatablePageMasterReferencePropertyPanel ui = (RepeatablePageMasterReferencePropertyPanel) proPanel;
				ui.setMasterReference(model.getMasterReference());
				ui.setRepeatNumber(model.getMaximumRepeats());
				//从master reference更新simple page master列表
				list.setSelectedValue(model.getMasterReference(), true);
			}
		} else if (userObject instanceof RepeatablePageMasterAlternativesModel) {
			RepeatablePageMasterAlternativesModel model = (RepeatablePageMasterAlternativesModel)userObject;
			if (proPanel instanceof RepeatablePageMasterAlternativesPropertyPanel) {
				RepeatablePageMasterAlternativesPropertyPanel ui = (RepeatablePageMasterAlternativesPropertyPanel) proPanel;
				ui.setMaxRepeat(model.getMaximumRepeats());
			}
		} else if (userObject instanceof ConditionalPageMasterReferenceModel) {
			ConditionalPageMasterReferenceModel model = (ConditionalPageMasterReferenceModel)userObject;
			if (proPanel instanceof ConditionalPMRPanel) {
				ConditionalPMRPanel ui = (ConditionalPMRPanel) proPanel;
				
				//设置master reference
				ui.setMasterReference(model.getMasterReference());
				//设置page position
				ui.setPagePosition(model.getPagePosition());
				//设置odd or even
				ui.setOddorEven(model.getOddorEven());
				//设置blank or not-blank
				ui.setBlank(model.getBlankorNotBlank());
				
				//从master reference更新simple page master列表
				list.setSelectedValue(model.getMasterReference(), true);
			}
		}
	}

	public static JPanel getCurrentPropertyPanel() {
		return currentPropertyPanel;
	}
	
	
	private static class LSListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			
			if (masterReference instanceof JLabel) {
				JLabel mRefLabel = (JLabel) masterReference;
				
				if (e.getSource() instanceof JList) {
					JList ui = (JList) e.getSource();
					mRefLabel.setText((String)ui.getSelectedValue().toString());
				}
			}
		}
	}
	
	
	private static Object currentTreeNodeModel;

	public static void updateCurrentTreeNodeModel(Object treeNodeModel){
		currentTreeNodeModel = treeNodeModel;
	}
}
