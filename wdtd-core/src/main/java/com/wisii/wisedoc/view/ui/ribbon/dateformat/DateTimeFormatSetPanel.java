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
/**
 * @DateTimeFormatSetPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.dateformat;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.DATE_TIME_DATE_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.DATE_TIME_SPERATE_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.DATE_TIME_TIME_TITLE;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.attribute.AbstractDateTimeInfo;
import com.wisii.wisedoc.document.attribute.DateInfo;
import com.wisii.wisedoc.document.attribute.DateTimeItem;
import com.wisii.wisedoc.document.attribute.TimeInfo;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DateTimeType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DefaultType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DigitHourType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DigitType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.MonthType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.YearType;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.DateTime;
/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-11-28
 */
public class DateTimeFormatSetPanel extends JPanel
{

	private boolean isoutputset;
	private final static List<Object> INDATEINFOS = new ArrayList<Object>();
	private final static List<Object> OUTDATEINFOS = new ArrayList<Object>();
	private final static List<Object> OUTTIMEIFOS = new ArrayList<Object>();
	private final static List<Object> INTIMEIFOS = new ArrayList<Object>();
	static
	{
		// 2009
		DateTimeItem yearitem1 = new DateTimeItem(DateTimeType.Year,
				DigitType.All.ordinal(), YearType.Arabia, (String) null);
		// 2009-
		DateTimeItem yearitem2 = new DateTimeItem(DateTimeType.Year,
				DigitType.All.ordinal(), YearType.Arabia, "-");
		// 2009年
		DateTimeItem yearitem3 = new DateTimeItem(DateTimeType.Year,
				DigitType.All.ordinal(), YearType.Arabia, "年");
		// 二零零九年
		DateTimeItem yearitem4 = new DateTimeItem(DateTimeType.Year,
				DigitType.All.ordinal(), YearType.China, "年");
		// 贰零零玖年
		DateTimeItem yearitem5 = new DateTimeItem(DateTimeType.Year,
				DigitType.All.ordinal(), YearType.China_Upper, "年");
		// 二〇〇九年
		DateTimeItem yearitem6 = new DateTimeItem(DateTimeType.Year,
				DigitType.All.ordinal(), YearType.China_Zero, "年");
		// 2009
		DateTimeItem yearitem7 = new DateTimeItem(DateTimeType.Year,
				DigitType.All.ordinal(), YearType.Arabia, " ");
		// 二零零九
		DateTimeItem yearitem8 = new DateTimeItem(DateTimeType.Year,
				DigitType.All.ordinal(), YearType.China, " ");
		// 贰零零玖
		DateTimeItem yearitem9 = new DateTimeItem(DateTimeType.Year,
				DigitType.All.ordinal(), YearType.China_Upper, " ");
		// 二〇〇九
		DateTimeItem yearitem10 = new DateTimeItem(DateTimeType.Year,
				DigitType.All.ordinal(), YearType.China_Zero, " ");
		// 06
		DateTimeItem month1 = new DateTimeItem(DateTimeType.Month, 2,
				MonthType.Arabia, (String) null);
		// 06-
		DateTimeItem month2 = new DateTimeItem(DateTimeType.Month, 2,
				MonthType.Arabia, "-");
		// 06月
		DateTimeItem month3 = new DateTimeItem(DateTimeType.Month, 2,
				MonthType.Arabia, "月");
		// 6
		DateTimeItem month4 = new DateTimeItem(DateTimeType.Month, 1,
				MonthType.Arabia, (String) null);
		// 6-
		DateTimeItem month5 = new DateTimeItem(DateTimeType.Month, 1,
				MonthType.Arabia, "-");
		// 6月
		DateTimeItem month6 = new DateTimeItem(DateTimeType.Month, 1,
				MonthType.Arabia, "月");
		// 六月
		DateTimeItem month7 = new DateTimeItem(DateTimeType.Month, 1,
				MonthType.China, "月");
		// 零六月
		DateTimeItem month8 = new DateTimeItem(DateTimeType.Month, 2,
				MonthType.China, "月");
		// 陸月
		DateTimeItem month9 = new DateTimeItem(DateTimeType.Month, 1,
				MonthType.China_Upper, "月");
		// 零陸月
		DateTimeItem month10 = new DateTimeItem(DateTimeType.Month, 2,
				MonthType.China_Upper, "月");
		// 06
		DateTimeItem month11 = new DateTimeItem(DateTimeType.Month, 2,
				MonthType.Arabia, " ");
		// 零六
		DateTimeItem month12 = new DateTimeItem(DateTimeType.Month, 2,
				MonthType.China, " ");
		// 零陸
		DateTimeItem month13 = new DateTimeItem(DateTimeType.Month, 2,
				MonthType.China_Upper, " ");
		// 〇七月
		DateTimeItem month14 = new DateTimeItem(DateTimeType.Month, 2,
				MonthType.China_Zero, "月");
		// 〇七
		DateTimeItem month15 = new DateTimeItem(DateTimeType.Month, 2,
				MonthType.China_Zero, " ");
		// 08
		DateTimeItem day1 = new DateTimeItem(DateTimeType.Day, 2,
				DefaultType.Arabia, (String) null);
		// 08日
		DateTimeItem day2 = new DateTimeItem(DateTimeType.Day, 2,
				DefaultType.Arabia, "日");
		// 8
		DateTimeItem day3 = new DateTimeItem(DateTimeType.Day, 1,
				DefaultType.Arabia, (String) null);
		// 8日
		DateTimeItem day4 = new DateTimeItem(DateTimeType.Day, 1,
				DefaultType.Arabia, "日");
		// 八日
		DateTimeItem day5 = new DateTimeItem(DateTimeType.Day, 1,
				DefaultType.China, "日");
		// 零八
		DateTimeItem day6 = new DateTimeItem(DateTimeType.Day, 2,
				DefaultType.China, (String) null);
		// 零八日
		DateTimeItem day7 = new DateTimeItem(DateTimeType.Day, 2,
				DefaultType.China, "日");
		// 捌日
		DateTimeItem day8 = new DateTimeItem(DateTimeType.Day, 1,
				DefaultType.China_Upper, "日");
		// 零捌日
		DateTimeItem day9 = new DateTimeItem(DateTimeType.Day, 2,
				DefaultType.China_Upper, "日");
		// 06
		DateTimeItem day10 = new DateTimeItem(DateTimeType.Day, 2,
				DefaultType.Arabia, " ");
		// 零六
		DateTimeItem day11 = new DateTimeItem(DateTimeType.Day, 2,
				DefaultType.China, " ");
		// 零陸
		DateTimeItem day12 = new DateTimeItem(DateTimeType.Day, 2,
				DefaultType.China_Upper, " ");
		// 〇六日
		DateTimeItem day13 = new DateTimeItem(DateTimeType.Day, 2,
				DefaultType.China_Zero, "日");
		// 〇六
		DateTimeItem day14 = new DateTimeItem(DateTimeType.Day, 2,
				DefaultType.China_Zero, " ");
		// 13:
		DateTimeItem hour1 = new DateTimeItem(DateTimeType.Hour,
				DigitHourType.Hour24.ordinal(), DefaultType.Arabia, ":");
		// 13时
		DateTimeItem hour2 = new DateTimeItem(DateTimeType.Hour,
				DigitHourType.Hour24.ordinal(), DefaultType.Arabia, "时");
		// 01时
		DateTimeItem hour3 = new DateTimeItem(DateTimeType.Hour,
				DigitHourType.D_Hour24.ordinal(), DefaultType.Arabia, "时");
		// 01:
		DateTimeItem hour4 = new DateTimeItem(DateTimeType.Hour,
				DigitHourType.D_Hour24.ordinal(), DefaultType.Arabia, ":");
		// 01
		DateTimeItem hour5 = new DateTimeItem(DateTimeType.Hour,
				DigitHourType.D_Hour24.ordinal(), DefaultType.Arabia,
				(String) null);
		// 1
		DateTimeItem hour6 = new DateTimeItem(DateTimeType.Hour,
				DigitHourType.Hour24.ordinal(), DefaultType.Arabia,
				(String) null);
		// 3:
		DateTimeItem minute1 = new DateTimeItem(DateTimeType.Minute, 1,
				DefaultType.Arabia, ":");
		// 3分
		DateTimeItem minute2 = new DateTimeItem(DateTimeType.Minute, 1,
				DefaultType.Arabia, "分");
		// 03:
		DateTimeItem minute3 = new DateTimeItem(DateTimeType.Minute, 2,
				DefaultType.Arabia, ":");
		// 03分
		DateTimeItem minute4 = new DateTimeItem(DateTimeType.Minute, 2,
				DefaultType.Arabia, "分");
		// 03
		DateTimeItem minute5 = new DateTimeItem(DateTimeType.Minute, 2,
				DefaultType.Arabia, (String) null);
		// 3
		DateTimeItem minute6 = new DateTimeItem(DateTimeType.Minute, 1,
				DefaultType.Arabia, (String) null);
		// 3
		DateTimeItem second1 = new DateTimeItem(DateTimeType.Second, 1,
				DefaultType.Arabia, (String) null);
		// 3秒
		DateTimeItem second2 = new DateTimeItem(DateTimeType.Second, 1,
				DefaultType.Arabia, "秒");
		// 03
		DateTimeItem second3 = new DateTimeItem(DateTimeType.Second, 2,
				DefaultType.Arabia, (String) null);
		// 03秒
		DateTimeItem second4 = new DateTimeItem(DateTimeType.Second, 2,
				DefaultType.Arabia, "秒");
		List<DateTimeItem> dateitems1 = new ArrayList<DateTimeItem>();
		dateitems1.add(yearitem1);
		dateitems1.add(month1);
		dateitems1.add(day1);
		// 20090201
		DateInfo date1 = DateInfo.Instanceof(dateitems1);
		List<DateTimeItem> dateitems2 = new ArrayList<DateTimeItem>();
		dateitems2.add(yearitem2);
		dateitems2.add(month2);
		dateitems2.add(day1);
		// 2009-02-01
		DateInfo date2 = DateInfo.Instanceof(dateitems2);
		List<DateTimeItem> dateitems3 = new ArrayList<DateTimeItem>();
		dateitems3.add(yearitem3);
		dateitems3.add(month3);
		dateitems3.add(day2);
		// 2009年02月01日
		DateInfo date3 = DateInfo.Instanceof(dateitems3);
		List<DateTimeItem> dateitems4 = new ArrayList<DateTimeItem>();
		dateitems4.add(yearitem1);
		dateitems4.add(month4);
		dateitems4.add(day1);
		// 2009201
		DateInfo date4 = DateInfo.Instanceof(dateitems4);
		List<DateTimeItem> dateitems5 = new ArrayList<DateTimeItem>();
		dateitems5.add(yearitem2);
		dateitems5.add(month5);
		dateitems5.add(day1);
		// 2009-2-01
		DateInfo date5 = DateInfo.Instanceof(dateitems5);
		List<DateTimeItem> dateitems6 = new ArrayList<DateTimeItem>();
		dateitems6.add(yearitem3);
		dateitems6.add(month6);
		dateitems6.add(day2);
		// 2009年2月01日
		DateInfo date6 = DateInfo.Instanceof(dateitems6);
		List<DateTimeItem> dateitems7 = new ArrayList<DateTimeItem>();
		dateitems7.add(yearitem1);
		dateitems7.add(month1);
		dateitems7.add(day3);
		// 2009021
		DateInfo date7 = DateInfo.Instanceof(dateitems7);
		List<DateTimeItem> dateitems8 = new ArrayList<DateTimeItem>();
		dateitems8.add(yearitem2);
		dateitems8.add(month2);
		dateitems8.add(day3);
		// 2009-02-1
		DateInfo date8 = DateInfo.Instanceof(dateitems8);
		List<DateTimeItem> dateitems9 = new ArrayList<DateTimeItem>();
		dateitems9.add(yearitem3);
		dateitems9.add(month3);
		dateitems9.add(day4);
		// 2009年02月1日
		DateInfo date9 = DateInfo.Instanceof(dateitems9);
		INDATEINFOS.add("无");
		INDATEINFOS.add(date1);
		INDATEINFOS.add(date2);
		INDATEINFOS.add(date3);
		INDATEINFOS.add(date4);
		INDATEINFOS.add(date5);
		INDATEINFOS.add(date6);
		INDATEINFOS.add(date7);
		INDATEINFOS.add(date8);
		INDATEINFOS.add(date9);
		// 以上为所有 输入日期格式
		List<DateTimeItem> dateitems10 = new ArrayList<DateTimeItem>();
		dateitems10.add(yearitem4);
		dateitems10.add(month8);
		dateitems10.add(day7);
		// 二零零九年零二月零一日
		DateInfo date10 = DateInfo.Instanceof(dateitems10);
		List<DateTimeItem> dateitems11 = new ArrayList<DateTimeItem>();
		dateitems11.add(yearitem5);
		dateitems11.add(month10);
		dateitems11.add(day9);
		// 貳零零玖年零貳月零壹日
		DateInfo date11 = DateInfo.Instanceof(dateitems11);
		List<DateTimeItem> dateitems12 = new ArrayList<DateTimeItem>();
		dateitems12.add(yearitem6);
		dateitems12.add(month14);
		dateitems12.add(day13);
		// 二〇〇九年〇二月〇一日
		DateInfo date12 = DateInfo.Instanceof(dateitems12);
		List<DateTimeItem> dateitems13 = new ArrayList<DateTimeItem>();
		dateitems13.add(yearitem4);
		dateitems13.add(month7);
		dateitems13.add(day5);
		// 二零零九年二月一日
		DateInfo date13 = DateInfo.Instanceof(dateitems13);
		List<DateTimeItem> dateitems14 = new ArrayList<DateTimeItem>();
		dateitems14.add(yearitem5);
		dateitems14.add(month9);
		dateitems14.add(day8);
		// 貳零零玖年貳月壹日
		DateInfo date14 = DateInfo.Instanceof(dateitems14);
		List<DateTimeItem> dateitems15 = new ArrayList<DateTimeItem>();
		dateitems15.add(yearitem6);
		dateitems15.add(month7);
		dateitems15.add(day5);
		// 二〇〇九年二月一日
		DateInfo date15 = DateInfo.Instanceof(dateitems15);
		List<DateTimeItem> dateitems16 = new ArrayList<DateTimeItem>();
		dateitems16.add(yearitem7);
		dateitems16.add(month11);
		dateitems16.add(day10);
		// 2009 02 01 这种主要是用于套打即底板中有年，月，日标识的情况
		DateInfo date16 = DateInfo.Instanceof(dateitems16);
		List<DateTimeItem> dateitems17 = new ArrayList<DateTimeItem>();
		dateitems17.add(yearitem8);
		dateitems17.add(month12);
		dateitems17.add(day11);
		// 二零零九 零二 零一
		DateInfo date17 = DateInfo.Instanceof(dateitems17);
		List<DateTimeItem> dateitems18 = new ArrayList<DateTimeItem>();
		dateitems18.add(yearitem9);
		dateitems18.add(month13);
		dateitems18.add(day12);
		// 貳零零玖 零貳 零壹
		DateInfo date18 = DateInfo.Instanceof(dateitems18);
		List<DateTimeItem> dateitems19 = new ArrayList<DateTimeItem>();
		dateitems19.add(yearitem10);
		dateitems19.add(month15);
		dateitems19.add(day14);
		// 二〇〇九 〇二 〇一
		DateInfo date19 = DateInfo.Instanceof(dateitems19);
		OUTDATEINFOS.add("无");
		OUTDATEINFOS.add(date1);
		OUTDATEINFOS.add(date2);
		OUTDATEINFOS.add(date3);
		OUTDATEINFOS.add(date4);
		OUTDATEINFOS.add(date5);
		OUTDATEINFOS.add(date6);
		OUTDATEINFOS.add(date7);
		OUTDATEINFOS.add(date8);
		OUTDATEINFOS.add(date9);
		OUTDATEINFOS.add(date10);
		OUTDATEINFOS.add(date11);
		OUTDATEINFOS.add(date12);
		OUTDATEINFOS.add(date13);
		OUTDATEINFOS.add(date14);
		OUTDATEINFOS.add(date15);
		OUTDATEINFOS.add(date16);
		OUTDATEINFOS.add(date17);
		OUTDATEINFOS.add(date18);
		OUTDATEINFOS.add(date19);
		// 以上为所有日期的格式
		List<DateTimeItem> timeitems4 = new ArrayList<DateTimeItem>();
		timeitems4.add(hour4);
		timeitems4.add(minute3);
		timeitems4.add(second3);
		// 01:01:01
		TimeInfo time4 = TimeInfo.Instanceof(timeitems4);
		List<DateTimeItem> timeitems5 = new ArrayList<DateTimeItem>();
		timeitems5.add(hour3);
		timeitems5.add(minute4);
		timeitems5.add(second4);
		// 01时01分01秒
		TimeInfo time5 = TimeInfo.Instanceof(timeitems5);
		List<DateTimeItem> timeitems6 = new ArrayList<DateTimeItem>();
		timeitems6.add(hour5);
		timeitems6.add(minute5);
		timeitems6.add(second3);
		// 010101
		TimeInfo time6 = TimeInfo.Instanceof(timeitems6);
		INTIMEIFOS.add("无");
		INTIMEIFOS.add(time4);
		INTIMEIFOS.add(time5);
		INTIMEIFOS.add(time6);
		List<DateTimeItem> timeitems1 = new ArrayList<DateTimeItem>();
		timeitems1.add(hour1);
		timeitems1.add(minute3);
		timeitems1.add(second3);
		// 1:01:01
		TimeInfo time1 = TimeInfo.Instanceof(timeitems1);
		List<DateTimeItem> timeitems2 = new ArrayList<DateTimeItem>();
		timeitems2.add(hour2);
		timeitems2.add(minute4);
		timeitems2.add(second4);
		// 1时01分01秒
		TimeInfo time2 = TimeInfo.Instanceof(timeitems2);
		List<DateTimeItem> timeitems3 = new ArrayList<DateTimeItem>();
		timeitems3.add(hour6);
		timeitems3.add(minute5);
		timeitems3.add(second3);
		// 10101
		TimeInfo time3 = TimeInfo.Instanceof(timeitems3);
		List<DateTimeItem> timeitems7 = new ArrayList<DateTimeItem>();
		timeitems7.add(hour1);
		timeitems7.add(minute1);
		timeitems7.add(second1);
		// 1:1:1
		TimeInfo time7 = TimeInfo.Instanceof(timeitems7);
		List<DateTimeItem> timeitems8 = new ArrayList<DateTimeItem>();
		timeitems8.add(hour2);
		timeitems8.add(minute2);
		timeitems8.add(second2);
		// 1时1分1秒
		TimeInfo time8 = TimeInfo.Instanceof(timeitems8);
		List<DateTimeItem> timeitems9 = new ArrayList<DateTimeItem>();
		timeitems9.add(hour1);
		timeitems9.add(minute6);
		// 8:48
		TimeInfo time9 = TimeInfo.Instanceof(timeitems9);
		List<DateTimeItem> timeitems10 = new ArrayList<DateTimeItem>();
		timeitems10.add(hour2);
		timeitems10.add(minute2);
		// 8时48分
		TimeInfo time10 = TimeInfo.Instanceof(timeitems10);
		OUTTIMEIFOS.addAll(INTIMEIFOS);
		OUTTIMEIFOS.add(time1);
		OUTTIMEIFOS.add(time2);
		OUTTIMEIFOS.add(time3);
		OUTTIMEIFOS.add(time7);
		OUTTIMEIFOS.add(time8);
		OUTTIMEIFOS.add(time9);
		OUTTIMEIFOS.add(time10);
	}
	private EditPanel datepanel;
	private EditPanel timepanel;
	private WiseTextField speratetext;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public DateTimeFormatSetPanel(boolean isoutputset)
	{
		this.isoutputset = isoutputset;
		inintpanel();
	}

