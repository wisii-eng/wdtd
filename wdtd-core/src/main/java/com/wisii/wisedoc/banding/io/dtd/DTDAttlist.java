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

import java.util.Vector;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008.10.23
 */

public class DTDAttlist
{

	/** The name of the element */
	public String name;

	/** The attlist's attributes */
	public Vector attributes;

	public DTDAttlist()
	{
		attributes = new Vector();
	}

	public DTDAttlist(String aName)
	{
		name = aName;

		attributes = new Vector();
	}

	public boolean equals(Object ob)
	{
		if (ob == this)
			return true;
		if (!(ob instanceof DTDAttlist))
			return false;

		DTDAttlist other = (DTDAttlist) ob;

		if ((name == null) && (other.name != null))
			return false;
		if ((name != null) && !name.equals(other.name))
			return false;

		return attributes.equals(other.attributes);
	}

	/** Returns the entity name of this attlist */
	public String getName()
	{
		return name;
	}

	/** Sets the entity name of this attlist */
	public void setName(String aName)
	{
		name = aName;
	}

	/** Returns the attributes in this list */
	public DTDAttribute[] getAttribute()
	{
		DTDAttribute attrs[] = new DTDAttribute[attributes.size()];
		attributes.copyInto(attrs);

		return attrs;
	}

	/** Sets the list of attributes */
	public void setAttribute(DTDAttribute[] attrs)
	{
		attributes = new Vector(attrs.length);
		for (int i = 0; i < attrs.length; i++)
		{
			attributes.addElement(attrs[i]);
		}
	}

	/** Returns a specific attribute from the list */
	public DTDAttribute getAttribute(int i)
	{
		return (DTDAttribute) attributes.elementAt(i);
	}

	/** Sets a specific attribute in the list */
	public void setAttribute(DTDAttribute attr, int i)
	{
		attributes.setElementAt(attr, i);
	}
}
