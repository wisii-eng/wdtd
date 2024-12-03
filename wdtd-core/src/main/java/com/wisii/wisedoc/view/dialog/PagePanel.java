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

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.model.CustomizeSimplePageMasterModel;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class PagePanel extends JPanel
{

	CustomizeSimplePageMasterModel spmm;

	JRadioButton vertical = new JRadioButton(UiText.PAGE_PROTRAIT_BUTTON);

	JRadioButton parallel = new JRadioButton(UiText.PAGE_LANDSCAPE_BUTTON);

	WiseCombobox writemodel = new WiseCombobox(new DefaultComboBoxModel(
			UiText.PAGE_TEXT_DIRECTION_LIST));

	WiseCombobox paperformat = new WiseCombobox(new DefaultComboBoxModel(
			UiText.PAGE_PAPER_FORMAT_LIST));

	FixedLengthSpinner widthspinner = new FixedLengthSpinner(
			new SpinnerFixedLengthModel());

	FixedLengthSpinner heightspinner = new FixedLengthSpinner(
			new SpinnerFixedLengthModel());
	WiseTextField mediause = new WiseTextField(50);
	CommonMarginBlockPanel marginpanel;

	public PagePanel()
	{
		super();
		spmm = null;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		initComponents();
	}

	public PagePanel(CustomizeSimplePageMasterModel master)
	{
		super();
		spmm = master;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		initComponents();
	}

	public void addListener()
	{
		vertical.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				vertical.setSelected(true);
				parallel.setSelected(false);
				spmm.setPageOrientation(0);
			}
		});
		parallel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				vertical.setSelected(false);
				parallel.setSelected(true);
				spmm.setPageOrientation(90);
			}
		});
		paperformat.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				int index = paperformat.getSelectedIndex();
				setWidthAndHeight(index);
			}
		});

		widthspinner.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				FixedLength width = (FixedLength) ((FixedLengthSpinner) e
						.getSource()).getValue();
				int currentwidth = width.getValue();
				int currentheight = ((FixedLength) heightspinner.getValue())
						.getValue();
				paperformat.initIndex(getIndexSelect(currentwidth,
						currentheight));
				spmm.setPageWidth(width);
			}
		});
		heightspinner.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				FixedLength height = (FixedLength) ((FixedLengthSpinner) e
						.getSource()).getValue();
				int currentheight = height.getValue();
				int currentwidth = ((FixedLength) widthspinner.getValue())
						.getValue();
				paperformat.initIndex(getIndexSelect(currentwidth,
						currentheight));
				spmm.setPageHeight(height);
			}
		});
		writemodel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				int index = writemodel.getSelectedIndex();
				int wmvalue = getWritingMode(index);
				spmm.setPageWritingMode(wmvalue);
			}
		});
		mediause.addFocusListener(new FocusListener()
		{

			@Override
			public void focusLost(FocusEvent e)
			{
				spmm.setMediaUsage(mediause.getText());

			}

			@Override
			public void focusGained(FocusEvent e)
			{
				// TODO Auto-generated method stub

			}
		});
		mediause.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				spmm.setMediaUsage(mediause.getText());

			}
		});
	}

	private void initComponents()
	{
		setInitialState();
		JPanel paperdirection = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel paperorgpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		paperdirection.setPreferredSize(new Dimension(750, 40));
		JLabel paperorglabel = new JLabel(UiText.PAGE_ORIENTATION_LABEL);
		paperorgpanel.add(paperorglabel);
		paperorgpanel.add(vertical);
		paperorgpanel.add(parallel);

		JPanel wtitemodelpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel writelabel = new JLabel(UiText.PAGE_TEXT_DIRECTION_LABEL);

		wtitemodelpanel.add(writelabel);
		wtitemodelpanel.add(writemodel);
		paperdirection.add(paperorgpanel);
		paperdirection.add(wtitemodelpanel);

		JPanel papersize = new JPanel(new FlowLayout(FlowLayout.LEFT));
		papersize.setPreferredSize(new Dimension(750, 40));
		JLabel papersizelabel = new JLabel(UiText.PAGE_PAPER_FORMAT_LABEL);

		JLabel widthlabel = new JLabel(UiText.PAGE_WIDTH_LABEL);

		JLabel heightlabel = new JLabel(UiText.PAGE_HEIGHT_LABEL);

		papersize.add(papersizelabel);
		papersize.add(paperformat);
		papersize.add(widthlabel);
		papersize.add(widthspinner);
		papersize.add(heightlabel);
		papersize.add(heightspinner);

		CommonMarginBlock cmb = spmm != null ? spmm.getPageMargion() : null;
		marginpanel = new CommonMarginBlockPanel(spmm, cmb,
				UiText.PAGE_MARGINS_LABEL);
		JPanel mediausepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel mediauselabel = new JLabel(UiText.PAGE_MEDIAUSE);
		mediause.setToolTipText(UiText.PAGE_MEDIAUSE_HELP);
		mediausepanel.add(mediauselabel);
		mediausepanel.add(mediause);
		this.add(paperdirection);
		this.add(papersize);
		this.add(marginpanel);
		add(mediausepanel);
		addListener();
	}

	public void setInitialState()
	{
		if (spmm != null)
		{
			if (spmm.getPageOrientation() == 0)
			{
				vertical.setSelected(true);
				parallel.setSelected(false);
			} else
			{
				vertical.setSelected(false);
				parallel.setSelected(true);
			}
			int trValue = spmm.getPageWritingMode();
			if (trValue == Constants.EN_LR_TB)
			{
				writemodel.setSelectedIndex(0);
			} else if (trValue == Constants.EN_RL_TB)
			{
				writemodel.setSelectedIndex(1);
			} else if (trValue == Constants.EN_TB_RL)
			{
				writemodel.setSelectedIndex(2);
			}
			FixedLength widthlength = (FixedLength) spmm.getPageWidth();
			FixedLength heightlength = (FixedLength) spmm.getPageHeight();
			widthspinner.initValue(widthlength);
			heightspinner.initValue(heightlength);
			int millipointswidth = widthlength.getValue();
			int millipointsheight = heightlength.getValue();
			paperformat.initIndex(getIndexSelect(millipointswidth,
					millipointsheight));
			CommonMarginBlock cmb = spmm != null ? spmm.getPageMargion() : null;
			marginpanel.setCommonMarginBlock(spmm, cmb);
			String mediausevalue = spmm.getMediaUsage();
			if(mediausevalue!=null)
			{
				mediause.setText(mediausevalue);
			}
		}
	}

	public void setSimplePageMaster(
			CustomizeSimplePageMasterModel simplepagemaster)
	{
		spmm = simplepagemaster;
		setInitialState();
	}

	public CommonMarginBlock getCommonMarginBlock()
	{
		return marginpanel.getCommonMarginBlock();
	}

	public void setWidthAndHeight(int index)
	{
		double width = 0d;
		double height = 0d;
		switch (index)
		{
		case 0:
		{
			width = 21.59d;
			height = 27.94d;
			break;
		}
		case 1:
		{
			width = 27.94d;
			height = 43.17d;
			break;
		}
		case 2:
		{
			width = 43.17d;
			height = 27.94d;
			break;
		}
		case 3:
		{
			width = 29.7d;
			height = 42d;
			break;
		}
		case 4:
		{
			width = 21d;
			height = 29.7d;
			break;
		}
		case 5:
		{
			width = 14.8d;
			height = 21d;
			break;
		}
		case 6:
		{
			width = 25.7d;
			height = 36.4d;
			break;
		}
		case 7:
		{
			width = 18.2d;
			height = 25.7d;
			break;
		}
		case 8:
		{
			width = 18.4d;
			height = 26d;
			break;
		}
		case 9:
		{
			width = 13d;
			height = 18.4d;
			break;
		}
		case 10:
		{
			width = 14d;
			height = 20.3d;
			break;
		}
		}
		if (index != 11)
		{
			FixedLength widthLength = new FixedLength(width, "cm");
			FixedLength heightLength = new FixedLength(height, "cm");
			widthspinner.initValue(widthLength);
			heightspinner.initValue(heightLength);
			spmm.setPageHeight(heightLength);
			spmm.setPageWidth(widthLength);
		}
	}

	public FixedLength getPageWidth()
	{
		return widthspinner.getValue();
	}

	public FixedLength getPageHeight()
	{
		return heightspinner.getValue();
	}

	public int getReferenceOrientation()
	{
		int referenceorientation = 0;
		if (parallel.isSelected())
		{
			referenceorientation = 90;
		}
		return referenceorientation;
	}

	public int getWritingMode(int index)
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
		}
		return writemodelvalue;
	}

	public int getIndexSelect(int width, int height)
	{
		int value = 0;
		if (isApproximately(width, 21.59d) && isApproximately(height, 27.94d))
		{
			value = 0;
		} else if (isApproximately(width, 27.94d)
				&& isApproximately(height, 43.17d))
		{
			value = 1;
		} else if (isApproximately(width, 43.17d)
				&& isApproximately(height, 27.94d))
		{
			value = 2;
		} else if (isApproximately(width, 29.7d)
				&& isApproximately(height, 42d))
		{
			value = 3;
		} else if (isApproximately(width, 21d)
				&& isApproximately(height, 29.7d))
		{
			value = 4;
		} else if (isApproximately(width, 14.8d)
				&& isApproximately(height, 21d))
		{
			value = 5;
		} else if (isApproximately(width, 25.7d)
				&& isApproximately(height, 36.4d))
		{
			value = 6;
		} else if (isApproximately(width, 18.2d)
				&& isApproximately(height, 25.7d))
		{
			value = 7;
		} else if (isApproximately(width, 18.4d)
				&& isApproximately(height, 26d))
		{
			value = 8;
		} else if (isApproximately(width, 13d)
				&& isApproximately(height, 18.4d))
		{
			value = 9;
		} else if (isApproximately(width, 14d)
				&& isApproximately(height, 20.3d))
		{
			value = 10;
		} else
		{
			value = 11;
		}
		return value;
	}

	public boolean isApproximately(int length, double compare)
	{
		if (length > 0)
		{
			int current = getIntValue(compare);
			if (Math.abs(length - current) < 100)
			{
				return true;
			}
		}
		return false;
	}

	public int getIntValue(double length)
	{
		int result = 0;
		if (length == 21.59d)
		{
			result = 612000;
		} else if (length == 27.94d)
		{
			result = 792000;
		} else if (length == 43.17d)
		{
			result = 1223716;
		} else if (length == 21d)
		{
			result = 595275;
		} else if (length == 29.7d)
		{
			result = 841889;
		} else if (length == 42d)
		{
			result = 1190551;
		} else if (length == 14.8d)
		{
			result = 419527;
		} else if (length == 25.7d)
		{
			result = 728503;
		} else if (length == 36.4d)
		{
			result = 1031811;
		} else if (length == 18.2d)
		{
			result = 515905;
		} else if (length == 18.4d)
		{
			result = 521574;
		} else if (length == 26d)
		{
			result = 737007;
		} else if (length == 13d)
		{
			result = 368503;
		} else if (length == 14d)
		{
			result = 396850;
		} else if (length == 20.3d)
		{
			result = 575433;
		}
		return result;
	}
}