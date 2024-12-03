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
 * @NumberEditorComponent.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.component;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.NumberFormatter;

/**
 * 类功能描述：所有数字格式化对象的父类。该对象主要提供一个可格式化数字的文本框。
 * 
 * 作者：李晓光 创建日期：2007-7-5
 */
@SuppressWarnings("serial")
public abstract class NumberEditorComponent extends EditorBaseComponent
{
	/**
	 * 
	 * 创建一个指定了格式换对象的文本框
	 * 
	 * @param 无
	 */
	public NumberEditorComponent()
	{
		init();
	}

	/**
	 * 
	 * 创建一个指定了格式换对象的文本框
	 * 
	 * @param format
	 *            指定格式化对象
	 */
	public NumberEditorComponent(String pattern)
	{
		if (pattern == null || pattern.trim() == "")
			return;
		objFormat.applyPattern(pattern);
		
		init();
	}

	/**
	 * 
	 * 创建一个指定了编辑、格式的文本框。
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public NumberEditorComponent(AbstractFormatter formatter)
	{
		if (formatter == null)
			return;
		numEditor = new JFormattedTextField(formatter);	

		init();
	}

	/**
	 * 
	 * 初始化给面板
	 * 
	 * @return void 无返回值
	 */
	private void init()
	{
		setLayout(new BorderLayout());

		add(numEditor, BorderLayout.CENTER);
		numEditor.setBorder(null);

		/* 数据有效性验证【如果设置了setAllowsInvalid(false)即不允许输入非法字符时，该验证就不在起作用了。】 */
//		 numEditor.setInputVerifier(new NumberVerifier());
//		numEditor.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				try
//				{
//					numEditor.commitEdit();
//
//					if(NumberEditorComponent.this instanceof IntegerEditorComponent)
//					{
//						 setValue(numEditor.getValue());
//						return;
//					}
//					
//					if(!(numEditor.getValue() instanceof Number))
//						return;
//					Number num = (Number)numEditor.getValue();
//					double dou = WiseDocUtil.translateUnitToInner(num.doubleValue(), Configuration.getUnitOfMeasure());
//					dou = Double.valueOf(objFormat.format(dou).replaceAll(",", ""));
//					setValue(dou);
//				} catch (Exception ex)
//				{
//					
//				}
//
//			}
//		});

		numEditor.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
//				try
//				{
//					numEditor.commitEdit();
//
//					if(NumberEditorComponent.this instanceof IntegerEditorComponent)
//					{
//						 setValue(numEditor.getValue());
//						return;
//					}
//					
//					if(!(numEditor.getValue() instanceof Number))
//						return;
//					Number num = (Number)numEditor.getValue();
//					double dou = WiseDocUtil.translateUnitToInner(num.doubleValue(), Configuration.getUnitOfMeasure());
//					dou = Double.valueOf(objFormat.format(dou).replaceAll(",", ""));
//					setValue(dou);
//				} catch (Exception ex) // Parse
//				{
//				}
			}
		});
	}

	/**
	 * 定义能够接受数字格式换的文本框
	 */
	protected JFormattedTextField numEditor = new JFormattedTextField(objFormat)
	{
		public void setValue(Object value)
		{
			if (!(value instanceof Number))
			{	
				super.setValue(value);
				return;
			}
			Format format = ((NumberFormatter) this.getFormatter()).getFormat();
			if(format == null || !(format instanceof DecimalFormat))
				return;
			
			String str = ((DecimalFormat) format).toPattern();
			if (str.indexOf(".") == -1)
			{
				Integer intValue = ((Number) value).intValue();
				String temp = format.format(intValue).replaceAll(",", "");
				intValue = Integer.parseInt(temp);
				super.setValue(intValue);
			} else
			{
				Double douValue = ((Number) value).doubleValue();
				String temp = format.format(douValue).replaceAll(",", "");
				douValue = Double.parseDouble(temp);
				super.setValue(douValue);
			}
			
		}
	};
	private static final String defaultFormat = ",##0.0#";
	/**
	 * 定义格式换对象
	 */
	public static DecimalFormat objFormat = new DecimalFormat(defaultFormat);

	/**
	 * 
	 * 类功能描述：检查输入的数据是否有效
	 * 
	 * 作者：李晓光 创建日期：2007-7-5
	 */
	class NumberVerifier extends InputVerifier
	{
		/**
		 * 检查输入的数据是否有效
		 * 
		 * @param input
		 *            录入数据的控件
		 * @return {@link Boolean} 如果认为录入的数据有效怎返回True否则返回False
		 * @exception NumberFormatException
		 *                当录入的数据有非数字字符时，产生该异常
		 */
		public boolean verify(JComponent input)
		{
			JFormattedTextField txtFormat = (JFormattedTextField) input;
			try
			{
				if (txtFormat.getText() == null
						|| txtFormat.getText().trim() == "")
					return true;

				Double.parseDouble(txtFormat.getText().trim().replace(".", ""));

				if (txtFormat.getText().trim().indexOf(".") != -1)
					return false;
				try
				{
					txtFormat.getFormatter().stringToValue(txtFormat.getText());

					return true;
				} catch (ParseException e)
				{
			

					return false;
				}
			} catch (NumberFormatException ex)
			{
				// TODO 输入LOG
		
				return false;
			}
		}
	}
}
