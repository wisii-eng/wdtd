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
package com.wisii.wisedoc.view.ui.model;

import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;


public class IndexedUIStateChangeEvent extends UIStateChangeEvent{
	
	private int index;

    /**
     * Constructs a new <code>IndexedPropertyChangeEvent</code> object.
     *
     * @param source  The bean that fired the event.
     * @param propertyName  The programmatic name of the property that
     *             was changed.
     * @param oldValue      The old value of the property.
     * @param newValue      The new value of the property.
     * @param index index of the property element that was changed.
     */
    public IndexedUIStateChangeEvent(Object source, String propertyName,
				      Object oldValue, Object newValue,
				      int index) {
	super (source, propertyName, oldValue, newValue);
	this.index = index;
    }
    
    
    
    public IndexedUIStateChangeEvent(Object source, ActionType actionType, int propertyKey,
		     Object oldValue, Object newValue,
			      int index) {
		super(source, actionType, propertyKey, oldValue, newValue);
		this.index = index;
	}


    /**
     * Gets the index of the property that was changed.
     *
     * @return The index specifying the property element that was
     *         changed.
     */
    public int getIndex() {
	return index;
    }
    
}
