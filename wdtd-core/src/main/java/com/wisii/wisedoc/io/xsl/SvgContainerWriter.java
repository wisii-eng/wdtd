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

import java.util.List;
import java.util.ListIterator;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

public class SvgContainerWriter extends AbsElementWriter
{

	public SvgContainerWriter(CellElement element)
	{
		super(element);
	}

	@Override
	public String getElementName()
	{
		return FoXsltConstants.BLOCK_CONTAINER;
	}

	public String getAttributes()
	{
		StringBuffer code = new StringBuffer();
		FixedLength linewidth = getLineWidth();
		String layer = getLayer();
		if (layer != null)
		{
			attributemap.put(Constants.PR_GRAPHIC_LAYER, layer);
		}
		Object[] keys = attributemap.keySet().toArray();
		int size = keys.length;
		for (int i = 0; i < size; i++)
		{
			int key = (Integer) keys[i];
			if (key == Constants.PR_CONDTION)
			{
			} else if (key == Constants.PR_LEFT || key == Constants.PR_TOP)
			{
				code.append(getAttribute(key, new FixedLength(
						((FixedLength) attributemap.get(key)).getValue()
								- Math.round(linewidth.getValue() / 2.0f))));
			} else
			{
				code.append(getAttribute(key, attributemap.get(key)));
			}
		}
		return code.toString();
	}

	public FixedLength getLineWidth()
	{
		FixedLength linewidth = null;
		List<CellElement> children = ((SVGContainer) foElement)
				.getAllChildren();
		if (children != null && !children.isEmpty())
		{
			CellElement child = children.get(0);
			linewidth = (FixedLength) child
					.getAttribute(Constants.PR_STROKE_WIDTH);
		}
		return linewidth;
	}

	public String getLayer()
	{
		String layer = null;
		List<CellElement> children = ((SVGContainer) foElement)
				.getAllChildren();
		if (children != null && !children.isEmpty())
		{
			CellElement child = children.get(0);
			layer = child.getAttribute(Constants.PR_GRAPHIC_LAYER) != null ? child
					.getAttribute(Constants.PR_GRAPHIC_LAYER)
					+ ""
					: null;
		}
		return layer;
	}

	@SuppressWarnings("unchecked")
	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (foElement != null)
		{
			ListIterator children = ((SVGContainer) foElement).getChildNodes();
			while (children.hasNext())
			{
				CellElement child = (CellElement) children.next();
				output.append(ewf.getWriter(child).write(child));
			}
		}
		return output.toString();
	}
}
