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

package com.wisii.wisedoc.io.xsl;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.RegionAfter;
import com.wisii.wisedoc.document.attribute.RegionBA;
import com.wisii.wisedoc.document.attribute.RegionBefore;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.RegionEnd;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.SideRegion;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.io.xsl.attribute.CommonBorderPaddingBackgroundWriter;
import com.wisii.wisedoc.io.xsl.attribute.CommonMarginBlockWriter;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

public class RegionWriter extends AbsLayoutElementWriter
{

	Region region;

	CommonMarginBlock cmp = null;

	public RegionWriter(Region rg)
	{
		region = rg;
	}

	public RegionWriter(Region rg, CommonMarginBlock commonmarginblock)
	{
		region = rg;
		cmp = commonmarginblock;
	}

	public String getCode()
	{
		boolean flg = false;
		if (region instanceof RegionBody)
		{
			flg = true;
		} else
		{
			SideRegion otherRegion = (SideRegion) region;
			Length extent = otherRegion.getExtent();
			if (extent != null && extent instanceof FixedLength)
			{
				if (((FixedLength) extent).getValue() > 0)
				{
					flg = true;
				} else
				{
					flg = region.getOverflow() == Constants.EN_VISIBLE;
				}
			}
		}
		if (flg)
		{
			StringBuffer code = new StringBuffer();
			code.append(getAttributeVariable());
			code.append(getHeaderElement());
			code.append(getChildCode());
			code.append(getEndElement());
			return code.toString();
		} else
		{
			return "";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getAttributes()
	 */
	public String getAttributes()
	{
		StringBuffer output = new StringBuffer();
		if (region instanceof RegionBody)
		{
			RegionBody regionBody = (RegionBody) region;
			int columnNumber = regionBody.getColumnCount();
			if (columnNumber > 1)
			{
				output.append(getAttribute(Constants.PR_COLUMN_COUNT,
						columnNumber));
				output.append(getAttribute(Constants.PR_COLUMN_GAP, regionBody
						.getColumnGapLength()));
			}
			if (cmp != null)
			{
				output.append(new CommonMarginBlockWriter().write(cmp));
			} else
			{
				output.append(new CommonMarginBlockWriter().write(regionBody
						.getCommonMarginBlock()));
			}
		} else
		{
			SideRegion otherRegion = (SideRegion) region;
			String name = otherRegion.getRegionName();
			if (!name.equals("xsl-region-before")
					&& !name.equals("xsl-region-start")
					&& !name.equals("xsl-region-end")
					&& !name.equals("xsl-region-after"))
			{
				output.append(getAttribute(Constants.PR_REGION_NAME, name));
			}
			output.append(getAttribute(Constants.PR_EXTENT, otherRegion
					.getExtent()));
			if (region instanceof RegionBA)
			{
				output.append(getAttribute(Constants.PR_PRECEDENCE,
						new EnumProperty(((RegionBA) region).getPrecedence(),
								"")));
			}
		}
		output.append(new CommonBorderPaddingBackgroundWriter().write(region
				.getCommonBorderPaddingBackground()));
		if (region.getDisplayAlign() != Constants.EN_BEFORE)
		{
			output.append(getAttribute(Constants.PR_DISPLAY_ALIGN,
					new EnumProperty(region.getDisplayAlign(), "")));
		}
		if (region.getOverflow() != Constants.EN_AUTO)
		{
			output.append(getAttribute(Constants.PR_OVERFLOW, new EnumProperty(
					region.getOverflow(), "")));
		}
		int ro = region.getRegionReferenceOrientation();
		if (ro != 0.0d)
		{
			output.append(getAttribute(Constants.PR_REFERENCE_ORIENTATION, ro));
		}
		int writemode=region.getRegionWritingMode();
		if ( writemode!= -1)
		{
				output.append(getAttribute(Constants.PR_WRITING_MODE,
						new EnumProperty(writemode, "")));
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
		String elementName = "";
		if (region instanceof RegionBody)
		{
			elementName = FoXsltConstants.REGION_BODY;
		} else if (region instanceof RegionBefore)
		{
			elementName = FoXsltConstants.REGION_BEFORE;
		} else if (region instanceof RegionAfter)
		{
			elementName = FoXsltConstants.REGION_AFTER;
		} else if (region instanceof RegionStart)
		{
			elementName = FoXsltConstants.REGION_START;
		} else if (region instanceof RegionEnd)
		{
			elementName = FoXsltConstants.REGION_END;
		}
		return elementName;
	}
}
