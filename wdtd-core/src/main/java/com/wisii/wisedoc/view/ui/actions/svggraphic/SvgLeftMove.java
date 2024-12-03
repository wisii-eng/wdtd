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
public class SvgLeftMove extends SvgMove
{

	public boolean lineOverFlow(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		FixedLength x1 = (FixedLength) oldattribute.get(Constants.PR_X1);
		FixedLength x2 = (FixedLength) oldattribute.get(Constants.PR_X2);
		FixedLength xqam = SVGUtil.getSubLength(x1, move);
		FixedLength xzam = SVGUtil.getSubLength(x2, move);
		return SVGUtil.inRightBottomSide(null, xqam)
				&& SVGUtil.inRightBottomSide(null, xzam);
	}

	public boolean svgContainerOverFlow(CellElement current, FixedLength move)
	{
		Element huabu = current.getParent();
		Map<Integer, Object> containerattribute = huabu.getAttributes()
				.getAttributes();
		FixedLength left = (FixedLength) containerattribute
				.get(Constants.PR_LEFT);
		FixedLength leftAfterMove = SVGUtil.getSubLength(left, move);
		if (SVGUtil.inRightBottomSide(null, leftAfterMove))
		{
			return true;
		}
		return false;
	}

	public boolean rectangleOverFlow(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		FixedLength x = (FixedLength) oldattribute.get(Constants.PR_X);
		FixedLength xam = SVGUtil.getSubLength(x, move);
		return SVGUtil.inRightBottomSide(null, xam);
	}
	public void svgContainerMove(CellElement current, FixedLength move)
	{
		Element huabu = current.getParent();
		Map<Integer, Object> containerattribute = huabu.getAttributes()
				.getAttributes();
		FixedLength left = (FixedLength) containerattribute
				.get(Constants.PR_LEFT);
		FixedLength leftAfterMove = SVGUtil.getSubLength(left, move);
		current.setAttribute(Constants.PR_LEFT, leftAfterMove);
	}

	public void lineMove(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		FixedLength x1 = (FixedLength) oldattribute.get(Constants.PR_X1);
		FixedLength x2 = (FixedLength) oldattribute.get(Constants.PR_X2);
		FixedLength xqam = SVGUtil.getSubLength(x1, move);
		FixedLength xzam = SVGUtil.getSubLength(x2, move);
		current.setAttribute(Constants.PR_X1, xqam);
		current.setAttribute(Constants.PR_X2, xzam);
	}

	public void rectangleMove(CellElement current, FixedLength move)
	{
		Map<Integer, Object> oldattribute = current.getAttributes()
				.getAttributes();
		FixedLength x = (FixedLength) oldattribute.get(Constants.PR_X);
		FixedLength xam = SVGUtil.getSubLength(x, move);
		current.setAttribute(Constants.PR_X, xam);
	}
}
