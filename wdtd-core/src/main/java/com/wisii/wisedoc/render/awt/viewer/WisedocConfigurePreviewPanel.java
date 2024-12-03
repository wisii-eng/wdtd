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
 * @WisedocConfigurePreviewPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.render.awt.viewer;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;
import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreeNode;

import org.xml.sax.SAXException;

import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.apps.FovFactory;
import com.wisii.wisedoc.area.AreaTreeHandler;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.layoutmanager.PageSequenceLayoutManager;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.awt.AWTRenderer;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;

/**
 * 类功能描述：设置对话中各个预览区域用panel,该panel主要是自定义个小的WisedocDocument，
 * 然后对这个Document中的inlin设置相应的属性【字体、段落……】来达到预览的效果。
 * 
 * 作者：李晓光 创建日期：2009-1-7
 */
@SuppressWarnings("serial")
public class WisedocConfigurePreviewPanel extends JPanel implements StatusListener, PropertyChangeListener {
	/* 定义FOUserAgent对象 */
	private FOUserAgent userAgent = new FOUserAgent(FovFactory.newInstance()){
		public Set<Integer> getLayers(){
			FOUserAgent agent = DocumentUtil.getUserAgent();
			if(isNull(agent))
				return null;
			return agent.getLayers();
		}
	};
	/* 定义AreaTreeHandler对象 */
	private AreaTreeHandler handler = null;
	private final static String FORMAT = "application/X-fov-awt-preview";
	/** The AWT renderer */
	private AWTRenderer renderer;
	/* 当前文档 */
	private Document document = null;
	/* 当前页 */
	private PageViewportPanel pagePanel = null;
	/** The number of pixels left empty at the top bottom and sides of the page. */
	private static final int BORDER_SPACING = 10;
	/* 预览时设置样式文本。 */
	private final static String TEST_TEXT = "汇智互联测试用文本";
	private String text = "";

	public WisedocConfigurePreviewPanel(){
		this(createInlineLevelDoc());
	}

	public WisedocConfigurePreviewPanel(WiseDocDocument doc) {
		super();
		try {
			handler = new AreaTreeHandler(userAgent, FORMAT, null);
		} catch (Exception e) {
			LogUtil.errorException(e.getMessage(), e);
		}
		renderer = (AWTRenderer) userAgent.getRendererOverride();
		renderer.setStatusListener(this);
		pagePanel = new PageViewportPanel(renderer, 0);
		pagePanel.setBorder(new EmptyBorder(BORDER_SPACING,
				BORDER_SPACING, BORDER_SPACING, BORDER_SPACING));
		setLayout(new BorderLayout());
		add(pagePanel, BorderLayout.CENTER);
		setDocument(doc);
		reload();
	}

	public void setDocument(WiseDocDocument doc) {
		this.document = doc;
	}

	/**
	 * 
	 * 对指定的FOTree进行解析【Layout处理】，生成AreaTree,并根据AreaTree在JPanel中绘制数据。
	 * 
	 */
	public void reload() {
		if (isNull(handler)) {
			LogUtil.error("没有初始化Handler");
			return;
		}
		this.reset();
		List<PageSequence> seqs = getPageSequences();
		PageSequenceLayoutManager pageSLM = null;
		for (PageSequence sequence : seqs) {
			sequence.reSet();
			handler.startPageSequence(sequence);
			pageSLM = new PageSequenceLayoutManager(handler, sequence);
			pageSLM.activateLayout();
		}
		try {
			handler.endDocument();
		} catch (SAXException e) {
			LogUtil.errorException(e.getMessage(), e);
		}
	}

	public void setText(String s){
		if(isEmpty(s))
			s = TEST_TEXT;
		this.text = s;
	}
	
	public void initValues(Map<Integer, Object> atts, ActionType type, boolean isReplace){
		if(isNull(atts))
			return;
		List<CellElement> elements = findElements((CellElement)document.getChildAt(0), type);
		if(isNull(elements))
			return;
		for (CellElement cell : elements) {
			cell.setAttributes(atts, isReplace);
		}
		reload();
	}

	private List<CellElement> findElements(CellElement ele, ActionType type){
		List<CellElement> cells = new ArrayList<CellElement>();
		if(isNull(ele))
			return cells;
		switch (type) {
		case INLINE:
			if(ele instanceof Inline){
				cells.add(ele);
				return cells;
			}
			break;
		case BLOCK:
			if(ele instanceof Block){
				cells.add(ele);
				return cells;
			}
			break;
		case BLOCK_CONTAINER:
			if(ele instanceof BlockContainer){
				cells.add(ele);
				return cells;
			}
			break;
		}
		List<CellElement> elements = ele.getChildren(0);
		if(isNull(elements))
			return cells;
		for (CellElement cell : elements) {
			cells.addAll(findElements(cell, type));
		}
		return cells;
	}
	public void reset() {
		if (!isNull(handler))
			handler.reset();
		if (!isNull(renderer))
			renderer.clearViewportList();
	}

	private List<PageSequence> getPageSequences() {
		List<PageSequence> sequences = new ArrayList<PageSequence>();
		if (isNull(document))
			return sequences;
		int count = document.getChildCount();
		TreeNode node = null;
		for (int i = 0; i < count; i++) {
			node = document.getChildAt(i);
			if (!(node instanceof PageSequence))
				continue;
			sequences.add((PageSequence) node);
		}
		return sequences;
	}

