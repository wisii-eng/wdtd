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

package com.wisii.wisedoc.view.ui.ribbon.blockcontainer;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.view.ui.actions.blockcontainer.BlockContainerEndPositionAction;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.BlockContainer;
import com.wisii.wisedoc.view.ui.ribbon.WiseTask;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 高级块容器task
 * 
 * @author 闫舒寰
 * @version 1.0 2009/01/16
 */
public class BlockContainerTask implements WiseTask
{

	public RibbonTask getTask()
	{

		JRibbonBand blockSizeBand = new BlockSizeBand().getBand();
		BlockPositionandBand componentband = new BlockPositionandBand();
		JRibbonBand blockPositionband = componentband.getBand();

		JRibbonBand paddingband = new PaddingBand().getBand();
		JRibbonBand blockContainerOverFlowBand = new BlockContainerOverFlowBand()
				.getBlockContainerOverFlowBand();
		JRibbonBand backgroudBand = new BackGroundBand().getBand();
		JRibbonBand disali = new BlockContainerDisplayAlignBand().getBand();
		JRibbonBand contenttreat = new ContentTreatBand().getBand();
		RibbonTask blockContainerTask = new RibbonTask(
				RibbonUIText.BLOCK_CONTAINER_TASK, blockSizeBand,
				blockPositionband, paddingband, backgroudBand, disali,
				blockContainerOverFlowBand, contenttreat);

		BlockContainerEndPositionAction typeaction = (BlockContainerEndPositionAction) ActionFactory
				.getAction(BlockContainer.END_POSITION_ACTION);
		typeaction.setComponentband(componentband);
		return blockContainerTask;
	}
}
