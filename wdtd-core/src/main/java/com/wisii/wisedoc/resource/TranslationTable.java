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
 * @TranslationTable.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.resource;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：根据指定的地址，区域信息，读取相应的资源包，并把资源包中信息保存在Map中
 * 
 * 作者：李晓光 创建日期：2008-8-18
 */
public abstract class TranslationTable<K, V> {
	public TranslationTable() {
	}

	public TranslationTable(String url) {
		this(url, USE_LOCALE);
	}

	public TranslationTable(String url, Locale locale) {
		this.url = url;
		this.locale = locale;
		load(url, locale);
	}

	/**
	 * 获得转换表的原始数据，及保持原有的Key+Value的关系，封装到Map中。
	 * 
	 * @return {@link Map} 返回读取数据的Map
	 */
	public Map<K, V> getPrimalMap() {
		return PRIMAL_MAP;
	}

	/**
	 * 获得转换表的原始数据，并调整原始数据的关系为Value + Key形式。封装到Map中
	 * 
	 * @return {@link Map} 返回读取数据的Map
	 */
	public Map<V, K> getReverseMap() {
		return REVERSE_MAP;
	}

	public MapBean<K, V> getMapBean() {
		MapBean<K, V> bean = new MapBean<K, V>();
		bean.PRIMAL_MAP = PRIMAL_MAP;
		bean.REVERSE_MAP = REVERSE_MAP;
		return bean;
	}

	/**
	 * 根据系统的环境【Locale信息】，读取指定地址的转换表中数据，并把数据封装在MapBean中。
	 * 
	 * @param url
	 *            指定数据位置。
	 * @return {@link MapBean}	返回封装了双向转换表的MapBean 
	 */
	public MapBean<K, V> getMapBean(String url) {
		return getMapBean(url, USE_LOCALE);
	}

	public MapBean<K, V> getMapBean(String url, Locale locale) {
		load(url, locale);
		MapBean<K, V> bean = new MapBean<K, V>();
		bean.PRIMAL_MAP = PRIMAL_MAP;
		bean.REVERSE_MAP = REVERSE_MAP;
		return bean;
	}

	public Map<K, V> getPrimalMap(String url) {
		return getPrimalMap(url, USE_LOCALE);
	}

	public Map<K, V> getPrimalMap(String url, Locale locale) {
		load(url, locale);
		return PRIMAL_MAP;
	}

	public Map<V, K> getReverseMap(String url) {
		return getReverseMap(url, USE_LOCALE);
	}

	public Map<V, K> getReverseMap(String url, Locale locale) {
		load(url, locale);
		return REVERSE_MAP;
	}

	/* 存储语言环境 */
	private void load(String url, Locale locale) {
		messages = ResourceBundle.getBundle(url, locale);
		if (messages == null)
			return;
		initionalMap();
	}

	private void initionalMap() {
		PRIMAL_MAP.clear();
		REVERSE_MAP.clear();
		if (messages == null)
			return;
		Enumeration<String> enums = messages.getKeys();
		String key = "", value = "";
		while (enums.hasMoreElements()) {
			key = (String) enums.nextElement();
			value = messages.getString(key);

			if (value == null) {
				LogUtil.debug("key = " + key + "  value = " + value);
				continue;
			}
			K k = convert2K(key);
			V v = convert2V(value);
			PRIMAL_MAP.put(k, v);
			REVERSE_MAP.put(v, k);
		}
	}

	public Locale getLocale() {
		if (isNull(locale))
			return USE_LOCALE;
		return this.locale;
	}

	public String getURL() {
		if (isNull(url))
			return "";
		return this.url;
	}

	/* key, value 组成的Map */
	private Map<K, V> PRIMAL_MAP = new HashMap<K, V>();
	/* value, key 组成的Map */
	private Map<V, K> REVERSE_MAP = new HashMap<V, K>();
	/* 读取资源包对象定义 */
	private ResourceBundle messages = null;
	private final static Locale USE_LOCALE = Locale.getDefault();
	private String url = "";
	private Locale locale = USE_LOCALE;

	public abstract K convert2K(String s);

	public abstract V convert2V(String s);

	static class MapBean<K, V> {
		private Map<K, V> PRIMAL_MAP = null;
		private Map<V, K> REVERSE_MAP = null;
		public Map<K, V> getPrimalMap(){
			return PRIMAL_MAP;
		}
		public Map<V, K> getReverseMap(){
			return REVERSE_MAP;
		}
	}
	/**
	 * 获得一个Key、Value均是String类型的转换表。
	 * @return {@link TranslationTable}	返回一个空的转换表。	
	 */
	public static TranslationTable<String, String> getTranslationTable(){
		return new TranslationTable<String, String>(){
			@Override
			public String convert2K(String s) {
				return process(s);
			}

			@Override
			public String convert2V(String s) {
				return process(s);
			}
			private String process(String s){
				if(isNull(s))
					return "";
				return s.trim();
			}
		};
	}
	public static TranslationTable<String, Double> getFontTranslationTable(){
		return new TranslationTable<String, Double>(){
			@Override
			public String convert2K(String s) {
				return process(s);
			}

			@Override
			public Double convert2V(String s) {
				return convert2Double(s);
			}
			private String process(String s){
				if(isNull(s))
					return "";
				return s.trim();
			}
			private Double convert2Double(String s){
				try {
					return new Double(s.trim());
				} catch (Exception e) {
					LogUtil.debug(e.getMessage());
					return null;
				}
			}
		};
	}
}
