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
 * @RepeatContentDocment.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

/**
 * 类功能描述：
 * 作者：wisii
 * 创建日期：2015-2-2
 */
public class FormulaContentDocment extends DefaultStyledDocument
{
	 static String VARATT = "var";
	 static String OPERATT = "oper";
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public FormulaContentDocment(List contents)
	{
		init(contents);
	}
	private void init(List contents)
	{
		if (contents != null)
		{
			int offs = 0;
			SimpleAttributeSet varattset = new SimpleAttributeSet();
			varattset.addAttribute("isvar", true);
			try
			{
				for (Object content : contents)
				{
					String text = null;
					SimpleAttributeSet attributeset = null;
					if (content instanceof String)
					{
						text = (String) content;
					}
					else if (content instanceof FormulaVar)
					{
						text = ((FormulaVar) content).toString();
						attributeset = new SimpleAttributeSet();
						attributeset.addAttribute(VARATT, content);
					}
					else
					{
						if(content !=null){
							text = content.toString();
						}else{
							text = "";
						}
					}
					insertString(offs, text, attributeset);
					offs += text.length();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * 选中部分时，删除整个节点
	 */
	@Override
	public void remove(int offs, int len) throws BadLocationException
	{
		AttributeSet attribute = getCharacterElement(offs).getAttributes();
		int newoffs = offs;
		int endoffs = offs + len;
		if (attribute != null)
		{
			Object node = attribute.getAttribute(VARATT);
			if (node != null)
			{
				for (int i = offs - 1; i >= 0; i--)
				{
					AttributeSet nattribute = getCharacterElement(i)
							.getAttributes();
					if (nattribute == null)
					{
						break;
					}
					Object nnode = nattribute.getAttribute(VARATT);
					if (!node.equals(nnode))
					{
						break;
					}
					newoffs = i;
				}
			}
		}
		attribute = getCharacterElement(endoffs).getAttributes();
		if (attribute != null)
		{
			Object node = attribute.getAttribute(VARATT);
			if (node != null)
			{
				int doclen = getLength();
				for (int i = endoffs - 2; i < doclen; i++)
				{
					AttributeSet nattribute = getCharacterElement(i)
							.getAttributes();
					if (nattribute == null)
					{
						break;
					}
					Object nnode = nattribute.getAttribute(VARATT);
					if (!node.equals(nnode))
					{
						break;
					}
					endoffs = i;
				}
			}
		}
		super.remove(newoffs, endoffs - newoffs);
	}

	public void insert(FormulaVar node, int offs, int len) {
		if (node == null) {
			return;
		}
		try {
			if (len > 0) {
				this.remove(offs, len);
			}
			SimpleAttributeSet attributeset = new SimpleAttributeSet();
			attributeset.addAttribute(VARATT, node);
			this.insertString(offs, node.toString(), attributeset);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insert(String node, int offs, int len) {
		if (node == null) {
			return;
		}
		try {
			if (len > 0) {
				this.remove(offs, len);
			}
			SimpleAttributeSet attributeset = new SimpleAttributeSet();
			attributeset.addAttribute(OPERATT, node);
			this.insertString(offs, node.toString(), attributeset);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(List contents,int offs)
	{
		if (contents != null)
		{
			SimpleAttributeSet varattset = new SimpleAttributeSet();
			varattset.addAttribute("isvar", true);
			try
			{
				for (Object content : contents)
				{
					String text = null;
					SimpleAttributeSet attributeset = null;
					if (content instanceof String)
					{
						text = (String) content;
					}
					else if (content instanceof FormulaVar)
					{
						text = ((FormulaVar) content).toString();
						attributeset = new SimpleAttributeSet();
						attributeset.addAttribute(VARATT, content);
					}
					else
					{
						if(content !=null){
							text = content.toString();
						}else{
							text = "";
						}
					}
					insertString(offs, text, attributeset);
					offs += text.length();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public List getContents()
	{
		List contens = new ArrayList();
		int len = getLength();
		Object prenode = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			for (int i = 0; i < len; i++)
			{
				AttributeSet att = getCharacterElement(i).getAttributes();
				Object thisnode = (att == null ? null : att
						.getAttribute(VARATT));
				if (i == 0)
				{
					if (thisnode instanceof FormulaVar)
					{
						contens.add(thisnode);
					}
					else
					{
						sb.append(getText(i, 1));
					}
				}
				else
				{
					if (prenode == thisnode)
					{
						if (!(thisnode instanceof FormulaVar))
						{
							sb.append(getText(i, 1));
						}
					}
					else
					{
						if (thisnode instanceof FormulaVar)
						{
							if (!(prenode instanceof FormulaVar))
							{
								contens.add(sb.toString());
							}
							contens.add(thisnode);
							sb = new StringBuffer();
						}
						else
						{
							sb = new StringBuffer();
							sb.append(getText(i, 1));
						}
					}
				}
				prenode = thisnode;
			}
		}
		catch (Exception e)
		{
		}
		return contens;
	}
	
}
