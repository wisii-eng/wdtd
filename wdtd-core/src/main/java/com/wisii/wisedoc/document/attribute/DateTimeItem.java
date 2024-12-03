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
 * @DateTimeItem.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.text.MessageFormat;

/**
 * @类功能说明：用于描述日期、时间的一项 如：年、月、日、时、分、秒
 * @作者：李晓光
 * @创建时间：2008-2-1 下午01:48:48
 */
public class DateTimeItem implements Comparable<DateTimeItem>,Cloneable
{

	/* 定义类型; 枚举 */
	public static enum DateTimeType
	{
		Year(1), Month(2), Day(3), Hour(4), Minute(5), Second(6);

		private int type = -1;

		private DateTimeType(int type)
		{
			this.type = type;
		}

		public int getType()
		{
			return this.type;
		}
	}

	/**
	 * 根据指定的信息初始化对象
	 * 
	 * @param type
	 *            指定类型：年、月、日等
	 * @param digit
	 *            指定要显示的位数
	 * @param viewStyle
	 *            指定显示的分割，阿拉伯数字、中文数字
	 * @param separator
	 *            指定分隔符： 如：2008年 这里指的是【年】
	 */
	public DateTimeItem(DateTimeType type, int digit, Enum viewStyle,
			String separator)
	{
		setType(type);
		setDigit(digit);
		setViewStyle(viewStyle);
		setSeparator(separator);
	}

	/* 定义数据的种类。 如：年 */
	private DateTimeType type;

	/* 要显示的位数 */
	private int digit = 0;

	/* 显示的样式。如：阿拉伯数字 */
	private Enum viewStyle;

	/* 定义后面的分割符号 */
	private String separator = "";

	/* toString用格式化样式信息 */
	private static String format = "[{0}：{1}：{2}：{3}]";

	/* 输出用格式化样式信息 */
	private static String parrern = "{0},{1},{2}";

	private static final int YEARMAX = 9;

	private static final int OTHERMAX = 2;

	private static final int HOURMAX = 7;

	private static final int MIN = 0;

	public Enum getViewStyle()
	{
		return viewStyle;
	}

	public void setViewStyle(Enum viewStyle)
	{
		this.viewStyle = viewStyle;
	}

	public int getDigit()
	{
		return digit;
	}

	public void setDigit(int digit)
	{
		this.digit = digit;
	}

	public DateTimeType getType()
	{
		return type;
	}

	public void setType(DateTimeType type)
	{
		this.type = type;
		initViewStyle(type);
	}

	private void initViewStyle(DateTimeType type)
	{
		if (type == null || viewStyle != null)
			return;
		switch (type)
		{
			case Year:
				setViewStyle(YearType.Arabia);
				break;
			case Month:
				setViewStyle(MonthType.Arabia);
				break;
			default:
				setViewStyle(DefaultType.Arabia);
				break;
		}
	}

	private boolean isLogicalDigit(int digit)
	{
		if (type == DateTimeType.Year)
		{
			return (digit >= MIN && digit <= YEARMAX);
		} else if (type == DateTimeType.Hour)
		{
			return (digit >= MIN && digit <= HOURMAX);
		} else
			return (digit >= MIN && digit <= OTHERMAX);
	}

	private int getLegalDigit(int digit)
	{
		if (digit < MIN)
			return MIN;

		if (type == DateTimeType.Year)
		{
			if (digit > YEARMAX)
				return YEARMAX;
		} else if (type == DateTimeType.Hour)
		{
			if (digit > HOURMAX)
				return HOURMAX;
		} else
		{
			if (digit > OTHERMAX)
				return OTHERMAX;
		}
		return digit;
	}

	public String getSeparator()
	{
		return separator;
	}

	public void setSeparator(String separator)
	{
		this.separator = separator;
	}

	/**
	 * 转换为XSLT中能用的数据信息 如 1，0，3
	 * 
	 * @return 转换后的信息
	 */
	public String getItemInfo()
	{
		String style = "-1", type = "-1";
		if (getType() != null)
			type = getType().getType() + "";
		if (getViewStyle() != null)
			style = ((ViewStyle) getViewStyle()).getViewStyle() + "";
		return MessageFormat.format(parrern, type, getDigit() + "", style);
	}

	/**
	 * 重写toString方法，更直观的表述当前对象。
	 */
	public String toString()
	{
		String style = "", type = "";
		if (getType() != null)
			type = getType().name();
		if (getViewStyle() != null)
			style = getViewStyle().name();
		return MessageFormat.format(format, type, getDigit(), style,
				getSeparator());
	}

