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
 * @SQLItem.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.db.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 类功能描述： 存储子模板名和对应的sql语句 如果有嵌套sql时，需保证该sql只是一条sql语句， 即不是被；分割的多条sql语句
 * 作者：zhangqiang 创建日期：2010-10-29 改类新增加的一些功能包括 执行SQL的方法和解析得到参数的方法
 * 
 * @param <proctect>
 */
public class SQLItem {
	protected String sql;
	protected List<SQLItem> children;

	public SQLItem(String sql) {
		this.sql = sql;
	}

	public SQLItem setChildren(List<SQLItem> children) {

		this.children = children;
		return this;
	}

	/**
	 * @返回 sql变量的值
	 */
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql=sql;
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
		result = prime * result + ((sql == null) ? 0 : sql.hashCode());
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
		SQLItem other = (SQLItem) obj;
		if (sql == null) {
			if (other.sql != null)
				return false;
		} else if (!sql.equals(other.sql))
			return false;
		return true;
	}

	/**
	 * @返回 children变量的值
	 */
	public List<SQLItem> getChildren() {
		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.sql;
	}

	/**
	 * 该方法未实现
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public SQLItem callProcedure(Connection conn) throws SQLException {

		return this;
	}

}
