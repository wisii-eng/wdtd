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

import com.wisii.wisedoc.document.attribute.EnumProperty;

public class EditUI
{

	EnumProperty type;

	Map<Integer, Object> attr;

	String name;

	// public static int GROUP = 1;

	public static int CONN_WITH = 1;

	public static int CONN_WITH_OPTION = 2;

	public static int CONN_OPTION = 3;

	public EditUI(EnumProperty uitype, Map<Integer, Object> map)
	{
		type = uitype;
		attr = map;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public EnumProperty getType()
	{
		return type;
	}

	public void setAttr(Map<Integer, Object> map)
	{
		attr = map;
	}

	public Map<Integer, Object> getAttr()
	{
		return attr;
	}

	// public String getCode()
	// {
	// String code = "";
	// code = code + "<";
	// code = code + new EnumPropertyWriter().write(type);
	// code = code + ElementUtil.outputAttributes("name", getName());
	// String connwith = "";
	// String group = "";
	// Object[] keys = attr.keySet().toArray();
	// for (int i = 0; i < keys.length; i++)
	// {
	// Integer key = (Integer) keys[i];
	// if (key == Constants.PR_CONN_WITH)
	// {
	// EnumProperty type = new EnumProperty(Constants.PR_CONN_WITH, "");
	// Map<Integer, Object> editmap = new HashMap<Integer, Object>();
	// editmap.put(key, attr.get(key));
	// EditUI current = new ConnWithUI(type, editmap);
	// String edituiname = name + "conn";
	// current.setName(edituiname);
	// connwith = current.getCode();
	// code = code + ElementUtil.outputAttributes("conn", edituiname);
	// } else if (key == Constants.PR_GROUP_REFERANCE)
	// {
	// EnumProperty type = new EnumProperty(Constants.EN_GROUP, "");
	// Map<Integer, Object> editmap = new HashMap<Integer, Object>();
	// editmap.put(key, attr.get(key));
	// GroupUI current = new GroupUI(type, editmap);
	// String edituiname = name + "group";
	// current.setName(edituiname);
	// group = current.getCode();
	// code = code
	// + ElementUtil.outputAttributes("groupReference",
	// edituiname);
	// } else
	// {
	// code = code + getAttribute(key, attr.get(key));
	// }
	// }
	// code = code + "/>";
	// code = code + connwith;
	// code = code + group;
	// return code;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attr == null) ? 0 : attr.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		// result = prime * result + ((writer == null) ? 0 : writer.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EditUI other = (EditUI) obj;
		if (attr == null)
		{
			if (other.attr != null)
				return false;
		} else if (!attr.equals(other.attr))
			return false;
//		if (name == null)
//		{
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
		if (type == null)
		{
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		// if (writer == null)
		// {
		// if (other.writer != null)
		// return false;
		// } else if (!writer.equals(other.writer))
		// return false;
		return true;
	}
}
