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
 * @CursorManager.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.cursor;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;
import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.swing.JFrame;

import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.util.WisedocUtil;

/**
 * 类功能描述：用于统一获得鼠标在不同情况的样式
 * 
 * 作者：李晓光 创建日期：2008-8-18
 */
public final class CursorManager {

	/* 指定配置文件的位置 */
	private final static String FILE_FULL_NAME = "bin/com/wisii/wisedoc/view/cursor/CustomCursor.properties";
	/* 配置项的前缀 */
	private final static String PREFIX = "Cursor";
	/* 表示图片的名称 */
	private final static String FILE = "File";
	/* 表示热点坐标 */
	private final static String HOT_SPOT = "HotSpot";
	/* 光标描述信息 */
	private final static String NAME = "Name";
	/* 配置文件中KEY的File、HotSpot、Name之前的部分 */
	private final static String PREFIX_NAME = PREFIX + ".{0}.";
	/* 配置文件中表示图片位置的配置项描述 */
	private final static String FILE_PATTERN = PREFIX_NAME + FILE;
	/* 配置文件中表示热点位置的配置项描述 */
	private final static String HOT_PATTERN = PREFIX_NAME + HOT_SPOT;
	/* 配置文件中表示鼠标样式描述信息的配置项描述 */
	private final static String NAME_PATTERN = PREFIX_NAME + NAME;
	/* 检测配置文件中热点信息是否复合定义样式 */
	private final static String REGEX = "\\d{1,}\\s{0,},\\s{0,}\\d{1,}";
	/* 缺省的热点 */
	private final static Point DEFAULT_POINT = new Point(0, 0);
	/* 读取配置用对象 */
	private static Properties configures = new Properties();
	static {
		try {
			if (!isEmpty(FILE_FULL_NAME))
				configures.load(new FileInputStream(FILE_FULL_NAME));

		} catch (FileNotFoundException e) {
			// LOG
			LogUtil.error(e.getMessage());
		} catch (IOException e) {
			// LOG
			LogUtil.error(e.getMessage());
		}
	}

	/**
	 * 
	 * 类功能描述：定义光标的类型
	 * 
	 * 作者：李晓光 创建日期：2008-8-18
	 */
	public static enum CursorType {
		// ---------------系统缺省光标样式-----------------
		/** 默认光标类型（如果没有定义光标，则获得该设置）。 */
		DEFAULT_CURSOR(0),
		/** 十字光标类型。 */
		CROSSHAIR_CURSOR(1),
		/** 文字光标类型 */
		TEXT_CURSOR(2),
		/** 等待光标类型 */
		WAIT_CURSOR(3),
		/** 调整窗口左下角位置的光标类型 */
		SW_RESIZE_CURSOR(4),
		/** 调整窗口右下角位置的光标类型 */
		SE_RESIZE_CURSOR(5),
		/** 调整窗口左上角位置的光标类型 */
		NW_RESIZE_CURSOR(6),
		/** 调整窗口右上角位置的光标类型 */
		NE_RESIZE_CURSOR(7),
		/** 调整窗口上边框位置的光标类型 */
		N_RESIZE_CURSOR(8),
		/** 调整窗口下边框位置的光标类型 */
		S_RESIZE_CURSOR(9),
		/** 调整窗口左边框位置的光标类型 */
		W_RESIZE_CURSOR(10),
		/** 调整窗口右边框位置的光标类型 */
		E_RESIZE_CURSOR(11),
		/** 手状光标类型 */
		HAND_CURSOR(12),
		/** 移动光标类型 */
		MOVE_CURSOR(13),
		// ---------------系统缺省光标样式-----------------
		// ---------------拖拽时用光标样式-----------------
		/** 用于复制操作的默认 Cursor，指示当前允许放置 */
		COPY_DROP_CURSOR("CopyDrop.32x32"),
		/** 用于移动操作的默认 Cursor，指示当前允许放置 */
		MOVE_DROP_CURSOR("MoveDrop.32x32"),
		/** 用于链接操作的默认 Cursor，指示当前允许放置 */
		LINK_DROP_CURSOR("LinkDrop.32x32"),
		/** 用于复制操作的默认 Cursor，指示当前不允许放置 */
		COPY_NO_DROP_CURSOR("CopyNoDrop.32x32"),
		/** 用于移动操作的默认 Cursor，指示当前不允许放置 */
		MOVE_NO_DROP_CURSOR("MoveNoDrop.32x32"),
		/** 用于链接操作的默认 Cursor，指示当前不允许放置 */
		LINK_NO_DROP_CURSOR("LinkNoDrop.32x32"),
		/**  */
		INVALID_CURSOR("Invalid.32x32");
		// ---------------拖拽时用光标样式-----------------
		private int viewType = -1;
		private String cursorName = "";

