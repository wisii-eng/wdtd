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
package com.wisii.wisedoc.swing.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBoxModel;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorItem;
import com.wisii.wisedoc.view.ui.text.UiText;

public class TestUI extends JPanel {

	public TestUI() {
		// TODO Auto-generated constructor stub

		// setLayout(null);
		/*
		 * setSize(640, 480); JComponentComboBox jcb = new JComponentComboBox();
		 * FontEffect fs = new FontEffect(); fs.addMouseListener(new
		 * MouseAdapter() { public void mousePressed(MouseEvent evt) {
		 * System.out.println("in fontset" + evt.getSource()); Component comp =
		 * popupPanel.getComponentAt(evt.getPoint());
		 * 
		 * if (comp != null) { if (comp instanceof ColorItem) { overItem =
		 * (ColorItem) comp; setSelectedItem(overItem.getColor()); hidePopup(); } } }
		 * }); try { jcb.setPopupComponent(fs); } catch
		 * (IncompatibleLookAndFeelException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } add(jcb); addMouseListener(new
		 * MouseAdapter(){ public void mousePressed(MouseEvent e){
		 * System.out.println("in test ui " + e.getSource()); }
		 * 
		 * });
		 */
		
		
		
		JPanel popupPanel = new JPanel();
		popupPanel.setLayout(new GridLayout(8,5));
		final ColorComboBoxModel model = new ColorComboBoxModel();
		int n = model.getSize();
		final int extraItemIndex = n;
		for (int i = 0; i < n; i++) {
			popupPanel.add(new ColorItem((Color) model.getElementAt(i)));
		}
		
//		JComponent popupComponent = getPopupComponent();
        JButton customColorButton = new JButton(UiText.FONT_COLOR_BUTTON);
        customColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println(evt.getSource());
               /* Color selectedColor = (Color) getSelectedItem();
                Color choosedColor = JColorChooser.showDialog(null, UiText.FONT_COLOR_BUTTON, selectedColor);*/
               /* if (choosedColor != null) {     
                    if (model.getElementAt(extraItemIndex) != null) {
                        model.removeElementAt(extraItemIndex);
                    }
                    model.insertElementAt(choosedColor, extraItemIndex);                        
                    setSelectedItem(choosedColor);
                    hidePopup();
                }*/
            }
        });
		
		
		/*add(popupPanel, BorderLayout.CENTER);
		add(customColorButton, BorderLayout.SOUTH);*/
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(popupPanel, BorderLayout.CENTER);
        jp.add(customColorButton, BorderLayout.SOUTH);
        
        /*JComponentComboBox jcb = new JComponentComboBox();
        try {
			jcb.setPopupComponent(jp);
		} catch (IncompatibleLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Object[] o = {popupPanel, customColorButton};*/
        
        String[] petStrings = {"Bird", "Cat", "Dog", "Rabbit", "Pig"};       
        Integer[] intArray = new Integer[petStrings.length];
        
        JComboBox jcb = new JComboBox(petStrings);
//        jcb.add(jp);
//        jcb.add(customColorButton);
        MyCellRenderer mcr = new MyCellRenderer();
        jcb.setRenderer(mcr);
		add(jcb);
		// System.out.println(this);
	}
	
	class MyCellRenderer extends JLabel implements ListCellRenderer {
	     public MyCellRenderer() {
	         setOpaque(true);
	     }

	     public Component getListCellRendererComponent(JList list,
	                                                   Object value,
	                                                   int index,
	                                                   boolean isSelected,
	                                                   boolean cellHasFocus) {

	         setText(value.toString());

	         Color background;
	         Color foreground;

	         // check if this cell represents the current DnD drop location
	         /*JList.DropLocation dropLocation = list.getDropLocation();
	         if (dropLocation != null
	                 && !dropLocation.isInsert()
	                 && dropLocation.getIndex() == index) {

	             background = Color.BLUE;
	             foreground = Color.WHITE;

	         // check if this cell is selected
	         } else if (isSelected) {
	             background = Color.RED;
	             foreground = Color.WHITE;

	         // unselected, and not the DnD drop location
	         } else {
	             background = Color.WHITE;
	             foreground = Color.BLACK;
	         };*/

	         /*setBackground(background);
	         setForeground(foreground);*/
	         
	         JButton jb = new JButton("hihi");
	         setBackground(Color.red);
	         

	         return this;
	     }
	 }

	/**
	 * 测试用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame fr = new JFrame("测试用");
		fr.setPreferredSize(new Dimension(800, 600));

		TestUI tui = new TestUI();

		fr.add(tui);
		// System.out.println(fr.getComponent(0).getPreferredSize());
		fr.setSize(fr.getComponent(0).getPreferredSize());
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
	}

}
