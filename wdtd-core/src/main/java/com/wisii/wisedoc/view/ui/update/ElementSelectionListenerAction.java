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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.EditSetAble;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.view.WisedocFrame;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.select.ElementSelectionEvent;
import com.wisii.wisedoc.view.select.ElementSelectionListener;
import com.wisii.wisedoc.view.select.SelectionModel;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 元素选择监听器
 * 
 * 有关更新Ribbon界面：这里涉及到三种选择方式
 * 1、用户点击单个对象，比如用户点击到barcode上，或者点击到cell上
 * 2、用户点击到“行间”，用户并没有明确的选择什么元素，仅仅点击到行间
 * 3、用户用鼠标划定所选范围
 * 
 * 该类用于处理情况1和3
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/27
 */
public class ElementSelectionListenerAction  implements ElementSelectionListener {
	
	@Override
	public void valueChanged(ElementSelectionEvent e) {
		
		SelectionModel sm = SystemManager.getMainframe().getEidtComponent().getSelectionModel();
		
		/**
		 * 这段代码是用来更新Ribbon界面的，根据当前选区元素的类型来更新Ribbon界面的，这个是根据用户选择的对象类型类判断的
		 * 只有当正确的选到对象上时才有作用，这个监听是元素选择监听
		 * 属于选择情况1，单个对象选择
		 */
		List<CellElement> objects = sm.getObjectSelection();
		if (!objects.isEmpty())
		{
			// 对应情况1所产生的列表
			List<Object> singleObjectList = new LinkedList<Object>();
			boolean iseditsetable = true;
			String bindnodepath = null;
			Class elementclass = null;
			boolean isallclassequal = true;
			boolean isimageurl = false;
			boolean isCheckbox = false;
 			for (Element element : objects)
			{
				if (element == null)
				{
					continue;
				}
				Class ceclass = element.getClass();
				if (elementclass == null)
				{
					elementclass = ceclass;
				} else
				{
					if (isallclassequal && !elementclass.equals(ceclass))
					{
						isallclassequal = false;
					}
				}
				if (iseditsetable
						&& (!(element instanceof EditSetAble) || !((EditSetAble) element)
								.isEditSetEnable())&&!(element instanceof CheckBoxInline))
				{
					iseditsetable = false;
					if (element instanceof BarCode)
					{
						Element ccparent = element.getParent();
						if (ccparent instanceof Inline)
						{
							InlineContent inlinecontent = ((Inline) ccparent)
									.getContent();
							if (inlinecontent != null
									&& inlinecontent.isBindContent())
							{
								bindnodepath = inlinecontent.getBindNode()
										.getXPath();
							}
						}
					}
					else if(element instanceof ExternalGraphic)
					{
						Object src = element.getAttribute(Constants.PR_SRC);
						if (src instanceof String)
						{
							isimageurl = true;
							bindnodepath = (String)src;
						} else if (src instanceof BindNode)
						{
							bindnodepath = ((BindNode) src).getXPath();
						}
					}
				} else if (iseditsetable||element instanceof CheckBoxInline)
				{
					if (element instanceof CheckBoxInline)
					{
						isCheckbox = true;
						iseditsetable = false;
					}
					if (element instanceof Inline)
					{
						InlineContent inlinecontent = ((Inline) element)
								.getContent();
						if (inlinecontent != null)
						{
							bindnodepath = inlinecontent.getBindNode()
									.getXPath();
						}
					}
				}
				// 这个for循环是用来确定当选定多个对象时，一个一个的更新界面
				while (element.getParent() != null)
				{
					// while循环是用来把当前选定对象的父亲都找出来，并存储起来
					// 更新RibbonUI
					singleObjectList.add(element);
					element = element.getParent();
				}
			}
			WisedocFrame frame = SystemManager.getMainframe();
			if (frame != null)
			{
				// 在状态栏上显示当前元素所绑定的动态节点
				if ((iseditsetable||isCheckbox) && bindnodepath != null)
				{
						frame.setStatus(RibbonUIText.FRAME_STATUS_CURRENTNODE
								+ bindnodepath);
				} else
				{
					// 在状态栏上显示当前选中元素的类型
					if (isallclassequal && elementclass != null)
					{
						if (elementclass.equals(TableCell.class))
						{
							frame
									.setStatus(RibbonUIText.FRAME_STATUS_SELECT_TABLECELL);
						} else if (elementclass.equals(TableRow.class))
						{
							frame
									.setStatus(RibbonUIText.FRAME_STATUS_SELECT_TABLEROW);
						} else if (elementclass.equals(Table.class))
						{
							frame
									.setStatus(RibbonUIText.FRAME_STATUS_SELECT_TABLE);
						} else if (elementclass.equals(ZiMoban.class))
						{
							CellElement elemeent=objects.get(0);
							if(elemeent instanceof ZiMoban){
							frame
									.setStatus(RibbonUIText.FRAME_STATUS_SELECT_ZIMOBAN+((ZiMoban)elemeent).getAttribute(Constants.PR_ZIMOBAN_NAME));
							}
						} else if ((elementclass.equals(BarCode.class) || elementclass
								.equals(ExternalGraphic.class))
								&& bindnodepath != null)
						{
							if (isimageurl)
							{
								frame
										.setStatus(RibbonUIText.FRAME_STATUS_CURRENTIMAGE
												+ bindnodepath);
							} else
							{
								frame
										.setStatus(RibbonUIText.FRAME_STATUS_CURRENTNODE
												+ bindnodepath);
							}
						} else
						{
							frame.setStatus("");
						}
					} else
					{
						frame.setStatus("");
					}
				}
			}
			// 更新RibbonUI的Task界面，情况1
			// 更新UI界面是否可用
			RibbonUpdateManager.Instance.updateUIAvaliableAndProperty(
					singleObjectList, iseditsetable);
		}
		
		/**
		 * 以下代码是针对情况3，当用户通过选定一定范围来更新Ribbon界面
		 */
		List<DocumentPositionRange> list = sm.getSelectionCells();
		if(list.size() != 0){
			
			Iterator<DocumentPositionRange> iterator = list.iterator();
			DocumentPositionRange dpRange = iterator.next();
			if(dpRange != null){
				
				List<Object> selectionList = new LinkedList<Object>(); //对应情况三
				
				Element element = dpRange.getEndPosition().getLeafElement();
				while (element.getParent() != null){
					//更新RibbonUI
					selectionList.add(element);
//					System.out.println("element: " + element + " attr: " + element.getAttributes().getAttributes());
					element = element.getParent();
				}
				
				//更新RibbonUI的Task界面，情况2
				//更新UI界面是否可用
//				System.out.println("updated by 3: " + list);
				RibbonUpdateManager.Instance.updateUIAvaliableAndProperty(selectionList,false);
			}
		}
	}
}
