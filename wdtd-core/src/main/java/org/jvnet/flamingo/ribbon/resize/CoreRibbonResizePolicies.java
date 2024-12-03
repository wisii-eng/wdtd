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
 * Copyright (c) 2005-2009 Flamingo Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Flamingo Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package org.jvnet.flamingo.ribbon.resize;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import org.jvnet.flamingo.common.AbstractCommandButton;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.ribbon.JFlowRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import org.jvnet.flamingo.ribbon.ui.JBandControlPanel;
import org.jvnet.flamingo.ribbon.ui.JFlowBandControlPanel;
import org.jvnet.flamingo.ribbon.ui.JRibbonGallery;

public class CoreRibbonResizePolicies {
	protected static interface Mapping {
		RibbonElementPriority map(RibbonElementPriority priority);
	}

	public static List<RibbonBandResizePolicy> getCorePoliciesPermissive(
			JRibbonBand ribbonBand) {
		List<RibbonBandResizePolicy> result = new ArrayList<RibbonBandResizePolicy>();
		result.add(new CoreRibbonResizePolicies.None(ribbonBand
				.getControlPanel()));
		result.add(new CoreRibbonResizePolicies.Low2Mid(ribbonBand
				.getControlPanel()));
		result.add(new CoreRibbonResizePolicies.Mid2Mid(ribbonBand
				.getControlPanel()));
		result.add(new CoreRibbonResizePolicies.Mirror(ribbonBand
				.getControlPanel()));
		result.add(new CoreRibbonResizePolicies.Mid2Low(ribbonBand
				.getControlPanel()));
		result.add(new CoreRibbonResizePolicies.High2Mid(ribbonBand
				.getControlPanel()));
		result.add(new CoreRibbonResizePolicies.High2Low(ribbonBand
				.getControlPanel()));
		result
				.add(new IconRibbonBandResizePolicy(ribbonBand
						.getControlPanel()));
		return result;
	}

	public static List<RibbonBandResizePolicy> getCorePoliciesRestrictive(
			JRibbonBand ribbonBand) {
		List<RibbonBandResizePolicy> result = new ArrayList<RibbonBandResizePolicy>();
		result.add(new CoreRibbonResizePolicies.Mirror(ribbonBand
				.getControlPanel()));
		result.add(new CoreRibbonResizePolicies.Mid2Low(ribbonBand
				.getControlPanel()));
		result.add(new CoreRibbonResizePolicies.High2Mid(ribbonBand
				.getControlPanel()));
		result.add(new CoreRibbonResizePolicies.High2Low(ribbonBand
				.getControlPanel()));
		result
				.add(new IconRibbonBandResizePolicy(ribbonBand
						.getControlPanel()));
		return result;
	}

	public static List<RibbonBandResizePolicy> getCorePoliciesNone(
			JRibbonBand ribbonBand) {
		List<RibbonBandResizePolicy> result = new ArrayList<RibbonBandResizePolicy>();
		result.add(new CoreRibbonResizePolicies.Mirror(ribbonBand
				.getControlPanel()));
		result
				.add(new IconRibbonBandResizePolicy(ribbonBand
						.getControlPanel()));
		return result;
	}

