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
 * @RepeatablePageMasterAlternativesWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.util.List;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-8
 */
public class RepeatablePageMasterAlternativesWriter extends
		AbsLayoutElementWriter
{

	RepeatablePageMasterAlternatives repeatablePageMasterAlternatives;

	public RepeatablePageMasterAlternativesWriter(
			RepeatablePageMasterAlternatives repeatablepagemasterAlternatives)
	{
		repeatablePageMasterAlternatives = repeatablepagemasterAlternatives;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getAttributes()
	 */
	public String getAttributes()
	{
		StringBuffer output = new StringBuffer();
		int maxnumber = repeatablePageMasterAlternatives.getMaximumRepeats();
		if (maxnumber > 0)
		{
			output
					.append(getAttribute(Constants.PR_MAXIMUM_REPEATS,
							maxnumber));
		}
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getChildCode()
	 */
	public String getChildCode()
	{

		StringBuffer output = new StringBuffer();
		if (repeatablePageMasterAlternatives != null
				&& repeatablePageMasterAlternatives.getPageMasterRefs() != null)
		{
			List<ConditionalPageMasterReference> listobj = repeatablePageMasterAlternatives
					.getPageMasterRefs();
			int size = listobj.size();
			if (size == 1)
			{
				ConditionalPageMasterReference subitem = listobj.get(0);
				int any = Constants.EN_ANY;
				if (subitem.getPagePosition() == Constants.EN_REST
						&& subitem.getOddOrEven() == any
						&& subitem.getBlankOrNotBlank() == any)
				{
					output.append(new ConditionalPageMasterReferenceWriter(
							subitem).getCodeAny());
					return output.toString();
				}
			}
			for (int i = 0; i < size; i++)
			{
				ConditionalPageMasterReference subitem = listobj.get(i);
				output.append(new ConditionalPageMasterReferenceWriter(subitem)
						.getCode());
			}
		}
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getElementName()
	 */
	public String getElementName()
	{
		return FoXsltConstants.REPEATABLE_PAGE_MASTER_ALTERNATIVES;
	}

}
