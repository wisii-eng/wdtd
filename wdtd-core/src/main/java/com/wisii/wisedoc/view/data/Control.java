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

import java.util.ArrayList;
import java.util.List;

import com.wisii.db.ConnectionSeting;
import com.wisii.db.util.DBUtil;
import com.wisii.wisedoc.view.panel.DataSourceEditPanel;
import com.wisii.wisedoc.view.panel.DataSourceNodePanel;

public class Control
{

	DataSourceNodePanel nodepanel;

	DataSourceEditPanel editpanel;

	private DataSourceNode currentnode;

	List<DataSourceNode> trackevents;

	public Control(DataSourceNodePanel node, DataSourceEditPanel masterpanel)
	{
		nodepanel = node;
		editpanel = masterpanel;
		trackevents = new ArrayList<DataSourceNode>();
	}

	public void upDataTree(int index)
	{
		nodepanel.datasourcetree.setSelectionRow(index);
		nodepanel.datasourcetree.updateUI();

	}

	public void getresult()
	{
		if (currentnode != null)
		{
			editpanel.getResult();
		}
	}

	public void upDataPanel()
	{
		editpanel.init(currentnode);
	}

	public DataSourceNode getCurrentnode()
	{
		return currentnode;
	}

	public void setCurrentnode(DataSourceNode currentnode)
	{
		this.currentnode = currentnode;
		upDataPanel();
		if (trackevents.contains(currentnode))
		{
			trackevents.remove(currentnode);
		}
		trackevents.add(0, currentnode);
	}

	public String getLastSelectName()
	{
		if (!trackevents.isEmpty())
		{
			for (DataSourceNode current : trackevents)
			{
				ConnectionSeting currentcs = current.getValue();
				if (DBUtil.testConnection(currentcs))
				{
					return currentcs.getName();
				}
			}
		}
		return null;
	}

}
