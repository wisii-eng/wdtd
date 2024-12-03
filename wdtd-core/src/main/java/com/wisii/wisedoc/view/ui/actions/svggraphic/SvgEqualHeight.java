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

package com.wisii.wisedoc.view.ui.actions.svggraphic;

import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.SVGUtil;

@SuppressWarnings("serial")
public class SvgEqualHeight extends SvgEqualWidth
{

	public FixedLength getLineLength(CellElement current)
	{
		FixedLength length = null;
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			FixedLength y1 = (FixedLength) currentattribute
					.get(Constants.PR_Y1);
			FixedLength y2 = (FixedLength) currentattribute
					.get(Constants.PR_Y2);
			length = SVGUtil.getSubAbsLength(y1, y2);
		}
		return length;
	}

	public FixedLength getRectangleLength(CellElement current)
	{
		FixedLength length = null;
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			length = (FixedLength) currentattribute.get(Constants.PR_HEIGHT);
		}
		return length;
	}


	public void setLineLength(CellElement current, FixedLength length)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			FixedLength y1 = (FixedLength) currentattribute
					.get(Constants.PR_Y1);
			FixedLength y2 = (FixedLength) currentattribute
					.get(Constants.PR_Y2);
			if (SVGUtil.inLeftTopSide(y1, y2))
			{
				setAttribute(current, Constants.PR_Y1, SVGUtil.getSumLength(y2,
						length));
			} else
			{
				setAttribute(current, Constants.PR_Y2, SVGUtil.getSumLength(y1,
						length));
			}
		}
	}

	public void setRectangleLength(CellElement current, FixedLength length)
	{
		setAttribute(current, Constants.PR_HEIGHT, length);
	}
}
