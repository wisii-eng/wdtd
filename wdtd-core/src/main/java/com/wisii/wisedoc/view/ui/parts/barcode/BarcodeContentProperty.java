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
package com.wisii.wisedoc.view.ui.parts.barcode;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.BARCODE_CONTENT_DISPLAY_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.BARCODE_CONTENT_POSITION_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.BARCODE_FONT_COLOR;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.BARCODE_FONT_FAMILY;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.BARCODE_FONT_SIZE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.BARCODE_FONT_STYLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.BARCODE_INNER_SPACE_LABEL;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.BARCODE_SUBSET_LABEL;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.BARCODE_TEXT_SPACE_LABEL;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.BARCODE_TEXT_TASK;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;

import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.ribbon.BindingTree;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 条形码内容设置面板
 * @author 闫舒寰
 * @version 1.0 2009/03/18
 */
public class BarcodeContentProperty extends JPanel{
	
	Map<Integer, Object> setPropertise;
	
	Map<Integer, Object> initialPropertise;
	
	private WiseCombobox bSubSet;
	private FixedLengthSpinner bInnerSpace;
	private FixedLengthSpinner bFontSpace;
	private JComboBox bFontColor;
	private WiseCombobox bFontStyle;
	private WiseCombobox bFontSize;
	private WiseCombobox bFontFamily;
	private JTextField bContentShow;
	private JTextField bContentField;
	private JCheckBox bPrintText;
	
	private JCommandButton bContentNode;
	
	/**
	 * Create the panel
	 */
	public BarcodeContentProperty() {
		super();
		
		setPropertise = new HashMap<Integer, Object>();

		JPanel panel;
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, RibbonUIText.BARCODE_CONTENT_DISPLAY_BAND, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText(RibbonUIText.BARCODE_CONTENT_LABEL);

		bContentField = new JTextField();

		/*JLabel label_5;
		label_5 = new JLabel();
		label_5.setText("节点：");*/

//		comboBox_1 = new WiseCombobox();
		
		bContentNode = new JCommandButton(RibbonUIText.BARCODE_NODE_BUTTON, MediaResource.getResizableIcon("07461.ico"));
		bContentNode.setDisplayState(CommandButtonDisplayState.MEDIUM);
//		rnNodeTip.addDescriptionSection("当前所选节点在分割线后显示：");
		bContentNode.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		bContentNode.setPopupCallback(new PopupPanelCallback(){
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new BindingTree();
			}
		});

		JLabel label_6;
		label_6 = new JLabel();
		label_6.setText(BARCODE_CONTENT_DISPLAY_BAND);

		bContentShow = new JTextField(RibbonUIText.BARCODE_CONTENT_DISPLAY_FIELD);

		bPrintText = new JCheckBox();
		bPrintText.setText(RibbonUIText.BARCODE_CONTENT_PRINT);
		
