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
 * @BarCodeTextListWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.util.List;

import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.io.wsd.DocumentWirter;

/**
 * 类功能描述：由BarCodeText组成的List Writer类
 *
 * 作者：zhangqiang
 * 创建日期：2009-5-25
 */
public class BarCodeTextListWriter extends AbstractAttributeWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof List))
		{
			return "";
		}
		String returns = "";
		List<BarCodeText> bctextlist = (List<BarCodeText>) value;
		if (bctextlist != null && !bctextlist.isEmpty())
		{
			returns = returns + getKeyName(key);
			String btextstring = null;
			for (BarCodeText barcodetext : bctextlist)
			{
				String btextvalue = null;
				if(barcodetext==null)
				{
					btextvalue = NULL;
				}
				else if (barcodetext.isBindContent())
				{
					btextvalue = DocumentWirter.getDataNodeID(barcodetext
							.getBindNode());
				} else
				{

					btextvalue = "T";
					if (barcodetext != null)
					{
						btextvalue = btextvalue + barcodetext.getText();
					}
				}
				if (btextstring == null)
				{
					btextstring = btextvalue;
				} else
				{
					btextstring = btextstring + TEXTSPLIT + btextvalue;
				}
			}
			returns = returns + EQUALTAG + QUOTATIONTAG
					+ IOUtil.getXmlText(btextstring) + QUOTATIONTAG + SPACETAG;
		}
		return returns;
	}

}
