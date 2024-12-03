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
 * @SizeTypeAction.java 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.table;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jvnet.flamingo.common.JCommandMenuButton;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.view.LocationConvert;
import com.wisii.wisedoc.view.action.OnlyInOneTableAction;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.TableCell;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-12-30
 */
public class SizeTypeAction extends OnlyInOneTableAction
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent
	 * )
	 */
	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCommandMenuButton)
		{

			Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
					.getUIComponents().get(TableCell.SIZE_TYPE);
			Iterator<List<Object>> iterator = set.get(
					ActionCommandType.RIBBON_ACTION).iterator();
			List<Object> list = iterator.next();
			int index = list.indexOf(e.getSource());
			setTableCellSize(index);
		}
	}

	private void setTableCellSize(int index)
	{
		List<CellElement> tablecells = DocumentUtil.getElements(table,
				com.wisii.wisedoc.document.TableCell.class);
		if (!isNeedtoSet(tablecells, index))
		{
			return;
		}
		double fateofmpt = getFateofmpt(tablecells);
		if (fateofmpt < 0)
		{
			return;
		}
		Document doc = getCurrentDocument();
		for (CellElement cell : tablecells)
		{
			com.wisii.wisedoc.document.TableCell tablecell = (com.wisii.wisedoc.document.TableCell) cell;
			LengthProperty width = tablecell.getInLineProgressionDimension()
					.getOptimum(null);
			if (index == 0)
			{
				double factor = width.getValue() * fateofmpt;
				LengthRangeProperty newwidth = new LengthRangeProperty(
						new PercentLength(factor, new LengthBase(
								LengthBase.TABLE_UNITS)));
				Map<Integer, Object> attrs = new HashMap<Integer, Object>();
				attrs.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, newwidth);
				doc.setElementAttributes(tablecell, attrs, false);
			} else
			{
				double factor = ((PercentLength) width).value();
				int len = (int) (factor / fateofmpt);
				LengthRangeProperty newwidth = new LengthRangeProperty(
						new FixedLength(len));
				Map<Integer, Object> attrs = new HashMap<Integer, Object>();
				attrs.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, newwidth);
				doc.setElementAttributes(tablecell, attrs, false);
			}

		}
		// 以下代码用来同步更新界面
		DocumentPosition pos = getCaretPosition();
		Map<Integer, Object> properties = null;
		if (pos != null)
		{
			CellElement poscell = pos.getLeafElement();
			while (poscell != null
					&& !(poscell instanceof com.wisii.wisedoc.document.TableCell))
			{
				poscell = (CellElement) poscell.getParent();
			}
			if (poscell instanceof com.wisii.wisedoc.document.TableCell)
			{
				properties = poscell.getAttributes().getAttributes();
			}
		} else
		{
			List<CellElement> cellelements = getObjectSelects();
			properties = DocumentUtil.getElementAttributes(DocumentUtil
					.getElements(cellelements,
							com.wisii.wisedoc.document.TableCell.class));
		}
		if (properties != null)
		{
			RibbonUIModel.RibbonUIModelInstance.updateUIState(actionType,
					properties);
			RibbonUIModel.RibbonUIModelInstance.sendPropertyMsg();
		}
	}

	/**
	 * 
	 * 是否需要设置，如果当前单元格的类型和药设置的类型一致，则不需要设置
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private boolean isNeedtoSet(List<CellElement> tableCells, int index)
	{
		if (tableCells == null || tableCells.isEmpty())
		{
			return false;
		}
		com.wisii.wisedoc.document.TableCell tablecell = (com.wisii.wisedoc.document.TableCell) tableCells
				.get(0);
		LengthRangeProperty cellwidth = tablecell
				.getInLineProgressionDimension();
		return (index == 0 && cellwidth.getOptimum(null) instanceof FixedLength)
				|| (index == 1 && cellwidth.getOptimum(null) instanceof PercentLength);
	}

	/**
	 * 
	 * 单位mpt对应的比率数
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private double getFateofmpt(List<CellElement> tableCells)
	{
		if (tableCells == null)
		{
			return -1;
		}
		for (CellElement cell : tableCells)
		{
			com.wisii.wisedoc.document.TableCell tablecell = (com.wisii.wisedoc.document.TableCell) cell;
			Area cellarea = tablecell.getArea();
			if (cellarea != null)
			{
				Block tablearea = LocationConvert.getCurrentTableArea(cellarea);
				if (tablearea != null)
				{
					int tablewidth = tablearea.getIPD();
					if (tablewidth > 0)
					{
						return 1d / tablewidth;
					}
				}
			}
		}
		return -1;
	}

}
