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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.document.attribute.Fixedarea;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class ContentTreatDialog extends AbstractWisedocDialog
{

	Fixedarea fixedarea;

	WiseSpinner maxnumber = new WiseSpinner();

	WiseSpinner lines = new WiseSpinner();

	WiseCombobox caozuofu = new WiseCombobox();
	{
		caozuofu.addItem(UiText.XIAOYUDENGYU);
		caozuofu.addItem(UiText.DAYU);
	}

	JTextField nullastext = new JTextField();

	JTextField beforeaddtext = new JTextField();

	JCheckBox yes = new JCheckBox(RibbonUIText.HAVE_POSITION);

	JCheckBox no = new JCheckBox(RibbonUIText.NO_POSITION);

	public ContentTreatDialog()
	{
		super();
		setTitle(UiText.CONTENT_TREAT);
		fixedarea = null;
		initialize();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	public ContentTreatDialog(Fixedarea area)
	{
		super();
		setTitle(UiText.CONTENT_TREAT);
		fixedarea = area;
		initialize();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	private void initialize()
	{
		this.setSize(600, 400);
		this.setLayout(null);
		JPanel panel = new JPanel();
		panel.setSize(600, 400);
		panel.setLayout(null);
		JLabel maxnumberlabel = new JLabel(UiText.ONELINE_MAX_NUMBER);
		maxnumberlabel.setBounds(10, 25, 110, 25);

		SpinnerNumberModel maxnumbermodel = new SpinnerNumberModel(20, 1,
				10000, 1);
		maxnumber.setModel(maxnumbermodel);

		SpinnerNumberModel model = new SpinnerNumberModel(5, 1, 10000, 1);
		lines.setModel(model);
		if (fixedarea != null)
		{
			int datamaxnumber = fixedarea.getOnelinemaxnumber();
			maxnumber.initValue(datamaxnumber);
			int caozuofuflg = fixedarea.getCaozuofu();
			caozuofu.setSelectedIndex(caozuofuflg);
			int linesnumber = fixedarea.getLines();
			lines.setValue(linesnumber);

			String nullasvalue = fixedarea.getNullas();
			nullastext.setText(nullasvalue == null ? "" : nullasvalue);
			String beforeaddvalue = fixedarea.getBeforeadd();
			beforeaddtext.setText(beforeaddvalue == null ? "" : beforeaddvalue);
			boolean isviewflg = fixedarea.isIsview();
			if (isviewflg)
			{
				yes.setSelected(false);
				no.setSelected(true);
			} else
			{
				yes.setSelected(true);
				no.setSelected(false);
			}
		} else
		{
			yes.setSelected(true);
			no.setSelected(false);
		}
		maxnumber.setBounds(120, 25, 60, 25);
		JLabel xiaodenglabel = new JLabel(UiText.CONTENT_VIEW_CONDITION);
		xiaodenglabel.setBounds(10, 65, 120, 25);

		caozuofu.setBounds(130, 65, 80, 25);

		lines.setBounds(215, 65, 80, 25);

		JLabel nullaslabel = new JLabel(UiText.CONTENT_NULL_VIEW_AS);

		nullaslabel.setBounds(10, 105, 150, 25);

		nullastext.setBounds(160, 105, 400, 25);

		JLabel beforeaddlabel = new JLabel(UiText.CONTENT_ADD);

		beforeaddlabel.setBounds(10, 145, 150, 25);

		beforeaddtext.setBounds(160, 145, 400, 25);

		JLabel isview = new JLabel(RibbonUIText.CONTENTHIDDEN_IF_OBJECTHIDDEN);

		isview.setBounds(10, 185, 250, 25);
		yes.setBounds(260, 185, 80, 25);
		no.setBounds(350, 185, 80, 25);
		yes.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				no.setSelected(!yes.isSelected());
			}
		});
		no.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				yes.setSelected(!no.isSelected());
			}
		});
		JPanel characterpanel = new JPanel();
		characterpanel.setLayout(null);
		characterpanel.setBounds(10, 225, 570, 100);

		JTextArea character = new JTextArea();
		character.setLineWrap(true);
		character
				.setText(UiText.LEAD_CHARACTER
						+ " "
						+ "$([{·‘“〈《「『【〔〖〝﹙﹛﹝＄（．［｛￡￥\""
						+ "\n"
						+ UiText.REAR_CHARACTER
						+ " "
						+ "%),.:;>?]}¨°·ˇˉ―‖’”…‰′″℃∶、。〃〉》」』】〕〗︶︺︾﹀﹄﹚﹜〞﹞！＂％＇），．：；？］｀｜｝～￠");
		character.setEditable(false);

		character.setBounds(10, 20, 555, 75);
		character.setBackground(this.getBackground());
		characterpanel.add(character);

		characterpanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY),
				UiText.FIRST_AND_LAST_CHARACTER, TitledBorder.LEFT,
				TitledBorder.TOP));
		JButton ok = new JButton(UiText.OK);
		JButton cancle = new JButton(UiText.CANCLE);

		ok.setBounds(360, 330, 80, 25);
		cancle.setBounds(470, 330, 80, 25);
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.OK;
				setFixedarea();
				ContentTreatDialog.this.dispose();
			}
		});
		cancle.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				ContentTreatDialog.this.dispose();
			}
		});
		panel.add(maxnumberlabel);
		panel.add(maxnumber);
		panel.add(xiaodenglabel);
		panel.add(caozuofu);
		panel.add(lines);

		panel.add(nullaslabel);
		panel.add(nullastext);
		panel.add(beforeaddlabel);
		panel.add(beforeaddtext);

		panel.add(isview);
		panel.add(yes);
		panel.add(no);
		panel.add(characterpanel);
		panel.add(ok);
		panel.add(cancle);
		this.add(panel);
	}

	public DialogResult showDialog()
	{
		return result;
	}

	public Fixedarea getFixedarea()
	{
		return fixedarea;
	}

	public void setFixedarea()
	{
		int onelinemaxnumber = (Integer) maxnumber.getValue();
		boolean isview = no.isSelected();
		int caozuofuflg = caozuofu.getSelectedIndex();
		int linesnumber = (Integer) lines.getValue();
		String nullasvalue = nullastext.getText();
		String beforeaddvalue = beforeaddtext.getText();
		fixedarea = new Fixedarea(onelinemaxnumber, linesnumber, caozuofuflg,
				isview, nullasvalue, beforeaddvalue);
	}

	public static void main(String[] args)
	{
		int onelinemaxnumber = 30;
		boolean isview = false;
		int caozuofuflg = 1;
		int linesnumber = 6;
		String nullasvalue = "null";
		String beforeaddvalue = "add";
		Fixedarea fixedarea = new Fixedarea(onelinemaxnumber, linesnumber,
				caozuofuflg, isview, nullasvalue, beforeaddvalue);
		new ContentTreatDialog(fixedarea);
	}
}
