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
package com.wisii.wisedoc.view.ui.update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.tree.TreeNode;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.RegionAfter;
import com.wisii.wisedoc.document.attribute.RegionBefore;
import com.wisii.wisedoc.document.attribute.RegionEnd;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.WisedocEditComponent;
import com.wisii.wisedoc.view.ui.model.RegionAfterModel;
import com.wisii.wisedoc.view.ui.model.RegionBeforeModel;
import com.wisii.wisedoc.view.ui.model.RegionEndModel;
import com.wisii.wisedoc.view.ui.model.RegionStartModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel;

/**
 * 处理有关region区域需要的document和预览区域的生成
 * 
 * @author 闫舒寰
 * @version 1.0 2009/03/03
 */
public enum RegionDocumentManager {

	Instance;

	// 显示region的组件
	private Map<Integer, JComponent> regionPreComponents;
	// region所对应的文档
	private Map<Integer, Document> regionDocuments;
	// region所对应的页布局
	// private Map<Integer, Object> regionPreSPM;

	private static int uniqueID;

	public static int getUniqueID() {
		return ++uniqueID;
	}

	// 当前章节中所有的区域的流
	private List<StaticContent> regionFlows;
	//保存当前主面板的region列表
	private Map<String, StaticContent> regionFlowNameMap = new HashMap<String, StaticContent>();

	public void clearRegionDocs() {
		regionPreComponents = null;
		regionDocuments = null;
		// regionPreSPM = null;
		regionFlows = null;
		regionFlowNameMap.clear();
	}

	/**
	 * 根据所给的regionID把region内容复制并且生成region区域
	 * 
	 * @param regionID
	 *            Constants中的regionID，例如：Constants.FO_REGION_BEFORE
	 * @return 给定regionID的预览区
	 */
	public JComponent getRegionPreviewComponent(final int regionID) {

		if (regionPreComponents == null) {
			regionPreComponents = new HashMap<Integer, JComponent>();
		}

		// 通过缓存来引用
		if (regionPreComponents.get(regionID) == null) {
			final WisedocEditComponent wec = new WisedocEditComponent(this
					.getRegionDocument(regionID));
			RibbonUpdateManager.Instance.setDocumentEditorListener(wec);
			regionPreComponents.put(regionID, wec);
			return wec;
		} else {
			final JComponent jc = regionPreComponents.get(regionID);
			if (jc instanceof AbstractEditComponent) {
				final AbstractEditComponent aec = (AbstractEditComponent) jc;
				RibbonUpdateManager.Instance.setDocumentEditorListener(aec);
			}
			return jc;
		}
	}

