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
package com.wisii.wisedoc.view.ui.parts;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import com.wisii.wisedoc.view.dialog.PropertyPanelInterface;

/**
 * 属性面板的父类，用于处理一些公共方法
 * @author 闫舒寰
 * @version 1.0 2009/03/19
 */
public abstract class AbstractWisePropertyPanel extends JPanel implements PropertyPanelInterface {
	
	public enum PropertyPanelType {
		NO_BORDER_AND_BACKGROUND;
	}
	
	/**
	 * 用于存放初始化该面板所用的Map
	 */
	private Map<Integer, Object> initialProMap;
	
	/**
	 * 获得当前属性面板的初始值
	 * @return 当前面板的初始Map值
	 */
	public Map<Integer, Object> getInitialProMap() {
		return initialProMap;
	}

	/**
	 * 用于存放当前更改的值
	 */
	private Map<Integer, Object> newProMap;
	
	
	@Override
	public void install(Map<Integer, Object> map) {
		if (map == null) {
			initialProMap = new HashMap<Integer, Object>();
		} else {
			this.initialProMap = map;
		}
		
		newProMap = new HashMap<Integer, Object>();
		
		this.initialPanel();
		
//		this.initialActions();
	}
	
	public void install(Map<Integer, Object> map, PropertyPanelType ppt) {
		if (map == null) {
			initialProMap = new HashMap<Integer, Object>();
		} else {
			this.initialProMap = map;
		}
		
		newProMap = new HashMap<Integer, Object>();
		
		this.initialPanel(ppt);
		
//		this.initialActions();
	}
	
	protected void initialPanel(PropertyPanelType ppt) {};
	
	/**
	 * 所有属性面板必须用该方法初始化面板上的按钮
	 */
	protected abstract void initialPanel();
	
	/**
	 * 专门用来初始化各个属性设置的动作和初始值的
	 */
//	protected abstract void initialActions();

	/**
	 * 通常是用来获得边框和背景的属性的方法。用于在最后设置属性的时候提取用户所设置的边框和背景属性
	 * 若用户没有设置边框和背景属性则返回一个空的Map
	 * 
	 * 若有的面板结构比较复杂，通过取得当前控件的属性来设置属性比较方便则会直接通过这个方法来返回用户所设置的属性
	 */
	protected abstract Map<Integer, Object> getPanelProperties();
	
	@Override
	public Map<Integer, Object> getSetting() {
		//changed map
		Map<Integer, Object> cMap = new HashMap<Integer, Object>();

		newProMap.putAll(getPanelProperties());
		
		Set<Integer> newkey = newProMap.keySet();
		
		for (Integer key : newkey) {
			
			if (initialProMap.containsKey(key)) {
				
				if (newProMap.get(key) == null && initialProMap.get(key) == null) {
					continue;
				}
				
				if (newProMap.get(key) == null && initialProMap.get(key) != null) {
					cMap.put(key, newProMap.get(key));
					continue;
				}
				
				if (!newProMap.get(key).equals(initialProMap.get(key))) {
					cMap.put(key, newProMap.get(key));
				} else {
					continue;
				}
			}
			
			//有特异的属性值为null，也有无意的属性值为null，这个不好区分，所以不对null进行过滤了
//			if (newProMap.get(key) != null) {
				cMap.put(key, newProMap.get(key));
//			}
			
		}
		
		return cMap;
	}
	
	/**
	 * 设置当前属性到该属性面板的Map中
	 * @param propertyId
	 * @param propertyValue
	 */
	protected void setFOProperty(int propertyId, Object propertyValue){
		this.newProMap.put(propertyId, propertyValue);
	}
	
	protected void setFOProperties(Map<Integer, Object> properties){
		this.newProMap.putAll(properties);
	}
	
}
