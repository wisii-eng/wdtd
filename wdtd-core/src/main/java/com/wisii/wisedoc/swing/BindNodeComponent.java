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
 * @BindNodeComponent.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.SqlBindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.resource.MediaResource;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-10-29
 */
public class BindNodeComponent extends DefaultTreeCellRenderer
{
	String toolTipText;
	public BindNodeComponent(int type)
	{
		Icon icon;
		switch (type)
		{
		case BindNode.DATE:
		{
			icon = MediaResource.getImageIcon("DateTime.gif");
			break;
		}
		case BindNode.TIME:
		{
			icon = MediaResource.getImageIcon("InternetLink.gif");
			break;
		}
		case BindNode.DECIMAL:
		{
			icon = MediaResource.getImageIcon("FormattedNumber.gif");
			break;
		}
		case BindNode.INTEGER:
		{
			icon = MediaResource.getImageIcon("Italic.gif");
			break;
		}
		case BindNode.BOOLEAN:
		{
			icon = MediaResource.getImageIcon("Warning16.png");
			break;
		}
		default:
			icon = MediaResource.getImageIcon("Font.gif");
			break;
		}
		setLeafIcon(icon);
		setOpenIcon(icon);
		setClosedIcon(icon);
		ToolTipManager.sharedInstance().registerComponent(this);
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus)
	{
		BindNode bindnode = (BindNode) value;
		if (bindnode instanceof SqlBindNode)
		{
			toolTipText = "SQL语句为：" + ((SqlBindNode) bindnode).getSql();
		} else
		{
			toolTipText = "类型:" + dateTypeTranslate(bindnode.getDataType())
					+ " , 最大长度:" + getlengthString(bindnode.getLength());
		}
		String nodename=bindnode.getNodeName();
		int nsindex=nodename.indexOf(':');
		if(nsindex!=-1)
		{
			DataStructureTreeModel model=(DataStructureTreeModel) tree.getModel();
			String namespace=model.getNameSpaceOfPre(nodename.substring(0, nsindex));
		    if(namespace!=null)
		    {
		    	toolTipText=toolTipText+ " , 命名空间:" + namespace;
		    }
		}
		return super.getTreeCellRendererComponent(tree,
				getText(bindnode), sel, expanded, leaf, row, hasFocus);
	}
	  /**
     * Returns the tooltip string that has been set with
     * <code>setToolTipText</code>.
     *
     * @return the text of the tool tip
     * @see #TOOL_TIP_TEXT_KEY
     */
    public String getToolTipText() {
        return toolTipText;
    }
	private String getText(BindNode bindnode)
	{
		String nodename=bindnode.getNodeName();
		int index=nodename.indexOf(':');
		if(index!=-1)
		{
			nodename=nodename.substring(index+1);
		}
		if (bindnode instanceof AttributeBindNode)
		{
			return "@" + nodename;
		} else
		{
			return nodename;
		}
	}

	private String getlengthString(int len)
	{
		if (len < 0)
		{
			return "不固定长度";
		} else
		{
			return "" + len;
		}
	}

	private String dateTypeTranslate(int type)
	{
		String result = FoXsltConstants.STRING;
		switch (type)
		{
		case BindNode.DATE:
		{
			result = FoXsltConstants.DATETYPE;
			break;
		}
		case BindNode.TIME:
		{
			result = FoXsltConstants.TIME;
			break;
		}
		case BindNode.DECIMAL:
		{
			result = FoXsltConstants.DECIMAL;
			break;
		}
		case BindNode.INTEGER:
		{
			result = FoXsltConstants.INTEGER;
			break;
		}
		case BindNode.BOOLEAN:
		{
			result = FoXsltConstants.BOOLEAN;
			break;
		}
		default:
			break;
		}
		return result;
	}
}
