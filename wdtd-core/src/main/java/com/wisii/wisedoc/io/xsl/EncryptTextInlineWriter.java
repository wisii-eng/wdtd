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

package com.wisii.wisedoc.io.xsl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.attribute.EncryptInformation;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class EncryptTextInlineWriter extends AbsElementWriter
{

	public EncryptTextInlineWriter(CellElement element)
	{
		super(element);
	}

	public String getChildCode()
	{
		EncryptInformation encry = (EncryptInformation) attributemap
				.get(com.wisii.wisedoc.document.Constants.PR_ENCRYPT);
		StringBuffer output = new StringBuffer();
		if (encry != null)
		{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			int type = encry.getEncrypttype();
			paramMap.put("type", type);
			List<BindNode> nodes = encry.getNodes();
			if (nodes != null && !nodes.isEmpty())
			{
				StringBuffer data = new StringBuffer();
				data.append("concat(");
				for (int i = 0; i < nodes.size(); i++)
				{
					BindNode current = nodes.get(i);
					String relativePath = IoXslUtil
							.compareCurrentPath(IoXslUtil.getXSLXpath(current));

					data.append(relativePath);
					if (i < nodes.size() - 1)
					{
						data.append(",");
					}
					if (i == nodes.size() - 1 && nodes.size() == 1)
					{
						data.append(",''");
					}
				}
				data.append(")");
				paramMap.put("data", data);
			}
			IoXslUtil.addFunctionTemplate("encryption");
			output.append(ElementUtil
					.outputCalltemplate("encryption", paramMap));
		}

		return output.toString();
	}

	@Override
	public String getElementName()
	{
		return FoXsltConstants.INLINE;
	}

}