		final GroupLayout groupLayout_1 = new GroupLayout(panel);
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(label_6)
						.addComponent(label_4, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12, 12, 12)
					.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(bContentShow)
						.addComponent(bContentField, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
					.addGap(30, 30, 30)
					.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(bContentNode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(bPrintText))
					.addGap(118, 118, 118))
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addGap(4, 4, 4)
					.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(bContentField, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(bContentNode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout_1.createSequentialGroup()
							.addGap(19, 19, 19)
							.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout_1.createSequentialGroup()
									.addGap(4, 4, 4)
									.addComponent(label_6))
								.addComponent(bPrintText)))
						.addGroup(groupLayout_1.createSequentialGroup()
							.addGap(21, 21, 21)
							.addComponent(bContentShow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		panel.setLayout(groupLayout_1);

		JPanel panel_1;
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, BARCODE_TEXT_TASK, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

		JLabel label;
		label = new JLabel();
		label.setText(BARCODE_FONT_FAMILY);

		bFontFamily = new WiseCombobox(new DefaultComboBoxModel(UiText.FONT_FAMILY_NAMES_LIST));

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(BARCODE_FONT_SIZE);

		bFontSize = new WiseCombobox(new DefaultComboBoxModel(UiText.FONT_SIZE_LIST));

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(BARCODE_FONT_STYLE);

		bFontStyle = new WiseCombobox(new DefaultComboBoxModel(UiText.FONT_STYLE_LIST));

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText(BARCODE_FONT_COLOR);

//		bFontColor = new WiseCombobox();
		
		try {
			bFontColor = new ColorComboBox();
		} catch (IncompatibleLookAndFeelException e) {
			e.printStackTrace();
		}
		
		final GroupLayout groupLayout_2 = new GroupLayout(panel_1);
		groupLayout_2.setHorizontalGroup(
			groupLayout_2.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_2.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout_2.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addGap(12, 12, 12)
							.addComponent(bFontFamily, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addGap(40, 40, 40)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addGap(38, 38, 38)
							.addComponent(bFontSize, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout_2.createSequentialGroup()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addGap(12, 12, 12)
							.addComponent(bFontStyle, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addGap(40, 40, 40)
							.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(12, 12, 12)
							.addComponent(bFontColor, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(75, Short.MAX_VALUE))
		);
		groupLayout_2.setVerticalGroup(
			groupLayout_2.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_2.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout_2.createSequentialGroup()
							.addGap(4, 4, 4)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addComponent(bFontFamily, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout_2.createSequentialGroup()
							.addGap(4, 4, 4)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addComponent(bFontSize, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(22, 22, 22)
					.addGroup(groupLayout_2.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout_2.createSequentialGroup()
							.addGap(4, 4, 4)
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addComponent(bFontStyle, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout_2.createSequentialGroup()
							.addGap(4, 4, 4)
							.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addComponent(bFontColor, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		panel_1.setLayout(groupLayout_2);

		JPanel panel_2;
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, BARCODE_CONTENT_POSITION_TITLE, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

		JLabel label_7;
		label_7 = new JLabel();
		label_7.setText(BARCODE_TEXT_SPACE_LABEL);

		bFontSpace = new FixedLengthSpinner();

		JLabel label_8;
		label_8 = new JLabel();
		label_8.setText(BARCODE_INNER_SPACE_LABEL);

		bInnerSpace = new FixedLengthSpinner();

		JLabel label_9;
		label_9 = new JLabel();
		label_9.setText(BARCODE_SUBSET_LABEL);

		bSubSet = new WiseCombobox(new DefaultComboBoxModel(RibbonUIText.BARCODE_SUBSET));
		
		final GroupLayout groupLayout_3 = new GroupLayout(panel_2);
		groupLayout_3.setHorizontalGroup(
			groupLayout_3.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_3.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout_3.createSequentialGroup()
							.addComponent(label_7)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(bFontSpace, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(26, 26, 26)
							.addComponent(label_8)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(bInnerSpace, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout_3.createSequentialGroup()
							.addComponent(label_9)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(bSubSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(67, Short.MAX_VALUE))
		);
		groupLayout_3.setVerticalGroup(
			groupLayout_3.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_3.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_7)
						.addComponent(bFontSpace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_8)
						.addComponent(bInnerSpace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20, 20, 20)
					.addGroup(groupLayout_3.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_9)
						.addComponent(bSubSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		panel_2.setLayout(groupLayout_3);
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
						.addComponent(panel_2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(24, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}
	
	private void initialActions() {
		BarcodeFontSizeAction bfSize = new BarcodeFontSizeAction();
		bFontSize.addActionListener(bfSize);
		bfSize.setDefaultState();
		
		BarcodeSubSetAction bssa = new BarcodeSubSetAction();
		bSubSet.addActionListener(bssa);
		bssa.setDefaultState();
		
		BarcodePrintTextAction bpta = new BarcodePrintTextAction();
		bPrintText.addActionListener(bpta);
		bpta.setDefaultState();
		
		BarcodeInputContentAction bica = new BarcodeInputContentAction();
		bContentField.addActionListener(bica);
		bica.setDefaultState();
	}
	
	public Map<Integer, Object> getProperties(){
		
		Map<Integer, Object> temp = new HashMap<Integer, Object>();
		
		if (!this.setPropertise.isEmpty()) {
			temp.putAll(setPropertise);
		}
		
		temp.put(Constants.PR_BARCODE_FONT_FAMILY, bFontFamily.getSelectedItem());
		temp.put(Constants.PR_BARCODE_TEXT_CHAR_SPACE, bInnerSpace.getValue());
		temp.put(Constants.PR_BARCODE_TEXT_BLOCK, bFontSpace.getValue());
		
		temp.put(Constants.PR_BARCODE_STRING, bContentShow.getText());
		
//		bContentNode;
		
		return temp;
	}
	
	public void initialPanelProperty(Map<Integer, Object> pro) {
		
		initialPropertise = pro;
		
		bFontFamily.setSelectedItem(pro.get(Constants.PR_BARCODE_FONT_FAMILY));
		bInnerSpace.initValue(pro.get(Constants.PR_BARCODE_TEXT_CHAR_SPACE));
		bFontSpace.initValue(pro.get(Constants.PR_BARCODE_TEXT_BLOCK));
		
		bContentShow.setText((String)pro.get(Constants.PR_BARCODE_STRING));
		
		//初始化面板是初始化面板，里面具体的属性还需要这里初始化
		initialActions();
	}
	
	
	public class BarcodeFontSizeAction extends BaseAction {
		@Override
		public void doAction(ActionEvent e) {

			
			JComboBox jb = bFontSize;
			double fontSize = -1;
			
			if (jb.getSelectedItem() instanceof String) {
				String selectedSize = (String) jb.getSelectedItem();
				//把中文字号转化成数字形式的pt字号
				if(selectedSize.equals(UiText.FONT_SIZE_LIST[0])){
					fontSize = 63;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SPECIAL))){
					fontSize = 54;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_BIG))){
					fontSize = 42;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_LITTLE_BIG))){
					fontSize = 36;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_BIG_FIRST))){
					fontSize = 31.5;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_FIRST))){
					fontSize = 28;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SECOND))){
					fontSize = 21;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SECOND_LITTLE))){
					fontSize = 18;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_THIRD))){
					fontSize = 16;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_FOURTH))){
					fontSize = 14;
				} else if(selectedSize.equals(UiText.FONT_SIZE_LIST[10])){
					fontSize = 12;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_FIFTH))){
					fontSize = 10.5;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_FIFTH_LITTLE))){
					fontSize = 9;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SIXTH))){
					fontSize = 8;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SIXTH_LITTLE))){
					fontSize = 6.875;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SEVENTH))){
					fontSize = 5.25;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_EIGHTH))){
					fontSize = 4.5;
				} else {
					//解析本来是数字形式的字符
					try{
						fontSize = Double.parseDouble(selectedSize);
					}catch (NumberFormatException ee) {
						// TODO: handle exception
//						System.out.println("请输入正确的数字");
					}
				}
			}
			
			//设置字号到属性中
			if (fontSize != -1) {
				setFOProperty(Constants.PR_BARCODE_FONT_HEIGHT,
						new FixedLength(fontSize,"pt"));
			}
		
		}
		
		public void setDefaultState() {
			Object oValue = initialPropertise.get(Constants.PR_BARCODE_FONT_HEIGHT);
			
			if (oValue instanceof FixedLength) {
				FixedLength value = (FixedLength) oValue;
				bFontSize.setSelectedItem(value.toString());
			}
		}
	}
	
	private class BarcodeSubSetAction extends BaseAction{
		@Override
		public void doAction(ActionEvent e) {
			setFOProperty(Constants.PR_BARCODE_SUBSET, 	new EnumProperty(convert(bSubSet.getSelectedIndex()), ""));
		}
		
		private int convert(int index){
			
			if(index == 0){
				return Constants.EN_A;
			} else if(index == 1) {
				return Constants.EN_B;
			} else if(index == 2){
				return Constants.EN_C;
			}
			
			return 0;
		}
		
		public void setDefaultState() {
			WiseCombobox ui = bSubSet;
			Object oValue = initialPropertise.get(Constants.PR_BARCODE_SUBSET);
			if (oValue instanceof EnumProperty) {
				EnumProperty value = (EnumProperty) oValue;
				if (value.equals(new EnumProperty(Constants.EN_A, ""))) {
					ui.initIndex(0);
				} else if (value.equals(new EnumProperty(Constants.EN_B, ""))) {
					ui.initIndex(1);
				} else if (value.equals(new EnumProperty(Constants.EN_C, ""))) {
					ui.initIndex(2);
				}
			}
		}
	}
	
	private class BarcodePrintTextAction extends BaseAction {
		@Override
		public void doAction(ActionEvent e) {
			setFOProperty(Constants.PR_BARCODE_PRINT_TEXT, new EnumProperty(convert(bPrintText.isSelected()),""));
		}
		
		private int convert(boolean value){
			if(value){
				return Constants.EN_TRUE;
			} else{
				return Constants.EN_FALSE;
			}
		}
		
		public void setDefaultState() {
			JCheckBox ui = bPrintText;
			
			Object o = initialPropertise.get(Constants.PR_BARCODE_PRINT_TEXT);
			
			if (o instanceof EnumProperty) {
				EnumProperty value = (EnumProperty) o;
				if (value.equals(new EnumProperty(convert(true),""))) {
					ui.setSelected(true);
				} else {
					ui.setSelected(false);
				}
			}
			
			
		}
	}
	
	private class BarcodeInputContentAction extends BaseAction {
		@Override
		public void doAction(ActionEvent e) {
			if (e.getSource() instanceof JTextField) {
				JTextField value = (JTextField) e.getSource();
				
				int barcodeType = ((EnumProperty)initialPropertise.get(Constants.PR_BARCODE_TYPE)).getEnum();
				
				if (barcodeType == Constants.EN_EAN13 || barcodeType == Constants.EN_EAN8
						|| barcodeType == Constants.EN_UPCA || barcodeType == Constants.EN_UPCE) {
					System.err.println("wrong");
					return;
				}
				
				setFOProperty(Constants.PR_BARCODE_CONTENT, new BarCodeText(value.getText()));
			}
		}
		
		public void setDefaultState() {
			
			Object obj = initialPropertise.get(Constants.PR_BARCODE_TYPE);
			JTextField ui = bContentField;
			if (obj instanceof BarCodeText) {
				BarCodeText value = (BarCodeText) obj;
				ui.setText(value.getText());
			}
		}
	}
	
	/**
	 * 设置当前属性到该属性面板的Map中
	 * @param propertyId
	 * @param propertyValue
	 */
	private void setFOProperty(int propertyId, Object propertyValue){
		this.setPropertise.put(propertyId, propertyValue);
	}
	
	private void setFOProperties(Map<Integer, Object> properties){
		this.setPropertise.putAll(properties);
	}

}
