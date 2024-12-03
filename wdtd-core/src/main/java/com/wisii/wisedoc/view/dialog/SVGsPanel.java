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

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.ui.parts.svg.SvgBorderAndColorPanel;
import com.wisii.wisedoc.view.ui.parts.svg.SvgPositionPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

public class SVGsPanel extends JPanel implements PropertyPanelInterface
{

	CardLayout layout = new CardLayout();

	JTabbedPane tabs;

	private static Map<Integer, Object> attrmap = new HashMap<Integer, Object>();

	Map<Integer, Object> old = new HashMap<Integer, Object>();

	@Override
	public Map<Integer, Object> getSetting()
	{
		return ParagraphPanel.replaceKey(old, attrmap);
	}

	@Override
	public void install(Map<Integer, Object> map)
	{
		attrmap = map;
		old = map;
		setPreferredSize(new Dimension(450, 400));
		WisedocUtil.centerOnScreen(this);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		tabs = new JTabbedPane();
		tabs.addTab(UiText.SVG_BORDER_AND_COLOR, new SvgBorderAndColorPanel());
		tabs.addTab(UiText.SVG_POSITION, new SvgPositionPanel());
		this.add(tabs);
	}

	public SVGsPanel()
	{
	}

	public static Map<Integer, Object> getAttrmap()
	{
		return attrmap;
	}

	public static void setAttrmap(Map<Integer, Object> map)
	{
		attrmap = map;
	}

	public static void setItem(Integer key, Object value)
	{
		Map<Integer, Object> map = getAttrmap();
		map.remove(key);
		map.put(key, value);
	}

	public static Object getItem(Integer key)
	{
		Map<Integer, Object> map = getAttrmap();
		if (map.containsKey(key))
		{
			return map.get(key);
		}
		return null;
	}

	@Override
	public boolean isValidate()
	{
		// TODO Auto-generated method stub
		return false;
	}
}