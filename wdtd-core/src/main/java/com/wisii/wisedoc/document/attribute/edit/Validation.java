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
 * @Validation.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.edit;

import java.util.List;

/**
 * 类功能描述：验证信息类
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-17
 */
public final class Validation
{
	public final static String SCHEMA = "schema";
	public final static String ISBLANKORNULL = "isBlankOrNull";
	public final static String ISBYTE = "isByte";
	public final static String ISDOUBLE = "isDouble";
	public final static String ISFLOAT = "isFloat";
	public final static String ISINT = "isInt";
	public final static String ISLONG = "isLong";
	public final static String ISSHORT = "isShort";
	public final static String MATCHREGEXP = "matchRegexp";
	public final static String MAXLENGTH = "maxLength";
	public final static String MINLENGTH = "minLength";
	public final static String ISINRANGE = "isInRange";
	public final static String MAXVALUE = "maxValue";
	public final static String MINVALUE = "minValue";
	public final static String ISINRANGEOFDATE = "isInRangeOfDate";
	private String validate;
	private String msg;
	private List parms;

	public Validation(String validate, String msg, List parms)
	{
		this.validate = validate;
		this.msg = msg;
		this.parms = parms;
	}

	/**
	 * @返回 validate变量的值
	 */
	public final String getValidate()
	{
		return validate;
	}

	/**
	 * @返回 msg变量的值
	 */
	public final String getMsg()
	{
		return msg;
	}

	/**
	 * @返回 parms变量的值
	 */
	public final List getParms()
	{
		return parms;
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
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result + ((parms == null) ? 0 : parms.hashCode());
		result = prime * result
				+ ((validate == null) ? 0 : validate.hashCode());
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
		Validation other = (Validation) obj;
		if (msg == null)
		{
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		if (parms == null)
		{
			if (other.parms != null)
				return false;
		} else if (!parms.equals(other.parms))
			return false;
		if (validate == null)
		{
			if (other.validate != null)
				return false;
		} else if (!validate.equals(other.validate))
			return false;
		return true;
	}
	@Override
	public String toString()
	{
		return "validate:" + validate + "\nmsg:" +msg + "\nparms:" + parms;
	}
}
