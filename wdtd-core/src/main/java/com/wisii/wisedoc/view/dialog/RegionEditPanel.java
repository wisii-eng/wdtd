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
import java.awt.Dimension;
import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.TreeNode;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.util.UiUtil;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.WisedocEditComponent;
import com.wisii.wisedoc.view.ui.model.CustomizeSimplePageMasterModel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.UpdateSPMProperty;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

@SuppressWarnings("serial")
public class RegionEditPanel extends JPanel implements UpdateSPMProperty
{

	JPanel previewPanel;

	StaticContent currentsc;

	CustomizeSimplePageMasterModel cspmm;

	WiseDocDocument doc = new WiseDocDocument();

	String name;

	int regionID;

	public RegionEditPanel(StaticContent sc,
			CustomizeSimplePageMasterModel spmm, int type)
	{
		setLayout(new BorderLayout());
		currentsc = sc;
		regionID = type;
		cspmm = spmm;
		name = getStaticContentName();
		createRegionContent();
		previewPanel = new PreviewPanel();
		this.add(previewPanel, BorderLayout.CENTER);
	}

	private class PreviewPanel extends JPanel
	{

		public PreviewPanel()
		{
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(100, 200));
			this.add(UiUtil.borrowRibbonUI(), BorderLayout.NORTH);
			previewComponent = new WisedocEditComponent(doc);
			RibbonUpdateManager.Instance
					.setDocumentEditorListener((WisedocEditComponent) previewComponent);
			JScrollPane jsp = new JScrollPane(previewComponent);
			WisedocUtil.disableScroPaneKeyborde(jsp);
			this.add(jsp, BorderLayout.CENTER);
		}

	}

	private JComponent previewComponent;

	@Override
	public void update()
	{
		previewPanel.removeAll();
		previewPanel.add(UiUtil.borrowRibbonUI(), BorderLayout.NORTH);
		previewComponent = new WisedocEditComponent(doc);
		JScrollPane jsp = new JScrollPane(previewComponent);
		WisedocUtil.disableScroPaneKeyborde(jsp);
		previewPanel.add(jsp, BorderLayout.CENTER);
		UiUtil.updateUI(previewPanel);
	}

	public String getStaticContentName()
	{
		String regionname = "";
		if (regionID == Constants.FO_REGION_BEFORE)
		{
			regionname = cspmm.getRegionBeforeModel().getRegionbeforemodel()
					.getRegionName();
		} else if (regionID == Constants.FO_REGION_AFTER)
		{
			regionname = cspmm.getRegionAfterModel().getRegionaftermodel()
					.getRegionName();
		} else if (regionID == Constants.FO_REGION_START)
		{
			regionname = cspmm.getRegionStartModel().getRegionstartmodel()
					.getRegionName();
		} else if (regionID == Constants.FO_REGION_END)
		{
			regionname = cspmm.getRegionEndModel().getRegionendmodel()
					.getRegionName();
		}
		return regionname;
	}

	public void createRegionContent()
	{
		TreeNode node = doc.getChildAt(0);
		if (!(node instanceof PageSequence))
		{
			return;
		}
		PageSequence sequence = (PageSequence) node;
		if (currentsc == null)
		{
			StaticContent beforeflow = new StaticContent(name);
			Block beforblock = new Block();
			final TextInline textbefore = new TextInline(new Text(' '), null);
			beforblock.add(textbefore);
			beforeflow.add(beforblock);
			currentsc = beforeflow;
		}
		WisedocUtil.addStaticContent(name, currentsc, sequence);
		sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, cspmm
				.getSimplePageMaster());
	}

	public StaticContent getStaticContent()
	{
		PageSequence currentps = ((PageSequence) doc.getChildAt(0));
		StaticContent sc = null;
		Enumeration<CellElement> flows = currentps.children();
		while (flows.hasMoreElements())
		{
			CellElement cellElement = flows.nextElement();
			if (cellElement instanceof StaticContent)
			{
				StaticContent current = (StaticContent) cellElement;
				String name = currentsc.getFlowName();
				if (name.equals(current.getFlowName()))
				{
					sc = current;
				}
			}
		}
		return sc;
	}
}
