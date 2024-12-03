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
 * @SelectColumnInFOesTable.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableCellEditor;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.attribute.transform.PopupBrowserColumnInfo;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserSource;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.component.TextAndButtonComponent;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.ribbon.edit.PopupBrowserSourceDialog;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-8-31
 */
public class PopupBrowserDataTable extends JTable {
	private DetialEditor detialeditor = new DetialEditor();

	public PopupBrowserDataTable(List<PopupBrowserSource> source) {
		super(new PopupBrowserDataTableModel(source));
	}

	public void reInitColumnInfoes(List<PopupBrowserSource> source) {
		setModel(new PopupBrowserDataTableModel(source));
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		if (column == 3) {
			return detialeditor;
		} else {
			return super.getCellEditor(row, column);
		}
	}

	private class DetialEditor extends javax.swing.AbstractCellEditor implements TableCellEditor{
		
		DetialComponent com;
		private DetialEditor() 
		{
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			com = new DetialComponent((String)value);
			return com;
		}

		public Object getCellEditorValue() {
			return com.getValue();
		}

	}
	private class DetialComponent extends TextAndButtonComponent
	{
		String dataname;

		public DetialComponent(String dataname) {
			super();
			this.dataname = dataname;
			txtComp.setEditable(false);
			setValue(dataname);
			btnComp.addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent e)
				{
					Container parent = PopupBrowserDataTable.this.getParent();
					while (parent != null
							&& !(parent instanceof PopupBrowserSourceDialog))
					{
						parent = parent.getParent();
					}
					ColumnSettingDialog dtsetdia = new ColumnSettingDialog(
							(Dialog) parent, DetialComponent.this.dataname);
					DialogResult result = dtsetdia.showDialog();
					// System.out.println("result:" + result);
					if (DialogResult.OK.equals(result))
					{
						// System.out.println("sdsdssdds");
//						setValue(dtsetdia.getColumnitems());
					}
				}
			});
		}
		
		


	}
	private class ColumnSettingDialog extends AbstractWisedocDialog
	{

		

		private JButton cancelbutton = new JButton(
				getMessage(MessageConstants.DIALOG_COMMON
						+ MessageConstants.CANCEL));


		private String columnitems;

		private ColumnSettingDialog(Dialog parent, String columnitems)
		{
			super(parent, "详细信息",
					true);
			this.columnitems = columnitems;
			setSize(400, 320);
			JPanel mainpanel = new JPanel(new BorderLayout());
			JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING,
					0, 0));
			buttonpanel.add(cancelbutton);
			JTextPane tp = new JTextPane();
			tp.setEditable(false);
			String datastr = columnitems.replaceAll(",", "\n");
			tp.setText(datastr);
			JScrollPane js = new JScrollPane(tp);
			mainpanel.add(js, BorderLayout.CENTER);
			mainpanel.add(buttonpanel, BorderLayout.SOUTH);
			getContentPane().add(mainpanel);
			setCancelButton(cancelbutton);
			DialogSupport.centerOnScreen(this);
			cancelbutton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					distroy(DialogResult.Cancel);
				}
			});

		}


		private void distroy(DialogResult result)
		{
			this.result = result;
			setVisible(false);
			dispose();
		}
	}

	/**
	 * 
	 * {方法的功能/动作描述}:
	 * 
	 * @param 引入参数
	 *            : {引入参数说明}
	 * @return List<FileSource>: {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public List<PopupBrowserSource> getPopupBrowserSource() {

		return ((PopupBrowserDataTableModel) getModel()).getPopupBrowserSource();
	}
	
	
}
