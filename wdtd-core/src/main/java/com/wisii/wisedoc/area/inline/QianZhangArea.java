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
 * @QianZhangArea.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.area.inline;

import java.awt.geom.Rectangle2D;

import com.wisii.wisedoc.document.QianZhang;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2011-11-9
 */
public class QianZhangArea extends Image
{
	private QianZhang qianzhang;
	private Rectangle2D viewport = null;
	public QianZhangArea(QianZhang qianzhang)
	{
		super("");
		this.qianzhang = qianzhang;
	}

	public QianZhang getQianZhang()
	{
		return qianzhang;
	}
	public Rectangle2D getViewport()
	{
		if (viewport != null)
		{
			return new Rectangle2D.Double(viewport.getX(), viewport.getY(),
					viewport.getWidth(), viewport.getHeight());
		} else
		{
			return viewport;
		}
	}

	public void setViewport(Rectangle2D viewport) {
		this.viewport = viewport;
	}
}
