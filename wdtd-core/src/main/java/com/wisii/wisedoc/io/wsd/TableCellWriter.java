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
 * 
 */
package com.wisii.wisedoc.io.wsd;

import java.util.Enumeration;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.attribute.Attributes;

/**
 * @author HP_Administrator
 *
 */
public class TableCellWriter extends DefaultElementWriter
{
	public static String ID = "tablecellid";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.ElementWriter#write(com.wisii.wisedoc.document.Element
	 * )
	 */
	public String write(CellElement element) {
		String returns = "";
		if (element != null) {
			Attributes atts = element.getAttributes();
			//去除掉跨列属性和列号属性（这些属性是在排版前临时生成的）
			if(atts!=null)
			{
				Map<Integer,Object> attmap = atts.getAttributes();
				attmap.remove(Constants.PR_NUMBER_COLUMNS_SPANNED);
				attmap.remove(Constants.PR_COLUMN_NUMBER);
				atts = new Attributes(attmap);
			}
			String attsrefid = "";
			// 生成属性引用代码
			String refid = DocumentWirter.getAttributesID(atts);
			if (atts != null&&refid != null) {
				attsrefid = attsrefid + ATTRIBUTEREFID + EQUALTAG
						+ QUOTATIONTAG + refid
						+ QUOTATIONTAG + SPACETAG;
			}
			String idref = "";
			TableCell tc =  (TableCell) element;
			if(tc.getNumberRowsSpanned()> 1)
			{
				idref = SPACETAG + ID + EQUALTAG + QUOTATIONTAG + tc.getId() + QUOTATIONTAG;
			}
			String elementname = getElementName(element);
			// 生成元素头代码
			returns = returns + TAGBEGIN + elementname + SPACETAG + attsrefid + idref
					+ TAGEND + LINEBREAK;
			Enumeration<CellElement> children = element.children();
			// 生成子元素代码
			returns = returns+ getChildrenString(children);
			// 生成元素结束代码
			returns = returns + TAGENDSTART + elementname + TAGEND + LINEBREAK;
		}
		return returns;
	}
}
