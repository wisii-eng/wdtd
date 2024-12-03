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


/**
 * 
 * @author 李晓光 2009-3-19 11:19:57
 * 
 * @param <T>
 *            指定封装对象的类型。
 * @param <K>
 *            指定封装对象的类型。
 */
public class ConditionItem {
	private LogicalExpression condition = null;
	private Attributes styles = null;

	public ConditionItem() {
	}

	public ConditionItem(LogicalExpression condition, Attributes att){
		this.condition = condition;
		this.styles = att;
	}
	public LogicalExpression getCondition() {
		return condition;
	}

	public void setCondition(LogicalExpression condition) {
		this.condition = condition;
	}

	public Map<Integer, Object> getStyles() {
		return styles.getAttributes();
	}
	public Attributes getAttributes(){
		return this.styles;
	}
	public void setStyles(Attributes styles) {
		this.styles = styles;
	}
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Condition:{");
		s.append(condition.toString());
		s.append("}");
		s.append("Styles:{");
		s.append(getStyles().toString());
		s.append("}");
		return s.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((styles == null) ? 0 : styles.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConditionItem other = (ConditionItem) obj;
		if (condition == null)
		{
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (styles == null)
		{
			if (other.styles != null)
				return false;
		} else if (!styles.equals(other.styles))
			return false;
		return true;
	}
	
}