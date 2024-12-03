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
 * @TableFObj.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.Map;

import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.datatype.Numeric;
/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-28
 */
public abstract class TableFObj extends DefaultElement{

    /**
     * 初始化过程的描述
     * 
     * @param 初始化参数说明
     * @exception {说明在某情况下,将发生什么异常}
     */
    public TableFObj() {
	// TODO Auto-generated constructor stub
    }

    /**
     * 初始化过程的描述
     * 
     * @param 初始化参数说明
     * @exception {说明在某情况下,将发生什么异常}
     */
    public TableFObj(final Map<Integer,Object> attributes) {
	super(attributes);
	// TODO Auto-generated constructor stub
    }

    /**
     * 
     * @param side
     *            the side for which to return the border precedence
     * @return the "border-precedence" value for the given side
     */
    public Numeric getBorderPrecedence(int side) {
	switch (side) {
	case CommonBorderPaddingBackground.BEFORE:
	    return (Numeric) getAttribute(Constants.PR_BORDER_BEFORE_PRECEDENCE);
	case CommonBorderPaddingBackground.AFTER:
	    return (Numeric) getAttribute(Constants.PR_BORDER_AFTER_PRECEDENCE);
	case CommonBorderPaddingBackground.START:
	    return (Numeric) getAttribute(Constants.PR_BORDER_START_PRECEDENCE);
	case CommonBorderPaddingBackground.END:
	    return (Numeric) getAttribute(Constants.PR_BORDER_END_PRECEDENCE);
	default:
	    return null;
	}
    }

    /**
     * Returns the current column index of the given TableFObj (overridden for
     * Table, TableBody, TableRow)
     * 
     * @return the next column number to use
     */
    protected int getCurrentColumnIndex() {
	return 0;
    }

    /**
     * Sets the current column index of the given TableFObj used when a value
     * for column-number is explicitly specified on the child FO (TableCell or
     * TableColumn) (overridden for Table, TableBody, TableRow)
     * 
     * @param newIndex
     *            new value for column index
     */
    protected void setCurrentColumnIndex(int newIndex) {
	// do nothing by default
    }

    /**
     * Checks if a certain column-number is already occupied (overridden for
     * Table, TableBody, TableRow)
     * 
     * @param colNr
     *            the column-number to check
     * @return true if column-number is already in use
     */
    public boolean isColumnNumberUsed(int colNr) {
	return false;
    }

    /**
     * Convenience method to returns a reference to the base Table instance
     * 
     * @return the base table instance
     * 
     */
    public Table getTable() {
	if (this.getNameId() == Constants.FO_TABLE) {
	    // node is a Table
	    // => return itself
	    return (Table) this;
	} else {
	    // any other Table-node
	    // => recursive call to parent.getTable()
	    return ((TableFObj) _parent).getTable();
	}
    }

    /**
     * @return the Common Border, Padding, and Background Properties.
     */
    public abstract CommonBorderPaddingBackground getCommonBorderPaddingBackground();

    /**
     * Flags column indices from <code>start</code> to <code>end</code>, and
     * updates the current column index. Overridden for Table, TableBody,
     * TableRow
     * 
     * @param start
     *            start index
     * @param end
     *            end index
     */
    protected void flagColumnIndices(int start, int end) {
	// nop
    }

    /**
     * Used for determining initial values for column-numbers in case of
     * row-spanning cells (for clarity)
     * 
     */
    protected static class PendingSpan {

	/**
	 * member variable holding the number of rows left
	 */
	protected int rowsLeft;

	/**
	 * Constructor
	 * 
	 * @param rows
	 *            number of rows spanned
	 */
	public PendingSpan(int rows) {
	    rowsLeft = rows;
	}
    }
}
