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
import java.io.PrintWriter;
import java.util.Hashtable;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008.10.23
 */

public class DTDElement
{

	/** The name of the element */
	public String name;

	/** The element's attributes */
	public Hashtable attributes;

	/** The element's content */
	public DTDItem content;

	public DTDElement()
	{
		attributes = new Hashtable();
	}

	public DTDElement(String aName)
	{
		name = aName;

		attributes = new Hashtable();
	}

	/**
	 * Writes out an element declaration and an attlist declaration (if
	 * necessary) for this element
	 */
	public void write(PrintWriter out) throws IOException
	{
		out.print("<!ELEMENT ");
		out.print(name);
		out.print(" ");
		if (content != null)
		{
			// content.write(out);
		} else
		{
			out.print("ANY");
		}
		out.println(">");
		out.println();
	}

	public boolean equals(Object ob)
	{
		if (ob == this)
			return true;
		if (!(ob instanceof DTDElement))
			return false;

		DTDElement other = (DTDElement) ob;

		if (name == null)
		{
			if (other.name != null)
				return false;
		} else
		{
			if (!name.equals(other.name))
				return false;
		}

		if (attributes == null)
		{
			if (other.attributes != null)
				return false;
		} else
		{
			if (!attributes.equals(other.attributes))
				return false;
		}

		if (content == null)
		{
			if (other.content != null)
				return false;
		} else
		{
			if (!content.equals(other.content))
				return false;
		}

		return true;
	}

	/** Sets the name of this element */
	public void setName(String aName)
	{
		name = aName;
	}

	/** Returns the name of this element */
	public String getName()
	{
		return name;
	}

	/** Stores an attribute in this element */
	public void setAttribute(String attrName, DTDAttribute attr)
	{
		attributes.put(attrName, attr);
	}

	/** Gets an attribute for this element */
	public DTDAttribute getAttribute(String attrName)
	{
		return (DTDAttribute) attributes.get(attrName);
	}

	/** Sets the content type of this element */
	public void setContent(DTDItem theContent)
	{
		content = theContent;
	}

	/** Returns the content type of this element */
	public DTDItem getContent()
	{
		return content;
	}
}
