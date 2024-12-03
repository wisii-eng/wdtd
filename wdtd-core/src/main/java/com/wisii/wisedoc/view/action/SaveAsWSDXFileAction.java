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

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.configure.RecentOpenFile;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileChooser;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.MoreThanOnePSDialog;

@SuppressWarnings("serial")
public class SaveAsWSDXFileAction extends BaseAction
{

	public SaveAsWSDXFileAction()
	{
		super();
	}

	/**
	 * 
	 * 用指定描述字符串和默认图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 */
	public SaveAsWSDXFileAction(String name)
	{
		super(name);
	}

	/**
	 * 
	 * 用指定描述字符串指定图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 * @param icon
	 *            指定Action的图标。
	 */
	public SaveAsWSDXFileAction(String name, Icon icon)
	{
		super(name, icon);
	}

	/**
	 * 到处xslt模版文件事件接口
	 */
	public void doAction(ActionEvent e)
	{
		Document document = getCurrentDocument();
		if (document == null)
		{
			JOptionPane.showInternalMessageDialog(null, MessageResource
					.getMessage("nodocument"), MessageResource
					.getMessage("prompt"), JOptionPane.CLOSED_OPTION);
		} else
		{
			saveAsWSDXFile(getCurrentDocument());
		}
	}

	/**
	 * 
	 * 另存为Action的实现
	 * 
	 * @param page
	 *            指定要保存的对象。
	 * @return void 无返回值。
	 * 
	 */
	private void saveAsWSDXFile(Document document)
	{
		int psn = document.getChildCount();
		if (psn > 1)
		{
			MoreThanOnePSDialog dia = new MoreThanOnePSDialog();
			if (dia.showDialog() == DialogResult.Cancel)
			{
				return;
			}
		}
		final WiseDocFileChooser chooser = (WiseDocFileChooser) DialogSupport
				.getDialog(new WiseDocFileFilter("wsdx", "wsdx"));
		String filePath = System.getProperty("user.dir") + File.separator
				+ "zimobans";
		File baseFile=new File(filePath);
		chooser.setCurrentDirectory(baseFile);
		chooser.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent evt)
			{
				if (JFileChooser.APPROVE_SELECTION.equals(evt
						.getActionCommand()))
				{
					chooser.setResult(DialogResult.OK);
					if (IOUtil.isExists(chooser.getSelectedFile(), "wsdx"))
					{
						int result = JOptionPane
								.showConfirmDialog(
										chooser,
										MessageResource
												.getMessage(MessageConstants.FILE
														+ MessageConstants.WHETHERREPLACEFILE),
										MessageResource
												.getMessage(MessageConstants.FILE
														+ MessageConstants.FILEEXISTED),
										JOptionPane.YES_NO_OPTION);
						if (result != JOptionPane.YES_OPTION)
							return;
					}
				} else if (JFileChooser.CANCEL_SELECTION.equals(evt
						.getActionCommand()))
				{
					chooser.setResult(DialogResult.Cancel);
				}
				chooser.distroy();
			}
		});
		DialogResult result = chooser.showDialog(JFileChooser.SAVE_DIALOG);
		if (result == DialogResult.OK)
		{
			IoXslUtil.setStandard(false);
			File selectfile = chooser.getSelectedFile();
			
			if (isSubFile(baseFile,selectfile))
			{
				String filename = selectfile.getAbsolutePath();
				if (!filename.endsWith(".wsdx"))
				{
					filename += ".wsdx";
				}
				File file = new File(filename);
				if (!WisedocUtil.canWrite(file))
				{
					WiseDocOptionPane
							.showMessageDialog(
									SystemManager.getMainframe(),
									MessageResource
											.getMessage(MessageConstants.FILE
													+ MessageConstants.FILECANOTWRITE),
									MessageResource
											.getMessage(MessageConstants.DIALOG_COMMON
													+ MessageConstants.INFORMATIONTITLE),
									WiseDocOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try
				{
					if (IOUtil.isHaveNormalDataStructure(document))
					{
						WiseDocOptionPane
								.showMessageDialog(
										SystemManager.getMainframe(),
										MessageResource
												.getMessage(MessageConstants.LOAD_NO_DATASTRUCT_OR_DB_DATASTRUCT),
										"",
										WiseDocOptionPane.INFORMATION_MESSAGE);
					} else
					{
						if (IOFactory.ifHaveZimoban())
						{
							WiseDocOptionPane
									.showMessageDialog(
											SystemManager.getMainframe(),
											MessageResource
													.getMessage(MessageConstants.CANT_HAVE_ZIMOBAN),
											"",
											WiseDocOptionPane.INFORMATION_MESSAGE);
						} else
						{
							IOFactory.getWriter(IOFactory.WSDX).write(filename,
									document);
							if (RecentOpenFile.isLoadROF())
							{
								RecentOpenFile.addOpenFile(filename);
								document.setSavePath(filename);
							}
						}
					}
				} catch (FileNotFoundException e)
				{
					LogUtil.debugException("出错", e);
				} catch (IOException e)
				{
					LogUtil.debugException("出错", e);
				}
			} else
			{
				WiseDocOptionPane.showMessageDialog(SystemManager
						.getMainframe(), MessageResource
						.getMessage(MessageConstants.FILE_ONLY_IN_ZIMOBANS),
						"", WiseDocOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	private boolean isSubFile(File base,File file)
	{
		File pfile = file.getParentFile();
		while(pfile!=null&&!pfile.equals(base))
		{
			pfile = pfile.getParentFile();
		}
		return base.equals(pfile);
	}
	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		boolean flg = !IOFactory.ifHaveZimoban();
		if (flg)
		{
			flg = !IOUtil.isHaveNormalDataStructure(getCurrentDocument());
		}
		return flg;
	}
}
