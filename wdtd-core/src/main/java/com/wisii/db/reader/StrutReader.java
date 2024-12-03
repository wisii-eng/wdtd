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
 * @StrutReader.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.db.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.wisii.db.ConnectionSeting;
import com.wisii.db.Setting;
import com.wisii.db.exception.DBConnectException;
import com.wisii.db.exception.FieldNotExistExceptin;
import com.wisii.db.exception.NoSQLException;
import com.wisii.db.exception.NotLeafShouldbeOneSQL;
import com.wisii.db.exception.OutOfOrderException;
import com.wisii.db.exception.SqlExecuteException;
import com.wisii.db.model.DeclareItem;
import com.wisii.db.model.SQLItem;
import com.wisii.db.model.SqlItemFactory;
import com.wisii.db.util.DBUtil;
import com.wisii.db.util.DriverTypeUtil;
import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.banding.SqlBindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.view.ui.SQLItemNode;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2010-10-27
 */
public class StrutReader
{
	private static Stack<List<String>> resultstack = new Stack<List<String>>();

	public static DataStructureTreeModel getDataStrutture(SQLItemNode sqlroot,
			String conname) throws DBConnectException, SqlExecuteException,
			NotLeafShouldbeOneSQL, FieldNotExistExceptin, OutOfOrderException,
			NoSQLException
	{
		if (sqlroot == null)
		{
			return null;
		}
		ConnectionSeting conset = DBUtil.getConnectionSeting(conname);
		String driverclass = DriverTypeUtil.getDriverClassName(conset
				.getDriverclassType());
		try
		{
			Class.forName(driverclass);
		} catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
			// LogUtil.debugException(message, e);
		}
		String url = conset.getUrl();
		String username = conset.getUsername();
		String password = conset.getPassword();
		Connection con = null;
		try
		{
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e)
		{
			throw new DBConnectException(e);
		}
		if (con == null)
		{
			throw new DBConnectException("建立连接不成功");
		}
		BindNode root = createTreeNode(null, "root", false);
		resultstack.clear();
		int count = sqlroot.getChildCount();
		for (int i = 0; i < count; i++)
		{
			SQLItemNode child = (SQLItemNode) sqlroot.getChildAt(i);
			CreateNodeForSQLItem(child, con, root);
		}
		resultstack.clear();
		try
		{
			con.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new DataStructureTreeModel(new Setting(sqlroot.getSqlItems(),
				conname), root, false);
	}

