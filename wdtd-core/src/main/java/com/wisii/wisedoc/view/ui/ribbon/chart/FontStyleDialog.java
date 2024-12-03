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
 * @FontStylePopupPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButtonStrip;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.resource.TranslationTableResource;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：在弹出菜单中，设置字体样式。【轻量级字体属性设置对话框】
 * 
 * 作者：李晓光 创建日期：2009-5-23
 */
public class FontStyleDialog extends AbstractWisedocDialog {
	public FontStyleDialog(final List<Integer> keys, final Map<Integer, Object> att){
		setTitle(TITLE);
		final JPanel main = new JPanel(new BorderLayout());
		
		final JScrollPane scr = new JScrollPane(fontFamily);		
		final JScrollPane scrSize = new JScrollPane(fontSize);
		final JPanel panel = new JPanel(new FlowLayout());
		final JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		fontFamily.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fontSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		panel.add(scr);
		panel.add(scrSize);
				
		final JCommandButtonStrip cleanStyleStrip = new JCommandButtonStrip();
		final JCommandButtonStrip styleBoldStrip = new JCommandButtonStrip();
		final JCommandButtonStrip styleItalicStrip = new JCommandButtonStrip();
		
		cleanStyleStrip.add(cleanStyle);
		styleBoldStrip.add(styleBoldButton);
		styleItalicStrip.add(styleItalicButton);
		
		btnPanel.add(cleanStyleStrip);
		btnPanel.add(styleBoldStrip);
		btnPanel.add(styleItalicButton);
		btnPanel.add(new FontColor());
		
		final JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		btnOK.setMargin(new Insets(0, 0, 0, 0));
		btnCancel.setMargin(new Insets(0, 0, 0, 0));
		controlPanel.add(btnOK);
		controlPanel.add(btnCancel);
		
		final JPanel temp = new JPanel(new BorderLayout());
		temp.add(panel, BorderLayout.CENTER);
		temp.add(btnPanel, BorderLayout.SOUTH);
		
		/*temp.setBorder(BorderFactory.createRaisedBevelBorder());*/
		temp.setBorder(BorderFactory.createLoweredBevelBorder());
		btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		main.add(temp, BorderLayout.CENTER);
		main.add(controlPanel, BorderLayout.SOUTH);
		
		/*setUndecorated(Boolean.TRUE);*/
		setResizable(Boolean.FALSE);
		setOkButton(btnOK);
		setCancelButton(btnCancel);
		
		/*main.setBorder(BorderFactory.createRaisedBevelBorder());*/
		add(main);
		pack();
		this.keys.clear();
		changedMap.clear();
		this.keys = keys;
		this.att = att;
		init();
		initActions();
	}
	private void initActions(){
		final Actions lis = new Actions(){
			@Override
			public void doAction(final ActionEvent e) {
				final Object source = e.getSource();
				if(source == btnOK) {
					result = DialogResult.OK;
					dispose();				
				} else if(source == btnCancel) {
					FontPropertyModel.clearProperty();
					dispose();
				}else if(source == cleanStyle){
					changedMap.clear();
				}
			}
		};
		btnOK.addActionListener(lis);
		btnCancel.addActionListener(lis);
		
		fontFamily.addListSelectionListener(new MySelectionListener(keys.get(0)));
		fontSize.addListSelectionListener(new MySelectionListener(keys.get(1)));
		
		final ActionListener l = new MyActionListener();
		styleBoldButton.addActionListener(l);
		styleItalicButton.addActionListener(l);
		
		colorBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(final ActionEvent e) {
				final Color selectedColor = (Color) colorBox.getSelectedItem();
				changedMap.put(keys.get(3), selectedColor);
			}
		});
	}
	private void init(){
		if(att == null || att.isEmpty()) {
			return;
		}
		if(keys.size() < 4) {
			return;
		}
		Object temp = att.get(keys.get(0));
		if(temp == null) {
			temp = InitialManager.getInitialValue(Constants.PR_FONT_FAMILY, null);
		}
		fontFamily.setSelectedValue(temp, Boolean.TRUE);
		temp = att.get(keys.get(1));
		if(temp == null) {
			temp = InitialManager.getInitialValue(Constants.PR_FONT_SIZE, null);
		}
		final FixedLength value = (FixedLength) temp;
		String s = TranslationTableResource.searchKey(value.getNumericValue() / 1000);
		if(s == null) {
			s = (int)(value.getNumericValue()/1000) +"";
		}
		fontSize.setSelectedValue(s, Boolean.TRUE);
		
		temp = att.get(Constants.PR_FONT_WEIGHT);
		if(temp == null) {
			temp = InitialManager.getInitialValue(Constants.PR_FONT_WEIGHT, null);
		}
		styleBoldButton.getActionModel().setSelected(((Integer)temp == Constants.EN_800));
		temp = att.get(keys.get(2));
		if(temp == null) {
			temp = InitialManager.getInitialValue(Constants.PR_FONT_STYLE, null); 
		}
		styleItalicButton.getActionModel().setSelected(((Integer)temp == Constants.EN_ITALIC));
		temp = att.get(keys.get(3));
		colorBox.setSelectedItem(temp);
	}
	public DialogResult showDialog(final JComponent comp) {
		final Point p = comp.getLocation();
		SwingUtilities.convertPointToScreen(p, comp);
		final int h = comp.getHeight();
		setLocationRelativeTo(comp);
		p.setLocation(p.x, p.y + h);
		setLocation(p);
		setVisible(true);
		return result;
	}
	public Map<Integer, Object> getAttributes(){
		return changedMap;
	}
	private class MySelectionListener implements ListSelectionListener{
		private Integer key = null;
		MySelectionListener(final Integer key){
			this.key = key;
		}
		@Override
		public void valueChanged(final ListSelectionEvent e) {
			final JList list = (JList)e.getSource();
			Object temp = list.getSelectedValue();
			if(list == fontSize) {
				Double d = TranslationTableResource.searchValue((String)temp);
				if(d == null){
					d = WisedocUtil.getDouble((String)temp);
				}
				temp = new FixedLength(d, "pt");
			}
			changedMap.put(key, temp);
		}
	}
	private class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(final ActionEvent e) {
			Integer style = Constants.EN_NORMAL;
			Integer weight = Constants.EN_NORMAL;
			final Object source = e.getSource();
			final JCommandToggleButton btn = (JCommandToggleButton)source;
			if(btn == styleBoldButton){
				if(btn.getActionModel().isSelected()){
					weight = Constants.EN_800;
				}else{
					weight = Constants.EN_NORMAL;
				}
			}else if(btn == styleItalicButton){
				if(btn.getActionModel().isSelected()){
					style = Constants.EN_ITALIC;
				}else{
					style = Constants.EN_NORMAL;
				}
			}
			changedMap.put(keys.get(2), style);
			changedMap.put(Constants.PR_FONT_WEIGHT, weight);
		}
	}
	/**
	 * 字体颜色下拉面板
	 * @author 闫舒寰
	 *
	 */
	private class FontColor extends JCommandPopupMenu {
		
		public FontColor() {
			try {				
				colorBox = new ColorComboBox();
				
				add(colorBox);
			} catch (final IncompatibleLookAndFeelException e1) {
				e1.printStackTrace();
			}		
		}		
	}

	private final Map<Integer, Object> changedMap = new HashMap<Integer, Object>(6);
	private List<Integer> keys = Collections.EMPTY_LIST;
	private Map<Integer, Object> att = null;
	final private JButton btnCancel = new JButton(UiText.DIALOG_CANCEL/*MessageResource.getMessage("wsd.view.gui.dialog.cancel")*/);
	final private JButton btnOK = new JButton(UiText.DIALOG_OK/*MessageResource.getMessage("wsd.view.gui.dialog.ok")*/);
	final private String[] font = UiText.FONT_FAMILY_NAMES_LIST;
	final private String[] size = UiText.FONT_SIZE_LIST;
	final private JList fontFamily = new JList(font);
	final private JList fontSize = new JList(size);
	final private JCommandButton cleanStyle = new JCommandButton("", MediaResource.getResizableIcon("00368.ico"));
	final private JCommandToggleButton styleBoldButton = new JCommandToggleButton("", MediaResource.getResizableIcon("00113.ico"));
	final private JCommandToggleButton styleItalicButton = new JCommandToggleButton("", MediaResource.getResizableIcon("00114.ico"));
	private ColorComboBox colorBox = null;
	private final static String TITLE = UiText.FONT_DIALOG_TAB_TITLE_FONT;//MessageResource.getMessage("wsd.view.gui.text.font.set");
}
