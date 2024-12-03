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
 * @WiseDocOptionPane.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * 类功能描述：Designer的OptionPane,所有用到JOptionPane的地方需用该类，
 * 为以后界面升级留接口
 *
 * 作者：zhangqiang
 * 创建日期：2008-10-7
 */
public class WiseDocOptionPane extends JOptionPane {

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public WiseDocOptionPane() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public WiseDocOptionPane(Object message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public WiseDocOptionPane(Object message, int messageType) {
		super(message, messageType);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public WiseDocOptionPane(Object message, int messageType, int optionType) {
		super(message, messageType, optionType);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public WiseDocOptionPane(Object message, int messageType, int optionType,
			Icon icon) {
		super(message, messageType, optionType, icon);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public WiseDocOptionPane(Object message, int messageType, int optionType,
			Icon icon, Object[] options) {
		super(message, messageType, optionType, icon, options);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public WiseDocOptionPane(Object message, int messageType, int optionType,
			Icon icon, Object[] options, Object initialValue) {
		super(message, messageType, optionType, icon, options, initialValue);
		// TODO Auto-generated constructor stub
	}

}
