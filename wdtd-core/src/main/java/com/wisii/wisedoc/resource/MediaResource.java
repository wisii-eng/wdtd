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
 * @MediaResource.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.resource;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;
import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jvnet.flamingo.common.icon.IcoWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;

import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.view.cursor.CursorManager;

/**
 * 类功能描述：用于获得图片、音频、视频信息。【目前仅实现了对图片信息的获取】
 * 
 * 
 * 作者：李晓光 创建日期：2008-8-18
 */
public final class MediaResource {
	/* 缺省基路径 */
	private final static String DEFAULT_PATH = "/com/wisii/wisedoc/resource/image/";

	/* 代码中使用的基路经 */
	private static String BASE_PATH = DEFAULT_PATH;

	/* 配置文件中【基路经】保存时采用的KEY */
	public final static String IMAGE_BASE_PATH = "IMAGE_BASE_PATH";
	static {
		// TODO 读取配置文件中的基路径
		String path = ConfigureUtil.getProperty(IMAGE_BASE_PATH);		
		setBasePath(path);
	}

	/**
	 * 
	 * 设置基路经位置
	 * 
	 * @param path
	 *            指定基路经位置，如果路径为Null，则采用缺省路径。
	 */
	public final static void setBasePath(String path) {
		if (isEmpty(path)){
			LogUtil.warn("set path:" + path);
			path = DEFAULT_PATH;
		}
		BASE_PATH = path;
		LogUtil.debug("Base Path" + BASE_PATH);
	}

	/**
	 * 
	 * 把基路经设置为缺省路径
	 * 
	 */
	public final static void reset() {
		setBasePath(DEFAULT_PATH);
	}

	/**
	 * 
	 * 获得有效基路经信息
	 * 
	 * @return String 如果配置文件中指定了基路经，则使用配置文件中的路径，如果没有指定，则采用缺省基路经。
	 */
	private static String getBasePath() {
		return BASE_PATH;
	}

	/**
	 * 
	 * 根据指定的图片名称，创建Image对象
	 * 
	 * @param name
	 *            指定图片名称
	 * @return Image 如果路径合法，且图片存在返回Image对象，否则：Null
	 */
	public final static Image getImage(String name) {

		Image image = getBufferedImage(name);

		return image;
	}

	/**
	 * 
	 * 根据指定的图片名称，创建ImageIcon对象
	 * 
	 * @param name
	 *            指定图片名称
	 * @return ImageIcon 如果路径合法，且图片存在返回ImageIcon对象，否则：Null
	 */
	public final static ImageIcon getImageIcon(String name) {
		Image image = getBufferedImage(name);
		ImageIcon icon = null;

		if (image != null)
			icon = new ImageIcon(image);

		return icon;
	}

	/**
	 * 
	 * 根据指定的图片名称，创建BufferedImage对象
	 * 
	 * @param name
	 *            指定图片名称
	 * @return BufferedImage 如果路径合法，且图片存在返回BufferedImage对象，否则：Null
	 */
	public final static BufferedImage getBufferedImage(String name) {
		BufferedImage image = null;
		if (isEmpty(name)){
			// LOG
			LogUtil.warn("Image Name:" + name);
			return image;			
		}
		//LOG
		LogUtil.debug("File Name:" + name);
		String path = getFullPath(name);
		//LOG
		LogUtil.debug("Full Path:" + path);
		
		File file = new File(path);
		URL url = URL.class.getResource(path);

		try {
			if (file.exists())
				image = ImageIO.read(file);
			else if (url != null)
				image = ImageIO.read(url);
			else{
				LogUtil.warn("给定的图片" + name + "找不到。");
				
			}
		} catch (IOException e) {
			// LOG
			LogUtil.error(e.getMessage());
		}

		return image;
	}

	/**
	 * 
	 * 根据指定的基路经和文件名，把两者连接起来。如果基路经不是以【\】结尾，要添加。
	 * 
	 * @param name
	 *            指定图片的名称
	 * @return String 返回完整的文件名
	 */
	private final static String getFullPath(String name) {
		String path = getBasePath();
		if (isEmpty(path))
			return name;
		if (!path.endsWith(File.separator) && !path.endsWith("/"))
			path += File.separator;

		path += name;
		return path;
	}

