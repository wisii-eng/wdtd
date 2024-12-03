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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileChooser;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
@SuppressWarnings("serial")
public class SaveAsWSDTFileAction extends BaseAction
{

	public final static String WSDT = "wsdt";

	/**
	 * 
	 * 保存Action接口方法实现
	 * 
	 * @param ActioinEvent
	 *            事件源对数据的封装。
	 * @return void 无返回值
	 */
	public void doAction(ActionEvent e)
	{
		saveWSDTFile();
	}

	private void saveWSDTFile()
	{
		Document doc = SystemManager.getCurruentDocument();
		if (doc != null)
		{
			final WiseDocFileChooser chooser = (WiseDocFileChooser) DialogSupport
					.getDialog(new WiseDocFileFilter(MessageResource
							.getMessage(MessageConstants.FILE
									+ MessageConstants.WSDTTYPE), WSDT));
			chooser.addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent evt)
				{
					if (JFileChooser.APPROVE_SELECTION.equals(evt
							.getActionCommand()))
					{
						chooser.setResult(DialogResult.OK);
						if (IOUtil.isExists(chooser.getSelectedFile(), "wsdt"))
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
			String filePath = System.getProperty("user.dir") + "/Templates";
			chooser.setCurrentDirectory(new File(filePath));
			int dialogResult = chooser.showSaveDialog(SystemManager
					.getMainframe());
			if (dialogResult == JFileChooser.APPROVE_OPTION)
			{
				String savepath = chooser.getSelectedFile().getAbsolutePath();
				if (!savepath.toLowerCase().endsWith(".wsdt"))
				{
					savepath = savepath + ".wsdt";
				}
				IOUtil.savaFile(doc, new File(savepath));
			}
		}
	}
}
