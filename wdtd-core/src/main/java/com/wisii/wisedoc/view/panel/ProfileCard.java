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

package com.wisii.wisedoc.view.panel;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;

@SuppressWarnings("serial")
public class ProfileCard extends JScrollPane
{

	static JPanel rowHeard = new JPanel();
	{
		setSize(200, 700);
		rowHeard.setBackground(Color.WHITE);
		XYLayout columnHeardlayout = new XYLayout();
		columnHeardlayout.setWidth(200);
		columnHeardlayout.setHeight(600);
		rowHeard.setLayout(columnHeardlayout);
		ProfileCellRender profileCellRender = new ProfileCellRender();
		profileCellRender.setSize(50, 30);
		DefaultBindNode root = getDateModel();
		ProfileTree tree = new ProfileTree(new DefaultTreeModel(root,
				false));
		rowHeard.add(tree, new XYConstraints(10, 20, 200, 700));
		tree.getSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener()
				{

					public void valueChanged(TreeSelectionEvent e)
					{
						String path = e.getPath().toString();
						int last = path.lastIndexOf(",");
						String name = path.substring(last + 1).replaceAll(" ",
								"").replaceAll("]", "");
						ProfileCard.getMainpanel().addPanel(name);
					}

				});

	}

	static MainPanel mainpanel = new MainPanel();

	public ProfileCard()
	{
		setSize(800, 700);
		MainPanel.setPanelList();
		setRowHeaderView(rowHeard);
		setViewportView(mainpanel);
	}

	public DefaultBindNode getDateModel()
	{
		DefaultBindNode root = new DefaultBindNode(null, 0, 1, "");
		DefaultBindNode wisedoc = new DefaultBindNode(root, 1, 1, "文档");
		DefaultBindNode pagesequense = new DefaultBindNode(root, 1, 1, "章节");
		DefaultBindNode pagelayout = new DefaultBindNode(root, 1, 1, "页布局");
		DefaultBindNode object = new DefaultBindNode(root, 1, 1, "文档中的对象");
		DefaultBindNode block = new DefaultBindNode(root, 1, 1, "段落");
		DefaultBindNode table = new DefaultBindNode(root, 1, 1, "表");
		DefaultBindNode picture = new DefaultBindNode(root, 1, 1, "图片");
		DefaultBindNode barcode = new DefaultBindNode(root, 1, 1, "条码");
		DefaultBindNode blockcontainer = new DefaultBindNode(root, 1, 1,
				"高级块容器");
		DefaultBindNode datetime = new DefaultBindNode(root, 1, 1, "日期时间");
		DefaultBindNode formatnumber = new DefaultBindNode(root, 1, 1, "格式化数字");
		object.addChild(block);
		object.addChild(table);
		object.addChild(picture);
		object.addChild(barcode);
		object.addChild(blockcontainer);
		object.addChild(datetime);
		object.addChild(formatnumber);
		DefaultBindNode others = new DefaultBindNode(root, 1, 1, "其它属性");
		DefaultBindNode view = new DefaultBindNode(root, 1, 1, "视图");
		DefaultBindNode banshi = new DefaultBindNode(root, 1, 1, "版式");
		DefaultBindNode system = new DefaultBindNode(root, 1, 1, "系统设置");
		root.addChild(wisedoc);
		root.addChild(pagesequense);
		root.addChild(pagelayout);
		root.addChild(object);
		root.addChild(others);
		root.addChild(view);
		root.addChild(banshi);
		root.addChild(system);
		return root;
	}

	public static MainPanel getMainpanel()
	{
		return mainpanel;
	}

	public void setMainpanel(MainPanel mainpanel)
	{
		ProfileCard.mainpanel = mainpanel;
	}

	public static void showPanel(String id)
	{
		mainpanel.addPanel(id);
	}

	public static JPanel getRowHeard()
	{
		return rowHeard;
	}

	public void setRowHeard(JPanel rowHeard)
	{
		ProfileCard.rowHeard = rowHeard;
	}

}
