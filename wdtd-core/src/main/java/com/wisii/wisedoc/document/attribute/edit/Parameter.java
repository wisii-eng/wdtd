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
 * @Parameter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.edit;

/**
 * 类功能描述：关联表达式的参数类
 * 
 * 作者：zhangqiang 创建日期：2009-8-29
 */
public final class Parameter
{

	/**
	 * 
	 * 类功能描述：参数类型
	 * 
	 * 作者：zhangqiang 创建日期：2009-8-29
	 */
	public static enum ParamTyle
	{
		// 当前UI
		UI,
		// 常量
		CONSTANCE,
		// xpath
		XPATH
	}

	/**
	 * 
	 * 类功能描述：数据类型
	 * 
	 * 作者：zhangqiang 创建日期：2009-8-29
	 */
	public static enum DataTyle
	{
		// 整形
		NUMBER,
		// 字符串型
		STRING
	}

	// 变量类型
	private  ParamTyle type;

	// 变量名
	private  String name;

	/*
	 * 变量值， 1,type==UI,value==null, 2,type==CONSTANCE,value为字符串类型的值,
	 * 2,type==XPATH,value为BindNode的值,
	 */
	private  Object value;

	private  DataTyle datatype;

	public Parameter(DataTyle datatype, String name, ParamTyle type,
			Object value)
	{
		this.datatype = datatype;
		this.name = name;
		this.type = type;
		this.value = value;
	}

	/**
	 * @返回 type变量的值
	 */
	public final ParamTyle getType()
	{
		return type;
	}

	/**
	 * @返回 name变量的值
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * @返回 value变量的值
	 */
	public final Object getValue()
	{
		return value;
	}

	/**
	 * @返回 datatype变量的值
	 */
	public final DataTyle getDatatype()
	{
		return datatype;
	}

	
	/**
	 * @param type 设置type成员变量的值
	
	 * 值约束说明
	
	 */
	public final void setType(ParamTyle type)
	{
		this.type = type;
	}

	/**
	 * @param name 设置name成员变量的值
	
	 * 值约束说明
	
	 */
	public final void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param value 设置value成员变量的值
	
	 * 值约束说明
	
	 */
	public final void setValue(Object value)
	{
		this.value = value;
	}

	/**
	 * @param datatype 设置datatype成员变量的值
	
	 * 值约束说明
	
	 */
	public final void setDatatype(DataTyle datatype)
	{
		this.datatype = datatype;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((datatype == null) ? 0 : datatype.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parameter other = (Parameter) obj;
		if (datatype == null)
		{
			if (other.datatype != null)
				return false;
		} else if (!datatype.equals(other.datatype))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null)
		{
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null)
		{
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
