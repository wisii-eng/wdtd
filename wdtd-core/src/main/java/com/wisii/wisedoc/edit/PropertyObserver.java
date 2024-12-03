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
package com.wisii.wisedoc.edit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.wisii.wisedoc.document.DefaultElement;
import com.wisii.wisedoc.view.ui.observer.UIObserver;

/**
 * 观察者，用来接收并处理用户改变的属性
 * 
 * @author 闫舒寰
 * @since 2008/09/10
 *
 */
public class PropertyObserver implements Observer {
	
	//消息发布者
	Observable observable;
	//需要更新属性的元素
	private List<DefaultElement> de;
	
	/**
	 * 注册观察者到属性发布者上
	 * 
	 * @param observable 属性发布者
	 */
	public PropertyObserver(Observable observable){
		this.observable = observable;
		/*
		 * 清除其他的观察者，同一时间只能有一个观察者，目前是自动完成，以后可以根据需要手动完成，
		 * 可以直接通过以下代码手动删除所有的观察者，详见Observerable的Java API
		 * SinglerObserver.getInstance().deleteObservers();
		 */		
//		observable.deleteObservers();
		observable.addObserver(this);
		
	}

	/**
	 * 注册观察者到属性发布者上，并
	 * 
	 * @param observable
	 * @param defaultElement
	 */
	public PropertyObserver(Observable observable, ArrayList<DefaultElement> defaultElement){
		this.observable = observable;
		observable.addObserver(this);
		this.de = defaultElement;
	}

	
	/**
	 * 当有属性变化的时候，被观察者调用这个方法，详见JDK的Observer的接口
	 */
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o instanceof UIObserver){
			//o里面携带者具体的属性，是由事件源发送过来的
			UIObserver po = (UIObserver) o;
			//读取并设置属性
			this.setProperty(po.getPropertyId(), po.getProperty());	
			
			if(po.getProperty() != null){
				//测试该观察者接收到的属性
				System.out.println("接收到的属性： " + po.getProperty().getClass().toString()
						+ " 值：" + po.getProperty().toString() + " 所属类型：" + po.getPropertyType());	
			}
		}		
	}
	
	public void setELementPorperty(List<DefaultElement> defaultElement){
		this.de = defaultElement;
	}
	
	/**
	 * 把所有选中的属性进行设定属性值
	 * 
	 * @param property_id	属性id
	 * @param property		属性值
	 */
	private void setProperty(int property_id, Object property){
		for (Iterator iterator = de.iterator(); iterator.hasNext();) {
			DefaultElement dfe = (DefaultElement) iterator.next();
			dfe.setAttribute(property_id, property);
		}
	}
}
