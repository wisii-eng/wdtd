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
 * @BookmarkTitle.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.document;

import java.util.Map;

/**
 * 类功能描述：The fo:bookmark-title formatting object, first introduced in the
 * XSL 1.1 WD.  Prototype version only, subject to change as XSL 1.1 WD
 * evolves.
 * 
 * 作者：李晓光 创建日期：2009-4-7
 */
public class BookmarkTitle extends DefaultElement {
	
	public BookmarkTitle(){
		
	}
	public BookmarkTitle(final Map<Integer,Object> attributes){
		super(attributes);
	}
	/**
     * Get the title for this BookmarkTitle.
     *
     * @return the bookmark title
     */
    public String getTitle() {
    	/*Property prop = (Property)getAttribute(Constants.PR_BOOKMARK_TITLE);
    	return prop.getString();*/
    	Object obj = getAttribute(Constants.PR_BOOKMARK_TITLE);
    	return (obj == null)? "" : obj.toString();
    }
    /** @see com.wisii.fov.fo.FONode#getLocalName() */
    public String getLocalName() {
        return "bookmark-title";
    }

    /**
     * @see com.wisii.fov.fo.FObj#getNameId()
     */
    public int getNameId() {
        return Constants.FO_BOOKMARK_TITLE;
    }
}
