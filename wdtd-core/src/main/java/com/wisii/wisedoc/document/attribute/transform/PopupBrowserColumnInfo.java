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
 * @SelectInFO.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.transform;

import com.wisii.wisedoc.banding.BindNode;

/**
 * Desc
 * 
 * @author xieli 2016年10月8日下午5:14:11
 */
public final class PopupBrowserColumnInfo {
	private String name;
	private BindNode optionpath;

	public PopupBrowserColumnInfo() {
	}

	public PopupBrowserColumnInfo(String name, BindNode optionpath) {
		this.name = name;
		this.optionpath = optionpath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BindNode getOptionpath() {
		return optionpath;
	}

	public void setOptionpath(BindNode optionpath) {
		this.optionpath = optionpath;
	}

}
