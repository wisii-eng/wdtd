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
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.WisedocFrame;

/*
 *  作者 : 刘译璟
 *  时间 : 2007-08-30
 *  描述 : 创建表格时,用来供用户选择表格行与列
 */
public class CreateTableDialog extends AbstractWisedocDialog
{
	/* 对话框的主面板 */
	private JPanel mainPanel = new JPanel();

	/* 设置表格行列的 Panel */
	private JPanel tablePanel = new JPanel();

	/* 放置按钮的Panel */
	private JPanel buttonsPanel = new JPanel(
			new FlowLayout(FlowLayout.TRAILING));

	/* 设置是否有表头 */
	JCheckBox chkHasHeader = new JCheckBox(
			getMessage(MessageConstants.INSERTTABLEDIALOG
					+ MessageConstants.HASHEADER));

	/* 设置是否有表尾 */
	JCheckBox chkHasFooter = new JCheckBox(
			getMessage(MessageConstants.INSERTTABLEDIALOG
					+ MessageConstants.HASFOOTER));

	/* 设置行 标签 */
	protected JLabel labRow = new JLabel(getMessage(MessageConstants.INSERTTABLEDIALOG
			+ MessageConstants.ROW));

	/* 设置行 文本框 */
	private JFormattedTextField txtRow = new JFormattedTextField(formatter);

	/* 设置列 标签 */
	protected JLabel labCol = new JLabel(getMessage(MessageConstants.INSERTTABLEDIALOG
			+ MessageConstants.COLUMN));

	/* 设置列 文本框 */
	private JFormattedTextField txtCol = new JFormattedTextField(formatter);
	/* 设置单元格宽 标签 */
	protected JLabel labcellwidth = new JLabel();

	/* 设置单元格宽框 */
	private FixedLengthSpinner txtcellwidth = new FixedLengthSpinner();
	/* 设置列 标签 */
	protected JLabel labrowheight = new JLabel();

	/* 设置列 文本框 */
	private FixedLengthSpinner txtrowheight = new FixedLengthSpinner();

	/* 确定 按钮 */
	private JButton btnOK = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

	/* 取消 按钮 */
	private JButton btnCancel = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));

	/* 定义对话框的返回状态 */
	private DialogResult result;

	public static final String PARTTEN = ",##0";

	protected static final int MAXCOUNT = 89;

	/* 定义数据的格式化样式对象 */
	private static DecimalFormat format = new DecimalFormat(PARTTEN);

	/* 定义数据的格式化对象 */
	private static NumberFormatter formatter = new NumberFormatter(format);

	/* 是否有表头 */
	private boolean hasHeader = true;

	/* 是否有表尾 */
	private boolean hasFooter = true;

	/* 行数 */
	private int row = 3;

	/* 列数 */
	private int col = 3;
	private LengthProperty colwidth = null;
	private LengthProperty rowheight = null;

	public CreateTableDialog()
	{
		this(SystemManager.getMainframe(),
				getMessage(MessageConstants.INSERTTABLEDIALOG
						+ MessageConstants.INSERTTABLEDIALOGTITLE), true);
	}

	public CreateTableDialog(JFrame owner, String title, boolean modal)
	{
		super(owner, title, modal);
		initComponents();
	}

	/**
	 * 
	 * 初始化窗体，为窗体添加组件
	 * 
	 * @param 无
	 * @return void 无
	 */
	private void initComponents()
	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(322, 200));
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		setContentPane(mainPanel);

		tablePanel.setBorder(BorderFactory.createEtchedBorder());
		tablePanel.setLayout(null);

		chkHasHeader.setBounds(40, 30, 100, 20);
		chkHasHeader.setSelected(hasHeader);

		chkHasFooter.setBounds(195, 30, 100, 20);
		chkHasFooter.setSelected(hasFooter);

		labRow.setHorizontalAlignment(SwingConstants.TRAILING);
