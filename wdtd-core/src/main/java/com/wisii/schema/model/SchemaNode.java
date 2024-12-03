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
package com.wisii.schema.model;

import java.util.List;

import javax.swing.tree.TreeNode;
/**
 * 
 * 类功能描述：
 *
 * 作者：wisii
 * 创建日期：2013-3-5
 */
public interface SchemaNode extends TreeNode
{
    SchemaNode getParent();
    void addChild(SchemaNode child);
    void addChildren(List<SchemaNode> children);
    void removeChild(SchemaNode child);
    void removeChildren(List<SchemaNode> children);
    void setParent(SchemaNode parent);
    XSObjectRef getXSObjectRef();
    SchemaNode getChildAt(int i);
    SchemaNode Clone();
    //是否为带有元素子节点的节点
    boolean hasElementChildren();
    //是否为元素
    boolean isElement();
    //是否为属性节点
    boolean isAttribute();
    boolean isRefNode();
	DefaultSchemaNode getRefNode();
	String getNodeName();
	String getNameSpace();
}
