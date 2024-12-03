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
 * @PropertyPanelInterface.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.dialog;

import java.util.Map;

import javax.swing.JComponent;

/**
 * 类功能描述：属性设置控件通用接口。
 * 
 * 作者：李晓光 创建日期：2009-3-17
 */
public interface PropertyPanelInterface {
	/**
	 * 按指定的属性集合，创始当前控件。
	 * 
	 * @param map
	 *            指定属性集合。
	 * @return {@link JComponent}	
	 */
	void install(Map<Integer, Object> map);

	/**
	 * 获得当前设置后的所有属性，并封装到Map中。
	 * 
	 * @return {@link Map} 返回设置后的属性集合。
	 */
	Map<Integer, Object> getSetting();
	/**
	 * 验证数据的有效性。
	 * @return {@link Boolean}	返回当前设置是否有效。
	 */
	boolean isValidate();
}
