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
 * @ParamTableModel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.wisii.wisedoc.document.attribute.edit.Parameter;
import com.wisii.wisedoc.document.attribute.edit.Parameter.DataTyle;
import com.wisii.wisedoc.document.attribute.edit.Parameter.ParamTyle;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-1
 */
public class ParamTableModel extends AbstractTableModel
{
	private List<Parameter> parameters = new ArrayList<Parameter>();

	public ParamTableModel(List<Parameter> parameters)
	{
		if (parameters != null)
		{
			this.parameters.addAll(parameters);
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
		return 4;
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
		return parameters.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Parameter parameter = parameters.get(rowIndex);
		if (columnIndex == 0)
		{
			return parameter.getName();
		} else if (columnIndex == 1)
		{
			ParamTyle type = parameter.getType();
			if (type == ParamTyle.CONSTANCE)
			{
				return RibbonUIText.EDIT_CONNWITH_PARM_TYPE_CONSTANCE;
			} else if (type == ParamTyle.XPATH)
			{
				return RibbonUIText.EDIT_CONNWITH_PARM_TYPE_XPATH;
			} else
			{
				return RibbonUIText.EDIT_CONNWITH_PARM_TYPE_UI;
			}
		} else if (columnIndex == 2)
		{
			DataTyle dataype = parameter.getDatatype();
			if (dataype == DataTyle.NUMBER)
			{
				return RibbonUIText.EDIT_CONNWITH_PARM_DATATYPE_NUMBER;
			} else
			{
				return RibbonUIText.EDIT_CONNWITH_PARM_DATATYPE_STRING;
			}
		} else
		{
			return parameter.getValue();
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		Parameter parameter = parameters.get(rowIndex);

		if (columnIndex == 0)
		{
			String newname = (String) value;
			if (newname != null)
			{
				newname = newname.trim();
				if (!newname.isEmpty())
				{
					for (Parameter para : parameters)
					{
						if (newname.equals(para.getName()))
						{
							return;
						}
					}
				} else
				{
					newname = null;
				}
			}
			parameter.setName(newname);
		} else if (columnIndex == 1)
		{
			ParamTyle oldtype = parameter.getType();
			ParamTyle type = (ParamTyle) value;
			parameter.setType(type);
			if (type != oldtype)
			{
				parameter.setValue(null);
			}
		} else if (columnIndex == 2)
		{
			parameter.setDatatype((DataTyle) value);
		} else
		{
			parameter.setValue(value);
		}
	}

	@Override
	public String getColumnName(int column)
	{
		if (column == 0)
		{
			return RibbonUIText.EDIT_CONNWITH_PARM_NAME;
		} else if (column == 1)
		{
			return RibbonUIText.EDIT_CONNWITH_PARM_TYPE;
		} else if (column == 2)
		{
			return RibbonUIText.EDIT_CONNWITH_PARM_DATATYPE;
		} else
		{
			return RibbonUIText.EDIT_CONNWITH_PARM_VALUE;
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
	public List<Parameter> getParameters()
	{
		return parameters;
	}

	public boolean isAllSetOk()
	{
		if (parameters != null && !parameters.isEmpty())
		{
			for (Parameter parameter : parameters)
			{
				if (parameter == null)
				{
					return false;
				}
				String name = parameter.getName();
				if (name == null || name.trim().isEmpty())
				{
					return false;
				}
				ParamTyle type = parameter.getType();
				if (type != ParamTyle.UI && parameter.getValue() == null)
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
		if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2)
		{
			return true;
		} else
		{
			Parameter parameter = parameters.get(rowIndex);
			return parameter.getType() != ParamTyle.UI;
		}

	}

	public void addRow(int rowindex)
	{
		if (rowindex >= parameters.size() || rowindex < 0)
		{
			parameters.add(new Parameter(DataTyle.STRING, null,
					ParamTyle.XPATH, null));
		} else
		{
			parameters.add(rowindex + 1, new Parameter(DataTyle.STRING, null,
					ParamTyle.XPATH, null));
		}
	}

	public void delRow(int rowindex)
	{
		if (rowindex >= parameters.size() || rowindex < 0)
		{
			return;
		} else
		{
			parameters.remove(rowindex);
		}
	}
}
