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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.swing.ImageFileFilter;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.LayerComboBox;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.view.ui.model.CustomizeSimplePageMasterModel;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class BackGroundPanel extends JPanel
{

	CustomizeSimplePageMasterModel spmm;

	CommonBorderPaddingBackground borderpaddingbackground;

	int regiontype;

	String paneltitle;

	ColorComboBox colorbox;

	JTextField pathlabel = new JTextField();

	// 背景颜色层标签
	JLabel colorlayerlabel = new JLabel(UiText.COLOR_LAYER);

	// 背景色的层
	LayerComboBox colorlayerbox = new LayerComboBox();

	// 背景图片的层标签
	JLabel imagelayerlabel = new JLabel(UiText.COLOR_LAYER);

	// 背景图片的层
	LayerComboBox imagelayerbox = new LayerComboBox();

	// 平铺方式标签
	JLabel repeatlabel = new JLabel(UiText.REPEAT_SET);

	// 平铺方式下拉选择框
	WiseCombobox repeatbox = new WiseCombobox(UiText.REPEAT_SET_LIST);

	// Map<Integer, Object> attributemap = new HashMap<Integer, Object>();

	// 横向位置标签
	JLabel poshlabel = new JLabel(UiText.HORIZONTAL_POSITION);

	// 横向位置下拉选择框
	WiseCombobox poshbox = new WiseCombobox(UiText.HORIZONTAL_POSITION_LIST);

	// 横向固定值设置控件
	FixedLengthSpinner poshspiner = new FixedLengthSpinner();

	// 纵向位置标签
	JLabel posvlabel = new JLabel(UiText.VERTICAL_SET);

	// 纵向位置下拉选择框
	WiseCombobox posvbox = new WiseCombobox(UiText.VERTICAL_SET_LIST);

	// 静态图片路径选择按钮
	JButton pathbutton = new JButton(UiText.STATIC_PATH);

	JButton removeimagebutton = new JButton(UiText.REMOVE_BACKGROUNDIMAGE);

	// 固定纵向位置设置控件
	FixedLengthSpinner posvspiner = new FixedLengthSpinner();

	public BackGroundPanel(CustomizeSimplePageMasterModel spmm, int type,
			String title)
	{
		super();
		this.spmm = spmm;
		paneltitle = title;
		regiontype = type;
		if (spmm != null)
		{
			switch (regiontype)
			{
				case 0:
					borderpaddingbackground = spmm.getRegionBodyModel()
							.getRegionBodyBackground();
					break;
				case 1:
					borderpaddingbackground = spmm.getRegionBeforeModel()
							.getRegionBackground();
					break;
				case 2:
					borderpaddingbackground = spmm.getRegionAfterModel()
							.getRegionBackground();
					break;
				case 3:
					borderpaddingbackground = spmm.getRegionStartModel()
							.getRegionBackground();
					break;
				case 4:
					borderpaddingbackground = spmm.getRegionEndModel()
							.getRegionBackground();
					break;
			}
		}
		this.setLayout(new BorderLayout());
		initComponents();
	}

	public BackGroundPanel(String title, int type)
	{
		super();
		this.spmm = null;
		paneltitle = title;
		regiontype = type;
		borderpaddingbackground = null;
		this.setLayout(new BorderLayout());
		initComponents();
	}

	private void initComponents()
	{
		JPanel colorpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		try
		{
			colorbox = new ColorComboBox(false);
			Object oldcolor = borderpaddingbackground != null ? borderpaddingbackground
					.getBackgroundColor()
					: null;
			if (oldcolor == null)
			{
				colorbox.setSelectedItem(null);
				colorlayerbox.setEnabled(false);
			} else if (oldcolor instanceof Color)
			{
				colorbox
						.setSelectedItem(new Color(((Color) oldcolor).getRGB()));
				if (oldcolor instanceof WiseDocColor)
				{
					colorlayerbox.setSelectedIndex(((WiseDocColor) oldcolor)
							.getLayer());
				}
			}
			colorbox.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (colorbox.getSelectedItem() != null)
					{
						colorlayerbox.setEnabled(true);
					} else
					{
						colorlayerbox.setEnabled(false);
					}
				}
			});
		} catch (IncompatibleLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		colorpanel.add(colorbox);
		colorpanel.add(colorlayerlabel);
		colorpanel.add(colorlayerbox);
		colorpanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE),
				UiText.FILL_COLOR, TitledBorder.LEFT, TitledBorder.TOP));

		JPanel imagepanel = new JPanel();

		imagepanel.setLayout(new GridLayout(3, 1));
		JPanel one = new JPanel(new FlowLayout(FlowLayout.LEFT));

		one.add(pathbutton);
		pathlabel.setPreferredSize(new Dimension(150, 25));
		one.add(pathlabel);
		one.add(imagelayerlabel);
		one.add(imagelayerbox);
		one.add(removeimagebutton);
		JPanel two = new JPanel(new FlowLayout(FlowLayout.LEFT));

		two.add(repeatlabel);
		two.add(repeatbox);

		JPanel three = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

		three.add(poshlabel);
		three.add(poshbox);
		three.add(poshspiner);
		three.add(posvlabel);
		three.add(posvbox);
		three.add(posvspiner);

		imagepanel.add(one);

		imagepanel.add(two);

		imagepanel.add(three);
		imagepanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE),
				UiText.FILL_PICTURE, TitledBorder.LEFT, TitledBorder.TOP));

		this.setBorder(new TitledBorder(new LineBorder(Color.BLUE), paneltitle,
				TitledBorder.LEFT, TitledBorder.TOP));

		this.add(colorpanel, BorderLayout.NORTH);
		this.add(imagepanel, BorderLayout.CENTER);
		setInitialState();
		addAction();
	}

	public void setInitialState()
	{
		pathlabel.setEditable(false);
		if (borderpaddingbackground != null)
		{
			Object oldcolor = borderpaddingbackground != null ? borderpaddingbackground
					.getBackgroundColor()
					: null;
			if (oldcolor == null)
			{
				colorbox.setSelectedItem(null);
				colorlayerbox.setEnabled(false);
			} else if (oldcolor instanceof Color)
			{
				colorbox
						.setSelectedItem(new Color(((Color) oldcolor).getRGB()));
				if (oldcolor instanceof WiseDocColor)
				{
					colorlayerbox.setSelectedIndex(((WiseDocColor) oldcolor)
							.getLayer());
				}
			}
			String backgroundImage = borderpaddingbackground
					.getBackgroundImage();
			if (backgroundImage == null || backgroundImage.equals(""))
			{
				setImageNull(false);
			} else
			{
				pathlabel.setText(backgroundImage);
				setImageNull(true);
				int backgroundImageLayer = borderpaddingbackground
						.getBackgroundImageLayer();
				imagelayerbox.setSelectedIndex(backgroundImageLayer);
				int backgroundRepeat = borderpaddingbackground
						.getBackgroundRepeat();
				switch (backgroundRepeat)
				{
					case Constants.EN_NOREPEAT:
					{
						repeatbox.initIndex(0);
						break;
					}
					case Constants.EN_REPEAT:
					{
						repeatbox.initIndex(1);
						break;
					}
					case Constants.EN_REPEATX:
					{
						repeatbox.initIndex(2);
						break;
					}
					case Constants.EN_REPEATY:
					{
						repeatbox.initIndex(3);
						break;
					}
					default:
					{
						repeatbox.initIndex(1);
						break;
					}
				}
				Length backgroundPositionHorizontal = borderpaddingbackground
						.getBackgroundPositionHorizontal();

				if (backgroundPositionHorizontal == null)
				{
					poshbox.InitValue(null);
					poshspiner.setEnabled(false);
				} else if (backgroundPositionHorizontal instanceof FixedLength)
				{
					poshbox.initIndex(3);
					poshspiner.initValue(backgroundPositionHorizontal);
				} else if (backgroundPositionHorizontal instanceof PercentLength)
				{
					PercentLength perposh = (PercentLength) backgroundPositionHorizontal;
					double value = perposh.value();
					double jingdu = 0.00001d;
					if (Math.abs(value - 0) < jingdu)
					{
						poshbox.initIndex(0);
					} else if (Math.abs(value - 0.5) < jingdu)
					{
						poshbox.initIndex(1);
					} else if (Math.abs(value - 1) < jingdu)
					{
						poshbox.initIndex(2);
					}
					poshspiner.setEnabled(false);
				} else
				{
					poshbox.InitValue(null);
					poshspiner.setEnabled(false);
				}
				Length backgroundPositionVertical = borderpaddingbackground
						.getBackgroundPositionVertical();
				if (backgroundPositionVertical == null)
				{
					posvbox.InitValue(null);
					posvspiner.setEnabled(false);
				} else if (backgroundPositionVertical instanceof FixedLength)
				{
					posvbox.initIndex(3);
					posvspiner.initValue(backgroundPositionVertical);
				} else if (backgroundPositionVertical instanceof PercentLength)
				{
					PercentLength perposh = (PercentLength) backgroundPositionVertical;
					double value = perposh.value();
					double jingdu = 0.00001d;
					if (Math.abs(value - 0) < jingdu)
					{
						posvbox.initIndex(0);
					} else if (Math.abs(value - 0.5) < jingdu)
					{
						posvbox.initIndex(1);
					} else if (Math.abs(value - 1) < jingdu)
					{
						posvbox.initIndex(2);
					}
					posvspiner.setEnabled(false);
				} else
				{
					posvbox.InitValue(null);
					posvspiner.setEnabled(false);
				}
			}
		} else
		{
			setImageNull(false);
		}
	}

	public void setCommonBorderPaddingBackground(
			CustomizeSimplePageMasterModel spmm)
	{
		this.spmm = spmm;
		if (spmm != null)
		{
			switch (regiontype)
			{
				case 0:
					borderpaddingbackground = spmm.getRegionBodyModel()
							.getRegionBodyBackground();
					break;
				case 1:
					borderpaddingbackground = spmm.getRegionBeforeModel()
							.getRegionBackground();
					break;
				case 2:
					borderpaddingbackground = spmm.getRegionAfterModel()
							.getRegionBackground();
					break;
				case 3:
					borderpaddingbackground = spmm.getRegionStartModel()
							.getRegionBackground();
					break;
				case 4:
					borderpaddingbackground = spmm.getRegionEndModel()
							.getRegionBackground();
					break;
			}
		}
		setInitialState();
	}

	public void setImageNull(boolean flg)
	{
		if (!flg)
		{
			pathlabel.setText("");
		}
		pathlabel.setEnabled(flg);
		imagelayerbox.setEnabled(flg);
		removeimagebutton.setEnabled(flg);
		repeatbox.setEnabled(flg);
		poshbox.setEnabled(flg);
		posvbox.setEnabled(flg);
		if (flg && poshbox.getSelectedIndex() == 3)
		{
			poshspiner.setEnabled(true);
		} else
		{
			poshspiner.setEnabled(false);
		}

		if (flg && posvbox.getSelectedIndex() == 3)
		{
			posvspiner.setEnabled(true);
		} else
		{
			posvspiner.setEnabled(false);
		}
	}

	public void addAction()
	{
		colorbox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Object color = colorbox.getSelectedItem();
				WiseDocColor newbackgroundColor = null;
				if (color != null)
				{
					Color bcolor = (Color) color;
					newbackgroundColor = new WiseDocColor(bcolor, colorlayerbox
							.getSelectedIndex());
				}
				switch (regiontype)
				{
					case 0:
						spmm.getRegionBodyModel().setBodyBackgroundColor(
								newbackgroundColor);
						break;
					case 1:
						spmm.getRegionBeforeModel().setBodyBackgroundColor(
								newbackgroundColor);
						break;
					case 2:
						spmm.getRegionAfterModel().setBodyBackgroundColor(
								newbackgroundColor);
						break;
					case 3:
						spmm.getRegionStartModel().setBodyBackgroundColor(
								newbackgroundColor);
						break;
					case 4:
						spmm.getRegionEndModel().setBodyBackgroundColor(
								newbackgroundColor);
						break;
				}
			}
		});
		colorlayerbox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Object color = colorbox.getSelectedItem();
				WiseDocColor newbackgroundColor = null;
				if (color != null)
				{
					Color bcolor = (Color) color;
					newbackgroundColor = new WiseDocColor(bcolor, colorlayerbox
							.getSelectedIndex());
				}
				switch (regiontype)
				{
					case 0:
						spmm.getRegionBodyModel().setBodyBackgroundColor(
								newbackgroundColor);
						break;
					case 1:
						spmm.getRegionBeforeModel().setBodyBackgroundColor(
								newbackgroundColor);
						break;
					case 2:
						spmm.getRegionAfterModel().setBodyBackgroundColor(
								newbackgroundColor);
						break;
					case 3:
						spmm.getRegionStartModel().setBodyBackgroundColor(
								newbackgroundColor);
						break;
					case 4:
						spmm.getRegionEndModel().setBodyBackgroundColor(
								newbackgroundColor);
						break;
				}
			}
		});
		repeatbox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int index = repeatbox.getSelectedIndex();
				int backgroundRepeat = getBackgroundRepeat(index);
				EnumProperty bgrpeat = new EnumProperty(backgroundRepeat, "");
				switch (regiontype)
				{
					case 0:
						spmm.getRegionBodyModel().setBodyBackgroundImageRepeat(
								bgrpeat);
						break;
					case 1:
						spmm.getRegionBeforeModel()
								.setBodyBackgroundImageRepeat(bgrpeat);
						break;
					case 2:
						spmm.getRegionAfterModel()
								.setBodyBackgroundImageRepeat(bgrpeat);
						break;
					case 3:
						spmm.getRegionStartModel()
								.setBodyBackgroundImageRepeat(bgrpeat);
						break;
					case 4:
						spmm.getRegionEndModel().setBodyBackgroundImageRepeat(
								bgrpeat);
						break;
				}
			}
		});
		pathbutton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				File openFile = DialogSupport.getFileDialog(
						DialogSupport.FileDialogType.open,
						new ImageFileFilter());
				if (openFile != null)
				{
					String name = openFile.getName();
					pathlabel.setText(name);
					setImageNull(true);
					switch (regiontype)
					{
						case 0:
							spmm.getRegionBodyModel().setBodyBackgroundImage(
									name);
							break;
						case 1:
							spmm.getRegionBeforeModel().setBodyBackgroundImage(
									name);
							break;
						case 2:
							spmm.getRegionAfterModel().setBodyBackgroundImage(
									name);
							break;
						case 3:
							spmm.getRegionStartModel().setBodyBackgroundImage(
									name);
							break;
						case 4:
							spmm.getRegionEndModel().setBodyBackgroundImage(
									name);
							break;
					}
				}
			}
		});
		removeimagebutton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setImageNull(false);
				switch (regiontype)
				{
					case 0:
						spmm.getRegionBodyModel().setBodyBackgroundImage(null);
						break;
					case 1:
						spmm.getRegionBeforeModel()
								.setBodyBackgroundImage(null);
						break;
					case 2:
						spmm.getRegionAfterModel().setBodyBackgroundImage(null);
						break;
					case 3:
						spmm.getRegionStartModel().setBodyBackgroundImage(null);
						break;
					case 4:
						spmm.getRegionEndModel().setBodyBackgroundImage(null);
						break;
				}
			}
		});
		poshbox.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				int index = poshbox.getSelectedIndex();
				Length backgroundPositionHorizontal;
				if (index == 3)
				{
					poshspiner.setEnabled(true);
					backgroundPositionHorizontal = poshspiner.getValue();
				} else
				{
					poshspiner.setEnabled(false);
					double pesent = getPersent(index);
					backgroundPositionHorizontal = new PercentLength(
							pesent,
							new LengthBase(
									LengthBase.IMAGE_BACKGROUND_POSITION_VERTICAL));
				}
				switch (regiontype)
				{
					case 0:
						spmm.getRegionBodyModel()
								.setBodyBackgroundPositionHorizontal(
										backgroundPositionHorizontal);
						break;
					case 1:
						spmm.getRegionBeforeModel()
								.setBodyBackgroundPositionHorizontal(
										backgroundPositionHorizontal);
						break;
					case 2:
						spmm.getRegionAfterModel()
								.setBodyBackgroundPositionHorizontal(
										backgroundPositionHorizontal);
						break;
					case 3:
						spmm.getRegionStartModel()
								.setBodyBackgroundPositionHorizontal(
										backgroundPositionHorizontal);
						break;
					case 4:
						spmm.getRegionEndModel()
								.setBodyBackgroundPositionHorizontal(
										backgroundPositionHorizontal);
						break;
				}
			}
		});
		poshspiner.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Length backgroundPositionHorizontal = poshspiner.getValue();
				switch (regiontype)
				{
					case 0:
						spmm.getRegionBodyModel()
								.setBodyBackgroundPositionHorizontal(
										backgroundPositionHorizontal);
						break;
					case 1:
						spmm.getRegionBeforeModel()
								.setBodyBackgroundPositionHorizontal(
										backgroundPositionHorizontal);
						break;
					case 2:
						spmm.getRegionAfterModel()
								.setBodyBackgroundPositionHorizontal(
										backgroundPositionHorizontal);
						break;
					case 3:
						spmm.getRegionStartModel()
								.setBodyBackgroundPositionHorizontal(
										backgroundPositionHorizontal);
						break;
					case 4:
						spmm.getRegionEndModel()
								.setBodyBackgroundPositionHorizontal(
										backgroundPositionHorizontal);
						break;
				}

			}
		});
		posvbox.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				int index = posvbox.getSelectedIndex();
				Length backgroundPositionVertical;
				if (index == 3)
				{
					posvspiner.setEnabled(true);
					backgroundPositionVertical = posvspiner.getValue();
				} else
				{
					posvspiner.setEnabled(false);
					double pesent = getPersent(index);
					backgroundPositionVertical = new PercentLength(
							pesent,
							new LengthBase(
									LengthBase.IMAGE_BACKGROUND_POSITION_VERTICAL));
				}
				switch (regiontype)
				{
					case 0:
						spmm.getRegionBodyModel()
								.setBodyBackgroundPositionVertical(
										backgroundPositionVertical);
						break;
					case 1:
						spmm.getRegionBeforeModel()
								.setBodyBackgroundPositionVertical(
										backgroundPositionVertical);
						break;
					case 2:
						spmm.getRegionAfterModel()
								.setBodyBackgroundPositionVertical(
										backgroundPositionVertical);
						break;
					case 3:
						spmm.getRegionStartModel()
								.setBodyBackgroundPositionVertical(
										backgroundPositionVertical);
						break;
					case 4:
						spmm.getRegionEndModel()
								.setBodyBackgroundPositionVertical(
										backgroundPositionVertical);
						break;
				}
			}
		});
		posvspiner.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Length backgroundPositionVertical = posvspiner.getValue();
				switch (regiontype)
				{
					case 0:
						spmm.getRegionBodyModel()
								.setBodyBackgroundPositionVertical(
										backgroundPositionVertical);
						break;
					case 1:
						spmm.getRegionBeforeModel()
								.setBodyBackgroundPositionVertical(
										backgroundPositionVertical);
						break;
					case 2:
						spmm.getRegionAfterModel()
								.setBodyBackgroundPositionVertical(
										backgroundPositionVertical);
						break;
					case 3:
						spmm.getRegionStartModel()
								.setBodyBackgroundPositionVertical(
										backgroundPositionVertical);
						break;
					case 4:
						spmm.getRegionEndModel()
								.setBodyBackgroundPositionVertical(
										backgroundPositionVertical);
						break;
				}
			}
		});
		imagelayerbox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int backgroundImageLayer = imagelayerbox.getSelectedIndex();
				switch (regiontype)
				{
					case 0:
						spmm.getRegionBodyModel().setBodyBackgroundImageLayer(
								backgroundImageLayer);
						break;
					case 1:
						spmm.getRegionBeforeModel()
								.setBodyBackgroundImageLayer(
										backgroundImageLayer);
						break;
					case 2:
						spmm.getRegionAfterModel().setBodyBackgroundImageLayer(
								backgroundImageLayer);
						break;
					case 3:
						spmm.getRegionStartModel().setBodyBackgroundImageLayer(
								backgroundImageLayer);
						break;
					case 4:
						spmm.getRegionEndModel().setBodyBackgroundImageLayer(
								backgroundImageLayer);
						break;
				}

			}
		});
	}

	public int getBackgroundRepeat(int index)
	{
		int result = 0;
		switch (index)
		{
			case 0:
			{
				result = Constants.EN_NOREPEAT;
				break;
			}
			case 1:
			{
				result = Constants.EN_REPEAT;
				break;
			}
			case 2:
			{
				result = Constants.EN_REPEATX;
				break;
			}
			case 3:
			{
				result = Constants.EN_REPEATY;
				break;
			}
		}
		return result;
	}

	public double getPersent(int index)
	{
		double value = 0d;
		if (index == 1)
		{
			value = 0.5d;
		} else if (index == 2)
		{
			value = 1.0d;
		}
		return value;
	}

}
