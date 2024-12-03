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
 * FixedLength.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.datatype.PercentBaseContext;

/**
 * 
 * 类功能描述：固定长度的长度属性
 * 
 * 作者：zhangqiang 创建日期：2008-8-21
 */
public class FixedLength extends LengthProperty
{

	// 转换后的千分之一pt值
	private int millipoints;

	// 内部长度值，该值是实际长度值×（10的精度次幂）取整后的结果，如12pt，
	// 如果pt单位的精度(各单位的精度可通过配置文件配)是小数后一位，则_length值为120
	private int _length;

	private int precision = ConfigureUtil.DEFAULTPRECISION;

	// 长度单位
	private String _units;

	/* 【添加：END】by 李晓光2009-1-5 */
	public final static String DEFAULTUNIT = "pt";

	/**
	 * 
	 * 以指定长度值和单位来构造FixedLength对象, 如要构造12pt的对象，则new FixedLength(12d,"pt")
	 * 
	 * @param lengthvalue
	 *            :长度值 units：单位
	 * @exception
	 * 
	 */
	public FixedLength(final double lengthvalue, final String units)
	{

		this(lengthvalue, units, ConfigureUtil.getPrecision(units));
	}

	/**
	 * 
	 * 以指定长度值,单位,精度来构造FixedLength对象, 如要构造12pt的对象，则new FixedLength(12d,"pt")
	 * 
	 * @param lengthvalue
	 *            :长度值 units：单位 precision：精度
	 * @exception
	 * 
	 */
	public FixedLength(final double lengthvalue, final String units,
			final int precision)
	{
		if (precision < 0)
		{
			this.precision = ConfigureUtil.getPrecision(units);
		} else
		{
			this.precision = precision;
		}
		convert(lengthvalue, units);
	}

	/**
	 * 
	 * 以内部长度值和指定单位来构造FixedLength对象, 如要构造12cm（如cm单位的精度为小数点后2位）的对象， 则new
	 * FixedLength(1200,"cm")
	 * 
	 * @param lengthvalue
	 *            :长度值 units：单位
	 * @exception
	 * 
	 */
	public FixedLength(final int length, final String units)
	{
		this(length, units, ConfigureUtil.getPrecision(units));
	}

	/**
	 * 
	 * 以内部长度值,单位,精度来构造FixedLength对象, 如要构造12cm（如cm单位的精度为小数点后2位）的对象， 则new
	 * FixedLength(1200,"cm")
	 * 
	 * @param lengthvalue
	 *            :长度值 units：单位
	 * @exception
	 * 
	 */
	public FixedLength(final int length, final String units, final int precision)
	{
		if (precision < 0)
		{
			this.precision = ConfigureUtil.getPrecision(units);
		} else
		{
			this.precision = precision;
		}
		_length = length;
		_units = units;
		double dvalue = length;
		if (_units.equals("in"))
		{
			dvalue = dvalue * 72
					/ Math.round((float) Math.pow(10, this.precision));
		} else if (_units.equals("cm"))
		{
			dvalue = dvalue * 28.3464567
					/ Math.round((float) Math.pow(10, this.precision));
		} else if (_units.equals("mm"))
		{
			dvalue = dvalue * 2.83464567
					/ Math.round((float) Math.pow(10, this.precision));
		} else if (_units.equals("pt"))
		{
			dvalue = dvalue / Math.round((float) Math.pow(10, this.precision));
		}
		if (units.equals("mpt"))
		{
			millipoints = (int) dvalue;
			_length = millipoints;
		} else
		{
			millipoints = (int) (dvalue * 1000);
		}
	}

