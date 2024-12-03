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
 * @Constants.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.util;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-1
 */
public interface Constants
{

	int PR_TYPE = 1;

	int PR_WIDTH = 2;

	int PR_HEIGHT = 3;

	int EN_BAR = 301;

	int EN_PIE = 302;

	int EN_FUNC_BY_PARAM = 5001;

	int EN_BIN_DATA_STR = 5002;

	// XSL
	/** XSL element constant */
	int XSL_STYLESHEET = 1001;

	/** XSL element constant */
	int XSL_OUTPUT = 1002;

	/** XSL element constant */
	int XSL_VARIABLE = 1003;

	/** XSL element constant */
	int XSL_PARAM = 1004;

	/** XSL element constant */
	int XSL_CHOOSE = 1005;

	/** XSL element constant */
	int XSL_WHEN = 1006;

	/** XSL element constant */
	int XSL_OTHERWISE = 1007;

	/** XSL element constant */
	int XSL_VALUE_OF = 1008;

	/** XSL element constant */
	int XSL_TEMPLATE = 1009;

	/** XSL element constant */
	int XSL_CALL_TEMPLATE = 1010;

	/** XSL element constant */
	int XSL_IF = 1011;

	/** XSL element constant */
	int XSL_FOR_EACH = 1012;

	/** XSL element constant */
	int XSL_SORT = 1013;

	/** XSL element constant */
	int XSL_WITH_PARAM = 1014;

	/** XSL element constant */
	int XSL_TEXT = 1015;

	/** XSL element constant */
	int XSL_NUMBER = 1016;

	/** XSL element constant */
	int XSL_MESSAGE = 1017;

	/** XSL element constant */
	int XSL_APPLY_TEMPLATES = 1018;

	/** XSL element constant */
	int XSL_KEY = 1019;

	/** XSL element constant */
	int XSL_TRANSFORM = 1020;

	/** XSL element constant */
	int XSL_DECIMAL_FORMAT = 1021;

	/** Number of XSL element constants defined */
	int FRM_XSLOBJ_COUNT = 21;

	// XSL property constants
	/** XSL Property constant */
	int PR_XSL_VERSION = 1001;

	/** XSL Property constant */
	int PR_XSL_METHOD = 1002;

	/** XSL Property constant */
	int PR_XSL_ENCODING = 1003;

	/** XSL Property constant */
	int PR_XSL_INDENT = 1004;

	/** XSL Property constant */
	int PR_XSL_NAME = 1005;

	/** XSL Property constant */
	int PR_XSL_SELECT = 1006;

	/** XSL Property constant */
	int PR_XSL_TEST = 1007;

	/** XSL Property constant */
	int PR_XSL_MATCH = 1008;

	/** XSL Property constant */
	int PR_XSL_MODE = 1009;

	/** XSL Property constant */
	int PR_XSL_DATE_TYPE = 1010;

	/** XSL Property constant */
	int PR_XSL_ORDER = 1011;

	/** XSL Property constant */
	int PR_XSL_CASE_ORDER = 1012;

	/** XSL Property constant */
	int PR_XSL_LANG = 1013;

	/** XSL Property constant */
	int PR_XSL_DISABLE_OUTPUT_ESCAPING = 1014;

	/** XSL Property constant */
	int PR_XSL_VALUE = 1015;

	/** XSL Property constant */
	int PR_XSL_FORMAT = 1016;

	/** XSL Property constant */
	int PR_XSL_DECIMAL_SEPARETOR = 1017;

	/** XSL Property constant */
	int PR_XSL_GROUPING_SEPARATOR = 1018;

	/** XSL Property constant */
	int PR_XSL_INFINITY = 1019;

	/** XSL Property constant */
	int PR_XSL_MINUS_SIGN = 1020;

	/** XSL Property constant */
	int PR_XSL_NAN = 1021;

	/** XSL Property constant */
	int PR_XSL_PERCENT = 1022;

	/** XSL Property constant */
	int PR_XSL_PER_MILLE = 1023;

	/** XSL Property constant */
	int PR_XSL_ZERO_DIGIT = 1024;

	/** XSL Property constant */
	int PR_XSL_DIGIT = 1025;

	/** XSL Property constant */
	int PR_XSL_PATTERN_SEPARATOR = 1026;

	/** XSL Property constant */
	int PR_XSL_DECIMAL_SEPARATOR = 1027;

	/** Number of XSL property constants defined */
	int PROPERTY_XSL_COUNT = 27;

	// XSL Enumeration constants
	/** Enumeration constant */
	int EN_XSL_VERSION_NUM = 1001;

	/** Enumeration constant */
	int EN_XSL_UTF_8 = 1002;

	/** Enumeration constant */
	int EN_XSL_XML = 1003;

	/** Enumeration constant */
	int EN_XSL_YES = 1004;

	/** Enumeration constant */
	int EN_XSL_NO = 1005;

	/** Enumeration constant */
	int EN_XSL_NUMBER = 1006;

	/** Enumeration constant */
	int EN_XSL_TEXT = 1007;

