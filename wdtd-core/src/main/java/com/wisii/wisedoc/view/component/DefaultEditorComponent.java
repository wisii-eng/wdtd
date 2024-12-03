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
 * @DefaultEditorComponent.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

/**
 * 类功能描述：缺省属性编辑器 即如果仅仅是一个普通的编辑过程，这是仅仅需要提供一个普通的文本框即可
 * 
 * 作者：李晓光 创建日期：2007-8-2
 */
@SuppressWarnings("serial")
public class DefaultEditorComponent extends EditorBaseComponent
{
	public DefaultEditorComponent()
	{
		setLayout(new BorderLayout());
		add(txtComp, BorderLayout.CENTER);
		txtComp.setBorder(null);

		/* 为文本框添加动作监听，以决定何设置有效值 */
		txtComp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setValue(txtComp.getText());
			}
		});
		txtComp.addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent e)
			{
				setValue(txtComp.getText());
			}
		});
	}

	/**
	 * 设置编辑后的值，给改编辑器value属性和文本框
	 * 
	 * @param 指定编辑的值
	 * @return 无
	 */
	public void setValue(Object value)
	{
		super.setValue(value);
		txtComp.setText(value == null ? "" : value.toString().trim());
	}

	/* 定义文本框组件 */
	protected JTextField txtComp = new JTextField();
}
