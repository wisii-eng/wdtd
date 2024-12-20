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
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.wisii.com/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: Property.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.attribute;
import java.awt.Color;
import java.util.List;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.Numeric;

/**
 * Base class for all property objects
 */
public class Property {
    

    /**
     * The original specified value for properties which inherit
     * specified values.
     */
    private String specVal;

    /**
     * Set the original value specified for the property attribute.
     * @param value The specified value.
     */
    public void setSpecifiedValue(String value) {
        this.specVal = value;
    }

    /**
     * Return the original value specified for the property attribute.
     * @return The specified value as a String.
     */
    public String getSpecifiedValue() {
        return specVal;
    }

/*
 * This section contains accessor functions for all possible Property datatypes
 */


/**
 * 
 */
    public Length getLength() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return ColorType property value
     */
    public Color getColor() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return CondLength property value
     */
    public CondLengthProperty getCondLength() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return LenghtRange property value
     */
    public LengthRangeProperty getLengthRange() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return LengthPair property value
     */
    public LengthPairProperty getLengthPair() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return Space property value
     */
    public SpaceProperty getSpace() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return Keep property value
     */
    public KeepProperty getKeep() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return integer equivalent of enumerated property value
     */
    public int getEnum() {
        return 0;
    }

    /** @return true if the property is an enum and has value 'auto' */
    public boolean isAuto() {
        return (getEnum() == Constants.EN_AUTO);
    }
    
    /**
     * This method expects to be overridden by subclasses
     * @return char property value
     */
    public char getCharacter() {
        return 0;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return collection of other property (sub-property) objects
     */
    public List getList() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return Number property value
     */
    public Number getNumber() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return Numeric property value
     */
    public Numeric getNumeric() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return NCname property value
     */
    public String getNCname() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses
     * @return Object property value
     */
    public Object getObject() {
        return null;
    }

    /**
     * This method expects to be overridden by subclasses.
     * @return String property value
     */
    public String getString() {
        return null;
    }

    /** @see java.lang.Object#toString() */
    public String toString() {
        Object obj = getObject();
        if (obj != this) {
            return obj.toString();
        }
        return null;
    }
}
