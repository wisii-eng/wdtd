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

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.manager.WiseDocThreadService;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionID;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.observer.UIObserver;

/**
 * 所有界面操作的父类，准确的说是所有涉及到往模型里面设置属性的操作的父类
 * 这里适合用一般的监听器的情况下，这个类仅仅实现了ActionListener这个接口，能用于大多数对象
 * 但是不适用于JSpinner这样的组件。
 * 这里支持控件组合。
 * 
 * 基本上每个子类都需要设置以下几点
 * 1、该动作所执行的动作  (doAction)
 * 2、通过用户的选择操作更新UI（需要通过property_id来确定）(uiStateChange)
 * 3、设置默认状态	(setDefaultState)
 * 4、设置属性不同的时候的界面的状态（需要通过property_id来确定）	(setDifferentPropertyState)
 * 5、设置什么情况下该UI可用或者不可用	(isAvailable)
 * 
 * 
 * @author 闫舒寰
 * @version 1.5 2008/11/24
 *
 */
public abstract class Actions extends BaseAction implements RibbonAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4685910457875841392L;
	
	/**
	 * 处理输入，屏蔽不合适的输入，Action中需要有的但不应该是public的
	 * 应该是内部处理好的
	 */

	/**
	 * <p>6、向model设置属性，这里需要调用{@link FOProperty}中的setFOProperty方法</p>
	 * 可以由该抽象类提供一个方便的方法，子类可以直接调用下面的方法设置FO属性，而不必操心其他
	 * 
	 * 用于满足第六条，向model中设置属性
	 * 这个是单条属性设置，这里不需要提供propertyID的类别，因为该对象初始化的时候已经包含类别了
	 * @param propertyId	{@link Constants}中PR_开头的属性类型
	 * @param propertyValue	FOV中所定义的具体的值的类型
	 * public void setFOProperty(int propertyId, Object propertyValue);
	 * 
	 * 用于满足第六条，向model中设置一组属性
	 * 这个是一组属性的设置，用于设置多余一个属性的属性集合到model中
	 * @param properties
	 * public void setFOProperties(Map<Integer, Object> properties);
	 */

	//该Action的类别
	protected ActionType actionType;
	
	//该action所对应的id，由创建action的时候自动获得
	protected Enum<? extends ActionID> actionID;
	
	//该action的命令，用于区分不同情况下所设定的action
	protected String actionCommand;
	
	/**
	 * 这两个对象是为了方便动作对界面元素的访问，可以通过getUIComponent和getUIComponents方法来获得
	 * 或该界面元素。如果action中需要对其他界面元素进行操作则需要保存自己的界面元素list。这里仅仅是
	 * 为了最常用的界面操作方式保存该变量
	 */
	//该Action中所操作的界面对象
	protected Object uiComponent;
	//该Action中所操作的一组界面对象
	protected List<Object> uiComponents;

	@Override
	public void setActionType(final ActionType actionType) {
		this.actionType = actionType;
	}
	
	public ActionType getActionType() {
		return this.actionType;
	}

	
	//子类所需要实现的方法
	//用于满足第二条：2、执行按钮的动作
	@Override
	public abstract void doAction(ActionEvent e);
	
	/**
	 * 根据用户选择改变按钮状态，这里是一个统一的刷新属性的方法，子类要想刷新界面状态要用uiStateChange这个方法
	 * @see com.wisii.wisedoc.view.ui.model.UIStateChangeListener#stateChange(com.wisii.wisedoc.view.ui.model.UIStateChangeEvent)
	 */
	@Override
	public final void stateChange(final UIStateChangeEvent evt) {
		
		
		/*if (evt.hasActionType(this.actionType)) {
			if(propertyKey == RibbonUIModel.DEFAULT_PROPERTY_ID){
				//如果RibbonUIModel.DEFAULT_PROPERTY_ID则直接把当前Action设置成默认状态
				this.setDefaultState(ActionCommandType.RIBBON_ACTION);
			} else if (value == Constants.NULLOBJECT){
				//如果新值是Constants.NULLOBJECT则代表用户选择到了不同的属性，则需要刷新界面到differentPropertyState
				this.setDifferentPropertyState(evt);
			} else {
				this.uiStateChange(evt);
			}
		}*/
		if (evt.hasActionType(this.actionType)) {
			
			this.uiEvt = evt;
			
			initUiComponent();
			
//			System.out.println("change property" + evt.getChangeProperties());
			
			//设置默认值见下面的hasPropertyKey方法
			if (evt.getChangeProperties() == RibbonUIModel.DEFAULT_PROPERTY) {
				this.setDefaultState(ActionCommandType.RIBBON_ACTION);
				return;
			}
			this.uiStateChange(evt);
		}
	}
	
	//界面元素改变事件
	private UIStateChangeEvent uiEvt;
	
	/**
	 * 这里用来判断是否有该属性的key，
	 * TODO 这里仅仅用来返回true或者false会是个问题，比如当选择多个值返回的时候，当遇到DifferentProeprtyState的时候会有问题，
	 * 因为这里对differentState进行了设置，但是还是返回了false，到子类的时候有可能又调用了setDefaultState方法。
	 * @param propertyKey
	 * @return
	 */
	protected boolean hasPropertyKey(final int propertyKey) {
		
		if (uiEvt != null) {
			final Object temp = uiEvt.getChangeProperties().get(propertyKey);
			
			//用于处理默认值为空的情况
			if (uiEvt.getChangeProperties().containsKey(propertyKey) && temp == null) {
				return uiEvt.hasPropertyKey(propertyKey);
			}
			
			//TODO 如果有这个值但是等于空，目前是当作没有这个id处理
			//当用户点击barcode，读取barcode属性，之后再点图片时，barcode中会报空指针错误，
			//其原因是barcode和图片属于一类，点图片时会清空barcode的属性，但是barcode的id会被留下来
			//目前暂时这么处理，应该约定一下空值的处理方式
			if (temp == null&&actionType != ActionType.Edit) {
//				this.setDefaultState(null);
				return false;
			}
			
			if (temp == Constants.NULLOBJECT) {
				this.setDifferentPropertyState(uiEvt);
				return false;
			}
		}
		
		return uiEvt.hasPropertyKey(propertyKey);
	}
	
	
	/**
	 * 设置按钮默认状态，有的可能要不需要设置默认状态
	 * <p>这个主要是针对当用户选择了某些区域时，这些区域返回的这一类的属性是null，或者属性个数为0的时候
	 * 这个方法类似于设置界面的“原始状态”。</p>
	 * <p>通常来说这个属性是当第一次点击的时候会调用一次，以后一般不会再被调用</p>
	 * 
	 * <p>这个主要用来做<b>界面的初始化工作<b/>，当界面第一创建时所应有的初始值，把本该在
	 * panel中初始化界面的工作放到action中来完成</p>
	 * 
	 * <p>对话框界面初始化需要用这个</p>
	 * 
	 * <p>目前如果读不到属性的时候会调用这个</p>
	 * 
	 * <p>这里面做了控件的初始化动作</p>
	 */
	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		initUiComponent();
	}
	
	@Override
	public boolean isAvailable() {
		return super.isAvailable();
	}
	
	/**
	 * 界面元素的属性值发生了变化，子类通过重写这个方法类实现更新界面的属性
	 */
	protected void uiStateChange(final UIStateChangeEvent evt) {}
	
	/**
	 * 当用户选择不同属性的时候所需要显示的界面，比如当用户选了两种字体的问题，字体框就应该显示空
	 * 
	 */
	protected void setDifferentPropertyState(final UIStateChangeEvent evt) {
		initUiComponent();
	}
	
	/**
	 * 获得单个和该action绑定的对象，这个方法会自动把得到的单个界面元素赋值到uiComponent变量中，同时也会返回该uiComponent主要是面向Ribbon界面
	 * @return 单个和该action绑定的对象
	 */
	protected  void initUiComponent()
	{

		if (uiComponent == null&&uiComponents==null)
		{
			final Map<ActionCommandType, Set<List<Object>>> uiComponentSet = RibbonUIManager
					.getUIComponents().get(this.getActionID());
			if (uiComponentSet != null)
			{
				if (uiComponentSet.get(ActionCommandType.RIBBON_ACTION) != null)
				{
					// 有的功能RibbonUI上没有，这时uiComponent就需要等于空了
					for (final List<Object> uiComponentList : uiComponentSet
							.get(ActionCommandType.RIBBON_ACTION))
					{
						if (uiComponentList.size() == 1)
						{
							this.uiComponent = uiComponentList.get(0);
						} else
						{
							uiComponents = uiComponentList;
						}
					}
				}
			}
		}
	}
	
	
	//==========以下用于满足 5、向model设置属性================///
	protected Map<Integer, Object> properties;
	
	/**
	 * 传递属性值到用户所选择的FO格式化对象中
	 * @param propertyId
	 * @param propertyValue
	 */
	protected void setFOProperty(final int propertyId, final Object propertyValue){
		if(actionType == null){
			System.err.print("do not have property type ");
		}
		if(properties == null){
			properties = new HashMap<Integer, Object>();
		}
		
		//特殊照顾simple page master和page sequence master，它们两个不能同时设置
		if (propertyId == Constants.PR_SIMPLE_PAGE_MASTER) {
			properties.put(Constants.PR_PAGE_SEQUENCE_MASTER, null);
		}
		if (propertyId == Constants.PR_PAGE_SEQUENCE_MASTER) {
			properties.put(Constants.PR_SIMPLE_PAGE_MASTER, null);
		}
		
		properties.put(propertyId, propertyValue);
		doSetFOProperties();
	}

	//应该不用重新赋propertyType的值了，初始化Actionde时候已经赋上了
	protected void setFOProperties(final Map<Integer, Object> properties){
		this.properties = properties;
		doSetFOProperties();
	}
	
	/*
	 * 采用多线程的模式来设置属性，加快界面反应
	 * 但目前是一个操作新建一个Thread，随着用户操作的增多会不停的新建Thread，比较费资源，还需要进一步改进
	 * 但是从debug中观察，这个新的线程会在这个设置完之后第一时间被回收，所以这里仅仅是在不停的重复新建线程
	 * 的过程。
	 * 
	 * 在对左右缩进的JSpinner进行操作的情况下，用户有可能会点的很快，而后面样式没有刷新完毕，所以会积累一些
	 * 没有完成的线程，不过这个也不会影响使用。并且当用户狂点的时候，并不费内存，仅仅是CPU需要转的很烈害
	 * 
	 * 由于这里是通过SinglerObserver进行属性通知的，而SinglerObserver是线程安全的，也就是说同一时间只会
	 * 有一个线程在对属性进行设置，所以这里完全消化了线程的问题，不会在属性设置部分有线程安全的问题
	 * 
	 * 这个地方还希望能够有这样的设置：
	 * 在多少毫秒以内，如果用户连续发送两个以上的属性设置请求则要按照最后一个请求进行发送属性
	 */
	private void doSetFOProperties() {
		//走swing线程的模式
//		setFOPropertiesFunctoin();
		
		//走专门的输入属性线程池的模式
		WiseDocThreadService.Instance.doSetPropertyService(new SetFoPropertyTask());
	}
	
	/**
	 * 设置属性的任务
	 * @author 闫舒寰
	 *
	 */
	private class SetFoPropertyTask implements Callable<Object> {
		@Override
		public Object call() throws Exception {
			setFOPropertiesFunctoin();
			return null;
		}
	}
	
	//具体的设置FO属性的方法
	private void setFOPropertiesFunctoin() {
		//有可能有的Action没有ActionType，这个情况是不合理的但是也要处理，这里应该抛出异常
		//TODO 这里应该有异常处理
		if (actionType == null) {
			return;
		}
		
		//拿着当前设置的proeprty和当前读到的property来进行比较，要是发现一个不一样的就发送消息设置属性
		final Map<Integer, Object> readProperties = RibbonUIModel.getReadCompletePropertiesByType().get(actionType);
		
		if (properties != null) {
			
			if (readProperties != null) {
				for (final Entry<Integer, Object> entry : properties.entrySet()) {
					final int key = entry.getKey();
					final Object value = entry.getValue();
					
//					System.out.println("set property: " + properties + " read key: " + key + " readproperty: " + readProperties.get(key));
					
					if (readProperties.get(key) != null && value != null) {
						//当从鼠标读取到的值和当前需要设定的值都不为null的时候
						if (!value.equals(readProperties.get(key))) {
//							System.out.println("key: " + key);
//							System.err.println("value: " + value + " read property: " + readProperties.get(key) + " ?: " + value.equals(readProperties.get(key)));
							//一旦有值不等的时候则设置新属性值
							UIObserver.getInstance().setProperties(actionType, properties);
							
							//为了应对图形部分的特殊属性，所采用的特例
							Map<Integer, Object> temp = new HashMap<Integer, Object>();
							if (key == Constants.PR_SVG_CONTAINER) {
								temp.put(Constants.PR_SVG_CONTAINER, value);
								updateReaderAndCurrentProperties(temp);
							} else if (key == Constants.PR_SVG_CANVAS) {
								temp.put(Constants.PR_SVG_CANVAS, value);
								updateReaderAndCurrentProperties(temp);
							} else {
								temp = null;
								updateReaderAndCurrentProperties(properties);
							}
							
							//这个是默认的结构
							updateReaderAndCurrentProperties(properties);
							
							break;
						} else {
							//但是一旦相等的时候则继续循环
							continue;
						}
					} else {
						if (readProperties.get(key) == null && value == null) {
							//当都是空的时候就是相等
							continue;
						}
						//当读取到的值为空的时候，直接把属性设置到模型层中，通过恰当的初始化这个值其实不应该为空
						UIObserver.getInstance().setProperties(actionType, properties);
						updateReaderAndCurrentProperties(properties);
					}
				}
			} else {
				//当选中条形码的时候这个readProperties就为空，（目前是这样）。以后也需要读取条形码的相关属性，目前暂时这么处理，即当读取不到任何属性的时候，直接发送属性到模型层中
				UIObserver.getInstance().setProperties(actionType, properties);
				updateReaderAndCurrentProperties(properties);
			}
			
		} else {
			UIObserver.getInstance().setProperties(actionType, properties);
			updateReaderAndCurrentProperties(RibbonUIModel.getDefaultPropertiesByType().get(actionType));
		}
	}
	
	
	private void updateReaderAndCurrentProperties(final Map<Integer, Object> properties){
		
		RibbonUIModel.RibbonUIModelInstance.updateUIState(actionType, properties);
		//把属性一起发送出去
		RibbonUIModel.RibbonUIModelInstance.sendPropertyMsg();
		RibbonUIManager.updateUIAvailable();
	}
	
	
	///===================第五条结束===============================///

	public Enum<? extends ActionID> getActionID() {
		return actionID;
	}

	public void setActionID(final Enum<? extends ActionID> actionID) {
		this.actionID = actionID;
	}

	public String getActionCommand() {
		return actionCommand;
	}

	public void setActionCommand(final String actionCommand) {
		this.actionCommand = actionCommand;
	}
}
