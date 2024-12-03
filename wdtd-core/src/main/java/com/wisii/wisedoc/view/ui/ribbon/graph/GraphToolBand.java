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
 * @GraphToolBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.graph;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_DISPLAY_ALIGN_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_DISPLAY_ALIGN_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_DISPLAY_ALIGN_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_DISPLAY_ALIGN_LIST;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_EQUAL_LIST;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_EQUAL_SIZE_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_EQUAL_SIZE_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_EQUAL_SIZE_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_TOOL_BAND_TITLE;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-11-25
 */
public class GraphToolBand implements WiseBand
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand band = new JRibbonBand(SVG_GRAPH_TOOL_BAND_TITLE, MediaResource.getResizableIcon("09379.ico"), null);
		//对齐方式
		JCommandButton displayAlign = new JCommandButton(SVG_GRAPH_DISPLAY_ALIGN_BUTTON, MediaResource.getResizableIcon("01435.ico"));
		displayAlign.setPopupCallback(new DisplayAlign());
		displayAlign.setPopupRichTooltip(new RichTooltip(SVG_GRAPH_DISPLAY_ALIGN_BUTTON_TITLE, SVG_GRAPH_DISPLAY_ALIGN_BUTTON_DESCRIPTION));
		displayAlign.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		band.addCommandButton(displayAlign, RibbonElementPriority.MEDIUM);
		//仅仅用做更新状态
		RibbonUIManager.getInstance().bind(SvgGraphic.BOTTOM_ALIGN, displayAlign, new Object());
		
		//等宽等高下拉菜单
		JCommandButton equal = new JCommandButton(SVG_GRAPH_EQUAL_SIZE_BUTTON, MediaResource.getResizableIcon("02077.ico"));
		equal.setPopupRichTooltip(new RichTooltip(SVG_GRAPH_EQUAL_SIZE_BUTTON_TITLE, SVG_GRAPH_EQUAL_SIZE_BUTTON_DESCRIPTION));
		equal.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		equal.setPopupCallback(new Equal());
		band.addCommandButton(equal, RibbonElementPriority.MEDIUM);
		//仅仅用做更新状态
		RibbonUIManager.getInstance().bind(SvgGraphic.EQUAL_WIDTH, equal, new Object());
		return band;
	}
	
	//对齐方式下拉菜单
	private class DisplayAlign extends JCommandPopupMenu implements PopupPanelCallback {
		
		public DisplayAlign() {
			JCommandMenuButton before = new JCommandMenuButton(RibbonUIText.CELL_DISPLAY_ALIGN_MENU[0], MediaResource.getResizableIcon("00666.ico"));
			JCommandMenuButton centerVertical = new JCommandMenuButton(RibbonUIText.CELL_DISPLAY_ALIGN_MENU[1], MediaResource.getResizableIcon("00669.ico"));
			JCommandMenuButton after = new JCommandMenuButton(RibbonUIText.CELL_DISPLAY_ALIGN_MENU[2], MediaResource.getResizableIcon("00667.ico"));
			
			JCommandMenuButton left = new JCommandMenuButton(SVG_GRAPH_DISPLAY_ALIGN_LIST[3], MediaResource.getResizableIcon("00664.ico"));
			JCommandMenuButton centerHorizontal = new JCommandMenuButton(SVG_GRAPH_DISPLAY_ALIGN_LIST[4], MediaResource.getResizableIcon("00668.ico"));
			JCommandMenuButton right = new JCommandMenuButton(SVG_GRAPH_DISPLAY_ALIGN_LIST[5], MediaResource.getResizableIcon("00665.ico"));
			
			
//			RibbonUIManager.getInstance().bind(actionID, components)
			
			RibbonUIManager.getInstance().bind(SvgGraphic.BOTTOM_ALIGN, after);
			RibbonUIManager.getInstance().bind(SvgGraphic.TOP_ALIGN, before);
			RibbonUIManager.getInstance().bind(SvgGraphic.VERTICAL_CENTER, centerVertical);
			
			RibbonUIManager.getInstance().bind(SvgGraphic.LEFT_ALIGN, left);
			RibbonUIManager.getInstance().bind(SvgGraphic.RIGHT_ALIGN, right);
			RibbonUIManager.getInstance().bind(SvgGraphic.HORIZONTAL_CENTER, centerHorizontal);
			
			this.addMenuButton(before);
			this.addMenuButton(centerVertical);
			this.addMenuButton(after);
			this.addMenuSeparator();
			this.addMenuButton(left);
			this.addMenuButton(centerHorizontal);
			this.addMenuButton(right);
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new DisplayAlign();
		}
	}
	private class Equal extends JCommandPopupMenu implements PopupPanelCallback {
		
		public Equal() {
			JCommandMenuButton equalWidth = new JCommandMenuButton(SVG_GRAPH_EQUAL_LIST[0], MediaResource.getResizableIcon("02077.ico"));
			JCommandMenuButton equalHeight = new JCommandMenuButton(SVG_GRAPH_EQUAL_LIST[1], MediaResource.getResizableIcon("02079.ico"));
			
			this.addMenuButton(equalWidth);
			this.addMenuButton(equalHeight);
			
			RibbonUIManager.getInstance().bind(SvgGraphic.EQUAL_WIDTH, equalWidth);
			RibbonUIManager.getInstance().bind(SvgGraphic.EQUAL_HEIGHT, equalHeight);
		}
		
		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new Equal();
		}
	}
}
