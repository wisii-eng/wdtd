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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.model.MultiPagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.psmnode.ConditionalPageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.TreePanel;

/**
 * 条件引用 conditional-page-master-reference
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class ConditionalPMRPanel extends JPanel implements MasterReferenceLabel
{

	ConditionalPageMasterReferenceModel model = getModel();

	JCheckBox pagepositioncheckbox = new JCheckBox("页布局引用位置");

	JCheckBox oddorevencheckbox = new JCheckBox("奇数页或偶数页页布局");

	JCheckBox blankornotblankcheckbox = new JCheckBox("空白页页布局");

	private JComboBox blankornotblankComboBox = new JComboBox(
			new DefaultComboBoxModel(new String[]
			{ "空白页", "非空白页", "任意" }));

	private JComboBox oddEvenCombobox = new JComboBox(new DefaultComboBoxModel(
			new String[]
			{ "奇数页页布局", "偶数页页布局", "任意" }));

	private JComboBox positionComboBox = new JComboBox(
			new DefaultComboBoxModel(new String[]
			{ "引用一次", "第一页时引用", "最后一页时引用", "非首尾时引用", "任意页均引用" }));

	private JLabel masterRef;

	/**
	 * Create the panel
	 */
	public ConditionalPMRPanel()
	{
		super();

		JLabel masterreferenceLabel;
		masterreferenceLabel = new JLabel();
		masterreferenceLabel.setText("页布局引用：");

		masterRef = new JLabel("master name: ");

		ReferenceNameAction rna = new ReferenceNameAction();
		masterRef.addPropertyChangeListener(rna);
		rna.setDefaultState();

		setCheckBoxDefaultState();

		CheckBoxOddEvenAction actionoddeven = new CheckBoxOddEvenAction();
		oddorevencheckbox.addActionListener(actionoddeven);
		CheckBoxPositionAction actionposition = new CheckBoxPositionAction();
		pagepositioncheckbox.addActionListener(actionposition);
		CheckBoxBlankAction actionblank = new CheckBoxBlankAction();
		blankornotblankcheckbox.addActionListener(actionblank);

		PagePositionAction ppa = new PagePositionAction();
		positionComboBox.addActionListener(ppa);

		OddorEvenAction oea = new OddorEvenAction();
		oddEvenCombobox.addActionListener(oea);

		BlankorNotBlankAction bnba = new BlankorNotBlankAction();
		blankornotblankComboBox.addActionListener(bnba);

		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addGroup(
						groupLayout.createParallelGroup(
								GroupLayout.Alignment.LEADING).addGroup(
								groupLayout.createSequentialGroup().addGap(12,
										12, 12).addComponent(
										blankornotblankComboBox,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)).addGroup(
								groupLayout.createSequentialGroup().addGap(12,
										12, 12).addComponent(oddEvenCombobox,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)).addGroup(
								groupLayout.createSequentialGroup().addGap(12,
										12, 12).addComponent(positionComboBox,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)).addGroup(
								groupLayout.createSequentialGroup().addGap(12,
										12, 12).addComponent(masterRef/*
																	 * ,
																	 * GroupLayout
																	 * .
																	 * PREFERRED_SIZE
																	 * , 87,
																	 * GroupLayout
																	 * .
																	 * PREFERRED_SIZE
																	 */))
								.addComponent(masterreferenceLabel)
								.addComponent(pagepositioncheckbox)
								.addComponent(oddorevencheckbox).addComponent(
										blankornotblankcheckbox))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(masterreferenceLabel).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(masterRef/*
												 * , GroupLayout.PREFERRED_SIZE,
												 * GroupLayout.DEFAULT_SIZE,
												 * GroupLayout.PREFERRED_SIZE
												 */).addGap(14, 14, 14)
						.addComponent(pagepositioncheckbox).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(positionComboBox,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(16, 16, 16)
						.addComponent(oddorevencheckbox).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(oddEvenCombobox,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18)
						.addComponent(blankornotblankcheckbox).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(blankornotblankComboBox,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap(
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		setLayout(groupLayout);
		//
	}

	public JLabel getMasterRef()
	{
		return masterRef;
	}

	@Override
	public Object getMasterReference()
	{
		return masterRef;
	}

	public void setMasterReference(String masterRef)
	{
		this.masterRef.setText(masterRef);
	}

	/**
	 * 这里是用户点击时用于更新界面属性的方法
	 * 
	 * @param pagePosition
	 */

	private void updateUI(final JComboBox ui, final int index)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{

			public void run()
			{
				ui.setSelectedIndex(index);
				ui.updateUI();
			}
		});
	}

	class ReferenceNameAction implements PropertyChangeListener
	{

		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			TreePath treePath = TreePanel.getTree().getSelectionPath();
			if (treePath != null)
			{
				if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode)
				{
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath
							.getLastPathComponent();
					Object objectModel = treeNode.getUserObject();
					if (objectModel instanceof ConditionalPageMasterReferenceModel)
					{
						ConditionalPageMasterReferenceModel model = (ConditionalPageMasterReferenceModel) objectModel;
						model.setMasterReference(masterRef.getText());
					}
				}
			}
		}

		public void setDefaultState()
		{

			TreePath treePath = TreePanel.getTree().getSelectionPath();
			if (treePath != null)
			{
				if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode)
				{
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath
							.getLastPathComponent();
					Object objectModel = treeNode.getUserObject();
					if (objectModel instanceof ConditionalPageMasterReferenceModel)
					{
						ConditionalPageMasterReferenceModel model = (ConditionalPageMasterReferenceModel) objectModel;
						if (model.getMasterReference() == null)
						{
							SimplePageMasterModel spmm = new SimplePageMasterModel.Builder()
									.defaultSimplePageMaster().build();
							MultiPagelayoutModel.MultiPageLayout
									.addSimplePageMasterModel(spmm);
							masterRef.setText(spmm.getMasterName());
						}
					}
				}
			}
		}
	}

	class CheckBoxPositionAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (pagepositioncheckbox.isSelected())
			{
				oddorevencheckbox.setSelected(false);
				pagepositioncheckbox.setSelected(true);
				blankornotblankcheckbox.setSelected(false);
				setModelOddorEven(-1);
				setModelPagePosition(positionComboBox.getSelectedIndex());
				setModelBlank(-1);
				oddEvenCombobox.setEnabled(false);
				positionComboBox.setEnabled(true);
				blankornotblankComboBox.setEnabled(false);
			} else
			{
				oddorevencheckbox.setSelected(false);
				pagepositioncheckbox.setSelected(false);
				blankornotblankcheckbox.setSelected(false);
				setModelOddorEven(-1);
				setModelPagePosition(-1);
				setModelBlank(-1);
				oddEvenCombobox.setEnabled(false);
				positionComboBox.setEnabled(false);
				blankornotblankComboBox.setEnabled(false);
			}
		}
	}

	class CheckBoxOddEvenAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (oddorevencheckbox.isSelected())
			{
				oddorevencheckbox.setSelected(true);
				pagepositioncheckbox.setSelected(false);
				blankornotblankcheckbox.setSelected(false);
				setModelOddorEven(oddEvenCombobox.getSelectedIndex());
				setModelPagePosition(-1);
				setModelBlank(-1);
				oddEvenCombobox.setEnabled(true);
				positionComboBox.setEnabled(false);
				blankornotblankComboBox.setEnabled(false);
			} else
			{
				oddorevencheckbox.setSelected(false);
				pagepositioncheckbox.setSelected(false);
				blankornotblankcheckbox.setSelected(false);
				setModelOddorEven(-1);
				setModelPagePosition(-1);
				setModelBlank(-1);
				oddEvenCombobox.setEnabled(false);
				positionComboBox.setEnabled(false);
				blankornotblankComboBox.setEnabled(false);
			}
		}
	}

	class CheckBoxBlankAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (blankornotblankcheckbox.isSelected())
			{
				oddorevencheckbox.setSelected(false);
				pagepositioncheckbox.setSelected(false);
				blankornotblankcheckbox.setSelected(true);
				setModelOddorEven(-1);
				setModelPagePosition(-1);
				setModelBlank(blankornotblankComboBox.getSelectedIndex());
				oddEvenCombobox.setEnabled(false);
				positionComboBox.setEnabled(false);
				blankornotblankComboBox.setEnabled(true);
			} else
			{
				oddorevencheckbox.setSelected(false);
				pagepositioncheckbox.setSelected(false);
				blankornotblankComboBox.setEnabled(false);
				setModelOddorEven(-1);
				setModelPagePosition(-1);
				setModelBlank(-1);
				oddEvenCombobox.setEnabled(false);
				positionComboBox.setEnabled(false);
				blankornotblankComboBox.setEnabled(false);
			}
		}
	}

	class PagePositionAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			setModelPagePosition(positionComboBox.getSelectedIndex());
		}
	}

	class OddorEvenAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			setModelOddorEven(oddEvenCombobox.getSelectedIndex());
		}
	}

	class BlankorNotBlankAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			setModelBlank(blankornotblankComboBox.getSelectedIndex());
		}
	}

	public ConditionalPageMasterReferenceModel getModel()
	{
		ConditionalPageMasterReferenceModel model = null;
		TreePath treePath = TreePanel.getTree().getSelectionPath();
		if (treePath != null)
		{
			if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode)
			{
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath
						.getLastPathComponent();
				Object objectModel = treeNode.getUserObject();
				if (objectModel instanceof ConditionalPageMasterReferenceModel)
				{
					model = (ConditionalPageMasterReferenceModel) objectModel;
				}
			}
		}
		return model;
	}

	public void setCheckBoxDefaultState()
	{
		int state = getState(model);
		switch (state)
		{
			case 1:
			{
				oddorevencheckbox.setSelected(true);
				pagepositioncheckbox.setSelected(false);
				blankornotblankcheckbox.setSelected(false);
				setOddorEven(model.getOddorEven());
				positionComboBox.setSelectedIndex(4);
				blankornotblankComboBox.setSelectedIndex(2);
				oddEvenCombobox.setEnabled(true);
				positionComboBox.setEnabled(false);
				blankornotblankComboBox.setEnabled(false);
				break;
			}
			case 2:
			{
				oddorevencheckbox.setSelected(false);
				pagepositioncheckbox.setSelected(true);
				blankornotblankcheckbox.setSelected(false);
				oddEvenCombobox.setSelectedIndex(2);
				setPagePosition(model.getPagePosition());
				blankornotblankComboBox.setSelectedIndex(2);
				oddEvenCombobox.setEnabled(false);
				positionComboBox.setEnabled(true);
				blankornotblankComboBox.setEnabled(false);
				break;
			}
			case 3:
			{
				oddorevencheckbox.setSelected(false);
				pagepositioncheckbox.setSelected(false);
				blankornotblankcheckbox.setSelected(true);
				oddEvenCombobox.setSelectedIndex(2);
				positionComboBox.setSelectedIndex(4);
				setBlank(model.getBlankorNotBlank());
				oddEvenCombobox.setEnabled(false);
				positionComboBox.setEnabled(false);
				blankornotblankComboBox.setEnabled(true);
				break;
			}
			default:
			{
				oddorevencheckbox.setSelected(false);
				pagepositioncheckbox.setSelected(false);
				blankornotblankcheckbox.setSelected(false);
				oddEvenCombobox.setSelectedIndex(2);
				positionComboBox.setSelectedIndex(4);
				blankornotblankComboBox.setSelectedIndex(2);
				oddEvenCombobox.setEnabled(false);
				positionComboBox.setEnabled(false);
				blankornotblankComboBox.setEnabled(false);
				break;
			}
		}
	}

	public int getState(ConditionalPageMasterReferenceModel model)
	{
		int result = 2;
		if (model != null)
		{
			int oddeven = model.getOddorEven();
			int pageposition = model.getPagePosition();
			int blankorno = model.getBlankorNotBlank();
			if (oddeven > 0 && oddeven != Constants.EN_ANY)
			{
				result = 1;
			} else if (pageposition > 0 && pageposition != Constants.EN_ANY)
			{
				result = 2;
			} else if (blankorno > 0 && blankorno != Constants.EN_ANY)
			{
				result = 3;
			}
		}
		return result;
	}

	public void setPagePosition(int pagePosition)
	{

		switch (pagePosition)
		{
			case Constants.EN_FIRST:
				updateUI(positionComboBox, 1);
				break;
			case Constants.EN_LAST:
				updateUI(positionComboBox, 2);
				break;
			case Constants.EN_REST:
				updateUI(positionComboBox, 3);
				break;
			case Constants.EN_ANY:
				updateUI(positionComboBox, 4);
				break;

			default:
				updateUI(positionComboBox, 4);
				break;
		}
	}

	public void setOddorEven(int oddorEven)
	{

		switch (oddorEven)
		{
			case Constants.EN_ODD:
				updateUI(oddEvenCombobox, 0);
				break;
			case Constants.EN_EVEN:
				updateUI(oddEvenCombobox, 1);
				break;
			case Constants.EN_ANY:
				updateUI(oddEvenCombobox, 2);
				break;

			default:
				updateUI(oddEvenCombobox, 2);
				break;
		}
	}

	public void setBlank(int blank)
	{

		switch (blank)
		{
			case Constants.EN_BLANK:
				updateUI(blankornotblankComboBox, 0);
				break;
			case Constants.EN_NOT_BLANK:
				updateUI(blankornotblankComboBox, 1);
				break;
			case Constants.EN_ANY:
				updateUI(blankornotblankComboBox, 2);
				break;

			default:
				updateUI(blankornotblankComboBox, 2);
				break;
		}
	}

	public void setModelPagePosition(int pagePosition)
	{
		switch (pagePosition)
		{
			case 0:
				model.setPagePosition(Constants.EN_ONLY);
				break;
			case 1:
				model.setPagePosition(Constants.EN_FIRST);
				break;
			case 2:
				model.setPagePosition(Constants.EN_LAST);
				break;
			case 3:
				model.setPagePosition(Constants.EN_REST);
				break;
			// case 4:
			// model.setPagePosition(Constants.EN_ANY);
			// break;

			default:
				model.setPagePosition(Constants.EN_ANY);
				break;
		}

	}

	public void setModelOddorEven(int oddorEven)
	{
		switch (oddorEven)
		{
			case 0:
				model.setOddorEven(Constants.EN_ODD);
				break;
			case 1:
				model.setOddorEven(Constants.EN_EVEN);
				break;
			// case 2:
			// model.setOddorEven(Constants.EN_ANY);
			// break;

			default:
				model.setOddorEven(Constants.EN_ANY);
				break;
		}
	}

	public void setModelBlank(int blank)
	{
		switch (blank)
		{
			case 0:
				model.setBlankorNotBlank(Constants.EN_BLANK);
				break;
			case 1:
				model.setBlankorNotBlank(Constants.EN_NOT_BLANK);
				break;
			// case 2:
			// model.setBlankorNotBlank(Constants.EN_ANY);
			// break;

			default:
				model.setBlankorNotBlank(Constants.EN_ANY);
				break;
		}
	}
}
