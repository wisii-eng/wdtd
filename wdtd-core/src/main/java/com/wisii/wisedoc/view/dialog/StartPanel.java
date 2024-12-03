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

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.StaticContentManeger;
import com.wisii.wisedoc.view.ui.model.CustomizeRegionStartModel;
import com.wisii.wisedoc.view.ui.model.CustomizeSimplePageMasterModel;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class StartPanel extends JPanel
{

	CustomizeSimplePageMasterModel spmm;

	CustomizeRegionStartModel regionstart;

	RegionalDirectionOverflowPanel regionbodydopanel;

	BackGroundPanel backgroundpanel;

	FixedLengthSpinner extentspinner = new FixedLengthSpinner();

	JButton setcontent = new JButton(UiText.SET_REGION_START_CONTENT);

	public StartPanel()
	{
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		initComponents();
	}

	public StartPanel(CustomizeSimplePageMasterModel region)
	{
		super();
		spmm = region;
		regionstart = region.getRegionStartModel();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		initComponents();
	}

	private void initComponents()
	{
		regionbodydopanel = new RegionalDirectionOverflowPanel(spmm, 3);
		backgroundpanel = new BackGroundPanel(spmm, 3, UiText.BACKGROUND);
		JPanel beforepanel = new JPanel(new GridLayout(1, 2));
		JPanel extentpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel extentlabel = new JLabel(UiText.REGION_START_WIDTH);
		extentspinner.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		extentpanel.add(extentlabel);
		extentpanel.add(extentspinner);

		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonpanel.add(setcontent);
		beforepanel.add(extentpanel);

		beforepanel.add(buttonpanel);
		this.add(regionbodydopanel);
		this.add(beforepanel);
		this.add(backgroundpanel);
		extentspinner.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				FixedLength current = extentspinner.getValue();
				if (regionstart.getExtent().equals(
						spmm.getRegionBodyModel().getRegionBodyMargin()
								.getMarginLeft()))
				{
					spmm.getRegionBodyModel().setRegionBodyMargionLeft(current);
				}
				regionstart.setExtent(current);
			}
		});
		setcontent.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				String name = regionstart.getRegionstartmodel().getRegionName();
				StaticContent currentsc = StaticContentManeger.getScmap().get(
						name);
				SetContntDialog dia = new SetContntDialog(
						UiText.SET_REGION_START_CONTENT,
						Constants.FO_REGION_START, currentsc, spmm);
				if (dia.showDialog() == DialogResult.OK)
				{
					StaticContent newsc = dia.getStaticContent();
					String namenew = newsc.getFlowName();
					StaticContentManeger.getScmap().put(namenew, newsc);
				}
			}
		});
	}

	public void setInitialState()
	{
		if (regionstart != null)
		{
			backgroundpanel.setCommonBorderPaddingBackground(spmm);
			regionbodydopanel.setModel(spmm);
			extentspinner.initValue(regionstart.getExtent());
		} else
		{
			backgroundpanel.setCommonBorderPaddingBackground(null);
			regionbodydopanel.setModel(null);
			extentspinner.initValue(null);
		}
	}

	public void setRegionModel(CustomizeSimplePageMasterModel model)
	{
		if (model != null)
		{
			spmm = model;
			regionstart = model.getRegionStartModel();
		} else
		{
			spmm = null;
			regionstart = null;
		}
		setInitialState();
	}

}