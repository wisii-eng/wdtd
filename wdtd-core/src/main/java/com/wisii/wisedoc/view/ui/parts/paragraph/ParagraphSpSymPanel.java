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

package com.wisii.wisedoc.view.ui.parts.paragraph;

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
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.ParagraphPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 特殊符号处理设置面板
 * 
 * @author 钟亚军
 * @version 0.1 2009/01/19
 */
@SuppressWarnings("serial")
public class ParagraphSpSymPanel extends JPanel
{

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
		Map<Integer, Object> map = RibbonUIModel
				.getReadCompletePropertiesByType().get(ActionType.BLOCK);
		JPanel spSymPanel = new JPanel();
		spSymPanel.setPreferredSize(new Dimension(467, 120));
		GridLayout layout = new GridLayout(3, 1, 1, 1);
		spSymPanel.setLayout(layout);

		JPanel nfs = new JPanel();
		nfs.setLayout(new FlowLayout(FlowLayout.LEFT));

		WiseCombobox numberofspaces = new WiseCombobox();
		numberofspaces.addItem(all);
		numberofspaces.addItem(one);

		Integer wsc = (Integer) map.get(Constants.PR_WHITE_SPACE_COLLAPSE);
		if (wsc != null && wsc == Constants.EN_TRUE)
		{
			numberofspaces.setSelectedIndex(1);
		}
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

		Integer wst = (Integer) map.get(Constants.PR_WHITE_SPACE_TREATMENT);
		spacesTreat.setSelectedIndex(getWSTindex(wst));
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

		Integer lfenum = (Integer) map.get(Constants.PR_LINEFEED_TREATMENT);
		linefeed.setSelectedIndex(getLFindex(lfenum));
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

	public int getWSTindex(Integer value)
	{
		int result = 0;
		if (value != null)
		{
			if (value == Constants.EN_IGNORE)
			{
				result = 1;
			} else if (value == Constants.EN_IGNORE_IF_BEFORE_LINEFEED)
			{
				result = 2;
			} else if (value == Constants.EN_IGNORE_IF_AFTER_LINEFEED)
			{
				result = 3;
			} else if (value == Constants.EN_IGNORE_IF_SURROUNDING_LINEFEED)
			{
				result = 4;
			}
		}
		return result;
	}

	public int getLFindex(Integer value)
	{
		int result = 2;
		if (value != null)
		{
			if (value == Constants.EN_IGNORE)
			{
				result = 1;
			} else if (value == Constants.EN_PRESERVE)
			{
				result = 0;
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

	public void setAttribute(int key, Object value)
	{
		ParagraphPropertyModel.setFOProperty(key, value);
	}

}
