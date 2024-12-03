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
package com.wisii.wisedoc.view.ui.actions.dialog;
import java.util.List;

import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;

/**
 * 这个类并非真正的Action类，这个类是用来做一些属性生成和转换的，这个为
 * 
 * @author	闫舒寰
 * @version 0.1 2008/10/20
 */
public class BorderDialogAction {
	
	List<FixedLengthSpinner> widthValue;	
	List<WiseCombobox> style;	
	List<ColorComboBox> color;
	
	public BorderDialogAction(BorderPanel borderPanel) {
		this.widthValue = borderPanel.getWidthValue();
		this.style = borderPanel.getStyle();
		this.color = borderPanel.getColor();	
	}

	
	
	

}
