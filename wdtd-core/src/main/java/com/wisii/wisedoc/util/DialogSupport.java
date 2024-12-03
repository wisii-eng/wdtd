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
 * @DialogSupport.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.util;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.configure.RecentOpenFile;
import com.wisii.wisedoc.swing.WiseDocFileChooser;


/**
 * 类功能描述：
 * 
 * 作者：李晓光 创建日期：2007-6-18
 */
public abstract class DialogSupport
{

	public static enum FileDialogType
	{
		open, save
	}

	/**
	 * 
	 * 使得显示控件显示在屏幕的正中央
	 * 
	 * @param Component
	 *            将要显示在屏幕中央的组件
	 * @return void 无返回值
	 */
	public static void centerOnScreen(Component aComponent)
	{
		if (aComponent == null)
			return;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension compSize = aComponent.getSize();

		compSize.width = Math.min(screenSize.width, compSize.width);
		compSize.height = Math.min(screenSize.height, compSize.height);

		aComponent.setSize(compSize);
		aComponent.setLocation((screenSize.width - compSize.width) / 2,
				(screenSize.height - compSize.height) / 2);
	}

	/**
	 * 
	 * 返回自身对象。
	 * 
	 * @return DialogSupport 产生自身对象。
	 */
	// public static DialogSupport getDialogSupport()
	// {
	// return support;
	// }
	/**
//	 * 
//	 * 返回替换处理对话框
//	 * 
//	 * @return {@link JDialog} 返回处理对话框
//	 */
//	public static ReplaceDialog getRePlaceDialog(List<Object> searchResult)
//	{
//		return new ReplaceDialog(LayoutManager.getMainFrame(), searchResult);
//	}
//
//	/**
//	 * 
//	 * 返回默认的对话框
//	 * 
//	 * @return JDialog 返回选择Tab用对话框
//	 */
//	public static ChooseDialog getChooseDialog()
//	{
//		ChooseDialog dialog = new ChooseDialog();
//
//		/* 【选择Tab、打开最近文档】用对话框 */
//		return dialog;
//	}

//	/**
//	 * 
//	 * 返回指定标题的tab【历史文件选择】选择对话框
//	 * 
//	 * @param title
//	 *            指定对话框的标题
//	 * @return {@link ChooseDialog} tab【历史文件】选择对话框
//	 */
//	public static ChooseDialog getChooseDialog(String title)
//	{
//		return (ChooseDialog) getChooseDialog(LayoutManager.getMainFrame(),
//				title, true);
//	}

//	/**
//	 * 
//	 * 返回指定了拥有者、标题、模式的对话框
//	 * 
//	 * @param onwer
//	 *            指定对话框的拥有则
//	 * @param title
//	 *            指定对话框的标题
//	 * @param modal
//	 *            指定对话框的模式
//	 * @return {@link JDialog} 返回相应的对话框
//	 */
//	public static JDialog getChooseDialog(Frame owner, String title,
//			boolean modal)
//	{
//		ChooseDialog dialog = new ChooseDialog(owner, title, modal);
//
//		/* 【选择Tab、打开最近文档】用对话框 */
//		return dialog;
//	}

	/**
	 * 
	 * @param isOpen
	 * @return
	 */
	public static File getFileDialog(FileDialogType isOpen)
	{
		return getFileDialog(SystemManager.getMainframe(), isOpen, null,null);
	}

