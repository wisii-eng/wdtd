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

package com.wisii.wisedoc.view.dialog;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.dialog.MasterNode.MasterType;

@SuppressWarnings("serial")
public class MasterCellRender extends DefaultTreeCellRenderer
{

	Icon iconsingle = MediaResource.getImageIcon("s.GIF");

	Icon iconrepeat = MediaResource.getImageIcon("r.GIF");

	Icon iconposition = MediaResource.getImageIcon("p.GIF");

	Icon iconcondition = MediaResource.getImageIcon("c.GIF");

	private JTree tree;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */

	public MasterCellRender()
	{

	}

	public Component getTreeCellRendererComponent(JTree jtree, Object obj,
			boolean sel, boolean expanded, boolean leaf, int i, boolean hasFocus)
	{
		this.tree = jtree;
		if (obj instanceof MasterReference)
		{
			MasterReference node = (MasterReference) obj;
			String name = node.getName();
			MasterType type = node.getType();
			switch (type)
			{
				case SINGLE:
				{
					setLeafIcon(iconsingle);
					setOpenIcon(iconsingle);
					setClosedIcon(iconsingle);
					break;
				}
				case REPEAT:
				{
					setLeafIcon(iconrepeat);
					setOpenIcon(iconrepeat);
					setClosedIcon(iconrepeat);
					break;
				}
				case POSITION:
				{
					setLeafIcon(iconposition);
					setOpenIcon(iconposition);
					setClosedIcon(iconposition);
					break;
				}

				case ROOT:
				{
					setLeafIcon(iconposition);
					setOpenIcon(iconposition);
					setClosedIcon(iconposition);
					break;
				}
				default:
				{
					setLeafIcon(iconcondition);
					setOpenIcon(iconcondition);
					setClosedIcon(iconcondition);
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
//		if (!jtree.isEnabled())
//		{
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
//		} else
//		{
//			setEnabled(true);
//			if (leaf)
//			{
//				setIcon(getLeafIcon());
//			} else if (expanded)
//			{
//				setIcon(getOpenIcon());
//			} else
//			{
//				setIcon(getClosedIcon());
//			}
//		}
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
