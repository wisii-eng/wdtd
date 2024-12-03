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
package com.wisii.wisedoc.document.attribute;

import java.util.Map;

import com.wisii.wisedoc.document.Block;

/**
 * 段落样式类
 * @author 闫舒寰
 * @version 1.0 2009/03/30
 */
public class ParagraphStyles {
	
	//样式名称，这个是唯一的
	private String styleName;
	
	public String getStyleName() {
		return styleName;
	}


	public Map<Integer, Object> getStyleProperty() {
		return styleProperty.getAttributes();
	}
	public Object getAttribute(final int key)
	{
		if(styleProperty==null)
		{
			return null;
		}
		return styleProperty.getAttribute(key);
	}
	public Attributes getAttributes(){
		return this.styleProperty;
	}
	//样式属性
	private Attributes styleProperty = null;
	
	public ParagraphStyles(String name, Map<Integer, Object> proMap) {
		this.styleName = name;
		if(proMap == null) {
			this.styleProperty = null;
		} else{
			AttributeContainer cont = AttributeManager.getAttributeContainer(Block.class);
			this.styleProperty = cont.getAttribute(proMap);;
		}
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((styleName == null) ? 0 : styleName.hashCode());
		result = prime * result
				+ ((styleProperty == null) ? 0 : styleProperty.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ParagraphStyles other = (ParagraphStyles) obj;
		if (styleName == null) {
			if (other.styleName != null) {
				return false;
			}
		} else if (!styleName.equals(other.styleName)) {
			return false;
		}
		if (styleProperty == null) {
			if (other.styleProperty != null) {
				return false;
			}
		} else if (!styleProperty.equals(other.styleProperty)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return " name:" + styleName + " | properties:" + getStyleProperty();
	}

}
