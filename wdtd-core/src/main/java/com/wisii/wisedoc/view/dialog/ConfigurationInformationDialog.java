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

package com.wisii.wisedoc.view.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.view.panel.ProfileCard;

@SuppressWarnings("serial")
public class ConfigurationInformationDialog extends JFrame
{

	public final static String RROFILE_PATH = "com/wisii/wisedoc/configure/configure.properties";

	private static boolean isLoadROF = false;

	private static Properties configurefile = new Properties();
	static
	{
		try
		{
			configurefile.load(ConfigurationInformationDialog.class.getClassLoader()
					.getResourceAsStream(RROFILE_PATH));
			setLoadROF(true);
		} catch (FileNotFoundException e)
		{
			LogUtil.debug(e);
			setLoadROF(false);
		} catch (IOException e)
		{
			LogUtil.debug(e);
			setLoadROF(false);
		} catch (Exception e)
		{
			LogUtil.debug(e);
			setLoadROF(false);
		}
	}

	XYLayout layout = new XYLayout();
	{
		layout.setHeight(800);
		layout.setWidth(800);
	}

	Dimension dimesion = new Dimension(800, 800);

	JButton ok = new JButton("确定");
	{
		ok.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				save();
				isChanged();
				dispose();
			}
		});
	}

	JButton cancel = new JButton("取消");
	{
		cancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
	}

	public ConfigurationInformationDialog()
	{
		setTitle("配置信息设置");
		setSize(dimesion);
		setLayout(layout);
		ProfileCard cards = new ProfileCard();
		add(cards, new XYConstraints(0, 0, 800, 700));
		add(ok, new XYConstraints(500, 720, 80, 30));
		add(cancel, new XYConstraints(600, 720, 80, 30));
	}

	public static void addProFileItem(String key, String value)
	{
		Properties recentopenfile = getConfigurefile();
		if (key == null)
		{
			return;
		}
		if (recentopenfile.containsKey(key))
		{
			String oldValue = recentopenfile.getProperty(key);
			if (!oldValue.equalsIgnoreCase(value))
			{
				recentopenfile.remove(key);
				recentopenfile.setProperty(key, value);
			}
		} else
		{
			recentopenfile.setProperty(key, value);
		}
	}

	public void save()
	{
		// chushihua();
		if (getConfigurefile() != null)
			try
			{
				getConfigurefile().store(
						new FileOutputStream(new File(ConfigurationInformationDialog.class
								.getClassLoader().getResource(RROFILE_PATH)
								.getPath())), null);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
	}

	public void chushihua()
	{
		Properties recentopenfile = getConfigurefile();
		String auto = "9";
		recentopenfile.setProperty("PR_ABSOLUTE_POSITION", auto);
		recentopenfile.setProperty("PR_FONT_SELECTION_STRATEGY", auto);
		recentopenfile.setProperty("PR_ALIGNMENT_BASELINE", auto);
		recentopenfile.setProperty("PR_DISPLAY_ALIGN", auto);
		recentopenfile.setProperty("PR_DOMINANT_BASELINE", auto);
		recentopenfile.setProperty("PR_SCALING_METHOD", auto);
		recentopenfile.setProperty("PR_HYPHENATION_KEEP", auto);
		recentopenfile.setProperty("PR_SUPPRESS_AT_LINE_BREAK", auto);
		recentopenfile.setProperty("PR_TREAT_AS_WORD_SPACE", auto);
		recentopenfile.setProperty("PR_RENDERING_INTENT", auto);
		recentopenfile.setProperty("PR_INTRUSION_DISPLACE", auto);
		recentopenfile.setProperty("PR_BREAK_AFTER", auto);
		recentopenfile.setProperty("PR_BREAK_BEFORE", auto);
		recentopenfile.setProperty("PR_OVERFLOW", auto);
		recentopenfile.setProperty("PR_CLIP", auto);
		recentopenfile.setProperty("PR_LETTER_VALUE", auto);
		recentopenfile.setProperty("PR_FORCE_PAGE_COUNT", auto);
		recentopenfile.setProperty("PR_INITIAL_PAGE_NUMBER", auto);
		recentopenfile.setProperty("PR_FORCE_PAGE_COUNT", auto);
		recentopenfile.setProperty("PR_MEDIA_USAGE", auto);
		recentopenfile.setProperty("PR_PAGE_HEIGHT", auto);
		recentopenfile.setProperty("PR_PAGE_WIDTH", auto);
		recentopenfile.setProperty("PR_TABLE_LAYOUT", auto);
		recentopenfile.setProperty("PR_CONTENT_TYPE", auto);
		recentopenfile.setProperty("PR_TOP", auto);
		recentopenfile.setProperty("PR_RIGHT", auto);
		recentopenfile.setProperty("PR_BOTTOM", auto);
		recentopenfile.setProperty("PR_LEFT", auto);
		recentopenfile.setProperty("PR_BACKGROUND_POSITION_HORIZONTAL", "73");
		recentopenfile.setProperty("PR_BACKGROUND_POSITION_VERTICAL", "145");
		recentopenfile.setProperty("PR_FONT_FAMILY", "宋体");
		recentopenfile.setProperty("PR_FONT_SIZE", "12,pt");
		recentopenfile.setProperty("PR_FONT_STRETCH", "97");
		recentopenfile.setProperty("PR_FONT_STYLE", "97");
		recentopenfile.setProperty("PR_FONT_WEIGHT", "97");
		//
		String enumlength = "9,0";
		recentopenfile.setProperty("PR_ALIGNMENT_ADJUST", enumlength);
		recentopenfile.setProperty("PR_BASELINE_SHIFT", enumlength);
		recentopenfile
				.setProperty("PR_BLOCK_PROGRESSION_DIMENSION", enumlength);
		recentopenfile.setProperty("PR_CONTENT_HEIGHT", enumlength);
		recentopenfile.setProperty("PR_CONTENT_WIDTH", enumlength);
		recentopenfile.setProperty("PR_HEIGHT", enumlength);
		recentopenfile.setProperty("PR_INLINE_PROGRESSION_DIMENSION",
				enumlength);
		recentopenfile.setProperty("PR_WIDTH", enumlength);
		recentopenfile.setProperty("PR_INLINE_PROGRESSION_DIMENSION",
				enumlength);
		recentopenfile
				.setProperty("PR_BLOCK_PROGRESSION_DIMENSION", enumlength);
		//
		recentopenfile.setProperty("PR_FONT_VARIANT", "97");
		recentopenfile.setProperty("PR_RELATIVE_POSITION", "136");
		recentopenfile.setProperty("PR_RELATIVE_ALIGN", "13");
		recentopenfile.setProperty("PR_APHLA", "0");
		recentopenfile.setProperty("PR_SCALING", "154");
		recentopenfile.setProperty("PR_HYPHENATION_LADDER_COUNT", "89,-1");
		recentopenfile.setProperty("PR_LINE_STACKING_STRATEGY", "83");
		recentopenfile.setProperty("PR_LINEFEED_TREATMENT", "97");
		recentopenfile.setProperty("PR_WHITE_SPACE_TREATMENT", "63");
		recentopenfile.setProperty("PR_TEXT_ALIGN", "135");
		recentopenfile.setProperty("PR_TEXT_ALIGN_LAST", "135");
		recentopenfile.setProperty("PR_WHITE_SPACE_COLLAPSE", "48");
		recentopenfile.setProperty("PR_WRAP_OPTION", "161");
		recentopenfile.setProperty("PR_LETTER_SPACING", "97");
		recentopenfile.setProperty("PR_WORD_SPACING", "97");
		String keep = "9,9,9";
		recentopenfile.setProperty("PR_KEEP_TOGETHER", keep);
		recentopenfile.setProperty("PR_KEEP_WITH_NEXT", keep);
		recentopenfile.setProperty("PR_KEEP_WITH_PREVIOUS", keep);
		recentopenfile.setProperty("PR_WIDOWS", "2");
		recentopenfile.setProperty("PR_ORPHANS", "2");
		recentopenfile.setProperty("PR_REFERENCE_ORIENTATION", "0");
		recentopenfile.setProperty("PR_SPAN", "95");
		recentopenfile.setProperty("PR_LEADER_PATTERN", "134");
		recentopenfile.setProperty("PR_LEADER_PATTERN_WIDTH", "156");
		recentopenfile.setProperty("PR_RULE_STYLE", "133");
		recentopenfile.setProperty("PR_RULE_THICKNESS", "1,pt");
		recentopenfile.setProperty("PR_AUTO_RESTORE", "48");
		recentopenfile.setProperty("PR_INDICATE_DESTINATION", "48");
		recentopenfile.setProperty("PR_STARTING_STATE", "130");
		recentopenfile.setProperty("PR_RETRIEVE_BOUNDARY", "105");
		recentopenfile.setProperty("PR_EXTERNAL_DESTINATION", "");
		recentopenfile.setProperty("PR_INTERNAL_DESTINATION", "");
		recentopenfile.setProperty("PR_INDEX_CLASS", "");
		recentopenfile.setProperty("PR_MARKER_CLASS_NAME", "");
		recentopenfile.setProperty("PR_RETRIEVE_CLASS_NAME", "");
		recentopenfile.setProperty("PR_FORMAT", "1");
		recentopenfile.setProperty("PR_BLANK_OR_NOT_BLANK", "8");
		recentopenfile.setProperty("PR_COLUMN_COUNT", "1");
		recentopenfile.setProperty("PR_COLUMN_GAP", "12,pt");
		recentopenfile.setProperty("PR_PROVISIONAL_LABEL_SEPARATION", "6,pt");
		recentopenfile.setProperty("PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS",
				"24,pt");
		recentopenfile.setProperty("PR_BORDER_SEPARATION", "0,pt,0,pt");
		recentopenfile.setProperty("PR_MASTER_NAME", "");
		recentopenfile.setProperty("PR_MASTER_REFERENCE", "");
		recentopenfile.setProperty("PR_MAXIMUM_REPEATS", "89");
		recentopenfile.setProperty("PR_ODD_OR_EVEN", "8");
		recentopenfile.setProperty("PR_PAGE_POSITION", "8");
		recentopenfile.setProperty("PR_PRECEDENCE", "48");
		recentopenfile.setProperty("PR_CAPTION_SIDE", "13");
		recentopenfile.setProperty("PR_ENDS_ROW", "48");
		recentopenfile.setProperty("PR_NUMBER_COLUMNS_REPEATED", "1");
		recentopenfile.setProperty("PR_NUMBER_COLUMNS_SPANNED", "1");
		recentopenfile.setProperty("PR_NUMBER_ROWS_SPANNED", "1");
		recentopenfile.setProperty("PR_STARTS_ROW", "48");
		recentopenfile.setProperty("PR_TABLE_OMIT_HEADER_AT_BREAK", "48");
		recentopenfile.setProperty("PR_TABLE_OMIT_FOOTER_AT_BREAK", "48");
		recentopenfile.setProperty("PR_DIRECTION", "80");
		recentopenfile.setProperty("PR_TEXT_ALTITUDE", "156");
		recentopenfile.setProperty("PR_TEXT_DEPTH", "156");
		recentopenfile.setProperty("PR_UNICODE_BIDI", "97");
		recentopenfile.setProperty("PR_TEXT_ALTITUDE", "156");
		recentopenfile.setProperty("PR_SCORE_SPACES", "149");
		recentopenfile.setProperty("PR_VISIBILITY", "159");
		recentopenfile.setProperty("PR_WRITING_MODE", "79");
		recentopenfile.setProperty("PR_BORDER_COLLAPSE", "129");
		recentopenfile.setProperty("PR_EMPTY_CELLS", "130");
		recentopenfile.setProperty("PR_BARCODE_TYPE", "188");
		recentopenfile.setProperty("PR_COLUMN_NUMBER", "1");
		recentopenfile.setProperty("PR_DATETIMEFORMAT", "1,1,1,1,1,1");

		//
		String none = "95";
		recentopenfile.setProperty("PR_BACKGROUND_ATTACHMENT", "126");
		recentopenfile.setProperty("PR_BORDER_BEFORE_STYLE", none);
		recentopenfile.setProperty("PR_BORDER_AFTER_STYLE", none);
		recentopenfile.setProperty("PR_BORDER_START_STYLE", none);
		recentopenfile.setProperty("PR_BORDER_END_STYLE", none);
		recentopenfile.setProperty("PR_HYPHENATE", "48");
		recentopenfile.setProperty("PR_SOURCE_DOCUMENT", none);
		recentopenfile.setProperty("PR_ROLE", none);
		recentopenfile.setProperty("PR_TEXT_TRANSFORM", none);
		recentopenfile.setProperty("PR_COLOR", "0,0,0,0");
		recentopenfile.setProperty("PR_TEXT_DECORATION", "0");
		StringBuffer sbu = new StringBuffer();
		sbu.append((char) 0x2010);
		recentopenfile.setProperty("PR_HYPHENATION_CHARACTER", sbu.toString());
		recentopenfile.setProperty("PR_HYPHENATION_PUSH_CHARACTER_COUNT",
				"-1,2");
		recentopenfile.setProperty("PR_HYPHENATION_REMAIN_CHARACTER_COUNT",
				"-1,2");
		recentopenfile.setProperty("PR_BACKGROUND_REPEAT", "112");
		//
		String length = "0d,pt,true";
		recentopenfile.setProperty("PR_PADDING_BEFORE", length);
		recentopenfile.setProperty("PR_PADDING_AFTER", length);
		recentopenfile.setProperty("PR_PADDING_START", length);
		recentopenfile.setProperty("PR_PADDING_END", length);
		recentopenfile.setProperty("PR_PADDING_TOP", length);
		recentopenfile.setProperty("PR_PADDING_BOTTOM", length);
		recentopenfile.setProperty("PR_PADDING_LEFT", length);
		recentopenfile.setProperty("PR_PADDING_RIGHT", length);
		//
		String fixlength = "0,pt";
		recentopenfile.setProperty("PR_MARGIN_TOP", fixlength);
		recentopenfile.setProperty("PR_MARGIN_BOTTOM", fixlength);
		recentopenfile.setProperty("PR_MARGIN_LEFT", fixlength);
		recentopenfile.setProperty("PR_MARGIN_RIGHT", fixlength);
		recentopenfile.setProperty("PR_EXTENT", fixlength);
		recentopenfile
				.setProperty("PR_DESTINATION_PLACEMENT_OFFSET", fixlength);
		recentopenfile.setProperty("PR_TEXT_INDENT", fixlength);
		recentopenfile.setProperty("PR_LAST_LINE_END_INDENT", fixlength);
		recentopenfile.setProperty("PR_MIN_HEIGHT", fixlength);
		recentopenfile.setProperty("PR_END_INDENT", fixlength);
		recentopenfile.setProperty("PR_START_INDENT", fixlength);
		//
		String space = "0,pt,-1,0,32";
		recentopenfile.setProperty("PR_SPACE_BEFORE", space);
		recentopenfile.setProperty("PR_SPACE_AFTER", space);
		recentopenfile.setProperty("PR_SPACE_END", space);
		recentopenfile.setProperty("PR_SPACE_START", space);
		String linejeight = "14.4,pt,-1,0,32";
		recentopenfile.setProperty("PR_LINE_HEIGHT", linejeight);
		//
		recentopenfile.setProperty("maxNumber", "0");
		recentopenfile.setProperty("PR_EDITMODE", "185");
		recentopenfile.setProperty("precedence", "");
		recentopenfile.setProperty("order", "");
		recentopenfile.setProperty("datatype", "");
		recentopenfile.setProperty("showruler", "true");
		recentopenfile.setProperty("shownetcell", "false");
		recentopenfile.setProperty("showvscoll", "true");
		recentopenfile.setProperty("showhscoll", "true");
		recentopenfile.setProperty("showstate", "true");
		recentopenfile.setProperty("widthFactor", "88%");
		recentopenfile.setProperty("standardlayout", "true");
		recentopenfile.setProperty("customlayout", "true");
		recentopenfile.setProperty("before", "([{·‘“〈《「『【〔〖（．［｛￡￥");
		recentopenfile.setProperty("after",
				"!),.:;?]}¨·ˇˉ―‖’”…∶、。〃々〉》」』】〕〗！＂＇），．：；？］｀｜｝～￠");
		recentopenfile.setProperty("unit", "mm");
		recentopenfile.setProperty("workSpace", "软件安装路径");
		recentopenfile.setProperty("numberRecentOpen", "10");
		recentopenfile.setProperty("language", "中文");
		recentopenfile.setProperty("change", "Alt+O");
		recentopenfile.setProperty("clear", "Alt+C");
		recentopenfile.setProperty("inputData", "Alt+X");
		recentopenfile.setProperty("outPSXSL", "Alt+P");
		recentopenfile.setProperty("outXSL", "Alt+W");
		recentopenfile.setProperty("print", "Ctrl+P");
		recentopenfile.setProperty("preview", "Ctrl+B");
		recentopenfile.setProperty("selectNumberofS", "Shift+两个鼠标单击");
		recentopenfile.setProperty("selectNumberofP", "Ctrl+鼠标单击");
		recentopenfile.setProperty("blowMove", "↓");
		recentopenfile.setProperty("aboveMove", "↑");
		recentopenfile.setProperty("rightMove", "→");
		recentopenfile.setProperty("leftMove", "←");
		recentopenfile.setProperty("open", "Ctrl+O");
		recentopenfile.setProperty("newCreat", "Ctrl+N");
		recentopenfile.setProperty("close", "Ctrl+W");
		recentopenfile.setProperty("allSelect", "Ctrl+A");
		recentopenfile.setProperty("replacement", "Ctrl+E");
		recentopenfile.setProperty("find", "Ctrl+F");
		recentopenfile.setProperty("delete", "Ctrl+D");
		recentopenfile.setProperty("shear", "Ctrl+X");
		recentopenfile.setProperty("paste", "Ctrl+V");
		recentopenfile.setProperty("copy", "Ctrl+C");
		recentopenfile.setProperty("save", "Ctrl+S");
		recentopenfile.setProperty("PR_EDITMODE", "");
	}

	public static void main(String[] args)
	{
		try
		{
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e)
		{
			LogUtil.errorException(e.getMessage(), e);
		}
		ConfigurationInformationDialog dialog = new ConfigurationInformationDialog();
		dialog.setVisible(true);
		dialog.save();
	}

	public static Properties getConfigurefile()
	{
		return ConfigurationInformationDialog.configurefile;
	}

	public static void setConfigurefile(Properties configurefile)
	{
		ConfigurationInformationDialog.configurefile = configurefile;
	}

	public static String getConfigureItem(String key)
	{
		if (getConfigurefile().get(key) != null)
		{
			return getConfigurefile().get(key).toString();
		} else
		{
			return null;
		}

	}

	public static void setConfigureItem(String key, String value)
	{
		getConfigurefile().setProperty(key, value);
	}

	public static void isChanged()
	{

	}

	public static boolean isLoadROF()
	{
		return isLoadROF;
	}

	public static void setLoadROF(boolean isLoadROF)
	{
		ConfigurationInformationDialog.isLoadROF = isLoadROF;
	}
}
