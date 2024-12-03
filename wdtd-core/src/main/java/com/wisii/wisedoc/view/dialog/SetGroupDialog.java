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

package com.wisii.wisedoc.view.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.Sort;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class SetGroupDialog extends AbstractWisedocDialog
{

	public SetGroupDialog(final Group gp)
	{
		super();
		setGroup(gp);
		this.setTitle(UiText.SET_GROUP_CONDITION);
		this.setSize(650, 500);
		laout.setWidth(650);
		laout.setHeight(500);
		this.setLayout(laout);
		initComponents();
	}

	private void initComponents()
	{
		final JPanel panel = new JPanel();
		panel.setSize(650, 500);
		panel.setLayout(laout);
		final String chushizhi = getGroup() == null ? "" : getGroup().getNode()
				.getXPath();
		setXpathText(chushizhi);

		panel.add(new JLabel(UiText.CURRENT_GROUP_NODE), new XYConstraints(20,
				10, 60, 30));
		panel.add(xpath, new XYConstraints(82, 10, 400, 30));
		panel.add(setNode, new XYConstraints(82, 40, 400, 360));
		setNode.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseExited(final MouseEvent e)
			{
				setNode.setVisible(false);
			}
		});
		final Group group = getGroup();
		final EnumNumber numb = group == null ? null : group.getMax();

		maxNumber.setEnabled(group != null);
		maxNumber.setSelected(group != null
				&& (numb != null && numb.getNumber().intValue() > 0));
		maxNumber.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent e)
			{
				maxnum.setEnabled(maxNumber.isSelected()
						&& maxNumber.isEnabled());

				if (maxNumber.isSelected())
				{
					final int currentnumber = (Integer) maxnum.getValue();
					EnumNumber num = null;
					if (currentnumber > -1)
					{
						num = new EnumNumber(-1, currentnumber);
					}
					setMaxNumber(num);
				} else
				{
					setMaxNumber(null);
				}
			}
		});

		final SpinnerNumberModel data = new SpinnerNumberModel(0, -1, 1000000,
				1);
		maxnum.setModel(data);
		if (numb != null)
		{
			maxnum.setValue(numb.getNumber());
		} else
		{
			maxnum.setValue(100);
		}
		maxnum.setEnabled(maxNumber.isSelected() && maxNumber.isEnabled());

		maxnum.addChangeListener(new ChangeListener()
		{

			public void stateChanged(final ChangeEvent e)
			{
				final int currentnumber = (Integer) ((JSpinner) e.getSource())
						.getValue();
				EnumNumber num = null;
				if (currentnumber > -1)
				{
					num = new EnumNumber(-1, currentnumber);
				}
				// else
				// {
				// num = new EnumNumber(0, currentnumber);
				// }
				setMaxNumber(num);
			};

		});
		final JPanel edit = new JPanel();
		edit.setPreferredSize(new Dimension(400, 40));
		edit.setLayout(new FlowLayout(FlowLayout.LEFT));
		edit.add(maxNumber);
		edit.add(maxnum);
		// edit.add(new JLabel(UiText.EDIT));
		final boolean flg = getGroup() != null;
		// editMode.setEnabled(flg);
		// editMode.addItem(noaddanddelete);
		// editMode.addItem(canadd);
		// editMode.addItem(candelete);
		// editMode.addItem(canaddanddelete);
		// editMode.addItem(datenode);
		// editMode.setSelectedItem(getItem(getEnumint()));
		// editMode.addItemListener(new ItemListener()
		// {
		//
		// @Override
		// public void itemStateChanged(final ItemEvent event)
		// {
		// if (event.getStateChange() == ItemEvent.SELECTED)
		// {
		// setEdit();
		// }
		// }
		// });
		// edit.add(editMode);

		panel.add(edit, new XYConstraints(20, 40, 500, 40));

		sort = getGroup() != null && getGroup().getSortSet() != null ? new SortEditorPanel(
				getGroup().getSortSet())
				: new SortEditorPanel();

		sort.setPreferredSize(new Dimension(600, 300));
		sort.setEnabled(flg);

		sort.setButtonEnabled(flg);

		condition = getGroup() != null
				&& getGroup().getFliterCondition() != null ? new ConditionEditorPanel(
				getGroup().getFliterCondition())
				: new ConditionEditorPanel(null);

		condition.setPreferredSize(new Dimension(600, 300));
		condition.setEnabled(flg);

		tablepanel.setPreferredSize(new Dimension(600, 330));
		tablepanel.add(UiText.SORT, sort);
		tablepanel.add(UiText.CONDITION, condition);
		tablepanel.setEnabled(flg);
		panel.add(tablepanel, new XYConstraints(20, 80, 600, 330));

		panel.add(ok, new XYConstraints(320, 420, 75, 25));
		panel.add(usethis, new XYConstraints(420, 420, 75, 25));
		panel.add(cancel, new XYConstraints(520, 420, 75, 25));

		/* 添加ESC、ENTER健处理 */
		setOkButton(ok);
		setCancelButton(cancel);

		this.add(panel, new XYConstraints(0, 0, 650, 500));
	}

	public void setMaxNumber(final EnumNumber number)
	{
		final Group current = getGroup();
		final Group newGroup = current == null ? null : Group.Instanceof(
				current.getNode(), current.getFliterCondition(), current
						.getSortSet(), current.getEditmode(), number);
		setGroup(newGroup);
	}

	public void setSort(final List<Sort> sort)
	{
		final Group current = getGroup();
		final Group newGroup = current == null ? null : Group.Instanceof(
				current.getNode(), current.getFliterCondition(), sort, current
						.getEditmode(), current.getMax());
		setGroup(newGroup);
	}

	public void setCondition(final LogicalExpression condition)
	{
		final Group current = getGroup();
		final Group newGroup = current == null ? null : Group.Instanceof(
				current.getNode(), condition, current.getSortSet(), current
						.getEditmode(), current.getMax());
		setGroup(newGroup);
	}

	public void setEditMode(final EnumProperty edit)
	{
		final Group current = getGroup();
		final Group newGroup = current == null ? null : Group.Instanceof(
				current.getNode(), current.getFliterCondition(), current
						.getSortSet(), edit, current.getMax());
		setGroup(newGroup);
	}

	// public void setEdit()
	// {
	// EnumProperty edit = null;
	// final String current = editMode.getSelectedItem().toString();
	// if (current.equalsIgnoreCase(noaddanddelete))
	// {
	// edit = new EnumProperty(Constants.EN_NOADDANDDELETE, "");
	// } else if (current.equalsIgnoreCase(canadd))
	// {
	// edit = new EnumProperty(Constants.EN_CANADD, "");
	// } else if (current.equalsIgnoreCase(candelete))
	// {
	// edit = new EnumProperty(Constants.EN_CANDELETE, "");
	// } else if (current.equalsIgnoreCase(canaddanddelete))
	// {
	// edit = new EnumProperty(Constants.EN_CANADDANDDELETE, "");
	// } else if (current.equalsIgnoreCase(datenode))
	// {
	// edit = new EnumProperty(Constants.EN_DATANODE, "");
	// }
	// if (edit != null)
	// {
	// setEditMode(edit);
	// }
	// }

	@Override
	public DialogResult showDialog()
	{
		if (SystemManager.getCurruentDocument().getDataStructure() == null)
		{
			JOptionPane.showMessageDialog(SystemManager.getMainframe(), "有问题",
					"没有树", JOptionPane.WARNING_MESSAGE);
		} else
		{
			DialogSupport.centerOnScreen(this);
			setVisible(true);
		}
		dispose();
		return result;
	}

	public void setOldEdit()
	{
		final Group group = getGroup();
		if (group != null)
		{
			final EnumProperty enump = group.getEditmode();
			if (enump != null)
			{
				setEnumint(enump.getEnum());
			}
		}
	}

	public String getItem(final int value)
	{
		String result = "";
		if (value == Constants.EN_NOADDANDDELETE)
		{
			result = noaddanddelete;
		} else if (value == Constants.EN_CANADD)
		{
			result = canadd;
		} else if (value == Constants.EN_CANDELETE)
		{
			result = candelete;
		} else if (value == Constants.EN_CANADDANDDELETE)
		{
			result = canaddanddelete;
		} else if (value == Constants.EN_DATANODE)
		{
			result = datenode;
		}
		return result;
	}

	public void setXpathText(final String text)
	{
		xpath.setText(text);
		if (!text.equalsIgnoreCase(""))
		{
			xpath.setBackground(Color.WHITE);
		}
	}

	public Group getGroup()
	{
		return group;
	}

	public void setGroup(final Group gp)
	{
		group = gp;
	}

	public int getEnumint()
	{
		return enumint;
	}

	public void setEnumint(final int enumint)
	{
		this.enumint = enumint;
	}

	String noaddanddelete = "不可添加不可删除";

	String canadd = "可添加";

	String candelete = "可删除";

	String canaddanddelete = "可添加可删除";

	String datenode = "由数据节点决定";

	private Group group = null;

	XYLayout laout = new XYLayout();

	private DialogResult result = DialogResult.Cancel;

	JScrollPane setNode = new JScrollPane();
	{
		setNode.setSize(400, 360);
		final WiseDocTree tree = new WiseDocTree(SystemManager
				.getCurruentDocument().getDataStructure());
		tree.setCellRenderer(new DataStructureCellRender());
		tree.getSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener()
				{

					public void valueChanged(final TreeSelectionEvent e)
					{
						final Group current = getGroup();
						final TreePath path = e.getPath();
						final BindNode node = path != null ? (BindNode) path
								.getLastPathComponent() : null;
						final Group newGroup = current == null ? Group
								.Instanceof(node, null, null, null, null)
								: Group.Instanceof(node, current
										.getFliterCondition(), current
										.getSortSet(), current.getEditmode(),
										current.getMax());
						setGroup(newGroup);
						maxNumber.setEnabled(true);
						// maxNumber.setSelected(true);
						// maxnum.setEnabled(true);
						// editMode.setEnabled(true);
						tablepanel.setEnabled(true);
						final String text = node == null ? "" : node.getXPath();
						setXpathText(text);
						setNode.setVisible(false);
						sort.setButtonEnabled(true);
					}
				});
		setNode.setViewportView(tree);
		setNode.setVisible(false);
	}

	JCheckBox maxNumber = new JCheckBox(UiText.MAX_REPEAT_TIMES, false);

	JSpinner maxnum = new JSpinner();

	JButton ok = new JButton(MessageResource
			.getMessage("wsd.view.gui.dialog.ok"));
	{
		ok.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent e)
			{
				ConditionEditorPanel conpanel = getConditionPanel();
				if (!conpanel.isSettingOK())
				{
					tablepanel.setSelectedComponent(condition);
					return;
				}
				final LogicalExpression condition = conpanel
						.getLogicalExpression();
				SortEditorPanel sortpanel = getSortPanel();
				if (!sortpanel.isSettingOK())
				{
					tablepanel.setSelectedComponent(sortpanel);
					return;
				}
				final List<Sort> sort = sortpanel.getSorts();
				setCondition(condition);
				setSort(sort);
				result = DialogResult.OK;
				dispose();
			}
		});
	}

	JButton usethis = new JButton(MessageResource
			.getMessage("wsd.view.gui.dialog.use"));
	{
		usethis.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent e)
			{
				ConditionEditorPanel conpanel = getConditionPanel();
				if (!conpanel.isSettingOK())
				{
					tablepanel.setSelectedComponent(condition);
					return;
				}
				final LogicalExpression condition = conpanel
						.getLogicalExpression();
				SortEditorPanel sortpanel = getSortPanel();
				if (!sortpanel.isSettingOK())
				{
					tablepanel.setSelectedComponent(sortpanel);
					return;
				}
				final List<Sort> sort = sortpanel.getSorts();
				setCondition(condition);
				setSort(sort);
			}
		});
	}

	JButton cancel = new JButton(MessageResource
			.getMessage("wsd.view.gui.dialog.cancel"));
	{
		cancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent e)
			{
				dispose();
			}
		});
	}

	int enumint = 0;

	// WiseCombobox editMode = new WiseCombobox();

	JTextField xpath = new JTextField();
	{
		xpath.setEditable(false);
		xpath.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(final MouseEvent event)
			{
				if(group!=null)
				{
					((WiseDocTree)(setNode.getViewport().getView())).setSelectedNode(group.getNode());
				}
				setNode.setVisible(true);
				setNode.setLocation(82, 38);
			}
		});
	}

	ConditionEditorPanel condition;

	public ConditionEditorPanel getConditionPanel()
	{
		return condition;
	}

	public SortEditorPanel getSortPanel()
	{
		return sort;
	}

	SortEditorPanel sort;

	JTabbedPane tablepanel = new JTabbedPane();
}
