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
 * @XMLGenerate.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.db.reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.wisii.db.model.DeclareItem;
import com.wisii.db.model.SQLItem;
import com.wisii.db.model.SqlItemFactory;
import com.wisii.wisedoc.io.XMLUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：Nov 24, 2010
 */
public final class XMLGenerate {
	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	public static String generateXMLofItems(List<SQLItem> sqlitems,
			Map<String, String> params, Connection con) {
		StringBuffer out = new StringBuffer();
		outputString("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>", out);
		outputString(generateXMLwithoutrootofItems(sqlitems, params, con), out);
		outputString("</root>", out);
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return out.toString();

	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	public static String generateXMLwithoutrootofItems(List<SQLItem> sqlitems,
			Map<String, String> params, Connection con) {
		if (sqlitems == null || sqlitems.isEmpty() || con == null) {
			return "";
		}
		StringBuffer out = new StringBuffer();
		String indexstr = "";
		int index = 0;
		Stack<Map<String, String>> resultstack = new Stack<Map<String, String>>();
		for (SQLItem item : sqlitems) {
			if (item != null) {
				generateXMLofIetmResult(item, params, out, con, indexstr
						+ index++, resultstack);
			}
		}
		return out.toString();
	}

	private static void outputString(String data, StringBuffer out) {
		if (data != null) {
			out.append(data);
		}
	}

	private static void generateXMLofIetmResult(SQLItem item,
			Map<String, String> params, StringBuffer out, Connection con,
			String indexstr, Stack<Map<String, String>> resultstack) {
		if (item == null) {
			return;
		}

		// 添加语句块的形式
		
		SQLItem sqit = SqlItemFactory.getSqlItem(item.getSql());
		if (sqit instanceof DeclareItem) {
			
			sqit.setSql(XMLGenerate.getParameteSql(item.getSql(), params, resultstack));
			List name=((DeclareItem) sqit).getVarName();
			try {
				((DeclareItem) sqit).callProcedure(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List value=((DeclareItem) sqit).getVarValue();
			
			outputString("<sql" + indexstr + ">", out);
			outputString("<row", out);
			
			Map fie=new HashMap();
			int vsize = value.size();
			for(int i=0;i<name.size();i++)
			{
				String rvalue = "";
				if(i<vsize)
				{
					rvalue = (String)value.get(i);
				}
				fie.put(name.get(i), rvalue);
				outputString(" " + name.get(i)
						+ "=\""
						+ ((String)value.get(i)).trim()
						+ "\"", out);
			}
			
			resultstack.push(fie);
			List<SQLItem> childitems = item.getChildren();
			if (childitems != null && !childitems.isEmpty()) {
				outputString(">", out);
				int index = 0;
				for (SQLItem childitem : childitems) {
					if (item != null) {
						generateXMLofIetmResult(childitem, params,
								out, con, indexstr + "." + index++,
								resultstack);
					}
				}
				outputString("</row>", out);
			} else {
				outputString("/>", out);
			}
			resultstack.pop();
			
			
			
		} else {
			// 分开SQL
			String[] sqls = item.getSql().split(";");
			List<String> sqlsnoempty = new ArrayList<String>();
			// 去除掉其中连续两个";"引起的空行
			for (String sql : sqls) {
				if (sql.trim().length() > 0) {
					sqlsnoempty.add(sql.trim());
				}
			}
			outputString("<sql" + indexstr + ">", out);
			int size = sqlsnoempty.size();
			for (int i = 0; i < size; i++) {
				String sql = sqlsnoempty.get(i);
				// 如果只有一条sql语句，则不用result+序号包装起来
				if (size > 1) {
					outputString("<result" + i + ">", out);
				}
				String nsql = getParameteSql(sql, params, resultstack);
				PreparedStatement statement;
				try {
					statement = con.prepareStatement(nsql);
					ResultSet rs = statement.executeQuery();
					ResultSetMetaData metadata = rs.getMetaData();
					int columncount = metadata.getColumnCount();
					List<String> columnlabels = new ArrayList<String>();
					Map<String, Integer> beenusednames = new HashMap<String, Integer>();
					for (int j = 1; j <= columncount; j++) {
						String name = XMLUtil.getXmlText(
								metadata.getColumnLabel(j)).toLowerCase();
						Integer usedcount = beenusednames.get(name);
						if (usedcount != null) {
							beenusednames.put(name, ++usedcount);
							name = name + "_" + usedcount;
						} else {
							beenusednames.put(name, 1);
						}
						columnlabels.add(name);
					}
					while (rs.next()) {
						Map<String, String> fields = new HashMap<String, String>();
						outputString("<row", out);
						for (int k = 1; k <= columncount; k++) {
							String value = rs.getString(k);
							int type = metadata.getColumnType(k);
							if (value != null) {
								// 是日期，且包含"."(标识含有)
								int indexdian = value.indexOf('.');
								if (type == Types.DATE && indexdian != -1) {
									value = value.substring(0, indexdian);
								}
								outputString(" " + columnlabels.get(k - 1)
										+ "=\""
										+ XMLUtil.getXmlText(value.trim())
										+ "\"", out);
							}
							fields.put(columnlabels.get(k - 1), value);
						}
						resultstack.push(fields);
						List<SQLItem> childitems = item.getChildren();
						if (childitems != null && !childitems.isEmpty()) {
							outputString(">", out);
							int index = 0;
							for (SQLItem childitem : childitems) {
								if (item != null) {
									generateXMLofIetmResult(childitem, params,
											out, con, indexstr + "." + index++,
											resultstack);
								}
							}
							outputString("</row>", out);
						} else {
							outputString("/>", out);
						}
						resultstack.pop();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// 如果只有一条sql语句，则不用result+序号包装起来
				if (size > 1) {
					outputString("</result" + i + ">", out);
				}

			}
		
		}
		outputString("</sql" + indexstr + ">", out);
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	private static String getParameteSql(String sql,
			Map<String, String> params, Stack<Map<String, String>> resultstack) {

		StringBuffer sb = new StringBuffer();
		char[] chares = sql.toCharArray();
		int count = chares.length;
		boolean isinparam = false;
		StringBuffer param = null;
		boolean isalldigit = true;
		for (int i = 0; i < count; i++) {
			char c = chares[i];
			if (c == '#' && !isinparam) {
				isinparam = true;
				param = new StringBuffer();
			} else {
				if (isinparam) {
					if (!Character.isWhitespace(c) && c != '#' && c != '\''
							&& c != ',' && c != ')') {
						if (!Character.isDigit(c)) {
							isalldigit = false;
						}
						param.append(c);
					} else {
						isinparam = false;
						if (!isalldigit) {
							sb.append(getParaValue(param.toString(),
									resultstack));
						} else {
							sb.append(getparmString(params
									.get(param.toString())));
						}
						if (c != '#') {
							sb.append(c);
						}
						isalldigit = true;
					}
				} else {
					sb.append(c);
				}
			}
		}
		if (isinparam) {
			if (!isalldigit) {
				sb.append(getParaValue(param.toString(), resultstack));
			} else {
				sb.append(getparmString(params.get(param.toString())));
			}
		}
		return sb.toString();
	}

	private static String getParaValue(String field,
			Stack<Map<String, String>> resultstack) {
		char[] ces = field.toCharArray();
		int fieldindex = -1;
		// 记录中括号中有多少个".",有多少个"."，则往上回溯对应的层数
		int diancount = 0;
		// resultstack.
		for (int i = 1; i < ces.length; i++) {
			// 遇到一个'.',则往上找一级
			if (ces[i] == '.') {
				diancount++;
			} else if (ces[i] == ']') {
				fieldindex = i;
				break;
			} else {
				return "";
			}
		}
		if (diancount == 0 || diancount > resultstack.size()) {
			return "";
		}
		if (fieldindex < 2 || ces.length - fieldindex < 2) {
			return "";
		}

		String fieldstr = field.substring(fieldindex + 2).toLowerCase();
		// 根据"."回溯到对应的字段列表
		Map<String, String> fileds = resultstack.get(resultstack.size()
				- diancount);
		String resultvalue = "null";
		String fieldresultitemvalue = fileds.get(fieldstr);
		if (fieldresultitemvalue != null) {
			resultvalue = fieldresultitemvalue;
		}
		return resultvalue;
	}

	private static String getparmString(String parm) {
		if (parm == null) {
			return "''";
		} else {
			return parm;
		}
	}
}
