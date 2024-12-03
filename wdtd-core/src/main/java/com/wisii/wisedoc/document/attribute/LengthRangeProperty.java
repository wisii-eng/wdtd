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
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.wisii.com/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: LengthRangeProperty.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.attribute;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.PercentBase;
import com.wisii.wisedoc.document.datatype.PercentBaseContext;
import com.wisii.wisedoc.log.LogUtil;

/**
 * Superclass for properties that contain LengthRange values
 */
public class LengthRangeProperty extends Property implements Constants
{
	private LengthProperty minimum;

	private LengthProperty optimum;

	private LengthProperty maximum;

	public static final int MINSET = 1;

	public static final int OPTSET = 2;

	public static final int MAXSET = 4;

	private int bfSet = 0; // bit field

	private boolean consistent = false;

	public LengthRangeProperty(LengthProperty length)
	{
		minimum = optimum = maximum = length;
	}

	public LengthRangeProperty(LengthProperty minimum, LengthProperty optimum,
			LengthProperty maximum)
	{
		this.minimum = minimum;
		this.optimum = optimum;
		this.maximum = maximum;
		bfSet = OPTSET;
	}

	public LengthRangeProperty(LengthProperty minimum, LengthProperty optimum,
			LengthProperty maximum, int bfset)
	{
		this.minimum = minimum;
		this.optimum = optimum;
		this.maximum = maximum;
		bfSet = bfset;
	}

	/**
	 * @see com.wisii.fov.datatypes.CompoundDatatype#getComponent(int)
	 */
	public Property getComponent(int cmpId)
	{
		if (cmpId == CP_MINIMUM)
		{
			return getMinimum(null);
		} else if (cmpId == CP_OPTIMUM)
		{
			return getOptimum(null);
		} else if (cmpId == CP_MAXIMUM)
		{
			return getMaximum(null);
		} else
		{
			return null; // SHOULDN'T HAPPEN
		}
	}

	// Minimum is prioritaire, if explicit
	private void checkConsistency(PercentBaseContext context)
	{
		if (consistent)
		{
			return;
		}
		if (context == null)
		{
			return;
		}
		// Make sure max >= min
		// Must also control if have any allowed enum values!

		if (!minimum.isAuto()
				&& !maximum.isAuto()
				&& minimum.getLength().getValue(context) > maximum.getLength()
						.getValue(context))
		{
			if ((bfSet & MINSET) != 0)
			{
				// if minimum is explicit, force max to min
				if ((bfSet & MAXSET) != 0)
				{
					// Warning: min>max, resetting max to min
					LogUtil.error("forcing max to min in LengthRange");
				}
				maximum = minimum;
			} else
			{
				minimum = maximum; // minimum was default value
			}
		}
		// Now make sure opt <= max and opt >= min
		if (!optimum.isAuto()
				&& !maximum.isAuto()
				&& optimum.getLength().getValue(context) > maximum.getLength()
						.getValue(context))
		{
			if ((bfSet & OPTSET) != 0)
			{
				if ((bfSet & MAXSET) != 0)
				{
					// Warning: opt > max, resetting opt to max
					LogUtil.error("forcing opt to max in LengthRange");
					optimum = maximum;
				} else
				{
					maximum = optimum; // maximum was default value
				}
			} else
			{
				// opt is default and max is explicit or default
				optimum = maximum;
			}
		} else if (!optimum.isAuto()
				&& !minimum.isAuto()
				&& optimum.getLength().getValue(context) < minimum.getLength()
						.getValue(context))
		{
			if ((bfSet & MINSET) != 0)
			{
				// if minimum is explicit, force opt to min
				if ((bfSet & OPTSET) != 0)
				{
					LogUtil.error("forcing opt to min in LengthRange");
				}
				optimum = minimum;
			} else
			{
				minimum = optimum; // minimum was default value
			}
		}

		consistent = true;
	}

	/**
	 * @param context
	 *            Percentage evaluation context
	 * @return minimum length
	 */
	public LengthProperty getMinimum(PercentBaseContext context)
	{
		checkConsistency(context);
		return this.minimum;
	}

	/**
	 * @param context
	 *            Percentage evaluation context
	 * @return maximum length
	 */
	public LengthProperty getMaximum(PercentBaseContext context)
	{
		checkConsistency(context);
		return this.maximum;
	}

	/**
	 * @param context
	 *            Percentage evaluation context
	 * @return optimum length
	 */
	public LengthProperty getOptimum(PercentBaseContext context)
	{
		checkConsistency(context);
		return this.optimum;
	}

	public String toString()
	{
		return "LengthRange[" + "min:" + getMinimum(null).getObject()
				+ ", max:" + getMaximum(null).getObject() + ", opt:"
				+ getOptimum(null).getObject() + "]";
	}

	/**
	 * @return this.lengthRange
	 */
	public LengthRangeProperty getLengthRange()
	{
		return this;
	}

