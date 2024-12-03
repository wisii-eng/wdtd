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

package com.wisii.wisedoc.view.ui.actions.datatreat;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.attribute.transform.DataTransformTable;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.dialog.SimpleDataSourceDialog;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

@SuppressWarnings("serial")
public class SetSimpleDatatreatTransformTableAction extends BaseAction
{

	@Override
	public void doAction(ActionEvent e)
	{
		DataTransformTable olddata = null;
		List<DocumentPositionRange> ranges = getSelects();
		DocumentPositionRange[] rs = null;
		Document doc = getCurrentDocument();
		if (!ranges.isEmpty())
		{
			rs = new DocumentPositionRange[ranges.size()];
			rs = ranges.toArray(rs);

			Map<Integer, Object> attrs = doc.getInlineAttributes(rs);
			if (attrs != null)
			{
				Object lecon = attrs.get(Constants.PR_TRANSFORM_TABLE);
				if (!Constants.NULLOBJECT.equals(lecon))
				{
					olddata = (DataTransformTable) lecon;
				}
			}
		}
		SimpleDataSourceDialog condia = new SimpleDataSourceDialog(null,
				olddata);
		DialogResult result = condia.showDialog();
		if (DialogResult.OK.equals(result))
		{
			DataTransformTable newle = condia.getSource();

			if (doc == null || newle == null)
			{
				return;
			}
			Map<Integer, Object> newatts = new HashMap<Integer, Object>();
			newatts.put(Constants.PR_TRANSFORM_TABLE, newle);
			if (rs != null)
			{
				doc.setInlineAttributes(rs, newatts, false);
			}
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		Document doc = getCurrentDocument();
		if (doc.getDataStructure() == null)
		{
			return false;
		}
		// 去掉了鼠标单击选中可用的操作，因为鼠标单击后还可以拖选，
		// 在设置属性和判断清除该属性时还需要分别判断，太麻烦，所以舍弃
		// DocumentPosition docposition = getCaretPosition();

		// if (docposition != null)
		// {
		// System.out.println("docposition !=null");
		// CellElement leaf = docposition.getLeafElement();
		// allbinding = isBindingInline(leaf);
		// }
		// if (!allbinding)
		// {
		List<CellElement> elements = getAllSelectObjects();
		if (elements != null && !elements.isEmpty())
		{
			for (CellElement current : elements)
			{
				boolean currentflg = isBindingInline(current);
				if (!currentflg)
				{
					return false;
				}
			}
			return true;
		}
		// }
		return false;
	}
}
