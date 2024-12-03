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

package com.wisii.wisedoc.view.ui.ribbon.pagetask;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class PageSequenceMasterBand
{

	public JRibbonBand getBand()
	{

		JRibbonBand band = new JRibbonBand(RibbonUIText.REGION_BAND,
				MediaResource.getResizableIcon("09379.ico"), null);
		PageSequenceMasterPanel pagesequencemaster = new PageSequenceMasterPanel();
		JRibbonComponent maincom = new JRibbonComponent(pagesequencemaster);
		band.addRibbonComponent(maincom);
		return band;
	}
}
