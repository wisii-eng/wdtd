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
 * 类功能描述：
 * 
 * 作者： 创建日期：
 */
public final class ButtonColumnInFO
{
	
	private BindNode optionpath;

	public ButtonColumnInFO(BindNode optionpath) {
		super();
		this.optionpath = optionpath;
	}

	public BindNode getOptionpath() {
		return optionpath;
	}

	public void setOptionpath(BindNode optionpath) {
		this.optionpath = optionpath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((optionpath == null) ? 0 : optionpath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ButtonColumnInFO other = (ButtonColumnInFO) obj;
		if (optionpath == null) {
			if (other.optionpath != null)
				return false;
		} else if (!optionpath.equals(other.optionpath))
			return false;
		return true;
	}
	


}