	/**
	 * 根据指定信息获得选择的文件
	 * 
	 * @param isOpen
	 *            指定对话框的模式【打开、保存】
	 * @param filter
	 *            指定选择对话框的过滤器
	 * @return 返回用户选择的文件信息
	 */
	public static File getFileDialog(FileDialogType isOpen, FileFilter filter)
	{
		return getFileDialog(SystemManager.getMainframe(), isOpen, filter,null);
	}
	/**
	 * 根据指定信息获得选择的文件
	 * 
	 * @param isOpen
	 *            指定对话框的模式【打开、保存】
	 * @param filter
	 *            指定选择对话框的过滤器
	 * @return 返回用户选择的文件信息
	 */
	public static File getFileDialog(FileDialogType isOpen, FileFilter filter,String path)
	{
		return getFileDialog(SystemManager.getMainframe(), isOpen, filter,path);
	}
	/**
	 * 
	 * 调用打开文件对话框。
	 * 
	 * @param parent
	 *            该对话框依赖的父窗体。
	 * @param FileDialogType
	 *            指明是打开文件模式，还是保存 文件模式。
	 * @return int 对话框上打开按钮、和取消按钮被出发是，均返回一个整数值。这个整数值来区分是打开按钮触发了事件，还是取消按钮触发了事件。
	 */
	private static File getFileDialog(Component parent, FileDialogType open,
			FileFilter filter,String path)
	{
		JFileChooser chooser = getDialog();
		//默认是图片
		if(path==null||path.isEmpty())
		{
			path="graphics";
		}
		//由于只有选择图片时才调用，而Designer的图片显示路径只能为产品目录下的graphics路径下，因此设置了当前路径
		chooser.setCurrentDirectory(new File(System
							.getProperty("user.dir")
							+ File.separator
							+ path));
		File selectFile = null;
		if (chooser == null)
			return selectFile;

		if (filter != null) {
			chooser.addChoosableFileFilter(filter);
			chooser.setAcceptAllFileFilterUsed(true);
			chooser.setFileFilter(filter);
		}

		int dialogResult = -1;

		centerOnScreen(chooser);

		if (FileDialogType.open.equals(open))
		{
			dialogResult = chooser.showOpenDialog(parent);
		}
		else
			dialogResult = chooser.showSaveDialog(parent);

		if (dialogResult == JFileChooser.APPROVE_OPTION)
		{
			selectFile = chooser.getSelectedFile();
		}

		return selectFile;
	}

	/**
	 * 根据默认值创建文件选择对话框
	 * 
	 * @param 无
	 * @return {@link JFileChooser} 返回创建的对话框
	 */
	public static JFileChooser getDialog()
	{
		return WiseDocFileChooser.getFileChooseDialog();
	}

	/**
	 * 【添加】 李晓光 2007-07-18 获得文件选择对话框
	 * 
	 * @param basePath
	 *            指定文件的基路径
	 * @return {@link JFileChooser} 文件选择器
	 */
	public static JFileChooser getDialog(FileFilter... fileters)
	{
		WiseDocFileChooser chooser = (WiseDocFileChooser) getDialog(true);
		chooser.addFileFilter(fileters);
		String directory = RecentOpenFile.getRencentEnableDirectory();
		if (directory != null)
		{
			chooser.setCurrentDirectory(new File(directory));
		}
		return chooser;
	}

	/**
	 * 
	 * 获得一个文件选择对话框，同时指定是否保存路径
	 * 
	 * @param savePath
	 *            True 保存 False 不保存
	 * @return {@link JFileChooser} 文件选择对话框
	 */
	public static JFileChooser getDialog(boolean savePath)
	{
		return WiseDocFileChooser.getFileChooseDialog(savePath);
	}

	public static JFileChooser getDialog(boolean savePath,
			FileFilter... fileters)
	{
		WiseDocFileChooser chooser = (WiseDocFileChooser) WiseDocFileChooser
				.getFileChooseDialog(savePath);
		chooser.addFileFilter(fileters);
		return chooser;
	}

	/**
	 * 【添加】 李晓光 2007-07-18 获得指定文件
	 * 
	 * @param parent
	 *            指定父窗口
	 * @param open
	 *            打开文件open， 保存文件 save 【FileDialogType】
	 * @param chooser
	 *            文件选择器
	 * @return {@link File} 返回文件
	 */
	public static File getFile(Component parent, FileDialogType open,
			JFileChooser chooser)
	{
		File selectFile = null;
		if (open == null || chooser == null)
			return selectFile;

		int dialogResult = -1;
		centerOnScreen(chooser);

		if (FileDialogType.open.equals(open))
		{
			dialogResult = chooser.showOpenDialog(parent);
		}
		else
			dialogResult = chooser.showSaveDialog(parent);

		if (dialogResult == JFileChooser.APPROVE_OPTION)
		{
			selectFile = chooser.getSelectedFile();
		}

		return selectFile;
	}

