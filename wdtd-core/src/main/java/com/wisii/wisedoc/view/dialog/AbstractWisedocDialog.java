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
 * @WisedocDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.dialog;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;
import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

/**
 * 在该对话框提供了对ESC、ENTER键的支持，如果需要是对话框支持这样的操作，可以直接从该对话框继承。
 * 
 * 作者：李晓光 创建日期：2007-08-20
 */
@SuppressWarnings("serial")
public abstract class AbstractWisedocDialog extends JDialog {
	/* 定义焦点管理者 */
	private final KeyboardFocusManager manager = KeyboardFocusManager
			.getCurrentKeyboardFocusManager();
	/* 确定对话框返回状态 */
	protected DialogResult result = DialogResult.Cancel;
	/* 确认按钮的文本 */
	protected final static String TEXT_OK_BUTTON = MessageResource.getMessage("wsd.view.gui.dialog.ok");
	/* 撤销按钮的文本 */
	protected final static String TEXT_CANCEL_BUTTON = MessageResource.getMessage("wsd.view.gui.dialog.cancel");

	/* 确定按钮 */
	private AbstractButton btnOK = null;

	/* 取消按钮 */
	private AbstractButton btnCancel = null;

	/* 为确认按钮定义辅记键 */
	public final static char OK_MNEMONIC = 'Y';

	/* 为确认撤销定义辅记键 */
	public final static char CANCEL_MNEMONIC = 'N';
	// 确定按钮动作命令
	public final static String OK_CMD = "ok";
	// 撤销按钮动作命令
	public final static String CANCEL_CMD = "cancel";

	public AbstractWisedocDialog() {
		this(SystemManager.getMainframe(), "", true);
	}

	public AbstractWisedocDialog(final Frame owner, final String title, final boolean modal) {
		super((Window)(owner==null?SystemManager.getMainframe():owner), title);
        setModal(modal);
		initDialog();
	}

	/** 闫舒寰添加 **/
	// 用于处理二层对话框
	public AbstractWisedocDialog(final Dialog owner, final String title, final boolean modal) {
		super((Window)(owner==null?SystemManager.getMainframe():owner), title);
        setModal(modal);
		initDialog();
	}

	/**
	 * 初始化对话框的键盘监听
	 * 
	 * @return void 无
	 */
	private void initDialog() {
		final JRootPane root = getRootPane();
		final InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		final ActionMap am = root.getActionMap();		
		final String ESCAPE_ACTION = "ESCAPE", ENTER_ACTION = "ENTER";
		
		//设置Escape健
		im.put(KeyStroke.getKeyStroke("ESCAPE"), ESCAPE_ACTION);
		am.put(ESCAPE_ACTION, new AbstractAction() {
			public void actionPerformed(final ActionEvent e) {
				if(isNull(btnCancel)) {
					return;
				}
				final Component c = manager.getFocusOwner();				
				if ((c instanceof JTextComponent || c instanceof JTable)) {
					if(isNull(btnOK)) {
						root.requestFocus();
					} else {
						btnOK.requestFocus();
					}
					return;
				}
				btnCancel.doClick();
			}
		});
		//设置Enter健
		im.put(KeyStroke.getKeyStroke("ENTER"), ENTER_ACTION);
		am.put(ENTER_ACTION, new AbstractAction() {
			public void actionPerformed(final ActionEvent e) {
				if(isNull(btnOK)) {
					return;
				}
				btnOK.doClick();
			}
		});
	}

	/**
	 * 设置对话框的取消按钮
	 * 
	 * @param cancelButton
	 * @return void 无
	 */
	public void setCancelButton(final AbstractButton cancelButton) {
		this.btnCancel = cancelButton;
		setMnemonic(CANCEL_MNEMONIC, this.btnCancel);
	}

	/**
	 * 设置对话框的确认按钮
	 * 
	 * @param okButton
	 * @return void 无
	 */
	public void setOkButton(final AbstractButton okButton) {
		this.btnOK = okButton;
		setMnemonic(OK_MNEMONIC, this.btnOK);
	}

	/**
	 * 为确认、取消按钮添加快捷键
	 * 
	 * @param key
	 *            指定快捷键
	 * @param btn
	 *            指定按钮
	 * @return viod 无
	 */
	public static void setMnemonic(final char key, final AbstractButton btn) {
		if (btn == null || !Character.isLetter(key)) {
			return;
		}
		btn.setMnemonic(key);
		String text = btn.getText();
		if(isEmpty(text) || text.indexOf(key) != -1) {
			return;
		}
		final String pattern = "{0}({1})";
		text = MessageFormat.format(pattern, btn.getText(), key);
		final String cmd = btn.getActionCommand();
		btn.setActionCommand(cmd);
		btn.setText(text);
	}

	/**
	 * 设置当前返回装体
	 * 
	 * @param result
	 *            指定返回状态。
	 */
	public void setResult(final DialogResult result) {
		this.result = result;
	}

	public DialogResult showDialog() {
		DialogSupport.centerOnScreen(this);
		setVisible(true);
		return result;
	}
}
