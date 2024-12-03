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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.configure.RecentOpenModel;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.Reader;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.OpenModelDialog;

public class OpenModelAction extends BaseAction
{

	@Override
	public void doAction(ActionEvent e)
	{
		Document currentdoc = getCurrentDocument();
		if (currentdoc != null && !currentdoc.isSaved())
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
		OpenModelDialog dia = new OpenModelDialog();
		DialogResult result = dia.showDialog();
		if (DialogResult.OK.equals(result))
		{
			File file = OpenModelDialog.getFile();
			if (file != null && file.exists())
			{
				try
				{
					FileInputStream filein = new FileInputStream(file);
					Reader reader = IOFactory.getReader(IOFactory.WSDT);
					try
					{
						Document doc = reader.read(filein);
						SystemManager.getMainframe().getEidtComponent()
								.setDocument(doc);
						if (RecentOpenModel.isLoadROF())
						{
							String path = file.getPath();
							RecentOpenModel.addOpenFile(path);
						}
					} catch (IOException ioe)
					{
						ioe.printStackTrace();
					}
				} catch (FileNotFoundException fnfe)
				{
					fnfe.printStackTrace();
				}

			} else
			{
				LogUtil.debugException("文件不存在", new Exception());
			}
			OpenModelDialog.setFile(null);
		}

	}
}
