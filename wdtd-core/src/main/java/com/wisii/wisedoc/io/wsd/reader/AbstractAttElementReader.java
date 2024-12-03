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
 * @AtttibuteElementReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import com.wisii.wisedoc.io.wsd.WSDAttributeIOFactory;

/**
 * 类功能描述：含有属性项的元素Reader
 * 
 * 作者：zhangqiang 创建日期：2008-10-10
 */
public abstract class AbstractAttElementReader extends AbstractHandler {
	private WSDAttributeIOFactory attrifactory = new WSDAttributeIOFactory();
	protected final String ID = "id";

	protected com.wisii.wisedoc.document.attribute.Attributes createattributes(
			Attributes atts) {
		com.wisii.wisedoc.document.attribute.Attributes attributes = null;
		int attslen = atts.getLength();
		if (attslen > 0) {
			Map<Integer, Object> values = new HashMap<Integer, Object>();
			List<String> attnames = new ArrayList<String>();
			for (int i = 0; i < attslen; i++) {
				String attname = atts.getQName(i);
				if (!attname.equals(ID)) {
					attnames.add(attname);
				}
			}
			Collections.sort(attnames);
			int size = attnames.size();
			List<String> compoundattnames = new ArrayList<String>();
			String oldname = null;
			for (int i = 0; i < size; i++) {
				String attname = attnames.get(i);
				int index = attname.indexOf('.');
				if (index < 0||(index >=0&&!attname.substring(0, index).equals(oldname))) {
					if (!compoundattnames.isEmpty()) {
						String fcompounname = compoundattnames.get(0);
						String name = fcompounname.substring(0, fcompounname
								.indexOf('.'));
						int key = attrifactory.getAttributeKeyNameFactory()
								.getKey(name);
						if (key > -1) {
							int casize = compoundattnames.size();
							Map<String, String> svalues = new HashMap<String, String>();
							for (int j = 0; j < casize; j++) {
								String compoundattname = compoundattnames
										.get(j);
								String svalue = atts.getValue(compoundattname);
								svalues.put(compoundattname
										.substring(compoundattname
												.indexOf('.') + 1),
												svalue);
							}

							Object value = generatecompoundvalue(key, svalues);
							if (value != null) {
								values.put(key, value);
							}
						}
						oldname = null;
						compoundattnames.clear();
					}
					if(index<0){
						oldname = null;
					String svalue = atts.getValue(attname);
					int key = attrifactory.getAttributeKeyNameFactory().getKey(
							attname);
					if (key > -1) {
						Object value = generatevalue(key, svalue);
						if (value != null) {
							values.put(key, value);
						}
					}
					}
					else
					{
						oldname=  attname.substring(0,index);
						compoundattnames.add(attname);
					}
				} else {
					oldname=  attname.substring(0,index);
					compoundattnames.add(attname);
				}
			}
			if (!compoundattnames.isEmpty()) {
				String fcompounname = compoundattnames.get(0);
				String name = fcompounname.substring(0, fcompounname
						.indexOf('.'));
				int key = attrifactory.getAttributeKeyNameFactory()
						.getKey(name);
				if (key > -1) {
					int casize = compoundattnames.size();
					Map<String, String> svalues = new HashMap<String, String>();
					for (int j = 0; j < casize; j++) {
						String compoundattname = compoundattnames
								.get(j);
						String svalue = atts.getValue(compoundattname);
						svalues.put(compoundattname
								.substring(compoundattname
										.indexOf('.') + 1),
										svalue);
					}

					Object value = generatecompoundvalue(key, svalues);
					if (value != null) {
						values.put(key, value);
					}
				}
				oldname = null;
				compoundattnames.clear();
			}
			if (!values.isEmpty()) {
				attributes = new com.wisii.wisedoc.document.attribute.Attributes(
						values);
			}
		}
		return attributes;
	}

	protected Object generatecompoundvalue(int key, Map<String, String> values) {
		return attrifactory.getAttributeReader(key).read(key, values);
	}

	protected Object generatevalue(int key, String value) {
		return attrifactory.getAttributeReader(key).read(key, value);
	}
	void ininHandler(AbstractElementsHandler handler)
	{
		super.ininHandler(handler);
		attrifactory.initWsdHander(handler);
	}
}
