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
 * @OpenFileAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.configure.RecentOpenFile;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.Reader;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.view.dialog.MasterNameManager;
import com.wisii.wisedoc.view.dialog.MasterTree;

/**
 * 
 * 类功能描述：打开文档操作事件类
 * 
 * 作者：zhangqiang 创建日期：2008-12-9
 */
public class OpenFileAction extends BaseAction
{

	/**
	 * 序列化版本号ID
	 */
	private static final long serialVersionUID = 3677572375861160596L;

	/* 创建读取配置文件的对象 */

	/**
	 * 
	 * 打开菜单共用的Action接口方法实现
	 * 
	 * @param ActioinEvent
	 *            事件源对数据的封装。
	 * @return void 无返回值
	 * @throws
	 */
	public void doAction(ActionEvent e)
	{
		Document doc = getCurrentDocument();
		if (doc != null && !doc.isSaved())
		{
			int res = WiseDocOptionPane.showConfirmDialog(SystemManager
					.getMainframe(), MessageResource
					.getMessage(MessageConstants.FILE
							+ MessageConstants.DOCUMENTNOTSAVED));
			if (res == JOptionPane.CANCEL_OPTION)
			{
				return;
			} else if (res == JOptionPane.OK_OPTION)
			{
				if (!SaveFileAction.saveWSDFile())
				{
					return;
				}
			}
		}
		readWSDFile();
	}

	private void readWSDFile()
	{
		JFileChooser chooser = DialogSupport.getDialog(new WiseDocFileFilter(
				MessageResource.getMessage(MessageConstants.ALLWSDFILETYPE),
				SaveFileAction.WSD, SaveFileAction.WSDM, SaveFileAction.WSDX,
				SaveFileAction.WSDT));
		int dialogResult = chooser.showOpenDialog(SystemManager.getMainframe());
		if (dialogResult == JFileChooser.APPROVE_OPTION)
		{
			File file = chooser.getSelectedFile();
			if (file.exists())
			{
				if (!file.canRead())
				{
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), MessageResource
							.getMessage(MessageConstants.FILE
									+ MessageConstants.FILECANNOTREAD));
					return;
				}
				FileInputStream filein = null;
				try
				{
					filein = new FileInputStream(file);
				} catch (FileNotFoundException e)
				{
					LogUtil.debugException("文件不存在", e);
					return;
				}
				Reader reader = IOFactory.getReader(IOFactory.WSD);
				if (file.getName().endsWith(SaveFileAction.WSDT))
				{
					reader = IOFactory.getReader(IOFactory.WSDT);
				} else if (file.getName().endsWith(SaveFileAction.WSDX))
				{
					reader = IOFactory.getReader(IOFactory.WSDX);
				} else if (file.getName().endsWith(SaveFileAction.WSDM))
				{
					reader = IOFactory.getReader(IOFactory.WSDM);
				}
				Document doc;
				try
				{

					doc = reader.read(filein);
					if (doc == null)
					{
						WiseDocOptionPane.showMessageDialog(SystemManager
								.getMainframe(), MessageResource
								.getMessage(MessageConstants.FILE
										+ MessageConstants.FILECANNOTOPEN));
						return;
					}

				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					LogUtil.debugException("读文件出错", e);
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), MessageResource
							.getMessage(MessageConstants.FILE
									+ MessageConstants.FILECANNOTOPEN));
					return;
				}
				if (RecentOpenFile.isLoadROF())
				{
					String path = file.getAbsolutePath();
					RecentOpenFile.addOpenFile(path);
				}
				doc.setSavePath(file.getAbsolutePath());
				getEditorPanel().setDocument(doc);
				MasterNameManager.clearMasterName();
				MasterTree.chushihuaflg = 0;
			} else
			{
				WiseDocOptionPane.showMessageDialog(SystemManager
						.getMainframe(), MessageResource
						.getMessage(MessageConstants.FILE
								+ MessageConstants.FILENOTEXISTED));
			}
		}
	}

}
