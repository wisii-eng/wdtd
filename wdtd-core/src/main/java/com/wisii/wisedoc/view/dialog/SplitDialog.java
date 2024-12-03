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
 * @SplitDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.dialog;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

/**
 * 拆分对话框
 * 
 * 作者：zhangqiang 创建日期：2007-8-15
 */
public class SplitDialog extends AbstractWisedocDialog
{
	public static final String PARTTEN = ",##0";

	/* 定义数据的格式化样式对象 */
	private static DecimalFormat format = new DecimalFormat(PARTTEN);

	/* 定义数据的格式化对象 */
	private static NumberFormatter formatter = new NumberFormatter(format);
	/* 定义对话框的返回状态 */
	private DialogResult result;

	/* 放置按钮的Panel */
	private JPanel buttonsPanel = new JPanel(
			new FlowLayout(FlowLayout.TRAILING));
	/* 对话框的主面板 */
	private JPanel mainPanel = new JPanel();
	/* 设置表格行列的 Panel */
	private JPanel tablePanel = new JPanel();
	/* 确定 按钮 */
	private JButton btnOK = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

	/* 取消 按钮 */
	private JButton btnCancel = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));
	/* 设置行 标签 */
	protected JLabel labRow = new JLabel(
			getMessage(MessageConstants.INSERTTABLEDIALOG
					+ MessageConstants.ROW));

	/* 设置行 文本框 */
	private JFormattedTextField txtRow = new JFormattedTextField(formatter);

	/* 设置列 标签 */
	protected JLabel labCol = new JLabel(
			getMessage(MessageConstants.INSERTTABLEDIALOG
					+ MessageConstants.COLUMN));

	/* 设置列 文本框 */
	private JFormattedTextField txtCol = new JFormattedTextField(formatter);

	// 拆分的行数
	int row = 1;
	// 拆分的列数
	int col = 2;
	private final static int MAX = 62;
	int _maxrow;
	private final int _maxcol = MAX;

	public SplitDialog(int row)
	{
		super(SystemManager.getMainframe(),
				getMessage(MessageConstants.INSERTTABLEDIALOG
						+ MessageConstants.SPLITTABLECELLTITLE), true);
		if (row > 1)
		{
			_maxrow = row;
		} else
		{
			_maxrow = MAX;
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(322, 200));
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		setContentPane(mainPanel);

		tablePanel.setBorder(BorderFactory.createEtchedBorder());
		tablePanel.setLayout(null);
		labRow.setHorizontalAlignment(SwingConstants.TRAILING);
		labRow.setBounds(new Rectangle(4, 72, 56, 15));
		txtRow.setBounds(new Rectangle(64, 69, 77, 20));
		labCol.setHorizontalAlignment(SwingConstants.TRAILING);
		labCol.setBounds(new Rectangle(149, 72, 65, 15));
		txtCol.setBounds(new Rectangle(216, 69, 77, 20));
		tablePanel.add(labRow);
		tablePanel.add(txtRow);
		tablePanel.add(labCol);
		tablePanel.add(txtCol);
		formatter.setAllowsInvalid(false);
		formatter.setOverwriteMode(false);

		txtRow.setValue(this.row);
		txtCol.setValue(col);
		buttonsPanel.add(btnOK);
		buttonsPanel.add(btnCancel);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

		/* 【添加】李晓光 2007-08-20 设置确认按钮、取消按钮 */
		setOkButton(btnOK);
		setCancelButton(btnCancel);
		pack();

		DialogSupport.centerOnScreen(this);

		/* 为按钮添加Action */
		initActions();
	}

	/**
	 * 
	 * 为按钮添加Action
	 * 
	 * @param 无
	 * @return void 无
	 */
	private void initActions()
	{

		btnOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() != SplitDialog.this && e.getSource() != btnOK)
				{
					return;
				}
				setValues();
				if (col <= 0 || col > _maxcol)
				{
					WiseDocOptionPane
							.showMessageDialog(
									SplitDialog.this,
									getMessage(MessageConstants.INSERTTABLEDIALOG
											+ MessageConstants.COLUMNISZEROERROR),
									MessageResource
											.getMessage(MessageConstants.DIALOG_COMMON
													+ MessageConstants.INFORMATIONTITLE),
									JOptionPane.INFORMATION_MESSAGE);
					txtCol.requestFocus();
					txtCol.selectAll();
					return;
				}
				if (row <= 0 || row > _maxrow)
				{
					WiseDocOptionPane.showMessageDialog(SplitDialog.this,
							getMessage(MessageConstants.INSERTTABLEDIALOG
									+ MessageConstants.ROWISZEROERROR,new String[]
									        										{""+_maxrow}),
							getMessage(MessageConstants.DIALOG_COMMON
									+ MessageConstants.INFORMATIONTITLE),
							WiseDocOptionPane.INFORMATION_MESSAGE);
					txtRow.requestFocus();
					txtRow.selectAll();
					return;
				} else
				{
					if (_maxrow != MAX && _maxrow % row != 0)
					{
						WiseDocOptionPane.showMessageDialog(SplitDialog.this,
								getMessage(MessageConstants.INSERTTABLEDIALOG
										+ MessageConstants.ROWSPLITNOTDIVISOR,
										new String[]
										{ "" + _maxrow }));
						txtRow.requestFocus();
						txtRow.selectAll();
						return;
					}
				}
				distroy(DialogResult.OK);
			}
		});

		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				distroy(DialogResult.Cancel);
			}
		});
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent arg0)
			{
				distroy(DialogResult.Cancel);
			}
		});
	}

	/**
	 * 
	 * 设置窗体返回状态，关闭窗口，释放窗体资源。
	 * 
	 * @param result
	 *            指定窗体的返回状态
	 * @return void 无
	 */
	private void distroy(DialogResult result)
	{
		this.result = result;
		setVisible(false);
		dispose();
	}

	/**
	 * 
	 * 显示对话框
	 * 
	 * @param 无
	 * @return void 无
	 */
	public DialogResult showDialog()
	{
		setVisible(true);
		return result;
	}

	private void setValues()
	{
		row = ((Number) txtRow.getValue()).intValue();
		col = ((Number) txtCol.getValue()).intValue();
	}

	/**
	 * @返回 row变量的值
	 */
	public int getRow()
	{
		return row;
	}

	/**
	 * @返回 col变量的值
	 */
	public int getCol()
	{
		return col;
	}
}
