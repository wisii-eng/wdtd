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

package com.wisii.wisedoc.view.dialog;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.model.CustomizeRegionAfterModel;
import com.wisii.wisedoc.view.ui.model.CustomizeRegionBeforeModel;
import com.wisii.wisedoc.view.ui.model.CustomizeRegionBodyModel;
import com.wisii.wisedoc.view.ui.model.CustomizeRegionEndModel;
import com.wisii.wisedoc.view.ui.model.CustomizeRegionStartModel;
import com.wisii.wisedoc.view.ui.model.CustomizeSimplePageMasterModel;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class RegionalDirectionOverflowPanel extends JPanel
{

	CustomizeSimplePageMasterModel regionmodel;

	int regiontype;

	WiseCombobox orienation = new WiseCombobox(new DefaultComboBoxModel(
			UiText.PAGE_BODY_ORIENTATION_LIST));

	WiseCombobox textdirection = new WiseCombobox(new DefaultComboBoxModel(
			UiText.REGION_TEXT_DIRECTION_LIST));

	WiseCombobox displayalign = new WiseCombobox(new DefaultComboBoxModel(
			UiText.PAGE_BODY_DISPLAY_ALIGN_LIST));

	WiseCombobox overflow = new WiseCombobox(new DefaultComboBoxModel(
			UiText.PAGE_BODY_CONTENT_OVERFLOW_LIST));

	public RegionalDirectionOverflowPanel(int type)
	{
		super();
		regiontype = type;
		this.setLayout(new GridLayout(2, 1));
		initComponents();
	}

	public RegionalDirectionOverflowPanel(CustomizeSimplePageMasterModel model,
			int type)
	{
		super();
		regiontype = type;
		regionmodel = model;
		this.setLayout(new GridLayout(2, 1));
		initComponents();
	}

	private void initComponents()
	{
		JPanel one = new JPanel(new GridLayout(1, 2));
		JPanel oneleft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel oneright = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel bodyorienationlabel = new JLabel(UiText.ORIENTATION_LABEL);
		JLabel textdirectionlabel = new JLabel(UiText.TEXT_DIRECTION);
		oneleft.add(bodyorienationlabel);
		oneleft.add(orienation);
		oneright.add(textdirectionlabel);
		oneright.add(textdirection);
		one.add(oneleft);
		one.add(oneright);

		JPanel two = new JPanel(new GridLayout(1, 2));
		JPanel twoleft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel tworight = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel displayalignlabel = new JLabel(UiText.DISPLAY_ALIGN);
		JLabel overflowlabel = new JLabel(UiText.CONTENT_OVERFLOW);
		twoleft.add(displayalignlabel);
		twoleft.add(displayalign);
		tworight.add(overflowlabel);
		tworight.add(overflow);
		two.add(twoleft);
		two.add(tworight);

		this.add(one);
		this.add(two);
		setInitialState();
		addListener();
	}

	public void addListener()
	{
		orienation.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (regionmodel != null)
				{
					int index = orienation.getSelectedIndex();
					int orienationvalue = getOrienation(index);
					if (regiontype == 0)
					{
						CustomizeRegionBodyModel bodymodel = regionmodel
								.getRegionBodyModel();
						bodymodel.setBodyReferenceOrientation(orienationvalue);
					} else if (regiontype == 1)
					{
						CustomizeRegionBeforeModel beforemodel = regionmodel
								.getRegionBeforeModel();
						beforemodel.setReferenceOrientation(orienationvalue);
					} else if (regiontype == 2)
					{
						CustomizeRegionAfterModel aftermodel = regionmodel
								.getRegionAfterModel();
						aftermodel.setReferenceOrientation(orienationvalue);
					} else if (regiontype == 3)
					{
						CustomizeRegionStartModel startmodel = regionmodel
								.getRegionStartModel();
						startmodel.setReferenceOrientation(orienationvalue);
					} else if (regiontype == 4)
					{
						CustomizeRegionEndModel endmodel = regionmodel
								.getRegionEndModel();
						endmodel.setReferenceOrientation(orienationvalue);
					}
				}
			}
		});
		textdirection.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (regionmodel != null)
				{
					int index = textdirection.getSelectedIndex();
					int writestylevalue = getWriteStyle(index);
					if (regiontype == 0)
					{
						CustomizeRegionBodyModel bodymodel = regionmodel
								.getRegionBodyModel();
						bodymodel.setWritingMode(writestylevalue);
					} else if (regiontype == 1)
					{
						CustomizeRegionBeforeModel beforemodel = regionmodel
								.getRegionBeforeModel();
						beforemodel.setWritingMode(writestylevalue);
					} else if (regiontype == 2)
					{
						CustomizeRegionAfterModel aftermodel = regionmodel
								.getRegionAfterModel();
						aftermodel.setWritingMode(writestylevalue);
					} else if (regiontype == 3)
					{
						CustomizeRegionStartModel startmodel = regionmodel
								.getRegionStartModel();
						startmodel.setWritingMode(writestylevalue);
					} else if (regiontype == 4)
					{
						CustomizeRegionEndModel endmodel = regionmodel
								.getRegionEndModel();
						endmodel.setWritingMode(writestylevalue);
					}
				}
			}
		});
		displayalign.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (regionmodel != null)
				{
					int index = displayalign.getSelectedIndex();
					int displayalignvalue = getDisplayalign(index);
					if (regiontype == 0)
					{
						CustomizeRegionBodyModel bodymodel = regionmodel
								.getRegionBodyModel();
						bodymodel.setDisplayAlign(displayalignvalue);
					} else if (regiontype == 1)
					{
						CustomizeRegionBeforeModel beforemodel = regionmodel
								.getRegionBeforeModel();
						beforemodel.setDisplayAlign(displayalignvalue);
					} else if (regiontype == 2)
					{
						CustomizeRegionAfterModel aftermodel = regionmodel
								.getRegionAfterModel();
						aftermodel.setDisplayAlign(displayalignvalue);
					} else if (regiontype == 3)
					{
						CustomizeRegionStartModel startmodel = regionmodel
								.getRegionStartModel();
						startmodel.setDisplayAlign(displayalignvalue);
					} else if (regiontype == 4)
					{
						CustomizeRegionEndModel endmodel = regionmodel
								.getRegionEndModel();
						endmodel.setDisplayAlign(displayalignvalue);
					}
				}
			}
		});
		overflow.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (regionmodel != null)
				{
					int index = overflow.getSelectedIndex();
					int overflowvalue = getOverflow(index);
					if (regiontype == 0)
					{
						CustomizeRegionBodyModel bodymodel = regionmodel
								.getRegionBodyModel();
						bodymodel.setOverflow(overflowvalue);
					} else if (regiontype == 1)
					{
						CustomizeRegionBeforeModel beforemodel = regionmodel
								.getRegionBeforeModel();
						beforemodel.setOverflow(overflowvalue);
					} else if (regiontype == 2)
					{
						CustomizeRegionAfterModel aftermodel = regionmodel
								.getRegionAfterModel();
						aftermodel.setOverflow(overflowvalue);
					} else if (regiontype == 3)
					{
						CustomizeRegionStartModel startmodel = regionmodel
								.getRegionStartModel();
						startmodel.setOverflow(overflowvalue);
					} else if (regiontype == 4)
					{
						CustomizeRegionEndModel endmodel = regionmodel
								.getRegionEndModel();
						endmodel.setOverflow(overflowvalue);
					}
				}
			}
		});
	}

	public void setInitialState()
	{
		if (regionmodel != null)
		{

			if (regiontype == 0)
			{
				CustomizeRegionBodyModel bodymodel = regionmodel
						.getRegionBodyModel();
				setWriteStyle(bodymodel.getWritingMode());
				setOrienation(bodymodel.getBodyReferenceOrientation());
				setDisplayalign(bodymodel.getDisplayAlign());
				setOverflow(bodymodel.getOverflow());
			} else if (regiontype == 1)
			{
				CustomizeRegionBeforeModel beforemodel = regionmodel
						.getRegionBeforeModel();
				setWriteStyle(beforemodel.getWritingMode());
				setOrienation(beforemodel.getReferenceOrientation());
				setDisplayalign(beforemodel.getDisplayAlign());
				setOverflow(beforemodel.getOverflow());
			} else if (regiontype == 2)
			{
				CustomizeRegionAfterModel aftermodel = regionmodel
						.getRegionAfterModel();
				setWriteStyle(aftermodel.getWritingMode());
				setOrienation(aftermodel.getReferenceOrientation());
				setDisplayalign(aftermodel.getDisplayAlign());
				setOverflow(aftermodel.getOverflow());
			} else if (regiontype == 3)
			{
				CustomizeRegionStartModel startmodel = regionmodel
						.getRegionStartModel();
				setWriteStyle(startmodel.getWritingMode());
				setOrienation(startmodel.getReferenceOrientation());
				setDisplayalign(startmodel.getDisplayAlign());
				setOverflow(startmodel.getOverflow());
			} else if (regiontype == 4)
			{
				CustomizeRegionEndModel endmodel = regionmodel
						.getRegionEndModel();
				setWriteStyle(endmodel.getWritingMode());
				setOrienation(endmodel.getReferenceOrientation());
				setDisplayalign(endmodel.getDisplayAlign());
				setOverflow(endmodel.getOverflow());
			}
		} else
		{
			setWriteStyle(-1);
			setOrienation(0);
			setDisplayalign(Constants.EN_BEFORE);
			setOverflow(Constants.EN_AUTO);
		}
	}

	public void setModel(CustomizeSimplePageMasterModel model)
	{
		regionmodel = model;
		setInitialState();
	}

	public void setWriteStyle(int value)
	{
		switch (value)
		{
			case Constants.EN_LR_TB:
			{
				textdirection.setSelectedIndex(0);
				break;
			}
			case Constants.EN_RL_TB:
			{
				textdirection.setSelectedIndex(1);
				break;
			}
			case Constants.EN_TB_RL:
			{
				textdirection.setSelectedIndex(2);
				break;
			}
			default:
			{
				textdirection.setSelectedIndex(3);
				break;
			}
		}
	}

	public void setOrienation(int value)
	{
		switch (value)
		{
			case 0:
			{
				orienation.setSelectedIndex(0);
				break;
			}
			case 90:
			{
				orienation.setSelectedIndex(1);
				break;
			}
			case 180:
			{
				orienation.setSelectedIndex(2);
				break;
			}
			case 270:
			{
				orienation.setSelectedIndex(3);
				break;
			}
			case -90:
			{
				orienation.setSelectedIndex(4);
				break;
			}
			case -180:
			{
				orienation.setSelectedIndex(5);
				break;
			}
			case -270:
			{
				orienation.setSelectedIndex(6);
				break;
			}
		}
	}

	public void setDisplayalign(int value)
	{
		switch (value)
		{
			case Constants.EN_BEFORE:
			{
				displayalign.setSelectedIndex(0);
				break;
			}
			case Constants.EN_CENTER:
			{
				displayalign.setSelectedIndex(1);
				break;
			}
			case Constants.EN_AFTER:
			{
				displayalign.setSelectedIndex(2);
				break;
			}
		}
	}

	public void setOverflow(int value)
	{
		switch (value)
		{
			case Constants.EN_VISIBLE:
			{
				overflow.setSelectedIndex(0);
				break;
			}
			case Constants.EN_HIDDEN:
			{
				overflow.setSelectedIndex(1);
				break;
			}
		}
	}

	public int getWriteStyle(int index)
	{
		int writemodelvalue = Constants.EN_LR_TB;
		switch (index)
		{
			case 1:
			{
				writemodelvalue = Constants.EN_RL_TB;
				break;
			}
			case 2:
			{
				writemodelvalue = Constants.EN_TB_RL;
				break;
			}
			case 3:
			{
				writemodelvalue = -1;
				break;
			}
		}
		return writemodelvalue;
	}

	public int getOrienation(int index)
	{
		int result = 0;
		switch (index)
		{
			case 1:
			{
				result = 90;
				break;
			}
			case 2:
			{
				result = 180;
				break;
			}
			case 3:
			{
				result = 270;
				break;
			}
			case 4:
			{
				result = -90;
				break;
			}
			case 5:
			{
				result = -180;
				break;
			}
			case 6:
			{
				result = -270;
				break;
			}
		}
		return result;
	}

	public int getDisplayalign(int index)
	{
		int result = 0;
		switch (index)
		{
			case 0:
			{
				result = Constants.EN_BEFORE;
				break;
			}
			case 1:
			{
				result = Constants.EN_CENTER;
				break;
			}
			case 2:
			{
				result = Constants.EN_AFTER;
				break;
			}
		}
		return result;
	}

	public int getOverflow(int index)
	{
		int result = 0;
		switch (index)
		{
			case 0:
			{
				result = Constants.EN_VISIBLE;
				break;
			}
			case 1:
			{
				result = Constants.EN_HIDDEN;
				break;
			}
		}
		return result;
	}
}
