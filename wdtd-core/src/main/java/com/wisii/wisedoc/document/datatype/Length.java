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

/* $Id: Length.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.datatype;

/**
 * A length quantity in XSL
 */
public interface Length extends Numeric
{

	/**
	 * Returns the length in 1/1000ths of a point (millipoints)
	 * 
	 * @return the length in millipoints
	 */
	int getValue();

	/**
	 * Returns the length in 1/1000ths of a point (millipoints)
	 * 
	 * @param context
	 *            The context for the length calculation (for percentage based
	 *            lengths)
	 * @return the length in millipoints
	 */
	int getValue(PercentBaseContext context);

	Length clone();
}
