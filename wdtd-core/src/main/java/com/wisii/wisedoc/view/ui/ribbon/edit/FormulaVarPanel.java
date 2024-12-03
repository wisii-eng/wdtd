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
package com.wisii.wisedoc.view.ui.ribbon.edit;

import javax.swing.JList;
import javax.swing.JPanel;
/**
 * 变量面板
 * @author xieli
 * 2016年10月26日上午10:36:49
 */
public class FormulaVarPanel extends JPanel {

	JList list ;
	public FormulaVarPanel(Object[] listdata) {
		super();
		list = new JList(listdata);
		add(list);
	}
}


