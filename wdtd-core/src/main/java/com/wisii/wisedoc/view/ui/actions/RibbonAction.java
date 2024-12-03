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

import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeListener;

/**
 * 这个接口应该描述了在RibbonAction中应该实现的几件事情
 * 
 * 按照目前的理解，action中需要做以下几件事情
 * <p>1、设置、取得动作类型
 * <p>2、按照默认值初始化按钮，这个不仅仅用于初始化按钮状态，在平时设置属性的时候也经常需要把按钮设置回初始状态</p>
 * <p>3、执行按钮的动作，（这个已经由Action接口定义了）</p>
 * <p>4、根据用户的点击的对象的属性更新按钮的状态（需要接收属性类型），由{@link UIStateChangeListener}接口来实现，
 * 		 在创建Action的时候，会通过 {@link RibbonUIManager}到{@link RibbonUIModel}注册监听器，当用户点击不同的文字的时候，
 * 		 文字属性会被实时的更新到{@link RibbonUIModel}中，RibbonUIModel发现当前属性和界面有所不同的时候，会通知相应的Action
 * 		 以改变界面的状态。</p>
 * <p>5、根据用户选择的对象设置按钮是否可用</p>
 * <p>6、设置动作的快捷键(key) （这个还需要实际看是否需要在每个Action中设置，还是应该在RibbonUIManager中管理）</p>
 * <p>7、当用户选择不同对象产生不同属性的时候界面的状态。比如用于同时选中了大字号和正常字号的文字，字号应该如何显示（提供控制界面状态的接口）</p>
 * 
 * @author 闫舒寰
 * @version 0.1 2008/11/19
 */
public interface RibbonAction extends UIStateChangeListener {
	
	/*
	 * 和Java的Swing中的Action接口做比较可以发现，Java中Swing的Action接口主要支持以下几种方法
	 * 1、一对	getValue和putValue，这个是用来设置一些Swing小组认为常用的按钮的属性的设置
	 * 2、一对	setEnabled和isEnabled，这个是用来设置按钮是否可用，不过这个需要有一个前提条件就是需要有组件到这个action里面注册propertyChangeListner，
	 * 	  这个消息是通过firePropertyChange发出的
	 * 3、最后就是一对注册PropertyChange的监听器动作了addPropertyChangeListener和remove... 这个是靠和钮约定的一些propertyName来改变按钮的状态的，而我们没有办法
	 *    和钮来约定这样的propertyName，所以需要自己来主动根据不同情况来更改钮的状态
	 * 
	 */
	
	/*
	 * 为了能保证任意组件都能被正确设置，目前是这样假设的：
	 * 1、一个ActionID仅能对应一个Action对象，而不一定是一个Action实体类，所以实际运行的时候是ActionID和Action对象对应，
	 *    比如当涉及到边框的时候，Inline的边框和Block的ActionID会对应一个action类，但是会在new Action的时候赋予不同的ActionType，
	 *    所以这个ActionType很重要应该要保证能有正确的ActionType
	 * 2、
	 *    
	 */
	
	/**
	 * 满足该接口第一条
	 * 设置Action的动作类型，动作类型被定义在了枚举类型ActionType中
	 * @see ActionType
	 */
	public void setActionType(ActionType actionType);
	
	/**
	 * 返回ActionType，发送消息的时候给相同类型的Action发
	 * @return ActionType
	 */
	public ActionType getActionType();

	/**
	 * 满足该接口第二条
	 * 把和该动作绑定的GUI元素初始化
	 */
	public void setDefaultState(ActionCommandType actionCommand);
	
	/**
	 * 用于满足第五条
	 * 判断当前按钮、或菜单不是可用的
	 * 
	 * @return boolean 如果是可用的则返回True，否则返回False
	 */
	public boolean isAvailable();

	
}
