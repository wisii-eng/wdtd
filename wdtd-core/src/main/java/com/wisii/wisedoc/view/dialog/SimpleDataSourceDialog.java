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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wisii.wisedoc.document.attribute.transform.DataTransformTable;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileChooser;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class SimpleDataSourceDialog extends AbstractWisedocDialog
{

	SimpleDataSourceDialog dsdialog;

	DataTransformTable source;

	JTable datatable = new JTable();

	JLabel nulltreat = new JLabel(UiText.NULL_DATA_TREAT);

	JTextArea text = new JTextArea();

	JButton ok = new JButton(UiText.OK);

	JButton cancle = new JButton(UiText.CANCLE);

	JButton delete = new JButton(UiText.DELETE_ROW);

	JButton add = new JButton(UiText.ADD_ROW);

	JButton importfile = new JButton("导入");

	JButton exportfile = new JButton("导出");

	WiseDocFileFilter filefilter = new WiseDocFileFilter(".xml", "xml");

	JScrollPane scrollpanel = new JScrollPane();

	WiseCombobox treat = new WiseCombobox();
	{
		treat.addItem(UiText.VIEW_AS_OLD_DATA);
		treat.addItem(UiText.NULL_DATA);
		treat.addItem(UiText.SPECIFIED_STRING);
		treat.setBounds(212, 280, 130, 25);
	}

	public SimpleDataSourceDialog()
	{
		super();
		setTitle(RibbonUIText.SET_SIMPLE_DATA_TRANSFORM_TABLE);
		source = null;
		dsdialog = this;
		initialize();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	public SimpleDataSourceDialog(AbstractWisedocDialog parent,
			DataTransformTable src)
	{
		super(parent, RibbonUIText.SET_SIMPLE_DATA_TRANSFORM_TABLE, true);
		dsdialog = this;
		source = src;
		initialize();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	private void initialize()
	{
		this.setSize(500, 400);
		this.setLayout(new BorderLayout());

		JPanel contentpanel = new JPanel(new BorderLayout());

		JPanel onepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JPanel twopanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));

		JScrollPane treepanel = new JScrollPane();

		Object[] columnNames =
		{ UiText.OLD_DATA, UiText.VIEW_AS };
		DefaultTableModel newdatamode;

		Object[][] models = getModel();
		newdatamode = new DefaultTableModel(models, columnNames);

		datatable.setModel(newdatamode);
		treepanel.setViewportView(datatable);

		onepanel.add(add);
		onepanel.add(delete);
		onepanel.add(importfile);
		onepanel.add(exportfile);

		twopanel.add(nulltreat);
		twopanel.add(treat);

		text.setLineWrap(true);
		text.setCaretPosition(text.getDocument().getLength());

		scrollpanel.setPreferredSize(new Dimension(200, 25));
		scrollpanel.setViewportView(text);
		twopanel.add(scrollpanel);

		contentpanel.add(twopanel, BorderLayout.NORTH);
		contentpanel.add(treepanel, BorderLayout.CENTER);
		contentpanel.add(onepanel, BorderLayout.SOUTH);

		buttonpanel.add(ok);
		buttonpanel.add(cancle);

		this.add(contentpanel, BorderLayout.CENTER);
		this.add(buttonpanel, BorderLayout.SOUTH);

		setInitialState();
		addAction();
	}

	public void setInitialState()
	{
		int treattype = source != null ? source.getDatatreat() : 0;
		String textvalue = source != null ? source.getText() : "";
		text.setText(textvalue);
		if (treattype == DataTransformTable.SETNULL)
		{
			treat.setSelectedItem(UiText.NULL_DATA);
			scrollpanel.setVisible(false);
		} else if (treattype == DataTransformTable.SETSAME)
		{
			treat.setSelectedItem(UiText.VIEW_AS_OLD_DATA);
			scrollpanel.setVisible(false);
		} else if (treattype == DataTransformTable.SETSTRING)
		{
			treat.setSelectedItem(UiText.SPECIFIED_STRING);
			scrollpanel.setVisible(true);
		}
	}

	public void addAction()
	{
		treat.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int index = treat.getSelectedIndex();
				if (index == 2)
				{
					scrollpanel.setVisible(true);
					scrollpanel.getParent().getParent().validate();
				} else
				{
					scrollpanel.setVisible(false);
					scrollpanel.getParent().getParent().validate();
				}
			}
		});
		add.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DefaultTableModel model = (DefaultTableModel) datatable
						.getModel();
				int current = datatable.getSelectedRow();
				model.insertRow(current + 1, new Object[]
				{});
			}
		});
		delete.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DefaultTableModel model = (DefaultTableModel) datatable
						.getModel();
				int current = datatable.getSelectedRow();
				if (current >= 0)
				{
					model.removeRow(current);
				}
			}
		});
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				boolean flg = setDataSource(true);
				if (flg)
				{
					result = DialogResult.OK;
					dsdialog.dispose();
				}
			}
		});
		cancle.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				dsdialog.dispose();
			}
		});

		importfile.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				final WiseDocFileChooser chooser = (WiseDocFileChooser) DialogSupport
						.getDialog(filefilter);
				chooser.setMultiSelectionEnabled(true);
				chooser.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent evt)
					{
						if (JFileChooser.APPROVE_SELECTION.equals(evt
								.getActionCommand()))
						{
							chooser.setResult(DialogResult.OK);
						} else if (JFileChooser.CANCEL_SELECTION.equals(evt
								.getActionCommand()))
						{
							chooser.setResult(DialogResult.Cancel);
						}
						chooser.distroy();
					}
				});
				DialogResult result = chooser
						.showDialog(JFileChooser.OPEN_DIALOG);
				if (result == DialogResult.OK)
				{
					File[] files = chooser.getSelectedFiles();
					if (files != null)
					{
						for (int i = 0; i < files.length; i++)
						{
							File currentfile = files[i];
							List<List<String>> datas = createColumnItems(currentfile);
							if (datas != null)
							{
								DefaultTableModel model = (DefaultTableModel) datatable
										.getModel();
								int currentrow = datatable.getSelectedRow();

								for (List<String> currentlist : datas)
								{
									String key = currentlist.get(0);
									String value = currentlist.get(1);
									if (!("".equals(key) && "".equals(value)))
									{
										model.insertRow(currentrow + 1,
												new Object[]
												{ currentlist.get(0),
														currentlist.get(1) });
										currentrow = currentrow + 1;
									}
								}
							}
						}
					}
				}
			}
		});
		exportfile.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				final WiseDocFileChooser chooser = (WiseDocFileChooser) DialogSupport
						.getDialog(filefilter);
				chooser.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent evt)
					{
						if (JFileChooser.APPROVE_SELECTION.equals(evt
								.getActionCommand()))
						{
							chooser.setResult(DialogResult.OK);
							if (isExists(chooser.getSelectedFile()))
							{
								int result = JOptionPane
										.showConfirmDialog(
												chooser,
												MessageResource
														.getMessage(MessageConstants.FILE
																+ MessageConstants.WHETHERREPLACEFILE),
												MessageResource
														.getMessage(MessageConstants.FILE
																+ MessageConstants.FILEEXISTED),
												JOptionPane.YES_NO_OPTION);
								if (result != JOptionPane.YES_OPTION)
									return;
							}
						} else if (JFileChooser.CANCEL_SELECTION.equals(evt
								.getActionCommand()))
						{
							chooser.setResult(DialogResult.Cancel);
						}
						chooser.distroy();
					}
				});
				DialogResult result = chooser
						.showDialog(JFileChooser.SAVE_DIALOG);
				if (result == DialogResult.OK)
				{
					String filename = "";
					filename = chooser.getSelectedFile().getAbsolutePath();
					if (!filename.endsWith(".xml"))
					{
						filename += ".xml";
					}
					try
					{
						boolean flg = setDataSource(false);
						if (flg)
						{
							java.io.Writer writer = new OutputStreamWriter(
									new FileOutputStream(filename), "UTF-8");
							PrintWriter out = new PrintWriter(writer);
							DataTransformTable source = getSource();
							List<List<String>> datas = source.getDatas();
							if (datas != null)
							{
								out.print("<root>");
								for (List<String> currentrow : datas)
								{
									if (currentrow != null
											&& currentrow.size() == 2)
									{
										String key = currentrow.get(0);
										String value = currentrow.get(1);
										// if (!(key == null && value == null
										// && "".equals(key) && ""
										// .equals(value)))
										// {
										out.print("<item");
										out.print(" key=\"");
										out.print(key);
										out.print("\"");
										out.print(" value=\"");
										out.print(value);
										out.print("\"/>");
										// }
									}
								}
								out.print("</root>");
							}
							out.close();
						}

					} catch (IOException e1)
					{
						e1.printStackTrace();
					}

				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	private List<List<String>> createColumnItems(File file)
	{
		Document document = null;
		SAXReader reader = new SAXReader();
		try
		{
			document = reader.read(file);
			Element root = document.getRootElement();
			List<Element> childrens = root.elements();
			if (childrens != null)
			{
				List<List<String>> list = new ArrayList<List<String>>();
				for (Element currentelement : childrens)
				{
					if (currentelement.elements() == null
							|| currentelement.elements().isEmpty())
					{
						List<String> current = new ArrayList<String>();
						Attribute key = currentelement.attribute("key");
						Attribute value = currentelement.attribute("value");
						if (key != null && value != null)
						{
							current.add(key.getValue());
							current.add(value.getValue());
						}
						if (!current.isEmpty())
						{
							list.add(current);
						}
					}
				}
				if (!list.isEmpty())
				{
					return list;
				}
			}

		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private boolean isExists(File file)
	{
		boolean result = false;
		if (file == null)
			result = false;
		else if (file.isDirectory())
			result = false;
		else if (file.exists())
			result = true;
		else
		{
			String fileName = file.getAbsolutePath() + ".xml";
			if (new File(fileName).exists())
				result = true;
			else
				result = false;
		}
		return result;
	}

	public DataTransformTable getDataSource()
	{
		return source;
	}

	public boolean setDataSource(boolean finalflg)
	{
		int rownumber = datatable.getRowCount();
		List<List<String>> table = new ArrayList<List<String>>();
		for (int i = 0; i < rownumber; i++)
		{
			List<String> row = new ArrayList<String>();
			Object column1 = datatable.getValueAt(i, 0);
			if (column1 != null && !"".equals(column1))
			{
				String columnone = column1.toString();
				row.add(columnone);
			} else if (finalflg)
			{
				datatable.editCellAt(i, 0);
				return false;
			}
			Object column2 = datatable.getValueAt(i, 1);

			if (column2 != null && !"".equals(column2))
			{
				String columntwo = column2.toString();
				row.add(columntwo);
			} else if (finalflg)
			{
				datatable.editCellAt(i, 1);
				return false;
			}
			table.add(row);
		}
		source = new DataTransformTable(table);
		int index = treat.getSelectedIndex();
		source.setDatatreat(index);
		if (index == 2)
		{
			String sourcetext = text.getText();
			// sourcetext = IoXslUtil.getXmlText(sourcetext);
			source.setText(sourcetext);
		}
		return true;
	}

	public DialogResult showDialog()
	{
		return result;
	}

	public DataTransformTable getSource()
	{
		return source;
	}

	public void setSource(DataTransformTable source)
	{
		this.source = source;
	}

	public Object[][] getModel()
	{
		if (source == null)
		{
			Object[][] models =
			{ null };
			return models;
		} else
		{
			List<List<String>> datas = source.getDatas();
			int size = datas.size();
			Object[][] models = new Object[size][2];
			for (int i = 0; i < size; i++)
			{
				List<String> currentlist = datas.get(i);
				for (int j = 0; j < 2; j++)
				{
					String current = currentlist.get(j);
					models[i][j] = current;
				}
			}
			return models;
		}
	}

}
