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
package com.wisii.wisedoc.view.ui.parts.pagelayout;

/**
 * 用于更新页布局面板的属性的对话框中的属性的接口
 * @author 闫舒寰
 * @version 1.0 2009/02/18
 */
public interface UpdateSPMProperty {
	
	/**
	 * 1、递归更新所有组件是否可用
	 * 2、更新所有界面的属性
	 */
	public void update();

}
