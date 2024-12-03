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
 * 
 */
package com.wisii.wisedoc.document.attribute.transform;

import com.wisii.wisedoc.document.Constants;

/**
 * @author wisii
 * 
 */
public class SwingDS implements DataSource {
	// callbackclass与dataname二选一
	private String callbackclass;
	private String dataname;
	// 数据源类型，树形或表型
	int type = Constants.EN_TABLE1;
	private String columns;

	public SwingDS(String callbackclass, int type, String columns) {
		this.callbackclass = callbackclass;
		if (type == Constants.EN_TABLE1 || type == Constants.EN_TREE) {
			this.type = type;
		}
		this.columns=columns;
	}

	public SwingDS(int type, String dataname, String columns) {
		this.dataname = dataname;
		if (type == Constants.EN_TABLE1 || type == Constants.EN_TREE) {
			this.type = type;
		}
		this.columns=columns;
	}

	public boolean iscallback() {
		return callbackclass != null;
	}

	public String getCallbackclass() {
		return callbackclass;
	}

	public String getDataname() {
		return dataname;
	}

	public int getType() {
		return type;
	}
    
	public String getColumns() {
		return columns;
	}

	public int getColumncount() {
		return columns.split(",").length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((callbackclass == null) ? 0 : callbackclass.hashCode());
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result
				+ ((dataname == null) ? 0 : dataname.hashCode());
		result = prime * result + type;
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
		SwingDS other = (SwingDS) obj;
		if (callbackclass == null) {
			if (other.callbackclass != null)
				return false;
		} else if (!callbackclass.equals(other.callbackclass))
			return false;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (dataname == null) {
			if (other.dataname != null)
				return false;
		} else if (!dataname.equals(other.dataname))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SwingDS [callbackclass=" + callbackclass + ", dataname="
				+ dataname + ", type=" + type + ", columns=" + columns + "]";
	}
    

}
