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
package com.wisii.wisedoc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.WisedocFrame;
import com.wisii.wisedoc.view.ui.ribbon.RibbonPanel;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 有关UI的一些常用方法
 * @author 闫舒寰
 * @version 1.0 2009/02/19
 */
public class UiUtil {
	
	//实例化这个类没有任何意义
	private UiUtil() {}
	
	public static void updateUI(final JComponent c){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				c.updateUI();
			}
		});
	}
	
	/**
	 * 递归调用指定组件的孩子，指定它的所有孩子是否可用
	 * @param component	容纳一堆孩子的组件，比如JPanel
	 * @param b	该组件的所有孩子
	 */
	public static void setComponentsEnabled(final JComponent component, final boolean b){
		
		if (component.getComponentCount() == 0) {
			component.setEnabled(b);
			return;
		} else {
			for (int i = 0; i < component.getComponentCount(); i++) {
				final Object temp = component.getComponent(i);
				if (temp instanceof JComponent) {
					final JComponent tempC = (JComponent) temp;
					
					//下拉菜单比较特殊需要单独处理
					if (tempC instanceof JComboBox) {
						tempC.setEnabled(b);
						continue;
					}
					
					if (tempC.getComponentCount() == 0) {
						tempC.setEnabled(b);
					} else {
						setComponentsEnabled(tempC, b);
					}
				}
			}
		}
	}
	
    private static boolean deleteFile(final String delpath)
			throws FileNotFoundException, IOException {
    	
		try {
			final File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				final String[] filelist = file.list();
				for (final String element : filelist) {
					final File delfile = new File(delpath + "\\" + element);
					if (!delfile.isDirectory()) {
						delfile.delete();
					} else if (delfile.isDirectory()) {
						deleteFile(delpath + "\\" + element);
					}
				}
				file.delete();
			}
		} catch (final FileNotFoundException e) {
			// Log.debug("deletefile()   Exception:" + e.getMessage());
		}
		return true;
	}
    
    /**
     * 删除文件和其下的子文件夹和文件
     * @param delFile 要删除的文件，或者文件夹
     * @return 删除成功或者不成功
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static boolean deleteFile(final File delFile)
			throws FileNotFoundException, IOException {
    	
		return deleteFile(delFile.getPath());
	}
	
	/**
	 * 检查给定的页布局中是否有页眉
	 * @param spm 给定的页布局
	 * @return 是否有页眉
	 */
	public static boolean hasRegionBefore(final SimplePageMaster spm){
		return spm.getRegion(Constants.FO_REGION_BEFORE) != null ? true : false;
	}
	
	/**
	 * 检查给定的页布局中是否有页脚
	 * @param spm	给定的页布局
	 * @return	是否有页脚
	 */
	public static boolean hasRegionAfter(final SimplePageMaster spm){
		return spm.getRegion(Constants.FO_REGION_AFTER) != null ? true : false;
	}
	
	/**
	 * 检查给定的页布局中是否有左区域
	 * @param spm	给定的页布局
	 * @return	是否有左区域
	 */
	public static boolean hasRegionStart(final SimplePageMaster spm){
		return spm.getRegion(Constants.FO_REGION_START) != null ? true : false;
	}
	
	/**
	 * 检查给定的页布局中是否有右区域
	 * @param spm	给定的页布局
	 * @return	是否有左区域
	 */
	public static boolean hasRegionEnd(final SimplePageMaster spm){
		return spm.getRegion(Constants.FO_REGION_END) != null ? true : false;
	}
	
	/**
	 * 获得当前需要放置到四个区域的流
	 * @return
	 */
	public static List<StaticContent> getRegionFlows(){
		
		final List<StaticContent> temp = new ArrayList<StaticContent>();
		
		final int ch = SystemManager.getCurruentDocument().getChildCount();
		
		for (int i = 0; i < ch; i++) {
//			System.out.println(SystemManager.getCurruentDocument().getChildAt(i));
			final Object o = SystemManager.getCurruentDocument().getChildAt(i);
			
			if (o instanceof PageSequence) {
				final PageSequence ps = (PageSequence) o;
				
				for (final CellElement ce : ps.getAllChildren()) {
					if (ce instanceof StaticContent) {
						final StaticContent sc = (StaticContent) ce;
						temp.add(sc);
					}
				}
			}
		}
		
		return temp;
	}
	
	//当前选中的任务栏
	private static RibbonTask rt;
	/**
	 * 当有对话框需要“借用”RibbonUI的时候，最后结束的时候需要把RibbonUI还回去
	 */
	public static void returnRibbonUI(){
		final Object o = SystemManager.getMainframe();
		if (o instanceof WisedocFrame) {
			final WisedocFrame mPanel = (WisedocFrame) o;
			
			mPanel.setJToolbar(RibbonPanel.getRibbon());
			
			//还原工具栏先
//			RibbonPanel.getRibbon().setMinimized(false);
			
//			RibbonPanelManager.Instance.mainRibbonSetting();
			
			RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getRibbon().getTask(2));
			
			//把监听器添加回主文档
			RibbonUpdateManager.Instance.addMainEditorListener();
			
			//设置完了四个区域属性之后需要把四区中的document清空
//			RegionDocumentManager.Instance.clearRegionDocs();
		}
	}
	
	/**
	 * 当有对话框需要“借用”RibbonUI的时候用这个方法得到RibbonUI
	 * @return RibbonUI
	 */
	public static JComponent borrowRibbonUI(){
		rt = RibbonPanel.getRibbon().getSelectedTask();
//		List<Component> list = RibbonPanel.getRibbon().getTaskbarComponents();
//		System.out.println(list.indexOf(RibbonPanel.getRibbon().getSelectedTask()));
		
		
		
		//最小化工具栏
//		RibbonPanel.getRibbon().setMinimized(true);
		
		return RibbonPanel.getRibbon();
//		return RibbonPanelManager.Instance.getRegionRibbonPanel();
	}

}
