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
 * 
 */
package com.wisii.wisedoc.view.ui.ribbon.dateformat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.wisii.wisedoc.document.attribute.AbstractDateTimeInfo;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
/**
 * 
 * 类功能描述：日期时间自定义格式设置界面
 *
 * 作者：zhangqiang
 * 创建日期：2009-6-26
 */
public class DateTimeSetPanel extends JPanel
{
	private WisedocDateTimeFormat oldformat;
	private boolean isinput;

	/**
	 * 
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public DateTimeSetPanel(WisedocDateTimeFormat format, boolean isinout)
	{
		oldformat = format;
		this.isinput = isinout;
		initComponents();
	}

	/**
	 * 
	 * 初始化窗体
	 * 
	 * @param
	 * @return void: {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private void initComponents()
	{

		buttonGroup1 = new ButtonGroup();
		AbstractDateTimeInfo dateinfo = null;
		AbstractDateTimeInfo timeinfo = null;
		boolean isdatetableenable = true;
		boolean istimetableenable = true;
		boolean isseperateenable=  true;
		if (oldformat != null)
		{
			if (isinput)
			{
				dateinfo = oldformat.getDatein();
				timeinfo = oldformat.getTimein();
				String seperate = oldformat.getInputseperate();
				if (seperate != null)
				{
					sepertext.setText(seperate);
				}
				boolean isdatefirst = oldformat.isIndatefirst();
				datefirstradiobutton.setSelected(isdatefirst);
				timefirstradiobutton.setSelected(!isdatefirst);
			} else
			{
				dateinfo = oldformat.getDateout();
				timeinfo = oldformat.getTimeout();
				String seperate = oldformat.getOutseperate();
				if (seperate != null)
				{
					sepertext.setText(seperate);
				}
				boolean isdatefirst = oldformat.isOutdatefirst();
				datefirstradiobutton.setSelected(isdatefirst);
				timefirstradiobutton.setSelected(!isdatefirst);
				isdatetableenable = oldformat.getDatein()!=null;
				istimetableenable = oldformat.getTimein()!=null;
				isseperateenable = isdatetableenable&&istimetableenable;
			}

		}
		datetable = new DateTable(dateinfo);
		timescrollpane = new JScrollPane();
		timetable = new TimeTable(timeinfo);
		setLayout(new BorderLayout());
		datetable.setEnabled(isdatetableenable);
		datedownbutton.setEnabled(istimetableenable);
		dateupperbutton.setEnabled(istimetableenable);
		timetable.setEnabled(istimetableenable);
		timeupperbutton.setEnabled(istimetableenable);
		timedownbutton.setEnabled(istimetableenable);
		sepertext.setEnabled(isseperateenable);
		datefirstradiobutton.setEnabled(isseperateenable);
		timefirstradiobutton.setEnabled(isseperateenable);
		mainpanel.setLayout(new java.awt.GridLayout(2, 0));
		

		datesetpanel.setBorder(BorderFactory.createTitledBorder("日期设置")); // NOI18N
		datesetpanel.setLayout(new BorderLayout());

		datesetscroolpanel.setViewportView(datetable);

		datesetpanel.add(datesetscroolpanel, BorderLayout.CENTER);

		dateactionpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 6));

		dateactionpanel.add(dateupperbutton);
		dateactionpanel.add(datedownbutton);

		datesetpanel.add(dateactionpanel, BorderLayout.SOUTH);

		mainpanel.add(datesetpanel);

		timesetpanel.setBorder(BorderFactory.createTitledBorder("时间设置"));
		timesetpanel.setLayout(new BorderLayout());

		timeactionpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 6));

		timeactionpanel.add(timeupperbutton);
		timeactionpanel.add(timedownbutton);
		timesetpanel.add(timeactionpanel, BorderLayout.SOUTH);
		timescrollpane.setViewportView(timetable);
		timesetpanel.add(timescrollpane, BorderLayout.CENTER);
		mainpanel.add(timesetpanel);
		add(mainpanel, BorderLayout.CENTER);
		othersetpanel.setLayout(new GridLayout(2, 1));
		JPanel seperpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
		// othersetpanel.add(datetimetype);
		sepertext.setPreferredSize(new Dimension(40, 22));
		seperpanel.add(seperlabel);
		seperpanel.add(sepertext);
		buttonGroup1.add(datefirstradiobutton);
		buttonGroup1.add(timefirstradiobutton);
		JPanel orderpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
		orderpanel.add(datefirstradiobutton);
		orderpanel.add(timefirstradiobutton);
		othersetpanel.add(seperpanel);
		othersetpanel.add(orderpanel);
		add(othersetpanel, BorderLayout.PAGE_START);
		initAction();
		dateupperbutton.setEnabled(false);
		datedownbutton.setEnabled(false);
		timeupperbutton.setEnabled(false);
		timeupperbutton.setEnabled(false);
	}

	private void initAction()
	{
		datetable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener()
				{

					@Override
					public void valueChanged(ListSelectionEvent e)
					{
						updateDateMoveButton();

					}
				});
		timetable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener()
				{

					@Override
					public void valueChanged(ListSelectionEvent e)
					{
						updateTimeMoveButton();

					}
				});
		dateupperbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				datetable.upSelectItem();

			}
		});
		datedownbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				datetable.downSelectItem();

			}
		});
		timeupperbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				timetable.upSelectItem();

			}
		});
		timedownbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				timetable.downSelectItem();

			}
		});

	}

	public void stopEdit()
	{
		if (datetable.getCellEditor() != null)
		{
			datetable.getCellEditor().stopCellEditing();
		}
		if (timetable.getCellEditor() != null)
		{
			timetable.getCellEditor().stopCellEditing();
		}
	}

	public WisedocDateTimeFormat getDateTimeFormat()
	{
		String seperate = null;
		String text = sepertext.getText();
		if (text != null && !text.equals(""))
		{
			seperate = text;
		}
		boolean isdatefirst = true;
		if (timefirstradiobutton.isSelected())
		{
			isdatefirst = false;
		}
		if (isinput)
		{

			return new WisedocDateTimeFormat(datetable.getInfo(), timetable
					.getInfo(), oldformat.getDateout(), oldformat.getTimeout(),
					seperate, oldformat.getOutseperate(), isdatefirst,
					oldformat.isOutdatefirst());

		} else
		{
			return new WisedocDateTimeFormat(oldformat.getDatein(), oldformat
					.getTimein(), datetable.getInfo(), timetable.getInfo(),
					oldformat.getInputseperate(), seperate, oldformat
							.isIndatefirst(), isdatefirst);
		}
	}

	private void updateDateMoveButton()
	{
		int rowselected = datetable.getSelectedRow();
		dateupperbutton.setEnabled(rowselected > 0);
		datedownbutton.setEnabled(rowselected > -1
				&& rowselected < datetable.getRowCount() - 1);
	}

	private void updateTimeMoveButton()
	{
		int rowselected = timetable.getSelectedRow();
		timeupperbutton.setEnabled(rowselected > 0);
		timedownbutton.setEnabled(rowselected > -1
				&& rowselected < datetable.getRowCount() - 1);
	}

	private ButtonGroup buttonGroup1;
	private JButton dateupperbutton = new JButton("上移");
	private JButton datedownbutton = new JButton("下移");
	private JButton timeupperbutton = new JButton("上移");
	private JButton timedownbutton = new JButton("下移");
	// private JComboBox datetimetype = new JComboBox(new
	// DefaultComboBoxModel(new String[]{"日期","时间","日期时间"}));
	private JPanel mainpanel = new JPanel();
	private JPanel othersetpanel = new JPanel();
	private JPanel datesetpanel = new JPanel();
	private JPanel timesetpanel = new JPanel();
	private JPanel dateactionpanel = new JPanel();
	private JPanel timeactionpanel = new JPanel();
	private JRadioButton datefirstradiobutton = new JRadioButton("日期在前");
	private JRadioButton timefirstradiobutton = new JRadioButton("时间在前");
	private JScrollPane datesetscroolpanel = new JScrollPane();
	private JScrollPane timescrollpane;
	private DateTable datetable;
	private TimeTable timetable;
	private JLabel seperlabel = new JLabel("日期时间之间的分割符");
	private JTextField sepertext = new JTextField();

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("test");
		frame.setSize(600, 400);
		frame.getContentPane().add(new DateTimeSetPanel(null, false));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();
	}
}
