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


public class UIStateChangeListenerProxy extends java.util.EventListenerProxy
implements UIStateChangeListener {
    private String propertyName;
    private int propertyKey;

    /**
     * Constructor which binds the PropertyChangeListener to a specific
     * property.
     * 
     * @param listener The listener object
     * @param propertyName The name of the property to listen on.
     */ 
    @Deprecated
    public UIStateChangeListenerProxy(String propertyName, 
    		UIStateChangeListener listener) {
        // XXX - msd NOTE: I changed the order of the arguments so that it's
        // similar to PropertyChangeSupport.addPropertyChangeListener(String,
        // PropertyChangeListener);
        super(listener);
        this.propertyName = propertyName;
    }
    
    public UIStateChangeListenerProxy(int propertyKey, 
    		UIStateChangeListener listener) {
        // XXX - msd NOTE: I changed the order of the arguments so that it's
        // similar to PropertyChangeSupport.addPropertyChangeListener(String,
        // PropertyChangeListener);
        super(listener);
        this.propertyKey = propertyKey;
    }

    /**
     * Forwards the property change event to the listener delegate.
     *
     * @param evt the property change event
     */
    public void stateChange(UIStateChangeEvent evt) {
        ((UIStateChangeListener)getListener()).stateChange(evt);
    }

    /**
     * Returns the name of the named property associated with the
     * listener.
     */
    public String getPropertyName() {
        return propertyName;
    }

	public int getPropertyKey() {
		return propertyKey;
	}
    
   
}
