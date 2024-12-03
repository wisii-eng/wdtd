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
package com.wisii.wisedoc.view.ui.actions.text;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 清除所有样式属性，设置样式属性为null即设置样式为默认值，同时调用所有激活action的setDefaultState方法
 * 设置所有Ribbon Action为默认值
 * 
 * @author	闫舒寰
 * @version 1.0 2009/01/13
 */
public class FontCleanAction extends Actions {

	public void doAction(ActionEvent e) {
			setFOProperties(null);
	}

}
