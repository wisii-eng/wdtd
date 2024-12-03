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
 * @PageSequenceMasterWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.util.List;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterReference;
import com.wisii.wisedoc.document.attribute.SinglePageMasterReference;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-8
 */
public class PageSequenceMasterWriter extends AbsLayoutElementWriter
{

	PageSequenceMaster pageSequenceMaster;

	public PageSequenceMaster getPageSequenceMaster()
	{
		return pageSequenceMaster;
	}

	String masterName = "";

	public PageSequenceMasterWriter(PageSequenceMaster pagesequencemaster)
	{
		pageSequenceMaster = pagesequencemaster;
		masterName = FoXsltConstants.PAGESEQUENCEMASTER
				+ pagesequencemaster.hashCode();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getAttributes()
	 */
	public String getAttributes()
	{
		StringBuffer output = new StringBuffer();
		output.append(getAttribute(Constants.PR_MASTER_NAME, pageSequenceMaster
				.hashCode()));
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
		if (pageSequenceMaster != null
				&& pageSequenceMaster.getSubsequenceSpecifiers() != null)
		{
			List<SubSequenceSpecifier> listobj = pageSequenceMaster
					.getSubsequenceSpecifiers();
			int size = listobj.size();
			for (int i = 0; i < size; i++)
			{
				SubSequenceSpecifier subitem = listobj.get(i);
				if (subitem instanceof SinglePageMasterReference)
				{
					SinglePageMasterReference singlePageMasterReference = (SinglePageMasterReference) subitem;
					output.append(new SinglePageMasterReferenceWriter(
							singlePageMasterReference).getCode());

				} else if (subitem instanceof RepeatablePageMasterReference)
				{
					RepeatablePageMasterReference repeatablePageMasterReference = (RepeatablePageMasterReference) subitem;
					output.append(new RepeatablePageMasterReferenceWriter(
							repeatablePageMasterReference).getCode());
				} else if (subitem instanceof RepeatablePageMasterAlternatives)
				{
					RepeatablePageMasterAlternatives repeatablePageMasterAlternatives = (RepeatablePageMasterAlternatives) subitem;
					output.append(new RepeatablePageMasterAlternativesWriter(
							repeatablePageMasterAlternatives).getCode());
				}
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
		return FoXsltConstants.PAGE_SEQUENCE_MASTER;
	}

	/**
	 * @返回 masterName变量的值
	 */
	public String getMasterName()
	{
		return masterName;
	}

	/**
	 * @param masterName
	 *            设置masterName成员变量的值 值约束说明
	 */
	public void setMasterName(String masterName)
	{
		this.masterName = masterName;
	}

	public String write()
	{
		return getCode();
	}

	public String getCode()
	{
		StringBuffer code = new StringBuffer();
		code.append(getHeaderElement());
		code.append(getChildCode());
		code.append(getEndElement());
		return code.toString();
	}
}
