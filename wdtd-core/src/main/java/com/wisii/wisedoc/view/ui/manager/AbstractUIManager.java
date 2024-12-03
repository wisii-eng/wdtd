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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JComponent;
import javax.swing.SwingWorker;

import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionID;

/**
 * 
 * 这里应该提供一个template方法来绑定action和UI component，并协助action和component正确的完成初始化
 * 
 * 这个template方法主要用于协助完成action所应有的8个功能的初始化
 * 
 * @author	闫舒寰
 * @version 1.0 2008/11/19
 */
public abstract class AbstractUIManager {
	
	/**
	 * 目前统一了这个set中的数据结构，set中应该记录着有机组对象和该action绑定，每一组中可以有多个组件和action绑定
	 */
	private static Map<Enum<? extends ActionID>, Map<ActionCommandType, Set<List<Object>>>> UIComponents = new HashMap<Enum<? extends ActionID>, Map<ActionCommandType, Set<List<Object>>>>();
	
	/**
	 * template pattern 强制子类按照要求绑定UI
	 */
	public final void bind(final Enum<? extends ActionID> actionID, final Object... components) {
		
		final ActionCommandType actionCommand = ActionCommandType.RIBBON_ACTION;
		
		//登记绑定组件
		addComponents(actionID, actionCommand, components);
		
		//为组件添加动作
		addUIAction(actionID, actionCommand, components);
		
		//设置可更新组件
		addUIUpdate(actionID, actionCommand, components);
		
		//为组件绑定key
		addUIKeyAccelerate();
		
	}
	
	public final void bind(final Enum<? extends ActionID> actionID, final ActionCommandType actionCommand, final Object... components) {
		
		//登记绑定组件
		addComponents(actionID, actionCommand, components);
		
		//为组件添加动作
		addUIAction(actionID, actionCommand, components);
		
		//设置可更新组件
		addUIUpdate(actionID, actionCommand, components);
		
		//为组件绑定key
		addUIKeyAccelerate();
	}
	
	/**
	 * 绑定动作和组件
	 * 
	 * @param actionID	动作ID
	 * @param components	组件集合
	 */
	protected void addComponents(final Enum<? extends ActionID> actionID, final ActionCommandType actionCommand, final Object... components) {
		
		if (components == null || components.length == 0) {
			//这里不用处理什么
		} else {
			final List<Object> tempList = new ArrayList<Object>();
			final Set<List<Object>> tempSet = new HashSet<List<Object>>();
			for (final Object component : components) {
				tempList.add(component);
			}
			tempSet.add(tempList);
			
			final Map<ActionCommandType, Set<List<Object>>> newMap = new HashMap<ActionCommandType, Set<List<Object>>>();
			newMap.put(actionCommand, tempSet);
			
			final Map<ActionCommandType, Set<List<Object>>> oldMap = getUIComponents().get(actionID);
			
			if (oldMap != null) {
				
				//针对对话框的组件可以直接清除
				if (oldMap.get(actionCommand) != null && actionCommand == ActionCommandType.DIALOG_ACTION) {
					//这里清空了对话框所注册的所有和该action id相关的组件
					oldMap.get(actionCommand).clear();
					oldMap.putAll(newMap);
				}
				
//				oldMap.putAll(newMap);
				//这个是用来保证一个actionID可以绑定多个界面对象，比如有两组ribbon对象需要绑定同一个action这种情况
				//目前这种情况只针对Ribbon Action的按钮
				if (oldMap.get(actionCommand) != null && actionCommand == ActionCommandType.RIBBON_ACTION) {
//					oldMap.get(actionCommand).addAll(newMap.get(actionCommand));
					
					//是否添加新的绑定对象的一个标签
					boolean flag = false;
					final Set<List<Object>> ts = oldMap.get(actionCommand);
					for (final List<Object> list : ts) {
						if (list.size() == components.length) {
							/*
							 * 当当前设置的对象的个数和以前设置的一样，
							 * 并且类型还一样的情况下则认为该组件和以前绑定的组件一样，
							 * 则把以前绑定的组件替换掉，而如果个数和类型不一样的时候则作为新的组件添加到绑定对应列表中
							 */
							for (int i = 0; i < components.length; i++) {
//								System.out.println(list.get(i).getClass() + " : " +(components[i].getClass()) + " : " + list.get(i).getClass().equals(components[i].getClass()));
								if (!list.get(i).getClass().equals(components[i].getClass())) {
									flag = true;
								} else {
									list.set(i, components[i]);
								}
							}
						} else {
							boolean flag1 = true;
							for (final List<Object> countList : ts) {
								if (countList.size() == components.length) {
									flag1 = false;
								}
							}
							
							if (flag1) {
								flag = true;
							}
							
							//old code
//							flag = true;
						}
					}
					
					if (flag) {
						//old
						oldMap.get(actionCommand).addAll(newMap.get(actionCommand));
					}
					flag = false;
					
				} else {
					//如果当前actionCommand为空，则应该说明当前actionCommand中没有该类别的组件，可以把新得到的组件放到里面去
					oldMap.putAll(newMap);
				}
				
				getUIComponents().put(actionID, oldMap);
			} else {
				getUIComponents().put(actionID, newMap);
			}
		}
	}
	
