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
 * @PopupMenuHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.parts.dialogs.ParagraphAllPropertiesDialog;
import com.wisii.wisedoc.view.ui.parts.dialogs.TextAllPropertiesDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 类功能描述：文档编辑界面的右键菜单管理类， 根据当前文档编各种辑器状态生成对应的右键菜单
 * 
 * 作者：zhangqiang 创建日期：2008-10-23
 */
public class PopupMenuHandler
{
	private static JPopupMenu defaultpop = createDefaultPopmenu();
	private static JPopupMenu norrmalpop = createNormalPopupMenu();

	public static JPopupMenu getCurrentPopupMenu()
	{
		AbstractEditComponent editor = RibbonUpdateManager.Instance
				.getCurrentEditPanel();
		if (editor != null)
		{
			if (editor.getCaretPosition() != null)
			{
				initNormalPopupMenu();
				return norrmalpop;
			}

		}
		initDefaultPopmenu();
		return defaultpop;
	}

	private static void initNormalPopupMenu()
	{
		JMenuItem cutitem = (JMenuItem) defaultpop.getComponent(0);
		cutitem.setEnabled(ActionFactory.getAction(Defalult.CUT_ACTION)
				.isAvailable());
		JMenuItem copyitem = (JMenuItem) defaultpop.getComponent(1);
		copyitem.setEnabled(ActionFactory.getAction(Defalult.COPY_ACTION)
				.isAvailable());
		JMenuItem pasteitem = (JMenuItem) defaultpop.getComponent(2);
		pasteitem.setEnabled(ActionFactory.getAction(Defalult.PASTE_ACTION)
				.isAvailable());
		JMenuItem pastewithoutbindnodeitem = (JMenuItem) defaultpop
				.getComponent(3);
		pastewithoutbindnodeitem.setEnabled(ActionFactory.getAction(
				Defalult.PASTE_WITHOUTBINDNODE_ACTION).isAvailable());
		JMenuItem pastetexttitem = (JMenuItem) defaultpop.getComponent(4);
		pastetexttitem.setEnabled(ActionFactory.getAction(
				Defalult.PASTE_TEXT_ACTION).isAvailable());
	}

	private static void initDefaultPopmenu()
	{
		JMenuItem cutitem = (JMenuItem) defaultpop.getComponent(0);
		cutitem.setEnabled(ActionFactory.getAction(Defalult.CUT_ACTION)
				.isAvailable());
		JMenuItem copyitem = (JMenuItem) defaultpop.getComponent(1);
		copyitem.setEnabled(ActionFactory.getAction(Defalult.COPY_ACTION)
				.isAvailable());
		JMenuItem pasteitem = (JMenuItem) defaultpop.getComponent(2);
		pasteitem.setEnabled(ActionFactory.getAction(Defalult.PASTE_ACTION)
				.isAvailable());
		JMenuItem pastewithoutbindnodeitem = (JMenuItem) defaultpop
				.getComponent(3);
		pastewithoutbindnodeitem.setEnabled(ActionFactory.getAction(
				Defalult.PASTE_WITHOUTBINDNODE_ACTION).isAvailable());
		JMenuItem pastetexttitem = (JMenuItem) defaultpop.getComponent(4);
		pastetexttitem.setEnabled(ActionFactory.getAction(
				Defalult.PASTE_TEXT_ACTION).isAvailable());
	}

