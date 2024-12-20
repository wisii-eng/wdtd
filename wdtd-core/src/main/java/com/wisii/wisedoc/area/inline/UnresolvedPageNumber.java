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
/* $Id: UnresolvedPageNumber.java,v 1.1 2007/04/12 06:41:18 cvsuser Exp $ */

package com.wisii.wisedoc.area.inline;



import java.util.List;

import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.area.Resolvable;
import com.wisii.wisedoc.fonts.Font;
import com.wisii.wisedoc.log.LogUtil;

/**
 * Unresolvable page number area.
 * This is a word area that resolves itself to a page number
 * from an id reference.
 */
public class UnresolvedPageNumber extends TextArea implements Resolvable {

    private boolean resolved = false;
    private String pageIDRef;
    private String text;
    private boolean pageType;

    /** Indicates that the reference refers to the first area generated by a formatting object. */
    public static final boolean FIRST = true;
    /** Indicates that the reference refers to the last area generated by a formatting object. */
    public static final boolean LAST = false;

    //Transient fields
    private transient Font font;

    /**
     * Create a new unresolved page number.
     *
     * @param id the id reference for resolving this
     * @param f  the font for formatting the page number
     */
    public UnresolvedPageNumber(String id, Font f) {
        this(id, f, FIRST);
    }

    /**
     * Create a new unresolved page number.
     *
     * @param id   the id reference for resolving this
     * @param f    the font for formatting the page number
     * @param type indicates whether the reference refers to the first or last area generated by
     *             a formatting object
     */
    public UnresolvedPageNumber(String id, Font f, boolean type) {
        pageIDRef = id;
        font = f;
        text = "??";
        pageType = type;
    }

    /**
     * Get the id references for this area.
     *
     * @return the id reference for this unresolved page number
     */
    public String[] getIDRefs() {
        return new String[] {pageIDRef};
    }

    /**
     * Resolve the page number idref
     * This resolves the idref for this object by getting the page number
     * string from the first page in the list of pages that apply
     * for this ID.  The page number text is then set to the String value
     * of the page number.
     *
     * @param id an id whose PageViewports have been determined
     * @param pages the list of PageViewports associated with this ID
     */
    public void resolveIDRef(String id, List pages) {
        if (!resolved && pageIDRef.equals(id) && pages != null) {
            LogUtil.debug("Resolving pageNumber: " + id);
            
            resolved = true;
            PageViewport page;
            if (pageType == FIRST) {
                page = (PageViewport)pages.get(0);
            } else {
                page = (PageViewport)pages.get(pages.size() - 1);
            }
            // replace the text
            removeText();
            addWord(page.getPageNumberString(), 0);
            // update ipd
            if (font != null) {
                updateIPD(font.getWordWidth(text));
                // set the Font object to null, as we don't need it any more
                font = null;
            } else {
                LogUtil.warn("Cannot update the IPD of an unresolved page number."
                        + " No font information available.");
            }
        }
    }

    /**
     * Check if this is resolved.
     *
     * @return true when this has been resolved
     */
    public boolean isResolved() {
       return resolved;
    }

    /**
     * recursively apply the variation factor to all descendant areas
     * @param variationFactor the variation factor that must be applied to adjustment ratios
     * @param lineStretch     the total stretch of the line
     * @param lineShrink      the total shrink of the line
     * @return true if there is an UnresolvedArea descendant
     */
    public boolean applyVariationFactor(double variationFactor,
                                        int lineStretch, int lineShrink) {
        return true;
    }
}
