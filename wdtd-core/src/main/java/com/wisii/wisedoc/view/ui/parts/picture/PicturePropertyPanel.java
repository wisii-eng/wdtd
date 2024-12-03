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
package com.wisii.wisedoc.view.ui.parts.picture;

import static com.wisii.wisedoc.document.ExternalGraphic.FUNCTIONIMAGE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PICTURE_FIX_ASPECT_RATIO;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PICTURE_LAYER;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PICTURE_SIZE_BAND_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PICTURE_SIZE_HEIGHT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PICTURE_SIZE_TYPE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PICTURE_SIZE_TYPE_LIST;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PICTURE_SIZE_WIDTH;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PICTURE_SOURCE_TYPE_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.PICTURE_TRANSPARENCY;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ExternalGraphic;
import com.wisii.wisedoc.view.ui.parts.AbstractWisePropertyPanel;
import com.wisii.wisedoc.view.ui.parts.common.BackGroundPanel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 图片属性设置面板
 * @author 闫舒寰
 * @version 1.0 2009/03/20
 */
public class PicturePropertyPanel extends AbstractWisePropertyPanel {
	
	JTabbedPane tablepane;
	BorderPanel borderPanel;
	BackGroundPanel backgroundpanel;
	
	PropertyPanel propertyPanel;
	
	ActionType propertyType = ActionType.GRAPH;
	
	private static Map<Integer, Object> newMap;

	public static Map<Integer, Object> getNewMap() {
		return newMap;
	}

	@Override
	protected void initialPanel() {
		setPreferredSize(new Dimension(512, 400));
		newMap = new HashMap<Integer, Object>();
		
		setLayout(new BorderLayout());
		
		tablepane = new JTabbedPane();
		
		propertyPanel = new PropertyPanel();
		tablepane.add(RibbonUIText.PICTURE_TASK, propertyPanel);

		borderPanel = new BorderPanel(getInitialProMap(), propertyType);
		tablepane.add(UiText.BORDER, borderPanel);
		
		backgroundpanel = new BackGroundPanel(getInitialProMap(), propertyType);
		tablepane.add(UiText.SHADING, backgroundpanel);
		
		add(tablepane, BorderLayout.CENTER);
	}
	
	@Override
	protected Map<Integer, Object> getPanelProperties() {
		
		if (!newMap.isEmpty()) {
			setFOProperties(newMap);
		}
		
		return getChangedMap();
	}

	@Override
	public boolean isValidate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Map<Integer, Object> getChangedMap() {
		Map<Integer, Object> borderattris = null;
		if (borderPanel != null) {
			borderattris = borderPanel.getProperties();
		}
		Map<Integer, Object> bgattris = backgroundpanel
				.getProperties();
		if (borderattris != null || bgattris != null) {
			Map<Integer, Object> attris = new HashMap<Integer, Object>();
			if (borderattris != null) {
				attris.putAll(borderattris);
			}
			if (bgattris != null) {
				attris.putAll(bgattris);
			}
			return attris;
		}
		
		return new HashMap<Integer, Object>();
	}

	private class PropertyPanel extends JPanel {

