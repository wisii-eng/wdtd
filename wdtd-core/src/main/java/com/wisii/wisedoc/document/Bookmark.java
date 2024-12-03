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
 * @Bookmark.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 类功能描述：The fo:bookmark formatting object, first introduced in the
 * XSL 1.1 WD.  Prototype version only, subject to change as
 * XSL 1.1 WD evolves.
 * 
 * 作者：李晓光 创建日期：2009-4-7
 */
public class Bookmark extends DefaultElement {
	private BookmarkTitle bookmarkTitle;
	public Bookmark(){
		this(null);
	}
	public Bookmark(final Map<Integer,Object> attributes){
		super(attributes);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Element#add(com.wisii.wisedoc.document.Element
	 * )
	 */
	public void add(CellElement newChild){
		if(newChild instanceof BookmarkTitle){
			this.bookmarkTitle = (BookmarkTitle)newChild;
			this.bookmarkTitle.setParent(this);
		}else{
			super.add(newChild);
		}
	}
	public List<CellElement> getChildBookmarks() {
		
        return new ArrayList<CellElement>(getAllChildren());
    }
	 /**
     * Determines if this fo:bookmark's subitems should be initially displayed
     * or hidden, based on the starting-state property set on this FO.
     *
     * @return true if this bookmark's starting-state is "show", false if "hide".
     */
    public boolean showChildItems() {
    	/*EnumProperty show = (EnumProperty) getAttribute(Constants.PR_STARTING_STATE);*/
    	/*return (show.getEnum() == Constants.EN_SHOW);*/
    	Integer show = (Integer)getAttribute(Constants.PR_STARTING_STATE);
    	return (show == Constants.EN_SHOW);
    }
    /**
     * Get the bookmark title for this bookmark
     *
     * @return the bookmark title string or an empty string if not found
     */
    public String getBookmarkTitle() {
        return bookmarkTitle == null ? "" : bookmarkTitle.getTitle();
    }
    
    public String getInternalDestination() {
    	/*Property prop = (Property)getAttribute(Constants.PR_INTERNAL_DESTINATION);
    	
    	return prop.getString();*/
    	Object obj = getAttribute(Constants.PR_INTERNAL_DESTINATION);
    	if(obj == null)
    		return "";
    	return "" + obj;
    }
    public String getExternalDestination() {
    	/*Property prop = (Property)getAttribute(Constants.PR_EXTERNAL_DESTINATION);
    	
    	return prop.getString();*/
    	Object obj = getAttribute(Constants.PR_EXTERNAL_DESTINATION);
    	if(obj == null)
    		return "";
    	return "" + obj;
    }
	  /** @see com.wisii.fov.fo.FONode#getLocalName() */
    public String getLocalName() {
        return "bookmark";
    }

    /**
     * @see com.wisii.fov.fo.FObj#getNameId()
     */
    public int getNameId() {
        return Constants.FO_BOOKMARK;
    }
}
