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

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ALB;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ALL_EVEN;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ALL_ODD;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_AUTO;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_END_EVEN;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_END_ODD;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_EVEN;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_NO_LIMIT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_NUMBER;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ODD;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ONE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ROMAN;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class PageSequencePanel extends JPanel implements PropertyPanelInterface
{

	private Map<Integer, Object> attr = new HashMap<Integer, Object>();

	Map<Integer, Object> old = new HashMap<Integer, Object>();

	@Override
	public Map<Integer, Object> getSetting()
	{
		return ParagraphPanel.replaceKey(old, attr);
	}

	@Override
	public void install(Map<Integer, Object> map)
	{
		attr = map;
		old = new HashMap<Integer, Object>(map);
		setLayout(new FlowLayout());
//		setPreferredSize(new Dimension(420, 300));
		JPanel initialPageNumberpanel = new JPanel(new FlowLayout(
				FlowLayout.LEFT));
		initialPageNumberpanel.setPreferredSize(new Dimension(400, 40));
		JLabel itpnlabel = new JLabel(
				RibbonUIText.PAGE_SEQUENCE_BAND_START_PAGE_NUMBER);
		itpnlabel.setPreferredSize(new Dimension(60, 30));

		WiseCombobox initialPageNumber = new WiseCombobox();
		initialPageNumber.addItem(one);
		initialPageNumber.addItem(auto);
		initialPageNumber.addItem(odd);
		initialPageNumber.addItem(even);
		initialPageNumber.setEditable(true);
		initialPageNumber.setPreferredSize(new Dimension(120, 30));
		EnumNumber initialpagepumber = attr != null ? (EnumNumber) attr
				.get(Constants.PR_INITIAL_PAGE_NUMBER) : null;
		EnumProperty forcepagecount = attr != null ? (EnumProperty) attr
				.get(Constants.PR_FORCE_PAGE_COUNT) : null;
		String formatStr = attr != null ? (String) attr
				.get(Constants.PR_FORMAT) : null;
		String ipn = one;
		if (initialpagepumber != null)
		{
			if (initialpagepumber.getEnum() < 0)
			{
				ipn = initialpagepumber.getNumber() + "";
			} else
			{
				ipn = getItem(initialpagepumber.getEnum());
			}
		}
		initialPageNumber.setSelectedItem(ipn);
		initialPageNumber.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					String current = e.getItem().toString();
					EnumNumber newipn = null;
					if (current.equalsIgnoreCase(auto))
					{
						newipn = new EnumNumber(Constants.EN_AUTO, 0);
					} else if (current.equalsIgnoreCase(odd))
					{
						newipn = new EnumNumber(Constants.EN_AUTO_ODD, 0);
					} else if (current.equalsIgnoreCase(even))
					{
						newipn = new EnumNumber(Constants.EN_AUTO_EVEN, 0);
					} else
					{
						newipn = new EnumNumber(-1, new Integer(current)
								.intValue());
					}
					setAttribute(Constants.PR_INITIAL_PAGE_NUMBER, newipn);
				}

			}
		});
		initialPageNumberpanel.add(itpnlabel);
		initialPageNumberpanel.add(initialPageNumber);

		JPanel forcePageCountpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		forcePageCountpanel.setPreferredSize(new Dimension(400, 40));

		JLabel fpclabel = new JLabel(RibbonUIText.PAGE_SEQUENCE_BAND_PAGE_LIMIT);
		fpclabel.setPreferredSize(new Dimension(60, 30));

		String fpc = forcepagecount == null ? auto : getItem(forcepagecount
				.getEnum());
		WiseCombobox forcePageCount = new WiseCombobox();
		forcePageCount.addItem(auto);
		forcePageCount.addItem(all_odd);
		forcePageCount.addItem(all_even);
		forcePageCount.addItem(end_odd);
		forcePageCount.addItem(end_even);
		forcePageCount.addItem(no_force);
		forcePageCount.setEditable(true);
		forcePageCount.setSelectedItem(fpc);
		forcePageCount.setPreferredSize(new Dimension(120, 30));
		forcePageCount.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					int index = ((WiseCombobox) e.getSource())
							.getSelectedIndex();
					int result = Constants.EN_AUTO;
					switch (index)
					{
						case 1:
						{
							result = Constants.EN_ODD;
							break;
						}
						case 2:
						{
							result = Constants.EN_EVEN;
							break;
						}
						case 3:
						{
							result = Constants.EN_END_ON_ODD;
							break;
						}
						case 4:
						{
							result = Constants.EN_END_ON_EVEN;
							break;
						}
						case 5:
						{
							result = Constants.EN_NO_FORCE;
							break;
						}
					}
					EnumProperty enumValue = new EnumProperty(result, "");
					setAttribute(Constants.PR_FORCE_PAGE_COUNT, enumValue);
				}
			}
		});

		forcePageCountpanel.add(fpclabel);
		forcePageCountpanel.add(forcePageCount);

		JPanel formatpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		formatpanel.setPreferredSize(new Dimension(400, 40));

		JLabel formatlabel = new JLabel(
				RibbonUIText.PAGE_SEQUENCE_BAND_PAGE_TYPE);
		fpclabel.setPreferredSize(new Dimension(60, 30));

		String fmt = alb;
		if (formatStr != null && formatStr.equalsIgnoreCase("i"))
		{
			fmt = luoma;
		}
		WiseCombobox format = new WiseCombobox();
		format.addItem(alb);
		format.addItem(luoma);
		format.setSelectedItem(fmt);
		format.setPreferredSize(new Dimension(120, 30));
		format.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					int index = ((WiseCombobox) e.getSource())
							.getSelectedIndex();
					String result = "1";
					if (index == 1)
					{
						result = "i";
					}

					setAttribute(Constants.PR_FORMAT, result);
				}
			}
		});
		formatpanel.add(formatlabel);
		formatpanel.add(format);

		this.add(initialPageNumberpanel);
		this.add(forcePageCountpanel);
		this.add(formatpanel);

	}

	public PageSequencePanel()
	{
		setPreferredSize(new Dimension(420, 300));
	}

	public void setAttribute(int key, Object value)
	{
		attr.put(key, value);
	}

	public String getItem(int value)
	{
		String item = "";
		if (value == 1)
		{
			item = one;
		} else if (value == Constants.EN_AUTO_EVEN)
		{
			item = even;
		} else if (value == Constants.EN_AUTO_ODD)
		{
			item = odd;
		} else if (value == Constants.EN_AUTO)
		{
			item = auto;
		} else if (value == Constants.EN_END_ON_ODD)
		{
			item = end_odd;
		} else if (value == Constants.EN_END_ON_EVEN)
		{
			item = end_even;
		} else if (value == Constants.EN_EVEN)
		{
			item = all_even;
		} else if (value == Constants.EN_ODD)
		{
			item = all_odd;
		} else if (value == Constants.EN_NO_FORCE)
		{
			item = no_force;
		} else
		{
			item = value + "";
		}
		return item;
	}

	String one = PAGE_SEQUENCE_BAND_ONE;

	String auto = PAGE_SEQUENCE_BAND_AUTO;

	String odd = PAGE_SEQUENCE_BAND_ODD;

	String even = PAGE_SEQUENCE_BAND_EVEN;

	String number = PAGE_SEQUENCE_BAND_NUMBER;

	String all_odd = PAGE_SEQUENCE_BAND_ALL_ODD;

	String all_even = PAGE_SEQUENCE_BAND_ALL_EVEN;

	String end_odd = PAGE_SEQUENCE_BAND_END_ODD;

	String end_even = PAGE_SEQUENCE_BAND_END_EVEN;

	String no_force = PAGE_SEQUENCE_BAND_NO_LIMIT;

	String alb = PAGE_SEQUENCE_BAND_ALB;

	String luoma = PAGE_SEQUENCE_BAND_ROMAN;

	@Override
	public boolean isValidate()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
