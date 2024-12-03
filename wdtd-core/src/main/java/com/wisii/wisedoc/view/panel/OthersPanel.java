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

package com.wisii.wisedoc.view.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;

@SuppressWarnings("serial")
public class OthersPanel extends OnlyLayoutPanel
{

	JCheckBox padding = new JCheckBox("四条内部边距相同", true);
	{
		padding.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				paddingValue.setEnabled(padding.isSelected());
				paddingBefore.setEnabled(!padding.isSelected());
				paddingBeforeValue.setEnabled(!padding.isSelected());
				paddingAfter.setEnabled(!padding.isSelected());
				paddingAfterValue.setEnabled(!padding.isSelected());
				paddingStart.setEnabled(!padding.isSelected());
				paddingStartValue.setEnabled(!padding.isSelected());
				paddingEnd.setEnabled(!padding.isSelected());
				paddingEndValue.setEnabled(!padding.isSelected());
				if (!padding.isSelected())
				{
					paddingBefore.setSelected(true);
					paddingAfter.setSelected(true);
					paddingStart.setSelected(true);
					paddingEnd.setSelected(true);
				}
			}
		});
	}

	WiseTextField paddingValue = new WiseTextField("0.0");
	{
		paddingValue.setEnabled(padding.isSelected());
	}

	JCheckBox paddingBefore = new JCheckBox("上边距");
	{
		paddingBefore.setEnabled(!padding.isSelected());
		paddingBefore.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				paddingBeforeValue.setEnabled(paddingBefore.isSelected());
			}
		});
	}

	WiseTextField paddingBeforeValue = new WiseTextField("0.0");
	{
		paddingBeforeValue.setEnabled(!padding.isSelected());
	}

	JCheckBox paddingAfter = new JCheckBox("下边距");
	{
		paddingAfter.setEnabled(!padding.isSelected());
		paddingAfter.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				paddingAfterValue.setEnabled(paddingAfter.isSelected());
			}
		});
	}

	WiseTextField paddingAfterValue = new WiseTextField("0.0");
	{
		paddingAfterValue.setEnabled(!padding.isSelected());
	}

	JCheckBox paddingStart = new JCheckBox("左边距");
	{
		paddingStart.setEnabled(!padding.isSelected());
		paddingStart.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				paddingStartValue.setEnabled(paddingStart.isSelected());
			}
		});
	}

	WiseTextField paddingStartValue = new WiseTextField("0.0");
	{
		paddingStartValue.setEnabled(!padding.isSelected());
	}

	JCheckBox paddingEnd = new JCheckBox("右边距");
	{
		paddingEnd.setEnabled(!padding.isSelected());
		paddingEnd.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				paddingEndValue.setEnabled(paddingEnd.isSelected());
			}
		});
	}

	WiseTextField paddingEndValue = new WiseTextField("0.0");
	{
		paddingEndValue.setEnabled(!padding.isSelected());
	}

	WiseTextField indentStart = new WiseTextField("0.0");

	WiseTextField indentEnd = new WiseTextField("0.0");

	String nextPage = "下一页";

	String nextOddPage = "下一奇数页";

	String nextEvenPage = "下一偶数页";

	WiseCombobox breakBefore = new WiseCombobox();
	{
		breakBefore.addItem(nextPage);
		breakBefore.addItem(nextOddPage);
		breakBefore.addItem(nextEvenPage);
		breakBefore.setSelectedItem(nextPage);
	}

	WiseCombobox breakAfter = new WiseCombobox();
	{
		breakAfter.addItem(nextPage);
		breakAfter.addItem(nextOddPage);
		breakAfter.addItem(nextEvenPage);
		breakAfter.setSelectedItem(nextPage);
	}

	JCheckBox nobreak = new JCheckBox("不能分页", true);

	JCheckBox keepWithP = new JCheckBox("与前一元素相邻", true);

	JCheckBox keepWithN = new JCheckBox("与后一元素相邻");
	{
		keepWithN.setEnabled(!keepWithP.isSelected());
	}

	WiseTextField spaceStart = new WiseTextField("0.0");

	WiseTextField spaceEnd = new WiseTextField("0.0");

	WiseTextField spaceBefore = new WiseTextField("0.0");

	WiseTextField spaceAfter = new WiseTextField("0.0");

	JButton backGround = new JButton("设置");

	JButton backGroundImage = new JButton("设置");

	String norepeat = "不重复";

	String repeatx = "水平方向重复";

	String repeaty = "垂直方向重复";

	String repeat = "两方向同时重复";

	WiseCombobox repeatList = new WiseCombobox();
	{
		repeatList.addItem(norepeat);
		repeatList.addItem(repeatx);
		repeatList.addItem(repeaty);
		repeatList.addItem(repeat);
		repeatList.setSelectedItem(norepeat);
	}

	String hidden = "隐藏";

	String visible = "显示";

	String scroll = "滚动";

	String error = "报错";

	WiseCombobox overflow = new WiseCombobox();
	{
		overflow.addItem(hidden);
		overflow.addItem(visible);
		overflow.addItem(scroll);
		overflow.addItem(error);
		overflow.setSelectedItem(visible);
	}

	String left = "左对齐";

	String right = "右对齐";

	String center = "居中对齐";

	String justify = "匀满";

	String before = "上对齐";

	String after = "下对齐";

	String superr = "上标";

	String sub = "下标";

	String baseline = "基线";

	String middle = "中间";

	WiseCombobox textAlign = new WiseCombobox();
	{
		textAlign.addItem(left);
		textAlign.addItem(right);
		textAlign.addItem(center);
		textAlign.addItem(justify);
		textAlign.setSelectedItem(left);
	}

	WiseCombobox textAlignLast = new WiseCombobox();
	{
		textAlignLast.addItem(left);
		textAlignLast.addItem(right);
		textAlignLast.addItem(center);
		textAlignLast.addItem(justify);
		textAlignLast.setSelectedItem(left);
	}

	WiseCombobox displayAlign = new WiseCombobox();
	{
		displayAlign.addItem(before);
		displayAlign.addItem(after);
		displayAlign.addItem(center);
		displayAlign.setSelectedItem(before);
	}

	WiseCombobox verticalAlign = new WiseCombobox();
	{
		verticalAlign.addItem(baseline);
		verticalAlign.addItem(superr);
		verticalAlign.addItem(sub);
		verticalAlign.addItem(middle);
		verticalAlign.setSelectedItem(baseline);
	}

	public OthersPanel()
	{
		super();
		JPanel paddingPanel = new JPanel();
		paddingPanel.setSize(490, 260);
		XYLayout paddingLayout = new XYLayout();
		paddingLayout.setWidth(490);
		paddingLayout.setHeight(260);
		paddingPanel.setLayout(paddingLayout);
		paddingPanel.setBackground(getBackground());
		padding.setBackground(getBackground());

		paddingPanel.add(padding, new XYConstraints(5, 0, 130, 20));
		paddingPanel.add(paddingValue, new XYConstraints(140, 0, 80, 20));
		paddingPanel.add(new JLabel("单位：MM"),
				new XYConstraints(240, 0, 100, 20));

		paddingBefore.setBackground(getBackground());
		paddingPanel.add(paddingBefore, new XYConstraints(5, 30, 70, 20));
		paddingPanel.add(paddingBeforeValue, new XYConstraints(80, 30, 80, 20));

		paddingAfter.setBackground(getBackground());
		paddingPanel.add(paddingAfter, new XYConstraints(180, 30, 70, 20));
		paddingPanel.add(paddingAfterValue, new XYConstraints(255, 30, 80, 20));
		paddingPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"内部边距", TitledBorder.CENTER, TitledBorder.TOP));

		paddingStart.setBackground(getBackground());
		paddingPanel.add(paddingStart, new XYConstraints(5, 60, 70, 20));
		paddingPanel.add(paddingStartValue, new XYConstraints(80, 60, 80, 20));

		paddingEnd.setBackground(getBackground());
		paddingPanel.add(paddingEnd, new XYConstraints(180, 60, 70, 20));
		paddingPanel.add(paddingEndValue, new XYConstraints(255, 60, 80, 20));
		paddingPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"内部边距", TitledBorder.CENTER, TitledBorder.TOP));

		this.add(paddingPanel, new XYConstraints(90, 20, 355, 110));

		this.add(new JLabel("前缩进"), new XYConstraints(100, 140, 50, 20));
		this.add(indentStart, new XYConstraints(150, 140, 80, 20));

		this.add(new JLabel("后缩进"), new XYConstraints(280, 140, 50, 20));
		this.add(indentEnd, new XYConstraints(330, 140, 80, 20));

		this.add(new JLabel("前分页"), new XYConstraints(100, 170, 50, 20));
		this.add(breakBefore, new XYConstraints(150, 170, 100, 20));

		this.add(new JLabel("后分页"), new XYConstraints(280, 170, 50, 20));
		this.add(breakAfter, new XYConstraints(330, 170, 100, 20));

		this.add(nobreak, new XYConstraints(100, 200, 100, 20));

		this.add(keepWithP, new XYConstraints(100, 230, 150, 20));
		this.add(keepWithN, new XYConstraints(280, 230, 150, 20));

		JPanel spacePanel = new JPanel();
		spacePanel.setSize(490, 260);
		XYLayout spaceLayout = new XYLayout();
		spaceLayout.setWidth(490);
		spaceLayout.setHeight(260);
		spacePanel.setLayout(spaceLayout);
		spacePanel.setBackground(getBackground());

		spacePanel.add(new JLabel("前间距"), new XYConstraints(5, 0, 60, 20));
		spacePanel.add(spaceStart, new XYConstraints(70, 0, 80, 20));

		spacePanel.add(new JLabel("后间距"), new XYConstraints(200, 0, 60, 20));
		spacePanel.add(spaceEnd, new XYConstraints(265, 0, 80, 20));

		spacePanel.add(new JLabel("上间距"), new XYConstraints(5, 30, 60, 20));
		spacePanel.add(spaceBefore, new XYConstraints(70, 30, 80, 20));

		spacePanel.add(new JLabel("下间距"), new XYConstraints(200, 30, 60, 20));
		spacePanel.add(spaceAfter, new XYConstraints(265, 30, 80, 20));

		spacePanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"元素间距(单位：MM)", TitledBorder.CENTER, TitledBorder.TOP));

		this.add(spacePanel, new XYConstraints(90, 260, 355, 80));

		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setSize(490, 260);
		XYLayout backgroundLayout = new XYLayout();
		backgroundLayout.setWidth(490);
		backgroundLayout.setHeight(260);
		backgroundPanel.setLayout(backgroundLayout);
		backgroundPanel.setBackground(getBackground());

		backgroundPanel.add(new JLabel("背景色"), new XYConstraints(5, 0, 60, 20));
		backgroundPanel.add(backGround, new XYConstraints(70, 0, 80, 20));

		backgroundPanel.add(new JLabel("背景图片"), new XYConstraints(200, 0, 60,
				20));
		backgroundPanel.add(backGroundImage, new XYConstraints(265, 0, 80, 20));

		backgroundPanel.add(new JLabel("背景图重复方式"), new XYConstraints(5, 30,
				100, 20));
		backgroundPanel.add(repeatList, new XYConstraints(110, 30, 120, 20));

		backgroundPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"背景", TitledBorder.CENTER, TitledBorder.TOP));

		this.add(backgroundPanel, new XYConstraints(90, 350, 355, 80));

		this.add(new JLabel("溢出处理"), new XYConstraints(100, 440, 60, 20));
		this.add(overflow, new XYConstraints(165, 440, 80, 20));

		JPanel alignPanel = new JPanel();
		alignPanel.setSize(490, 260);
		XYLayout alignLayout = new XYLayout();
		alignLayout.setWidth(490);
		alignLayout.setHeight(260);
		alignPanel.setLayout(alignLayout);
		alignPanel.setBackground(getBackground());

		alignPanel.add(new JLabel("水平对齐方式"), new XYConstraints(5, 0, 80, 20));
		alignPanel.add(textAlign, new XYConstraints(90, 0, 80, 20));

		alignPanel.add(new JLabel("末行水平对齐方式"), new XYConstraints(190, 0, 110,
				20));
		alignPanel.add(textAlignLast, new XYConstraints(305, 0, 80, 20));

		alignPanel.add(new JLabel("垂直对齐方式"), new XYConstraints(5, 30, 80, 20));
		alignPanel.add(displayAlign, new XYConstraints(90, 30, 80, 20));

		alignPanel.add(new JLabel("文字行内对齐方式"), new XYConstraints(190, 30, 110,
				20));
		alignPanel.add(verticalAlign, new XYConstraints(305, 30, 80, 20));

		alignPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "背景",
				TitledBorder.CENTER, TitledBorder.TOP));

		this.add(alignPanel, new XYConstraints(75, 470, 400, 80));

	}
}
