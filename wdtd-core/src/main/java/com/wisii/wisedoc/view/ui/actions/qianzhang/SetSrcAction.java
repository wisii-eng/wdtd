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
 * @SetSrcAction.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.qianzhang;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.swing.ImageFileFilter;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2011-11-16
 */
public class SetSrcAction extends Actions
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 
	 * com.wisii.wisedoc.view.ui.actions.Actions#doAction(java.awt.event.ActionEvent
	 * )
	 */
	@Override
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
				
			setFOProperty(Constants.PR_SRC,path.substring(imageroot
					.length()));
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.actions.Actions#isAvailable()
	 */
	@Override
	public boolean isAvailable()
	{
		if(!super.isAvailable())
		{
			return false;
		}
		List<CellElement> elements = getObjectSelects();
		if(elements==null||elements.isEmpty())
		{
			return false;
		}
		return elements.get(0) instanceof QianZhang;
	}

}
