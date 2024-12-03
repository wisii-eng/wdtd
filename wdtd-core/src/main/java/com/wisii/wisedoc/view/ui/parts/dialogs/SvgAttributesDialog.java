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

package com.wisii.wisedoc.view.ui.parts.dialogs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.document.svg.SVGUtil;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.ui.parts.svg.SvgBorderAndColorPanel;
import com.wisii.wisedoc.view.ui.parts.svg.SvgPositionPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class SvgAttributesDialog extends JDialog
{

	List<CellElement> selects;

	CardLayout layout = new CardLayout();

	JTabbedPane tabs;

	private static Map<Integer, Object> attrmap = new HashMap<Integer, Object>();

	public SvgAttributesDialog(List<CellElement> selectsobj)
	{
		this(selectsobj, 0);
	}

	public SvgAttributesDialog(List<CellElement> selectsobj, int selectedindex)
	{
		super();
		selects = selectsobj;
		this.setTitle(UiText.SET_SVG_STYLE);

		setPreferredSize(new Dimension(450, 400));
		WisedocUtil.centerOnScreen(this);
		setLayout(new BorderLayout());
		getAttributes();
		tabs = new JTabbedPane();

		tabs.addTab(UiText.SVG_BORDER_AND_COLOR, new SvgBorderAndColorPanel());
		tabs.addTab(UiText.SVG_POSITION, new SvgPositionPanel());
		ButtonPanel bp = new ButtonPanel();
		add(tabs, BorderLayout.CENTER);
		add(bp, BorderLayout.SOUTH);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.NORTH);
		pack();
		tabs.setSelectedIndex(selectedindex);
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	class ButtonPanel extends JPanel
	{

		public ButtonPanel()
		{
			super();
			JButton yesButton = new JButton(UiText.DIALOG_OK);
			yesButton.addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent e)
				{
					setAttributes();
					dispose();
				}
			});
			JButton cancelButton = new JButton(UiText.DIALOG_CANCEL);
			cancelButton.addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
		}
	}

	public void setAttributes()
	{
		setAllSvgAttributes();
		setLineAttributes();
		setRecAndEllAttributes();
		setSvgOtherAttributes();
	}

	public void setAllSvgAttributes()
	{
		Map<Integer, Object> map = getAttrmap();
		Map<Integer, Object> mapall = new HashMap<Integer, Object>();
		if (map.containsKey(Constants.PR_COLOR))
		{
			mapall.put(Constants.PR_COLOR, map.get(Constants.PR_COLOR));
		}
		if (map.containsKey(Constants.PR_SVG_LINE_TYPE))
		{
			mapall.put(Constants.PR_SVG_LINE_TYPE, map
					.get(Constants.PR_SVG_LINE_TYPE));
		}
		if (map.containsKey(Constants.PR_STROKE_WIDTH))
		{
			mapall.put(Constants.PR_STROKE_WIDTH, map
					.get(Constants.PR_STROKE_WIDTH));
		}
		if (map.containsKey(Constants.PR_SVG_ORIENTATION))
		{
			mapall.put(Constants.PR_SVG_ORIENTATION, map
					.get(Constants.PR_SVG_ORIENTATION));
		}
		SystemManager.getCurruentDocument().setElementAttributes(selects,
				mapall, false);
	}

	public void setLineAttributes()
	{
		Map<Integer, Object> map = getAttrmap();
		Map<Integer, Object> mapline = new HashMap<Integer, Object>();
		if (map.containsKey(Constants.PR_SVG_ARROW_START_TYPE))
		{
			mapline.put(Constants.PR_SVG_ARROW_START_TYPE, map
					.get(Constants.PR_SVG_ARROW_START_TYPE));
		}
		if (map.containsKey(Constants.PR_SVG_ARROW_END_TYPE))
		{
			mapline.put(Constants.PR_SVG_ARROW_END_TYPE, map
					.get(Constants.PR_SVG_ARROW_END_TYPE));
		}
		List<CellElement> selectsline = new ArrayList<CellElement>();
		for (CellElement current : selects)
		{
			if (current instanceof Line)
			{
				selectsline.add(current);
			}
		}
		SystemManager.getCurruentDocument().setElementAttributes(selectsline,
				mapline, false);
	}

	public void setRecAndEllAttributes()
	{
		Map<Integer, Object> map = getAttrmap();
		Map<Integer, Object> maprande = new HashMap<Integer, Object>();
		if (map.containsKey(Constants.PR_FILL))
		{
			maprande.put(Constants.PR_FILL, map.get(Constants.PR_FILL));
		}
		if (map.containsKey(Constants.PR_APHLA))
		{
			maprande.put(Constants.PR_APHLA, map.get(Constants.PR_APHLA));
		}
		List<CellElement> selectsrecandell = new ArrayList<CellElement>();
		for (CellElement current : selects)
		{
			if (current instanceof Rectangle || current instanceof Ellipse)
			{
				selectsrecandell.add(current);
			}
		}
		SystemManager.getCurruentDocument().setElementAttributes(
				selectsrecandell, maprande, false);
	}

	public void setSvgOtherAttributes()
	{
		Map<Integer, Object> map = getAttrmap();
		for (CellElement current : selects)
		{
			Map<Integer, Object> mapall = new HashMap<Integer, Object>();
			if (current instanceof Line)
			{
				if (map.containsKey(Constants.PR_HEIGHT))
				{
					FixedLength y1 = (FixedLength) map.get(Constants.PR_Y1);
					FixedLength y2 = (FixedLength) map.get(Constants.PR_Y2);
					if (SVGUtil.inRightBottomSide(y1, y2))
					{
						mapall.put(Constants.PR_Y2, SVGUtil.getSumLength(y1,
								(FixedLength) map.get(Constants.PR_HEIGHT)));
					} else
					{
						mapall.put(Constants.PR_Y1, SVGUtil.getSumLength(y2,
								(FixedLength) map.get(Constants.PR_HEIGHT)));
					}
				}
				if (map.containsKey(Constants.PR_WIDTH))
				{
					FixedLength x1 = (FixedLength) map.get(Constants.PR_X1);
					FixedLength x2 = (FixedLength) map.get(Constants.PR_X2);
					if (SVGUtil.inRightBottomSide(x1, x2))
					{
						mapall.put(Constants.PR_X2, SVGUtil.getSumLength(x1,
								(FixedLength) map.get(Constants.PR_WIDTH)));
					} else
					{
						mapall.put(Constants.PR_X1, SVGUtil.getSumLength(x2,
								(FixedLength) map.get(Constants.PR_WIDTH)));
					}
				}
				SystemManager.getCurruentDocument().setElementAttributes(
						current, mapall, false);
			} else if (current instanceof Rectangle)
			{
				if (map.containsKey(Constants.PR_HEIGHT))
				{
					mapall.put(Constants.PR_HEIGHT, map
							.get(Constants.PR_HEIGHT));
				}
				if (map.containsKey(Constants.PR_WIDTH))
				{
					mapall.put(Constants.PR_WIDTH, map.get(Constants.PR_WIDTH));
				}
				SystemManager.getCurruentDocument().setElementAttributes(
						current, mapall, false);

			} else if (current instanceof Ellipse)
			{
				if (map.containsKey(Constants.PR_HEIGHT))
				{
					mapall.put(Constants.PR_RY, SVGUtil.getNumbersOfLength(
							(FixedLength) map.get(Constants.PR_HEIGHT), 05d));
				}
				if (map.containsKey(Constants.PR_WIDTH))
				{
					mapall.put(Constants.PR_RX, SVGUtil.getNumbersOfLength(
							(FixedLength) map.get(Constants.PR_WIDTH), 05d));
				}
				SystemManager.getCurruentDocument().setElementAttributes(
						current, mapall, false);
			}
		}

	}

	public void getAttributes()
	{
		Color fillcolor = null;
		Color strokecolor = null;
		Object opacityfill = null;
		int strokestyle = -1;
		FixedLength strokewidth = null;
		int shistyle = -1;
		int zhongstyle = -1;
		FixedLength height = null;
		FixedLength width = null;
		int rotating = 0;
		if (selects != null && selects.size() > 0)
		{
			for (int i = 0; i < selects.size(); i++)
			{
				CellElement current = selects.get(i);
				if (i == 0)
				{
					if (current.getAttributes() == null)
					{
						return;
					} else
					{
						Map<Integer, Object> map = current.getAttributes()
								.getAttributes();
						if (map == null)
						{
							return;
						} else
						{
							Object[] keys = map.keySet().toArray();
							int size = keys.length;
							for (int j = 0; j < size; j++)
							{
								int key = (Integer) keys[j];
								if (key == Constants.PR_FILL)
								{
									fillcolor = (Color) map.get(key);
								} else if (key == Constants.PR_COLOR)
								{
									strokecolor = (Color) map.get(key);
								} else if (key == Constants.PR_APHLA)
								{
									opacityfill = map.get(key);
								} else if (key == Constants.PR_SVG_LINE_TYPE)
								{
									strokestyle = (Integer) map.get(key);
								} else if (key == Constants.PR_STROKE_WIDTH)
								{
									strokewidth = (FixedLength) map.get(key);
								} else if (key == Constants.PR_SVG_ORIENTATION)
								{
									rotating = (Integer) map.get(key);
								}
							}
							if (current instanceof Line)
							{
								for (int j = 0; j < size; j++)
								{
									int key = (Integer) keys[j];
									if (key == Constants.PR_SVG_ARROW_START_TYPE)
									{
										shistyle = (Integer) map.get(key);
									} else if (key == Constants.PR_SVG_ARROW_END_TYPE)
									{
										zhongstyle = (Integer) map.get(key);
									} else if (key == Constants.PR_Y1)
									{
										FixedLength y1 = (FixedLength) map
												.get(key);
										FixedLength y2 = (FixedLength) map
												.get(Constants.PR_Y2);
										height = SVGUtil
												.getSubAbsLength(y1, y2);
									} else if (key == Constants.PR_X1)
									{
										FixedLength x1 = (FixedLength) map
												.get(key);
										FixedLength x2 = (FixedLength) map
												.get(Constants.PR_X2);
										width = SVGUtil.getSubAbsLength(x1, x2);
									}
								}
							} else if (current instanceof Rectangle)
							{
								for (int j = 0; j < size; j++)
								{
									int key = (Integer) keys[j];
									if (key == Constants.PR_HEIGHT)
									{
										height = (FixedLength) map.get(key);
									} else if (key == Constants.PR_WIDTH)
									{
										width = (FixedLength) map.get(key);
									}
								}
							} else if (current instanceof Ellipse)
							{
								for (int j = 0; j < size; j++)
								{
									int key = (Integer) keys[j];
									if (key == Constants.PR_RY)
									{
										height = SVGUtil.getNumbersOfLength(
												(FixedLength) map.get(key), 2);
									} else if (key == Constants.PR_RX)
									{
										width = SVGUtil.getNumbersOfLength(
												(FixedLength) map.get(key), 2);
									}
								}
							}
						}
					}
				} else
				{
					if (current.getAttributes() == null)
					{
						return;
					} else
					{
						Map<Integer, Object> map = current.getAttributes()
								.getAttributes();
						if (map == null)
						{
							return;
						} else
						{
							fillcolor = getColorEqual(fillcolor, map,
									Constants.PR_FILL);
							strokecolor = getColorEqual(strokecolor, map,
									Constants.PR_COLOR);
							if (opacityfill != null)
							{
								if (map.containsKey(Constants.PR_APHLA))
								{
									Object currentopacityfill = map
											.get(Constants.PR_APHLA);
									if (!(opacityfill
											.equals(currentopacityfill)))
									{
										opacityfill = null;
									}
								}
							}
							strokestyle = getIntEqual(strokestyle, map,
									Constants.PR_SVG_LINE_TYPE);
							strokewidth = getFixedLengthEqual(strokewidth, map,
									Constants.PR_STROKE_WIDTH, 0, 1);
							rotating = getIntEqual(rotating, map,
									Constants.PR_SVG_ORIENTATION);
							shistyle = getIntEqual(shistyle, map,
									Constants.PR_SVG_ARROW_START_TYPE);
							zhongstyle = getIntEqual(zhongstyle, map,
									Constants.PR_SVG_ARROW_END_TYPE);
							if (current instanceof Line)
							{
								height = getFixedLengthEqual(height, map,
										Constants.PR_Y1, Constants.PR_Y2, 1);
								width = getFixedLengthEqual(width, map,
										Constants.PR_X1, Constants.PR_X2, 1);
							} else if (current instanceof Rectangle)
							{
								height = getFixedLengthEqual(height, map,
										Constants.PR_HEIGHT, 0, 1);
								width = getFixedLengthEqual(width, map,
										Constants.PR_HEIGHT, 0, 1);
							} else if (current instanceof Ellipse)
							{
								height = getFixedLengthEqual(height, map,
										Constants.PR_RY, 0, 2);
								width = getFixedLengthEqual(width, map,
										Constants.PR_RX, 0, 2);
							}
						}
					}
				}
			}
		}

		if (fillcolor != null)
		{
			setItem(Constants.PR_FILL, fillcolor);
		}
		if (strokecolor != null)
		{
			setItem(Constants.PR_COLOR, strokecolor);
		}
		if (opacityfill != null)
		{
			setItem(Constants.PR_APHLA, opacityfill);
		}
		if (strokestyle >= 0)
		{
			setItem(Constants.PR_SVG_LINE_TYPE, strokestyle);
		}
		if (strokewidth != null)
		{
			setItem(Constants.PR_STROKE_WIDTH, strokewidth);
		}
		if (rotating >= 0)
		{
			setItem(Constants.PR_SVG_ORIENTATION, rotating);
		}
		if (shistyle >= 0)
		{
			setItem(Constants.PR_SVG_ARROW_START_TYPE, shistyle);
		}
		if (zhongstyle >= 0)
		{
			setItem(Constants.PR_SVG_ARROW_END_TYPE, zhongstyle);
		}
		if (height != null)
		{
			setItem(Constants.PR_HEIGHT, height);
		}
		if (width != null)
		{
			setItem(Constants.PR_WIDTH, height);
		}
	}

	public Color getColorEqual(Color color, Map<Integer, Object> map, int key)
	{
		if (color != null && map.containsKey(key))
		{
			Color currentcolor = (Color) map.get(key);
			if (color.equals(currentcolor))
			{
				return color;
			}
		}
		return null;
	}

	public int getIntEqual(int num, Map<Integer, Object> map, int key)
	{
		if (num >= 0 && map.containsKey(key))
		{
			int currentnum = (Integer) map.get(key);
			if (num == currentnum)
			{
				return num;
			}
		}
		return -1;
	}

	public FixedLength getFixedLengthEqual(FixedLength length,
			Map<Integer, Object> map, int key1, int key2, int numberof)
	{
		if (length != null)
		{
			if (key2 > 0)
			{
				if (map.containsKey(key1))
				{
					FixedLength x1 = (FixedLength) map.get(key1);
					FixedLength x2 = (FixedLength) map.get(key2);
					FixedLength currentlength = SVGUtil.getSubAbsLength(x1, x2);
					if (length.equals(currentlength))
					{
						return length;
					}
				}
			} else
			{
				if (map.containsKey(key1))
				{
					if (map.containsKey(key1))
					{
						FixedLength currentlength = SVGUtil.getNumbersOfLength(
								(FixedLength) map.get(key1), 2);
						if (length.equals(currentlength))
						{
							return length;
						}
					}
				}
			}
		}
		return null;
	}

	public static void main(String[] args)
	{
		new SvgAttributesDialog(new ArrayList<CellElement>());
	}

	public static Map<Integer, Object> getAttrmap()
	{
		return attrmap;
	}

	public static void setAttrmap(Map<Integer, Object> attrmap)
	{
		SvgAttributesDialog.attrmap = attrmap;
	}

	public static void setItem(Integer key, Object value)
	{
		Map<Integer, Object> map = getAttrmap();
//		map.remove(key);
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
}
