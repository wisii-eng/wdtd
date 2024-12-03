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
package com.wisii.wisedoc.view.ui.ribbon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButtonPanel;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbon;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.WisedocMainFrame;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonTableListPanel;

public class TestRibbon extends JPanel{
	
	WisedocMainFrame frame;
	public TestRibbon() {
	
		setLayout(new BorderLayout());
		
//		add(new RibbonPanel().getRibbon(), BorderLayout.NORTH);
		
		JRibbon ribbon = new JRibbon();
		
		JRibbonBand jrb = new JRibbonBand("test", new EmptyResizableIcon(32));
		
		
		JCommandButton jb = new JCommandButton("test", new EmptyResizableIcon(16));
		
		jb.setPopupCallback(new DisplayAlign());
		
//		System.out.println(jb);
		
		jb.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		
		
		jrb.addCommandButton(jb, RibbonElementPriority.TOP);
		
		JCommandButton jb1 = new JCommandButton("test", new EmptyResizableIcon(16));
		
		jb1.setPopupCallback(new Test1());
		
//		System.out.println(jb);
		
		jb1.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		
		jrb.addCommandButton(jb1, RibbonElementPriority.TOP);
		
		System.out.println(jb1.getActionModel().getClass());
//		jb1.set
		
		RibbonTask rt = new RibbonTask("test",jrb);
		ribbon.addTask(rt);
		this.add(ribbon, BorderLayout.NORTH);
		
		
//		RepaintManager.currentManager(this).
		
//		add(new PreviewDialogAPP().createPreviewDialog(null, null, true), BorderLayout.CENTER);
		
//		frame = new WisedocMainFrame();
//		PreviewDialogAPP pdat = (PreviewDialogAPP)frame.getMainFrame();
//		
//		RibbonUpdateManager.Instance.setCurrentMainPanel(frame.getEidtComponent());
		
//		add(pdat.getPreviewPanel(), BorderLayout.CENTER);
//		add(new PropertyEditPanel(), BorderLayout.EAST);
		
	}
	
	
	private class DisplayAlign extends JCommandPopupMenu implements PopupPanelCallback {
		
		public DisplayAlign() {
			
			JCommandMenuButton before = new JCommandMenuButton("左对齐", MediaResource.getResizableIcon("00666.ico"));
			JCommandMenuButton center = new JCommandMenuButton("居中", MediaResource.getResizableIcon("00669.ico"));
			JCommandMenuButton after = new JCommandMenuButton("右对齐", MediaResource.getResizableIcon("00667.ico"));
//			RibbonUIManager.getInstance().bind(Table.DISPLAY_ALIGN_ACTION, before, center, after);
			
			JCommandButton jb = new JCommandButton("hihi", new EmptyResizableIcon(32));
			jb.setPopupCallback(new Test1());
			jb.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
			
			RibbonTableListPanel tableList = new RibbonTableListPanel();
			
			this.add(tableList);
			
			this.addMenuButton(before);
			this.addMenuButton(center);
			this.addMenuButton(after);
			
			before.setActionRichTooltip(new RichTooltip("hihi","jijhiodsjaf"));
			
			center.getActionModel().setSelected(true);
			
			this.addMenuSeparator();
			this.add(jb);
			
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
//			System.out.println(commandButton);
			return new DisplayAlign();
		}
	}
	
	private class Test1 implements PopupPanelCallback {
		
		private JPopupPanel getPopu(){
			
			
			JCommandButtonPanel jbp = new JCommandButtonPanel(CommandButtonDisplayState.ORIG);
			jbp.addButtonGroup("hihi");
//			jbp.add
			
			JCommandPopupMenu jp = new JCommandPopupMenu(jbp, 2, 2);
			
			
			
			return jp;
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			// TODO Auto-generated method stub
			return new Test1().getPopu();
		}
		
	}
	
	
	
	
	
	
	

	
	/**
	 * 测试用
	 * 
	 * @param args
	 */
	public static void createAndShowGUI() {
		JFrame tr = new JFrame();
		tr.setLayout(new BorderLayout());
		tr.add(new JPanel(), BorderLayout.NORTH);
		tr.add(new TestRibbon(), BorderLayout.CENTER);
//		tr.configureRibbon();
		/*try {
			UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();

		tr.setPreferredSize(new Dimension(/*r.width * 4 / 5, r.height / 2*/640,480));
		tr.pack();
		tr.setLocation(r.x, r.y);
		tr.setVisible(true);
		tr.setLocationRelativeTo(null);
		tr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}
	
}
