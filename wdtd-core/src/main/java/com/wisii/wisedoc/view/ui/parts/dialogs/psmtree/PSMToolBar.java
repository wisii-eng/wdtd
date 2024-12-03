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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.ribbon.JRibbon;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.model.psmnode.ConditionalPageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.model.psmnode.PageSequenceMasterModel;
import com.wisii.wisedoc.view.ui.model.psmnode.RepeatablePageMasterAlternativesModel;
import com.wisii.wisedoc.view.ui.model.psmnode.RepeatablePageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.model.psmnode.SinglePageMasterReferenceModel;

/**
 * 针对页布局序列的Ribbon按钮栏
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class PSMToolBar implements ActionListener{
	
	JRibbon jRibbon;
	
	private TreePanel treePanel;
	
    public enum Command {
    	Add_PageSequenceMaster,
    	Add_SPMasterRef,
    	Add_RPMasterRef,
    	Add_RPMasterAlter,
    	Add_CPMasterRef,
    	
    	Remove,
    	Clear;
    }
    
    private static JCommandButton psmb, spmrb, rpmrb, rpmab, cpmrb;
	
	public PSMToolBar(TreePanel treePanel){
		
		this.treePanel = treePanel;
		
		jRibbon = new JRibbon();
        
        JRibbonBand jRibbonBand = new JRibbonBand("添加节点", MediaResource.getResizableIcon("09379.ico"));
        
        psmb = new JCommandButton("页布局序列"/*"page-sequence-master"*/, MediaResource.getResizableIcon("03705.ico"));
        jRibbonBand.addCommandButton(psmb, RibbonElementPriority.MEDIUM);
        psmb.getActionModel().setActionCommand(Command.Add_PageSequenceMaster.name());
        psmb.addActionListener(this);
        
        spmrb = new JCommandButton("单次引用"/*"single-page-master-reference"*/, MediaResource.getResizableIcon("03705.ico"));
        jRibbonBand.addCommandButton(spmrb, RibbonElementPriority.MEDIUM);
        spmrb.getActionModel().setActionCommand(Command.Add_SPMasterRef.name());
        spmrb.addActionListener(this);
        
        rpmrb = new JCommandButton("多次引用"/*"repeatable-page-master-reference"*/, MediaResource.getResizableIcon("03705.ico"));
        jRibbonBand.addCommandButton(rpmrb, RibbonElementPriority.MEDIUM);
        rpmrb.getActionModel().setActionCommand(Command.Add_RPMasterRef.name());
        rpmrb.addActionListener(this);
        
        rpmab = new JCommandButton("特殊位置引用"/*"repeatable-page-master-alternatives"*/, MediaResource.getResizableIcon("03705.ico"));
        jRibbonBand.addCommandButton(rpmab, RibbonElementPriority.MEDIUM);
        rpmab.getActionModel().setActionCommand(Command.Add_RPMasterAlter.name());
        rpmab.addActionListener(this);
        
        cpmrb = new JCommandButton("条件引用"/*"conditional-page-master-reference"*/, MediaResource.getResizableIcon("03705.ico"));
        jRibbonBand.addCommandButton(cpmrb, RibbonElementPriority.MEDIUM);
        cpmrb.getActionModel().setActionCommand(Command.Add_CPMasterRef.name());
        cpmrb.addActionListener(this);
        
        
        JRibbonBand cleanBand = new JRibbonBand("删除节点", MediaResource.getResizableIcon("03705.ico"));
        JCommandButton removeNode = new JCommandButton("删除节点", MediaResource.getResizableIcon("03705.ico"));
        cleanBand.addCommandButton(removeNode, RibbonElementPriority.MEDIUM);
        removeNode.getActionModel().setActionCommand(Command.Remove.name());
        removeNode.addActionListener(this);
        
        JCommandButton clearNode = new JCommandButton("清除节点", MediaResource.getResizableIcon("03705.ico"));
        cleanBand.addCommandButton(clearNode, RibbonElementPriority.MEDIUM);
        clearNode.getActionModel().setActionCommand(Command.Clear.name());
        clearNode.addActionListener(this);
        
        JCommandButton property  = new JCommandButton("查model属性", MediaResource.getResizableIcon("03705.ico"));
