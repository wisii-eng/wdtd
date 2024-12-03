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
package com.wisii.wisedoc.view.ui.ribbon.wisedoc;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_CANNOT_EDIT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_CAN_EDIT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_EDIT_BAND;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WiseDocument;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;

/**
 * 编辑属性面板
 * @author 钟亚军
 * @version 1.0 2009/03/23
 */
public class EditBand implements WiseBand
{

	String canedit = WISE_DOC_CAN_EDIT;

	String noedit = WISE_DOC_CANNOT_EDIT;

	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand groupBand = new JRibbonBand(WISE_DOC_EDIT_BAND, MediaResource.getResizableIcon("09379.ico"), null);

		JPanel mainpanel = new JPanel();
		mainpanel.setPreferredSize(new Dimension(110, 60));
		WiseCombobox edit = new WiseCombobox();
		edit.addItem(noedit);
		edit.addItem(canedit);
		RibbonUIManager.getInstance().bind(WiseDocument.EDITABLE, edit);

		mainpanel.add(edit);
//		groupBand.addPanel(mainpanel);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent mainPanelWrapper = new JRibbonComponent(mainpanel);
		groupBand.addRibbonComponent(mainPanelWrapper);

		return groupBand;
	}

	public String getItem(EnumProperty xmledit)
	{
		String result = noedit;
		if (xmledit != null)
		{
			int value = xmledit.getEnum();
			if (value == Constants.EN_EDITABLE)
			{
				result = canedit;
			} 
		}
		return result;
	}
}
