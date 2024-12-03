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
package com.wisii.wisedoc.view.ui.parts.index;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.wisii.wisedoc.view.ui.model.index.IndexStylesModel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 
 * 目录样式列表
 * 
 * @author 闫舒寰
 * @version 1.0 2009/03/26
 */
public class IndexListPanel extends JPanel {
	
    private static JList list;
    
    public static JList getList() {
		return list;
	}

	private DefaultListModel listModel;

    private JTextField spmName;

    public IndexListPanel() {
    	
    	setLayout(new BorderLayout());
        
    	list = new JList ();
        listModel = new DefaultListModel();
        
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        list.setModel (listModel);
        
        for (String name : IndexStylesModel.Instance.getPsName()) {
        	listModel.addElement(name);
		}
        	
        // show list
        JScrollPane scroller = new JScrollPane (list,
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					IndexStylesModel.Instance.UpdateUI();
				}
			}
        	
        });

        spmName = new JTextField(10);

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        
        buttonPane.setLayout(new GridLayout(2, 0));

        JPanel panel = new JPanel();
        panel.add(new JLabel(UiText.INDEX_LIST_TITLE));
        
        add(panel, BorderLayout.NORTH);
        
        add(scroller, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }
}