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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.wisii.wisedoc.area.Area;

/**
 * 类功能描述：元素接口，文档元素包括段落，文本，图片，列表等
 * 该接口继承TreeNode接口，采用树方式的接口访问方式
 * 
 * 作者：zhangqiang 创建日期：2008-4-15
 */
public interface CellElement extends Element
{

	
    /**
     * 
     * 添加节点
     *
     * @param      newChild：要添加的节点
     * @return     
     * @exception  
     */
    public void add(CellElement newChild);
    /**
     * 
     * 在指定位置插入子节点
     *
     * @param      newChild：要插入的节点
     *              childIndex：指定节点位置
     * @return     
     * @exception  
     */
    public void insert(CellElement newChild, int childIndex);
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
     * 删除掉指定位置的子对象
     *
     * @param     childIndex：要删除的子对象位置
     * @return     
     * @exception   
     */
    public void remove(int childIndex);
 
    /**
     * 
     * 删除指定子对象
     *
     * @param    child：要删除的子对象
     * @return     
     * @exception  
     */
    public void remove(CellElement child);
    /**
     * 
     * 删除所有子对象
     *
     * @param     
     * @return     
     * @exception   
     */
    public void removeAllChildren();
    /**
     * 
     * 删除指定多个子对象
     *
     * @param    children：要删除的子对象集合
     * @return     
     * @exception  
     */
    public void removeChildren(List<CellElement> children);
    /**
     * 
     * 设置对象的父对象
     *
     * @param      
     * @return      
     * @exception   
     */
    public void setParent(Element newParent);
	/**
	 * 
	 * 获得所有子对象
	 *
	 * @param      
	 * @return     
	 * @exception   
	 */
	Iterator<CellElement> getChildren();
	   /**
     * 
     *获得指定位置及其之后的所有子对象，
     *
     * @param     childIndex：子对象位置
     * @return    返回指定位置之后的所有子对象，包含指定位置的对象 
     * @exception   
     */
    public List<CellElement> getChildren(int childIndex);
    public CellElement getChildAt(int childIndex);
    /**
     * 
     *获得指定位置之间的元素，
     *
     * @param     childIndex：开始位置，包含本位置元素
     *             endindex：结束位置，不包含本位置元素
     * @return    返回指定位置之后的所有子对象，不包含指定位置的对象 
     * @exception   
     */
    public List<CellElement> getChildren(int childIndex,int endindex);
    public ListIterator getChildNodes();
    public String getId();
    /* 【添加：START 】 by 李晓光 2008-11-5*/
    public Area getArea();
    /* 【添加：END 】 by 李晓光 2008-11-5*/
    /**
     * 
     *初始化排版所需要的属性，主要是指Common类型的属性（如CommonBorderPaddingBackground），
     *这类属性需要内部保存的子属性组合而成，因此在排版前调用
     *
     * @param   
     *             
     * @return   
     * @exception   
     */
    public void initFOProperty();
}
