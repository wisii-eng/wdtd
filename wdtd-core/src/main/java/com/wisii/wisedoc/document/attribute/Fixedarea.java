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

package com.wisii.wisedoc.document.attribute;

public class Fixedarea
{

	int onelinemaxnumber = 0;

	int lines;

	int caozuofu;

	boolean isview;

	String nullas;

	String beforeadd;

	public static int LESS = 0;

	public static int THANANDEQUAL = 1;

	public Fixedarea(int maxnumber, int lines, int caozuofu, boolean view,
			String nullas, String beforeadd)
	{
		onelinemaxnumber = maxnumber;
		this.lines = lines;
		this.caozuofu = caozuofu;
		isview = view;
		this.nullas = nullas;
		this.beforeadd = beforeadd;
	}

	public int getOnelinemaxnumber()
	{
		return onelinemaxnumber;
	}

	public int getLines()
	{
		return lines;
	}

	public int getCaozuofu()
	{
		return caozuofu;
	}

	public boolean isIsview()
	{
		return isview;
	}

	public String getNullas()
	{
		return nullas;
	}

	public String getBeforeadd()
	{
		return beforeadd;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beforeadd == null) ? 0 : beforeadd.hashCode());
		result = prime * result + caozuofu;
		result = prime * result + (isview ? 1231 : 1237);
		result = prime * result + lines;
		result = prime * result + ((nullas == null) ? 0 : nullas.hashCode());
		result = prime * result + onelinemaxnumber;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fixedarea other = (Fixedarea) obj;
		if (beforeadd == null)
		{
			if (other.beforeadd != null)
				return false;
		} else if (!beforeadd.equals(other.beforeadd))
			return false;
		if (caozuofu != other.caozuofu)
			return false;
		if (isview != other.isview)
			return false;
		if (lines != other.lines)
			return false;
		if (nullas == null)
		{
			if (other.nullas != null)
				return false;
		} else if (!nullas.equals(other.nullas))
			return false;
		if (onelinemaxnumber != other.onelinemaxnumber)
			return false;
		return true;
	}

}
