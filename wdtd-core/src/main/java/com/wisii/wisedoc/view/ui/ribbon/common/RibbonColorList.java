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
package com.wisii.wisedoc.view.ui.ribbon.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

import org.jvnet.flamingo.common.popup.PopupPanelManager;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.MultiItemComboBox;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBoxModel;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBoxRenderer;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorItem;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 专门为Ribbon界面设计的颜色选择器
 * 
 * @author	闫舒寰
 * @version 0.1 2008/10/22
 */
public class RibbonColorList extends MultiItemComboBox {


    public static final int DEFAULT_COLUMN_COUNT = 8;
    public static final int DEFAULT_ROW_COUNT = 5;
    
    protected ColorItem overItem;
//    protected boolean showCustomColorButton;
    
    JButton customColorButton;
//    文本的自动颜色按钮
    JButton autoColorButton;
//  文本的无颜色按钮
	JButton nullColorButton;
    public JPanel popupComponent;
    
	public RibbonColorList() throws IncompatibleLookAndFeelException {
		super();
        
        overItem = null;
        setPopupColumnCount(DEFAULT_COLUMN_COUNT);
        setPopupRowCount(DEFAULT_ROW_COUNT);

        popupPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                Component comp = popupPanel.getComponentAt(evt.getPoint());
//                System.out.println("in color: " + evt);
                if (comp != null) {
                    if (comp instanceof ColorItem) {
                        overItem = (ColorItem) comp;
                        setSelectedItem(overItem.getColor());
                        
                        PopupPanelManager.defaultManager().hideLastPopup();                           

                    }
                }
            }
		});
        
        popupPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent evt) {
                Component comp = popupPanel.getComponentAt(evt.getPoint());
//                System.out.println(evt.getSource());
                if (comp != null) {
                    if (comp instanceof ColorItem) {
                        if (overItem != null) {
                            overItem.setOver(false);
                        }
                        overItem = (ColorItem) comp;
                        overItem.setOver(true);
                        if (selectOnMouseOver) {
                            setSelectedItem(overItem.getColor());
                        }
                    }
                }
            }            
        });
       
        setPreferredSize(new Dimension(50, 30));
        
        popupComponent = new JPanel();
        popupComponent.setLayout(new BorderLayout());
        popupComponent.add(popupPanel, BorderLayout.CENTER);
		setPopupComponent(popupComponent);
        setRenderer(new ColorComboBoxRenderer());

        
        // key movement support
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                processKeyOnPopup(event);
                event.consume();
            }
        });
        
        
        populatePopup();        
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
		// 在下拉菜单中添加“重量级”的组件的组件的时候，会有失去焦点这个问题发生，所以这里暂时不能用JButton
		// 若想用重量级的组件，sun的swing小组建议从JCompont下开始继承，即自己重写JComboBox
		// JComboBox的设计就是容纳轻量级的组件，并且只能下拉形式的
		// 经测试发现，即便是设置了setLightWeightPopupEnabled为false，鼠标焦点还是照常丢失
		customColorButton = new JButton(UiText.FONT_COLOR_BUTTON, MediaResource
				.getResizableIcon("00417.ico"));
		customColorButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				// System.out.println(evt.getSource());
				Color selectedColor = (Color) getSelectedItem();
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
					// hidePopup();
				}
			}
		});
		autoColorButton = new JButton(UiText.AUTO_COLOR_BUTTON);
		autoColorButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
			
					setSelectedItem(null);
					  PopupPanelManager.defaultManager().hideLastPopup();    
					// hidePopup();
			}
		});
		nullColorButton = new JButton(UiText.NULL_COLOR_BUTTON);
		nullColorButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				setSelectedItem(null);
				PopupPanelManager.defaultManager().hideLastPopup();    
				// hidePopup();
			}
		});
		customColorButton.setPreferredSize(new Dimension(50,22));
		nullColorButton.setPreferredSize(new Dimension(50,22));
		nullColorButton.setPreferredSize(new Dimension(50,22));
		setModel(model);
	}
    
    
    protected synchronized void processKeyOnPopup(KeyEvent event) {
        int keyCode = event.getKeyCode();
        ColorComboBoxModel model = (ColorComboBoxModel) getModel();
        
        int selectedIndex;
        
        if (overItem != null) {
            selectedIndex = model.getIndexOf(overItem.getColor());
        } else {
            selectedIndex = getSelectedIndex();
        }
        
        Point coord = convertIndexToXY(selectedIndex);
        
        switch (keyCode) {
            case KeyEvent.VK_UP: 
                if (coord.y > 0) {
                    coord.y--;
                } else {
                    return;
                }
                break;
                
            case KeyEvent.VK_DOWN:
                if (coord.y < DEFAULT_ROW_COUNT-1) {
                    coord.y++;
                } else {
                    return;
                }                
                break;
                
            case KeyEvent.VK_LEFT:
                if (coord.x > 0) {
                    coord.x--;
                } else {
                    return;
                }                    
                break;                
                
            case KeyEvent.VK_RIGHT:
                if (coord.x < DEFAULT_COLUMN_COUNT-1) {
                    coord.x++;                
                } else {
                    return;
                }                  
                break;
            
            case KeyEvent.VK_ENTER:
                if (overItem != null) { // only happends if the user hits enter immediatly after clicking the combobox arrow button (1 key pressed)
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
         * The first condition checks if an item change was made (not on boundaries and
         * the second condition checks if there are more items (in the case we don't have a square of items)
         */
        if ((newIndex != selectedIndex) && (newIndex < this.getItemCount())) { 
            if (overItem != null) {
                overItem.setOver(false);
            }
                        
            overItem = (ColorItem) popupPanel.getComponent(newIndex);
            overItem.setOver(true);

            if (selectOnKeyPress) {
                setSelectedIndex(newIndex);
            }
        }
    }

	public JButton getCustomColorButton() {
		
		return customColorButton;
	}

	public JPanel getPopupComponent() {
		return popupComponent;
	}   
	   /**
	 * @返回  autoColorButton变量的值
	 */
	public JButton getAutoColorButton()
	{
		return autoColorButton;
	}


	/**
	 * @返回  nullColorButton变量的值
	 */
	public JButton getNullColorButton()
	{
		return nullColorButton;
	}
}
