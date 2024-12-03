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
 * @ConnWith.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.edit;

import java.util.List;

/**
 * 类功能描述：关联类
 *
 * 作者：zhangqiang
 * 创建日期：2009-8-29
 */
public final class ConnWith
{
	/*
	 * 参数，所有的参数在该类中定义， 输出Formula时可以将这些参数信息全部输出，也可以只输出表达式用到的参数
	 */
	private final List<Parameter> parms;
	/* 可以有多个Formula，即可以同步更新多个 */
	private final List<Formula> formulas;

	public ConnWith(List<Formula> formulas, List<Parameter> parms)
	{
		super();
		this.formulas = formulas;
		this.parms = parms;
	}

	/**
	 * @返回 parms变量的值
	 */
	public final List<Parameter> getParms()
	{
		return parms;
	}

	/**
	 * @返回 formulas变量的值
	 */
	public final List<Formula> getFormulas()
	{
		return formulas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((formulas == null) ? 0 : formulas.hashCode());
		result = prime * result + ((parms == null) ? 0 : parms.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnWith other = (ConnWith) obj;
		if (formulas == null)
		{
			if (other.formulas != null)
				return false;
		} else if (!formulas.equals(other.formulas))
			return false;
		if (parms == null)
		{
			if (other.parms != null)
				return false;
		} else if (!parms.equals(other.parms))
			return false;
		return true;
	}

}