//		labRow.setText();
		labRow.setBounds(new Rectangle(4, 72, 56, 15));
		txtRow.setBounds(new Rectangle(64, 69, 77, 20));

		labCol.setHorizontalAlignment(SwingConstants.TRAILING);
		labCol.setBounds(new Rectangle(149, 72, 65, 15));
		txtCol.setBounds(new Rectangle(216, 69, 77, 20));
		labcellwidth.setText(getMessage(MessageConstants.INSERTTABLEDIALOG
				+ MessageConstants.COLUMNWIDTH));
		labcellwidth.setHorizontalAlignment(SwingConstants.TRAILING);
		labcellwidth.setBounds(new Rectangle(4, 90, 56, 20));
		txtcellwidth.setBounds(new Rectangle(64, 90, 77, 20));
		labrowheight.setText(getMessage(MessageConstants.INSERTTABLEDIALOG
				+ MessageConstants.ROWHEIGHT));
		labrowheight.setHorizontalAlignment(SwingConstants.TRAILING);
		labrowheight.setBounds(new Rectangle(149, 90, 65, 20));
		txtrowheight.setBounds(new Rectangle(216, 90, 77, 20));
		tablePanel.add(chkHasHeader);
		tablePanel.add(chkHasFooter);
		tablePanel.add(labRow);
		tablePanel.add(txtRow);
		tablePanel.add(labCol);
		tablePanel.add(txtCol);
		tablePanel.add(labcellwidth);
		tablePanel.add(txtcellwidth);
		tablePanel.add(labrowheight);
		tablePanel.add(txtrowheight);
		formatter.setAllowsInvalid(false);
		formatter.setOverwriteMode(false);

		txtRow.setValue(row);
		txtCol.setValue(col);
		txtcellwidth.initValue(new FixedLength(3d,"cm"));
		txtrowheight.initValue(new FixedLength(1d,"cm"));;

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
				if (e.getSource() != CreateTableDialog.this
						&& e.getSource() != btnOK)
				{
					return;
				}
				setValues();
				if (col <= 0 || col > MAXCOUNT)
				{
					WiseDocOptionPane
							.showMessageDialog(
									CreateTableDialog.this,
									getMessage(MessageConstants.INSERTTABLEDIALOG
											+ MessageConstants.COLUMNISZEROERROR),
											WisedocFrame.DEFAULT_TITLE
									/*MessageResource
											.getMessage(MessageConstants.DIALOG_COMMON
													+ MessageConstants.INFORMATIONTITLE)*/,
									JOptionPane.INFORMATION_MESSAGE);
					txtCol.requestFocus();
					txtCol.selectAll();
					return;
				}
				if (row <= 0 || row > MAXCOUNT)
				{
					WiseDocOptionPane
							.showMessageDialog(
									CreateTableDialog.this,
									getMessage(MessageConstants.INSERTTABLEDIALOG
											+ MessageConstants.ROWISZEROERROR),WisedocFrame.DEFAULT_TITLE
									/*MessageResource
											.getMessage(MessageConstants.DIALOG_COMMON
													+ MessageConstants.INFORMATIONTITLE)*/,
									WiseDocOptionPane.INFORMATION_MESSAGE);
					txtRow.requestFocus();
					txtRow.selectAll();
					return;
				}
				if (colwidth == null)
				{
					WiseDocOptionPane
							.showMessageDialog(
									CreateTableDialog.this,
									getMessage(MessageConstants.INSERTTABLEDIALOG
											+ MessageConstants.COLUMNWIDTHERROR),
									MessageResource
											.getMessage(MessageConstants.DIALOG_COMMON
													+ MessageConstants.INFORMATIONTITLE),
									WiseDocOptionPane.INFORMATION_MESSAGE);
					txtcellwidth.requestFocus();
					return;
				}
				if (rowheight == null)
				{
					WiseDocOptionPane
							.showMessageDialog(
									CreateTableDialog.this,
									getMessage(MessageConstants.INSERTTABLEDIALOG
											+ MessageConstants.ROWHEIGHTERROR),
									MessageResource
											.getMessage(MessageConstants.DIALOG_COMMON
													+ MessageConstants.INFORMATIONTITLE),
									WiseDocOptionPane.INFORMATION_MESSAGE);
					txtrowheight.requestFocus();
					return;
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
		hasHeader = chkHasHeader.isSelected();
		hasFooter = chkHasFooter.isSelected();
		row = ((Number) txtRow.getValue()).intValue();
		col = ((Number) txtCol.getValue()).intValue();
		colwidth = txtcellwidth.getValue();
		rowheight = txtrowheight.getValue();
	}

	public int getCol()
	{
		return col;
	}

	public boolean isHasFooter()
	{
		return hasFooter;
	}

	public boolean isHasHeader()
	{
		return hasHeader;
	}

	public int getRow()
	{
		return row;
	}

	public LengthProperty getColumnWidth()
	{
		return colwidth;
	}

	public LengthProperty getRowHeight()
	{
		return rowheight;
	}
}
