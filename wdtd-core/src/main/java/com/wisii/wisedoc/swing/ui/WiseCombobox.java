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
 * @WiseCombox.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.ui;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * 类功能描述：重新复写了JcomboBox
 * 
 * 作者：zhangqiang 创建日期：2008-12-29
 */
public class WiseCombobox extends JComboBox
{
	private boolean selectingItem = false;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseCombobox()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseCombobox(ComboBoxModel model)
	{
		super(model);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseCombobox(Object[] items)
	{
		super(items);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WiseCombobox(Vector<?> items)
	{
		super(items);
		// TODO Auto-generated constructor stub
	}

	public void InitValue(Object anObject)
	{

		Object oldSelection = getSelectedItem();
		Object objectToSelect = anObject;
		if ((oldSelection == null && anObject != null)
				|| (oldSelection != null && !oldSelection.equals(anObject)))
		{

			if (anObject != null && !isEditable())
			{
				// For non editable combo boxes, an invalid selection
				// will be rejected.
				boolean found = false;
				for (int i = 0; i < dataModel.getSize(); i++)
				{
					Object element = dataModel.getElementAt(i);
					if (anObject.equals(element))
					{
						found = true;
						objectToSelect = element;
						break;
					}
				}
				if (!found)
				{
					return;
				}
			}

			// Must toggle the state of this flag since this method
			// call may result in ListDataEvents being fired.
			selectingItem = true;
			dataModel.setSelectedItem(objectToSelect);
			selectingItem = false;

			if (selectedItemReminder != dataModel.getSelectedItem())
			{
				// in case a users implementation of ComboBoxModel
				// doesn't fire a ListDataEvent when the selection
				// changes.
				selectedItemChanged();
			}
		}
	}

	public void initIndex(int index)
	{
		if(index==getSelectedIndex())
		{
			return;
		}
		int size = dataModel.getSize();
		if (index == -1)
		{
			InitValue(null);
		} else if (index < -1 || index >= size)
		{
			throw new IllegalArgumentException("setSelectedIndex: " + index
					+ " out of bounds");
		} else
		{
			InitValue(dataModel.getElementAt(index));
		}
	}
	 /**
	  * 控制Action的调用，在调用init方法时不触发Action事件
	  */
	  protected void fireActionEvent() 
	  {
//		  屏蔽掉按回车时重复Action事件
		if (!selectingItem&&!"comboBoxEdited".equals(getActionCommand()))
		{
			super.fireActionEvent();
		}
	}

}