//        cleanBand.addCommandButton(property, RibbonElementPriority.MEDIUM);
        property.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TreePath treePath = TreePanel.getTree().getSelectionPath();
				if (treePath != null) {
					if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode) {
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
						Object objectModel = treeNode.getUserObject();
						if (objectModel instanceof ConditionalPageMasterReferenceModel) {
							ConditionalPageMasterReferenceModel model = (ConditionalPageMasterReferenceModel) objectModel;
							System.out.println(model.getAllProperties());
						}
					}
				}
			}
        });
        
        RibbonTask addTask = new RibbonTask("页布局序列节点"/*"page-sequence-master"*/, jRibbonBand, cleanBand);
        
//        RibbonTask removeTask = new RibbonTask("移除页布局序列节点"/*"remove"*/, cleanBand);
        jRibbon.addTask(addTask);
//        jRibbon.addTask(removeTask);
        
        //设置成自定义RibbonUI
        jRibbon.setUI(new DialogRibbonUI());
	}
	
	public JComponent getToolBar(){
		return jRibbon;
	}
	
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (Command.Add_PageSequenceMaster.name().equals(command)) {
        	treePanel.addPageSequenceMasterNode(new PageSequenceMasterModel("页布局序列"/*"page sequence master name"*/));
		} else if (Command.Add_SPMasterRef.name().equals(command)) {
			treePanel.addSinglePageMasterReferenceNode(new SinglePageMasterReferenceModel());
		} else if (Command.Add_RPMasterRef.name().equals(command)) {
			treePanel.addRepeatablePageMasterReferenceNode(new RepeatablePageMasterReferenceModel());
		} else if (Command.Add_RPMasterAlter.name().equals(command)) {
			treePanel.addRepeatablePageMasterAlternativesNode(new RepeatablePageMasterAlternativesModel());
		} else if (Command.Add_CPMasterRef.name().equals(command)) {
			treePanel.addConditionalPageMasterReferenceNode(new ConditionalPageMasterReferenceModel());
		} else if (Command.Remove.name().equals(command)) {
			//Remove button clicked
            treePanel.removeCurrentNode();
		} else if (Command.Clear.name().equals(command)) {
			//Clear button clicked.
            treePanel.clear();
		}
    }
    
    public static void updateUIState(Object userObject){
    	if (userObject instanceof PageSequenceMasterModel) {
    		javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                	psmb.setEnabled(true);
            		spmrb.setEnabled(true);
            		rpmrb.setEnabled(true);
            		rpmab.setEnabled(true);
            		cpmrb.setEnabled(false);
                }
            });
		} else if (userObject instanceof SinglePageMasterReferenceModel) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	psmb.setEnabled(true);
	        		spmrb.setEnabled(false);
	        		rpmrb.setEnabled(false);
	        		rpmab.setEnabled(false);
	        		cpmrb.setEnabled(false);
	            }
	        });
		} else if (userObject instanceof RepeatablePageMasterReferenceModel) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	psmb.setEnabled(true);
	        		spmrb.setEnabled(false);
	        		rpmrb.setEnabled(false);
	        		rpmab.setEnabled(false);
	        		cpmrb.setEnabled(false);
	            }
	        });
		} else if (userObject instanceof RepeatablePageMasterAlternativesModel) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	psmb.setEnabled(true);
	        		spmrb.setEnabled(false);
	        		rpmrb.setEnabled(false);
	        		rpmab.setEnabled(false);
	        		cpmrb.setEnabled(true);
	            }
	        });
		} else if (userObject instanceof ConditionalPageMasterReferenceModel) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	psmb.setEnabled(true);
	        		spmrb.setEnabled(false);
	        		rpmrb.setEnabled(false);
	        		rpmab.setEnabled(false);
	        		cpmrb.setEnabled(false);
	            }
	        });
		}
    }
}