	public WiseDocDocument getDocument() {
		return (WiseDocDocument)this.document;
	}

	@Override
	public void notifyCurrentPageRendered() {}

	@Override
	public void notifyPageRendered() {}

	@Override
	public void notifyRendererStopped() {
		loadPage();
	}
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object obj = evt.getNewValue();
		if(!(obj instanceof Map))return;
		Map<Integer, Object> map = (Map<Integer, Object>)obj;
		initValues(map, ActionType.INLINE, Boolean.FALSE);
	}
	
	private void loadPage(){
		pagePanel.loadPage();
	}
	public static WiseDocDocument createInlineLevelDoc(){
		return createInlineLevelDoc(TEST_TEXT, 250, 80);
	}
	public static WiseDocDocument createInlineLevelDoc(double w, double h){
		return createInlineLevelDoc(TEST_TEXT, w, h);
	}
	public static WiseDocDocument createInlineLevelDoc(String info, double w, double h){
		WiseDocDocument doc = new WisedcoPreviewDocument(createPageSequence(w, h));
		TreeNode node = doc.getChildAt(0);
		PageSequence sequence = (PageSequence) node;
		Flow flow = (Flow) sequence.getChildAt(0);
		BlockContainer container = new BlockContainer();
		EnumProperty display = new EnumProperty(Constants.EN_CENTER,"");
		container.setAttribute(Constants.PR_DISPLAY_ALIGN, display);
		container.setAttribute(Constants.PR_INLINE_PROGRESSION_DIMENSION, new LengthRangeProperty(new FixedLength(w, "pt")));
		container.setAttribute(Constants.PR_BLOCK_PROGRESSION_DIMENSION, new LengthRangeProperty(new FixedLength(h, "pt")));
		Block block = new Block();
		/*Inline inline = new Inline();*/
		TextInline inline = new TextInline(new Text('B'), null);
		insertString(block, info, inline);
		EnumProperty center = new EnumProperty(Constants.EN_CENTER,"");
		block.setAttribute(Constants.PR_TEXT_ALIGN, center);
		block.setAttribute(Constants.PR_TEXT_ALIGN_LAST, center);
		if(isNull(info))
			info = TEST_TEXT;
		/*FOText text = new FOText(inline, info.toCharArray());*/
		flow.add(container);
		container.add(block);
		/*block.add(inline);
		inline.add(text);*/
		return doc;
	}
	private static void insertString(Block block, String text, TextInline textinline) {
		if (block != null && textinline != null && text != null
				&& !text.isEmpty()) {
			int index = block.getIndex(textinline) + 1;
			List<CellElement> lines = new ArrayList<CellElement>();
			int size = text.length();
			for (int i = 0; i < size; i++) {
				char c = text.charAt(i);
				TextInline tmpinline = new TextInline(new Text(c), null);
				lines.add(tmpinline);
			}
			block.insert(lines, index);
		}

	}
	private static List<PageSequence> createPageSequence(double w, double h){
		PageSequence sequence = new PageSequence(null);
		sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, createSimplePageMaster(w, h));
		List<PageSequence> list = new ArrayList<PageSequence>();
		list.add(sequence);
		return list; 
	}
	private static SimplePageMaster createSimplePageMaster(double w, double h){
		Length height = new FixedLength(h, "pt");//80
		Length width = new FixedLength(w, "pt");//250
		Map<Integer, Region> regions = new HashMap<Integer, Region>();
		Length extent = new FixedLength(0d, "cm");
		EnumProperty conditionality = new EnumProperty(Constants.EN_DISCARD, "");
		EnumNumber precedence = new EnumNumber(-1, 0);
		SpaceProperty space = new SpaceProperty((LengthProperty) extent,
				precedence, conditionality);
		CommonMarginBlock margin = new CommonMarginBlock(extent, extent,
				extent, extent, space, space, null, null);
		// CommonMarginBlock margin = new CommonMarginBlock(extent, extent,
		// extent, extent, null, null, null, null);
		RegionBody body = new RegionBody(1, null, margin, null, null,
				Constants.EN_BEFORE, Constants.EN_AUTO, null, 0,
				Constants.EN_LR_TB);
		regions.put(Constants.FO_REGION_BODY, body);
		SimplePageMaster simple = new SimplePageMaster(null, height, width, 0,
				Constants.EN_LR_TB, regions, "");
		body.setLayoutMaster(simple);
		return simple;
	}
	public static class WisedcoPreviewDocument extends WiseDocDocument{
		public WisedcoPreviewDocument(){
			super();
		}
		public WisedcoPreviewDocument(List<PageSequence> pagesequences){
			super(pagesequences);
		}
		public boolean isCellChanged(CellElement cell){
			return true;
		}
	}
	public static WisedocConfigurePreviewPanel getInstance(){
		return getInstance(createInlineLevelDoc());
	}
	public static WisedocConfigurePreviewPanel getInstance(WiseDocDocument doc){
		return new WisedocConfigurePreviewPanel(doc);
	}
}
