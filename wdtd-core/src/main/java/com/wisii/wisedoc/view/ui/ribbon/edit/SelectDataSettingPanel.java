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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.FileSource;
import com.wisii.wisedoc.document.attribute.transform.MultiSource;
import com.wisii.wisedoc.document.attribute.transform.OutInterface;
import com.wisii.wisedoc.document.attribute.transform.SelectColumnInFO;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.document.attribute.transform.SwingDS;
import com.wisii.wisedoc.document.attribute.transform.TransformTable;
import com.wisii.wisedoc.document.attribute.transform.TreeSource;
import com.wisii.wisedoc.document.attribute.transform.Column.ColumnType;
import com.wisii.wisedoc.swing.SelectColumnInFOesTable;
import com.wisii.wisedoc.swing.SelectColumnInFOesTableModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.dialog.SetTransformTableDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-8-26
 */
public class SelectDataSettingPanel extends JPanel
{

	private WiseCombobox dstypebox = new WiseCombobox(new String[]
 {
			RibbonUIText.DATASOURCE_SCHEMA,
			RibbonUIText.DATASOURCE_INNER,
			RibbonUIText.DATASOURCE_INCLUDE,
			RibbonUIText.DATASOURCE_OUTFUNCTION,
			RibbonUIText.DATASOURCE_SWINGDS });

	JCheckBox issearchablebox = new JCheckBox(RibbonUIText.SELECT_ISSEARCHABLE);

	WiseCombobox sortbox = new WiseCombobox(new String[]
	{ RibbonUIText.SELECT_SORT_C, RibbonUIText.SELECT_SORT_P,
			RibbonUIText.SELECT_SORT_N });

	SelectColumnInFOesTable table;

	private DataSource datasource;

	private SelectInfo selectinfo;
	private JButton dssetbutton = new JButton(RibbonUIText.SELECT_DATASOURCE);

