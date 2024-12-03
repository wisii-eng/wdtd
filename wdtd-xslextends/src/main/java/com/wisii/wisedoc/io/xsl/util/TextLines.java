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

package com.wisii.wisedoc.io.xsl.util;
public class TextLines
{
	public int getLines(String text, String number)
	{
		int onelinenumber = new Integer(number);
		int lines = 0;
		String realtext = text.replaceAll("&lt;", "<");
		realtext = realtext.replaceAll("&gt;", ">");
		realtext = realtext.replaceAll("&apos;", "'");
		realtext = realtext.replaceAll("&quot;", "\"");
		realtext = realtext.replaceAll("&#13;&#10;", "\n");
		realtext = realtext.replaceAll("&#10;&#13;", "\n");
		realtext = realtext.replaceAll("&#13;", "\n");
		realtext = realtext.replaceAll("&#10;", "\n");
		realtext = realtext.replaceAll("&amp;", "&");
		realtext = realtext.replaceAll("\r\n", "\n");
		while (realtext.contains("\n\n"))
		{
			realtext = realtext.replaceAll("\n\n", "\n \n");
		}
		String[] blocks = realtext.split("\n");
		if (blocks != null && blocks.length > 0)
		{
			for (int i = 0; i < blocks.length; i++)
			{
				int currentlines = getCurrentLines(blocks[i], onelinenumber);
				lines = lines + currentlines;
			}
		}
		return lines;
	}

	private int getCurrentLines(String text, int onelinenumber)
	{
		int lines = 0;
		int length = text.length();
		if (length == 0)
		{
			lines = lines + 1;
		} else
		{
			lines = getReal(onelinenumber, text);
		}
		return lines;
	}

	private int getReal(int onelinenumber, String text)
	{
		String first = "$([{·‘“〈《「『【〔〖〝﹙﹛﹝＄（．［｛￡￥\"";

		String last = "%),.:;>?]}¨°·ˇˉ―‖’”…‰′″℃∶、。〃〉》」』】〕〗〞︶︺︾﹀﹄﹚﹜﹞！＂％＇），．：；？］｀｜｝～￠";

		String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
		int length = text.length();
		int start = 0;
		int lines = 0;
		int lianxu = 0;
		for (int i = 0; i < length; i++)
		{
			String current = text.substring(i, i + 1);

			if (i == length - 1)
			{
				if (!(start == 0 && last.contains(current)))
				{
					lines = lines + 1;
				}
				start = 0;
			} else
			{
				if (ALPHANUMERIC.contains(current))
				{
					lianxu = lianxu + 1;
				} else
				{
					lianxu = 0;
				}
				if (start + 1 < onelinenumber)
				{
					start = start + 1;
				} else
				{
					if (lianxu == 0)
					{
						lines = lines + 1;
						if (first.contains(current))
						{
							start = 1;
						} else
						{
							start = 0;
						}
					} else
					{
						String next = text.substring(i, i + 1);
						if (ALPHANUMERIC.contains(next))
						{
							if (lianxu != start)
							{
								lines = lines + 1;
								start = lianxu;
							} else
							{
								if (start == onelinenumber)
								{
									lines = lines + 1;
								}
								start = lianxu;
							}
							if (start >= onelinenumber)
							{
								lines = lines - 1;
							}
						} else
						{
							lines = lines + 1;
							lianxu = 0;
							if (first.contains(next))
							{
								start = 1;
							} else
							{
								start = 0;
							}
						}
					}
				}
			}
		}
		if (start > 0)
		{
			lines = lines + 1;
		}
		return lines;
	}
}
