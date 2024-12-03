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
 * @Element.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.document.attribute.Attributes;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-8-14
 */
public interface Element extends Cloneable, TreeNode
{
	
	/**
	 * 
	 * 深克隆方法，要求实现该接口的对象必须深克隆该对象，从而提供对复制，粘贴的支持
	 * 
	 * @param
	 * @return 克隆后的对象
	 * @exception
	 */
	Element clone();
	/**
	 * 
	 * 返回对象文本，之所以定义这个接口，是为了使得实现类能够覆写Object默认的toString方法，以使得他们能够返回有意义的子符串
	 *
	 * @param     
	 * @return     
	 * @exception 
	 */
	String toString();
	/**
	 * 
	 * 获得对象的所有属性，注意，为了防止调用该接口的对象获得该属性之后进行修改，
	 * 这个地方返回的最好的保存的属性的副本。
	 *
	 * @param      
	 * @return      
	 * @exception   
	 */
	Attributes getAttributes();
	/**
	 * 
	 * 获得指定关键字的属性
	 *
	 * @param：指定的属性关键字，key为AttributeConstants类中定义的静态变量      
	 * @return     
	 * @exception  
	 */
	Object getAttribute(int key);
	/**
	 * 
	 * 设置指定属性的属性值
	 *
	 * @param      
	 * @return     
	 * @exception   
	 */
	void setAttribute(int key,Object value);
	/**
	 * 
	 * 设置多个属性
	 *
	 * @param   atts:要设置的属性值
	 *           isreplace：true表示用新的属性替换掉原来的所有属性
	 *                      false：如果 atts中没有对新属性的设置，则保留原属性值 
	 * @return     
	 * @exception  
	 */
	void setAttributes(Map<Integer, Object> atts,boolean isreplace);
	/**
	 * 
	 * 删除指定属性
	 *
	 * @param      key：属性关键字
	 * @return     
	 * @exception  
	 */
    void removeAttributes(int key);
    /**
     * 
     * 在指定位置插入多个子节点
     *
     * @param      children：要插入的子元素集合
     *              childIndex：指定节点位置
     * @return     
     * @exception  
     */
    public void insert(List<CellElement> children, int childIndex);
    /**
     * 
     * 删除指定多个子对象
     *
     * @param    children：要删除的子对象集合
     * @return     
     * @exception  
     */
    public void removeChildren(List<CellElement> children);
    public Element getParent();
    /**
     * 
     * 根据文档位置返回该文档位置在该对象的子对象的偏移量
     * 即该文档位置在该对象的第几个子对象上
     *
     * @param      pos：文档位置
     * @return     负数表示文档位置不在该对象上，其他表示文档位置所在的第几个子对象位置
     * @exception   {说明在某情况下,将发生什么异常}
     */
    int getoffsetofPos(DocumentPosition pos);
    public int getNameId();
    public FOUserAgent getUserAgent();
    public List<CellElement> getAllChildren();
}
