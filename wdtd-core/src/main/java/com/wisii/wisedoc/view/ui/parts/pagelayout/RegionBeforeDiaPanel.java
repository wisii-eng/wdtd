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
package com.wisii.wisedoc.view.ui.parts.pagelayout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.util.UiUtil;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.WisedocEditComponent;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel.SPMLayoutType;
import com.wisii.wisedoc.view.ui.text.UiText;
import com.wisii.wisedoc.view.ui.update.RegionDocumentManager;

/**
 * 页眉属性设置的总面板
 * @author 闫舒寰
 * @version 1.0 2009/02/19
 */
public class RegionBeforeDiaPanel extends JPanel implements UpdateSPMProperty {
	
	private SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
//	private SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getMainSPMM();
	
//	HeaderDiaPanel propertyPanel = new HeaderDiaPanel();
	
	JPanel previewPanel;
	
	public RegionBeforeDiaPanel() {
		
		setLayout(new BorderLayout());
		
		previewPanel = new PreviewPanel();
		
		this.add(new NorthPanel(), BorderLayout.NORTH);
		
//		this.add(propertyPanel, BorderLayout.CENTER);
		
		this.add(previewPanel, BorderLayout.CENTER);
		
//		this.add(new SimplePageMasterListPanel(), BorderLayout.EAST);
	}
	
	private class NorthPanel extends JPanel	{

		private WiseCombobox comboBox;
		private JCheckBox checkBox;
		private JButton button;
//		private JTextField flowName;
		
		/**
		 * Create the panel
		 */
		public NorthPanel() {
			super();

			
			checkBox = new JCheckBox();
			checkBox.setText(UiText.ADD_REGION_BEFORE);
			
			JLabel label_1;
			label_1 = new JLabel();
			label_1.setText(UiText.APPLY_FLOW);

			comboBox = new WiseCombobox();
			
			
			button = new JButton();
			button.setText(UiText.APPLY_CURRENT_LAYOUT);
			
			AddRegionBeforeAction arba = new AddRegionBeforeAction();
			checkBox.addActionListener(arba);
			arba.setDefaultState();
			
			AddFlowAction afa = new AddFlowAction();
			button.addActionListener(afa);
			afa.setDefaultState();
			
			CorrespondingFLowAction cfa = new CorrespondingFLowAction();
			comboBox.addActionListener(cfa);
			cfa.setDefaultState();
			
//			flowName = new JTextField();
			
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(checkBox)
						.addGap(18, 18, 18)
						.addComponent(label_1)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(22, 22, 22)
						.addComponent(button)
						/*.addGap(18, 18, 18)
						.addComponent(flowName)*/
						.addContainerGap(25, Short.MAX_VALUE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(checkBox)
							.addComponent(label_1)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(button)
							/*.addComponent(flowName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)*/)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			setLayout(groupLayout);
			//
		}
		
		private class AddRegionBeforeAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (checkBox.isSelected()) {
					//创建一个默认页眉
//					spmm.setRegionBefore(new RegionBeforeModel.Builder().defaultRegionBefore().build().getRegionBefore());
					
					//更新界面
					if (previewComponent instanceof WisedocEditComponent) {
						WisedocEditComponent wec = (WisedocEditComponent) previewComponent;
						wec.setDocument(RegionDocumentManager.Instance.
								createRegionContent(Constants.FO_REGION_BEFORE, "region" + RegionDocumentManager.getUniqueID()));
					}
					
					//设置版心的margin为默认页脚的高
//					SinglePagelayoutModel.Instance.getMainSPMM().getRegionBodyModel().setMarginTop(spmm.getRegionBeforeModel().getExtent());
					
					comboBox.setEnabled(true);
					button.setEnabled(true);
					previewPanel.setVisible(true);
				} else {
					spmm.setRegionBefore(null);
					
					RegionDocumentManager.Instance.deleteRegion(Constants.FO_REGION_BEFORE);
					
					//把页眉所占的版心距离设置回来
					SinglePagelayoutModel.Instance.getMainSPMM().getRegionBodyModel().setMarginTop(0d, "cm");
					
					comboBox.setEnabled(false);
					button.setEnabled(false);
					previewPanel.setVisible(false);
				}
//				propertyPanel.update();
			}
			
