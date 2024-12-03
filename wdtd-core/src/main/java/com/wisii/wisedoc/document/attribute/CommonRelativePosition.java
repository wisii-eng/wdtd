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

/* $Id: CommonRelativePosition.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * Store all common relative position properties. See Sec 7.12 of the XSL-FO
 * Standard. Public "structure" allows direct member access.
 */
public final class CommonRelativePosition extends AbstractCommonAttributes
{

	public CommonRelativePosition(CellElement cellelement)
	{
		super(cellelement);
	}
	/**
	 * @返回  relativePosition变量的值
	 */
	public final int getRelativePosition()
	{
		return (Integer)getAttribute(Constants.PR_RELATIVE_POSITION);
	}

	/**
	 * @返回  top变量的值
	 */
	public final Length getTop()
	{
		return (Length)getAttribute(Constants.PR_TOP);
	}

	/**
	 * @返回  right变量的值
	 */
	public final Length getRight()
	{
		return (Length)getAttribute(Constants.PR_RIGHT);
	}

	/**
	 * @返回  bottom变量的值
	 */
	public final Length getBottom()
	{
		return (Length)getAttribute(Constants.PR_BOTTOM);
	}

	/**
	 * @返回  left变量的值
	 */
	public final Length getLeft()
	{
		return (Length)getAttribute(Constants.PR_LEFT);
	}

}
