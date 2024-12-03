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

package com.wisii.wisedoc.view.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;

public class StaticContentManeger
{

	private static Map<String, StaticContent> scmap = new HashMap<String, StaticContent>();

	private static List<String> realstaticcontent = new ArrayList<String>();

	public static Map<String, StaticContent> getScmap()
	{
		return scmap;
	}

	public static Map<String, StaticContent> getFinalScmap()
	{
		Object[] keys = scmap.keySet().toArray();
		for (Object key : keys)
		{
			if (!realstaticcontent.contains(key))
			{
				scmap.remove(key);
			}
		}
		return scmap;
	}
	public static void setScmap(Map<String, StaticContent> map)
	{
		scmap = map;
	}

	public static void clearScmap()
	{
		scmap.clear();
	}

	public static List<String> getRealstaticcontent()
	{
		return realstaticcontent;
	}

	public static void addRealstaticcontent(SimplePageMaster master)
	{
		Region before = master.getRegion(Constants.FO_REGION_BEFORE);
		if (before != null) {
			realstaticcontent.add(before.getRegionName());
		}
		Region after = master.getRegion(Constants.FO_REGION_AFTER);
		if (after != null) {
			realstaticcontent.add(after.getRegionName());
		}
		Region start = master.getRegion(Constants.FO_REGION_START);
		if (start != null) {
			realstaticcontent.add(start.getRegionName());
		}
		Region end = master.getRegion(Constants.FO_REGION_END);
		if (end != null) {
			realstaticcontent.add(end.getRegionName());
		}
	}

	public static void clearRealstaticcontent()
	{
		realstaticcontent.clear();
	}
}
