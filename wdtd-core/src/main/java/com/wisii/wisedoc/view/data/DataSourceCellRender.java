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

package com.wisii.wisedoc.view.data;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.wisii.db.ConnectionSeting;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.data.DataSourceNode.NodeType;

@SuppressWarnings("serial")
public class DataSourceCellRender extends DefaultTreeCellRenderer
{

	Icon iconsourceitem = MediaResource.getImageIcon("s.GIF");

	Icon iconroot = MediaResource.getImageIcon("r.GIF");

	private JTree tree;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */

	public DataSourceCellRender()
	{

	}

	public Component getTreeCellRendererComponent(JTree jtree, Object obj,
			boolean sel, boolean expanded, boolean leaf, int i, boolean hasFocus)
	{
		this.tree = jtree;
		if (obj instanceof DataSourceNode)
		{
			DataSourceNode node = (DataSourceNode) obj;
			ConnectionSeting cs = node.getValue();
			String name = DataSourceNodeReferece.DATASOURCEROOT;
			if (cs != null)
			{
				name = node.getValue().getName();
			}
			NodeType type = node.getNodetype();
			switch (type)
			{
				case ROOT:
				{
					setLeafIcon(iconroot);
					setOpenIcon(iconroot);
					setClosedIcon(iconroot);
					break;
				}
				case DATASOUREITEM:
				{
					setLeafIcon(iconsourceitem);
					setOpenIcon(iconsourceitem);
					setClosedIcon(iconsourceitem);
					break;
				}
				default:
				{
					setLeafIcon(iconsourceitem);
					setOpenIcon(iconsourceitem);
					setClosedIcon(iconsourceitem);
					break;
				}
			}
			String text = name;
			setText(text);
		}
		this.hasFocus = hasFocus;
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
		} else if (sel)
		{
			color = getTextSelectionColor();
		} else
		{
			color = getTextNonSelectionColor();
		}
		setForeground(color);

		setEnabled(jtree.isEnabled());
		if (leaf)
		{
			setDisabledIcon(getLeafIcon());
		} else if (expanded)
		{
			setDisabledIcon(getOpenIcon());
		} else
		{
			setDisabledIcon(getClosedIcon());
		}
		setComponentOrientation(jtree.getComponentOrientation());
		selected = sel;
		return this;
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
