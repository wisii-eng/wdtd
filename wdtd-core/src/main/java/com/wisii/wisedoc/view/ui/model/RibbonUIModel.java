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
package com.wisii.wisedoc.view.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.EditSetAble;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.EncryptTextInline;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.EncryptInformation;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.document.svg.WordArtText;
import com.wisii.wisedoc.view.LocationConvert;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.select.SelectionModel;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;

/**
 * RibbonUI的属性模型，里面存放着用户读取到的属性、默认属性和当前界面所对应的属性。用户通过{@link UpdateUI}
 * 发送鼠标点击、选择属性消息到这里 这里负责处理这些消息，并通知界面更新。
 * 
 * 在1.2版中统一了更新界面的消息源，仅仅是从当前选择的消息树中读取对象，删除了根据鼠标点击位置更新界面的更新代码
 * 删除了根据主动取得inline，block，page级别属性的更新界面方法，统一采用element级别getAttributes方法来取得属性
 * 
 * 在1.3版中针对读取不同类型属性的繁杂代码进行了整理，做了一个映射关系，把实际数据结构中的元素类型和属性分类中的
 * 类型对应上
 * 
 * @author 闫舒寰
 * @version 1.3 2009/02/27
 * @since 2008/11/21
 */
public enum RibbonUIModel
{

	RibbonUIModelInstance;

	/**
	 * 界面设置成默认状态的的标记
	 */
	public static final int DEFAULT_PROPERTY_ID = -1;

	public static final Map<Integer, Object> DEFAULT_PROPERTY = new HashMap<Integer, Object>();

	// 各个类型的默认属性
	private final static Map<ActionType, Map<Integer, Object>> defaultPropertiesByType = new ConcurrentHashMap<ActionType, Map<Integer, Object>>();

	// 存储用户读取到的属性
	private static Map<ActionType, Map<Integer, Object>> readPropertiesByType = new ConcurrentHashMap<ActionType, Map<Integer, Object>>();

	// 把用户读取到的属性和默认值比补全，以便和下面的界面属性相比较
	private static Map<ActionType, Map<Integer, Object>> readCompletePropertiesByType = new ConcurrentHashMap<ActionType, Map<Integer, Object>>();

	// 当前界面对应的属性集合，也就是界面的model
	private static Map<ActionType, Map<Integer, Object>> currentPropertiesByType = new ConcurrentHashMap<ActionType, Map<Integer, Object>>();

	//当前用户的鼠标所在点
	private static DocumentPosition documentPosition;
	
	/**
	 * 当前所选中的对象树
	 */
	private static List<Object> elementList = new ArrayList<Object>();
	