		private WiseCombobox source;
		private WiseCombobox layer;
		private WiseSpinner alaphValue;
		/**
		 * Create the panel
		 */
		public PropertyPanel() {
			super();

			JPanel panel;
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBorder(new TitledBorder(null, PICTURE_SIZE_BAND_TITLE, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			
			{
				//添加图片大小面板
				JPanel setpanel = new JPanel(new GridLayout(2, 2, 0, 0));
				JLabel typelabel = new JLabel(PICTURE_SIZE_TYPE);
				typelabel.setPreferredSize(new Dimension(40, 20));
				WiseCombobox typevale = new WiseCombobox(PICTURE_SIZE_TYPE_LIST);
				typevale.setPreferredSize(new Dimension(90, 20));
				
				RibbonUIManager.getInstance().bind(ExternalGraphic.SET_SIZETYPE_ACTION, ActionCommandType.DYNAMIC_ACTION, typevale);
				
				JPanel typepanel = new JPanel();
				typepanel.add(typelabel);
				typepanel.add(typevale);
				JCheckBox scalingvalue = new JCheckBox(PICTURE_FIX_ASPECT_RATIO);
				
				RibbonUIManager.getInstance().bind(ExternalGraphic.SET_SCALING_ACTION, ActionCommandType.DYNAMIC_ACTION, scalingvalue);
				
				JPanel scalingpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				scalingpanel.add(scalingvalue);
				JLabel heightlabel = new JLabel(PICTURE_SIZE_HEIGHT);
				FixedLengthSpinner heightValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
				
				RibbonUIManager.getInstance().bind(ExternalGraphic.SET_CONTENTHEIGHT_ACTION, ActionCommandType.DYNAMIC_ACTION, heightValue);
				
				JPanel heightpanel = new JPanel();
				heightpanel.add(heightlabel);
				heightpanel.add(heightValue);
				JLabel widthlabel = new JLabel(PICTURE_SIZE_WIDTH);
				FixedLengthSpinner widthValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
				RibbonUIManager.getInstance().bind(ExternalGraphic.SET_CONTENTWIDTH_ACTION, ActionCommandType.DYNAMIC_ACTION, widthValue);
				
				JPanel widthpanel = new JPanel();
				widthpanel.add(widthlabel);
				widthpanel.add(widthValue);
				setpanel.add(typepanel);
				setpanel.add(scalingpanel);
				setpanel.add(heightpanel);
				setpanel.add(widthpanel);
				
				panel.add(setpanel, BorderLayout.CENTER);
			}
			
			

			JLabel label;
			label = new JLabel();
			label.setText(PICTURE_TRANSPARENCY);

			alaphValue = new WiseSpinner();
			SpinnerNumberModel bottomModel = new SpinnerNumberModel(255, 1, 255, 1);
			alaphValue.setModel(bottomModel);

			JLabel label_1;
			label_1 = new JLabel();
			label_1.setText(PICTURE_LAYER);

			layer = new WiseCombobox(UiText.COMMON_COLOR_LAYER);

			JLabel label_2;
			label_2 = new JLabel();
			label_2.setText(PICTURE_SOURCE_TYPE_TITLE);

			source = new WiseCombobox(RibbonUIText.PICTURE_SOURCE_TYPE_LIST);
			
			initialActions();
			
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(label_2)
									.addComponent(label))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(alaphValue, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
										.addGap(28, 28, 28)
										.addComponent(label_1)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(layer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addComponent(source, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label)
							.addComponent(label_1)
							.addComponent(layer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(alaphValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(21, 21, 21)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label_2)
							.addComponent(source, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(29, Short.MAX_VALUE))
			);
			setLayout(groupLayout);
			//
		}
		
		private void initialActions() {
			
			SetDataTypeAction sdta = new SetDataTypeAction();
			source.addActionListener(sdta);
			sdta.setDefaultState();
			
			SetLayerAction sla = new SetLayerAction();
			layer.addActionListener(sla);
			sla.setDefaultState();
			
			SetAlphaAction saa = new SetAlphaAction();
			alaphValue.addActionListener(saa);
			saa.setDefaultState();
		}
		
		public class SetDataTypeAction extends BaseAction {
			@Override
			public void doAction(ActionEvent e) {
				if (e.getSource() instanceof WiseCombobox) {
					WiseCombobox value = (WiseCombobox) e.getSource();
					int index = value.getSelectedIndex();
					EnumProperty set = null;
					if (index == 0) {
						setFOProperty(Constants.PR_SRC_TYPE, null);
						return;
					} else if (index == 1) {
						setFOProperty(Constants.PR_SRC_TYPE, FUNCTIONIMAGE);
						return;
					} else {

					}
				}
			}
			
			public void setDefaultState() {
				Object obj = getInitialProMap().get(Constants.PR_SRC_TYPE);
				Object uiComponent = source;
				if (uiComponent instanceof WiseCombobox) {
					WiseCombobox ui = (WiseCombobox) uiComponent;
					if (obj != null) {
						if (obj.equals(FUNCTIONIMAGE)) {
							ui.initIndex(1);
						}

					} else {
						ui.initIndex(0);
					}
				}
			}
		}
		
		public class SetLayerAction extends BaseAction {
			@Override
			public void doAction(ActionEvent e) {
				if (e.getSource() instanceof JComboBox)
				{
					JComboBox value = (JComboBox) e.getSource();
					int index = value.getSelectedIndex();
					if (index > -1)
					{
						setFOProperty(Constants.PR_GRAPHIC_LAYER, index);
					}
				}
			}
			
			public void setDefaultState() {
				Object obj = getInitialProMap().get(Constants.PR_GRAPHIC_LAYER);
				Object uiComponent = layer;
				if (uiComponent instanceof WiseCombobox) {
					WiseCombobox ui = (WiseCombobox) uiComponent;
					if (obj != null) {
						if (obj instanceof Integer)
						{
							ui.initIndex((Integer) obj);
						}
					} else {
						ui.initIndex(0);
					}
				}
			}
		}
		
		public class SetAlphaAction extends BaseAction {
			@Override
			public void doAction(ActionEvent e) {
				WiseSpinner valuecom = alaphValue;
				Object value = valuecom.getValue();
				setFOProperty(Constants.PR_APHLA, value);
			}
			
			public void setDefaultState() {
				Object obj = getInitialProMap().get(Constants.PR_APHLA);
				
				if (obj instanceof Integer) {
					Integer value = (Integer) obj;
					if (obj != null && value != 0) {
						alaphValue.initValue(obj);
					} else {
						alaphValue.initValue(255);
					}
				}
			}
		}
		
	}
}
