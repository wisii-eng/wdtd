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

public class CustomizeRegionStartModel
{

	RegionStartModel regionstartmodel;

	public CustomizeRegionStartModel(RegionStartModel regionstartmodel)
	{
		this.regionstartmodel = regionstartmodel;
	}

	public CommonBorderPaddingBackground getRegionBackground()
	{
		Map<Integer, Object> property = new HashMap<Integer, Object>();
		property.put(Constants.PR_BACKGROUND_COLOR, regionstartmodel
				.getBodyBackgroundColor());
		property.put(Constants.PR_BACKGROUND_IMAGE, regionstartmodel
				.getBodyBackgroundImage());
		property.put(Constants.PR_BACKGROUND_REPEAT, regionstartmodel
				.getBodyBackgroundImageRepeat());
		property.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
				regionstartmodel.getBodyBackgroundPositionHorizontal());
		property.put(Constants.PR_BACKGROUND_POSITION_VERTICAL,
				regionstartmodel.getBodyBackgroundPositionVertical());
		CommonBorderPaddingBackground cbpb = new CommonBorderPaddingBackground(
				property);
		return cbpb;
	}

	public void setRegionBackground(
			CommonBorderPaddingBackground commonborderpaddingbackgroup)
	{
		regionstartmodel.setBodyBackgroundColor(commonborderpaddingbackgroup
				.getBackgroundColor());
		regionstartmodel.setBodyBackgroundImage(commonborderpaddingbackgroup
				.getBackgroundImage());
		regionstartmodel.setBodyBackgroundImageRepeat(new EnumProperty(
				commonborderpaddingbackgroup.getBackgroundRepeat(), ""));
		regionstartmodel
				.setBodyBackgroundPositionHorizontal(commonborderpaddingbackgroup
						.getBackgroundPositionHorizontal());
		regionstartmodel
				.setBodyBackgroundPositionVertical(commonborderpaddingbackgroup
						.getBackgroundPositionVertical());
	}

	public Color getBodyBackgroundColor()
	{
		return regionstartmodel.getBodyBackgroundColor();
	}

	public void setBodyBackgroundColor(Color color)
	{
		regionstartmodel.setBodyBackgroundColor(color);
	}

	public Object getBodyBackgroundImage()
	{
		return regionstartmodel.getBodyBackgroundImage();
	}

	public void setBodyBackgroundImage(Object image)
	{
		regionstartmodel.setBodyBackgroundImage(image);
	}

	public int getBodyBackgroundImageLayer()
	{
		return regionstartmodel.getBodyBackgroundImageLayer();
	}

	public void setBodyBackgroundImageLayer(int image)
	{
		regionstartmodel.setBodyBackgroundImageLayer(image);
	}

	public EnumProperty getBodyBackgroundImageRepeat()
	{
		return regionstartmodel.getBodyBackgroundImageRepeat();
	}

	public void setBodyBackgroundImageRepeat(EnumProperty brp)
	{
		regionstartmodel.setBodyBackgroundImageRepeat(brp);
	}

	public Length getBodyBackgroundPositionHorizontal()
	{
		return regionstartmodel.getBodyBackgroundPositionHorizontal();
	}

	public void setBodyBackgroundPositionHorizontal(Length bph)
	{
		regionstartmodel.setBodyBackgroundPositionHorizontal(bph);
	}

	public Length BodyBackgroundPositionVertical()
	{
		return regionstartmodel.getBodyBackgroundPositionVertical();
	}

	public void setBodyBackgroundPositionVertical(Length bpv)
	{
		regionstartmodel.setBodyBackgroundPositionVertical(bpv);
	}

	public int getWritingMode()
	{
		return regionstartmodel.getWritingMode();
	}

	public void setWritingMode(int writingmodel)
	{
		regionstartmodel.setWritingMode(writingmodel);
	}

	public int getReferenceOrientation()
	{
		return regionstartmodel.getReferenceOrientation();
	}

	public void setReferenceOrientation(int orientation)
	{
		regionstartmodel.setReferenceOrientation(orientation);
	}

	public int getDisplayAlign()
	{
		return regionstartmodel.getDisplayAlign();
	}

	public void setDisplayAlign(int displayalign)
	{
		regionstartmodel.setDisplayAlign(displayalign);
	}

	public int getOverflow()
	{
		return regionstartmodel.getOverflow();
	}

	public void setOverflow(int overflow)
	{
		regionstartmodel.setOverflow(overflow);
	}

	public Length getExtent()
	{
		return regionstartmodel.getExtent();
	}

	public void setExtent(Length extent)
	{
		regionstartmodel.setExtent(extent);
	}

	public RegionStartModel getRegionstartmodel()
	{
		return regionstartmodel;
	}

	public void setRegionstartmodel(RegionStartModel regionstartmodel)
	{
		this.regionstartmodel = regionstartmodel;
	}
}
