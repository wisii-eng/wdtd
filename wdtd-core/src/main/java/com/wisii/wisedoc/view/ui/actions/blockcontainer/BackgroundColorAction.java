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
package com.wisii.wisedoc.view.ui.actions.blockcontainer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;

/**
 * 设置背景颜色动作
 * @author 闫舒寰
 * @version 1.0 2009/03/05
 */
public class BackgroundColorAction extends Actions {
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof RibbonColorList) {
			RibbonColorList colorCom = (RibbonColorList) e.getSource();
				Color selectedColor = (Color) colorCom.getSelectedItem();				
					Map<Integer, Object> att = new HashMap<Integer, Object>();
					att.put(Constants.PR_BACKGROUND_COLOR, selectedColor);
					if(selectedColor != null){
					att.put(Constants.PR_BACKGROUND_IMAGE, null);
					}
					setFOProperties(att);
		}
	}

}
