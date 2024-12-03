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
 * @WisedocDateTimeFormat.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.document.attribute;

/**
 * @类功能说明：保存输入样式、输出样式
 * @作者：李晓光
 * @创建时间：2008-2-2 上午10:38:04
 */
public class WisedocDateTimeFormat
{
	private final DateInfo datein;
	private final DateInfo dateout;
	private final TimeInfo timein;
	private final TimeInfo timeout;
	private final boolean isindatefirst;
	private final boolean isoutdatefirst;
	private final String inputseperate;
	private final String outseperate;

	public WisedocDateTimeFormat(DateInfo datein, TimeInfo timein,
			DateInfo dateout, TimeInfo timeout, String inputseperate,
			String outseperate)
	{
		this.datein = datein;
		this.timein = timein;
		this.dateout = dateout;
		this.timeout = timeout;
		this.inputseperate = inputseperate;
		this.outseperate = outseperate;
		isindatefirst = true;
		isoutdatefirst = true;
	}

	public WisedocDateTimeFormat(DateInfo datein, TimeInfo timein,
			DateInfo dateout, TimeInfo timeout, String inputseperate,
			String outseperate, boolean isindatefirst, boolean isoutdatefirst)
	{
		this.datein = datein;
		this.timein = timein;
		this.dateout = dateout;
		this.timeout = timeout;
		this.inputseperate = inputseperate;
		this.outseperate = outseperate;
		this.isindatefirst = isindatefirst;
		this.isoutdatefirst = isoutdatefirst;
	}

	/**
	 * @返回 datein变量的值
	 */
	public final DateInfo getDatein()
	{
		return datein;
	}

	/**
	 * @返回 dateout变量的值
	 */
	public final DateInfo getDateout()
	{
		return dateout;
	}

	/**
	 * @返回 timein变量的值
	 */
	public final TimeInfo getTimein()
	{
		return timein;
	}

	/**
	 * @返回 timeout变量的值
	 */
	public final TimeInfo getTimeout()
	{
		return timeout;
	}

	/**
	 * @返回 isindatefirst变量的值
	 */
	public final boolean isIndatefirst()
	{
		return isindatefirst;
	}

	/**
	 * @返回 isoutdatefirst变量的值
	 */
	public final boolean isOutdatefirst()
	{
		return isoutdatefirst;
	}

	/**
	 * @返回 inputseperate变量的值
	 */
	public String getInputseperate()
	{
		return inputseperate;
	}

	/**
	 * @返回 outseperate变量的值
	 */
	public String getOutseperate()
	{
		return outseperate;
	}
   
	public String toString()
	{
		String outdatestring = "";
		String outtimestring = "";
		if (dateout != null)
		{
			outdatestring = dateout.toString();
		}
		if (timeout != null)
		{
			outtimestring = timeout.toString();
		}
		String outseperateString = "";
		if (outseperate != null)
		{
			outseperateString = outseperate;
		}
		if (isoutdatefirst)
		{
			return outdatestring + outseperateString + outtimestring;
		} else
		{
			return outtimestring + outseperateString + outdatestring;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datein == null) ? 0 : datein.hashCode());
		result = prime * result + ((dateout == null) ? 0 : dateout.hashCode());
		result = prime * result
				+ ((inputseperate == null) ? 0 : inputseperate.hashCode());
		result = prime * result + (isindatefirst ? 1231 : 1237);
		result = prime * result + (isoutdatefirst ? 1231 : 1237);
		result = prime * result
				+ ((outseperate == null) ? 0 : outseperate.hashCode());
		result = prime * result + ((timein == null) ? 0 : timein.hashCode());
		result = prime * result + ((timeout == null) ? 0 : timeout.hashCode());
		return result;
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
		WisedocDateTimeFormat other = (WisedocDateTimeFormat) obj;
		if (datein == null)
		{
			if (other.datein != null)
				return false;
		} else if (!datein.equals(other.datein))
			return false;
		if (dateout == null)
		{
			if (other.dateout != null)
				return false;
		} else if (!dateout.equals(other.dateout))
			return false;
		if (inputseperate == null)
		{
			if (other.inputseperate != null)
				return false;
		} else if (!inputseperate.equals(other.inputseperate))
			return false;
		if (isindatefirst != other.isindatefirst)
			return false;
		if (isoutdatefirst != other.isoutdatefirst)
			return false;
		if (outseperate == null)
		{
			if (other.outseperate != null)
				return false;
		} else if (!outseperate.equals(other.outseperate))
			return false;
		if (timein == null)
		{
			if (other.timein != null)
				return false;
		} else if (!timein.equals(other.timein))
			return false;
		if (timeout == null)
		{
			if (other.timeout != null)
				return false;
		} else if (!timeout.equals(other.timeout))
			return false;
		return true;
	}
}
