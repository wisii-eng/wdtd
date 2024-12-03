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
package com.wisii.wisedoc.view.ui.ribbon.basicband;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.ui.svg.transcoded.edit_find;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 查找属性面板
 * @author 闫舒寰
 * @version 1.0 2009/01/19
 *
 */
public class LayersettingBand {
	public static int FIRST_LAYER = 1;
	public static int SECOND_LAYER = 2;
	public static int THIRD_LAYER = 4;
	public static int FORTH_LAYER = 8;
	private static Set<Integer> selectLayers = new HashSet<Integer>();
	private final Integer[] values = DocumentUtil.getUserAgent().getAllLayers().toArray(new Integer[]{});
	private final String[] display = UiText.COMMON_COLOR_LAYER;
	public JRibbonBand getBand() {
		final JRibbonBand layerBand = new JRibbonBand(RibbonUIText.LAYER_BAND, new edit_find());

		/*JCommandToggleButton findButton = new JCommandToggleButton(RibbonUIText.FIND_BUTTON,
				new system_search());
		findBand.addCommandButton(findButton, RibbonElementPriority.MEDIUM);

		JCommandToggleButton goToButton = new JCommandToggleButton(RibbonUIText.REPLACE_BUTTON
				, new edit_find_replace());
		findBand.addCommandButton(goToButton, RibbonElementPriority.MEDIUM);*/
		
		
		/*final JCommandToggleButton first = new JCommandToggleButton("一层", new edit_clear());
		findBand.addCommandButton(first, RibbonElementPriority.TOP);
		
		final JCommandToggleButton sec = new JCommandToggleButton("二层", new edit_clear());
		findBand.addCommandButton(sec, RibbonElementPriority.TOP);
		
		final JCommandToggleButton thir = new JCommandToggleButton("三层", new edit_clear());
		findBand.addCommandButton(thir, RibbonElementPriority.TOP);
		
		final JCommandToggleButton fourth = new JCommandToggleButton("四层", new edit_clear());
		findBand.addCommandButton(fourth, RibbonElementPriority.TOP);
		
		final JCommandToggleButton five = new JCommandToggleButton("五层", new edit_clear());
		findBand.addCommandButton(five, RibbonElementPriority.TOP);*/
		
		final ActionListener action = new ActionListener(){
			@Override
			public void actionPerformed(final ActionEvent e) {
				final JCommandToggleButton btn = (JCommandToggleButton)e.getSource();
				final String value = btn.getActionModel().getActionCommand();
				int layer = 0;
				if(WisedocUtil.isNumbers(value)){
					layer = Integer.parseInt(value);
				}
				if(btn.getActionModel().isSelected()) {
					selectLayers.add(layer);
				} else {
					selectLayers.remove(layer);
				}
				
				updateUI(Boolean.TRUE);
			}
		
		};
		int count = 0;
		for (final String name : display) {
			createToggleButton(name, values[count++].toString(), action, layerBand);
		}
		updateUI(Boolean.FALSE);
		return layerBand;
	}
	/** 更新编辑区，使得仅显示设置的层 */
	private void updateUI(final boolean isFire){
		final FOUserAgent agent = DocumentUtil.getUserAgent();
		agent.setLayers(selectLayers, isFire);
	}
	private JCommandToggleButton createToggleButton(final String display, final String value, final ActionListener lis, final JRibbonBand comp){
		final JCommandToggleButton btn = new JCommandToggleButton(display, /*new edit_clear()*/MediaResource.getResizableIcon("00062.ico"));
		btn.getActionModel().setActionCommand(value);
		btn.addActionListener(lis);
		comp.addCommandButton(btn, RibbonElementPriority.LOW);
		
		btn.getActionModel().setSelected(Boolean.TRUE);
		
		int layer = 0;
		if(WisedocUtil.isNumbers(value)){
			layer = Integer.parseInt(value);
		}
		selectLayers.add(layer);
		
		return btn;
	}
}
