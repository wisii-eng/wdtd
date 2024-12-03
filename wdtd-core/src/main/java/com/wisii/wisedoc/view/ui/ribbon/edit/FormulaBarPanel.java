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
package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.ribbon.JRibbon;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import org.jvnet.flamingo.ribbon.RibbonTask;
import org.jvnet.flamingo.ribbon.resize.CoreRibbonResizePolicies;
import org.jvnet.flamingo.ribbon.resize.IconRibbonBandResizePolicy;

import com.wisii.wisedoc.resource.MediaResource;

public class FormulaBarPanel extends JRibbon {


	public FormulaBarPanel() {
		super();
		JRibbonBand band1 = new JRibbonBand("运算符", MediaResource.getResizableIcon("pi.gif")); // 新建一个Band
		band1.setResizePolicies((List) Arrays.asList(new CoreRibbonResizePolicies.None(band1.getControlPanel()), new IconRibbonBandResizePolicy(band1.getControlPanel())));
		JCommandButton button1 = new JCommandButton("", MediaResource.getResizableIcon("plus2.gif"));
		JCommandButton button2 = new JCommandButton("", MediaResource.getResizableIcon("minus2.gif"));
		JCommandButton button3 = new JCommandButton("", MediaResource.getResizableIcon("multiply.gif"));
		JCommandButton button4 = new JCommandButton("", MediaResource.getResizableIcon("divide.gif"));
		JCommandButton button5 = new JCommandButton("", MediaResource.getResizableIcon("square.gif"));
		JCommandButton button6 = new JCommandButton("", MediaResource.getResizableIcon("ln.gif"));
		JCommandButton button7 = new JCommandButton("", MediaResource.getResizableIcon("sin.gif"));
		JCommandButton button8 = new JCommandButton("", MediaResource.getResizableIcon("cos.gif"));
		JCommandButton button9 = new JCommandButton("", MediaResource.getResizableIcon("log.gif"));
		JCommandButton button10 = new JCommandButton("", MediaResource.getResizableIcon("parentheses.gif"));
		band1.addCommandButton(button1, RibbonElementPriority.TOP);
		band1.addCommandButton(button2, RibbonElementPriority.LOW);
		band1.addCommandButton(button3, RibbonElementPriority.LOW);
		band1.addCommandButton(button4, RibbonElementPriority.LOW);
		band1.addCommandButton(button5, RibbonElementPriority.LOW);
		band1.addCommandButton(button6, RibbonElementPriority.LOW);
		band1.addCommandButton(button7, RibbonElementPriority.LOW);
		band1.addCommandButton(button8, RibbonElementPriority.LOW);
		band1.addCommandButton(button9, RibbonElementPriority.LOW);
		band1.addCommandButton(button10, RibbonElementPriority.LOW);
		// 新建一个Task，并将Band添加到该Task中去
		RibbonTask task1 = new RibbonTask("", band1);
		// 先获取Ribbon，然后在Ribbon上添加一个Task
		this.addTask(task1);
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,300);
		FormulaBarPanel p = new FormulaBarPanel();
		frame.getContentPane().add(p);
		frame.setVisible(true);
	}

}


