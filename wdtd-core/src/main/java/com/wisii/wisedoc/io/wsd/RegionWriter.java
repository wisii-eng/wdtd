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
 * @RegionWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.RegionAfter;
import com.wisii.wisedoc.document.attribute.RegionBA;
import com.wisii.wisedoc.document.attribute.RegionBefore;
import com.wisii.wisedoc.document.attribute.RegionEnd;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.SideRegion;
import com.wisii.wisedoc.io.ElementWriter;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-9-22
 */
final class RegionWriter extends SideRegionWriter {
	private String BEFORENAME = "regionbefore";
	private String AFTERNAME = "regionafter";
	private String STRATNAME = "regionstart";
	private String ENDNAME = "regionend";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.AbstractRegionWriter#write(com.wisii.wisedoc
	 * .document.attribute.Region)
	 */
	@Override
	public String write(Region region) {
		String name;
		if(region instanceof RegionBefore)
		{
			name = BEFORENAME;
		}
		else if(region instanceof RegionAfter)
		{
			name = AFTERNAME;
		}
		else if(region instanceof RegionStart)
		{
			name = STRATNAME;
		}
		else if(region instanceof RegionEnd)
		{
			name = ENDNAME;
		}
		else
		{
			return "";
		}
		String returns = ElementWriter.TAGBEGIN + name + " ";
		returns = returns + super.generateRegionAttString((SideRegion) region);
		if (region instanceof RegionBA) {
			Object precedence = ((RegionBA) region).getPrecedence();
			if (precedence instanceof Integer) {
				precedence = new EnumProperty((Integer)precedence,"");
				returns = returns
						+ attiofactory
								.getAttributeWriter(precedence.getClass())
								.write(Constants.PR_PRECEDENCE, precedence);
			}
		}
		returns = returns + ElementWriter.TAGEND + ElementWriter.LINEBREAK;
		returns = returns + ElementWriter.TAGENDSTART + name
				+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
		return returns;
	}

}
