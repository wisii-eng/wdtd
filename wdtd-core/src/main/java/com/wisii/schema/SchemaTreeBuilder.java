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
 * @SchemaTreeBuilder.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.schema;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wisii.schema.model.CacheStore;
import com.wisii.schema.model.DefaultSchemaNode;
import com.wisii.schema.model.SchemaTreeModel;
import com.wisii.schema.model.XSObjectRef;
import com.wisii.schema.util.SchemaLoader;
import com.sun.org.apache.xerces.internal.xs.*;
import com.sun.org.apache.xerces.internal.impl.dv.*;

/**
 * 类功能描述：
 * 
 * 作者：wisii 创建日期：2013-3-5
 */
public class SchemaTreeBuilder
{
	private XSModel xsmodel;
	private Map<String, List<String>> allRootElements = new HashMap<String, List<String>>();

	private List<String> rootnamespaces = new ArrayList<String>();
	private CacheStore cache;
	public SchemaTreeBuilder()
	{
		cache = new CacheStore();
		
	}

	public SchemaTreeModel buildSchemaTree(XSModel xsmodel, String namespace,
			String rootelementName)
	{
		if (xsmodel == null)
		{
			return null;
		}
		this.xsmodel = xsmodel;
		if (rootelementName == null || rootelementName.isEmpty())
		{
			if (!collectRootElements())
			{
				return null;
			}
			namespace = rootnamespaces.get(0);
			rootelementName = allRootElements.get(namespace).get(0);
		}
		XSElementDeclaration ed = xsmodel.getElementDeclaration(
				rootelementName, namespace);
		if (ed == null)
		{
			return null;
		}
		cache.init(xsmodel);
		DefaultSchemaNode rootNode = buildElement(ed);
		return new SchemaTreeModel(xsmodel, rootNode);
	}

	private boolean collectRootElements()
	{
		this.allRootElements.clear();
		rootnamespaces.clear();
		if (xsmodel == null)
			return false;
		StringList allNS = xsmodel.getNamespaces();
		int n = allNS.getLength();
		for (int i = 0; i < n; i++)
		{
			String ns = allNS.item(i);
			ArrayList elements = new ArrayList();
			XSNamedMap nm = xsmodel.getComponentsByNamespace((short) 2, ns);
			int m = nm.getLength();
			for (int j = 0; j < m; j++)
			{
				XSElementDeclaration ed = (XSElementDeclaration) nm.item(j);
				if (!ed.getAbstract())
					elements.add(ed.getName());
			}
			if (elements.size() > 0)
			{
				rootnamespaces.add(ns);
				this.allRootElements.put(ns, elements);
			}
		}
		return !allRootElements.isEmpty();
	}

	protected DefaultSchemaNode buildElement(XSElementDeclaration ed)
	{
		DefaultSchemaNode node = cache.getCacheNode(ed);
		if (node != null)
		{
			return new DefaultSchemaNode(node.getXSObjectRef(), node);
		}
		XSComplexTypeDefinition ctd = null;
		XSSimpleTypeDefinition std = null;
		XSTypeDefinition td = ed.getTypeDefinition();
		int type = XSObjectRef.ELEMENT_EMPTY;
		if ((td instanceof XSSimpleType))
		{
			std = (XSSimpleType) td;
			type = XSObjectRef.ELEMENT_SIMPLE;
		} else
		{
			ctd = (XSComplexTypeDefinition) td;
			std = ctd.getSimpleType();
			if (std != null)
				type = XSObjectRef.ELEMENT_EXT_SIMPLE;
			else if (ctd.getParticle() != null)
				type = XSObjectRef.ELEMENT_COMPLEX;
			else if (ctd.getAttributeUses().getLength() > 0)
				type = XSObjectRef.ELEMENT_EXT_EMPTY;
			else
				type = XSObjectRef.ELEMENT_EMPTY;
		}
		XSObjectRef ref = new XSObjectRef(type, ed);
		DefaultSchemaNode newnode = new DefaultSchemaNode(ref);
		cache.cacheNode(ed, newnode);
		buildChildren(newnode);
		return newnode;
	}

