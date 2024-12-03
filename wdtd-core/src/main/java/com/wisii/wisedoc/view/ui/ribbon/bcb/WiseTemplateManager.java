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

package com.wisii.wisedoc.view.ui.ribbon.bcb;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * 用于管理模板库几个区域的管理类
 * 
 * @author 闫舒寰
 * @version 1.0 2009/04/01
 */
public enum WiseTemplateManager
{

	Instance;

	private static File rootfile = new File(System.getProperty("user.dir")
			+ "/Templates");

	// 文件树
	private JFileTree fileTree;

	// 文件树面板
	private JPanel fileTreePanel;

	// 文件bcb
	private WiseTreeBcb filelist;

	// 文件bcb面板
	private JPanel fileListPanel;

	// 管理器工具栏
	WiseTemplateToolBar wttb;

	// 预览面板
	TemplatePreviewPanel previewPanel;

	// 当前选择的文件对象
	private static File currentSelectedFolder;

	public void setCurrentSelectedFolder(File currentSelectedFolder) {
		this.currentSelectedFolder = currentSelectedFolder;
	}

	// 当前选中的模板
	private File currentSelectedTemplate;

	/**
	 * 取得当前选中的模板文件
	 * 
	 * @return 当前选中的模板
	 */
	public File getCurrentSelectedTemplate()
	{
		return currentSelectedTemplate;
	}

	/**
	 * 取得当前选中的模板分类文件夹
	 * 
	 * @return 当前选中的模板分类文件夹
	 */
	public static File getCurrentSelectedFolder()
	{
		return currentSelectedFolder;
	}

	// 当用户执行操作时更新面板
	public void updataPanels()
	{
//		System.out.println(fileTree.gets);
//		updateFileTreePanel();
		//刷新工具栏
		WiseTemplateToolBar.updateUIState();
		
		filelist.updateFilelist();
		if(previewPanel != null){
			previewPanel.updatePreview();
		}
	}

	// 当用户点击模板分类树的时候，更新manager里面的当前选中模板文件夹
	public void updataFileTree()
	{
		if (fileTree != null)
		{
			this.currentSelectedFolder = fileTree.getSelectFile();
			this.currentSelectedTemplate = null;
			// filelist.updateJlist(currentSelectedFolder);
			// System.out.println(currentSelectedFolder);
			updataPanels();
		}
	}

	// 当用户点击模板文件时，更新manager里面的当前选中模板文件
	public void updataFileList(Object file)
	{
		if (file instanceof File)
		{
			File ff = (File) file;
			this.currentSelectedTemplate = ff;
			// System.err.println(ff);
			updataPanels();
		}
	}
	
	// 初始化对话框面板
	public void initialManagerPanels()
	{
		initialFileTreePanel();
		initialFileListPanel();
		initialToolBarPanel();
		initialPreviewPanel();
		
		//刷新工具栏
		WiseTemplateToolBar.updateUIState();
	}

	private void initialFileTreePanel()
	{
		// FileTreePanel ftp = new FileTreePanel();
		fileTreePanel = new JPanel();
		fileTreePanel.setLayout(new BorderLayout());
		JFileTree tree = new JFileTree();
		this.fileTree = tree;
		fileTree.setEditable(true);
		// temp.add(tree, BorderLayout.CENTER);
		JScrollPane jsp = new JScrollPane(tree);
		fileTreePanel.add(jsp, BorderLayout.CENTER);
	}
	
	public void updateFileTreePanel() {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				fileTreePanel.removeAll();
				JFileTree tree = new JFileTree();
				fileTree = tree;
//				System.out.println(WiseTemplateManager.Instance.getCurrentSelectedFolder());
				File currentFolder = WiseTemplateManager.Instance.getCurrentSelectedFolder();
				try {
					//孩子一级被删掉了，则展开父亲一级
					if (currentFolder != null) {
						fileTree.setSelectFile(currentFolder);
					}
				} catch (Exception e) {
//					e.printStackTrace();
					try {
						fileTree.setSelectFile(currentFolder.getParentFile());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
//				fileTree.setExpandsSelectedPaths(true);
				
//				fileTree.expandFile(currentFolder.getParentFile());
				// temp.add(tree, BorderLayout.CENTER);
				JScrollPane jsp = new JScrollPane(tree);
				fileTreePanel.add(jsp, BorderLayout.CENTER);
				fileTreePanel.updateUI();
				//刷新工具栏
				WiseTemplateToolBar.updateUIState();
			}
		});
	}

	private void initialFileListPanel()
	{
		fileListPanel = new JPanel();
		fileListPanel.setLayout(new BorderLayout());
		WiseTreeBcb wtb = new WiseTreeBcb();
		filelist = wtb;
		fileListPanel.add(wtb, BorderLayout.CENTER);
	}

	private void initialPreviewPanel()
	{
		previewPanel = new TemplatePreviewPanel();
	}

	private void initialToolBarPanel()
	{
		wttb = new WiseTemplateToolBar();
	}

	/**
	 * 取得工具栏
	 * 
	 * @return
	 */
	public JComponent getToolBarPanel()
	{
		return wttb.getToolBar();
	}

	/**
	 * 取得模板分类树面板
	 * 
	 * @return
	 */
	public JPanel getFileTreePanel()
	{
		return fileTreePanel;
	}

	/**
	 * 取得模板列表面板
	 * 
	 * @return
	 */
	public JPanel getFileListPanel()
	{
		return fileListPanel;
	}

	/**
	 * 取得预览区域面板
	 * 
	 * @return
	 */
	public JPanel getPreviewPanel()
	{
		return previewPanel;
	}

	public JPanel getFilelist()
	{
		return filelist;
	}

	public static File getRootfile()
	{
		return rootfile;
	}

}