	protected static abstract class BaseCoreRibbonBandResizePolicy extends
			BaseRibbonBandResizePolicy<JBandControlPanel> {
		protected Mapping mapping;

		protected BaseCoreRibbonBandResizePolicy(
				JBandControlPanel controlPanel, Mapping mapping) {
			super(controlPanel);
			this.mapping = mapping;
		}

		/**
		 * Returns the total width of the specified buttons.
		 * 
		 * @param bigButtons
		 *            List of buttons in big display state.
		 * @param mediumButtons
		 *            List of buttons in medium display state.
		 * @param smallButtons
		 *            List of buttons in small display state.
		 * @return Total width of the specified buttons.
		 */
		protected int getWidth(int gap,
				java.util.List<AbstractCommandButton> bigButtons,
				java.util.List<AbstractCommandButton> mediumButtons,
				java.util.List<AbstractCommandButton> smallButtons) {
			int result = 0;
			for (AbstractCommandButton top : bigButtons) {
				result += getPreferredWidth(top, RibbonElementPriority.TOP);
				result += gap;
			}

			int medSize = mediumButtons.size();
			if (medSize > 0) {
				// try to move buttons from low to med to make
				// three-somes.
				while (((mediumButtons.size() % 3) != 0)
						&& (smallButtons.size() > 0)) {
					AbstractCommandButton low = smallButtons.remove(0);
					mediumButtons.add(low);
				}
			}

			// at this point, mediumButtons list contains either
			// threesomes, or there are no buttons in lowButtons.
			int index3 = 0;
			int maxWidth3 = 0;
			for (AbstractCommandButton medium : mediumButtons) {
				int medWidth = getPreferredWidth(medium,
						RibbonElementPriority.MEDIUM);
				maxWidth3 = Math.max(maxWidth3, medWidth);
				index3++;

				if (index3 == 3) {
					// last button in threesome
					index3 = 0;
					result += maxWidth3;
					result += gap;
					maxWidth3 = 0;
				}
			}
			// at this point, maxWidth3 may be non-zero. We can safely
			// add it, since in this case there will be no buttons
			// left in lowButtons
			result += maxWidth3;
			if (maxWidth3 > 0)
				result += gap;

			index3 = 0;
			maxWidth3 = 0;
			for (AbstractCommandButton low : smallButtons) {
				int lowWidth = getPreferredWidth(low, RibbonElementPriority.LOW);
				maxWidth3 = Math.max(maxWidth3, lowWidth);
				index3++;

				if (index3 == 3) {
					// last button in threesome
					index3 = 0;
					result += maxWidth3;
					result += gap;
					maxWidth3 = 0;
				}
			}
			// at this point, maxWidth3 may be non-zero. We can safely
			// add it, since in this case there will be no buttons left
			result += maxWidth3;

			return result;
		}

		private int getPreferredWidth(AbstractCommandButton button,
				RibbonElementPriority buttonDisplayPriority) {
			CommandButtonDisplayState displayState = null;
			switch (buttonDisplayPriority) {
			case TOP:
				displayState = CommandButtonDisplayState.BIG;
				break;
			case MEDIUM:
				displayState = CommandButtonDisplayState.MEDIUM;
				break;
			case LOW:
				displayState = CommandButtonDisplayState.SMALL;
				break;
			}
			return displayState.createLayoutManager(button).getPreferredSize(
					button).width;
		}

		/**
		 * Returns the preferred width of <code>this</code> control panel in the
		 * specified collapse kind.
		 * 
		 * @param collapseKind
		 *            Collapse kind.
		 * @param availableHeight
		 *            Available height in pixels.
		 * @return Preferred width of <code>this</code> control panel in the
		 *         specified collapse kind.
		 */
		@Override
		public int getPreferredWidth(int availableHeight, int gap) {
			int result = 0;

			Insets ins = this.controlPanel.getInsets();

			for (JBandControlPanel.ControlPanelGroup controlPanelGroup : this.controlPanel
					.getControlPanelGroups()) {
				boolean isCoreContent = controlPanelGroup.isCoreContent();
				if (isCoreContent) {
					// if a group has a title, then the core components in that
					// group will take two rows instead of three
					int contentRows = (controlPanelGroup.getGroupTitle() == null) ? 3
							: 2;
					List<JRibbonComponent> ribbonComps = controlPanelGroup.getRibbonComps();
					int rowIndex = 0;
					int maxWidthInCurrColumn = 0;
					for (int i=0; i<ribbonComps.size(); i++) {
						JComponent coreComp = ribbonComps.get(i);
						int prefWidth = coreComp.getPreferredSize().width;
						maxWidthInCurrColumn = Math.max(maxWidthInCurrColumn, prefWidth);
						rowIndex++;
						if (rowIndex == contentRows) {
							result += maxWidthInCurrColumn;
							result += gap;
							rowIndex = 0;
							maxWidthInCurrColumn = 0;
						}
					}
					if (rowIndex < contentRows) {
						result += maxWidthInCurrColumn;
						result += gap;
					}
				} else {
					int galleryAvailableHeight = availableHeight - ins.top
							- ins.bottom;
					// ribbon galleries
					result += this.getPreferredGalleryWidth(controlPanelGroup,
							galleryAvailableHeight, gap);

					// ribbon buttons
					result += this.getPreferredButtonWidth(controlPanelGroup,
							gap);
				}

				result += gap;
			}
			// no gap after the last group
			result -= gap;

			result += ins.left + ins.right;

			return result;
		}

		protected int getPreferredButtonWidth(
				JBandControlPanel.ControlPanelGroup controlPanelGroup, int gap) {
			Map<RibbonElementPriority, List<AbstractCommandButton>> mapped = new HashMap<RibbonElementPriority, List<AbstractCommandButton>>();
			for (RibbonElementPriority rep : RibbonElementPriority.values()) {
				mapped.put(rep, new ArrayList<AbstractCommandButton>());
			}

			for (RibbonElementPriority elementPriority : RibbonElementPriority
					.values()) {
				// map the priority
				RibbonElementPriority mappedPriority = mapping
						.map(elementPriority);
				for (AbstractCommandButton button : controlPanelGroup
						.getRibbonButtons(elementPriority)) {
					// and add the button to a list based on the mapped priority
					mapped.get(mappedPriority).add(button);
				}
			}

			// at this point, the lists in the 'mapped' contain the buttons
			// grouped by the priority computed by the resize policy.
			return this.getWidth(gap, mapped.get(RibbonElementPriority.TOP),
					mapped.get(RibbonElementPriority.MEDIUM), mapped
							.get(RibbonElementPriority.LOW));

		}

		private int getPreferredGalleryWidth(
				JBandControlPanel.ControlPanelGroup controlPanelGroup,
				int galleryAvailableHeight, int gap) {
			int result = 0;
			for (RibbonElementPriority elementPriority : RibbonElementPriority
					.values()) {
				// map the priority
				RibbonElementPriority mappedPriority = mapping
						.map(elementPriority);
				// go over all galleries registered with the specific priority
				for (JRibbonGallery gallery : controlPanelGroup
						.getRibbonGalleries(elementPriority))
					// and take the preferred width under the mapped priority
					result += (gallery.getPreferredWidth(mappedPriority,
							galleryAvailableHeight) + gap);
			}

			return result;
		}

		@Override
		public void install(int availableHeight, int gap) {
			for (JBandControlPanel.ControlPanelGroup controlPanelGroup : this.controlPanel
					.getControlPanelGroups()) {
				// set the display priority for the galleries
				for (RibbonElementPriority elementPriority : RibbonElementPriority
						.values()) {
					// map the priority
					RibbonElementPriority mappedPriority = mapping
							.map(elementPriority);
					// go over all galleries registered with the specific
					// priority
					for (JRibbonGallery gallery : controlPanelGroup
							.getRibbonGalleries(elementPriority))
						// and set the display priority based on the specific
						// resize policy
						gallery.setDisplayPriority(mappedPriority);
				}

				// set the display priority for the buttons
				Map<RibbonElementPriority, List<AbstractCommandButton>> mapped = new HashMap<RibbonElementPriority, List<AbstractCommandButton>>();
				for (RibbonElementPriority rep : RibbonElementPriority.values()) {
					mapped.put(rep, new ArrayList<AbstractCommandButton>());
				}

				for (RibbonElementPriority elementPriority : RibbonElementPriority
						.values()) {
					// map the priority
					RibbonElementPriority mappedPriority = mapping
							.map(elementPriority);
					for (AbstractCommandButton button : controlPanelGroup
							.getRibbonButtons(elementPriority)) {
						// and add the button to a list based on the mapped
						// priority
						mapped.get(mappedPriority).add(button);
					}
				}

				// start from the top priority
				for (AbstractCommandButton big : mapped
						.get(RibbonElementPriority.TOP)) {
					big.setDisplayState(CommandButtonDisplayState.BIG);
				}

				// next - medium priority
				if (mapped.get(RibbonElementPriority.MEDIUM).size() > 0) {
					// try to move buttons from small to medium to make
					// three-somes.
					while (((mapped.get(RibbonElementPriority.MEDIUM).size() % 3) != 0)
							&& (mapped.get(RibbonElementPriority.LOW).size() > 0)) {
						AbstractCommandButton low = mapped.get(
								RibbonElementPriority.LOW).get(0);
						mapped.get(RibbonElementPriority.LOW).remove(low);
						mapped.get(RibbonElementPriority.MEDIUM).add(low);
					}
				}
				for (AbstractCommandButton medium : mapped
						.get(RibbonElementPriority.MEDIUM)) {
					medium.setDisplayState(CommandButtonDisplayState.MEDIUM);
				}

				// finally - low priority
				for (AbstractCommandButton low : mapped
						.get(RibbonElementPriority.LOW)) {
					low.setDisplayState(CommandButtonDisplayState.SMALL);
				}
			}
		}
	}

