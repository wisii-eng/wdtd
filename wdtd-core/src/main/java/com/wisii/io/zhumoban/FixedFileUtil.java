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

package com.wisii.io.zhumoban;

public class FixedFileUtil
{

	public static enum WSDXKind
	{
		WSD(WSDXKind.WSD_NAME), XSLTEMPLATE(WSDXKind.XSL_NAME), DATASOURCE(
				WSDXKind.DATASOURCE_NAME), FCTEMPLATE(WSDXKind.FC_NAME), SQL(
				WSDXKind.SQL_NAME),NAMESPACE(WSDXKind.NAMESPACE_NAME);

		/**
		 * 获得当前类型的文件名
		 * 
		 * @return {@link String} 返回该类型文件的文件名
		 */
		public String getName()
		{
			return this.name;
		}

		private WSDXKind(String name)
		{
			this.name = name;
		}

		/* 定义wsd文件放置的位置、文件名称 */
		private final static String WSD_NAME = "WSD/document.xml";

		/* 定义XSL模板文件的放置位置、文件名称。 */
		private final static String XSL_NAME = "XSL/xsl.xml";

		/* 定义数据转换文件的放置位置、文件名称。 */
		private final static String DATASOURCE_NAME = "XSL/datasource.xml";

		/* 定义功能模板文件的放置位置、文件名称。 */
		private final static String FC_NAME = "XSL/fctemplate.xml";

		/* 定义功能模板文件的放置位置、文件名称。 */
		private final static String SQL_NAME = "SQL/sql.xml";
		private final static String NAMESPACE_NAME = "xsl/namespace.xml";

		/* 定义文件名称 */
		private String name = "";
	}

	public static enum WSDMKind
	{
		WSD(WSDMKind.WSD_NAME), XSLTEMPLATE(WSDMKind.XSL_NAME), DATASOURCE(
				WSDMKind.DATASOURCE_NAME), FCTEMPLATE(WSDMKind.FC_NAME), SQL(
				WSDMKind.SQL_NAME), SUB(WSDMKind.SUBTEMP_NAME),NAMESPACE(WSDMKind.NAMESPACE_NAME);

		/**
		 * 获得当前类型的文件名
		 * 
		 * @return {@link String} 返回该类型文件的文件名
		 */
		public String getName()
		{
			return this.name;
		}

		private WSDMKind(String name)
		{
			this.name = name;
		}

		/* 定义wsd文件放置的位置、文件名称 */
		private final static String WSD_NAME = "WSD/document.xml";

		/* 定义XSL模板文件的放置位置、文件名称。 */
		private final static String XSL_NAME = "XSL/xsl.xml";

		/* 定义数据转换文件的放置位置、文件名称。 */
		private final static String DATASOURCE_NAME = "XSL/datasource.xml";

		/* 定义功能模板文件的放置位置、文件名称。 */
		private final static String FC_NAME = "XSL/fctemplate.xml";

		/* 定义功能模板文件的放置位置、文件名称。 */
		private final static String SQL_NAME = "SQL/sql.xml";

		/* 定义功能模板文件的放置位置、文件名称。 */
		private final static String SUBTEMP_NAME = "SUB/sub.xml";
		private final static String NAMESPACE_NAME = "xsl/namespace.xml";

		/* 定义文件名称 */
		private String name = "";
	}

}
