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

import javax.swing.JScrollPane;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterReference;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SinglePageMasterReference;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.view.dialog.MasterNode.MasterType;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class NodePanel extends JScrollPane
{

	Object master;

	MasterTree mastertree;

	Control control;

	public NodePanel(Object pagemaster)
	{
		super();
		master = pagemaster;
		initComponents();
	}

	private void initComponents()
	{
		MasterTreeModel model = getModel();
		mastertree = new MasterTree(model);
		this.setViewportView(mastertree);
	}

	public void setInitialState(Object pagemaster)
	{
		master = pagemaster;
		MasterTreeModel model = getModel();
		mastertree.setModel(model);
	}

	public MasterTreeModel getModel()
	{
		MasterReference pagesequencemaster = new MasterReference(
				MasterType.ROOT, "页布局序列", null, 1);
		if (master != null)
		{
			if (master instanceof SimplePageMaster)
			{
				SimplePageMaster spmmaster = (SimplePageMaster) master;
				MasterReference onesingle = new MasterReference(
						MasterType.SINGLE, UiText.ONE_USE, spmmaster, 1);
				pagesequencemaster.addChild(onesingle);
				MasterNameManager.addMasterName(spmmaster.getMasterName());
			} else if (master instanceof PageSequenceMaster)
			{
				PageSequenceMaster psm = (PageSequenceMaster) master;
				List<SubSequenceSpecifier> sssflist = psm
						.getSubsequenceSpecifiers();
				if (sssflist != null)
				{
					for (SubSequenceSpecifier currentssf : sssflist)
					{
						if (currentssf instanceof SinglePageMasterReference)
						{
							SimplePageMaster spmmaster = ((SinglePageMasterReference) currentssf)
									.getMasterReference();
							MasterNameManager.addMasterName(spmmaster
									.getMasterName());
							MasterReference single = new MasterReference(
									MasterType.SINGLE, UiText.ONE_USE,
									spmmaster, 1);
							pagesequencemaster.addChild(single);
						} else if (currentssf instanceof RepeatablePageMasterReference)
						{
							SimplePageMaster spmmaster = ((RepeatablePageMasterReference) currentssf)
									.getMasterReference();
							MasterNameManager.addMasterName(spmmaster
									.getMasterName());
							MasterReference repeat = new MasterReference(
									MasterType.REPEAT, UiText.MORE_USE,
									spmmaster, 1);
							pagesequencemaster.addChild(repeat);
						} else if (currentssf instanceof RepeatablePageMasterAlternatives)
						{
							RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) currentssf;
							int number = rpma.getMaximumRepeats();
							MasterReference position = new MasterReference(
									MasterType.POSITION, UiText.POSITION_USE,
									null, number);
							List<ConditionalPageMasterReference> conditionlist = rpma
									.getPageMasterRefs();
							if (conditionlist != null)
							{
								for (ConditionalPageMasterReference currentcpmf : conditionlist)
								{
									int positionvalue = currentcpmf
											.getPagePosition();

									MasterReference condition = null;
									SimplePageMaster spmmaster = currentcpmf
											.getMasterReference();
									MasterNameManager.addMasterName(spmmaster
											.getMasterName());
									if (positionvalue == Constants.EN_ANY)
									{
										int oddevenvalue = currentcpmf
												.getOddOrEven();
										if (oddevenvalue == Constants.EN_ANY)
										{
											int blankvalue = currentcpmf
													.getBlankOrNotBlank();
											if (blankvalue == Constants.EN_BLANK)
											{
												condition = new MasterReference(
														MasterType.CONDITION_BLANK,
														UiText.BLANK_USE,
														spmmaster, 1);
											} else
											{
												condition = new MasterReference(
														MasterType.CONDITION_REST,
														UiText.REST_USE,
														spmmaster, 1);
											}
										} else
										{
											if (oddevenvalue == Constants.EN_ODD)
											{
												condition = new MasterReference(
														MasterType.CONDITION_ODD,
														UiText.ODD_USE,
														spmmaster, 1);
											} else if (oddevenvalue == Constants.EN_EVEN)
											{
												condition = new MasterReference(
														MasterType.CONDITION_EVEN,
														UiText.EVEN_USE,
														spmmaster, 1);
											}
										}

									} else
									{
										if (positionvalue == Constants.EN_FIRST)
										{
											condition = new MasterReference(
													MasterType.CONDITION_FIRST,
													UiText.FIRST_USE,
													spmmaster, 1);
										} else if (positionvalue == Constants.EN_LAST)
										{
											condition = new MasterReference(
													MasterType.CONDITION_LAST,
													UiText.LAST_USE, spmmaster,
													1);
										} else if (positionvalue == Constants.EN_REST)
										{
											condition = new MasterReference(
													MasterType.CONDITION_REST,
													UiText.REST_USE, spmmaster,
													1);
										}
									}
									position.addChild(condition);
								}
							}
							pagesequencemaster.addChild(position);
						}
					}
				}
			}
		}
		MasterTreeModel model = new MasterTreeModel(pagesequencemaster);
		return model;
	}

	public Control getControl()
	{
		return control;
	}

	public void setControl(Control control)
	{
		this.control = control;
		mastertree.setControl(control);
	}

	public Object getMaster()
	{
		return mastertree.getMaster();
	}

	public boolean setMaster()
	{
		return mastertree.setMaster();
	}
}
