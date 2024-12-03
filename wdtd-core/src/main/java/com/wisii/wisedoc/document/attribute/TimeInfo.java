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
 * @TimeInfo.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wisii.wisedoc.document.attribute.DateTimeItem.DateTimeType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DefaultType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DigitHourType;

/**
 * 类功能描述：时间信息类
 *
 * 作者：zhangqiang
 * 创建日期：2009-6-24
 */
public final class TimeInfo extends AbstractDateTimeInfo
{
	private TimeInfo(List<DateTimeItem> timeitems)
	{
		super(timeitems);
	}

	/**
	 * 
	 * 对传入的信息进行校验，校验合格则生成TimeInfo对象
	 * 
	 * @param
	 * @return TimeInfo:
	 * @exception
	 */
	public static TimeInfo Instanceof(List<DateTimeItem> dateitems)
	{
		if (dateitems != null)
		{
			List<DateTimeItem> newdateitems = new ArrayList<DateTimeItem>();
			for (DateTimeItem dateitem : dateitems)
			{
				if (dateitem != null)
				{
					DateTimeType type = dateitem.getType();
					// 校验是否是时间项
					if (type == DateTimeType.Hour
							|| type == DateTimeType.Minute
							|| type == DateTimeType.Second)
					{
						newdateitems.add(dateitem);
					}
				}
			}
			if (!newdateitems.isEmpty())
			{
				return new TimeInfo(newdateitems);
			}
		}
		return null;
	}

	@Override
	protected String getItemString(DateTimeItem dateitem)
	{
		DateTimeType type = dateitem.getType();
		String timestring;
		Date date = getDate();
		if (type == DateTimeType.Hour)
		{
			int hour = date.getHours();
			int digit = dateitem.getDigit();
			if (digit == DigitHourType.Am_Pm.ordinal())
			{
				if (hour > 12)
				{
					timestring = "pm" + (hour - 12);
				} else
				{
					timestring = "am" + hour;
				}
			} else if (digit == DigitHourType.Am_Pm_Cn.ordinal())
			{
				if (hour > 12)
				{
					timestring = "下午" + (hour - 12);
				} else
				{
					timestring = "上午" + hour;
				}
			} else if (digit == DigitHourType.D_Am_Pm.ordinal())
			{
				if (hour > 12)
				{
					timestring = "pm" + (hour - 12);
				} else
				{
					if (hour > 9)
					{
						timestring = "am" + hour;
					} else
					{
						timestring = "am0" + hour;
					}
				}
			} else if (digit == DigitHourType.D_Am_Pm_Cn.ordinal())
			{
				if (hour > 12)
				{
					timestring = "下午" + (hour - 12);
				} else
				{
					if (hour > 9)
					{
						timestring = "上午" + hour;
					} else
					{
						timestring = "上午0" + hour;
					}
				}
			} else if (digit == DigitHourType.D_Hour12.ordinal())
			{
				if (hour > 12)
				{
					timestring = "" + (hour - 12);
				} else
				{
					if (hour > 9)
					{
						timestring = "" + hour;
					} else
					{
						timestring = "0" + hour;
					}
				}
			} else if (digit == DigitHourType.D_Hour24.ordinal())
			{
				if (hour > 9)
				{
					timestring = "" + hour;
				} else
				{
					timestring = "0" + hour;
				}
			} else
			{
				timestring = "" + hour;
			}
		} else if (type == DateTimeType.Minute)
		{
			int minute = date.getMinutes();
			int digit = dateitem.getDigit();
			if (digit == 2 && minute < 10)
			{
				timestring = "0" + minute;
			} else
			{
				timestring = "" + minute;
			}
		} else
		{
			int second = date.getSeconds();
			int digit = dateitem.getDigit();
			if (digit == 2 && second < 10)
			{
				timestring = "0" + second;
			} else
			{
				timestring = "" + second;
			}
		}
		DefaultType viewstyle = (DefaultType) dateitem.getViewStyle();
		if (viewstyle == DefaultType.China)
		{
			return getDataString(timestring, getChineseDataMap());
		} else if (viewstyle == DefaultType.China_Upper)
		{
			return getDataString(timestring, getChineseUpperDataMap());
		}
		else if(viewstyle == DefaultType.China_Zero)
		{
			return getDataString(timestring, getChineseZeroDataMap());
		}
		else
		{
			return timestring;
		}
	}
	private static List<DateTimeItem> items;
	static
	{
		items = new ArrayList<DateTimeItem>();
		items.add(new DateTimeItem(DateTimeType.Hour, DigitHourType.D_Hour24.ordinal(),
				DefaultType.Arabia, ":"));
		items.add(new DateTimeItem(DateTimeType.Minute, 2, DefaultType.Arabia,
				":"));
		items.add(new DateTimeItem(DateTimeType.Second, 2, DefaultType.Arabia,
				(String)null));
	}
	public static final TimeInfo DEFAULTTIME = Instanceof(items);
}
