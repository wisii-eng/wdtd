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
 * @InsertManager.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.mousehandler;

/**
 * 类功能描述：插入菜单选择状态类
 * 
 * 作者：zhangqiang 创建日期：2008-10-30
 */
public class InsertManager
{

	private static int type = -1;

	// blcokcontainer
	public static int BLOCKCONTAINER = 1;

	//	
	public static int RELATIVEBLOCKCONTAINER = 2;

	// 直线
	public static int LINE = 3;

	// 矩形
	public static int RECTANGLE = 4;

	// 椭圆
	public static int ELLIPSE = 5;

	// 画布
	public static int CANVAS = 6;

	// 类型为空
	public static int NOTYPE = -1;

	public static void setType(int type)
	{
		InsertManager.type = type;
	}

	public static int getType()
	{
		return type;
	}

	public static void clearType()
	{
		type = NOTYPE;
	}

}
