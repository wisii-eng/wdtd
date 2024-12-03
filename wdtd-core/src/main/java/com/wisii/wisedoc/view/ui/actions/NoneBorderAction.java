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
import java.util.Map;

import com.wisii.wisedoc.document.Constants;

/**
 * 清空所有边框动作
 * @author 闫舒寰
 * @version 1.0 2009/02/01
 */
public class NoneBorderAction extends Actions {

	@Override
	public void doAction(ActionEvent e) {
		setDefaultBorder();
	}
	
	private void setDefaultBorder(){		
		Map<Integer, Object> properties = new HashMap<Integer, Object>();
		//设置四个边默认的宽度
		properties.put(Constants.PR_BORDER_BEFORE_WIDTH, null);
		properties.put(Constants.PR_BORDER_AFTER_WIDTH, null);
		properties.put(Constants.PR_BORDER_START_WIDTH, null);
		properties.put(Constants.PR_BORDER_END_WIDTH, null);
		//设置四个边默认的样式
		properties.put(Constants.PR_BORDER_BEFORE_STYLE, null);
		properties.put(Constants.PR_BORDER_AFTER_STYLE, null);
		properties.put(Constants.PR_BORDER_START_STYLE, null);
		properties.put(Constants.PR_BORDER_END_STYLE, null);
		//设置四个边默认的颜色
		properties.put(Constants.PR_BORDER_BEFORE_COLOR, null);
		properties.put(Constants.PR_BORDER_AFTER_COLOR, null);
		properties.put(Constants.PR_BORDER_START_COLOR, null);
		properties.put(Constants.PR_BORDER_END_COLOR, null);
		
		setFOProperties(properties);		
	}

}
