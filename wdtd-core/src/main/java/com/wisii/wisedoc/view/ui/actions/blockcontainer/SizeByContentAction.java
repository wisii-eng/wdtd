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
 * @SizeByContentAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.blockcontainer;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.jvnet.flamingo.common.JCommandToggleButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：blockcontainer的大小不固定事件类
 * 
 * 作者：zhangqiang 创建日期：2009-1-12
 */
public class SizeByContentAction extends Actions {
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JCommandToggleButton) {
			setFOProperty(Constants.PR_BLOCK_PROGRESSION_DIMENSION, null);
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt) {
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_BLOCK_PROGRESSION_DIMENSION)) {
			if (uiComponent instanceof JCommandToggleButton) {
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				Object bpd = evt.getNewValue();
				if ( bpd== null
						||bpd == Constants.NULLOBJECT) {
					ui.getActionModel().setSelected(true);
				} else {
					ui.getActionModel().setSelected(false);
				}
			}
		}
	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_OVERFLOW)) {
			if (uiComponent instanceof JCommandToggleButton) {
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				ui.getActionModel().setSelected(false);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof JCommandToggleButton) {
			JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
			ui.getActionModel().setSelected(false);
		}
	}

	public boolean isAvailable() {
		if (!super.isAvailable()) {
			return false;
		}
		Map<Integer, Object> attmap = RibbonUIModel
				.getCurrentPropertiesByType().get(actionType);
		EnumProperty posenum = null;
		if (attmap != null) {
			posenum = (EnumProperty) attmap.get(Constants.PR_ABSOLUTE_POSITION);
		}
		// 如果是绝对定位，则不能设置大小随内容变化而变化。
		if (posenum == null) {
			posenum = (EnumProperty) InitialManager.getInitialValue(
					Constants.PR_ABSOLUTE_POSITION, null);
		}
		if (posenum != null && posenum.getEnum() == Constants.EN_ABSOLUTE) {
			return false;
		} else {
			return true;
		}
	}
}
