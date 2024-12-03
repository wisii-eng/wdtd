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
package com.wisii.wisedoc.document.attribute.edit;

import com.wisii.wisedoc.view.dialog.ButtonNoDataNode;

public class Button {

	public static String ADDBEFORE = "addbefore";

	public static String ADDAFTER = "addafter";

	public static String DELETE = "delete";

	String type;
	ButtonNoDataNode dataContent;
	String auty = "";

	public Button(String btype) {
		type = btype;
	}

	public ButtonNoDataNode getDataContent() {
		return dataContent;
	}

	public void setDataContent(ButtonNoDataNode dataContent) {
		this.dataContent = dataContent;
	}

	public Button(String btype, String auty) {
		type = btype;
		if (auty != null) {
			this.auty = auty;
		}
	}

	public Button(String btype, String auty, ButtonNoDataNode bnode) {
		type = btype;
		if (auty != null) {
			this.auty = auty;
		}
		this.dataContent = bnode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuty() {
		return auty;
	}

	public void setAuty(String auty) {
		this.auty = auty;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return type + ":" + auty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auty == null) ? 0 : auty.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Button other = (Button) obj;
		if (auty == null) {
			if (other.auty != null)
				return false;
		} else if (!auty.equals(other.auty))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
