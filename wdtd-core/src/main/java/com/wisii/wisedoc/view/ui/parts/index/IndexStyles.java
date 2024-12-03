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
package com.wisii.wisedoc.view.ui.parts.index;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.attribute.AttributeManager;
import com.wisii.wisedoc.document.attribute.Attributes;

/**
 * 目录样式类
 * @author 闫舒寰
 * @version 1.0 2009/04/13
 */
public class IndexStyles {
	
	private String styleName;
	
	private Map<Integer, Object> properties;
	
	public IndexStyles(String name) {
		this.styleName = name;
		this.properties = new HashMap<Integer, Object>();
	}
	
	public IndexStyles(String name, Map<Integer, Object> property) {
		this.styleName = name;
		this.properties = property;
	}
	
	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String indexLevel) {
		this.styleName = indexLevel;
	}

	public Map<Integer, Object> getStyleProperty() {
		return properties;
	}

	public void setStylePropert(Map<Integer, Object> properties) {
		this.properties = properties;
	}

	public void setPropertise(Map<Integer, Object> proprety) {
		this.properties.putAll(proprety);
	}
	
	public Attributes getAttributes() {
		return AttributeManager.getAttributeContainer(Block.class).getAttribute(properties);
	}

	@Override
	public String toString() {
		return "IndexStyles [indexLevel=" + styleName + ", properties="
				+ properties + "]";
	}

}
