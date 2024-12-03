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
 * @ConditionalPageMasterReferenceWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-8
 */
public class ConditionalPageMasterReferenceWriter extends
		AbsLayoutElementWriter
{

	ConditionalPageMasterReference conditionalPageMasterReference;

	public ConditionalPageMasterReferenceWriter(
			ConditionalPageMasterReference conditionalpagemasterreference)
	{
		conditionalPageMasterReference = conditionalpagemasterreference;

		WiseDocDocumentWriter.addSimP(conditionalPageMasterReference
				.getMasterReference());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getAttributes()
	 */
	public String getAttributes()
	{
		StringBuffer output = new StringBuffer();
		int any = Constants.EN_ANY;
		output
				.append(getAttribute(Constants.PR_MASTER_REFERENCE,
						conditionalPageMasterReference.getMasterReference()
								.hashCode()));
		int _pagePosition = conditionalPageMasterReference.getPagePosition();
		int _oddOrEven = conditionalPageMasterReference.getOddOrEven();
		int _blankOrNotBlank = conditionalPageMasterReference
				.getBlankOrNotBlank();
		if (_pagePosition == any && _oddOrEven == any
				&& _blankOrNotBlank == any)
		{
			output.append(getAttribute(Constants.PR_PAGE_POSITION,
					new EnumProperty(conditionalPageMasterReference
							.getPagePosition(), "")));
		} else
		{
			if (_pagePosition >= 0 && _pagePosition != any)
			{
				output.append(getAttribute(Constants.PR_PAGE_POSITION,
						new EnumProperty(conditionalPageMasterReference
								.getPagePosition(), "")));
			}
			if (_oddOrEven >= 0 && _oddOrEven != any)
			{
				output.append(getAttribute(Constants.PR_ODD_OR_EVEN,
						new EnumProperty(conditionalPageMasterReference
								.getOddOrEven(), "")));
			}
			if (_blankOrNotBlank >= 0 && _blankOrNotBlank != any)
			{
				output.append(getAttribute(Constants.PR_BLANK_OR_NOT_BLANK,
						new EnumProperty(conditionalPageMasterReference
								.getBlankOrNotBlank(), "")));
			}
		}
		return output.toString();
	}

	public String getAttributesAny()
	{
		StringBuffer output = new StringBuffer();
		int any = Constants.EN_ANY;
		output
				.append(getAttribute(Constants.PR_MASTER_REFERENCE,
						conditionalPageMasterReference.getMasterReference()
								.hashCode()));
		output.append(getAttribute(Constants.PR_PAGE_POSITION,
				new EnumProperty(any, "")));
		return output.toString();
	}

	public String getCodeAny()
	{
		StringBuffer code = new StringBuffer();
		code.append(getHeaderStart());
		code.append(getAttributesAny());
		code.append(getHeaderEnd());
		code.append(getEndElement());
		return code.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getElementName()
	 */
	public String getElementName()
	{
		return FoXsltConstants.CONDITIONAL_PAGE_MASTER_REFERENCE;
	}

}
