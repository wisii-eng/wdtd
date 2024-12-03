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

import com.wisii.wisedoc.document.BasicLink;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.BlockWrapper;
import com.wisii.wisedoc.document.BookmarkTree;
import com.wisii.wisedoc.document.ChartInline;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.PageNumber;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableColumn;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.TableFooter;
import com.wisii.wisedoc.document.TableHeader;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.log.LogUtil;

public class XSLElementNameWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeKeyNameFactory#getKeyName(int)
	 */
	public String getElementName(String objname)
	{
		String name = "";
		elementName.get(objname);
		if (!elementName.containsKey(objname))
		{
			LogUtil.debug("该元素\"" + objname + "\"不是fo元素");
		} else
		{
			name = elementName.get(objname);
		}
		return name;
	}

	// 整数key和属性名之间对应关系
	private static Map<String, String> elementName = new HashMap<String, String>();
	static
	{
		elementName.put(Block.class.getName(), FoXsltConstants.BLOCK);
		elementName.put(BlockWrapper.class.getName(), FoXsltConstants.WRAPPER);
		elementName.put(BookmarkTree.class.getName(), FoXsltConstants.BOOKMARK);
		elementName.put(StaticContent.class.getName(),
				FoXsltConstants.STATIC_CONTENT);
		elementName.put(Flow.class.getName(), FoXsltConstants.FLOW);
		elementName.put(DateTimeInline.class.getName(), FoXsltConstants.INLINE);
		elementName.put(ImageInline.class.getName(), FoXsltConstants.INLINE);
		elementName.put(NumberTextInline.class.getName(),
				FoXsltConstants.INLINE);
		elementName.put(TextInline.class.getName(), FoXsltConstants.INLINE);
		elementName.put(ExternalGraphic.class.getName(),
				FoXsltConstants.EXTERNAL_GRAPHIC);
		elementName.put(QianZhang.class.getName(),
				FoXsltConstants.QIANZHANG);
		elementName.put(Table.class.getName(), FoXsltConstants.TABLE);
		elementName.put(TableBody.class.getName(), FoXsltConstants.TABLE_BODY);
		elementName.put(TableCell.class.getName(), FoXsltConstants.TABLE_CELL);
		elementName.put(TableColumn.class.getName(),
				FoXsltConstants.TABLE_COLUMN);
		elementName.put(TableFooter.class.getName(),
				FoXsltConstants.TABLE_FOOTER);
		elementName.put(TableHeader.class.getName(),
				FoXsltConstants.TABLE_HEADER);
		elementName.put(TableRow.class.getName(), FoXsltConstants.TABLE_ROW);
		elementName
				.put(PageNumber.class.getName(), FoXsltConstants.PAGE_NUMBER);
		elementName.put(BasicLink.class.getName(), FoXsltConstants.BASIC_LINK);
		elementName.put(BlockContainer.class.getName(),
				FoXsltConstants.BLOCK_CONTAINER);
		elementName.put(TableContents.class.getName(),
				FoXsltConstants.BLOCK_CONTAINER);
		elementName.put(Inline.class.getName(), FoXsltConstants.INLINE);
		elementName.put(ChartInline.class.getName(), FoXsltConstants.INLINE);
	}
}
