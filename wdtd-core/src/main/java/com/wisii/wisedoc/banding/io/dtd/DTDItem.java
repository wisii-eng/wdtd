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

public abstract class DTDItem
{

	/** Indicates how often the item may occur */
	public DTDCardinal cardinal;

	public DTDItem()
	{
		cardinal = DTDCardinal.NONE;
	}

	public DTDItem(DTDCardinal aCardinal)
	{
		cardinal = aCardinal;
	}

	public boolean equals(Object ob)
	{
		if (ob == this)
			return true;
		if (!(ob instanceof DTDItem))
			return false;

		DTDItem other = (DTDItem) ob;

		if (cardinal == null)
		{
			if (other.cardinal != null)
				return false;
		} else
		{
			if (!cardinal.equals(other.cardinal))
				return false;
		}

		return true;
	}

	/** Sets the cardinality of the item */
	public void setCardinal(DTDCardinal aCardinal)
	{
		cardinal = aCardinal;
	}

	/** Retrieves the cardinality of the item */
	public DTDCardinal getCardinal()
	{
		return cardinal;
	}
}
