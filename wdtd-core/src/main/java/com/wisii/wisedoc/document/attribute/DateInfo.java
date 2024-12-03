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
 * @DateInfo.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.DateTimeItem.DateTimeType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DefaultType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DigitType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.MonthType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.YearType;

/**
 * 类功能描述：日期信息类
 *
 * 作者：zhangqiang
 * 创建日期：2009-6-24
 */
public final class DateInfo extends AbstractDateTimeInfo
{

	private DateInfo(List<DateTimeItem> dateitems)
	{
		super(dateitems);
	}
	/**
	 * 
	 * 对传入的信息进行校验，校验合格则生成DateInfo对象
	 *
	 * @param      
	 * @return      DateInfo:  
	 * @exception   
	 */
    public static DateInfo Instanceof(List<DateTimeItem> dateitems)
    {
		if (dateitems != null)
		{
			List<DateTimeItem> newdateitems = new ArrayList<DateTimeItem>();
			for (DateTimeItem dateitem : dateitems)
			{
				if (dateitem != null)
				{
					DateTimeType type = dateitem.getType();
                   //校验是否是日期项					
					if (type == DateTimeType.Year || type == DateTimeType.Month
							|| type == DateTimeType.Day)
					{
						newdateitems.add(dateitem);
					}
				}
			}
			if (!newdateitems.isEmpty())
			{
				return new DateInfo(newdateitems);
			}
		}
		return null;
	}
	/*
     * 
     */
	protected String getItemString(DateTimeItem dateitem)
	{
		DateTimeType type = dateitem.getType();
		if (type == DateTimeType.Year)
		{
			return getYearItemString(dateitem);
		} else if (type == DateTimeType.Month)
		{
			return getMonthItemString(dateitem);
		} else
		{
			return getDayItemString(dateitem);
		}

	}

	private String getYearItemString(DateTimeItem dateitem)
	{
		String year = "" + (getDate().getYear() + 1900);
		int digit = dateitem.getDigit();
		if (digit == DigitType.First.ordinal())
		{
			year = year.substring(0, 1);
		} else if (digit == DigitType.Second.ordinal())
		{
			year = year.substring(1, 2);
		} else if (digit == DigitType.Third.ordinal())
		{
			year = year.substring(2, 3);
		} else if (digit == DigitType.Forth.ordinal())
		{
			year = year.substring(3, 4);
		} else if (digit == DigitType.FS.ordinal())
		{
			year = year.substring(0, 2);
		} else if (digit == DigitType.ST.ordinal())
		{
			year = year.substring(1, 3);
		} else if (digit == DigitType.TF.ordinal())
		{
			year = year.substring(2, 4);
		} else if (digit == DigitType.FST.ordinal())
		{
			year = year.substring(0, 3);
		} else if (digit == DigitType.STF.ordinal())
		{
			year = year.substring(1, 4);
		} else
		{
			// do nothing
		}
		YearType type = (YearType) dateitem.getViewStyle();
		if (type == YearType.China)
		{
			return getDataString(year, getChineseDataMap());
		} else if (type == YearType.China_Upper)
		{
			return getDataString(year, getChineseUpperDataMap());
		} else if (type == YearType.China_Zero)
		{
			return getDataString(year, getChineseZeroDataMap());
		} else
		{
			return year;
		}
	}

	private String getMonthItemString(DateTimeItem dateitem)
	{
		String month = "" + (getDate().getMonth()+1);
		int digit = dateitem.getDigit();
		MonthType type = (MonthType) dateitem.getViewStyle();
		if (type == MonthType.China)
		{
			if (digit == 2 && month.length() == 1)
			{
				month = 0 + month;
			}
			return getDataString(month, getChineseDataMap());
		}
		else if (type == MonthType.China_Zero)
		{
			if (digit == 2 && month.length() == 1)
			{
				month = 0 + month;
			}
			return getDataString(month, getChineseZeroDataMap());
		} 
		else if (type == MonthType.China_Upper)
		{
			if (digit == 2 && month.length() == 1)
			{
				month = 0 + month;
			}
			return getDataString(month, getChineseUpperDataMap());
		} else if (type == MonthType.Arabia)
		{
			if (digit == 2 && month.length() == 1)
			{
				month = 0 + month;
			}
			return month;
		} else if (type == MonthType.En_Lower)
		{
			return getEnglishMonth(month);
		} else if (type == MonthType.En_Word)
		{
			String enlishstring = getEnglishMonth(month);
			return enlishstring.substring(0, 1).toUpperCase()
					+ enlishstring.substring(1);
		} else if (type == MonthType.En_Short_Lower)
		{
			String enlishstring = getEnglishMonth(month);
			return enlishstring.substring(0, 3);
		} else if (type == MonthType.En_Short_Upper)
		{
			String enlishstring = getEnglishMonth(month);
			return enlishstring.substring(0, 1).toUpperCase()
					+ enlishstring.substring(1, 3);
		} 
		 else if (type == MonthType.En_Short_SUPPER)
		{
			String enlishstring = getEnglishMonth(month);
			return enlishstring.substring(0, 3).toUpperCase();
		}else
		{
			return month;
		}

	}

	private String getDayItemString(DateTimeItem dateitem)
	{
		String day = "" + getDate().getDate();
		int digit = dateitem.getDigit();
		DefaultType type = (DefaultType) dateitem.getViewStyle();
		if (type == DefaultType.China)
		{
			if (digit == 2 && day.length() == 1)
			{
				day = 0 + day;
			}
			return getDataString(day, getChineseDataMap());
		}
		else if (type == DefaultType.China_Zero)
		{
			if (digit == 2 && day.length() == 1)
			{
				day = 0 + day;
			}
			return getDataString(day, getChineseZeroDataMap());
		}else if (type == DefaultType.China_Upper)
		{
			if (digit == 2 && day.length() == 1)
			{
				day = 0 + day;
			}
			return getDataString(day, getChineseUpperDataMap());
		} else
		{
			if (digit == 2 && day.length() == 1)
			{
				day = 0 + day;
			}
			return day;
		}

	}

	private String getEnglishMonth(String month)
	{
		Map<String, String> monthmap = new HashMap<String, String>();
		monthmap.put("1", "january");
		monthmap.put("2", "february");
		monthmap.put("3", "march");
		monthmap.put("4", "april");
		monthmap.put("5", "may");
		monthmap.put("6", "june");
		monthmap.put("7", "july");
		monthmap.put("8", "august");
		monthmap.put("9", "september");
		monthmap.put("10", "october");
		monthmap.put("11", "november");
		monthmap.put("12", "december");
		return monthmap.get(month);
	}
	private static List<DateTimeItem> items;
	static
	{
		items = new ArrayList<DateTimeItem>();
		items.add(new DateTimeItem(DateTimeType.Year, DigitType.All.ordinal(),
				YearType.Arabia, (String) null));
		items.add(new DateTimeItem(DateTimeType.Month, 2, MonthType.Arabia,
				(String) null));
		items.add(new DateTimeItem(DateTimeType.Day, 2, DefaultType.Arabia,
				(String) null));
	}
	public final static DateInfo DEFAULTDATE = Instanceof(items);
}
