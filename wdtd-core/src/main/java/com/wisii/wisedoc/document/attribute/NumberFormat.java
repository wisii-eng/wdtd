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
 * @DataFormat.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

/**
 * 数字格式化类
 * 
 * 作者：zhangqiang 创建日期：2007-7-5
 */
public final class NumberFormat
{
	// 小数数位
	private int _decimaldigits = -1;
	private boolean hasthousseparator=true;
	private boolean isbaifenbi=false;
//
//	// 小数数位分隔符
//	private String _decimalseparator = "";
//
//	// 千分为分隔符
//	private String _thousandseparator = "";

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public NumberFormat(int decimaldigits, boolean hasthousseparator,boolean isbaifenbi)
	{
		_decimaldigits = decimaldigits;
		this.hasthousseparator=hasthousseparator;
		this.isbaifenbi=isbaifenbi;
//		_decimalseparator = decimalseparator.trim();
//		_thousandseparator = thousandseparator.trim();
	}

	/**
	 * @返回 _decimaldigits变量的值
	 */
	public int getDecimalDigits()
	{
		return _decimaldigits;
	}

	/**
	 * @返回  _decimaldigits变量的值
	 */
	public int get_decimaldigits()
	{
		return _decimaldigits;
	}

	/**
	 * @返回  hasthousseparator变量的值
	 */
	public boolean isHasthousseparator()
	{
		return hasthousseparator;
	}

	/**
	 * @返回  isbaifenbi变量的值
	 */
	public boolean isIsbaifenbi()
	{
		return isbaifenbi;
	}
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
	{

		// num = "###" + form.getThousandSeparator() + "###"
		// + form.getDecimalSeparator();
		String num = null;
		if (isHasthousseparator())
		{
			num = "#,###";
		} else
		{
			num = "#";
		}
		int dc = getDecimalDigits();
		if (dc > 0)
		{
			num = num + ".";
			for (int i = 0; i < dc; i++)
			{
				num = num + "0";
			}
		}
		if (isIsbaifenbi())
		{
			num = num + "%";
		}
		return num;
	}
	
}
