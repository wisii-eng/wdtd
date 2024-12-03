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
/*
 * ColorComboBox.java
 *
 * Created on 24 de Fevereiro de 2005, 22:17
 */

package com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.MultiItemComboBox;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 一个类似于Word 2007的颜色下拉菜单，2005年Lopes老兄写的
 * 
 * @author Lopes
 */
public class ColorComboBox extends MultiItemComboBox
{

	public static final int DEFAULT_COLUMN_COUNT = 8;

	public static final int DEFAULT_ROW_COUNT = 5;

	protected ColorItem overItem;

	protected boolean showCustomColorButton;

	// 是否是前景色设置
	protected boolean isforegroundset;

	private Color currentColor = null;

	public ColorComboBox(boolean isforegroundset)
			throws IncompatibleLookAndFeelException
	{
		super();
		this.isforegroundset = isforegroundset;
		overItem = null;
		showCustomColorButton = true;
		setPopupColumnCount(DEFAULT_COLUMN_COUNT);
		setPopupRowCount(DEFAULT_ROW_COUNT);

		popupPanel.addMouseListener(new MouseAdapter()
		{

			public void mousePressed(MouseEvent evt)
			{
				Component comp = popupPanel.getComponentAt(evt.getPoint());

				if (comp != null)
				{
					if (comp instanceof ColorItem)
					{
						overItem = (ColorItem) comp;
						setSelectedItem(overItem.getColor());
						hidePopup();
					}
				}
			}
		});

		popupPanel.addMouseMotionListener(new MouseMotionAdapter()
		{

			public void mouseMoved(MouseEvent evt)
			{
				Component comp = popupPanel.getComponentAt(evt.getPoint());
				// System.out.println(evt.getSource());
				if (comp != null)
				{
					if (comp instanceof ColorItem)
					{
						if (overItem != null)
						{
							overItem.setOver(false);
						}
						overItem = (ColorItem) comp;
						overItem.setOver(true);
						if (selectOnMouseOver)
						{
							setSelectedItem(overItem.getColor());
						}
					}
				}
			}
		});

		setPreferredSize(new Dimension(50, 23));// 【修改】by 李晓光2009-1-6 【30->23】

		JPanel popupComponent = new JPanel();
		popupComponent.setLayout(new BorderLayout());
		popupComponent.add(popupPanel, BorderLayout.NORTH);
		setPopupComponent(popupComponent);
		setRenderer(new ColorComboBoxRenderer());

		// key movement support
		this.addKeyListener(new KeyAdapter()
		{

			public void keyPressed(KeyEvent event)
			{
				processKeyOnPopup(event);
				event.consume();
			}
		});

		populatePopup();
	}

	public ColorComboBox() throws IncompatibleLookAndFeelException
	{
		super();

		overItem = null;
		showCustomColorButton = true;
		setPopupColumnCount(DEFAULT_COLUMN_COUNT);
		setPopupRowCount(DEFAULT_ROW_COUNT);

		popupPanel.addMouseListener(new MouseAdapter()
		{

			public void mousePressed(MouseEvent evt)
			{
				Component comp = popupPanel.getComponentAt(evt.getPoint());

				if (comp != null)
				{
					if (comp instanceof ColorItem)
					{
						overItem = (ColorItem) comp;
						setSelectedItem(overItem.getColor());
						hidePopup();
					}
				}
			}
		});

		popupPanel.addMouseMotionListener(new MouseMotionAdapter()
		{

			public void mouseMoved(MouseEvent evt)
			{
				Component comp = popupPanel.getComponentAt(evt.getPoint());
				// System.out.println(evt.getSource());
				if (comp != null)
				{
					if (comp instanceof ColorItem)
					{
						if (overItem != null)
						{
							overItem.setOver(false);
						}
						overItem = (ColorItem) comp;
						overItem.setOver(true);
						if (selectOnMouseOver)
						{
							setSelectedItem(overItem.getColor());
						}
					}
				}
			}
		});

		setPreferredSize(new Dimension(50, 23));// 【修改】by 李晓光2009-1-6 【30->23】

		JPanel popupComponent = new JPanel();
		popupComponent.setLayout(new BorderLayout());
		popupComponent.add(popupPanel, BorderLayout.NORTH);
		setPopupComponent(popupComponent);
		setRenderer(new ColorComboBoxRenderer());

		// key movement support
		this.addKeyListener(new KeyAdapter()
		{

			public void keyPressed(KeyEvent event)
			{
				processKeyOnPopup(event);
				event.consume();
			}
		});

		populatePopup();
	}

