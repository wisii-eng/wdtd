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

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;

/**
 * 这个是属性变化的事件
 * 
 * @author 闫舒寰
 *
 */
public class UIStateChangeEvent extends java.util.EventObject {
	/**
     * Constructs a new <code>PropertyChangeEvent</code>.
     *
     * @param source  The bean that fired the event.
     * @param propertyName  The programmatic name of the property
     *		that was changed.
     * @param oldValue  The old value of the property.
     * @param newValue  The new value of the property.
     */
	@Deprecated
    public UIStateChangeEvent(final Object source, final String propertyName,
				     final Object oldValue, final Object newValue) {
	super(source);
	this.propertyName = propertyName;
	this.newValue = newValue;
	this.oldValue = oldValue;
    }


    

    /**
     * Gets the programmatic name of the property that was changed.
     *
     * @return  The programmatic name of the property that was changed.
     *		May be null if multiple properties have changed.
     */
/*    public String getPropertyName() {
	return propertyName;
    }*/
    
    /**
     * Gets the new value for the property, expressed as an Object.
     *
     * @return  The new value for the property, expressed as an Object.
     *		May be null if multiple properties have changed.
     */
    public Object getNewValue() {
	return newValue;
    }

    /**
     * Gets the old value for the property, expressed as an Object.
     *
     * @return  The old value for the property, expressed as an Object.
     *		May be null if multiple properties have changed.
     */
    public Object getOldValue() {
	return oldValue;
    }

    /**
     * Sets the propagationId object for the event.
     *
     * @param propagationId  The propagationId object for the event.
     */
    public void setPropagationId(final Object propagationId) {
	this.propagationId = propagationId;
    }

    /**
     * The "propagationId" field is reserved for future use.  In Beans 1.0
     * the sole requirement is that if a listener catches a PropertyChangeEvent
     * and then fires a PropertyChangeEvent of its own, then it should
     * make sure that it propagates the propagationId field from its
     * incoming event to its outgoing event.
     *
     * @return the propagationId object associated with a bound/constrained
     *		property update.
     */
    public Object getPropagationId() {
	return propagationId;
    }

    /**
     * name of the property that changed.  May be null, if not known.
     * @serial
     */
    private String propertyName;

    /**
     * New value for property.  May be null if not known.
     * @serial
     */
    private Object newValue;

    /**
     * Previous value for property.  May be null if not known.
     * @serial
     */
    private Object oldValue;

    /**
     * Propagation ID.  May be null.
     * @serial
     * @see #getPropagationId.
     */
    private Object propagationId;
    
    private ActionType actionType;
    private int propertyKey;
    
    public UIStateChangeEvent(final Object source, final ActionType actionType, final int propertyKey,
		     final Object oldValue, final Object newValue) {
		super(source);
		this.actionType = actionType;
		this.propertyKey = propertyKey;
		this.newValue = newValue;
		this.oldValue = oldValue;
   }

	public ActionType getActionType() {
		return actionType;
	}
	
	@Deprecated
	public int getPropertyKey() {
		return propertyKey;
	}
	
	public boolean hasPropertyKey(final int propertyKey){
		
		if (changeProperties.containsKey(propertyKey)) {
			final Object temp = changeProperties.get(propertyKey);
			
			this.newValue = temp;
			return true;
		} else {
			return false;
		}
		
		/*if (temp != null) {
			this.newValue = temp;
			return true;
		} else {
			return false;
		}*/
		
//		return propertyKey == this.propertyKey;
	}
	
	private Map<ActionType, Map<Integer, Object>> changePropertiseByType = new HashMap<ActionType, Map<Integer,Object>>();
	
	public UIStateChangeEvent(final Object source, final Map<ActionType, Map<Integer, Object>> changePropertiseByType) {
		super(source);
		this.changePropertiseByType = changePropertiseByType;
//		System.out.println("change property: " + changePropertiseByType);
	}
	
	private Map<Integer, Object> changeProperties = new HashMap<Integer, Object>();
	
	public boolean hasActionType(final ActionType actionType){
		if (changePropertiseByType.get(actionType) != null) {
			changeProperties = changePropertiseByType.get(actionType);
			return true;
		} else {
			return false;
		}
	}


	public Map<Integer, Object> getChangeProperties() {
		return changeProperties;
	}


	public Map<ActionType, Map<Integer, Object>> getChangePropertiseByType() {
		return changePropertiseByType;
	}
    
}
