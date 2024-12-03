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
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;

/**
 * 类功能描述：
 * 
 * 作者：李晓光 创建日期：2008-9-10
 */
@SuppressWarnings("serial")
public class ProfileCellRender extends DefaultTreeCellRenderer
{

	private JTree tree;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */

	public ProfileCellRender()
	{

	}

	public Component getTreeCellRendererComponent(JTree jtree, Object obj,
			boolean flag, boolean flag1, boolean flag2, int i, boolean flag3)
	{
		this.tree = jtree;
		if (obj instanceof BindNode)
		{
			BindNode bindnode = (BindNode) obj;
			String path = bindnode.getXPath();
			int last = path.lastIndexOf("/");
			String text = path.substring(last + 1);
			setText(text);
		}
		hasFocus = flag3;
		Color color = null;
		javax.swing.JTree.DropLocation droplocation = jtree.getDropLocation();
		if (droplocation != null && droplocation.getChildIndex() == -1
				&& jtree.getRowForPath(droplocation.getPath()) == i)
		{
			Color color1 = UIManager.getColor("Tree.dropCellForeground");
			if (color1 != null)
			{
				color = color1;
			} else
			{
				color = getTextSelectionColor();
			}
		} else if (flag)
		{
			color = getTextSelectionColor();
		} else
		{
			color = getTextNonSelectionColor();
		}
		setForeground(color);
		if (!jtree.isEnabled())
		{
			setEnabled(false);
		} else
		{
			setEnabled(true);
		}
		setComponentOrientation(jtree.getComponentOrientation());
		selected = flag;
		return this;
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

	public static void main(String[] args)
	{
		ProfileCellRender profileCellRender = new ProfileCellRender();
		profileCellRender.setSize(50, 30);
		DefaultBindNode root = profileCellRender.getDateModel();
		JFrame fram = new JFrame("tupian");
		fram.setSize(800, 900);
		JPanel nullpanel = new JPanel();
		nullpanel.setSize(800, 800);
		XYLayout layout = new XYLayout();
		layout.setHeight(800);
		layout.setWidth(800);
		nullpanel.setLayout(layout);
		fram.setLayout(layout);
		ProfileTree tree = new ProfileTree(new DefaultTreeModel(root,
				false));
		nullpanel.add(tree, new XYConstraints(10, 10, 200, 325));
		fram.add(nullpanel);
		fram.setVisible(true);
	}

	public JTree getTree()
	{
		return tree;
	}

	public void setTree(JTree tree)
	{
		this.tree = tree;
	}

}
