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
 * @ClipBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.externalgraphic;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.ribbon.JRibbonBand;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;

/**
 * 类功能描述：裁剪设置面板
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-23
 */
public class ClipBand implements WiseBand
{
	public JRibbonBand getBand()
	{
		JRibbonBand clipband = new JRibbonBand("裁剪", MediaResource.getResizableIcon("00062.ico"), null);
		JPanel clippanel = new JPanel(new GridLayout(2, 2, 0, 0));
		JLabel leftlabel = new JLabel("左:");
		leftlabel.setPreferredSize(new Dimension(30, 22));
		JSpinner leftvale = new JSpinner();
		SpinnerNumberModel heightmodel = new SpinnerNumberModel(0.0, 0.0, null,
				1);
		leftvale.setModel(heightmodel);
		leftvale.setPreferredSize(new Dimension(60, 22));
		leftvale.setPreferredSize(new Dimension(60, 22));
		JPanel leftpanel = new JPanel();
		leftpanel.add(leftlabel);
		leftpanel.add(leftvale);
		JLabel toplabel = new JLabel("上:");
		toplabel.setPreferredSize(new Dimension(30, 22));
		JSpinner topvale = new JSpinner();
		SpinnerNumberModel topmodel = new SpinnerNumberModel(0.0, 0.0, null,
				1);
		topvale.setModel(topmodel);
		topvale.setPreferredSize(new Dimension(60, 22));
		topvale.setPreferredSize(new Dimension(60, 22));
		JPanel toppanel = new JPanel();
		toppanel.add(toplabel);
		toppanel.add(topvale);
		JLabel rightlabel = new JLabel("右:");
		rightlabel.setPreferredSize(new Dimension(30, 22));
		JSpinner rightValue = new JSpinner();
		SpinnerNumberModel rightmodel = new SpinnerNumberModel(0.0, 0.0, null,
				1);
		rightValue.setModel(rightmodel);
		rightValue.setPreferredSize(new Dimension(60, 22));
		JPanel rightpanel = new JPanel();
		rightpanel.add(rightlabel);
		rightpanel.add(rightValue);
		JLabel bottomlabel = new JLabel("下");
		bottomlabel.setPreferredSize(new Dimension(30, 22));
		JSpinner bottomValue = new JSpinner();
		SpinnerNumberModel widthModel = new SpinnerNumberModel(0.0, 0.0, null,
				1);
		bottomValue.setModel(widthModel);
		bottomValue.setPreferredSize(new Dimension(60, 22));
		JPanel bottompanel = new JPanel();
		bottompanel.add(bottomlabel);
		bottompanel.add(bottomValue);
		clippanel.add(leftpanel);
		clippanel.add(toppanel);
		clippanel.add(rightpanel);
		clippanel.add(bottompanel);
//		clipband.addPanel(clippanel);
		return clipband;
	}

}
