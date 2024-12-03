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
 * @ImageLocalAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.swing.ImageFileFilter;
import com.wisii.wisedoc.util.DialogSupport;

/**
 * 类功能描述：插入图片事件类
 * 
 * 作者：zhongyajun 创建日期：2007-6-15
 */
public class InsertImageAction extends InsertObjectAction
{
	protected List<CellElement> creatCells()
	{
		List<CellElement> inserts = null;
		File openFile = DialogSupport.getFileDialog(
				DialogSupport.FileDialogType.open, new ImageFileFilter());
		if (openFile != null)
		{
			String path = openFile.getPath();
			String imageroot = System.getProperty("user.dir") + File.separator
					+ "graphics" + File.separator;
			if (path.startsWith(imageroot))
			{
				ImageInline imagein = new ImageInline(path.substring(imageroot
						.length()));
				inserts = new ArrayList<CellElement>();
				inserts.add(imagein);
			}

		}
		return inserts;
	}
}