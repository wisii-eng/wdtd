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

package com.wisii.wisedoc.view.ui.actions.paragraph;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 设置段落级别的边框动作
 * 
 * @author 闫舒寰
 * @since 2008/10/6
 */
public class ParagraphBorderStyleAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JComboBox)
		{
			JComboBox jb = (JComboBox) e.getSource();

			/*
			 * 边框的具体效果，详见XSL规范7.8.20
			 */
			switch ((Integer) jb.getSelectedIndex())
			{
				// 无边框
				case 0:
					setBorderStyle(Constants.EN_NONE);
					break;
				// 隐藏
				case 1:
					setBorderStyle(Constants.EN_HIDDEN);
					break;
				// The border is a series of dots.
				case 2:
					setBorderStyle(Constants.EN_DOTTED);
					break;
				// The border is a series of short line segments
				case 3:
					setBorderStyle(Constants.EN_DASHED);
					break;
				// The border is a single line segment.
				case 4:
					setBorderStyle(Constants.EN_SOLID);
					break;
				// The border is tow solid lines. The sum of the two lines and
				// the space between them equals the values of 'border-width'.
				case 5:
					setBorderStyle(Constants.EN_DOUBLE);
					break;
				// The border looks as though it were carved into the canvas.
				case 6:
					setBorderStyle(Constants.EN_GROOVE);
					break;
				// The opposite of 'groove': the border looks as though it were
				// coming out of the canvas.
				case 7:
					setBorderStyle(Constants.EN_RIDGE);
					break;
				// The border makes the entire box look as though it were
				// embedded in the canvas
				case 8:
					setBorderStyle(Constants.EN_INSET);
					break;
				// The opposite of 'inst': the border makes th eentire box look
				// as though it were coming out of the canvas
				case 9:
					setBorderStyle(Constants.EN_OUTSET);
					break;

				default:
					break;
			}
		}

	}

	private void setBorderStyle(int style)
	{

		// 设置四个边的样式
		Map<Integer, Object> properties = new HashMap<Integer, Object>();
		EnumProperty ep = new EnumProperty(style, "NONE");
		properties.put(Constants.PR_BORDER_BEFORE_STYLE, ep);
		properties.put(Constants.PR_BORDER_AFTER_STYLE, ep);
		properties.put(Constants.PR_BORDER_START_STYLE, ep);
		properties.put(Constants.PR_BORDER_END_STYLE, ep);

		setFOProperties(properties);

		/*
		 * setFOProperty(propertyType, Constants.PR_BORDER_BEFORE_STYLE, new
		 * EnumProperty(style, "NONE")); setFOProperty(propertyType,
		 * Constants.PR_BORDER_AFTER_STYLE, new EnumProperty(style, "NONE"));
		 * setFOProperty(propertyType, Constants.PR_BORDER_START_STYLE, new
		 * EnumProperty(style, "NONE")); setFOProperty(propertyType,
		 * Constants.PR_BORDER_END_STYLE, new EnumProperty(style, "NONE"));
		 */
	}

}
