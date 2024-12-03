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
package com.wisii.wisedoc.view.ui.actions.text;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 文字缩放的类，目前FOP0.94还没有实现这个效果
 * 
 * @author	闫舒寰
 * @since	2008/09/26
 */
public class FontStretchAction extends Actions {


	
	
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox jb = (JComboBox) e.getSource();
			/*
			 * 目前这个效果FOP0.94没有实现
			 */
			if (jb.getSelectedIndex() == 0) {
				// 正常情况下
				setFOProperty(Constants.PR_FONT_STRETCH, Constants.EN_NORMAL);

			} else if (jb.getSelectedIndex() == 1) {
				// 加宽
				setFOProperty(Constants.PR_FONT_STRETCH, Constants.EN_WIDER);
				
			} else if (jb.getSelectedIndex() == 2) {
				// 紧缩
				setFOProperty(Constants.PR_FONT_STRETCH, Constants.EN_NARROWER);
			}

		}
	}

}
