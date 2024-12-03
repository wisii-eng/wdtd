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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.model.CustomizeSimplePageMasterModel;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class CommonMarginBlockPanel extends JPanel
{

	CustomizeSimplePageMasterModel spmm;

	CommonMarginBlock commonmarginblock;

	FixedLengthSpinner topmarginspinner = new FixedLengthSpinner();

	FixedLengthSpinner bottommarginspinner = new FixedLengthSpinner();

	FixedLengthSpinner leftmarginspinner = new FixedLengthSpinner();

	FixedLengthSpinner rightmarginspinner = new FixedLengthSpinner();

	String title;

	public CommonMarginBlockPanel(CustomizeSimplePageMasterModel cspm,
			String paneltitle)
	{
		super();
		spmm = cspm;
		commonmarginblock = null;
		title = paneltitle;
		this.setLayout(new GridLayout(2, 1));
		this.setPreferredSize(new Dimension(420, 110));
		initComponents();
	}

	public CommonMarginBlockPanel(CustomizeSimplePageMasterModel cspm,
			CommonMarginBlock cmb, String paneltitle)
	{
		super();
		spmm = cspm;
		commonmarginblock = cmb;
		title = paneltitle;
		this.setLayout(new GridLayout(2, 1));
		this.setPreferredSize(new Dimension(420, 110));
		initComponents();
	}

	private void initComponents()
	{
		JPanel topbottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 3));

		JPanel leftright = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 3));

		JLabel margintop = new JLabel(UiText.PAGE_MARGIN_TOP_LABEL);

		JLabel marginbottom = new JLabel(UiText.PAGE_MARGIN_BOTTOM_LABEL);

		JLabel marginleft = new JLabel(UiText.PAGE_MARGIN_LEFT_LABEL);

		JLabel marginright = new JLabel(UiText.PAGE_MARGIN_RIGHT_LABEL);

		topmarginspinner.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		bottommarginspinner
				.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		leftmarginspinner.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		rightmarginspinner.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		topbottom.add(margintop);
		topbottom.add(topmarginspinner);
		topbottom.add(marginbottom);
		topbottom.add(bottommarginspinner);
		leftright.add(marginleft);
		leftright.add(leftmarginspinner);
		leftright.add(marginright);
		leftright.add(rightmarginspinner);
		this.add(topbottom);
		this.add(leftright);
		this.setBorder(new TitledBorder(new LineBorder(Color.BLUE), title,
				TitledBorder.LEFT, TitledBorder.TOP));
		setInitialState();
		addaction();
	}

	public void addaction()
	{
		topmarginspinner.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if (spmm != null)
				{
					FixedLength current = topmarginspinner.getValue();
					if (UiText.PAGE_BODY_MARGINS_LABEL.equals(title))
					{
						spmm.getRegionBodyModel().setRegionBodyMargionTop(
								current);
					} else if (UiText.PAGE_MARGINS_LABEL.equals(title))
					{
						spmm.setPageMargionTop(current);
					}
				}
			}
		});
		bottommarginspinner.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if (spmm != null)
				{
					FixedLength current = bottommarginspinner.getValue();
					if (UiText.PAGE_BODY_MARGINS_LABEL.equals(title))
					{
						spmm.getRegionBodyModel().setRegionBodyMargionBottom(
								current);
					} else if (UiText.PAGE_MARGINS_LABEL.equals(title))
					{
						spmm.setPageMargionBottom(current);
					}
				}
			}
		});
		leftmarginspinner.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if (spmm != null)
				{
					FixedLength current = leftmarginspinner.getValue();
					if (UiText.PAGE_BODY_MARGINS_LABEL.equals(title))
					{
						spmm.getRegionBodyModel().setRegionBodyMargionLeft(
								current);
					} else if (UiText.PAGE_MARGINS_LABEL.equals(title))
					{
						spmm.setPageMargionLeft(current);
					}
				}
			}
		});
		rightmarginspinner.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if (spmm != null)
				{
					FixedLength current = rightmarginspinner.getValue();
					if (UiText.PAGE_BODY_MARGINS_LABEL.equals(title))
					{
						spmm.getRegionBodyModel().setRegionBodyMargionRight(
								current);
					} else if (UiText.PAGE_MARGINS_LABEL.equals(title))
					{
						spmm.setPageMargionRight(current);
					}
				}
			}
		});
	}

	public void setInitialState()
	{
		if (commonmarginblock != null)
		{
			Length toplength = commonmarginblock.getMarginTop();
			Length bottomlength = commonmarginblock.getMarginBottom();
			Length leftlength = commonmarginblock.getMarginLeft();
			Length rightlength = commonmarginblock.getMarginRight();
			topmarginspinner.initValue(toplength);
			bottommarginspinner.initValue(bottomlength);
			leftmarginspinner.initValue(leftlength);
			rightmarginspinner.initValue(rightlength);
		} else
		{
			topmarginspinner.initValue(null);
			bottommarginspinner.initValue(null);
			leftmarginspinner.initValue(null);
			rightmarginspinner.initValue(null);
		}
	}

	public void setCommonMarginBlock(CustomizeSimplePageMasterModel cspm,
			CommonMarginBlock cmb)
	{
		spmm = cspm;
		commonmarginblock = cmb;
		setInitialState();
	}

	public CommonMarginBlock getCommonMarginBlock()
	{
		Length marginTop = topmarginspinner.getValue();
		Length marginBottom = bottommarginspinner.getValue();
		Length marginLeft = leftmarginspinner.getValue();
		Length marginRight = rightmarginspinner.getValue();
		CommonMarginBlock newcmb = new CommonMarginBlock(marginTop,
				marginBottom, marginLeft, marginRight, commonmarginblock
						.getSpaceBefore(), commonmarginblock.getSpaceAfter(),
				commonmarginblock.getStartIndent(), commonmarginblock
						.getEndIndent());
		return newcmb;
	}
}
