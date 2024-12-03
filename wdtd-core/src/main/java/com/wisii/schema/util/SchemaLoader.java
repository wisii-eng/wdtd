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
 * 
 */
package com.wisii.schema.util;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sun.org.apache.xerces.internal.xs.StringList;
import com.sun.org.apache.xerces.internal.xs.XSModel;
import com.sun.org.apache.xerces.internal.xs.XSNamedMap;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaLoader;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaGrammar;
import com.sun.org.apache.xerces.internal.impl.xs.XSModelImpl;
import com.sun.org.apache.xerces.internal.xs.XSElementDeclaration;
import com.sun.org.apache.xerces.internal.xs.XSConstants;
/**
 * @author wisii
 *
 */
public class SchemaLoader
{
	public static XSModel LoadSchema(URL schemaURL) {
		if(schemaURL==null)
		{
			return null;
		}
		try
		{
			return LoadSchema(schemaURL.toURI());
		} catch (URISyntaxException e)
		{
			return LoadSchema(schemaURL.toString());
		}
	}
	public static XSModel LoadSchema(URI uri)
	{
		if(uri==null)
		{
			return null;
		}
		
		return LoadSchema(uri.toASCIIString());
	}
	public static XSModel LoadSchema(File file)
	{
		if(file==null||!file.exists()||!file.canRead())
		{
			return null;
		}
			return LoadSchema(file.toURI());
		
	}
	public static XSModel LoadSchema(String url)
	{
		if(url==null||url.isEmpty())
		{
			return null;
		}
		File file=new File(url);
		if(file.exists())
		{
			url=file.toURI().toASCIIString();
		}
		XMLInputSource src = new XMLInputSource(null,url,url);
		XMLSchemaLoader loader = new XMLSchemaLoader();
		SchemaGrammar grammar;
		try
		{
			grammar = (SchemaGrammar) loader.loadGrammar(src);
			return new XSModelImpl(new SchemaGrammar[] { grammar });
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	
	}
	/*
	 * 返回schema中各命名空间可能的根节点名称
	 */
	public static Map<String,List<String>> getRootElements(XSModel model)
 {
		if (model == null)
		{
			return null;
		}
		Map<String,List<String>> nsroots=new HashMap<String, List<String>>();
		StringList allNS = model.getNamespaces();
		int n = allNS.getLength();
		for (int i = 0; i < n; i++)
		{
			String ns = allNS.item(i);
			ArrayList<String> elements = new ArrayList<String>();
			XSNamedMap nm = model.getComponentsByNamespace(XSConstants.ELEMENT_DECLARATION, ns);
			int m = nm.getLength();
			for (int j = 0; j < m; j++)
			{
				XSElementDeclaration ed = (XSElementDeclaration) nm.item(j);
				if (!ed.getAbstract())
					elements.add(ed.getName());
			}
			if (elements.size() > 0)
			{
				nsroots.put(ns, elements);
			}
		}
		return nsroots;

	} 
	public static void main(String[] args)
	{
		LoadSchema(new File("E:/zhangqiang/航二院/英文/xml_schema_flat/descript.xsd"));
	}

}
