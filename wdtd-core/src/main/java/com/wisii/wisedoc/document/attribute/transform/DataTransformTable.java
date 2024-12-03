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
package com.wisii.wisedoc.document.attribute.transform;

import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.banding.BindNode;

public class DataTransformTable extends TransformTable {

	public static int SETSAME = 0;

	public static int SETNULL = 1;

	public static int SETSTRING = 2;

	int datatreat = SETSAME;

	String text = "";

	List<List<BindNode>> nodes = new ArrayList<List<BindNode>>();

	public DataTransformTable(List<List<String>> datas) {
		super(datas);
	}

	public DataTransformTable(List<List<String>> datas, int datatreat, String text, List<List<BindNode>> nodes) {
		super(datas);
		if (datatreat >= SETSAME && datatreat <= SETSTRING) {
			this.datatreat = datatreat;
		}
		this.text = text;
		if (nodes != null) {
			this.nodes.addAll(nodes);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text != null) {
			this.text = text;
		} else {
			this.text = "";
		}
	}

	public int getDatatreat() {
		return datatreat;
	}

	public void setDatatreat(int datatreat) {
		this.datatreat = datatreat;
	}

	public List<List<BindNode>> getNodes() {
		return nodes;
	}

	public int isDeleteBlank() {
		int flg = 0;
		if (datas != null) {
			for (List<String> current : datas) {
				for (int i = 0; i < current.size(); i++) {
					String currentitem = current.get(i) != null ? current.get(i) : "";
					if ("@null".equals(currentitem) && (flg == 0 || flg == 10)) {
						flg = flg + 1;
					} else if ("@dnull".equals(currentitem) && (flg == 0 || flg == 1)) {
						flg = flg + 10;
					}
				}
			}
		}
		return flg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + datatreat;
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataTransformTable other = (DataTransformTable) obj;
		if (datatreat != other.datatreat)
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
