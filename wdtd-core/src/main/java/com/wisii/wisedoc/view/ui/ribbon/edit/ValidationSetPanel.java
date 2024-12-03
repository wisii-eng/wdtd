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
/**
 * @ValidationSetPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.wisii.wisedoc.document.attribute.edit.Validation;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.ForEachEditorComponent;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：验证设置主面板
 * 
 * 作者：zhangqiang 创建日期：2009-7-17
 */
public class ValidationSetPanel extends JPanel
{
	private JPanel commonpanel = new JPanel(new GridLayout(2, 1));
	private JPanel validatepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel msgpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel parasetpanel = new JPanel(new BorderLayout());
	private JLabel validatelabel = new JLabel(RibbonUIText.VALIDATION_VALIDATE);
	private WiseCombobox validatebox = new WiseCombobox(
			new DefaultComboBoxModel(new String[]
			{ RibbonUIText.VALIDATION_TYPE_SCHEMA,
					RibbonUIText.VALIDATION_TYPE_ISBLANKORNULL,
					RibbonUIText.VALIDATION_TYPE_ISBYTE,
					RibbonUIText.VALIDATION_TYPE_ISDOUBLE,
					RibbonUIText.VALIDATION_TYPE_ISFLOAT,
					RibbonUIText.VALIDATION_TYPE_ISINT,
					RibbonUIText.VALIDATION_TYPE_ISLONG,
					RibbonUIText.VALIDATION_TYPE_ISSHORT,
					RibbonUIText.VALIDATION_TYPE_MATCHREGEXP,
					RibbonUIText.VALIDATION_TYPE_MAXLENGTH,
					RibbonUIText.VALIDATION_TYPE_MINLENGTH,
					RibbonUIText.VALIDATION_TYPE_ISINRANGE,
					RibbonUIText.VALIDATION_TYPE_MAXVALUE,
					RibbonUIText.VALIDATION_TYPE_MINVALUE,
					RibbonUIText.VALIDATION_TYPE_ISINRANGEOFDATE }));
	private JLabel msglabel = new JLabel(RibbonUIText.VALIDATION_MSG);
	private WiseTextField msgtext = new WiseTextField(8);
	private ParameterTable parmtable;
	private JButton btnAdd = new JButton(RibbonUIText.VALIDATION_PARM_ADD);
	private JButton btndelete = new JButton(RibbonUIText.VALIDATION_PARM_DEL);
	private List<String> validatetypes =
	Arrays.asList(Validation.SCHEMA, Validation.ISBLANKORNULL, Validation.ISBYTE,
		Validation.ISDOUBLE, Validation.ISFLOAT, Validation.ISINT,
		Validation.ISLONG, Validation.ISSHORT, Validation.MATCHREGEXP,
		Validation.MAXLENGTH, Validation.MINLENGTH, Validation.ISINRANGE,
		Validation.MAXVALUE, Validation.MINVALUE,
		Validation.ISINRANGEOFDATE);

	ValidationSetPanel(Validation validation)
	{
		if (validation != null)
		{
			validatebox.initIndex(validatetypes.indexOf(validation.getValidate()));
			String msg = validation.getMsg();
			if (msg != null)
			{
				msgtext.setText(msg);
			}
			parmtable = new ParameterTable(validation.getParms());
		} else
		{
			parmtable = new ParameterTable(null);
		}
		validatepanel.add(validatelabel);
		validatepanel.add(validatebox);
		msgpanel.add(msglabel);
		msgpanel.add(msgtext);
		commonpanel.add(validatepanel);
		commonpanel.add(msgpanel);
		add(commonpanel, BorderLayout.NORTH);
		JScrollPane parmscrpane = new JScrollPane();
		parmscrpane.getViewport().add(parmtable);
		parmscrpane.setPreferredSize(new Dimension(500, 200));
		parasetpanel.add(parmscrpane, BorderLayout.CENTER);
		parasetpanel
				.setBorder(new TitledBorder(RibbonUIText.VALIDATION_PARMSET));
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonpanel.add(btnAdd);
		buttonpanel.add(btndelete);
		parasetpanel.add(buttonpanel, BorderLayout.NORTH);
		add(parasetpanel, BorderLayout.CENTER);
		resetCompoentState();
		initAction();

	}