		/**
		 * 根据整型数据初始鼠标样式对象
		 * 
		 * @param 指定鼠标样式
		 */
		private CursorType(int viewType) {
			this.viewType = viewType;
		}

		public int getViewType() {
			return this.viewType;
		}

		private CursorType(String cursorName) {
			this.cursorName = cursorName;
		}

		public String getViewName() {
			return this.cursorName;
		}
	}

	/**
	 * 
	 * 获得系统中预定义的光标样式
	 * 
	 * @param type
	 *            指定枚举型鼠标样式
	 * @return Cursor 返回相应的鼠标样式，如果指定的样式不存在，则返回缺省的光标样式。
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public final static Cursor getSytemCursor(CursorType type) {
		int viewType = type.getViewType();

		Cursor cursor = null;

		LogUtil.debug(type);

		if (viewType == -1)
			try {
				cursor = Cursor.getSystemCustomCursor(type.getViewName());
			} catch (Exception e) {
				LogUtil.error(e.getMessage());
				cursor = getDefaultCursor();
			}
		else
			cursor = Cursor.getPredefinedCursor(type.getViewType());

		return cursor;
	}

	/**
	 * 
	 * 获得系统默认的光标样式
	 */
	public final static Cursor getDefaultCursor() {
		return Cursor.getDefaultCursor();
	}

	/**
	 * 
	 * 指定配置文件中的名称，来创建光标样式。 Cursor.Binding.File= Binding32x32.gif【指定图标的位置】
	 * Cursor.Binding.HotSpot=0,0【指定热点的位置】 Cursor.
	 * Binding.Name=Binding【指定鼠标样式描述信息】
	 * 
	 * @param name
	 *            指定配置文件中的名称
	 * @return Cursor 返回根据配置信息创建的光标样式
	 */
	public final static Cursor getCustomCursor(String name) {
		if (isEmpty(name) || isEmpty(FILE_FULL_NAME) || configures.isEmpty()) {
			LogUtil.debug("Iamge Name " + name);
			LogUtil.debug("Configure File Name" + FILE_FULL_NAME);
			LogUtil.debug("Configure" + configures.size());

			return getDefaultCursor();
		}
		Image image = getImage(name);
		if (isNull(image)) {
			LogUtil.warn(image);
			return getDefaultCursor();
		}

		return getCustomCursor(image, getName(name), getHotSpot(name));
	}

	/**
	 * 
	 * 根据配置文件中指定的图片名称，创建Image
	 * 
	 * @return Image 返回Image对象
	 */
	private final static Image getImage(String name) {
		String key = MessageFormat.format(FILE_PATTERN, name);
		String file = ConfigureUtil.getProperty(key, configures).trim();

		// LOG
		LogUtil.debug("Key = " + key);
		LogUtil.debug("File = " + file);

		Image image = MediaResource.getImage(file);

		return image;
	}

