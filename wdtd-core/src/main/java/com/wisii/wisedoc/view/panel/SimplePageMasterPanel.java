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
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.component.WiseTextField;

@SuppressWarnings("serial")
public class SimplePageMasterPanel extends OnlyLayoutPanel
{

	JCheckBox standard = new JCheckBox("标准", true);
	{
		standard.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				custom.setSelected(!standard.isSelected());
				comboBoxPageType.setEnabled(standard.isSelected());
				pageWidth.setEnabled(custom.isSelected());
				pageHeight.setEnabled(custom.isSelected());
				extentBefore.setEnabled(custom.isSelected());
				extentAfter.setEnabled(custom.isSelected());
				extentStart.setEnabled(custom.isSelected());
				extentEnd.setEnabled(custom.isSelected());
				pageType.setEnabled(standard.isSelected());
				pagewidth.setEnabled(custom.isSelected());
				pageheight.setEnabled(custom.isSelected());
				all.setEnabled(custom.isSelected());
				all.setSelected(custom.isSelected());
				margin.setEnabled(custom.isSelected());
				marginTop
						.setEnabled(custom.isSelected() && !margin.isEnabled());
				marginBottom.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginLeft.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginRight.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				margintop
						.setEnabled(custom.isSelected() && !margin.isEnabled());
				marginbottom.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginleft.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginright.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				extentend.setEnabled(custom.isSelected());
				extentstart.setEnabled(custom.isSelected());
				extentafter.setEnabled(custom.isSelected());
				extentbefore.setEnabled(custom.isSelected());
				allBody.setEnabled(custom.isSelected());
				allBody.setSelected(custom.isSelected());
				marginBody.setEnabled(custom.isSelected());
				marginTopBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginBottomBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginLeftBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginRightBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				margintopbody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginbottombody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginleftbody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginrightbody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
			}
		});
	}

	JCheckBox custom = new JCheckBox("自定义");
	{
		custom.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				standard.setSelected(!custom.isSelected());
				comboBoxPageType.setEnabled(standard.isSelected());
				pageWidth.setEnabled(custom.isSelected());
				pageHeight.setEnabled(custom.isSelected());
				extentBefore.setEnabled(custom.isSelected());
				extentAfter.setEnabled(custom.isSelected());
				extentStart.setEnabled(custom.isSelected());
				extentEnd.setEnabled(custom.isSelected());
				pageType.setEnabled(standard.isSelected());
				pagewidth.setEnabled(custom.isSelected());
				pageheight.setEnabled(custom.isSelected());
				all.setEnabled(custom.isSelected());
				all.setSelected(custom.isSelected());
				margin.setEnabled(custom.isSelected());
				marginTop
						.setEnabled(custom.isSelected() && !margin.isEnabled());
				marginBottom.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginLeft.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginRight.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				margintop
						.setEnabled(custom.isSelected() && !margin.isEnabled());
				marginbottom.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginleft.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginright.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				extentend.setEnabled(custom.isSelected());
				extentstart.setEnabled(custom.isSelected());
				extentafter.setEnabled(custom.isSelected());
				extentbefore.setEnabled(custom.isSelected());
				allBody.setEnabled(custom.isSelected());
				allBody.setSelected(custom.isSelected());
				marginBody.setEnabled(custom.isSelected());
				marginTopBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginBottomBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginLeftBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginRightBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				margintopbody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginbottombody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginleftbody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginrightbody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
			}
		});
	}

	JCheckBox all = new JCheckBox("缩进同时设置(页)");
	{
		all.setEnabled(custom.isSelected());
		all.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				margin.setEnabled(all.isSelected());
				marginTop.setEnabled(!margin.isEnabled());
				marginBottom.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginLeft.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginRight.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				margintop
						.setEnabled(custom.isSelected() && !margin.isEnabled());
				marginbottom.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginleft.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
				marginright.setEnabled(custom.isSelected()
						&& !margin.isEnabled());
			}
		});
	}

	JCheckBox allBody = new JCheckBox("版心区缩进同时设置");
	{
		allBody.setEnabled(custom.isSelected());
		allBody.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				marginBody.setEnabled(allBody.isSelected());
				marginTopBody.setEnabled(!marginBody.isEnabled());
				marginBottomBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginLeftBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginRightBody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				margintopbody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginbottombody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginleftbody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
				marginrightbody.setEnabled(custom.isSelected()
						&& !marginBody.isEnabled());
			}
		});
	}

	String letter = "Letter(215mm*279mm)";

	String legal = "Legal(215mm*355mm)";

	String A0 = "A0(841mm*1189mm)";

	String A1 = "A1(594mm*841mm)";

	String A2 = "A2(420mm*594mm)";

	String A3 = "A3(457mm*305mm)";

	String A4 = "A4(210mm*297mm)";

	String A5 = "A5(148mm*210mm)";

	String A6 = "A6(105mm*148mm)";

	String B4 = "B4(257mm*365mm)";

	String B5 = "B5(182mm*257mm)";

	String SR = "SR(450mm*320mm)";

	String mu = "默认进纸口";

	String lr_tb = "从左到右从上到下";

	String rl_tb = "从右到左从上到下";

	String tb_rl = "从上到下从右到左";

	String tb_lr = "从上到下从左到右";

	WiseCombobox comboBoxPageType = new WiseCombobox();
	{
		comboBoxPageType.addItem(letter);
		comboBoxPageType.addItem(legal);
		comboBoxPageType.addItem(A0);
		comboBoxPageType.addItem(A1);
		comboBoxPageType.addItem(A2);
		comboBoxPageType.addItem(A3);
		comboBoxPageType.addItem(A4);
		comboBoxPageType.addItem(A5);
		comboBoxPageType.addItem(A6);
		comboBoxPageType.addItem(B4);
		comboBoxPageType.addItem(B5);
		comboBoxPageType.addItem(SR);
		comboBoxPageType.setSelectedItem(A4);
		comboBoxPageType.setEnabled(standard.isSelected());
	}

	WiseTextField pageWidth = new WiseTextField("0.0");
	{
		pageWidth.setEnabled(custom.isSelected());
	}

	WiseTextField pageHeight = new WiseTextField("0.0");
	{
		pageHeight.setEnabled(custom.isSelected());
	}

	WiseTextField margin = new WiseTextField("0.0");
	{
		margin.setEnabled(custom.isSelected());
	}

	WiseTextField marginTop = new WiseTextField("0.0");
	{
		marginTop.setEnabled(custom.isSelected() && !margin.isEnabled());
	}

	WiseTextField marginBottom = new WiseTextField("0.0");
	{
		marginBottom.setEnabled(custom.isSelected() && !margin.isEnabled());
	}

	WiseTextField marginLeft = new WiseTextField("0.0");
	{
		marginLeft.setEnabled(custom.isSelected() && !margin.isEnabled());
	}

	WiseTextField marginRight = new WiseTextField("0.0");
	{
		marginRight.setEnabled(custom.isSelected() && !margin.isEnabled());
	}

	WiseTextField marginBody = new WiseTextField("0.0");
	{
		marginBody.setEnabled(custom.isSelected());
	}

	WiseTextField marginTopBody = new WiseTextField("0.0");
	{
		marginTopBody
				.setEnabled(custom.isSelected() && !marginBody.isEnabled());
	}

	WiseTextField marginBottomBody = new WiseTextField("0.0");
	{
		marginBottomBody.setEnabled(custom.isSelected()
				&& !marginBody.isEnabled());
	}

	WiseTextField marginLeftBody = new WiseTextField("0.0");
	{
		marginLeftBody.setEnabled(custom.isSelected()
				&& !marginBody.isEnabled());
	}

	WiseTextField marginRightBody = new WiseTextField("0.0");
	{
		marginRightBody.setEnabled(custom.isSelected()
				&& !marginBody.isEnabled());
	}

	WiseTextField extentBefore = new WiseTextField("0.0");
	{
		extentBefore.setEnabled(custom.isSelected());
	}

	WiseTextField extentAfter = new WiseTextField("0.0");
	{
		extentAfter.setEnabled(custom.isSelected());
	}

	WiseTextField extentStart = new WiseTextField("0.0");
	{
		extentStart.setEnabled(custom.isSelected());
	}

	WiseTextField extentEnd = new WiseTextField("0.0");
	{
		extentEnd.setEnabled(custom.isSelected());
	}

	WiseTextField columnNumber = new WiseTextField("1");

	WiseTextField columnGrap = new WiseTextField("0.0");

	WiseCombobox comboBoxMediaUsage = new WiseCombobox();
	{
		comboBoxMediaUsage.addItem(mu);
		comboBoxMediaUsage.setSelectedItem(mu);
		comboBoxMediaUsage.setEditable(true);
	}

	WiseCombobox comboBoxWriterMode = new WiseCombobox();
	{
		comboBoxWriterMode.addItem(lr_tb);
		comboBoxWriterMode.addItem(rl_tb);
		comboBoxWriterMode.addItem(tb_rl);
		comboBoxWriterMode.addItem(tb_lr);
		comboBoxWriterMode.setSelectedItem(lr_tb);
	}

	SpinnerNumberModel data = new SpinnerNumberModel(0, -270, 270, 90);

	WiseSpinner pageOrg = new WiseSpinner();
	{
		pageOrg.setModel(data);
	}

	WiseSpinner bodyOrg = new WiseSpinner();
	{
		bodyOrg.setModel(data);
	}

	WiseSpinner beforeOrg = new WiseSpinner();
	{
		beforeOrg.setModel(data);
	}

	WiseSpinner afterOrg = new WiseSpinner();
	{
		afterOrg.setModel(data);
	}

	WiseSpinner startOrg = new WiseSpinner();
	{
		startOrg.setModel(data);
	}

	WiseSpinner endOrg = new WiseSpinner();
	{
		endOrg.setModel(data);
	}

	JLabel pageType = new JLabel("纸张类型");
	{
		pageType.setEnabled(standard.isSelected());
	}

	JLabel pagewidth = new JLabel("页宽");
	{
		pagewidth.setEnabled(custom.isSelected());
	}

	JLabel pageheight = new JLabel("页高");
	{
		pageheight.setEnabled(custom.isSelected());
	}

	JLabel margintop = new JLabel("上缩进(页)");
	{
		margintop.setEnabled(custom.isSelected() && !all.isSelected());
	}

	JLabel marginbottom = new JLabel("下缩进(页)");
	{
		marginbottom.setEnabled(custom.isSelected() && !all.isSelected());
	}

	JLabel marginleft = new JLabel("左缩进(页)");
	{
		marginleft.setEnabled(custom.isSelected() && !all.isSelected());
	}

	JLabel marginright = new JLabel("右缩进(页)");
	{
		marginright.setEnabled(custom.isSelected() && !all.isSelected());
	}

	JLabel margintopbody = new JLabel("上缩进(版心)");
	{
		margintopbody.setEnabled(custom.isSelected() && !allBody.isSelected());
	}

	JLabel marginbottombody = new JLabel("下缩进(版心)");
	{
		marginbottombody.setEnabled(custom.isSelected()
				&& !allBody.isSelected());
	}

	JLabel marginleftbody = new JLabel("左缩进(版心)");
	{
		marginleftbody.setEnabled(custom.isSelected() && !allBody.isSelected());
	}

	JLabel extentend = new JLabel("右区域宽");
	{
		extentend.setEnabled(custom.isSelected());
	}

	JLabel extentstart = new JLabel("左区域宽");
	{
		extentstart.setEnabled(custom.isSelected());
	}

	JLabel extentafter = new JLabel("下区域高");
	{
		extentafter.setEnabled(custom.isSelected());
	}

	JLabel extentbefore = new JLabel("上区域高");
	{
		extentbefore.setEnabled(custom.isSelected());
	}

	JLabel marginrightbody = new JLabel("右缩进(版心)");
	{
		marginrightbody
				.setEnabled(custom.isSelected() && !allBody.isSelected());
	}

	public SimplePageMasterPanel()
	{
		super();
		this.add(standard, new XYConstraints(20, 10, 100, 20));

		this.add(pageType, new XYConstraints(140, 40, 60, 20));
		this.add(comboBoxPageType, new XYConstraints(200, 40, 200, 20));

		custom.setSelected(!standard.isSelected());
		this.add(custom, new XYConstraints(20, 70, 100, 20));

		this.add(pagewidth, new XYConstraints(60, 110, 30, 20));
		this.add(pageWidth, new XYConstraints(90, 110, 80, 20));

		this.add(pageheight, new XYConstraints(220, 110, 30, 20));
		this.add(pageHeight, new XYConstraints(250, 110, 80, 20));

		JPanel marginPanel = new JPanel();
		marginPanel.setSize(490, 260);
		XYLayout marginLayout = new XYLayout();
		marginLayout.setWidth(490);
		marginLayout.setHeight(260);
		marginPanel.setLayout(marginLayout);
		marginPanel.setBackground(getBackground());

		marginPanel.add(all, new XYConstraints(5, 0, 125, 20));
		marginPanel.add(margin, new XYConstraints(130, 0, 120, 20));

		marginPanel.add(margintop, new XYConstraints(25, 30, 65, 20));
		marginPanel.add(marginTop, new XYConstraints(90, 30, 80, 20));

		marginPanel.add(marginbottom, new XYConstraints(185, 30, 65, 20));
		marginPanel.add(marginBottom, new XYConstraints(250, 30, 80, 20));

		marginPanel.add(marginleft, new XYConstraints(25, 60, 65, 20));
		marginPanel.add(marginLeft, new XYConstraints(90, 60, 80, 20));

		marginPanel.add(marginright, new XYConstraints(185, 60, 65, 20));
		marginPanel.add(marginRight, new XYConstraints(250, 60, 80, 20));

		marginPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"缩进(单位：MM)", TitledBorder.CENTER, TitledBorder.TOP));
		this.add(marginPanel, new XYConstraints(60, 140, 350, 120));

		JPanel marginBodyPanel = new JPanel();
		marginBodyPanel.setSize(490, 260);
		XYLayout marginBodyLayout = new XYLayout();
		marginBodyLayout.setWidth(490);
		marginBodyLayout.setHeight(260);
		marginBodyPanel.setLayout(marginBodyLayout);
		marginBodyPanel.setBackground(getBackground());

		marginBodyPanel.add(allBody, new XYConstraints(5, 0, 140, 20));
		marginBodyPanel.add(marginBody, new XYConstraints(145, 0, 120, 20));

		marginBodyPanel.add(margintopbody, new XYConstraints(25, 30, 75, 20));
		marginBodyPanel.add(marginTopBody, new XYConstraints(100, 30, 80, 20));

		marginBodyPanel.add(marginbottombody,
				new XYConstraints(185, 30, 75, 20));
		marginBodyPanel.add(marginBottomBody,
				new XYConstraints(260, 30, 80, 20));

		marginBodyPanel.add(marginleftbody, new XYConstraints(25, 60, 75, 20));
		marginBodyPanel.add(marginLeftBody, new XYConstraints(100, 60, 80, 20));

		marginBodyPanel
				.add(marginrightbody, new XYConstraints(185, 60, 75, 20));
		marginBodyPanel
				.add(marginRightBody, new XYConstraints(260, 60, 80, 20));

		marginBodyPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"版心缩进(单位：MM)", TitledBorder.CENTER, TitledBorder.TOP));
		this.add(marginBodyPanel, new XYConstraints(60, 280, 360, 110));

		JPanel extentPanel = new JPanel();
		extentPanel.setSize(490, 260);
		XYLayout extentLayout = new XYLayout();
		extentLayout.setWidth(490);
		extentLayout.setHeight(260);
		extentPanel.setLayout(extentLayout);
		extentPanel.setBackground(getBackground());

		extentPanel.add(extentbefore, new XYConstraints(5, 0, 55, 20));
		extentPanel.add(extentBefore, new XYConstraints(60, 0, 80, 20));

		extentPanel.add(extentafter, new XYConstraints(160, 0, 55, 20));
		extentPanel.add(extentAfter, new XYConstraints(215, 0, 80, 20));

		extentPanel.add(extentstart, new XYConstraints(5, 30, 55, 20));
		extentPanel.add(extentStart, new XYConstraints(60, 30, 80, 20));

		extentPanel.add(extentend, new XYConstraints(160, 30, 55, 20));
		extentPanel.add(extentEnd, new XYConstraints(215, 30, 80, 20));

		extentPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"区域厚度(单位：MM)", TitledBorder.CENTER, TitledBorder.TOP));
		this.add(extentPanel, new XYConstraints(60, 410, 320, 80));

		this.add(new JLabel("进纸口"), new XYConstraints(60, 520, 55, 20));
		this.add(comboBoxMediaUsage, new XYConstraints(115, 520, 120, 20));

		this.add(new JLabel("排版方向"), new XYConstraints(340, 520, 55, 20));
		this.add(comboBoxWriterMode, new XYConstraints(395, 520, 150, 20));

		this.add(new JLabel("栏数"), new XYConstraints(60, 550, 55, 20));
		this.add(columnNumber, new XYConstraints(115, 550, 120, 20));

		this.add(new JLabel("栏间距"), new XYConstraints(340, 550, 55, 20));
		this.add(columnGrap, new XYConstraints(395, 550, 120, 20));

		JPanel orientationPanel = new JPanel();
		orientationPanel.setSize(490, 260);
		XYLayout orientationLayout = new XYLayout();
		orientationLayout.setWidth(490);
		orientationLayout.setHeight(260);
		orientationPanel.setLayout(orientationLayout);
		orientationPanel.setBackground(getBackground());

		orientationPanel.add(new JLabel("页旋转角度"), new XYConstraints(5, 0, 100,
				20));
		orientationPanel.add(pageOrg, new XYConstraints(105, 0, 75, 20));

		orientationPanel.add(new JLabel("版心区旋转角度"), new XYConstraints(280, 0,
				100, 20));
		orientationPanel.add(bodyOrg, new XYConstraints(380, 0, 75, 20));

		orientationPanel.add(new JLabel("左区域旋转角度"), new XYConstraints(5, 30,
				100, 20));
		orientationPanel.add(startOrg, new XYConstraints(105, 30, 75, 20));

		orientationPanel.add(new JLabel("右区域旋转角度"), new XYConstraints(280, 30,
				100, 20));
		orientationPanel.add(endOrg, new XYConstraints(380, 30, 75, 20));

		orientationPanel.add(new JLabel("上区域旋转角度"), new XYConstraints(5, 60,
				100, 20));
		orientationPanel.add(beforeOrg, new XYConstraints(105, 60, 75, 20));

		orientationPanel.add(new JLabel("下区域旋转角度"), new XYConstraints(280, 60,
				100, 20));
		orientationPanel.add(afterOrg, new XYConstraints(380, 60, 75, 20));

		orientationPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"旋转方向", TitledBorder.CENTER, TitledBorder.TOP));
		this.add(orientationPanel, new XYConstraints(40, 580, 500, 110));
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		Color old = g.getColor();
		g.setColor(Color.GRAY);
		g.drawLine(20, 95, 550, 95);
		g.drawLine(20, 500, 550, 500);
		g.setColor(old);
	}

	public static void main(String[] args)
	{
		JFrame fram = new JFrame();
		fram.setSize(600, 700);
		XYLayout layout = new XYLayout();
		layout.setHeight(700);
		layout.setWidth(600);
		fram.setLayout(layout);
		SimplePageMasterPanel panel = new SimplePageMasterPanel();
		fram.add(panel, new XYConstraints(0, 0, 600, 700));
		fram.setVisible(true);
	}
}
