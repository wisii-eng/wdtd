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
 * @DateTimeInfoTableModel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.dateformat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.wisii.wisedoc.document.attribute.AbstractDateTimeInfo;
import com.wisii.wisedoc.document.attribute.DateTimeItem;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DateTimeType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DefaultType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DigitHourType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.DigitType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.MonthType;
import com.wisii.wisedoc.document.attribute.DateTimeItem.YearType;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-6-26
 */
public class DateTimeInfoTableModel extends AbstractTableModel
{
	private List<DateTimeItem> items;
	private Map<DateTimeType, String> typetostringmap = new HashMap<DateTimeType, String>();
	private Map<DateTimeType, Map<Integer, String>> digittostringmap = new HashMap<DateTimeType, Map<Integer, String>>();
	private String[] columnnames =
	{ "类型", "输出位数设置", "编码类型", "分割符" };

	public DateTimeInfoTableModel(AbstractDateTimeInfo info, boolean isdate)
	{
		items = new LinkedList<DateTimeItem>();
		if (info != null)
		{
			List<DateTimeItem> olditems = info.getDateitems();
			if (isdate)
			{
				boolean isyearin = false;
				boolean ismonthin = false;
				boolean isdayin = false;
				for (DateTimeItem item : olditems)
				{
					if (item != null)
					{

						if (item.getType() == DateTimeType.Year)
						{
							isyearin = true;
							items.add(item.clone());
						} else if (item.getType() == DateTimeType.Month)
						{
							ismonthin = true;
							items.add(item.clone());
						} else if (item.getType() == DateTimeType.Day)
						{
							isdayin = true;
							items.add(item.clone());
						}
					}
				}
				if (!isyearin)
				{
					items.add(new DateTimeItem(DateTimeType.Year, -1, null,
							(String) null));
				}
				if (!ismonthin)
				{
					items.add(new DateTimeItem(DateTimeType.Month, -1, null,
							(String) null));
				}
				if (!isdayin)
				{
					items.add(new DateTimeItem(DateTimeType.Day, -1, null,
							(String) null));
				}

			} else
			{

				boolean ishourin = false;
				boolean isminutein = false;
				boolean issecondin = false;
				for (DateTimeItem item : olditems)
				{
					if (item != null)
					{

						if (item.getType() == DateTimeType.Hour)
						{
							ishourin = true;
							items.add(item.clone());
						} else if (item.getType() == DateTimeType.Minute)
						{
							isminutein = true;
							items.add(item.clone());
						} else if (item.getType() == DateTimeType.Second)
						{
							issecondin = true;
							items.add(item.clone());
						}
					}
				}
				if (!ishourin)
				{
					items.add(new DateTimeItem(DateTimeType.Hour, -1, null,
							(String) null));
				}
				if (!isminutein)
				{
					items.add(new DateTimeItem(DateTimeType.Minute, -1, null,
							(String) null));
				}
				if (!issecondin)
				{
					items.add(new DateTimeItem(DateTimeType.Second, -1, null,
							(String) null));
				}

			}

		} else
		{
			if (isdate)
			{

				items.add(new DateTimeItem(DateTimeType.Year, -1, null,
						(String) null));

				items.add(new DateTimeItem(DateTimeType.Month, -1, null,
						(String) null));

				items.add(new DateTimeItem(DateTimeType.Day, -1, null,
						(String) null));

			} else
			{

				items.add(new DateTimeItem(DateTimeType.Hour, -1, null,
						(String) null));

				items.add(new DateTimeItem(DateTimeType.Minute, -1, null,
						(String) null));

				items.add(new DateTimeItem(DateTimeType.Second, -1, null,
						(String) null));

			}
		}
		initmap();
	}

