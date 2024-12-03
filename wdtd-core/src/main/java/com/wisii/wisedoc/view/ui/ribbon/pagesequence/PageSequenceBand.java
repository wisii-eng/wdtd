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

package com.wisii.wisedoc.view.ui.ribbon.pagesequence;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ALB;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ALL_EVEN;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ALL_ODD;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_AUTO;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_END_EVEN;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_END_ODD;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_EVEN;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_NO_LIMIT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_NUMBER;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ODD;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ONE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_PAGE_LIMIT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_PAGE_TYPE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_ROMAN;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PAGE_SEQUENCE_BAND_START_PAGE_NUMBER;

import java.awt.Dimension;
import java.util.Map;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.PageSequence;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;

/**
 * 章节特有属性设置面板
 * 
 * @author 闫舒寰
 * @version 1.0 2009/03/17
 */
public class PageSequenceBand implements WiseBand
{

	String one = PAGE_SEQUENCE_BAND_ONE;

	String auto = PAGE_SEQUENCE_BAND_AUTO;

	String odd = PAGE_SEQUENCE_BAND_ODD;

	String even = PAGE_SEQUENCE_BAND_EVEN;

	String number = PAGE_SEQUENCE_BAND_NUMBER;

	String all_odd = PAGE_SEQUENCE_BAND_ALL_ODD;

	String all_even = PAGE_SEQUENCE_BAND_ALL_EVEN;

	String end_odd = PAGE_SEQUENCE_BAND_END_ODD;

	String end_even = PAGE_SEQUENCE_BAND_END_EVEN;

	String no_force = PAGE_SEQUENCE_BAND_NO_LIMIT;

	String alb = PAGE_SEQUENCE_BAND_ALB;

	String luoma = PAGE_SEQUENCE_BAND_ROMAN;

	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand pagesequenceBand = new JRibbonBand(PAGE_SEQUENCE_BAND,
				MediaResource.getResizableIcon("09379.ico"), null);

		Map<Integer, Object> att = RibbonUIModel
				.getReadCompletePropertiesByType()
				.get(ActionType.PAGE_SEQUENCE);
		EnumNumber initialpagepumber = att != null ? (EnumNumber) att
				.get(Constants.PR_INITIAL_PAGE_NUMBER) : null;
		EnumProperty forcepagecount = att != null ? (EnumProperty) att
				.get(Constants.PR_FORCE_PAGE_COUNT) : null;
		String formatStr = att != null ? (String) att.get(Constants.PR_FORMAT)
				: null;

		WiseCombobox initialPageNumber = new WiseCombobox();
		initialPageNumber.addItem(auto);
		initialPageNumber.addItem(one);
		initialPageNumber.addItem(odd);
		initialPageNumber.addItem(even);
		initialPageNumber.setEditable(true);
		String ipn = one;
		if (initialpagepumber != null)
		{
			if (initialpagepumber.getEnum() < 0)
			{
				ipn = initialpagepumber.getNumber() + "";
			} else
			{
				ipn = getItem(initialpagepumber.getEnum());
			}
		}
		initialPageNumber.InitValue(ipn);
		RibbonUIManager.getInstance().bind(
				PageSequence.SET_INITIAL_PAGE_NUMBER_ACTION, initialPageNumber);

		initialPageNumber.setPreferredSize(new Dimension(110, 22));
		JRibbonComponent initialPageNumberCom = new JRibbonComponent(
				MediaResource.getResizableIcon("02285.ico"),
				PAGE_SEQUENCE_BAND_START_PAGE_NUMBER, initialPageNumber);
		pagesequenceBand.addRibbonComponent(initialPageNumberCom);

		String fpc = forcepagecount == null ? auto : getItem(forcepagecount
				.getEnum());

		WiseCombobox forcePageCount = new WiseCombobox();
		forcePageCount.addItem(auto);
		forcePageCount.addItem(all_odd);
		forcePageCount.addItem(all_even);
		forcePageCount.addItem(end_odd);
		forcePageCount.addItem(end_even);
		forcePageCount.addItem(no_force);
		forcePageCount.InitValue(fpc);
		RibbonUIManager.getInstance().bind(
				PageSequence.SET_FORCEPAGECOUNT_ACTION, forcePageCount);

		forcePageCount.setPreferredSize(new Dimension(110, 22));
		JRibbonComponent fpcCom = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"), PAGE_SEQUENCE_BAND_PAGE_LIMIT,
				forcePageCount);
		pagesequenceBand.addRibbonComponent(fpcCom);

		String fmt = alb;
		if (formatStr != null && formatStr.equalsIgnoreCase("i"))
		{
			fmt = luoma;
		}
		WiseCombobox format = new WiseCombobox();
		format.addItem(alb);
		format.addItem(luoma);
		format.InitValue(fmt);
		RibbonUIManager.getInstance().bind(PageSequence.SET_FORMAT_ACTION,
				format);

		format.setPreferredSize(new Dimension(110, 22));
		JRibbonComponent fmtCom = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"), PAGE_SEQUENCE_BAND_PAGE_TYPE,
				format);
		pagesequenceBand.addRibbonComponent(fmtCom);

		return pagesequenceBand;
	}

	public String getItem(int value)
	{
		String item = "";
		if (value == 1)
		{
			item = one;
		} else if (value == Constants.EN_AUTO_EVEN)
		{
			item = even;
		} else if (value == Constants.EN_AUTO_ODD)
		{
			item = odd;
		} else if (value == Constants.EN_AUTO)
		{
			item = auto;
		} else if (value == Constants.EN_END_ON_ODD)
		{
			item = end_odd;
		} else if (value == Constants.EN_END_ON_EVEN)
		{
			item = end_even;
		} else if (value == Constants.EN_EVEN)
		{
			item = all_even;
		} else if (value == Constants.EN_ODD)
		{
			item = all_odd;
		} else if (value == Constants.EN_NO_FORCE)
		{
			item = no_force;
		} else
		{
			item = value + "";
		}
		return item;
	}
}
