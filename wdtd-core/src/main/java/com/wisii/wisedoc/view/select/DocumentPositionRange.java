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
 * @SelectCell.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.select;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.TextInline;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-9-23
 */
public final class DocumentPositionRange implements Comparable<DocumentPositionRange> {
	// 起始位置
	private DocumentPosition _start;
	// 结束位置
	private DocumentPosition _end;

	private DocumentPositionRange(DocumentPosition start, DocumentPosition end) {
		_start = start;
		_end = end;
	}

	/**
	 * @返回 _start变量的值
	 */
	public DocumentPosition getStartPosition() {
		return _start;
	}

	/**
	 * @返回 _end变量的值
	 */
	public DocumentPosition getEndPosition() {
		return _end;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof DocumentPositionRange)) {
			return false;
		}
		DocumentPositionRange selcell = (DocumentPositionRange) obj;
		return _start.equals(selcell._start) && _end.equals(selcell._end);
	}

	public static DocumentPositionRange creatSelectCell(DocumentPosition start,
			DocumentPosition end) {
		if (start != null && end != null) {
			int compare = start.compareTo(end);
			// 如果起始位置比结束位置大，则交换
			if (compare > 0) {
				DocumentPosition temp = start;
				start = end;
				end = temp;
			}
			// 如果相等，则直接返回空
			else if (compare == 0) {
				return null;
			} else {
				// nothing
			}
			return new DocumentPositionRange(start, end);
		}
		return null;
	}

	/**
	 * 
	 * 判断点是否在区域范围以内，
	 * 
	 * @param pos
	 *            :位置
	 * @return 在则返回true，否则返回false
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public boolean isPositionin(DocumentPosition pos) {
		if (pos != null && pos.compareTo(_start) >= 0
				&& pos.compareTo(_end) <= 0) {
			return true;
		}
		return false;
	}

	public int compareTo(DocumentPositionRange o) {
		if (o == null) {
			return 1;
		} else {
			// 获得结束位置的大小关系
			int startcompare = this.getStartPosition().compareTo(
					o.getStartPosition());
			// 如果起始闻之相等，则判断结束位置的大小
			if (startcompare == 0) {
				return this.getEndPosition().compareTo(o.getEndPosition());
			}
			return startcompare;
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "start:" + ((TextInline)_start.getLeafElement()).getContent().getText() + ":end:" + ((TextInline)_end.getLeafElement()).getContent().getText() ;
	}
}
