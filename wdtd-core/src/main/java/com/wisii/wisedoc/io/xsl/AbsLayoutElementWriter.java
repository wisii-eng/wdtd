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

package com.wisii.wisedoc.io.xsl;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;

public class AbsLayoutElementWriter implements FoElementIFWriter
{

	SelectOutputAttributeWriter writerFactory = new SelectOutputAttributeWriter();

	public String getAttribute(int key, Object value)
	{
		return writerFactory.write(key, value);
	}

	public String getAttributes()
	{
		return "";
	}

	protected String getChildCode()
	{
		return "";
	}

	public String getCode()
	{
		StringBuffer code = new StringBuffer();
		code.append(getAttributeVariable());
		code.append(getHeaderElement());
		code.append(getChildCode());
		code.append(getEndElement());
		return code.toString();
	}

	public String getElementName()
	{
		return "";
	}

	public String getEndElement()
	{
		return ElementUtil.endElement(getElementName());
	}

	public String getAttributeVariable()
	{
		String code = "";
		return code;
	}

	public String getHeaderElement()
	{
		StringBuffer code = new StringBuffer();
		code.append(getHeaderStart());
		code.append(getAttributes());
		code.append(getHeaderEnd());
		return code.toString();
	}

	public String getHeaderEnd()
	{
		return ElementWriter.TAGEND;
	}

	public String getHeaderStart()
	{
		String code = ElementWriter.TAGBEGIN;
		code = code + getElementName();
		return code;
	}

	public String write(CellElement element)
	{
		return getCode();
	}
}
