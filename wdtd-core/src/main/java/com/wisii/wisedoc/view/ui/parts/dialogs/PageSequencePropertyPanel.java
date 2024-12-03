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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.model.MultiPageSequenceModel;
import com.wisii.wisedoc.view.ui.model.MultiPagelayoutModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmlist.TreePanelSPMList;

public class PageSequencePropertyPanel extends JPanel {
	
//	static int count;

//	private JComboBox comboBox_3;
//	private JComboBox comboBox_2;
	private JComboBox comboBox_1;
	private JSpinner spinner;
	private JComboBox comboBox;
	
	//存放simple page master的master name的面板
	SimplePageMasterPanel simplePageMasterPanel;
	JLabel simplePageMasterNameLabel;
	//存放page sequence master的master name的面板
	PageSequenceMasterPanel pageSequenceMasterPanel;
	JLabel pageSequenceMasterNameLabel;
	
	private JRadioButton simplepagemasterRadioButton;
	private JRadioButton pagesequencemasterRadioButton;
	
	private static PageMasterSelectionAction pageMasterSelectionAction;
	private static InitialPageNumberAction initialPageNumberAction;
	private static ForcePageCountAction forcePageCountAction;
	
	public static void updateUIState(){
		pageMasterSelectionAction.setDefalutState();
		initialPageNumberAction.setDefalutState();
		forcePageCountAction.setDefalutState();
	}
	
	/**
	 * Create the panel
	 */
	public PageSequencePropertyPanel() {
		super();
		/*count += 1;
		System.out.println(count);*/
		
		/**************sequence master的radio button设置开始************************************/
		JLabel masterreferenceLabel;
		masterreferenceLabel = new JLabel();
		masterreferenceLabel.setText("master-reference");

		ButtonGroup buttonGroup = new ButtonGroup();
		simplepagemasterRadioButton = new JRadioButton();
		simplepagemasterRadioButton.setText("simple-page-master");
		buttonGroup.add(simplepagemasterRadioButton);
		
		pagesequencemasterRadioButton = new JRadioButton();
		pagesequencemasterRadioButton.setText("page-sequence-master");
		buttonGroup.add(pagesequencemasterRadioButton);
		
		simplePageMasterPanel = new SimplePageMasterPanel(simplepagemasterRadioButton);
		simplePageMasterNameLabel = simplePageMasterPanel.getSimplePageMasterNameLabel();
		pageSequenceMasterPanel = new PageSequenceMasterPanel(pagesequencemasterRadioButton);
		pageSequenceMasterNameLabel = pageSequenceMasterPanel.getPageSequenceMasterNameLabel();
		CustomLayoutManager.getInstance().initialSimplePageMasterNameLabel(simplePageMasterNameLabel);
		CustomLayoutManager.getInstance().initialPageSequenceMasterNameLabel(pageSequenceMasterNameLabel);
		
		pageMasterSelectionAction = new PageMasterSelectionAction();
		simplepagemasterRadioButton.addActionListener(pageMasterSelectionAction);
		pagesequencemasterRadioButton.addActionListener(pageMasterSelectionAction);
		pageMasterSelectionAction.setDefalutState();
		/**************sequence master的radio button设置结束************************************/
		
		JLabel initialpagenumberLabel;
		initialpagenumberLabel = new JLabel();
		initialpagenumberLabel.setText("initial-page-number");

		comboBox = new JComboBox(new DefaultComboBoxModel(new String[]{"auto", "auto-odd", "auto-even", "number"}));

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 0, null, 1));
		
		initialPageNumberAction = new InitialPageNumberAction();
		comboBox.addActionListener(initialPageNumberAction);
		spinner.addChangeListener(initialPageNumberAction);
		initialPageNumberAction.setDefalutState();

		JLabel forcepagecountLabel;
		forcepagecountLabel = new JLabel();
		forcepagecountLabel.setText("force-page-count");

		comboBox_1 = new JComboBox(new DefaultComboBoxModel(new String[]{"auto", "even", "odd", "end-on-even", "end-on-odd", "no-force"}));
		forcePageCountAction = new ForcePageCountAction();
		comboBox_1.addActionListener(forcePageCountAction);
		forcePageCountAction.setDefalutState();

		/*JLabel referenceorientationLabel;
		referenceorientationLabel = new JLabel();
		referenceorientationLabel.setText("reference-orientation");*/

//		comboBox_2 = new JComboBox(new DefaultComboBoxModel(new String[]{"0", "90", "180", "270", "-90", "-180", "-270"}));

		/*JLabel writingmodeLabel;
		writingmodeLabel = new JLabel();
		writingmodeLabel.setText("writing-mode");*/

