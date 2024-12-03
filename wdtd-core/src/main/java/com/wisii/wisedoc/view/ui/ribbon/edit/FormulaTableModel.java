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
 * @FormulaTableModel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.edit.Formula;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-1
 */
public class FormulaTableModel extends AbstractTableModel
{
	private List<Formula> formulas = new ArrayList<Formula>();

	public FormulaTableModel(List<Formula> formulas)
	{
		if (formulas != null && formulas.size()>0)
		{
			this.formulas.addAll(formulas);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount()
	{
		// TODO Auto-generated method stub
		return 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount()
	{
		// TODO Auto-generated method stub
		return formulas.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Formula formula = formulas.get(rowIndex);
		if (columnIndex == 0)
		{
			return formula.getExpression();
		} else
		{
			return formula.getXpath();
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		Formula formula = formulas.get(rowIndex);
		if (columnIndex == 0)
		{
			String ex = (String)value;
			if(ex != null&&ex.trim().isEmpty())
			{
				ex = null;
			}
			formula.setExpression(ex);
		}  else
		{
			formula.setXpath((BindNode) value);
		}
	}

	@Override
	public String getColumnName(int column)
	{
		if (column == 0)
		{
			return RibbonUIText.EDIT_CONNWITH_FORMUL_EXPRESSION;
		}else
		{
			return RibbonUIText.EDIT_CONNWITH_FORMUL_NODE;
		}
	}

	/**
	 * 
	 * {方法的功能/动作描述}:
	 * 
	 * @param 引入参数
	 *            : {引入参数说明}
	 * @return List<FileSource>: {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public List<Formula> getFormulas()
	{
		return formulas;
	}

	public boolean isAllSetOk()
	{
		if (formulas != null && !formulas.isEmpty())
		{
			for (Formula formula : formulas)
			{
				if (formula == null)
				{
					return false;
				}
				if(formula.getExpression()==null||formula.getXpath()==null)
				{
					return false;
				}

			}
			return true;
		} else
		{
			return true;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
			return true;
	}

	public void addRow(int rowindex)
	{
		if (rowindex >= formulas.size() || rowindex < 0)
		{
			formulas.add(new Formula(null, null));
		} else
		{
			formulas.add(rowindex + 1, new Formula(null, null));
		}
	}

	public void delRow(int rowindex)
	{
		if (rowindex >= formulas.size() || rowindex < 0)
		{
			return;
		} else
		{
			formulas.remove(rowindex);
		}
	}
}
