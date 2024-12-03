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

package com.wisii.wisedoc.view.ui.model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.datatype.Length;

public class CustomizeRegionBodyModel
{

	RegionBodyModel regionbodymodel;

	public CustomizeRegionBodyModel(RegionBodyModel regionbodymodel)
	{
		this.regionbodymodel = regionbodymodel;
	}

	public int getColumnCount()
	{
		return regionbodymodel.getColumnCount();
	}

	public Length getColumnGap()
	{
		return regionbodymodel.getColumnGap();
	}

	public void setColumnCount(int columnnumber)
	{
		regionbodymodel.setColumnCount(columnnumber);
	}

	public void setColumnGap(Length grap)
	{
		regionbodymodel.setColumnGap(grap);
	}

	public CommonMarginBlock getRegionBodyMargin()
	{
		return new CommonMarginBlock(regionbodymodel.getMarginTop(),
				regionbodymodel.getMarginBottom(), regionbodymodel
						.getMarginLeft(), regionbodymodel.getMarginRight(),
				regionbodymodel.getSpaceBefore(), regionbodymodel
						.getSpaceAfter(), regionbodymodel.getStartIndent(),
				regionbodymodel.getEndIndent());
	}

	/**
	 * @param PageMargion
	 *            the PageMargion to set
	 */
	public void setRegionBodyMargion(CommonMarginBlock pageMargion)
	{
		regionbodymodel.setMarginTop(pageMargion.getMarginTop());
		regionbodymodel.setMarginBottom(pageMargion.getMarginBottom());
		regionbodymodel.setMarginLeft(pageMargion.getMarginLeft());
		regionbodymodel.setMarginRight(pageMargion.getMarginRight());
		// Length before=pageMargion.getSpaceBefore().getLength();
		// regionbodymodel.setSpaceBefore(before.getNumericValue(),before.);
		// regionbodymodel.setMarginTop(pageMargion.getMarginTop());
		// regionbodymodel.setMarginTop(pageMargion.getMarginTop());
		// regionbodymodel.setMarginTop(pageMargion.getMarginTop());
	}

	/**
	 * @param PageMargion
	 *            the PageMargion to set
	 */
	public void setRegionBodyMargionTop(Length length)
	{
		regionbodymodel.setMarginTop(length);
	}

	/**
	 * @param PageMargion
	 *            the PageMargion to set
	 */
	public void setRegionBodyMargionBottom(Length length)
	{
		regionbodymodel.setMarginBottom(length);
	}

	/**
	 * @param PageMargion
	 *            the PageMargion to set
	 */
	public void setRegionBodyMargionLeft(Length length)
	{
		regionbodymodel.setMarginLeft(length);
	}

	/**
	 * @param PageMargion
	 *            the PageMargion to set
	 */
	public void setRegionBodyMargionRight(Length length)
	{
		regionbodymodel.setMarginRight(length);
	}

	public CommonBorderPaddingBackground getRegionBodyBackground()
	{
		Map<Integer, Object> property = new HashMap<Integer, Object>();
		property.put(Constants.PR_BACKGROUND_COLOR, regionbodymodel
				.getBodyBackgroundColor());
		property.put(Constants.PR_BACKGROUND_IMAGE, regionbodymodel
				.getBodyBackgroundImage());
		property.put(Constants.PR_BACKGROUND_REPEAT, regionbodymodel
				.getBodyBackgroundImageRepeat());
		property.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
				regionbodymodel.getBodyBackgroundPositionHorizontal());
		property.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, regionbodymodel
				.getBodyBackgroundPositionVertical());
		CommonBorderPaddingBackground cbpb = new CommonBorderPaddingBackground(
				property);
		return cbpb;
	}

	public void setRegionBodyBackground(
			CommonBorderPaddingBackground commonborderpaddingbackgroup)
	{
		regionbodymodel.setBodyBackgroundColor(commonborderpaddingbackgroup
				.getBackgroundColor());
		regionbodymodel.setBodyBackgroundImage(commonborderpaddingbackgroup
				.getBackgroundImage());
		regionbodymodel
				.setBodyBackgroundImageRepeat(commonborderpaddingbackgroup
						.getBackgroundRepeat());
		regionbodymodel
				.setBodyBackgroundPositionHorizontal(commonborderpaddingbackgroup
						.getBackgroundPositionHorizontal());
		regionbodymodel
				.setBodyBackgroundPositionVertical(commonborderpaddingbackgroup
						.getBackgroundPositionVertical());
	}

	public Color getBodyBackgroundColor()
	{
		return regionbodymodel.getBodyBackgroundColor();
	}

	public void setBodyBackgroundColor(Color color)
	{
		regionbodymodel.setBodyBackgroundColor(color);
	}

	public Object getBodyBackgroundImage()
	{
		return regionbodymodel.getBodyBackgroundImage();
	}

	public void setBodyBackgroundImage(Object image)
	{
		regionbodymodel.setBodyBackgroundImage(image);
	}

	public int getBodyBackgroundImageLayer()
	{
		return regionbodymodel.getBodyBackgroundImageLayer();
	}

	public void setBodyBackgroundImageLayer(int image)
	{
		regionbodymodel.setBodyBackgroundImageLayer(image);
	}

	public EnumProperty getBodyBackgroundImageRepeat()
	{
		return regionbodymodel.getBodyBackgroundImageRepeat();
	}

	public void setBodyBackgroundImageRepeat(EnumProperty brp)
	{
		regionbodymodel.setBodyBackgroundImageRepeat(brp);
	}

	public Length getBodyBackgroundPositionHorizontal()
	{
		return regionbodymodel.getBodyBackgroundPositionHorizontal();
	}

	public void setBodyBackgroundPositionHorizontal(Length bph)
	{
		regionbodymodel.setBodyBackgroundPositionHorizontal(bph);
	}

	public Length BodyBackgroundPositionVertical()
	{
		return regionbodymodel.getBodyBackgroundPositionVertical();
	}

	public void setBodyBackgroundPositionVertical(Length bpv)
	{
		regionbodymodel.setBodyBackgroundPositionVertical(bpv);
	}

	public int getWritingMode()
	{
		return regionbodymodel.getWritingMode();
	}

	public void setWritingMode(int writingmodel)
	{
		regionbodymodel.setWritingMode(writingmodel);
	}

	public int getBodyReferenceOrientation()
	{
		return regionbodymodel.getBodyReferenceOrientation();
	}

	public void setBodyReferenceOrientation(int orientation)
	{
		regionbodymodel.setBodyReferenceOrientation(orientation);
	}

	public int getDisplayAlign()
	{
		return regionbodymodel.getDisplayAlign();
	}

	public void setDisplayAlign(int displayalign)
	{
		regionbodymodel.setDisplayAlign(displayalign);
	}

	public int getOverflow()
	{
		return regionbodymodel.getOverflow();
	}

	public void setOverflow(int overflow)
	{
		regionbodymodel.setOverflow(overflow);
	}

	public RegionBodyModel getRegionbodymodel()
	{
		return regionbodymodel;
	}

	public void setRegionbodymodel(RegionBodyModel regionbodymodel)
	{
		this.regionbodymodel = regionbodymodel;
	}
}
