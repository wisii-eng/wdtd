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
/* $Id: InstreamForeignObjectLM.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.layoutmanager.inline;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.inline.ForeignObject;
import com.wisii.wisedoc.document.svg.Canvas;


/**
 * LayoutManager for the fo:instream-foreign-object formatting object
 */
public class InstreamForeignObjectLM extends AbstractGraphicsLayoutManager
{

	private Canvas fobj;

	/**
	 * Constructor
	 * 
	 * @param node
	 *            the formatting object that creates this area
	 */
	public InstreamForeignObjectLM(Canvas node)
	{
		super(node);
		fobj = node;
	}

	/**
	 * Get the inline area created by this element.
	 * 
	 * @return the inline area
	 */
	protected Area getChildArea()
	{
		return new ForeignObject(fobj.getSVGDocument(), fobj.getNamespaceURI());
	}

}

