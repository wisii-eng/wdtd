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
import java.awt.Container;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.wisii.wisedoc.view.ui.model.MultiPageSequenceModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmlist.PageSequenceList;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmlist.TreePanelSPMList;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.PSMasterPanel;

public class CustomLayoutManager {
	
//	CustomLayoutManager;
	
	private transient static final CustomLayoutManager Instance = new CustomLayoutManager();
	
	private CustomLayoutManager() {}
	
	public static CustomLayoutManager getInstance(){
		return Instance;
	}
	
	private JList pageSequenceList;
	
	private PSMasterPanel pageSequenceM = new PSMasterPanel();
	
	JPanel sequenceMasterPanel ;
	
	JPanel psp;
	
	//page sequence整体上的属性
	private JPanel pageSeqPropertyPanel ;
	
	/**
	 * 1、最先初始化page sequence列表
	 * @return
	 */
	public JPanel getPageSequenceListPanel(){
		PageSequenceList pageSequenceListPanel = new PageSequenceList();
		pageSequenceList = pageSequenceListPanel.getPageSequenceList();
		
		iniPSPPanel();
		sequenceMasterPanel = new JPanel(new BorderLayout());
		
		//这里应该指定当前list的选择的初始值，并在模型层中指定当前所选的page sequence
		MultiPageSequenceModel.MultiPageSeqModel.setCurrentSelectingSequence(pageSequenceList.getSelectedIndex());
		
		return pageSequenceListPanel;
	}
	
	/**
	 * 2、然后根据列表中所选择的对象创建page sequence属性面板
	 * @return
	 */
	public JPanel getPageSequencePropertyPanel(){
		
//		pageSeqPropertyPanel = new JPanel(new BorderLayout());
		
//		checkPSPPanel();
		pageSeqPropertyPanel = new JPanel(new BorderLayout());
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, psp , sequenceMasterPanel);
		pageSeqPropertyPanel.add(jsp, BorderLayout.CENTER);
		return pageSeqPropertyPanel;
	}
	
	
	private PageSequencePropertyPanel pspp;
	/**
	 * 2.1、根据所选的page sequence的属性创建相应的属性面板
	 * @return
	 */
	private void iniPSPPanel(){
		
		if (psp == null) {
			psp = new JPanel(new BorderLayout());
		}
		
		if (pspp == null) {
			pspp = new PageSequencePropertyPanel();
		}
		
		psp.add(pspp, BorderLayout.CENTER);
		/*SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				psp.updateUI();
			}
		});*/
		
//		return psp;
	}
	
	/**
	 * 2.2、根据page sequence属性面板中的radio button来决定sequenceMaster面板上应该放置什么属性面板
	 * @return
	 */
	/*private JComponent getSequenceMasterPanel(){
		if (sequenceMasterPanel == null) {
			sequenceMasterPanel = new JPanel(new BorderLayout());
		}
		return sequenceMasterPanel;
	}*/
	
	public void getPageSequenceMasterPanel(){
		sequenceMasterPanel.removeAll();
		sequenceMasterPanel.add(pageSequenceM, BorderLayout.CENTER);
		getPageSequenceM().addTreePanel();
		doLayout(CustomLayoutDialog.getLayoutDialog());
	}
	
	public void getSimplePageMasterPanel(){
//		System.out.println("hihi");
		sequenceMasterPanel.removeAll();
		sequenceMasterPanel.add(TreePanelSPMList.getInstance(), BorderLayout.CENTER);
		doLayout(CustomLayoutDialog.getLayoutDialog());
	}
	
	private void doLayout(Container container){
		if ((container != null) && (container instanceof Window)) {
			((Window) container).pack();
			container.repaint();
		}
	}
	
	public void checkPSPPanel(){
		
		PageSequencePropertyPanel.updateUIState();
		
	}
	

	public PSMasterPanel getPageSequenceM() {
		return pageSequenceM;
	}

	public JList getPageSequenceList() {
		return pageSequenceList;
	}
	
	private JLabel simplePageMasterNameLabel;
	private JLabel pageSequenceMasterNameLabel;
	
	public void initialSimplePageMasterNameLabel(JLabel nameLabel){
		simplePageMasterNameLabel = nameLabel;
	}
	
	public void initialPageSequenceMasterNameLabel(JLabel nameLabel){
		pageSequenceMasterNameLabel = nameLabel;
	}
	
	private static final JList list = TreePanelSPMList.getInstance().getList();
	private static ListSelectionListener listListener;
	
	public void updateMasterNameListener(){
		
		if (sequenceMasterPanel.getComponentCount() == 1) {
			Object temp = sequenceMasterPanel.getComponent(0);
			//simple page master的列表的监听器
			if (temp instanceof TreePanelSPMList) {
				
				if (listListener != null) {
					list.removeListSelectionListener(listListener);
				}
				
				if (list != null) {
					listListener = new SPMListListener();
					list.addListSelectionListener(listListener);
				}
				
			} else {
				if (listListener != null) {
					list.removeListSelectionListener(listListener);
				}
			}
			//page sequence master的树的监听器
			if (temp instanceof PSMasterPanel) {
				
				
			}
		}
	}
	
	private class SPMListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource() instanceof JList) {
				JList ui = (JList) e.getSource();
				simplePageMasterNameLabel.setText((String)ui.getSelectedValue().toString());
			}
		}
	}

	public JLabel getSimplePageMasterNameLabel() {
		return simplePageMasterNameLabel;
	}

	public JLabel getPageSequenceMasterNameLabel() {
		return pageSequenceMasterNameLabel;
	}
	
	
}
