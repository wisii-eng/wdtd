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
 * @FormulaTable.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.wisii.wisedoc.document.attribute.edit.Formula;
import com.wisii.wisedoc.document.attribute.edit.Parameter;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.component.ForEachEditorComponent;
import com.wisii.wisedoc.view.component.TextAndButtonComponent;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-1
 */
public class FormulaTable extends JTable
{
	NodeChooseEditor nodeeditor = new NodeChooseEditor();
	
	FormulaEditor formulaEditor = new FormulaEditor();
	
	private ParamTable conwith;

	FormulaTable(List<Formula> formulas,ParamTable conwith)
	{
		this.conwith = conwith;
		List<Formula> sformulas = new ArrayList<Formula>();
		if (formulas != null)
		{
			for (Formula formula : formulas)
			{
				sformulas.add(new Formula(formula.getExpression(), formula
						.getXpath()));
			}
		}
		setModel(new FormulaTableModel(sformulas));
	}
	public TableCellEditor getCellEditor(int row, int column)
	{
		 if (column == 0)
		{
			return formulaEditor;
		} else
		{
			return nodeeditor;
		}
	}
	
	private class FormulaEditor extends javax.swing.AbstractCellEditor implements TableCellEditor, PropertyChangeListener{

		FormulaCom fcom ;
		
		private FormulaEditor(){
		}
		
		@Override
		public Object getCellEditorValue() {
			Object value = fcom.getValue();
			return value;
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			stopCellEditing();
			
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			fcom = new FormulaCom();
			fcom.setValue(value);
			fcom.addPropertyChangeListener(this);
			return fcom;
		}

		

	}

	private class FormulaCom extends TextAndButtonComponent{
		public FormulaCom() {
			txtComp.setEditable(false);
			btnComp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List<Parameter> parms = conwith.getParameters();
					FormulaVar[] para = new FormulaVar[parms.size()];
					for (int i = 0; i < parms.size(); i++) {
						para[i] = new FormulaVar(parms.get(i).getName());
					}
					String value = txtComp.getText();
					List content =	dealValue(value);
					FormulaEditorDialog feditDialog = new FormulaEditorDialog(null,new FormulaExpression(content),para);
					DialogResult result = feditDialog.showDialog();
					if (result == DialogResult.OK)
					{
						FormulaExpression newValue = feditDialog.getNewValue();
						FormulaCom.this.setValue(newValue.toString());
						feditDialog.dispose();
					}
				}

				
			});
		}
	}
	private List dealValue(String value) {
		String regEx = "([#][{][a-zA-Z_$][a-zA-Z0-9_$]*[}])";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(value);
		String[] ss = value.split(regEx);
		List content = new ArrayList();
		int i = 0;
		while (mat.find()) {
//			System.out.print(ss[i++]);
			content.add(ss[i++]);
//			System.out.print(mat.group(1));
			content.add(new FormulaVar(mat.group(1)));
		}
		try {
			content.add(ss[i]);
		} catch (Exception e) {
		}
//		System.out.println(ss[i]);
		return content;
		
	}
	private class NodeChooseEditor extends javax.swing.AbstractCellEditor implements TableCellEditor, PropertyChangeListener {
		ForEachEditorComponent com;;

		private NodeChooseEditor() {
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			com = new ForEachEditorComponent();
			com.setValue(value);
			com.addPropertyChangeListener(this);
			return com;
		}

		public Object getCellEditorValue() {
			Object value = com.getValue();
			if (value instanceof String) {
				value = null;
			}
			return value;
		}

		public void propertyChange(PropertyChangeEvent evt) {
			stopCellEditing();
		}
	}
	public void addRow()
	{
		int rowselect = getSelectedRow();
		if (rowselect < 0)
		{
			rowselect = getRowCount();
		}
		((FormulaTableModel) getModel()).addRow(rowselect);
		updateUI();
	}

	public void delRow(int rowindex)
	{
		((FormulaTableModel) getModel()).delRow(rowindex);
		updateUI();
	}
	public boolean isAllSetOk()
	{
		if(getCellEditor()!=null)
		{
			getCellEditor().stopCellEditing();
		}
		return ((FormulaTableModel) getModel()).isAllSetOk();	
	}
	public List<Formula> getFormulas()
	{
		return ((FormulaTableModel) getModel()).getFormulas();
	}
}
