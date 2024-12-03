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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sun.awt.EventListenerAggregate;

import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;

public class UIStateChangeSupport implements java.io.Serializable {
	// Manages the listener list.
	private transient EventListenerAggregate listeners;

	/**
	 * Constructs a <code>PropertyChangeSupport</code> object.
	 * 
	 * @param sourceBean
	 *            The bean to be given as the source for any events.
	 */

	public UIStateChangeSupport(Object sourceBean) {
		if (sourceBean == null) {
			throw new NullPointerException();
		}
		source = sourceBean;
	}

	/**
	 * Add a PropertyChangeListener to the listener list. The listener is
	 * registered for all properties. The same listener object may be added more
	 * than once, and will be called as many times as it is added. If
	 * <code>listener</code> is null, no exception is thrown and no action is
	 * taken.
	 * 
	 * @param listener
	 *            The PropertyChangeListener to be added
	 */
	public synchronized void addUIStateChangeListener(
			UIStateChangeListener listener) {
		if (listener == null) {
			return;
		}

		if (listener instanceof UIStateChangeListenerProxy) {
			UIStateChangeListenerProxy proxy = (UIStateChangeListenerProxy) listener;
			// Call two argument add method.
			addUIStateChangeListener(proxy.getPropertyKey(),
					(UIStateChangeListener) proxy.getListener());
		} else {
			if (listeners == null) {
				listeners = new EventListenerAggregate(
						UIStateChangeListener.class);
			}
			listeners.add(listener);
		}
	}

	/**
	 * Remove a PropertyChangeListener from the listener list. This removes a
	 * PropertyChangeListener that was registered for all properties. If
	 * <code>listener</code> was added more than once to the same event source,
	 * it will be notified one less time after being removed. If
	 * <code>listener</code> is null, or was never added, no exception is thrown
	 * and no action is taken.
	 * 
	 * @param listener
	 *            The PropertyChangeListener to be removed
	 */
	public synchronized void removeUIStateChangeListener(
			UIStateChangeListener listener) {
		if (listener == null) {
			return;
		}

		if (listener instanceof UIStateChangeListenerProxy) {
			UIStateChangeListenerProxy proxy = (UIStateChangeListenerProxy) listener;
			// Call two argument remove method.
			removeUIStateChangeListener(proxy.getPropertyKey(),
					(UIStateChangeListener) proxy.getListener());
		} else {
			if (listeners == null) {
				return;
			}
			listeners.remove(listener);
		}
	}

	/**
	 * Returns an array of all the listeners that were added to the
	 * PropertyChangeSupport object with addPropertyChangeListener().
	 * <p>
	 * If some listeners have been added with a named property, then the
	 * returned array will be a mixture of PropertyChangeListeners and
	 * <code>UIStateChangeListenerProxy</code>s. If the calling method is
	 * interested in distinguishing the listeners then it must test each element
	 * to see if it's a <code>UIStateChangeListenerProxy</code>, perform the
	 * cast, and examine the parameter.
	 * 
	 * <pre>
	 * PropertyChangeListener[] listeners = bean.getPropertyChangeListeners();
	 * for (int i = 0; i &lt; listeners.length; i++) {
	 * 	if (listeners[i] instanceof UIStateChangeListenerProxy) {
	 * 		UIStateChangeListenerProxy proxy = (UIStateChangeListenerProxy) listeners[i];
	 * 		if (proxy.getPropertyName().equals(&quot;foo&quot;)) {
	 * 			// proxy is a PropertyChangeListener which was associated
	 * 			// with the property named &quot;foo&quot;
	 * 		}
	 * 	}
	 * }
	 *</pre>
	 * 
	 * @see UIStateChangeListenerProxy
	 * @return all of the <code>PropertyChangeListeners</code> added or an empty
	 *         array if no listeners have been added
	 * @since 1.4
	 */
	public synchronized UIStateChangeListener[] getUIStateChangeListeners() {
		List returnList = new ArrayList();

		// Add all the UIStateChangeListeners
		if (listeners != null) {
			returnList.addAll(Arrays.asList(listeners.getListenersInternal()));
		}

		// Add all the UIStateChangeListenerProxys
		if (children != null) {
			Iterator iterator = children.keySet().iterator();
			while (iterator.hasNext()) {
				int key = (Integer) iterator.next();
				UIStateChangeSupport child = (UIStateChangeSupport) children
						.get(key);
				UIStateChangeListener[] childListeners = child
						.getUIStateChangeListeners();
				for (int index = childListeners.length - 1; index >= 0; index--) {
					returnList.add(new UIStateChangeListenerProxy(key,
							childListeners[index]));
				}
			}
		}
		return (UIStateChangeListener[]) (returnList
				.toArray(new UIStateChangeListener[0]));
	}

