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
package com.wisii.wisedoc.view.ui.actions;

import javax.swing.ActionMap;
import javax.swing.ComponentInputMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.ribbon.RibbonPanel;

/**
 * 快捷键可按照如下方式来和动作绑定
 * @author 闫舒寰
 * @version 0.1 2009/01/13
 */
public class ShortCutKeyBind {
	
	public static void initShortCutKey(){
		ActionMap am = new ActionMap();
		am.put(Font.BOLD_ACTION, ActionFactory.getAction(Font.BOLD_ACTION));
		am.put(Defalult.UNDO_ACTION, ActionFactory.getAction(Defalult.UNDO_ACTION));
		
		InputMap im = new ComponentInputMap(RibbonPanel.getRibbon());
		im.put(KeyStroke.getKeyStroke("control B"), Font.BOLD_ACTION);
		im.put(KeyStroke.getKeyStroke("control Z"), Defalult.UNDO_ACTION);
		
		RibbonPanel.getRibbon().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, im);
		RibbonPanel.getRibbon().setActionMap(am);
		
//		PreviewDialogAPP.g
		
	}

}
