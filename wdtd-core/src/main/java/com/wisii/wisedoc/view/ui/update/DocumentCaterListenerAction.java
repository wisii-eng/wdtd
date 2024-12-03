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

import java.util.LinkedList;
import java.util.List;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.EditSetAble;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.listener.DocumentCaretEvent;
import com.wisii.wisedoc.document.listener.DocumentCaretListener;
import com.wisii.wisedoc.view.WisedocFrame;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 这个监听器是用来监听鼠标点击事件的，当用户不选取内容的时候，会根据用户的点击来取得相应文字的属性
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/27
 */
public class DocumentCaterListenerAction implements DocumentCaretListener {
	
	@Override
	public void documentCaretUpdate(DocumentCaretEvent event) {
		
		DocumentPosition dp = event.getNewpos();
		
		//如果用户点击到表格内，则向上寻找，直到找到头（null）如果有表格则添加表格RibbonUI
		//这个主要是针对表格，当用户点到页面上时会根据用户光标所在位置向上查找，直接点到对象上则不会触发该事件
		//这里主要针对情况2更新Ribbon界面，鼠标点击
		if(dp != null){
			List<Object> clickList = new LinkedList<Object>();	//对应情况二
			
			Element element = dp.getLeafElement();
			boolean iseditable = (element instanceof EditSetAble)&&((EditSetAble)element).isEditSetEnable();
			WisedocFrame frame = SystemManager.getMainframe();
			if (frame != null)
			{
				if (iseditable && element instanceof Inline)
				{
					InlineContent inlinecontent = ((Inline) element)
							.getContent();
					String path = inlinecontent.getBindNode().getXPath();

					if (path != null)
					{
						frame.setStatus(RibbonUIText.FRAME_STATUS_CURRENTNODE
								+ path);
					}
				} else
				{
					frame.setStatus("");
				}
			}
			while (element.getParent() != null)
			{
				// 拿到其所有的父亲节点，更新RibbonUI的Task界面
                //如果是inline级别的对象，且不是TextInline的话（如DateTimeInline），则此时不添加到clickList，
                //以防止出现相应的属性设置面板，这类对象只有对象选中时才可以设置属性，而不是光标在其上面就可以设置				
				if (element instanceof Inline
						&& !(element instanceof TextInline))
				{

				} else
				{
					clickList.add(element);
				}
				element = element.getParent();
			}
			clickList.add(SystemManager.getCurruentDocument());
			//目前提供了记录当前点击点的设置，不过似乎没有任何用处
			RibbonUIModel.setDocumentPosition(dp);
			//更新RibbonUI的Task界面，情况3
			//更新UI界面是否可用，这里面先更新了RibbonUIModel中的elementList
			RibbonUpdateManager.Instance.updateUIAvaliableAndProperty(clickList,iseditable);
		}
	}

}