	private void initmap()
	{
		typetostringmap.put(DateTimeType.Year, "年");
		typetostringmap.put(DateTimeType.Month, "月");
		typetostringmap.put(DateTimeType.Day, "日");
		typetostringmap.put(DateTimeType.Hour, "时");
		typetostringmap.put(DateTimeType.Minute, "分");
		typetostringmap.put(DateTimeType.Second, "秒");
		Map<Integer, String> yeardigitmap = new HashMap<Integer, String>();
		yeardigitmap.put(-1, "不输出");
		yeardigitmap.put(DigitType.All.ordinal(), "全部");
		yeardigitmap.put(DigitType.First.ordinal(), "第一位");
		yeardigitmap.put(DigitType.Second.ordinal(), "第二位");
		yeardigitmap.put(DigitType.Third.ordinal(), "第三位");
		yeardigitmap.put(DigitType.Forth.ordinal(), "第四位");
		yeardigitmap.put(DigitType.FS.ordinal(), "前两位");
		yeardigitmap.put(DigitType.ST.ordinal(), "中间两位");
		yeardigitmap.put(DigitType.TF.ordinal(), "后两位");
		yeardigitmap.put(DigitType.FST.ordinal(), "前三位");
		yeardigitmap.put(DigitType.STF.ordinal(), "后三位");
		Map<Integer, String> hourdigitmap = new HashMap<Integer, String>();
		hourdigitmap.put(-1, "不输出");
		hourdigitmap.put(DigitHourType.Hour24.ordinal(), "不补位24时制");
		hourdigitmap.put(DigitHourType.Am_Pm.ordinal(), "不补位am,pm");
		hourdigitmap.put(DigitHourType.Am_Pm_Cn.ordinal(), "不补位上午,下午");
		hourdigitmap.put(DigitHourType.Hour12.ordinal(), "不补位12时制");
		hourdigitmap.put(DigitHourType.D_Hour24.ordinal(), "补位24时制");
		hourdigitmap.put(DigitHourType.D_Am_Pm.ordinal(), "补位am,pm");
		hourdigitmap.put(DigitHourType.D_Am_Pm_Cn.ordinal(), "补位上午下午");
		hourdigitmap.put(DigitHourType.D_Hour12.ordinal(), "补位12时制");
		Map<Integer, String> defaultdigitmap = new HashMap<Integer, String>();
		defaultdigitmap.put(-1, "不输出");
		defaultdigitmap.put(1, "不补位");
		defaultdigitmap.put(2, "补位");
		digittostringmap.put(DateTimeType.Year, yeardigitmap);
		digittostringmap.put(DateTimeType.Month, defaultdigitmap);
		digittostringmap.put(DateTimeType.Day, defaultdigitmap);
		digittostringmap.put(DateTimeType.Hour, hourdigitmap);
		digittostringmap.put(DateTimeType.Minute, defaultdigitmap);
		digittostringmap.put(DateTimeType.Second, defaultdigitmap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount()
	{
		// TODO Auto-generated method stub
		return columnnames.length;
	}

	@Override
	public String getColumnName(int column)
	{
		return columnnames[column];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount()
	{
		// TODO Auto-generated method stub
		return items.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		DateTimeItem item = items.get(rowIndex);
		if (item == null)
		{
			return null;
		} else
		{

			if (columnIndex == 0)
			{
				return typetostringmap.get(item.getType());
			} else if (columnIndex == 1)
			{
				return digittostringmap.get(item.getType())
						.get(item.getDigit());
			} else if (columnIndex == 2)
			{
				return item.getViewStyle();
			} else
			{
				return item.getSeparator();
			}
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		DateTimeItem newitem;
		DateTimeItem olditem = items.get(rowIndex);
		if (columnIndex == 1)
		{
			if (value == null || !(value instanceof Integer))
			{
				return;
			}
			olditem.setDigit((Integer) value);
			if(((Integer) value)<0)
			{
				olditem.setViewStyle(null);
				olditem.setSeparator(null);
			}
			else
			{
				if (olditem.getViewStyle() == null)
				{
					DateTimeType type = olditem.getType();
					if (type == DateTimeType.Year)
					{
						olditem.setViewStyle(YearType.Arabia);
					} else if (type == DateTimeType.Month)
					{
						olditem.setViewStyle(MonthType.Arabia);
					} else
					{
						olditem.setViewStyle(DefaultType.Arabia);
					}
				}
			}

		} else if (columnIndex == 2)
		{
			olditem.setViewStyle((Enum) value);
		} else
		{
			if (value != null && value.toString().equals(""))
			{
				value = null;
			}
			olditem.setSeparator((String) value);
		}

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		if (columnIndex == 0)
		{
			return false;
		} else if (columnIndex == 1)
		{
			return true;
		} else
		{
			DateTimeItem item = items.get(rowIndex);
			if (item == null)
			{
				return false;
			} else
			{
				DateTimeType type = item.getType();
				if (item.getDigit() <0)
				{
					return false;
				}
				return true;
			}
		}
	}

	public DateTimeItem getDateTimeItem(int row)
	{
		return items.get(row);
	}
	/**
	 * 
	 * row1和row2的内容互换
	 *
	 * @param      
	 * @return    
	 * @exception   
	 */
    void convertrow(int row1,int row2)
    {
		if (row1 != row2 && row1 >= 0 && row1 < getRowCount() && row2 >= 0
				&& row2 < getRowCount())
		{
			DateTimeItem temp = items.get(row1);
			items.set(row1, items.get(row2));
			items.set(row2, temp);
		}
	}
	/**
	 * @返回 items变量的值
	 */
	public final List<DateTimeItem> getItems()
	{
		List<DateTimeItem> newitems = new ArrayList<DateTimeItem>();
		for (DateTimeItem item : items)
		{
			if (item.getDigit() != -1)
			{
				newitems.add(item);
			}
		}
		return newitems;
	}
}
