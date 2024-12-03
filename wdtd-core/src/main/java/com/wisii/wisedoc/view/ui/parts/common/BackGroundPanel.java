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
 * @BackGroundPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.parts.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButtonStrip;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.common.popup.PopupPanelManager;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ImageFileFilter;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.LayerComboBox;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.ribbon.BindingTree;
import com.wisii.wisedoc.view.ui.text.UiText;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 类功能描述：设置填充属性的面板
 * 
 * 这里利用以下属性来初始化整个面板
 * PR_BACKGROUND_COLOR
 * PR_BACKGROUND_IMAGE
 * PR_BACKGROUND_REPEAT
 * PR_BACKGROUND_POSITION_HORIZONTAL
 * PR_BACKGROUND_POSITION_VERTICAL
 * 
 * 同时设置以下属性
 * PR_BACKGROUND_COLOR
 * PR_BACKGROUNDGRAPHIC_LAYER
 * PR_BACKGROUND_IMAGE
 * PR_BACKGROUND_IMAGE
 * PR_BACKGROUND_REPEAT
 * IMAGE_BACKGROUND_POSITION_HORIZONTAL
 * PR_BACKGROUND_POSITION_VERTICAL
 * 
 * 
 * 作者：zhangqiang 创建日期：2008-12-31
 */
public class BackGroundPanel extends JPanel
{
	private JTextField pathlabel = new JTextField();
	// 背景色选择框
	private ColorComboBox colorbox;
	// 背景颜色层标签
	JLabel colorlayerlabel = new JLabel(UiText.COLOR_LAYER);
	// 背景色的层
	private LayerComboBox colorlayerbox = new LayerComboBox();
	// 背景图片的层标签
	JLabel imagelayerlabel = new JLabel(UiText.COLOR_LAYER);
	// 背景图片的层
	LayerComboBox imagelayerbox = new LayerComboBox();
	// 平铺方式标签
	JLabel repeatlabel = new JLabel(UiText.REPEAT_SET);
	// 平铺方式下拉选择框
	WiseCombobox repeatbox = new WiseCombobox(UiText.REPEAT_SET_LIST);
	Map<Integer, Object> attributemap = new HashMap<Integer, Object>();
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
	// 动态图片选择按钮
	JCommandButton bandingpathbutton = new JCommandButton(UiText.DYNAMIC_PATH_BUTTON, MediaResource
			.getResizableIcon("07461.ico"));
	// 固定纵向位置设置控件
	FixedLengthSpinner posvspiner = new FixedLengthSpinner();