	/**
	 * Add a UIStateChangeListener for a specific property. The listener will be
	 * invoked only when a call on firePropertyChange names that specific
	 * property. The same listener object may be added more than once. For each
	 * property, the listener will be invoked the number of times it was added
	 * for that property. If <code>propertyName</code> or <code>listener</code>
	 * is null, no exception is thrown and no action is taken.
	 * 
	 * @param propertyKey
	 *            The name of the property to listen on.
	 * @param listener
	 *            The UIStateChangeListener to be added
	 */

	public synchronized void addUIStateChangeListener(int propertyKey,
			UIStateChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (children == null) {
			children = new java.util.Hashtable();
		}
		UIStateChangeSupport child = (UIStateChangeSupport) children
				.get(propertyKey);
		if (child == null) {
			child = new UIStateChangeSupport(source);
			children.put(propertyKey, child);
		}
		child.addUIStateChangeListener(listener);
	}

	/**
	 * Remove a PropertyChangeListener for a specific property. If
	 * <code>listener</code> was added more than once to the same event source
	 * for the specified property, it will be notified one less time after being
	 * removed. If <code>propertyName</code> is null, no exception is thrown and
	 * no action is taken. If <code>listener</code> is null, or was never added
	 * for the specified property, no exception is thrown and no action is
	 * taken.
	 * 
	 * @param propertyName
	 *            The name of the property that was listened on.
	 * @param listener
	 *            The PropertyChangeListener to be removed
	 */

	public synchronized void removeUIStateChangeListener(int propertyKey,
			UIStateChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (children == null) {
			return;
		}
		UIStateChangeSupport child = (UIStateChangeSupport) children
				.get(propertyKey);
		if (child == null) {
			return;
		}
		child.removeUIStateChangeListener(listener);
	}

	/**
	 * Returns an array of all the listeners which have been associated with the
	 * named property.
	 * 
	 * @param propertyName
	 *            The name of the property being listened to
	 * @return all of the <code>PropertyChangeListeners</code> associated with
	 *         the named property. If no such listeners have been added, or if
	 *         <code>propertyName</code> is null, an empty array is returned.
	 * @since 1.4
	 */
	public synchronized UIStateChangeListener[] getUIStateChangeListeners(
			int propertyKey) {
		ArrayList returnList = new ArrayList();

		if (children != null) {
			UIStateChangeSupport support = (UIStateChangeSupport) children
					.get(propertyKey);
			if (support != null) {
				returnList.addAll(Arrays.asList(support
						.getUIStateChangeListeners()));
			}
		}
		return (UIStateChangeListener[]) (returnList
				.toArray(new UIStateChangeListener[0]));
	}

