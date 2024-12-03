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
 * @DatasReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import com.wisii.db.ConnectionSeting;
import com.wisii.db.Setting;
import com.wisii.db.model.SQLItem;
import com.wisii.db.model.SqlItemFactory;
import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.banding.SchemaRefNode;
import com.wisii.wisedoc.banding.SqlBindNode;
import com.wisii.wisedoc.banding.io.NameSpace;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-14
 */
public class DatasReader extends AbstractHandler
{
	protected final String NAME = "node";
	// 属性map
	private Map<String, BindNode> _nodesmap = new HashMap<String, BindNode>();
	private Stack<BindNode> nodes = new Stack<BindNode>();
	private final String NAMEATT = "name";
	private final String TYPE = "type";
	private final String LENGTH = "length";
	private final String ISATTNODE = "isattnode";
	private final String ID = "id";
	private final String SQL = "sql";
	private final String REF = "ref";
	private BindNode _rootnode;
	private final String DBSET = "dbset";
	private final String SQLITEM = "sqlitem";
	// 数据库链接设置信息
	private Setting dbsetting;
	private ConnectionSeting con;
	private String dbname;
	private Stack<SQLItem> sqlitemstack;
	private List<SQLItem> sqlitems;
	private final String NAMESPACE = "namespace";
	private List<NameSpace> namespaces;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#startElement(java.lang
	 * .String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (NAME.equals(qname))
		{
			String names = atts.getValue(NAMEATT);
			String types = atts.getValue(TYPE);
			String lengths = atts.getValue(LENGTH);
			String isattnodes = atts.getValue(ISATTNODE);
			String id = atts.getValue(ID);
			if (names != null && id != null)
			{
				int type = BindNode.STRING;
				if (types != null)
				{
					try
					{
						int t = Integer.parseInt(types);
						type = t;
					} catch (NumberFormatException e)
					{
						throw new SAXException(NAME + "节点的" + TYPE + "属性值("
								+ types + ")不合法", e);
					}
				}
				int length = BindNode.UNLIMT;
				if (lengths != null)
				{
					try
					{
						int l = Integer.parseInt(lengths);
						length = l;
					} catch (NumberFormatException e)
					{
						throw new SAXException(NAME + "节点的" + LENGTH + "属性值("
								+ lengths + ")不合法", e);
					}
				}
				boolean isatt = false;
				if (isattnodes != null)
				{

					boolean b = Boolean.parseBoolean(isattnodes);
					isatt = b;
				}
				BindNode parent = null;
				if (!nodes.isEmpty())
				{
					parent = nodes.peek();
				}
				BindNode node;
				if (isatt)
				{
					node = new AttributeBindNode(parent, type, length, names);
				} else
				{
					String sql = atts.getValue(SQL);
					if (sql != null && !sql.isEmpty())
					{
						node = new SqlBindNode(parent, type, length, names, sql);
					} else
					{
						String ref = atts.getValue(REF);
						if (ref != null)
						{
							DefaultBindNode refnode=(DefaultBindNode) _nodesmap.get(ref);
							node =new SchemaRefNode(parent, type, length,
									names, refnode);

						} else
						{
							node = new DefaultBindNode(parent, type, length,
									names);
						}
					}
				}
				if (parent == null)
				{
					_rootnode = node;
				} else
				{
					parent.addChild(node);
				}
				_nodesmap.put(id, node);
				nodes.push(node);
			} else
			{
				throw new SAXException(NAME + "节点必须有" + NAMEATT + "属性");
			}
		} else if (DBSET.equals(qname))
		{
			dbname = atts.getValue("dbname");
		} else if (SQLITEM.equals(qname))
		{
			String sql = atts.getValue("sql");
			if (sqlitemstack == null)
			{
				sqlitemstack = new Stack<SQLItem>();
			}
			sqlitemstack.push(SqlItemFactory.getSqlItem(sql));
		}
		else if(NAMESPACE.equals(qname))
		{
			String key=atts.getValue("key");
			String value=atts.getValue("value");
			if(key!=null&&!key.isEmpty()&&value!=null&&!value.isEmpty())
			{
				if(namespaces==null)
				{
					namespaces=new ArrayList<NameSpace>();
				}
				namespaces.add(new NameSpace(key, value));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#endElement(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (NAME.equals(qname))
		{
			nodes.pop();
		} else if (DBSET.equals(qname))
		{
			if (dbname != null && sqlitems != null)
			{
				dbsetting = new Setting(sqlitems, dbname);
			}
		} else if (SQLITEM.equals(qname))
		{
			SQLItem item = sqlitemstack.pop();
			if (sqlitemstack.isEmpty())
			{
				if(sqlitems==null)
				{
					sqlitems = new ArrayList<SQLItem>();
				}
				sqlitems.add(item);
			} else
			{
				SQLItem parentitem = sqlitemstack.pop();
				List<SQLItem> childitems = parentitem.getChildren();
				if (childitems == null)
				{
					childitems = new ArrayList<SQLItem>();

				} else
				{
					childitems = new ArrayList<SQLItem>(childitems);
				}
				childitems.add(item);
				SQLItem newparentitem = SqlItemFactory.getSqlItem(parentitem.getSql());
				newparentitem.setChildren(childitems);
				sqlitemstack.push(newparentitem);

			}
		}

	}

	public BindNode getRootNode()
	{
		return _rootnode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public Map<String, BindNode> getObject()
	{
		return _nodesmap;
	}
    
	public List<NameSpace> getNamespaces() {
		return namespaces;
	}

	/**
	 * @返回 dbsetting变量的值
	 */
	public Setting getDbsetting()
	{
		return dbsetting;
	}

}
