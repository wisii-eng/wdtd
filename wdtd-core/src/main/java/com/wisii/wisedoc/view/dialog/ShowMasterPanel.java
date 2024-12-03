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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.dialog.MasterNode.MasterType;
import com.wisii.wisedoc.view.ui.StaticContentManeger;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class ShowMasterPanel extends JPanel
{

	JPanel toppanel = new JPanel();

	RepeatPanel repeatpanel = new RepeatPanel();

	PositionPanel positionpanel = new PositionPanel();

	SetPageLayoutPanel masterpanel;

	CardLayout cardlayout = new CardLayout();

	Control control;

	public ShowMasterPanel()
	{
		super();
		initComponents();
	}

	public ShowMasterPanel(Object pagemaster)
	{
		super();
		initComponents();
	}

	private void initComponents()
	{
		this.setLayout(new BorderLayout());
		masterpanel = new SetPageLayoutPanel();
		toppanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE),
				UiText.USESMPANDPROPERTY, TitledBorder.LEFT, TitledBorder.TOP));
		masterpanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE),
				UiText.SETPROPERTY, TitledBorder.LEFT, TitledBorder.TOP));
		initPnael();
		toppanel.invalidate();
		this.invalidate();
		this.add(toppanel, BorderLayout.NORTH);
		this.add(masterpanel, BorderLayout.CENTER);
	}

	public void initPnael()
	{
		toppanel.setLayout(cardlayout);
		toppanel.add(repeatpanel, UiText.MORE_USE);
		toppanel.add(positionpanel, UiText.POSITION_USE);
		setInitialState();
		toppanel.invalidate();
		this.invalidate();
	}

	public void setCurrentNode()
	{
		setInitialState();
	}

	public void setInitialState()
	{
		String panelname = "";
		MasterNode currentnode = control != null ? control.getCurrentnode()
				: null;
		if (currentnode != null)
		{
			MasterType type = currentnode.getType();
			switch (type)
			{
				case SINGLE:
				{
					panelname = UiText.ONE_USE;
					masterpanel.setVisible(true);
					break;
				}
				case REPEAT:
				{
					panelname = UiText.MORE_USE;
					repeatpanel.setInitialState();
					masterpanel.setVisible(true);
					break;
				}
				case POSITION:
				{
					panelname = UiText.POSITION_USE;
					positionpanel.setInitialState();
					masterpanel.setVisible(false);
					break;
				}
				case ROOT:
				{
					panelname = "";
					masterpanel.setVisible(false);
					toppanel.setVisible(false);
					break;
				}
				default:
				{
					panelname = UiText.CONDITION_USE;
					masterpanel.setVisible(true);
					break;
				}
			}
			cardlayout.show(toppanel, panelname);
			masterpanel.setSimplePageMaster(MasterTree.getModel(currentnode
					.getValue()));
			if (type == MasterType.REPEAT || type == MasterType.POSITION)
			{
				toppanel.setVisible(true);
			} else
			{
				toppanel.setVisible(false);
			}
		} else
		{
			masterpanel.setVisible(false);
			toppanel.setVisible(false);
		}
	}

	public void getResult()
	{
		MasterNode currentnode = control.getCurrentnode();
		if (currentnode != null)
		{
			MasterType type = currentnode.getType();
			String name = null;
			switch (type)
			{
				case REPEAT:
				{
					name = repeatpanel.setResult();
					break;
				}
				case POSITION:
				{
					positionpanel.setResult();
					break;
				}
			}
			if (name != null)
			{
				SimplePageMasterModel spmm = masterpanel
						.getSimplePageMasterModel();
				spmm.setVirtualMasterName(name);
				currentnode.setValue(spmm.getSimplePageMaster());
			} else
			{
				currentnode.setValue(masterpanel.getSimplePageMaster());
			}
		}
	}

	class RepeatPanel extends JPanel
	{

		WiseSpinner number = new WiseSpinner(new SpinnerNumberModel(1, 1, null,
				1));

		public RepeatPanel()
		{
			super();
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			initComponents();
		}

		private void initComponents()
		{
			JLabel numberlabel = new JLabel(UiText.THIS_USE_SPM);
			this.add(numberlabel);
			this.add(number);
		}

		public void setInitialState()
		{
			number.initValue(control.getCurrentnode().getNumber());
		}

		public String setResult()
		{
			int refnumber = (Integer) number.getValue();
			control.getCurrentnode().setNumber(refnumber);
			return null;
		}
	}

	class PositionPanel extends JPanel
	{

		JCheckBox firstcb = new JCheckBox(UiText.FIRST_DIFFENT);

		JCheckBox lastcb = new JCheckBox(UiText.LAST_DIFFENT);

		JCheckBox oddevencb = new JCheckBox(UiText.ODD_EVEN_DIFFENT);

		JCheckBox blankcb = new JCheckBox(UiText.BLANK_NOBLANK_DIFFENT);

		WiseSpinner number = new WiseSpinner(new SpinnerNumberModel(1, 1, null,
				1));

		WiseCombobox combox = new WiseCombobox();
		{
			combox.addItem(UiText.BUXIANZAHIYESHU);
			combox.addItem(UiText.ZHIDINGYESHU);
		}

		public PositionPanel()
		{
			super();
			this.setLayout(new GridLayout(2, 1));
			initComponents();
		}

		private void initComponents()
		{
			JPanel one = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JPanel two = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel label = new JLabel(UiText.POSITION_USE_SPM);
			JLabel numberlabel = new JLabel(UiText.USENUMBER_SPM);
			two.add(label);
			two.add(firstcb);
			two.add(lastcb);
			two.add(oddevencb);
			two.add(blankcb);

			one.add(numberlabel);
			one.add(combox);
			one.add(number);
			number.setPreferredSize(new Dimension(60, 25));
			this.add(one);
			this.add(two);
			combox.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					int index = combox.getSelectedIndex();
					if (index == 0)
					{
						number.setVisible(false);
						control.getCurrentnode().setNumber(-1);
					} else
					{
						number.setVisible(true);
						control.getCurrentnode().setNumber(
								(Integer) number.getValue());
					}
				}
			});

			firstcb.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{

					boolean firstflg = firstcb.isSelected();
					boolean lastflg = lastcb.isSelected();
					boolean oddevenflg = oddevencb.isSelected();
					MasterReference currentnode = control.getCurrentnode();
					if (firstflg)
					{
						SimplePageMaster addfirst = null;
						if (oddevenflg)
						{
							addfirst = control.getCurrentnode().getChild(
									MasterType.CONDITION_ODD).getValue();
						} else if (lastflg)
						{
							addfirst = control.getCurrentnode().getChild(
									MasterType.CONDITION_REST).getValue();
						}

						MasterReference first = new MasterReference(
								MasterType.CONDITION_FIRST, UiText.FIRST_USE);
						SimplePageMaster spm = MasterTree.getClone(addfirst,
								StaticContentManeger.getScmap())
								.getSimplePageMaster();
						first.setValue(spm);
						currentnode.addChild(first, 0);
					} else
					{
						currentnode.removeChild(MasterType.CONDITION_FIRST);
					}
					control.upDataTree();
				}
			});
			lastcb.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{

					boolean firstflg = firstcb.isSelected();
					boolean lastflg = lastcb.isSelected();
					boolean oddevenflg = oddevencb.isSelected();
					MasterReference currentnode = control.getCurrentnode();
					if (lastflg)
					{
						SimplePageMaster addlast = null;
						if (oddevenflg)
						{
							addlast = currentnode.getChild(
									MasterType.CONDITION_EVEN).getValue();
						} else if (firstflg)
						{
							addlast = currentnode.getChild(
									MasterType.CONDITION_REST).getValue();
						}

						MasterReference last = new MasterReference(
								MasterType.CONDITION_LAST, UiText.LAST_USE);
						SimplePageMaster spm = MasterTree.getClone(addlast,
								StaticContentManeger.getScmap())
								.getSimplePageMaster();
						last.setValue(spm);
						int size = firstflg ? 1 : 0;
						currentnode.addChild(last, size);
					} else
					{
						currentnode.removeChild(MasterType.CONDITION_LAST);
					}
					control.upDataTree();
				}
			});
			oddevencb.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					boolean firstflg = firstcb.isSelected();
					boolean lastflg = lastcb.isSelected();
					boolean oddevenflg = oddevencb.isSelected();
					MasterReference currentnode = control.getCurrentnode();
					if (oddevenflg)
					{
						MasterReference odd = new MasterReference(
								MasterType.CONDITION_ODD, UiText.ODD_USE);
						MasterReference even = new MasterReference(
								MasterType.CONDITION_EVEN, UiText.EVEN_USE);
						SimplePageMaster addodd = null;
						SimplePageMaster addeven = null;
						MasterReference first = currentnode
								.getChild(MasterType.CONDITION_FIRST);
						SimplePageMaster spmodd;
						SimplePageMaster spmeven;
						if (firstflg || lastflg)
						{

							if (first == null)
							{
								addodd = currentnode.getChild(
										MasterType.CONDITION_REST).getValue();
								addeven = currentnode.getChild(
										MasterType.CONDITION_REST).getValue();
								spmodd = MasterTree.getClone(addodd,
										StaticContentManeger.getScmap())
										.getSimplePageMaster();
								spmeven = MasterTree.getModel(addeven)
										.getSimplePageMaster();
							} else
							{
								addodd = first.getValue();
								addeven = currentnode.getChild(
										MasterType.CONDITION_REST).getValue();
								spmodd = MasterTree.getClone(addodd,
										StaticContentManeger.getScmap())
										.getSimplePageMaster();
								spmeven = MasterTree.getModel(addeven)
										.getSimplePageMaster();
							}
						} else
						{
							spmodd = MasterTree.getClone(addodd,
									StaticContentManeger.getScmap())
									.getSimplePageMaster();
							spmeven = MasterTree.getClone(addeven,
									StaticContentManeger.getScmap())
									.getSimplePageMaster();
						}
						odd.setValue(spmodd);
						even.setValue(spmeven);
						currentnode.removeChild(MasterType.CONDITION_REST);
						if (firstflg)
						{
							currentnode.addChild(even, 1);
							currentnode.addChild(odd, 1);
						} else
						{
							currentnode.addChild(even, 0);
							currentnode.addChild(odd, 0);
						}
					} else
					{
						MasterReference rest = currentnode
								.getChild(MasterType.CONDITION_EVEN);
						rest.setName(UiText.REST_USE);
						rest.setType(MasterType.CONDITION_REST);
						currentnode.removeChild(MasterType.CONDITION_ODD);
						currentnode.removeChild(MasterType.CONDITION_EVEN);
					}
					control.upDataTree();
				}
			});
			blankcb.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					boolean blankflg = blankcb.isSelected();
					MasterReference currentnode = control.getCurrentnode();
					if (blankflg)
					{
						MasterReference blank = new MasterReference(
								MasterType.CONDITION_BLANK, UiText.BLANK_USE);
						MasterReference rest = currentnode
								.getChild(MasterType.CONDITION_REST);
						if (rest == null)
						{
							rest = currentnode
									.getChild(MasterType.CONDITION_ODD);
						}
						SimplePageMaster blankspm = rest.getValue();
						blank.setValue(MasterTree.getClone(blankspm,
								StaticContentManeger.getScmap())
								.getSimplePageMaster());
						currentnode.addChild(blank);
					} else
					{
						currentnode.removeChild(currentnode
								.getChild(MasterType.CONDITION_BLANK));
					}
					control.upDataTree();
				}
			});
			combox.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					MasterReference currentnode = control.getCurrentnode();
					MasterReference parent = (MasterReference) currentnode
							.getParent();
					int all = parent.getChildCount();
					int index = parent.getIndex(currentnode);
					if (index < all - 1)
					{
						combox.setSelectedIndex(1);
					}
				}
			});
		}

		public void setInitialState()
		{
			List<MasterNode> childrens = control.getCurrentnode().getChildren();
			setAllFalse();
			if (childrens != null && !childrens.isEmpty())
			{
				boolean first = false;
				boolean last = false;
				boolean odd = false;
				boolean even = false;
				boolean blank = false;
				for (MasterNode current : childrens)
				{
					if (current.getType() == MasterType.CONDITION_FIRST)
					{
						first = true;
					} else if (current.getType() == MasterType.CONDITION_LAST)
					{
						last = true;
					} else if (current.getType() == MasterType.CONDITION_ODD)
					{
						odd = true;
					} else if (current.getType() == MasterType.CONDITION_EVEN)
					{
						even = true;
					} else if (current.getType() == MasterType.CONDITION_BLANK)
					{
						blank = true;
					}
				}
				if (first)
				{
					firstcb.setSelected(true);
				}
				if (last)
				{
					lastcb.setSelected(true);
				}
				if (odd || even)
				{
					oddevencb.setSelected(true);
				}
				if (blank)
				{
					blankcb.setSelected(true);
				}
			}
			int refnumber = control.getCurrentnode().getNumber();
			if (refnumber == -1)
			{
				combox.setSelectedIndex(0);
				number.setVisible(false);
			} else
			{
				combox.setSelectedIndex(1);
				number.setVisible(true);
				number.initValue(refnumber);
			}
		}

		public void setAllFalse()
		{
			firstcb.setSelected(false);
			lastcb.setSelected(false);
			oddevencb.setSelected(false);
			blankcb.setSelected(false);
		}

		public void setResult()
		{
			int index = combox.getSelectedIndex();
			if (index == 0)
			{
				control.getCurrentnode().setNumber(-1);
			} else
			{
				control.getCurrentnode().setNumber((Integer) number.getValue());
			}
		}
	}

	public Control getControl()
	{
		return control;
	}

	public void setControl(Control control)
	{
		this.control = control;
	}
}
