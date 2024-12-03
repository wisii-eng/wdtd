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

package com.wisii.wisedoc.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.parts.common.BackGroundPanel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 段落属性面板
 * @author 钟亚军
 *
 */
public class ParagraphPanel extends JPanel implements PropertyPanelInterface
{

	private static Map<Integer, Object> attr = new HashMap<Integer, Object>();

	Map<Integer, Object> old = new HashMap<Integer, Object>();

	private JTabbedPane tabs;

	ParagraphPositionPanel paragraphposition;

	ParagraphKeepBreakPanel paragraphkeepbreak;

	ParagraphSpSymPanel paragraphspsym;

	BackGroundPanel background;

	BorderPanel borderPanel;

	public ParagraphPanel()
	{
		setPreferredSize(new Dimension(566, 600));
	}

	@Override
	public Map<Integer, Object> getSetting()
	{
		Map<Integer, Object> borderattris = null;
		if (borderPanel != null)
		{
			borderattris = borderPanel.getPropertie();
		}
		Map<Integer, Object> bgattris = background.getProperties();
		if (borderattris != null || bgattris != null)
		{
			if (borderattris != null)
			{
				attr.putAll(borderattris);
			}
			if (bgattris != null)
			{
				attr.putAll(bgattris);
			}
		}
		Map<Integer, Object> result = replaceKey(old, attr);
		attr.clear();
//		System.out.println("result:"+result);
		return result;
	}

	@Override
	public void install(Map<Integer, Object> map)
	{
		if (map != null)
		{
			attr = map;
			old = new HashMap<Integer, Object>(map);
		} else
		{
			attr = new HashMap<Integer, Object>();
			old = new HashMap<Integer, Object>();
		}
		setLayout(new BorderLayout());
		tabs = new JTabbedPane();
		paragraphposition = new ParagraphPositionPanel();
		paragraphkeepbreak = new ParagraphKeepBreakPanel();
		paragraphspsym = new ParagraphSpSymPanel();
		tabs.addTab(UiText.PARAGRAPH_DIALOG_POSITION_TAB, paragraphposition);
		tabs.addTab(UiText.PARAGRAPH_DIALOG_KEEP_BREAK_TAB, paragraphkeepbreak);
		tabs.addTab(UiText.PARAGRAPH_DIALOG_SPY_SYM_TAB, paragraphspsym);
		background = new BackGroundPanel(attr, ActionType.BLOCK);
		borderPanel = new BorderPanel(attr, ActionType.BLOCK);
		tabs.addTab("底纹", background);
		tabs.addTab("边框", borderPanel);
		add(tabs, BorderLayout.CENTER);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.NORTH);
	}

	public static Map<Integer, Object> getAttr()
	{
		return attr;
	}

	public static Map<Integer, Object> replaceKey(Map<Integer, Object> map,
			Map<Integer, Object> replace)
	{
		if (map == null)
		{
			return new HashMap<Integer, Object>();
		} else
		{
			if (replace == null || replace.size() == 0)
			{
				return map;
			} else
			{
				Object[] keys = replace.keySet().toArray();
				int size = keys.length;
				for (int i = 0; i < size; i++)
				{
					int key = (Integer) keys[i];
					if (replace.get(key) == null)
					{
						if (map.get(key) == null)
						{
							map.remove(key);
						} else
						{
							map.put(key, replace.get(key));
						}

					} else
					{
						if (map.get(key) == null)
						{
							map.put(key, replace.get(key));

						} else
						{
							if (replace.get(key).equals(map.get(key)))
							{
								map.remove(key);
							} else
							{
								map.put(key, replace.get(key));
							}
						}
					}
				}
			}
		}
		return map;
	}

	@Override
	public boolean isValidate()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
