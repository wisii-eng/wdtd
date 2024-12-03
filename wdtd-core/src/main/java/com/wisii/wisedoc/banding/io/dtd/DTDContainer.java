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

public abstract class DTDContainer extends DTDItem
{

	protected Vector items;

	/** Creates a new DTDContainer */
	public DTDContainer()
	{
		items = new Vector();
	}

	/** Adds an element to the container */
	public void add(DTDItem item)
	{
		items.addElement(item);
	}

	/** Removes an element from the container */
	public void remove(DTDItem item)
	{
		items.removeElement(item);
	}

	/** Returns the elements as a vector (not a clone!) */
	public Vector getItemsVec()
	{
		return items;
	}

	/** Returns the elements as an array of items */
	public DTDItem[] getItems()
	{
		DTDItem[] retval = new DTDItem[items.size()];
		items.copyInto(retval);
		return retval;
	}

	public boolean equals(Object ob)
	{
		if (ob == this)
			return true;
		if (!(ob instanceof DTDContainer))
			return false;

		if (!super.equals(ob))
			return false;

		DTDContainer other = (DTDContainer) ob;

		return items.equals(other.items);
	}

	/** Stores items in the container */
	public void setItem(DTDItem[] newItems)
	{
		items = new Vector(newItems.length);
		for (int i = 0; i < newItems.length; i++)
		{
			items.addElement(newItems[i]);
		}
	}

	/** Retrieves the items in the container */
	public DTDItem[] getItem()
	{
		DTDItem[] retval = new DTDItem[items.size()];
		items.copyInto(retval);

		return retval;
	}

	/** Stores an item in the container */
	public void setItem(DTDItem anItem, int i)
	{
		items.setElementAt(anItem, i);
	}

	/** Retrieves an item from the container */
	public DTDItem getItem(int i)
	{
		return (DTDItem) items.elementAt(i);
	}

}