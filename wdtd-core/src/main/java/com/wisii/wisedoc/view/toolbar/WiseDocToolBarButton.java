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
 * @WiseDocToolBarButton.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.toolbar;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

/**
 * 类功能描述：定义自己风格的JButton
 * 
 * 作者：李晓光 创建日期：2008-08-22
 */
public class WiseDocToolBarButton extends JButton
{
	public WiseDocToolBarButton()
	{

		super();

		initStyle();
	}

	private void initStyle()
	{
		setMargin(new Insets(0, 0, 0, 0));
		Insets borderInsets = getBorder().getBorderInsets(this);
		setPreferredSize(new Dimension(borderInsets.left + borderInsets.right
				+ 24, borderInsets.top + borderInsets.bottom + 24));
		setFocusPainted(false);
		setRolloverEnabled(true);
		setBorderPainted(false);
		setPreferredSize(new Dimension(24, 24));
		addMouseListener(new MouseAdapter()
		{

			public void mouseEntered(MouseEvent e)
			{
				setBorderPainted(true);
			}

			public void mouseExited(MouseEvent e)
			{
				setBorderPainted(false);
			}

		});
	}
}
