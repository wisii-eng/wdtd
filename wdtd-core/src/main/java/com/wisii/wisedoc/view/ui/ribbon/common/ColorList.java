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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.flamingo.common.popup.PopupPanelManager;

import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBoxModel;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorItem;

public class ColorList extends JPanel {
	
	ColorList colorList;
	
    public static final int DEFAULT_COLUMN_COUNT = 8;
    public static final int DEFAULT_ROW_COUNT = 5;
    
    protected ColorItem overItem;
    protected boolean showCustomColorButton;
    protected boolean selectOnMouseOver;
    
    JButton customColorButton;
	
	public ColorList() {
		colorList = this;
		selectOnMouseOver = false;
		this.setLayout(new GridLayout(5,8,0,0));
		this.setPreferredSize(new Dimension(50, 30));
		
		final ColorComboBoxModel model = new ColorComboBoxModel();
        int n = model.getSize();
        
        for (int i=0; i<n; i++) {
        	colorList.add(new ColorItem((Color) model.getElementAt(i)));
        }
		
		
		this.addMouseListener(new MousePressAction());
		this.addMouseMotionListener(new MouseMotionAction());
		/* // key movement support
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                processKeyOnPopup(event);
                event.consume();
            }
        });*/
	}
	
	private class MousePressAction extends MouseAdapter{
		
		 @Override
		 public void mousePressed(MouseEvent evt) {
             Component comp = colorList.getComponentAt(evt.getPoint());
             if (comp != null) {
                 if (comp instanceof ColorItem) {
                     overItem = (ColorItem) comp;
//                     setSelectedItem(overItem.getColor());                       
//                     System.out.println("hihi");
                     PopupPanelManager.defaultManager().hideLastPopup();                  

                 }
             }
         }		
	}
	
	private class MouseMotionAction extends MouseMotionAdapter{

		@Override
		public void mouseMoved(MouseEvent e) {
			super.mouseMoved(e);
			Component comp = colorList.getComponentAt(e.getPoint());
//            System.out.println("xxx");
            if (comp != null) {
                if (comp instanceof ColorItem) {
                    if (overItem != null) {
                        overItem.setOver(false);
                    }
                    overItem = (ColorItem) comp;
                    overItem.setOver(true);
                    if (selectOnMouseOver) {
//                        setSelectedItem(overItem.getColor());
                    }
                }
            }
		}
		
	}
	
	
	
	/**
	 * 测试用
	 * 
	 * @param args
	 */
	public static void createAndShowGUI() {
		JFrame tr = new JFrame();
		tr.add(new ColorList());
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();

		tr.setPreferredSize(new Dimension(r.width * 4 / 5, r.height / 2));
		tr.pack();
		tr.setLocation(r.x, r.y);
		tr.setVisible(true);
		tr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}
	

}
