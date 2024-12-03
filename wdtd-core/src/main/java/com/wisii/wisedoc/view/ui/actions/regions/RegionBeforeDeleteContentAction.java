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

package com.wisii.wisedoc.view.ui.actions.regions;

import java.awt.event.ActionEvent;
import java.util.List;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.ui.actions.pagelayout.DefaultSimplePageMasterActions;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 类功能描述：删除页眉内容动作
 * 
 * 作者：钟亚军 创建日期：2010-1-7
 */
@SuppressWarnings("serial")
public class RegionBeforeDeleteContentAction extends
		DefaultSimplePageMasterActions
{

	@Override
	public void doAction(ActionEvent e)
	{
		SimplePageMaster currentspm = ViewUiUtil
				.getCurrentSimplePageMaster(this.actionType);
		DocumentPosition olddocumentposition = getCaretPosition();
		boolean isstart = olddocumentposition.isStartPos();
		PageSequence currentps = WisedocUtil.getCurrentPageSequence();
		if (currentps != null)
		{
			List<CellElement> children = currentps.getAllChildren();
			Document doc = SystemManager.getCurruentDocument();
			String name = currentspm.getRegions().get(
					Constants.FO_REGION_BEFORE).getRegionName();
			if (children != null)
			{
				for (CellElement current : children)
				{
					if (current instanceof StaticContent)
					{
						StaticContent currentsc = (StaticContent) current;
						String scname = currentsc.getFlowName();
						if (name.equals(scname))
						{
							List<CellElement> scchildren = currentsc
									.getAllChildren();
							doc.deleteElements(scchildren, currentsc);
							DocumentPosition documentposition = new DocumentPosition(currentsc, isstart, Boolean.TRUE, -1, olddocumentposition.getPageIndex());
							
							RibbonUpdateManager.Instance.getCurrentEditPanel()
									.setCaretPosition(documentposition);
							break;
						}
					}
				}
			}
		}
	}
}
