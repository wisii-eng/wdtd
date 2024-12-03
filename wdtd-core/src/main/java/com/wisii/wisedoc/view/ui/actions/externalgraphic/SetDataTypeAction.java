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
 * @SetDataTypeAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.externalgraphic;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：设置数据类型事件类
 * 
 * 作者：zhangqiang 创建日期：2008-12-23
 */
public class SetDataTypeAction extends Actions {
	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof WiseCombobox) {
			WiseCombobox value = (WiseCombobox) e.getSource();
			int index = value.getSelectedIndex();
			EnumProperty set = null;
			if (index == 0) {
				setFOProperty(Constants.PR_SRC_TYPE, null);
				return;
			} else if (index == 1) {
				setFOProperty(Constants.PR_SRC_TYPE,
						ExternalGraphic.TYPE_FUNCTION);
				return;
			}
			
			else {

			}
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt) {
		if (hasPropertyKey(Constants.PR_SRC_TYPE)) {
			Object obj = evt.getNewValue();
			if (uiComponent instanceof WiseCombobox) {
				WiseCombobox ui = (WiseCombobox) uiComponent;
				if (obj != null&&obj.equals(ExternalGraphic.TYPE_FUNCTION)) {
						ui.initIndex(1);
				} else {
					ui.initIndex(0);
				}
			}

		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseCombobox) {
			WiseCombobox ui = (WiseCombobox) uiComponent;
			ui.initIndex(0);
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof WiseCombobox) {
			WiseCombobox ui = (WiseCombobox) uiComponent;
			ui.InitValue(null);
		}
	}
}
