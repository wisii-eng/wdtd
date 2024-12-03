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
package com.wisii.wisedoc.view.ui.actions.pagelayout;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.JCheckBox;

public class PageShowHeaderAction extends DefaultSimplePageMasterActions {

	@Override
	public void doAction(ActionEvent e) {
		
		if (e.getSource() instanceof JCheckBox) {
			JCheckBox showHeader = (JCheckBox) e.getSource();
			if(showHeader.isSelected()){
				//显示页眉设置菜单
				for (Iterator iterator = this.uiComponents.iterator(); iterator.hasNext();) {
					Component component = (Component) iterator.next();
					component.setEnabled(true);
				}
				
				
//				setFOProperty(Constants.PR_SIMPLE_PAGE_MASTER, createDefaultHeader());
				
				
			} else {
				Iterator comIterator = uiComponents.iterator();
				comIterator.next();
				while (comIterator.hasNext()) {
					Component component = (Component) comIterator.next();
					component.setEnabled(false);
				}
				
//				setFOProperty(Constants.PR_SIMPLE_PAGE_MASTER, cancelHeader());
			}
		}
		
		
		
	}

}