	SelectDataSettingPanel(final AbstractWisedocDialog parent,
			SelectInfo selectinfo)
	{
		super(new BorderLayout());
		dstypebox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				datasource = null;
				table.reInitColumnInfoes(null);
				dssetbutton.setEnabled(dstypebox.getSelectedIndex() != 0);
			}
		});

		// dstypebox.addItemListener(new ItemListener()
		// {
		//
		// public void itemStateChanged(ItemEvent event)
		// {
		// datasource = null;
		// table.reInitColumnInfoes(null);
		// }
		// });
		dssetbutton.setEnabled(false);
		if (selectinfo != null)
		{
			this.selectinfo = new SelectInfo(selectinfo.getColumninfos(),
					selectinfo.getDatasource(), selectinfo.getDatasourcetype(),
					selectinfo.isSearchable(), selectinfo.getSorttype());
			datasource = selectinfo.getDatasource();
			int type = selectinfo.getDatasourcetype();
			if (type == SelectInfo.TRANSFORMTABLE)
			{
				dstypebox.initIndex(1);
				dssetbutton.setEnabled(true);
			} else if (type == SelectInfo.IMPORTSOURCE)
			{
				dstypebox.initIndex(2);
				dssetbutton.setEnabled(true);
			} else if (type == SelectInfo.EXTERNALFUNCTION)
			{
				dstypebox.initIndex(3);
				dssetbutton.setEnabled(true);
			}
			 else if (type == SelectInfo.SWINGDS)
				{
					dstypebox.initIndex(4);
					dssetbutton.setEnabled(true);
				}
		}
		JPanel commonpanel = new JPanel(new GridLayout(3, 1));
		JLabel dstypelabel = new JLabel(RibbonUIText.DATASOURCE_TYPE);
		dssetbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectindex = dstypebox.getSelectedIndex();
				if (selectindex == 1)
				{
					SetTransformTableDialog setdialog = new SetTransformTableDialog(
							parent, (TransformTable) datasource);
					DialogResult result = setdialog.showDialog();
					if (result == DialogResult.OK)
					{
						TransformTable ndatasource = setdialog.getDataSource();
						if (ndatasource.equals(datasource))
						{
							return;
						} else
						{
							datasource = ndatasource;
							reInitColumn();
						}
					}
				} else if (selectindex == 2)
				{
					DataSourceSettingDialog setdialog = new DataSourceSettingDialog(
							parent, datasource);
					DialogResult result = setdialog.showDialog();
					if (result == DialogResult.OK)
					{
						DataSource ndatasource = setdialog.getDataSource();
						if (ndatasource.equals(datasource))
						{
							return;
						} else
						{
							datasource = ndatasource;
							reInitColumn();
						}
					}
				} else if (selectindex == 3)
				{
					OutInterfaceDialog setdialog = new OutInterfaceDialog(
							parent, (OutInterface) datasource);
					DialogResult result = setdialog.showDialog();
					if (result == DialogResult.OK)
					{
						DataSource ndatasource = setdialog.getDataSource();
						if (ndatasource.equals(datasource))
						{
							return;
						} else
						{
							datasource = ndatasource;
							reInitColumn();
						}
					}
				}
				else if (selectindex == 4)
				{
					SwingDSDialog setdialog = new SwingDSDialog(
							parent, (SwingDS) datasource);
					DialogResult result = setdialog.showDialog();
					if (result == DialogResult.OK)
					{
						DataSource ndatasource = setdialog.getDataSource();
						if (ndatasource.equals(datasource))
						{
							return;
						} else
						{
							datasource = ndatasource;
							reInitColumn();
						}
					}
				}

			}

		});
		if (selectinfo != null)
		{
			issearchablebox.setSelected(selectinfo.isSearchable());
		}
		JPanel datasourcepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		datasourcepanel.add(dstypelabel);
		datasourcepanel.add(dstypebox);
		datasourcepanel.add(dssetbutton);
		JLabel sortlabel = new JLabel(RibbonUIText.SELECT_SORT);
		JPanel sortpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sortpanel.add(sortlabel);
		sortpanel.add(sortbox);
		commonpanel.add(datasourcepanel);
		commonpanel.add(sortpanel);
		commonpanel.add(issearchablebox);
		add(BorderLayout.NORTH, commonpanel);
		JPanel viewsetpanel = new JPanel(new BorderLayout());
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton upperbutton = new JButton("上移");
		JButton downbutton = new JButton("下移");
		buttonpanel.add(upperbutton);
		buttonpanel.add(downbutton);
		viewsetpanel.add(BorderLayout.NORTH, buttonpanel);
		List<SelectColumnInFO> infos = null;
		if (this.selectinfo != null)
		{
			infos = this.selectinfo.getColumninfos();
			int sorttype = this.selectinfo.getSorttype();
			if (sorttype == Constants.EN_SORT_P)
			{
				sortbox.initIndex(1);
			} else if (sorttype == Constants.EN_SORT_N)
			{
				sortbox.initIndex(2);
			}
		}
		table = new SelectColumnInFOesTable(infos);
		JScrollPane datasetjs = new JScrollPane(table);
		viewsetpanel.add(BorderLayout.CENTER, datasetjs);
		add(BorderLayout.CENTER, viewsetpanel);
	}

	public boolean isAllSettingRight()
	{
		int typeindex = dstypebox.getSelectedIndex();
		if ((typeindex == 1 || typeindex == 2 || typeindex == 3)
				&& datasource == null)
		{
			return false;
		}
		if (typeindex != 0)
		{
			if (table == null)
			{
				return false;
			} else
			{
				if (table.getModel() != null)
				{
					SelectColumnInFOesTableModel model = (SelectColumnInFOesTableModel) table
							.getModel();
					if (!model.HaveOptionPath())
					{
						return false;
					}
				} else
				{
					return false;
				}
			}
		}
		return true;
	}

	public SelectInfo getSelectInFo()
	{
		boolean issearchable = issearchablebox.isSelected();
		int sortindex = sortbox.getSelectedIndex();
		int sorttype = Constants.EN_SORT_C;
		if (sortindex == 1)
		{
			sorttype = Constants.EN_SORT_P;
		} else if (sortindex == 2)
		{
			sorttype = Constants.EN_SORT_N;
		}
		int typeindex = dstypebox.getSelectedIndex();

		if (typeindex == 0)
		{
			return new SelectInfo(null, null, SelectInfo.SCHEMA, issearchable,
					sorttype);
		} else if (typeindex == 1)
		{
			return new SelectInfo(table.getSelectColumnInFOs(), datasource,
					SelectInfo.TRANSFORMTABLE, issearchable, sorttype);
		} else if (typeindex == 2)
		{
			return new SelectInfo(table.getSelectColumnInFOs(), datasource,
					SelectInfo.IMPORTSOURCE, issearchable, sorttype);
		} else if (typeindex == 3)
		{
			return new SelectInfo(table.getSelectColumnInFOs(), datasource,
					SelectInfo.EXTERNALFUNCTION, issearchable, sorttype);
		}
		else if (typeindex == 4)
		{
			return new SelectInfo(table.getSelectColumnInFOs(), datasource,
					SelectInfo.SWINGDS, issearchable, sorttype);
		} 
		else
		{
			return null;
		}

	}

	private void reInitColumn()
	{
		List<SelectColumnInFO> cifoes = new ArrayList<SelectColumnInFO>();
		if (datasource != null)
		{
			if (datasource instanceof MultiSource)
			{
				MultiSource ms = (MultiSource) datasource;
				int bond = ms.getBond();
				List<FileSource> fses = ms.getFilesources();
				if (bond == Constants.EN_DATASOURCE_VERT)
				{
					FileSource fs = fses.get(0);
					List<String> columnsnames = fs.getColumninfo();
					int size = columnsnames.size();
					for (int i = 0; i < size; i++)
					{
						String columnsname = columnsnames.get(i);
						cifoes.add(new SelectColumnInFO(i, false, null, -1, -1,
								"列" + i, ColumnType.Varchar));
					}
				} else
				{
					int j = 0;
					for (FileSource fs : fses)
					{
						List<String> columnsnames = fs.getColumninfo();
						int size = columnsnames.size();
						for (int i = 0; i < size; i++)
						{
							String columnsname = columnsnames.get(i);
							cifoes.add(new SelectColumnInFO(j, false, null, -1,
									-1, "列" + i, ColumnType.Varchar));
							j++;
						}
					}
				}
			} else if (datasource instanceof TreeSource)
			{
				TreeSource ts = (TreeSource) datasource;
				List<String> columnsnames = ts.getColumninfo();
				int size = columnsnames.size();
				for (int i = 0; i < size; i++)
				{
					String columnsname = columnsnames.get(i);
					cifoes.add(new SelectColumnInFO(i, false, null, -1, -1, "列"
							+ i, ColumnType.Varchar));
				}
			} else if (datasource instanceof OutInterface)
			{
				OutInterface ts = (OutInterface) datasource;
				int size = ts.getCloumncount();
				for (int i = 0; i < size; i++)
				{
					cifoes.add(new SelectColumnInFO(i, false, null, -1, -1, "列"
							+ i, ColumnType.Varchar));
				}
			}
			 else if (datasource instanceof SwingDS)
				{
				 SwingDS ts = (SwingDS) datasource;
					int size = ts.getColumncount();
					for (int i = 0; i < size; i++)
					{
						cifoes.add(new SelectColumnInFO(i, false, null, -1, -1, "列"
								+ i, ColumnType.Varchar));
					}
				}
			else
			{

				TransformTable ts = (TransformTable) datasource;
				List<List<String>> datas = ts.getDatas();
				List<String> columnsnames = datas.get(0);
				int size = columnsnames.size();
				for (int i = 0; i < size; i++)
				{
					String columnsname = columnsnames.get(i);
					cifoes.add(new SelectColumnInFO(i, false, null, -1, -1, "列"
							+ i, ColumnType.Varchar));
				}

			}
		}
		table.setEnabled(true);
		table.reInitColumnInfoes(cifoes);
	}
}
