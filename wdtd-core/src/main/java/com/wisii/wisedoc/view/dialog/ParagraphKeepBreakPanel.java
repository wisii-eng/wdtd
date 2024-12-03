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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.model.ParagraphPropertyModel;
import com.wisii.wisedoc.view.ui.text.UiText;

public class ParagraphKeepBreakPanel extends JPanel
{

	// Map<Integer, Object> map = ParagraphPanel.getAttr();

	String nobreak = UiText.PARAGRAPH_NO_PAGE_BREAK;

	String next_page = UiText.PARAGRAPH_NEXT_PAGE_BREAK;

	String next_odd_page = UiText.PARAGRAPH_NEXT_ODD_PAGE;

	String next_even_page = UiText.PARAGRAPH_NEXT_EVEN_PAGE;

	String auto = UiText.PARAGRAPH_AUTO;

	String always = UiText.PARAGRAPH_ALWAYS;

	String column = UiText.PARAGRAPH_COLUMN;

	String page = UiText.PARAGRAPH_PAGE;

	public ParagraphKeepBreakPanel()
	{
		super();
		initialize();
	}

	private void initialize()
	{
		this.setPreferredSize(new Dimension(460, 450));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		JPanel breakPanel = new JPanel();
		breakPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		breakPanel.setPreferredSize(new Dimension(450, 60));

		WiseCombobox breakBefore = new WiseCombobox();
		breakBefore.addItem(auto);
		breakBefore.addItem(next_page);
		breakBefore.addItem(next_odd_page);
		breakBefore.addItem(next_even_page);
		// breakBefore.addItem(next_column);
		breakBefore.setSelectedItem(getAttribute(Constants.PR_BREAK_BEFORE));
		breakBefore.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					EnumProperty value = current.equalsIgnoreCase(nobreak) ? new EnumProperty(
							Constants.EN_AUTO, "")
							: getEnumProperty(current);
					setAttribute(Constants.PR_BREAK_BEFORE, value);
				}
			}
		});

		WiseCombobox breakAfter = new WiseCombobox();
		breakAfter.addItem(auto);
		breakAfter.addItem(next_page);
		breakAfter.addItem(next_odd_page);
		breakAfter.addItem(next_even_page);
		// breakAfter.addItem(next_column);
		breakAfter.setSelectedItem(getAttribute(Constants.PR_BREAK_AFTER));
		breakAfter.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					EnumProperty value = current.equalsIgnoreCase(nobreak) ? new EnumProperty(
							Constants.EN_AUTO, "")
							: getEnumProperty(current);
					setAttribute(Constants.PR_BREAK_AFTER, value);
				}
			}
		});

		breakPanel.add(new JLabel(UiText.PARAGRAPH_BREAK_PAGE_BEFORE_BUTTON));
		breakPanel.add(breakBefore);
		breakPanel.add(new JLabel(UiText.PARAGRAPH_BREAK_AFTER_PAGE_LABEL));
		breakPanel.add(breakAfter);
		breakPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				UiText.PARAGRAPH_PAGE_NEXT_PAGE_LABEL, TitledBorder.LEFT,
				TitledBorder.TOP));

		JPanel keepPanel = new JPanel();
		GridLayout keeplayout = new GridLayout(3, 1, 1, 1);
		keepPanel.setLayout(keeplayout);
		keepPanel.setPreferredSize(new Dimension(450, 120));

		JPanel keepTogether = new JPanel();
		keepTogether.setLayout(new FlowLayout(FlowLayout.LEFT));

		final JCheckBox keepTogetherColumn = new JCheckBox(
				UiText.PARAGRAPH_PAGE_NO_BREAK_COLUMN);
		keepTogetherColumn.setSelected(getAttribute(Constants.PR_KEEP_TOGETHER,
				column));
		keepTogetherColumn.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_KEEP_TOGETHER, getKeepProperty(
						Constants.PR_KEEP_TOGETHER, keepTogetherColumn
								.isSelected(), column));
			}
		});

		final JCheckBox keepTogetherPage = new JCheckBox(
				UiText.PARAGRAPH_PAGE_NO_BREAK_PAGE);
		keepTogetherPage.setSelected(getAttribute(Constants.PR_KEEP_TOGETHER,
				page));
		keepTogetherPage.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_KEEP_TOGETHER, getKeepProperty(
						Constants.PR_KEEP_TOGETHER, keepTogetherPage
								.isSelected(), page));
			}
		});
		keepTogether.add(keepTogetherColumn);
		keepTogether.add(keepTogetherPage);

		JPanel keepPrevious = new JPanel();
		keepPrevious.setLayout(new FlowLayout(FlowLayout.LEFT));

		final JCheckBox keepPreviousColumn = new JCheckBox(
				UiText.PARAGRAPH_KEEP_PREVIOUS_COLUMN);
		keepPreviousColumn.setSelected(getAttribute(
				Constants.PR_KEEP_WITH_PREVIOUS, column));
		keepPreviousColumn.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_KEEP_WITH_PREVIOUS, getKeepProperty(
						Constants.PR_KEEP_WITH_PREVIOUS, keepPreviousColumn
								.isSelected(), column));
			}
		});

		final JCheckBox keepPreviousPage = new JCheckBox(
				UiText.PARAGRAPH_KEEP_WITH_PREVIOUS_PAGE);
		keepPreviousPage.setSelected(getAttribute(
				Constants.PR_KEEP_WITH_PREVIOUS, page));
		keepPreviousPage.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_KEEP_WITH_PREVIOUS, getKeepProperty(
						Constants.PR_KEEP_WITH_PREVIOUS, keepPreviousPage
								.isSelected(), page));
			}
		});
		keepPrevious.add(keepPreviousColumn);
		keepPrevious.add(keepPreviousPage);

		JPanel keepNext = new JPanel();
		keepNext.setLayout(new FlowLayout(FlowLayout.LEFT));

		final JCheckBox keepNextColumn = new JCheckBox(
				UiText.PARAGRAPH_KEEP_WITH_NEXT_COLUMN);
		keepNextColumn.setSelected(getAttribute(Constants.PR_KEEP_WITH_NEXT,
				column));
		keepNextColumn.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_KEEP_WITH_NEXT, getKeepProperty(
						Constants.PR_KEEP_WITH_NEXT, keepNextColumn
								.isSelected(), column));
			}
		});

		final JCheckBox keepNextPage = new JCheckBox(
				UiText.PARAGRAPH_KEEP_WITH_NEXT_PARA_BUTTON);
		keepNextPage
				.setSelected(getAttribute(Constants.PR_KEEP_WITH_NEXT, page));
		keepNextPage.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_KEEP_WITH_NEXT, getKeepProperty(
						Constants.PR_KEEP_WITH_NEXT, keepNextPage.isSelected(),
						page));
			}
		});
		keepNext.add(keepNextColumn);
		keepNext.add(keepNextPage);

		keepPanel.add(keepTogether);
		keepPanel.add(keepPrevious);
		keepPanel.add(keepNext);
		keepPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				UiText.PARAGRAPH_PAGE_NEXT_PAGE_LABEL, TitledBorder.LEFT,
				TitledBorder.TOP));

		JPanel widowsOrphans = new JPanel();

		widowsOrphans.setLayout(new FlowLayout(FlowLayout.LEFT));
		widowsOrphans.setPreferredSize(new Dimension(450, 80));

		WiseSpinner widows = new WiseSpinner();
		SpinnerNumberModel widowsNumber = new SpinnerNumberModel(0, 0, 10000, 1);
		widows.setModel(widowsNumber);
		if (getAttributeValue(Constants.PR_WIDOWS) != null)
		{
			widows.setValue(getAttributeValue(Constants.PR_WIDOWS));
		} else
		{
			widows.setValue(2);
		}
		widows.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				setAttribute(Constants.PR_WIDOWS, ((WiseSpinner) e.getSource())
						.getValue());
			};

		});

		WiseSpinner orphans = new WiseSpinner();
		SpinnerNumberModel orphansNumber = new SpinnerNumberModel(0, 0, 10000,
				1);
		orphans.setModel(orphansNumber);
		if (getAttributeValue(Constants.PR_ORPHANS) != null)
		{
			orphans.setValue(getAttributeValue(Constants.PR_ORPHANS));
		} else
		{
			orphans.setValue(2);
		}
		orphans.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				setAttribute(Constants.PR_ORPHANS,
						((WiseSpinner) e.getSource()).getValue());
			};

		});
		widowsOrphans.add(new JLabel(UiText.PARAGRAPH_LAST_LINE_MIN_NUMBER));
		widowsOrphans.add(widows);
		widowsOrphans.add(new JLabel(UiText.PARAGRAPH_FIRST_LINE_MIN_NUMBER));
		widowsOrphans.add(orphans);
		widowsOrphans.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				UiText.PARAGRAPH_WIDOWS_ORPHAN, TitledBorder.LEFT,
				TitledBorder.TOP));

		this.add(breakPanel);
		this.add(keepPanel);
		this.add(widowsOrphans);
	}

	public KeepProperty getKeepProperty(int key, boolean item, String child)
	{
		KeepProperty old = (KeepProperty) ParagraphPropertyModel
				.getCurrentProperty(key);
		if (old != null)
		{
			EnumProperty line = old.getWithinLine();
			EnumProperty columne = old.getWithinColumn();
			EnumProperty pagee = old.getWithinPage();
			if (child.equalsIgnoreCase(column))
			{
				columne = getEnumProperty(item);
			} else if (child.equalsIgnoreCase(page))
			{
				pagee = getEnumProperty(item);
			}
			return new KeepProperty(line, columne, pagee);
		} else
		{
			if (child.equalsIgnoreCase(column))
			{
				EnumProperty columne = getEnumProperty(item);
				return new KeepProperty(null, columne, null);
			} else if (child.equalsIgnoreCase(page))
			{
				EnumProperty page = getEnumProperty(item);
				return new KeepProperty(null, null, page);
			}

		}
		return null;
	}

	public EnumProperty getEnumProperty(String item)
	{
		int value = 0;
		if (item.equalsIgnoreCase(nobreak))
		{
			value = Constants.EN_AUTO;
		} else if (item.equalsIgnoreCase(next_page))
		{
			value = Constants.EN_PAGE;
		} else if (item.equalsIgnoreCase(next_odd_page))
		{
			value = Constants.EN_ODD_PAGE;
		} else if (item.equalsIgnoreCase(next_even_page))
		{
			value = Constants.EN_EVEN_PAGE;
		} else if (item.equalsIgnoreCase(auto))
		{
			value = Constants.EN_AUTO;
		} else if (item.equalsIgnoreCase(always))
		{
			value = Constants.EN_ALWAYS;
		}
		return new EnumProperty(value, "");
	}

	public EnumProperty getEnumProperty(boolean item)
	{
		if (item)
		{
			return new EnumProperty(Constants.EN_ALWAYS, "");
		} else
		{
			return new EnumProperty(Constants.EN_AUTO, "");
		}
	}

	public String getItem(int value)
	{
		String item = "";
		if (value == Constants.EN_AUTO)
		{
			item = auto;
		} else if (value == Constants.EN_PAGE)
		{
			item = next_page;
		} else if (value == Constants.EN_ODD_PAGE)
		{
			item = next_odd_page;
		} else if (value == Constants.EN_EVEN_PAGE)
		{
			item = next_even_page;
		} else if (value == Constants.EN_ALWAYS)
		{
			item = always;
		}
		return item;
	}

	public void setAttribute(int key, Object value)
	{
		ParagraphPanel.getAttr().put(key, value);
	}

	public String getAttribute(int key)
	{
		String result = "";
		Map<Integer, Object> map = ParagraphPanel.getAttr();
		if (key == Constants.PR_BREAK_BEFORE || key == Constants.PR_BREAK_AFTER)
		{
			result = nobreak;
		} else if (key == Constants.PR_KEEP_TOGETHER
				|| key == Constants.PR_KEEP_WITH_NEXT
				|| key == Constants.PR_KEEP_WITH_PREVIOUS)
		{
			result = auto;
		}
		if (map != null && map.containsKey(key))
		{
			if (map.get(key) instanceof Integer)
			{
				result = getItem((Integer) map.get(key));
			} else if (map.get(key) instanceof EnumProperty)
			{
				EnumProperty ep = (EnumProperty) map.get(key);
				if (ep != null)
				{
					result = getItem(ep.getEnum());
				}
			}
		}
		return result;
	}

	public boolean getAttribute(int key, String child)
	{
		Map<Integer, Object> map = ParagraphPanel.getAttr();
		if (map != null && map.containsKey(key))
		{
			if (map.get(key) instanceof KeepProperty)
			{
				KeepProperty sp = (KeepProperty) map.get(key);
				if (sp != null)
				{
					if (child.equals(column))
					{
						return sp.getWithinColumn().getEnum() == Constants.EN_ALWAYS;
					} else if (child.equals(page))
					{
						return sp.getWithinPage().getEnum() == Constants.EN_ALWAYS;
					}
				}
			}
		}
		return false;
	}

	public Object getAttributeValue(int key)
	{
		Map<Integer, Object> map = ParagraphPanel.getAttr();
		if (map != null && map.containsKey(key))
		{
			return map.get(key);
		}
		return null;
	}

}