	/**
	 * 
	 * 以mpt为单位的长度来构造对象，单位将会取配置的单位
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public FixedLength(final int mpt)
	{
		this(mpt, ConfigureUtil.getPrecision(ConfigureUtil.getUnit()));
	}

	/**
	 * 
	 * 以mpt为单位的长度来构造对象，单位将会取配置的单位
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public FixedLength(final int mpt, final int precision)
	{
		this(ConfigureUtil.getUnit(),mpt, precision);
	}
	/**
	 * 
	 * 以mpt为单位的长度,指定单位类型来构造对象，
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public FixedLength(String unit,final int mpt, final int precision)
    {
		if (precision < 0)
		{
			this.precision = ConfigureUtil
					.getPrecision(ConfigureUtil.getUnit());
		} else
		{
			this.precision = precision;
		}
		convert(mpt, unit);
    }
	/*
	 * 
	 * 传入入mpt为单位的长度值，得到以units为单位的长度值
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * 
	 * @return {返回参数名} {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	protected void convert(final int mpt, String unit)
	{
		// TODO: the whole routine smells fishy.

		final int assumedResolution = 1; // points/pixel = 72dpi
		if (unit == null || unit.trim().equals(""))
		{
			unit = DEFAULTUNIT;
		}
		unit = unit.trim().toLowerCase();
		_units = unit;
		millipoints = mpt;
		double dvalue = mpt;
		if (unit.equals("in"))
		{
			dvalue = dvalue / 72;
		} else if (unit.equals("cm"))
		{
			dvalue = dvalue / 28.3464567;
		} else if (unit.equals("mm"))
		{
			dvalue = dvalue / 2.83464567;
		} else if (unit.equals("pt"))
		{

		} else if (unit.equals("mpt"))
		{ // mpt is non-standard!!! mpt=millipoints
			// TODO: this seems to be wrong.
			// Do nothing.
			// dvalue = dvalue;
		} else if (unit.equals("pc"))
		{
			dvalue = dvalue / 12;
			/*
			 * } else if (unit.equals("em")) { dvalue = dvalue fontsize;
			 */
		} else if (unit.equals("px"))
		{
			// TODO: get resolution from user agent?
			dvalue = dvalue / assumedResolution;
		} else
		{
			dvalue = 0;
			_units = DEFAULTUNIT;
		}
		if (unit.equals("mpt"))
		{
		} else
		{
			dvalue = dvalue / 1000;
		}
		_length = Math.round((float) (dvalue * Math.pow(10, precision)));
	}

	/**
	 * 
	 * 将固定单位值转换为千分之一pt值
	 * 
	 * @param dvalue
	 *            ：长度值 unit：单位
	 * @return
	 * @exception
	 */
	protected void convert(double dvalue, String unit)
	{
		// TODO: the whole routine smells fishy.

		final int assumedResolution = 1; // points/pixel = 72dpi
		if (unit == null || unit.trim().equals(""))
		{
			unit = DEFAULTUNIT;
		}
		unit = unit.trim().toLowerCase();
		_units = unit;
		_length = Math.round((float) (dvalue * (Math.pow(10, precision))));
		if (unit.equals("in"))
		{

			dvalue = dvalue * 72;

		} else if (unit.equals("cm"))
		{
			dvalue = dvalue * 28.3464567;

		} else if (unit.equals("mm"))
		{
			dvalue = dvalue * 2.83464567;

		} else if (unit.equals("pt"))
		{
		} else if (unit.equals("mpt"))
		{ // mpt is non-standard!!! mpt=millipoints
			// TODO: this seems to be wrong.
			// Do nothing.
			// dvalue = dvalue;
		} else if (unit.equals("pc"))
		{
			dvalue = dvalue * 12;
			/*
			 * } else if (unit.equals("em")) { dvalue = dvalue fontsize;
			 */
		} else if (unit.equals("px"))
		{
			// TODO: get resolution from user agent?
			dvalue = dvalue * assumedResolution;
		} else
		{
			dvalue = 0;
			_length = 0;
			_units = DEFAULTUNIT;
		}
		if (unit.equals("mpt"))
		{
			millipoints = (int) dvalue;
			_length = millipoints;
		} else
		{
			millipoints = (int) (dvalue * 1000);
		}
	}

	/*
	 * @see com.wisii.fov.datatypes.Numeric#getValue()
	 */
	public int getValue()
	{
		return millipoints;
	}

	/*
	 * @see com.wisii.fov.datatypes.Numeric#getValue(PercentBaseContext)
	 */
	public int getValue(final PercentBaseContext context)
	{
		return millipoints;
	}

	/*
	 * @see com.wisii.fov.datatypes.Numeric#getNumericValue()
	 */
	public double getNumericValue()
	{
		return millipoints;
	}

	/*
	 * @see com.wisii.fov.datatypes.Numeric#getNumericValue(PercentBaseContext)
	 */
	public double getNumericValue(final PercentBaseContext context)
	{
		return millipoints;
	}

	/*
	 * Return true since FixedLength are always absolute.
	 * 
	 * @see com.wisii.fov.datatypes.Numeric#isAbsolute()
	 */
	public boolean isAbsolute()
	{
		return true;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String text = getLengthValueString();
		text = text + " " + ConfigureUtil.getTextofUnit(_units);
		return text;
	}

	@Override
	public int getDimension()
	{
		return 1;
	}

	@Override
	public int getEnum()
	{
		return 0;
	}

	public int getInnerLengthValue()
	{
		return _length;
	}

	/**
	 * @返回 _length变量的值
	 */
	public String getLengthValueString()
	{
		String text = "" + _length;
		if (precision > 0 && _length != 0)
		{
			final int tlen = text.length();
			int index = 0;
			for (int i = 0; i < precision; i++)
			{
				final char c = text.charAt(tlen - i - 1);
				if (c != '0')
				{
					break;
				}
				index = i + 1;
			}
			String decimalstring = null;
			String integerstring = null;
			if (precision < tlen)
			{
				decimalstring = text.substring(tlen - precision, tlen - index);
				integerstring = text.substring(0, tlen - precision);
			}
			//如果精度大于长度，证明全部是小数长度
			else
			{
				decimalstring = text.substring(0, tlen - index);
				integerstring = null;
			}

			if (integerstring == null || integerstring.equals(""))
			{
				if (decimalstring.indexOf('-') != -1)
				{
					decimalstring = decimalstring.substring(1);
					integerstring = "-0";
				} else
				{
					integerstring = "0";
				}
			} else if (integerstring.equals("-"))
			{
				integerstring = integerstring + "0";
			}
			if (decimalstring != null && !decimalstring.isEmpty())
			{
				text = integerstring + "." + decimalstring;
			} else
			{
				text = integerstring;
			}
		}
		return text;
	}

	/**
	 * @返回 _units变量的值
	 */
	public String getUnits()
	{
		return _units;
	}

	/**
	 * @返回 precision变量的值
	 */
	public int getPrecision()
	{
		return precision;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + _length;
		result = prime * result + ((_units == null) ? 0 : _units.hashCode());
		result = prime * result + millipoints;
		result = prime * result + precision;
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final FixedLength other = (FixedLength) obj;
		if (_length != other._length)
		{
			return false;
		}
		if (_units == null)
		{
			if (other._units != null)
			{
				return false;
			}
		} else if (!_units.equals(other._units))
		{
			return false;
		}
		if (millipoints != other.millipoints)
		{
			return false;
		}
		if (precision != other.precision)
		{
			return false;
		}
		return true;
	}

	// 老的人工手写的equals方法
	/*
	 * public boolean equals(Object obj) { if (obj == null || !(obj instanceof
	 * FixedLength)) { return false; } FixedLength fl = (FixedLength) obj; if
	 * (_length == 0 && fl._length == 0) { return true; } return (_length ==
	 * fl._length)&&(precision ==fl.precision) && _units.equals(fl._units); }
	 */
	public FixedLength clone()
	{
		int lengthvalue = getInnerLengthValue();
		String units = getUnits();
		FixedLength newlength = new FixedLength(lengthvalue, units);
		newlength.precision = getPrecision();
		return newlength;
	}
}
