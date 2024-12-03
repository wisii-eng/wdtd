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
 * @XSDReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding.io;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import com.sun.org.apache.xerces.internal.xs.*;
import com.wisii.schema.SchemaTreeBuilder;
import com.wisii.schema.model.DefaultSchemaNode;
import com.wisii.schema.model.SchemaTreeModel;
import com.wisii.schema.model.XSObjectRef;
import com.wisii.schema.util.SchemaLoader;
import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.banding.SchemaRefNode;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
/**
 * 根据XSD文件生成文档结构类
 * 
 * 作者：zhangqiang 创建日期：2007-6-11
 */
public class XSDReader implements StructureReader
{
	private Map<DefaultSchemaNode, DefaultBindNode> nodemap=new HashMap<DefaultSchemaNode, DefaultBindNode>();
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.StructureReader#readStructure(java.io.InputStream)
	 */
	public DataStructureTreeModel readStructure(String filepath)
	{
		File file = new File(filepath);
		XSModel model = SchemaLoader.LoadSchema(file);
		SchemaTreeBuilder builder = new SchemaTreeBuilder();
		List<List<String>> roots = builder.getAllRootElements(model);
		if (roots == null || roots.size() == 0)
		{
			return null;
		}
		String namespaceselect = null;
		String rootname = null;
		if (roots.size() == 1)
		{
			List<String> rootset = roots.get(0);
			namespaceselect = rootset.get(0);
			rootname = rootset.get(1);
		} else
		{
			RootSelectDialog dialog = new RootSelectDialog(roots);
			if (dialog.showDialog() == DialogResult.OK)
			{
				ElementItem item = dialog.getSelectRoot();
				namespaceselect = item.namespace;
				rootname = item.name;
			} else
			{
				return null;
			}
		}
		SchemaTreeModel treemodel = builder.buildSchemaTree(model,
				namespaceselect, rootname);
		if (treemodel == null)
		{
			return null;
		}

		List<NameSpace> usedns = new ArrayList<NameSpace>();
		BindNode root = getRootNode(treemodel.getRoot(), usedns);
		return new DataStructureTreeModel(root, false, usedns);
	}

	private BindNode getRootNode(DefaultSchemaNode root,List<NameSpace> usedns)
	{
	
		return getBindNode(root,null,usedns);

	}
	private BindNode getBindNode(DefaultSchemaNode schemanode,BindNode parent,List<NameSpace> usedns)
	{
		if (schemanode == null)
		{
			return null;
		}
		XSObjectRef ref=schemanode.getXSObjectRef();
		
		XSObject xsobject=ref.getXSNode();
		BindNode node=null;
		 if(ref.isElement())
		{
			String name = xsobject.getName();
			String ns=xsobject.getNamespace();
			String pre=getPreOfNameSpace(ns, usedns);
			if(pre!=null)
			{
				name=pre+":"+name;
			}
			if(schemanode.isRefNode())
			{
				DefaultBindNode refnode = nodemap.get(schemanode.getRefNode());
				node=new SchemaRefNode(parent,BindNode.STRING,BindNode.UNLIMT,name, refnode);
			}
			else{
			node=new DefaultBindNode(parent,BindNode.STRING,BindNode.UNLIMT,name);
			nodemap.put(schemanode, (DefaultBindNode)node);
			}
		}
		else if(ref.isAttribute())
		{
			String name = xsobject.getName();
			String ns=xsobject.getNamespace();
			String pre=getPreOfNameSpace(ns, usedns);
			if(pre!=null)
			{
				name=pre+":"+name;
			}
			node=new AttributeBindNode(parent,BindNode.STRING,BindNode.UNLIMT,name);
		}
		if(parent!=null&&node!=null)
		{
			parent.addChild(node);
		}
		if(node!=null)
		{
			parent=node;
		}
		int count=schemanode.getChildCount();
		for(int i=0;i<count;i++)
		{
			getBindNode((DefaultSchemaNode)schemanode.getChildAt(i),parent,usedns);
		}
		return node;
	}
	private String getPreOfNameSpace(String ns,List<NameSpace> usedns)
	{
		if(ns==null)
		{
			return null;
		}
		for(NameSpace namespace:usedns)
		{
			if(namespace.getAttributeValue().equals(ns))
			{
				return namespace.attribute.substring(6);
			}
		}
		String pre = "ns"+usedns.size();
		usedns.add(new NameSpace("xmlns:"+pre, ns));
		return pre;
	}
	private static class RootSelectDialog extends AbstractWisedocDialog
	{

		private JButton okbutton = new JButton(
				getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

		private JButton cancelbutton = new JButton(
				getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));
		private JComboBox box;
		public RootSelectDialog(List<List<String>> roots)
		{
			super();
			this.setTitle(RibbonUIText.ABOUT);
			this.setSize(400, 200);
			this.setLayout(new BorderLayout());
			JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
			setOkButton(okbutton);
			setCancelButton(cancelbutton);
			buttonpanel.add(okbutton);
			buttonpanel.add(cancelbutton);
			add(buttonpanel, BorderLayout.SOUTH);
			JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			ElementItem[] items = new ElementItem[roots.size()];
			int i = 0;
			for (List<String> root : roots)
			{
				items[i++] = new ElementItem(root.get(0), root.get(1));
			}
			box = new JComboBox(items);
			contentPanel.add(box);
			add(contentPanel, BorderLayout.CENTER);
			initAction();
		}

		private void initAction() {
			okbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
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
		public ElementItem getSelectRoot() {
			return (ElementItem)box.getSelectedItem();
		}
	}
	private static class ElementItem
	{
		private String namespace;
		private String name;
		public ElementItem(String namespace, String name)
		{
			this.namespace = namespace;
			this.name = name;
		}
		@Override
		public String toString()
		{
			if (namespace == null)
			{
				return name;
			} else
			{
				return name + "-" + namespace;
			}
		}
		
	}
}
