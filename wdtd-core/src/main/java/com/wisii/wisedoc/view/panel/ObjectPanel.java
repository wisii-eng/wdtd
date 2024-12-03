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
import com.borland.jbcl.layout.XYConstraints;

@SuppressWarnings("serial")
public class ObjectPanel extends OnlyLayoutPanel
{

	JButton block = new JButton("段落");
	{
		block.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				block.doClick();
			}
		});

	}

	JButton table = new JButton("表");
	{
		table.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				table.doClick();
			}
		});

	}

	JButton picture = new JButton("图片");
	{
		picture.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				picture.doClick();
			}
		});

	}

	JButton barcode = new JButton("条码");
	{
		barcode.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				barcode.doClick();
			}
		});

	}

	JButton blockcontainer = new JButton("高级块容器");
	{
		blockcontainer.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				blockcontainer.doClick();
			}
		});

	}

	JButton datetime = new JButton("日期时间");
	{
		datetime.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				datetime.doClick();
			}
		});

	}

	JButton numbertext = new JButton("格式化数字");
	{
		numbertext.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				numbertext.doClick();
			}
		});

	}

	public ObjectPanel()
	{
		super();
		this.add(block, new XYConstraints(100, 20, 100, 20));
		this.add(table, new XYConstraints(100, 60, 100, 20));
		this.add(picture, new XYConstraints(100, 100, 100, 20));
		this.add(barcode, new XYConstraints(100, 140, 100, 20));
		this.add(blockcontainer, new XYConstraints(100, 180, 100, 20));
		this.add(datetime, new XYConstraints(100, 220, 100, 20));
		this.add(numbertext, new XYConstraints(100, 260, 100, 20));
	}
}
