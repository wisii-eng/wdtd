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
package com.wisii.wisedoc.view.ui.ribbon.edit;

import com.wisii.wisedoc.document.attribute.edit.Formula;

public final class FormulaTransform {
	
	/**
	 * The single quote character.
	 */
	public static final char SINGLE_QUOTE = '\'';

	/**
	 * The double quote character.
	 */
	public static final char DOUBLE_QUOTE = '"';

	/**
	 * The open brace character.
	 */
	public static final char OPEN_BRACE = '{';

	/**
	 * The closed brace character.
	 */
	public static final char CLOSED_BRACE = '}';

	/**
	 * The pound sign character.
	 */
	public static final char POUND_SIGN = '#';

	/**
	 * The open variable string.
	 */
	public static final String OPEN_VARIABLE = String.valueOf(POUND_SIGN)
			+ String.valueOf(OPEN_BRACE);

	/**
	 * The closed brace string.
	 */
	public static final String CLOSED_VARIABLE = String.valueOf(CLOSED_BRACE);

	/**
	 * The true value for a Boolean string.
	 */
	public static final String BOOLEAN_STRING_TRUE = "1.0";

	/**
	 * The false value for a Boolean string.
	 */
	public static final String BOOLEAN_STRING_FALSE = "0.0";
	
	/**
	 * The comma character.
	 */
	public static final char COMMA = ',';
	
	//#{a}+#{b}
	public String formula2string(FormulaEdit formula){
		String formula2 = formula.getFormula();
		String result = formula2.replace(OPEN_VARIABLE, "[").replace(CLOSED_VARIABLE, "]");
		
		return result;
		
	}
	public FormulaEdit string2formula(String str){
		String result = str.replace("[",OPEN_VARIABLE).replace("]", CLOSED_VARIABLE);
		return new FormulaEdit(result);
	}
	
	
	public static void main(String[] args) {
		FormulaTransform f = new FormulaTransform();
		String formula2String = f.formula2string(new FormulaEdit("#{var1} + abs(sqrt(#{var2}))"));
		System.out.println(formula2String);
		FormulaEdit string2formula = f.string2formula("[a]+[b]");
		System.out.println(string2formula.getFormula());
		
	}
}
