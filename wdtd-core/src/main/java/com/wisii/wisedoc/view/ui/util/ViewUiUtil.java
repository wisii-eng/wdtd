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

package com.wisii.wisedoc.view.ui.util;

import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterReference;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SinglePageMasterReference;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

public class ViewUiUtil
{

	public static SimplePageMaster getCurrentSimplePageMaster(
			ActionType actiontype)
	{
		Object currentobj = RibbonUIModel.getCurrentPropertiesByType().get(
				actiontype).get(Constants.PR_CURRENT_SIMPLE_PAGE_MASTER);
		if (currentobj == null)
		{
			Object spm = RibbonUIModel.getCurrentPropertiesByType().get(
					actiontype).get(Constants.PR_SIMPLE_PAGE_MASTER);

			if (spm != null)
			{
				return (SimplePageMaster) spm;
			}
			return null;
		}
		return (SimplePageMaster) currentobj;
	}

	public static Object getMaster(SimplePageMaster current,
			SimplePageMaster old, ActionType actiontype)
	{
		Object psmobj = RibbonUIModel.getCurrentPropertiesByType().get(
				actiontype).get(Constants.PR_PAGE_SEQUENCE_MASTER);
		if (psmobj == null)
		{
			return current;
		} else
		{
			PageSequenceMaster psm = (PageSequenceMaster) psmobj;
			List<SubSequenceSpecifier> subSequenceSpecifiers = psm
					.getSubsequenceSpecifiers();
			List<SubSequenceSpecifier> newsubSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();

			if (subSequenceSpecifiers != null)
			{
				for (SubSequenceSpecifier currentsss : subSequenceSpecifiers)
				{
					if (currentsss instanceof SinglePageMasterReference)
					{
						SimplePageMaster currentspm = ((SinglePageMasterReference) currentsss)
								.getMasterReference();
						SinglePageMasterReference newspmr;
						if (currentspm == old)
						{
							newspmr = new SinglePageMasterReference(current);
						} else
						{
							newspmr = new SinglePageMasterReference(currentspm);
						}
						newsubSequenceSpecifiers.add(newspmr);
					} else if (currentsss instanceof RepeatablePageMasterReference)
					{
						RepeatablePageMasterReference rpm = (RepeatablePageMasterReference) currentsss;
						SimplePageMaster currentspm = rpm.getMasterReference();
						RepeatablePageMasterReference newrpmr;
						int repeart = rpm.getMaximumRepeats();
						EnumNumber rpEnumNumber = new EnumNumber(-1, repeart);
						if (currentspm == old)
						{
							newrpmr = new RepeatablePageMasterReference(
									rpEnumNumber, current);
						} else
						{
							newrpmr = new RepeatablePageMasterReference(
									rpEnumNumber, currentspm);
						}
						newsubSequenceSpecifiers.add(newrpmr);
					} else if (currentsss instanceof RepeatablePageMasterAlternatives)
					{
						RepeatablePageMasterAlternatives currentrpma = (RepeatablePageMasterAlternatives) currentsss;
						List<ConditionalPageMasterReference> conditionalPageMasterRefs = currentrpma
								.getPageMasterRefs();
						List<ConditionalPageMasterReference> newconditionalPageMasterRefs = new ArrayList<ConditionalPageMasterReference>();
						if (conditionalPageMasterRefs != null)
						{
							for (ConditionalPageMasterReference currentcpmf : conditionalPageMasterRefs)
							{
								int position = currentcpmf.getPagePosition();
								int oddeven = currentcpmf.getOddOrEven();
								int blankorno = currentcpmf
										.getBlankOrNotBlank();
								SimplePageMaster currentcpmfspm = currentcpmf
										.getMasterReference();
								ConditionalPageMasterReference newcpmr;
								if (currentcpmfspm == old)
								{
									newcpmr = new ConditionalPageMasterReference(
											current, position, oddeven,
											blankorno);
								} else
								{
									newcpmr = new ConditionalPageMasterReference(
											currentcpmfspm, position, oddeven,
											blankorno);
								}
								newconditionalPageMasterRefs.add(newcpmr);
							}

							int repeart = currentrpma.getMaximumRepeats();
							EnumNumber rpEnumNumber;
							if (repeart == RepeatablePageMasterAlternatives.INFINITE)
							{

								rpEnumNumber = new EnumNumber(
										Constants.EN_NO_LIMIT, -1);
							} else
							{
								rpEnumNumber = new EnumNumber(-1, repeart);
							}

							RepeatablePageMasterAlternatives newrpmf = new RepeatablePageMasterAlternatives(
									rpEnumNumber, newconditionalPageMasterRefs);
							newsubSequenceSpecifiers.add(newrpmf);
						}
					}
				}
				PageSequenceMaster newpsm = new PageSequenceMaster(
						newsubSequenceSpecifiers);
				return newpsm;
			}
			return psm;
		}
	}
}
