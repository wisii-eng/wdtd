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
 * @ConnectWithDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.document.attribute.edit.Formula;
import com.wisii.wisedoc.document.attribute.edit.Parameter;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-1
 */
public class ConnectWithDialog extends AbstractWisedocDialog
{
	private ConnWith conwith;
	private ParamTable parmtable;
	private FormulaTable formulatable;
	private JButton parmaddbutton = new JButton(RibbonUIText.UTIL_ADD);
	private JButton parmdelbutton = new JButton(RibbonUIText.UTIL_DEL);
	private JButton formulaaddbutton = new JButton(RibbonUIText.UTIL_ADD);
	private JButton formuladelbutton = new JButton(RibbonUIText.UTIL_DEL);
	private JButton okbutton = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));
	private JButton cancelbutton = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));

	public ConnectWithDialog(ConnWith conwith)
	{
		setTitle(RibbonUIText.EDIT_CONNWITH_TITLE);
		this.conwith = conwith;
		JPanel mainpanel = new JPanel(new GridLayout(2, 1));
		List<Parameter> parameters = null;
		List<Formula> formulas = null;
		if (conwith != null)
		{
			parameters = conwith.getParms();
			formulas = conwith.getFormulas();
		}
		JPanel parmpanel = new JPanel(new BorderLayout());
		parmpanel.setBorder(new TitledBorder(RibbonUIText.EDIT_CONNWITH_PARM));
		parmtable = new ParamTable(parameters);
		JScrollPane parmjs = new JScrollPane(parmtable);
		parmpanel.add(parmjs, BorderLayout.CENTER);
		JPanel parabuttonpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		parabuttonpanel.add(parmaddbutton);
		parabuttonpanel.add(parmdelbutton);
		parmpanel.add(parabuttonpanel, BorderLayout.NORTH);
		JPanel formulapanel = new JPanel(new BorderLayout());
		formulapanel.setBorder(new TitledBorder(RibbonUIText.EDIT_CONNWITH_FORMUL));
		formulatable = new FormulaTable(formulas,parmtable);
		JScrollPane formulajs = new JScrollPane(formulatable);
		formulapanel.add(formulajs, BorderLayout.CENTER);
		JPanel formulabuttonpanel = new JPanel(new FlowLayout(
				FlowLayout.LEADING));
		formulabuttonpanel.add(formulaaddbutton);
		formulabuttonpanel.add(formuladelbutton);
		formulapanel.add(formulabuttonpanel, BorderLayout.NORTH);
		mainpanel.add(parmpanel);
		mainpanel.add(formulapanel);
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		buttonpanel.add(okbutton);
		buttonpanel.add(cancelbutton);
		initActions();
		JPanel mpanel = new JPanel(new BorderLayout());
		mpanel.add(mainpanel, BorderLayout.CENTER);
		mpanel.add(buttonpanel, BorderLayout.SOUTH);
		getContentPane().add(mpanel);
		setSize(600, 400);
	}

	private void initActions()
	{
		parmaddbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				parmtable.addRow();

			}
		});
		parmdelbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectrow = parmtable.getSelectedRow();
				if (selectrow > -1)
				{
					parmtable.delRow(selectrow);
				}

			}
		});

		formulaaddbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				formulatable.addRow();

			}
		});
		formuladelbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectrow = formulatable.getSelectedRow();
				if (selectrow > -1)
				{
					formulatable.delRow(selectrow);
				}

			}
		});
		okbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!parmtable.isAllSetOk() || !formulatable.isAllSetOk())
				{
					return;
				}
				List<Parameter> parmeters = parmtable.getParameters();
				List<Formula> formulas = formulatable.getFormulas();
				conwith = new ConnWith(formulas, parmeters);
				result = DialogResult.OK;
				dispose();
			}
		});
		cancelbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
	}

	/**
	 * @返回 conwith变量的值
	 */
	public final ConnWith getConwith()
	{
		return conwith;
	}

}