	public static final class None extends BaseCoreRibbonBandResizePolicy {
		public None(JBandControlPanel controlPanel) {
			super(controlPanel, new Mapping() {
				@Override
				public RibbonElementPriority map(RibbonElementPriority priority) {
					return RibbonElementPriority.TOP;
				}
			});
		}
	}

	public static final class Low2Mid extends BaseCoreRibbonBandResizePolicy {
		public Low2Mid(JBandControlPanel controlPanel) {
			super(controlPanel, new Mapping() {
				@Override
				public RibbonElementPriority map(RibbonElementPriority priority) {
					switch (priority) {
					case TOP:
						return RibbonElementPriority.TOP;
					case MEDIUM:
						return RibbonElementPriority.TOP;
					case LOW:
						return RibbonElementPriority.MEDIUM;
					}
					return null;
				}
			});
		}
	}

	public static final class Mid2Mid extends BaseCoreRibbonBandResizePolicy {
		public Mid2Mid(JBandControlPanel controlPanel) {
			super(controlPanel, new Mapping() {
				@Override
				public RibbonElementPriority map(RibbonElementPriority priority) {
					switch (priority) {
					case TOP:
						return RibbonElementPriority.TOP;
					case MEDIUM:
						return RibbonElementPriority.MEDIUM;
					case LOW:
						return RibbonElementPriority.MEDIUM;
					}
					return null;
				}
			});
		}
	}

