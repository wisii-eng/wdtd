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
 * @SelectDataSettingPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserColumnInfo;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserInfo;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserSource;
import com.wisii.wisedoc.swing.PopupBrowserColumnInfoTable;
import com.wisii.wisedoc.swing.PopupBrowserColumnInfoTableModel;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-8-26
 */
public class PopupBrowserSettingPanel extends JPanel {

	PopupBrowserColumnInfoTable table;

	private DataSource datasource;

	private PopupBrowserInfo popupbrowserinfo;

	private JButton dssetbutton = new JButton(RibbonUIText.SELECT_DATASOURCE);

	PopupBrowserSettingPanel(final AbstractWisedocDialog parent,
			PopupBrowserInfo popupbrowserinfo)
	{
		super(new BorderLayout());
		dssetbutton.setEnabled(true);
		if (popupbrowserinfo != null)
		{
			this.popupbrowserinfo = new PopupBrowserInfo(popupbrowserinfo.getDatasource(),popupbrowserinfo.getColumninfos());
			datasource = popupbrowserinfo.getDatasource();
			
		}
		JPanel commonpanel = new JPanel(new GridLayout(3, 1));
		dssetbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {

				PopupBrowserSourceDialog setdialog = new PopupBrowserSourceDialog(parent, datasource);
				DialogResult result = setdialog.showDialog();
				if (result == DialogResult.OK) {
					DataSource ndatasource = setdialog.getDataSource();
					if (ndatasource.equals(datasource)) {
						return;
					} else {
						datasource = ndatasource;
						reInitColumn();
					}
				}
			}

		});
		JPanel datasourcepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		datasourcepanel.add(dssetbutton);
		add(BorderLayout.NORTH, commonpanel);
		JPanel viewsetpanel = new JPanel(new BorderLayout());
		List<PopupBrowserColumnInfo> infos = null;
		if(this.popupbrowserinfo !=null){
			infos = this.popupbrowserinfo.getColumninfos();
		}
		table = new PopupBrowserColumnInfoTable(infos);
		JScrollPane datasetjs = new JScrollPane(table);
		viewsetpanel.add(BorderLayout.CENTER, datasetjs);
		add(datasourcepanel,BorderLayout.NORTH);
		add(BorderLayout.CENTER, viewsetpanel);
	}

	public boolean isAllSettingRight() {
		if (table == null) {
			return false;
		} else {
			if (table.getModel() != null) {
				PopupBrowserColumnInfoTableModel model = (PopupBrowserColumnInfoTableModel) table.getModel();
				if (!model.HaveOptionPath()) {
					return false;
				}
			} else {
				return false;
			}
		}

		return true;
	}

	public PopupBrowserInfo getPopupBrowserInFo() {

		return new PopupBrowserInfo(datasource, table.getSelectColumnInFOs());

	}

	private void reInitColumn() {
		List<PopupBrowserColumnInfo> cifoes = new ArrayList<PopupBrowserColumnInfo>();
		if (datasource != null) {
			if (datasource instanceof PopupBrowserSource) {
				String string = ((PopupBrowserSource) datasource).getData();
				String[] split = string.split(",");
				for (int i = 0; i < split.length; i++) {
					cifoes.add(new PopupBrowserColumnInfo(split[i], null));
				}
			}

		}
		table.setEnabled(true);
		table.reInitColumnInfoes(cifoes);
	}
}
