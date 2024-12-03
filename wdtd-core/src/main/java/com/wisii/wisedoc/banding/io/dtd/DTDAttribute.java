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

public class DTDAttribute
{

	/** The name of the attribute */
	public String name;

	/**
	 * The type of the attribute (either String, DTDEnumeration or
	 * DTDNotationList)
	 */
	public Object type;

	public String dateType = "";

	public String getDateType()
	{
		return dateType;
	}

	public void setDateType(String dateType)
	{
		this.dateType = dateType;
	}

	/** The attribute's declaration (required, fixed, implied) */
	// public DTDDecl decl;
	/** The attribute's default value (null if not declared) */
	public String defaultValue;

	public DTDAttribute()
	{
	}

	public DTDAttribute(String aName)
	{
		name = aName;
	}

	public boolean equals(Object ob)
	{
		if (ob == this)
			return true;
		if (!(ob instanceof DTDAttribute))
			return false;

		DTDAttribute other = (DTDAttribute) ob;

		if (name == null)
		{
			if (other.name != null)
				return false;
		} else
		{
			if (!name.equals(other.name))
				return false;
		}

		if (type == null)
		{
			if (other.type != null)
				return false;
		} else
		{
			if (!type.equals(other.type))
				return false;
		}

		if (defaultValue == null)
		{
			if (other.defaultValue != null)
				return false;
		} else
		{
			if (!defaultValue.equals(other.defaultValue))
				return false;
		}
		return true;
	}

	/** Sets the name of the attribute */
	public void setName(String aName)
	{
		name = aName;
	}

	/** Returns the attribute name */
	public String getName()
	{
		return name;
	}

	/** Sets the type of the attribute */
	public void setType(Object aType)
	{
		if (!(aType instanceof String) && !(aType instanceof DTDEnumeration))
		{
			throw new IllegalArgumentException(
					"Must be String, DTDEnumeration or DTDNotationList");
		}

		type = aType;
	}

	/** Gets the type of the attribute */
	public Object getType()
	{
		return type;
	}

	/** Sets the default value */
	public void setDefaultValue(String aDefaultValue)
	{
		defaultValue = aDefaultValue;
	}

	/** Returns the default value */
	public String getDefaultValue()
	{
		return defaultValue;
	}
}
