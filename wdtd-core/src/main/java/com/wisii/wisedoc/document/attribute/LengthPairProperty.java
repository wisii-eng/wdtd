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
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.wisii.com/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: LengthPairProperty.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.attribute;

/**
 * Superclass for properties wrapping a LengthPair value
 */
public class LengthPairProperty extends Property
{
	private Property ipd;

	private Property bpd;

	/**
	 * Creates a new LengthPairProperty with empty values.
	 */
	public LengthPairProperty()
	{
		super();
	}

	/**
	 * Creates a new LengthPairProperty.
	 * 
	 * @param ipd
	 *            inline-progression-dimension
	 * @param bpd
	 *            block-progression-dimension
	 */
	public LengthPairProperty(Property ipd, Property bpd)
	{
		this();
		this.ipd = ipd;
		this.bpd = bpd;
	}

	/**
	 * Creates a new LengthPairProperty which sets both bpd and ipd to the same
	 * value.
	 * 
	 * @param len
	 *            length for both dimensions
	 */
	public LengthPairProperty(Property len)
	{
		this(len, len);
	}

	/**
	 * @return Property holding the ipd length
	 */
	public Property getIPD()
	{
		return this.ipd;
	}

	/**
	 * @return Property holding the bpd length
	 */
	public Property getBPD()
	{
		return this.bpd;
	}

	/** @see java.lang.Object#toString() */
	public String toString()
	{
		return "LengthPair[" + "ipd:" + getIPD().getObject() + ", bpd:"
				+ getBPD().getObject() + "]";
	}

	/**
	 * @return this.lengthPair
	 */
	public LengthPairProperty getLengthPair()
	{
		return this;
	}

	/**
	 * @return this.lengthPair cast as an Object
	 */
	public Object getObject()
	{
		return this;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof LengthPairProperty))
		{
			return false;
		}
		LengthPairProperty lpp = (LengthPairProperty) obj;
		return ipd == null ? lpp.ipd == null : ipd.equals(lpp.ipd)
				&& bpd == null ? lpp.bpd == null : bpd.equals(lpp.bpd);
	}
}
