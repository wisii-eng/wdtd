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

package com.wisii.wisedoc.view.ui.actions.pagelayout;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

@SuppressWarnings("serial")
public class AddFirstLayoutAction extends Actions
{

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCheckBox)
		{
			Object masterreference = null;
			CellElement elements = getCaretPosition().getLeafElement();
			while (!(elements instanceof PageSequence))
			{
				elements = (CellElement) elements.getParent();
			}
			Document doc = getCurrentDocument();
			if (elements != null)
			{
				Map<Integer, Object> attrs = elements.getAttributes() == null ? null
						: elements.getAttributes().getAttributes();
				if (attrs != null)
				{
					Object lecon = null;
					if (attrs.containsKey(Constants.PR_PAGE_SEQUENCE_MASTER))
					{
						lecon = attrs.get(Constants.PR_PAGE_SEQUENCE_MASTER);
					} else if (attrs
							.containsKey(Constants.PR_SIMPLE_PAGE_MASTER))
					{
						lecon = attrs.get(Constants.PR_SIMPLE_PAGE_MASTER);
					}
					if (!Constants.NULLOBJECT.equals(lecon))
					{
						masterreference = lecon;
					}
				}
			}
			JCheckBox wrap = (JCheckBox) e.getSource();
			boolean isselect = wrap.isSelected();
			Map<Integer, Object> newatts = new HashMap<Integer, Object>();
			if (doc == null)
			{
				return;
			}
			Object newobj = WisedocUtil.getPageSequenceMaster(masterreference,
					isselect, 1);
			if (newobj instanceof SimplePageMaster)
			{
				// String code = new SimplePageMasterWriter(
				// (SimplePageMaster) newobj).write(elements);
				// System.out.println("class::::" + code);
				newatts.put(Constants.PR_SIMPLE_PAGE_MASTER, newobj);
				newatts.put(Constants.PR_PAGE_SEQUENCE_MASTER, null);
			} else if (newobj instanceof PageSequenceMaster)
			{
				// String code = new PageSequenceMasterWriter(
				// (PageSequenceMaster) newobj).write();
				// System.out.println("class::::" + code);
				newatts.put(Constants.PR_SIMPLE_PAGE_MASTER, null);
				newatts.put(Constants.PR_PAGE_SEQUENCE_MASTER, newobj);
			}

			if (elements != null)
			{
				doc.setElementAttributes(elements, newatts, false);
			}
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof JCheckBox
				&& hasPropertyKey(Constants.PR_PAGE_SEQUENCE_MASTER))
		{
			JCheckBox wrapradio = (JCheckBox) uiComponent;
			Object masterreference = null;
			CellElement elements = getCaretPosition().getLeafElement();
			while (!(elements instanceof PageSequence))
			{
				elements = (CellElement) elements.getParent();
			}
			if (elements != null)
			{
				Map<Integer, Object> attrs = elements.getAttributes() == null ? null
						: elements.getAttributes().getAttributes();
				if (attrs != null)
				{
					Object lecon = null;
					if (attrs.containsKey(Constants.PR_PAGE_SEQUENCE_MASTER))
					{
						lecon = attrs.get(Constants.PR_PAGE_SEQUENCE_MASTER);
					} else if (attrs
							.containsKey(Constants.PR_SIMPLE_PAGE_MASTER))
					{
						lecon = attrs.get(Constants.PR_SIMPLE_PAGE_MASTER);
					}
					if (!Constants.NULLOBJECT.equals(lecon))
					{
						masterreference = lecon;
					}
				}
			}
			wrapradio.setSelected(WisedocUtil.getIsCheck(masterreference, 1));
		}
	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_PAGE_SEQUENCE_MASTER))
		{
			if (uiComponent instanceof JCheckBox)
			{
				JCheckBox wrapradio = (JCheckBox) uiComponent;
				wrapradio.setSelected(false);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof JCheckBox)
		{
			JCheckBox wrapradio = (JCheckBox) uiComponent;
			wrapradio.setSelected(false);
		}
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		if (getCaretPosition() != null)
		{
			return true;
		}
		return false;
	}
}