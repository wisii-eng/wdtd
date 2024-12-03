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
 * @InsertEllipseAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import com.wisii.wisedoc.view.mousehandler.InsertManager;

/**
 * 类功能描述：插入椭圆事件类
 *
 * 作者：zhangqiang
 * 创建日期：2009-2-27
 */
public class InsertEllipseAction extends AbstractInsertAction
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.action.AbstractInsertAction#getType()
	 */
	protected int getType()
	{
		return InsertManager.ELLIPSE;
	}

}