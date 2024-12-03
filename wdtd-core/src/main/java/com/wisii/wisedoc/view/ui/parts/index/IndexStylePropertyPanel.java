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
package com.wisii.wisedoc.view.ui.parts.index;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.index.IndexStylesModel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 目录属性面板
 * @author 闫舒寰
 * @version 1.0 2009/04/13
 */
public class IndexStylePropertyPanel extends JPanel {

//	private JTextField name;
	
	final JTextPane propertyDescription;
	
	/**
	 * Create the panel
	 */
	public IndexStylePropertyPanel() {
		super();

//		JLabel label;
//		label = new JLabel();
//		label.setText("段落属性名：");

//		name = new JTextField();

//		JButton reNameButton;
//		reNameButton = new JButton();
//		reNameButton.setText("更名");

		JButton fontButton;
		fontButton = new JButton();
		fontButton.setText(UiText.INDEX_FONT_PROPERTY);
		fontButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				new FontPropertyDialog(ActionType.INLINE);
			}
		});

		JButton paragraphButton;
		paragraphButton = new JButton();
		paragraphButton.setText(UiText.INDEX_PARAGRAPH_PROPERTY);
		paragraphButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				new ParagraphPropertyDialog(ActionType.BLOCK);
			}
		});

		//属性和预览面板
//		JPanel panel;
//		panel = new JPanel();
//		panel.setLayout(new BorderLayout());
//		panel.setSize(270, 174);
//		panel.setBorder(new TitledBorder(null, UiText.INDEX_PROPERTY_DESCRIPTION + "和预览", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

//		final JPanel iPreview = new JPanel();
//		iPreview.setBorder(new TitledBorder(null, UiText.INDEX_PROPERTY_DESCRIPTION, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
//		iPreview.setLayout(new BorderLayout());
//		iPreview.add(propertyDescription, BorderLayout.CENTER);
		
//		final JComponent previewComponent = RegionDocumentManager.Instance.getRegionPreviewComponent(Constants.FO_REGION_BODY);
//
//		final Document doc = RegionDocumentManager.Instance.getRegionDocumentsMap().get(Constants.FO_REGION_BODY);
//
//		final PageSequence ps = (PageSequence)doc.getChildAt(0);
//
//		final SimplePageMaster spm = (SimplePageMaster) ps.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);
//
//		final SimplePageMasterModel spmm = new SimplePageMasterModel.Builder().simplePageMaster(spm).build();
//
//		final String selectedName = "region-body";
//		spmm.getRegionBeforeModel().setRegionName(selectedName);
		
		
//		ps.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, spmm.getSimplePageMaster());
//
//		ps.removeAllChildren();
//
//		ps.add(RegionDocumentManager.Instance.getRegionFlowsMap().get(selectedName));
//
//		if (previewComponent instanceof WisedocEditComponent) {
//			final WisedocEditComponent wec = (WisedocEditComponent) previewComponent;
//			wec.setDocument(doc);
//		}
//		iPreview.add(previewComponent, BorderLayout.CENTER);
		
//		final JScrollPane jsp = new JScrollPane(iPreview);
		
		//预览主面板
//		panel.add(jsp, BorderLayout.CENTER);
		
		//属性文字
		propertyDescription = new JTextPane();
		propertyDescription.setEditable(false);
		
		final JPanel pText = new JPanel();
		pText.setBorder(new TitledBorder(null, UiText.INDEX_PROPERTY_DESCRIPTION, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		pText.setLayout(new BorderLayout());
		
		pText.add(propertyDescription, BorderLayout.CENTER);
		
		//属性面板
//		panel.add(pText, BorderLayout.SOUTH);
//		panel.add(pText, BorderLayout.CENTER);
		
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(pText, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
						.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
//							.addComponent(label)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//							.addComponent(name, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							/*.addGap(23, 23, 23)
							.addComponent(reNameButton)*/)
						.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(fontButton)
							.addGap(20, 20, 20)
							.addComponent(paragraphButton)))
					.addGap(25, 25, 25))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//						.addComponent(label)
//						.addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						/*.addComponent(reNameButton)*/)
					.addGap(28, 28, 28)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(fontButton)
						.addComponent(paragraphButton))
					.addGap(26, 26, 26)
					.addComponent(pText, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(45, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}
	
	public void setPName(final String pName) {
		final Map<Integer, Object> temp = IndexStylesModel.Instance.getPsProMap(pName);
		
		if (temp != null) {
			propertyDescription.setText(pName + temp.toString());
		}
	}
	
	
	private Map<Integer, Object> filterMap(final Map<Integer, Object> map){
		final Integer[] keys = {Constants.PR_FONT_FAMILY, Constants.PR_FONT_SIZE, Constants.PR_FONT_STYLE, Constants.PR_FONT_WEIGHT, Constants.PR_TEXT_DECORATION, Constants.PR_COLOR};
		/*Set<Integer> keys = map.keySet();*/
		final Map<Integer, Object> result = new HashMap<Integer, Object>();
		for (final Integer key : keys) {
			result.put(key, map.get(key));
		}
		return result;
	}

}
