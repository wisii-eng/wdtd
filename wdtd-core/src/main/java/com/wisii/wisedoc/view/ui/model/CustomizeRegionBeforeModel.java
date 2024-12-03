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
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.datatype.Length;

public class CustomizeRegionBeforeModel
{

	RegionBeforeModel regionbeforemodel;

	public CustomizeRegionBeforeModel(RegionBeforeModel regionbeforemodel)
	{
		this.regionbeforemodel = regionbeforemodel;
	}

	public CommonBorderPaddingBackground getRegionBackground()
	{

		Map<Integer, Object> property = new HashMap<Integer, Object>();
		property.put(Constants.PR_BACKGROUND_COLOR, regionbeforemodel
				.getBodyBackgroundColor());
		property.put(Constants.PR_BACKGROUND_IMAGE, regionbeforemodel
				.getBodyBackgroundImage());
		property.put(Constants.PR_BACKGROUND_REPEAT, regionbeforemodel
				.getBodyBackgroundImageRepeat());
		property.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
				regionbeforemodel.getBodyBackgroundPositionHorizontal());
		property.put(Constants.PR_BACKGROUND_POSITION_VERTICAL,
				regionbeforemodel.getBodyBackgroundPositionVertical());
		CommonBorderPaddingBackground cbpb = new CommonBorderPaddingBackground(
				property);
		return cbpb;
	}

	public void setRegionBackground(
			CommonBorderPaddingBackground commonborderpaddingbackgroup)
	{
		regionbeforemodel.setBodyBackgroundColor(commonborderpaddingbackgroup
				.getBackgroundColor());
		regionbeforemodel.setBodyBackgroundImage(commonborderpaddingbackgroup
				.getBackgroundImage());
		regionbeforemodel.setBodyBackgroundImageRepeat(new EnumProperty(
				commonborderpaddingbackgroup.getBackgroundRepeat(), ""));
		regionbeforemodel
				.setBodyBackgroundPositionHorizontal(commonborderpaddingbackgroup
						.getBackgroundPositionHorizontal());
		regionbeforemodel
				.setBodyBackgroundPositionVertical(commonborderpaddingbackgroup
						.getBackgroundPositionVertical());
	}

	public Color getBodyBackgroundColor()
	{
		return regionbeforemodel.getBodyBackgroundColor();
	}

	public void setBodyBackgroundColor(Color color)
	{
		regionbeforemodel.setBodyBackgroundColor(color);
	}

	public Object getBodyBackgroundImage()
	{
		return regionbeforemodel.getBodyBackgroundImage();
	}

	public void setBodyBackgroundImage(Object image)
	{
		regionbeforemodel.setBodyBackgroundImage(image);
	}
	public int getBodyBackgroundImageLayer()
	{
		return regionbeforemodel.getBodyBackgroundImageLayer();
	}

	public void setBodyBackgroundImageLayer(int image)
	{
		regionbeforemodel.setBodyBackgroundImageLayer(image);
	}
	public EnumProperty getBodyBackgroundImageRepeat()
	{
		return regionbeforemodel.getBodyBackgroundImageRepeat();
	}

	public void setBodyBackgroundImageRepeat(EnumProperty brp)
	{
		regionbeforemodel.setBodyBackgroundImageRepeat(brp);
	}

	public Length getBodyBackgroundPositionHorizontal()
	{
		return regionbeforemodel.getBodyBackgroundPositionHorizontal();
	}

	public void setBodyBackgroundPositionHorizontal(Length bph)
	{
		regionbeforemodel.setBodyBackgroundPositionHorizontal(bph);
	}

	public Length BodyBackgroundPositionVertical()
	{
		return regionbeforemodel.getBodyBackgroundPositionVertical();
	}

	public void setBodyBackgroundPositionVertical(Length bpv)
	{
		regionbeforemodel.setBodyBackgroundPositionVertical(bpv);
	}

	public int getWritingMode()
	{
		return regionbeforemodel.getWritingMode();
	}

	public void setWritingMode(int writingmodel)
	{
		regionbeforemodel.setWritingMode(writingmodel);
	}

	public int getReferenceOrientation()
	{
		return regionbeforemodel.getReferenceOrientation();
	}

	public void setReferenceOrientation(int orientation)
	{
		regionbeforemodel.setReferenceOrientation(orientation);
	}

	public int getDisplayAlign()
	{
		return regionbeforemodel.getDisplayAlign();
	}

	public void setDisplayAlign(int displayalign)
	{
		regionbeforemodel.setDisplayAlign(displayalign);
	}

	public int getOverflow()
	{
		return regionbeforemodel.getOverflow();
	}

	public void setOverflow(int overflow)
	{
		regionbeforemodel.setOverflow(overflow);
	}

	public Length getExtent()
	{
		return regionbeforemodel != null ? regionbeforemodel.getExtent() : null;
	}

	public void setExtent(Length extent)
	{
		regionbeforemodel.setExtent(extent);
	}

	public void setPrecedence(int precedence)
	{
		regionbeforemodel.setPrecedence(precedence);
	}

	public int getPrecedence()
	{
		return regionbeforemodel.getPrecedence();
	}

	public RegionBeforeModel getRegionbeforemodel()
	{
		return regionbeforemodel;
	}

	public void setRegionbeforemodel(RegionBeforeModel regionbeforemodel)
	{
		this.regionbeforemodel = regionbeforemodel;
	}
}
