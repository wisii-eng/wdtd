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
 * @SelectInfo.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.transform;

import java.util.List;

/**
 * 
 * @author xieli 2016年10月8日下午5:15:24
 */
public final class PopupBrowserInfo {


	private final DataSource datasource;

	private final List<PopupBrowserColumnInfo> columninfos;

	public PopupBrowserInfo(DataSource datasource, List<PopupBrowserColumnInfo> columninfos) {
		super();
		this.datasource = datasource;
		this.columninfos = columninfos;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public List<PopupBrowserColumnInfo> getColumninfos() {
		return columninfos;
	}

	

}
