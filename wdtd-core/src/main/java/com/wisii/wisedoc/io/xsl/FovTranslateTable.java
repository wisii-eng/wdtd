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
 * @FovTranslateTable.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.util.List;

import com.wisii.wisedoc.io.xsl.util.ElementUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-28
 */
public class FovTranslateTable
{

	String name;

	List<FovTranslate> translates;

	public FovTranslateTable(String tablename, List<FovTranslate> translatetable)
	{
		name = tablename;
		translates = translatetable;
	}

	public String write()
	{
		String output = "";
		output = output + ElementUtil.startElement(getName());
		if (translates != null)
		{
			int num = translates.size();
			for (int i = 0; i < num; i++)
			{
				FovTranslate translate = translates.get(i);
				output = output + translate.write();
			}
		}
		output = output + ElementUtil.endElement(getName());
		return output;
	}

	/**
	 * @返回 name变量的值
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            设置name成员变量的值 值约束说明
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @返回 translates变量的值
	 */
	public List<FovTranslate> getTranslates()
	{
		return translates;
	}

	/**
	 * @param translates
	 *            设置translates成员变量的值 值约束说明
	 */
	public void setTranslates(List<FovTranslate> translates)
	{
		this.translates = translates;
	}

}
