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
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.SVGUtil;

@SuppressWarnings("serial")
public class SvgRightMove extends SvgMove
{

	public boolean lineOverFlow(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		Element huabu = current.getParent();
		FixedLength x1 = (FixedLength) oldattribute.get(Constants.PR_X1);
		FixedLength xqam = SVGUtil.getSumLength(x1, move);
		FixedLength x2 = (FixedLength) oldattribute.get(Constants.PR_X2);
		FixedLength xzam = SVGUtil.getSumLength(x2, move);
		Map<Integer, Object> huabuattribute = huabu.getAttributes()
				.getAttributes();
		FixedLength width = (FixedLength) huabuattribute
				.get(Constants.PR_WIDTH);
		return SVGUtil.inLeftTopSide(width, xqam)
				&& SVGUtil.inLeftTopSide(width, xzam);
	}

	public boolean svgContainerOverFlow(CellElement current, FixedLength move)
	{
		return true;
	}

	public boolean rectangleOverFlow(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		Element huabu = current.getParent();
		Map<Integer, Object> huabuattribute = huabu.getAttributes()
				.getAttributes();
		FixedLength huabuwidth = (FixedLength) huabuattribute
				.get(Constants.PR_WIDTH);
		FixedLength x = (FixedLength) oldattribute.get(Constants.PR_X);
		FixedLength width = (FixedLength) oldattribute.get(Constants.PR_WIDTH);
		FixedLength xqam = SVGUtil.getSumLength(x, move);
		FixedLength xzam = SVGUtil.getSumLength(xqam, width);
		return SVGUtil.inLeftTopSide(xzam, huabuwidth);
	}
	public void svgContainerMove(CellElement current, FixedLength move)
	{
		Element huabu = current.getParent();
		Map<Integer, Object> containerattribute = huabu.getAttributes()
				.getAttributes();
		FixedLength left = (FixedLength) containerattribute
				.get(Constants.PR_LEFT);
		FixedLength leftAfterMove = SVGUtil.getSumLength(left, move);
		huabu.setAttribute(Constants.PR_LEFT, leftAfterMove);
	}

	public void lineMove(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		FixedLength x1 = (FixedLength) oldattribute.get(Constants.PR_X1);
		FixedLength xqam = SVGUtil.getSumLength(x1, move);
		FixedLength x2 = (FixedLength) oldattribute.get(Constants.PR_X2);
		FixedLength xzam = SVGUtil.getSumLength(x2, move);
		current.setAttribute(Constants.PR_X1, xqam);
		current.setAttribute(Constants.PR_X2, xzam);
	}

	public void rectangleMove(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		FixedLength x = (FixedLength) oldattribute.get(Constants.PR_X);
		FixedLength xqam = SVGUtil.getSumLength(x, move);
		current.setAttribute(Constants.PR_X, xqam);
	}
}
