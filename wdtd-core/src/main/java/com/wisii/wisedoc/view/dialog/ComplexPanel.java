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

import java.util.List;

import javax.swing.JPanel;

import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;

@SuppressWarnings("serial")
public class ComplexPanel extends JPanel
{

	boolean complex;

	Object pagemaster;

	public ComplexPanel(boolean issimple)
	{
		super();
		complex = !issimple;
		initComponents();
	}

	public ComplexPanel(Object pagemaster, boolean issimple)
	{
		super();
		complex = !issimple;
		this.pagemaster = pagemaster;
		initComponents();
	}

	private void initComponents()
	{
	
	}

	public void setInitialState()
	{
		if (complex)
		{
			if (pagemaster instanceof PageSequenceMaster)
			{
				PageSequenceMaster currentpsm = (PageSequenceMaster) pagemaster;
				List<SubSequenceSpecifier> subss = currentpsm
						.getSubsequenceSpecifiers();
				if (subss != null && !subss.isEmpty())
				{

				} else
				{
					setSimple();
				}
			}
		} else
		{
			setSimple();
		}

	}

	public void setSimple()
	{
		
	}

	public Object getSimplePageMaster()
	{
		return null;
	}

	public boolean setMaster()
	{
		return true;
	}
}
