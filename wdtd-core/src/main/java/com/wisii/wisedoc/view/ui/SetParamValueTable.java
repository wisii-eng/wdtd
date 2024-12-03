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

package com.wisii.wisedoc.view.ui;

import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;

@SuppressWarnings("serial")
public class SetParamValueTable extends JTable
{

	Map<String, String> params;

	public SetParamValueTable(Map<String, String> params)
	{
		super();
		this.params = params;
		this.setRowHeight(25);
		Object[] columnNames = new Object[2];
		columnNames[0] = MessageResource.getMessage(MessageConstants.PARAMNAME);
		columnNames[1] = MessageResource
				.getMessage(MessageConstants.PARAMVALUE);
		Object[][] models = getModelByMap();

		DefaultTableModel newdatamode = new DefaultTableModel(models,
				columnNames);
		this.setModel(newdatamode);
	}

	public Object[][] getModelByMap()
	{
		Object[][] defaultmodels = null;
		if (params != null && !params.isEmpty())
		{
			Object[] keys = params.keySet().toArray();
			int size = keys.length;
			Object[][] models = new Object[size][2];
			for (int i = 0; i < size; i++)
			{
				Object key = keys[i];
				String value = params.get(key);
				models[i][0] = key.toString();
				models[i][1] = value;
			}
			return models;
		}
		return defaultmodels;
	}

	@Override
	public boolean isCellEditable(int row, int column)
	{
		if (column == 0)
		{
			return false;
		}
		return true;
	}

	public void setParams()
	{
		if (params != null)
		{
			params.clear();
			int row = this.getRowCount();
			for (int r = 0; r < row; r++)
			{
				Object ko = getValueAt(r, 0);
				if (ko != null)
				{
					String key = ko.toString();
					Object vo = getValueAt(r, 1);
					String value = vo == null ? "" : vo.toString();
					params.put(key, value);
				}
			}
		}
	}
}
