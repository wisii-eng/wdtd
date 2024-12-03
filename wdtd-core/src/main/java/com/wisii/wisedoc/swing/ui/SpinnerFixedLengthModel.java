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
 * @SpinnerFixedLengthModel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.ui;

import javax.swing.AbstractSpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 类功能描述：设置固定长度的model
 * 
 * 作者：zhangqiang 创建日期：2008-12-24
 */
public class SpinnerFixedLengthModel extends AbstractSpinnerModel
{
	private int min = 0;
	// 以mpt为单位的最大长度值
	private int max = 10000000;
	private FixedLength value;
	int step = -1;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public SpinnerFixedLengthModel()
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
	public SpinnerFixedLengthModel(FixedLength value, FixedLength min,
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SpinnerModel#getNextValue()
	 */
	public FixedLength getNextValue()
	{
		return insrc(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SpinnerModel#getPreviousValue()
	 */
	public FixedLength getPreviousValue()
	{
		return insrc(-1);
	}

	private FixedLength insrc(int i)
	{
		FixedLength oldlength = getValue();
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
			int precision = oldlength.getPrecision();
			int defstep = 1;
			for (int j = 0; j < precision; j++)
			{
				defstep = defstep * 10;
			}
			newlen = (oldlength.getInnerLengthValue() / defstep + i) * defstep;
		}
		FixedLength newlength = new FixedLength(newlen, oldlength.getUnits(),
				oldlength.getPrecision());
		if (newlength.getValue() < min)
		{
			newlength = new FixedLength(oldlength.getUnits(),min,oldlength.getPrecision());
		} else if (newlength.getValue() > max)
		{
			newlength = new FixedLength(oldlength.getUnits(),max,oldlength.getPrecision());
		}
		return newlength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SpinnerModel#getValue()
	 */
	public FixedLength getValue()
	{
		return value;
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
		if(value==null)
		{
			String unit = ConfigureUtil.getUnit();
			if(this.value!=null)
			{
				unit=this.value.getUnits();
			}
			value = new FixedLength(0,unit);
		}
		if ((value instanceof FixedLength) && !(value.equals(this.value)))
		{
			int newlen = ((FixedLength) value).getValue();
			if (newlen >= min && newlen <= max)
			{
				this.value = (FixedLength) value;
				fireStateChanged();
			}
		}
	}

	/**
	 * @返回 min变量的值
	 */
	public final int getMin()
	{
		return min;
	}

	/**
	 * @param min
	 *            设置min成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setMin(int min)
	{
		if (min <= max)
		{
			this.min = min;
		}
	}

	/**
	 * @返回 max变量的值
	 */
	public final int getMax()
	{
		return max;
	}

	/**
	 * @param max
	 *            设置max成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setMax(int max)
	{
		if (max >= min)
		{
			this.max = max;
		}
	}

	/**
	 * @返回 step变量的值
	 */
	public final int getStep()
	{
		return step;
	}

	/**
	 * @param step
	 *            设置step成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setStep(int step)
	{
		if (step > 0)
		{
			this.step = step;
		}
	}

}
