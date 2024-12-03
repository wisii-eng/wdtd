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
 * @TableBackGroupImageAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ImageFileFilter;
import com.wisii.wisedoc.util.DialogSupport;

/**
 * 类功能描述：设置表格的背景图片操作
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-16
 */
public class BackgroundImageSetAction extends Actions
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent)
	 */
	public void doAction(ActionEvent e)
	{
		File openFile = DialogSupport.getFileDialog(DialogSupport.FileDialogType.open, new ImageFileFilter());
		if(openFile != null){
			Map<Integer, Object> att = new HashMap<Integer, Object>();
			/*att.put(Constants.PR_BACKGROUND_COLOR, null);*/
			att.put(Constants.PR_BACKGROUND_IMAGE, openFile.getName());
			setFOProperties(att);
		}

	}

}
