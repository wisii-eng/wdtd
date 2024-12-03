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

package com.wisii.wisedoc.io.xsl.attribute.edit;

import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;

public class ConnWithUI extends EditUI
{

	ConnWith connwith;

	public ConnWith getConnwith()
	{
		return connwith;
	}

	public void setConnwith(ConnWith connwith)
	{
		this.connwith = connwith;
	}

	public static int CONN_WITH = 1;

	public ConnWithUI(EnumProperty uitype, Map<Integer, Object> map)
	{
		super(uitype, map);
		connwith = (ConnWith) map.get(Constants.PR_CONN_WITH);
	}

}
