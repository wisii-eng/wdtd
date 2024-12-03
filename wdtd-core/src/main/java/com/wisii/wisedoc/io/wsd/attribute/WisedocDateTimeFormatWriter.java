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
 * @WisedocDateTimeFormatWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;
import java.util.List;

import com.wisii.wisedoc.document.attribute.AbstractDateTimeInfo;
import com.wisii.wisedoc.document.attribute.DateInfo;
import com.wisii.wisedoc.document.attribute.DateTimeItem;
import com.wisii.wisedoc.document.attribute.TimeInfo;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.io.IOUtil;
/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-11-14
 */
public class WisedocDateTimeFormatWriter extends AbstractAttributeWriter
{
	public static final String DATEIN = "datein";
	public static final String DATEOUT = "dateout";
	public static final String TIMEIN = "timein";
	public static final String TIMEOUT = "timeout";
	public static final String INDATEFIRST = "indatefirst";
	public static final String OUTDATEFIRST = "outdatefirst";
	public static final String INSEPERATE = "inseperate";
	public static final String OUTSEPERATE = "outseperate";
	public static final String ITEMSEPERATE = "@@!#!@";
	public static final String ITEMINNERSEPERATE = "@@!#";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof WisedocDateTimeFormat))
		{
			return "";
		}
		String returns = "";
		WisedocDateTimeFormat datetimeformat = (WisedocDateTimeFormat) value;
		String keyname = getKeyName(key);
		DateInfo indate = datetimeformat.getDatein();
		if (indate != null)
		{
			String instring = keyname + DOTTAG + DATEIN + EQUALTAG
					+ QUOTATIONTAG + getDatetimeinfoString(indate)
					+ QUOTATIONTAG;
			returns = returns + instring;
		}

		DateInfo outdate = datetimeformat.getDateout();
		if (outdate != null)
		{
			String outstring = SPACETAG + keyname + DOTTAG + DATEOUT + EQUALTAG
					+ QUOTATIONTAG + getDatetimeinfoString(outdate)
					+ QUOTATIONTAG;
			returns = returns + outstring;
		}
		TimeInfo intime = datetimeformat.getTimein();
		if (intime != null)
		{
			String instring =SPACETAG +  keyname + DOTTAG + TIMEIN + EQUALTAG
					+ QUOTATIONTAG + getDatetimeinfoString(intime)
					+ QUOTATIONTAG;
			returns = returns + instring;
		}

		TimeInfo outtime = datetimeformat.getTimeout();
		if (outtime != null)
		{
			String outstring = SPACETAG + keyname + DOTTAG + TIMEOUT + EQUALTAG
					+ QUOTATIONTAG + getDatetimeinfoString(outtime)
					+ QUOTATIONTAG;
			returns = returns + outstring;
		}
		String inseperate = datetimeformat.getInputseperate();
		if (inseperate != null)
		{
			String inseperatestring = SPACETAG + keyname + DOTTAG + INSEPERATE
					+ EQUALTAG + QUOTATIONTAG + IOUtil.getXmlText(inseperate) + QUOTATIONTAG;
			returns = returns + inseperatestring;
		}
		String outseperate = datetimeformat.getOutseperate();
		if (outseperate != null)
		{
			String outseperatestring = SPACETAG + keyname + DOTTAG
					+ OUTSEPERATE + EQUALTAG + QUOTATIONTAG + IOUtil.getXmlText(outseperate)
					+ QUOTATIONTAG;
			returns = returns + outseperatestring;
		}
		String isindatefirststrng = SPACETAG + keyname + DOTTAG + INDATEFIRST
				+ EQUALTAG + QUOTATIONTAG + datetimeformat.isIndatefirst()
				+ QUOTATIONTAG;
		returns = returns + isindatefirststrng;
		String isoutdatefirststrng = SPACETAG + keyname + DOTTAG + OUTDATEFIRST
				+ EQUALTAG + QUOTATIONTAG + datetimeformat.isOutdatefirst()
				+ QUOTATIONTAG;
		returns = returns + isoutdatefirststrng;
		return returns + SPACETAG;
	}

	private String getDatetimeinfoString(AbstractDateTimeInfo info)
	{
		String returns = "";
		if (info != null)
		{
			List<DateTimeItem> items = info.getDateitems();
			for (DateTimeItem item : items)
			{
				if (returns.equals(""))
				{
					returns = returns + getItemString(item);
				} else
				{
					returns = returns + ITEMSEPERATE + getItemString(item);
				}
			}
		}
		return returns;
	}

	private String getItemString(DateTimeItem item)
	{
		String returns = "";
		if (item != null)
		{
			returns = returns + item.getType() + ITEMINNERSEPERATE
					+ item.getDigit() + ITEMINNERSEPERATE + item.getViewStyle().name();
			String seper = item.getSeparator();
			if (seper != null && !seper.isEmpty())
			{
				returns = returns + ITEMINNERSEPERATE + IOUtil.getXmlText(seper);
			}
		}
		return returns;
	}
}