	static {
		//用默认值初始化文字和段落的的属性
		
		defaultPropertiesByType.put(ActionType.INLINE, InitialManager
				.getInitalAttributes().getAttributes());
		defaultPropertiesByType.put(ActionType.BLOCK, InitialManager
				.getInitalAttributes().getAttributes());
		final Map<Integer, Object> map = createChartProperties();
		defaultPropertiesByType.put(ActionType.Chart, map);
		defaultPropertiesByType.put(ActionType.Edit, createEditProperties());
		defaultPropertiesByType.put(ActionType.WordArtText, createWordArtTextProperties());
		final Map<Integer,Object> att = new HashMap<Integer, Object>();
		att.put(Constants.PR_ENCRYPT, new EncryptInformation(EncryptInformation.ENCRYPTTYPE1,null));
		defaultPropertiesByType.put(ActionType.ENCRYPTTEXT, att);
		//当前状态的属性不应该在这里初始化，需要动态读取的时候进行第一次初始化
		/*currentPropertiesByType.put(Font.getType(), InitialManager
				.getInitalAttributes().getAttributes());
		currentPropertiesByType.put(Paragraph.getType(), InitialManager
				.getInitalAttributes().getAttributes());*/
//		currentPropertiesByType.put(Font.getType(), new HashMap<Integer, Object>());
//		currentPropertiesByType.put(Paragraph.getType(), new HashMap<Integer, Object>());
		
		readCompletePropertiesByType.put(ActionType.INLINE, InitialManager.getInitalAttributes().getAttributes());
		readCompletePropertiesByType.put(ActionType.BLOCK, InitialManager.getInitalAttributes().getAttributes());
         	
	}
	private final static Map<Integer, Object> createChartProperties(){
		return InitialManager.getChartMap();
		/*final Integer[] list = {Constants.PR_TITLE, Constants.PR_CHART_TYPE, Constants.PR_SERIES_COUNT, Constants.PR_VALUE_COUNT,
				Constants.PR_CHART_ORIENTATION, Constants.PR_RANGEAXIS_LABEL, Constants.PR_DOMAINAXIS_LABEL, Constants.PR_ZERORANGELINE_VISABLE};
		final Map<Integer, Object> map = new HashMap<Integer, Object>();
		Object temp = null;
		for (final Integer key : list) {
			temp = InitialManager.getInitialValue(key, null);
			map.put(key, temp);
		}
		return map;*/
	}
	private final static Map<Integer, Object> createWordArtTextProperties()
	{
		final Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
				Constants.EN_WORDARTTEXT_TYPE_LINE, ""));
		map.put(Constants.PR_WORDARTTEXT_LETTERSPACE, new FixedLength(5d,"pt"));
		map.put(Constants.PR_WORDARTTEXT_CONTENT, "北京汇智互联软件技术有限公司");
		map.put(Constants.PR_WORDARTTEXT_PATHVISABLE, new EnumProperty(Constants.EN_FALSE,""));
		map.put(Constants.PR_WORDARTTEXT_ROTATION, 0);
		map.put(Constants.PR_WORDARTTEXT_STARTPOSITION, 0);
		map.put(Constants.PR_TEXT_ALIGN, new EnumProperty(Constants.EN_START,""));
		return map;
	}
	private final static Map<Integer, Object> createEditProperties()
	{
		final Map<Integer, Object> editinitproperties = new HashMap<Integer, Object>();
		editinitproperties.put(Constants.PR_EDITTYPE, null);
		editinitproperties.put(Constants.PR_AUTHORITY, null);
		editinitproperties.put(Constants.PR_ISRELOAD, null);
		editinitproperties.put(Constants.PR_APPEARANCE, null);
		editinitproperties.put(Constants.PR_EDIT_WIDTH, null);
		editinitproperties.put(Constants.PR_EDIT_HEIGHT, null);
		editinitproperties.put(Constants.PR_HINT, null);
		editinitproperties.put(Constants.PR_ONBLUR, null);
		editinitproperties.put(Constants.PR_ONSELECTED, null);
		editinitproperties.put(Constants.PR_ONKEYPRESS, null);
		editinitproperties.put(Constants.PR_ONKEYDOWN, null);
		editinitproperties.put(Constants.PR_ONKEYUP, null);
		editinitproperties.put(Constants.PR_ONCHANGE, null);
		editinitproperties.put(Constants.PR_ONCLICK, null);
		editinitproperties.put(Constants.PR_ONEDIT, null);
		editinitproperties.put(Constants.PR_ONRESULT, null);
		editinitproperties.put(Constants.PR_INPUT_TYPE, null);
		editinitproperties.put(Constants.PR_INPUT_MULTILINE, null);
		editinitproperties.put(Constants.PR_INPUT_WRAP, null);
		editinitproperties.put(Constants.PR_INPUT_FILTER, null);
		editinitproperties.put(Constants.PR_DATE_TYPE, null);
		editinitproperties.put(Constants.PR_DATE_FORMAT, null);
		editinitproperties.put(Constants.PR_RADIO_CHECK_VALUE, null);
		editinitproperties.put(Constants.PR_CHECKBOX_UNSELECT_VALUE, null);
		editinitproperties.put(Constants.PR_CHECKBOX_BOXSTYLE, null);
		editinitproperties.put(Constants.PR_CHECKBOX_TICKMARK, null);
		editinitproperties.put(Constants.PR_RADIO_CHECK_CHECKED, null);
		editinitproperties.put(Constants.PR_SELECT_TYPE, null);
		editinitproperties.put(Constants.PR_SELECT_MULTIPLE, null);
		editinitproperties.put(Constants.PR_SELECT_LINES, null);
		editinitproperties.put(Constants.PR_SELECT_ISEDIT, null);
		editinitproperties.put(Constants.PR_SELECT_NEXT, null);
		editinitproperties.put(Constants.PR_SELECT_SHOWLIST, null);
		editinitproperties.put(Constants.PR_SELECT_INFO, null);
		editinitproperties.put(Constants.PR_POPUPBROWSER_INFO, null);
		editinitproperties.put(Constants.PR_TRANSFORM_TABLE, null);
		editinitproperties.put(Constants.PR_XPATH_POSITION, null);
		editinitproperties.put(Constants.PR_EDIT_BUTTON, null);
		editinitproperties.put(Constants.PR_BUTTON_TYPE, null);
		editinitproperties.put(Constants.PR_BUTTON_INSERT_POSITION, null);
		editinitproperties.put(Constants.PR_CONN_WITH, null);
		editinitproperties.put(Constants.PR_GROUP_REFERANCE, null);
		editinitproperties.put(Constants.PR_GROUP_MAX_SELECTNUMBER, null);
		editinitproperties.put(Constants.PR_GROUP_MIN_SELECTNUMBER, null);
		editinitproperties.put(Constants.PR_GROUP_NONE_SELECT_VALUE, null);
		return editinitproperties;
	}
	/**
	 * 当界面设置属性时，用户选择的对象不会变，为了保证界面和设置同步需要在这里再更新一遍界面
	 * 这个主要针对通过对话框设置属性的时候界面同步更新的问题
	 * 
	 * @param actionType 当前类型
	 * @param properties 当前类型所对应的的属性
	 */
	public void updateUIState(final ActionType actionType, final Map<Integer, Object> properties) {
//		System.out.println("action type: " + actionType + " property: " + properties);

		//专门针对段落样式所做的处理，当用户设置完段落样式后，界面可以同步更新
		/*if (actionType == Paragraph.getType()) {
			Map<Integer, Object> temp = new HashMap<Integer, Object>(readPropertiesByType.get(Paragraph.getType()));
			
			//需要移除上次所应用的样式
			if (properties.containsKey(Constants.PR_BLOCK_STYLE)) {
				if (temp.containsKey(Constants.PR_BLOCK_STYLE)) {
					Object oldStyle = temp.get(Constants.PR_BLOCK_STYLE);
					if (oldStyle instanceof ParagraphStyles) {
						ParagraphStyles oldPs = (ParagraphStyles) oldStyle;
						Map<Integer, Object> oldStyleMap = oldPs.getStyleProperty();
						Set<Integer> oldStyleKeySet =  oldStyleMap.keySet();
						for (Integer key : oldStyleKeySet) {
							if (oldStyleMap.get(key).equals(temp.get(key))) {
								temp.remove(key);
							}
						}
					}
				}
			}
			
			temp.putAll(properties);
			dealParagraphStyles(temp);
			System.err.println(temp);
		} else */{
			//这个是针对一般性质的属性设置时同步更新界面的代码
			readPropertiesByType.put(actionType, properties);
		}

		if (readCompletePropertiesByType.get(actionType) != null) {
			// 先清空老的再设置值
			// readCompletePropertiesByType.get(actionType).clear();

			// 每次比较属性变化是用readCompletePropertiesByType和currentPropertiesByType进行比较的
			// ，当用户界面有所变化的时候，需要用当前的界面属性来填充读取到的总属性，即默认值；然后再用
			// 这次设置的属性来设置
			if (currentPropertiesByType.get(actionType) != null) {
				readCompletePropertiesByType.get(actionType).putAll(currentPropertiesByType.get(actionType));
			}
			
			if (readPropertiesByType.get(actionType) != null) {
				readCompletePropertiesByType.get(actionType).putAll(readPropertiesByType.get(actionType));
			}
		}
		
		//针对段落样式的特殊问题，特意发送文字改变属
		/*if (actionType == Paragraph.getType() && properties.containsKey(Constants.PR_BLOCK_STYLE)) {
			if (readCompletePropertiesByType.get(Font.getType()) != null) {
				// 先清空老的再设置值
				// readCompletePropertiesByType.get(actionType).clear();

				// 每次比较属性变化是用readCompletePropertiesByType和currentPropertiesByType进行比较的
				// ，当用户界面有所变化的时候，需要用当前的界面属性来填充读取到的总属性，即默认值；然后再用
				// 这次设置的属性来设置
				if (currentPropertiesByType.get(Font.getType()) != null) {
					readCompletePropertiesByType.get(Font.getType()).putAll(currentPropertiesByType.get(Font.getType()));
				}
				
				if (readPropertiesByType.get(Font.getType()) != null) {
					readCompletePropertiesByType.get(Font.getType()).putAll(readPropertiesByType.get(Font.getType()));
				}
			}
			fireChangedState(Font.getType());
		}*/
		
		fireChangedState(actionType);
	}

	/**
	 * 目前更改成所有的属性都可以通过这里更新，这里负责根据当前读取的元素树来读取所有的属性
	 * 
	 * 这里用来更新元素对象的属性，比如用户点击barcode的时候更新新出来的Barcode的Task的界面的属性
	 */
	public void updateUIState(final List<?> elementList) {
		
//		printPageSequenceInfo();
		
		//这里要注意顺序，先设置元素，再读取属性，最后再更新界面是否可用。因为有的界面是否可用是由元素的属性决定的
		RibbonUIModel.setElementList(elementList);
		
		// 按照选取的对象来读取属性更新界面
//		System.out.println(getElementList());

		final List<Class<? extends Element>> temp = new ArrayList<Class<? extends Element>>();

		// 清空过去已读属性
		readPropertiesByType.clear();
		
		final List<Object> eList = getElementList();
		if (eList != null) {
			
			for (final Object obj : eList) {
				if (obj instanceof Element) {
					final Element element = (Element) obj;
					
					//XXX for test 这里读取当前属性
//					System.out.println(element.getClass() + "   property: " + element.getAttributes().getAttributes());
					
					// 用来读取最近选中的相同类型的属性，比如两个表嵌套会只处理用户选择的最里面的表，过滤掉后面读取到的相同的类型
					if (temp.contains(element.getClass())) {
						continue;
					} else {
						temp.add(element.getClass());
					}
					readElementProperties(element);
				}
			}
		}
		
		updateUIState();
		
		dealProperties();
	}
	
	/**
	 * 处理用户拉动鼠标选定一定范围的内容的属性
	 */
	public void updateUIState(){
		
		final SelectionModel sm = SystemManager.getMainframe().getEidtComponent().getSelectionModel();
		Map<Integer, Object> properties;
		final List<DocumentPositionRange> list = sm.getSelectionCells();
		final Document docu = SystemManager.getCurruentDocument();
		
		//如果一个cell也没有选中则交给单击的那个事件来处理，这里不参与处理（就是当list=0的情况）
		if(list.size() != 0){
			final DocumentPositionRange[] dpr = list.toArray(new DocumentPositionRange[list.size()]);
			
			//清空过去已读属性
			readPropertiesByType.clear();
			//通知inline级别的属性
			properties = docu.getInlineAttributes(dpr);
			if (properties != null) {
				readPropertiesByType.put(ActionType.INLINE, properties);
			}
			
			//通知段落级别的属性
			properties = docu.getParagraphAttributes(dpr);
			if (properties != null) {
//				System.out.println("block: " + properties + " class: " + properties.getClass());
//				readPropertiesByType.put(Paragraph.getType(), properties);
				dealParagraphStyles(properties);
			}
			
			//目前这里的section的值始终为null
			/*properties = docu.getSectionAttributes(dpr);
			if (properties != null) {
				readPropertiesByType.put(Page.getType(), properties);
			}*/
		}
	}

	/**
	 * 处理各种选择情况之下的属性更新， 读取特殊属性，这里针对单个Element进行处理
	 * 
	 * @param element 当前用户选择节点集合
	 */
	private void readElementProperties(final Element element)
	{

		final ActionType aType = elementTypeMap.get(element.getClass());
		final Map<Integer, Object> properties = element.getAttributes()
				.getAttributes();

		if (properties != null && aType != null)
		{

			// 当处理svg图形的时候，需要合并一些类型一样的属性
			final Map<Integer, Object> temp = readPropertiesByType.get(aType);
			if (temp != null)
			{
				// 专门处理svg的情况
				if (element instanceof Canvas
						&& !(element instanceof WordArtText))
				{
					temp.put(Constants.PR_SVG_CANVAS, properties);
				} else if (element instanceof SVGContainer)
				{
					temp.put(Constants.PR_SVG_CONTAINER, properties);
				}
				
				readPropertiesByType.put(aType, temp);
				
			} else
			{
				final Map<Integer, Object> temp1 = new HashMap<Integer, Object>();
				// 分类处理一些特殊的样式
				if (element instanceof WordArtText)
				{
					readPropertiesByType.put(aType, properties);
				} else if (element instanceof Canvas)
				{
					temp1.put(Constants.PR_SVG_CANVAS, properties);
					readPropertiesByType.put(aType, temp1);
				} else if (element instanceof SVGContainer)
				{
					temp1.put(Constants.PR_SVG_CONTAINER, properties);
					readPropertiesByType.put(aType, temp1);
				} else if (element instanceof Block)
				{

					// 专门处理段落样式这一分支，基本逻辑是看有没有段落样式属性，有的话再和block和inline的属性进行比较，然后把
					final Map<Integer, Object> blockProperty = element
							.getAttributes().getAttributes();
					dealParagraphStyles(blockProperty);

				} else if (element instanceof PageSequence){
					temp1.putAll(properties);
					Object spm = element.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);
					Object psm = element.getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER);
					
					//目前的逻辑是无论读取到的是simple-page-master还是page-sequence-master都把当前页的simple-page-master设置到PR_CURRENT_SIMPLE_PAGE_MASTER属性中
					//走上面的模式会出现这样的问题：当用户设置属性的时候，只会更新PR_SIMPLE_PAGE_MASTER属性，而Constants.PR_CURRENT_SIMPLE_PAGE_MASTER属性不会更新
					//所以经过考虑当读取到simple-page-master的时候PR_CURRENT_SIMPLE_PAGE_MASTER属性空值
					if (spm != null) {
//						temp1.put(Constants.PR_SIMPLE_PAGE_MASTER, spm);
						temp1.put(Constants.PR_CURRENT_SIMPLE_PAGE_MASTER, null);
					}
					
					//用于处理当当前页布局是页布局序列(page-sequence-master)的情况下，把当前单一页布局(simple-page-master)读取到map属性中
					if (psm != null) {
//						temp1.put(Constants.PR_PAGE_SEQUENCE_MASTER, psm);
						//成从AreaTree中读取的simple-page-master
						temp1.put(Constants.PR_CURRENT_SIMPLE_PAGE_MASTER, LocationConvert.getSimplePageMaster(documentPosition));
					}
					readPropertiesByType.put(aType, temp1);
				}else 
				{
					readPropertiesByType.put(aType, properties);
					// add by zhangqiang 20090709 处理编辑属性
					if (element instanceof EditSetAble
							&& ((EditSetAble) element).isEditSetEnable())
					{
						if (properties.isEmpty())
						{
							properties.put(Constants.PR_EDITTYPE, null);
						}
						readPropertiesByType.put(ActionType.Edit, properties);
					}
					// zhangqiang added end
				}
			}

			// 原来的方法
			// readPropertiesByType.put(aType, properties);
		}
	}
	
	//专门用来处理段落样式的方法
	private void dealParagraphStyles(final Map<Integer, Object> blockProperty) {
		
		final Object blockStyleProperty = blockProperty.get(Constants.PR_BLOCK_STYLE);
		
		if (blockStyleProperty != null && blockStyleProperty instanceof ParagraphStyles) {
			final ParagraphStyles ps = (ParagraphStyles) blockStyleProperty;
			final Map<Integer, Object> psStyles = ps.getStyleProperty();
			final Set<Integer> psKeySet = psStyles.keySet();
			final Map<Integer, Object> blockTemp = new HashMap<Integer, Object>();
			//只把不一样的放到读取属性中，因为这里有这样一个逻辑：
			//若当前段落或者inline自身有属性，并且也有样式的时候，以自身的属性为主
			for (final Integer key : psKeySet) {
				if (blockProperty.containsKey(key)) {
					continue;
				}
				if (!psStyles.get(key).equals(blockProperty.get(key))) {
					blockTemp.put(key, psStyles.get(key));
				}
			}
			//专门用来处理inline级别，段落样式
			final Map<Integer, Object> inlineProperty = readPropertiesByType.get(ActionType.INLINE);
			if (inlineProperty != null) {
				final Map<Integer, Object> inlineTemp = new HashMap<Integer, Object>();
				for (final Integer key : psKeySet) {
					if (inlineProperty.containsKey(key)) {
						continue;
					}
					if (!psStyles.get(key).equals(inlineProperty.get(key))) {
						inlineTemp.put(key, psStyles.get(key));
					}
				}
				
				//把段落级别动态样式应用到inline级别元素上
				readPropertiesByType.get(ActionType.INLINE).putAll(inlineTemp);
			}
			
			final Map<Integer, Object> finalProperty = new HashMap<Integer, Object>(blockProperty);
			finalProperty.putAll(blockTemp);
//			System.err.println(finalProperty);
			readPropertiesByType.put(ActionType.BLOCK, finalProperty);
			
		} else {
			readPropertiesByType.put(ActionType.BLOCK, blockProperty);
		}
	}
	
	//实际读取到的对象和属性分类的对应关系
	private static final Map<Class<? extends Element>, ActionType> elementTypeMap = new HashMap<Class<? extends Element>, ActionType>();
	
	public static Map<Class<? extends Element>, ActionType> getElementTypeMap() {
		return new HashMap<Class<? extends Element>, ActionType>(elementTypeMap);
	}

	static {
		/**
		 * 实际模型层数据结构中的类型和属性的分类的对应方式
		 * 日后添加新的数据类型和属性类型的时候需要在这里添加其相对应的关系
		 */
		elementTypeMap.put(TextInline.class, ActionType.INLINE);
		elementTypeMap.put(Block.class, ActionType.BLOCK);
		elementTypeMap.put(PageSequence.class, ActionType.LAYOUT);
		
		elementTypeMap.put(TableCell.class, ActionType.TABLE_CELL);
		elementTypeMap.put(TableRow.class, ActionType.TABLE_ROW);
		elementTypeMap.put(Table.class,ActionType.TABLE);
		
		elementTypeMap.put(BarCode.class, ActionType.GRAPH);
		
		elementTypeMap.put(BlockContainer.class,ActionType.BLOCK_CONTAINER);
		
		elementTypeMap.put(DateTimeInline.class, ActionType.DATETIME);
		elementTypeMap.put(NumberTextInline.class,ActionType.NUMBERTEXT);
		elementTypeMap.put(CheckBoxInline.class,ActionType.CheckBox);
		elementTypeMap.put(ExternalGraphic.class, ActionType.GRAPH);
		
		elementTypeMap.put(SVGContainer.class, ActionType.SVG_GRAPHIC);
		elementTypeMap.put(Canvas.class, ActionType.SVG_GRAPHIC);
		elementTypeMap.put(Rectangle.class, ActionType.SVG_GRAPHIC);
		elementTypeMap.put(Ellipse.class, ActionType.SVG_GRAPHIC);
		elementTypeMap.put(Line.class, ActionType.SVG_GRAPHIC);
		/* 【添加：START】 by 李晓光2009-5-21 */
		elementTypeMap.put(Chart.class, ActionType.Chart);
		/* 【添加：END】 by 李晓光2009-5-21 */
		elementTypeMap.put(WordArtText.class, ActionType.WordArtText);
		elementTypeMap.put(EncryptTextInline.class, ActionType.ENCRYPTTEXT);
		elementTypeMap.put(WiseDocDocument.class, ActionType.DOCUMENT);
	}

	// /================以上是获得属性======================////////////////

	// /=================下面是处理属性===========================//////////
	/**
	 * 基本思路是先把读到的属性补齐，然后再和当前界面对应的属性相比较，然后发现不同的发送消息
	 */
	// 第二版处理属性的方法
	private void dealProperties() {
		
		//处理段落样式表的逻辑

		for (final Entry<ActionType, Map<Integer, Object>> entry1 : readPropertiesByType.entrySet()) {
			final ActionType actionType = entry1.getKey();
			final Map<Integer, Object> readProperties = entry1.getValue();

			
			/*if (actionType == ActionType.INLINE) {
				System.err.println("read action type: " + actionType
						+ " read property: " + readProperties); //
				System.err.println(" read property: " + readProperties);
			}*/
			 
//			System.err.println("read action type: " + actionType + " read property: " + readProperties);
			
			if (readProperties != null)
			{

				/*
				 * if (readProperties.size() == 0) {
				 * //当读到的property为空的时候，则证明读到的默认情况下的文字，所以要恢复界面为默认状态
				 * fireDefaultState(actionType); } else
				 */{

					if (actionType == ActionType.INLINE
							|| actionType == ActionType.BLOCK || actionType == ActionType.Chart
							|| actionType == ActionType.Edit || actionType == ActionType.WordArtText
							|| actionType == ActionType.ENCRYPTTEXT)
					{
						// 目前为了方便inline和block属性的设置，暂时只针对inline和block类型的对象进行补全
						// 只有这两种情况需要补充默认值，其他，比如barcode则是读到什么什么就是当前值
						// 先清空老的再设置值
						if (!readCompletePropertiesByType.containsKey(actionType)) {
							readCompletePropertiesByType.put(actionType, new HashMap<Integer, Object>());
						} else {
							readCompletePropertiesByType.get(actionType).clear();
						}
						
						
						// 设置回默认值
						readCompletePropertiesByType.get(actionType).putAll(
								defaultPropertiesByType.get(actionType));
						// 复制读取到的属性，并把读取到的属性填充成默认属性值
						readCompletePropertiesByType.get(actionType).putAll(
								readProperties);
					} else
					{
						if (!readCompletePropertiesByType.containsKey(actionType))
						{
							readCompletePropertiesByType.put(actionType, new HashMap<Integer, Object>());
						} else {
							// 先清空老的再设置值
							readCompletePropertiesByType.get(actionType).clear();
						}
						readCompletePropertiesByType.get(actionType).putAll(readProperties);
					}

					// 这里是正常读到的属性，首先用默认的属性补全，然后开始处理
					fireChangedState(actionType);
					// return;
				}
			} else
			{
				// 当读到空属性的时候
//				fireDefaultState(actionType);
			}
		}

		// 把属性集体发送出去
		sendPropertyMsg();
	}

	/**
	 * 根据从用户处去读的属性更新UI界面 由于读取的属性是不完整的，只有用户设置过的属性才能读取出来， 所以需先要用默认的属性来补全，
	 * 然后和现有界面的属性进行比较，发现不同则发通知 最后恢复读取属性的“模板”为默认属性
	 */
	private void fireChangedState(final ActionType actionType) {

		if (readCompletePropertiesByType == null
				|| readCompletePropertiesByType.get(actionType) == null
				//这里当处理table属性的时候，属性map的size为空，代表着默认值所以这个判断需要被注掉
				/*|| readCompletePropertiesByType.get(actionType).size() == 0*/) {
			return;
		}
		
		final Map<Integer, Object> readCompleteProperties = new HashMap<Integer, Object>(
				readCompletePropertiesByType.get(actionType));
		
		if (currentPropertiesByType.get(actionType) == null) {
			currentPropertiesByType.put(actionType,	new HashMap<Integer, Object>());
		}
		
		for (final Entry<Integer, Object> entry : readCompleteProperties.entrySet()) {
			final int key = entry.getKey();
			final Object value = entry.getValue();
			
//			System.err.println("action type: " + actionType + " fire key: " + key +
//					" old value: " + currentPropertiesByType.get(actionType).get(key) + " new value: " + value);
			

			// fireUIStateChange(actionType, key,
			// currentPropertiesByType.get(actionType).get(key), value);
			accumulateChanges(actionType, key, currentPropertiesByType.get(
					actionType).get(key), value);

			/*
			 * if(key == 94){ System.out.println("current key: " + key +
			 * " value: " + currentProperties.get(key)); }
			 */
		}
		storeChange(actionType);

		// 反向比一遍，用来保证界面的动作产生新的id时能被检测到
		if (currentPropertiesByType == null
				|| currentPropertiesByType.get(actionType) == null
		/* || currentPropertiesByType.get(actionType).size() == 0 */) {
			return;
		}
		final Map<Integer, Object> currentPropertiesProperties = new HashMap<Integer, Object>(
				currentPropertiesByType.get(actionType));
		if (readCompletePropertiesByType.get(actionType) == null) {
			readCompletePropertiesByType.put(actionType,
					new HashMap<Integer, Object>());
		}
		for (final Entry<Integer, Object> entry : currentPropertiesProperties
				.entrySet()) {
			final int key = entry.getKey();
			final Object value = entry.getValue();
//			System.out.println("action type: " + actionType + " fire key: " +
//			key + " value: " + value);

			// fireUIStateChange(actionType, key,
			// currentPropertiesByType.get(actionType).get(key), value);
			accumulateChanges(actionType, key, value,
					readCompletePropertiesByType.get(actionType).get(key));

			/*
			 * if(key == 94){ System.out.println("current key: " + key +
			 * " value: " + currentProperties.get(key)); }
			 */
		}
		storeChange(actionType);
		// 反向比结束

		// 界面要和当前选择对象的属性一致，所以要先清除
		currentPropertiesByType.get(actionType).clear();
		currentPropertiesByType.get(actionType).putAll(
				readCompletePropertiesByType.get(actionType));
	}

	/**
	 * 发送让UI恢复到默认属性的消息，Action可以通过属性名为空的时候来判断是否回复界面为默认值
	 * 
	 * @param actionType
	 *            当前消息所针对的属性类型
	 */
	private void fireDefaultState(final ActionType actionType)
	{
		Map<Integer, Object> defaultProperties = defaultPropertiesByType
				.get(actionType);
		if (defaultProperties == null)
		{
			defaultProperties = new HashMap<Integer, Object>();
		}
		if (readCompletePropertiesByType.get(actionType) == null)
		{
			readCompletePropertiesByType.put(actionType,
					new HashMap<Integer, Object>());
		}
		if (currentPropertiesByType.get(actionType) == null)
		{
			currentPropertiesByType.put(actionType,
					new HashMap<Integer, Object>());
		}
		readCompletePropertiesByType.get(actionType).clear();
		readCompletePropertiesByType.get(actionType).putAll(defaultProperties);
		currentPropertiesByType.get(actionType).clear();
		currentPropertiesByType.get(actionType).putAll(defaultProperties);
		// stateChange.firePropertyChange(actionType, DEFAULT_PROPERTY_ID, null,
		// null);
		/*
		 * Map<ActionType, Map<Integer,Object>> def = new
		 * ConcurrentHashMap<ActionType, Map<Integer,Object>>();
		 * 
		 * def.put(actionType, null);
		 */
		// Map<Integer, Object> tempMap = new HashMap<Integer, Object>();
		changePropertiseByType.put(actionType, RibbonUIModel.DEFAULT_PROPERTY);
	}

	// 当前变化的属性
	private static Map<ActionType, Map<Integer, Object>> changePropertiseByType = new ConcurrentHashMap<ActionType, Map<Integer, Object>>();

	private final Map<Integer, Object> accumulateMaptemp = new HashMap<Integer, Object>();

	/**
	 * 在for循环中积累变化的值
	 * @param actionType
	 * @param propertyKey
	 * @param oldValue
	 * @param newValue
	 */
	public void accumulateChanges(final ActionType actionType, final int propertyKey,
			final Object oldValue, final Object newValue)
	{
		if ((newValue == null && oldValue == null)
				|| (oldValue != null && newValue != null && oldValue
						.equals(newValue)))
		{
			return;
		}
		
		accumulateMaptemp.put(propertyKey, newValue);
		
//		changePropertiseByType.put(actionType, accumulateMaptemp);
		
		
//		accumulateMaptemp.clear();
		
//		if (changePropertiseByType.get(ActionType.Chart) != null) {
//			System.err.println("341 " + changePropertiseByType.get(ActionType.Chart).get(341));
//			if ((Integer)changePropertiseByType.get(ActionType.Chart).get(341) == 6) {
//				System.out.println("hihi");
//			}
//		}
	}
	
	/**
	 * 最终把变化的值设到变化map中，并清空临时存储变化的值
	 * @param actionType
	 */
	private void storeChange(final ActionType actionType) {
		
		if (accumulateMaptemp.isEmpty()) {
			return;
		}
		final Map<Integer, Object> map = changePropertiseByType.get(actionType);
		if (map == null) {
			changePropertiseByType.put(actionType, new HashMap<Integer, Object>());
		}
		changePropertiseByType.get(actionType).putAll(new HashMap<Integer, Object>(accumulateMaptemp));
		
		accumulateMaptemp.clear();
	}

	public void sendPropertyMsg()
	{
//		System.out.println("change properties: " + changePropertiseByType);
		//如果没有任何变化则直接返回，而不用走通知机制
		if (changePropertiseByType.size() == 0) {
			changePropertiseByType.clear();
			accumulateMaptemp.clear();
			return;
		}
		
		fireUIStateChange(new ConcurrentHashMap<ActionType, Map<Integer, Object>>(
				changePropertiseByType));
		
		changePropertiseByType.clear();
		accumulateMaptemp.clear();
	}

	// /=================处理属性结束===========================//////////

	// ================以下代码是属性（状态）变化监听器=============================//
	private UIStateChangeSupport stateChange;

	public synchronized void addUIStateChangeListener(
			final UIStateChangeListener listener)
	{
		if (listener == null)
		{
			return;
		}
		if (stateChange == null)
		{
			stateChange = new UIStateChangeSupport(this);
		}
		stateChange.addUIStateChangeListener(listener);
	}

	public synchronized void removeUIStateChangeListener(
			final UIStateChangeListener listener)
	{
		if (listener == null || stateChange == null)
		{
			return;
		}
		stateChange.removeUIStateChangeListener(listener);
	}

	public synchronized UIStateChangeListener[] getUIStateChangeListeners()
	{
		if (stateChange == null)
		{
			return new UIStateChangeListener[0];
		}
		return stateChange.getUIStateChangeListeners();
	}

	protected void fireUIStateChange(final ActionType actionType, final int propertyKey,
			final Object oldValue, final Object newValue)
	{
		final UIStateChangeSupport changeSupport = this.stateChange;

		/*
		 * if (!oldValue.equals(newValue)) { System.out.println("Action type: "
		 * + actionType + " property name: " + propertyKey + " old: " + oldValue
		 * + " new: " + newValue); }
		 */

		if (changeSupport == null
				|| (oldValue != null && newValue != null && oldValue
						.equals(newValue)))
		{
			return;
		}

		// System.out.println("property name: " + propertyName + " old: " +
		// oldValue + " new: " + newValue);
		changeSupport.firePropertyChange(actionType, propertyKey, oldValue,
				newValue);
	}

	protected void fireUIStateChange(
			final Map<ActionType, Map<Integer, Object>> changePropertiseByType)
	{
		final UIStateChangeSupport changeSupport = this.stateChange;
		changeSupport.firePropertyChange(changePropertiseByType);
	}

	// /===================属性变化监听器结束==========================///

	/**
	 * 获得当前读取到的属性，这个不应该被其他对象读取到，因为这里的属性不完整，需要读取当前读到的属性的用{@code
	 * getReadPropertiesByType}这个
	 */
	public static Map<ActionType, Map<Integer, Object>> getReadPropertiesByType()
	{
		return readPropertiesByType;
	}

	/**
	 * 取得当前读取的完整（补全）属性集合
	 * 
	 * @return 当前读取到的属性集合
	 */
	public static Map<ActionType, Map<Integer, Object>> getReadCompletePropertiesByType()
	{
		//应该返回的是副本，而不应该直接针对读取的属性进行更改，只有通过文档监听器才能更改读取到的属性
//		return new HashMap<ActionType, Map<Integer,Object>>(readCompletePropertiesByType);
		return readCompletePropertiesByType;
//		return Collections.unmodifiableMap(readCompletePropertiesByType);
	}

	/**
	 * 获得当前选取的元素树
	 * 
	 * @return 返回当前选取的元素树
	 */
	public static List<Object> getElementList()
	{
//		return new ArrayList<Object>(elementList);
		return elementList;
	}
	
	/**
	 * 获得当前属性列表的类型
	 * @return 返回当前元素树的类型
	 */
	public static List<Class<?>> getElementListType() {
		
		final List<Class<?>> cList = new ArrayList<Class<?>>();

		for (final Object element : elementList) {
			cList.add(element.getClass());
		}
		
		return cList;
	}

	public static void setElementList(final List<?> elementList)
	{
		RibbonUIModel.elementList = null;
		//这个确保在下次用户点击界面更新界面树前，RibbonUIModel中还有界面树
		RibbonUIModel.elementList = new ArrayList<Object>(elementList);
//		System.out.println(elementList);
	}

	/**
	 * 获得当前Ribbon界面的属性
	 * 
	 * @return 当前Ribbon界面属性集合
	 */
	public static Map<ActionType, Map<Integer, Object>> getCurrentPropertiesByType()
	{
		return currentPropertiesByType;
	}

	/**
	 * 获得默认属性
	 * 
	 * @return
	 */
	public static Map<ActionType, Map<Integer, Object>> getDefaultPropertiesByType()
	{
		return defaultPropertiesByType;
	}

	public static DocumentPosition getDocumentPosition() {
		return documentPosition;
	}

	public static void setDocumentPosition(final DocumentPosition documentPosition) {
		RibbonUIModel.documentPosition = documentPosition;
	}
	
	//XXX for test
	private void printPageSequenceInfo() {
		/*PageSequence ps = RibbonUpdateManager.Instance.getMainPageSequence();
		int totalchild = ps.getChildCount();
		System.out.println(totalchild);
		for (int i = 0; i < totalchild; i++) {
			System.out.println(ps.getChildAt(i));
		}*/
		
		/*Map<Integer, Object> temp1 = new HashMap<Integer, Object>();
		PageSequence ps = null;
		if (elementList != null) {
			for (Object o : elementList) {
//				System.out.println(o);
				if (o instanceof PageSequence) {
					ps = (PageSequence) o;
					temp1 = ps.getAttributes().getAttributes();
				}
			}
		}
		
		int totalchild = ps.getChildCount();
		System.out.println(totalchild);
		for (int i = 0; i < totalchild; i++) {
			System.out.println(ps.getChildAt(i));
		}*/
		
		/*Object o = temp1.get(Constants.PR_SIMPLE_PAGE_MASTER);
		
		if (o != null) {
			if (o instanceof SimplePageMaster) {
				SimplePageMaster spm = (SimplePageMaster) o;
				System.out.println(spm.getRegions());
			}
		}*/
		
//		System.out.println(temp1.get(key));
	}

}
