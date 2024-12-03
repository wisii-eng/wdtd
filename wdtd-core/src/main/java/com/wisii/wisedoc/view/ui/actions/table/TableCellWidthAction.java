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
package com.wisii.wisedoc.view.ui.actions.table;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.Property;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.swing.ui.PercentLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 设置单元格的宽度
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/04
 */
public class TableCellWidthAction extends Actions
{
	protected Table table;

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof PercentLengthSpinner)
		{
			PercentLengthSpinner ui = (PercentLengthSpinner) e.getSource();
			Object value = ui.getValue();
			LengthProperty length = null;
			if (value instanceof Double)
			{
				length = new PercentLength((Double) value, new LengthBase(
						LengthBase.TABLE_UNITS));
			} else if (value instanceof FixedLength)
			{
				length = (LengthProperty) value;
			}
			if (length != null)
			{
				Property newlen = new LengthRangeProperty(length);
				setFOProperty(Constants.PR_INLINE_PROGRESSION_DIMENSION, newlen);
			}
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_INLINE_PROGRESSION_DIMENSION))
		{
			if (uiComponent instanceof PercentLengthSpinner)
			{
				PercentLengthSpinner ui = (PercentLengthSpinner) uiComponent;
				LengthRangeProperty length = (LengthRangeProperty) evt
						.getNewValue();
				Object uivalue = null;
				if (length != null)
				{
					LengthProperty oldlen = length.getOptimum(null);

					if (oldlen instanceof EnumLength)
					{
						Length enlength = ((EnumLength) oldlen).getFixLength();
						if (enlength instanceof FixedLength)
						{
							uivalue = (FixedLength) enlength;
						}
					} else if (oldlen instanceof FixedLength)
					{
						uivalue = (FixedLength) oldlen;
					} else if (oldlen instanceof PercentLength)
					{
						uivalue = ((PercentLength) oldlen).value();
					}
				}
				ui.initValue(uivalue);

			}

		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);

		if (uiComponent instanceof PercentLengthSpinner)
		{
			PercentLengthSpinner ui = (PercentLengthSpinner) uiComponent;
			FixedLength length = new FixedLength(0);
			ui.initValue(length);
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof PercentLengthSpinner)
		{

			PercentLengthSpinner ui = (PercentLengthSpinner) uiComponent;
			ui.initValue(null);
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}

		List<CellElement> cells = getAllSelectObjects();
		DocumentPosition pos = getCaretPosition();
		if ((cells == null || cells.isEmpty()) && pos != null)
		{
			CellElement poselement = pos.getLeafElement();
			if (cells == null)
			{
				cells = new ArrayList<CellElement>();
			}
			if (!cells.contains(poselement))
			{
				cells.add(poselement);
			}
		}
		if (cells == null || cells.isEmpty())
		{
			return false;
		}
		int size = cells.size();
		Table oldtable = null;
		for (int i = 0; i < size; i++)
		{
			CellElement cell = cells.get(i);
			// 找到该对象的最近的是表格的对象
			while (cell != null && !(cell instanceof Table)
					&& !(cell instanceof Flow))
			{
				cell = (CellElement) cell.getParent();
			}
			if (cell instanceof Table)
			{
				if (oldtable == null)
				{
					oldtable = (Table) cell;
				} else
				{
					if (oldtable != cell)
					{
						return false;
					}
				}

			}
			// 如果父对象不是表格，则返回假
			else
			{
				return false;
			}
		}
		table = oldtable;
		return true;
	}

}