	public boolean getShowCustomColorButton()
	{
		return showCustomColorButton;
	}

	public void setShowCustomColorButton(boolean newValue)
	{
		showCustomColorButton = newValue;
	}

	protected void populatePopup()
	{
		final ColorComboBoxModel model = new ColorComboBoxModel();
		int n = model.getSize();
		final int extraItemIndex = n;
		for (int i = 0; i < n; i++)
		{
			popupPanel.add(new ColorItem((Color) model.getElementAt(i)));
		}

		if (showCustomColorButton)
		{
			JComponent popupComponent = getPopupComponent();
			// 在下拉菜单中添加“重量级”的组件的组件的时候，会有失去焦点这个问题发生，所以这里暂时不能用JButton
			// 若想用重量级的组件，sun的swing小组建议从JCompont下开始继承，即自己重写JComboBox
			// JComboBox的设计就是容纳轻量级的组件，并且只能下拉形式的
			// 经测试发现，即便是设置了setLightWeightPopupEnabled为false，鼠标焦点还是照常丢失
			/*
			 * JButton customColorButton = new
			 * JButton(UiText.FONT_COLOR_BUTTON);
			 * customColorButton.addActionListener(new ActionListener() { public
			 * void actionPerformed(ActionEvent evt) { //
			 * System.out.println(evt.getSource()); Color selectedColor =
			 * (Color) getSelectedItem(); Color choosedColor =
			 * JColorChooser.showDialog(null, UiText.FONT_COLOR_BUTTON,
			 * selectedColor); if (choosedColor != null) { if
			 * (model.getElementAt(extraItemIndex) != null) {
			 * model.removeElementAt(extraItemIndex); }
			 * model.insertElementAt(choosedColor, extraItemIndex);
			 * setSelectedItem(choosedColor); hidePopup(); } } });
			 * popupComponent.add(customColorButton, BorderLayout.WEST);
			 */

			/*
			 * 下面为了结局重量级组件丢失焦点的问题，只添加了JPanel，用JPanel模拟按钮
			 * 目前这个Panel还比较苍白，仅仅是一个Label，以后还要添加图片并且重新整理一下
			 */
			JPanel jp = new JPanel();
			jp.setLayout(new BorderLayout());
			JLabel jl = new JLabel(UiText.FONT_COLOR_BUTTON);
			jp.add(jl, BorderLayout.CENTER);
			jp.setBorder(new BevelBorder(BevelBorder.RAISED));
			final Color original = jp.getBackground();
			;
			jp.addMouseListener(new MouseAdapter()
			{

				// 想用边框来做出按钮的效果，但是实际效果不好
				BevelBorder bevel = new BevelBorder(BevelBorder.RAISED);

				EmptyBorder empty = new EmptyBorder(2, 2, 2, 2);

				public void mouseEntered(MouseEvent e)
				{
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					JPanel jp1 = (JPanel) e.getSource();
					jp1.setBackground(Color.CYAN);
					jp1.setBorder(bevel);
				}

				@Override
				public void mouseExited(MouseEvent e)
				{
					// TODO Auto-generated method stub
					super.mouseExited(e);
					JPanel jp1 = (JPanel) e.getSource();
					jp1.setBackground(original);
					jp1.setBorder(empty);
				}

				public void mousePressed(MouseEvent evt)
				{
					// System.out.println(evt.getSource());
					Color selectedColor = (Color) getSelectedItem();
					/*
					 * if(selectedColor != null) currentColor = selectedColor;
					 */
					Color choosedColor = JColorChooser.showDialog(null,
							UiText.FONT_COLOR_BUTTON, selectedColor);
					if (choosedColor != null)
					{
						if (model.getElementAt(extraItemIndex) != null)
						{
							model.removeElementAt(extraItemIndex);
						}
						model.insertElementAt(choosedColor, extraItemIndex);
						setSelectedItem(choosedColor);
					}
				}
			});
			// 选择颜色“按钮”结束
			popupComponent.add(jp, BorderLayout.SOUTH);
			JPanel northpanel = new JPanel();
			JLabel northlabel = null;
			if (isforegroundset)
			{
				northlabel = new JLabel(UiText.AUTO_COLOR_BUTTON);
			}
			// else
			// {
			// northlabel = new JLabel(UiText.NULL_COLOR_BUTTON);
			// }
			northpanel.setLayout(new BorderLayout());
			if (northlabel != null)
			{
				northpanel.add(northlabel, BorderLayout.CENTER);
			}
			northpanel.setBorder(new BevelBorder(BevelBorder.RAISED));
			northpanel.addMouseListener(new MouseAdapter()
			{

				Color original;

				// 想用边框来做出按钮的效果，但是实际效果不好
				BevelBorder bevel = new BevelBorder(BevelBorder.RAISED);

				EmptyBorder empty = new EmptyBorder(2, 2, 2, 2);

				public void mouseEntered(MouseEvent e)
				{
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					JPanel jp1 = (JPanel) e.getSource();
					original = jp1.getBackground();
					jp1.setBackground(Color.CYAN);
					jp1.setBorder(bevel);
				}

				public void mouseExited(MouseEvent e)
				{
					// TODO Auto-generated method stub
					super.mouseExited(e);
					JPanel jp1 = (JPanel) e.getSource();
					jp1.setBackground(original);
					jp1.setBorder(empty);
				}

				public void mousePressed(MouseEvent evt)
				{
					setSelectedItem(null);
					hidePopup();
				}
			});
			popupComponent.add(jp, BorderLayout.SOUTH);
			popupComponent.add(northpanel, BorderLayout.CENTER);
		}

		setModel(model);
	}

