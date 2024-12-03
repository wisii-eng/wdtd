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

package com.wisii.wisedoc.swing.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTextField;

import com.wisii.wisedoc.view.dialog.ConfigurationInformationDialog;
import com.wisii.wisedoc.view.dialog.GetProfileValue;
import com.wisii.wisedoc.view.panel.SystemPanel;

@SuppressWarnings("serial")
public class JTextFieldKeyset extends JTextField implements KeyListener
{

	HashMap<Integer, String> map = new HashMap<Integer, String>();
	{
		map.put(0x1, "鼠标左键");
		map.put(0x2, "鼠标右键");
		map.put(0x3, "CANCEL ");
		map.put(0x4, "鼠标中键");
		map.put(0x8, "BACKSPACE ");
		map.put(0x9, "TAB");
		map.put(0xC, "CLEAR ");
		map.put(0xD, "ENTER ");
		map.put(0x10, "SHIFT ");
		map.put(0x11, "CTRL ");
		map.put(0x12, "MENU ");
		map.put(0x13, "PAUSE ");
		map.put(0x14, "CAPS LOCK ");
		map.put(0x1B, "ESC ");
		map.put(0x20, "SPACEBAR ");
		map.put(0x21, "PAGE UP ");
		map.put(0x22, "PAGE DOWN ");
		map.put(0x23, "END ");
		map.put(0x24, "HOME ");
		map.put(0x25, "←");
		map.put(0x26, "↑");
		map.put(0x27, "→");
		map.put(0x28, "↓");
		map.put(0x29, "SELECT ");
		map.put(0x2A, "PRINT SCREEN ");
		map.put(0x2B, "EXECUTE ");
		map.put(0x2C, "SNAPSHOT ");
		map.put(0x2D, "- ");
		map.put(0x2E, ". ");
		map.put(0x2F, "/ ");
		map.put(0x90, "NUM LOCK ");
		map.put(65, "A ");
		map.put(66, "B ");
		map.put(67, "C ");
		map.put(68, "D ");
		map.put(69, "E ");
		map.put(70, "F ");
		map.put(71, "G ");
		map.put(72, "H ");
		map.put(73, "I ");
		map.put(74, "J ");
		map.put(75, "K ");
		map.put(76, "L ");
		map.put(77, "M ");
		map.put(78, "N ");
		map.put(79, "O ");
		map.put(80, "P ");
		map.put(81, "Q ");
		map.put(82, "R ");
		map.put(83, "S ");
		map.put(84, "T ");
		map.put(85, "U ");
		map.put(86, "V ");
		map.put(87, "W ");
		map.put(88, "X ");
		map.put(89, "Y ");
		map.put(90, "Z ");
		map.put(48, "0 ");
		map.put(49, "1 ");
		map.put(50, "2 ");
		map.put(51, "3 ");
		map.put(52, "4 ");
		map.put(53, "5 ");
		map.put(54, "6 ");
		map.put(55, "7 ");
		map.put(56, "8 ");
		map.put(57, "9 ");
		map.put(0x60, "0 ");
		map.put(0x61, "1 ");
		map.put(0x62, "2 ");
		map.put(0x63, "3 ");
		map.put(0x64, "4 ");
		map.put(0x65, "5 ");
		map.put(0x66, "6 ");
		map.put(0x67, "7 ");
		map.put(0x68, "8 ");
		map.put(0x69, "9 ");
		map.put(0x6A, "MULTIPLICATION SIGN (*) ");
		map.put(0x6B, "PLUS SIGN (+) ");
		map.put(0x6C, "ENTER ");
		map.put(0x6D, "MINUS SIGN (–) ");
		map.put(0x6E, "DECIMAL POINT (.) ");
		map.put(0x6F, "DIVISION SIGN (/) ");
		map.put(0x70, "F1 ");
		map.put(0x71, "F2 ");
		map.put(0x72, "F3 ");
		map.put(0x73, "F4 ");
		map.put(0x74, "F5 ");
		map.put(0x75, "F6 ");
		map.put(0x76, "F7 ");
		map.put(0x77, "F8 ");
		map.put(0x78, "F9 ");
		map.put(0x79, "F10 ");
		map.put(0x7A, "F11 ");
		map.put(0x7B, "F12 ");
		map.put(0x7C, "F13 ");
		map.put(0x7D, "F14 ");
		map.put(0x7E, "F15 ");
		map.put(0x7F, "DELETE ");
		map.put(92, "\\");
		map.put(93, "]");
		map.put(91, "[");
		map.put(61, "=");
		map.put(192, "`");
	}

	String name;

	List<String> keysList = new ArrayList<String>();

	public JTextFieldKeyset()
	{
		super();
	}

	public JTextFieldKeyset(String key)
	{
		super();
		name = key;
		String text = GetProfileValue.getValue(key);
		this.setText(text);
		String[] keys = text.split("\\+");
		for (String item : keys)
		{
			keysList.add(item);
		}
		SystemPanel.addKeys(name, keysList);
		addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int value = e.getKeyCode();
		if (value == 0x8)
		{
			setText("");
			SystemPanel.removeKeys(name, keysList);
			keysList.clear();
		} else
		{
			if (keysList.size() < 2)
			{
				String showValue = map.get(value);
				if (!keysList.contains(showValue))
				{
					keysList.add(showValue);
				}
			}
			setText(getRealText());
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		SystemPanel.addKeys(name, keysList);
		ConfigurationInformationDialog.addProFileItem(name, this.getText());
		e.consume();

	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		e.consume();
	}

	public String getRealText()
	{
		int size = keysList.size();
		String text = "";
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
			{
				text = text + keysList.get(i);
				text = text + " ";
				if (i < size - 1)
				{
					text = text + "+";
					text = text + " ";
				}
			}
		}
		return text;
	}

}
