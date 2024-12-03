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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.actions.MeasureConversion;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;


/**
 * 设置边框宽度值，这个应该能一次设定四个边框宽度
 * 
 * @author	闫舒寰
 * @since	2008/09/27
 *
 */
public class ParagraphBorderWidthAction extends Actions implements ChangeListener{

	/**
	 * 四个边框一个一个设会有问题呀，暂时不能实现，等待研究结果
	 * 
	 */
	
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
		
		setBorderProperty();
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		setBorderProperty();
	}
	
	
	private void setBorderProperty(){
		
		FixedLength fixedLength = new FixedLength((Double)((JSpinner) value).getValue(),
				new MeasureConversion().getIndexConversion(((JComboBox) measurement).getSelectedIndex()));
		CondLengthProperty length = new CondLengthProperty(fixedLength, true);
		
		//设置四个边的长度
		/*setFOProperty(propertyType, Constants.PR_BORDER_BEFORE_WIDTH, length);
		setFOProperty(propertyType, Constants.PR_BORDER_AFTER_WIDTH, length);
		setFOProperty(propertyType, Constants.PR_BORDER_START_WIDTH, length);
		setFOProperty(propertyType, Constants.PR_BORDER_END_WIDTH, length);*/
		
		Map<Integer, Object> properties = new HashMap<Integer, Object>();
		properties.put(Constants.PR_BORDER_BEFORE_WIDTH, length);
		properties.put(Constants.PR_BORDER_AFTER_WIDTH, length);
		properties.put(Constants.PR_BORDER_START_WIDTH, length);
		properties.put(Constants.PR_BORDER_END_WIDTH, length);
		setFOProperties(properties);
		
		/*//设置四个边的样式
		setFOProperty(propertyType, Constants.PR_BORDER_BEFORE_STYLE, Constants.EN_SOLID);
		setFOProperty(propertyType, Constants.PR_BORDER_AFTER_STYLE, Constants.EN_SOLID);
		setFOProperty(propertyType, Constants.PR_BORDER_START_STYLE, Constants.EN_SOLID);
		setFOProperty(propertyType, Constants.PR_BORDER_END_STYLE, Constants.EN_SOLID);
		
		//设置四个边的颜色
		setFOProperty(propertyType, Constants.PR_BORDER_BEFORE_COLOR, Color.black);
		setFOProperty(propertyType, Constants.PR_BORDER_AFTER_COLOR, Color.black);
		setFOProperty(propertyType, Constants.PR_BORDER_START_COLOR, Color.black);
		setFOProperty(propertyType, Constants.PR_BORDER_END_COLOR, Color.black);*/
	}

}