	private static JPopupMenu createNormalPopupMenu()
	{
		JPopupMenu pop = new JPopupMenu();
		JMenuItem cutitem = new JMenuItem(RibbonUIText.CUT_BUTTON,
				MediaResource.getResizableIcon("Cut.gif"));
		BaseAction cutaction = ActionFactory.getAction(Defalult.CUT_ACTION);
		cutitem.addActionListener(cutaction);
		pop.add(cutitem);
		JMenuItem copyitem = new JMenuItem(RibbonUIText.COPY_BUTTON,
				MediaResource.getResizableIcon("Copy.gif"));
		BaseAction copyaction = ActionFactory.getAction(Defalult.COPY_ACTION);
		copyitem.addActionListener(copyaction);
		pop.add(copyitem);
		JMenuItem pasteitem = new JMenuItem(RibbonUIText.PASTE_BUTTON,
				MediaResource.getResizableIcon("Paste.gif"));
		BaseAction pasteaction = ActionFactory.getAction(Defalult.PASTE_ACTION);
		pasteitem.addActionListener(pasteaction);
		pop.add(pasteitem);
		JMenuItem pastewithoutbindnodeitem = new JMenuItem(
				RibbonUIText.PASTE_WITHOUT_BINDNODE_BUTTON, MediaResource
						.getResizableIcon("Paste.gif"));
		BaseAction pastewithoutbindnodeaction = ActionFactory
				.getAction(Defalult.PASTE_WITHOUTBINDNODE_ACTION);
		pastewithoutbindnodeitem.addActionListener(pastewithoutbindnodeaction);
		pop.add(pastewithoutbindnodeitem);
		JMenuItem copytextitem = new JMenuItem(RibbonUIText.PASTE_TEXT_BUTTON,
				MediaResource.getResizableIcon("Paste.gif"));
		BaseAction copytextaction = ActionFactory
				.getAction(Defalult.PASTE_TEXT_ACTION);
		copytextitem.addActionListener(copytextaction);
		pop.add(copytextitem);
		pop.addSeparator();
		JMenuItem fontitem = new JMenuItem(RibbonUIText.FONT_BAND,
				MediaResource.getResizableIcon("Font.gif"));
		fontitem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new TextAllPropertiesDialog(ActionType.INLINE);
			}
		});
		pop.add(fontitem);
		JMenuItem praphitem = new JMenuItem(RibbonUIText.PARAGRAPH_BAND,
				MediaResource.getResizableIcon("Paragrap.gif"));
		praphitem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new ParagraphAllPropertiesDialog(ActionType.BLOCK);
			}
		});
		pop.add(praphitem);
		return pop;
	}

	private static JPopupMenu createDefaultPopmenu()
	{
		JPopupMenu pop = new JPopupMenu();
		JMenuItem cutitem = new JMenuItem(RibbonUIText.CUT_BUTTON,
				MediaResource.getResizableIcon("Cut.gif"));
		BaseAction cutaction = ActionFactory.getAction(Defalult.CUT_ACTION);
		cutitem.addActionListener(cutaction);
		pop.add(cutitem);
		JMenuItem copyitem = new JMenuItem(RibbonUIText.COPY_BUTTON,
				MediaResource.getResizableIcon("Copy.gif"));
		BaseAction copyaction = ActionFactory.getAction(Defalult.COPY_ACTION);
		copyitem.addActionListener(copyaction);
		pop.add(copyitem);
		JMenuItem pasteitem = new JMenuItem(RibbonUIText.PASTE_BUTTON,
				MediaResource.getResizableIcon("Paste.gif"));
		BaseAction pasteaction = ActionFactory.getAction(Defalult.PASTE_ACTION);
		pasteitem.addActionListener(pasteaction);
		pop.add(pasteitem);

		JMenuItem pastewithoutbindnodeitem = new JMenuItem(
				RibbonUIText.PASTE_WITHOUT_BINDNODE_BUTTON, MediaResource
						.getResizableIcon("Paste.gif"));
		BaseAction pastewithoutbindnodeaction = ActionFactory
				.getAction(Defalult.PASTE_WITHOUTBINDNODE_ACTION);
		pastewithoutbindnodeitem.addActionListener(pastewithoutbindnodeaction);
		pop.add(pastewithoutbindnodeitem);

		JMenuItem copytextitem = new JMenuItem(RibbonUIText.PASTE_TEXT_BUTTON,
				MediaResource.getResizableIcon("Paste.gif"));
		BaseAction pastetextaction = ActionFactory
				.getAction(Defalult.PASTE_TEXT_ACTION);
		copytextitem.addActionListener(pastetextaction);
		pop.add(copytextitem);

		return pop;
	}
}