	/**
	 * 发送自定义的消息
	 * 
	 * @param source
	 * @param actionType
	 * @param actionID
	 * @param index
	 * @param oldValue
	 * @param newValue
	 */
	public void firePropertyChange(ActionType actionType,
			int propertyKey, Object oldValue, Object newValue) {
		if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
			return;
		}
		firePropertyChange(new UIStateChangeEvent(source, actionType,
				propertyKey, oldValue, newValue));
	}
	
	public void firePropertyChange(Map<ActionType, Map<Integer, Object>> changePropertiseByType) {
		if (changePropertiseByType == null || changePropertiseByType.size() == 0) {
			return;
		}
		firePropertyChange(new UIStateChangeEvent(source, changePropertiseByType));
	}

	/**
	 * 这里实际发送消息
	 * Fire an existing UIStateChangeEvent to any registered listeners. No event
	 * is fired if the given event's old and new values are equal and non-null.
	 * 
	 * @param evt
	 *            The UIStateChangeEvent object.
	 */
	public void firePropertyChange(UIStateChangeEvent evt) {
		/*Object oldValue = evt.getOldValue();
		Object newValue = evt.getNewValue();
		int propertyKey = evt.getPropertyKey();
		ActionType actionType = evt.getActionType();
		if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
			return;
		}
*/
		if (listeners != null) {
			Object[] list = listeners.getListenersInternal();
			for (int i = 0; i < list.length; i++) {
				UIStateChangeListener target = (UIStateChangeListener) list[i];
				target.stateChange(evt);				
			}
		}

		/*if (children != null) {
			UIStateChangeSupport child = null;
			child = (UIStateChangeSupport) children.get(propertyKey);
			if (child != null) {
				child.firePropertyChange(evt);
			}
		}*/
	}

	/**
	 * 发送自定义的消息
	 * 
	 * @param source
	 * @param actionType
	 * @param actionID
	 * @param index
	 * @param oldValue
	 * @param newValue
	 */
	public void fireIndexedPropertyChange(Object source,
			ActionType actionType, int propertyKey, int index,
			Object oldValue, Object newValue) {
		firePropertyChange(new IndexedUIStateChangeEvent(source, actionType,
				propertyKey, oldValue, newValue, index));
	}

	/**
	 * Check if there are any listeners for a specific property, including those
	 * registered on all properties. If <code>propertyName</code> is null, only
	 * check for listeners registered on all properties.
	 * 
	 * @param propertyName
	 *            the property name.
	 * @return true if there are one or more listeners for the given property
	 */
	public synchronized boolean hasListeners(int propertyKey) {
		if (listeners != null && !listeners.isEmpty()) {
			// there is a generic listener
			return true;
		}
		if (children != null) {
			UIStateChangeSupport child = (UIStateChangeSupport) children
					.get(propertyKey);
			if (child != null && child.listeners != null) {
				return !child.listeners.isEmpty();
			}
		}
		return false;
	}

	/**
	 * @serialData Null terminated list of <code>PropertyChangeListeners</code>.
	 *             <p>
	 *             At serialization time we skip non-serializable listeners and
	 *             only serialize the serializable listeners.
	 * 
	 */
	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		if (listeners != null) {
			Object[] list = listeners.getListenersCopy();

			for (int i = 0; i < list.length; i++) {
				UIStateChangeListener l = (UIStateChangeListener) list[i];
				if (l instanceof Serializable) {
					s.writeObject(l);
				}
			}
		}
		s.writeObject(null);
	}

	private void readObject(ObjectInputStream s) throws ClassNotFoundException,
			IOException {
		s.defaultReadObject();

		Object listenerOrNull;
		while (null != (listenerOrNull = s.readObject())) {
			addUIStateChangeListener((UIStateChangeListener) listenerOrNull);
		}
	}

	/**
	 * Hashtable for managing listeners for specific properties. Maps property
	 * names to UIStateChangeSupport objects.
	 * 
	 * @serial
	 * @since 1.2
	 */
	private java.util.Hashtable children;

	/**
	 * The object to be provided as the "source" for any generated events.
	 * 
	 * @serial
	 */
	private Object source;

	/**
	 * Internal version number
	 * 
	 * @serial
	 * @since
	 */
	private int UIStateChangeSupportSerializedDataVersion = 2;

	/**
	 * Serialization version ID, so we're compatible with JDK 1.1
	 */
	static final long serialVersionUID = 6401253773779951803L;
}
