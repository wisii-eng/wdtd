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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.parts.dialogs.SvgAttributesDialog;

@SuppressWarnings("serial")
public class SvgBorderAndColorPanel extends JPanel
{

	String angle = "平三角头";

	String circle = "圆头";

	String square = "方头";

	String diamond = "钻石头";

	String neiaopingangle = "内凹平面三角头";

	String neiaohuangle = "内凹弧面三角头";

	String daoangle = "倒平三角头";

	String daoneiaopingangle = "倒内凹平面三角头";

	String daoneiaohuangle = "倒内凹弧面三角头";

	public SvgBorderAndColorPanel()
	{
		this.setPreferredSize(new Dimension(420, 400));
		initComponents();
	}

	private void initComponents()
	{
		JPanel mainpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainpanel.setPreferredSize(new Dimension(420, 400));

		JPanel fillpanel = new JPanel(new GridLayout(2, 1));
		fillpanel.setPreferredSize(new Dimension(400, 90));
		JPanel colorpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		colorpanel.setPreferredSize(new Dimension(200, 25));

		JLabel color = new JLabel("颜色");
		color.setPreferredSize(new Dimension(40, 25));
		colorpanel.add(color);
		JComboBox fillcolor;
		try
		{
			fillcolor = new ColorComboBox();
			fillcolor.setPreferredSize(new Dimension(140, 25));
			fillcolor.setSelectedItem(SvgAttributesDialog
					.getItem(Constants.PR_FILL));
			fillcolor.addItemListener(new ItemListener()
			{

				@Override
				public void itemStateChanged(ItemEvent event)
				{
					if (event.getStateChange() == ItemEvent.SELECTED)
					{
						SvgAttributesDialog.setItem(Constants.PR_FILL,
								((JComboBox) event.getSource())
										.getSelectedItem());
					}
				}
			});
			colorpanel.add(fillcolor);
		} catch (IncompatibleLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JPanel opacitypanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		opacitypanel.setPreferredSize(new Dimension(200, 30));
		JLabel opacity = new JLabel("透明度");
		opacity.setPreferredSize(new Dimension(40, 25));
		WiseSpinner opacityspinner = new WiseSpinner();
		opacityspinner.setModel(new SpinnerNumberModel(0, 0, 1, 0.01));
		if (SvgAttributesDialog.getItem(Constants.PR_FILL) != null)
		{
			opacityspinner.setValue(SvgAttributesDialog
					.getItem(Constants.PR_FILL));
		} else
		{
			opacityspinner.setValue(0);
		}

		opacityspinner.setPreferredSize(new Dimension(140, 25));
		opacityspinner.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent event)
			{
				SvgAttributesDialog.setItem(Constants.PR_APHLA,
						((WiseSpinner) event.getSource()).getValue());
			};

		});
		opacitypanel.add(opacity);
		opacitypanel.add(opacityspinner);

		fillpanel.add(colorpanel);
		fillpanel.add(opacitypanel);

		fillpanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "填充",
				TitledBorder.LEFT, TitledBorder.TOP));

		JPanel borderpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		borderpanel.setPreferredSize(new Dimension(400, 85));

		JLabel colorborder = new JLabel("颜色");
		colorborder.setPreferredSize(new Dimension(40, 25));
		borderpanel.add(colorborder);

		JComboBox bordercolor;
		try
		{
			bordercolor = new ColorComboBox();
			bordercolor.setPreferredSize(new Dimension(140, 25));
			bordercolor.setSelectedItem(SvgAttributesDialog
					.getItem(Constants.PR_COLOR));
			bordercolor.addItemListener(new ItemListener()
			{

				@Override
				public void itemStateChanged(ItemEvent event)
				{
					if (event.getStateChange() == ItemEvent.SELECTED)
					{
						SvgAttributesDialog.setItem(Constants.PR_COLOR,
								((JComboBox) event.getSource())
										.getSelectedItem());
					}
				}
			});
			borderpanel.add(bordercolor);
		} catch (IncompatibleLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel borderstyle = new JLabel("线型");
		borderstyle.setPreferredSize(new Dimension(40, 25));

		WiseCombobox borderStyle = new WiseCombobox();
		borderStyle.addItem("实线");
		borderStyle.addItem("虚线");
		borderStyle.addItem("点划线");
		borderStyle.addItem("双点划线");
		borderStyle.setPreferredSize(new Dimension(140, 25));
		int chushistyle = SvgAttributesDialog
				.getItem(Constants.PR_SVG_LINE_TYPE) != null ? (Integer) SvgAttributesDialog
				.getItem(Constants.PR_SVG_LINE_TYPE)
				: 0;
		borderStyle.setSelectedIndex(chushistyle);
		borderStyle.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					SvgAttributesDialog.setItem(Constants.PR_SVG_LINE_TYPE,
							((JComboBox) event.getSource()).getSelectedIndex());
				}
			}
		});
		borderpanel.add(borderstyle);
		borderpanel.add(borderStyle);

		JLabel borderwidth = new JLabel("线宽");
		borderwidth.setPreferredSize(new Dimension(40, 25));

		FixedLengthSpinner borderWidth = new FixedLengthSpinner();
		borderWidth.setModel(new SpinnerFixedLengthModel());
		borderWidth.setPreferredSize(new Dimension(140, 25));
		borderWidth.setValue(SvgAttributesDialog
				.getItem(Constants.PR_STROKE_WIDTH));

		borderWidth.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent event)
			{
				SvgAttributesDialog.setItem(Constants.PR_STROKE_WIDTH,
						((FixedLengthSpinner) event.getSource()).getValue());
			};

		});
		borderpanel.add(borderwidth);
		borderpanel.add(borderWidth);

		// JLabel borderpacity = new JLabel("透明度");
		// borderpacity.setPreferredSize(new Dimension(40, 25));
		// WiseSpinner borderopacityspinner = new WiseSpinner();
		// borderopacityspinner.setModel(new SpinnerNumberModel(0, 0, 1, 0.1));
		// borderopacityspinner.setPreferredSize(new Dimension(140, 25));
		//
		// borderpanel.add(borderpacity);
		// borderpanel.add(borderopacityspinner);

		borderpanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"线条", TitledBorder.LEFT, TitledBorder.TOP));

		JPanel borderheardpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		borderheardpanel.setPreferredSize(new Dimension(400, 55));

		JLabel borderheardstyle = new JLabel("始端样式");
		borderheardstyle.setPreferredSize(new Dimension(55, 25));
		WiseCombobox borderheardStyle = new WiseCombobox();
		borderheardStyle.setPreferredSize(new Dimension(130, 25));
		borderheardStyle.addItem(angle);
		borderheardStyle.addItem(square);
		borderheardStyle.addItem(circle);
		borderheardStyle.addItem(diamond);
		borderheardStyle.addItem(neiaopingangle);
		borderheardStyle.addItem(neiaohuangle);
		borderheardStyle.addItem(daoangle);
		borderheardStyle.addItem(daoneiaopingangle);
		borderheardStyle.addItem(daoneiaohuangle);

		int qishistyle = SvgAttributesDialog
				.getItem(Constants.PR_SVG_ARROW_START_TYPE) != null ? (Integer) SvgAttributesDialog
				.getItem(Constants.PR_SVG_ARROW_START_TYPE)
				: 0;
		borderheardStyle.setSelectedIndex(qishistyle);
		borderheardStyle.setEnabled(SvgAttributesDialog
				.getItem(Constants.PR_SVG_ARROW_START_TYPE) != null);
		borderheardStyle.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					SvgAttributesDialog.setItem(
							Constants.PR_SVG_ARROW_START_TYPE,
							((JComboBox) event.getSource()).getSelectedItem());
				}
			}
		});

		JLabel borderfooterstyle = new JLabel("终端样式");
		borderfooterstyle.setPreferredSize(new Dimension(55, 25));
		WiseCombobox borderfooterStyle = new WiseCombobox();
		borderfooterStyle.setPreferredSize(new Dimension(130, 25));
		borderfooterStyle.addItem(angle);
		borderfooterStyle.addItem(square);
		borderfooterStyle.addItem(circle);
		borderfooterStyle.addItem(diamond);
		borderfooterStyle.addItem(neiaopingangle);
		borderfooterStyle.addItem(neiaohuangle);
		borderfooterStyle.addItem(daoangle);
		borderfooterStyle.addItem(daoneiaopingangle);
		borderfooterStyle.addItem(daoneiaohuangle);

		int zhongzhistyle = SvgAttributesDialog
				.getItem(Constants.PR_SVG_ARROW_END_TYPE) != null ? (Integer) SvgAttributesDialog
				.getItem(Constants.PR_SVG_ARROW_END_TYPE)
				: 0;
		borderfooterStyle.setSelectedIndex(zhongzhistyle);
		borderfooterStyle.setEnabled(SvgAttributesDialog
				.getItem(Constants.PR_SVG_ARROW_END_TYPE) != null);
		borderfooterStyle.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					SvgAttributesDialog.setItem(
							Constants.PR_SVG_ARROW_END_TYPE, ((JComboBox) event
									.getSource()).getSelectedItem());
				}
			}
		});

		borderheardpanel.add(borderheardstyle);
		borderheardpanel.add(borderheardStyle);
		borderheardpanel.add(borderfooterstyle);
		borderheardpanel.add(borderfooterStyle);

		borderheardpanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"箭头", TitledBorder.LEFT, TitledBorder.TOP));

		mainpanel.add(fillpanel);
		mainpanel.add(borderpanel);
		mainpanel.add(borderheardpanel);
		add(mainpanel);
	}
}
