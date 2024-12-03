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
public class SvgBottomMove extends SvgMove
{

	public boolean lineOverFlow(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		Element huabu = current.getParent();
		Map<Integer, Object> huabuattribute = huabu.getAttributes()
				.getAttributes();
		FixedLength y1 = (FixedLength) oldattribute.get(Constants.PR_Y1);
		FixedLength yqam = SVGUtil.getSumLength(y1, move);
		FixedLength y2 = (FixedLength) oldattribute.get(Constants.PR_Y2);
		FixedLength yzam = SVGUtil.getSumLength(y2, move);
		FixedLength height = (FixedLength) huabuattribute
				.get(Constants.PR_HEIGHT);
		return SVGUtil.inLeftTopSide(yqam, height)
				&& SVGUtil.inLeftTopSide(yzam, height);
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
		FixedLength huabuheight = (FixedLength) huabuattribute
				.get(Constants.PR_HEIGHT);
		FixedLength y = (FixedLength) oldattribute.get(Constants.PR_Y);
		FixedLength height = (FixedLength) oldattribute
				.get(Constants.PR_HEIGHT);
		FixedLength yqam = SVGUtil.getSumLength(y, move);
		FixedLength yzam = SVGUtil.getSumLength(yqam, height);
		return SVGUtil.inLeftTopSide(yzam, huabuheight);
	}

	public void svgContainerMove(CellElement current, FixedLength move)
	{
		Element huabu = current.getParent();
		Map<Integer, Object> containerattribute = huabu.getAttributes()
				.getAttributes();
		FixedLength top = (FixedLength) containerattribute
				.get(Constants.PR_TOP);
		FixedLength topAfterMove = SVGUtil.getSumLength(top, move);
		huabu.setAttribute(Constants.PR_TOP, topAfterMove);
	}

	public void lineMove(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		FixedLength y1 = (FixedLength) oldattribute.get(Constants.PR_Y1);
		FixedLength yqam = SVGUtil.getSumLength(y1, move);
		FixedLength y2 = (FixedLength) oldattribute.get(Constants.PR_Y2);
		FixedLength yzam = SVGUtil.getSumLength(y2, move);
		current.setAttribute(Constants.PR_Y1, yqam);
		current.setAttribute(Constants.PR_Y2, yzam);
	}

	public void rectangleMove(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		FixedLength y = (FixedLength) oldattribute.get(Constants.PR_Y);
		FixedLength yqam = SVGUtil.getSumLength(y, move);
		current.setAttribute(Constants.PR_Y, yqam);
	}
}