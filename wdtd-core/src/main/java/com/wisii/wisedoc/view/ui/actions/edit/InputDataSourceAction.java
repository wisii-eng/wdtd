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
package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;
import java.util.Map;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.SwingDS;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.ribbon.edit.InputDataSourceDialog;

/**
 * @author wisii
 *
 */
public class InputDataSourceAction extends AbstractEditAction {

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.ui.actions.Actions#doAction(java.awt.event.ActionEvent)
	 */
	@Override
	public void doAction(ActionEvent e) {
		Map<Integer, Object> attrs = RibbonUIModel
				.getReadCompletePropertiesByType().get(actionType);
		SwingDS datasource = null;
		if (attrs != null)
		{
			datasource = (SwingDS) attrs.get(Constants.PR_DATA_SOURCE);
		}
		InputDataSourceDialog dialog = new InputDataSourceDialog(datasource);
		DialogResult result = dialog.showDialog();
		if (result == DialogResult.OK)
		{
			DataSource nds = dialog.getDataSource();
			setFOProperty(Constants.PR_DATA_SOURCE, nds);
		}
	}

}
