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
 * @WiseDcoFileFilter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 类功能描述：定义文件过滤器【所有文件过滤器的父对象，以后定义的说有文件过滤器 均应该继承自该对象】
 * 
 * 作者：李晓光 创建日期：2007-7-12
 */
public class WiseDocFileFilter extends FileFilter
{

	/**
	 * 
	 * 创建图片
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public WiseDocFileFilter(String descript, String... suffixs)
	{
		this.suffixs = suffixs;
		this.descript = descript;
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public boolean accept(File file)
	{
		boolean accept = file.isDirectory();
		if (!accept)
		{
			String extName = getExtension(file);
			for (String temp : suffixs)
			{
				if (temp.equalsIgnoreCase(extName))
				{
					accept = true;
					break;
				}
			}
		}

		return accept;
	}

	/**
	 * 
	 * 返回过滤器的的文字描述信息
	 * 
	 * @param 无
	 * @return {@link String} 过滤器的描述信息
	 */
	@Override
	public String getDescription()
	{
		return descript;
	}

	/**
	 * 
	 * 获得文件的扩展名【不带.的字符串】
	 * 
	 * @param file
	 *            指定文件
	 * @return {@link String} 指定文件的扩展名
	 */
	public static String getExtension(File file)
	{
		if(file == null || !file.exists())
			return "";
		String fileName = file.getName(), suffix = "";
		int where = fileName.lastIndexOf('.');
		if (where == -1)
			return suffix;

		return suffix = fileName.substring(where + 1).toLowerCase();
	}

	/* 过滤器的文字描述【JPEG (*.JPEG)】 */
	private String descript;

	/* 过滤器所接受的扩展名集合 */
	private String[] suffixs;
}
