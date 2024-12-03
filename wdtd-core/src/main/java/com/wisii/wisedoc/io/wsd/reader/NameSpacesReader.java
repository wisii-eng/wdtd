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
package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.banding.io.NameSpace;

/**
 * @author wisii
 *
 */
public class NameSpacesReader extends AbstractHandler
{
	private final String NAMESPACE = "namespace";
	private List<NameSpace> namespaces;
	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException {
		if(NAMESPACE.equals(qname))
		{
			String key=atts.getValue("key");
			String value=atts.getValue("value");
			if(key!=null&&!key.isEmpty()&&value!=null&&!value.isEmpty())
			{
				if(namespaces==null)
				{
					namespaces=new ArrayList<NameSpace>();
				}
				namespaces.add(new NameSpace(key, value));
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localname, String qname)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	@Override
	public List<NameSpace> getObject() {
		return namespaces;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