	void addformateinfo(WisedocDateTimeFormat newformat)
	{
		if (isoutputset)
		{
			datepanel.addItem(newformat.getDateout());
			timepanel.addItem(newformat.getTimeout());
			speratetext.setText(newformat.getOutseperate());
		} else
		{
			datepanel.addItem(newformat.getDatein());
			timepanel.addItem(newformat.getTimein());
			speratetext.setText(newformat.getInputseperate());
		}
	}

	private void inintpanel()
	{
		setLayout(new FlowLayout());
		datepanel = inintDateSetpanel();
		JPanel speratesetpanel = inintSperateSetpanel();
		timepanel = inintTimeSetpanel();
		add(datepanel);
		add(speratesetpanel);
		add(timepanel);
		// setBorder(new TitledBorder("输入设置"));
	}

	private EditPanel inintDateSetpanel()
	{
		EditPanel panel = new EditPanel();
		if (isoutputset)
		{
			panel.setItems(OUTDATEINFOS.toArray(), true);
		} else
		{
			panel.setItems(INDATEINFOS.toArray(), true);
		}
		panel.setBorder(new TitledBorder(DATE_TIME_DATE_TITLE));
		return panel;
	}

	private JPanel inintSperateSetpanel()
	{
		JPanel panel = new JPanel();
		speratetext = new WiseTextField();
		if (isoutputset)
		{
			RibbonUIManager.getInstance().bind(
					DateTime.OUTPUT_SEPEAATE_SET_ACTION, speratetext);
		} else
		{
			RibbonUIManager.getInstance().bind(
					DateTime.INPUT_SEPEAATE_SET_ACTION, speratetext);
		}
		panel.add(speratetext);
		speratetext.setPreferredSize(new Dimension(40, 22));
		panel.setBorder(new TitledBorder(DATE_TIME_SPERATE_TITLE));
		return panel;
	}

