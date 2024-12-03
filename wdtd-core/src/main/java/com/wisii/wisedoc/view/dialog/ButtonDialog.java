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
package com.wisii.wisedoc.view.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.edit.Button;
import com.wisii.wisedoc.document.attribute.edit.ButtonGroup;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class ButtonDialog extends AbstractWisedocDialog {

	ButtonDialog dia;

	public List<ButtonGroup> groups;

	List<ButtonGroupPanel> buttongrouppanel = new ArrayList<ButtonGroupPanel>();

	JLabel nodataLabel = new JLabel("节点值唯一设置:");

	ButtonNoDataSetDialog nodataDialog;
	ButtonNoDataNode btnNoDataNode;
	JButton nodataBtn = new JButton("设置");
	JLabel delLabel = new JLabel("    ");
	JButton delnodataBtn = new JButton("清除");

	public ButtonDialog(CellElement currentcellment) {
		super();
		this.setTitle(RibbonUIText.SET_BUTTONS);
		dia = this;
		setGoup(currentcellment);
		int size = groups != null ? groups.size() : 0;
		if (size > 0) {
			int row = size % 2 == 1 ? size / 2 + 1 : size / 2;
			int column = size < 2 ? 1 : 2;
			this.setLayout(null);
			JPanel panel = new JPanel();
			panel.setLayout(null);
			this.setSize(300 * column + 20, 120 * row + 120);
			panel.setBounds(0, 0, 300 * column + 20, (100 + 20) * row + 80);
			for (int i = 0; i < size; i++) {
				ButtonGroup current = groups.get(i);
				ButtonGroupPanel buttonpanel = new ButtonGroupPanel(current, i, this.btnNoDataNode);
				buttongrouppanel.add(buttonpanel);
				panel.add(buttonpanel);
			}
			JButton ok = new JButton(UiText.OK);
			ok.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					result = DialogResult.OK;
					setButtonGroup();
					dia.dispose();
				}
			});
			JButton cancle = new JButton(UiText.CANCLE);
			cancle.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					result = DialogResult.Cancel;
					dia.dispose();
				}
			});
			ok.setBounds(300 * column - 200, 120 * row + 40, 80, 25);
			cancle.setBounds(300 * column - 100, 120 * row + 40, 80, 25);
			panel.add(ok);
			panel.add(cancle);
			JPanel btnNodataPanel = new JPanel();
			btnNodataPanel.add(nodataLabel);
			btnNodataPanel.add(nodataBtn);
			btnNodataPanel.add(delLabel);
			btnNodataPanel.add(delnodataBtn);
			btnNodataPanel.setBounds(5, 120, 300, 30);
			panel.add(btnNodataPanel);
			this.add(panel);
			nodataBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ButtonNoDataSetDialog dia = new ButtonNoDataSetDialog(btnNoDataNode);
					DialogResult result = dia.showDialog();
					if (DialogResult.OK.equals(result)) {
						ButtonNoDataNode virtualNode = dia.getVirtualNode();
						ButtonDialog.this.btnNoDataNode = virtualNode;
					}

					WisedocUtil.centerOnScreen(nodataDialog);
					// nodataDialog.setVisible(true);
				}
			});
			delnodataBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ButtonDialog.this.btnNoDataNode = null;

				}
			});
			WisedocUtil.centerOnScreen(this);
			this.setVisible(true);

		} else {
			this.setVisible(false);
		}
	}

	public void setButtonGroup() {
		if (buttongrouppanel != null) {
			groups = new ArrayList<ButtonGroup>();
			for (ButtonGroupPanel current : buttongrouppanel) {
				ButtonGroup currentgroup = current.getButtonGroup();
				if (currentgroup != null) {
					List<Button> buttons = currentgroup.getButtons();
					for (Button button : buttons) {
						button.setDataContent(btnNoDataNode);
					}
					groups.add(currentgroup);
				}
			}
		}
	}

	public List<ButtonGroup> getButtonGroup() {
		return groups;
	}

	public DialogResult showDialog() {
		return result;
	}

	@SuppressWarnings("unchecked")
	public void setGoup(CellElement elements) {
		groups = elements.getAttribute(Constants.PR_EDIT_BUTTON) != null ? (List<ButtonGroup>) elements
				.getAttribute(Constants.PR_EDIT_BUTTON) : null;
		if (groups == null) {
			groups = new ArrayList<ButtonGroup>();
			while (elements != null && !(elements instanceof WiseDocDocument)) {
				if (elements.getAttribute(Constants.PR_GROUP) != null) {
					ButtonGroup bottongroup = new ButtonGroup(elements);
					groups.add(bottongroup);
				}
				if (!(elements instanceof PageSequence)) {
					elements = (CellElement) elements.getParent();
				} else {
					break;
				}
			}
		} else {
			while (elements != null && !(elements instanceof WiseDocDocument)) {
				if (elements.getAttribute(Constants.PR_GROUP) != null) {
					if (!isInside(elements)) {
						if (!isBrother(elements)) {
							ButtonGroup bottongroup = new ButtonGroup(elements);
							groups.add(bottongroup);
						}
					}
				} else if (elements.getAttributes() != null && elements.getAttributes().getAttributes() != null
						&& elements.getAttributes().getAttributes().containsKey(Constants.PR_GROUP)) {
				}
				if (!(elements instanceof PageSequence)) {
					elements = (CellElement) elements.getParent();
				} else {
					break;
				}
			}
			for (ButtonGroup current : groups) {
				CellElement currentelement = current.getCellment();
				if (currentelement.getParent() != null || (currentelement instanceof WiseDocDocument)) {
					Object group = currentelement.getAttribute(Constants.PR_GROUP);
					if (group == null) {
						groups.remove(current);
					}
				}
			}
			ButtonNoDataNode dataContent = groups.get(0).getButtons().get(0).getDataContent();
			this.btnNoDataNode = dataContent;
		}

	}

	public boolean isInside(CellElement element) {
		boolean flg = false;
		for (ButtonGroup current : groups) {
			CellElement currentelement = current.getCellment();
			if (currentelement.equals(element)) {
				return true;
			}
		}
		return flg;
	}

	public boolean isBrother(CellElement element) {
		boolean flg = false;
		for (ButtonGroup current : groups) {
			CellElement currentelement = current.getCellment();
			if (currentelement.getParent() != null && element.getParent() != null
					&& currentelement.getParent().equals(element.getParent())) {
				current.setCellment(element);
				return true;
			}
		}
		return flg;
	}

}
