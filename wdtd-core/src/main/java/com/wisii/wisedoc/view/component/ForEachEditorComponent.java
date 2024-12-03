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
 * @ForEachEditorComponent.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.component;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;
/**
 * 
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-5
 */
public class ForEachEditorComponent extends TextAndButtonComponent
{
	private JPopupMenu pop;
	WiseDocTree tree;
	JScrollPane scrpanel;
	private boolean isBindNode = Boolean.FALSE;
	private boolean isChanged = Boolean.FALSE;
	public ForEachEditorComponent(boolean txteditable)
	{
		txtComp.setEditable(txteditable);
	}

	public ForEachEditorComponent()
	{
		btnComp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				Document doc = SystemManager.getCurruentDocument();
				if (doc != null && doc.getDataStructure() != null)
				{
					tree = new WiseDocTree();
					tree.setCellRenderer(new DataStructureCellRender());
					scrpanel = new JScrollPane();
					scrpanel.getViewport().setView(tree);
					pop = new JPopupMenu();
					pop.add(scrpanel);
					int width = ForEachEditorComponent.this.getWidth();
					if(width<260)
					{
						width=260;
					}
					pop.setPreferredSize(new Dimension(
							width, 260));
					tree.setModel(doc.getDataStructure());
                    Object oldvalue = getValue();
                    if(oldvalue!=null&&oldvalue instanceof BindNode)
                    {
                    	tree.setSelectedNode((BindNode)oldvalue);
                    }
					pop.show(txtComp, 0, 0);
					tree.addTreeSelectionListener(new TreeSelectionListener()
					{
						public void valueChanged(TreeSelectionEvent e)
						{
							TreePath path = tree.getSelectionPath();
							if (path != null)
							{
								BindNode node = (BindNode) tree
										.getSelectionPath()
										.getLastPathComponent();
								isBindNode = Boolean.TRUE;
								isChanged = Boolean.FALSE;
								setValue(node);
								pop.setVisible(false);
								pop = null;
							}
						}
					});
				}

			}
		});
		init();
	}
	private void init(){
		txtComp.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				isChanged = Boolean.TRUE & !isBindNode;
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				isChanged = Boolean.TRUE & !isBindNode;
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				isChanged = Boolean.TRUE & !isBindNode;
			}
		});
	}
	@Override
	public void setValue(Object value) {
		if(value instanceof BindNode){
			isBindNode = Boolean.TRUE;
		}else{
			isBindNode = Boolean.FALSE;
		}
		super.setValue(value);
	}
	
	@Override
	public Object getValue() {
		if(isChanged){
			return txtComp.getText();
		}
		return super.getValue();
	}
}
