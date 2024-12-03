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
 * @CommonMarginBlock.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：通用的Margin属性
 * 
 * 作者：zhangqiang 创建日期：2008-8-21
 */
public final class CommonMarginBlock extends AbstractCommonAttributes
{

	/**
	 * The "margin-top" property.
	 */
	private Length marginTop;

	/**
	 * The "margin-bottom" property.
	 */
	private Length marginBottom;

	/**
	 * The "margin-left" property.
	 */
	private Length marginLeft;

	/**
	 * The "margin-right" property.
	 */
	private Length marginRight;

	/**
	 * The "space-before" property.
	 */
	private SpaceProperty spaceBefore;

	/**
	 * The "space-after" property.
	 */
	private SpaceProperty spaceAfter;

	/**
	 * The "start-indent" property.
	 */
	private Length startIndent;

	/**
	 * The "end-indent" property.
	 */
	private Length endIndent;

	public CommonMarginBlock(Length marginTop, Length marginBottom,
			Length marginLeft, Length marginRight, SpaceProperty spaceBefore,
			SpaceProperty spaceAfter, Length startIndent, Length endIndent)
	{
		super(null);
		if (marginTop != null)
		{
			this.marginTop = marginTop;
		} else
		{
			this.marginTop = (Length) InitialManager.getInitialValue(
					Constants.PR_MARGIN_TOP, null);
		}
		if (marginBottom != null)
		{
			this.marginBottom = marginBottom;
		} else
		{
			this.marginBottom = (Length) InitialManager.getInitialValue(
					Constants.PR_MARGIN_BOTTOM, null);
		}
		if (marginLeft != null)
		{
			this.marginLeft = marginLeft;
		} else
		{
			this.marginLeft = (Length) InitialManager.getInitialValue(
					Constants.PR_MARGIN_LEFT, null);
		}
		if (marginRight != null)
		{
			this.marginRight = marginRight;
		} else
		{
			this.marginRight = (Length) InitialManager.getInitialValue(
					Constants.PR_MARGIN_RIGHT, null);
		}
		if (spaceBefore != null)
		{
			this.spaceBefore = spaceBefore;
		} else
		{
			this.spaceBefore = (SpaceProperty) InitialManager.getInitialValue(
					Constants.PR_SPACE_BEFORE, null);
		}
		if (spaceAfter != null)
		{
			this.spaceAfter = spaceAfter;
		} else
		{
			this.spaceAfter = (SpaceProperty) InitialManager.getInitialValue(
					Constants.PR_SPACE_AFTER, null);
		}
		if (startIndent != null)
		{
			this.startIndent = startIndent;
		} else
		{
			this.startIndent = (Length) InitialManager.getInitialValue(
					Constants.PR_START_INDENT, null);
		}
		if (endIndent != null)
		{
			this.endIndent = endIndent;
		} else
		{
			this.endIndent = (Length) InitialManager.getInitialValue(
					Constants.PR_END_INDENT, null);
		}
	}

	public CommonMarginBlock(CellElement cellelement)
	{
		super(cellelement);
		init(cellelement);

	}

	@Override
	public void init(CellElement cellelement)
	{
		// TODO Auto-generated method stub
		super.init(cellelement);
		marginTop = (Length) getAttribute(Constants.PR_MARGIN_TOP);
		marginBottom = (Length) getAttribute(Constants.PR_MARGIN_BOTTOM);
		marginLeft = (Length) getAttribute(Constants.PR_MARGIN_LEFT);
		marginRight = (Length) getAttribute(Constants.PR_MARGIN_RIGHT);
		spaceBefore = (SpaceProperty) getAttribute(Constants.PR_SPACE_BEFORE);
		spaceAfter = (SpaceProperty) getAttribute(Constants.PR_SPACE_AFTER);
		startIndent = (Length) getAttribute(Constants.PR_START_INDENT);
		endIndent = (Length) getAttribute(Constants.PR_END_INDENT);
		if (cellelement instanceof BlockContainer)
		{
			CondLengthProperty padingstart = (CondLengthProperty) getAttribute(Constants.PR_PADDING_START);
			CondLengthProperty padingend = (CondLengthProperty) getAttribute(Constants.PR_PADDING_END);
			if (startIndent == null)
			{
				startIndent = padingstart.getLength();
			} else if (padingstart != null)
			{
				startIndent = new FixedLength(startIndent.getValue()
						+ padingstart.getLengthValue());
			}
			if (endIndent == null)
			{
				endIndent = padingend.getLength();
			} else if (padingend != null)
			{
				endIndent = new FixedLength(endIndent.getValue()
						+ padingend.getLengthValue());
			}
		}
	}

	/**
	 * @返回 marginTop变量的值
	 */
	public final Length getMarginTop()
	{
		return marginTop;
	}

	/**
	 * @返回 marginBottom变量的值
	 */
	public final Length getMarginBottom()
	{
		return marginBottom;
	}

	/**
	 * @返回 marginLeft变量的值
	 */
	public final Length getMarginLeft()
	{
		return marginLeft;
	}

	/**
	 * @返回 marginRight变量的值
	 */
	public final Length getMarginRight()
	{
		return marginRight;
	}

	/**
	 * @返回 spaceBefore变量的值
	 */
	public final SpaceProperty getSpaceBefore()
	{
		return spaceBefore;
	}

	/**
	 * @返回 spaceAfter变量的值
	 */
	public final SpaceProperty getSpaceAfter()
	{
		return spaceAfter;
	}

	/**
	 * @返回 startIndent变量的值
	 */
	public final Length getStartIndent()
	{
		return startIndent;
	}

	/**
	 * @返回 endIndent变量的值
	 */
	public final Length getEndIndent()
	{
		return endIndent;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof CommonMarginBlock))
		{
			return false;
		}
		CommonMarginBlock cmb = (CommonMarginBlock) obj;
		return marginTop == null ? cmb.marginTop == null
				: marginTop.equals(cmb.marginTop) && marginBottom == null ? cmb.marginBottom == null
						: marginBottom.equals(cmb.marginBottom)
								&& marginLeft == null ? cmb.marginLeft == null
								: marginLeft.equals(cmb.marginLeft)
										&& marginRight == null ? cmb.marginRight == null
										: marginRight.equals(cmb.marginRight)
												&& spaceBefore == null ? cmb.spaceBefore == null
												: spaceBefore
														.equals(cmb.spaceBefore)
														&& spaceAfter == null ? cmb.spaceAfter == null
														: spaceAfter
																.equals(cmb.spaceAfter)
																&& startIndent == null ? cmb.startIndent == null
																: startIndent
																		.equals(cmb.startIndent)
																		&& endIndent == null ? cmb.endIndent == null
																		: endIndent
																				.equals(cmb.endIndent);
	}

	public CommonMarginBlock clone()
	{
		Length marginTop = this.marginTop.clone();

		Length marginBottom = this.marginBottom.clone();

		Length marginLeft = this.marginLeft.clone();

		Length marginRight = this.marginRight.clone();

		SpaceProperty spaceBefore = this.spaceBefore.clone();
		SpaceProperty spaceAfter = this.spaceAfter.clone();
		Length startIndent = this.startIndent.clone();

		Length endIndent = this.endIndent.clone();

		CommonMarginBlock cmb = new CommonMarginBlock(marginTop, marginBottom,
				marginLeft, marginRight, spaceBefore, spaceAfter, startIndent,
				endIndent);
		return cmb;
	}
}