	public static final class Mirror extends BaseCoreRibbonBandResizePolicy {
		public Mirror(JBandControlPanel controlPanel) {
			super(controlPanel, new Mapping() {
				@Override
				public RibbonElementPriority map(RibbonElementPriority priority) {
					return priority;
				}
			});
		}
	}

	public static final class Mid2Low extends BaseCoreRibbonBandResizePolicy {
		public Mid2Low(JBandControlPanel controlPanel) {
			super(controlPanel, new Mapping() {
				@Override
				public RibbonElementPriority map(RibbonElementPriority priority) {
					switch (priority) {
					case TOP:
						return RibbonElementPriority.TOP;
					case MEDIUM:
						return RibbonElementPriority.LOW;
					case LOW:
						return RibbonElementPriority.LOW;
					}
					return null;
				}
			});
		}
	}

	public static final class High2Mid extends BaseCoreRibbonBandResizePolicy {
		public High2Mid(JBandControlPanel controlPanel) {
			super(controlPanel, new Mapping() {
				@Override
				public RibbonElementPriority map(RibbonElementPriority priority) {
					switch (priority) {
					case TOP:
						return RibbonElementPriority.MEDIUM;
					case MEDIUM:
						return RibbonElementPriority.LOW;
					case LOW:
						return RibbonElementPriority.LOW;
					}
					return null;
				}
			});
		}
	}

