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

package com.wisii.wisedoc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.wisii.wisedoc.view.ui.ribbon.bcb.WiseTemplateManagementPanel;
import com.wisii.wisedoc.view.ui.ribbon.bcb.WiseTemplateManager;

public class WiseTemplatePanel extends JPanel
{

	public WiseTemplatePanel()
	{

		setLayout(new BorderLayout());

		// 初始化模板几个区域的面板
		WiseTemplateManager.Instance.initialManagerPanels();

		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				WiseTemplateManager.Instance.getFileTreePanel(),
				WiseTemplateManager.Instance.getFileListPanel());

		jsp.setOneTouchExpandable(true);

		JSplitPane jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp,
				WiseTemplateManager.Instance.getPreviewPanel());

		jsp1.setOneTouchExpandable(true);

		this.add(jsp1, BorderLayout.CENTER);
	}

	/**
	 * 测试用
	 * 
	 * @param args
	 */
	public static void createAndShowGUI()
	{
		JFrame tr = new JFrame();
		tr.setLayout(new BorderLayout());
		tr.add(new WiseTemplateManagementPanel(), BorderLayout.CENTER);
		// tr.configureRibbon();
		/*
		 * try {
		 * UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel"
		 * ); } catch (ClassNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (InstantiationException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IllegalAccessException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (UnsupportedLookAndFeelException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();

		tr.setPreferredSize(new Dimension(/* r.width 4 / 5, r.height / 2 */640,
				480));
		tr.pack();
		tr.setLocation(r.x, r.y);
		tr.setVisible(true);
		tr.setLocationRelativeTo(null);
		tr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public static void main(String[] args)
	{

		SwingUtilities.invokeLater(new Runnable()
		{

			public void run()
			{
				createAndShowGUI();
			}
		});

	}

}