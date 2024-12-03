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
 * @RegionBodyWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.io.ElementWriter;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-8
 */
public class RegionBodyWriter extends AbstractRegionWriter {
	private String NAME = "regionbody";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.AbstractRegionWriter#write(com.wisii.wisedoc
	 * .document.attribute.Region)
	 */
	@Override
	public String write(Region region) {
		if (!(region instanceof RegionBody)) {
			return "";
		}
		RegionBody regionbody = (RegionBody) region;
		String returns = ElementWriter.TAGBEGIN + NAME + " ";
		returns = returns + super.generateRegionAttString(region);
		Integer columncount = regionbody.getColumnCount();
		if (columncount > 1) {
			returns = returns
					+ attiofactory.getAttributeWriter(columncount.getClass())
							.write(Constants.PR_COLUMN_COUNT, columncount);
			Object columngap = regionbody.getColumnGapLength();
			if (columngap != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(columngap.getClass())
								.write(Constants.PR_COLUMN_GAP, columngap);
			}
		}
		CommonMarginBlock marginblock = regionbody.getCommonMarginBlock();
		if (marginblock != null) {
			returns = returns + generatesting(marginblock);
		}
		returns = returns + ElementWriter.TAGEND + ElementWriter.LINEBREAK;
		returns = returns + ElementWriter.TAGENDSTART + NAME
				+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
		return returns;
	}

	private String generatesting(CommonMarginBlock marginblock) {
		String returns = "";
		Object endIndent = marginblock.getEndIndent();
		if (endIndent != null) {
			returns = returns
					+ attiofactory.getAttributeWriter(endIndent.getClass())
							.write(Constants.PR_END_INDENT, endIndent);
		}
		Object marginBottom = marginblock.getMarginBottom();
		if (marginBottom != null) {
			returns = returns
					+ attiofactory.getAttributeWriter(marginBottom.getClass())
							.write(Constants.PR_MARGIN_BOTTOM, marginBottom);
		}
		Object marginLeft = marginblock.getMarginLeft();
		if (marginLeft != null) {
			returns = returns
					+ attiofactory.getAttributeWriter(marginLeft.getClass())
							.write(Constants.PR_MARGIN_LEFT, marginLeft);
		}
		Object marginRight = marginblock.getMarginRight();
		if (marginRight != null) {
			returns = returns
					+ attiofactory.getAttributeWriter(marginRight.getClass())
							.write(Constants.PR_MARGIN_RIGHT, marginRight);
		}
		Object marginTop = marginblock.getMarginTop();
		if (marginTop != null) {
			returns = returns
					+ attiofactory.getAttributeWriter(marginTop.getClass())
							.write(Constants.PR_MARGIN_TOP, marginTop);
		}
		Object spaceAfter = marginblock.getSpaceAfter();
		if (spaceAfter != null) {
			returns = returns
					+ attiofactory.getAttributeWriter(spaceAfter.getClass())
							.write(Constants.PR_SPACE_AFTER, spaceAfter);
		}
		Object spaceBefore = marginblock.getSpaceBefore();
		if (spaceBefore != null) {
			returns = returns
					+ attiofactory.getAttributeWriter(spaceBefore.getClass())
							.write(Constants.PR_SPACE_BEFORE, spaceBefore);
		}
		Object startIndent = marginblock.getStartIndent();
		if (startIndent != null) {
			returns = returns
					+ attiofactory.getAttributeWriter(startIndent.getClass())
							.write(Constants.PR_START_INDENT, startIndent);
		}
		return returns;
	}

}
