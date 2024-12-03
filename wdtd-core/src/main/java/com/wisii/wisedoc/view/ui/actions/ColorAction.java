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
package com.wisii.wisedoc.view.ui.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JColorChooser;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

public class ColorAction extends Actions {	

	
	
	public void doAction(ActionEvent e) {
		//颜色设置窗口			
		Color selectedColor = JColorChooser.showDialog(null, 
				MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_COLOR_SET),Color.black);
		if(selectedColor != null){
//			SinglerObserver.getInstance().setProperty(propertyType, Constants.PR_COLOR, selectedColor);
			setFOProperty(Constants.PR_COLOR, selectedColor);
		}	

	}

	@Override
	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void uiStateChange(UIStateChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	
}
