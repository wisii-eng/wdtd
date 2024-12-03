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
 * @InsertZiMobanAction.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileChooser;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.swing.ZimobanFileFilter;
import com.wisii.wisedoc.util.DialogSupport;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-10-22
 */
public class InsertZiMobanAction extends BaseAction
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent)
	 */
	@Override
	public void doAction(ActionEvent event)
	{
		List<CellElement> inserts = null;
		WiseDocFileChooser filechooser = (WiseDocFileChooser) DialogSupport
				.getDialog(false, new ZimobanFileFilter());
		filechooser.setCurrentDirectory(new File(ZiMoban.PATH));
		int dialogResult = filechooser.showOpenDialog(SystemManager
				.getMainframe());
		if (dialogResult == JFileChooser.APPROVE_OPTION)
		{
			File file = filechooser.getSelectedFile();
			String path = file.getPath();
			if (path.startsWith(ZiMoban.PATH) && file.exists())
			{
				if (!file.canRead())
				{
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), MessageResource
							.getMessage(MessageConstants.FILE
									+ MessageConstants.FILECANNOTREAD));
					return;
				}
				String relatepath = path.substring(ZiMoban.PATH.length());
				try
				{
					ZiMoban.validName(relatepath);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					LogUtil.debugException("读文件出错", e);
					e.printStackTrace();
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), MessageResource
							.getMessage(MessageConstants.FILE
									+ MessageConstants.FILECANNOTOPEN));
					return;
				}
				ZiMoban zimoban = ZiMoban.getInstanceof(relatepath);
				List<CellElement> elements = new ArrayList<CellElement>();
				elements.add(zimoban);
				this.getCurrentDocument().insertElements(elements,
						getCaretPosition());
			} else
			{
				WiseDocOptionPane.showMessageDialog(SystemManager
						.getMainframe(), MessageResource
						.getMessage(MessageConstants.FILE
								+ MessageConstants.FILENOTEXISTED));
			}
		}
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		return getCaretPosition() != null;
	}
}
