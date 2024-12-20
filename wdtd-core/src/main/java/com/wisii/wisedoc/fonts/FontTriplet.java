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

/* $Id: FontTriplet.java 426576 2006-07-28 15:44:37Z jeremias $ */
 
package com.wisii.wisedoc.fonts;

import java.io.Serializable;

/**
 * FontTriplet contains information on name, style and weight of one font
 */
public class FontTriplet implements Comparable, Serializable {
    
    /** serial version UID */
    private static final long serialVersionUID = 1168991106658033508L;
    
    private String name;
    private String style;
    private int weight;
    
    //This is only a cache
    private transient String key;
    
    /**
     * Creates a new font triplet.
     * @param name font name
     * @param style font style (normal, italic etc.)
     * @param weight font weight (100, 200, 300...800, 900)
     */
    public FontTriplet(String name, String style, int weight) {
        this.name = name;
        this.style = style;
        this.weight = weight;
    }

    /** @return the font name */
    public String getName() {
        return name;
    }

    /** @return the font style */
    public String getStyle() {
        return style;
    }
    
    /** @return the font weight */
    public int getWeight() {
        return weight;
    }

    private String getKey() {
        if (this.key == null) {
            //This caches the combined key
            this.key = getName() + "," + getStyle() + "," + getWeight();
        }
        return this.key;
    }
    
    /** @see java.lang.Comparable#compareTo(java.lang.Object) */
    public int compareTo(Object o) {
        return getKey().compareTo(((FontTriplet)o).getKey());
    }

    /** @see java.lang.Object#hashCode() */
    public int hashCode() {
        return toString().hashCode();
    }

    /** @see java.lang.Object#equals(java.lang.Object) */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else {
            if (obj instanceof FontTriplet) {
                FontTriplet other = (FontTriplet)obj;
                return (getName().equals(other.getName())
                        && getStyle().equals(other.getStyle()) 
                        && (getWeight() == other.getWeight()));
            }
        }
        return false;
    }

    /** @see java.lang.Object#toString() */
    public String toString() {
        return getKey();
    }

}

