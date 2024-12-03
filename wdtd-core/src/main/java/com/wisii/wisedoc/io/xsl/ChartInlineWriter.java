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

import java.util.Enumeration;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class ChartInlineWriter extends FoElementWriter
{

	public ChartInlineWriter(CellElement element)
	{
		super(element);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (!IoXslUtil.isStandard())
		{
			if (foElement != null && foElement.children() != null)
			{
				Enumeration<CellElement> listobj = foElement.children();
				CellElement obj = null;
				while (listobj.hasMoreElements())
				{
					obj = listobj.nextElement();
				}
				if (obj != null)
				{
					output.append(ElementWriter.TAGBEGIN);
					output.append(FoXsltConstants.INSTREAM_FOREIGN_OBJECT);
					output.append(" ");
					output.append(getAttributeAdd(obj));
					output.append(ElementWriter.TAGEND);
					if (obj instanceof Chart)
					{
						output.append(new PieBarWriter(obj).write(obj));
					}
					output
							.append(ElementUtil
									.endElement(FoXsltConstants.INSTREAM_FOREIGN_OBJECT));
				}
			}
		}
		return output.toString();
	}

	public String getAttributeAdd(CellElement obj)
	{
		String output = "";
		output = output
				+ getAttribute(Constants.PR_GRAPHIC_LAYER, obj
						.getAttribute(Constants.PR_GRAPHIC_LAYER));
		output = output
				+ getAttribute(Constants.PR_BORDER_STYLE, obj
						.getAttribute(Constants.PR_BORDER_STYLE));
		output = output
				+ getAttribute(Constants.PR_BORDER_WIDTH, obj
						.getAttribute(Constants.PR_BORDER_WIDTH));
		output = output
				+ getAttribute(Constants.PR_BORDER_COLOR, obj
						.getAttribute(Constants.PR_BORDER_COLOR));
		output = output
				+ getAttribute(Constants.PR_BORDER_BEFORE_STYLE, obj
						.getAttribute(Constants.PR_BORDER_BEFORE_STYLE));
		output = output
				+ getAttribute(Constants.PR_BORDER_BEFORE_WIDTH, obj
						.getAttribute(Constants.PR_BORDER_BEFORE_WIDTH));
		output = output
				+ getAttribute(Constants.PR_BORDER_BEFORE_COLOR, obj
						.getAttribute(Constants.PR_BORDER_BEFORE_COLOR));
		output = output
				+ getAttribute(Constants.PR_BORDER_AFTER_STYLE, obj
						.getAttribute(Constants.PR_BORDER_AFTER_STYLE));
		output = output
				+ getAttribute(Constants.PR_BORDER_AFTER_WIDTH, obj
						.getAttribute(Constants.PR_BORDER_AFTER_WIDTH));
		output = output
				+ getAttribute(Constants.PR_BORDER_AFTER_COLOR, obj
						.getAttribute(Constants.PR_BORDER_AFTER_COLOR));
		output = output
				+ getAttribute(Constants.PR_BORDER_START_STYLE, obj
						.getAttribute(Constants.PR_BORDER_START_STYLE));
		output = output
				+ getAttribute(Constants.PR_BORDER_START_WIDTH, obj
						.getAttribute(Constants.PR_BORDER_START_WIDTH));
		output = output
				+ getAttribute(Constants.PR_BORDER_START_COLOR, obj
						.getAttribute(Constants.PR_BORDER_START_COLOR));
		output = output
				+ getAttribute(Constants.PR_BORDER_END_STYLE, obj
						.getAttribute(Constants.PR_BORDER_END_STYLE));
		output = output
				+ getAttribute(Constants.PR_BORDER_END_WIDTH, obj
						.getAttribute(Constants.PR_BORDER_END_WIDTH));
		output = output
				+ getAttribute(Constants.PR_BORDER_END_COLOR, obj
						.getAttribute(Constants.PR_BORDER_END_COLOR));
		output = output
				+ getAttribute(Constants.PR_BACKGROUND_COLOR, obj
						.getAttribute(Constants.PR_BACKGROUND_COLOR));
		output = output
				+ getAttribute(Constants.PR_BACKGROUND_IMAGE, obj
						.getAttribute(Constants.PR_BACKGROUND_IMAGE));
		output = output
				+ getAttribute(Constants.PR_BACKGROUND_REPEAT, obj
						.getAttribute(Constants.PR_BACKGROUND_REPEAT));
		output = output
				+ getAttribute(
						Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
						obj
								.getAttribute(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
		output = output
				+ getAttribute(
						Constants.PR_BACKGROUND_POSITION_VERTICAL,
						obj
								.getAttribute(Constants.PR_BACKGROUND_POSITION_VERTICAL));
		output = output
				+ getAttribute(Constants.PR_BACKGROUNDGRAPHIC_LAYER, obj
						.getAttribute(Constants.PR_BACKGROUNDGRAPHIC_LAYER));
		return output;
	}
}
