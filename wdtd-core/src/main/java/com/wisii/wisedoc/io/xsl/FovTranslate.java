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
 * @FovTranslate.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-28
 */
public class FovTranslate
{

	String fovKey;

	String fovValue;

	public FovTranslate(String key, String value)
	{
		fovKey = key;
		fovValue = value;
	}

	/**
	 * 
	 * 得到元素fov_translate的代码
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public String write()
	{
		String output = "<";
		output = output + FoXsltConstants.FOV_TRANSLATE;
		output = output + getFovkeyAttribute();
		output = output + getFovvalueAttribute();
		output = output + "/>";
		return output;
	}

	/**
	 * 
	 * 得到属性fov_key的赋值语句
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public String getFovkeyAttribute()
	{
		return ElementUtil.outputAttributes(FoXsltConstants.FOV_KEY, fovKey);
	}

	/**
	 * 
	 * 得到属性fov_value的赋值语句
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public String getFovvalueAttribute()
	{
		return ElementUtil
				.outputAttributes(FoXsltConstants.FOV_VALUE, fovValue);
	}

	/**
	 * @返回 fovKey变量的值
	 */
	public String getFovKey()
	{
		return fovKey;
	}

	/**
	 * @param fovKey
	 *            设置fovKey成员变量的值 值约束说明
	 */
	public void setFovKey(String fovKey)
	{
		this.fovKey = fovKey;
	}

	/**
	 * @返回 fovValue变量的值
	 */
	public String getFovValue()
	{
		return fovValue;
	}

	/**
	 * @param fovValue
	 *            设置fovValue成员变量的值 值约束说明
	 */
	public void setFovValue(String fovValue)
	{
		this.fovValue = fovValue;
	}

}
