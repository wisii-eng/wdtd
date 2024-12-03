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
 * @ExternalGraphicTask.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.externalgraphic;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.view.ui.ribbon.WiseTask;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：插入图片属性设置task
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-23
 */
public class ExternalGraphicTask implements WiseTask
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseTask#getTask()
	 */
	public RibbonTask getTask()
	{
		JRibbonBand setband = new SetBand().getBand();
		JRibbonBand sizeband = new SizeBand().getBand();
		JRibbonBand bordcontentband = new BorderAndContentSetBand().getBand();
		JRibbonBand editband = new EditBand().getBand(); 
		RibbonTask task = new RibbonTask(RibbonUIText.PICTURE_TASK,setband,sizeband,/*clipband,*/bordcontentband,editband);
		return task;
	}
}