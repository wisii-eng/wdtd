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
/**
 * @ElementsWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableFObj;
import com.wisii.wisedoc.document.svg.SVGContainer;

/**
 * 类功能描述：处理将多个元素生成序列化的字符串数据
 * 
 * 作者：zhangqiang 创建日期：2008-10-15
 */
public class ElementsWriter extends AbstractElementsWriter
{
	public static final String ROOTNODETAG = "elements";

	public String write(List<CellElement> elements)
	{
		String returns = "";
		if (elements != null && !elements.isEmpty())
		{
			String elementstring = writeElements(elements);
			if (elementstring == null)
			{
				return null;
			}
			returns = returns + elementstring;
			String attstring = generateAttributesesString();
			String logicalliststring = generateLogicallistString();
			String groupliststring = generateGrouplistString();
			String dynamicstyleliststring = generateDynamicStylelistString();
			String paragraphStylesliststring = generateParagraphStyleslistString();
			String chartdataliststring = generateChartDataListesString();
			String datasourceliststring = generateDataSourceListString();
			String selectinfoliststring = generateSelectInfoListString();
			String popupbrowserinfoliststring = generatePopupBrowserInfoListString();
			String connwithstring = generateConnwithListString();
			String groupuistring = generateGroupUIListString();
			String bgliststring = generateButtonGrouplistString();
			returns = logicalliststring + groupliststring + chartdataliststring
					+ datasourceliststring + selectinfoliststring + popupbrowserinfoliststring
					+ connwithstring + groupuistring + bgliststring + attstring
					+ dynamicstyleliststring + paragraphStylesliststring
					+ returns;
			returns = WSDWriter.XMLDEFINE + WSDWriter.WSDBEGINDEFINE + returns
			+ WSDWriter.WSDENDDEFINE;
			clear();
		}
		
		return returns;
	};

	private String writeElements(List<CellElement> elements)
	{
		String returns = "";
		int size = elements.size();
		List<CellElement> nelements = new ArrayList<CellElement>();
		for (int i = 0; i < size; i++)
		{
			CellElement element = elements.get(i);
			// 只生成流内的内容，如果选择区域超出流，则取其流内内容
			if (element instanceof PageSequence)
			{
				Iterator<CellElement> it = ((PageSequence) element)
						.getMainFlow().getChildren();
				while (it.hasNext())
				{
					CellElement celle = it.next();
					if (!nelements.contains(celle))
					{
						nelements.add(celle);
					}
				}
			} else if (element instanceof Flow)
			{
				Iterator<CellElement> it = element.getChildren();
				while (it.hasNext())
				{
					CellElement celle = it.next();
					if (!nelements.contains(celle))
					{
						nelements.add(celle);
					}
				}
			} else if ((element instanceof TableFObj)
					&& !(element instanceof Table))
			{

			} else if ((element instanceof AbstractGraphics)
					|| (element.getParent() instanceof SVGContainer))
			{
				if (!nelements.contains(element.getParent()))
				{
					nelements.add((CellElement) element.getParent());
				}
			} else
			{
				if (!nelements.contains(element))
				{
					nelements.add(element);
				}
			}
		}
		if (!nelements.isEmpty())
		{

			returns = returns + TAGBEGIN + ROOTNODETAG + TAGEND + LINEBREAK;
			returns = returns + generateElementsStrng(nelements);
			returns = returns + TAGENDSTART + ROOTNODETAG + TAGEND + LINEBREAK;
		}
		if (returns.equals(""))
		{
			returns = null;
		}
		return returns;
	}
}