			public void setDefaultState(){
				if (spmm.getRegionBeforeModel() != null) {
					checkBox.setSelected(true);
					
					comboBox.setEnabled(true);
					button.setEnabled(true);
					previewPanel.setVisible(true);
				} else {
					checkBox.setSelected(false);
					
					comboBox.setEnabled(false);
					button.setEnabled(false);
					previewPanel.setVisible(false);
				}
			}
		}
		
		private DefaultComboBoxModel flowListComboBoxModel;
		
		private class CorrespondingFLowAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Document doc = RegionDocumentManager.Instance.getRegionDocumentsMap().get(Constants.FO_REGION_BEFORE);
				
				PageSequence ps = (PageSequence)doc.getChildAt(0);
				
				SimplePageMaster spm = (SimplePageMaster) ps.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);
				
				SimplePageMasterModel spmm = new SimplePageMasterModel.Builder().simplePageMaster(spm).build();
				
				String selectedName = (String)comboBox.getSelectedItem();
				
				spmm.getRegionBeforeModel().setRegionName(selectedName);
				
//				System.out.println("region name: " + spmm.getRegionBeforeModel().getRegionName());
				
				ps.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, spmm.getSimplePageMaster());
				
				ps.removeAllChildren();
				
				ps.add(RegionDocumentManager.Instance.getRegionFlowsMap().get(selectedName));
				
				if (previewComponent instanceof WisedocEditComponent) {
					WisedocEditComponent wec = (WisedocEditComponent) previewComponent;
					wec.setDocument(doc);
				}
			}
			
			public void setDefaultState(){
				
				List<String> modelString = new ArrayList<String>();
				
				for (StaticContent sc : RegionDocumentManager.Instance.getRegionFlows()) {
					modelString.add(sc.getFlowName());
				}
				
				flowListComboBoxModel = new DefaultComboBoxModel(modelString.toArray());
				comboBox.setModel(flowListComboBoxModel);
				
				if (spmm.getRegionBeforeModel() != null) {
					comboBox.InitValue(spmm.getRegionBeforeModel().getRegionName());
				}
			}
		}
		
		private class AddFlowAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				SimplePageMasterModel spmmTemp = SinglePagelayoutModel.Instance.getSinglePageLayoutModel(SPMLayoutType.setFO);
				
				String regionName = spmmTemp.getRegionBeforeModel().getRegionName();
				
				String newName = regionName + RegionDocumentManager.getUniqueID();
				
				StaticContent sc = RegionDocumentManager.Instance.getRegionFlowsMap().get(regionName);
				
				sc.setAttribute(Constants.PR_FLOW_NAME, newName);
				
				spmmTemp.getRegionBeforeModel().setRegionName(newName);
			}
			
			public void setDefaultState(){
				
				List<String> modelString = new ArrayList<String>();
				
				for (StaticContent sc : UiUtil.getRegionFlows()) {
					modelString.add(sc.getFlowName());
				}
				
				DefaultComboBoxModel dcb = new DefaultComboBoxModel(modelString.toArray());
				comboBox.setModel(dcb);
				
				if (spmm.getRegionAfterModel() != null) {
					comboBox.InitValue(spmm.getRegionAfterModel().getRegionName());
				}
			}
		}
	}
	
	private class PreviewPanel extends JPanel{
		
		public PreviewPanel() {
			
			setLayout(new BorderLayout());
			
			/*JLabel label = new JLabel("预览区");
			this.add(label);*/
			
//			this.add(UiUtil.borrowRibbonUI(), BorderLayout.NORTH);
			
			setPreferredSize(new Dimension(100, 200));
			
//			JScrollPane jsp = new JScrollPane(UIDocumentManager.Instance.getRegionPreviewComponet(Constants.FO_REGION_BEFORE));
//			this.add(jsp, BorderLayout.CENTER);
		}
		
	}
	
	private static JComponent previewComponent;

	@Override
	public void update() {
//		propertyPanel.update();
		
		previewPanel.removeAll();
		previewPanel.add(UiUtil.borrowRibbonUI(), BorderLayout.NORTH);
		previewComponent = RegionDocumentManager.Instance.getRegionPreviewComponent(Constants.FO_REGION_BEFORE);
		JScrollPane jsp = new JScrollPane(previewComponent);
		/* 【添加：START】 by 李晓光   2009-10-28 */
		WisedocUtil.disableScroPaneKeyborde(jsp);
		/* 【添加：END】 by 李晓光   2009-10-28 */
//		previewPanel.add(jsp, BorderLayout.CENTER);
//		previewPanel.add(new SimplePageMasterListPanel(), BorderLayout.EAST);
		
//		RegionFlowListPanel rfp = new RegionFlowListPanel();
//		rfp.setPreferredSize(new Dimension(40, 200));
		
		/*JSplitPane jsp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp, rfp);
		jsp2.setOneTouchExpandable(true);
		jsp2.setDividerLocation(0.8);*/
		
		previewPanel.add(jsp, BorderLayout.CENTER);
		
		UiUtil.updateUI(previewPanel);
	}

}
