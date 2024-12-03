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
 * @CommonMarginBlockWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.attribute;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.io.xsl.SelectOutputAttributeWriter;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-8
 */
public class CommonMarginBlockWriter extends OutputAttributeWriter
{

	SelectOutputAttributeWriter writerFactory = new SelectOutputAttributeWriter();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter#write(java.lang
	 * .Object)
	 */
	public String write(Object value)
	{
		StringBuffer output = new StringBuffer();
		CommonMarginBlock margin = (CommonMarginBlock) value;
		Length marginTop = margin.getMarginTop();
		Length marginLeft = margin.getMarginLeft();
		Length marginRight = margin.getMarginRight();
		Length marginBottom = margin.getMarginBottom();

		if (marginTop.equals(marginBottom) && marginLeft.equals(marginRight)
				&& marginTop.equals(marginLeft))
		{
			output.append(writerFactory.write(Constants.PR_MARGIN, marginTop));
		} else
		{
			output.append(writerFactory.write(Constants.PR_MARGIN_TOP,
					marginTop));
			output.append(writerFactory.write(Constants.PR_MARGIN_BOTTOM,
					marginBottom));
			output.append(writerFactory.write(Constants.PR_MARGIN_LEFT,
					marginLeft));
			output.append(writerFactory.write(Constants.PR_MARGIN_RIGHT,
					marginRight));
		}

		// SpaceProperty spaceBefore = margin.getSpaceBefore();
		// SpaceProperty spaceAfter = margin.getSpaceAfter();
		//output.append(writerFactory.write(Constants.PR_SPACE_BEFORE,spaceBefore
		// ));
		//output.append(writerFactory.write(Constants.PR_SPACE_AFTER,spaceAfter)
		// );
		Length startIndent = margin.getStartIndent();
		if (startIndent.getValue() != 0)
		{
			output.append(writerFactory.write(Constants.PR_START_INDENT,
					startIndent));
		}
		Length endIndent = margin.getEndIndent();
		if (endIndent.getValue() != 0)
		{
			output.append(writerFactory.write(Constants.PR_END_INDENT,
					endIndent));
		}
		return output.toString();
	}
}
