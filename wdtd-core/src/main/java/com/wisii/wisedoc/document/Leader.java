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
 * @Leader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.Map;

import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：leader对象
 *
 * 作者：zhangqiang
 * 创建日期：2009-4-10
 */
public class Leader extends InlineLevel
{

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public Leader()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public Leader(final Map<Integer, Object> attributes)
	{
		super(attributes);
		// TODO Auto-generated constructor stub
	}
    /**
     * @return the "rule-style" property.
     */
    public int getRuleStyle() {
    	
        return ((EnumProperty)getAttribute(Constants.PR_RULE_STYLE)).getEnum();
    }

    /**
     * @return the "rule-thickness" property.
     */
    public Length getRuleThickness() {
        return (Length) getAttribute(Constants.PR_RULE_THICKNESS);
    }

    /**
     * @return the "leader-alignment" property.
     */
    public int getLeaderAlignment() {
        return ((EnumProperty)getAttribute(Constants.PR_LEADER_ALIGNMENT)).getEnum();
    }

    /**
     * @return the "leader-length" property.
     */
    public LengthRangeProperty getLeaderLength() {
        return (LengthRangeProperty)getAttribute(Constants.PR_LEADER_LENGTH);
    }

    /**
     * @return the "leader-pattern" property.
     */
    public int getLeaderPattern() {
        return ((EnumProperty)getAttribute(Constants.PR_LEADER_PATTERN)).getEnum();
    }

    /**
     * @return the "leader-pattern-width" property.
     */
    public Length getLeaderPatternWidth() {
        return (Length) getAttribute(Constants.PR_LEADER_PATTERN_WIDTH);
    }

    /**
     * @return the "alignment-adjust" property
     */
    public Length getAlignmentAdjust() {
        return (Length) getAttribute(Constants.PR_ALIGNMENT_ADJUST);
    }

    /**
     * @return the "alignment-baseline" property
     */
    public int getAlignmentBaseline() {
        return ((EnumProperty)getAttribute(Constants.PR_ALIGNMENT_BASELINE)).getEnum();
    }

    /**
     * @return the "baseline-shift" property
     */
    public Length getBaselineShift() {
        return (Length) getAttribute(Constants.PR_BASELINE_SHIFT);
    }

    /**
     * @return the "dominant-baseline" property
     */
    public int getDominantBaseline() {
        return ((EnumProperty)getAttribute(Constants.PR_DOMINANT_BASELINE)).getEnum();
    }

    /** @see com.wisii.fov.fo.FONode#getLocalName() */
    public String getLocalName() {
        return "leader";
    }

    /**
     * @see com.wisii.fov.fo.FObj#getNameId()
     */
    public int getNameId() {
        return Constants.FO_LEADER;
    }

}
