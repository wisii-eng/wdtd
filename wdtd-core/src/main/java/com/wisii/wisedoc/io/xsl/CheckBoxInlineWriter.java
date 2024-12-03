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
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class CheckBoxInlineWriter extends InlineWriter
{

	public CheckBoxInlineWriter(CellElement element, int n)
	{
		super(element, n);
	}

	public String write(CellElement element)
	{
		StringBuffer output = new StringBuffer();
		Inline inlne = (Inline) foElement;
		InlineContent textin = inlne.getContent();
		if (textin != null)
		{
			output.append(beforeDeal());
			output.append(getCode());
			output.append(endDeal());
		}
		return output.toString();
	}

	public String getContent()
	{
		StringBuffer code = new StringBuffer();
		Inline inlne = (Inline) foElement;
		InlineContent textin = inlne.getContent();
		if (textin == null)
		{
			code.append("<");
			code.append(FoXsltConstants.INLINE);
			code.append("/>");
		} else
		{
			if (textin instanceof Text)
			{
				Text textinLine = (Text) textin;
				boolean flg = textinLine.isBindContent();
				if (flg)
				{

					String yuanshiPath = IoXslUtil.getXSLXpath(textinLine
							.getBindNode());
					String relativePath = IoXslUtil
							.compareCurrentPath(yuanshiPath);

					String checked = null;
					if (attributemap
							.containsKey(Constants.PR_RADIO_CHECK_VALUE))
					{
						checked = attributemap.get(
								Constants.PR_RADIO_CHECK_VALUE).toString();
					}

					if (checked != null)
					{
						EnumProperty tickmark = (EnumProperty) attributemap
								.get(Constants.PR_CHECKBOX_TICKMARK);
						String value = "" + (char) 0xF050;
						if (tickmark != null
								&& tickmark.getEnum() == Constants.EN_CHECKBOX_TICKMARK_DOT)
						{
							value = "" + (char) 0xF09D;
						}
						// String unchecked = attributemap.get(
						// Constants.PR_CHECKBOX_UNSELECT_VALUE).toString();

						code.append("<");
						code.append(FoXsltConstants.CHOOSE);
						code.append(">");

						code.append("<");
						code.append(FoXsltConstants.WHEN);

						code.append(ElementUtil.outputAttributes("test",
								relativePath + "='" + checked + "'"));
						code.append(">");
						code.append(value);
						code.append("</");

						code.append(FoXsltConstants.WHEN);
						code.append(">");

						code.append("<");
						code.append(FoXsltConstants.OTHERWISE);
						code.append(">");
						code.append(ElementUtil.ElementValueOf("' '"));
						code.append("</");
						code.append(FoXsltConstants.OTHERWISE);
						code.append(">");

						code.append("</");
						code.append(FoXsltConstants.CHOOSE);
						code.append(">");
					} else
					{
						code.append(ElementUtil.ElementValueOf("' '"));
					}
				}
			}
		}
		return code.toString();
	}

}
