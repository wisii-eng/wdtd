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
package com.wisii.wisedoc.view.ui.actions.paragraph;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JSpinner;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.model.ParagraphPropertyModel;

/**
 * 
 * 设置段落特殊缩进的action，里面包括正常、首行缩进和悬挂缩进
 * 
 * @author	闫舒寰
 * @since	2008/09/26
 *
 */
public class ParagraphSpecialIndentAction extends Actions {
	
	/*
	 * 首行缩进的设置值
	 */
	JSpinner firstLineIndent;
	/*
	 * 段落缩进的设置值
	 */
	JSpinner paragraphIndent;
	/*
	 * 选择单位的下拉菜单
	 */
	JComboBox measurement;
	
	

	
	
	private void getComponents(){
		
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(Paragraph.SPECIAL_INDENT_INPUT_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		List<JSpinner> temp = new ArrayList<JSpinner>();
		
		for (Iterator<List<Object>> iterator2 = comSet.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					this.measurement = (JComboBox) ui;
				}
				
				if (uiComponents instanceof JSpinner) {
					JSpinner ui = (JSpinner) uiComponents;
					temp.add(ui);
					/*if (this.firstLineIndent == null) {
						this.firstLineIndent = ui;
					}
					if (this.firstLineIndent != null && this.paragraphIndent == null) {
						this.paragraphIndent = ui;
					}*/
				}
				
			}
		}
		
		this.firstLineIndent = temp.get(0);
		this.paragraphIndent = temp.get(1);
	}
	
	public void doAction(ActionEvent e) {
		getComponents();
		if (e.getSource() instanceof JComboBox) {
			JComboBox jb = (JComboBox) e.getSource();
			if (true) {
				JSpinner value_1 = (JSpinner) firstLineIndent;
				JSpinner value_2 = (JSpinner) paragraphIndent;
				JComboBox measurement = (JComboBox) this.measurement;
				
				Map<Integer, Object> properties = new HashMap<Integer, Object>();
				
				if (jb.getSelectedIndex() == 0) {
					/*
					 * 默认情况下没有文本缩进和段落缩进
					 */
					properties.put(Constants.PR_TEXT_INDENT, new FixedLength(0d,"pt"));
					properties.put(Constants.PR_START_INDENT, new FixedLength(0d,"pt"));
					ParagraphPropertyModel.setFOProperties(properties);
					
					value_1.setEnabled(false);
					value_2.setEnabled(false);
					measurement.setEnabled(false);
				} else if (jb.getSelectedIndex() == 1){
					/*
					 * 这里的设置参照《WiseDoc2.0结合XSL1.1排版样式》文档的第4.1节段落部分有关缩进的内容，这里
					 * 设置的值应该是以em作为单位，但目前模型层还不支持em，所以暂用pt作为代替
					 */
					properties.put(Constants.PR_TEXT_INDENT, new FixedLength(10d,"pt"));
					properties.put(Constants.PR_START_INDENT, new FixedLength(0d,"pt"));
					ParagraphPropertyModel.setFOProperties(properties);
					
					value_1.setEnabled(true);
					value_2.setEnabled(true);
					measurement.setEnabled(true);
				} else if (jb.getSelectedIndex() == 2){
					/*
					 * 同上
					 */
					properties.put(Constants.PR_TEXT_INDENT, new FixedLength(-10d,"pt"));
					properties.put(Constants.PR_START_INDENT, new FixedLength(10d,"pt"));
					ParagraphPropertyModel.setFOProperties(properties);
					
					value_1.setEnabled(true);
					value_2.setEnabled(true);
					measurement.setEnabled(true);
				}				
			}					
		}		
	}



}
