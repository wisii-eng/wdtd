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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.w3c.dom.Element;

import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.banding.io.StructureReaderUtil;
import com.wisii.wisedoc.exception.DataStructureException;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

@SuppressWarnings("serial")
public class EditXmlNodeDialog extends AbstractWisedocDialog
{

	EditXmlNodeDialog dia;

	Element thiselement;

	String resultcode = null;

	public EditXmlNodeDialog(String filename) throws FileNotFoundException,
			DataStructureException
	{
		super();
		this.setTitle("选择根元素");
		dia = this;
		this.setSize(new Dimension(600, 500));
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(600, 500));
		JScrollPane treepanel = new JScrollPane();
		treepanel.setSize(new Dimension(600, 420));
		treepanel.setBounds(0, 0, 600, 420);

		DataStructureTreeModel model = StructureReaderUtil
				.readStructure(filename);
		JTree tree = new JTree(model);
		tree.getSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener()
				{

					public void valueChanged(TreeSelectionEvent e)
					{
						TreePath path = e.getPath();
						Object[] paths = path.getPath();
						if (paths != null && paths.length > 0)
						{
							for (int i = 0; i < paths.length; i++)
							{
								if (paths[i] != null)
								{
									resultcode = resultcode + "/";
								}
								String current = paths[i].toString();
								current = current.replaceAll("\\[", "");
								current = current.replaceAll("\\]", "");
								resultcode = resultcode + current;
							}
						}
						if (resultcode.startsWith("null"))
						{
							resultcode = resultcode.replaceFirst("null", "");
						}
					}
				});
		treepanel.setViewportView(tree);
		JButton ok = new JButton("确定");
		JButton cancel = new JButton("取消");

		panel.add(treepanel);
		ok.setBounds(300, 430, 80, 25);

		cancel.setBounds(400, 430, 80, 25);
		panel.add(ok);

		panel.add(cancel);
		ok.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.OK;
				EditXmlNodeDialog.this.dispose();
			}
		});
		cancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				EditXmlNodeDialog.this.dispose();
			}
		});

		this.add(panel);
		centerOnScreen(this);
	}

	public static void centerOnScreen(final Component aComponent)
	{
		if (aComponent == null)
		{
			return;
		}
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		final Dimension compSize = aComponent.getSize();

		compSize.width = Math.min(screenSize.width, compSize.width);
		compSize.height = Math.min(screenSize.height, compSize.height);

		aComponent.setSize(compSize);
		aComponent.setLocation((screenSize.width - compSize.width) / 2,
				(screenSize.height - compSize.height) / 2);
	}

	public static void main(String[] args) throws FileNotFoundException,
			DataStructureException
	{
		EditXmlNodeDialog dia = new EditXmlNodeDialog("c:/a.xml");
		dia.setVisible(true);
		System.out.println(dia.resultcode);
	}

	public DialogResult showDialog()
	{
		return result;
	}

	public String getSelectElement()
	{
		return resultcode;
	}

	public void setResult(String result)
	{
		this.resultcode = result;
	}
}
