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
 * @SchemaTreeModel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.schema.model;
import javax.swing.tree.DefaultTreeModel;
import com.sun.org.apache.xerces.internal.xs.*;
/**
 * 类功能描述：
 * 解析schema得到的结构树
 * 作者：wisii
 * 创建日期：2013-3-5
 */
public class SchemaTreeModel extends DefaultTreeModel
{

	private XSModel xsmodel;

	private DefaultSchemaNode root;

	public SchemaTreeModel(XSModel xsmodel, DefaultSchemaNode root)
	{
		super(root);
		this.xsmodel = xsmodel;
		this.root = root;
	}
	@Override
	public DefaultSchemaNode getRoot()
	{
		return root;
	}
	public XSModel getXsmodel()
	{
		return xsmodel;
	}

}
