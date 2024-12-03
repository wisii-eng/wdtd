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

/* $Id: CommonFont.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.PercentBaseContext;
import com.wisii.wisedoc.fonts.Font;
import com.wisii.wisedoc.fonts.FontInfo;
import com.wisii.wisedoc.fonts.FontTriplet;

/**
 * 
 * 类功能描述：字体属性信息类
 * 
 * 作者：zhangqiang 创建日期：2008-8-25
 */
public final class CommonFont extends AbstractCommonAttributes
{
	private Font _fontState;

	// public CommonFont(Attributes attribute)
	// {
	// String fontfamily = (String) attribute
	// .getAttribute(Constants.PR_FONT_FAMILY);
	// String[] fontfamilys = new String[1];
	// fontfamilys[0] = fontfamily;
	// int fontselectionStrategy = (Integer) attribute
	// .getAttribute(Constants.PR_FONT_SELECTION_STRATEGY);
	// Length fontsize = (Length) attribute
	// .getAttribute(Constants.PR_FONT_SIZE);
	// int fontstretch = (Integer) attribute
	// .getAttribute(Constants.PR_FONT_STRETCH);
	// Numeric fontsizeAdjust = (Numeric) attribute
	// .getAttribute(Constants.PR_FONT_SIZE_ADJUST);
	// int fontStyle = (Integer) attribute
	// .getAttribute(Constants.PR_FONT_STYLE);
	// int fontvariant = (Integer) attribute
	// .getAttribute(Constants.PR_FONT_VARIANT);
	// int fontweight = (Integer) attribute
	// .getAttribute(Constants.PR_FONT_WEIGHT);
	// init(fontfamilys, fontselectionStrategy, fontsize, fontstretch,
	// fontsizeAdjust, fontStyle, fontvariant, fontweight);
	// }

	/**
	 * 
	 * 初始化过程的描述, 传入单项的属性值，生成CommonFont类对象
	 * 
	 * @param
	 * @exception
	 */
	public CommonFont(CellElement cellelement)
	{
		super(cellelement);
	}

	/** @return the first font-family name in the list */
	public String getFirstFontFamily()
	{
		return (String) getAttribute(Constants.PR_FONT_FAMILY);
	}

	/** @return the font-family names */
	public String[] getFontFamily()
	{
		String fontfamily = (String) getAttribute(Constants.PR_FONT_FAMILY);
		String[] fontfamilys = new String[1];
		fontfamilys[0] = fontfamily;
		return fontfamilys;
	}

	/**
	 * Create and return a Font object based on the properties.
	 * 
	 * @param fontInfo
	 * @return a Font object.
	 */
	public Font getFontState(FontInfo fontInfo, PercentBaseContext context)
	{
		String fontfamily = (String) getAttribute(Constants.PR_FONT_FAMILY);
		String[] fontfamilys = new String[1];
		fontfamilys[0] = fontfamily;

		Length fontsize = (Length) getAttribute(Constants.PR_FONT_SIZE);
		int fontStyle = (Integer) getAttribute(Constants.PR_FONT_STYLE);
		int fontweight = (Integer) getAttribute(Constants.PR_FONT_WEIGHT);
		if (_fontState == null)
		{
			/** @todo this is ugly. need to improve. */

			int font_weight = 400;
			if (fontweight == Constants.EN_BOLDER)
			{
				// +100 from inherited
			} else if (fontweight == Constants.EN_LIGHTER)
			{
				// -100 from inherited
			} else
			{
				switch (fontweight)
				{
				case Constants.EN_100:
					font_weight = 100;
					break;
				case Constants.EN_200:
					font_weight = 200;
					break;
				case Constants.EN_300:
					font_weight = 300;
					break;
				case Constants.EN_400:
					font_weight = 400;
					break;
				case Constants.EN_500:
					font_weight = 500;
					break;
				case Constants.EN_600:
					font_weight = 600;
					break;
				case Constants.EN_700:
					font_weight = 700;
					break;
				case Constants.EN_800:
					font_weight = 800;
					break;
				case Constants.EN_900:
					font_weight = 900;
					break;
				}
			}

			String style;
			switch (fontStyle)
			{
			case Constants.EN_ITALIC:
				style = "italic";
				break;
			case Constants.EN_OBLIQUE:
				style = "oblique";
				break;
			case Constants.EN_BACKSLANT:
				style = "backslant";
				break;
			default:
				style = "normal";
			}
			// NOTE: this is incomplete. font-size may be specified with
			// various kinds of keywords too
			// int fontVariant = propertyList.get("font-variant").getEnum();
			FontTriplet triplet = fontInfo.fontLookup(getFontFamily(), style,
					font_weight);
			_fontState = fontInfo.getFontInstance(triplet, fontsize
					.getValue(context));
		}
		return _fontState;
	}

	@Override
	public void init(CellElement cellelements)
	{
		super.init(cellelements);
		this._fontState = null;
	}

}
