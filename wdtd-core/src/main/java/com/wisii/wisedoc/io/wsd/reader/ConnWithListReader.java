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
 * @ConnWithListReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.document.attribute.edit.Formula;
import com.wisii.wisedoc.document.attribute.edit.Parameter;
import com.wisii.wisedoc.document.attribute.edit.Parameter.DataTyle;
import com.wisii.wisedoc.document.attribute.edit.Parameter.ParamTyle;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.wsd.attribute.ConnWithWriter;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-4
 */
public class ConnWithListReader extends AbstractHandler
{
	private List<ConnWith> connwithlists = new ArrayList<ConnWith>();
	private List<Parameter> parmlist;
	private List<Formula> formulaslist;

	void init()
	{
		connwithlists = new ArrayList<ConnWith>();
	}

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (ConnWithWriter.CONNWITH.equals(qname))
		{
		} else if (ConnWithWriter.PARMS.equals(qname))
		{
			parmlist = new ArrayList<Parameter>();
		} else if (ConnWithWriter.FORMULAS.equals(qname))
		{
          formulaslist = new ArrayList<Formula>();			
		} else if (ConnWithWriter.PARM.equals(qname))
		{
          if(parmlist!=null)
          {
        	  String paraname = XMLUtil.fromXmlText(atts.getValue(ConnWithWriter.PARMNAME));
        	  if(paraname==null)
        	  {
        		  return;
        	  }
        	  String datatypestring = atts.getValue(ConnWithWriter.DATATYPE);
        	  DataTyle datatype=null; 
        	  if(datatypestring==null)
        	  {
        		  return;
        	  }
        	  else
        	  {
        		  datatype = DataTyle.valueOf(datatypestring);
        	  }
        	  if(datatype==null)
        	  {
        		  return;
        	  }
        	  String parmtypestring = atts.getValue(ConnWithWriter.PARMTYPE);
        	  ParamTyle paramtype=null; 
        	  if(parmtypestring==null)
        	  {
        		  return;
        	  }
        	  else
        	  {
        		  paramtype = ParamTyle.valueOf(parmtypestring);
        	  }
        	  if(paramtype==null)
        	  {
        		  return;
        	  }
        	  String parmvalue = XMLUtil.fromXmlText(atts.getValue(ConnWithWriter.PARMVALUE));
        	  Object value = parmvalue;
        	  if(parmvalue!=null&&parmvalue.startsWith(ConnWithWriter.NODETAG))
        	  {
        		  value = wsdhandler.getNode(parmvalue.substring(ConnWithWriter.NODETAG.length())); 
        	  }
        	  parmlist.add(new Parameter(datatype,paraname,paramtype,value));
          }
		} else if (ConnWithWriter.FORMULA.equals(qname))
		{
			 if(formulaslist!=null)
	          {
				 String expression = XMLUtil.fromXmlText(atts.getValue(ConnWithWriter.FORMULAEXPRESSION));
				 if(expression==null)
				 {
					 return;
				 }
				 String nodeid =  atts.getValue(ConnWithWriter.FORMULAXPATH);
				 BindNode xpathnode = wsdhandler.getNode(nodeid);
				 if(xpathnode==null)
				 {
					 return;
				 }
				 formulaslist.add(new Formula(expression,xpathnode));
	          }
		} else
		{
			throw new SAXException("chartdatalists 节点下的" + qname + "节点为非法");
		}
	}

	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (ConnWithWriter.CONNWITH.equals(qname))
		{
			if (parmlist != null && formulaslist != null&&!parmlist.isEmpty()&&!formulaslist.isEmpty())
			{
				connwithlists.add(new ConnWith(formulaslist, parmlist));
			} else
			{
				connwithlists.add(null);
			}
			parmlist = null;
			formulaslist = null;
		}
	}

	public List<ConnWith> getObject()
	{
		return connwithlists;
	}

}
