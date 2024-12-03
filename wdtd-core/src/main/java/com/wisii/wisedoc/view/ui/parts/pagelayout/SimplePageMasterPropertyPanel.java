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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.wisii.wisedoc.view.ui.model.MultiPagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel;
import com.wisii.wisedoc.view.ui.text.Messages;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 页布局属性详细设置菜单
 * @author 闫舒寰
 * @version 1.0 2009/01/20
 */
public class SimplePageMasterPropertyPanel extends JPanel {
	
	public SimplePageMasterPropertyPanel() {
		setLayout(new BorderLayout());
		
		add(new SimplePageMasterName(), BorderLayout.NORTH);
		
		add(new SPMPanel(), BorderLayout.CENTER);
	}
}


class SimplePageMasterName extends JPanel {
	
//	SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getSinglePageLayoutModel();
	
	SimplePageMasterModel spmm = SinglePagelayoutModel.Instance.getMainSPMM();

	private JTextField textField;
	/**
	 * Create the panel
	 */
	public SimplePageMasterName() {
		super();

		JLabel label;
		label = new JLabel();
		label.setText(UiText.PAGE_SIMPLE_MASTER_NAME);

		textField = new JTextField();
		
		textField.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(final DocumentEvent e) {
				spmm.setMasterName(textField.getText());
			}

			@Override
			public void insertUpdate(final DocumentEvent e) {
				spmm.setMasterName(textField.getText());
			}

			@Override
			public void removeUpdate(final DocumentEvent e) {
				spmm.setMasterName(textField.getText());
			}
			
		});
		
		final MasterNameAction masterNameAction = new MasterNameAction();
		textField.addActionListener(masterNameAction);
		masterNameAction.setDefaultState();
		
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE/*GroupLayout.PREFERRED_SIZE*//*90*/, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}
	
	private class MasterNameAction implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			spmm.setMasterName(textField.getText());
		}
		
		public void setDefaultState(){
			
			if (spmm.getMasterName() == null || spmm.getMasterName().equals("")) {
				textField.setText(Messages.getString("SimplePageMasterPropertyPanel.0") + " " + MultiPagelayoutModel.getCount());
			} else {
				textField.setText(spmm.getMasterName());
			}
		}
	}
}
