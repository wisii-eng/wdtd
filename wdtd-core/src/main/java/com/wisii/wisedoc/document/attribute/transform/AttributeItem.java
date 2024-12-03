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

package com.wisii.wisedoc.document.attribute.transform;

public class AttributeItem
{

	String key;

	String value;

	public AttributeItem(String k, String v)
	{
		key = k;
		value = v;
	}

	@Override
	public String toString()
	{
		String result = "";
		// if (key != null && value != null && !"".equals(key)
		// && !"".equals(value))
		if (key != null && value != null)
		{
			result = result + " ";
			result = result + key;
			result = result + "=\"";
			result = result + value;
			result = result + "\"";
		}
		return result;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public TreeDSNode getTreeDSNode()
	{
		TreeDSNode root = null;
		// if (key != null && !key.equals(""))
		if (key != null)
		{
			root = new TreeNodeDS(null, TreeDSNode.ATTRIBUTE, key);
			root.setValue(value);
		}
		return root;
	}

	public AttributeItem clone()
	{
		String newkey = new String(key);
		String newvalue = new String(value);
		AttributeItem newitem = new AttributeItem(newkey, newvalue);
		return newitem;
	}
}
