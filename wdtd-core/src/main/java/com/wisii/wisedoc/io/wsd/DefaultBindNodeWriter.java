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
 * @DefaultBindNodeWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.banding.SchemaRefNode;
import com.wisii.wisedoc.banding.SqlBindNode;
import com.wisii.wisedoc.io.XMLUtil;

/**
 * 类功能描述：DefaultBindNode 的writer类
 * 
 * 作者：zhangqiang 创建日期：2008-10-14
 */
public class DefaultBindNodeWriter extends AbstractBindNodeWriter
{
	private final String NAME = "name";
	private final String TYPE = "type";
	private final String LENGTH = "length";
	private final String ISATTNODE = "isattnode";
	private final String SQL = "sql";
	private final String REF = "ref";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.BindNodeWriter#write(com.wisii.wisedoc.banding
	 * .BindNode, int)
	 */
	public String writebegin(BindNode node, int index)
	{
		String s = "";
		if (node != null && node instanceof DefaultBindNode)
		{
			s = s + TAGBEGIN + NODE + " " + ID + "=\"" + index + "\"" + " ";
			DefaultBindNode dbnode = (DefaultBindNode) node;
			boolean isatt = node instanceof AttributeBindNode;
			s = s + ISATTNODE + "=\"" + isatt + "\"" + " ";
			String name = dbnode.getNodeName();
			if (name != null)
			{
				s = s + NAME + "=\"" + name + "\"" + " ";
			}
			int type = dbnode.getDataType();
			s = s + TYPE + "=\"" + type + "\"" + " ";
			if(dbnode instanceof SqlBindNode)
			{
				SqlBindNode sqlnode = (SqlBindNode)dbnode;
				String sql = sqlnode.getSql();
				s = s + SQL + "=\"" + XMLUtil.getXmlText(sql) + "\"" + " ";
			}
			if(dbnode instanceof SchemaRefNode)
			{
				s = s + REF + "=\"" + DocumentWirter.getDataNodeID(((SchemaRefNode)dbnode).getRefnode()) + "\"" + " ";
			}
			int length = dbnode.getLength();
			s = s + LENGTH + "=\"" + length + "\"" + TAGEND + LINEBREAK;

		}
		return s;
	}

}
