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
 * @abs.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.ElementWriterFactory;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-22
 */
public class SelectElementWriterFactory implements ElementWriterFactory
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.ElementWriterFactory#getWriter(com.wisii.wisedoc
	 * .document.CellElement)
	 */
	public ElementWriter getWriter(CellElement element)
	{
		if (element != null)
		{
			if (element instanceof SVGContainer)
			{
				return new SvgContainerWriter(element);
			} else if (element instanceof ZiMoban)
			{
				return new ZiMobanWriter(element);
			} else if (element instanceof TableContents)
			{
				return new TableContentsWriter(element);
			} else if (element instanceof Table)
			{
				return new TableWriter(element);
			} else if (element instanceof TableRow)
			{
				return new TableRowWriter(element);
			} else if (element instanceof BlockContainer)
			{
				return new BlockContainerWriter(element);
			} else if (element instanceof Block)
			{
				return new BlockWriter(element);
			} else if (element instanceof Chart)
			{
				return new PieBarWriter(element);
			} else if (element instanceof ExternalGraphic)
			{
				return new ExternalGraphicWriter(element);
			}
			 else if (element instanceof QianZhang)
			{
				return new QianZhangWriter(element);
			}
			else if (element instanceof BarCode)
			{
				return new InstreamForeignObjectWriter(element);
			} else if (element instanceof Group)
			{
				return new GroupWriter(element);
			} else if (element instanceof CellElement)
			{
				return new FoElementWriter(element);
			} else
			{
				return new FoElementWriter(element);
			}
		}
		return null;
	}
}
