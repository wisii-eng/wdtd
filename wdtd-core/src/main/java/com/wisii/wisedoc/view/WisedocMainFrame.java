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
 * @WisedocMainFrame.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.UIManager;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.swing.DataStructureTree;
import com.wisii.wisedoc.util.WisedocUtil;

/**
 * 类功能描述：Wisedoc的主窗体, 主要提供对外接口，可以通过接口设置编辑区、功能区用控件
 * 
 * 作者：李晓光 创建日期：2008-8-21
 */
@SuppressWarnings("serial")
public class WisedocMainFrame implements Serializable {
	private WisedocFrame fr = null;

	public WisedocMainFrame() {
		SystemManager.setMainframe(this);		
		fr = new WisedocFrame();//PreviewDialogAPP.createPreviewDialog(Boolean.TRUE);		
		final DataStructureTree datastree = new DataStructureTree(); 
		setDataStructComponent(datastree); 
		final Document doc = SystemManager.getCurruentDocument();
		if(doc!=null)
		{
			doc.addDataStructureChangeListener(datastree);
		}
	}

	public WisedocMainFrame(final String title) {
		fr = new WisedocFrame(title);//PreviewDialogAPP.createPreviewDialog(Boolean.TRUE);
	}

	/**
	 * 
	 * 向主窗体编辑区设置控件
	 * 
	 * @param comp
	 *            指定要设置的控件
	 * @return 无
	 */
	public void setEidtComponent(final DocumentPanel comp) {
		fr.setEidtComponent(comp);
	}

	/**
	 * 
	 * 获得编辑区控件【DocumentPanel】
	 * 
	 * @return {@link DocumentPanel} 返回编辑区控件
	 */
	public AbstractEditComponent getEidtComponent() {
		return fr.getEidtComponent();
	}

	/**
	 * 
	 * 向功能区设置数据结构处理展现、处理控件
	 * 
	 * @param comp
	 *            指定要添加的控件
	 * @return 无
	 */
	public void setDataStructComponent(final Component comp) {
		fr.setDataStructComponent(comp);
	}

	/**
	 * 
	 * 向功能区设置属性编辑控件
	 * 
	 * @param comp
	 *            指定要添加的控件
	 * @return 无
	 */
	public void setPropertyComponent(final Component comp) {
		fr.setPropertyComponent(comp);
	}

	/**
	 * 
	 * 显示主窗体
	 * 
	 */
	public void showFrame() {
		WisedocUtil.centerOnScreen(fr);
		fr.setVisible(true);
	}

	/**
	 * 
	 * 获得主窗体对象
	 * 
	 * @return {@link WisedocFrame} 返回主窗体对象
	 */
	public WisedocFrame getMainFrame() {
		return fr;
	}
	/**
	 * 为当前窗体设置标题。
	 * @param title	指定标题内容
	 */
	public void setTitle(final String title){
		fr.setTitle(title);
	}
	/**
	 * 测试用
	 */
	public static void main(final String[] args) {
		// LogUtil.entry();

		try {
			// com.sun.java.swing.plaf.windows.WindowsLookAndFeel laf =
			// new
			// com.sun.java.swing.plaf.windows.WindowsLookAndFeel();
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		} catch (final Exception e) {
			e.printStackTrace();
		}
		final WisedocMainFrame fr = new WisedocMainFrame(null);
		/*
		 * JLabel lab = new JLabel(MediaResource.getImageIcon("1.jpg")); JLabel
		 * lab0 = new JLabel(MediaResource.getImageIcon("1.jpg"));
		 */
		// 编辑面板
		/* JPanel editPropertyPanel = new TextPropertyEditUI(); */
		// JLabel lab1 = new JLabel(MediaResource.getImageIcon("1.jpg"));
		/*
		 * fr.setPropertyComponent(editPropertyPanel);
		 * fr.setDataStructComponent(lab0); fr.setEidtComponent(lab);
		 */
		
		fr.showFrame();

		LogUtil.exit();
	}

}
