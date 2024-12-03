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
 * Column.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.transform;

import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.EnumPropertyWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;

/**
 * 
 * 类功能描述： 列信息 作者：zhongyajun 创建日期：2009-8-29
 */
public final class Column
{

	public static enum ColumnType
	{
		Integer(1), Double(2), Float(3), Varchar(4), Date(5), Time(6), Timestamp(
				7), Datetime(8), Numeric(9), Boolean(10), Tinyint(11), Smallint(
				12), Bigint(13), Real(14), Binary(15), Varbinary(16), Longvarbinary(
				17);

		private int type = -1;

		private ColumnType(int type)
		{
			this.type = type;
		}

		public int getType()
		{
			return this.type;
		}
	}

	private String name;

	private ColumnType type;

	private String attrname;

	public Column(String n, ColumnType tp, String attrname)
	{
		name = n;
		type = tp;
		this.attrname = attrname;
	}

	@Override
	public String toString()
	{
		String code = "";

		if (name != null && type != null)
		{
			code = code + ElementWriter.TAGBEGIN;
			code = code + EnumPropertyWriter.WDWEMSNS;
			code = code + "column";
			code = code + " ";
			code = code + ElementUtil.outputAttributes("name", name);
			code = code
					+ ElementUtil.outputAttributes("type", type.toString()
							.toUpperCase());
			code = code + ElementWriter.NOCHILDTAGEND;
		}
		return code;
	}

	/**
	 * @返回 name变量的值
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * @返回 type变量的值
	 */
	public final ColumnType getType()
	{
		return type;
	}

	/**
	 * @返回 attrname变量的值
	 */
	public final String getAttrname()
	{
		return attrname;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attrname == null) ? 0 : attrname.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Column other = (Column) obj;
		if (attrname == null)
		{
			if (other.attrname != null)
				return false;
		} else if (!attrname.equals(other.attrname))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null)
		{
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public static ColumnType getType(String value)
	{
		ColumnType type = null;
		if ("INTEGER".equals(value))
		{
			type = ColumnType.Integer;
		} else if ("DOUBLE".equals(value))
		{
			type = ColumnType.Double;
		} else if ("FLOAT".equals(value))
		{
			type = ColumnType.Float;
		} else if ("VARCHAR".equals(value))
		{
			type = ColumnType.Varchar;
		} else if ("DATE".equals(value))
		{
			type = ColumnType.Date;
		} else if ("TIME".equals(value))
		{
			type = ColumnType.Time;
		} else if ("TIMESTAMP".equals(value))
		{
			type = ColumnType.Timestamp;
		} else if ("DATETIME".equals(value))
		{
			type = ColumnType.Datetime;
		} else if ("NUMERIC".equals(value))
		{
			type = ColumnType.Numeric;
		} else if ("BOOLEAN".equals(value))
		{
			type = ColumnType.Boolean;
		} else if ("TINYINT".equals(value))
		{
			type = ColumnType.Tinyint;
		} else if ("SMALLINT".equals(value))
		{
			type = ColumnType.Smallint;
		} else if ("BIGINT".equals(value))
		{
			type = ColumnType.Bigint;
		} else if ("REAL".equals(value))
		{
			type = ColumnType.Real;
		} else if ("BINARY".equals(value))
		{
			type = ColumnType.Binary;
		} else if ("VARBINARY".equals(value))
		{
			type = ColumnType.Varbinary;
		} else if ("LONGVARBINARY".equals(value))
		{
			type = ColumnType.Longvarbinary;
		}
		return type;
	}

	public Column clone()
	{
		Column newcolumn = new Column(name, type, attrname);
		return newcolumn;
	}
}