	private boolean buildChildren(DefaultSchemaNode parentNode)
	{
		if (parentNode.getChildCount() > 0)
			return false;
		XSObjectRef ref = parentNode.getXSObjectRef();
		if (!ref.isElementWithChildNodes())
			return false;
		XSElementDeclaration ed = ref.getXSElement();
		XSComplexTypeDefinition ctd = (XSComplexTypeDefinition) ed
				.getTypeDefinition();
		XSObjectList attrUses = ctd.getAttributeUses();
		if (attrUses != null)
		{
			for (int i = 0; i < attrUses.getLength(); i++)
			{
				XSAttributeUse au = (XSAttributeUse) attrUses.item(i);
				parentNode.addChild(buildAttribute(au));
			}
		}
		XSParticle particle = ctd.getParticle();
		if (particle == null)
			return true;
		XSTerm term = particle.getTerm();
//		if ((particle.getMinOccurs() == 1) && (particle.getMaxOccurs() == 1)
//				&& ((term instanceof XSModelGroup))
//				&& (((XSModelGroup) term).getCompositor() == XSModelGroup.COMPOSITOR_SEQUENCE))
//		{
//			XSModelGroup mg = (XSModelGroup) term;
//			XSObjectList list = mg.getParticles();
//			for (int i = 0; i < list.getLength(); i++)
//			{
//				XSParticle childParticle = (XSParticle) list.item(i);
//				DefaultSchemaNode childNode = buildOccurs(childParticle);
//				if (childNode != null)
//				{
//					parentNode.addChild(childNode);
//				}
//			}
//		} else
//		{
			DefaultSchemaNode childNode = buildOccurs(particle);
			if (childNode != null)
			{
				parentNode.addChild(childNode);
			}
//		}
		return true;
	}

	private DefaultSchemaNode buildAttribute(XSAttributeUse au)
	{
		XSAttributeDeclaration ad = au.getAttrDeclaration();
		DefaultSchemaNode node = new DefaultSchemaNode(new XSObjectRef(
				XSObjectRef.ATTRIBUTE, ad,au.getRequired()));
		return node;
	}

	protected DefaultSchemaNode buildOccurs(XSParticle particle)
	{
		int minOccurs = particle.getMinOccurs();
		int maxOccurs = particle.getMaxOccursUnbounded() ? Integer.MAX_VALUE
				: particle.getMaxOccurs();
		if ((maxOccurs < 1) || (maxOccurs < minOccurs))
			return null;
		XSTerm term = particle.getTerm();
		if ((term instanceof XSElementDeclaration))
			return buildElementOccurs(particle, (XSElementDeclaration) term);
		if ((term instanceof XSModelGroup))
			return buildGroupOccurs(particle, (XSModelGroup) term);
		return null;
	}

	protected DefaultSchemaNode buildElementOccurs(XSParticle particle,
			XSElementDeclaration ed)
	{
		int minOccurs = particle.getMinOccurs();
		int maxOccurs = particle.getMaxOccursUnbounded() ? Integer.MAX_VALUE
				: particle.getMaxOccurs();
		if ((maxOccurs < 1) || (minOccurs > maxOccurs))
			return null;
		DefaultSchemaNode node = new DefaultSchemaNode(new XSObjectRef(
				XSObjectRef.ARRAY_ELEMENTS, particle));
		DefaultSchemaNode childNode = buildSubstitutionGroup(ed);
		node.addChild(childNode);
		return node;
	}

