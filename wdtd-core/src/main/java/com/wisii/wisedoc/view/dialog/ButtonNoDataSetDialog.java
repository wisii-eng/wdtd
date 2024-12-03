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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.component.ForEachEditorComponent;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.text.UiText;
/**
 * Desc:节点唯一值设置对话框。
 * @author xieli
 * 2016年11月17日下午4:23:05
 */
public class ButtonNoDataSetDialog extends AbstractWisedocDialog {

	private ButtonNoDataNode oldnode;
	private ButtonNoDataNode newnode;
	private JEditorPane contentcom;
	private DataTreeMouseListener treelistener;
	/* 确定按钮 */
	private JButton btnOK = new JButton(UiText.DIALOG_OK);
	/* 取消按钮 */
	private JButton btnCancel = new JButton(UiText.DIALOG_CANCEL);
	private JTree tree;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public ButtonNoDataSetDialog(ButtonNoDataNode virtualnode) {
		this.oldnode = virtualnode;
		init();
	}

	public ButtonNoDataNode getVirtualNode() {
		return newnode;
	}

	private void init() {
		tree = new WiseDocTree(SystemManager.getCurruentDocument().getDataStructure());
		tree.setCellRenderer(new DataStructureCellRender());
		setTitle("节点值唯一设置");
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		contentcom = new JEditorPane();
		JScrollPane contentjspanel = new JScrollPane(contentcom);
		contentjspanel.setPreferredSize(new Dimension(400, 250));
		con.add(contentjspanel, BorderLayout.CENTER);
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		buttonpanel.add(btnOK);
		buttonpanel.add(btnCancel);
		buttonpanel.setPreferredSize(new Dimension(400, 50));
		con.add(buttonpanel, BorderLayout.SOUTH);
		setOkButton(btnOK);
		setCancelButton(btnCancel);
		JScrollPane treejs = new JScrollPane(tree);
		treejs.setPreferredSize(new Dimension(200, 450));
		con.add(treejs, BorderLayout.EAST);
		setSize(600, 450);
		List contents = null;
		if (oldnode != null) {
			contents = oldnode.getContents();
		}
		final RepeatContentDocment doc = new RepeatContentDocment(contents);
		contentcom.setDocument(doc);
		contentcom.addInputMethodListener(new InputMethodListener() {
			@Override
			public void inputMethodTextChanged(InputMethodEvent event) {
				if (event.getCommittedCharacterCount() < 1) {// 不加上该代码，打包之后在谷歌输入法中输入中文时会把附近的动态节点清掉
					event.consume();
				}
			}

			@Override
			public void caretPositionChanged(InputMethodEvent event) {
				event.consume();
			}
		});
		contentcom.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				int offs = e.getDot();
				if(offs == 0){
					return;
				}
				AttributeSet attribute = doc.getCharacterElement(offs).getAttributes();
				int newoffs = offs;
				int newoffsend = offs;
				if (attribute != null) {
					Object node = attribute.getAttribute(RepeatContentDocment.NODEATT);
					if (node != null) {
						for (int i = offs - 1; i >= 0; i--) {
							AttributeSet nattribute = doc.getCharacterElement(i).getAttributes();
							if (nattribute == null) {
								break;
							}
							Object nnode = nattribute.getAttribute(RepeatContentDocment.NODEATT);
							if (!node.equals(nnode)) {
								break;
							}
							newoffs = i;
						}
						for (int i = offs + 1; i < doc.getLength(); i++) {
							AttributeSet nattribute = doc.getCharacterElement(i).getAttributes();
							if (nattribute == null) {
								break;
							}
							Object nnode = nattribute.getAttribute(RepeatContentDocment.NODEATT);
							if (!node.equals(nnode)) {
								break;
							}
							newoffsend = i + 1;
						}
						if (newoffsend - offs > offs - newoffs) {
							offs = newoffs;
						} else {
							offs = newoffsend;
						}
						contentcom.setCaretPosition(offs);
					}
				}

			}
		});
		initAction();
	}

	private void initAction() {
		treelistener = new DataTreeMouseListener();
		tree.addMouseListener(treelistener);
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newnode = creatVirtualNode();
//				if (newnode == null) {
//					return;
//				}
				result = DialogResult.OK;
				ButtonNoDataSetDialog.this.dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				result = DialogResult.Cancel;
				ButtonNoDataSetDialog.this.dispose();
			}
		});
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private ButtonNoDataNode creatVirtualNode() {
		String text = contentcom.getText();
		if (text == null || text.trim().isEmpty()) {
			contentcom.setCaretPosition(0);
			contentcom.requestFocus();
			return null;
		}
		RepeatContentDocment doc = (RepeatContentDocment) contentcom.getDocument();
		List contents = doc.getContents();
		boolean hasbindnode = false;
		for (Object content : contents) {
			if (content instanceof BindNode) {
				hasbindnode = true;
				break;
			}
		}
		if (!hasbindnode) {
			return null;
		}
		return new ButtonNoDataNode(null, contents);
	}

	private String getText(WiseTextField textcom) {
		String text = textcom.getText();
		if (text != null) {
			text = text.trim();
			if (text.isEmpty()) {
				text = null;
			}
		}
		return text;
	}

	private class DataTreeMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent mouseevent) {
			mouseevent.consume();
			WiseDocTree tree = (WiseDocTree) mouseevent.getSource();
			if (SwingUtilities.isLeftMouseButton(mouseevent) && mouseevent.getClickCount() == 2) {
				TreePath path = tree.getSelectionPath();
				if (path != null) {
					BindNode node = (BindNode) path.getLastPathComponent();
					if (!(node instanceof ButtonNoDataNode)) {
						int start = contentcom.getSelectionStart();
						int end = contentcom.getSelectionEnd();
						((RepeatContentDocment) contentcom.getDocument()).insert(node, start, end - start);
						contentcom.setCaretPosition(end + node.toString().length());
						contentcom.requestFocus();
					}
				}
			}
		}
	}

	private static class NodeChooseComponent extends ForEachEditorComponent {
		private NodeChooseComponent() {
			super(false);
		}

		@Override
		public void setValue(Object value) {
			if (value instanceof ButtonNoDataNode)// 不容许设置虚拟节点为重复节点
			{
				return;
			}
			super.setValue(value);
		}
	}

}
