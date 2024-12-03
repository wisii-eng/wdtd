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
 * @VirtualBindNode.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.dialog;

import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;

/**
 * 类功能描述： 作者：wisii 创建日期：2015-1-31
 */
public class ButtonNoDataNode extends DefaultBindNode{
	private List contents;// 内容

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public ButtonNoDataNode(BindNode parent,List contents) {
		super(parent, BindNode.STRING, -1, "");
		this.contents = contents;
	}

	public final List getContents() {
		return contents;
	}

	public void set(String nodeName, BindNode repeatenode, String starttext, String endtext, String intervaltext, List contents) {
		this.contents = contents;
	}

	public ButtonNoDataNode Clone() {
		List newcontens = new ArrayList();
		newcontens.addAll(contents);
		ButtonNoDataNode node = new ButtonNoDataNode(null,newcontens);
		return node;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ButtonNoDataNode other = (ButtonNoDataNode) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		return true;
	}

}
