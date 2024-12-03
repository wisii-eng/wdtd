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
 * @MessageResource.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.resource;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.wisii.wisedoc.configure.ConfigureConstants;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：获得资源包中信息
 * 
 * 作者：李晓光 创建日期：2008-8-18
 */
public class MessageResource {

	/* 读取资源包对象定义 */
	private static ResourceBundle messages = null;
	/* 指定缺省的资源包位置 */
	private final static String DEFAULT_RESOURCE_PATH = "com/wisii/wisedoc/resource/message/MessagesBundle";
	/* 存储资源包的位置 */
	private static String RESOURCE_LOCATION = DEFAULT_RESOURCE_PATH;
	/* 存储语言环境 */
	private static Locale USE_LOCALE = Locale.getDefault();
	/* 定义配置文件中给定的Local信息，是否满足如下样式 */
	private final static String LOCALE_STYLE = "[a-zA-Z]{2}_[a-zA-Z]{2}";

	/* 配置文件中使用的KEY，指定资源包的基路经 */
	public final static String RESOURCE_BASE_PATH = "RESOURCE_BASE_PATH";
	
	static {
		load();
	}

	/**
	 * 
	 * 初始化语言环境信息，资源包位置信息
	 * 
	 */
	private static void load() {
		initResourceLocation();
		initLocale();
		try {
			messages = ResourceBundle.getBundle(RESOURCE_LOCATION, USE_LOCALE);
		} catch (Exception e) {
			messages = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH, USE_LOCALE);
			LogUtil.warn(e.getMessage());
		}
	}

	/**
	 * 
	 * 初始化语言环境信息【从配置文件中获得】
	 * 
	 */
	private static void initLocale() {
		// 从配置文件中获得语言环境信息,默认值Locale.getDefault()虚拟机使用的语言环境。
		String locale = ConfigureUtil.getProperty(ConfigureConstants.LOCALE);
		//LOG
		LogUtil.debug("配置文件中Local:" + locale);
		
		if (isEmpty(locale) || !locale.matches(LOCALE_STYLE)){
			//LOG
			USE_LOCALE = Locale.getDefault();
			LogUtil.warn("配置文件中没有指定Local信息。使用默认的Local = " + USE_LOCALE);
			return;
		}
		String[] zh_CN = locale.split("_");
		Locale tempLocale = new Locale(zh_CN[0], zh_CN[1]);
		Locale[] systemLocales = Locale.getAvailableLocales();
		List list = Arrays.asList(systemLocales);

		if (!list.contains(tempLocale)){
			//LOG
			LogUtil.warn("配置文件中配置的Local" + tempLocale + " 不存在");
			tempLocale = Locale.getDefault();
		}

		USE_LOCALE = tempLocale;
	}

	/**
	 * 
	 * 初始化资源包位置信息，【从配置文件中获得】
	 * 
	 */
	private final static void initResourceLocation() {
		// 从配置文件获得配置的路径信息，同时保存到RESOURCE_LOCATION中
		String basePaht = ConfigureUtil.getProperty(RESOURCE_BASE_PATH);
		//LOG
		LogUtil.debug("配置文件中获得的基路经名:" + basePaht);
		
		if (isEmpty(basePaht)){
			//LOG
			LogUtil.debug("配置文件中没有设置基路径。");
		
			basePaht = DEFAULT_RESOURCE_PATH;
		}
		RESOURCE_LOCATION = basePaht;
	}

	/**
	 * 
	 * 重新设置语言环境
	 * 
	 * @param locale
	 *            指定要设置的语言环境
	 * @return 无
	 */
	public final static void setLocale(Locale locale) {
		// LOG
		LogUtil.debug("Locale:" + locale);
		ConfigureUtil.setProperty(RESOURCE_LOCATION, locale.toString());
		load();
	}

	/**
	 * 重新加载资源包信息
	 */
	public final static void reload() {
		load();
	}

	/**
	 * 根据指定的KEY，从相应的资源包获得值
	 * 
	 * @param key
	 *            指定资源包中一使用的KEY
	 * @return String 返回资源包中指定的KEY对于的值
	 */
	public final static String getMessage(String key) {
		if(isEmpty(key)){
			//LOG
			LogUtil.warn("指定的KEY = " + key + " 不合法");
			return "";
		}
		//LOG
		LogUtil.debug("key:" + key);
		
		return messages.getString(key);
	}
	
	/**
	 * 根据指定的KEY，从相应的资源包获得值
	 * 
	 * @param key
	 *            指定资源包中一使用的KEY
	 * @return String 返回资源包中指定的KEY对于的值
	 */
	public final static String getMessage(String key1, String key2) {
		
		return getMessage(key1 + key2);
	}

	/**
	 * 
	 * 根据指定的KEY、需要传递的参数，从相应的资源包获得值
	 * 
	 * @param key
	 *            指定资源包中一使用的KEY
	 * @param params
	 *            指定资源包中key对应的值所需的参数信息。
	 * @return 根据指定的key和params获得资源包中值信息。
	 */
	public final static String getMessage(String key, String... params) {
		String s = getMessage(key);
		
		//LOG
		LogUtil.debug("params：", params);
		
		return MessageFormat.format(s, params);
	}
	
	/**
	 * 返回当前配置文件中所设定的区域
	 * @return 当前配置文件中所设定的区域
	 */
	public static Locale getLocale() {
		return USE_LOCALE;
	}

	/* 测试用 */
	public static void main(String[] args) {
		
		System.out.println(MessageResource.getLocale());
		
		System.out.println(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER, MessageConstants.FONT_COLOR));
//		System.out.println(MessageResource.getMessage("second"));
//		System.out.println(MessageResource.getMessage("third"));
//		MessageResource.getMessage("first", "0", "1", "2");
//		MessageFormat.format("first", "");
//		System.out.println(Locale.CHINA);
//		new Locale("zh", "cn");
//		String s =.println("boolean = " + s.matches(pa));
	}
}