	private EditPanel inintTimeSetpanel()
	{
		EditPanel panel = new EditPanel();
		if (isoutputset)
		{
			panel.setItems(OUTTIMEIFOS.toArray(), false);
		} else
		{
			panel.setItems(INTIMEIFOS.toArray(), false);
		}
		panel.setBorder(new TitledBorder(DATE_TIME_TIME_TITLE));
		return panel;
	}

	private class EditPanel extends JPanel
	{
		private WiseCombobox combox;
		private boolean editable = true;

		private EditPanel()
		{
			init();
		}

		void addItem(AbstractDateTimeInfo info)
		{
			if (info != null
					&& ((DefaultComboBoxModel) combox.getModel())
							.getIndexOf(info) < 0)
			{
				combox.addItem(info);
				combox.InitValue(info);
			}
		}

		private void init()
		{
			setLayout(new FlowLayout());
			combox = new WiseCombobox();
			add(combox);
		}

		void setItems(Object[] items, boolean isdate)
		{
			combox.setModel(new DefaultComboBoxModel(items));
			if (isoutputset)
			{
				if (isdate)
				{
					RibbonUIManager.getInstance().bind(
							DateTime.DATE_OUTPUT_SET_ACTION, combox);
				} else
				{
					RibbonUIManager.getInstance().bind(
							DateTime.TIME_OUTPUT_SET_ACTION, combox);
				}

			} else
			{
				if (isdate)
				{
					RibbonUIManager.getInstance().bind(
							DateTime.DATE_INPUT_SET_ACTION, combox);
				} else
				{
					RibbonUIManager.getInstance().bind(
							DateTime.TIME_INPUT_SET_ACTION, combox);
				}
			}
		}
	}
}
