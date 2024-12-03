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
 * @NewLayoutAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.view.dialog.MasterNameManager;
import com.wisii.wisedoc.view.dialog.MasterTree;

/**
 * 
 * 类功能描述：新建文档操作
 * 
 * 作者：zhangqiang 创建日期：2008-12-9
 */
public class NewDocumentAction extends BaseAction
{

	/**
	 * serialVersionUID用来存储/记录什么，以及改变量的用途
	 */
	private static final long serialVersionUID = -6983914464719119263L;

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
		Document newdoc = new WiseDocDocument();
		getEditorPanel().setDocument(newdoc);
		MasterNameManager.clearMasterName();
		MasterTree.chushihuaflg = 0;
	}
}
