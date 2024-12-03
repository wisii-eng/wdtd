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
package com.wisii.wisedoc.view.ui.parts.dialogs.psmtree;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.wisii.wisedoc.view.ui.parts.dialogs.psmlist.ReorderableJList;

public enum SimplePageMasterListListener implements ListSelectionListener {

	SPMLL;
	
	private static JLabel labelUI;
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		System.out.println(labelUI);
		
		if (e.getSource() instanceof ReorderableJList) {
			ReorderableJList ui = (ReorderableJList) e.getSource();
			if (labelUI == null) {
				return;
			}
			if (ui.getSelectedValue() == null) {
				labelUI.setForeground(Color.red);
				labelUI.setText("you must choose a master name");
			} else {
				labelUI.setForeground(Color.black);
				labelUI.setText("master name: " + (String)ui.getSelectedValue());
			}
//			System.out.println(ui.getSelectedValue());
		}
	}



	public static void setLabelUI(JLabel labelUI) {
//		System.err.println(labelUI);
		SimplePageMasterListListener.labelUI = labelUI;
	}
	

}
