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
package com.wisii.wisedoc.view.ui.actions.svggraphic;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 清除svg图片属性
 * @author 闫舒寰
 * @version 1.0 2009/03/04
 */
public class SvgGraphicFontCleanAction extends Actions {

	public void doAction(ActionEvent e) {
			setFOProperties(null);
	}

}