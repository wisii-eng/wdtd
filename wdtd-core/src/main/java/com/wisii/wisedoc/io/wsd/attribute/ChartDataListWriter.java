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
 * @ChartDataListsWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;
import com.wisii.wisedoc.document.NumberContent;
import com.wisii.wisedoc.document.attribute.ChartData2D;
import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.wsd.DocumentWirter;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-5-25
 */
public class ChartDataListWriter extends AbstractAttributeWriter
{
    public final static String CHARTDATALIST = "chartdatalist";
    public final static String CHARTDATA = "chartdata";
    public final static String ROW = "row";
    public final static String COLUMN = "column";
    public final static String DATA = "data";
	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof ChartDataList))
		{
			return "";
		}
		String returns = "";
		ChartDataList dynamicstyle = (ChartDataList) value;
		returns = returns + getChartDataListString(dynamicstyle);
		return returns;
	}
	private String getChartDataListString(ChartDataList chartdatalist)
	{
		String returns = "";
		if (chartdatalist != null)
		{
			returns = returns + ElementWriter.TAGBEGIN + CHARTDATALIST
					+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
			for (ChartData2D chartdata2d : chartdatalist)
			{
				if(chartdata2d != null){
				}
				returns = returns + ElementWriter.TAGBEGIN + CHARTDATA + SPACETAG + ROW + 
				EQUALTAG + QUOTATIONTAG + chartdata2d.getIndex2d() + QUOTATIONTAG + SPACETAG + COLUMN + 
				EQUALTAG + QUOTATIONTAG + chartdata2d.getIndex1d() + QUOTATIONTAG+ SPACETAG + DATA + 
				EQUALTAG + QUOTATIONTAG + getDataString(chartdata2d.getNumbercontent()) + QUOTATIONTAG + ElementWriter.NOCHILDTAGEND +ElementWriter.LINEBREAK;
			}
			returns = returns + ElementWriter.TAGENDSTART + CHARTDATALIST
					+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
		}
		return returns;
	}
	private String getDataString(NumberContent numbercontent)
	{
		String value;
		if (numbercontent.isBindContent())
		{
			value = DocumentWirter.getDataNodeID(numbercontent.getBindNode());
		} else
		{
			value = "N@" + numbercontent.getNumber();
		}
		return value;
	}
}
