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

/**
 * @XMLSelectDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-4-16
 */
@SuppressWarnings("serial")
public class XMLSelectDialog extends AbstractWisedocDialog
{

	JPanel content;

	private JLabel jSouceLabel = new JLabel(MessageResource
			.getMessage("datasourceurl"));

	private JTextField jSouceField = new JTextField("");

	JButton okButton = new JButton(MessageResource
			.getMessage("wsd.view.gui.dialog.ok"));

	JButton cancelButton = new JButton(MessageResource
			.getMessage("wsd.view.gui.dialog.cancel"));

	/* 对话框的返回状态 */
	private DialogResult result = DialogResult.Cancel;

	private static WiseDocFileFilter FILTER = new WiseDocFileFilter(
			MessageResource.getMessage("wsd.datastructure.type"), "datafile");

	private WiseDocFileFilter _filter = null;

	protected XYLayout layout = new XYLayout();
    private String DEFAULTFILEPATH = System.getProperty("user.dir")
	+ File.separatorChar+"data"+File.separatorChar+"default.xml";
	private String _file = DEFAULTFILEPATH;

	JCheckBox standard = new JCheckBox("预览标准模板效果", false);

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public XMLSelectDialog(String file)
	{
		super(SystemManager.getMainframe(), MessageResource
				.getMessage("xmlselecttitle"), true);
		if (file != null)
		{
			_file = file;
		}
		jSouceField.setText(_file);
		init();
	}

	protected void init()
	{
		_filter = new WiseDocFileFilter("*.xml", "xml");
		content = new JPanel();
		layout.setWidth(500);
		layout.setHeight(180); // 320 --> 230
		content.setLayout(layout);
		JButton jSouceButton = new JButton(MediaResource
				.getImageIcon("Open.gif"));
		jSouceButton.setToolTipText("选择数据文件");
		jSouceButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent event)
			{
				JFileChooser filechooser = DialogSupport.getDialog(_filter,
						FILTER);
				if (_filter != null)
				{
					filechooser.setFileFilter(_filter);
				} else
				{
					filechooser.setFileFilter(FILTER);
				}
				filechooser.setDialogTitle(MessageResource
						.getMessage("xmlselecttitle"));
				if(_file!=null&&!_file.isEmpty()&&!_file.equals(DEFAULTFILEPATH))
				{
					_file = _file.substring(0, _file.lastIndexOf("\\"));
					filechooser.setCurrentDirectory(new File(_file));
				}
				int result = filechooser.showOpenDialog(XMLSelectDialog.this);
				jSouceField.requestFocus();
				if (result == JFileChooser.CANCEL_OPTION)
					return;
				File file = filechooser.getSelectedFile();
				String souceFile = file == null ? "" : file.getPath();
				jSouceField.setText(souceFile);
			}
		});

		// JButton jSouceCondition = new JButton(MediaResource
		// .getImageIcon("Open.gif"));
		// if (getfile().equalsIgnoreCase("")
		// || getfile().equalsIgnoreCase(
		// BrowseAction.class.getClassLoader().getResource("temp")
		// .getPath()
		// + "/a.xml"))
		// {
		// jSouceCondition.setEnabled(false);
		// } else
		// {
		// // jSouceCondition.setEnabled(false);
		// jSouceCondition.setToolTipText("设置过滤条件");
		// }
		// jSouceCondition.addActionListener(new ActionListener()
		// {
		//
		// public void actionPerformed(ActionEvent event)
		// {
		// SetGroupDialog dialog = new SetGroupDialog(SystemManager
		// .getCurruentDocument());
		// DialogResult result = dialog.showDialog();
		// if (DialogResult.OK.equals(result))
		// {
		// IoXslUtil.setGroup(dialog.getGroup());
		// }
		// }
		// });
		content.add(jSouceLabel, new XYConstraints(35, 30, 60, 25));
		content.add(jSouceField, new XYConstraints(100, 30, 340, 25));
		content.add(jSouceButton, new XYConstraints(450, 30, 30, 25));
		// content.add(jSouceCondition, new XYConstraints(400, 45, 30, 25));
		setOkButton(okButton);
		okButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent event)
			{
				if (event.getSource() != XMLSelectDialog.this
						&& event.getSource() != okButton)
				{
					return;
				}
				if (jSouceField.getText().trim().equals(""))
				{
					JOptionPane.showMessageDialog(XMLSelectDialog.this,
							MessageResource.getMessage("filecannotunselect"));
					jSouceField.requestFocus();
					return;
				}
				_file = jSouceField.getText().trim();
				File datafile = new File(_file);
				// 如果文件不存在或可读，则提示
				if (datafile == null || !datafile.canRead())
				{
					JOptionPane.showMessageDialog(SystemManager.getMainframe(),
							MessageResource.getMessage("filecannotreadopen"));
					jSouceField.selectAll();
					jSouceField.requestFocus();
					return;
				}
				result = DialogResult.OK;
				IoXslUtil.setStandard(standard.isSelected());
				dispose();
			}
		});
		jSouceField.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				okButton.requestFocus();
			}
		});
		setCancelButton(cancelButton);
		cancelButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent event)
			{
				if (event.getSource() != XMLSelectDialog.this
						&& event.getSource() != cancelButton
						&& event.getSource() != jSouceField)
				{
					return;
				}
				dispose();
			}
		});
		content.add(okButton, new XYConstraints(290, 110, 80, 28)); // 67 --> 80
		// 356 -->
		// 276
		content.add(cancelButton, new XYConstraints(380, 110, 80, 28));

		standard.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				IoXslUtil.setStandard(standard.isSelected());
			}
		});
		content.add(standard, new XYConstraints(150, 70, 150, 30));
		getContentPane().add(content);
		setSize(500, 180);
		setResizable(false);
	}

	/**
	 * 
	 * 返回数据。
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public InputStream getData()
	{
		return getData(-1);
	}

	/**
	 * 
	 * 返回数据。
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public InputStream getData(int count)
	{
		InputStream data = null;
		// 如果选择了文件
		if (_file != null)
		{
			InputStream in = null;
			try
			{
				in = new FileInputStream(_file);
				data = WisedocUtil.coverInputStream(in);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	public DialogResult showDialog()
	{
		DialogSupport.centerOnScreen(this);
		setVisible(true);
		return result;
	}

	public String getfile()
	{
		// TODO Auto-generated method stub
		return _file;
	}

}
