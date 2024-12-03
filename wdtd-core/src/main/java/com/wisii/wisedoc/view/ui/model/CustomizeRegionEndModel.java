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

public class CustomizeRegionEndModel
{

	RegionEndModel regionendmodel;

	public CustomizeRegionEndModel(RegionEndModel regionendmodel)
	{
		this.regionendmodel = regionendmodel;
	}

	public CommonBorderPaddingBackground getRegionBackground()
	{
		Map<Integer, Object> property = new HashMap<Integer, Object>();
		property.put(Constants.PR_BACKGROUND_COLOR, regionendmodel
				.getBodyBackgroundColor());
		property.put(Constants.PR_BACKGROUND_IMAGE, regionendmodel
				.getBodyBackgroundImage());
		property.put(Constants.PR_BACKGROUND_REPEAT, regionendmodel
				.getBodyBackgroundImageRepeat());
		property.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
				regionendmodel.getBodyBackgroundPositionHorizontal());
		property.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, regionendmodel
				.getBodyBackgroundPositionVertical());
		CommonBorderPaddingBackground cbpb = new CommonBorderPaddingBackground(
				property);
		return cbpb;
	}

	public void setRegionBackground(
			CommonBorderPaddingBackground commonborderpaddingbackgroup)
	{
		regionendmodel.setBodyBackgroundColor(commonborderpaddingbackgroup
				.getBackgroundColor());
		regionendmodel.setBodyBackgroundImage(commonborderpaddingbackgroup
				.getBackgroundImage());
		regionendmodel.setBodyBackgroundImageRepeat(new EnumProperty(
				commonborderpaddingbackgroup.getBackgroundRepeat(), ""));
		regionendmodel
				.setBodyBackgroundPositionHorizontal(commonborderpaddingbackgroup
						.getBackgroundPositionHorizontal());
		regionendmodel
				.setBodyBackgroundPositionVertical(commonborderpaddingbackgroup
						.getBackgroundPositionVertical());
	}

	public Color getBodyBackgroundColor()
	{
		return regionendmodel.getBodyBackgroundColor();
	}

	public void setBodyBackgroundColor(Color color)
	{
		regionendmodel.setBodyBackgroundColor(color);
	}

	public Object getBodyBackgroundImage()
	{
		return regionendmodel.getBodyBackgroundImage();
	}

	public void setBodyBackgroundImage(Object image)
	{
		regionendmodel.setBodyBackgroundImage(image);
	}
	public int getBodyBackgroundImageLayer()
	{
		return regionendmodel.getBodyBackgroundImageLayer();
	}

	public void setBodyBackgroundImageLayer(int image)
	{
		regionendmodel.setBodyBackgroundImageLayer(image);
	}
	public EnumProperty getBodyBackgroundImageRepeat()
	{
		return regionendmodel.getBodyBackgroundImageRepeat();
	}

	public void setBodyBackgroundImageRepeat(EnumProperty brp)
	{
		regionendmodel.setBodyBackgroundImageRepeat(brp);
	}

	public Length getBodyBackgroundPositionHorizontal()
	{
		return regionendmodel.getBodyBackgroundPositionHorizontal();
	}

	public void setBodyBackgroundPositionHorizontal(Length bph)
	{
		regionendmodel.setBodyBackgroundPositionHorizontal(bph);
	}

	public Length BodyBackgroundPositionVertical()
	{
		return regionendmodel.getBodyBackgroundPositionVertical();
	}

	public void setBodyBackgroundPositionVertical(Length bpv)
	{
		regionendmodel.setBodyBackgroundPositionVertical(bpv);
	}

	public int getWritingMode()
	{
		return regionendmodel.getWritingMode();
	}

	public void setWritingMode(int writingmodel)
	{
		regionendmodel.setWritingMode(writingmodel);
	}

	public int getReferenceOrientation()
	{
		return regionendmodel.getReferenceOrientation();
	}

	public void setReferenceOrientation(int orientation)
	{
		regionendmodel.setReferenceOrientation(orientation);
	}

	public int getDisplayAlign()
	{
		return regionendmodel.getDisplayAlign();
	}

	public void setDisplayAlign(int displayalign)
	{
		regionendmodel.setDisplayAlign(displayalign);
	}

	public int getOverflow()
	{
		return regionendmodel.getOverflow();
	}

	public void setOverflow(int overflow)
	{
		regionendmodel.setOverflow(overflow);
	}

	public Length getExtent()
	{
		return regionendmodel.getExtent();
	}

	public void setExtent(Length extent)
	{
		regionendmodel.setExtent(extent);
	}

	public RegionEndModel getRegionendmodel()
	{
		return regionendmodel;
	}

	public void setRegionendmodel(RegionEndModel regionendmodel)
	{
		this.regionendmodel = regionendmodel;
	}

}
