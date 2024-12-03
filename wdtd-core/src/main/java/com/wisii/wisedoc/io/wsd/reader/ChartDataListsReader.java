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
 * @ChartDataListsReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.NumberContent;
import com.wisii.wisedoc.document.attribute.ChartData2D;
import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.io.wsd.attribute.ChartDataListWriter;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-5-25
 */
public class ChartDataListsReader extends AbstractHandler
{
	private List<ChartDataList> chartdatalists = new ArrayList<ChartDataList>();
	private ChartDataList chardatalist;
	void init()
	{
		chardatalist = null;
		chartdatalists.clear();
	}

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (ChartDataListWriter.CHARTDATALIST.equals(qname))
		{
			chardatalist = new ChartDataList();
		
            chartdatalists.add(chardatalist);
		}
		else if(ChartDataListWriter.CHARTDATA.equals(qname))
		{
			String rowstring = atts.getValue(ChartDataListWriter.ROW);
			String columnstring = atts.getValue(ChartDataListWriter.COLUMN);
			String datastring = atts.getValue(ChartDataListWriter.DATA);
            try{
			int row = Integer.parseInt(rowstring);
			int column = Integer.parseInt(columnstring);
			NumberContent nc = getNumberContent(datastring);
			if(nc != null)
			{
				chardatalist.add(new ChartData2D(column,row,nc));
			}
            }
            catch (Exception e) {
				LogUtil.debugException(localname + ":" + qname + ":row:" + rowstring + ":column:" +columnstring, e);
			}	
		}
		else
		{
			throw new SAXException("chartdatalists 节点下的" + qname + "节点为非法");
		}
	}

	private NumberContent getNumberContent(String datastring)
	{
		if (datastring != null)
		{
			if (datastring.startsWith("N@"))
			{
				String valuestring = datastring.substring(2);
				Number number = null;
				try
				{
					if (valuestring.contains("."))
					{
						number = Double.parseDouble(valuestring);
					} else
					{
						number = Integer.parseInt(valuestring);
					}
				} catch (NumberFormatException e)
				{
					LogUtil.debugException("属性的属性值：" + datastring + "不是合法的数字",
							e);
				}
				if (number != null)
				{
					return new NumberContent(number);
				}
			} else
			{
				BindNode node = wsdhandler.getNode(datastring);
				if (node != null)
				{
					return new NumberContent(node);
				}
			}
		}
		return null;
	}

	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
	}

	public List<ChartDataList> getObject()
	{
		return chartdatalists;
	}

}
