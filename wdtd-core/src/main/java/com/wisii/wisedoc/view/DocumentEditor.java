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
 * @DocumentEditor.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.listener.DocumentCaretListener;
import com.wisii.wisedoc.view.mousehandler.MouseHandler;
import com.wisii.wisedoc.view.select.SelectionModel;

/**
 * 类功能描述：文档编辑器接口
 * 
 * 作者：zhangqiang 创建日期：2008-4-18
 */
public interface DocumentEditor
{
	/**
	 * 
	 * 由界面上的像素点转换成内部文档位置
	 * 
	 * @param point
	 *            ：界面位置
	 * @return 文档位置
	 * @exception
	 */
	DocumentPosition pointtodocpos(Point point);

	/**
	 * 
	 * 根据文档位置
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	Point docpostopoint(DocumentPosition pos);

	/**
	 * 
	 * 界面像素点大小转换为内部单位大小的矩形
	 * 
	 * @param rect
	 *            :以界面像素点为单位的大小
	 * @return 以内部单位为大小矩形
	 * @exception
	 */
	Rectangle2D fromScreen(Rectangle2D rect);
	/**
	 * 
	 * 内部矩形大小转换成界面矩形大小，area为矩形区域所显示的对象
	 *
	 * @param      
	 * @return      
	 * @exception  
	 */
	Rectangle   toScreen(Rectangle2D rect,Area area);

	/**
	 * 
	 * 设置文档对象
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setDocument(Document doc);

	/*
	 * 
	 * 设置选中操作处理器
	 * 
	 * @param:selecthandler:要设置成选中操作处理器
	 * 
	 * @return
	 * 
	 * @exception
	 */
	void setMouseHandler(MouseHandler mousehandler);

	/*
	 * 
	 * 获得选中操作处理器
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	MouseHandler getMouseHandler();

	/**
	 * 
	 * 设置选中模型类
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	void setSelecctionModel(SelectionModel selmodel);

	// 获得选中模型类
	SelectionModel getSelectionModel();

	/**
	 * 
	 * 设置光标位置
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setCaretPosition(DocumentPosition caretpos);

	/**
	 * 
	 *获得光标位置
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	DocumentPosition getCaretPosition();
	/**
	 * 
	 * 根据坐标点返回坐标点对应的area
	 *
	 * @param   point：坐标点  
	 * @return   Area:坐标点所在的Area  
	 * @exception   
	 */
	Area getAreaofPoint(Point point);
	/**
	 * 
	 * 添加光标改变事件监听器
	 * @param
	 * @return
	 * @exception
	 */
	void addDocumentCaretListener(DocumentCaretListener listener);

	/**
	 * 
	 * 删除光标改变事件监听器
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void removeDocumentCaretListener(DocumentCaretListener listener);
}