	// 获得当前区域的region面板
	private Document getRegionDocument(final int regionID) {

		if (regionDocuments == null) {
			initialRegionPreDocument();
		}

		if (regionDocuments.get(regionID) != null) {
			return regionDocuments.get(regionID);
		} else {

			final WiseDocDocument doc = new WiseDocDocument();
			final TreeNode node = doc.getChildAt(0);
			if (!(node instanceof PageSequence)) {
//				System.out.println("is Null");
				return null;
			}
			// 新建document的sequence
			final PageSequence sequence = (PageSequence) node;

			final PageSequence sequenceTemp = RibbonUpdateManager.Instance
					.getMainPageSequence();

			String regionName = null;

			// region name有可能在simple page master里，也有可能在page sequence master里
			// 这个spmTemp应该存有目前编辑的
			Object spmTemp = null;

			if (sequenceTemp.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER) != null) {
				spmTemp = sequenceTemp
						.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);
			} else if (sequenceTemp
					.getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER) != null) {
				spmTemp = sequenceTemp
						.getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER);
			}

			SimplePageMaster mainSPM = null;
			if (spmTemp instanceof SimplePageMaster) {
				mainSPM = (SimplePageMaster) spmTemp;
				if (mainSPM.getRegion(regionID) != null) {
					regionName = mainSPM.getRegion(regionID).getRegionName();
				}
				// System.out.println(regionName);
			}

			// 针对页布局序列这种情况
			if (spmTemp instanceof PageSequenceMaster) {
				mainSPM = SinglePagelayoutModel.Instance.getMainSPMM()
						.getSimplePageMaster();
				if (mainSPM.getRegion(regionID) != null) {
					regionName = mainSPM.getRegion(regionID).getRegionName();
				}
			}

			if (regionName != null) {
				for (int i = 0; i < sequenceTemp.getChildCount(); i++) {
					if (sequenceTemp.getChildAt(i) instanceof StaticContent) {
						final StaticContent sc = (StaticContent) sequenceTemp
								.getChildAt(i);
						if (sc.getFlowName().equals(regionName)) {
							sequence.add((CellElement) sc.clone());
							break;
						}
					}
				}
			}

			if (mainSPM == null) {
				return null;
			}

			// 根据不同属性来配置页面
			final SimplePageMasterModel spmm = new SimplePageMasterModel.Builder()
					.pageWidth(mainSPM.getPageWidth()).pageHeight(
							mainSPM.getPageHeight()).build();
			switch (regionID) {
			case Constants.FO_REGION_BEFORE:
				spmm.setRegionBefore(new RegionBeforeModel.Builder()
						.regionBefore(
								(RegionBefore) mainSPM.getRegion(regionID))
						.build().getRegionBefore());
				spmm.setRegionAfter(null);
				spmm.setRegionStart(null);
				spmm.setRegionEnd(null);
				break;
			case Constants.FO_REGION_AFTER:
				spmm.setRegionBefore(null);
				spmm.setRegionAfter(new RegionAfterModel.Builder().regionAfter(
						(RegionAfter) mainSPM.getRegion(regionID)).build()
						.getRegionAfter());
				spmm.setRegionStart(null);
				spmm.setRegionEnd(null);
				break;
			case Constants.FO_REGION_START:
				spmm.setRegionBefore(null);
				spmm.setRegionAfter(null);
				spmm.setRegionStart(new RegionStartModel.Builder().regionStart(
						(RegionStart) mainSPM.getRegion(regionID)).build()
						.getRegionStart());
				spmm.setRegionEnd(null);
				break;
			case Constants.FO_REGION_END:
				spmm.setRegionBefore(null);
				spmm.setRegionAfter(null);
				spmm.setRegionStart(null);
				spmm.setRegionEnd(new RegionEndModel.Builder().regionEnd(
						(RegionEnd) mainSPM.getRegion(regionID)).build()
						.getRegionEnd());
				break;

			default:
				break;
			}
			sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, spmm
					.getSimplePageMaster());
			// regionPreSPM.put(regionID, spmm.getSimplePageMaster());
			regionDocuments.put(regionID, doc);
			return doc;
		}
	}


	/**
	 * 当弹出四区域属性设置对话框的时候需要
	 */
	public void initialRegionFlows() {
		initialFlows();
		initialRegionPreDocument();
	}
	
	private void initialFlows() {
		//以下是获得主面板用户当前操作的四区域流
		final PageSequence ps = RibbonUpdateManager.Instance.getMainPageSequence();
		
		if (regionFlows == null) {
			regionFlows = new LinkedList<StaticContent>();
		} else {
			regionFlows.clear();
		}

		for (int i = 0; i < ps.getChildCount(); i++) {
			final Object o = ps.getChildAt(i);
			if (o instanceof StaticContent) {
				final StaticContent sc = (StaticContent) o;
				regionFlowNameMap.put(sc.getFlowName(), sc);
				regionFlows.add(sc);
			}
		}
		// System.out.println(currentPSRegions);
	}

	// 初始化region编辑所需要的四个区域，主要是针对四区域编辑对话框初始化四个区域
	private void initialRegionPreDocument() {
		regionDocuments = new HashMap<Integer, Document>();
		// regionPreSPM = new HashMap<Integer, Object>();

		regionDocuments.put(Constants.FO_REGION_BEFORE,
				getMainRegions(Constants.FO_REGION_BEFORE));
		regionDocuments.put(Constants.FO_REGION_AFTER,
				getMainRegions(Constants.FO_REGION_AFTER));
		regionDocuments.put(Constants.FO_REGION_START,
				getMainRegions(Constants.FO_REGION_START));
		regionDocuments.put(Constants.FO_REGION_END,
				getMainRegions(Constants.FO_REGION_END));
	}
	
	//为四区域属性设置对话框提供后台数据支持
	private Document getMainRegions(final int regionID) {

		final WiseDocDocument doc = new WiseDocDocument();
		final TreeNode node = doc.getChildAt(0);
		if (!(node instanceof PageSequence)) {
//			System.out.println("is Null");
			return null;
		}
		final PageSequence sequence = (PageSequence) node;

		final PageSequence sequenceTemp = RibbonUpdateManager.Instance
				.getMainPageSequence();

		final Object objectTemp = sequenceTemp
				.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);

		String regionName = null;
		SimplePageMaster mainSPM = null;
		if (objectTemp instanceof SimplePageMaster) {
			mainSPM = (SimplePageMaster) objectTemp;
			if (mainSPM.getRegion(regionID) != null) {
				regionName = mainSPM.getRegion(regionID).getRegionName();
			}
		}

		if (regionName != null) {
			for (int i = 0; i < sequenceTemp.getChildCount(); i++) {
				if (sequenceTemp.getChildAt(i) instanceof StaticContent) {
					final StaticContent sc = (StaticContent) sequenceTemp
							.getChildAt(i);
					if (sc.getFlowName().equals(regionName)) {
						sequence.add((CellElement) sc.clone());
						break;
					}
				}
			}
		}

		if (mainSPM == null) {
			return null;
		}

		// 根据不同属性来配置页面
		final SimplePageMasterModel spmm = new SimplePageMasterModel.Builder()
				.pageWidth(mainSPM.getPageWidth()).pageHeight(
						mainSPM.getPageHeight()).build();
		switch (regionID) {
		case Constants.FO_REGION_BEFORE:
			spmm.setRegionBefore(new RegionBeforeModel.Builder().regionBefore(
					(RegionBefore) mainSPM.getRegion(regionID)).build()
					.getRegionBefore());
			spmm.setRegionAfter(null);
			spmm.setRegionStart(null);
			spmm.setRegionEnd(null);
			break;
		case Constants.FO_REGION_AFTER:
			spmm.setRegionBefore(null);
			spmm.setRegionAfter(new RegionAfterModel.Builder().regionAfter(
					(RegionAfter) mainSPM.getRegion(regionID)).build()
					.getRegionAfter());
			spmm.setRegionStart(null);
			spmm.setRegionEnd(null);
			break;
		case Constants.FO_REGION_START:
			spmm.setRegionBefore(null);
			spmm.setRegionAfter(null);
			spmm.setRegionStart(new RegionStartModel.Builder().regionStart(
					(RegionStart) mainSPM.getRegion(regionID)).build()
					.getRegionStart());
			spmm.setRegionEnd(null);
			break;
		case Constants.FO_REGION_END:
			spmm.setRegionBefore(null);
			spmm.setRegionAfter(null);
			spmm.setRegionStart(null);
			spmm.setRegionEnd(new RegionEndModel.Builder().regionEnd(
					(RegionEnd) mainSPM.getRegion(regionID)).build()
					.getRegionEnd());
			break;

		default:
			break;
		}
		sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, spmm
				.getSimplePageMaster());
		// regionPreSPM.put(regionID, spmm.getSimplePageMaster());
		regionDocuments.put(regionID, doc);

		return doc;
	}
	
	/**
	 * 根据给定的regionDI删除预览面板中的区域
	 * @param regionID Constants常量接口中的regionID
	 */
	public void deleteRegion(final int regionID) {
		switch (regionID) {
		case Constants.FO_REGION_BEFORE:
			regionDocuments.put(Constants.FO_REGION_BEFORE, null);
			break;
		case Constants.FO_REGION_AFTER:
			regionDocuments.put(Constants.FO_REGION_AFTER, null);
			break;
		case Constants.FO_REGION_START:
			regionDocuments.put(Constants.FO_REGION_START, null);
			break;
		case Constants.FO_REGION_END:
			regionDocuments.put(Constants.FO_REGION_END, null);
			break;

		default:
			break;
		}
	}

	/**
	 * 根据regionID和region名字来创建具有名字的区域
	 * @param regionID Constants中的regionID，有四个
	 * @param regionName region的名字
	 * @return 根据regionID和名字创建的Document
	 */
	public Document createRegionContent(final int regionID, final String regionName) {
		final WiseDocDocument doc = new WiseDocDocument();
		final TreeNode node = doc.getChildAt(0);
		if (!(node instanceof PageSequence)) {
//			System.out.println("is Null");
			return null;
		}
		final PageSequence sequence = (PageSequence) node;

		final SimplePageMasterModel mainSPM = SinglePagelayoutModel.Instance
				.getMainSPMM();
		final SimplePageMasterModel spmm = new SimplePageMasterModel.Builder()
				.pageWidth(mainSPM.getPageWidth()).pageHeight(
						mainSPM.getPageHeight()).build();

		// sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, new
		// SimplePageMasterModel.Builder().defaultSimplePageMaster().build().getSimplePageMaster());

		// 添加内容
		final StaticContent beforeflow = new StaticContent(regionName);
		final Block beforblock = new Block();
		final TextInline textbefore = new TextInline(new Text('a'), null);
		beforblock.add(textbefore);
		insertString(beforblock, "这里添加内容", textbefore);
		beforeflow.add(beforblock);
		sequence.add(beforeflow);

		// 添加样式
		// spmm.setRegionBefore(new
		// RegionBeforeModel.Builder().regionName(regionName).build().getRegionBefore());

		// sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER,
		// spmm.getSimplePageMaster());
		// System.out.println("flow name: " + beforeflow.getFlowName() +
		// " region name: " + spmm.getRegionBeforeModel().getRegionName());
		
//		spmm.getRegionBodyModel().setBodyBackgroundColor(Color.gray);
//		sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, spmm.getSimplePageMaster());

		// 还需要把新创建的流保留下来
		switch (regionID) {

		case Constants.FO_REGION_BEFORE:
			spmm.setRegionBefore(new RegionBeforeModel.Builder().regionName(
					regionName).build().getRegionBefore());
			spmm.setRegionAfter(null);
			spmm.setRegionStart(null);
			spmm.setRegionEnd(null);
			//设置预览的版新区域的margin以便不和四区域重叠
			spmm.getRegionBodyModel().setMarginTop(spmm.getRegionBeforeModel().getExtent());
			sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, spmm
					.getSimplePageMaster());
			regionDocuments.put(Constants.FO_REGION_BEFORE, doc);
			break;
		case Constants.FO_REGION_AFTER:
			spmm.setRegionBefore(null);
			spmm.setRegionAfter(new RegionAfterModel.Builder().regionName(
					regionName).build().getRegionAfter());
			spmm.setRegionStart(null);
			spmm.setRegionEnd(null);
			
			spmm.getRegionBodyModel().setMarginBottom(spmm.getRegionAfterModel().getExtent());
			sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, spmm
					.getSimplePageMaster());
			regionDocuments.put(Constants.FO_REGION_AFTER, doc);
			break;
		case Constants.FO_REGION_START:
			spmm.setRegionBefore(null);
			spmm.setRegionAfter(null);
			spmm.setRegionStart(new RegionStartModel.Builder().regionName(
					regionName).build().getRegionStart());
			spmm.setRegionEnd(null);
			
			spmm.getRegionBodyModel().setMarginLeft(spmm.getRegionStartModel().getExtent());
			sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, spmm
					.getSimplePageMaster());
			regionDocuments.put(Constants.FO_REGION_START, doc);
			break;
		case Constants.FO_REGION_END:
			spmm.setRegionBefore(null);
			spmm.setRegionAfter(null);
			spmm.setRegionStart(null);
			spmm.setRegionEnd(new RegionEndModel.Builder().regionName(
					regionName).build().getRegionEnd());
			
			spmm.getRegionBodyModel().setMarginRight(spmm.getRegionEndModel().getExtent());
			sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, spmm
					.getSimplePageMaster());
			regionDocuments.put(Constants.FO_REGION_END, doc);
			break;

		default:
			break;
		}

		return doc;
	}

	private void insertString(final Block block, final String text, final TextInline textinline) {
		if (block != null && textinline != null && text != null
				&& !text.isEmpty()) {
			final int index = block.getIndex(textinline) + 1;
			final List<CellElement> lines = new ArrayList<CellElement>();
			final int size = text.length();
			for (int i = 0; i < size; i++) {
				final char c = text.charAt(i);
				final TextInline tmpinline = new TextInline(new Text(c), null);
				lines.add(tmpinline);
			}
			block.insert(lines, index);
		}
	}

	// 该方法用来设置四个区域的改变的内容到当前章节的四个区域中
	public void setSPMRegionContent() {
		final List<Document> staticdocs = RegionDocumentManager.Instance
				.getRegionDocumentsList();

		// System.out.println("size: " + staticdocs.size());

		// 获得当前编辑的章节
		final PageSequence editps = RibbonUpdateManager.Instance.getMainPageSequence();
		
		if (editps == null) {
			return;
		}
		final Document editdoc = (Document) editps.getParent();
		if (editdoc == null) {
			return;
		}
		if (staticdocs != null && !staticdocs.isEmpty()) {
			final List<CellElement> todeletes = new ArrayList<CellElement>();
			final List<CellElement> toaddeds = new ArrayList<CellElement>();
			final int size = staticdocs.size();
			for (int i = 0; i < size; i++) {
				final Document staticdoc = staticdocs.get(i);
				if (staticdoc != null) {
					final CellElement ps = (CellElement) staticdoc.getChildAt(0);
					final StaticContent editcontent = (StaticContent) ps.getChildAt(1);
					String oldName = null;
					if (editcontent != null) {
						oldName = editcontent.getFlowName();
					}
					StaticContent oldcontent = null;
					if (oldName != null) {
						oldcontent = editps.getStaticContent(oldName);
					}
					// 如果当前编辑的PageSequence包含有编辑后的内容流，则将原来的流添加到删除列表中
					if (oldcontent != null) {
						todeletes.add(oldcontent);
					}
					toaddeds.add(editcontent);
				}
			}
			if (!todeletes.isEmpty()) {
				editdoc.deleteElements(todeletes);
			}
			if (!toaddeds.isEmpty()) {
				editdoc.insertElements(toaddeds, editps, editps.getChildCount());
			}
		}
	}

	// 返回各个region文档的列表，以便添加到主文档中
	public List<Document> getRegionDocumentsList() {
		
		if (regionDocuments == null) {
			initialRegionPreDocument();
		}
		
		final List<Document> temp = new LinkedList<Document>();
		
		//这里这么处理因为以下两点
		//1、有可能有区域为空，而把null值放到list中，模型层会有问题
		//2、这里有可能会得到四个一样document，是当四个区域都指向一个流的时候，这样设置到模型层也会有偶问题
		for (final Document document : regionDocuments.values()) {
			if (document != null && !temp.contains(document)) {
				temp.add(document);
			}
		}

		return /*new ArrayList<Document>(regionDocuments.values())*/temp;
	}

	public Map<Integer, Document> getRegionDocumentsMap() {

		if (regionDocuments == null) {
			return new HashMap<Integer, Document>();
		}

		return regionDocuments;
	}

	public List<StaticContent> getRegionFlows() {
		if (regionFlows == null) {
			return new LinkedList<StaticContent>();
		}
		return regionFlows;
	}

	public Map<String, StaticContent> getRegionFlowsMap() {
		final Map<String, StaticContent> temp = new HashMap<String, StaticContent>();

		final List<StaticContent> flowList = getRegionFlows();

		for (final StaticContent sc : flowList) {
			temp.put(sc.getFlowName(), sc);
		}
		return temp;
	}

}
