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

package com.wisii.wisedoc.nio;

public class WSDIOUtil
{

	public static enum WSDFileKind
	{
		/* IMAGE(WSDFileKind.IMAGE_NAME), ICON(WSDFileKind.IOCN_NAME), */
		WSD(WSDFileKind.WSD_NAME), PROPERTY(WSDFileKind.PROPERTY_NAME), DATA(
				WSDFileKind.DATA_NAME), INFORMATION(
				WSDFileKind.INFORMATION_NAME), CONF(WSDFileKind.CONF_NAME);

		/**
		 * 获得当前类型的文件名
		 * 
		 * @return {@link String} 返回该类型文件的文件名
		 */
		public String getName()
		{
			return this.name;
		}

		/**
		 * 获得图像类型
		 * 
		 * @return {@link String} 返回图像类型
		 */
		public String getImageType()
		{
			return imageType;
		}

		private WSDFileKind(String name)
		{
			this.name = name;
		}

		/* 定义wsd文件放置的位置、文件名称 */
		private final static String WSD_NAME = "document/document.xml";

		/* 定义缩略图片的放置位置、文件名称。 */
		private final static String IMAGE_NAME = "image/";

		/* 定义属性配置文件的放置位置、文件名称。 */
		private final static String PROPERTY_NAME = "property/property.xml";

		/* 定义数据文件的放置位置、文件名称。 */
		private final static String DATA_NAME = "data/data.xml";

		/* 定义文档信息文件的放置位置、文件名称。 */
		private final static String INFORMATION_NAME = "information/information.xml";

		/* 定义其它参数配置文件的放置位置、文件名称。 */
		private final static String CONF_NAME = "conf/conf.xml";

		private final static String SUFFIX = "JPG";

		private final static String IOCN_NAME = "icon/icon." + SUFFIX;

		//
		// /* 定义图片类型 */
		private String imageType = SUFFIX;

		/* 定义文件名称 */
		private String name = "";
	}
}
