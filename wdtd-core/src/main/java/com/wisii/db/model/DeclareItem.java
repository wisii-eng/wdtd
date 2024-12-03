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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;

/**
 * 类功能描述： 存储子模板名和对应的sql语句 如果有嵌套sql时，需保证该sql只是一条sql语句， 即不是被；分割的多条sql语句
 * 作者：zhangqiang 创建日期：2010-10-29
 */
public class DeclareItem extends SQLItem {

	private List varName = new ArrayList();
	private List varValue = new ArrayList();
	private CallableStatement enable_stmt;
	private CallableStatement disable_stmt;
	private CallableStatement show_stmt;

	public DeclareItem(String sql) {
		super(sql);
		//将所有的回车都变成空格
		// 解析SQL得到变量名
		String[] sa = sql.split("dbms_output\\.put_line\\(");
		for (int i = 1; i < sa.length; i++) {
			String ff = sa[i];
			ff = ff.substring(0, ff.indexOf(")"));
			varName.add(ff);
		}
		// System.out.println(varName);
	}

	/*
	 * our constructor simply prepares the three statements we plan on
	 * executing.
	 * 
	 * the statement we prepare for SHOW is a block of code to return a String
	 * of dbms_output output. Normally, you might bind to a PLSQL table type but
	 * the jdbc drivers don't support PLSQL table types -- hence we get the
	 * output and concatenate it into a string. We will retrieve at least one
	 * line of output -- so we may exceed your MAXBYTES parameter below. If you
	 * set MAXBYTES to 10 and the first line is 100 bytes long, you will get the
	 * 100 bytes. MAXBYTES will stop us from getting yet another line but it
	 * will not chunk up a line.
	 */
	private void DbmsOutput(Connection conn) throws SQLException {
		enable_stmt = conn.prepareCall("begin dbms_output.enable(:1); end;");
		disable_stmt = conn.prepareCall("begin dbms_output.disable; end;");

		show_stmt = conn
				.prepareCall("declare "
						+ "    l_line varchar2(255); "
						+ "    l_done number; "
						+ "    l_buffer long; "
						+ "begin "
						+ "  loop "
						+ "    exit when length(l_buffer)+255 > :maxbytes OR l_done = 1; "
						+ "    dbms_output.get_line( l_line, l_done ); "
						+ "    l_buffer := l_buffer || l_line || chr(10); "
						+ "  end loop; " + " :done := l_done; "
						+ " :buffer := l_buffer; " + "end;");
	}

	/**
	 * @return the varName
	 */
	public List getVarName() {
		return varName;
	}

	/**
	 * @return the varValue
	 */
	public List getVarValue() {
		return varValue;
	}

	/*
	 * enable simply sets your size and executes the dbms_output.enable call
	 */
	private void enable(int size) throws SQLException {
		enable_stmt.setInt(1, size);
		enable_stmt.executeUpdate();
	}

	/*
	 * disable only has to execute the dbms_output.disable call
	 */
	private void disable() throws SQLException {
		disable_stmt.executeUpdate();
	}

	/*
	 * show does most of the work. It loops over all of the dbms_output data,
	 * fetching it in this case 32,000 bytes at a time (give or take 255 bytes).
	 * It will print this output on stdout by default (just reset what
	 * System.out is to change or redirect this output).
	 */

	private void show() throws SQLException {
		int done = 0;

		show_stmt.registerOutParameter(2, java.sql.Types.INTEGER);
		show_stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
        int index=0;
		for (;;) {
			show_stmt.setInt(1, 32000);
			show_stmt.executeUpdate();
			varValue.addAll(getLine(show_stmt.getString(3)));
			if ((done = show_stmt.getInt(2)) == 1)
				break;
		}

	}
    public List<String> getLine(String s)
    {
    	if(s==null)
    	{
    		return null;
    	}
    	char[] cs = s.toCharArray();
    	StringBuffer sb = new StringBuffer();
    	List<String> values = new ArrayList();
    	for(char c:cs)
    	{
    		if(c=='\n')
    		{
    			if(sb.length()!=0)
    			{
    				values.add(sb.toString());
    			}
    			else{
    			values.add("");
    			}
    			sb = new StringBuffer();
    		}
    		else
    		{
    			sb.append(c);
    		}
    	}
    	return values;
    }
	/*
	 * close closes the callable statements associated with the DbmsOutput
	 * class. Call this if you allocate a DbmsOutput statement on the stack and
	 * it is going to go out of scope -- just as you would with any callable
	 * statement, result set and so on.
	 */
	private void close() throws SQLException {
		enable_stmt.close();
		disable_stmt.close();
		show_stmt.close();
	}

	public DeclareItem callProcedure(Connection conn) throws SQLException {

		conn.setAutoCommit(false);

		Statement stmt = conn.createStatement();

		DbmsOutput(conn);

		enable(1000000);
		//将所有的回车都变成空格
		String executedsql = sql.replace("\n", " ");
		executedsql = executedsql.replace("\r", " ");
		stmt.execute(executedsql);
		stmt.close();

		show();

		close();
		//conn.close();

		return this;
	}

	public static void main(String[] args) {
		try {
			String sql = "declare"
					+ " var_clm_no varchar2(50);"
					+ " begin "
					+ " select user_id  into var_clm_no from zm_user  where user_id='TYU';"
					+ " dbms_output.put_line(var_clm_no);  " + "end;";
			DeclareItem sd = new DeclareItem(sql);
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.0.50:1521:itownet", "zhongmei",
					"zhongmei");
			sd.callProcedure(conn);
			System.out.println(sd.getVarValue());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createColumnNode(List cloNam, BindNode rownode) {
		if (varName != null && varName.size() > 0) {
			for (int i = 0; i < varName.size(); i++) {
				BindNode columnnode = new AttributeBindNode(rownode,
						BindNode.STRING, -1, (String) varName.get(i));
				rownode.addChild(columnnode);
			}
		}
	}

}
