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

/* $Id: LayoutMasterSet.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document;

import java.util.Map;

import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;

/**
 * The layout-master-set formatting object.
 * This class maintains the set of simple page master and
 * page sequence masters.
 * The masters are stored so that the page sequence can obtain
 * the required page master to create a page.
 * The page sequence masters can be reset as they hold state
 * information for a page sequence.
 */
public class LayoutMasterSet extends DefaultElement
{

	private Map _simplePageMasters;

	private Map _pageSequenceMasters;

	public LayoutMasterSet(Map simplePageMasters, Map pageSequenceMasters)
	{
		_simplePageMasters = simplePageMasters;
		_pageSequenceMasters = pageSequenceMasters;
	}

	private boolean existsName(String masterName)
	{
		if (_simplePageMasters.containsKey(masterName)
				|| _pageSequenceMasters.containsKey(masterName))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * Get a simple page master by name.
	 * This is used by the page sequence to get a page master for
	 * creating pages.
	 * @param masterName the name of the page master
	 * @return the requested simple-page-master
	 */
	public SimplePageMaster getSimplePageMaster(String masterName)
	{
		return (SimplePageMaster) _simplePageMasters.get(masterName);
	}

	/**
	 * Get a page sequence master by name.
	 * This is used by the page sequence to get a page master for
	 * creating pages.
	 * @param masterName name of the master
	 * @return the requested PageSequenceMaster instance
	 */
	public PageSequenceMaster getPageSequenceMaster(String masterName)
	{
		return (PageSequenceMaster) _pageSequenceMasters.get(masterName);
	}

}