	public static void CreateNodeForSQLItem(SQLItemNode sqlitemnode,
			Connection con, BindNode toaddnode) throws NotLeafShouldbeOneSQL,
			SqlExecuteException, FieldNotExistExceptin, OutOfOrderException,
			NoSQLException
	{
		if (sqlitemnode == null)
		{
			return;
		}
		String nodesql = sqlitemnode.getSql();
		if (nodesql == null || nodesql.trim().equals(""))
		{
			throw new NoSQLException(sqlitemnode);
		}
		nodesql = nodesql.trim();
		// TODO 插一句
		int childcount = 0;
		BindNode rownode = null;
		SQLItem sqit = SqlItemFactory.getSqlItem(nodesql);
		if (sqit instanceof DeclareItem)
		{
			// 得到节点
			List a = ((DeclareItem) sqit).getVarName();
			BindNode sqlnode = createTreeNode(toaddnode,
					sqlitemnode.toString(), false);
			rownode = createTreeNode(sqlnode, "row", false);
			((DeclareItem) sqit).createColumnNode(a, rownode);
			resultstack.push(a);
		} else if (sqit instanceof SQLItem)
		{
			// 将SQL语句分开
			String[] sqls = nodesql.split(";");

			// 判断如果有子SQL那么当前SQL就不能是多条的
			childcount = sqlitemnode.getChildCount();
			if (childcount > 0 && sqls.length > 1)
			{
				throw new NotLeafShouldbeOneSQL(sqlitemnode);
			}
			// 创建当前根节点
			BindNode sqlitemnoderoot = createTreeNode(toaddnode, sqlitemnode
					.toString(), false);

			List<String> sqlsnoempty = new ArrayList<String>();
			// 去除掉其中连续两个";"引起的空行
			for (String sql : sqls)
			{
				if (!sql.trim().isEmpty())
				{
					sqlsnoempty.add(sql.trim());
				}
			}
			int size = sqlsnoempty.size();
			for (int i = 0; i < size; i++)
			{
				String sql = sqlsnoempty.get(i);
				BindNode sqlnode;
				// 如果只有一条sql语句，则不用result+序号包装起来
				if (size > 1)
				{
					sqlnode = createSqlNode(sqlitemnoderoot, "result" + i, sql);
				} else
				{
					sqlnode = sqlitemnoderoot;
				}
				try
				{
					// 执行SQL
					PreparedStatement perstate = getPreparedStatement(sql, con,
							sqlitemnode);
					perstate.execute();
					ResultSetMetaData meta = perstate.getMetaData();
					// 建立row级结点
					rownode = createTreeNode(sqlnode, "row", false);
					int columncount = meta.getColumnCount();
					Map<String, Integer> beenusednames = new HashMap<String, Integer>();
					// 记录下每一级的字段名，用来判断子孙SQLItemNode引用的节点名是否存在
					List<String> filedresult = new ArrayList<String>();
					for (int j = 1; j <= columncount; j++)
					{
						createColumnNode(j, meta, rownode, beenusednames,
								filedresult);
					}
					resultstack.push(filedresult);
					perstate.close();

				} catch (SQLException e)
				{
					throw new SqlExecuteException(sql, sqlitemnode, e);
				}

			}
		}

		for (int k = 0; k < childcount; k++)
		{
			CreateNodeForSQLItem((SQLItemNode) sqlitemnode.getChildAt(k), con,
					rownode);
		}
		resultstack.pop();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	public static BindNode createSqlNode(BindNode root, String nodeName,
			String sql)
	{
		BindNode sqlnode = new SqlBindNode(root, BindNode.STRING,
				BindNode.UNLIMT, nodeName, sql);
		root.addChild(sqlnode);
		return sqlnode;
	}

	/*
	 * 
	 * 根据节点名创建节点
	 * 
	 * @param nodeName：节点名 @return MutableTreeNode：返回文档节点 @exception
	 */

	public static BindNode createTreeNode(BindNode parent, String nodeName,
			boolean isatt)
	{
		// if(nodeName.startsWith("@"))
		BindNode node;
		if (isatt)
		{
			node = new AttributeBindNode(parent, BindNode.STRING,
					BindNode.UNLIMT, nodeName);
		} else
		{
			node = new DefaultBindNode(parent, BindNode.STRING,
					BindNode.UNLIMT, nodeName);
		}
		if (parent != null)
		{
			parent.addChild(node);
		}
		return node;
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	public static void createColumnNode(int index, ResultSetMetaData meta,
			BindNode rownode, Map<String, Integer> beenusednames,
			List<String> fileds)
	{
		try
		{
			String name = XMLUtil.getXmlText(meta.getColumnLabel(index))
					.toLowerCase();
			Integer usedcount = beenusednames.get(name);
			if (usedcount != null)
			{
				beenusednames.put(name, ++usedcount);
				name = name + "_" + usedcount;
			} else
			{
				beenusednames.put(name, 1);
			}
			fileds.add(name);
			int sqltype = meta.getColumnType(index);
			int nodetype = -1;
			switch (sqltype)
			{
			case Types.BIGINT:
			case Types.BIT:
			case Types.INTEGER:
			case Types.SMALLINT:
			case Types.TINYINT:
			{
				nodetype = BindNode.INTEGER;
				break;
			}
			case Types.BOOLEAN:
			{
				nodetype = BindNode.BOOLEAN;
				break;
			}
			case Types.DATE:
			{
				nodetype = BindNode.DATE;
				break;
			}
			case Types.TIME:
			case Types.TIMESTAMP:
			{
				nodetype = BindNode.TIME;
				break;
			}
			case Types.DOUBLE:
			case Types.FLOAT:
			case Types.DECIMAL:
			{
				nodetype = BindNode.DECIMAL;
				break;
			}
			}
			int len = meta.getPrecision(index);
			if (len < 0)
			{
				len = BindNode.UNLIMT;
			}
			BindNode columnnode = new AttributeBindNode(rownode, nodetype, len,
					name);
			rownode.addChild(columnnode);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param sqlsetting
	 * @param con
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @throws SQLException
	 * @throws FieldNotExistExceptin
	 * @throws OutOfOrderException
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	public static PreparedStatement getPreparedStatement(String sql,
			Connection con, SQLItemNode sqlitemnode) throws SQLException,
			FieldNotExistExceptin, OutOfOrderException
	{
		StringBuffer sb = new StringBuffer();
		char[] chares = sql.toCharArray();
		int count = chares.length;
		boolean isinparam = false;
		StringBuffer param = null;
		Map<Integer, String> parmtoindex = new HashMap<Integer, String>();
		int parmindex = 1;
		boolean isalldigit = true;
		int index = -1;
		for (int i = 0; i < count; i++)
		{
			char c = chares[i];
			if (c == '#' && !isinparam)
			{
				isinparam = true;
				index = i;
				param = new StringBuffer();
			} else
			{
				if (isinparam)
				{
					if (!Character.isWhitespace(c) && c != '#' && c != '\''&& c != ','&& c != ')')
					{
						if (!Character.isDigit(c))
						{
							isalldigit = false;
						}
						param.append(c);
					} else
					{
						isinparam = false;
						if (!isalldigit)
						{
							if (!isfieldExist(sql, param.toString(),
									sqlitemnode, index))
							{
								throw new FieldNotExistExceptin(sql,
										sqlitemnode, param.toString(), index);
							}
						}
						//带有单引号时，标识参数是字符串类型的
						if (c != '\'')
						{
							parmtoindex.put(parmindex++, param.toString());
							sb.append("?");
						}
						if (c != '#')
						{
							sb.append(c);
						}
						isalldigit = true;
					}
				} else
				{
					sb.append(c);
				}
			}
		}
		if (isinparam)
		{
			parmtoindex.put(parmindex++, param.toString());
			if (!isalldigit)
			{
				if (!isfieldExist(sql, param.toString(), sqlitemnode, index))
				{
					throw new FieldNotExistExceptin(sql, sqlitemnode, param
							.toString(), index);
				}
			}
			sb.append("?");
		}
		PreparedStatement preparedstatement;
		preparedstatement = con.prepareStatement(sb.toString());
		for (int i = 1; i < parmindex; i++)
		{
			preparedstatement.setNull(i, Types.CHAR);
		}
		return preparedstatement;
	}

	
	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @throws OutOfOrderException
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private static boolean isfieldExist(String sql, String field,
			SQLItemNode sqlitemnode, int index) throws OutOfOrderException
	{
		if (!field.startsWith("["))
		{
			return false;
		}
		char[] ces = field.toCharArray();
		int fieldindex = -1;
		// 记录中括号中有多少个".",有多少个"."，则往上回溯对应的层数
		int diancount = 0;
		// resultstack.
		for (int i = 1; i < ces.length; i++)
		{
			// 遇到一个'.',则往上找一级
			if (ces[i] == '.')
			{
				diancount++;
			} else if (ces[i] == ']')
			{
				fieldindex = i;
				break;
			} else
			{
				return false;
			}
		}
		if (diancount == 0 || diancount > resultstack.size())
		{
			throw new OutOfOrderException(field, sql, sqlitemnode, index);
		}
		if (fieldindex < 2 || ces.length - fieldindex < 2)
		{
			return false;
		}

		String fieldstr = field.substring(fieldindex + 2).toLowerCase();
		// 根据"."回溯到对应的字段列表
		List<String> fileds = resultstack.get(resultstack.size() - diancount);
		return fileds.indexOf(fieldstr) != -1;
	}

	public static void main(String[] args)
	{
		// Map<String, Integer> type = new HashMap<String, Integer>();
		// type.put("2", Types.VARCHAR);
		// ConnectionSeting conset = new ConnectionSeting("test",
		// "oracle.jdbc.driver.OracleDriver",
		// "jdbc:oracle:thin:@192.168.0.50:1521:itownet", "zhongmei",
		// "zhongmei");
		// Setting set = new Setting(
		// "select a.user_title,a.user_id,b.id from zm_user a,test b where b.id< #2",
		// type, conset);
		// try
		// {
		// DataStructureTreeModel model = getDataStrutture(set);
		// } catch (DBConnectException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SqlExecuteException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
