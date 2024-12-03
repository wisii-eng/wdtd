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
 * @ConnWithWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;
import java.util.List;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.document.attribute.edit.Formula;
import com.wisii.wisedoc.document.attribute.edit.Parameter;
import com.wisii.wisedoc.document.attribute.edit.Parameter.DataTyle;
import com.wisii.wisedoc.document.attribute.edit.Parameter.ParamTyle;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.wsd.DocumentWirter;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-3
 */
public class ConnWithWriter extends AbstractAttributeWriter
{
	public static final String CONNWITH = "connwith";
	public static final String PARMS = "parms";
	public static final String FORMULAS = "formulas";
	public static final String PARM = "parm";
	public static final String FORMULA = "formula";
	public static final String PARMTYPE = "type";
	public static final String DATATYPE = "datatype";
	public static final String PARMNAME = "name";
	public static final String PARMVALUE = "value";
	public static final String FORMULAEXPRESSION = "expression";
	public static final String FORMULAXPATH = "xpath";
	public static final String NODETAG = "@@@bnode";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (!(value instanceof ConnWith))
		{
			return "";
		}
		String returns = "";
		returns = returns + ElementWriter.TAGBEGIN + CONNWITH
				+ ElementWriter.TAGEND;
		ConnWith conwith = (ConnWith) value;
		List<Parameter> parms = conwith.getParms();
		if (parms != null && !parms.isEmpty())
		{
			returns = returns + getParmsString(parms);
		}
		List<Formula> formulas = conwith.getFormulas();
		if (formulas != null && !formulas.isEmpty())
		{
			returns = returns + getFormulasString(formulas);
		}
		returns = returns + ElementWriter.TAGENDSTART + CONNWITH
				+ ElementWriter.TAGEND;
		return returns;
	}

	private String getParmsString(List<Parameter> parms)
	{
		String returns = "";
		returns = returns + ElementWriter.TAGBEGIN + PARMS
				+ ElementWriter.TAGEND;
		for (Parameter parm : parms)
		{
			returns = returns + ElementWriter.TAGBEGIN + PARM;
			DataTyle datatype = parm.getDatatype();
			returns = returns + SPACETAG + DATATYPE + EQUALTAG + QUOTATIONTAG
					+ datatype + QUOTATIONTAG;
			String name = XMLUtil.getXmlText(parm.getName());
			returns = returns + SPACETAG + PARMNAME + EQUALTAG + QUOTATIONTAG
					+ name + QUOTATIONTAG;
			ParamTyle parmmtype = parm.getType();
			returns = returns + SPACETAG + PARMTYPE + EQUALTAG + QUOTATIONTAG
					+ parmmtype + QUOTATIONTAG;
			if (parmmtype != ParamTyle.UI)
			{
				Object value = parm.getValue();
				if(value instanceof String)
				{
					value = XMLUtil.getXmlText((String)value);
				}
				else if(value instanceof BindNode)
				{
					value = NODETAG+DocumentWirter.getDataNodeID((BindNode)value);
				}
				returns = returns + SPACETAG + PARMVALUE + EQUALTAG
						+ QUOTATIONTAG + value + QUOTATIONTAG;
			}
			returns = returns + ElementWriter.NOCHILDTAGEND;
		}
		returns = returns + ElementWriter.TAGENDSTART + PARMS
				+ ElementWriter.TAGEND;
		return returns;
	}

	private String getFormulasString(List<Formula> formulas)
	{
		String returns = "";
		returns = returns + ElementWriter.TAGBEGIN + FORMULAS
				+ ElementWriter.TAGEND;
		for (Formula formula : formulas)
		{
			returns = returns + ElementWriter.TAGBEGIN + FORMULA;
			String expression = XMLUtil.getXmlText(formula.getExpression());
			returns = returns + SPACETAG + FORMULAEXPRESSION + EQUALTAG
					+ QUOTATIONTAG + expression + QUOTATIONTAG;
			BindNode xpathnode = formula.getXpath();
			returns = returns + SPACETAG + FORMULAXPATH + EQUALTAG
					+ QUOTATIONTAG + DocumentWirter.getDataNodeID(xpathnode)
					+ QUOTATIONTAG;

			returns = returns + ElementWriter.NOCHILDTAGEND;
		}
		returns = returns + ElementWriter.TAGENDSTART + FORMULAS
				+ ElementWriter.TAGEND;
		return returns;
	}

}
