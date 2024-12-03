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
 * @FileEditorComponent.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.component;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.util.DialogSupport;

/**
 * 类功能描述：
 * 
 * 作者:zhangqiang 创建日期：2009-6-12
 */
public class FileEditorComponent extends JComponent
{

	/* 属性监听器的管理工具 */
	private PropertyChangeSupport lisManager = new PropertyChangeSupport(this);

	/* 记录用户编辑时的有效值 */
	private String value = null;

	/**
	 * 
	 * 初始化该对象，直接调用父对象的构造方法
	 * 
	 * @param 无
	 */
	public FileEditorComponent(String filepath)
	{
		super();
		this.value = filepath;
		setValue(filepath);
		setLayout(new BorderLayout());
		add(txtComp, BorderLayout.CENTER);
		txtComp.setBorder(null);
		txtComp.setEditable(false);
		add(btnComp, BorderLayout.EAST);
		btnComp.setMargin(new Insets(0, 0, 0, 0));
		FocusListener[] listeners = txtComp.getFocusListeners();
		if (listeners.length > 2)
			txtComp.removeFocusListener(listeners[listeners.length - 1]);
		btnComp.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser openfile = DialogSupport
						.getDialog(new WiseDocFileFilter(".xml", "xml"));
				openfile.setAcceptAllFileFilterUsed(false);
				if (value != null && !value.isEmpty())
				{
					File file = new File(value.toString());
					openfile.setCurrentDirectory(file.getParentFile());
				} else
				{
					openfile.setCurrentDirectory(new File(System
							.getProperty("user.dir")
							+ File.separator
							+ "conf"));
				}
				int dialogResult = openfile.showOpenDialog(SystemManager
						.getMainframe());
				if (dialogResult == JFileChooser.APPROVE_OPTION)
				{
					File file = openfile.getSelectedFile();
					String filename = file.toString();
					setValue(filename);
				}

			}
		});
	}

	/* 获取有效值 */
	public Object getValue()
	{
		return value;
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
	 * 设置编辑后的值，给改编辑器value属性和文本框
	 * 
	 * @param 指定编辑的值
	 * @return 无
	 */
	public void setValue(String newValue)
	{
		Object oldValue = getValue();
		this.value = newValue;
		if (oldValue != null && newValue != null && oldValue.equals(newValue))
		{
		} else
		{
			/* 通知监听对象，属性值发生变化 */
			lisManager.firePropertyChange("", oldValue, newValue);
		}
		txtComp.setText(value == null ? "" : value.toString());
	}

	/**
	 * 设置编辑后的值，给改编辑器value属性和文本框
	 * 
	 * @param 指定编辑的值
	 * @return 无
	 */
	public void initValue(String newValue)
	{
		Object oldValue = getValue();
		this.value = newValue;
		if (oldValue != null && newValue != null && oldValue.equals(newValue))
		{
		} else
		{
			txtComp.setText(value == null ? "" : value.toString());
		}

	}

	/* 定义文本框组件 */
	protected JTextField txtComp = new JTextField();

	protected JButton btnComp = new JButton("  ");

	public JButton getBtnComp() {
		return btnComp;
	}

	
}
