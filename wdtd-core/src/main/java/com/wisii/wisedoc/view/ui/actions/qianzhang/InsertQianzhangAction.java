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
 * @InsertQianzhangAction.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.qianzhang;

import java.awt.event.ActionEvent;
import java.io.File;
import com.wisii.wisedoc.swing.ImageFileFilter;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.mousehandler.QianZhangInsertManager;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2011-11-10
 */
public class InsertQianzhangAction extends BaseAction
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent
	 * )
	 */
	public void doAction(ActionEvent e)
	{
		File openFile = DialogSupport.getFileDialog(
				DialogSupport.FileDialogType.open, new ImageFileFilter(),"qianzhangs");
		if (openFile != null)
		{
			String path = openFile.getPath();
			String imageroot = System.getProperty("user.dir") + File.separator
					+ "qianzhangs" + File.separator;
			if (path.startsWith(imageroot))
			{
				QianZhangInsertManager.setPath(path.substring(imageroot
						.length()));
			}

		}
	}

}