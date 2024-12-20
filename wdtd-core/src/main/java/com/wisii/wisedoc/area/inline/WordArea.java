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
/* $Id: WordArea.java,v 1.1 2007/04/12 06:41:18 cvsuser Exp $ */
package com.wisii.wisedoc.area.inline;

/**
 * A string of characters without spaces
 */
public class WordArea extends InlineArea {
	/** The text for this word area */
    protected String word;

    /** The correction offset for the next area */
    protected int offset = 0;

    /** An array of width for adjusting the individual letters (optional) */
    protected int[] letterAdjust;

	//【添加：END】 by 李晓光 2010-1-20
	
    public int getElementCount() {
		/*return elementCount;*/
    	return word.length();
	}
    
	//【添加：END】 by 李晓光 2010-1-20
    /**
     * Create a word area
     * @param w the word string
     * @param o the offset for the next area
     * @param la the letter adjust array (may be null)
     */
    public WordArea(final String w, final int o, final int[] la) {
        word = w;
        offset = o;
        this.letterAdjust = la;
    }

    /**
     * @return Returns the word.
     */
    public String getWord() {
        return word;
    }

    /**
     * @return Returns the offset.
     */
    @Override
	public int getOffset() {
        return offset;
    }
    /**
     * @param o The offset to set.
     */
    @Override
	public void setOffset(final int o) {
        offset = o;
    }

    /** @return the array of letter adjust widths */
    public int[] getLetterAdjustArray() {
        return this.letterAdjust;
    }

}
