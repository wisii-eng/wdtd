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
 * @UpdateTableContentsAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.BasicLink;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Leader;
import com.wisii.wisedoc.document.PageNumberCitation;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
/**
 * 类功能描述：更新目录操作
 *
 * 作者：zhangqiang
 * 创建日期：2009-4-10
 */
public class UpdateTableContentsAction extends BaseAction
{
	public void doAction(ActionEvent e)
	{
		List<CellElement> tablecontentsblocks = getContentsBlocks();
		Document doc = getCurrentDocument();
		TableContents tablecontents = doc.getTableContents();
		List<CellElement> contentblocks = new ArrayList<CellElement>();
		if (tablecontentsblocks != null)
		{
			int maxblocklevel = (Integer)tablecontents.getAttribute(Constants.PR_BLOCK_REF_SHOW_LEVEL);
			for (CellElement block : tablecontentsblocks)
			{
				Integer blocklevel = (Integer) block
						.getAttribute(Constants.PR_BLOCK_LEVEL);
				// 如果设置有大纲属性
				if (blocklevel != null && blocklevel >= 0&&blocklevel <= maxblocklevel)
				{
					block.setAttribute(Constants.PR_ID, Integer.toHexString(block.hashCode())+"a");
					CellElement colneblock = new Block(tablecontents
							.getAttributesofLevel(blocklevel).getAttributes());
					List<CellElement> colneblockchildren = new ArrayList<CellElement>();
					int size = block.getChildCount();
					for (int i = 0; i < size; i++)
					{
						CellElement blockchild = (CellElement) block
								.getChildAt(i).clone();
						// 清除掉其中文本对象的文本属性，文本对象的文本属性
						if (blockchild instanceof TextInline)
						{
							blockchild.setAttributes(null, true);
						}
						colneblockchildren.add(blockchild);
					}
					// 每个目录项的标题内容放在basiclink对象中
					Map<Integer, Object> linkatt = new HashMap<Integer, Object>();
					linkatt.put(Constants.PR_INTERNAL_DESTINATION, block
							.getId());
					BasicLink baselink = new BasicLink(linkatt);
					baselink.insert(colneblockchildren, 0);
					colneblock.insert(baselink, 0);
					Attributes levelatt = tablecontents
							.getAttributesofLevel(blocklevel);
					if (levelatt != null)
					{
						colneblock
								.setAttributes(levelatt.getAttributes(), true);
					} else
					{
						colneblock.setAttributes(null, true);
					}
					Leader leader = new Leader(tablecontents
							.getAttributesofLeader().getAttributes());
					colneblock.insert(leader, 1);
					Map<Integer, Object> pagenatt = new HashMap<Integer, Object>();
					pagenatt.put(Constants.PR_REF_ID, block.getId());
					PageNumberCitation pagenumberc = new PageNumberCitation(pagenatt);
					colneblock.insert(pagenumberc, 2);
					contentblocks.add(colneblock);
				}
			}
		}
		Block block = new Block();
		block.insert(contentblocks, 0);
		List<CellElement> insertelements = new ArrayList<CellElement>();
		insertelements.add(block);
		doc.deleteElements(tablecontents.getAllChildren(), tablecontents);
		doc.insertElements(insertelements, tablecontents, 0);
	}

	private List<CellElement> getContentsBlocks()
	{
		Document doc = getCurrentDocument();
		List<CellElement> blocks = null;
		if (doc != null)
		{
			List<CellElement> flows = new ArrayList<CellElement>();
			int docsize = doc.getChildCount();
			for (int i = 0; i < docsize; i++)
			{
				CellElement element = (CellElement) doc.getChildAt(i);
				if (element instanceof PageSequence)
				{
					PageSequence pageSequence = (PageSequence) element;
					// 索引只对版心区的内容进行索引，因此只取版心区的内容；
					Flow mainflow = pageSequence.getMainFlow();
					flows.add(pageSequence.getMainFlow());
				}
			}
			blocks = DocumentUtil.getElements(flows, Block.class);
		}
		return blocks;
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		Document doc = getCurrentDocument();
		return doc != null && (doc.getTableContents() != null);
	}

}
