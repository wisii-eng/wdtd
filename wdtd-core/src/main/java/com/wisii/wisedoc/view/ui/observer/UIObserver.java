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
package com.wisii.wisedoc.view.ui.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;


/**
 * 这个是Singler模式的观察者中的事件发布者
 * 
 * 用Singler的主要原因是目前没有同一管理事件机制，界面相应事件和FO属性事件是分散到两个类设置的，互相不能取得引用
 * 目前只能通过这个全局“变量”来为FO属性事件制作一个统一的的事件监听器。
 * 
 * @author 闫舒寰
 * @version 1.0
 * @since	2008/9/10
 *
 */

public class UIObserver extends Observable{
	
	private volatile static UIObserver uniqueObserver;
	
	private Object property;
	private int property_id;
	private ActionType actionType;
	private Map<Integer, Object> properties;
	
	private UIObserver(){}
	
	/**
	 * 该函数是用户唯一取得SinglerObserver的方法
	 * 
	 * @return SinglerObserver
	 */
	public static UIObserver getInstance(){
		if(uniqueObserver == null){
			//同步区块，这样不同把该方法整个都进行同步化，只在系统还没有初始化SinglerObserver的时候使用同步
			synchronized (UIObserver.class){
				uniqueObserver = new UIObserver();
			}			
		}		
		return uniqueObserver;
	}
	
	
	public void propertyChanged(){
		setChanged();
		notifyObservers();
	}
	
	/**
	 * 当有属性变化的时候，设置属性属性，并通知所有的观察者
	 * 
	 * V1.0的更新为该方法设置了synchronized属性，这样无论是从取得这个singler还是设置属性，都不会有同步的问题了
	 * 
	 * 这里的效率也是一个问题，这个函数的调用代价是非synchronized函数的至少4倍，（因为要检查锁、开锁、上锁等操作）
	 * 
	 * @param property_type	属性类型（inline-level或是block-level等
	 * @param property_id	属性id
	 * @param changedProperty	属性值
	 */
	public synchronized void setProperty(ActionType actionType, int property_id, Object changedProperty){
		this.actionType = actionType;
		this.property = changedProperty;
		this.property_id = property_id;
		/*
		 * 初步计划是这里每500ms向内核通知一次，在这500ms之中会建立一个list，用来记录所有用户的操作，然后生成一个sendList
		 * 如果发现在这500ms中，同一property_id被设置了N多次，则取最后一次进行设置
		 * 如果在这500ms中不止一个property_id被设置，则需要正确安排设置的sendList
		 * 也就是说属性池初步计划在这里实施
		 */
//		System.out.println("属性 id： " + property_id + " 属性类型： " + property_type + " 属性值： " + property);
		this.propertyChanged();
	}
	
	/**
	 * 和setProperty一样，只不过是批量设置设置属性
	 * 
	 * @param property_type 属性类型（inline-level或是block-level等
	 * @param properties	Map形式的一组属性
	 */
	public synchronized void setProperties(ActionType property_type, Map<Integer, Object> properties){
		this.actionType = property_type;
		this.properties = properties;
		
		this.propertyChanged();
	}
	
	/**
	 * 取得object类型的属性值
	 * 
	 * @return 属性值
	 */
	public Object getProperty(){
		return this.property;
	}
	
	/**
	 * 取得属性id
	 * 
	 * @return 属性id
	 */
	public int getPropertyId(){
		return this.property_id;
	}
	
	/**
	 * 取得属性类型，属性类型定义在PropertyTypeConstant类中
	 * 
	 * @return 属性类型
	 */
	public ActionType getPropertyType(){
		return this.actionType;
	}
	
	/**
	 * 取得一组属性
	 * 
	 * @return Map类型的一组属性
	 */
	public Map<Integer, Object>	getProperties(){
		if(properties == null){
			properties = new HashMap<Integer, Object>();
//			System.out.println("property id: " + getPropertyId() + " Property:" + getProperty());
			if(getPropertyId() == 0 && getProperty() == null){
				return null;
			}
			properties.put(this.getPropertyId(), this.getProperty());
			return properties;
		} else {
			return properties;
		}		
	}

}