	private DefaultSchemaNode buildGroupOccurs(XSParticle particle,
			XSModelGroup mg)
	{
		int minOccurs = particle.getMinOccurs();
		int maxOccurs = particle.getMaxOccursUnbounded() ? Integer.MAX_VALUE
				: particle.getMaxOccurs();
		if ((maxOccurs < 1) || (minOccurs > maxOccurs))
			return null;
		// boolean optional = minOccurs < 1;
		// if (minOccurs==1&&(maxOccurs == 1) &&
		// (!particle.getMaxOccursUnbounded()))
		// return buildGroup(mg, optional, false);
		DefaultSchemaNode arrayNode = new DefaultSchemaNode(new XSObjectRef(
				XSObjectRef.ARRAY_GROUPS, particle));
		DefaultSchemaNode groupNode = buildGroup(mg);
		if (groupNode != null)
		{
			arrayNode.addChild(groupNode);
			buildChildren(groupNode);
			return arrayNode;
		}
		return null;
	}

	protected DefaultSchemaNode buildSubstitutionGroup(XSElementDeclaration ed)
	{
		List substList = this.cache.getSubstitutionGroup(ed);
		if (substList == null)
		{
			DefaultSchemaNode node = buildElement(ed);
			return node;
		}

		DefaultSchemaNode groupNode = new DefaultSchemaNode(new XSObjectRef(
				XSObjectRef.SUBSTITUTION, ed));
		for (int i = 0; i < substList.size(); i++)
		{
			XSElementDeclaration edSubst = (XSElementDeclaration) substList
					.get(i);
			DefaultSchemaNode child = buildElement(edSubst);
			if (child != null)
			{
				groupNode.addChild(child);
			}
		}
		return groupNode;
	}

	protected DefaultSchemaNode buildGroup(XSModelGroup mg)
	{
		int type;
		switch (mg.getCompositor())
		{
		case XSModelGroup.COMPOSITOR_SEQUENCE:
			type = XSObjectRef.GROUP_SEQUENCE;
			break;
		case XSModelGroup.COMPOSITOR_CHOICE:
			type = XSObjectRef.GROUP_CHOICE;
			break;
		case XSModelGroup.COMPOSITOR_ALL:
			type = XSObjectRef.GROUP_ALL;
			break;
		default:
			return null;
		}
		DefaultSchemaNode groupNode = new DefaultSchemaNode(new XSObjectRef(
				type, mg));
		XSObjectList list = mg.getParticles();
		for (int i = 0; i < list.getLength(); i++)
		{
			XSParticle particle = (XSParticle) list.item(i);
			XSTerm term = particle.getTerm();
			DefaultSchemaNode child = null;
			if ((term instanceof XSElementDeclaration))
				child = buildElementOccurs(particle,
						(XSElementDeclaration) term);
			else if ((term instanceof XSModelGroup))
				child = buildGroupOccurs(particle, (XSModelGroup) term);
			if (child != null)
			{
				groupNode.addChild(child);
			}
		}
		int childCount = groupNode.getChildCount();
		if (childCount == 0)
			return null;
//		if ((childCount == 1) && (!multiple))
//		{
//			DefaultSchemaNode child = (DefaultSchemaNode) groupNode
//					.getChildAt(0);
//			if (!optional)
//				return child;
//		}
		return groupNode;
	}

	public List<List<String>> getAllRootElements(XSModel xsmodel)
	{
		List<List<String>> roots = new ArrayList<List<String>>();
		StringList allNS = xsmodel.getNamespaces();
		int n = allNS.getLength();
		for (int i = 0; i < n; i++)
		{
			String ns = allNS.item(i);
			XSNamedMap nm = xsmodel.getComponentsByNamespace((short) 2, ns);
			int m = nm.getLength();
			for (int j = 0; j < m; j++)
			{
				XSElementDeclaration ed = (XSElementDeclaration) nm.item(j);
				List<String> names = new ArrayList<String>();
				names.add(ns);
				names.add(ed.getName());
				roots.add(names);
			}
		}
		return roots;
	}
	public static void main(String[] args)
	{
		new SchemaTreeBuilder().buildSchemaTree(SchemaLoader.LoadSchema(new File("E:\\workspace\\XmlEditor\\test\\proced\\proced.xsd")), null, "mainProcedure");
	}
	
}
