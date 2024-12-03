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
 * 
 */
package com.wisii.wisedoc.view.ui.ribbon.bcb;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.wisii.wisedoc.io.TemplateReader;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：主要用来预览模板文件 做法是把模板文件第一页保存成图片, 把图标保存到模板[Zip]文件中,预览 时，从Zip文件中读取图像文件,并在
 * 当前Panle预览图片。 作者：李晓光 创建日期：2009-4-2
 */
@SuppressWarnings("serial")
public class TemplatePreviewPanel extends JPanel {

	public TemplatePreviewPanel() {
		setLayout(new GridBagLayout());
		setBorder(border);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (isNull(image))
					return;
				imageArea = scaleImage(image);
				updateImage();
			}
		});
		setPreferredSize(new Dimension(300, 200));
		setMinimumSize(new Dimension(((SPACE + SPACE_START) << 1) + MIN_SPACE, ((SPACE + SPACE_BEFORE) << 1)));
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;

		loadImage(fileName);
	}

	public void updatePreview() {
		loadImage(WiseTemplateManager.Instance.getCurrentSelectedTemplate());
	}

	private void loadImage(final String fileName) {
		loadImage(new File(fileName));
	}

	private void loadImage(final File file) {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					if (!isNull(file) && file.exists() && !file.isDirectory()) {
						TemplateReader reader = new TemplateReader();
						image = reader.readIcon(file);
						imageArea = scaleImage(image);
					} else {
						reset();
					}
				} catch (IOException e) {
					if (LogUtil.canDebug())
						LogUtil.debugException(e.getMessage(), e);
				}
				updateImage();
			}
		};
		t.start();
	}

	private void updateImage() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				TemplatePreviewPanel.this.repaint();
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}

	private void reset() {
		image = null;
		imageArea = null;
	}

	private Rectangle scaleImage(Image image) {
		if (isNull(image))
			return new Rectangle();
		int imageW = image.getWidth(this);
		int imageH = image.getHeight(this);
		int areaW = getWidth() - ((SPACE + SPACE_START) << 1);
		int areaH = getHeight() - ((SPACE + SPACE_BEFORE) << 1);
		double imageRatio = (double)imageW / imageH;
		double areaRatio = (double)areaW / areaH;
		int x = SPACE + SPACE_START, y = SPACE + SPACE_BEFORE, w = areaW, h = areaH;

		if (areaRatio >= imageRatio) {
			x += (int)(areaW - areaH * imageRatio) >> 1;//【除以2】
			w = (int)(areaH * imageRatio);
		} else {
			y += (int)(areaH - areaW / imageRatio) >> 1;
			h = (int)(areaW * 1.0 / imageRatio);
		}
		w = (w < 0) ? 0 : w;
		h = (h < 0) ? 0 : h;
		return new Rectangle(x, y, w, h);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, getWidth(), getHeight());
		if (isNull(image))
			return;
		Rectangle r = imageArea;
		if (isNull(r))
			return;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(image, r.x, r.y, r.width, r.height, this);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(r.x, r.y, r.width, r.height);
	}
	/* 定义图像 */
	private Image image = null;
	/* 定义占据/区域 */
	private Rectangle imageArea = new Rectangle();
	/* 定义模板文件名称 */
	private String fileName = "";
	/* 定义预览蓝色边框外的宽度 */
	private final static int SPACE = 10;
	/* 定义蓝色边框内到图像上边缘的距离 */
	private final static int SPACE_BEFORE = 20;
	/* 定义蓝色边框内道图像左边缘的距离 */
	private final static int SPACE_START = 20;
	/* 定义图像的最小宽度 */
	private final static int MIN_SPACE = 50;
	/* 定义最外层边框 */
	private Border emptyBorder = BorderFactory.createEmptyBorder(SPACE, SPACE, SPACE, SPACE);
	/* 定义蓝色边框 */
	private Border innerBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, SystemColor.desktop, SystemColor.desktop);
	/* 定义组合边框 */
	private Border border = BorderFactory.createCompoundBorder(emptyBorder, innerBorder);

	public static void main(String[] args) {
		JFrame fr = new JFrame("");
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(800, 600);
		fr.add(new TemplatePreviewPanel());

		fr.setVisible(true);
	}
}
