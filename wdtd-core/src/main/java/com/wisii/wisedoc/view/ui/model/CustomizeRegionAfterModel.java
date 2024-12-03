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

public class CustomizeRegionAfterModel
{

	RegionAfterModel regionaftermodel;

	public CustomizeRegionAfterModel(RegionAfterModel regionaftermodel)
	{
		this.regionaftermodel = regionaftermodel;
	}

	public CommonBorderPaddingBackground getRegionBackground()
	{
		Map<Integer, Object> property = new HashMap<Integer, Object>();
		property.put(Constants.PR_BACKGROUND_COLOR, regionaftermodel
				.getBodyBackgroundColor());
		property.put(Constants.PR_BACKGROUND_IMAGE, regionaftermodel
				.getBodyBackgroundImage());
		property.put(Constants.PR_BACKGROUND_REPEAT, regionaftermodel
				.getBodyBackgroundImageRepeat());
		property.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
				regionaftermodel.getBodyBackgroundPositionHorizontal());
		property.put(Constants.PR_BACKGROUND_POSITION_VERTICAL,
				regionaftermodel.getBodyBackgroundPositionVertical());
		CommonBorderPaddingBackground cbpb = new CommonBorderPaddingBackground(
				property);
		return cbpb;
	}

	public void setRegionBackground(
			CommonBorderPaddingBackground commonborderpaddingbackgroup)
	{
		regionaftermodel.setBodyBackgroundColor(commonborderpaddingbackgroup
				.getBackgroundColor());
		regionaftermodel.setBodyBackgroundImage(commonborderpaddingbackgroup
				.getBackgroundImage());
		regionaftermodel.setBodyBackgroundImageRepeat(new EnumProperty(
				commonborderpaddingbackgroup.getBackgroundRepeat(), ""));
		regionaftermodel
				.setBodyBackgroundPositionHorizontal(commonborderpaddingbackgroup
						.getBackgroundPositionHorizontal());
		regionaftermodel
				.setBodyBackgroundPositionVertical(commonborderpaddingbackgroup
						.getBackgroundPositionVertical());
	}

	public Color getBodyBackgroundColor()
	{
		return regionaftermodel.getBodyBackgroundColor();
	}

	public void setBodyBackgroundColor(Color color)
	{
		regionaftermodel.setBodyBackgroundColor(color);
	}

	public Object getBodyBackgroundImage()
	{
		return regionaftermodel.getBodyBackgroundImage();
	}

	public void setBodyBackgroundImage(Object image)
	{
		regionaftermodel.setBodyBackgroundImage(image);
	}
	public int getBodyBackgroundImageLayer()
	{
		return regionaftermodel.getBodyBackgroundImageLayer();
	}

	public void setBodyBackgroundImageLayer(int image)
	{
		regionaftermodel.setBodyBackgroundImageLayer(image);
	}
	public EnumProperty getBodyBackgroundImageRepeat()
	{
		return regionaftermodel.getBodyBackgroundImageRepeat();
	}

	public void setBodyBackgroundImageRepeat(EnumProperty brp)
	{
		regionaftermodel.setBodyBackgroundImageRepeat(brp);
	}

	public Length getBodyBackgroundPositionHorizontal()
	{
		return regionaftermodel.getBodyBackgroundPositionHorizontal();
	}

	public void setBodyBackgroundPositionHorizontal(Length bph)
	{
		regionaftermodel.setBodyBackgroundPositionHorizontal(bph);
	}

	public Length BodyBackgroundPositionVertical()
	{
		return regionaftermodel.getBodyBackgroundPositionVertical();
	}

	public void setBodyBackgroundPositionVertical(Length bpv)
	{
		regionaftermodel.setBodyBackgroundPositionVertical(bpv);
	}

	public int getWritingMode()
	{
		return regionaftermodel.getWritingMode();
	}

	public void setWritingMode(int writingmodel)
	{
		regionaftermodel.setWritingMode(writingmodel);
	}

	public int getReferenceOrientation()
	{
		return regionaftermodel.getReferenceOrientation();
	}

	public void setReferenceOrientation(int orientation)
	{
		regionaftermodel.setReferenceOrientation(orientation);
	}

	public int getDisplayAlign()
	{
		return regionaftermodel.getDisplayAlign();
	}

	public void setDisplayAlign(int displayalign)
	{
		regionaftermodel.setDisplayAlign(displayalign);
	}

	public int getOverflow()
	{
		return regionaftermodel.getOverflow();
	}

	public void setOverflow(int overflow)
	{
		regionaftermodel.setOverflow(overflow);
	}

	public Length getExtent()
	{
		return regionaftermodel.getExtent();
	}

	public void setExtent(Length extent)
	{
		regionaftermodel.setExtent(extent);
	}

	public void setPrecedence(int precedence)
	{
		regionaftermodel.setPrecedence(precedence);
	}

	public int getPrecedence()
	{
		return regionaftermodel.getPrecedence();
	}

	public RegionAfterModel getRegionaftermodel()
	{
		return regionaftermodel;
	}

	public void setRegionaftermodel(RegionAfterModel regionaftermodel)
	{
		this.regionaftermodel = regionaftermodel;
	}
}
