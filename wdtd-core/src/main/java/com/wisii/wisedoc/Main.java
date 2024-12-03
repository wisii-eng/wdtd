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
 * @Main.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc;

import javax.swing.SwingUtilities;

import com.wisii.wisedoc.view.WisedocMainFrame;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-9-17
 */
public class Main
{

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */

	public static void main(final String[] args)
	{
		// 以Swing线程运行主界面
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				final WisedocMainFrame frame = new WisedocMainFrame();
				frame.showFrame();
				// 为主面板添加功能栏监听器
				RibbonUpdateManager.Instance.setCurrentMainPanel(frame
						.getEidtComponent());
			}
		});
	}
}
