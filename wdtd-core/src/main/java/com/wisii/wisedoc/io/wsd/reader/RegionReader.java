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
 * @SizeRegionReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.RegionAfter;
import com.wisii.wisedoc.document.attribute.RegionBefore;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.RegionEnd;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-10
 */
public class RegionReader extends AbstractAttElementReader
{
	private String BEFORENAME = "regionbefore";
	private String AFTERNAME = "regionafter";
	private String STRATNAME = "regionstart";
	private String ENDNAME = "regionend";
	private String BODYNAME = "regionbody";
	private Region _region;
	private com.wisii.wisedoc.document.attribute.Attributes _atttiutes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#startElement(java.lang
	 * .String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (!BEFORENAME.equals(qname) && !AFTERNAME.equals(qname)
				&& !STRATNAME.equals(qname) && !ENDNAME.equals(qname)
				&& !BODYNAME.equals(qname))
		{
			throw new SAXException("Region的节点名(" + qname + ")不是" + BEFORENAME
					+ "或" + AFTERNAME + "或" + STRATNAME + "或" + ENDNAME);
		}
		_atttiutes = super.createattributes(atts);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#endElement(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (_atttiutes != null)
		{
			EnumProperty precedenceObject = (EnumProperty) _atttiutes
					.getAttribute(Constants.PR_PRECEDENCE);
			int precedence = -1;
			if (precedenceObject != null)
			{
				precedence = precedenceObject.getEnum();
			}
			Length extent = (Length) _atttiutes
					.getAttribute(Constants.PR_EXTENT);
			Map<Integer, Object> attmap = null;
			if (_atttiutes != null)
			{
				attmap = _atttiutes.getAttributes();
			}
			CommonBorderPaddingBackground commonBorderPaddingBackground = new CommonBorderPaddingBackground(
					attmap);
			CommonMarginBlock commonMarginBlock = new CommonMarginBlock(
					(Length) _atttiutes.getAttribute(Constants.PR_MARGIN_TOP),
					(Length) _atttiutes
							.getAttribute(Constants.PR_MARGIN_BOTTOM),
					(Length) _atttiutes.getAttribute(Constants.PR_MARGIN_LEFT),
					(Length) _atttiutes.getAttribute(Constants.PR_MARGIN_RIGHT),
					(SpaceProperty) _atttiutes
							.getAttribute(Constants.PR_SPACE_BEFORE),
					(SpaceProperty) _atttiutes
							.getAttribute(Constants.PR_SPACE_AFTER),
					(Length) _atttiutes.getAttribute(Constants.PR_START_INDENT),
					(Length) _atttiutes.getAttribute(Constants.PR_END_INDENT));
			Integer columnCountobject = (Integer) _atttiutes
					.getAttribute(Constants.PR_COLUMN_COUNT);
			int columnCount = 1;
			if (columnCountobject != null)
			{
				columnCount = columnCountobject;
			}
			Length columnGap = (Length) _atttiutes
					.getAttribute(Constants.PR_COLUMN_GAP);

			EnumProperty displayAlignobject = (EnumProperty) _atttiutes
					.getAttribute(Constants.PR_DISPLAY_ALIGN);
			int displayAlign = -1;
			if (displayAlignobject != null)
			{
				displayAlign = displayAlignobject.getEnum();
			}
			Object referenceOrientationobject = (Object) _atttiutes
					.getAttribute(Constants.PR_REFERENCE_ORIENTATION);
			int referenceOrientation = -1;
			if (referenceOrientationobject instanceof Integer)
			{
				referenceOrientation = (Integer) referenceOrientationobject;
			}
			Integer writingModeobject = (Integer) _atttiutes
					.getAttribute(Constants.PR_WRITING_MODE);
			int writingMode = -1;
			if (writingModeobject != null)
			{
				writingMode = (Integer) writingModeobject;
			}
			Object overflowobject = _atttiutes
					.getAttribute(Constants.PR_OVERFLOW);
			int overflow = -1;
			if (overflowobject != null
					&& overflowobject instanceof EnumProperty)
			{
				overflow = ((EnumProperty) overflowobject).getEnum();
			} else if (overflowobject instanceof Integer)
			{
				overflow = (Integer) overflowobject;
			}
			String regionName = (String) _atttiutes
					.getAttribute(Constants.PR_REGION_NAME);
			if (BEFORENAME.equals(qname))
			{

				_region = new RegionBefore(precedence, extent, null,
						commonBorderPaddingBackground, displayAlign, overflow,
						regionName, referenceOrientation, writingMode);
			} else if (AFTERNAME.equals(qname))
			{
				_region = new RegionAfter(precedence, extent, null,
						commonBorderPaddingBackground, displayAlign, overflow,
						regionName, referenceOrientation, writingMode);
			} else if (STRATNAME.equals(qname))
			{
				_region = new RegionStart(extent, null,
						commonBorderPaddingBackground, displayAlign, overflow,
						regionName, referenceOrientation, writingMode);
			} else if (ENDNAME.equals(qname))
			{
				_region = new RegionEnd(extent, null,
						commonBorderPaddingBackground, displayAlign, overflow,
						regionName, referenceOrientation, writingMode);
			} else if (BODYNAME.equals(qname))
			{
				_region = new RegionBody(columnCount, columnGap,
						commonMarginBlock, null, commonBorderPaddingBackground,
						displayAlign, overflow, regionName,
						referenceOrientation, writingMode);
			} else
			{
				LogUtil.debug("region节点名(" + qname + ")不是合法的节点名");
				throw new SAXException("region节点名(" + qname + ")不是合法的节点名");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public Region getObject()
	{
		return _region;
	}

}