	@Override
	public void setSelectedItem(Object color)
	{
		super.setSelectedItem(color);
		if (color instanceof Color)
		{
			currentColor = (Color) color;
		}
	}

	public void InitValue(Object color)
	{
		super.InitValue(color);
		if (color instanceof Color)
		{
			currentColor = (Color) color;
		}
	}

	protected synchronized void processKeyOnPopup(KeyEvent event)
	{
		int keyCode = event.getKeyCode();
		ColorComboBoxModel model = (ColorComboBoxModel) getModel();

		int selectedIndex;

		if (overItem != null)
		{
			selectedIndex = model.getIndexOf(overItem.getColor());
		} else
		{
			selectedIndex = getSelectedIndex();
		}

		Point coord = convertIndexToXY(selectedIndex);

		switch (keyCode)
		{
			case KeyEvent.VK_UP:
				if (coord.y > 0)
				{
					coord.y--;
				} else
				{
					return;
				}
				break;

			case KeyEvent.VK_DOWN:
				if (coord.y < DEFAULT_ROW_COUNT - 1)
				{
					coord.y++;
				} else
				{
					return;
				}
				break;

			case KeyEvent.VK_LEFT:
				if (coord.x > 0)
				{
					coord.x--;
				} else
				{
					return;
				}
				break;

			case KeyEvent.VK_RIGHT:
				if (coord.x < DEFAULT_COLUMN_COUNT - 1)
				{
					coord.x++;
				} else
				{
					return;
				}
				break;

			case KeyEvent.VK_ENTER:
				if (overItem != null)
				{ // only happends if the user hits enter immediatly after
					// clicking
					// the combobox arrow button (1 key pressed)
					setSelectedItem(overItem.getColor());
				}
				hidePopup();
				break;

			case KeyEvent.VK_ESCAPE:
				hidePopup();
				break;
		}

		int newIndex = convertXYToIndex(coord);

		/*
		 * The first condition checks if an item change was made (not on
		 * boundaries and the second condition checks if there are more items
		 * (in the case we don't have a square of items)
		 */
		if ((newIndex != selectedIndex) && (newIndex < this.getItemCount()))
		{
			if (overItem != null)
			{
				overItem.setOver(false);
			}

			overItem = (ColorItem) popupPanel.getComponent(newIndex);
			overItem.setOver(true);

			if (selectOnKeyPress)
			{
				setSelectedIndex(newIndex);
			}
		}
	}

}
