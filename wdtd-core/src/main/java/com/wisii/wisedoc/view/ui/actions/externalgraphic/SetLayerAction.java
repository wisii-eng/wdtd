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
 * @SetLayerAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.externalgraphic;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：设置图片层 2009-02-03添加了针对条形码设置层属性的关联
 * 
 * 作者：zhangqiang 创建日期：2008-12-23
 */
public class SetLayerAction extends Actions {

	private WiseCombobox uiComponent;

	private void getComponents() {

		final Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				this.getActionID()).get(ActionCommandType.RIBBON_ACTION);

		for (final List<Object> list : comSet) {
			for (final Object uiComponents : list) {
				if (uiComponents instanceof WiseCombobox) {
					final WiseCombobox ui = (WiseCombobox) uiComponents;
					uiComponent = ui;
				}
			}
		}
	}

	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			final JComboBox value = (JComboBox) e.getSource();
			final int index = value.getSelectedIndex();
			if (index > -1) {
				setFOProperty(Constants.PR_GRAPHIC_LAYER, index);
			}
		}
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_GRAPHIC_LAYER))
		{
			getComponents();
			final Object obj = evt.getNewValue();
			if (uiComponent instanceof WiseCombobox)
			{
				final WiseCombobox ui = uiComponent;
				if (obj instanceof Integer)
				{
					ui.initIndex((Integer) obj);

				} else
				{
					ui.initIndex(0);
				}
			}
		}
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		getComponents();
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseCombobox) {
			final WiseCombobox ui = uiComponent;
			ui.initIndex(0);
		}
	}

	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		getComponents();
		if (uiComponent instanceof WiseCombobox) {
			final WiseCombobox ui = uiComponent;
			ui.InitValue(null);
		}
	}

}
