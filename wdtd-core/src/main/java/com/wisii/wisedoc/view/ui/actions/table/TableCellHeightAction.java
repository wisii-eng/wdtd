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
import com.wisii.wisedoc.document.datatype.PercentBase;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 单元格高度设置动作
 * @author 闫舒寰
 * @version 1.0 2009/02/04
 */
public class TableCellHeightAction extends Actions
{
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner ui = (FixedLengthSpinner) e.getSource();
			FixedLength length = ui.getValue();
			Property newlen = new LengthRangeProperty(length);
			setFOProperty(Constants.PR_BLOCK_PROGRESSION_DIMENSION, newlen);

		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_BLOCK_PROGRESSION_DIMENSION))
		{
			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				LengthRangeProperty length = (LengthRangeProperty) evt
						.getNewValue();
				FixedLength uivalue = null;
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
						PercentBase base = ((PercentLength) oldlen).getBaseLength();
						if(base instanceof LengthBase&&((LengthBase)base).getBaseLength() instanceof FixedLength)
						{
							uivalue = (FixedLength) ((LengthBase)base).getBaseLength();
						}
					}
				}
				ui.initValue(uivalue);

			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);

		if (uiComponent instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			FixedLength length = new FixedLength(14.4d,"pt");
			ui.initValue(length);
		}
	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			ui.initValue(null);
		}
	}

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
		return true;
	}

}
