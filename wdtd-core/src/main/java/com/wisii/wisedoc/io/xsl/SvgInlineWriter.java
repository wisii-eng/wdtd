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
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

public class SvgInlineWriter extends AbsElementWriter
{

	public SvgInlineWriter(CellElement element)
	{
		super(element);
	}

	@Override
	public String getElementName()
	{
		return FoXsltConstants.INLINE;
	}

	@SuppressWarnings("unchecked")
	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (foElement != null && foElement.children() != null)
		{
			Enumeration<CellElement> listobj = foElement.children();
			while (listobj.hasMoreElements())
			{
				CellElement obj = listobj.nextElement();
				if (obj instanceof Canvas)
				{
					output.append(new CanvasWriter((Canvas) obj).write());
				}
			}
		}
		return output.toString();
	}
}
