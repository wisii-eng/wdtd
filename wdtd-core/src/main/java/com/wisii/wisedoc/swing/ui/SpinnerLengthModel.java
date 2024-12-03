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

package com.wisii.wisedoc.swing.ui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.Map;

import javax.swing.AbstractSpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

public class SpinnerLengthModel extends AbstractSpinnerModel
{

	FixedLength length = new FixedLength(12d, "pt");

	private int min = 0;

	// 以mpt为单位的最大长度值
	private int max = 10000000;

	private LengthProperty value;

	private LengthSpinner spinner;

	int step = -1;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public SpinnerLengthModel()
	{
		value = new FixedLength(0);
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public SpinnerLengthModel(FixedLength value, FixedLength min,
			FixedLength max, int step)
	{
		if (value == null)
		{
			value = new FixedLength(0);
		}
		this.value = value;
		if (min != null)
		{
			this.min = min.getValue();
		}
		if (max != null)
		{
			this.max = max.getValue();
		}
		if (step > 0)
		{
			this.step = step;
		}
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public SpinnerLengthModel(LengthProperty value, LengthProperty min,
			LengthProperty max, int step)
	{
		if (value == null)
		{
			value = new FixedLength(0);
		} else
		{
			this.value = value;
		}
		if (min != null)
		{
			PercentLength property = (PercentLength) min;
			FixedLength length = (FixedLength) ((LengthBase) property
					.getBaseLength()).getBaseLength();
			double factor = property.value();
			this.min = new Double(factor * length.getValue()).intValue();
		}
		if (max != null)
		{
			PercentLength property = (PercentLength) max;
			FixedLength length = (FixedLength) ((LengthBase) property
					.getBaseLength()).getBaseLength();
			double factor = property.value();
			this.max = new Double(factor * length.getValue()).intValue();
		}
		if (step > 0)
		{
			this.step = step;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SpinnerModel#getNextValue()
	 */
	public LengthProperty getNextValue()
	{
		return insrc(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SpinnerModel#getPreviousValue()
	 */
	public LengthProperty getPreviousValue()
	{
		return insrc(-1);
	}

	private LengthProperty insrc(int i)
	{
		LengthProperty lp = null;
		LengthProperty oldlength = getValue();
		if (oldlength instanceof FixedLength)
		{
			if ((oldlength.getValue() >= max && i > 0)
					|| (oldlength.getValue() <= min && i < 0))
			{
				return null;
			} else
			{
				lp = insrcf(i);
			}
		} else if (oldlength instanceof PercentLength)
		{
			PercentLength property = (PercentLength) oldlength;
			FixedLength length = (FixedLength) ((LengthBase) property
					.getBaseLength()).getBaseLength();
			double factor = property.value();
			if ((factor * length.getValue() >= max && i > 0)
					|| (factor * length.getValue() <= min && i < 0))
			{
				return null;
			} else
			{
				lp = insrcp(i);
			}
		}
		return lp;
	}

	private FixedLength insrcf(int i)
	{
		FixedLength oldlength = (FixedLength) getValue();
		if ((oldlength.getValue() >= max && i > 0)
				|| (oldlength.getValue() <= min && i < 0))
		{
			return null;
		}
		int newlen = 0;
		if (step > 0)
		{
			newlen = oldlength.getInnerLengthValue() + i * step;
		} else
		{
			newlen = (oldlength.getInnerLengthValue() / 10 + i) * 10;
		}
		FixedLength newlength = new FixedLength(newlen, oldlength.getUnits(),
				oldlength.getPrecision());
		if (newlength.getValue() < min)
		{
			newlength = new FixedLength(min, oldlength.getUnits(), oldlength
					.getPrecision());
		} else if (newlength.getValue() > max)
		{
			newlength = new FixedLength(max, oldlength.getUnits(), oldlength
					.getPrecision());
		}
		return newlength;
	}

	private PercentLength insrcp(int i)
	{
		PercentLength oldlength = (PercentLength) getValue();
		double type = oldlength.value();
		PercentLength newlength = new PercentLength(type + i, oldlength
				.getBaseLength());
		return newlength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SpinnerModel#getValue()
	 */
	public LengthProperty getValue()
	{
		return value;
	}

	public void initValue(Object value, ActionType type)
	{
		if (value == null)
		{
			value = new FixedLength(0);
		}
		if ((value instanceof LengthProperty) && !(value.equals(this.value)))
		{
			this.value = (LengthProperty) value;
			setInitLength(type);
			fireStateChangedforupdateUI();
		}
	}

	public void setInitLength(ActionType type)
	{
		if (type != null)
		{
			Map<Integer, Object> map = RibbonUIModel
					.getCurrentPropertiesByType().get(type);
			if (map != null)
			{
				if (type == ActionType.BLOCK)
				{
					if (map.containsKey(Constants.PR_FONT_SIZE)
							&& map.get(Constants.PR_FONT_SIZE) != null)
					{
						length = (FixedLength) map.get(Constants.PR_FONT_SIZE);
					}
				} else if (type == ActionType.TABLE)
				{
					if (map
							.containsKey(Constants.PR_INLINE_PROGRESSION_DIMENSION)
							&& map
									.get(Constants.PR_INLINE_PROGRESSION_DIMENSION) != null)
					{
						length = (FixedLength) map
								.get(Constants.PR_INLINE_PROGRESSION_DIMENSION);
					}
				}
			}
		}

	}

	/**
	 * 只更新界面显示，不触发属性值改变时间
	 * 
	 * @see #setValue
	 * @see EventListenerList
	 */
	protected void fireStateChangedforupdateUI()
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if ((listeners[i] == ChangeListener.class)
					&& !(listeners[i] instanceof Actions))
			{
				ChangeEvent changeEvent = new ChangeEvent(this);
				((ChangeListener) listeners[i + 1]).stateChanged(changeEvent);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SpinnerModel#setValue(java.lang.Object)
	 */
	public void setValue(Object value)
	{
		if ((value instanceof LengthProperty) && !(value.equals(this.value)))
		{
			this.value = (LengthProperty) value;
			fireStateChanged();
			postActionEvent();
		}
	}

	/**
	 * Adds the specified action listener to receive action events from this
	 * textfield.
	 * 
	 * @param l
	 *            the action listener to be added
	 */
	public synchronized void addActionListener(ActionListener l)
	{
		listenerList.add(ActionListener.class, l);
	}

	/**
	 * Removes the specified action listener so that it no longer receives
	 * action events from this textfield.
	 * 
	 * @param l
	 *            the action listener to be removed
	 */
	public synchronized void removeActionListener(ActionListener l)
	{
		listenerList.remove(ActionListener.class, l);
	}

	public void postActionEvent()
	{
		fireActionPerformed();
	}

	/**
	 * @param spinner
	 *            设置spinner成员变量的值
	 * 
	 *            值约束说明
	 */
	public void setSpinner(LengthSpinner spinner)
	{
		this.spinner = spinner;
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created. The listener list
	 * is processed in last to first order.
	 * 
	 * @see EventListenerList
	 */
	protected void fireActionPerformed()
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		int modifiers = 0;
		AWTEvent currentEvent = EventQueue.getCurrentEvent();
		if (currentEvent instanceof InputEvent)
		{
			modifiers = ((InputEvent) currentEvent).getModifiers();
		} else if (currentEvent instanceof ActionEvent)
		{
			modifiers = ((ActionEvent) currentEvent).getModifiers();
		}
		ActionEvent e = new ActionEvent(spinner, ActionEvent.ACTION_PERFORMED,
				"fixedlength", EventQueue.getMostRecentEventTime(), modifiers);

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ActionListener.class)
			{
				((ActionListener) listeners[i + 1]).actionPerformed(e);
			}
		}
	}

	public FixedLength getLength()
	{
		return length;
	}

	public void setLength(FixedLength length)
	{
		this.length = length;
	}
}