	/**
	 * 
	 * 根据指定的字节数组，创建Image
	 * 
	 * @param data
	 *            指定字节数组
	 * @return Image 返回相应的Image对象
	 */
	public final static Image getImage(byte[] data) {
		Image image = null;
		if (isNull(data)){
			LogUtil.warn("给定的字节数组为Null");
			return image;
		}
		ByteArrayInputStream stream = new ByteArrayInputStream(data);
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			// LOG
			LogUtil.error(e.getMessage());
		}
		return image;
	}

	/**
	 * 
	 * 根据指定的字节数组【内存中图片是由byte数组组成】，创建图标
	 * 
	 * @param date
	 *            指定图片的内存数据
	 * @return ImageIcon 返回图标
	 */
	public final static ImageIcon getImageIcon(byte[] data) {

		ImageIcon icon = null;
		if (isNull(data)){
			LogUtil.warn("给定的字节数组为Null");
			return icon;
		}

		icon = new ImageIcon(data);
		return icon;
	}

	/**
	 * 
	 * 根据指定的URL获得相应的图标。【如果要获得网络图片，应采用该方法获得】
	 * 
	 * @param url
	 *            指定图片的RUL
	 * @return ImageIcon 根据指定的URL创建相应的图片
	 */
	public final static ImageIcon getImageIcon(URL url) {
		ImageIcon icon = null;
		if (isNull(url)){
			LogUtil.warn("给定的URL为Null");
			return icon;
		}
		icon = new ImageIcon(url);

		return icon;
	}
	
	/**
	 * 
	 * 根据图片名字返回该图片的URL
	 * 
	 * @param imageName	图片名字
	 * @return	图片在wisedoc2.0中的URL地址
	 */
	public final static URL getImageURL(String imageName){
		StringBuffer imageDir = new StringBuffer(getFullPath(imageName));
		imageDir.deleteCharAt(0);
		URL imageURL = URLClassLoader.getSystemClassLoader().getResource(imageDir.toString());
//		System.out.println(getFullPath(imageName));
		if(imageURL == null){
			LogUtil.warn("给定的imageName不正确");
//			System.err.println("Can not find image: " + imageName);
			return null;
		}		
		return imageURL;
	}
	
	/**
	 * 采用默认图标的初始值取得可变大小的图标（32×32）
	 * 
	 * @param imageName	图标名称
	 * @return	可变大小的图标
	 */
	public final static ResizableIcon getResizableIcon(String imageName){
		if(imageName.endsWith(".gif")){
			return ImageWrapperResizableIcon.getIcon(MediaResource.getImageURL(imageName), new Dimension(32, 32));
		} else if (imageName.endsWith(".ico")){
			return IcoWrapperResizableIcon.getIcon(MediaResource.getImageURL(imageName), new Dimension(32, 32));
		}
		
		return null;
	}
	
	/**
	 * 取得可变大小的图标
	 * 
	 * @param imageName 图标名称
	 * @param initialDimension	图标初始大小
	 * @return	可变大小的图标
	 */
	public final static ImageWrapperResizableIcon getResizableIcon(String imageName, Dimension initialDimension){
		return ImageWrapperResizableIcon.getIcon(MediaResource.getImageURL(imageName), initialDimension);
	}

	/* 测试用 */
	public static void main(String[] args) {
		final Image image = getImage("1.gif");
		final JFrame fr = new JFrame() {
			public void paint(Graphics g) {
				super.paint(g);

				g.drawImage(image, 0, 30, this);
			}
		};
		fr.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				fr.repaint();
			}
		});
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel lab = new JLabel();
		lab.setText("This is a test");
		fr.add(lab);		
		fr.setPreferredSize(new Dimension(600, 400));
		fr.setSize(600, 400);
		Cursor c = CursorManager.getCustomCursor("test");
		fr.setCursor(c);
		fr.setVisible(true);

	}

}
