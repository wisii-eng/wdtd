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
package com.wisii.wisedoc.view.ui.parts.dialogs.psmlist;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel.SPMLayoutType;
import com.wisii.wisedoc.view.ui.parts.dialogs.SimplePageMasterDialog;

/**
 * 单个页布局列表
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class TreePanelSPMList extends JPanel implements ListSelectionListener {
	
private volatile static TreePanelSPMList uniqueInstance;
	
	private TreePanelSPMList(){
		 super(new BorderLayout());
		 initSimplePageMasterList();
		 ListPanel = this;
	}
	
	public static TreePanelSPMList getInstance(){
		if (uniqueInstance == null) {
			synchronized (SimplePageMasterListPanel.class) {
				uniqueInstance = new TreePanelSPMList();
			}
		}
		return uniqueInstance;
	}
	
	private JPanel ListPanel;
	
    private JList list;
    private DefaultListModel listModel;

    private static final String addSPM = "添加单个页布局";
    private static final String delSPM = "删除单个页布局";
    private static final String editSPM = "修改单个页布局";
    
    private JButton delButton;
    private JButton addButton;
    private JButton editButton;
    
    private JTextField spmName;

    private void initSimplePageMasterList() {
    	
        list = new ReorderableJList ();
        listModel = new DefaultListModel();
        list.setModel (listModel);
        	
        // show list
        JScrollPane scroller = new JScrollPane (list,
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        
        addButton = new JButton(addSPM);
        AddListener hireListener = new AddListener(addButton);
        addButton.setActionCommand(addSPM);
        addButton.addActionListener(hireListener);

        delButton = new JButton(delSPM);
        delButton.setActionCommand(delSPM);
        delButton.addActionListener(new FireListener());
        
        
        editButton = new JButton(editSPM);
        editButton.setActionCommand(editSPM);
        editButton.addActionListener(new EditListener());

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        
        buttonPane.setLayout(new GridLayout(3, 0));
        buttonPane.add(delButton);
        buttonPane.add(editButton);
        buttonPane.add(addButton);

        JPanel panel = new JPanel();
        panel.add(new JLabel("单个页布局列表"));
        
        add(panel, BorderLayout.NORTH);
        
        add(scroller, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
        
    }
    
    class DragSourceAction implements DragGestureListener {
		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			// TODO Auto-generated method stub
//			System.err.println("hihi");
		}
    }
    
    class EditListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new SimplePageMasterDialog(SPMLayoutType.editLayout); 
		}
    }

    class FireListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
        	
            int index = list.getSelectedIndex();
            if (index == -1) {
				return;
			}
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                delButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }
    
    public void addSimplePageMasterList(SimplePageMasterModel spmm){
    	
		String name = spmm.getMasterName();
//		System.out.println("in tree panel: " + name);
		
		//当读取文件的时候master-name会为空
		if (name == null) {
			name = "simple-page-master";
		}

		// User didn't type in a unique name...
		if (name.equals("") || listModel.contains(name)) {
			Toolkit.getDefaultToolkit().beep();
			return;
		}

		int index = list.getSelectedIndex(); // get selected index
		if (index == -1) { // no selection, so insert at beginning
			index = 0;
		} else { // add after the selected item
			index++;
		}

		listModel.insertElementAt(spmm.getMasterName(), index);

		// Reset the text field.
		/*
		 * spmName.requestFocusInWindow(); spmName.setText("");
		 */

		// Select the new item and make it visible.
		list.setSelectedIndex(index);
		list.ensureIndexIsVisible(index);
		
		SinglePagelayoutModel.Instance.setDefaultSinglePageLayoutModel();
    }

    //This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;
        
        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
		public void actionPerformed(ActionEvent e) {
			// 页布局对话框
			new SimplePageMasterDialog(SPMLayoutType.addLayout);
		}

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
            //No selection, disable fire button.
                delButton.setEnabled(false);

            } else {
            //Selection, enable the fire button.
                delButton.setEnabled(true);
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new TreePanelSPMList();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	public JList getList() {
		return list;
	}

	public JPanel getListPanel() {
		return ListPanel;
	}
}
