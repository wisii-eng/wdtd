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

package com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.model.MultiPagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.psmnode.RepeatablePageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.TreePanel;

/**
 * 多次引用属性面板 repeatable-page-master-reference
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/05
 */
public class RepeatablePageMasterReferencePropertyPanel extends JPanel
		implements MasterReferenceLabel
{

	RepeatablePageMasterReferenceModel model = getModel();

	private WiseSpinner spinner;

	private JComboBox comboBox;

	JLabel referenceNameLabel;

	/**
	 * Create the panel
	 */
	public RepeatablePageMasterReferencePropertyPanel()
	{
		super();

		JLabel masterReferenceLabel;
		masterReferenceLabel = new JLabel();
		masterReferenceLabel.setText("页布局引用：");// master reference:

		referenceNameLabel = new JLabel();
		referenceNameLabel.setText("masterReference");//

		ReferenceNameAction rna = new ReferenceNameAction();
		referenceNameLabel.addPropertyChangeListener(rna);
		rna.setDefaultState();

		JLabel maximumRepeatsLabel;
		maximumRepeatsLabel = new JLabel();
		maximumRepeatsLabel.setText("持续页数：");

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[]
		{ "无限制", "指定页数" }));

		spinner = new WiseSpinner();
		SpinnerNumberModel snm = new SpinnerNumberModel(1, 1, null, 1);
		spinner.setModel(snm);
		setDefaultState();

		MaxRepeatsAction mra = new MaxRepeatsAction();
		MaxRepeatsNumberAction mrna = new MaxRepeatsNumberAction();
		spinner.addChangeListener(mrna);
		comboBox.addActionListener(mra);

		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(
																				12,
																				12,
																				12)
																		.addComponent(
																				referenceNameLabel,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																masterReferenceLabel)
														.addComponent(
																maximumRepeatsLabel)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(
																				12,
																				12,
																				12)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								spinner,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								comboBox,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))))
										.addContainerGap(64, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(masterReferenceLabel).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(referenceNameLabel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(16, 16, 16)
						.addComponent(maximumRepeatsLabel).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap(
								133, Short.MAX_VALUE)));
		setLayout(groupLayout);
		//
	}

	@Override
	public Object getMasterReference()
	{
		return referenceNameLabel;
	}

	public void setRepeatNumber(Object o)
	{
		if (o != null && o instanceof EnumNumber
				&& ((EnumNumber) o).getEnum() <= 0
				&& ((EnumNumber) o).getNumber().intValue() > 0)
		{
			EnumNumber value = (EnumNumber) o;
			comboBox.setSelectedIndex(1);
			spinner.setVisible(true);
			spinner.setEnabled(true);
			spinner.initValue((Integer) value.getNumber());
		} else if (o != null && o instanceof Integer && (Integer) o != 0)
		{
			int value = (Integer) o;
			comboBox.setSelectedIndex(1);
			spinner.setVisible(true);
			spinner.setEnabled(true);
			spinner.initValue(value);
		} else
		{
			comboBox.setSelectedIndex(0);
			spinner.setVisible(false);
		}

	}

	public void setMasterReference(String masterRef)
	{
		referenceNameLabel.setText(masterRef);
	}

	class ReferenceNameAction implements PropertyChangeListener
	{

		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			model.setMasterReference(referenceNameLabel.getText());
		}

		public void setDefaultState()
		{
			if (model.getMasterReference() == null)
			{
				SimplePageMasterModel spmm = new SimplePageMasterModel.Builder()
						.defaultSimplePageMaster().build();
				MultiPagelayoutModel.MultiPageLayout
						.addSimplePageMasterModel(spmm);
				referenceNameLabel.setText(spmm.getMasterName());
			}
		}
	}

	class MaxRepeatsAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (comboBox.getSelectedIndex() == 0)
			{
				spinner.setVisible(false);
				setValue(null);
			} else if (comboBox.getSelectedIndex() == 1)
			{
				spinner.setVisible(true);
				spinner.setEnabled(true);
				setValue(new EnumNumber(0, (Integer) spinner.getValue()));
			}
		}

	}

	class MaxRepeatsNumberAction implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e)
		{
			WiseSpinner sp = (WiseSpinner) e.getSource();
			int value = (Integer) sp.getValue();
			setValue(new EnumNumber(0, value));
		}
	}

	public RepeatablePageMasterReferenceModel getModel()
	{
		RepeatablePageMasterReferenceModel model = null;
		TreePath treePath = TreePanel.getTree().getSelectionPath();
		if (treePath != null)
		{
			if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode)
			{
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath
						.getLastPathComponent();
				Object objectModel = treeNode.getUserObject();
				if (objectModel instanceof RepeatablePageMasterReferenceModel)
				{
					model = (RepeatablePageMasterReferenceModel) objectModel;
				}
			}
		}
		return model;
	}

	public void setDefaultState()
	{
		Object o = model.getMaximumRepeats();
		if (o != null && o instanceof EnumNumber
				&& ((EnumNumber) o).getEnum() <= 0
				&& ((EnumNumber) o).getNumber().intValue() > 0)
		{
			EnumNumber value = (EnumNumber) o;
			comboBox.setSelectedIndex(1);
			spinner.setVisible(true);
			spinner.setEnabled(true);
			spinner.initValue((Integer) value.getNumber());
		} else if (o != null && o instanceof Integer && (Integer) o != 0)
		{
			int value = (Integer) o;
			comboBox.setSelectedIndex(1);
			spinner.setVisible(true);
			spinner.setEnabled(true);
			spinner.initValue(value);
		} else
		{
			comboBox.setSelectedIndex(0);
			spinner.setVisible(false);
		}
	}

	public void setValue(Object value)
	{
		model.setMaximumRepeats(value);
	}
}
