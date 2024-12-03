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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JSpinner;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.actions.MeasureConversion;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;

/**
 * 
 * 设置行间距的功能
 * 
 * @author	闫舒寰
 * @since	2008/09/27
 *
 */
public class ParagraphLineSpaceAction extends Actions {

	JSpinner value;
	JComboBox measurement;
	
	private void getComponents(){
		
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(Paragraph.LINE_SPACE_INPUT_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		for (Iterator<List<Object>> iterator2 = comSet.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					this.measurement = ui;
				}
				
				if (uiComponents instanceof JSpinner) {
					JSpinner ui = (JSpinner) uiComponents;
					this.value = ui;
				}
				
			}
		}
	}

	@Override
	public void doAction(ActionEvent e) {
		 getComponents();
		/**
		 * 目前还设不了em值，所以还不能具体的实现以下功能
		 * 
		 */
		if (e.getSource() instanceof JComboBox) {
			
			JComboBox jb = (JComboBox) e.getSource();
			
			if(jb.getSelectedIndex() == 0){
				//单倍行距
				value.setEnabled(false);
				measurement.setEnabled(false);				
			} else if(jb.getSelectedIndex() == 1) {
				//1.5倍行距
				value.setEnabled(false);
				measurement.setEnabled(false);
			} else if(jb.getSelectedIndex() == 2) {
				//2倍行距
				
				/*FixedLength length = new FixedLength((Integer)((JSpinner) value).getValue(),
						MeasureConversion.getIndexConversion(((JComboBox) measurement).getSelectedIndex()));*/
				LengthProperty length = new PercentLength(2d, new LengthBase(LengthBase.FONTSIZE));
				EnumProperty conditionality = new EnumProperty(Constants.EN_DISCARD,"");
				EnumNumber precedence = new EnumNumber(-1,0);
				SpaceProperty space = new SpaceProperty(length,precedence,conditionality);	
				
				setFOProperty(Constants.PR_LINE_HEIGHT, space);
				value.setEnabled(false);
				measurement.setEnabled(false);
			} else if(jb.getSelectedIndex() == 3) {
				//最小值
				value.setEnabled(false);
				measurement.setEnabled(false);
			} else if(jb.getSelectedIndex() == 4) {
				//固定值
				value.setEnabled(true);
				measurement.setEnabled(true);
			} else if(jb.getSelectedIndex() == 5) {
				//多倍行距
				value.setEnabled(true);
				measurement.setEnabled(true);
			}
		}
	}

	
	private void setPorperty(){
		
		
		FixedLength length = new FixedLength((Integer)((JSpinner) value).getValue(),
				MeasureConversion.getIndexConversion(((JComboBox) measurement).getSelectedIndex()));
		EnumProperty conditionality = new EnumProperty(Constants.EN_DISCARD,"");
		EnumNumber precedence = new EnumNumber(-1,0);
		SpaceProperty space = new SpaceProperty(length,precedence,conditionality);		
		
		// 设置正确的属性类型和属性值
		setFOProperty(Constants.PR_LINE_HEIGHT, space);
	}

}
