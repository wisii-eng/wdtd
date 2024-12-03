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
 * 
 */
package com.wisii.wisedoc.view.ui.ribbon.edit;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.SwingDS;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * @author wisii
 * 
 */
public class InputDataSourceDialog extends AbstractWisedocDialog {

	DataSourcePanel swingdspanel;

	private JButton okbutton = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

	private JButton cancelbutton = new JButton(
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));

	public InputDataSourceDialog(SwingDS datasource) {
		super(SystemManager.getMainframe(),
				RibbonUIText.DATASOURCE_SWINGDS_TITLE, true);
		swingdspanel = new DataSourcePanel(datasource);
		JPanel mainpanel = new JPanel(new BorderLayout());
		mainpanel.add(swingdspanel, BorderLayout.CENTER);
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		buttonpanel.add(okbutton);
		buttonpanel.add(cancelbutton);
		mainpanel.add(buttonpanel, BorderLayout.SOUTH);
		getContentPane().add(mainpanel);
		setSize(400, 200);
		initAction();
	}

	private void initAction() {
		okbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!swingdspanel.isAllSettingRight()) {
					return;
				}
				result = DialogResult.OK;
				dispose();
			}
		});
		cancelbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public DataSource getDataSource() {
		return swingdspanel.getDataSource();
	}

	/**
	 * 类功能描述：
	 * 
	 * 作者：zhangqiang 创建日期：2009-9-21
	 */
	private static class DataSourcePanel extends JPanel {
		JCheckBox callbox = new JCheckBox(
				RibbonUIText.DATASOURCE_SWINGDS_CALLBACK_CHECK);
		JLabel namelabel = new JLabel(
				RibbonUIText.DATASOURCE_SWINGDS_NAME_LABEL);
		WiseTextField namecom = new WiseTextField(30);

		public DataSourcePanel(SwingDS swingds) {
			setLayout(new FlowLayout(FlowLayout.LEFT));
			add(callbox);
			add(namelabel);
			add(namecom);
			if (swingds != null) {
				if (swingds.iscallback()) {
					namecom.setText(swingds.getCallbackclass());
					callbox.setSelected(true);
					namelabel
							.setText(RibbonUIText.DATASOURCE_SWINGDS_CALLBACK_LABEL);
				} else {
					namecom.setText(swingds.getDataname());
				}
			}
			callbox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (callbox.isSelected()) {
						namelabel
								.setText(RibbonUIText.DATASOURCE_SWINGDS_CALLBACK_LABEL);
					} else {

						namelabel
								.setText(RibbonUIText.DATASOURCE_SWINGDS_NAME_LABEL);
					}

				}
			});

		}

		public boolean isAllSettingRight() {
			String name = namecom.getText();
			if (name == null || name.trim().isEmpty()) {
				namecom.requestFocus();
				return false;
			}
			return true;
		}

		public SwingDS getDataSource()
		{
			String name = namecom.getText();
			if (name == null || name.trim().isEmpty())
			{
				return null;
			}
			if (callbox.isSelected())
			{
				return new SwingDS(name, Constants.EN_TABLE1, "c");
			} else
			{
				return new SwingDS(Constants.EN_TABLE1,name, "c");
			}
		}
	}

}
