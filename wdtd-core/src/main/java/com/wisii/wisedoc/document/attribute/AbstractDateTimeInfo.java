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
 * @AbstractDateTimeInfo.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-6-25
 */
public abstract class AbstractDateTimeInfo
{
	private final List<DateTimeItem> dateitems;
	private final static Date DATE = new Date(new Date().getYear(),0,1,1,1,1);

	protected AbstractDateTimeInfo(List<DateTimeItem> dateitems)
	{
		this.dateitems = dateitems;
	}

	protected abstract String getItemString(DateTimeItem dateitem);

	/**
	 * @返回 dateitems变量的值
	 */
	public final List<DateTimeItem> getDateitems()
	{
		return new ArrayList<DateTimeItem>(dateitems);
	}

	@Override
	public String toString()
	{
		final StringBuffer returns = new StringBuffer();
		for (DateTimeItem dateitem : dateitems)
		{
			returns.append(getItemString(dateitem));
			String itseparator = dateitem.getSeparator();
			if (itseparator != null && !itseparator.isEmpty())
			{
				returns.append(itseparator);
			}
		}
		return returns.toString();
	}

	protected String getDataString(String dataString, Map<Character, ?> datamap)
	{

		StringBuffer returns = new StringBuffer();
		if (dataString != null && datamap != null)
		{
			char[] datachars = dataString.toCharArray();
			for (int i = 0; i < datachars.length; i++)
			{
				Object datastring = datamap.get(datachars[i]);
				if (datastring != null)
				{
					returns.append(datastring);
				} else
				{
					returns.append(datachars[i]);
				}
			}
		}
		return returns.toString();

	}

	protected Map<Character, Character> getChineseDataMap()
	{
		Map<Character, Character> chinesedatamap = new HashMap<Character, Character>();
		chinesedatamap.put('0', '零');
		chinesedatamap.put('1', '一');
		chinesedatamap.put('2', '二');
		chinesedatamap.put('3', '三');
		chinesedatamap.put('4', '四');
		chinesedatamap.put('5', '五');
		chinesedatamap.put('6', '六');
		chinesedatamap.put('7', '七');
		chinesedatamap.put('8', '八');
		chinesedatamap.put('9', '九');
		return chinesedatamap;
	}

	protected Map<Character, Character> getChineseUpperDataMap()
	{
		Map<Character, Character> chineseupperdatamap = new HashMap<Character, Character>();
		chineseupperdatamap.put('0', '零');
		chineseupperdatamap.put('1', '壹');
		chineseupperdatamap.put('2', '貳');
		chineseupperdatamap.put('3', '叁');
		chineseupperdatamap.put('4', '肆');
		chineseupperdatamap.put('5', '伍');
		chineseupperdatamap.put('6', '陸');
		chineseupperdatamap.put('7', '柒');
		chineseupperdatamap.put('8', '捌');
		chineseupperdatamap.put('9', '玖');
		return chineseupperdatamap;
	}

	protected Map<Character, Character> getChineseZeroDataMap()
	{
		Map<Character, Character> chinesezerodatamap = new HashMap<Character, Character>();
		chinesezerodatamap.put('0', '〇');
		chinesezerodatamap.put('1', '一');
		chinesezerodatamap.put('2', '二');
		chinesezerodatamap.put('3', '三');
		chinesezerodatamap.put('4', '四');
		chinesezerodatamap.put('5', '五');
		chinesezerodatamap.put('6', '六');
		chinesezerodatamap.put('7', '七');
		chinesezerodatamap.put('8', '八');
		chinesezerodatamap.put('9', '九');
		return chinesezerodatamap;
	}

	protected Date getDate()
	{
		return DATE;
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
				+ ((dateitems == null) ? 0 : dateitems.hashCode());
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
		AbstractDateTimeInfo other = (AbstractDateTimeInfo) obj;
		if (dateitems == null)
		{
			if (other.dateitems != null)
				return false;
		} else if (!dateitems.equals(other.dateitems))
			return false;
		return true;
	}
}