	/**
	 * 为新加入的component(s)添加监听器
	 * 
	 * @param ActionID 动作
	 * @param componts
	 */
	protected abstract void addUIAction(Enum<? extends ActionID> actionID, ActionCommandType actionCommand, Object... components);
	
	public abstract void addUIKeyAccelerate();
	
	/**
	 * 为新加入的component(s)添加界面更新监听器
	 * 
	 * 可以通过接口标记的方法，看该动作能否接受数据更新
	 * 
	 * @param actionID
	 * @param components
	 */
	public abstract void addUIUpdate(Enum<? extends ActionID> actionID, ActionCommandType actionCommand, Object... components);
	
	/**
	 * 检查UI是否可用
	 */
	public static void updateUIAvailable(){
		
		//控制每次只有一个线程来更新界面是否可用
		updateThreadPool.execute(new AbstractUIManager.UpdateUI());
	   //如果发现界面可用不可用不正常时，则将上面代码注释点，下面代码可用，下面的代码发生异常时有异常消息
       //singleThread();
	}
	
	/**
	 * 专门用于更新界面是否可用的线程池
	 */
	private static final ExecutorService updateThreadPool = Executors.newSingleThreadExecutor();
	
	//目前调整SwingWorker会有问题，暂时不用
	private static class UpdateUI extends SwingWorker<Boolean, Object> {
		
		//用来保存该对象是否可用的信息
		private Map<JComponent, Boolean> uiMap;

		@Override
		protected Boolean doInBackground() throws Exception {
			
			uiMap = new HashMap<JComponent, Boolean>();
			final Set<Enum<? extends ActionID>> acd = AbstractUIManager.getUIComponents().keySet();
			
			final Map<Enum<? extends ActionID>, Map<ActionCommandType, Set<List<Object>>>> com = AbstractUIManager.getUIComponents();
			
			final Map<Enum<? extends ActionID>, BaseAction> actions = ActionFactory.getActiveActions();
			
			for (final Enum<? extends ActionID> enu : acd)
			{
				final Set<List<Object>> setCom = com.get(enu).get(
						ActionCommandType.RIBBON_ACTION);
				if (setCom == null || setCom.isEmpty())
				{
					continue;
				}
				boolean isenable = false;
				BaseAction action = actions.get(enu);
				if (action == null)
				{
					System.err.println("isAvailable problem in"
							+ actions.get(enu));
				}
				else
				{
					isenable = action.isAvailable();
				}
				for (final List<Object> list : setCom)
				{
					for (final Object object : list)
					{
						if (object instanceof JComponent)
						{
							final JComponent jcom = (JComponent) object;
							uiMap.put(jcom, isenable);
						}
					}
				}
			}
			
			return false;
		}
		
		@Override
		protected void done() {
			
			if (uiMap != null) {
				final Set<JComponent> uiSet = uiMap.keySet();
				
				for (final JComponent component : uiSet) {
					component.setEnabled(uiMap.get(component));
				}
			}
		}
	}
	
	/**
	 * 单线程更新界面，用于测试isAvailable中哪些方法会有异常
	 */
	@Deprecated
	private static void singleThread() {
		final Set<Enum<? extends ActionID>> acd = AbstractUIManager.getUIComponents().keySet();
		
		final Map<Enum<? extends ActionID>, Map<ActionCommandType, Set<List<Object>>>> com = AbstractUIManager.getUIComponents();
		
		final Map<Enum<? extends ActionID>, BaseAction> actions = ActionFactory.getActiveActions();
		
		for (final Enum<? extends ActionID> enu : acd) {
			final Set<List<Object>> setCom = com.get(enu).get(ActionCommandType.RIBBON_ACTION);
			for (final List<Object> list : setCom) {
				for (final Object object : list) {
					if (object instanceof JComponent) {
						final JComponent jcom = (JComponent) object;
						jcom.setEnabled(actions.get(enu).isAvailable());
					}
				}
			}
		}
	}

	public static Map<Enum<? extends ActionID>, Map<ActionCommandType, Set<List<Object>>>> getUIComponents() {
		return UIComponents;
	}
}
