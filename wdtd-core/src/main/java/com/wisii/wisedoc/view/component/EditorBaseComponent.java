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
 * @EditorBaseComponent.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

/**
 * 类功能描述：所有编辑器组件的基对象，在该对象中提供获取、设置所需的值的方法。
 * 
 * 作者：李晓光 创建日期：2007-7-4
 */
@SuppressWarnings("serial")
public abstract class EditorBaseComponent extends JComponent
{
	/** 定义按钮的名字 */
	public final static String BTN_TEXT = "…";

	/*
	 * 定义所有显示名称在资源包中的KEY的名字的前半部分 【资源包名称 = baseName ＋ Enum.className +
	 * Enum.item.Name】
	 */
	public final static String baseName = "Wisedoc.Property.DisName";

	public final static String format = "{0}.{1}.{2}";

	/* 定义所有名称的缓存 */
	private static Map<Object, String> disNames = new HashMap<Object, String>();

	/* 属性监听器的管理工具 */
	private PropertyChangeSupport lisManager = new PropertyChangeSupport(this);

	/* 记录用户编辑时的有效值 */
	private Object value = null;

	private final static String ISCANCEL = "ISCANCEL";

	public final static String VALUE = "VALUE";

	/**
	 * 
	 * 初始化该对象，直接调用父对象的构造方法
	 * 
	 * @param 无
	 */
	public EditorBaseComponent()
	{
		super();
	}

	/* 获取有效值 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * 
	 * 设置有效值
	 * 
	 * @param 指定有效值
	 * @return 无
	 */
	public void setValue(Object newValue)
	{
		Object oldValue = getValue();
		this.value = newValue;
		if (oldValue != null && newValue != null && oldValue.equals(newValue))
		{
			setCancel();
		}
		else
			/* 通知监听对象，属性值发生变化 */
			lisManager.firePropertyChange(VALUE, oldValue, newValue);
	}

	/**
	 * 
	 * 想集合中添加备份信息【显示名称】
	 * 
	 * @param key
	 *            指定KEY【相应枚举的Value】如Boolean.True
	 * @param value
	 *            指定资源包中的KEY
	 * @return voie 无
	 */
	public static void addDisName(Object key, String value)
	{
		disNames.put(key, value);
	}

	/**
	 * 
	 * 获得显示名称在资源包中的KEY
	 * 
	 * @param key
	 *            指定key 具体的值
	 * @return {@link String} 返回资源包中的KEY
	 */
	public static String getDisName(Object key)
	{
		return disNames.get(key);
	}

	/**
	 * 
	 * 格式化显示名称的KEY【资源包】
	 * 
	 * @param params
	 *            分别指定 baseName, 类型名称、自身名称，例如 Wisedoc.Property.DisName 、 Bool 、
	 *            true 结合起来形成了KEY = Wisedoc.Property.DisName.Bool.true
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public static String formateDisname(String... params)
	{
		String result = "";
		if (params == null || params.length < 3)
			return result;

		result = MessageFormat.format(format, params);
		return result;
	}

	/**
	 * 为该Bean添加监听对象
	 * 
	 * @param listener
	 *            指定监听对象
	 * @return void 无
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		if (listener == null)
			return;

		lisManager.addPropertyChangeListener(listener);
	}

	/**
	 * 删除指定的属性change监听
	 * 
	 * @param listener
	 *            指定要删除的监听对象
	 * @return void 无
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		if (listener == null)
			return;

		lisManager.removePropertyChangeListener(listener);
	}

	/**
	 * 取消编辑时，用来通知Table，停止编辑【stopEdit】
	 * 
	 * @return void 无
	 */
	public void setCancel()
	{
		boolean isCancel = true; 
		lisManager.firePropertyChange(ISCANCEL, !isCancel, isCancel);
	}
}
