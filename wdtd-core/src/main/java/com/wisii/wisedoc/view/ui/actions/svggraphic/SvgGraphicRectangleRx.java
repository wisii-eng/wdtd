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
package com.wisii.wisedoc.view.ui.actions.svggraphic;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg矩形圆角边设置
 * @author 闫舒寰
 * @version 1.0 2009/03/09
 */
public class SvgGraphicRectangleRx extends Actions {
	
	private final int CONSTANTS_KEY = Constants.PR_WIDTH;
	
	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof FixedLengthSpinner)
		{
			final FixedLengthSpinner value = (FixedLengthSpinner) e.getSource();
			setFOProperty(Constants.PR_RX, value.getValue());
		}
	}
	
	@Override
	protected void uiStateChange(final UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		
		final Map<Integer, Object> svgMap = RibbonUIModel.getReadCompletePropertiesByType().get(this.actionType);
		
		final Object width = svgMap.get(CONSTANTS_KEY);
		if (width != null)
		{
			if (uiComponent instanceof FixedLengthSpinner)
			{
				final FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				FixedLength uivalue = null;
				
				//重新设置spinner的model会导致监听器被移除，这个还需要做深入的调查，原则上这个rx只能是宽度的一半
				/*FixedLength widthLength = null;
				SpinnerFixedLengthModel directionModel = null;
				if (width instanceof FixedLength) {
					widthLength = (FixedLength) width;
					int i = widthLength.getInnerLengthValue();
					int j = (int)Math.floor(i/2);
					
					String s = widthLength.getUnits();
					directionModel = new SpinnerFixedLengthModel(new FixedLength(0.0,"mm")new FixedLength(j, s, widthLength.getPrecision()),
							new FixedLength(0.0,"mm"), new FixedLength(j, s, widthLength.getPrecision()), 1);
				}
				
				if (directionModel != null) {
					ui.setModel(directionModel);
				}*/
				
				final Object value = svgMap.get(Constants.PR_RX);
				
				if (value != null) {
					if (value instanceof FixedLength)
					{
						uivalue = (FixedLength) value;
					}
					ui.initValue(uivalue);
				} else {
					setDefaultState(null);
				}
			}
		} else {
			setDefaultState(null);
		}
	}

	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof FixedLengthSpinner)
		{
			final FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			ui.initValue(null);
		}
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (uiComponent instanceof FixedLengthSpinner)
		{
			final FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			ui.initValue(new FixedLength(0));
		}
	}
	
	@Override
	public boolean isAvailable() {
		if (!super.isAvailable())
		{
			return false;
		}
		List<Object> elementList = RibbonUIModel.getElementList();
		if (elementList == null)
		{
			return false;
		}
		//目前认为只有矩形有rx,ry
		for (final Object object : elementList) {
			if (object instanceof Rectangle) {
				return true;
			}
		}
		return false;
	}

}
