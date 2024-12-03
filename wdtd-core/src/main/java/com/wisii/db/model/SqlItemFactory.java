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
package com.wisii.db.model;

/**
 * 生成SQLItem的工厂类
 * @author liuxiao
 *
 */
public class SqlItemFactory
{
	/**
	 * <div>得到相应的SQL <br>
	 * 判断方式：<br>
	 * 如果SQl是select 打头的则为 普通SQL<br>
	 * 如果是call打头则为 存储过程<br>
	 * 如果是declare打头则为SQL代码块<br>
	 * </div>
	 * 
	 * @param sql
	 * @return
	 */
	public static SQLItem getSqlItem(String sql)
	{
		// 去掉头尾空格
		if (sql != null)
		{
			sql = sql.trim();
			if (sql.toLowerCase().startsWith("call"))
			{
				return new CallItem(sql);
			}
			if (sql.toLowerCase().startsWith("declare"))
			{
				return new DeclareItem(sql);
			} else
			{
				return new SQLItem(sql);
			}
		}
		return new SQLItem(sql);

	}
}
