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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.model.MultiPageSequenceModel;

public class SimplePageMasterPanel extends JPanel {
	
	JRadioButton simplepagemasterRadioButton;
	JLabel masterNameLabel;
	
	public SimplePageMasterPanel(JRadioButton simplepagemasterRadioButton) {
		super();

//		simplepagemasterRadioButton = new JRadioButton();
		this.simplepagemasterRadioButton = simplepagemasterRadioButton;
//		simplepagemasterRadioButton.setText("simple-page-master");

		
		masterNameLabel = new JLabel();
		masterNameLabel.setText("master name:");
		
		masterNameLabel.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				//把当前改动设置到当前引用的对象的属性
				MultiPageSequenceModel.MultiPageSeqModel.getCurrentSequenceProperty().put(Constants.PR_SIMPLE_PAGE_MASTER, evt.getNewValue());
			}
			
		});
		
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(simplepagemasterRadioButton)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(masterNameLabel)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(simplepagemasterRadioButton)
						.addComponent(masterNameLabel))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
	
	public JLabel getSimplePageMasterNameLabel() {
		return masterNameLabel;
	}
	
}
