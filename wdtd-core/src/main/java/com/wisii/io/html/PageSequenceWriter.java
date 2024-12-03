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
 * @PageSequenceWriter.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html;

import java.util.List;

import com.wisii.io.html.object.DefaultObjectWriter;
import com.wisii.io.html.object.ObjectWriter;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.RegionBA;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang
 * 创建日期：2012-5-29
 */
public class PageSequenceWriter extends DefaultObjectWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 
	 * com.wisii.io.html.object.ObjectWriter#getString(com.wisii.wisedoc.document
	 * .CellElement, com.wisii.wisedoc.io.xsl.util.XslContext)
	 */
	@Override
	public String getString(CellElement element, HtmlContext context)
	{
		if (element instanceof PageSequence)
		{
			StringBuffer sb = new StringBuffer();
			PageSequence ps = (PageSequence) element;
			SimplePageMaster master = ps.getNextSimplePageMaster(0, false,
					false, false);
			DefaultObjectWriter writer = new DefaultObjectWriter();
			RegionBA before = (RegionBA) master
					.getRegion(Constants.FO_REGION_BEFORE);
			Attributes att=ps.getAttributes();
			if (before != null && before.getExtent().getValue() > 0)
			{
				StaticContent beforecont = ps.getStaticContent(before
						.getRegionName());
				List<CellElement> children = beforecont.getAllChildren();
				if (children != null && !children.isEmpty())
				{
					for (CellElement child : children)
					{
						sb.append(writer.getString(child, context));
					}
				}
			}

			Flow flow = ps.getMainFlow();

			List<CellElement> children = flow.getAllChildren();
			for (CellElement child : children)
			{
				sb.append(writer.getString(child, context));
			}
			RegionBA after = (RegionBA) master
					.getRegion(Constants.FO_REGION_AFTER);
			if (after != null && after.getExtent().getValue() > 0)
			{
				StaticContent aftercont = ps.getStaticContent(after
						.getRegionName());
				List<CellElement> afterchildren = aftercont.getAllChildren();
				if (afterchildren != null && !afterchildren.isEmpty())
				{
					for (CellElement child : afterchildren)
					{
						sb.append(writer.getString(child, context));
					}
				}
			}
			return sb.toString();
		}
		return "";
	}

}
