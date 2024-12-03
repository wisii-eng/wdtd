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
 * @SpaceWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.attribute;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.Property;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-5
 */
public class SpaceWriter extends OutputAttributeWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter#write(java.lang
	 * .Object)
	 */
	public String write(int key, Object value)
	{
		StringBuffer output = new StringBuffer();
		String attributeName = "";
		attributeName = getKeyName(key);
		if (!"".equalsIgnoreCase(attributeName))
		{
			SpaceProperty spaceValue = (SpaceProperty) value;
			EnumNumber precedence = spaceValue.getPrecedence();
			EnumProperty conditionality = spaceValue.getConditionality();
			Property propertyMax = spaceValue.getMaximum(null);
			Property propertyMin = spaceValue.getMinimum(null);
			Property propertyOpt = spaceValue.getOptimum(null);
			if (propertyMax instanceof FixedLength
					&& propertyMin instanceof FixedLength
					&& propertyOpt instanceof FixedLength)
			{
				FixedLength fixedLengthMax = (FixedLength) propertyMax;
				if (propertyMax.equals(propertyMin)
						&& propertyMin.equals(propertyOpt))
				{
					String valueStr = IoXslUtil.removePoint(fixedLengthMax
							.getLengthValueString())
							+ fixedLengthMax.getUnits();
					if (!valueStr.equalsIgnoreCase("0pt")
							&& !valueStr.equalsIgnoreCase("0mm")
							&& !valueStr.equalsIgnoreCase("0cm"))
					{
						output.append(ElementUtil.outputAttributes(
								attributeName, valueStr));
					}
				} else
				{
					FixedLength fixedLengthMin = (FixedLength) propertyMin;
					FixedLength fixedLengthOpt = (FixedLength) propertyOpt;
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.MAXIMUM, IoXslUtil
							.removePoint(fixedLengthMax.getLengthValueString())
							+ fixedLengthMax.getUnits()));
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.MINIMUM, IoXslUtil
							.removePoint(fixedLengthMin.getLengthValueString())
							+ fixedLengthMin.getUnits()));
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.OPTIMUM, IoXslUtil
							.removePoint(fixedLengthOpt.getLengthValueString())
							+ fixedLengthOpt.getUnits()));
				}
			} else if (propertyMax instanceof PercentLength
					&& propertyMin instanceof PercentLength
					&& propertyOpt instanceof PercentLength)
			{
				PercentLength fixedLengthMax = (PercentLength) propertyMax;
				if (propertyMax.equals(propertyMin)
						&& propertyMin.equals(propertyOpt))
				{
					output.append(ElementUtil.outputAttributes(attributeName,
							IoXslUtil.removePoint(new PercentLengthWriter()
									.write(fixedLengthMax))));

				} else
				{
					PercentLength fixedLengthMin = (PercentLength) propertyMin;
					PercentLength fixedLengthOpt = (PercentLength) propertyOpt;
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.MAXIMUM, IoXslUtil
							.removePoint(new PercentLengthWriter()
									.write(fixedLengthMax))));
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.MINIMUM, IoXslUtil
							.removePoint(new PercentLengthWriter()
									.write(fixedLengthMin))));
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.OPTIMUM, IoXslUtil
							.removePoint(new PercentLengthWriter()
									.write(fixedLengthOpt))));
				}
			}
			if (key != Constants.PR_LINE_HEIGHT)
			{
				String attributePrecedenceValue = "";
				// 此处被注释掉的代码才是正确的，但是因为目前修改SpaceProperty的初始值，
				// 受影响的地方太多，担心出其它问题，所以在此处做不按套路的转换
				// precedence的初始值应为 new EnumNumber(Constants.EN_FORCE, 0);
				// if (precedence.getEnum() == Constants.EN_FORCE)
				// {
				// attributePrecedenceValue = "force";
				// } else
				// {
				// attributePrecedenceValue = precedence.getNumber() + "";
				// }
				if (precedence.getEnum() == Constants.EN_DISCARD)
				{
					attributePrecedenceValue = "force";
				} else
				{
					attributePrecedenceValue = precedence.getNumber() + "";
				}
				String attributeConditionalityValue = conditionality != null ? new EnumPropertyWriter()
						.write(conditionality.getEnum())
						: "";
				// 此处被注释掉的代码才是正确的，但是因为目前修改SpaceProperty的初始值，
				// 受影响的地方太多，担心出其它问题，所以在此处做不按套路的转换
				// if (precedence != null
				// && precedence.getEnum() != Constants.EN_FORCE
				// && !attributeConditionalityValue
				// .equalsIgnoreCase(FoXsltConstants.DISCARD))
				if (precedence != null
						&& precedence.getEnum() != Constants.EN_DISCARD
						&& !attributeConditionalityValue
								.equalsIgnoreCase(FoXsltConstants.DISCARD))
				{
					if (!"".equalsIgnoreCase(attributePrecedenceValue))
					{
						output.append(ElementUtil.outputAttributes(" "
								+ attributeName + "."
								+ FoXsltConstants.PRECEDENCE,
								attributePrecedenceValue));
					}
					if (!"".equalsIgnoreCase(attributeConditionalityValue))
					{
						output.append(ElementUtil.outputAttributes(" "
								+ attributeName + "."
								+ FoXsltConstants.CONDITIONALITY,
								attributeConditionalityValue));
					}
				}
			}
		}
		return output.toString();
	}
}