	/**
	 * @return this.lengthRange cast as an Object
	 */
	public Object getObject()
	{
		return this;
	}
/**
 * 
 * 合并多个LengthRangeProperty对象，合并后的对象的最小长度，默认长度，最大长度
 * 分别是要合并的LengthRangeProperty的最小，默认，和最大长度值之和
 *
 * @param    
 * @return   
 * @exception  
 */
	public static LengthRangeProperty combinationLength(
			LengthRangeProperty... lenranges)
	{
		if (lenranges == null || lenranges.length < 1)
		{
			return null;
		}
		int min = 0;
		int max = 0;
		int opt = 0;
		double minfactor = 0;
		double maxfactor = 0;
		double optfactor = 0;
		for (LengthRangeProperty lenrange : lenranges)
		{
			LengthProperty minp = (LengthProperty) lenrange.getMinimum(null);
			if (minp instanceof PercentLength)
			{
				minfactor = minfactor + ((PercentLength) minp).value();
			} else
			{
				if (minp.getEnum() != Constants.EN_AUTO)
				{
					min = min + minp.getValue();
				}
			}
			LengthProperty maxp = (LengthProperty) lenrange.getMaximum(null);
			if (maxp instanceof PercentLength)
			{
				maxfactor = maxfactor + ((PercentLength) maxp).value();
			} else
			{
				if (maxp.getEnum() != Constants.EN_AUTO)
				{
					max = max + maxp.getValue();
				}
			}
			LengthProperty optp = (LengthProperty) lenrange.getOptimum(null);
			if (optp instanceof PercentLength)
			{
				optfactor = optfactor + ((PercentLength) optp).value();
			} else
			{
				if (optp.getEnum() != Constants.EN_AUTO)
				{
					opt = opt + optp.getValue();
				}
			}
		}
		EnumLength initp = new EnumLength(Constants.EN_AUTO, null);
		boolean ispercentlength = lenranges[0].getOptimum(null) instanceof PercentLength;
		PercentBase base = null;
		if (ispercentlength)
		{
			base = ((PercentLength) lenranges[0].getOptimum(null))
					.getBaseLength();
		}
		LengthProperty minlen = initp;
		if (ispercentlength)
		{
			if (minfactor > 0)
			{
				minlen = new PercentLength(minfactor, base);
			}
		} else
		{
			if (min > 0)
			{
				minlen = new FixedLength(min);
			}
		}
		LengthProperty maxlen = initp;
		if (ispercentlength)
		{
			if (maxfactor > 0)
			{
				maxlen = new PercentLength(maxfactor, base);
			}
		} else
		{
			if (max > 0)
			{
				maxlen = new FixedLength(max);
			}
		}
		LengthProperty optlen = initp;
		if (ispercentlength)
		{
			if (optfactor > 0)
			{
				optlen = new PercentLength(optfactor, base);
			}
		} else
		{
			if (opt > 0)
			{
				optlen = new FixedLength(opt);
			}
		}
		return new LengthRangeProperty(minlen, optlen, maxlen);
	}

	/**
	 * 
	 * 将长度拆分成指定数目的长度
	 * 
	 * @param len
	 *            ：拆分前的原长度 count：拆分数
	 * @return
	 * @exception
	 */
	public static LengthRangeProperty divideLength(LengthRangeProperty len,
			int count)
	{
		LengthRangeProperty nlen = null;
		if (len != null && count > 0)
		{
			EnumLength initp = new EnumLength(Constants.EN_AUTO, null);
			LengthProperty minlen = initp;
			LengthProperty maxlen = initp;
			LengthProperty optlen = initp;
			LengthProperty minp = (LengthProperty) len.getMinimum(null);
			if (minp instanceof PercentLength)
			{
				PercentLength minperlen = (PercentLength) minp;
				minlen = new PercentLength(minperlen.value() / count, minperlen
						.getBaseLength());
			} else
			{
				if (minp.getEnum() != Constants.EN_AUTO)
				{
					minlen = new FixedLength(minp.getValue() / count);
				}
			}
			LengthProperty maxp = (LengthProperty) len.getMaximum(null);
			if (maxp instanceof PercentLength)
			{
				PercentLength maxperlen = (PercentLength) maxp;
				maxlen = new PercentLength(maxperlen.value() / count, maxperlen
						.getBaseLength());
			} else
			{
				if (maxp.getEnum() != Constants.EN_AUTO)
				{
					maxlen = new FixedLength(maxp.getValue() / count);
				}
			}
			LengthProperty optp = (LengthProperty) len.getOptimum(null);
			if (optp instanceof PercentLength)
			{
				PercentLength optperlen = (PercentLength) optp;
				optlen = new PercentLength(optperlen.value() / count, optperlen
						.getBaseLength());
			} else
			{
				if (optp.getEnum() != Constants.EN_AUTO)
				{
					optlen = new FixedLength(optp.getValue() / count);
				}
			}
			nlen = new LengthRangeProperty(minlen, optlen, maxlen);
		}
		return nlen;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof LengthRangeProperty))
		{
			return false;
		}
		LengthRangeProperty range = (LengthRangeProperty) obj;
		return minimum.equals(range.minimum) && optimum.equals(range.optimum)
				&& maximum.equals(range.maximum);
	}
}
