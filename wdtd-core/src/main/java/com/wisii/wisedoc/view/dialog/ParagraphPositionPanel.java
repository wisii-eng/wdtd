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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreeNode;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.FOText;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.render.awt.viewer.WisedocConfigurePreviewPanel;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 段落位置属性设置面板
 * @author 钟亚军
 *
 */
@SuppressWarnings("serial")
public class ParagraphPositionPanel extends JPanel
{

	// Map<Integer, Object> attrMap = ParagraphPanel.getAttr();

	String left = UiText.PARAGRAPH_TEXT_ALIGN_LIST[1];// "左对齐";

	String right = UiText.PARAGRAPH_TEXT_ALIGN_LIST[2];// "右对齐";

	String center = UiText.PARAGRAPH_TEXT_ALIGN_LIST[3];// "居中";

	String justify = UiText.PARAGRAPH_TEXT_ALIGN_LIST[4];// "匀满";

	String maxheight = UiText.PARAGRAPH_LINE_STACKING_STRATEGY_LIST[0];//"最大高度";

	String lineheight = UiText.PARAGRAPH_LINE_STACKING_STRATEGY_LIST[1];// "行高";

	String fontheight = UiText.PARAGRAPH_LINE_STACKING_STRATEGY_LIST[2];// "字高";

	String discard = UiText.PARAGRAPH_DISCARD;

	String retain = UiText.PARAGRAPH_RETAIN;

	String one = UiText.PARAGRAPH_INLINE_SPACE_LIST[0];// "单倍行距";

	String onepointfive = UiText.PARAGRAPH_INLINE_SPACE_LIST[1];// "1.5倍行距";

	String two = UiText.PARAGRAPH_INLINE_SPACE_LIST[2];// "2倍行距";

	String min = UiText.PARAGRAPH_INLINE_SPACE_LIST[3];// "最小值";

	String fixed = UiText.PARAGRAPH_INLINE_SPACE_LIST[4];// "固定值";

	String numbersof = UiText.PARAGRAPH_INLINE_SPACE_LIST[5];// "多倍行距";

	FixedLength fontsize = new FixedLength(12, "pt");

	WiseSpinner linespaceNumber = new WiseSpinner();
	{
		linespaceNumber.setPreferredSize(new Dimension(80, 25));
		SpinnerNumberModel number = new SpinnerNumberModel(1, 0, 100.000, 0.25);
		linespaceNumber.setModel(number);
	}

	FixedLengthSpinner linespaceFixedlength = new FixedLengthSpinner();
	{
		linespaceFixedlength.setPreferredSize(new Dimension(80, 25));
		linespaceFixedlength.setModel(new SpinnerFixedLengthModel());
	}

	FixedLengthSpinner oneLineHeight = new FixedLengthSpinner();
	{
		oneLineHeight.setModel(new SpinnerFixedLengthModel());
	}

	JComboBox lineSpace = new JComboBox();

	JComboBox lineHeightStrategy = new JComboBox();
	{
		lineHeightStrategy.addItem(maxheight);
		lineHeightStrategy.addItem(lineheight);
		lineHeightStrategy.addItem(fontheight);
		lineHeightStrategy
				.setSelectedItem(getAttribute(Constants.PR_LINE_STACKING_STRATEGY));
	}

	WiseDocDocument doc = createInlineLevelDoc(UiText.PARAGRAPH_PREVIEW_TEXT,
			300, 70);

	CardLayout cardLayout = new CardLayout();

	JPanel lhSpinnerPanel = new JPanel();
	{
		lhSpinnerPanel.setLayout(cardLayout);
		lhSpinnerPanel.add("length", linespaceFixedlength);
		lhSpinnerPanel.add("number", linespaceNumber);
	}

