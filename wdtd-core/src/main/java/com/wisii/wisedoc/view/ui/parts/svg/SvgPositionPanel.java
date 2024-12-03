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

package com.wisii.wisedoc.view.ui.parts.svg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.SVGUtil;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.parts.dialogs.SvgAttributesDialog;

@SuppressWarnings("serial")
public class SvgPositionPanel extends JPanel
{

	WiseSpinner heightpersentspinner;

	WiseSpinner widthpersentspinner;

	FixedLengthSpinner widthspinner;

	FixedLengthSpinner heightspinner;

	FixedLength currentwidth;

	FixedLength currentheight;

	final JCheckBox suodingwh = new JCheckBox("锁定纵横比");

	public SvgPositionPanel()
	{
		initComponents();
	}

	private void initComponents()
	{

		JPanel mainpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainpanel.setPreferredSize(new Dimension(420, 400));

		JPanel heightpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		heightpanel.setPreferredSize(new Dimension(400, 60));

		JLabel heightlabel = new JLabel("高度：");
		heightlabel.setPreferredSize(new Dimension(40, 25));

		heightspinner = new FixedLengthSpinner();
		heightspinner.setModel(new SpinnerFixedLengthModel());
		heightspinner.setPreferredSize(new Dimension(100, 25));

		FixedLength heightlg = (FixedLength) SvgAttributesDialog
				.getItem(Constants.PR_HEIGHT);
		heightspinner.setValue(heightlg);
		setCurrantheight(heightlg);

		heightspinner.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				FixedLength current = (FixedLength) ((FixedLengthSpinner) e
						.getSource()).getValue();
				SvgAttributesDialog.setItem(Constants.PR_HEIGHT, current);
				setCurrantheight(current);
			}
		});

		heightpanel.add(heightlabel);
		heightpanel.add(heightspinner);

		heightpanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"高度", TitledBorder.LEFT, TitledBorder.TOP));

		JPanel widthpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		widthpanel.setPreferredSize(new Dimension(400, 60));

		JLabel widthlabel = new JLabel("宽度：");
		widthlabel.setPreferredSize(new Dimension(40, 25));

		widthspinner = new FixedLengthSpinner();
		widthspinner.setModel(new SpinnerFixedLengthModel());
		widthspinner.setPreferredSize(new Dimension(100, 25));

		FixedLength widthlg = (FixedLength) SvgAttributesDialog
				.getItem(Constants.PR_WIDTH);
		widthspinner.setValue(widthlg);
		setCurrentwidth(widthlg);

		widthspinner.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				FixedLength current = (FixedLength) ((FixedLengthSpinner) e
						.getSource()).getValue();
				SvgAttributesDialog.setItem(Constants.PR_WIDTH, current);
				setCurrentwidth(current);
			}
		});
		widthpanel.add(widthlabel);
		widthpanel.add(widthspinner);

		widthpanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "宽度",
				TitledBorder.LEFT, TitledBorder.TOP));

		JPanel rotatingpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rotatingpanel.setPreferredSize(new Dimension(400, 60));

		JLabel rotatinglabel = new JLabel("旋转度数：");
		rotatinglabel.setPreferredSize(new Dimension(80, 25));

		WiseSpinner rotatingspinner = new WiseSpinner();
		SpinnerNumberModel rotatingmodel = new SpinnerNumberModel(0, 0, 360, 1);
		rotatingspinner.setModel(rotatingmodel);
		rotatingspinner.setPreferredSize(new Dimension(100, 25));
		int rotatingnum = SvgAttributesDialog
				.getItem(Constants.PR_SVG_ORIENTATION) != null ? (Integer) SvgAttributesDialog
				.getItem(Constants.PR_SVG_ORIENTATION)
				: 0;
		rotatingspinner.setValue(rotatingnum);

		rotatingspinner.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent event)
			{
				SvgAttributesDialog.setItem(Constants.PR_SVG_ORIENTATION,
						((WiseSpinner) event.getSource()).getValue());
			};

		});

		rotatingpanel.add(rotatinglabel);
		rotatingpanel.add(rotatingspinner);

		rotatingpanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"旋转", TitledBorder.LEFT, TitledBorder.TOP));

		JPanel aspectratiopanel = new JPanel(new GridLayout(2, 2));
		aspectratiopanel.setPreferredSize(new Dimension(400, 85));

		JPanel widthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		widthPanel.setPreferredSize(new Dimension(190, 30));

		JLabel width = new JLabel("宽度：");
		width.setPreferredSize(new Dimension(40, 25));

		widthpersentspinner = new WiseSpinner();
		SpinnerNumberModel widthpersentmodel = new SpinnerNumberModel(1, 0, 1,
				0.01);
		widthpersentspinner.setModel(widthpersentmodel);
		JSpinner.NumberEditor editorwidth = new JSpinner.NumberEditor(
				widthpersentspinner, "#0%");
		widthpersentspinner.setEditor(editorwidth);
		widthpersentspinner.setPreferredSize(new Dimension(100, 25));

		widthpersentspinner.setValue(1);

		widthpersentspinner.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent event)
			{
				Double currentvalue = (Double) ((WiseSpinner) event.getSource())
						.getValue();
				if (currentvalue >= 0d && currentvalue <= 1d)
				{
					Object valuewidth = SVGUtil.getNumbersOfLength(
							getCurrentwidth(), currentvalue);
					SvgAttributesDialog.setItem(Constants.PR_WIDTH, valuewidth);
					widthspinner.initValue(valuewidth);
					if (suodingwh.isSelected())
					{
						Object valueheight = SVGUtil.getNumbersOfLength(
								getCurrantheight(), currentvalue);
						SvgAttributesDialog.setItem(Constants.PR_HEIGHT,
								valueheight);
						heightspinner.initValue(valueheight);
						heightpersentspinner.setValue(currentvalue);
					}
				}
			};

		});

		widthPanel.add(width);
		widthPanel.add(widthpersentspinner);

		JPanel heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		heightPanel.setPreferredSize(new Dimension(190, 30));

		JLabel height = new JLabel("高度：");
		height.setPreferredSize(new Dimension(40, 25));

		heightpersentspinner = new WiseSpinner();
		SpinnerNumberModel heightpersentmodel = new SpinnerNumberModel(1, 0, 1,
				0.01);
		heightpersentspinner.setModel(heightpersentmodel);
		JSpinner.NumberEditor editorheight = new JSpinner.NumberEditor(
				heightpersentspinner, "#0%");
		heightpersentspinner.setEditor(editorheight);
		heightpersentspinner.setPreferredSize(new Dimension(100, 25));

		heightpersentspinner.setValue(1);

		heightpersentspinner.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent event)
			{
				Double currentvalue = (Double) ((WiseSpinner) event.getSource())
						.getValue();
				if (currentvalue >= 0d && currentvalue <= 1d)
				{
					Object valueheight = SVGUtil.getNumbersOfLength(
							getCurrantheight(), currentvalue);
					SvgAttributesDialog.setItem(Constants.PR_HEIGHT,
							valueheight);
					heightspinner.initValue(valueheight);
					if (suodingwh.isSelected())
					{
						Object valuewidth = SVGUtil.getNumbersOfLength(
								getCurrentwidth(), currentvalue);
						SvgAttributesDialog.setItem(Constants.PR_WIDTH,
								valuewidth);
						widthspinner.initValue(valuewidth);
						widthpersentspinner.setValue(currentvalue);
					}
				}
			};

		});
		heightPanel.add(height);
		heightPanel.add(heightpersentspinner);

		suodingwh.setSelected(false);
		suodingwh.setPreferredSize(new Dimension(100, 25));

		aspectratiopanel.add(heightPanel);
		aspectratiopanel.add(widthPanel);
		aspectratiopanel.add(suodingwh);

		aspectratiopanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"缩放", TitledBorder.LEFT, TitledBorder.TOP));

		mainpanel.add(heightpanel);
		mainpanel.add(widthpanel);
		mainpanel.add(rotatingpanel);
		mainpanel.add(aspectratiopanel);
		add(mainpanel);

	}

	public FixedLength getCurrentwidth()
	{
		return currentwidth;
	}

	public void setCurrentwidth(FixedLength currentwidth)
	{
		this.currentwidth = currentwidth;
	}

	public FixedLength getCurrantheight()
	{
		return currentheight;
	}

	public void setCurrantheight(FixedLength currantheight)
	{
		this.currentheight = currantheight;
	}

}
