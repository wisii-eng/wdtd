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
package com.wisii.wisedoc.view.ui.ribbon.pagetask;

import static com.wisii.wisedoc.view.ui.text.UiText.MARGIN_BOTTOM;
import static com.wisii.wisedoc.view.ui.text.UiText.MARGIN_LEFT;
import static com.wisii.wisedoc.view.ui.text.UiText.MARGIN_RIGHT;
import static com.wisii.wisedoc.view.ui.text.UiText.MARGIN_TOP;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_right;

/**
 * 上下左右四边版心边距面板
 * @author 闫舒寰
 * @version 1.0 2009/02/09
 */
public class MarginPanel extends JPanel {

	private FixedLengthSpinner spinner_3;
	private FixedLengthSpinner spinner_2;
	private FixedLengthSpinner spinner_1;
	private FixedLengthSpinner spinner;
	/**
	 * Create the panel
	 */
	public MarginPanel(String title) {
		super();
		setBorder(new TitledBorder(null, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {0,7};
		gridBagLayout.columnWidths = new int[] {0,7,7,7};
		setLayout(gridBagLayout);
		{
			final JLabel label = new JLabel();
			label.setText(MARGIN_TOP);
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.EAST;
			gridBagConstraints.insets = new Insets(0, 10, 0, -14);
			gridBagConstraints.ipadx = 15;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			add(label, gridBagConstraints);
		}
		{
			spinner = new FixedLengthSpinner();
			spinner.setModel(new SpinnerNumberModel(0.0, null, null, 1));
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.ipadx = 20;
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 1;
			add(spinner, gridBagConstraints);
		}
		{
			final JLabel label = new JLabel();
			label.setText(MARGIN_LEFT);
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 10, 0, -4);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 2;
			add(label, gridBagConstraints);
		}
		{
			spinner_2 = new FixedLengthSpinner();
			spinner_2.setModel(new SpinnerNumberModel(0.0, null, null, 1));
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 3;
			add(spinner_2, gridBagConstraints);
		}
		{
			final JLabel label = new JLabel();
			label.setText(MARGIN_BOTTOM);
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.EAST;
			gridBagConstraints.insets = new Insets(5, 10, 0, -4);
			gridBagConstraints.ipadx = 5;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.gridx = 0;
			add(label, gridBagConstraints);
		}
		{
			spinner_1 = new FixedLengthSpinner();
			spinner_1.setModel(new SpinnerNumberModel(0.0, null, null, 1));
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints.ipadx = 20;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.gridx = 1;
			add(spinner_1, gridBagConstraints);
		}
		{
			final JLabel label = new JLabel();
			label.setText(MARGIN_RIGHT);
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(5, 10, 0, -4);
			gridBagConstraints.gridy = 1;
			gridBagConstraints.gridx = 2;
			add(label, gridBagConstraints);
		}
		{
			spinner_3 = new FixedLengthSpinner();
			spinner_3.setModel(new SpinnerNumberModel(0.0, null, null, 1));
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.ipadx = 20;
			gridBagConstraints.insets = new Insets(5, 0, 0, 10);
			gridBagConstraints.gridy = 1;
			gridBagConstraints.gridx = 3;
			add(spinner_3, gridBagConstraints);
		}
		
		
	}
	
	/**
	 * 按照上、下、左、右的顺序返回JSpinner
	 * @return
	 */
	public FixedLengthSpinner[] getFixedlengthSpinners(){
		return new FixedLengthSpinner[]{spinner, spinner_1, spinner_2, spinner_3};
	}
	
	public JRibbonBand getParagraphBand() {
		JRibbonBand paragraphBand = new JRibbonBand("Paragraph",
				new format_justify_left(), null);
		
		JPanel topPanel = new JPanel(new FlowLayout());
		final JLabel topLabel = new JLabel();
		topLabel.setText(MARGIN_TOP);
		final JLabel leftLabel = new JLabel();
		leftLabel.setText(MARGIN_LEFT);
		topPanel.add(topLabel);
		topPanel.add(spinner);
		topPanel.add(leftLabel);
		topPanel.add(spinner_2);

		paragraphBand.startGroup("Indent");
		JRibbonComponent justifyLeftWrapper = new JRibbonComponent(topPanel);
		justifyLeftWrapper.setKeyTip("PL");

		RichTooltip justifyLeftTooltip = new RichTooltip();
		justifyLeftTooltip.setTitle("Indent Left");
		justifyLeftTooltip
				.addDescriptionSection("Move in the left side of the paragraph by a certain amount");
		justifyLeftTooltip
				.addDescriptionSection("To change the margins for the whole document, click the Margins button");
		justifyLeftWrapper.setRichTooltip(justifyLeftTooltip);

		paragraphBand.addRibbonComponent(justifyLeftWrapper);

		JRibbonComponent justifyRightWrapper = new JRibbonComponent(
				new format_justify_right(), "Right:", new JSpinner(
						new SpinnerNumberModel(0, 0, 100, 5)));
		justifyRightWrapper.setKeyTip("PR");

		RichTooltip justifyRightTooltip = new RichTooltip();
		justifyRightTooltip.setTitle("Indent Right");
		justifyRightTooltip
				.addDescriptionSection("Move in the right side of the paragraph by a certain amount");
		justifyRightTooltip
				.addDescriptionSection("To change the margins for the whole document, click the Margins button");
		justifyRightWrapper.setRichTooltip(justifyRightTooltip);

		paragraphBand.addRibbonComponent(justifyRightWrapper);

//		paragraphBand.startGroup("Spacing");
		/*paragraphBand.startGroup();
		JRibbonComponent beforeWrapper = new JRibbonComponent(new format_justify_right(), "Right:", new JSpinner(
				new SpinnerNumberModel(0, 0, 100, 5)));
		beforeWrapper.setKeyTip("PB");
		paragraphBand.addRibbonComponent(beforeWrapper);

		JRibbonComponent afterWrapper = new JRibbonComponent(new format_justify_right(), "Right:", new JSpinner(
				new SpinnerNumberModel(10, 0, 100, 5)));
		afterWrapper.setKeyTip("PA");
		paragraphBand.addRibbonComponent(afterWrapper);*/

		return paragraphBand;
	}
	
	public JRibbonComponent getTopComponent(){
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		topPanel.setBorder(new EmptyBorder(0,0,0,0));
		final JLabel topLabel = new JLabel();
		topLabel.setText(MARGIN_TOP);
		final JLabel leftLabel = new JLabel();
		leftLabel.setText(MARGIN_LEFT);
		topPanel.add(topLabel);
		topPanel.add(spinner);
		topPanel.add(leftLabel);
		topPanel.add(spinner_2);

//		paragraphBand.startGroup("Indent");
		JRibbonComponent justifyLeftWrapper = new JRibbonComponent(topPanel);
		
		return justifyLeftWrapper;
	}
	
	public JRibbonComponent getBottomComponent(){
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		topPanel.setBorder(new EmptyBorder(0,0,0,0));
		final JLabel topLabel = new JLabel();
		topLabel.setText(MARGIN_BOTTOM);
		final JLabel leftLabel = new JLabel();
		leftLabel.setText(MARGIN_RIGHT);
		topPanel.add(topLabel);
		topPanel.add(spinner_1);
		topPanel.add(leftLabel);
		topPanel.add(spinner_3);

//		paragraphBand.startGroup("Indent");
		JRibbonComponent justifyLeftWrapper = new JRibbonComponent(topPanel);
		
		return justifyLeftWrapper;
	}

}
