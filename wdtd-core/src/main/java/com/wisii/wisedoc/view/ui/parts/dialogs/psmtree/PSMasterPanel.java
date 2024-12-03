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
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterReference;
import com.wisii.wisedoc.document.attribute.SinglePageMasterReference;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.psmnode.ConditionalPageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.model.psmnode.PageSequenceMasterModel;
import com.wisii.wisedoc.view.ui.model.psmnode.RepeatablePageMasterAlternativesModel;
import com.wisii.wisedoc.view.ui.model.psmnode.RepeatablePageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.model.psmnode.SinglePageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmlist.TreePanelSPMList;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.ConditionalPMRPanel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.PageSequenceMasterPropertyPanel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.RepeatablePageMasterAlternativesPropertyPanel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.RepeatablePageMasterReferencePropertyPanel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel.SinglePageMasterReferencePropertyPanel;

/**
 * 页布局序列属性设置面板
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class PSMasterPanel extends JPanel {
    
    public static PageSequenceMasterPropertyPanel pageSequenceMasterPropertyPanel;
    public static SinglePageMasterReferencePropertyPanel singlePageMasterReferencePropertyPanel;
    public static RepeatablePageMasterReferencePropertyPanel repeatablePageMasterReferencePropertyPanel;
    public static RepeatablePageMasterAlternativesPropertyPanel repeatablePageMasterAlternativesPropertyPanel;
    public static ConditionalPMRPanel conPMR;
    
    private TreePanel treePanel;
    private static JPanel propertyPanel;
    
    JPanel treePanelSPMList;

    public PSMasterPanel() {
        super(new BorderLayout());
        
        this.setPreferredSize(new Dimension(600, 600));
        
        //创建并初始化页布局序列树
        treePanel = new TreePanel();
        treePanel.setPreferredSize(new Dimension(180, 120));
        initialTree(treePanel);
        
        //中间放置页布局序列各个孩子的属性面板
        propertyPanel = PSMPanelManager.getInitialPropertyPanel();
        
        //创建默认的page sequence master属性面板
        pageSequenceMasterPropertyPanel = new PageSequenceMasterPropertyPanel();
        propertyPanel.add(pageSequenceMasterPropertyPanel, BorderLayout.CENTER);
        
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, propertyPanel);
        jsp.setOneTouchExpandable(true);
        
        //单个页布局列表
        treePanelSPMList = new JPanel(new BorderLayout());
        treePanelSPMList.add(TreePanelSPMList.getInstance().getListPanel(), BorderLayout.CENTER);
        
        JSplitPane jsp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp, treePanelSPMList);
        jsp2.setOneTouchExpandable(true);
        add(jsp2, BorderLayout.CENTER);
        
        //该对话框上面操作的ribbon按钮栏
        this.add(new PSMToolBar(treePanel).getToolBar(), BorderLayout.NORTH);
    }
    
    public void addTreePanel(){
    	treePanelSPMList.removeAll();
    	treePanelSPMList.add(TreePanelSPMList.getInstance().getListPanel(), BorderLayout.CENTER);
    }

    /**
     * 根据当前属性值初始化页布局序列树
     * @param treePanel
     */
    public void initialTree(TreePanel treePanel) {
    	
    	Object o = RibbonUIModel.getCurrentPropertiesByType().get(ActionType.LAYOUT).get(Constants.PR_PAGE_SEQUENCE_MASTER);
    	
    	if (o == null) {
			treePanel.addPageSequenceMasterNode(new PageSequenceMasterModel("页布局序列"));
			treePanel.setSelection();
			return;
		}
    	
    	if (o instanceof PageSequenceMaster) {
    		PageSequenceMaster psm = (PageSequenceMaster) o;
    		List<SubSequenceSpecifier> sss = psm.getSubsequenceSpecifiers();
    		
    		treePanel.addPageSequenceMasterNode(new PageSequenceMasterModel(psm));
    		
    		//这个方法需要实现选择当前刚添加的节点
    		treePanel.setPageSequenceMasterSelection();    		
    		
    		for (int i = 0; i < sss.size(); i++) {
    			
    			Object masters = sss.get(i);
    			
    			if (masters instanceof SinglePageMasterReference) {
					SinglePageMasterReference master = (SinglePageMasterReference) masters;
					treePanel.addSinglePageMasterReferenceNode(new SinglePageMasterReferenceModel(master));
				}
    			
    			if (masters instanceof RepeatablePageMasterReference) {
					RepeatablePageMasterReference master = (RepeatablePageMasterReference) masters;
					treePanel.addRepeatablePageMasterReferenceNode(new RepeatablePageMasterReferenceModel(master));
				}
    			
    			if (masters instanceof RepeatablePageMasterAlternatives) {
					RepeatablePageMasterAlternatives master = (RepeatablePageMasterAlternatives) masters;
					RepeatablePageMasterAlternativesModel rpam = new RepeatablePageMasterAlternativesModel(master);
					//添加这个的时候会自动添加一个默认的节点，这里会有问题
					treePanel.addRepeatablePageMasterReferenceNode(rpam);
					
					List<ConditionalPageMasterReferenceModel> cpmrList = new ArrayList<ConditionalPageMasterReferenceModel>();
					cpmrList.addAll(rpam.getConditionalPageMasterReferenceModelList());
					
					treePanel.setSelection();
					
					for (int j = 0; j < cpmrList.size(); j++) {
						treePanel.addConditionalPageMasterReferenceNode(cpmrList.get(j));
					}
					
					treePanel.setPageSequenceMasterSelection();
				}
			}
		}
    }
}
