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
package com.wisii.wisedoc.view.ui.ribbon.basicband;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 缩进面板
 * @author 闫舒寰
 * @version 1.0 2009/02/09
 */
public class IndentPanel extends JPanel {

	private FixedLengthSpinner endIndentValue;
	private FixedLengthSpinner startIndentValue;
	/**
	 * Create the panel
	 */
	public IndentPanel() {
		super();
//		setBorder(new TitledBorder(null, "缩进", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		
//		FixedLength currentFontSize =
//			(FixedLength)RibbonUIModel.getReadCompletePropertiesByType().get(Font.getType()).get(Constants.PR_FONT_SIZE);
		
		JLabel label;
		{
			label = new JLabel();
			label.setText(RibbonUIText.PARAGRAPH_INDENT_START);
		}
		{
			FixedLength minlen = new FixedLength(-9000d,"pt");
			FixedLength maxlen = new FixedLength(9000d,"pt");
			FixedLength deflen = new FixedLength(0d,"pt");
			startIndentValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(deflen,minlen,maxlen,-1));
//			SpinnerNumberModel startIndentModel = new SpinnerNumberModel(0.0, null, null, 0.5);
//			startIndentValue.setModel(startIndentModel);
		}
		JLabel label_1;
		{
			label_1 = new JLabel();
			label_1.setText(RibbonUIText.PARAGRAPH_INDENT_END);
		}
		{
			FixedLength minlen = new FixedLength(-9000d,"pt");
			FixedLength maxlen = new FixedLength(9000d,"pt");
			FixedLength deflen = new FixedLength(0d,"pt");
			endIndentValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(deflen,minlen,maxlen,-1));
//			SpinnerNumberModel endIndentModel = new SpinnerNumberModel(0.0, null, null, 0.5);
//			endIndentValue.setModel(endIndentModel);
		}
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6, 6, 6)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(startIndentValue, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(endIndentValue, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1, 1, 1)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(startIndentValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(endIndentValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}
	public Object getEndIndentValue() {
		return endIndentValue;
	}
	public Object getStartIndentValue() {
		return startIndentValue;
	}
}
