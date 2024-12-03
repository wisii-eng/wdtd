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
 * @WisedocDateTimeFormatReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.AbstractDateTimeInfo;
import com.wisii.wisedoc.document.attribute.DateInfo;
import com.wisii.wisedoc.document.attribute.DateTimeItem;
import com.wisii.wisedoc.document.attribute.TimeInfo;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DateTimeType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DefaultType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.MonthType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.YearType;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.io.wsd.attribute.WisedocDateTimeFormatWriter;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-11-14
 */
public class WisedocDateTimeFormatReader extends CompoundAttributeReader
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.CompoundAttributeReader#read(int,
	 * java.util.Map)
	 */
	public Object read(int key, Map<String, String> values)
	{
		if (values != null)
		{
			String indatestring = values.get(WisedocDateTimeFormatWriter.DATEIN);
			DateInfo datein = null;
			if (indatestring != null)
			{
				datein = (DateInfo) getDateTimeInfo(indatestring,true);
			}
			String outdatestring = values.get(WisedocDateTimeFormatWriter.DATEOUT);
			DateInfo dateout = null;
			if (outdatestring != null)
			{
				dateout = (DateInfo) getDateTimeInfo(outdatestring,true);
			}
			TimeInfo timein = null;
			String intimestring = values.get(WisedocDateTimeFormatWriter.TIMEIN);
			if (intimestring != null)
			{
				timein = (TimeInfo) getDateTimeInfo(intimestring,false);
			}
			String timeoutstring = values.get(WisedocDateTimeFormatWriter.TIMEOUT);
			TimeInfo timeout = null;
			if (timeoutstring != null)
			{
				timeout = (TimeInfo) getDateTimeInfo(timeoutstring,false);
			}
			String inseperate = values
					.get(WisedocDateTimeFormatWriter.INSEPERATE);
			if(inseperate!=null)
			{
				inseperate = IOUtil.fromXmlText(inseperate);
			}
			String outseperate = values
					.get(WisedocDateTimeFormatWriter.OUTSEPERATE);
			if(outseperate!=null)
			{
				outseperate = IOUtil.fromXmlText(outseperate);
			}
			boolean isindatefirst = Boolean.parseBoolean(values.get(WisedocDateTimeFormatWriter.INDATEFIRST));
			boolean isoutdatefirst = Boolean.parseBoolean(values.get(WisedocDateTimeFormatWriter.OUTDATEFIRST));
			if ((datein != null || timein != null)&&(dateout != null || timeout != null))
			{
				return new WisedocDateTimeFormat(datein,timein,dateout,timeout, inseperate,
						outseperate,isindatefirst,isoutdatefirst);
			}
		}
		return null;
	}

	private AbstractDateTimeInfo getDateTimeInfo(String value,boolean isdate)
	{
		List<DateTimeItem> items = getDateTimeItems(value);
		if(items != null)
		{
			if(isdate)
			{
				return DateInfo.Instanceof(items);
			}
			else
			{
				return TimeInfo.Instanceof(items);
			}
		}
		else{
		return null;
		}
	}

	private List<DateTimeItem> getDateTimeItems(String value)
	{
		if(value != null)
		{
			List<DateTimeItem> items = new ArrayList<DateTimeItem>();
			String[] itemstrings = value.split(WisedocDateTimeFormatWriter.ITEMSEPERATE);
			for(String itemstring:itemstrings)
			{
				DateTimeItem item = getItem(itemstring);
				if(item != null)
				{
					items.add(item);
				}
			}
		 return items;
		}
		else{
		return null;
		}
	}

	private DateTimeItem getItem(String itemstring)
	{
		if (itemstring != null)
		{
			String[] inneritemstring = itemstring
					.split(WisedocDateTimeFormatWriter.ITEMINNERSEPERATE);
			try
			{
                DateTimeType type = DateTimeType.valueOf(inneritemstring[0]);
                if(type!= null&&inneritemstring.length > 1)
                {
                	Integer digit = Integer.parseInt(inneritemstring[1]);
                	if(inneritemstring.length > 2)
                	{
                		Enum viewstyle; 
                		if(type==DateTimeType.Year)
                		{
                			viewstyle = YearType.valueOf(inneritemstring[2]);
                		}
                		else if(type==DateTimeType.Month)
                		{
                			viewstyle = MonthType.valueOf(inneritemstring[2]);
                		}
                		else
                		{
                			viewstyle = DefaultType.valueOf(inneritemstring[2]);
                		}
                		if(viewstyle != null)
                		{
                			String seperate = null;
                			if(inneritemstring.length > 3)
                			{
                				seperate = IOUtil.fromXmlText(inneritemstring[3]);
                			}
                			return new DateTimeItem(type,digit,viewstyle,seperate);
                		}
                			
                	}
                }
			} catch (Exception e)
			{
				LogUtil.debug(e);
				return null;
			}
			return null;
		} else
		{
			return null;
		}
	}
}
