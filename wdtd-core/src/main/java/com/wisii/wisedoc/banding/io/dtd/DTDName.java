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

package com.wisii.wisedoc.banding.io.dtd;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008.10.23
 */

public class DTDName extends DTDItem
{

	public String value;

	public DTDName()
	{
	}

	public DTDName(String aValue)
	{
		value = aValue;
	}

	public boolean equals(Object ob)
	{
		if (ob == this)
			return true;
		if (!(ob instanceof DTDName))
			return false;
		if (!super.equals(ob))
			return false;

		DTDName other = (DTDName) ob;

		if (value == null)
		{
			if (other.value != null)
				return false;
		} else
		{
			if (!value.equals(other.value))
				return false;
		}
		return true;
	}

	/** Sets the name value */
	public void setValue(String aValue)
	{
		value = aValue;
	}

	/** Retrieves the name value */
	public String getValue()
	{
		return value;
	}
}
