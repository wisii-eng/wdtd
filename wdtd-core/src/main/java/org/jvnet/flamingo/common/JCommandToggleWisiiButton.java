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

package org.jvnet.flamingo.common;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import org.jvnet.flamingo.common.icon.ResizableIcon;

public class JCommandToggleWisiiButton extends JCommandToggleButton
{

	Image image;

	public JCommandToggleWisiiButton(String title, ResizableIcon icon)
	{
		super(title, icon);
		// TODO Auto-generated constructor stub
	}

	public JCommandToggleWisiiButton(String title, ResizableIcon icon, Image ima)
	{
		super(title, icon);
		setHGapScaleFactor(6);
		setVGapScaleFactor(6);
		image = ima;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Dimension size = this.getSize();
		g.drawImage(this.image, 1, 1, size.width-2, size.height-2, null);
	}
}
