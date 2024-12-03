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

package com.wisii.wisedoc.view.panel;

import java.awt.CardLayout;
import java.util.HashMap;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel
{

	static HashMap<String, JPanel> panelList = new HashMap<String, JPanel>();

	CardLayout cardlayout = new CardLayout();

	public MainPanel()
	{
		this.setSize(600, 700);
		this.setLayout(cardlayout);
		DefaultPanel defaultPanel = new DefaultPanel();
		this.add(defaultPanel, "default");
	
	}

	public void addPanel(String id)
	{
		if (panelList.containsKey(id))
		{
			JPanel currentPanel = panelList.get(id);
			this.add(currentPanel, id);
			panelList.remove(id);
		}
		cardlayout.show(this, id);
	}

	public static void setPanelList()
	{
		panelList.put("文档", new WiseDocmentPanel());
		panelList.put("章节", new PagesequencePanel());
		panelList.put("页布局", new SimplePageMasterPanel());
		panelList.put("文档中的对象", new ObjectPanel());
		panelList.put("段落", new BlockPanel());
		panelList.put("表", new TablePanel());
		panelList.put("图片", new PicturePanel());
		panelList.put("条码", new BarcodePanel());
		panelList.put("高级块容器", new BlockContainerPanel());
		panelList.put("日期时间", new DatetimePanel());
		panelList.put("格式化数字", new NumbertextPanel());
		panelList.put("其它属性", new OthersPanel());
		panelList.put("视图", new ViewPanel());
		panelList.put("版式", new LayoutPanel());
		panelList.put("系统设置", new SystemPanel());
	}

}