	public String repairePattern()
	{
		StringBuilder s = new StringBuilder("");
		if (this == null)
			return "";
		switch (this.getType())
		{
			case Year:
				if (this.getDigit() == DigitType.TF.ordinal())// item.getDigit
					// ()
					// >= 5 &&
					// item.getDigit
					// () <= 6
					s.append("YY");
				else
					s.append("YYYY");
				break;
			case Month:
				// s.append("MM");
				s.append(getAppendInfo(this.getDigit(), "M"));
				break;
			case Day:
				// s.append("DD");
				s.append(getAppendInfo(this.getDigit(), "D"));
				break;
			case Hour:
				s.append("hh");
				break;
			case Minute:
				s.append("mm");
				break;
			case Second:
				s.append("ss");
				break;
		}
		 String separator = this.getSeparator();
		 if (separator != null)
		 {
		 s.append(separator);
		 }
		return s.toString();
	}
	public String repairePatternWithoutSeparator()
	{
		StringBuilder s = new StringBuilder("");
		if (this == null)
			return "";
		switch (this.getType())
		{
			case Year:
				if (this.getDigit() == DigitType.TF.ordinal())// item.getDigit
					s.append("YY");
				else
					s.append("YYYY");
				break;
			case Month:
				// s.append("MM");
				s.append(getAppendInfo(this.getDigit(), "M"));
				break;
			case Day:
				// s.append("DD");
				s.append(getAppendInfo(this.getDigit(), "D"));
				break;
			case Hour:
				s.append("hh");
				break;
			case Minute:
				s.append("mm");
				break;
			case Second:
				s.append("ss");
				break;
		}
		return s.toString();
	}
	private String getAppendInfo(int count, String repeat)
	{
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < count; i++)
		{
			result.append(repeat);
		}
		return result.toString();
	}

	public int compareTo(DateTimeItem item)
	{
		if (item == null)
			return 1;

		return (getType().getType() - item.getType().getType());
	}

	public interface ViewStyle
	{

		int getViewStyle();
	}

	/**
	 * @类功能说明：定义年的显示类型
	 * @作者：李晓光
	 * @创建时间：2008-2-14 上午09:20:49
	 */
	public static enum YearType implements ViewStyle
	{
		/* 阿拉伯数字 */
		Arabia(1),
		/* 中文数字 */
		/* 〇-零 中文数字 */
		China_Zero(2),
		China(3),
		/* 中文大写 --壹贰叁肆伍 */
		China_Upper(4);
		

		private int viewType = -1;

		private YearType(int viewType)
		{
			this.viewType = viewType;
		}

		public int getViewStyle()
		{
			return this.viewType;
		}

		public static YearType getYearType(int i)
		{
			YearType[] s = YearType.values();
			for (YearType year : s)
			{
				if (year.getViewStyle() == i)
					return year;
			}
			return null;
		}
		@Override
		public String toString()
		{
			if(this == Arabia)
			{
				return "阿拉伯数字";
			}
			else if(this == China)
			{
				return "中文数字";
			}
			else if(this == China_Upper)
			{
				return "中文繁体数字";
			}
			else if(this==China_Zero)
			{
				return "零-〇 中文数字";
			}
			else
			{
				return "" + viewType;
			}
		}
	}

	/**
	 * @类功能说明：定义月的显示类型
	 * @作者：李晓光
	 * @创建时间：2008-2-14 上午09:20:49
	 */
	public static enum MonthType implements ViewStyle
	{
		/* 阿拉伯数字 */
		Arabia(1),
		/* 〇-零 中文数字 */
		China_Zero(2),
		/* 中文数字 */
		China(3),
		/* 中文大写--壹贰叁肆伍 */
		China_Upper(4),
		/* 英文小写 */
		En_Lower(5),
		/* 首字母大写 */
		En_Word(6),
		/* 英文缩写【小写】 */
		En_Short_Lower(7),
		/* 英文缩写【首字母大写】 */
		En_Short_Upper(8),
		/* 英文缩写【取前三个字母，且都大写】 */
		En_Short_SUPPER(9);

		private int viewType = -1;

		private MonthType(int viewType)
		{
			this.viewType = viewType;
		}

		public int getViewStyle()
		{
			return this.viewType;
		}

		public static MonthType getMonthType(int i)
		{
			MonthType[] s = MonthType.values();
			for (MonthType month : s)
			{
				if (month.getViewStyle() == i)
					return month;
			}
			return null;
		}
		@Override
		public String toString()
		{
			if(this == Arabia)
			{
				return "阿拉伯数字";
			}
			else if(this == China_Zero)
			{
				return "零-〇 中文数字";
			}
			else if(this == China)
			{
				return "中文数字";
			}
			else if(this == China_Upper)
			{
				return "中文繁体数字";
			}
			else if(this == En_Lower)
			{
				return "英文小写";
			}
			else if(this == En_Word)
			{
				return "英文-首字母大写";
			}
			else if(this == En_Short_Lower)
			{
				return "英文缩写(全部字母小写)";
			}
			else if(this == En_Short_Upper)
			{
				return "英文缩写(首字母大写)";
			}
			else if(this == En_Short_SUPPER)
			{
				return "英文缩写（取前三个字母，且都大写）";
			}
			else
			{
				return "" + viewType;
			}
		}
	}

	/**
	 * @类功能说明：定义日、时、分、秒的显示类型
	 * @作者：李晓光
	 * @创建时间：2008-2-14 上午09:20:49
	 */
	public static enum DefaultType implements ViewStyle
	{
		/* 阿拉伯数字 */
		Arabia(1),
		/* 〇-零 中文数字 */
		China_Zero(2),
		/* 中文数字 */
		China(3),
		/* 中文大写--壹贰叁肆伍 */
		China_Upper(4);

		private int viewType = -1;

		private DefaultType(int viewType)
		{
			this.viewType = viewType;
		}

		public int getViewStyle()
		{
			return this.viewType;
		}

		public static DefaultType getDefaultType(int i)
		{
			DefaultType[] s = DefaultType.values();
			for (DefaultType defaulttype : s)
			{
				if (defaulttype.getViewStyle() == i)
					return defaulttype;
			}
			return null;
		}
		@Override
		public String toString()
		{
			if(this == Arabia)
			{
				return "阿拉伯数字";
			}
			else if(this == China_Zero)
			{
				return "零-〇 中文数字";
			}
			else if(this == China)
			{
				return "中文数字";
			}
			else if(this == China_Upper)
			{
				return "中文繁体数字";
			}
			else
			{
				return "" + viewType;
			}
		}
	}

	/**
	 * @类功能说明：定义年的显示位数及位置
	 * @作者：李晓光
	 * @创建时间：2008-2-14 上午09:20:49
	 */
	public static enum DigitType
	{
		/* 全部输出 */
		All,
		/* 第一位 */
		First,
		/* 第二位 */
		Second,
		/* 第三位 */
		Third,
		/* 第四位 */
		Forth,
		/* 前两位 First + Second */
		FS,
		/* 中间两位 Second + Third */
		ST,
		/* 后两位 Third + Forth */
		TF,
		/* 前三位 First + Second + Third */
		FST,
		/* 后三位 Second + Third + Forth */
		STF;
	}

	/**
	 * @类功能说明：定义时的显示位数及是否显示AM、PM、上午、下午
	 * @作者：李晓光
	 * @创建时间：2008-2-14 下午01:41:26
	 */
	public static enum DigitHourType
	{
		/* 不补位24时制 */
		Hour24,
		/* 不补位am：pm */
		Am_Pm,
		/* 不补位上午下午 */
		Am_Pm_Cn,
		/* 不补位12时制 */
		Hour12,
		/* 补位24时制 */
		D_Hour24,
		/* 补位am：pm */
		D_Am_Pm,
		/* 补位上午下午 */
		D_Am_Pm_Cn,
		/* 补位12时制 */
		D_Hour12
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + digit;
		result = prime * result
				+ ((separator == null) ? 0 : separator.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((viewStyle == null) ? 0 : viewStyle.hashCode());
		return result;
	}
    public DateTimeItem clone()
    {
    	// TODO Auto-generated method stub
    	return new DateTimeItem(type,digit,viewStyle,separator);
    }
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateTimeItem other = (DateTimeItem) obj;
		if (digit != other.digit)
			return false;
		if (separator == null)
		{
			if (other.separator != null)
				return false;
		} else if (!separator.equals(other.separator))
			return false;
		if (type == null)
		{
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (viewStyle == null)
		{
			if (other.viewStyle != null)
				return false;
		} else if (!viewStyle.equals(other.viewStyle))
			return false;
		return true;
	}
	
}
