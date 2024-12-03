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

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

public class TableCellWriter extends AbsElementWriter
{

	public TableCellWriter(CellElement element)
	{
		super(element);
	}

	@Override
	public void setAttributemap()
	{
		Map<Integer, Object> map = null;
		Attributes attributes = foElement.getAttributes();
		if (attributes != null)
		{
			map = attributes.getAttributes();
			attributemap = new HashMap<Integer, Object>();
			if (map.containsKey(Constants.PR_CONDTION))
			{
				attributemap.put(Constants.PR_CONDTION, map
						.get(Constants.PR_CONDTION));
			}
			if (map.containsKey(Constants.PR_GROUP))
			{
				attributemap.put(Constants.PR_GROUP, map
						.get(Constants.PR_GROUP));
			}
			if (map.containsKey(Constants.PR_NUMBER_ROWS_SPANNED))
			{
				attributemap.put(Constants.PR_NUMBER_ROWS_SPANNED, map
						.get(Constants.PR_NUMBER_ROWS_SPANNED));
			}
		}
	}

	@Override
	public String getElementName()
	{
		return FoXsltConstants.TABLE_CELL;
	}

	@Override
	public String getChildCode()
	{
		return ElementWriter.TAGBEGIN+FoXsltConstants.BLOCK+ElementWriter.NOCHILDTAGEND;
	}

	@Override
	public String getAttributes()
	{
		StringBuffer code = new StringBuffer();
		if (attributemap.containsKey(Constants.PR_GROUP))
		{
			code.append(ElementUtil.outputAttributes(FoXsltConstants.ID, "{$"
					+ FoXsltConstants.ID + "}"));
		} else
		{
			code.append(ElementUtil.outputAttributes(FoXsltConstants.ID, "'"
					+ foElement.hashCode() + "'"));
		}
		return code.toString();
	}
}
