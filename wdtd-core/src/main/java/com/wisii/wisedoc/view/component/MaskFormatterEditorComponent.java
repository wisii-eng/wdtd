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
 * @MaskFormatterEditorComponent.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;

/**
 * 类功能描述：格式数字用文本框，不同浮点数的是，该对象中可以定义占位符。
 * 效果：如果定义了格式化字符串:"###-##-###",则当没有填充数据时可以显示为 "___-__-___"形式 【数值格式属性编辑器实现】
 * 作者：李晓光 创建日期：2007-7-5
 */
@SuppressWarnings("serial")
public class MaskFormatterEditorComponent extends NumberEditorComponent
{

	/* 定义格式换对象 */
	private static MaskFormatter formatter = new MaskFormatter();

	/**
	 * 创建默认的格式对象，并初始化文本框信息
	 */
	public MaskFormatterEditorComponent() throws ParseException
	{
		this(null);
	}

	/**
	 * 
	 * 创建该对象，并初始化文本框信息
	 * 
	 * @param strFormat
	 *            指定格式化信息
	 */
	public MaskFormatterEditorComponent(String strFormatter)
			throws ParseException
	{
		super(formatter);
		
		numEditor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				try
				{
					numEditor.commitEdit();
//					if(numEditor.getValue() instanceof NumberFormat)
						setValue(numEditor.getValue());
				}
				catch (ParseException e1)
				{
					e1.printStackTrace();
				}
			}			
		});
		numEditor.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent e)
			{
				try
				{
					numEditor.commitEdit();
//					if(numEditor.getValue() instanceof NumberFormat)
						setValue(numEditor.getValue());
				}
				catch (ParseException e1)
				{
					e1.printStackTrace();
				}
			}			
		});
		if (strFormatter != null || strFormatter == "")
			setMask(strFormatter);
		
		formatter.setPlaceholderCharacter('_');
		formatter.setPlaceholder("");
	}
	
	public void setValue(Object value)
	{	
		numEditor.setValue(value);
		
		super.setValue(value);
	}

	/**
	 * 
	 * 获得有效的字符
	 * 
	 * @param 无
	 * @return {@link String} 返回有效的字符集合
	 */
	public String getValidChars()
	{
		return formatter.getValidCharacters();
	}

	/**
	 * 
	 * 设置有效字符集合
	 * 
	 * @param validChars
	 *            有效字符的集合
	 * @return void 无
	 */
	public void setValidChars(String validChars)
	{
		if (formatter == null)
			return;
		formatter.setValidCharacters(validChars);
	}

	/**
	 * 
	 * 获得格式化的匹配字符串
	 * 
	 * @param 无
	 * @return {@link String} 返回格式化字符串
	 */
	public String getMask()
	{
		return formatter.getMask();
	}

	/**
	 * 
	 * 设置格式化的匹配字符串
	 * 
	 * @param mask
	 *            指定匹配符字符串
	 * @return void 无
	 * @throws ParseException
	 *             匹配符解析错误时，产生该异常
	 */
	public void setMask(String mask) throws ParseException
	{
		formatter.setMask(mask);
	}
}
