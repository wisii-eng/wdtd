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
/* $Id: CommonHyphenation.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;

/**
 * Store all common hyphenation properties.
 * See Sec. 7.9 of the XSL-FO Standard.
 * Public "structure" allows direct member access.
 */
public final class CommonHyphenation extends AbstractCommonAttributes
{
	public CommonHyphenation(CellElement cellelement)
	{
		super(cellelement);
	}

	/**
	 * @返回 language变量的值
	 */
	public final String getLanguage()
	{
		return (String) getAttribute(Constants.PR_LANGUAGE);
	}

	/**
	 * @返回 country变量的值
	 */
	public final String getCountry()
	{
		return (String) getAttribute(Constants.PR_COUNTRY);
	}

	/**
	 * @返回 script变量的值
	 */
	public final String getScript()
	{
		return (String) getAttribute(Constants.PR_SCRIPT);
	}

	/**
	 * @返回 hyphenate变量的值
	 */
	public final int getHyphenate()
	{
		return ((EnumProperty) getAttribute(Constants.PR_HYPHENATE)).getEnum();
	}

	/**
	 * @返回 hyphenationCharacter变量的值
	 */
	public final char getHyphenationCharacter()
	{
		return (Character) getAttribute(Constants.PR_HYPHENATION_CHARACTER);
	}

	/**
	 * @返回 hyphenationPushCharacterCount变量的值
	 */
	public final int getHyphenationPushCharacterCount()
	{
		return ((NumberProperty) getAttribute(Constants.PR_HYPHENATION_PUSH_CHARACTER_COUNT))
				.getNumber().intValue();
	}

	/**
	 * @返回 hyphenationRemainCharacterCount变量的值
	 */
	public final int getHyphenationRemainCharacterCount()
	{
		return ((NumberProperty) getAttribute(Constants.PR_HYPHENATION_REMAIN_CHARACTER_COUNT))
				.getNumber().intValue();
	}
}
