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

/* $Id: KeepProperty.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.attribute;

/**
 * 
 * 类功能描述：页续属性
 * 
 * 作者：zhangqiang 创建日期：2008-8-25
 */
public class KeepProperty extends Property
{
	private EnumProperty withinLine;

	private EnumProperty withinColumn;

	private EnumProperty withinPage;

	public KeepProperty(EnumProperty withinLine, EnumProperty withinColumn,
			EnumProperty withinPage)
	{
		this.withinLine = withinLine;
		this.withinColumn = withinColumn;
		this.withinPage = withinPage;
	}

	/**
	 * @return the withinLine property
	 */
	public EnumProperty getWithinLine()
	{
		return this.withinLine;
	}

	/**
	 * @return the withinColumn property
	 */
	public EnumProperty getWithinColumn()
	{
		return this.withinColumn;
	}

	/**
	 * @return the withinPage property
	 */
	public EnumProperty getWithinPage()
	{
		return this.withinPage;
	}

	/**
	 * Not sure what to do here. There isn't really a meaningful single value.
	 * 
	 * @return String representation
	 */
	public String toString()
	{
		return "Keep[" + "withinLine:" + getWithinLine().getObject()
				+ ", withinColumn:" + getWithinColumn().getObject()
				+ ", withinPage:" + getWithinPage().getObject() + "]";
	}

	/**
	 * @return this.keep
	 */
	public KeepProperty getKeep()
	{
		return this;
	}

	/**
	 * @return this.keep cast as Object
	 */
	public Object getObject()
	{
		return this;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof KeepProperty))
		{
			return false;
		}
		KeepProperty kp = (KeepProperty) obj;
		return (withinLine == null ? kp.withinLine == null : withinLine
				.equals(kp.withinLine))
				&& (withinColumn == null ? kp.withinColumn == null
						: withinColumn.equals(kp.withinColumn))
				&& (withinPage == null ? kp.withinPage == null : withinPage
						.equals(kp.withinPage));
	}

}
