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
/* $Id: CommonTextDecoration.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.document.attribute;
import java.awt.Color;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;

/**
 * Stores all information concerning text-decoration.
 */
public final class CommonTextDecoration extends AbstractCommonAttributes
{

	// using a bit-mask here
	public static final int UNDERLINE = 1;

	public static final int OVERLINE = 2;

	public static final int LINE_THROUGH = 4;

	public static final int BLINK = 8;

	private  int decoration;

	// public CommonTextDecoration(Attributes attributes)
	// {
	// int decoration = (Integer) attributes
	// .getAttribute(Constants.PR_TEXT_DECORATION);
	// Color underColor = (Color) attributes.getAttribute(Constants.PR_COLOR);
	// Color overColor = underColor;
	// Color throughColor = underColor;
	// init(decoration, underColor, overColor, throughColor);
	// }

	/**
	 * Creates a new CommonTextDecoration object with default values.
	 */
	public CommonTextDecoration(CellElement cellelement)
	{
		super(cellelement);
		init(cellelement);
	}
    @Override
    public void init(CellElement cellelement)
    {
    	// TODO Auto-generated method stub
    	super.init(cellelement);
    	decoration = (Integer) getAttribute(Constants.PR_TEXT_DECORATION);
    }
	/** @return true if underline is active */
	public boolean hasUnderline()
	{
		return (this.decoration & UNDERLINE) != 0;
	}

	/** @return true if overline is active */
	public boolean hasOverline()
	{
		return (this.decoration & OVERLINE) != 0;
	}

	/** @return true if line-through is active */
	public boolean hasLineThrough()
	{
		return (this.decoration & LINE_THROUGH) != 0;
	}

	/** @return true if blink is active */
	public boolean isBlinking()
	{
		return (this.decoration & BLINK) != 0;
	}

	/** @return the color of the underline mark */
	public Color getUnderlineColor()
	{
		// to add realy implement;
		return (Color) getAttribute(Constants.PR_COLOR);
	}

	/** @return the color of the overline mark */
	public Color getOverlineColor()
	{
		// to add realy implement;
		return (Color) getAttribute(Constants.PR_COLOR);
	}

	/** @return the color of the line-through mark */
	public Color getLineThroughColor()
	{
		// to add realy implement;
		return (Color) getAttribute(Constants.PR_COLOR);
	}

}
