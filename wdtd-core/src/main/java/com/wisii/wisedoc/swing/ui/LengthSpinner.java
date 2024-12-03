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

package com.wisii.wisedoc.swing.ui;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.ChangeEvent;
import javax.swing.text.DefaultFormatter;

import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;

@SuppressWarnings("serial")
public class LengthSpinner extends JSpinner
{

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public LengthSpinner()
	{
		this(new SpinnerLengthModel());
	}

	public LengthSpinner(SpinnerLengthModel model)
	{
		super(model);
		getModel().setSpinner(this);
		setPreferredSize(new Dimension(80, 22));
	}

	public LengthSpinner(ActionType type)
	{
		this(new SpinnerLengthModel());
	}

	public LengthSpinner(SpinnerLengthModel model, ActionType type)
	{
		super(model);
		getModel().setSpinner(this);
		getModel().setInitLength(type);
		setPreferredSize(new Dimension(80, 22));
	}

	protected JComponent createEditor(SpinnerModel model)
	{
		return new LengthEditor(this);
	}

	public SpinnerLengthModel getModel()
	{
		return (SpinnerLengthModel) super.getModel();
	}

	public void setModel(SpinnerModel model)
	{
		if (model instanceof SpinnerLengthModel)
		{
			super.setModel(model);
			getModel().setSpinner(this);
		}
	}

	public LengthProperty getValue()
	{
		return getModel().getValue();
	}

	public void initValue(Object length, ActionType type)
	{
		getModel().initValue(length, type);
	}

	public void initValue(Object length)
	{
		getModel().initValue(length, null);
	}

	public static class LengthEditor extends DefaultEditor implements
			FocusListener
	{

		public LengthEditor(LengthSpinner spinner)
		{
			super(spinner);
			JFormattedTextField ftf = getTextField();
			AbstractFormatter formatter = ftf.getFormatter();
			if (formatter instanceof DefaultFormatter)
			{
				((DefaultFormatter) formatter).setOverwriteMode(false);
			}
			ftf.setEditable(true);
			ftf.addFocusListener(this);
		}

		/**
		 * Called by the <code>JFormattedTextField</code>
		 * <code>PropertyChangeListener</code>. When the <code>"value"</code>
		 * property changes, which implies that the user has typed a new number,
		 * we set the value of the spinners model.
		 * <p>
		 * This class ignores <code>PropertyChangeEvents</code> whose source is
		 * not the <code>JFormattedTextField</code>, so subclasses may safely
		 * make <code>this</code> <code>DefaultEditor</code> a
		 * <code>PropertyChangeListener</code> on other objects.
		 * 
		 * @param e
		 *            the <code>PropertyChangeEvent</code> whose source is the
		 *            <code>JFormattedTextField</code> created by this class.
		 * @see #getTextField
		 */
		public void propertyChange(PropertyChangeEvent e)
		{
			JSpinner spinner = getSpinner();

			if (spinner == null)
			{
				// Indicates we aren't installed anywhere.
				return;
			}

			Object source = e.getSource();
			String name = e.getPropertyName();
			if ((source instanceof JFormattedTextField) && "value".equals(name))
			{
				Object lastValue = spinner.getValue();

				// Try to set the new value
				try
				{
					String text = getTextField().getText();
					LengthProperty length = createLengthProperty(text, spinner
							.getValue());
					spinner.setValue(length);
					getTextField().setText("" + spinner.getValue());

				} catch (IllegalArgumentException iae)
				{
					// SpinnerModel didn't like new value, reset
					try
					{
						((JFormattedTextField) source).setValue(lastValue);
					} catch (IllegalArgumentException iae2)
					{
						// Still bogus, nothing else we can do, the
						// SpinnerModel and JFormattedTextField are now out
						// of sync.
					}
				}
			}
		}

		private LengthProperty createLengthProperty(String text, Object oldvale)
		{
			if (text != null)
			{
				text = text.trim();
				if (!text.equals(""))
				{
					String lengths = "";
					String units = null;
					int size = text.length();
					int index = -1;
					for (int i = 0; i < size; i++)
					{
						// 取出其中的长度数字值
						char c = text.charAt(i);
						// 找到第一个不是数字的字符
						if ((c < '0' || c > '9') && c != '.'&&(c!='-'||i!=0))
						{
							index = i;
							break;
						}
						lengths = lengths + c;
					}
					try
					{
						units = text.substring(index).trim();
						units = ConfigureUtil.getUnitofText(units);
					} catch (IndexOutOfBoundsException e)
					{
					}
					if (!lengths.equals(""))
					{
						try
						{
							double len = Double.parseDouble(lengths);
							if (units.equalsIgnoreCase("em"))
							{
								return new PercentLength(len, new LengthBase(
										((SpinnerLengthModel) getSpinner()
												.getModel()).getLength(),
										LengthBase.FONTSIZE));
							} else
							{
								int precion = -1;
								if (oldvale instanceof FixedLength)
								{
									precion = ((FixedLength) oldvale)
											.getPrecision();
								}

								return new FixedLength(len, units, precion);
							}

						} catch (Exception e)
						{
							LogUtil.debug(e);
						}
					}
				}
			}
			return null;
		}

		/**
		 * This method is called when the spinner's model's state changes. It
		 * sets the <code>value</code> of the text field to the current value of
		 * the spinners model.
		 * 
		 * @param e
		 *            the <code>ChangeEvent</code> whose source is the
		 *            <code>JSpinner</code> whose model has changed.
		 * @see #getTextField
		 * @see JSpinner#getValue
		 */
		public void stateChanged(ChangeEvent e)
		{
			LengthSpinner spinner = (LengthSpinner) (e.getSource());
			getTextField().setText("" + spinner.getValue());
		}

		@Override
		public void focusGained(FocusEvent e)
		{
		}

		public void focusLost(FocusEvent e)
		{
			JSpinner spinner = getSpinner();
			String text = getTextField().getText();
			LengthProperty length = createLengthProperty(text, spinner
					.getValue());
			spinner.setValue(length);
			getTextField().setText("" + spinner.getValue());
		}
	}

	public synchronized void addActionListener(ActionListener l)
	{
		getModel().addActionListener(l);
	}

	public synchronized void removeActionListener(ActionListener l)
	{
		getModel().removeActionListener(l);
	}
}
