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
 * @ImagePreviewPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


/**
 * 类功能描述：文件选择用的预览面板
 * 
 * 作者：李晓光 创建日期：2007-08-30
 */
@SuppressWarnings("serial")
public class ImagePreviewPanel extends JPanel
{

	/* 定义预览图片的宽度、高度 */
	private int width, height;

	/* 预览图片引用 */
	private Image image;
	/* 初始化大小 */
	private static final int ACCSIZE = 155;

	/* 预览面板的边框 */
	Border borderList = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
			SystemColor.desktop, SystemColor.desktop);

	public ImagePreviewPanel()
	{
		setPreferredSize(new Dimension(ACCSIZE, -1));
		setBorder(borderList);
		setVisible(false);
	}

	/**
	 * 显示图片
	 * 
	 * @param file
	 *            指定图片文件
	 * @return void 无
	 */
	public void viewImage(File file)
	{
		Image image = loadImage(file);
		scaleImage(image);
		if (!isVisible())
			setVisible(true);
	}

	/**
	 * 加载当前选择的图片
	 * 
	 * @param file
	 *            指定当前文件
	 * @return 根据File返回Image对象
	 */
	private Image loadImage(File file)
	{
		Image image = null;
		if (file == null || !file.exists())
			return image;
		String fileName = file.getAbsolutePath();

		String suffix = WiseDocFileFilter.getExtension(file);

		if (!WiseDocFileChooser.isImageSuffix(suffix))
			return image;

		ImageIcon icon = new ImageIcon(fileName);
		image = icon.getImage();
		return image;
	}

	/**
	 * 确定图片的显示宽度和高度
	 * 
	 * @param image
	 */
	private void scaleImage(Image image)
	{
		if (image == null)
			return;
		width = image.getWidth(this);
		height = image.getHeight(this);
		double ratio = 1.0;

		if (width >= height)
		{
			ratio = (double) (ACCSIZE - 5) / width;
			width = ACCSIZE - 5;
			height = (int) (height * ratio);
		} else
		{
			if (getHeight() > 150)
			{
				ratio = (double) (ACCSIZE - 5) / height;
				height = ACCSIZE - 5;
				width = (int) (width * ratio);
			} else
			{
				int h = getHeight();
				if(h == 0)
					h = 150;
				ratio = (double) h / height;
				height = h;//getHeight();
				width = (int) (width * ratio);
			}
		}
		if(width != 0 && height != 0)
		this.image = image.getScaledInstance(width, height, Image.SCALE_FAST);//SCALE_AREA_AVERAGING
		repaint();
	}

	/**
	 * 绘制加载的图片
	 * 
	 * @param g
	 *            指定绘制对象
	 * @return void 无
	 */
	public void paintComponent(Graphics g)
	{
		g.setColor(getBackground());
		g.fillRect(0, 0, ACCSIZE, getHeight());
		/*g.drawImage(image, getWidth() / 2 - width / 2, getHeight() / 2 - height
				/ 2, this);*/
		g.drawImage(image, (getWidth() - width) >> 1, (getHeight() - height) >> 1, this);
	}
}
