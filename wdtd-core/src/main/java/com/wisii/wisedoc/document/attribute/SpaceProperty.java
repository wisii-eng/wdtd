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
/**
 * @SimplePageMaster.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.Constants;

/**
 * Base class used for handling properties of the fo:space-before and
 * fo:space-after variety. It is extended by
 * com.wisii.fov.fo.properties.GenericSpace, which is extended by many other
 * properties.
 */
public class SpaceProperty extends LengthRangeProperty
{

	private EnumNumber precedence;

	private EnumProperty conditionality;

	public SpaceProperty(LengthProperty length, EnumNumber precedence,
			EnumProperty conditionality)
	{
		super(length);
		this.precedence = precedence;
		this.conditionality = conditionality;

	}

	public SpaceProperty(LengthProperty minimum, LengthProperty optimum,
			LengthProperty maximum, EnumNumber precedence,
			EnumProperty conditionality)
	{
		super(minimum, optimum, maximum);
		this.precedence = precedence;
		this.conditionality = conditionality;
	}

	public SpaceProperty(LengthProperty minimum, LengthProperty optimum,
			LengthProperty maximum, int bfset, EnumNumber precedence,
			EnumProperty conditionality)
	{
		super(minimum, optimum, maximum, bfset);
		this.precedence = precedence;
		this.conditionality = conditionality;
	}

	/**
	 * 
	 * @param precedence
	 *            precedence Property to set
	 * @param bIsDefault
	 *            (is not used anywhere)
	 */
	protected void setPrecedence(EnumNumber precedence, boolean bIsDefault)
	{
		this.precedence = precedence;
	}

	/**
	 * 
	 * @param conditionality
	 *            conditionality Property to set
	 * @param bIsDefault
	 *            (is not used anywhere)
	 */
	protected void setConditionality(EnumProperty conditionality,
			boolean bIsDefault)
	{
		this.conditionality = conditionality;
	}

	/**
	 * @return precedence Property
	 */
	public EnumNumber getPrecedence()
	{
		return this.precedence;
	}

	/**
	 * @return conditionality Property
	 */
	public EnumProperty getConditionality()
	{
		return this.conditionality;
	}

	/**
	 * Indicates if the length can be discarded on certain conditions.
	 * 
	 * @return true if the length can be discarded.
	 */
	public boolean isDiscard()
	{
		return this.conditionality.getEnum() == Constants.EN_DISCARD;
	}

	public String toString()
	{
		return "Space[" + "min:" + getMinimum(null) + ", max:"
				+ getMaximum(null) + ", opt:" + getOptimum(null)
				+ ", precedence:" + precedence + ", conditionality:"
				+ conditionality + "]";
	}

	/**
	 * @return the Space (datatype) object contained here
	 */
	public SpaceProperty getSpace()
	{
		return this;
	}

	/**
	 * Space extends LengthRange.
	 * 
	 * @return the Space (datatype) object contained here
	 */
	public LengthRangeProperty getLengthRange()
	{
		return this;
	}

	/**
	 * @return the Space (datatype) object contained here
	 */
	public Object getObject()
	{
		return this;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof SpaceProperty))
		{
			return false;
		}
		SpaceProperty sp = (SpaceProperty) obj;
		return (conditionality == null ? sp.conditionality == null
				: (conditionality.equals(sp.conditionality)))
				&& (precedence == null ? sp.precedence == null : (precedence
						.equals(sp.precedence))) && super.equals(sp);
	}

	public SpaceProperty clone()
	{
		LengthProperty minimum = this.getMinimum(null).clone();

		LengthProperty optimum = this.getOptimum(null).clone();

		LengthProperty maximum = this.getMaximum(null).clone();

		// int bfset = GetBooleanAction();
		EnumNumber precedence = new EnumNumber(this.getPrecedence().getEnum(),
				this.getPrecedence().getNumber());
		EnumProperty conditionality = new EnumProperty(this.getConditionality()
				.getEnum(), new String((String) this.getConditionality()
				.getObject()));
		SpaceProperty newspace;
		// if (bfset == 0)
		// {
		newspace = new SpaceProperty(minimum, optimum, maximum, precedence,
				conditionality);
		// } else
		// {
		// newspace = new SpaceProperty(minimum, optimum, maximum, bfset,
		// precedence, conditionality);
		// }
		return newspace;
	}
}
