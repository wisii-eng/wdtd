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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.text.UiText;

public class ParagraphSpSymPanel extends JPanel
{

	// Map<Integer, Object> map = ParagraphPanel.getAttr();

	String all = UiText.PARAGRAPH_SPYSYM_ALL;// "全部保留";

	String one = UiText.PARAGRAPH_SPYSYM_ONE;// "只保留一个";

	String ignore = UiText.PARAGRAPH_SPYSSYM_IGNORE;// "忽略";

	String preserve = UiText.PARAGRAPH_SPYSSYM_PRESERVE;// "保留";

	String ignoreB = UiText.PARAGRAPH_SPYSSYM_IGNORE_BEFORE;// "换行符之前忽略";

	String ignoreA = UiText.PARAGRAPH_SPYSSYM_IGNORE_AFTER;// "换行符之后忽略";

	String ignoreR = UiText.PARAGRAPH_SPYSSYM_IGNORE_BOTH;// "换行符前后忽略";

	String asSpace = UiText.PARAGRAPH_SPYSSYM_AS_SPACE;// "作为空格处理";

	public ParagraphSpSymPanel()
	{
		super();
		JPanel spSymPanel = new JPanel();
		spSymPanel.setPreferredSize(new Dimension(467, 120));
		GridLayout layout = new GridLayout(3, 1, 1, 1);
		spSymPanel.setLayout(layout);

		JPanel nfs = new JPanel();
		nfs.setLayout(new FlowLayout(FlowLayout.LEFT));

		WiseCombobox numberofspaces = new WiseCombobox();
		numberofspaces.addItem(all);
		numberofspaces.addItem(one);
		numberofspaces
				.setSelectedItem(getAttribute(Constants.PR_WHITE_SPACE_COLLAPSE));

		numberofspaces.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					setAttribute(Constants.PR_WHITE_SPACE_COLLAPSE,
							getEnumProperty(current));
				}
			}
		});

		nfs.add(new JLabel(UiText.PARAGRAPH_SPYSYM_CONTINUAL_SPACE));
		nfs.add(numberofspaces);

		JPanel st = new JPanel();
		st.setLayout(new FlowLayout(FlowLayout.LEFT));

		WiseCombobox spacesTreat = new WiseCombobox();
		spacesTreat.addItem(preserve);
		spacesTreat.addItem(ignore);
		spacesTreat.addItem(ignoreB);
		spacesTreat.addItem(ignoreA);
		spacesTreat.addItem(ignoreR);
		spacesTreat
				.setSelectedItem(getAttribute(Constants.PR_WHITE_SPACE_TREATMENT));
		spacesTreat.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					setAttribute(Constants.PR_WHITE_SPACE_TREATMENT,
							getEnumProperty(current));
				}
			}
		});

		st.add(new JLabel(UiText.PARAGRAPH_SPYSYM_DEAL_SPACE_LABEL));
		st.add(spacesTreat);

		JPanel lf = new JPanel();
		lf.setLayout(new FlowLayout(FlowLayout.LEFT));

		WiseCombobox linefeed = new WiseCombobox();
		linefeed.addItem(preserve);
		linefeed.addItem(ignore);
		linefeed.addItem(asSpace);
		linefeed.setSelectedItem(getAttribute(Constants.PR_LINEFEED_TREATMENT));
		linefeed.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					setAttribute(Constants.PR_LINEFEED_TREATMENT,
							getEnumProperty(current));
				}
			}
		});
		lf.add(new JLabel(UiText.PARAGRAPH_SPYSYM_DEAL_ENTER_LABEL));
		lf.add(linefeed);

		spSymPanel.add(nfs);
		spSymPanel.add(st);
		spSymPanel.add(lf);

		spSymPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				UiText.PARAGRAPH_SPYSYM_SPECIAL_LABEL, TitledBorder.LEFT,
				TitledBorder.TOP));
		this.add(spSymPanel);

	}

	public String getAttribute(int key)
	{
		String result = "";
		Map<Integer, Object> map = ParagraphPanel.getAttr();
		if (map != null && map.containsKey(key))
		{
			if (map.get(key) instanceof Integer)
			{
				result = getItem((Integer) map.get(key));
			} else if (map.get(key) instanceof EnumProperty)
			{
				EnumProperty ep = (EnumProperty) map.get(key);
				if (ep != null && ep.getNumber() != null)
				{
					result = getItem(ep.getNumber().intValue());
				}
			}
		}
		return result;
	}

	public int getEnumProperty(String item)
	{
		int value = 0;
		if (item.equalsIgnoreCase(all))
		{
			value = Constants.EN_FALSE;
		} else if (item.equalsIgnoreCase(one))
		{
			value = Constants.EN_TRUE;
		} else if (item.equalsIgnoreCase(ignore))
		{
			value = Constants.EN_IGNORE;
		} else if (item.equalsIgnoreCase(preserve))
		{
			value = Constants.EN_PRESERVE;
		} else if (item.equalsIgnoreCase(ignoreB))
		{
			value = Constants.EN_IGNORE_IF_BEFORE_LINEFEED;
		} else if (item.equalsIgnoreCase(ignoreA))
		{
			value = Constants.EN_IGNORE_IF_AFTER_LINEFEED;
		} else if (item.equalsIgnoreCase(ignoreR))
		{
			value = Constants.EN_IGNORE_IF_SURROUNDING_LINEFEED;
		} else if (item.equalsIgnoreCase(asSpace))
		{
			value = Constants.EN_SPACE;
		}
		return value;
	}

	public String getItem(int value)
	{
		String item = "";
		if (value == Constants.EN_FALSE)
		{
			item = all;
		} else if (value == Constants.EN_TRUE)
		{
			item = one;
		} else if (value == Constants.EN_IGNORE)
		{
			item = ignore;
		} else if (value == Constants.EN_PRESERVE)
		{
			item = preserve;
		} else if (value == Constants.EN_IGNORE_IF_BEFORE_LINEFEED)
		{
			item = ignoreB;
		} else if (value == Constants.EN_IGNORE_IF_AFTER_LINEFEED)
		{
			item = ignoreA;
		} else if (value == Constants.EN_IGNORE_IF_SURROUNDING_LINEFEED)
		{
			item = ignoreR;
		} else if (value == Constants.EN_SPACE)
		{
			item = asSpace;
		}
		return item;
	}

	public void setAttribute(int key, Object value)
	{
		ParagraphPanel.getAttr().put(key, value);
	}

}
