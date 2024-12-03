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

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_IF_STANDARD;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.WISE_DOC_STANDARD;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

public class StandardDialog extends AbstractWisedocDialog
{

	XYLayout laout = new XYLayout();

	public StandardDialog()
	{
		super();
		this.setTitle(WISE_DOC_IF_STANDARD);
		this.setSize(300, 110);
		laout.setWidth(300);
		laout.setHeight(110);
		this.setLayout(laout);
		initComponents();
	}

	private void initComponents()
	{
		JPanel panel = new JPanel();
		panel.setSize(300, 110);
		panel.setLayout(laout);
		// JLabel label = new JLabel(WISE_DOC_NOT_STANDARD);
		final JCheckBox box = new JCheckBox(WISE_DOC_STANDARD, false);

		panel.add(box, new XYConstraints(110, 10, 150, 25));
		JButton yes = new JButton("确定");

		yes.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent e)
			{
				IoXslUtil.setStandard(box.isSelected());
				result = DialogResult.OK;
				dispose();
			}
		});

//		JButton no = new JButton("取消");
//		no.addActionListener(new ActionListener()
//		{
//
//			public void actionPerformed(final ActionEvent e)
//			{
//				IoXslUtil.setStandard(false);
//				result = DialogResult.Cancel;
//				dispose();
//			}
//		});
		panel.add(yes, new XYConstraints(120, 40, 75, 25));
//		panel.add(no, new XYConstraints(170, 40, 75, 25));

		this.add(panel, new XYConstraints(0, 0, 300, 110));
	}

	@Override
	public DialogResult showDialog()
	{
		DialogSupport.centerOnScreen(this);
		setVisible(true);
		dispose();
		return result;
	}
}