	/**
	 * 【添加】 李晓光 2007-08-13 获得指定文件
	 * 
	 * @param parent
	 *            指定父窗口
	 * @param open
	 *            打开文件open， 保存文件 save 【FileDialogType】
	 * @param dialogTitle
	 *            对话框标题
	 * @return {@link String} 返回文件名称
	 */
	public static String getFilePath(Component parent, FileDialogType open,
			String dialogTitle)
	{
		JFileChooser chooser = getDialog((FileFilter) null);
		chooser.setDialogTitle(dialogTitle);

		File file = getFile(parent, open, chooser);

		if (file == null)
			return "";

		return file.getAbsolutePath();
	}

//	/**
//	 * 
//	 * 创建默认的检索、替换对话框
//	 * 
//	 * @return {@link List} 用户录入信息的List
//	 */
//	public static List<String> getFindDialog(OpenState openState)
//	{
//
//		return getFindDialog(SystemManager.getMainframe(), null, openState);
//	}

	/**
	 * 
	 * 创建指定了拥有则、标题、模式的对话框
	 * 
	 * @param owner
	 *            指定对话框的拥有者
	 * @param title
	 *            指定对话框的标题
	 * @param modal
	 *            指定对话框的模式
	 * @return {@link List} 用户录入信息的List
	 */
//	public static List<String> getFindDialog(Frame owner, String title,
//			OpenState openState)
//	{
//		FindDialog findDialog;
//		if (title == null || title == "")
//			findDialog = new FindDialog(owner, openState);
//		else
//			findDialog = new FindDialog(owner, title, true, openState);
//
//		/* 窗口显示在屏幕的正中央 */
//		DialogSupport.centerOnScreen(findDialog);
//		findDialog.setVisible(true);
//
//		return findDialog.getCollectInfo();
//	}

	/**
	 * 
	 * 网格布局管理器加入控件
	 * 
	 * @param {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public static void constrain(Container c, Component p, int x, int y,
			int width, int height, int fill, int anchor, double weightx,
			double weighty, int insetTop, int insetLeft, int insetRight,
			int insetBottom)
	{
		GridBagConstraints cc = new GridBagConstraints();
		cc.gridx = x;
		cc.gridy = y;
		cc.gridwidth = width;
		cc.gridheight = height;
		cc.fill = fill;
		cc.anchor = anchor;
		cc.weightx = weightx;
		cc.weighty = weighty;
		if (insetTop != 0 || insetBottom != 0 || insetLeft != 0
				|| insetRight != 0)
			cc.insets = new Insets(insetTop, insetLeft, insetBottom, insetRight);
		java.awt.LayoutManager lm = c.getLayout();
		GridBagLayout gbl = (GridBagLayout) lm;
		gbl.setConstraints(p, cc);
		c.add(p);
	}

	/**
	 * 
	 * 
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public static JComponent getHR()
	{
		JComponent HR = new JPanel();
		HR.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(1, 0, 0, 0, HR.getBackground().darker()),
				BorderFactory.createMatteBorder(1, 0, 0, 0, HR.getBackground()
						.brighter())));
		return HR;
	}

	private DialogSupport()
	{

	}

	// 【添加：START】 by 李晓光 2008-01-09
	/**
	 * 获得从颜色对话框中设定的颜色，并把获得的颜色转换为WisedocColor类型
	 * 
	 * @param color
	 *            指定初始化用颜色
	 * @return {@link WisedocColor} 自定义Color
	 */
//	public static WisedocColor getSelectedColor(Color color)
//	{
//		color = JColorChooser.showDialog(null,
//				getMessage("Wisedoc.Color.Choose.Title"), color);
//		if (color == null)
//			return null;
//		return new WisedocColor(color, LayerType.forefront);
//
//	}

	// 【添加：END】 by 李晓光 2008-01-09
	/**
	 * 产生自身对象。
	 */
	// private static DialogSupport support = new DialogSupport();
}
