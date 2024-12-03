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
 * @StyleBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.wordarttext;

import java.util.Map;

import javax.swing.JRadioButton;
import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButtonPanel;
import org.jvnet.flamingo.common.JCommandToggleWisiiButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WordArtText;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：设置艺术字样式面板
 * 
 * 作者：zhangqiang 创建日期：2009-9-15
 */
public class StyleBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand styleband = new JRibbonBand(
				RibbonUIText.RIBBON_WORDARTTEXT_STYLE, MediaResource
						.getResizableIcon("09379.ico"), null);
		JCommandButton stylebutton = new JCommandButton(
				RibbonUIText.RIBBON_WORDARTTEXT_STYLE_PATH, MediaResource
						.getResizableIcon("02285.ico"));
		stylebutton.setPopupCallback(new TextPopup());
		stylebutton.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		styleband.startGroup();
		styleband.addCommandButton(stylebutton, RibbonElementPriority.MEDIUM);
		styleband.startGroup();
		WiseSpinner rotationspinner = new WiseSpinner(new SpinnerNumberModel(0,
				-180, 180, 45));
		JRibbonComponent rotationWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"),
				RibbonUIText.RIBBON_WORDARTTEXT_STYLE_ROTATION, rotationspinner);
		styleband.addRibbonComponent(rotationWrapper);
		WiseSpinner startspinner = new WiseSpinner(new SpinnerNumberModel(0, 0,
				100, 10));
		JRibbonComponent startWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"),
				RibbonUIText.RIBBON_WORDARTTEXT_STYLE_START, startspinner);
		styleband.addRibbonComponent(startWrapper);

		JRadioButton isshowpathrb = new JRadioButton();
		JRibbonComponent isshowpathWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"),
				RibbonUIText.RIBBON_WORDARTTEXT_STYLE_PATHVISABLE, isshowpathrb);
		styleband.addRibbonComponent(isshowpathWrapper);
		RibbonUIManager.getInstance().bind(WordArtText.SET_ROTATION_ACTION,
				rotationspinner);
		RibbonUIManager.getInstance().bind(WordArtText.SET_STARTPOSITION_ACTION,
				startspinner);
		RibbonUIManager.getInstance().bind(WordArtText.SET_PATHVISABLE_ACTION,
				isshowpathrb);
		return styleband;
	}

	private class TextPopup implements PopupPanelCallback
	{
		TextType typepanel = new TextType();
		JPopupPanel typemenu = new JCommandPopupMenu(typepanel, 6, 6);

		@Override
		public JPopupPanel getPopupPanel(final JCommandButton commandButton)
		{
			Map<Integer, Object> temp = RibbonUIModel
					.getReadCompletePropertiesByType().get(
							ActionType.WordArtText);
			EnumProperty type = null;
			if (temp != null)
			{
				type = (EnumProperty) temp.get(Constants.PR_WORDARTTEXT_TYPE);
			}
			typepanel.initProperty(type);
			return typemenu;
		}

	}

	private class TextType extends JCommandButtonPanel
	{
		final JCommandToggleWisiiButton line = new JCommandToggleWisiiButton(
				"", MediaResource.getResizableIcon("zhixian.ico"),
				MediaResource.getImage("zhixian.gif"));

		final JCommandToggleWisiiButton zhexian2 = new JCommandToggleWisiiButton(
				"", MediaResource.getResizableIcon("2zhexian.ico"),
				MediaResource.getImage("2zhexian.gif"));

		final JCommandToggleWisiiButton zhexian3 = new JCommandToggleWisiiButton(
				"", MediaResource.getResizableIcon("3zhexian.ico"),
				MediaResource.getImage("3zhexian.gif"));
		final JCommandToggleWisiiButton topcircle = new JCommandToggleWisiiButton(
				"", MediaResource.getResizableIcon("shangbantuoyuan.ico"),
				MediaResource.getImage("shangbantuoyuan.gif"));
		final JCommandToggleWisiiButton bottomcircle = new JCommandToggleWisiiButton(
				"", MediaResource.getResizableIcon("xiabantuoyuan.ico"),
				MediaResource.getImage("xiabantuoyuan.gif"));
		final JCommandToggleWisiiButton shuncircle = new JCommandToggleWisiiButton(
				"", MediaResource.getResizableIcon("shuntuoyuan.ico"),
				MediaResource.getImage("shuntuoyuan.gif"));
		final JCommandToggleWisiiButton nicircle = new JCommandToggleWisiiButton(
				"", MediaResource.getResizableIcon("nituoyuan.ico"),
				MediaResource.getImage("nituoyuan.gif"));
		final JCommandToggleWisiiButton beisaier2 = new JCommandToggleWisiiButton(
				"", MediaResource.getResizableIcon("2cibeisaierquxian.ico"),
				MediaResource.getImage("2cibeisaierquxian.gif"));
		final JCommandToggleWisiiButton beisaier3 = new JCommandToggleWisiiButton(
				"", MediaResource.getResizableIcon("3cibeisaierquxian.ico"),
				MediaResource.getImage("3cibeisaierquxian.gif"));

		public TextType()
		{
			super(20);
			this.addButtonGroup(RibbonUIText.INSERT_SVG_TEXT);
			RibbonUIManager.getInstance().bind(WordArtText.SET_PATHTYPE_ACTION,
					line, zhexian2, zhexian3, topcircle, bottomcircle,
					shuncircle, nicircle, beisaier2, beisaier3);
			this.addButtonToLastGroup(line);
			this.addButtonToLastGroup(zhexian2);
			this.addButtonToLastGroup(zhexian3);
			this.addButtonToLastGroup(topcircle);
			this.addButtonToLastGroup(bottomcircle);
			this.addButtonToLastGroup(shuncircle);
			this.addButtonToLastGroup(nicircle);
			this.addButtonToLastGroup(beisaier2);
			this.addButtonToLastGroup(beisaier3);
			this.setSingleSelectionMode(true);
		}

		public void initProperty(EnumProperty type)
		{
			if (type == null)
			{
				line.getActionModel().setSelected(true);
				zhexian2.getActionModel().setSelected(false);
				zhexian3.getActionModel().setSelected(false);
				topcircle.getActionModel().setSelected(false);
				bottomcircle.getActionModel().setSelected(false);
				shuncircle.getActionModel().setSelected(false);
				nicircle.getActionModel().setSelected(false);
				beisaier2.getActionModel().setSelected(false);
				beisaier3.getActionModel().setSelected(false);
			} else
			{
				int typeenum = type.getEnum();
				if (typeenum == Constants.EN_WORDARTTEXT_TYPE_BEZIERCURVES2)
				{
					line.getActionModel().setSelected(false);
					zhexian2.getActionModel().setSelected(false);
					zhexian3.getActionModel().setSelected(false);
					topcircle.getActionModel().setSelected(false);
					bottomcircle.getActionModel().setSelected(false);
					shuncircle.getActionModel().setSelected(false);
					nicircle.getActionModel().setSelected(false);
					beisaier2.getActionModel().setSelected(true);
					beisaier3.getActionModel().setSelected(false);
				} else if (typeenum == Constants.EN_WORDARTTEXT_TYPE_BEZIERCURVES3)
				{
					line.getActionModel().setSelected(false);
					zhexian2.getActionModel().setSelected(false);
					zhexian3.getActionModel().setSelected(false);
					topcircle.getActionModel().setSelected(false);
					bottomcircle.getActionModel().setSelected(false);
					shuncircle.getActionModel().setSelected(false);
					nicircle.getActionModel().setSelected(false);
					beisaier2.getActionModel().setSelected(false);
					beisaier3.getActionModel().setSelected(true);
				} else if (typeenum == Constants.EN_WORDARTTEXT_TYPE_ELLIPSE)
				{
					line.getActionModel().setSelected(false);
					zhexian2.getActionModel().setSelected(false);
					zhexian3.getActionModel().setSelected(false);
					topcircle.getActionModel().setSelected(false);
					bottomcircle.getActionModel().setSelected(false);
					shuncircle.getActionModel().setSelected(true);
					nicircle.getActionModel().setSelected(false);
					beisaier2.getActionModel().setSelected(false);
					beisaier3.getActionModel().setSelected(false);
				} else if (typeenum == Constants.EN_WORDARTTEXT_TYPE_ELLIPSEDOWN)
				{
					line.getActionModel().setSelected(false);
					zhexian2.getActionModel().setSelected(false);
					zhexian3.getActionModel().setSelected(false);
					topcircle.getActionModel().setSelected(false);
					bottomcircle.getActionModel().setSelected(true);
					shuncircle.getActionModel().setSelected(false);
					nicircle.getActionModel().setSelected(false);
					beisaier2.getActionModel().setSelected(false);
					beisaier3.getActionModel().setSelected(false);
				} else if (typeenum == Constants.EN_WORDARTTEXT_TYPE_ELLIPSEINNER)
				{
					line.getActionModel().setSelected(false);
					zhexian2.getActionModel().setSelected(false);
					zhexian3.getActionModel().setSelected(false);
					topcircle.getActionModel().setSelected(false);
					bottomcircle.getActionModel().setSelected(false);
					shuncircle.getActionModel().setSelected(false);
					nicircle.getActionModel().setSelected(true);
					beisaier2.getActionModel().setSelected(false);
					beisaier3.getActionModel().setSelected(false);
				} else if (typeenum == Constants.EN_WORDARTTEXT_TYPE_ELLIPSEUP)
				{
					line.getActionModel().setSelected(false);
					zhexian2.getActionModel().setSelected(false);
					zhexian3.getActionModel().setSelected(false);
					topcircle.getActionModel().setSelected(true);
					bottomcircle.getActionModel().setSelected(false);
					shuncircle.getActionModel().setSelected(false);
					nicircle.getActionModel().setSelected(false);
					beisaier2.getActionModel().setSelected(false);
					beisaier3.getActionModel().setSelected(false);
				} else if (typeenum == Constants.EN_WORDARTTEXT_TYPE_ZHEXIAN2)
				{
					line.getActionModel().setSelected(false);
					zhexian2.getActionModel().setSelected(true);
					zhexian3.getActionModel().setSelected(false);
					topcircle.getActionModel().setSelected(false);
					bottomcircle.getActionModel().setSelected(false);
					shuncircle.getActionModel().setSelected(false);
					nicircle.getActionModel().setSelected(false);
					beisaier2.getActionModel().setSelected(false);
					beisaier3.getActionModel().setSelected(false);
				} else if (typeenum == Constants.EN_WORDARTTEXT_TYPE_ZHEXIAN3)
				{
					line.getActionModel().setSelected(false);
					zhexian2.getActionModel().setSelected(false);
					zhexian3.getActionModel().setSelected(true);
					topcircle.getActionModel().setSelected(false);
					bottomcircle.getActionModel().setSelected(false);
					shuncircle.getActionModel().setSelected(false);
					nicircle.getActionModel().setSelected(false);
					beisaier2.getActionModel().setSelected(false);
					beisaier3.getActionModel().setSelected(false);
				} else
				{

					line.getActionModel().setSelected(true);
					zhexian2.getActionModel().setSelected(false);
					zhexian3.getActionModel().setSelected(false);
					topcircle.getActionModel().setSelected(false);
					bottomcircle.getActionModel().setSelected(false);
					shuncircle.getActionModel().setSelected(false);
					nicircle.getActionModel().setSelected(false);
					beisaier2.getActionModel().setSelected(false);
					beisaier3.getActionModel().setSelected(false);

				}
			}
		}
	}
}
