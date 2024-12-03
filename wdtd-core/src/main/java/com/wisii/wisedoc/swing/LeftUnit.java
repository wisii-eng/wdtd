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

package com.wisii.wisedoc.swing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wisii.wisedoc.configure.ConfigureEvent;
import com.wisii.wisedoc.configure.ConfigureListener;
import com.wisii.wisedoc.configure.ConfigureUtil;

@SuppressWarnings("serial")
public class LeftUnit extends JPanel implements ConfigureListener
{

	public LeftUnit()
	{
		super();

	}

	@Override
	public void paintComponent(Graphics g)
	{
		setToolTipText(ConfigureUtil.getUnit());
		JLabel unit = new JLabel(ConfigureUtil.getUnit().toUpperCase());
		unit.setSize(40, 40);
		unit.setBackground(Color.GRAY);
		this.setBackground(Color.GRAY);
		this.add(unit);
	}

	@Override
	public void configureChanged(ConfigureEvent event)
	{
		repaint();
	}
}
