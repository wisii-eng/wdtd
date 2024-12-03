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
 * @TranslationTableResource.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.resource;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.wisii.wisedoc.configure.ConfigureConstants;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：用读取，保存转换表
 * 
 * 作者：李晓光 创建日期：2008-8-18
 */
public class TranslationTableResource {	
	public static String searchKey(Double value){
		/*value = processString(value);*/
		return REVERSE_MAP.get(value);
	}
	public static Double searchValue(String key){
		key = processString(key);
		return PRIMAL_MAP.get(key);
	}
	public static Map<String, Double> getPrimalMap(){
		return PRIMAL_MAP;
	}
	public static Map<Double, String> getReverseMap(){
		return REVERSE_MAP;
	}
	private static String processString(String s){
		if(isEmpty(s))
			return "";
		return s.trim();
	}
	private static void load(){
		initLocale();
		
		/*TranslationTable.MapBean<String, String> bean = TranslationTable.getTranslationTable().getMapBean(DEFAULT_RESOURCE_PATH, USE_LOCALE);*/
		TranslationTable.MapBean<String, Double> bean = TranslationTable.getFontTranslationTable().getMapBean(DEFAULT_RESOURCE_PATH, USE_LOCALE);;
		PRIMAL_MAP = bean.getPrimalMap();
		REVERSE_MAP = bean.getReverseMap();
	}

	/**
	 * 
	 * 初始化语言环境信息【从配置文件中获得】
	 * 
	 */
	private static void initLocale() {
		// 从配置文件中获得语言环境信息,默认值Locale.getDefault()虚拟机使用的语言环境。
		String locale = ConfigureUtil.getProperty(ConfigureConstants.LOCALE);
		LogUtil.debug("配置文件中Local:" + locale);
		
		if (isEmpty(locale) || !locale.matches(LOCALE_STYLE)){
			USE_LOCALE = Locale.getDefault();
			LogUtil.warn("配置文件中没有指定Local信息。使用默认的Local = " + USE_LOCALE);
			return;
		}
		String[] zh_CN = locale.split("_");
		Locale tempLocale = new Locale(zh_CN[0], zh_CN[1]);
		Locale[] systemLocales = Locale.getAvailableLocales();
		List<Locale> list = Arrays.asList(systemLocales);

		if (!list.contains(tempLocale)){
			//LOG
			LogUtil.warn("配置文件中配置的Local" + tempLocale + " 不存在");
			tempLocale = Locale.getDefault();
		}

		USE_LOCALE = tempLocale;
	}

	/* 指定缺省的资源包位置 */
	private final static String DEFAULT_RESOURCE_PATH = "com/wisii/wisedoc/resource/message/TranslationTableMessagesBundle";
	/* 存储语言环境 */
	private static Locale USE_LOCALE = Locale.getDefault();
	/* 定义配置文件中给定的Local信息，是否满足如下样式 */
	private final static String LOCALE_STYLE = "[a-zA-Z]{2}_[a-zA-Z]{2}";	
	/* 配置文件中使用的KEY，指定资源包的基路经 */
	private static Map<String, Double> PRIMAL_MAP = new HashMap<String, Double>();
	private static Map<Double, String> REVERSE_MAP = new HashMap<Double, String>();
	static{
		load();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] keys = {"大特号", "特号", "初号  ", "小初号", "大一号", "一号", "二号", "小二号", "三号", "四号", "小四号", "五号", "小五号", "六号", "小六号", "七号", "八号"};
		for (String s : keys) {
			System.out.print(s + " = ");
			System.out.println(TranslationTableResource.searchValue(s));
		}
		System.out.println("----------------------------------------------------");
		String[] values = {"63","54","42","36","31.5","28","21","18","16","14","12","10.5","9","8","6.875","5.25","4.5"};
		for (String s : values) {
			System.out.print(TranslationTableResource.searchKey(new Double(s)));
			System.out.println(" = " + s);
		}
		Map<Double, String> map = new HashMap<Double, String>();
		map.put(2.36, "OK");
		System.out.println(map.get(2.36));
		System.out.println(map.get(2.360));
		System.out.println(map.get(new Double(2.36)));
	}	
}
