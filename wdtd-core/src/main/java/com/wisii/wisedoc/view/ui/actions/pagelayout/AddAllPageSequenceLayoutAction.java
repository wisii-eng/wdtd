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
import java.util.Enumeration;
import java.util.Map;

import javax.swing.JCheckBox;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

@SuppressWarnings("serial")
public class AddAllPageSequenceLayoutAction extends Actions
{

	@SuppressWarnings("unchecked")
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
			if (isselect)
			{
				Enumeration<Object> list = doc.children();
				while (list.hasMoreElements())
				{
					PageSequence pagesequence = (PageSequence) list
							.nextElement();
					if (masterreference instanceof SimplePageMaster)
					{
						pagesequence.setAttribute(
								Constants.PR_SIMPLE_PAGE_MASTER,
								masterreference);
					} else if (masterreference instanceof PageSequenceMaster)
					{
						pagesequence.setAttribute(
								Constants.PR_PAGE_SEQUENCE_MASTER,
								masterreference);
					}
				}
			} else
			{
				Enumeration<Object> list = doc.children();
				int flg = 0;
				while (list.hasMoreElements())
				{
					PageSequence pagesequence = (PageSequence) list
							.nextElement();
					if (flg > 0)
					{
						if (masterreference instanceof SimplePageMaster)
						{
							pagesequence.setAttribute(
									Constants.PR_SIMPLE_PAGE_MASTER,
									((SimplePageMaster) masterreference)
											.clone());
						} else if (masterreference instanceof PageSequenceMaster)
						{
							pagesequence.setAttribute(
									Constants.PR_PAGE_SEQUENCE_MASTER,
									((PageSequenceMaster) masterreference)
											.clone());
						}
					} else
					{
						if (masterreference instanceof SimplePageMaster)
						{
							pagesequence.setAttribute(
									Constants.PR_SIMPLE_PAGE_MASTER,
									masterreference);
						} else if (masterreference instanceof PageSequenceMaster)
						{
							pagesequence.setAttribute(
									Constants.PR_PAGE_SEQUENCE_MASTER,
									masterreference);
						}
					}
					flg++;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof JCheckBox
				&& hasPropertyKey(Constants.PR_PAGE_SEQUENCE_MASTER))
		{
			JCheckBox wrapradio = (JCheckBox) uiComponent;
			Object masterreference = null;
			Document doc = getCurrentDocument();
			Enumeration<Object> list = doc.children();
			int flg = 0;
			boolean ischeck = true;
			while (list.hasMoreElements())
			{
				PageSequence pagesequence = (PageSequence) list.nextElement();
				Object mf = pagesequence
						.getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER);
				if (mf == null)
				{
					mf = pagesequence
							.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);
				}
				if (flg == 0)
				{
					masterreference = mf;
				} else
				{
					if (!((mf == null && masterreference == null) || mf != null
							&& mf.equals(masterreference)))
					{
						ischeck = false;
						break;
					} else
					{
						masterreference = mf;
					}
				}
				flg++;

			}
			wrapradio.setSelected(ischeck);
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
				wrapradio.setSelected(true);
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

}