//		comboBox_3 = new JComboBox(new DefaultComboBoxModel(new String[]{"lr-tb", "rl-tb", "tb-rl", "tb-lr", "bt-lr", "bt-rl", "lr-bt"}));
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						/*.addGroup(groupLayout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))*/
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addComponent(masterreferenceLabel)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(pageSequenceMasterPanel)
								.addComponent(simplePageMasterPanel)))
						.addComponent(initialpagenumberLabel)
						.addComponent(forcepagecountLabel)
						/*.addComponent(referenceorientationLabel)
						.addComponent(writingmodeLabel)*/)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(masterreferenceLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(simplePageMasterPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(pageSequenceMasterPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(12, 12, 12)
					.addComponent(initialpagenumberLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20, 20, 20)
					.addComponent(forcepagecountLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(17, 17, 17)
//					.addComponent(referenceorientationLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//					.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(21, 21, 21)
//					.addComponent(writingmodeLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//					.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
		
	}
	
	private class PageMasterSelectionAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (simplepagemasterRadioButton.isSelected()) {
				updateSimplePageMasterUI();
			} 
			
			if (pagesequencemasterRadioButton.isSelected()) {
				updatePageSequenceMasterUI();
			}
		}
		
		public void setDefalutState(){
			
			if (MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty() == null) {
				return;
			}
			
			Object spm = MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty().get(Constants.PR_SIMPLE_PAGE_MASTER);
			
//			System.out.println(temp);
			
			if (spm != null /*&& spm instanceof SimplePageMaster*/){
				
				/*if (temp instanceof String) {
					String value = (String) temp;
					MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty().put(Constants.PR_SIMPLE_PAGE_MASTER, );
				}*/
				simplepagemasterRadioButton.setSelected(true);
				
				updateSimplePageMasterUI();
			}
			
			Object psm = MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty().get(Constants.PR_PAGE_SEQUENCE_MASTER);
			
			if (psm != null && psm instanceof PageSequenceMaster){
				pagesequencemasterRadioButton.setSelected(true);
				
				updatePageSequenceMasterUI();
			}  
		}
		
		private void updateSimplePageMasterUI(){
			
			TreePanelSPMList.getInstance().getList().setEnabled(true);
			
			simplePageMasterNameLabel.setEnabled(true);
			pageSequenceMasterNameLabel.setEnabled(false);
			
			/*simplePageMasterNameLabel.setVisible(true);
			pageSequenceMasterNameLabel.setVisible(false);*/
			
			
			//更新界面面板
			CustomLayoutManager.getInstance().getSimplePageMasterPanel();
			
			updateUIListener();
			
//			simplePageMasterNameLabel.getText()
			
			//先把当前所选的page sequence的master name设置到simplePageMasterNameLabel上
			Object currentSimplePageMaster = MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty().get(Constants.PR_SIMPLE_PAGE_MASTER);
			
//			System.out.println(MultiPagelayoutModel.MultiPageLayout.);
			
			if (currentSimplePageMaster instanceof SimplePageMaster) {
				SimplePageMaster spm = (SimplePageMaster) currentSimplePageMaster;
				simplePageMasterNameLabel.setText(MultiPagelayoutModel.MultiPageLayout.getSystemSimplePageMasterName(spm));
			}
			//为了不每次都创建一个simple page master， 这里暂存simple page master都直接存名字
			if (currentSimplePageMaster instanceof String) {
				String value = (String) currentSimplePageMaster;
				simplePageMasterNameLabel.setText(value);
			}
			
			TreePanelSPMList.getInstance().getList().setSelectedValue(simplePageMasterNameLabel.getText(), true);
			
		}
		
		private void updatePageSequenceMasterUI(){
			simplePageMasterNameLabel.setEnabled(false);
			pageSequenceMasterNameLabel.setEnabled(true);
			
			//设置simple page master的引用不可用
//			MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty().put(Constants.PR_SIMPLE_PAGE_MASTER, null);
			
			/*simplePageMasterNameLabel.setVisible(false);
			pageSequenceMasterNameLabel.setVisible(true);*/
			
			//更新界面面板
			CustomLayoutManager.getInstance().getPageSequenceMasterPanel();
			updateUIListener();
		}
		
		private void updateUIListener(){
			//更新master name的监听器
			CustomLayoutManager.getInstance().updateMasterNameListener();
		}
		
	}
	
	private class InitialPageNumberAction implements ActionListener, ChangeListener {

		@Override
		public void actionPerformed(ActionEvent e) {
//			new EnumNumber(Constants.EN_AUTO, null);
			
			Map<Integer, Object> pro = new HashMap<Integer, Object>();
			
			switch (comboBox.getSelectedIndex()) {
			case 0:
				pro.put(Constants.PR_INITIAL_PAGE_NUMBER, new EnumNumber(Constants.EN_AUTO, null));
				spinner.setEnabled(false);
				setProperty(pro);
				break;
			case 1:
				pro.put(Constants.PR_INITIAL_PAGE_NUMBER, new EnumNumber(Constants.EN_AUTO_ODD, null));
				spinner.setEnabled(false);
				setProperty(pro);
				break;
			case 2:
				pro.put(Constants.PR_INITIAL_PAGE_NUMBER, new EnumNumber(Constants.EN_AUTO_EVEN, null));
				spinner.setEnabled(false);
				setProperty(pro);
				break;
			case 3:
				spinner.setEnabled(true);
				break;

			default:
				break;
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			Map<Integer, Object> pro = new HashMap<Integer, Object>();
			pro.put(Constants.PR_INITIAL_PAGE_NUMBER, new EnumNumber(-1, (Integer) spinner.getValue()));
			setProperty(pro);
		}
		
		private void setProperty(Map<Integer, Object> property){
//			Document doc = SystemManager.getCurruentDocument();
//			MultiPageSequenceModel.MultiPageSeqModel.getCurrentSelectingSequence().setAttributes(property, false);
//			doc.setElementAttributes(MultiPageSequenceModel.MultiPageSeqModel.getCurrentSelectingSequence(), property, false);
			
			MultiPageSequenceModel.MultiPageSeqModel.setPageSequenceProperty(property);
		}
		
		public void setDefalutState(){
			if (MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty() == null) {
				return;
			}
			
			Object o = MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty().get(Constants.PR_INITIAL_PAGE_NUMBER);
			
			if (o instanceof EnumNumber) {
				EnumNumber value = (EnumNumber) o;
//				System.out.println(value.getEnum());
//				System.out.println(value.getNumber());
				
				if (value.getEnum() == -1) {
					comboBox.setSelectedIndex(3);
					spinner.setEnabled(true);
					spinner.setValue(value.getNumber());
					comboBox.updateUI();
				}
				
				if (value.getEnum() == Constants.EN_AUTO) {
					comboBox.setSelectedIndex(0);
					spinner.setEnabled(false);
//					spinner.setValue();
				} else if (value.getEnum() == Constants.EN_AUTO_ODD) {
					comboBox.setSelectedIndex(1);
					spinner.setEnabled(false);
//					spinner.setValue(null);
				} else if (value.getEnum() == Constants.EN_AUTO_EVEN) {
					comboBox.setSelectedIndex(2);
					spinner.setEnabled(false);
//					spinner.setValue(null);
				}
			}
		}
	}
	
	private class ForcePageCountAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Map<Integer, Object> pro = new HashMap<Integer, Object>();
			
			switch (comboBox_1.getSelectedIndex()) {
			case 0:
				pro.put(Constants.PR_FORCE_PAGE_COUNT, new EnumProperty(Constants.EN_AUTO, null));
				setProperty(pro);
				break;
			case 1:
				pro.put(Constants.PR_FORCE_PAGE_COUNT, new EnumProperty(Constants.EN_EVEN, null));
				setProperty(pro);
				break;
			case 2:
				pro.put(Constants.PR_FORCE_PAGE_COUNT, new EnumProperty(Constants.EN_ODD, null));
				setProperty(pro);
				break;
			case 3:
				pro.put(Constants.PR_FORCE_PAGE_COUNT, new EnumProperty(Constants.EN_END_ON_EVEN, null));
				setProperty(pro);
				break;
			case 4:
				pro.put(Constants.PR_FORCE_PAGE_COUNT, new EnumProperty(Constants.EN_END_ON_ODD, null));
				setProperty(pro);
				break;
			case 5:
				pro.put(Constants.PR_FORCE_PAGE_COUNT, new EnumProperty(Constants.EN_NO_FORCE, null));
				setProperty(pro);
				break;

			default:
				break;
			}
		}
		
		private void setProperty(Map<Integer, Object> property){
			MultiPageSequenceModel.MultiPageSeqModel.setPageSequenceProperty(property);
		}
		
		public void setDefalutState(){
			if (MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty() == null) {
				return;
			}
			
			Object o = MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty().get(Constants.PR_FORCE_PAGE_COUNT);
			
			if (o instanceof EnumProperty) {
				EnumProperty value = (EnumProperty) o;
				
				if (value.getEnum() == Constants.EN_AUTO) {
					comboBox_1.setSelectedIndex(0);
				} else if (value.getEnum() == Constants.EN_EVEN) {
					comboBox_1.setSelectedIndex(1);
				} else if (value.getEnum() == Constants.EN_ODD) {
					comboBox_1.setSelectedIndex(2);
				} else if (value.getEnum() == Constants.EN_END_ON_EVEN) {
					comboBox_1.setSelectedIndex(2);
				} else if (value.getEnum() == Constants.EN_END_ON_ODD) {
					comboBox_1.setSelectedIndex(2);
				} else if (value.getEnum() == Constants.EN_NO_FORCE) {
					comboBox_1.setSelectedIndex(2);
				}
			}
		}
	}

}
