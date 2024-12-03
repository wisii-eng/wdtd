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
 * @KeyManager.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ActionMap;
import javax.swing.ComponentInputMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionID;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;

/**
 * 类功能描述：快捷键管理类
 * 
 * 作者：zhangqiang 创建日期：2008-12-15
 */
public class KeyManager
{
	static void initKey(AbstractEditComponent editor)
	{
		if (editor != null)
		{
			initActionMap(editor);
			initInputMap(editor);
		}
	}

	private static void initActionMap(AbstractEditComponent editor)
	{
		Map<Enum<? extends ActionID>, BaseAction> map = ActionFactory
				.getActiveActions();
		Iterator<Enum<? extends ActionID>> keyit = map.keySet().iterator();
		ActionMap actionmap = new ActionMap();
		while (keyit.hasNext())
		{
			Enum<? extends ActionID> id = keyit.next();
			actionmap.put(id, map.get(id));
		}
		actionmap.put(Defalult.PASTE_TEXT_ACTION, ActionFactory.getAction(Defalult.PASTE_TEXT_ACTION));
		actionmap.put(Defalult.PASTE_WITHOUTBINDNODE_ACTION, ActionFactory.getAction(Defalult.PASTE_WITHOUTBINDNODE_ACTION));
		actionmap.put(Defalult.INSERT_BLOCKBEFORE_ACTION, ActionFactory.getAction(Defalult.INSERT_BLOCKBEFORE_ACTION));
		actionmap.put(Defalult.INSERT_BLOCKAFTER_ACTION, ActionFactory.getAction(Defalult.INSERT_BLOCKAFTER_ACTION));
		editor.setActionMap(actionmap);
	}

	private static void initInputMap(AbstractEditComponent editor)
	{
		InputMap inputmap = new ComponentInputMap(editor);
		inputmap.put(KeyStroke.getKeyStroke("control C"), Defalult.COPY_ACTION);
		inputmap.put(KeyStroke.getKeyStroke("control shift V"),
				Defalult.PASTE_TEXT_ACTION);
		inputmap.put(KeyStroke.getKeyStroke("control Z"), Defalult.UNDO_ACTION);
		inputmap.put(KeyStroke.getKeyStroke("control Y"), Defalult.REDO_ACTION);
		inputmap
				.put(KeyStroke.getKeyStroke("control alt V"), Defalult.PASTE_WITHOUTBINDNODE_ACTION);
		inputmap
		.put(KeyStroke.getKeyStroke("control V"), Defalult.PASTE_ACTION);
		inputmap.put(KeyStroke.getKeyStroke("control X"), Defalult.CUT_ACTION);
		inputmap.put(KeyStroke.getKeyStroke("control S"),
				Defalult.SAVE_FILE_ACTION);
		inputmap.put(KeyStroke.getKeyStroke("control O"),
				Defalult.OPEN_FILE_ACTION);
		inputmap.put(KeyStroke.getKeyStroke("control N"),
				Defalult.NEW_DOCUMENT_ACTION);
		inputmap.put(KeyStroke.getKeyStroke("control B"),
				Defalult.BROWSE_ACTION);
		inputmap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK),
				Defalult.INSERT_BLOCKBEFORE_ACTION);
		inputmap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK|InputEvent.SHIFT_MASK),
				Defalult.INSERT_BLOCKAFTER_ACTION);
		editor.setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputmap);
	}

	static void setKey(AbstractEditComponent editor, Map<ActionID, KeyStroke> keyset)
	{

	}
}
