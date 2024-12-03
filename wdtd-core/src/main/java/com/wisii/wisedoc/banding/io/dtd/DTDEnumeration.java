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

public class DTDEnumeration
{

	protected Vector items;

	/** Creates a new enumeration */
	public DTDEnumeration()
	{
		items = new Vector();
	}

	/** Adds a new value to the list of values */
	public void add(String item)
	{
		items.addElement(item);
	}

	/** Removes a value from the list of values */
	public void remove(String item)
	{
		items.removeElement(item);
	}

	/** Returns the values as an array */
	public String[] getItems()
	{
		String[] retval = new String[items.size()];
		items.copyInto(retval);

		return retval;
	}

	/** Returns the values as a vector (not a clone!) */
	public Vector getItemsVec()
	{
		return items;
	}

	public boolean equals(Object ob)
	{
		if (ob == this)
			return true;
		if (!(ob instanceof DTDEnumeration))
			return false;

		DTDEnumeration other = (DTDEnumeration) ob;
		return items.equals(other.items);
	}

	/** Returns the items in the enumeration */
	public String[] getItem()
	{
		return getItems();
	}

	/** Sets the items in the enumeration */
	public void setItem(String[] newItems)
	{
		items = new Vector(newItems.length);
		for (int i = 0; i < newItems.length; i++)
		{
			items.addElement(newItems[i]);
		}
	}

	/** Stores an item in the enumeration */
	public void setItem(String item, int i)
	{
		items.setElementAt(item, i);
	}

	/** Retrieves an item from the enumeration */
	public String getItem(int i)
	{
		return (String) items.elementAt(i);
	}
}