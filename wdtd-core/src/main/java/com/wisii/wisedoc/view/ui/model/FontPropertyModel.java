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
package com.wisii.wisedoc.view.ui.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;

/**
 * 文字部分属性设置模型层，用于临时保存属性对话框设置中用户设置的属性，当用户设置完属性，该临时属性会被清除
 * @author 闫舒寰
 * @version 1.0 2009/03/27
 */
public enum FontPropertyModel {
	FontProperties;
	private static Map<Integer, Object> fontProperties = new HashMap<Integer, Object>();
	private static PropertyChangeSupport support = null;//new PropertyChangeSupport(null);
	
	/**
	 * 供设置属性结束时调用，这个方法会清空该属性集合
	 * @return
	 */
	public static Map<Integer, Object> getFinalProperties() {
		Map<Integer, Object> temp = new HashMap<Integer, Object>();
		temp.putAll(fontProperties);
		fontProperties.clear();
		return temp;
	}
	
	/**
	 * 清空当前设置的属性，当用户点击“取消”按钮的时候调用
	 */
	public static void clearProperty(){
		fontProperties.clear();
	}

	public static void setFOProperties(Map<Integer, Object> fontProperties0) {
		FontPropertyModel.fontProperties.putAll(fontProperties0);
		if(support != null) {
			support.firePropertyChange(new PropertyChangeEvent("value", "value", null, fontProperties));
		}
	}
	
	public static void setFOProperty(int propertyId, Object propertyValue) {
		FontPropertyModel.fontProperties.put(propertyId, propertyValue);
		if(support != null) {
			support.firePropertyChange(new PropertyChangeEvent("value", "value", null, fontProperties));
		}
	}
	
	public static Object getCurrentProperty(int propertyID) {
		
		if (fontProperties.get(propertyID) == null) {
			fontProperties.put(propertyID, RibbonUIModel.getCurrentPropertiesByType().get(ActionType.INLINE).get(propertyID));
		}
		return fontProperties.get(propertyID);
	}
	
	/**
	 * 添加foProperty到
	 * @param fontProperties
	 */
	public static void addFOProperties(Map<Integer, Object> fontProperties){
		FontPropertyModel.fontProperties.putAll(fontProperties);
	}

	public static void addPropertyChangeListener(PropertyChangeListener listener){
		if(support == null) {
			support = new PropertyChangeSupport("value");
		}
		support.addPropertyChangeListener(listener);
	}
	public static void removePropertyChangeListener(PropertyChangeListener listener){
		
		support.removePropertyChangeListener(listener);
	}
}
