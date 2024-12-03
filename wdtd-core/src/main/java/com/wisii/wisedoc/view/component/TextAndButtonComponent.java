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
 * @TextAndButtonComponent.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.component;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.FocusListener;

import javax.swing.JButton;

/**
 * 类功能描述：当编辑器的编辑模式为，文本框 + 按钮的形式时，要继承该类 目的方便与调整、统一样式
 * 
 * 作者：李晓光 创建日期：2007-7-4
 */
@SuppressWarnings("serial")
public abstract class TextAndButtonComponent extends DefaultEditorComponent
{
	/**
	 * 
	 * 创建文本框 + 按钮型属性编辑器
	 * 
	 * @param 无
	 */
	public TextAndButtonComponent()
	{
		add(btnComp, BorderLayout.EAST);
		btnComp.setMargin(new Insets(0, 0, 0, 0));
		FocusListener[] listeners = txtComp.getFocusListeners();
		if(listeners.length > 2)
			txtComp.removeFocusListener(listeners[listeners.length - 1]);
//		for (FocusListener listener : listeners)
//		{
//			txtComp.removeFocusListener(listener);
//		}
	}
	
	/* 定义按钮组件 */
	protected JButton btnComp = new JButton(EditorBaseComponent.BTN_TEXT);
}
