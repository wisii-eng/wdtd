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
/**
 * @WiseDocTree.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * 类功能描述：WiseDoc数对象
 * 
 * 作者：zhangqiang 创建日期：2008-11-27
 */
public class WiseDocTree extends JTree
{
	public final static String SELECTEDCMD = "select changed";

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseDocTree()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseDocTree(Object[] value)
	{
		super(value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseDocTree(Vector<?> value)
	{
		super(value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseDocTree(Hashtable<?, ?> value)
	{
		super(value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseDocTree(TreeNode root)
	{
		super(root);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseDocTree(TreeModel newModel)
	{
		super(newModel);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseDocTree(TreeNode root, boolean asksAllowsChildren)
	{
		super(root, asksAllowsChildren);
		// TODO Auto-generated constructor stub
	}
    public void setSelectedNode(TreeNode root)
    {
		if (root != null)
		{
			List<TreeNode> nodes = new ArrayList<TreeNode>();
			while (root != null)
			{
				nodes.add(0, root);
				root = root.getParent();
			}
			TreePath path = new TreePath(nodes.toArray());
			expandPath(path);
			setSelectionPath(path);
			scrollPathToVisible(path);
		}
	}
	protected void fireValueChanged(TreeSelectionEvent e)
	{
		// TODO Auto-generated method stub
		super.fireValueChanged(e);
		fireActionPerformed();
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created. The listener list
	 * is processed in last to first order.
	 * 
	 * @see EventListenerList
	 */
	protected void fireActionPerformed()
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		int modifiers = 0;
		AWTEvent currentEvent = EventQueue.getCurrentEvent();
		if (currentEvent instanceof InputEvent)
		{
			modifiers = ((InputEvent) currentEvent).getModifiers();
		} else if (currentEvent instanceof ActionEvent)
		{
			modifiers = ((ActionEvent) currentEvent).getModifiers();
		}
		ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				SELECTEDCMD, EventQueue.getMostRecentEventTime(), modifiers);

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ActionListener.class)
			{
				((ActionListener) listeners[i + 1]).actionPerformed(e);
			}
		}
	}

	/**
	 * Adds the specified action listener to receive action events from this
	 * textfield.
	 * 
	 * @param l
	 *            the action listener to be added
	 */
	public synchronized void addActionListener(ActionListener l)
	{
		listenerList.add(ActionListener.class, l);
	}

	/**
	 * Removes the specified action listener so that it no longer receives
	 * action events from this textfield.
	 * 
	 * @param l
	 *            the action listener to be removed
	 */
	public synchronized void removeActionListener(ActionListener l)
	{
		listenerList.remove(ActionListener.class, l);
	}
}
