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
 * @SelectHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.mousehandler;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.wisii.wisedoc.view.AbstractEditComponent;

/**
 * 类功能描述：鼠标操作接口
 * 
 * 作者：zhangqiang 创建日期：2008-9-23
 */
public interface MouseHandler extends MouseListener, MouseMotionListener
{
	public void install(AbstractEditComponent c);

	/**
	 * Called when the UI is being removed from the interface of a
	 * JTextComponent. This is used to unregister any listeners that were
	 * attached.
	 * 
	 * @param c
	 *            the JTextComponent
	 */
	public void deinstall(AbstractEditComponent c);

	/*
	 * 
	 * 选中的显示功能
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	public void paint(Graphics g);

	public boolean isInControl(MouseEvent event);

	/**
	 * 
	 * 初始化init的状态，在DefaultMouseHandler切换handler是调用
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void init();
	public MouseHandler getCurrentHandler();

}
