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
package com.wisii.schema.model;

import com.sun.org.apache.xerces.internal.xs.*;
import com.sun.org.apache.xerces.internal.impl.dv.*;
public class XSObjectRef
{
	public static final int ARRAY_ELEMENTS = 1;

	public static final int ARRAY_GROUPS = 2;

	public static final int GROUP_SEQUENCE = 4;

	public static final int GROUP_CHOICE = 8;

	public static final int GROUP_ALL = 16;

	public static final int SUBSTITUTION = 32;

	public static final int ELEMENT_COMPLEX = 64;

	public static final int ELEMENT_SIMPLE = 128;

	public static final int ELEMENT_EXT_SIMPLE = 256;

	public static final int ELEMENT_EMPTY = 512;

	public static final int ELEMENT_EXT_EMPTY = 1024;

	public static final int ATTRIBUTE = 2048;

	public static final int ANY_ARRAY = 3;

	public static final int ANY_ELEMENT = 1984;

	public static final int ANY_GROUP = 28;

	public static final int ANY_NODE_WITH_VALUE = 2432;

	public static final int ANY_ELEMENT_WITH_CHILD_NODES = 1344;

	public static final int ANY_ELEMENT_WITH_ATTRIBUTES = 1280;

	private int type;

	private XSObject xsNode;
	private boolean isRequired=false;

	public XSObjectRef(int type, XSObject xsNode)
	{
		this.type = type;
		this.xsNode = xsNode;
	}
	public XSObjectRef(int type, XSObject xsNode,boolean isRequired)
	{
		this.type = type;
		this.xsNode = xsNode;
		this.isRequired=isRequired;
	}
	public int getType()
	{
		return this.type;
	}

	public boolean in(int types)
	{
		return (this.type & types) > 0;
	}

	public XSObject getXSNode()
	{
		return this.xsNode;
	}

	public XSParticle getParticle()
	{
		return (XSParticle) this.xsNode;
	}

	public XSElementDeclaration getXSElement()
	{
		return (XSElementDeclaration) this.xsNode;
	}

	public XSModelGroup getXSGroup()
	{
		return (XSModelGroup) this.xsNode;
	}

	public XSAttributeDeclaration getXSAttr()
	{
		return (XSAttributeDeclaration) this.xsNode;
	}
    
	public boolean isRequired()
	{
		return isRequired;
	}
	public boolean isArray()
	{
		return in(ANY_ARRAY);
	}

	public boolean isGroup()
	{
		return in(ANY_GROUP);
	}

	public boolean isElement()
	{
		return in(ANY_ELEMENT);
	}

	public boolean isElementWithChildNodes()
	{
		return in(ANY_ELEMENT_WITH_CHILD_NODES);
	}

	public boolean isElementWithAttributes()
	{
		return in(ANY_ELEMENT_WITH_ATTRIBUTES);
	}

	public boolean isAttribute()
	{
		return in(ATTRIBUTE);
	}

	public boolean hasValue()
	{
		return in(ANY_NODE_WITH_VALUE);
	}

	public String validate(Object value)
	{
		String sValue = value == null ? "" : value.toString();
		XSSimpleTypeDefinition std = null;
		if (this.type == ATTRIBUTE)
		{
			XSAttributeDeclaration ad = (XSAttributeDeclaration) this.xsNode;
			std = ad.getTypeDefinition();
		} else if (this.type == ELEMENT_SIMPLE)
		{
			XSElementDeclaration ed = (XSElementDeclaration) this.xsNode;
			std = (XSSimpleTypeDefinition) ed.getTypeDefinition();
		} else if (this.type == ELEMENT_EXT_SIMPLE)
		{
			XSElementDeclaration ed = (XSElementDeclaration) this.xsNode;
			XSComplexTypeDefinition ctd = (XSComplexTypeDefinition) ed
					.getTypeDefinition();
			std = ctd.getSimpleType();
		}
		if ((std instanceof XSSimpleType))
		{
			try
			{
				((XSSimpleType) std)
						.validate(sValue, null, new ValidatedInfo());
			} catch (InvalidDatatypeValueException ex)
			{
				return ex.getMessage();
			}
		}
		return null;
	}
	@Override
	public String toString()
	{
		return "type="+type+ " name="+xsNode.getName()+" namespace="+xsNode.getNamespace();
	}
}