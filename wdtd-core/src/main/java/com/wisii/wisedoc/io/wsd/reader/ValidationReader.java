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
 * @ValidationReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.edit.Validation;
import com.wisii.wisedoc.io.AttributeWriter;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.io.wsd.attribute.ValidationWriter;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-21
 */
public class ValidationReader extends CompoundAttributeReader
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.wsd.reader.CompoundAttributeReader#read(int, java.util.Map)
	 */
	@Override
	public Object read(int key, Map<String, String> values)
	{
		if (values != null && !values.isEmpty())
		{
			String msg = values.get(ValidationWriter.MSG);
			if (msg != null)
			{
				msg = IOUtil.fromXmlText(msg);
			}
			String validate = values.get(ValidationWriter.VALIDATE);
			if (validate != null)
			{
				validate = IOUtil.fromXmlText(validate);
			}
			String parmstring = values.get(ValidationWriter.PARM);
			List parms = null;
			if (parmstring != null)
			{
				parms = generateParms(parmstring);
			}
			if (validate != null)
			{
				return new Validation(validate, msg, parms);
			} else
			{
				return null;
			}
		} else
		{
			return null;

		}}

	private List generateParms(String parmstring)
	{
		String[] attstrings = parmstring.split(AttributeWriter.TEXTSPLIT);
		List parms = new ArrayList();
		for(int i = 0;i < attstrings.length;i++)
		{
			parms.add(getParm(attstrings[i]));
		}
		return parms;
	}

	private Object getParm(String value)
	{
		if (value == null)
		{
			return null;
		} else if (value.startsWith("T"))
		{
			if (value.length() > 1)
			{
				return IOUtil.fromXmlText(value.substring(1));
			}
			return null;
		} else
		{
			return wsdhandler.getNode(value);

		}
	}

}