	public BackGroundPanel(Map<Integer, Object> atts, ActionType propertyType)
	{
//		初始化界面用的属性map，主要是为了处理传入atts为null的情况,此时使用initatt就不需要再判断非null了
		Map<Integer, Object> initatt = new HashMap<Integer, Object>();
		if(atts != null)
		{
			initatt.putAll(atts);
		}
//		如果类型为空的话，则所有设置控件均不可用
		boolean isallenable = propertyType!= null;
//		背景图片设置相关控件是否可用
		boolean isimagesetenable = isallenable;
//		if(isimagesetenable)
//		{
//			isimagesetenable = (propertyType != ActionFactory.ActionType.INLINE&&propertyType != ActionFactory.ActionType.BLOCK);
//		}
		setLayout(new VerticalFlowLayout());
		JPanel colorpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		colorpanel.setBorder(new TitledBorder(UiText.FILL_COLOR));
		try
		{
			colorbox = new ColorComboBox(false);
			Object oldcolor = initatt.get(Constants.PR_BACKGROUND_COLOR);
			if (oldcolor instanceof Color)
			{
				colorbox.setSelectedItem(new Color(((Color) oldcolor).getRGB()));
				if(oldcolor instanceof WiseDocColor)
				{
					colorlayerbox.setSelectedIndex(((WiseDocColor)oldcolor).getLayer());
				}
			}
			colorbox.setEnabled(isallenable);
			colorlayerbox.setEnabled(isallenable&&oldcolor != null);
			colorpanel.add(colorbox);
			colorpanel.add(colorlayerlabel);
			colorpanel.add(colorlayerbox);
			
			add(colorpanel);
		} catch (IncompatibleLookAndFeelException e)
		{
			LogUtil.debug(UiText.FILL_COLOR_ERROR, e);
		}
		JPanel imagepanel = new JPanel(new VerticalFlowLayout());
		imagepanel.setBorder(new TitledBorder(UiText.FILL_PICTURE));
		JPanel imagechoosepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pathlabel.setPreferredSize(new Dimension(100, 22));
		pathlabel.setEditable(false);
		Object backgroundimage = initatt.get(Constants.PR_BACKGROUND_IMAGE);
		imagelayerbox.setEnabled(false);
		if(backgroundimage != null&& backgroundimage !=Constants.NULLOBJECT)
		{
			pathlabel.setText(backgroundimage.toString());
			imagelayerbox.setEnabled(isimagesetenable);
		}
		JCommandButtonStrip bandingpathStrip = new JCommandButtonStrip();
		bandingpathbutton.setPopupRichTooltip(new RichTooltip(UiText.DYNAMIC_PATH_BUTTON_TITLE,
				UiText.DYNAMIC_PATH_BUTTON_DESCRIPTION));
		bandingpathbutton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		bandingpathbutton.setEnabled(isimagesetenable &&
				RibbonUpdateManager.Instance.getCurrentMainPanel().getDocument().getDataStructure() != null /*SystemManager.getCurruentDocument().getDataStructure() != null*/);
		pathbutton.setEnabled(isimagesetenable);
		
		bandingpathStrip.add(bandingpathbutton);
		imagechoosepanel.add(pathbutton);
		imagechoosepanel.add(bandingpathStrip);
		imagechoosepanel.add(pathlabel);
		imagechoosepanel.add(imagelayerlabel);
		imagechoosepanel.add(imagelayerbox);
		imagechoosepanel.add(removeimagebutton);
		JPanel imagerepeatpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		imagerepeatpanel.add(repeatlabel);
		imagerepeatpanel.add(repeatbox);
		repeatbox.setEnabled(isimagesetenable);
		if(isimagesetenable)
		{
			Object repeatenum = initatt.get(Constants.PR_BACKGROUND_REPEAT);
			if(repeatenum==null)
			{
				repeatenum = InitialManager.getInitialValue(Constants.PR_BACKGROUND_REPEAT, null);
			}

			if(repeatenum instanceof EnumProperty &&((EnumProperty)repeatenum).getEnum() > -1)
			{
				int index = ((EnumProperty)repeatenum).getEnum();
				switch (index)
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
			}
			else if(repeatenum == Constants.NULLOBJECT)
			{
				repeatbox.InitValue(null);
			}
		}
		JPanel impogepospanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		poshbox.setEnabled(isimagesetenable);
		posvbox.setEnabled(isimagesetenable);
		if(isimagesetenable)
		{
			Object posh = initatt.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL);
			if(posh == null)
			{
				posh = InitialManager.getInitialValue(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, null);
			}
			boolean isposhspinerenable = true;
			if(posh==null||posh == Constants.NULLOBJECT)
			{
				poshbox.InitValue(null);
				isposhspinerenable = false;
				
			}
			else if(posh instanceof FixedLength)
			{
				poshbox.initIndex(3);
				poshspiner.initValue(posh);
			}
			else if(posh instanceof PercentLength)
			{
				isposhspinerenable = false;
				PercentLength perposh = (PercentLength) posh;
				double value = perposh.value();
				double jingdu = 0.00001d;
				if(Math.abs(value-0) < jingdu)
				{
					poshbox.initIndex(0);
				}
				else if(Math.abs(value-0.5) < jingdu)
				{
					poshbox.initIndex(1);
				}
				else if(Math.abs(value-1) < jingdu)
				{
					poshbox.initIndex(2);
				}
			}
			Object posv = initatt.get(Constants.PR_BACKGROUND_POSITION_VERTICAL);
			if(posv == null)
			{
				posv = InitialManager.getInitialValue(Constants.PR_BACKGROUND_POSITION_VERTICAL, null);
			}
			boolean isposvspinerenable = true;
			if(posv==null||posv == Constants.NULLOBJECT)
			{
				posvbox.InitValue(null);
				isposvspinerenable = false;
				
			}
			else if(posv instanceof FixedLength)
			{
				posvbox.initIndex(3);
				posvspiner.initValue(posv);
			}
			else if(posv instanceof PercentLength)
			{
				isposvspinerenable = false;
				PercentLength perposv = (PercentLength) posv;
				double value = perposv.value();
				double jingdu = 0.00001d;
				if(Math.abs(value-0) < jingdu)
				{
					posvbox.initIndex(0);
				}
				else if(Math.abs(value-0.5) < jingdu)
				{
					posvbox.initIndex(1);
				}
				else if(Math.abs(value-1) < jingdu)
				{
					posvbox.initIndex(2);
				}
			}
			poshspiner.setEnabled(isposhspinerenable);
			posvspiner.setEnabled(isposvspinerenable);
		}
		else
		{
			poshspiner.setEnabled(false);
			posvspiner.setEnabled(false);
		}
		impogepospanel.add(poshlabel);
		impogepospanel.add(poshbox);
		impogepospanel.add(poshspiner);
		impogepospanel.add(posvlabel);
		impogepospanel.add(posvbox);
		impogepospanel.add(posvspiner);
		imagepanel.add(imagechoosepanel);
		imagepanel.add(imagerepeatpanel);
		imagepanel.add(impogepospanel);
		add(imagepanel);
		initAction();
	}

	/**
	 * 
	 * 初始化控件所有的监听时间
	 * 
	 * @param
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private void initAction()
	{
		colorbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == colorbox)
				{
					Object color = colorbox.getSelectedItem();
					if (color == null || color instanceof Color)
					{
						if(color instanceof Color)
						{
							int layer = colorlayerbox.getSelectedIndex();
							if(layer < 0)
							{
								layer = 0;
							}
							color = new WiseDocColor((Color) color,layer);
						}
						attributemap.put(Constants.PR_BACKGROUND_COLOR, color);
						colorlayerbox.setEnabled(color != null);
					}
				}
			}
		});
		colorlayerbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == colorlayerbox)
				{
					int layer = colorlayerbox.getLayer();
					if (layer > -1)
					{
						Color color = (Color) colorbox.getSelectedItem();
						WiseDocColor newcolor = new WiseDocColor(color, layer);
						attributemap.put(Constants.PR_BACKGROUND_COLOR,
								newcolor);
					}
				}

			}
		});
		imagelayerbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == imagelayerbox)
				{
					int layer = imagelayerbox.getLayer();
					if (layer > -1)
					{
						attributemap.put(Constants.PR_BACKGROUNDGRAPHIC_LAYER,
								layer);
					}
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
					attributemap.put(Constants.PR_BACKGROUND_IMAGE, openFile
							.getName());
					pathlabel.setText(openFile.getName());
					imagelayerbox.setEnabled(true);

				}
			}

		});
		bandingpathbutton.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(JCommandButton arg0)
			{
				BindingTree treepopup = new BindingTree();
				treepopup.getTree().addTreeSelectionListener(
						new TreeSelectionListener()
						{
							public void valueChanged(TreeSelectionEvent e)
							{
								if (e.getNewLeadSelectionPath() != null
										&& e.getNewLeadSelectionPath()
												.getLastPathComponent() instanceof BindNode)
								{
									BindNode node = (BindNode) e
											.getNewLeadSelectionPath()
											.getLastPathComponent();
									attributemap
											.put(Constants.PR_BACKGROUND_IMAGE,
													node);
									pathlabel.setText(node.toString());
									imagelayerbox.setEnabled(true);
								}
								PopupPanelManager.defaultManager()
										.hideLastPopup();

							}
						});
				return treepopup;
			}
		});
		removeimagebutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
			{
				attributemap.put(Constants.PR_BACKGROUND_IMAGE, null);
				pathlabel.setText("");
				imagelayerbox.setEnabled(false);
				
			}});
		repeatbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == repeatbox
						&& repeatbox.getSelectedIndex() != -1)
				{
					int repeatint = 0;
					int selectindex = repeatbox.getSelectedIndex();
					switch (selectindex)
					{
					case 0:
						repeatint = Constants.EN_NOREPEAT;
						break;
					case 1:
						repeatint = Constants.EN_REPEAT;
						break;
					case 2:
						repeatint = Constants.EN_REPEATX;
						break;

					default:
						repeatint = Constants.EN_REPEATY;
						break;
					}
					attributemap.put(Constants.PR_BACKGROUND_REPEAT,
							new EnumProperty(repeatint, ""));
				}

			}
		});
		poshbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == poshbox
						&& poshbox.getSelectedIndex() != -1)
				{
					PercentLength length = null;
					int selectindex = poshbox.getSelectedIndex();
					switch (selectindex)
					{
					case 0:
						length = new PercentLength(
								0d,
								new LengthBase(
										LengthBase.IMAGE_BACKGROUND_POSITION_HORIZONTAL));
						poshspiner.setEnabled(false);
						break;
					case 1:
						length = new PercentLength(
								0.5d,
								new LengthBase(
										LengthBase.IMAGE_BACKGROUND_POSITION_HORIZONTAL));
						poshspiner.setEnabled(false);
						break;
					case 2:
						length = new PercentLength(
								1d,
								new LengthBase(
										LengthBase.IMAGE_BACKGROUND_POSITION_HORIZONTAL));
						poshspiner.setEnabled(false);
						break;

					default:
						poshspiner.setEnabled(true);
						break;
					}
					if (length != null)
					{
						attributemap.put(
								Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
								length);
					}
				}

			}
		});
		posvbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == posvbox
						&& posvbox.getSelectedIndex() != -1)
				{
					PercentLength length = null;
					int selectindex = posvbox.getSelectedIndex();
					switch (selectindex)
					{
					case 0:
						length = new PercentLength(
								0d,
								new LengthBase(
										LengthBase.IMAGE_BACKGROUND_POSITION_VERTICAL));//以前的是IMAGE_BACKGROUND_POSITION_HORIZONTAL
						posvspiner.setEnabled(false);
						break;
					case 1:
						length = new PercentLength(
								0.5d,
								new LengthBase(
										LengthBase.IMAGE_BACKGROUND_POSITION_VERTICAL));//以前的是IMAGE_BACKGROUND_POSITION_HORIZONTAL
						posvspiner.setEnabled(false);
						break;
					case 2:
						length = new PercentLength(
								1d,
								new LengthBase(
										LengthBase.IMAGE_BACKGROUND_POSITION_VERTICAL));//以前的是IMAGE_BACKGROUND_POSITION_HORIZONTAL
						posvspiner.setEnabled(false);
						break;

					default:
						posvspiner.setEnabled(true);
						break;
					}
					if (length != null)
					{
						attributemap.put(
								Constants.PR_BACKGROUND_POSITION_VERTICAL,
								length);
					}
				}
			}
		});
		poshspiner.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == poshspiner)
				{
					FixedLength length = poshspiner.getValue();
					if (length != null)
					{
						attributemap.put(
								Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
								length);
					}
				}
			}

		});
		posvspiner.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == posvspiner)
				{
					FixedLength length = posvspiner.getValue();
					if (length != null)
					{
						attributemap.put(
								Constants.PR_BACKGROUND_POSITION_VERTICAL,
								length);
					}
				}
			}

		});

	}
	/**
	 * 
	 * 返回null表示没有进行属性设置，即设置不成功
	 *
	 * @param
	 * @return
	 * @exception
	 */
	public Map<Integer, Object> getProperties()
	{
		if (attributemap.isEmpty())
		{
			return null;
		}
		String pathtext = pathlabel.getText();
//		如果记不包含背景色，又不包含背景图片，则返回null，即设置不成功
	    if(!attributemap.containsKey(Constants.PR_BACKGROUND_COLOR)&&!attributemap.containsKey(Constants.PR_BACKGROUND_IMAGE)&&(pathtext==null||pathtext.trim().equals("")))
	    {
	    	return null;
	    }
		return attributemap;
	}
}
