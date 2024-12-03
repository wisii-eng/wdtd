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
package com.wisii.schema.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xerces.internal.xs.*;
public class CacheStore
{
	private Map<XSElementDeclaration, List<XSElementDeclaration>> substMap = new HashMap<XSElementDeclaration, List<XSElementDeclaration>>();
	private Map<XSElementDeclaration, DefaultSchemaNode> nodemap = new HashMap<XSElementDeclaration, DefaultSchemaNode>();

	public CacheStore()
	{
	}

	public void clear()
	{
		this.substMap.clear();
		nodemap.clear();
	}

	private void buildSubstGroups(XSModel model)
	{
		XSNamedMap xsMap = model
				.getComponents(XSSimpleTypeDefinition.VARIETY_LIST);
		for (int i = 0; i < xsMap.getLength(); i++)
		{
			XSElementDeclaration ed = (XSElementDeclaration) xsMap.item(i);
			if (!ed.getAbstract())
			{
				XSElementDeclaration ed0 = ed.getSubstitutionGroupAffiliation();
				if (ed0 != null)
				{
					List<XSElementDeclaration> list = (List<XSElementDeclaration>) this.substMap
							.get(ed0);
					if (list == null)
					{
						list = new ArrayList<XSElementDeclaration>();
						this.substMap.put(ed0, list);
						if (!ed0.getAbstract())
							list.add(ed0);
					}
					list.add(ed);
				}
			}
		}
	}

	public void cacheNode(XSElementDeclaration ed, DefaultSchemaNode node)
	{
		if (ed != null && node != null)
		{
			nodemap.put(ed, node);
		}
	}

	public DefaultSchemaNode getCacheNode(XSElementDeclaration ed)
	{
		DefaultSchemaNode node=nodemap.get(ed);
		return node;
	}

	public void init(XSModel model)
	{
		clear();
		buildSubstGroups(model);
	}

	public List<XSElementDeclaration> getSubstitutionGroup(
			XSElementDeclaration ed)
	{
		return substMap.get(ed);
	}
}