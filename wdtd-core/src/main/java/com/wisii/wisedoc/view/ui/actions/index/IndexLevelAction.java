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
package com.wisii.wisedoc.view.ui.actions.index;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jvnet.flamingo.common.JCommandMenuButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

/**
 * 索引级别设置菜单
 * @author 闫舒寰
 * @version 1.0 2009/04/13
 */
public class IndexLevelAction extends Actions {
	
	private List<Object> componentList;
	
	private void getComponents() {
		Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
				.getUIComponents().get(this.getActionID());

		componentList = new ArrayList<Object>();
		
		for (List<Object> list2 : set.get(ActionCommandType.RIBBON_ACTION)) {
			List<Object> list = list2;
			if (!list.isEmpty() && list.get(0) instanceof JCommandMenuButton) {
				componentList = list;
			}
		}
	}

	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JCommandMenuButton)
		{
			getComponents();
			setFOProperty(Constants.PR_BLOCK_LEVEL, componentList.indexOf(e.getSource()));
		}
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(actionCommand);

		if (actionCommand == ActionCommandType.RIBBON_ACTION) {
			Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
					.getUIComponents().get(this.getActionID());
			for (List<Object> list : set.get(ActionCommandType.RIBBON_ACTION)) {
				if (!list.isEmpty()
						&& list.get(0) instanceof JCommandMenuButton) {
					if (RibbonUIModel.getCurrentPropertiesByType() == null
							|| RibbonUIModel.getCurrentPropertiesByType().get(
									ActionType.LAYOUT) == null) {
						return;
					}
					Object value = RibbonUIModel.getCurrentPropertiesByType()
							.get(this.actionType).get(Constants.PR_BLOCK_LEVEL);

					if (value instanceof Integer) {
						int index = (Integer) value;
						((JCommandMenuButton) list.get(index)).getActionModel()
								.setSelected(true);
					}
				}
			}
		}

	}

}
