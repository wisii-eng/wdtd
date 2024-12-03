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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;

@SuppressWarnings("serial")
public class DatetimePanel extends OnlyLayoutPanel
{

	JCheckBox dateFormat = new JCheckBox("日期格式设置", true);
	{
		dateFormat.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				dateinput.setEnabled(dateFormat.isSelected());
				dateInputData.setEnabled(dateFormat.isSelected());
				// text.setEnabled(dateFormat.isSelected()
				// && timeFormat.isSelected());
				// date_time.setEnabled(dateFormat.isSelected()
				// && timeFormat.isSelected());
				dateInputWrite.setEnabled(dateFormat.isSelected());
				dateinputwrite.setEnabled(dateFormat.isSelected());
				dateInputCheck.setEnabled(dateFormat.isSelected());
				dateinputcheck.setEnabled(dateFormat.isSelected());
			}
		});
	}

	JCheckBox timeFormat = new JCheckBox("时间格式设置", false);
	{
		timeFormat.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				// text.setEnabled(dateFormat.isSelected()
				// && timeFormat.isSelected());
				// date_time.setEnabled(dateFormat.isSelected()
				// && timeFormat.isSelected());
				timeinput.setEnabled(timeFormat.isSelected());
				timeInputData.setEnabled(timeFormat.isSelected());
				timeInputWrite.setEnabled(timeFormat.isSelected());
				timeinputwrite.setEnabled(timeFormat.isSelected());
				timeInputCheck.setEnabled(timeFormat.isSelected());
				timeinputcheck.setEnabled(timeFormat.isSelected());

			}
		});
	}

	JLabel text = new JLabel("日期时间分隔符");

	// {
	// text.setEnabled(dateFormat.isSelected() && timeFormat.isSelected());
	// }

	WiseTextField date_time = new WiseTextField("T");

	// {
	// date_time
	// .setEnabled(dateFormat.isSelected() && timeFormat.isSelected());
	// }

	JLabel dateinput = new JLabel("输入格式");
	{
		dateinput.setEnabled(dateFormat.isSelected());
	}

	WiseTextField dateInputData = new WiseTextField("YYYYMMDD");
	{
		dateInputData.setEnabled(dateFormat.isSelected());
	}

	JLabel timeinput = new JLabel("输入格式");
	{
		timeinput.setEnabled(timeFormat.isSelected());
	}

	WiseTextField timeInputData = new WiseTextField("hh:mm:ss");
	{
		timeInputData.setEnabled(timeFormat.isSelected());
	}

	JLabel dateInputWrite = new JLabel("手动输入");
	{
		dateInputWrite.setEnabled(dateFormat.isSelected());
	}

	WiseTextField dateinputwrite = new WiseTextField("");
	{
		dateinputwrite.setEnabled(dateFormat.isSelected());
	}

	JLabel dateInputCheck = new JLabel("从列表项选择");
	{
		dateInputCheck.setEnabled(dateFormat.isSelected());
	}

	String yyyymmdd = "20080101";

	String yyyymdd = "2008101";

	String yyyymmd = "2008011";

	String yymmdd = "080101";

	String yymdd = "08101";

	String yymmd = "2008011";

	String yyyymmddz = "2008年01月01日";

	String yyyymddz = "2008年1月01日";

	String yyyymmdz = "2008年01月1日";

	String yyyymmddf = "2008-01-01";

	String yyyymddf = "2008-1-01";

	String yyyymmdf = "2008-01-1";

	WiseCombobox dateinputcheck = new WiseCombobox();
	{
		dateinputcheck.addItem(yyyymmdd);
		dateinputcheck.addItem(yyyymdd);
		dateinputcheck.addItem(yyyymmd);
		dateinputcheck.addItem(yymmdd);
		dateinputcheck.addItem(yymdd);
		dateinputcheck.addItem(yymmd);
		dateinputcheck.addItem(yyyymmddz);
		dateinputcheck.addItem(yyyymddz);
		dateinputcheck.addItem(yyyymmdz);
		dateinputcheck.addItem(yyyymmddf);
		dateinputcheck.addItem(yyyymddf);
		dateinputcheck.addItem(yyyymmdf);
		dateinputcheck.setSelectedItem(yyyymmdd);
		dateinputcheck.setEnabled(dateFormat.isSelected());
	}

	JLabel timeInputWrite = new JLabel("手动输入");
	{
		timeInputWrite.setEnabled(timeFormat.isSelected());
	}

	WiseTextField timeinputwrite = new WiseTextField("");
	{
		timeinputwrite.setEnabled(timeFormat.isSelected());
	}

	JLabel timeInputCheck = new JLabel("从列表项选择");
	{
		timeInputCheck.setEnabled(timeFormat.isSelected());
	}

	WiseCombobox timeinputcheck = new WiseCombobox();
	{
		timeinputcheck.addItem(yyyymmdd);
		timeinputcheck.addItem(yyyymdd);
		timeinputcheck.addItem(yyyymmd);
		timeinputcheck.addItem(yymmdd);
		timeinputcheck.addItem(yymdd);
		timeinputcheck.addItem(yymmd);
		timeinputcheck.addItem(yyyymmddz);
		timeinputcheck.addItem(yyyymddz);
		timeinputcheck.addItem(yyyymmdz);
		timeinputcheck.addItem(yyyymmddf);
		timeinputcheck.addItem(yyyymddf);
		timeinputcheck.addItem(yyyymmdf);
		timeinputcheck.setSelectedItem(yyyymmdd);
		timeinputcheck.setEnabled(timeFormat.isSelected());
	}

	JCheckBox dateFormatOutput = new JCheckBox("日期格式设置", true);
	{
		dateFormatOutput.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				// textOutput.setEnabled(dateFormatOutput.isSelected()
				// && timeFormatOutput.isSelected());
				// date_timeOutput.setEnabled(dateFormatOutput.isSelected()
				// && timeFormatOutput.isSelected());
				dateoutput.setEnabled(dateFormatOutput.isSelected());
				dateOutputData.setEnabled(dateFormatOutput.isSelected());
				dateOutputWrite.setEnabled(dateFormatOutput.isSelected());
				dateoutputwrite.setEnabled(dateFormatOutput.isSelected());
				dateOutputCheck.setEnabled(dateFormatOutput.isSelected());
				dateoutputcheck.setEnabled(dateFormatOutput.isSelected());
				year.setEnabled(dateFormatOutput.isSelected());
				styleYear.setEnabled(dateFormatOutput.isSelected());
				month.setEnabled(dateFormatOutput.isSelected());
				styleMonth.setEnabled(dateFormatOutput.isSelected());
				day.setEnabled(dateFormatOutput.isSelected());
				styleDay.setEnabled(dateFormatOutput.isSelected());
			}
		});
	}

	JCheckBox timeFormatOutput = new JCheckBox("时间格式设置", false);
	{
		timeFormatOutput.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				// textOutput.setEnabled(dateFormatOutput.isSelected()
				// && timeFormatOutput.isSelected());
				// date_timeOutput.setEnabled(dateFormatOutput.isSelected()
				// && timeFormatOutput.isSelected());
				timeoutput.setEnabled(timeFormatOutput.isSelected());
				timeOutputData.setEnabled(timeFormatOutput.isSelected());
				timeOutputWrite.setEnabled(timeFormatOutput.isSelected());
				timeoutputwrite.setEnabled(timeFormatOutput.isSelected());
				timeOutputCheck.setEnabled(timeFormatOutput.isSelected());
				timeoutputcheck.setEnabled(timeFormatOutput.isSelected());
				hours.setEnabled(timeFormatOutput.isSelected());
				styleHours.setEnabled(timeFormatOutput.isSelected());
				minutes.setEnabled(timeFormatOutput.isSelected());
				styleMinutes.setEnabled(timeFormatOutput.isSelected());
				seconds.setEnabled(timeFormatOutput.isSelected());
				styleSeconds.setEnabled(timeFormatOutput.isSelected());
			}
		});
	}

	JLabel textOutput = new JLabel("日期时间分隔符");

	// {
	// textOutput.setEnabled(dateFormatOutput.isSelected()
	// && timeFormatOutput.isSelected());
	// }

	WiseTextField date_timeOutput = new WiseTextField("");

	// {
	// date_timeOutput.setEnabled(dateFormatOutput.isSelected()
	// && timeFormatOutput.isSelected());
	// }

	JLabel dateoutput = new JLabel("输出格式");
	{
		dateoutput.setEnabled(dateFormatOutput.isSelected());
	}

	WiseTextField dateOutputData = new WiseTextField("YYYYMMDD");
	{
		dateOutputData.setEnabled(dateFormatOutput.isSelected());
	}

	JLabel timeoutput = new JLabel("输出格式");
	{
		timeoutput.setEnabled(timeFormatOutput.isSelected());
	}

	WiseTextField timeOutputData = new WiseTextField("hh:mm:ss");
	{
		timeOutputData.setEnabled(timeFormatOutput.isSelected());
	}

	JLabel dateOutputWrite = new JLabel("手动输入");
	{
		dateOutputWrite.setEnabled(dateFormatOutput.isSelected());
	}

	WiseTextField dateoutputwrite = new WiseTextField("");
	{
		dateoutputwrite.setEnabled(dateFormatOutput.isSelected());
	}

	JLabel dateOutputCheck = new JLabel("从列表项选择");
	{
		dateOutputCheck.setEnabled(dateFormatOutput.isSelected());
	}

	WiseCombobox dateoutputcheck = new WiseCombobox();
	{
		dateoutputcheck.addItem(yyyymmdd);
		dateoutputcheck.addItem(yyyymdd);
		dateoutputcheck.addItem(yyyymmd);
		dateoutputcheck.addItem(yymmdd);
		dateoutputcheck.addItem(yymdd);
		dateoutputcheck.addItem(yymmd);
		dateoutputcheck.addItem(yyyymmddz);
		dateoutputcheck.addItem(yyyymddz);
		dateoutputcheck.addItem(yyyymmdz);
		dateoutputcheck.addItem(yyyymmddf);
		dateoutputcheck.addItem(yyyymddf);
		dateoutputcheck.addItem(yyyymmdf);
		dateoutputcheck.setSelectedItem(yyyymmdd);
		dateoutputcheck.setEnabled(dateFormatOutput.isSelected());
	}

	JLabel timeOutputWrite = new JLabel("手动输入");
	{
		timeOutputWrite.setEnabled(timeFormatOutput.isSelected());
	}

	WiseTextField timeoutputwrite = new WiseTextField("");
	{
		timeoutputwrite.setEnabled(timeFormatOutput.isSelected());
	}

	JLabel timeOutputCheck = new JLabel("从列表项选择");
	{
		timeOutputCheck.setEnabled(timeFormatOutput.isSelected());
	}

	WiseCombobox timeoutputcheck = new WiseCombobox();
	{
		timeoutputcheck.addItem(yyyymmdd);
		timeoutputcheck.addItem(yyyymdd);
		timeoutputcheck.addItem(yyyymmd);
		timeoutputcheck.addItem(yymmdd);
		timeoutputcheck.addItem(yymdd);
		timeoutputcheck.addItem(yymmd);
		timeoutputcheck.addItem(yyyymmddz);
		timeoutputcheck.addItem(yyyymddz);
		timeoutputcheck.addItem(yyyymmdz);
		timeoutputcheck.addItem(yyyymmddf);
		timeoutputcheck.addItem(yyyymddf);
		timeoutputcheck.addItem(yyyymmdf);
		timeoutputcheck.setSelectedItem(yyyymmdd);
		timeoutputcheck.setEnabled(timeFormatOutput.isSelected());
	}

	String albo = "阿拉伯数字";

	String chinese1 = "○一二三型中文";

	String chinese2 = "零一二三型中文";

	String chinese3 = "零壹贰叁型中文";

	String english1 = "英文小写";

	String english2 = "英文首字母大写";

	JLabel year = new JLabel("年输出样式");
	{
		year.setEnabled(dateFormatOutput.isSelected());
	}

	WiseCombobox styleYear = new WiseCombobox();
	{
		styleYear.addItem(albo);
		styleYear.addItem(chinese1);
		styleYear.addItem(chinese2);
		styleYear.addItem(chinese3);
		// styleYear.addItem(english1);
		// styleYear.addItem(english2);
		styleYear.setSelectedItem(albo);
		styleYear.setEnabled(dateFormatOutput.isSelected());
	}

	JLabel month = new JLabel("月输出样式");
	{
		month.setEnabled(dateFormatOutput.isSelected());
	}

	WiseCombobox styleMonth = new WiseCombobox();
	{
		styleMonth.addItem(albo);
		styleMonth.addItem(chinese1);
		styleMonth.addItem(chinese2);
		styleMonth.addItem(chinese3);
		styleMonth.addItem(english1);
		styleMonth.addItem(english2);
		styleMonth.setSelectedItem(albo);
		styleMonth.setEnabled(dateFormatOutput.isSelected());
	}

	JLabel day = new JLabel("日输出样式");
	{
		day.setEnabled(dateFormatOutput.isSelected());
	}

	WiseCombobox styleDay = new WiseCombobox();
	{
		styleDay.addItem(albo);
		styleDay.addItem(chinese1);
		styleDay.addItem(chinese2);
		styleDay.addItem(chinese3);
		// styleDay.addItem(english1);
		// styleDay.addItem(english2);
		styleDay.setSelectedItem(albo);
		styleDay.setEnabled(dateFormatOutput.isSelected());
	}

	JLabel hours = new JLabel("时输出样式");
	{
		hours.setEnabled(timeFormatOutput.isSelected());
	}

	WiseCombobox styleHours = new WiseCombobox();
	{
		styleHours.addItem(albo);
		styleHours.addItem(chinese1);
		styleHours.addItem(chinese2);
		styleHours.addItem(chinese3);
		// styleHours.addItem(english1);
		// styleHours.addItem(english2);
		styleHours.setSelectedItem(albo);
		styleHours.setEnabled(timeFormatOutput.isSelected());
	}

	JLabel minutes = new JLabel("分输出样式");
	{
		minutes.setEnabled(timeFormatOutput.isSelected());
	}

	WiseCombobox styleMinutes = new WiseCombobox();
	{
		styleMinutes.addItem(albo);
		styleMinutes.addItem(chinese1);
		styleMinutes.addItem(chinese2);
		styleMinutes.addItem(chinese3);
		// styleMinutes.addItem(english1);
		// styleMinutes.addItem(english2);
		styleMinutes.setSelectedItem(albo);
		styleMinutes.setEnabled(timeFormatOutput.isSelected());

	}

	JLabel seconds = new JLabel("秒输出样式");
	{
		seconds.setEnabled(timeFormatOutput.isSelected());
	}

	WiseCombobox styleSeconds = new WiseCombobox();
	{
		styleSeconds.addItem(albo);
		styleSeconds.addItem(chinese1);
		styleSeconds.addItem(chinese2);
		styleSeconds.addItem(chinese3);
		// styleSeconds.addItem(english1);
		// styleSeconds.addItem(english2);
		styleSeconds.setSelectedItem(albo);
		styleSeconds.setEnabled(timeFormatOutput.isSelected());
	}

	public DatetimePanel()
	{
		super();

		JPanel inputFormatPanel = new JPanel();
		inputFormatPanel.setSize(490, 260);
		XYLayout inputFormatLayout = new XYLayout();
		inputFormatLayout.setWidth(490);
		inputFormatLayout.setHeight(260);
		inputFormatPanel.setLayout(inputFormatLayout);
		inputFormatPanel.setBackground(getBackground());
		inputFormatPanel.add(dateFormat, new XYConstraints(45, 0, 120, 20));
		inputFormatPanel.add(text, new XYConstraints(155, 0, 100, 20));
		inputFormatPanel.add(date_time, new XYConstraints(250, 0, 80, 20));
		inputFormatPanel.add(timeFormat, new XYConstraints(335, 0, 120, 20));

		inputFormatPanel.add(dateinput, new XYConstraints(5, 40, 80, 20));
		inputFormatPanel.add(dateInputData, new XYConstraints(95, 40, 100, 20));

		inputFormatPanel.add(dateInputWrite, new XYConstraints(5, 70, 80, 20));
		inputFormatPanel
				.add(dateinputwrite, new XYConstraints(95, 70, 100, 20));

		inputFormatPanel.add(dateInputCheck, new XYConstraints(5, 100, 80, 20));
		inputFormatPanel.add(dateinputcheck,
				new XYConstraints(95, 100, 100, 20));

		inputFormatPanel.add(timeinput, new XYConstraints(250, 40, 80, 20));
		inputFormatPanel
				.add(timeInputData, new XYConstraints(330, 40, 100, 20));

		inputFormatPanel
				.add(timeInputWrite, new XYConstraints(250, 70, 80, 20));
		inputFormatPanel.add(timeinputwrite,
				new XYConstraints(330, 70, 100, 20));

		inputFormatPanel.add(timeInputCheck,
				new XYConstraints(250, 100, 80, 20));
		inputFormatPanel.add(timeinputcheck, new XYConstraints(330, 100, 100,
				20));

		inputFormatPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"输入格式设置", TitledBorder.CENTER, TitledBorder.TOP));
		this.add(inputFormatPanel, new XYConstraints(55, 50, 490, 160));

		JPanel outputFormatPanel = new JPanel();
		outputFormatPanel.setSize(490, 260);
		XYLayout outputFormatLayout = new XYLayout();
		outputFormatLayout.setWidth(490);
		outputFormatLayout.setHeight(260);
		outputFormatPanel.setLayout(outputFormatLayout);
		outputFormatPanel.setBackground(getBackground());

		outputFormatPanel.add(dateFormatOutput, new XYConstraints(35, 0, 120,
				20));
		outputFormatPanel.add(textOutput, new XYConstraints(175, 0, 100, 20));
		outputFormatPanel.add(date_timeOutput,
				new XYConstraints(265, 0, 80, 20));
		outputFormatPanel.add(timeFormatOutput, new XYConstraints(355, 0, 120,
				20));

		outputFormatPanel.add(dateoutput, new XYConstraints(5, 40, 80, 20));
		outputFormatPanel.add(dateOutputData,
				new XYConstraints(95, 40, 100, 20));

		outputFormatPanel
				.add(dateOutputWrite, new XYConstraints(5, 70, 80, 20));
		outputFormatPanel.add(dateoutputwrite, new XYConstraints(95, 70, 100,
				20));

		outputFormatPanel.add(dateOutputCheck,
				new XYConstraints(5, 100, 80, 20));
		outputFormatPanel.add(dateoutputcheck, new XYConstraints(95, 100, 100,
				20));

		outputFormatPanel.add(timeoutput, new XYConstraints(250, 40, 80, 20));
		outputFormatPanel.add(timeOutputData, new XYConstraints(330, 40, 100,
				20));

		outputFormatPanel.add(timeOutputWrite, new XYConstraints(250, 70, 80,
				20));
		outputFormatPanel.add(timeoutputwrite, new XYConstraints(330, 70, 100,
				20));

		outputFormatPanel.add(timeOutputCheck, new XYConstraints(250, 100, 80,
				20));
		outputFormatPanel.add(timeoutputcheck, new XYConstraints(330, 100, 100,
				20));

		outputFormatPanel.setBorder(new TitledBorder(
				new LineBorder(Color.GRAY), "输出格式设置", TitledBorder.CENTER,
				TitledBorder.TOP));
		this.add(outputFormatPanel, new XYConstraints(55, 240, 490, 155));

		JPanel outStylePanel = new JPanel();
		outStylePanel.setSize(490, 260);
		XYLayout outStyleLayout = new XYLayout();
		outStyleLayout.setWidth(490);
		outStyleLayout.setHeight(260);
		outStylePanel.setLayout(outStyleLayout);
		outStylePanel.setBackground(getBackground());

		outStylePanel.add(year, new XYConstraints(5, 0, 80, 20));
		outStylePanel.add(styleYear, new XYConstraints(90, 0, 100, 20));

		outStylePanel.add(month, new XYConstraints(5, 40, 80, 20));
		outStylePanel.add(styleMonth, new XYConstraints(90, 40, 100, 20));

		outStylePanel.add(day, new XYConstraints(5, 80, 80, 20));
		outStylePanel.add(styleDay, new XYConstraints(90, 80, 100, 20));

		outStylePanel.add(hours, new XYConstraints(250, 0, 80, 20));
		outStylePanel.add(styleHours, new XYConstraints(330, 0, 100, 20));

		outStylePanel.add(minutes, new XYConstraints(250, 40, 80, 20));
		outStylePanel.add(styleMinutes, new XYConstraints(330, 40, 100, 20));

		outStylePanel.add(seconds, new XYConstraints(250, 80, 80, 20));
		outStylePanel.add(styleSeconds, new XYConstraints(330, 80, 100, 20));
		outStylePanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				"输出样式设置", TitledBorder.CENTER, TitledBorder.TOP));
		this.add(outStylePanel, new XYConstraints(55, 420, 490, 140));
	}

	public static void main(String[] args)
	{
		DatetimePanel panel = new DatetimePanel();
		panel.setVisible(true);

	}

	public void paint(Graphics g)
	{
		super.paint(g);
		Color old = g.getColor();
		g.setColor(Color.GRAY);
		g.drawLine(300, 90, 300, 180);
		g.drawLine(300, 300, 300, 390);
		g.drawLine(300, 450, 300, 550);
		g.setColor(old);
	}
}