	/** Enumeration constant */
	int EN_XSL_UPPER_FIRST = 1008;

	/** Enumeration constant */
	int EN_XSL_LOWER_FIRST = 1009;

	/** Enumeration constant */
	int EN_XSL_ASCENDING = 1010;

	/** Enumeration constant */
	int EN_XSL_DESCENDING = 1011;

	int ENUM_XSL_EM_XSL_COUNT = 11;

	// SVG
	/** SVG element constant */
	int SVG_SVG = 2001;

	/** SVG element constant */
	int SVG_G = 2002;

	/** SVG element constant */
	int SVG_LINE = 2003;

	/** SVG element constant */
	int SVG_RECT = 2004;

	/** SVG element constant */
	int SVG_CIRCLE = 2005;

	/** SVG element constant */
	int SVG_ELLIPSE = 2006;

	/** SVG element constant */
	int SVG_POLYLINE = 2007;

	/** SVG element constant */
	int SVG_POLYGON = 2008;

	/** SVG element constant */
	int SVG_DEFS = 2009;

	/** SVG element constant */
	int SVG_PATH = 2010;

	/** SVG element constant */
	int SVG_TEXT = 2011;

	/** SVG element constant */
	int SVG_TSPAN = 2012;

	/** SVG element constant */
	int SVG_TEXTPATH = 2013;

	/** SVG element constant */
	int SVG_USE = 2014;

	/** Number of SVG element constants defined */
	int FRM_SVGOBJ_COUNT = 14;

	// SVG property constants
	/** SVG Property constant */
	int PR_SVG_WIDTH_SVG = 3001;

	/** SVG Property constant */
	int PR_SVG_HEIGHT_SVG = 3002;

	/** SVG Property constant */
	int PR_SVG_VIEWBOX = 3003;

	/** SVG Property constant */
	int PR_SVG_TRANSFORM = 3004;

	/** SVG Property constant */
	int PR_SVG_STROKE = 3005;

	/** SVG Property constant */
	int PR_SVG_X1 = 3006;

	/** SVG Property constant */
	int PR_SVG_X2 = 3007;

	/** SVG Property constant */
	int PR_SVG_Y1 = 3008;

	/** SVG Property constant */
	int PR_SVG_Y2 = 3009;

	/** SVG Property constant */
	int PR_SVG_STROKE_WIDTH = 3010;

	/** SVG Property constant */
	int PR_SVG_X = 3011;

	/** SVG Property constant */
	int PR_SVG_Y = 3012;

	/** SVG Property constant */
	int PR_SVG_WIDTH = 3013;

	/** SVG Property constant */
	int PR_SVG_HEIGHT = 3014;

	/** SVG Property constant */
	int PR_SVG_FILL = 3015;

	/** SVG Property constant */
	int PR_SVG_CX = 3016;

	/** SVG Property constant */
	int PR_SVG_CY = 3017;

	/** SVG Property constant */
	int PR_SVG_R = 3018;

	/** SVG Property constant */
	int PR_SVG_RX = 3019;

	/** SVG Property constant */
	int PR_SVG_RY = 3020;

	/** SVG Property constant */
	int PR_SVG_POINTS = 3021;

	/** SVG Property constant */
	int PR_SVG_ID = 3022;

	/** SVG Property constant */
	int PR_SVG_D = 3023;

	/** SVG Property constant */
	int PR_SVG_FONT_FAMILY = 3024;

	/** SVG Property constant */
	int PR_SVG_FONT_SIZE = 3025;

	/** SVG Property constant */
	int PR_SVG_FONT_WEIGHT = 3026;

	/** SVG Property constant */
	int PR_SVG_FONT_STYLE = 3027;

	/** SVG Property constant */
	int PR_SVG_TEXT_DECORATION = 3028;

	/** SVG Property constant */
	int PR_SVG_DX = 3029;

	/** SVG Property constant */
	int PR_SVG_DY = 3030;

	/** SVG Property constant */
	int PR_SVG_XLINK_HREF = 3031;

	/** Number of SVG property constants defined */
	int PROPERTY_SVG_COUNT = 31;

	// FOV
	/** FOV element constant */
	int FOV_TABLEINFO = 3001;

	/** FOV element constant */
	int FOV_TRANSLATE = 3002;

	/** Number of FOV element constants defined */
	int FRM_FOVOBJ_COUNT = 2;

	// FOV property constants
	/** FOV Property constant */
	int PR_FOV_STARTNAME = 3001;

	/** FOV Property constant */
	int PR_FOV_ROWCOUNT = 3002;

	/** FOV Property constant */
	int PR_FOV_KEY = 3003;

	/** FOV Property constant */
	int PR_FOV_VALUE = 3004;

	/** FOV Property constant */
	int PR_FOV_EDITMODE = 3005;

	/** FOV Property constant */
	int PR_FOV_XPATH = 3006;

	/** FOV Property constant */
	int PR_FOV_HIDENAME = 3007;

	/** Number of FOV property constants defined */
	int PROPERTY_FOV_COUNT = 7;

}
