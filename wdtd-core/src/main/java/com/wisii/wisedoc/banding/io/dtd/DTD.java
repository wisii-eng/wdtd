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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008.10.23
 */

public class DTD
{

	/** Contains all the elements defined in the DTD */
	public Hashtable elements;

	/** Contains parsed DTD's for any external entity DTD declarations */
	public Hashtable externalDTDs;

	/** Contains all the items defined in the DTD in their original order */
	public Vector items;

	/**
	 * Contains the element that is most likely the root element or null if the
	 * root element can't be determined.
	 */
	public DTDElement rootElement;

	DefaultBindNode rootNode = null;

	public static HashMap<String, DefaultBindNode> elementMap = new HashMap<String, DefaultBindNode>();

	public static List<String> list = new ArrayList<String>();

	/** Creates a new DTD */
	public DTD()
	{
		elements = new Hashtable();
		externalDTDs = new Hashtable();
		items = new Vector();
	}

	/** Returns true if this object is equal to another */
	public boolean equals(Object ob)
	{
		if (this == ob)
			return true;

		if (!(ob instanceof DTD))
			return false;

		DTD otherDTD = (DTD) ob;

		return items.equals(otherDTD.items);
	}

	/** Stores an array of items in the items array */
	public void setItems(Object[] newItems)
	{
		items = new Vector(newItems.length);
		for (int i = 0; i < newItems.length; i++)
		{
			items.addElement(newItems[i]);
		}
	}

	/** Returns the items as an array */
	public Object[] getItems()
	{
		return items.toArray();
	}

	/** Stores an item in the items array */
	public void setItem(Object item, int i)
	{
		items.setElementAt(item, i);
	}

	/** Retrieves an item from the items array */
	public Object getItem(int i)
	{
		return items.elementAt(i);
	}

	/** Retrieves a list of items of a particular type */
	public Vector getItemsByType(Class itemType)
	{
		Vector results = new Vector();

		Enumeration e = items.elements();

		while (e.hasMoreElements())
		{
			Object ob = e.nextElement();

			if (itemType.isAssignableFrom(ob.getClass()))
			{
				results.addElement(ob);
			}
		}

		return results;
	}

	public BindNode getRootElement(String rootname) throws IOException
	{

		Enumeration e = elements.elements();
		while (e.hasMoreElements())
		{
			DTDElement element = (DTDElement) e.nextElement();
			DefaultBindNode elementNode = new DefaultBindNode(null, 0, -1,
					element.getName());
			Enumeration attrs = element.attributes.elements();
			while (attrs.hasMoreElements())
			{
				DTDAttribute attr = (DTDAttribute) attrs.nextElement();
				AttributeBindNode attrNode = new AttributeBindNode(elementNode,
						0, -1, attr.getName());
				attrNode.setParent(elementNode);
				elementNode.addChild(attrNode);
			}
			elementMap.put(element.name, elementNode);
		}
		e = elements.elements();
		while (e.hasMoreElements())
		{
			DTDElement element = (DTDElement) e.nextElement();
			String elementName = element.getName();
			list.clear();
			dumpDTDItem(element.content);
			int num = list.size();
			if (num > 0)
			{
				for (int i = 0; i < num; i++)
				{
					DefaultBindNode pnode = elementMap.get(elementName);
					String name = list.get(i);
					DefaultBindNode cnode = elementMap.get(name);
					elementMap.remove(elementName);
					cnode.setParent(pnode);
					pnode.addChild(cnode);
					elementMap.put(elementName, pnode);
				}
			}
		}
		if ("".equalsIgnoreCase(rootname))
		{
			if (rootElement != null)
			{
				rootNode = elementMap.get(rootElement.getName());
			} else
			{
				LogUtil.debug("您选择的DTD文件没有指定根节点！");
				// System.out.println("您选择的DTD文件没有指定根节点！");
			}
		} else
		{
			rootNode = elementMap.get(rootname);
		}
		return rootNode;
	}

	public static void dumpDTDItem(DTDItem item)
	{

		if (item != null)
		{
			if (item instanceof DTDEmpty)
			{

			} else if (item instanceof DTDName)
			{
				String str = ((DTDName) item).value;
				list.add(str);
			}

			else if (item instanceof DTDSequence)
			{

				DTDItem[] items = ((DTDSequence) item).getItems();

				for (int i = 0; i < items.length; i++)
				{

					dumpDTDItem(items[i]);
				}

			} else if (item instanceof DTDMixed)
			{

				DTDItem[] items = ((DTDMixed) item).getItems();

				for (int i = 0; i < items.length; i++)
				{
					dumpDTDItem(items[i]);
				}
			}
		}
	}

	public int getDateType(String typestr) throws IOException
	{
		int type = BindNode.STRING;
		// if (!"".equalsIgnoreCase(typestr))
		// {
		//
		// }
		return type;
	}
}
