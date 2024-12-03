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

import java.util.Map;

import javax.swing.JScrollPane;

import com.wisii.db.ConnectionSeting;
import com.wisii.db.util.DBUtil;
import com.wisii.wisedoc.view.data.Control;
import com.wisii.wisedoc.view.data.DataSourceNode;
import com.wisii.wisedoc.view.data.DataSourceNodeReferece;
import com.wisii.wisedoc.view.data.DataSourceTree;
import com.wisii.wisedoc.view.data.DataSourceTreeModel;

@SuppressWarnings("serial")
public class DataSourceNodePanel extends JScrollPane
{

	Object master;

	public DataSourceTree datasourcetree;

	Control control;

	DataSourceNode select;

	public DataSourceNodePanel(String name)
	{
		super();
		initComponents(name);
	}

	private void initComponents(String name)
	{
		datasourcetree = new DataSourceTree(getDSModel(name));
		this.setViewportView(datasourcetree);
	}

	public Control getControl()
	{
		return control;
	}

	public void setControl(Control control)
	{
		this.control = control;
		datasourcetree.setControl(control);
	}

	public Map<String, ConnectionSeting> getDataSources()
	{
		return datasourcetree.getDataSources();
	}

	public DataSourceTreeModel getDSModel(String name)
	{
		DataSourceNode root = new DataSourceNodeReferece();
		Map<String, ConnectionSeting> map = DBUtil.getConnectionSetings();
		if (map != null && !map.isEmpty())
		{
			DataSourceTree.setCons(map);
			Object[] keys = map.keySet().toArray();
			for (Object current : keys)
			{
				DataSourceNode currentnode = new DataSourceNodeReferece(map
						.get(current));
				root.addChild(currentnode, 0);
				if (name != null && name.equals(current))
				{
					select = currentnode;
				}
			}
		}
		DataSourceTreeModel model = new DataSourceTreeModel(root,false);

		return model;
	}

	public void setSelectNode(String nodename)
	{
		if (select != null)
		{
			control.setCurrentnode(select);
			DataSourceNode root = select.getParent();
			int index = root.getIndex(select);
			control.upDataTree(index+1);
		}
	}
}
