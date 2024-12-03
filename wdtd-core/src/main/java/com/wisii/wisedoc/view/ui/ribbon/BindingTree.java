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
 * @BindingTree.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.PopupPanelManager;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;

/**
 * 类功能描述：显示动态数据结构树对象
 * 
 * 作者：zhangqiang 创建日期：2008-11-27
 */
public class BindingTree extends JCommandPopupMenu
{
	private WiseDocTree dTree = new WiseDocTree();

	public BindingTree()
	{
		Document doc = SystemManager.getCurruentDocument();
		if (doc != null && doc.getDataStructure() != null)
		{

			JPanel jp = new JPanel();
			dTree.setCellRenderer(new DataStructureCellRender());
			dTree.setModel(doc.getDataStructure());
			jp.add(dTree);
			JScrollPane jsp = new JScrollPane(jp);
			jsp.setPreferredSize(new Dimension(120,200));
			dTree.addTreeSelectionListener(new TreeSelectionListener()
			{
				public void valueChanged(TreeSelectionEvent e)
				{
					PopupPanelManager.defaultManager().hideLastPopup();

				}
			});
			this.add(jsp);
		}

	}

	public WiseDocTree getTree()
	{
		return dTree;
	}
}
