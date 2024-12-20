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
/* $Id: BorderElement.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.layoutmanager;

import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.datatype.PercentBaseContext;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.traits.MinOptMax;



/**
 * This represents an unresolved border element.
 */
public class BorderElement extends BorderOrPaddingElement {

    /**
     * Main constructor
     * @param position the Position instance needed by the addAreas stage of the LMs.
     * @param side the side to which this space element applies.
     * @param condLength the length-conditional property for a border or padding specification
     * @param isFirst true if this is a padding- or border-before of the first area generated.
     * @param isLast true if this is a padding- or border-after of the last area generated.
     * @param context the property evaluation context
     */
    public BorderElement(Position position, CondLengthProperty condLength,
            RelSide side,
            boolean isFirst, boolean isLast, PercentBaseContext context) {
        super(position, condLength, side, isFirst, isLast, context);
    }

    /** @see com.wisii.wisedoc.layoutmanager.UnresolvedListElementWithLength */
    public void notifyLayoutManager(MinOptMax effectiveLength) {
        LayoutManager lm = getOriginatingLayoutManager();
        if (lm instanceof ConditionalElementListener) {
            ((ConditionalElementListener)lm).notifyBorder(
                    getSide(), effectiveLength);
        } else {
            LogUtil.warn("Cannot notify LM. It does not implement ConditionalElementListener: "
                    + lm.getClass().getName());
        }
    }

    /** @see java.lang.Object#toString() */
    public String toString() {
        StringBuffer sb = new StringBuffer("Border[");
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }
    public boolean equals(Object obj)
    {
    	return super.equals(obj);
    }

}
