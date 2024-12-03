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
 * @OverFlowReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import com.wisii.wisedoc.document.attribute.Fixedarea;
import com.wisii.wisedoc.io.AttributeWriter;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-10-12
 */
public class FixedareaReader extends SingleAttributeReader
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.SingleAttributeReader#read(int,
	 * java.lang.String)
	 */
	@Override
	public Object read(int key, String value)
	{
		String[] fixedareavalues = value.split(AttributeWriter.TEXTSPLIT);
		if (fixedareavalues.length == 6)
		{
			try
			{
				String maxnumberstr = fixedareavalues[0];
				int maxnumber = Integer.parseInt(maxnumberstr);
				String linesstr = fixedareavalues[1];
				int lines = Integer.parseInt(linesstr);
				String caozuofustr = fixedareavalues[2];
				int caozuofu = Integer.parseInt(caozuofustr);
				String isviewstr = fixedareavalues[3];
				boolean isview = Boolean.parseBoolean(isviewstr);
				String nullas = fixedareavalues[4];
				if (AttributeWriter.NULL.equals(nullas))
				{
					nullas = null;
				}
				String beforeadd = fixedareavalues[5];
				if (AttributeWriter.NULL.equals(beforeadd))
				{
					beforeadd = null;
				}
				return new Fixedarea(maxnumber, lines, caozuofu, isview,
						XMLUtil.fromXmlText(nullas), XMLUtil.fromXmlText(beforeadd));
			} catch (NumberFormatException e)
			{
				LogUtil.debugException("解析" + key + "属性时出错", e);
				return null;
			}
		}
		return null;
	}

}
