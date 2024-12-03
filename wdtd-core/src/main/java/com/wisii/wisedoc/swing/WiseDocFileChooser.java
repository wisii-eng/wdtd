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
 * @WiseDocFileChooser.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

/**
 * 
 * 类功能描述：重写文件选择器，方便增加新的功能。
 * 
 * 作者：李晓光 创建日期：2007-08-30
 */
@SuppressWarnings("serial")
public class WiseDocFileChooser extends JFileChooser implements
		PropertyChangeListener
{

	/**
	 * 根据默认值创建文件选择对话框，创建文件选择对话框
	 * 
	 */
	private WiseDocFileChooser()
	{
		super(System.getProperty("user.dir"));

		addPropertyChangeListener(this);
		setAccessory(panel);
	}

	/**
	 * 
	 * 向文件选择器中加入过滤器
	 * 
	 * @param fileFilter
	 *            指定过滤器
	 * @return void 无
	 */
	public void addFileFilter(FileFilter... filters)
	{
		if (filters == null || filters.length <= 0)
			return;

		for (FileFilter filter : filters)
		{
			addChoosableFileFilter(filter);
		}
		setAcceptAllFileFilterUsed(true);
		setFileFilter(filters[0]);
	}

	/**
	 * 属性监听对象，判断如果当前路径发生变化是，把最新的值保存的配置文件中
	 * 
	 * @param evt
	 *            对事件源的数据整合
	 * @return void 无
	 */
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equalsIgnoreCase(evt
				.getPropertyName()))
		{
			File newDir = (File) evt.getNewValue();
			if (isSavePath())
			{
				// 添加写配置文件的代码
				// ConfigureUtil.setFilePath(newDir.getPath());
			}

		} else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY
				.equalsIgnoreCase(evt.getPropertyName()))
		{
			File file = (File) evt.getNewValue();
			String suffix = WiseDocFileFilter.getExtension(file);
			if (file == null || !file.exists() || !isImageSuffix(suffix))
			{
				getAccessory().setVisible(false);
				return;
			}
			setSelectedFile(file);
			panel.viewImage(file);
		}
	}

	public static boolean isImageSuffix(String suffix)
	{
		List<String> list = Arrays.asList(IMAGESUFFIX);
		return list.contains(suffix.toUpperCase());
	}

	/**
	 * 这个是获得文件选择对话框的唯一接口，获得文件选择对话框
	 * 
	 * @param 无
	 * @return {@link JFileChooser} 返回文件选择对话框
	 */
	public static JFileChooser getFileChooseDialog()
	{
		return new WiseDocFileChooser();
	}

	/**
	 * 
	 * 获得文件选择对话框，同时指定是否保存改变的基路经
	 * 
	 * @param savePath
	 *            true 保存路径到配置文件，false 不保存基路经
	 * @return 文件选择对话框
	 */
	public static JFileChooser getFileChooseDialog(boolean savePath)
	{
		WiseDocFileChooser chooser = (WiseDocFileChooser) getFileChooseDialog();
		chooser.setSavePath(savePath);

		return chooser;
	}

	public JDialog getCustomDialog()
	{
		return createCustomDialog(getParent());
	}

	/**
	 * 显示自定义文件选择对话框
	 * 
	 * @return 对话框的返回状态【确定那个按钮的触发使得对话框返回】
	 */
	public DialogResult showDialog(int dialogType)
	{
		setDialogType(dialogType);
		JDialog dialog = getCustomDialog();
		if (dialog == null)
			return result;
		DialogSupport.centerOnScreen(this);
		dialog.setVisible(true);
		return result;
	}

	/**
	 * 释放自定义对话框资源
	 * 
	 * @param 无
	 * @return void 无
	 */
	public void distroy()
	{
		if (customDialog == null)
			return;
		customDialog.dispose();
		customDialog = null;
	}

	/**
	 * 为对话框设置返回状态
	 * 
	 * @param result
	 */
	public void setResult(DialogResult result)
	{
		this.result = result;
	}

	/**
	 * 创建自定义对话框
	 * 
	 * @param parent
	 *            指定对话框的拥有者
	 * @return 返回创建的对话框对象
	 */
	private JDialog createCustomDialog(Component parent)
	{
		customDialog = super.createDialog(parent);
		return customDialog;
	}

	private ImagePreviewPanel panel = new ImagePreviewPanel();

	/* 定义图片后缀名集合 */
	private static final String[] IMAGESUFFIX =
	{ "JPG", "BMP", "JPEG", "GIF", "PNP" };

	/* 创建自定义对话框 */
	private JDialog customDialog = null;

	private boolean savePath = true;

	private DialogResult result = DialogResult.Cancel;

	public boolean isSavePath()
	{
		return savePath;
	}

	public void setSavePath(boolean savePath)
	{
		this.savePath = savePath;
	}

	/* 定义文件选择框的内部对象 */
	// private static WiseDocFileChooser FILECHOOSER = new WiseDocFileChooser();
}