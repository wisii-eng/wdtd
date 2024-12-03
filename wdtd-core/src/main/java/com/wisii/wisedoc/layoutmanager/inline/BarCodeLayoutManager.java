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
 * @BarCodeLayoutManager.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.layoutmanager.inline;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Trait;
import com.wisii.wisedoc.area.inline.BarCodeArea;
import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.Constants;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-10-24
 */
public class BarCodeLayoutManager extends AbstractGraphicsLayoutManager
{
	private BarCode _barcode;
	public BarCodeLayoutManager(BarCode node)
	{
		super(node);
		_barcode = node;
	}

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.layoutmanager.inline.AbstractGraphicsLayoutManager#getChildArea()
	 */
	Area getChildArea()
	{
		/*return new BarCodeArea(_barcode);*///【删除】 by 李晓光2009-1-20
		/* 【添加：START】 by 李晓光 2009-1-20 */
		BarCodeArea area = new BarCodeArea(_barcode);
		area.addTrait(Trait.IMAGE_LAYER, _barcode.getAttribute(Constants.PR_GRAPHIC_LAYER));
		return area;
		/* 【添加：END】 by 李晓光 2009-1-20 */
	}

}