	public WiseDocDocument createInlineLevelDoc(String info, double w, double h)
	{
		WiseDocDocument doc = new WiseDocDocument(createPageSequence(w, h));
		TreeNode node = doc.getChildAt(0);
		PageSequence sequence = (PageSequence) node;
		Flow flow = (Flow) sequence.getChildAt(0);
		BlockContainer container = new BlockContainer();
		container.setAttribute(Constants.PR_INLINE_PROGRESSION_DIMENSION,
				new LengthRangeProperty(new FixedLength(w, "pt")));
		container.setAttribute(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
				new LengthRangeProperty(new FixedLength(h, "pt")));
		Block block1 = new Block();
		Block block2 = new Block();
		Inline inline = new Inline();
		EnumProperty center = new EnumProperty(Constants.EN_CENTER, "");
		block1.setAttribute(Constants.PR_TEXT_ALIGN, center);
		block1.setAttribute(Constants.PR_TEXT_ALIGN_LAST, center);
		block2.setAttribute(Constants.PR_TEXT_ALIGN, center);
		block2.setAttribute(Constants.PR_TEXT_ALIGN_LAST, center);
		FOText text = new FOText(inline, info.toCharArray());
		flow.add(container);
		container.add(block1);
		container.add(block2);
		block1.add(inline);
		block2.add(inline);
		inline.add(text);
		return doc;
	}

	public List<PageSequence> createPageSequence(double w, double h)
	{
		PageSequence sequence = new PageSequence(null);
		sequence.setAttribute(Constants.PR_SIMPLE_PAGE_MASTER,
				createSimplePageMaster(w, h));
		List<PageSequence> list = new ArrayList<PageSequence>();
		list.add(sequence);
		return list;
	}

	public SimplePageMaster createSimplePageMaster(double w, double h)
	{
		Length height = new FixedLength(h, "pt");// 80
		Length width = new FixedLength(w, "pt");// 250
		Map<Integer, Region> regions = new HashMap<Integer, Region>();
		Length extent = new FixedLength(0d, "cm");
		EnumProperty conditionality = new EnumProperty(Constants.EN_DISCARD, "");
		EnumNumber precedence = new EnumNumber(-1, 0);
		SpaceProperty space = new SpaceProperty((LengthProperty) extent,
				precedence, conditionality);
		CommonMarginBlock margin = new CommonMarginBlock(extent, extent,
				extent, extent, space, space, null, null);
		RegionBody body = new RegionBody(1, null, margin, null, null,
				Constants.EN_BEFORE, Constants.EN_AUTO, null, 0,
				Constants.EN_LR_TB);
		regions.put(Constants.FO_REGION_BODY, body);
		SimplePageMaster simple = new SimplePageMaster(null, height, width, 0,
				Constants.EN_LR_TB, regions, "");
		body.setLayoutMaster(simple);
		return simple;
	}

	WisedocConfigurePreviewPanel previewPanel = new WisedocConfigurePreviewPanel(
			doc);
	{
		previewPanel.initValues(RibbonUIModel.getReadCompletePropertiesByType()
				.get(ActionType.BLOCK), ActionType.BLOCK, false);
	}

