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
 * @ChangedItem.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.configure;

import java.text.MessageFormat;

/**
 * 类功能描述：对一个配置改变项的封装
 * 
 * 作者：李晓光 创建日期：2008-8-19
 */
public class ChangedItem {
	/* 改变项的关键字 */
	private String key = "";
	/* 改变前的值 */
	private String oldValue = "";
	/* 改变后的值 */
	private String newValue = "";
	
	private final static String PATTERN = "[Key={0}][OleValue={1}][NewValue={2}]";

	public ChangedItem(String key, String oldValue, String newValue) {
		this.key = key;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public String getKey() {
		return this.key;
	}

	public String getOldValue() {
		return this.oldValue;
	}

	public String getNewValue() {
		return this.newValue;
	}
	
	public String toString(){
		return MessageFormat.format(PATTERN, getKey(), getOldValue(), getNewValue());
	}
}
