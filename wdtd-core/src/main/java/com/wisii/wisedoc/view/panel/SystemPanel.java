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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.swing.ui.JTextFieldKeyset;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.dialog.ConfigurationInformationDialog;
import com.wisii.wisedoc.view.dialog.GetProfileValue;

@SuppressWarnings("serial")
public class SystemPanel extends OnlyLayoutPanel
{

	private static HashMap<String, List<String>> keys = new HashMap<String, List<String>>();

	WiseTextField unit = new WiseTextField("unit");

	WiseTextField workSpace = new WiseTextField("workSpace");

	String chinese = "中文";

	String english = "英文";

	WiseCombobox language = new WiseCombobox();
	{
		language.addItem(chinese);
		language.addItem(english);
		language.setSelectedItem(chinese);
		String value = GetProfileValue.getValue("language");
		if (value != null)
		{
			language.setSelectedItem(value);
		} else
		{
			language.setSelectedItem(chinese);
		}
		language.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					language.setSelectedItem(current);
					ConfigurationInformationDialog.addProFileItem("language",
							current);
				}
			}
		});
	}

	WiseTextField numberRecentOpen = new WiseTextField(
			"numberRecentOpen");

	JTextFieldKeyset save = new JTextFieldKeyset("save");

	JTextFieldKeyset copy = new JTextFieldKeyset("copy");

	JTextFieldKeyset paste = new JTextFieldKeyset("paste");

	JTextFieldKeyset shear = new JTextFieldKeyset("shear");

	JTextFieldKeyset delete = new JTextFieldKeyset("delete");

	JTextFieldKeyset find = new JTextFieldKeyset("find");

	JTextFieldKeyset replacement = new JTextFieldKeyset("replacement");

	JTextFieldKeyset allSelect = new JTextFieldKeyset("allSelect");

	JTextFieldKeyset close = new JTextFieldKeyset("close");

	JTextFieldKeyset newCreat = new JTextFieldKeyset("newCreat");

	JTextFieldKeyset open = new JTextFieldKeyset("open");

	JTextFieldKeyset leftMove = new JTextFieldKeyset("leftMove");

	JTextFieldKeyset rightMove = new JTextFieldKeyset("rightMove");

	JTextFieldKeyset aboveMove = new JTextFieldKeyset("aboveMove");

	JTextFieldKeyset blowMove = new JTextFieldKeyset("blowMove");

	JTextFieldKeyset selectNumberofP = new JTextFieldKeyset("selectNumberofP");

	JTextFieldKeyset selectNumberofS = new JTextFieldKeyset("selectNumberofS");

	JTextFieldKeyset preview = new JTextFieldKeyset("preview");

	JTextFieldKeyset print = new JTextFieldKeyset("print");

	JTextFieldKeyset outXSL = new JTextFieldKeyset("outXSL");

	JTextFieldKeyset outPSXSL = new JTextFieldKeyset("outPSXSL");

	JTextFieldKeyset inputData = new JTextFieldKeyset("inputData");

	JTextFieldKeyset clear = new JTextFieldKeyset("clear");

	JTextFieldKeyset change = new JTextFieldKeyset("change");

	public SystemPanel()
	{
		super();
		this.add(new JLabel("默认单位"), new XYConstraints(80, 20, 60, 20));
		this.add(unit, new XYConstraints(165, 20, 80, 20));

		this.add(new JLabel("工作空间"), new XYConstraints(260, 20, 60, 20));
		this.add(workSpace, new XYConstraints(345, 20, 80, 20));

		this.add(new JLabel("语言种类"), new XYConstraints(80, 50, 60, 20));
		this.add(language, new XYConstraints(165, 50, 80, 20));

		this.add(new JLabel("最近使用文档的最大允许个数"), new XYConstraints(260, 50, 170,
				20));
		this.add(numberRecentOpen, new XYConstraints(435, 50, 80, 20));

		JPanel keySetPanel = new JPanel();
		keySetPanel.setSize(490, 260);
		XYLayout keySetLayout = new XYLayout();
		keySetLayout.setWidth(490);
		keySetLayout.setHeight(260);
		keySetPanel.setLayout(keySetLayout);
		keySetPanel.setBackground(getBackground());

		keySetPanel.add(new JLabel("保存"), new XYConstraints(5, 0, 95, 20));
		keySetPanel.add(save, new XYConstraints(100, 0, 130, 20));
		// keySetPanel.add(new JLabel("S可选"), new XYConstraints(165, 0, 80,
		// 20));

		keySetPanel.add(new JLabel("复制"), new XYConstraints(255, 0, 95, 20));
		keySetPanel.add(copy, new XYConstraints(350, 0, 130, 20));
		// keySetPanel.add(new JLabel("C可选"), new XYConstraints(415, 0, 40,
		// 20));

		keySetPanel.add(new JLabel("粘贴"), new XYConstraints(5, 30, 95, 20));
		keySetPanel.add(paste, new XYConstraints(100, 30, 130, 20));
		// keySetPanel.add(new JLabel("V可选"), new XYConstraints(165, 30, 80,
		// 20));

		keySetPanel.add(new JLabel("剪切"), new XYConstraints(255, 30, 95, 20));
		keySetPanel.add(shear, new XYConstraints(350, 30, 130, 20));
		// keySetPanel.add(new JLabel("X可选"), new XYConstraints(415, 30, 40,
		// 20));

		keySetPanel.add(new JLabel("删除"), new XYConstraints(5, 60, 95, 20));
		keySetPanel.add(delete, new XYConstraints(100, 60, 130, 20));
		// keySetPanel.add(new JLabel("D可选"), new XYConstraints(165, 60, 80,
		// 20));

		keySetPanel.add(new JLabel("查找"), new XYConstraints(255, 60, 95, 20));
		keySetPanel.add(find, new XYConstraints(350, 60, 130, 20));
		// keySetPanel.add(new JLabel("F可选"), new XYConstraints(415, 60, 40,
		// 20));

		keySetPanel.add(new JLabel("替换"), new XYConstraints(5, 90, 95, 20));
		keySetPanel.add(replacement, new XYConstraints(100, 90, 130, 20));
		// keySetPanel.add(new JLabel("E可选"), new XYConstraints(165, 90, 80,
		// 20));

		keySetPanel.add(new JLabel("全选"), new XYConstraints(255, 90, 95, 20));
		keySetPanel.add(allSelect, new XYConstraints(350, 90, 130, 20));
		// keySetPanel.add(new JLabel("A可选"), new XYConstraints(415, 90, 40,
		// 20));

		keySetPanel.add(new JLabel("关闭"), new XYConstraints(5, 120, 95, 20));
		keySetPanel.add(close, new XYConstraints(100, 120, 130, 20));
		// keySetPanel.add(new JLabel("W可选"), new XYConstraints(165, 120, 80,
		// 20));

		keySetPanel.add(new JLabel("新建"), new XYConstraints(255, 120, 95, 20));
		keySetPanel.add(newCreat, new XYConstraints(350, 120, 130, 20));
		// keySetPanel.add(new JLabel("N可选"), new XYConstraints(415, 120, 40,
		// 20));

		keySetPanel.add(new JLabel("打开"), new XYConstraints(5, 150, 95, 20));
		keySetPanel.add(open, new XYConstraints(100, 150, 130, 20));
		// keySetPanel.add(new JLabel("O可选"), new XYConstraints(165, 150, 80,
		// 20));

		keySetPanel.add(new JLabel("预览"), new XYConstraints(255, 150, 95, 20));
		keySetPanel.add(preview, new XYConstraints(350, 150, 130, 20));
		// keySetPanel.add(new JLabel("P可选"), new XYConstraints(415, 150, 40,
		// 20));

		keySetPanel.add(new JLabel("左移"), new XYConstraints(5, 180, 95, 20));
		keySetPanel.add(leftMove, new XYConstraints(100, 180, 130, 20));
		// keySetPanel.add(new JLabel("可选"), new XYConstraints(165, 180, 80,
		// 20));

		keySetPanel.add(new JLabel("右移"), new XYConstraints(255, 180, 95, 20));
		keySetPanel.add(rightMove, new XYConstraints(350, 180, 130, 20));
		// keySetPanel.add(new JLabel("可选"), new XYConstraints(415, 180, 40,
		// 20));

		keySetPanel.add(new JLabel("上移"), new XYConstraints(5, 210, 95, 20));
		keySetPanel.add(aboveMove, new XYConstraints(100, 210, 130, 20));
		// keySetPanel.add(new JLabel("可选"), new XYConstraints(165, 210, 80,
		// 20));

		keySetPanel.add(new JLabel("下移"), new XYConstraints(255, 210, 95, 20));
		keySetPanel.add(blowMove, new XYConstraints(350, 210, 130, 20));
		// keySetPanel.add(new JLabel("可选"), new XYConstraints(415, 210, 40,
		// 20));

		keySetPanel
				.add(new JLabel("多选(点选)"), new XYConstraints(5, 240, 95, 20));
		keySetPanel.add(selectNumberofP, new XYConstraints(100, 240, 130, 20));
		// keySetPanel.add(new JLabel("Ctrl可选"), new XYConstraints(165, 240, 80,
		// 20));

		keySetPanel.add(new JLabel("多选(区间全选)"), new XYConstraints(255, 240, 95,
				20));
		keySetPanel.add(selectNumberofS, new XYConstraints(350, 240, 130, 20));
		// keySetPanel.add(new JLabel("Shift可选"), new XYConstraints(415, 240,
		// 60,
		// 20));

		keySetPanel.add(new JLabel("打印"), new XYConstraints(5, 270, 95, 20));
		keySetPanel.add(print, new XYConstraints(100, 270, 130, 20));
		// keySetPanel.add(new JLabel("P可选"), new XYConstraints(165, 270, 80,
		// 20));

		keySetPanel.add(new JLabel("导出xsl模板"), new XYConstraints(255, 270, 95,
				20));
		keySetPanel.add(outXSL, new XYConstraints(350, 270, 130, 20));
		// keySetPanel.add(new JLabel("W可选"), new XYConstraints(415, 270, 40,
		// 20));

		keySetPanel
				.add(new JLabel("导出章节模板"), new XYConstraints(5, 300, 95, 20));
		keySetPanel.add(outPSXSL, new XYConstraints(100, 300, 130, 20));
		// keySetPanel.add(new JLabel("P可选"), new XYConstraints(165, 300, 80,
		// 20));

		keySetPanel.add(new JLabel("导入数据结构"), new XYConstraints(255, 300, 95,
				20));
		keySetPanel.add(inputData, new XYConstraints(350, 300, 130, 20));
		// keySetPanel.add(new JLabel("X可选"), new XYConstraints(415, 300, 40,
		// 20));

		keySetPanel
				.add(new JLabel("清除数据结构"), new XYConstraints(5, 330, 95, 20));
		keySetPanel.add(clear, new XYConstraints(100, 330, 130, 20));
		// keySetPanel.add(new JLabel("C可选"), new XYConstraints(165, 330, 80,
		// 20));

		keySetPanel.add(new JLabel("更改配置信息"), new XYConstraints(255, 330, 95,
				20));
		keySetPanel.add(change, new XYConstraints(350, 330, 130, 20));
		// keySetPanel.add(new JLabel("O可选"), new XYConstraints(415, 330, 40,
		// 20));

		keySetPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"快捷键设置(只有可选键可以改变)", TitledBorder.CENTER, TitledBorder.TOP));
		this.add(keySetPanel, new XYConstraints(50, 100, 500, 380));

	}

	public void paint(Graphics g)
	{
		super.paint(g);
		Color old = g.getColor();
		g.setColor(Color.BLUE);
		g.drawLine(285, 120, 285, 470);
		g.setColor(old);
	}

	public static boolean checkKeys(List<String> current)
	{
		boolean flg = true;
		HashMap<String, List<String>> lists = getKeys();
		Object[] keys = lists.keySet().toArray();
		for (int j = 0; j < keys.length; j++)
		{
			String key = keys[j].toString();
			List<String> item = lists.get(key);
			int size = item.size();
			if (size == current.size())
			{
				int number = 0;
				for (int i = 0; i < size; i++)
				{
					if (item.get(i).equalsIgnoreCase(current.get(i)))
					{
						number++;
					}
				}
				if (size == number && size > 0)
				{
					flg = false;
					break;
				}
			}
		}
		return flg;
	}

	public static boolean addKeys(String key, List<String> current)
	{
		boolean flg = checkKeys(current);
		HashMap<String, List<String>> lists = getKeys();
		if (flg)
		{
			if (lists.containsKey(key))
			{
				lists.remove(key);
				lists.put(key, current);
			} else
			{
				lists.put(key, current);
			}
		}
		return flg;
	}

	public static void removeKeys(List<String> current)
	{
		HashMap<String, List<String>> lists = getKeys();
		Object[] keys = lists.keySet().toArray();
		for (int j = 0; j < keys.length; j++)
		{
			String key = keys[j].toString();
			List<String> item = lists.get(key);
			int size = item.size();
			if (size == current.size())
			{
				int number = 0;
				for (int i = 0; i < size; i++)
				{
					if (item.get(i).equalsIgnoreCase(current.get(i)))
					{
						number++;
					}
				}
				if (size == number && size > 0)
				{
					lists.remove(key);
					break;
				}
			}
		}
	}

	public static void removeKeys(String key, List<String> current)
	{
		HashMap<String, List<String>> lists = getKeys();
		if (lists.containsKey(key))
		{
			lists.remove(key);
		}
	}

	public static HashMap<String, List<String>> getKeys()
	{
		return keys;
	}

	public static void resetKeys(HashMap<String, List<String>> keys)
	{
		SystemPanel.keys = new HashMap<String, List<String>>();
	}

}
