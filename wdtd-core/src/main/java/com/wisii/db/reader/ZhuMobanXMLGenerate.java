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
 * @XMLGenerate.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.db.reader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import com.wisii.db.model.Setting;

/**
 * 类功能描述：执行sql语句生成xml数据的实现类 作者：zhangqiang 创建日期：2010-10-29
 */
public class ZhuMobanXMLGenerate {
	public static String generateXML(List<Setting> sqlsettings,
			Map<String, String> params, Connection con) {
		if (sqlsettings == null || sqlsettings.isEmpty()) {
			return null;
		}
		StringBuffer out = new StringBuffer();
		generateXML(sqlsettings, params, out, con);
		return out.toString();
	}

	private static void generateXML(List<Setting> sqlsettings,
			Map<String, String> params, StringBuffer out, Connection con) {
		if (sqlsettings == null || sqlsettings.isEmpty() || out == null) {
			return;
		}
		outputString("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>", out);
		Stack<Map<String, String>> resultstack = new Stack<Map<String, String>>();
		Setting mainsetting = null;
		List<Setting> subsettings = new ArrayList<Setting>();
		for (Setting sqlsetting : sqlsettings) {
			if (sqlsetting == null) {
				continue;
			}
			if (sqlsetting.isIsmainsql()) {
				mainsetting = sqlsetting;
			} else {
				subsettings.add(sqlsetting);
			}
		}
		if (mainsetting != null) {
			generateXMLofIetm(mainsetting, params, out, con, resultstack);
		}
		if (!subsettings.isEmpty()) {
			outputString("<subs>", out);
			for (Setting subsetting : subsettings) {
				generateXMLofIetm(subsetting, params, out, con, resultstack);
			}
			outputString("</subs>", out);
		}
		// 没有数据时，生成一个唯一节点的内容，否则预览会有问题
		if (mainsetting == null && subsettings.isEmpty()) {
			outputString("<nodata/>", out);
		}
		outputString("</root>", out);
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void outputString(String data, StringBuffer out) {
		if (data != null) {
			out.append(data);
		}
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private static void generateXMLofIetm(Setting sqlsetting,
			Map<String, String> params, StringBuffer out, Connection con,
			Stack<Map<String, String>> resultstack) {
		outputString("<" + sqlsetting.getName() + ">", out);
		outputString(XMLGenerate.generateXMLwithoutrootofItems(sqlsetting
				.getSqlitem(), params, con), out);
		outputString("</" + sqlsetting.getName() + ">", out);

	}

}
