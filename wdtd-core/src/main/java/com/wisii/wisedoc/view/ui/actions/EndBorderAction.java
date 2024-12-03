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
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

/**
 * 右边框属性设置
 * @author 闫舒寰
 * @version 1.0 2009/02/01
 */
public class EndBorderAction extends Actions {

	@Override
	public void doAction(ActionEvent e) {
		setProperties();
	}
	
	private void setProperties(){
		
		Map<Integer, Object> properties = new HashMap<Integer, Object>();
		
		if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).containsKey(Constants.PR_BORDER_END_WIDTH)) {
			if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_BORDER_END_WIDTH) != null) {
				setNullBorder(properties);
			} else {
				setDefalutBorder(properties);
			}
		}  else {
			setDefalutBorder(properties);
		}
		
		setFOProperties(properties);
	}
	
	private void setDefalutBorder(Map<Integer, Object> properties){
		//设置四个边默认的宽度
		properties.put(Constants.PR_BORDER_END_WIDTH, convertLength(0.5d, "pt"));
		//设置四个边默认的样式
		properties.put(Constants.PR_BORDER_END_STYLE, convertStyle(4));
		//设置四个边默认的颜色
		properties.put(Constants.PR_BORDER_END_COLOR, convertColor());	
	}
	
	private void setNullBorder(Map<Integer, Object> properties){
		//设置四个边默认的宽度
		properties.put(Constants.PR_BORDER_END_WIDTH, null);
		//设置四个边默认的样式
		properties.put(Constants.PR_BORDER_END_STYLE, null);
		//设置四个边默认的颜色
		properties.put(Constants.PR_BORDER_END_COLOR, null);
	}
	
	private CondLengthProperty convertLength(Double widthValue, String measurement){
		FixedLength fixedLength = new FixedLength(widthValue, measurement);
		CondLengthProperty length = new CondLengthProperty(fixedLength, true);
		return length;
	}
	
	private EnumProperty convertStyle(int id){
		
		int styleId = -1;		
		/*
		 * 边框的具体效果，详见XSL规范7.8.20
		 */
		switch (id) {
		//无边框
		case 0:
			styleId = Constants.EN_NONE;
			break;
		//隐藏
		case 1:
			styleId = Constants.EN_HIDDEN;
			break;
		//The border is a series of dots.
		case 2:
			styleId = Constants.EN_DOTTED;
			break;
		//The border is a series of short line segments
		case 3:
			styleId = Constants.EN_DASHED;
			break;
		//The border is a single line segment.
		case 4:
			styleId = Constants.EN_SOLID;
			break;
		//The border is tow solid lines. The sum of the two lines and the space between them equals the values of 'border-width'.
		case 5:
			styleId = Constants.EN_DOUBLE;
			break;
		//The border looks as though it were carved into the canvas.
		case 6:
			styleId = Constants.EN_GROOVE;
			break;
		//The opposite of 'groove': the border looks as though it were coming out of the canvas.
		case 7:
			styleId = Constants.EN_RIDGE;
			break;
		//The border makes the entire box look as though it were embedded in the canvas
		case 8:
			styleId = Constants.EN_INSET;
			break;
		//The opposite of 'inst': the border makes th eentire box look as though it were coming out of the canvas
		case 9:
			styleId = Constants.EN_OUTSET;
			break;
			
		default:
			break;
		}	
		
		EnumProperty ep = new EnumProperty(styleId, "NONE");		
		return ep;
	}
	
	private Color convertColor(){
		return new Color(0,0,0);		
	}

}