	/**
	 * 
	 * 根据配置文件中指定的热点坐标，创建Point对象
	 * 
	 * @return Point 返回Point对象
	 */
	private final static Point getHotSpot(String name) {
		String key = MessageFormat.format(HOT_PATTERN, name);
		String s = ConfigureUtil.getProperty(key, "0,0", configures).trim();

		// LOG
		LogUtil.debug("Key = " + key);
		LogUtil.debug("Hot Spot = " + s);

		if (s.length() < 3 || !s.matches(REGEX)) {
			LogUtil.warn("热点坐标值" + s + "配置不正确");
			return DEFAULT_POINT;
		}
		String[] a = s.split(",");
		int x = WisedocUtil.getInt(a[0]);
		int y = WisedocUtil.getInt(a[1]);

		return new Point(x, y);
	}

	/**
	 * 
	 * 获得配置文件中的光标描述信息
	 * 
	 * @return String 返回光标描述信息
	 */
	private final static String getName(String name) {
		String key = MessageFormat.format(NAME_PATTERN, name);
		String s = ConfigureUtil.getProperty(key, "", configures).trim();

		// LOG
		LogUtil.debug("key = " + key);
		LogUtil.debug("name:" + s);

		return s;
	}

	/**
	 * 
	 * 根据指定的图片名称，创建光标样式
	 * 
	 * @param imageName
	 *            指定图片名称
	 * 
	 * @param name
	 *            指定光标描述
	 * @return Cursor 返回光标样式
	 */
	public final static Cursor getCustomCursor(String imageName, String name) {
		return getCustomCursor(imageName, name, DEFAULT_POINT);
	}

	/**
	 * 
	 * 根据指定图片名称，热点、光标描述，创建光标样式。
	 * 
	 * @param imageName
	 *            指定图片名称
	 * @param name
	 *            指定光标描述信息
	 * @param hotSpot
	 *            指定热点
	 * @return Cursor 返回光标样式
	 */
	public final static Cursor getCustomCursor(String imageName, String name,
			Point hotSpot) {
		LogUtil.debug("Image Name:" + imageName);
		LogUtil.debug("Hot Spot:" + hotSpot);
		LogUtil.debug("Name:" + name);

		Image image = MediaResource.getImage(imageName);
		if (isNull(image)) {
			LogUtil.warn("获得的光标图片为Null");
			return getDefaultCursor();
		}
		if (isNull(hotSpot)) {
			LogUtil.warn("指定的光标热点为Null");
			hotSpot = DEFAULT_POINT;
		}

		return getCustomCursor(image, name, hotSpot);
	}

	/**
	 * 
	 * 根据指定的图片、缺省热点、光标描述创建光标样式
	 * 
	 * @param image
	 *            指定光标用图片
	 * @param name
	 *            指定光标描述细心
	 * @return Cursor 返回光标样式
	 */
	public final static Cursor getCustomCursor(Image image, String name) {
		return getCustomCursor(image, name, DEFAULT_POINT);
	}

	/**
	 * 
	 * 根据指定的图片、热点、光标描述创建光标对象
	 * 
	 * @param image
	 *            指定定光标图片
	 * @param name
	 *            指定光标描述信息
	 * @param hotSpot
	 *            指定热点位置
	 * @return Cursor 返回光标对象
	 */
	public final static Cursor getCustomCursor(Image image, String name,
			Point hotSpot) {
		//LOG
		if(isNull(image)){
			LogUtil.warn("Image:" + image);
			return getDefaultCursor();
		}
		if(isNull(hotSpot)){
			LogUtil.warn("Hot Spot:" + hotSpot);
			return getDefaultCursor();
		}
		
		LogUtil.debug("Image:" + image);
		LogUtil.debug("Hot Spot:" + hotSpot);
		LogUtil.debug("Name:" + name);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		return kit.createCustomCursor(image, hotSpot, name);
	}

	/**
	 * 测试用
	 */
	public static void main(String[] args) {
		JFrame fr = new JFrame();
		fr.setSize(600, 400);
		fr.setCursor(getSytemCursor(CursorType.COPY_NO_DROP_CURSOR));
		fr.setVisible(true);
	}
}