	/**
	 * This is the default constructor
	 */
	public ParagraphPositionPanel()
	{
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setPreferredSize(new Dimension(460, 500));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		JPanel textAlignPanel = new JPanel();
		textAlignPanel.setPreferredSize(new Dimension(450, 60));
		FlowLayout alignLayout = new FlowLayout();
		alignLayout.setAlignment(FlowLayout.LEFT);
		textAlignPanel.setLayout(alignLayout);

		JPanel lastTextAlign = new JPanel();
		final JComboBox lasttextaligncomboBox = new JComboBox();
		lasttextaligncomboBox.addItem(left);
		lasttextaligncomboBox.addItem(right);
		lasttextaligncomboBox.addItem(center);
		lasttextaligncomboBox.addItem(justify);
		lasttextaligncomboBox
				.setSelectedItem(getAttribute(Constants.PR_TEXT_ALIGN_LAST));
		lasttextaligncomboBox.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					setAttribute(Constants.PR_TEXT_ALIGN_LAST,
							getEnumProperty(current));
				}
			}
		});

		lastTextAlign.add(new JLabel(UiText.PARAGRAPH_TEXT_ALIGN_LAST_LABEL));
		lastTextAlign.add(lasttextaligncomboBox);

		JPanel textAlign = new JPanel();
		WiseCombobox textaligncomboBox = new WiseCombobox();
		textaligncomboBox.addItem(left);
		textaligncomboBox.addItem(right);
		textaligncomboBox.addItem(center);
		textaligncomboBox.addItem(justify);
		textaligncomboBox
				.setSelectedItem(getAttribute(Constants.PR_TEXT_ALIGN));
		textaligncomboBox.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					setAttribute(Constants.PR_TEXT_ALIGN,
							getEnumProperty(current));
					if (current.equalsIgnoreCase(justify))
					{
						lasttextaligncomboBox.setSelectedItem(left);
						setAttribute(Constants.PR_TEXT_ALIGN_LAST,
								getEnumProperty(left));
					} else
					{
						lasttextaligncomboBox.setSelectedItem(current);
						setAttribute(Constants.PR_TEXT_ALIGN_LAST,
								getEnumProperty(current));
					}
				}
			}
		});
		textAlign.add(new JLabel(UiText.PARAGRAPH_TEXT_ALIGN_LABEL));
		textAlign.add(textaligncomboBox);

		textAlignPanel.add(textAlign);
		textAlignPanel.add(lastTextAlign);
		textAlignPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				UiText.PARAGRAPH_TEXT_ALIGN_LABEL, TitledBorder.LEFT,
				TitledBorder.TOP));

		JPanel marginPanel = new JPanel();
		GridLayout marginLayout = new GridLayout(2, 2, 1, 1);
		marginPanel.setPreferredSize(new Dimension(450, 80));
		marginPanel.setLayout(marginLayout);

		JPanel startindentPanel = new JPanel();
		startindentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		if (getAttributeValue(Constants.PR_FONT_SIZE) != null)
		{
			fontsize = (FixedLength) getAttributeValue(Constants.PR_FONT_SIZE);
		}
		FixedLength minlen = new FixedLength(-9000d,"pt");
		FixedLength maxlen = new FixedLength(9000d,"pt");
		FixedLength deflen = new FixedLength(0d,"pt");
		FixedLengthSpinner startindent = new FixedLengthSpinner(new SpinnerFixedLengthModel(deflen,minlen,maxlen,-1));
		startindent.initValue(getAttributeValue(Constants.PR_START_INDENT));
		startindent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_START_INDENT, ((FixedLengthSpinner) e
						.getSource()).getValue());
			};

		});
		startindentPanel.add(new JLabel(UiText.PARAGRAPH_END_INDENT_LABEL));
		startindentPanel.add(startindent);

		JPanel endindentPanel = new JPanel();
		endindentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		FixedLengthSpinner endindent = new FixedLengthSpinner(new SpinnerFixedLengthModel(deflen,minlen,maxlen,-1));
		endindent.initValue(getAttributeValue(Constants.PR_END_INDENT));
		endindent.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_END_INDENT, ((FixedLengthSpinner) e
						.getSource()).getValue());
			};

		});

		endindentPanel.add(new JLabel(UiText.PARAGRAPH_START_INDENT_LABEL));
		endindentPanel.add(endindent);

		JPanel textindentPanel = new JPanel();
		textindentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		FixedLengthSpinner textindent = new FixedLengthSpinner();
		textindent.initValue(getAttributeValue(Constants.PR_TEXT_INDENT));
		textindent.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_TEXT_INDENT, ((FixedLengthSpinner) e
						.getSource()).getModel().getValue());

			};

		});

		textindentPanel
				.add(new JLabel(UiText.PARAGRAPH_SPECIAL_INDENT_LIST[1]));
		textindentPanel.add(textindent);

		JPanel hangingindentPanel = new JPanel();
		hangingindentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		FixedLengthSpinner hangingindent = new FixedLengthSpinner();
		Object hangingind=getAttributeValue(Constants.PR_HANGING_INDENT);
		if(hangingind==null)
		{
			hangingind = new FixedLength(0d,"pt");
		}
		hangingindent.initValue(hangingind);
		hangingindent.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_HANGING_INDENT, ((FixedLengthSpinner) e
						.getSource()).getModel().getValue());
			};
		});

		hangingindentPanel.add(new JLabel(
				UiText.PARAGRAPH_SPECIAL_INDENT_LIST[2]));
		hangingindentPanel.add(hangingindent);

		marginPanel.add(startindentPanel);
		marginPanel.add(endindentPanel);
		marginPanel.add(textindentPanel);
		marginPanel.add(hangingindentPanel);

		marginPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				UiText.PARAGRAPH_INDENT_SET_LABEL, TitledBorder.LEFT,
				TitledBorder.TOP));

		JPanel spacePanel = new JPanel();
		GridLayout spaceLayout = new GridLayout(2, 1, 1, 1);
		spacePanel.setPreferredSize(new Dimension(450, 80));
		spacePanel.setLayout(spaceLayout);

		JPanel spaceBeforePanel = new JPanel();
		FlowLayout spacebeforelayout = new FlowLayout();
		spacebeforelayout.setAlignment(FlowLayout.LEFT);
		spaceBeforePanel.setLayout(spacebeforelayout);

		FixedLengthSpinner spacebefore = new FixedLengthSpinner();
		spacebefore.setModel(new SpinnerFixedLengthModel());
		spacebefore.initValue(getAttributeValue(Constants.PR_SPACE_BEFORE));
		spacebefore.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				setAttribute(Constants.PR_SPACE_BEFORE, getSpaceProperty(
						(FixedLengthSpinner) e.getSource(),
						Constants.PR_SPACE_BEFORE));
			};

		});

		final JCheckBox spaceBeforeConditionality = new JCheckBox(
				UiText.PARAGRAPH_SPACE_BEFORE_KEEP_IN_FIRST_PAGE);
		spaceBeforeConditionality
				.setSelected(getAttributeBoolean(Constants.PR_SPACE_BEFORE));
		spaceBeforeConditionality.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_SPACE_BEFORE, getSpaceProperty(
						spaceBeforeConditionality.isSelected(),
						Constants.PR_SPACE_BEFORE));
			}
		});

		spaceBeforePanel.add(new JLabel(
				UiText.PARAGRAPH_BLOCK_SPACE_BEFORE_LABEL));
		spaceBeforePanel.add(spacebefore);
		spaceBeforePanel.add(spaceBeforeConditionality);

		JPanel spaceAfterPanel = new JPanel();
		FlowLayout spaceafterlayout = new FlowLayout();
		spaceafterlayout.setAlignment(FlowLayout.LEFT);
		spaceAfterPanel.setLayout(spaceafterlayout);

		FixedLengthSpinner spaceafter = new FixedLengthSpinner();
		spaceafter.setModel(new SpinnerFixedLengthModel());
		spaceafter.initValue(getAttributeValue(Constants.PR_SPACE_AFTER));
		spaceafter.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				setAttribute(Constants.PR_SPACE_AFTER, getSpaceProperty(
						(FixedLengthSpinner) e.getSource(),
						Constants.PR_SPACE_AFTER));
			};

		});

		final JCheckBox spaceAfterConditionality = new JCheckBox(
				UiText.PARAGRAPH_SPACE_AFTER_KEEP_IN_LAST_PAGE);
		spaceAfterConditionality
				.setSelected(getAttributeBoolean(Constants.PR_SPACE_AFTER));
		spaceAfterConditionality.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setAttribute(Constants.PR_SPACE_AFTER, getSpaceProperty(
						spaceAfterConditionality.isSelected(),
						Constants.PR_SPACE_AFTER));
			}
		});

		spaceAfterPanel
				.add(new JLabel(UiText.PARAGRAPH_BLOCK_SPACE_AFTER_LABEL));
		spaceAfterPanel.add(spaceafter);
		spaceAfterPanel.add(spaceAfterConditionality);

		spacePanel.add(spaceBeforePanel);
		spacePanel.add(spaceAfterPanel);
		spacePanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				UiText.PARAGRAPH_BLOCK_SPACE_SET_LABEL, TitledBorder.LEFT,
				TitledBorder.TOP));

		JPanel linePanel = new JPanel();
		GridLayout lineLayout = new GridLayout(2, 1, 1, 1);
		linePanel.setPreferredSize(new Dimension(450, 90));
		linePanel.setLayout(lineLayout);

		JPanel lineSpacePanel = new JPanel();
		FlowLayout linespacelayout = new FlowLayout();
		linespacelayout.setAlignment(FlowLayout.LEFT);
		lineSpacePanel.setLayout(linespacelayout);
		lineSpace.addItem(one);
		lineSpace.addItem(onepointfive);
		lineSpace.addItem(two);
		lineSpace.addItem(min);
		lineSpace.addItem(fixed);
		lineSpace.addItem(numbersof);
		String selectOne = getAttribute(Constants.PR_LINE_HEIGHT);
		lineSpace.setSelectedItem(selectOne);
		if (selectOne.equalsIgnoreCase(fixed))
		{
			cardLayout.show(lhSpinnerPanel, "length");
			linespaceFixedlength
					.initValue(getAttributeValue(Constants.PR_LINE_HEIGHT));
			linespaceFixedlength.updateUI();
			lhSpinnerPanel.updateUI();

		} else
		{
			cardLayout.show(lhSpinnerPanel, "number");
			if (selectOne.equalsIgnoreCase(numbersof))
			{
				linespaceNumber.initValue(getNumber(Constants.PR_LINE_HEIGHT));
			} else
			{
				double num = 1;
				if (selectOne.equalsIgnoreCase(onepointfive))
				{
					num = 1.5d;
				} else if (selectOne.equalsIgnoreCase(two))
				{
					num = 2d;
				} else if (selectOne.equalsIgnoreCase(min))
				{
					num = 0.83333d;
				}
				linespaceNumber.initValue(num);
			}
			linespaceNumber.updateUI();

			lhSpinnerPanel.updateUI();
		}
		lineSpace.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					lineHeightStrategy.setSelectedItem(lineheight);
					String current = event.getItem().toString();
					if (current.equalsIgnoreCase(numbersof))
					{
						cardLayout.show(lhSpinnerPanel, "number");
						linespaceNumber
								.initValue(getNumber(Constants.PR_LINE_HEIGHT));
						linespaceNumber.updateUI();
						lhSpinnerPanel.updateUI();
					} else if (current.equalsIgnoreCase(fixed))
					{
						cardLayout.show(lhSpinnerPanel, "length");
						linespaceFixedlength
								.setModel(new SpinnerFixedLengthModel());
						linespaceFixedlength
								.initValue(getAttributeValue(Constants.PR_LINE_HEIGHT));
						linespaceFixedlength.updateUI();

						lhSpinnerPanel.updateUI();
					} else
					{
						cardLayout.show(lhSpinnerPanel, "number");
						double number = 1d;
						if (current.equalsIgnoreCase(onepointfive))
						{
							number = 1.5d;
						} else if (current.equalsIgnoreCase(two))
						{
							number = 2d;
						} else if (current.equalsIgnoreCase(min))
						{
							number = 0.83333d;
						}
						setAttribute(Constants.PR_LINE_HEIGHT,
								getSpaceProperty(number));
						linespaceNumber.initValue(number);
						linespaceNumber.updateUI();
						lhSpinnerPanel.updateUI();
					}
				}
			}
		});

		linespaceNumber.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				lineHeightStrategy.setSelectedItem(lineheight);
				setAttribute(Constants.PR_LINE_HEIGHT,
						getSpaceProperty(new Double(((WiseSpinner) e
								.getSource()).getValue()
								+ "")));
				lineSpace.setSelectedItem(numbersof);
			};
		});
		linespaceFixedlength.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				lineHeightStrategy.setSelectedItem(lineheight);
				setAttribute(Constants.PR_LINE_HEIGHT,
						getSpaceProper(((FixedLengthSpinner) e
								.getSource()).getValue()));
				lineSpace.setSelectedItem(fixed);
			};
		});
		lineSpacePanel.add(new JLabel(UiText.PARAGRAPH_INLINE_SPACE_LABEL));
		lineSpacePanel.add(lineSpace);
		lineSpacePanel.add(new JLabel(UiText.PARAGRAPH_INLINE_SPACE_VALUE));

		lineSpacePanel.add(lhSpinnerPanel);

		JPanel lineHeightPanel = new JPanel();
		FlowLayout lineheightlayout = new FlowLayout();
		lineheightlayout.setAlignment(FlowLayout.LEFT);
		lineHeightPanel.setLayout(lineheightlayout);

		FixedLength newLength = (FixedLength) getAttributeValue(Constants.PR_LINE_HEIGHT);
		oneLineHeight.initValue(newLength);
		oneLineHeight.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				lineHeightStrategy.setSelectedItem(lineheight);
				FixedLength chushi = ((FixedLengthSpinner) e
						.getSource()).getValue();
				String current = lineSpace.getSelectedItem().toString();
				if (current.equalsIgnoreCase(one)
						|| current.equalsIgnoreCase(onepointfive)
						|| current.equalsIgnoreCase(two)
						|| current.equalsIgnoreCase(min)
						|| current.equalsIgnoreCase(numbersof))
				{
					setAttribute(Constants.PR_LINE_HEIGHT,
							getSpaceProperty(chushi));

				}
			};
		});

		lineHeightStrategy.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString();
					setAttribute(Constants.PR_LINE_STACKING_STRATEGY,
							getEnumProperty(current).getEnum());
				}
			}
		});

		lineHeightPanel.add(new JLabel(UiText.PARAGRAPH_INLINE_SPACE_LIST[0]));
		lineHeightPanel.add(oneLineHeight);
		lineHeightPanel.add(new JLabel(
				UiText.PARAGRAPH_LINE_STACKING_STRATEGY_LABEL));
		lineHeightPanel.add(lineHeightStrategy);

		linePanel.add(lineHeightPanel);
		linePanel.add(lineSpacePanel);

		linePanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				UiText.PARAGRAPH_HEIGHT_SPACE_SET, TitledBorder.LEFT,
				TitledBorder.TOP));

		JPanel broswer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		broswer.setPreferredSize(new Dimension(450, 140));

		broswer.add(previewPanel);

		broswer.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				UiText.PREVIEW_BORDER_LABEL, TitledBorder.LEFT,
				TitledBorder.TOP));
		this.add(textAlignPanel, BorderLayout.CENTER);
		this.add(marginPanel, BorderLayout.CENTER);
		this.add(spacePanel, BorderLayout.CENTER);
		this.add(linePanel, BorderLayout.CENTER);
		this.add(broswer, BorderLayout.CENTER);
	}

	public SpaceProperty getSpaceProperty(FixedLengthSpinner length, int key)
	{
		LengthProperty opi = length.getValue();
		if(opi.getValue()==0)
		{
			return null;
		}
		SpaceProperty old = null;
		Object object = ParagraphPanel.getAttr().get(key);
		if(object==null||object==Constants.NULLOBJECT)
		{
			object = InitialManager.getInitialValue(key, null);
		}
		if(object instanceof SpaceProperty)
		{
			old = (SpaceProperty) object;
		}
		EnumNumber precedence = old != null ? old.getPrecedence() : null;
		EnumProperty conditionality = old != null ? old.getConditionality()
				: null;
		return new SpaceProperty(opi, precedence, conditionality);
	}

	public SpaceProperty getSpaceProperty(FixedLength length)
	{
		SpaceProperty old = (SpaceProperty) ParagraphPanel.getAttr().get(Constants.PR_LINE_HEIGHT);
		LengthProperty spLength = old != null ? old.getLengthRange()
				.getOptimum(null) : null;
		PercentLength percentLength;
		if (spLength != null && spLength instanceof PercentLength)
		{
			PercentLength perLength = (PercentLength) spLength;
			percentLength = new PercentLength(perLength.value(),
					new LengthBase(length, LengthBase.FONTSIZE));
		} else
		{
			percentLength = new PercentLength(1, new LengthBase(length,
					LengthBase.FONTSIZE));
		}
		EnumNumber precedence = old != null ? old.getPrecedence() : null;
		EnumProperty conditionality = new EnumProperty(-2, "");
		return new SpaceProperty(percentLength, precedence, conditionality);
	}

	public SpaceProperty getSpaceProper(FixedLength length)
	{
		SpaceProperty old = (SpaceProperty) ParagraphPanel.getAttr().get(Constants.PR_LINE_HEIGHT);
		EnumNumber precedence = old != null ? old.getPrecedence() : null;
		EnumProperty conditionality = old != null ? old.getConditionality()
				: null;
		return new SpaceProperty(length, precedence, conditionality);
	}

	public SpaceProperty getSpaceProperty(double lengthint, int key)
	{
		SpaceProperty old = (SpaceProperty) ParagraphPanel.getAttr().get(key);
		FixedLength length = (FixedLength) ParagraphPanel.getAttr().get(Constants.PR_FONT_SIZE);
		PercentLength percentLength = new PercentLength(lengthint,
				new LengthBase(length, LengthBase.FONTSIZE));
		EnumNumber precedence = old != null ? old.getPrecedence() : null;
		EnumProperty conditionality = new EnumProperty(new Double(
				lengthint * 10).intValue(), "");
		return new SpaceProperty(percentLength, precedence, conditionality);
	}

	public SpaceProperty getSpaceProperty(double lengthint)
	{
		Object oldvalue = ParagraphPanel.getAttr().get(Constants.PR_LINE_HEIGHT);
		if(oldvalue==null)
		{
			oldvalue = InitialManager.getInitialValue(Constants.PR_LINE_HEIGHT, null);
		}
		if (oldvalue != null)
		{
			SpaceProperty old = (SpaceProperty) oldvalue;
			PercentLength percentLength = null;
			LengthProperty lp = old.getLengthRange().getOptimum(null);
			if (lp instanceof FixedLength)
			{
				percentLength = new PercentLength(lengthint, new LengthBase(
						(FixedLength) lp, LengthBase.FONTSIZE));
			} else if (lp instanceof PercentLength)
			{
				percentLength = new PercentLength(lengthint,
						((PercentLength) lp).getBaseLength());
			}

			EnumNumber precedence = old != null ? old.getPrecedence() : null;
			EnumProperty conditionality = old != null ? old.getConditionality()
					: null;
			return new SpaceProperty(percentLength, precedence, conditionality);
		} else
		{
			return null;
		}
	}

	public SpaceProperty getSpaceProperty(boolean cd, int key)
	{
		SpaceProperty old = (SpaceProperty)ParagraphPanel.getAttr().get(key);
		LengthProperty opi = old != null ? old.getOptimum(null) : null;
		EnumNumber precedence = old != null ? old.getPrecedence() : null;
		EnumProperty conditionality = cd ? new EnumProperty(
				Constants.EN_RETAIN, "") : new EnumProperty(
				Constants.EN_DISCARD, "");
		return new SpaceProperty(opi, precedence, conditionality);
	}

	public EnumProperty getEnumProperty(String item)
	{
		int value = 0;
		if (item.equalsIgnoreCase(left))
		{
			value = Constants.EN_LEFT;
		} else if (item.equalsIgnoreCase(right))
		{
			value = Constants.EN_RIGHT;
		} else if (item.equalsIgnoreCase(center))
		{
			value = Constants.EN_CENTER;
		} else if (item.equalsIgnoreCase(justify))
		{
			value = Constants.EN_JUSTIFY;
		} else if (item.equalsIgnoreCase(maxheight))
		{
			value = Constants.EN_MAX_HEIGHT;
		} else if (item.equalsIgnoreCase(lineheight))
		{
			value = Constants.EN_LINE_HEIGHT;
		} else if (item.equalsIgnoreCase(fontheight))
		{
			value = Constants.EN_FONT_HEIGHT;
		} else if (item.equalsIgnoreCase(discard))
		{
			value = Constants.EN_DISCARD;
		} else if (item.equalsIgnoreCase(retain))
		{
			value = Constants.EN_RETAIN;
		}

		return new EnumProperty(value, "");
	}

	public String getItem(int value)
	{
		String item = "";
		if (value == Constants.EN_LEFT || value == Constants.EN_START)
		{
			item = left;
		} else if (value == Constants.EN_RIGHT)
		{
			item = right;
		} else if (value == Constants.EN_CENTER)
		{
			item = center;
		} else if (value == Constants.EN_JUSTIFY)
		{
			item = justify;
		} else if (value == Constants.EN_MAX_HEIGHT)
		{
			item = maxheight;
		} else if (value == Constants.EN_LINE_HEIGHT)
		{
			item = lineheight;
		} else if (value == Constants.EN_FONT_HEIGHT)
		{
			item = fontheight;
		} else if (value == Constants.EN_DISCARD)
		{
			item = discard;
		} else if (value == Constants.EN_RETAIN)
		{
			item = retain;
		}

		return item;
	}

	public void setAttribute(int key, Object value)
	{
		ParagraphPanel.getAttr().put(key, value);
	}

	public String getAttribute(int key)
	{
		String result = "";
		Map<Integer, Object> attrMap = ParagraphPanel.getAttr();
		if (attrMap != null && attrMap.containsKey(key))
		{
			if (attrMap.get(key) instanceof Integer)
			{
				result = getItem((Integer) attrMap.get(key));
			} else if (attrMap.get(key) instanceof EnumProperty)
			{
				EnumProperty ep = (EnumProperty) attrMap.get(key);
				if (ep != null)
				{
					result = getItem(ep.getEnum());
				}
			} else if (attrMap.get(key) instanceof SpaceProperty)
			{
				SpaceProperty sp = (SpaceProperty) attrMap.get(key);
				if (sp != null)
				{
					LengthProperty lp = sp.getLengthRange().getOptimum(null);
					if (lp instanceof FixedLength)
					{
						result = fixed;
					} else if (lp instanceof PercentLength)
					{
						double factor = ((PercentLength) lp).value();
						if (factor == 1d)
						{
							result = one;
						} else if (factor == 2d)
						{
							result = two;
						} else if (factor == 0.83333d)
						{
							result = min;
						} else if (factor == 1.5d)
						{
							result = onepointfive;
						} else
						{
							result = numbersof;
						}
					}
				}
			}
		}
		return result;
	}

	public boolean getAttributeBoolean(int key)
	{
		Map<Integer, Object> attrMap = ParagraphPanel.getAttr();
		if (attrMap != null && attrMap.containsKey(key))
		{
			if (attrMap.get(key) instanceof SpaceProperty)
			{
				SpaceProperty sp = (SpaceProperty) attrMap.get(key);
				if (sp != null
						&& sp.getConditionality().getEnum() == Constants.EN_RETAIN)
				{
					return true;
				}
			}
		}
		return false;
	}

	public Object getAttributeValue(int key)
	{
		Map<Integer, Object> attrMap = ParagraphPanel.getAttr();
		if (attrMap != null && attrMap.containsKey(key))
		{
			Object value = attrMap.get(key);
			if (value == Constants.NULLOBJECT)
			{
				value = InitialManager.getInitialValue(key, null);
			}
			if (value instanceof SpaceProperty)
			{
				LengthProperty length = ((SpaceProperty) value)
						.getOptimum(null);

				if (length instanceof FixedLength)
				{
					return ((SpaceProperty) value).getOptimum(null);
				} else if (length instanceof PercentLength)
				{
					PercentLength pelength = (PercentLength) length;
					return ((LengthBase) pelength.getBaseLength())
							.getBaseLength();
				}
			}
			return value;
		}
		return InitialManager.getInitialValue(key, null);
}

	public double getNumber(int key)
	{
		SpaceProperty space = (SpaceProperty)ParagraphPanel.getAttr().get(key);
		if (space != null)
		{
			LengthProperty length = space.getLengthRange().getOptimum(null);
			if (length instanceof PercentLength)
			{
				Double factor = ((PercentLength) space.getLengthRange()
						.getOptimum(null)).value();
				return factor;
			}
		}
		return 1;
	}
}