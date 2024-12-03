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
 * @SimplePageMasterWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.io.ElementWriter;

/**
 * 类功能描述：SimplePageMaster对象的writer类
 * 
 * 作者：zhangqiang 创建日期：2008-9-22
 */
public final class SimplePageMasterWriter {
	private final String NAME = "simplepagemaster";
	private WSDAttributeIOFactory attiofactory = new WSDAttributeIOFactory();
	private AbstractRegionWriter rw = new RegionWriter();
	private AbstractRegionWriter rbw = new RegionBodyWriter();
	public String write(SimplePageMaster spm) {
		String returns = "";
		if (spm != null) {
			returns = returns+ElementWriter.TAGBEGIN + NAME + " ";
			returns = returns
					+ generateComMarinBlockString(spm.getCommonMarginBlock());
			String mastername = spm.getMasterName();
			if (mastername != null) {
				returns = returns
						+ attiofactory
								.getAttributeWriter(mastername.getClass())
								.write(Constants.PR_MASTER_NAME, mastername);
			}
			String visualmastername = spm.getVirtualMasterName();
			if (visualmastername != null) {
				returns = returns
						+ attiofactory
								.getAttributeWriter(visualmastername.getClass())
								.write(Constants.PR_VIRTUAL_MASTER_NAME, visualmastername);
			}
			String mediaUsage = spm.getMediaUsage();
			if (mediaUsage != null&&!mediaUsage.isEmpty()) {
				returns = returns
						+ attiofactory
								.getAttributeWriter(mediaUsage.getClass())
								.write(Constants.PR_MEDIA_USAGE, mediaUsage);
			}
			Object pageheight = spm.getPageHeight();
			if (pageheight != null) {
				returns = returns
						+ attiofactory
								.getAttributeWriter(pageheight.getClass())
								.write(Constants.PR_PAGE_HEIGHT, pageheight);
			}
			Object pagewidth = spm.getPageWidth();
			if (pagewidth != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(pagewidth.getClass())
								.write(Constants.PR_PAGE_WIDTH, pagewidth);
			}
			Object writemode = spm.getWritingMode();
			if (writemode != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(writemode.getClass())
								.write(Constants.PR_WRITING_MODE, writemode);
			}
			Object orientation = spm.getReferenceOrientation();
			if (orientation != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(
								orientation.getClass())
								.write(Constants.PR_REFERENCE_ORIENTATION,
										orientation);
			}
			returns = returns + ElementWriter.TAGEND + ElementWriter.LINEBREAK;
			returns = returns + generateRegionsString(spm.getRegions());
			returns = returns + ElementWriter.TAGENDSTART + NAME + ElementWriter.TAGEND
					+ ElementWriter.LINEBREAK;
		}
		return returns;
	}

	/**
	 * 
	 * 生成region相关的内容
	 * 
	 * @param
	 * @return
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private String generateRegionsString(Map<Integer, Region> regions) {
		String returns = "";
		Region regionbefore = regions.get(Constants.FO_REGION_BEFORE);
		if (regionbefore != null) {
			returns = returns + rw.write(regionbefore);
		}
		Region regionafter = regions.get(Constants.FO_REGION_AFTER);
		if (regionafter != null) {
			returns = returns + rw.write(regionafter);
		}
		Region regionstart = regions.get(Constants.FO_REGION_START);
		if (regionstart != null) {
			returns = returns + rw.write(regionstart);
		}
		Region regionend = regions.get(Constants.FO_REGION_END);
		if (regionend != null) {
			returns = returns + rw.write(regionend);
		}
		Region regionbody = regions.get(Constants.FO_REGION_BODY);
		if (regionbody != null) {
			returns = returns + rbw.write(regionbody);
		}
		return returns;
	}

	private String generateComMarinBlockString(
			CommonMarginBlock commonMarginBlock) {
		String returns = "";
		if (commonMarginBlock != null) {
			Object endIndent = commonMarginBlock.getEndIndent();
			if (endIndent != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(endIndent.getClass())
								.write(Constants.PR_END_INDENT, endIndent);
			}
			Object marginBottom = commonMarginBlock.getMarginBottom();
			if (marginBottom != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(
								marginBottom.getClass()).write(
								Constants.PR_MARGIN_BOTTOM, marginBottom);
			}
			Object marginLeft = commonMarginBlock.getMarginLeft();
			if (marginLeft != null) {
				returns = returns
						+ attiofactory
								.getAttributeWriter(marginLeft.getClass())
								.write(Constants.PR_MARGIN_LEFT, marginLeft);
			}
			Object marginRight = commonMarginBlock.getMarginRight();
			if (marginRight != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(
								marginRight.getClass()).write(
								Constants.PR_MARGIN_RIGHT, marginRight);

			}
			Object marginTop = commonMarginBlock.getMarginTop();
			if (marginTop != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(marginTop.getClass())
								.write(Constants.PR_MARGIN_TOP, marginTop);
			}
			Object spaceAfter = commonMarginBlock.getSpaceAfter();
			if (spaceAfter != null) {
				returns = returns
						+ attiofactory
								.getAttributeWriter(spaceAfter.getClass())
								.write(Constants.PR_SPACE_AFTER, spaceAfter);
			}
			Object spaceBefore = commonMarginBlock.getSpaceBefore();
			if (spaceBefore != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(
								spaceBefore.getClass()).write(
								Constants.PR_SPACE_BEFORE, spaceBefore);
			}
			Object startIndent = commonMarginBlock.getStartIndent();
			if (startIndent != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(
								startIndent.getClass()).write(
								Constants.PR_START_INDENT, startIndent);
			}
		}
		return returns;
	}
}
