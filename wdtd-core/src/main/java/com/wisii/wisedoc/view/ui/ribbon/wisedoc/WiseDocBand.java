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

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_ALL;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_IGNORE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_IGNORE_A;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_IGNORE_B;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_IGNORE_R;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_LINE_FEED;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_NUMBER_SPACE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_ONE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_PRESERVE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_SPACE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_SPACE_TREAT;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WiseDocument;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;

/**
 * 文档属性栏
 * @author 钟亚军
 * @version 1.0 2009/03/23
 */
public class WiseDocBand implements WiseBand
{

	String all = WISE_DOC_ALL;

	String one = WISE_DOC_ONE;

	String ignore = WISE_DOC_IGNORE;

	String preserve = WISE_DOC_PRESERVE;

	String ignoreB = WISE_DOC_IGNORE_B;

	String ignoreA = WISE_DOC_IGNORE_A;

	String ignoreR = WISE_DOC_IGNORE_R;

	String asSpace = WISE_DOC_SPACE;

	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand wisedocumentBand = new JRibbonBand(WISE_DOC_BAND,
				MediaResource.getResizableIcon("09379.ico"), null);

		WiseCombobox numberofspaces = new WiseCombobox();
		numberofspaces.addItem(one);
		numberofspaces.addItem(all);
		RibbonUIManager.getInstance().bind(
				WiseDocument.SET_WHITESPACECOLLAPSE_ACTION, numberofspaces);
		
		JRibbonComponent numberofspacesCom = new JRibbonComponent(MediaResource.getResizableIcon("02285.ico"),
				WISE_DOC_NUMBER_SPACE, numberofspaces);
		wisedocumentBand.addRibbonComponent(numberofspacesCom);

		WiseCombobox spacesTreat = new WiseCombobox();
		spacesTreat.addItem(preserve);
		spacesTreat.addItem(ignore);
		spacesTreat.addItem(ignoreB);
		spacesTreat.addItem(ignoreA);
		spacesTreat.addItem(ignoreR);
		RibbonUIManager.getInstance().bind(
				WiseDocument.SET_WHITESPACETREATMENT_ACTION, spacesTreat);
		JRibbonComponent spacesTreatCom = new JRibbonComponent(MediaResource.getResizableIcon("02285.ico"),
				WISE_DOC_SPACE_TREAT, spacesTreat);
		wisedocumentBand.addRibbonComponent(spacesTreatCom);
		WiseCombobox linefeed = new WiseCombobox();
		linefeed.addItem(preserve);
		linefeed.addItem(ignore);
		linefeed.addItem(asSpace);
		RibbonUIManager.getInstance().bind(
				WiseDocument.SET_LINEFEEDTREATMENT_ACTION, linefeed);
		
		JRibbonComponent lineFeedCom = new JRibbonComponent(MediaResource.getResizableIcon("02285.ico"),
				WISE_DOC_LINE_FEED, linefeed);
		wisedocumentBand.addRibbonComponent(lineFeedCom);
		return wisedocumentBand;
	}

}
