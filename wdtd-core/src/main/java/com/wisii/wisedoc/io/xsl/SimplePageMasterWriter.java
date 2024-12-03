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

package com.wisii.wisedoc.io.xsl;

import java.awt.Color;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.RegionAfter;
import com.wisii.wisedoc.document.attribute.RegionBefore;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.RegionEnd;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.io.xsl.attribute.CommonMarginBlockWriter;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-8
 */
public class SimplePageMasterWriter extends AbsLayoutElementWriter
{

	SimplePageMaster simplePageMaster;

	public SimplePageMaster getSimplePageMaster()
	{
		return simplePageMaster;
	}

	String simplelePageMasterName = "";

	int regionsflg = 0;

	CommonMarginBlock bodycmp = null;

	public SimplePageMasterWriter(SimplePageMaster simplepagemaster)
	{
		simplePageMaster = simplepagemaster;
		regionsflg = 0;
		simplelePageMasterName = simplepagemaster.hashCode() + "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getAttributes()
	 */
	public String getAttributes()
	{
		StringBuffer output = new StringBuffer();
		output.append(getAttribute(Constants.PR_MASTER_NAME,
				getSimplelePageMasterName()));
		output.append(getAttribute(Constants.PR_PAGE_HEIGHT, simplePageMaster
				.getPageHeight()));
		output.append(getAttribute(Constants.PR_PAGE_WIDTH, simplePageMaster
				.getPageWidth()));
		int ro = simplePageMaster.getReferenceOrientation();
		if (ro != 0.0d)
		{
			output.append(getAttribute(Constants.PR_REFERENCE_ORIENTATION, ro));
		}
		// if (((EnumProperty) simplePageMaster.getWritingMode()).getEnum() ==
		// Constants.EN_LR_TB)
		if (simplePageMaster.getWritingMode() != Constants.EN_LR_TB)
		{
			output.append(getAttribute(Constants.PR_WRITING_MODE,
					new EnumProperty(simplePageMaster.getWritingMode(), "")));
		}
		output.append(getAttribute(Constants.PR_MEDIA_USAGE, simplePageMaster
				.getMediaUsage()));
		CommonMarginBlock cmb = dealRegionsAndMargins();
		output.append(new CommonMarginBlockWriter().write(cmb));
		return output.toString();
	}

	public CommonMarginBlock dealRegionsAndMargins()
	{
		CommonMarginBlock old = simplePageMaster.getCommonMarginBlock();
		Map<Integer, Region> regions = simplePageMaster.getRegions();
		RegionBefore regionbefore = (RegionBefore) regions
				.get(Constants.FO_REGION_BEFORE);
		RegionAfter regionafter = (RegionAfter) regions
				.get(Constants.FO_REGION_AFTER);
		RegionStart regionstart = (RegionStart) regions
				.get(Constants.FO_REGION_START);
		RegionEnd regionend = (RegionEnd) regions.get(Constants.FO_REGION_END);
		RegionBody regionbody = (RegionBody) regions
				.get(Constants.FO_REGION_BODY);
		CommonMarginBlock oldregionbodycmb = regionbody.getCommonMarginBlock();
		bodycmp = oldregionbodycmb;
		boolean beforeflg = isNotOut(regionbefore);
		boolean afterflg = isNotOut(regionafter);
		boolean startflg = isNotOut(regionstart);
		boolean endflg = isNotOut(regionend);
		if (!(beforeflg && afterflg && startflg && endflg))
		{
			Length top = old.getMarginTop();
			Length bottom = old.getMarginBottom();
			Length left = old.getMarginLeft();
			Length right = old.getMarginRight();
			Length topbody = oldregionbodycmb.getMarginTop();
			Length bottombody = oldregionbodycmb.getMarginBottom();
			Length leftbody = oldregionbodycmb.getMarginLeft();
			Length rightbody = oldregionbodycmb.getMarginRight();
			boolean beforeyouxian = regionbefore.getPrecedence() == Constants.EN_TRUE ? true
					: false;
			boolean afteryouxian = regionafter.getPrecedence() == Constants.EN_TRUE ? true
					: false;
			if (beforeyouxian)
			{
				if (beforeflg)
				{
					Length extentbefore = regionbefore != null ? regionbefore
							.getExtent() : null;
					top = getNewAddLength(extentbefore, top);
					topbody = getNewSubLength(extentbefore, topbody);
					regionsflg = regionsflg + 1000;
					if (afteryouxian)
					{
						if (afterflg)
						{
							Length extentafter = regionafter != null ? regionafter
									.getExtent()
									: null;
							bottom = getNewAddLength(extentafter, bottom);
							bottombody = getNewSubLength(extentafter,
									bottombody);
							regionsflg = regionsflg + 100;
							if (startflg)
							{
								regionsflg = regionsflg + 10;
							}
							if (endflg)
							{
								regionsflg = regionsflg + 1;
							}
						}
					} else
					{
						if (afterflg)
						{
							regionsflg = regionsflg + 100;
						}
						if (startflg)
						{
							Length extent = regionstart != null ? regionstart
									.getExtent() : null;
							left = getNewAddLength(extent, left);
							leftbody = getNewSubLength(extent, leftbody);
							regionsflg = regionsflg + 10;
						}
						if (endflg)
						{
							Length extent = regionend != null ? regionend
									.getExtent() : null;
							right = getNewAddLength(extent, right);
							rightbody = getNewSubLength(extent, rightbody);
							regionsflg = regionsflg + 1;
						}
					}
				} else
				{
					if (afteryouxian)
					{
						if (afterflg)
						{
							Length extentafter = regionafter != null ? regionafter
									.getExtent()
									: null;
							bottom = getNewAddLength(extentafter, bottom);
							bottombody = getNewSubLength(extentafter,
									bottombody);
							regionsflg = regionsflg + 100;
						}
						if (startflg)
						{
							regionsflg = regionsflg + 10;
						}
						if (endflg)
						{
							regionsflg = regionsflg + 1;
						}
					} else
					{
						if (afterflg)
						{
							regionsflg = regionsflg + 100;
							if (startflg)
							{
								regionsflg = regionsflg + 10;
							}
							if (endflg)
							{
								regionsflg = regionsflg + 1;
							}
						}
					}
				}
			} else
			{
				if (beforeflg)
				{
					regionsflg = regionsflg + 1000;
				}

				if (afteryouxian)
				{
					if (afterflg)
					{
						Length extentafter = regionafter != null ? regionafter
								.getExtent() : null;
						bottom = getNewAddLength(extentafter, bottom);
						bottombody = getNewSubLength(extentafter, bottombody);
						regionsflg = regionsflg + 100;
						if (startflg)
						{
							Length extent = regionstart != null ? regionstart
									.getExtent() : null;
							left = getNewAddLength(extent, left);
							leftbody = getNewSubLength(extent, leftbody);
							regionsflg = regionsflg + 10;
						}
						if (endflg)
						{
							Length extent = regionend != null ? regionend
									.getExtent() : null;
							right = getNewAddLength(extent, right);
							rightbody = getNewSubLength(extent, rightbody);
							regionsflg = regionsflg + 1;
						}
					}
				} else
				{
					if (afterflg)
					{
						regionsflg = regionsflg + 100;
					}
					if (startflg)
					{
						Length extent = regionstart != null ? regionstart
								.getExtent() : null;
						left = getNewAddLength(extent, left);
						leftbody = getNewSubLength(extent, leftbody);
						regionsflg = regionsflg + 10;
					}
					if (endflg)
					{
						Length extent = regionend != null ? regionend
								.getExtent() : null;
						right = getNewAddLength(extent, right);
						rightbody = getNewSubLength(extent, rightbody);
						regionsflg = regionsflg + 1;
					}
				}
			}
			CommonMarginBlock newcmb = new CommonMarginBlock(top, bottom, left,
					right, old.getSpaceBefore(), old.getSpaceAfter(), old
							.getStartIndent(), old.getEndIndent());
			bodycmp = new CommonMarginBlock(topbody, bottombody, leftbody,
					rightbody, oldregionbodycmb.getSpaceBefore(),
					oldregionbodycmb.getSpaceAfter(), oldregionbodycmb
							.getStartIndent(), oldregionbodycmb.getEndIndent());
			return newcmb;
		} else
		{
			if (beforeflg)
			{
				regionsflg = regionsflg + 1000;
			}
			if (afterflg)
			{
				regionsflg = regionsflg + 100;
			}
			if (startflg)
			{
				regionsflg = regionsflg + 10;
			}
			if (endflg)
			{
				regionsflg = regionsflg + 1;
			}
		}
		return old;
	}

	public FixedLength getNewAddLength(Length extent, Length old)
	{
		int oldvalue = old != null ? old.getValue() : 0;
		int extentvalue = extent != null ? extent.getValue() : 0;
		FixedLength newlength = new FixedLength(oldvalue + extentvalue);
		return newlength;
	}

	public FixedLength getNewSubLength(Length extent, Length old)
	{
		int oldvalue = old != null ? old.getValue() : 0;
		int extentvalue = extent != null ? extent.getValue() : 0;
		FixedLength newlength = new FixedLength(oldvalue - extentvalue);
		return newlength;
	}

	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();

		Map<Integer, Region> regions = simplePageMaster.getRegions();
		RegionBody regionbody = (RegionBody) regions
				.get(Constants.FO_REGION_BODY);
		output.append(new RegionWriter(regionbody, bodycmp).getCode());

		if (regionsflg >= 1000)
		{
			regionsflg = regionsflg - 1000;
		} else
		{
			RegionBefore regionbefore = (RegionBefore) regions
					.get(Constants.FO_REGION_BEFORE);
			if (regionbefore != null)
			{
				// String before = regionbefore.getRegionName();
				// CommonBorderPaddingBackground cbpb = regionbefore
				// .getCommonBorderPaddingBackground();
				// if (!isNoCommonBorderPaddingBackground(cbpb)
				// || IoXslUtil.isStaticcontentContainer(before))
				// {
				output.append(new RegionWriter(regionbefore).getCode());
				// }
			}
		}
		if (regionsflg >= 100)
		{

			regionsflg = regionsflg - 100;
		} else
		{
			RegionAfter regionafter = (RegionAfter) regions
					.get(Constants.FO_REGION_AFTER);
			if (regionafter != null)
			{
				// String after = regionafter.getRegionName();
				// CommonBorderPaddingBackground cbpb = regionafter
				// .getCommonBorderPaddingBackground();
				// if (!isNoCommonBorderPaddingBackground(cbpb)
				// || IoXslUtil.isStaticcontentContainer(after))
				// {
				output.append(new RegionWriter(regionafter).getCode());
				// }
			}
		}
		if (regionsflg >= 10)
		{

			regionsflg = regionsflg - 10;
		} else
		{
			RegionStart regionstart = (RegionStart) regions
					.get(Constants.FO_REGION_START);
			if (regionstart != null)
			{
				// String start = regionstart.getRegionName();
				// CommonBorderPaddingBackground cbpb = regionstart
				// .getCommonBorderPaddingBackground();
				// if (!isNoCommonBorderPaddingBackground(cbpb)
				// || IoXslUtil.isStaticcontentContainer(start))
				// {
				output.append(new RegionWriter(regionstart).getCode());
				// }
			}
		}
		if (regionsflg == 0)
		{
			RegionEnd regionend = (RegionEnd) regions
					.get(Constants.FO_REGION_END);
			if (regionend != null)
			{
				// String end = regionend.getRegionName();
				// CommonBorderPaddingBackground cbpb = regionend
				// .getCommonBorderPaddingBackground();
				// if (!isNoCommonBorderPaddingBackground(cbpb)
				// || IoXslUtil.isStaticcontentContainer(end))
				// {
				output.append(new RegionWriter(regionend).getCode());
				// }
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
		return FoXsltConstants.SIMPLE_PAGE_MASTER;
	}

	/**
	 * @返回 simplelePageMasterName变量的值
	 */
	public String getSimplelePageMasterName()
	{
		return simplelePageMasterName;
	}

	/**
	 * @param simplelePageMasterName
	 *            设置simplelePageMasterName成员变量的值 值约束说明
	 */
	public void setSimplelePageMasterName(String simplelePageMasterName)
	{
		this.simplelePageMasterName = simplelePageMasterName;
	}

	public boolean isNotOut(Region region)
	{
		if (region != null)
		{
			return isNoCommonBorderPaddingBackground(region
					.getCommonBorderPaddingBackground())
					&& !WiseDocDocumentWriter.getStaticcontents().contains(
							region.getRegionName());
		}
		return true;
	}

	public boolean isNoCommonBorderPaddingBackground(
			CommonBorderPaddingBackground cbpb)
	{
		if (cbpb != null)
		{
			Color color = cbpb.getBackgroundColor();
			String image = cbpb.getBackgroundImage();
			return color == null && image == null;
		}
		return true;
	}
}
