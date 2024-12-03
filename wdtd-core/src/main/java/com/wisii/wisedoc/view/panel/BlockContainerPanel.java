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

package com.wisii.wisedoc.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import com.borland.jbcl.layout.XYConstraints;
import com.wisii.wisedoc.view.component.WiseTextField;

@SuppressWarnings("serial")
public class BlockContainerPanel extends OnlyLayoutPanel
{

	WiseTextField left = new WiseTextField("0.0");

	WiseTextField top = new WiseTextField("0.0");

	JButton other = new JButton("其它属性");
	{
		other.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				other.doClick();
			}
		});
	}

	public BlockContainerPanel()
	{
		super();
		this
				.add(new JLabel("距所在区域左边界的距离"), new XYConstraints(80, 100, 180,
						20));
		this.add(left, new XYConstraints(260, 100, 80, 20));

		this
				.add(new JLabel("距所在区域上边界的距离"), new XYConstraints(80, 140, 180,
						20));
		this.add(top, new XYConstraints(260, 140, 80, 20));

		this.add(other, new XYConstraints(160, 240, 100, 20));
	}
}
