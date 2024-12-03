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

package com.wisii.wisedoc.view.action;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wisii.wisedoc.view.ui.parts.dialogs.WiseTemplateManagementDialog;

public class ModelManegemnetAction extends BaseAction
{

	@Override
	public void doAction(ActionEvent e)
	{
		WiseTemplateManagementDialog dialog = new WiseTemplateManagementDialog();
	}

	public static void main(String[] args)
	{
		String filePath = System.getProperty("user.dir") + "/Templates";
		File gen = new File(filePath);
		File[] filelist = gen.listFiles();
		List<File> wenjianlist = new ArrayList<File>();
		if (filelist != null && filelist.length > 0)
		{
			for (int i = 0; i < filelist.length; i++)
			{
				File current = filelist[i];
				if (!current.isDirectory())
				{
					String path = current.getPath();
					if (!path.equals(""))
					{
						String[] names = path.split("\\.");
						int length = names.length;
						if (names[length - 1].equalsIgnoreCase("wsd"))
						{
							wenjianlist.add(current);
						}
					}
				}
			}
		}
		JFrame fr = new JFrame("wen jian tong ji");
		fr.setSize(800, 600);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setPreferredSize(new Dimension(780, 580));

		JPanel mainpanel = new JPanel();
		panel.setPreferredSize(new Dimension(750, 500));

		JPanel totalpanel = new JPanel();
		panel.setPreferredSize(new Dimension(750, 50));
		JLabel number = new JLabel(wenjianlist.size() + "");
		System.out.println("zong shu:" + wenjianlist.size());
		number.setPreferredSize(new Dimension(200, 40));
		totalpanel.add(number);

		panel.add(mainpanel);
		panel.add(totalpanel);
		fr.add(panel);
		fr.setVisible(true);
	}
}
