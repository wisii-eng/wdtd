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
package com.wisii.wisedoc.view.ui.ribbon.insertband;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.parts.index.IndexDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 目录属性面板
 * @author 闫舒寰
 * @version 1.0 2009/04/10
 */
public class IndexBand {
	
	public JRibbonBand getBand() {
		
		final JRibbonBand band = new JRibbonBand("目录", MediaResource.getResizableIcon("09379.ico"), null);
		
		//toc means table of contents目录按钮
		final JCommandButton toc = new JCommandButton("目录",  MediaResource.getResizableIcon("00013.ico"));
		toc.setActionRichTooltip(new RichTooltip(RibbonUIText.PAGE_ORIENTATION_BUTTON_TITLE, RibbonUIText.PAGE_ORIENTATION_BUTTON_DESCRIPTION));
		toc.setPopupCallback(new PopupPanelCallback(){
			@Override
			public JPopupPanel getPopupPanel(final JCommandButton commandButton) {
				return new tocList();
			}
			
		});
		toc.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		band.addCommandButton(toc, RibbonElementPriority.MEDIUM);
		
		//目录级别
		final JCommandButton tocIndex = new JCommandButton("目录级别", MediaResource.getResizableIcon("09379.ico"));
		tocIndex.setActionRichTooltip(new RichTooltip(RibbonUIText.PAGE_SIZE_BUTTON_TITLE, RibbonUIText.PAGE_SIZE_BUTTON_DESCRIPTION));
		tocIndex.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(final JCommandButton arg0) {
				return new IndexList();
			}
		});
		tocIndex.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		band.addCommandButton(tocIndex, RibbonElementPriority.MEDIUM);
		
		//更新目录
		final JCommandButton updateIndex = new JCommandButton("更新目录", MediaResource.getResizableIcon("00782.ico"));
		//添加可下拉按钮
		updateIndex.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		//下拉菜单的按钮
		//鼠标浮动显示解释说明
		updateIndex.setActionRichTooltip(new RichTooltip("更新目录", "根据当前文档内容更新目录"));
		//这个是在地方紧张的时候，显示的优先级
		band.addCommandButton(updateIndex, RibbonElementPriority.MEDIUM);
		RibbonUIManager.getInstance().bind(Defalult.UPDATE_TABLECONTENTS_ACTION, updateIndex);
		
		return band;
	}
	
	private class tocList extends JCommandPopupMenu {
		public tocList() {
			final JCommandMenuButton addToc = new JCommandMenuButton("插入目录", MediaResource.getResizableIcon("06259.ico"));
			RibbonUIManager.getInstance().bind(Defalult.INSERT_TABLECONTENTS_ACTION, addToc);
			this.addMenuButton(addToc);
			final JCommandMenuButton delToc = new JCommandMenuButton("删除目录", MediaResource.getResizableIcon("06258.ico"));
			this.addMenuButton(delToc);
			
			this.addMenuSeparator();
			
			final JCommandMenuButton setToc = new JCommandMenuButton("设置目录属性", MediaResource.getResizableIcon("06258.ico"));
			this.addMenuButton(setToc);
			
			setToc.addActionListener(new ActionListener(){
			
				@Override
				public void actionPerformed(final ActionEvent e) {
					new IndexDialog(ActionType.INDEX);
				}
			});
			
//			RibbonUIManager.getInstance().bind(Page.ORIENTATION_ACTION, addToc, delToc, setToc);
		}
	}
	
	//目录级别下拉菜单
	private class IndexList extends JCommandPopupMenu {
		
		public IndexList() {
			final List<Object> indexLevelMenue = new ArrayList<Object>();
			
			for (final String element : UiText.INDEX_LEVEL) {
				indexLevelMenue.add(new JCommandMenuButton(element, new EmptyResizableIcon(16)));
			}
			
			for (final Object object : indexLevelMenue) {
				final JCommandMenuButton menuItem = (JCommandMenuButton) object;
				this.addMenuButton(menuItem);
			}
			
			RibbonUIManager.getInstance().bind(Paragraph.LEVEL_ACTION, indexLevelMenue.toArray());
			
			this.addMenuSeparator();
			
			final JCommandMenuButton cancelIndex = new JCommandMenuButton("取消级别", MediaResource.getResizableIcon("06258.ico"));
			this.addMenuButton(cancelIndex);
			
			RibbonUIManager.getInstance().bind(Paragraph.LEVEL_CANCEL_ACTION, cancelIndex);
		}
	}
}
