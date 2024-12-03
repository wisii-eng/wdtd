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

package com.wisii.wisedoc.view.ui.actions.dynamicstyle;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.DynamicStylesDialog;
import com.wisii.wisedoc.view.dialog.DynamicStylesDialog.DynamicStylesClass;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

@SuppressWarnings("serial")
public class SetObjectDynamicStyleAction extends Actions
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent
	 * )
	 */
	public void doAction(ActionEvent e)
	{
		DynamicStylesClass type = getSelectType();
		ActionType actiontype = getActionType(type);
		Map<Integer, Object> attrs = RibbonUIModel.getCurrentPropertiesByType()
				.get(actiontype);
		if (type != null)
		{
			DynamicStylesDialog dynamicstyle = new DynamicStylesDialog(attrs,
					type);
			DialogResult result = dynamicstyle.showDialog();
			if (DialogResult.OK.equals(result))
			{
				Map<Integer, Object> newatts = dynamicstyle.getAttributes();
				this.actionType = actiontype;
				setFOProperties(newatts);
			}
		}

	}

	private ActionType getActionType(DynamicStylesClass dsc)
	{
		ActionType type = null;
		if (dsc == DynamicStylesClass.Cell)
		{
			type = ActionType.TABLE_CELL;
		} else if (dsc == DynamicStylesClass.Row)
		{
			type = ActionType.TABLE_ROW;
		} else if (dsc == DynamicStylesClass.Table)
		{
			type = ActionType.TABLE;
		} else if (dsc == DynamicStylesClass.Container)
		{
			type = ActionType.BLOCK_CONTAINER;
		} else if (dsc == DynamicStylesClass.Image)
		{
			type = ActionType.SVG_GRAPHIC;
		} else if (dsc == DynamicStylesClass.Barcod)
		{
			type = ActionType.SVG_GRAPHIC;
		} else if (dsc == DynamicStylesClass.Number)
		{
			type =ActionType.NUMBERTEXT;
		} else if (dsc == DynamicStylesClass.Datetime)
		{
			type = ActionType.DATETIME;
		} else if (dsc == DynamicStylesClass.SVG)
		{
			type = ActionType.SVG_GRAPHIC;
		}
		return type;
	}

	private DynamicStylesClass getSelectType()
	{
		DynamicStylesClass type = null;
		List<CellElement> elements = getObjectSelects();
		if (elements!=null&&!elements.isEmpty())
		{
			CellElement current = elements.get(0);
			if (current instanceof TableCell)
			{
				type = DynamicStylesClass.Cell;
			} else if (current instanceof TableRow)
			{
				type = DynamicStylesClass.Row;
			} else if (current instanceof Table)
			{
				type = DynamicStylesClass.Table;
			} else if (current instanceof BlockContainer)
			{
				type = DynamicStylesClass.Container;
			} else if (current instanceof ExternalGraphic)
			{
				type = DynamicStylesClass.Image;
			} else if (current instanceof BarCode)
			{
				type = DynamicStylesClass.Barcod;
			} else if (current instanceof AbstractSVG)
			{
				type = DynamicStylesClass.SVG;
			} else if (current instanceof DateTimeInline)
			{
				type = DynamicStylesClass.Datetime;
			} else if (current instanceof NumberTextInline)
			{
				type = DynamicStylesClass.Number;
			}
		}
		return type;
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		List<CellElement> objectselects = getObjectSelects();
		if (objectselects != null && !objectselects.isEmpty())
		{
			Document doc = getCurrentDocument();
			if (doc.getDataStructure() != null)
			{
				return true;
			} else
			{
				return false;
			}
		}
		return false;
	}
}
