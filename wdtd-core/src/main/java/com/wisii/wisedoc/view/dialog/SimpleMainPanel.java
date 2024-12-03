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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterReference;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SinglePageMasterReference;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.view.ui.StaticContentManeger;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class SimpleMainPanel extends JPanel
{

	boolean simple;

	Object pagemaster;

	JPanel simplepanel = new JPanel();

	JTabbedPane tablepanel = new JTabbedPane();

	JCheckBox firstrest = new JCheckBox(UiText.FIRST_DIFFENT);

	JCheckBox lastrest = new JCheckBox(UiText.LAST_DIFFENT);

	JCheckBox oddeven = new JCheckBox(UiText.ODD_EVEN_DIFFENT);

	JCheckBox blankorno = new JCheckBox(UiText.BLANK_NOBLANK_DIFFENT);

	SetPageLayoutPanel onepanel;

	SetPageLayoutPanel firstpanel;

	SetPageLayoutPanel lastpanel;

	SetPageLayoutPanel restpanel;

	SetPageLayoutPanel oddpanel;

	SetPageLayoutPanel evenpanel;

	SetPageLayoutPanel blankpanel;

	SimplePageMaster defaultspm;

	public SimpleMainPanel(boolean issimple)
	{
		super();
		simple = issimple;
		initComponents();
	}

	public SimpleMainPanel(Object pagemaster, boolean issimple)
	{
		super();
		simple = issimple;
		this.pagemaster = pagemaster;
		initComponents();
	}

	private void initComponents()
	{
		onepanel = new SetPageLayoutPanel();
		firstpanel = new SetPageLayoutPanel();
		lastpanel = new SetPageLayoutPanel();
		restpanel = new SetPageLayoutPanel();
		oddpanel = new SetPageLayoutPanel();
		evenpanel = new SetPageLayoutPanel();
		blankpanel = new SetPageLayoutPanel();
		this.setLayout(new BorderLayout());
		JLabel psmlabel = new JLabel(UiText.CURRENT_PAGE_SEQUENCE_PSM);
		simplepanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
		simplepanel.add(psmlabel);
		simplepanel.add(firstrest);
		simplepanel.add(lastrest);
		simplepanel.add(oddeven);
		simplepanel.add(blankorno);
		this.add(simplepanel, BorderLayout.NORTH);
		this.add(tablepanel, BorderLayout.CENTER);
		setInitialState();

		firstrest.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				boolean blankornocheck = blankorno.isSelected();
				boolean firstrestcheck = firstrest.isSelected();
				boolean lastrestcheck = lastrest.isSelected();
				boolean oddevencheck = oddeven.isSelected();
				setTablepanelCurrentStatic(firstrestcheck, lastrestcheck,
						oddevencheck, blankornocheck);
			}
		});
		lastrest.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				boolean blankornocheck = blankorno.isSelected();
				boolean firstrestcheck = firstrest.isSelected();
				boolean lastrestcheck = lastrest.isSelected();
				boolean oddevencheck = oddeven.isSelected();
				setTablepanelCurrentStatic(firstrestcheck, lastrestcheck,
						oddevencheck, blankornocheck);
			}
		});
		oddeven.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				boolean blankornocheck = blankorno.isSelected();
				boolean firstrestcheck = firstrest.isSelected();
				boolean lastrestcheck = lastrest.isSelected();
				boolean oddevencheck = oddeven.isSelected();
				setTablepanelCurrentStatic(firstrestcheck, lastrestcheck,
						oddevencheck, blankornocheck);
			}
		});
		blankorno.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				boolean blankornocheck = blankorno.isSelected();
				boolean firstrestcheck = firstrest.isSelected();
				boolean lastrestcheck = lastrest.isSelected();
				boolean oddevencheck = oddeven.isSelected();
				setTablepanelCurrentStatic(firstrestcheck, lastrestcheck,
						oddevencheck, blankornocheck);
			}
		});
	}

	public void setInitialState()
	{
		tablepanel.removeAll();
		if (simple)
		{
			if (pagemaster != null)
			{
				if (pagemaster instanceof PageSequenceMaster)
				{
					PageSequenceMaster currentpsm = (PageSequenceMaster) pagemaster;
					List<SubSequenceSpecifier> subss = currentpsm
							.getSubsequenceSpecifiers();
					SubSequenceSpecifier onlyone = subss.get(0);
					RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) onlyone;
					List<ConditionalPageMasterReference> listcpmr = rpma
							.getPageMasterRefs();
					Map<Integer, SimplePageMaster> spmmap = new HashMap<Integer, SimplePageMaster>();
					if (listcpmr != null && !listcpmr.isEmpty())
					{
						for (ConditionalPageMasterReference current : listcpmr)
						{
							SimplePageMaster currentspm = current
									.getMasterReference();
							int pp = current.getPagePosition();
							if (pp == Constants.EN_ANY)
							{
								int oe = current.getOddOrEven();
								if (oe == Constants.EN_ANY)
								{
									int bn = current.getBlankOrNotBlank();
									if (bn == Constants.EN_BLANK)
									{
										spmmap.put(bn, currentspm);
									} else
									{
										spmmap.put(Constants.EN_REST,
												currentspm);
									}
								} else
								{
									spmmap.put(oe, currentspm);
								}

							} else
							{
								spmmap.put(pp, currentspm);
							}
						}
					}
					if (spmmap.containsKey(Constants.EN_REST))
					{
						restpanel.setSimplePageMaster(MasterTree
								.getModel(spmmap.get(Constants.EN_REST)));
						tablepanel.add(UiText.REST_SPM, restpanel);
						defaultspm = MasterTree.getClone(
								restpanel.getSimplePageMaster(),
								StaticContentManeger.getScmap())
								.getSimplePageMaster();
					} else
					{
						restpanel.setSimplePageMaster(MasterTree.getClone(
								defaultspm, StaticContentManeger.getScmap()));
						defaultspm = MasterTree.getClone(
								spmmap.get(Constants.EN_ODD),
								StaticContentManeger.getScmap())
								.getSimplePageMaster();
					}
					if (spmmap.containsKey(Constants.EN_FIRST))
					{
						firstpanel.setSimplePageMaster(MasterTree
								.getModel(spmmap.get(Constants.EN_FIRST)));
						firstrest.setSelected(true);
						tablepanel.add(UiText.FIRST_SPM, firstpanel);
					} else
					{
						firstpanel.setSimplePageMaster(MasterTree.getClone(
								defaultspm, StaticContentManeger.getScmap()));
					}
					if (spmmap.containsKey(Constants.EN_LAST))
					{
						lastpanel.setSimplePageMaster(MasterTree
								.getModel(spmmap.get(Constants.EN_LAST)));
						lastrest.setSelected(true);
						tablepanel.add(UiText.LAST_SPM, lastpanel);
					} else
					{
						lastpanel.setSimplePageMaster(MasterTree.getClone(
								defaultspm, StaticContentManeger.getScmap()));
					}
					if (spmmap.containsKey(Constants.EN_ODD))
					{
						oddpanel.setSimplePageMaster(MasterTree.getModel(spmmap
								.get(Constants.EN_ODD)));
						oddeven.setSelected(true);
						tablepanel.add(UiText.ODD_SPM, oddpanel);
					} else
					{
						oddpanel.setSimplePageMaster(MasterTree.getClone(
								defaultspm, StaticContentManeger.getScmap()));
					}
					if (spmmap.containsKey(Constants.EN_EVEN))
					{
						evenpanel.setSimplePageMaster(MasterTree
								.getModel(spmmap.get(Constants.EN_EVEN)));
						oddeven.setSelected(true);
						tablepanel.add(UiText.EVEN_SPM, evenpanel);
					} else
					{
						evenpanel.setSimplePageMaster(MasterTree.getClone(
								defaultspm, StaticContentManeger.getScmap()));
					}
					if (spmmap.containsKey(Constants.EN_BLANK))
					{
						blankpanel.setSimplePageMaster(MasterTree
								.getModel(spmmap.get(Constants.EN_BLANK)));
						blankorno.setSelected(true);
						tablepanel.add(UiText.BLANK_SPM, blankpanel);
					} else
					{
						blankpanel.setSimplePageMaster(MasterTree.getClone(
								defaultspm, StaticContentManeger.getScmap()));
					}
					onepanel.setSimplePageMaster(MasterTree.getClone(
							defaultspm, StaticContentManeger.getScmap()));
					return;
				}
			}
		}
		setNoSimple();
	}

	private void setNoSimple()
	{
		firstrest.setSelected(false);
		lastrest.setSelected(false);
		oddeven.setSelected(false);
		blankorno.setSelected(false);
		if (pagemaster instanceof SimplePageMaster)
		{
			onepanel.setSimplePageMaster(MasterTree
					.getModel((SimplePageMaster) pagemaster));
			defaultspm = (SimplePageMaster) pagemaster;
		} else if (pagemaster instanceof PageSequenceMaster)
		{
			PageSequenceMaster currentpsm = (PageSequenceMaster) pagemaster;
			List<SubSequenceSpecifier> subss = currentpsm
					.getSubsequenceSpecifiers();
			SubSequenceSpecifier onlyone = subss.get(0);
			SimplePageMaster one = null;
			if (onlyone instanceof SinglePageMasterReference)
			{
				one = ((SinglePageMasterReference) onlyone)
						.getMasterReference();
			} else if (onlyone instanceof RepeatablePageMasterReference)
			{
				one = ((RepeatablePageMasterReference) onlyone)
						.getMasterReference();
			} else if (onlyone instanceof RepeatablePageMasterAlternatives)
			{
				RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) onlyone;
				List<ConditionalPageMasterReference> listcpmr = rpma
						.getPageMasterRefs();
				one = listcpmr.get(0).getMasterReference();
			}
			defaultspm = MasterTree.getClone(one,
					StaticContentManeger.getScmap()).getSimplePageMaster();
			onepanel.setSimplePageMaster(MasterTree.getModel(one));
		} else
		{
			onepanel.setSimplePageMaster(MasterTree.getModel(null));
		}
		tablepanel.add(UiText.ONE_SPM, onepanel);
		firstpanel.setSimplePageMaster(MasterTree.getClone(defaultspm,
				StaticContentManeger.getScmap()));
		lastpanel.setSimplePageMaster(MasterTree.getClone(defaultspm,
				StaticContentManeger.getScmap()));
		oddpanel.setSimplePageMaster(MasterTree.getClone(defaultspm,
				StaticContentManeger.getScmap()));
		evenpanel.setSimplePageMaster(MasterTree.getClone(defaultspm,
				StaticContentManeger.getScmap()));
		restpanel.setSimplePageMaster(MasterTree.getClone(defaultspm,
				StaticContentManeger.getScmap()));
		blankpanel.setSimplePageMaster(MasterTree.getClone(defaultspm,
				StaticContentManeger.getScmap()));
	}

	public void setMaster()
	{
		int any = Constants.EN_ANY;
		boolean firstrestcheck = firstrest.isSelected();
		boolean lastrestcheck = lastrest.isSelected();
		boolean oddevencheck = oddeven.isSelected();
		boolean blankornocheck = blankorno.isSelected();
		if (firstrestcheck || lastrestcheck || oddevencheck || blankornocheck)
		{
			List<ConditionalPageMasterReference> listcpmr = new ArrayList<ConditionalPageMasterReference>();
			if (firstrestcheck)
			{
				SimplePageMaster firstspm = firstpanel.getSimplePageMaster();
				StaticContentManeger.addRealstaticcontent(firstspm);
				ConditionalPageMasterReference first = new ConditionalPageMasterReference(
						firstspm, Constants.EN_FIRST, any, any);
				listcpmr.add(first);
			}
			if (lastrestcheck)
			{
				SimplePageMaster lastspm = lastpanel.getSimplePageMaster();
				StaticContentManeger.addRealstaticcontent(lastspm);
				ConditionalPageMasterReference last = new ConditionalPageMasterReference(
						lastspm, Constants.EN_LAST, any, any);
				listcpmr.add(last);
			}
			if (blankornocheck)
			{
				SimplePageMaster blankspm = blankpanel.getSimplePageMaster();
				StaticContentManeger.addRealstaticcontent(blankspm);
				ConditionalPageMasterReference blank = new ConditionalPageMasterReference(
						blankspm, any, any, Constants.EN_BLANK);
				listcpmr.add(blank);
			}
			if (!oddevencheck)
			{
				SimplePageMaster restspm = restpanel.getSimplePageMaster();
				StaticContentManeger.addRealstaticcontent(restspm);
				ConditionalPageMasterReference rest = new ConditionalPageMasterReference(
						restspm, Constants.EN_ANY, any, any);
				listcpmr.add(rest);
			} else
			{
				SimplePageMaster oddspm = oddpanel.getSimplePageMaster();
				StaticContentManeger.addRealstaticcontent(oddspm);
				ConditionalPageMasterReference odd = new ConditionalPageMasterReference(
						oddspm, any, Constants.EN_ODD, any);
				listcpmr.add(odd);
				SimplePageMaster evenspm = evenpanel.getSimplePageMaster();
				StaticContentManeger.addRealstaticcontent(evenspm);
				ConditionalPageMasterReference even = new ConditionalPageMasterReference(
						evenspm, any, Constants.EN_EVEN, any);
				listcpmr.add(even);
			}
			RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
					new EnumNumber(RepeatablePageMasterAlternatives.INFINITE,
							-1), listcpmr);
			List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
			subSequenceSpecifiers.add(rpm);
			PageSequenceMaster psm = new PageSequenceMaster(
					subSequenceSpecifiers);
			pagemaster = psm;
		} else
		{
			SimplePageMaster one = onepanel.getSimplePageMaster();
			StaticContentManeger.addRealstaticcontent(one);
			pagemaster = one;
		}
	}

	public Object getSimplePageMaster()
	{
		return pagemaster;
	}

	private List<String> getCurrentAllTitles()
	{
		List<String> alltitles = new ArrayList<String>();
		int size = tablepanel.getTabCount();
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
			{
				String current = tablepanel.getTitleAt(i);
				alltitles.add(current);
			}
		}
		return alltitles;
	}

	private List<String> getNewAllTitles(boolean first, boolean last,
			boolean oddeven, boolean blank)
	{
		List<String> alltitles = new ArrayList<String>();
		if (first || last || oddeven || blank)
		{
			if (first)
			{
				alltitles.add(UiText.FIRST_SPM);
			}
			if (last)
			{
				alltitles.add(UiText.LAST_SPM);
			}
			if (blank)
			{
				alltitles.add(UiText.BLANK_SPM);
			}
			if (oddeven)
			{
				alltitles.add(UiText.ODD_SPM);
				alltitles.add(UiText.EVEN_SPM);
			} else
			{
				alltitles.add(UiText.REST_SPM);
			}
		} else
		{
			alltitles.add(UiText.ONE_SPM);
		}
		return alltitles;
	}

	private void setTablepanelCurrentStatic(boolean first, boolean last,
			boolean oddeven, boolean blank)
	{
		List<String> oldlist = getCurrentAllTitles();
		List<String> newlist = getNewAllTitles(first, last, oddeven, blank);
		for (String current : newlist)
		{
			if (oldlist.contains(current))
			{
				oldlist.remove(current);
			} else
			{
				tablepanel.add(current, getPanelByTitle(current));
			}
		}
		for (String current : oldlist)
		{
			tablepanel.remove(getPanelByTitle(current));
		}
	}

	private SetPageLayoutPanel getPanelByTitle(String title)
	{
		SetPageLayoutPanel resultpanel = null;
		if (title != null)
		{
			if (title.equals(UiText.ONE_SPM))
			{
				resultpanel = onepanel;
			} else if (title.equals(UiText.FIRST_SPM))
			{
				resultpanel = firstpanel;
			} else if (title.equals(UiText.LAST_SPM))
			{
				resultpanel = lastpanel;
			} else if (title.equals(UiText.ODD_SPM))
			{
				resultpanel = oddpanel;
			} else if (title.equals(UiText.EVEN_SPM))
			{
				resultpanel = evenpanel;
			} else if (title.equals(UiText.BLANK_SPM))
			{
				resultpanel = blankpanel;
			} else if (title.equals(UiText.REST_SPM))
			{
				resultpanel = restpanel;
			}
		}
		return resultpanel;
	}
}
