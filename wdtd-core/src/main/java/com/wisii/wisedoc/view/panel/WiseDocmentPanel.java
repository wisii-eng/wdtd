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

package com.wisii.wisedoc.view.panel;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.dialog.ConfigurationInformationDialog;
import com.wisii.wisedoc.view.dialog.GetProfileValue;

@SuppressWarnings("serial")
public class WiseDocmentPanel extends OnlyLayoutPanel
{

	String canEdit = "可编辑";

	String noEdit = "不可编辑";

	String xmlNode = "由xml数据节点决定";

	String variable = "由变量决定";

	String text = "字符串";

	String number = "数字";

	String ascending = "升序";

	String descending = "降序";

	String lowerFirst = "小写优先";

	String upperFirst = "大写优先";

	WiseSpinner maxNumber = new WiseSpinner();
	{
		SpinnerNumberModel data = new SpinnerNumberModel(0, 0, 10000, 1);
		maxNumber.setModel(data);
		maxNumber.setValue(GetProfileValue.getIntValue("maxNumber"));
		maxNumber.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				ConfigurationInformationDialog.addProFileItem("maxNumber",
						((WiseSpinner) e.getSource()).getValue() + "");

			};

		});
	}

	WiseCombobox comboBoxWiseDoc = new WiseCombobox();
	{
		comboBoxWiseDoc.addItem(noEdit);
		comboBoxWiseDoc.addItem(canEdit);
		comboBoxWiseDoc.addItem(variable);
		int value = GetProfileValue.getIntValue(Constants.PR_XMLEDIT);
		if (value == Constants.EN_EDITABLE)
		{
			comboBoxWiseDoc.setSelectedItem(canEdit);
		} else if (value == Constants.EN_EDITVARIBLE)
		{
			comboBoxWiseDoc.setSelectedItem(variable);
		} else
		{
			comboBoxWiseDoc.setSelectedItem(noEdit);
		}

		comboBoxWiseDoc.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					comboBoxWiseDoc.setSelectedItem(current);
					if (current.equalsIgnoreCase(canEdit))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue.getKey(Constants.PR_XMLEDIT),
								Constants.EN_EDITABLE + "");
					} else if (current.equalsIgnoreCase(noEdit))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue.getKey(Constants.PR_XMLEDIT),
								Constants.EN_UNEDITABLE + "");
					} else if (current.equalsIgnoreCase(variable))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue.getKey(Constants.PR_XMLEDIT),
								Constants.EN_EDITVARIBLE + "");
					}
				}
			}
		});

	}

	WiseCombobox comboBoxOneobject = new WiseCombobox();
	{
		comboBoxOneobject.addItem(noEdit);
		comboBoxOneobject.addItem(canEdit);
		comboBoxOneobject.addItem(xmlNode);
		int value = GetProfileValue.getIntValue(Constants.PR_EDITMODE);
		if (value == Constants.EN_EDITABLE)
		{
			comboBoxOneobject.setSelectedItem(canEdit);
		} else if (value == Constants.EN_DATANODE)
		{
			comboBoxOneobject.setSelectedItem(xmlNode);
		} else
		{
			comboBoxOneobject.setSelectedItem(noEdit);
		}
		comboBoxOneobject.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					comboBoxOneobject.setSelectedItem(current);
					if (current.equalsIgnoreCase(canEdit))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue.getKey(Constants.PR_EDITMODE),
								Constants.EN_EDITABLE + "");
					} else if (current.equalsIgnoreCase(noEdit))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue.getKey(Constants.PR_EDITMODE),
								Constants.EN_UNEDITABLE + "");
					} else if (current.equalsIgnoreCase(xmlNode))
					{
						ConfigurationInformationDialog.addProFileItem(
								GetProfileValue.getKey(Constants.PR_EDITMODE),
								Constants.EN_DATANODE + "");
					}
				}
			}
		});
	}

	WiseCombobox comboBoxDateType = new WiseCombobox();
	{
		comboBoxDateType.addItem(text);
		comboBoxDateType.addItem(number);
		String value = GetProfileValue.getValue("datatype");
		if (value.equalsIgnoreCase(number))
		{
			comboBoxDateType.setSelectedItem(number);
		} else
		{
			comboBoxDateType.setSelectedItem(text);
		}
		comboBoxDateType.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					comboBoxDateType.setSelectedItem(current);
					if (current.equalsIgnoreCase(text))
					{
						ConfigurationInformationDialog.addProFileItem(
								"datatype", text);
					} else if (current.equalsIgnoreCase(number))
					{
						ConfigurationInformationDialog.addProFileItem(
								"datatype", number);
					}
				}
			}
		});
	}

	WiseCombobox comboBoxOrder = new WiseCombobox();
	{
		comboBoxOrder.addItem(ascending);
		comboBoxOrder.addItem(descending);
		String value = GetProfileValue.getValue("order");
		if (value.equalsIgnoreCase(descending))
		{
			comboBoxOrder.setSelectedItem(descending);
		} else
		{
			comboBoxOrder.setSelectedItem(ascending);
		}
		comboBoxOrder.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					comboBoxOrder.setSelectedItem(current);
					if (current.equalsIgnoreCase(descending))
					{
						ConfigurationInformationDialog.addProFileItem("order",
								descending);
					} else if (current.equalsIgnoreCase(ascending))
					{
						ConfigurationInformationDialog.addProFileItem("order",
								ascending);
					}
				}
			}
		});
	}

	WiseCombobox comboBoxDatePrecedence = new WiseCombobox();
	{
		comboBoxDatePrecedence.addItem(lowerFirst);
		comboBoxDatePrecedence.addItem(upperFirst);
		String value = GetProfileValue.getValue("precedence");
		if (value.equalsIgnoreCase(upperFirst))
		{
			comboBoxDatePrecedence.setSelectedItem(upperFirst);
		} else
		{
			comboBoxDatePrecedence.setSelectedItem(lowerFirst);
		}
		comboBoxDatePrecedence.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					comboBoxDatePrecedence.setSelectedItem(current);
					if (current.equalsIgnoreCase(upperFirst))
					{
						ConfigurationInformationDialog.addProFileItem(
								"precedence", upperFirst);
					} else if (current.equalsIgnoreCase(lowerFirst))
					{
						ConfigurationInformationDialog.addProFileItem(
								"precedence", lowerFirst);
					}
				}
			}
		});
	}

	public WiseDocmentPanel()
	{
		super();
		this.add(new JLabel("文档编辑属性"), new XYConstraints(200, 20, 100, 20));
		this.add(comboBoxWiseDoc, new XYConstraints(300, 20, 100, 20));
		this.add(new JLabel("单个对象编辑属性"), new XYConstraints(170, 60, 130, 20));
		this.add(comboBoxOneobject, new XYConstraints(300, 60, 140, 20));

		JPanel groupPanel = new JPanel();
		groupPanel.setSize(490, 260);
		XYLayout groupLayout = new XYLayout();
		groupLayout.setWidth(490);
		groupLayout.setHeight(260);
		groupPanel.setLayout(groupLayout);
		groupPanel.setBackground(getBackground());

		groupPanel.add(new JLabel("最大循环次数"), new XYConstraints(35, 0, 130, 20));
		groupPanel.add(maxNumber, new XYConstraints(135, 0, 100, 20));
		groupPanel.add(new JLabel("数据类型"), new XYConstraints(35, 40, 80, 20));
		groupPanel.add(comboBoxDateType, new XYConstraints(135, 40, 80, 20));
		groupPanel.add(new JLabel("排序方式"), new XYConstraints(35, 80, 80, 20));
		groupPanel.add(comboBoxOrder, new XYConstraints(135, 80, 80, 20));
		groupPanel.add(new JLabel("优先设置"), new XYConstraints(35, 120, 80, 20));
		groupPanel.add(comboBoxDatePrecedence, new XYConstraints(135, 120, 80,
				20));

		groupPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"组属性", TitledBorder.CENTER, TitledBorder.TOP));
		this.add(groupPanel, new XYConstraints(170, 120, 300, 180));
	}

	public static void main(String[] args)
	{
		JFrame fram = new JFrame();
		fram.setSize(600, 700);
		XYLayout layout = new XYLayout();
		layout.setHeight(700);
		layout.setWidth(600);
		fram.setLayout(layout);
		// WiseDocmentPanel panel = new WiseDocmentPanel();
		// PagesequencePanel panel = new PagesequencePanel();
		// SimplePageMasterPanel panel = new SimplePageMasterPanel();
		// ObjectPanel panel = new ObjectPanel();
		// BlockPanel panel = new BlockPanel();
		TablePanel panel = new TablePanel();
		// SimplePageMasterPanel panel = new SimplePageMasterPanel();
		// SimplePageMasterPanel panel = new SimplePageMasterPanel();
		// SimplePageMasterPanel panel = new SimplePageMasterPanel();
		// SimplePageMasterPanel panel = new SimplePageMasterPanel();
		// SimplePageMasterPanel panel = new SimplePageMasterPanel();
		// SimplePageMasterPanel panel = new SimplePageMasterPanel();
		// SimplePageMasterPanel panel = new SimplePageMasterPanel();
		// SimplePageMasterPanel panel = new SimplePageMasterPanel();
		fram.add(panel, new XYConstraints(0, 0, 600, 700));
		fram.setVisible(true);
	}
}
