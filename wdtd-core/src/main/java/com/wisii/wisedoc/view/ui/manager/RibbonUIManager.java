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
package com.wisii.wisedoc.view.ui.manager;

import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionID;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeListener;

/**
 * 这是用来管理所有的Action和UI组件的一个中心控制类，
 * 界面上的元素可以通过下面的方式来和action绑定
 * 
 * <p> <code>RibbonUIManager.getInstance.bind(ActionID, components....)</code> </p>
 * 
 * <p>上面的ActionID就是在Action中定义好的所有实现ActionID接口的枚举类型，比如{@link Font}、{@link Paragraph};ActionID是识别
 * 动作的唯一标记，用户只要把ActionID和执行这个动作的UI组件绑定到一起就可以了，这个RibbonUIManager会自动根据这个ActionID找到
 * 相应的Action，并做以下几件事情：</p>
 * 
 * <p>1、创建这个Action（addAction方法中调用ActionFactory的getAction），并注册该创建的Action到ActionFactory的ActiveAction中，见{@link ActionFactory}</p>
 * <p>2、初始化这个Action，自动为这个Action初始化正确的ActionType，见{@link ActionType}；</p>
 * <p>3、把这个Action注册到RibbonUIModel中，以便监听用户所操作的属性的变化。</p>
 * <p>4、和快捷键绑定</p>
 * 
 * @author 闫舒寰
 * @version 1.0 2008/11/21
 */
public class RibbonUIManager extends AbstractUIManager {
	
	private static RibbonUIManager uIManager;

	private RibbonUIManager() {}

	/**
	 * 获得该类唯一的实例
	 * @return 唯一的实例
	 */
	public static RibbonUIManager getInstance() {
		if (uIManager == null) {
			synchronized (RibbonUIManager.class){
				uIManager = new RibbonUIManager();
			}
		}
		
		return uIManager;
	}
	
	@Override
	protected void addUIAction(final Enum<? extends ActionID> actionID, final ActionCommandType actionCommand, final Object... components) {
		if(components == null || components.length == 0){
			System.err.println("应该把组件自身放到里面");
		} else {
			addAction(actionID, actionCommand, components);
		}
	}
	
	//一组对象共享一个action，并且每一个组件都和该action绑定，目前不支持往action中传递多个组件的行为
	private void addAction(final Enum<? extends ActionID> actionID, final ActionCommandType actionCommand, final Object... components){

		final Class<? extends Object>[] ownerClass = new Class<?>[components.length];
		final Method[] methods = new Method[components.length];
		
		//创建Action
		final BaseAction action = ActionFactory.getAction(actionID);
		//为该组件初始化
		if(action instanceof Actions){
		((Actions)action).setDefaultState(actionCommand);
		}
		
		for (int i = 0; i < components.length; i++) {
			ownerClass[i] = components[i].getClass();
//			System.out.println(i+ ":" + ownerClass[i]);
		}
		
//		Class<? extends Object> ownerClass = component.getClass();
		
		for (int i = 0; i < ownerClass.length; i++) {
			try {
				methods[i] = ownerClass[i].getMethod("addActionListener",  ActionListener.class);
				methods[i].invoke(components[i], action);
				
			} catch (final SecurityException e) {
//				e.printStackTrace();
			} catch (final NoSuchMethodException e) {
//				e.printStackTrace();
			} catch (final IllegalArgumentException e) {
//				e.printStackTrace();
			} catch (final IllegalAccessException e) {
//				e.printStackTrace();
			} catch (final InvocationTargetException e) {
//				e.printStackTrace();
			}
//			System.out.println("addActionListener");
			
			try {
				methods[i] = ownerClass[i].getMethod("addChangeListener",  ChangeListener.class);
				methods[i].invoke(components[i], action);
//				System.err.println(ownerClass[i]);
			} catch (final SecurityException e) {
//				e.printStackTrace();
			} catch (final NoSuchMethodException e) {
//				e.printStackTrace();
			} catch (final IllegalArgumentException e) {
//				e.printStackTrace();
			} catch (final IllegalAccessException e) {
//				e.printStackTrace();
			} catch (final InvocationTargetException e) {
//				e.printStackTrace();
			}
//			System.out.println("addChangeListener");
		}
	}

	@Override
	public void addUIKeyAccelerate() {
		
	}
	
	@Override
	public void addUIUpdate(final Enum<? extends ActionID> actionID, final ActionCommandType actionCommand, final Object... components) {
		final BaseAction action = ActionFactory.getActiveActions().get(actionID);
//		System.out.println("in addUIUpdate: " + actionCommand);
		//在创建Action的时候，检查如果这个Action可以更新界面则把这个Action添加到UIModel中，以监听属性变化
		if (action instanceof UIStateChangeListener && actionCommand == ActionCommandType.RIBBON_ACTION) {
			final UIStateChangeListener uiChange = (UIStateChangeListener) action;
			RibbonUIModel.RibbonUIModelInstance.addUIStateChangeListener(uiChange);
		}
//		System.out.println("action id: " + actionID);
	}
}
