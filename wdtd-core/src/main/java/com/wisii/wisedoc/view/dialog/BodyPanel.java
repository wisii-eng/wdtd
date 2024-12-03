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

package com.wisii.wisedoc.view.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.model.CustomizeRegionBodyModel;
import com.wisii.wisedoc.view.ui.model.CustomizeSimplePageMasterModel;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class BodyPanel extends JPanel
{

	CustomizeSimplePageMasterModel spmm;

	CustomizeRegionBodyModel regionbody;

	WiseSpinner columncount = new WiseSpinner(new SpinnerNumberModel(1, 1,
			null, 1));

	FixedLengthSpinner columngap = new FixedLengthSpinner();

	RegionalDirectionOverflowPanel regionbodydopanel;

	BackGroundPanel backgroundpanel;

	CommonMarginBlockPanel marginpanel;

	public BodyPanel()
	{
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		initComponents();
	}

	public BodyPanel(CustomizeSimplePageMasterModel region)
	{
		super();
		spmm = region;
		if (region != null)
		{
			regionbody = region.getRegionBodyModel();
		}
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		initComponents();
	}

	private void initComponents()
	{
		JPanel bodylayout = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bodylayout.setPreferredSize(new Dimension(500, 125));

		regionbodydopanel = new RegionalDirectionOverflowPanel(spmm, 0);

		JPanel two = new JPanel(new GridLayout(1, 2));

		JPanel twoleft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel tworight = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel columncountlabel = new JLabel(UiText.PAGE_COLUMN_COUNT_LABEL);

		JLabel columngaplabel = new JLabel(UiText.PAGE_COLUMN_GAP_LABEL);

		columngap.setModel(new SpinnerNumberModel(1, 1, null, 1));

		twoleft.add(columncountlabel);
		twoleft.add(columncount);
		tworight.add(columngaplabel);
		tworight.add(columngap);

		two.add(twoleft);
		two.add(tworight);

		bodylayout.add(regionbodydopanel);
		bodylayout.add(two);

		backgroundpanel = new BackGroundPanel(spmm, 0,
				UiText.PAGE_BODY_BACKGROUND_LABEL);

		marginpanel = new CommonMarginBlockPanel(spmm,
				UiText.PAGE_BODY_MARGINS_LABEL);
		this.add(bodylayout);
		this.add(marginpanel);
		this.add(backgroundpanel);
		columncount.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (regionbody != null)
				{
					regionbody.setColumnCount((Integer) columncount.getValue());
				}
			}
		});
		columngap.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (regionbody != null)
				{
					regionbody.setColumnGap(columngap.getValue());
				}
			}
		});
	}

	public void setInitialState()
	{
		if (regionbody != null)
		{
			int clumnnumber = regionbody.getColumnCount();
			Length grap = regionbody.getColumnGap();
			columncount.initValue(clumnnumber);
			columngap.initValue(grap);
			regionbodydopanel.setModel(spmm);
			CommonMarginBlock cmb = regionbody.getRegionBodyMargin();
			marginpanel.setCommonMarginBlock(spmm, cmb);
			backgroundpanel.setCommonBorderPaddingBackground(spmm);
		} else
		{
			columncount.initValue(1);
			columngap.initValue(null);
			regionbodydopanel.setModel(null);
			marginpanel.setCommonMarginBlock(spmm, null);
			backgroundpanel.setCommonBorderPaddingBackground(null);
		}
	}

	public void setRegionModel(CustomizeSimplePageMasterModel model)
	{
		if (model != null)
		{
			spmm = model;
			regionbody = model.getRegionBodyModel();
		} else
		{
			spmm = null;
			regionbody = null;
		}
		setInitialState();
	}

}