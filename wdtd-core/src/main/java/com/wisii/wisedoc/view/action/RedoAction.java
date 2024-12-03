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
 * @RedoAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.undo.DocumentUndoManager;
import com.wisii.wisedoc.view.select.SelectionModel;

/**
 * 重做功能类
 * 
 * 作者：zhangqiang 创建日期：2007-6-13
 */
public class RedoAction extends BaseAction
{
	public void doAction(ActionEvent e)
	{
		Document doc = getCurrentDocument();
		DocumentUndoManager uodo = doc.getDocumentUndoManager();
		if (uodo.canRedo())
		{
			SelectionModel selectionmodel = getEditorPanel()
					.getSelectionModel();
			selectionmodel.clearSelection();
			uodo.redo();
		}
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		Document doc = getCurrentDocument();
		DocumentUndoManager uodo = doc.getDocumentUndoManager();
		if (uodo.canRedo())
		{
			return true;
		}
		return false;
	}
}