	private void initAction()
	{
		validatebox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String validate = validatetypes.get(validatebox.getSelectedIndex());
				if (Validation.SCHEMA.equals(validate))
				{
					msgtext.setText("");
					parmtable.removeAll();
				}
				resetCompoentState();

			}
		});
		btnAdd.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				parmtable.addRow();

			}

		});
		btndelete.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				parmtable.delerow();

			}
		});

	}

	private void resetCompoentState()
	{
		String validate = validatetypes.get(validatebox.getSelectedIndex());
		if (Validation.SCHEMA.equals(validate))
		{
			msgtext.setEnabled(false);
			btnAdd.setEnabled(false);
			btndelete.setEnabled(false);
			parmtable.setEnabled(false);
		} else
		{
			msgtext.setEnabled(true);
			parmtable.setEnabled(true);
			int rowcount = parmtable.getRowCount();
			int selectrow = parmtable.getSelectedRow();
			btndelete.setEnabled(selectrow > -1);
			btnAdd.setEnabled(rowcount == 0 || selectrow > -1);
		}
	}

	private class ParameterTable extends JTable
	{
		ParameterTable(List parms)
		{
			if (parms != null && !parms.isEmpty())
			{
				int size = parms.size();
				Object[][] parmobjects = new Object[size][1];
				for (int i = 0; i < size; i++)
				{
					parmobjects[i][0] = parms.get(i);
				}
				setModel(new DefaultTableModel(parmobjects, new String[]
				{ RibbonUIText.VALIDATION_PARM }));
			} else
			{
				setModel(new DefaultTableModel(new String[]
				{ RibbonUIText.VALIDATION_PARM }, 0));
			}
			getSelectionModel().addListSelectionListener(
					new ListSelectionListener()
					{
						public void valueChanged(ListSelectionEvent e)
						{
							int selectrow = ParameterTable.this
									.getSelectedRow();
							btndelete.setEnabled(selectrow > -1);
							btnAdd.setEnabled(selectrow > -1);
						}
					});
		}

		private ParmEditor parmeditor = new ParmEditor();

		@Override
		public TableCellEditor getCellEditor(int row, int column)
		{
			return parmeditor;
		}

		private class ParmEditor extends javax.swing.AbstractCellEditor
				implements TableCellEditor, PropertyChangeListener
		{

			ForEachEditorComponent com;;

			private ParmEditor()
			{
			}

			public Component getTableCellEditorComponent(JTable table,
					Object value, boolean isSelected, int row, int column)
			{
				com = new ForEachEditorComponent();
				com.setValue(value);
				com.addPropertyChangeListener(this);
				return com;
			}

			public Object getCellEditorValue()
			{
				return com.getValue();
			}

			public void propertyChange(PropertyChangeEvent evt)
			{
				stopCellEditing();
			}

			void requestFocus()
			{
				com.requestFocus();
			}
		}

		boolean ValidateParm()
		{
			if (getCellEditor() != null)
			{
				getCellEditor().stopCellEditing();
			}
			int rowsize = getRowCount();
			for (int i = 0; i < rowsize; i++)
			{
				Object value = getValueAt(i, 0);
				if (value == null)
				{
					editCellAt(i, 0);
					parmeditor.requestFocus();
					return false;
				}
			}
			return true;
		}

		List getParms()
		{
			int rowsize = getRowCount();
			if (rowsize > 0)
			{
				List parms = new ArrayList();
				for (int i = 0; i < rowsize; i++)
				{
					Object value = getValueAt(i, 0);
					if (value != null)
					{
						parms.add(value);
					}

				}
				return parms;
			}
			return null;
		}

		void addRow()
		{
			if (getCellEditor() != null)
			{
				getCellEditor().stopCellEditing();
			}
			int rowadd = getSelectedRow();
			int rowcount = getRowCount();
			if (rowadd < 0)
			{
				rowadd = rowcount - 1;
			}
			DefaultTableModel model = (DefaultTableModel) getModel();
			if (rowcount > 0)
			{
				model.insertRow(rowadd + 1, new Object[]
				{ null });
				setRowSelectionInterval(rowadd + 1, rowadd + 1);
			} else
			{
				model.addRow(new Object[]
				{ null });
				setRowSelectionInterval(0, 0);
			}
		}

		void delerow()
		{
			if (getCellEditor() != null)
			{
				getCellEditor().stopCellEditing();
			}
			int rowdel = getSelectedRow();
			if (rowdel > -1)
			{
				DefaultTableModel model = (DefaultTableModel) getModel();
				model.removeRow(rowdel);
				if (rowdel < getRowCount())
				{
					setRowSelectionInterval(rowdel, rowdel);
				} else
				{
					if (rowdel > 0)
					{
						setRowSelectionInterval(rowdel - 1, rowdel - 1);
					} else if (rowdel == 0 && getRowCount() > 0)
					{
						setRowSelectionInterval(0, 0);
					}
					// rowcount==0;
					else
					{
						btndelete.setEnabled(false);
						btnAdd.setEnabled(true);
					}
				}

			}
		}
	}

	boolean ValidateSet()
	{
		return parmtable.ValidateParm();
	}

	Validation getValidation()
	{
		String validate = validatetypes.get(validatebox.getSelectedIndex());
		String msg = msgtext.getText();
		if (msg != null)
		{
			msg = msg.trim();
		}
		if (msg != null && msg.equals(""))
		{
			msg = null;
		}
		return new Validation(validate, msg, parmtable.getParms());
	}
}
