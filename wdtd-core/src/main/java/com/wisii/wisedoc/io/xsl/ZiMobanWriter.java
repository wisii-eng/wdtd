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

import com.wisii.io.zhumoban.MainXSLWriter;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

public class ZiMobanWriter extends AbsElementWriter
{

	public ZiMobanWriter(CellElement element)
	{
		super(element);
		MainXSLWriter.setHaveZimoban(true);
	}

	@Override
	public String getElementName()
	{
		return "";
	}

	@Override
	public String write(CellElement element)
	{
		StringBuffer content = new StringBuffer();
		content.append(beforeDeal());
		content.append(ElementWriter.TAGBEGIN);
		content.append(FoXsltConstants.CALL_TEMPLATE);
		String filename = element.getAttribute(Constants.PR_ZIMOBAN_NAME)
				.toString();
		String resultname = getSUbTEmplateName(filename);
		content.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
				resultname));
		MainXSLWriter.addZimoban(resultname);
		content.append(ElementWriter.TAGEND);
		content.append(ElementUtil.endElement(FoXsltConstants.CALL_TEMPLATE));
		content.append(endDeal());
		return content.toString();
	}

	public String getSUbTEmplateName(String fn)
	{
		fn = fn.replaceFirst(".wsdx", "");
		int length = fn.length();
		String result = "";
		for (int i = 0; i < length; i++)
		{
			char current = fn.charAt(i);
			if (current == '\\' || current == '/')
			{
				result += "_wisiifs_";
			} else
			{
				result += current;
			}
		}
		return result;
	}
}
