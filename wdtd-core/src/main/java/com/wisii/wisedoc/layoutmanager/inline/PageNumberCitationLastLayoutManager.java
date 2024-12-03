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
/* $Id: PageNumberCitationLastLayoutManager.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.layoutmanager.inline;

import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.area.Resolvable;
import com.wisii.wisedoc.area.inline.InlineArea;
import com.wisii.wisedoc.area.inline.TextArea;
import com.wisii.wisedoc.area.inline.UnresolvedPageNumber;
import com.wisii.wisedoc.document.PageNumberCitationLast;
import com.wisii.wisedoc.layoutmanager.LayoutContext;
import com.wisii.wisedoc.layoutmanager.LayoutManager;

/**
 * LayoutManager for the fo:page-number-citation-last formatting object
 */
public class PageNumberCitationLastLayoutManager extends
		PageNumberCitationLayoutManager
{

	private PageNumberCitationLast fobj;

	/**
	 * Constructor
	 * 
	 * @param node
	 *            the formatting object that creates this area
	 * @todo better retrieval of font info
	 */
	public PageNumberCitationLastLayoutManager(PageNumberCitationLast node)
	{
		super(node);
		fobj = node;
	}

	/** @see com.wisii.wisedoc.layoutmanager.inline.LeafNodeLayoutManager#get(LayoutContext) */
	public InlineArea get(LayoutContext context)
	{
		curArea = getPageNumberCitationLastInlineArea(parentLM);
		return curArea;
	}

	/**
	 * if id can be resolved then simply return a word, otherwise return a
	 * resolvable area
	 */
	private InlineArea getPageNumberCitationLastInlineArea(
			LayoutManager parentLM)
	{
		TextArea text = null;
		resolved = false;
		if (!getPSLM().associateLayoutManagerID(fobj.getRefId()))
		{
			text = new UnresolvedPageNumber(fobj.getRefId(), font,
					UnresolvedPageNumber.LAST);
			getPSLM().addUnresolvedArea(fobj.getRefId(), (Resolvable) text);
			String str = "MMM"; // reserve three spaces for page number
			int width = getStringWidth(str);
			text.setIPD(width);
		} else
		{
			PageViewport page = getPSLM().getLastPVWithID(fobj.getRefId());
			String str = page.getPageNumberString();
			// get page string from parent, build area
			text = new TextArea();
			int width = getStringWidth(str);
			text.addWord(str, 0);
			text.setIPD(width);
			resolved = true;
		}		
		updateTextAreaTraits(text);
		 /* 【添加：START】by 李晓光 2008-11-5 */
        text.setSource(fobj);
        fobj.setArea(text);
        text.setWhole(Boolean.TRUE);
        /* 【添加：END】 by 李晓光  2008-11-5 */
		return text;
	}
}
