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
 * @AttributeChange.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.BarCodeInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.attribute.Attributes;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-15
 */
public class AttributeChange implements DocumentChange {

	Map<Integer, Object> _newvalue;
	List<Map<Integer, Object>> _oldvalues;

	// 要操作的元素
	List<Element> _editelements;

	// 是否替换掉原来的属性
	boolean _isreplace;

	/**
	 * 
	 * 传入的editelements需要是属性还没改变前的元素
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public AttributeChange(List<Element> editelements,
			Map<Integer, Object> newvalue, boolean isreplace) {
		_editelements = editelements;
		_oldvalues = new ArrayList<Map<Integer, Object>>();
		if (_editelements != null && !_editelements.isEmpty()) {
			int size = _editelements.size();
			for (int i = 0; i < size; i++) {
				Element editelement =  _editelements.get(i);
				Attributes att =editelement.getAttributes();
				Map<Integer, Object> oldvalue = null;
				if (att != null) {
//		            如果是替换方式，则取得原属性为
					if(isreplace){
						oldvalue = att.getAttributes();
					}
//					否则或的新属性中对应的原属性
					else
					{
						if(newvalue != null&&!newvalue.isEmpty())
						{
							oldvalue = new HashMap<Integer, Object>();
							Iterator<Integer> keyit = newvalue.keySet().iterator();
//							获得所有的单项属性
							while(keyit.hasNext())
							{
								Integer key = keyit.next();
								//如果是图片或统计图，则其原来的属性值需要取对应的PR_BARCODE_CONTENT，PR_SRC，这样才能正确的Undo或redo
								if (key == Constants.PR_INLINE_CONTENT&&(editelement instanceof BarCodeInline||editelement instanceof ImageInline))
								{
									if(editelement instanceof BarCodeInline)
									{
										oldvalue.put(Constants.PR_INLINE_CONTENT, ((Element)editelement.getChildAt(0)).getAttribute(Constants.PR_BARCODE_CONTENT));
									}
									else
									{
										oldvalue.put(Constants.PR_INLINE_CONTENT, ((Element)editelement.getChildAt(0)).getAttribute(Constants.PR_SRC));
									}
									
								} else
								{
									oldvalue.put(key, att.getAttribute(key));
								}
							}
						}
					}
				}
				_oldvalues.add(oldvalue);
			}
		}
		if(newvalue!=null){
		_newvalue = new HashMap<Integer, Object>(newvalue);
		}
		else
		{
			_newvalue = null;
		}
		_isreplace = isreplace;
	}

	/**
	 * @返回 _editelements变量的值
	 */
	public List<Element> getEditelements() {
		return _editelements;
	}

	/**
	 * @返回 _isreplace变量的值
	 */
	public boolean isReplace() {
		return _isreplace;
	}

	/**
	 * @返回 _newvalue变量的值
	 */
	public Map<Integer, Object> getNewvalue() {
		return _newvalue;
	}
	public List<Map<Integer, Object>> getOldvalues() {
		return new ArrayList<Map<Integer,Object>>(_oldvalues);
	}

}