	public static final class High2Low extends BaseCoreRibbonBandResizePolicy {
		public High2Low(JBandControlPanel controlPanel) {
			super(controlPanel, new Mapping() {
				@Override
				public RibbonElementPriority map(RibbonElementPriority priority) {
					return RibbonElementPriority.LOW;
				}
			});
		}
	}

	public static class FlowTwoRows extends
			BaseRibbonBandResizePolicy<JFlowBandControlPanel> {
		public FlowTwoRows(JFlowBandControlPanel controlPanel) {
			super(controlPanel);
		}

		@Override
		public int getPreferredWidth(int availableHeight, int gap) {
			int compCount = controlPanel.getFlowComponents().size();
			int[] widths = new int[compCount];
			int index = 0;
			int currBestResult = 0;
			for (JComponent flowComp : controlPanel.getFlowComponents()) {
				int pref = flowComp.getPreferredSize().width;
				widths[index++] = pref;
				currBestResult += (pref + gap);
			}

			// need to find the inflection point that results in
			// lowest value for max length of two sub-sequences
			for (int inflectionIndex = 0; inflectionIndex < (compCount - 1); inflectionIndex++) {
				int w1 = 0;
				for (int index1 = 0; index1 <= inflectionIndex; index1++) {
					w1 += widths[index1] + gap;
				}
				int w2 = 0;
				for (int index2 = inflectionIndex + 1; index2 < compCount; index2++) {
					w2 += widths[index2] + gap;
				}

				int width = Math.max(w1, w2);
				if (width < currBestResult)
					currBestResult = width;
			}

			return currBestResult;
		}

		@Override
		public void install(int availableHeight, int gap) {
		}
	}

	public static class FlowThreeRows extends
			BaseRibbonBandResizePolicy<JFlowBandControlPanel> {
		public FlowThreeRows(JFlowBandControlPanel controlPanel) {
			super(controlPanel);
		}

		@Override
		public int getPreferredWidth(int availableHeight, int gap) {
			int compCount = controlPanel.getFlowComponents().size();
			int[] widths = new int[compCount];
			int index = 0;
			int currBestResult = 0;
			for (JComponent flowComp : controlPanel.getFlowComponents()) {
				int pref = flowComp.getPreferredSize().width;
				widths[index++] = pref;
				currBestResult += (pref + gap);
			}

			// need to find the inflection points that results in
			// lowest value for max length of three sub-sequences
			for (int inflectionIndex1 = 0; inflectionIndex1 < (compCount - 2); inflectionIndex1++) {
				for (int inflectionIndex2 = inflectionIndex1 + 1; inflectionIndex2 < (compCount - 1); inflectionIndex2++) {
					int w1 = 0;
					for (int index1 = 0; index1 <= inflectionIndex1; index1++) {
						w1 += widths[index1] + gap;
					}
					int w2 = 0;
					for (int index2 = inflectionIndex1 + 1; index2 <= inflectionIndex2; index2++) {
						w2 += widths[index2] + gap;
					}
					int w3 = 0;
					for (int index3 = inflectionIndex2 + 1; index3 < compCount; index3++) {
						w3 += widths[index3] + gap;
					}

					int width = Math.max(Math.max(w1, w2), w3);
					if (width < currBestResult)
						currBestResult = width;
				}
			}

			return currBestResult;
		}

		@Override
		public void install(int availableHeight, int gap) {
		}
	}

	public static List<RibbonBandResizePolicy> getCoreFlowPoliciesRestrictive(
			JFlowRibbonBand ribbonBand, int stepsToRepeat) {
		List<RibbonBandResizePolicy> result = new ArrayList<RibbonBandResizePolicy>();
		for (int i = 0; i < stepsToRepeat; i++) {
			result.add(new FlowTwoRows(ribbonBand.getControlPanel()));
		}
		for (int i = 0; i < stepsToRepeat; i++) {
			result.add(new FlowThreeRows(ribbonBand.getControlPanel()));
		}
		result
				.add(new IconRibbonBandResizePolicy(ribbonBand
						.getControlPanel()));
		return result;
	}

}
