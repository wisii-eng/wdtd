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
 * @ValidationWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.util.List;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.edit.Validation;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.io.wsd.DocumentWirter;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-21
 */
public class ValidationWriter extends AbstractAttributeWriter
{
	public static final String VALIDATE = "validate";
	public static final String MSG = "msg";
	public static final String PARM = "parm";
	public static final String PARMSEPERATE = "@@!#!@";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof Validation))
		{
			return "";
		}
		String returns = "";
		Validation validation = (Validation) value;
		String keyname = getKeyName(key);
		String msg = validation.getMsg();
		if (msg != null && !msg.isEmpty())
		{

			String msgstring = keyname + DOTTAG + MSG + EQUALTAG + QUOTATIONTAG
					+ IOUtil.getXmlText(msg) + QUOTATIONTAG;
			returns = returns + msgstring;

		}
		String validate = validation.getValidate();
		if (validate != null && !validate.isEmpty())
		{

			String validatestring = SPACETAG + keyname + DOTTAG + VALIDATE
					+ EQUALTAG + QUOTATIONTAG + IOUtil.getXmlText(validate)
					+ QUOTATIONTAG;
			returns = returns + validatestring;

		}
		List parms = validation.getParms();
		if (parms != null && !parms.isEmpty())
		{
			String parmstring = SPACETAG + keyname + DOTTAG + PARM + EQUALTAG
					+ QUOTATIONTAG + getParmsString(parms) + QUOTATIONTAG;
			returns = returns + parmstring;
		}
		return returns;
	}

	private String getParmsString(List parms)
	{
		String returns = null;
		for (Object parm : parms)
		{
			String parmstring = null;
			if (parm instanceof BindNode)
			{
				parmstring = DocumentWirter.getDataNodeID((BindNode) parm);
			} else if (parm instanceof String)
			{
				parmstring = "T" + IOUtil.getXmlText((String) parm);
			} else
			{
				continue;
			}
			if (returns == null)
			{
				returns = parmstring;
			} else
			{
				returns = returns + TEXTSPLIT + parmstring;
			}
		}
		return returns;

	}
}
