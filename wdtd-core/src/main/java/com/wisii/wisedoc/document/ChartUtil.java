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
 * @ChartUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.chart.renderer.category.StackedBarRenderer3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.Rotation;
import org.jfree.util.TableOrder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.resource.MessageResource;
/**
 * 类功能描述：统计图工具类，该类中定义的是统计
 * 图相关的工具方法
 *
 * 作者：zhangqiang
 * 创建日期：2009-5-19
 */
public final class ChartUtil
{
	/**
	 * 
	 * 根据传入的属性，生成统计图像
	 * 
	 * @param:统计图相关的属性
	 * @return
	 * @exception
	 */
	public static BufferedImage getChartImage(
			final Map<Integer, Object> chartatt)
	{
		final JFreeChart chart = createChart(chartatt);
		if (chart != null)
		{

			final FixedLength widthproperty = (FixedLength) getAttribute(
					chartatt, Constants.PR_WIDTH);
			final FixedLength heightproperty = (FixedLength) getAttribute(
					chartatt, Constants.PR_HEIGHT);
			if (widthproperty != null && heightproperty != null)
			{
				final int width = widthproperty.getValue() / 1000;
				final int height = heightproperty.getValue() / 1000;
				final BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = image.createGraphics();
				// double scale = 72 / DocumentUtil.getUserAgent()
				// .getTargetResolution();
				// g2.scale(scale, scale);
				chart.draw(g2, new Rectangle2D.Double(0, 0, width, height),
						null, null);
				g2.dispose();
				return chart.createBufferedImage(width, height);
			}
		}
		return null;
	}

	/**
	 * 
	 * 根据传入的属性，生成统计图对应的svg文档
	 * 
	 * @param:统计图相关的属性
	 * @return
	 * @exception
	 */
	public static org.w3c.dom.Document getSVGDocument(
			final Map<Integer, Object> chartatt)
	{
		final JFreeChart chart = createChart(chartatt);
		if (chart != null)
		{

			final FixedLength widthproperty = (FixedLength) getAttribute(
					chartatt, Constants.PR_WIDTH);
			final FixedLength heightproperty = (FixedLength) getAttribute(
					chartatt, Constants.PR_HEIGHT);
			if (widthproperty != null && heightproperty != null)
			{
				final int width = widthproperty.getValue() / 1000;
				final int height = heightproperty.getValue() / 1000;
				final DOMImplementation domImpl = GenericDOMImplementation
						.getDOMImplementation();

				// Create an instance of org.w3c.dom.Document.
				String svgNS = "http://www.w3.org/2000/svg";
				final Document document = domImpl.createDocument(svgNS, "svg",
						null);

				// Create an instance of the SVG Generator.
				final SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
				double scale = 72 / DocumentUtil.getUserAgent()
						.getTargetResolution();
				svgGenerator.scale(scale, scale);
				chart.draw(svgGenerator, new Rectangle2D.Double(0, 0, width,
						height), null, null);
				svgGenerator.dispose();
				return document;
			}
		}
		return null;
	}

	private static JFreeChart createChart(final Map<Integer, Object> chartatt)
	{
		JFreeChart chart = null;
		if (chartatt != null)
		{

			final EnumProperty type = (EnumProperty) getAttribute(chartatt,
					Constants.PR_CHART_TYPE);
			if (type != null)
			{
				final int typeint = type.getEnum();
				final InlineContent title = (InlineContent) getAttribute(
						chartatt, Constants.PR_TITLE);
				final Dataset dataset = creatRangeDataset(chartatt, typeint);
				if (dataset != null)
				{
					final InlineContent rangeaxislabel = (InlineContent) getAttribute(
							chartatt, Constants.PR_RANGEAXIS_LABEL);
					final InlineContent seriesaxislabel = (InlineContent) getAttribute(
							chartatt, Constants.PR_DOMAINAXIS_LABEL);
					final int plotori = ((EnumProperty) getAttribute(chartatt,
							Constants.PR_CHART_ORIENTATION)).getEnum();
					PlotOrientation plotprientation = PlotOrientation.VERTICAL;
					if (plotori == Constants.EN_HORIZONTAL)
					{
						plotprientation = PlotOrientation.HORIZONTAL;
					}
					boolean islengendvisable = true;
					EnumProperty islenvisenum = (EnumProperty) getAttribute(
							chartatt, Constants.PR_LENGEND_LABEL_VISABLE);
					if (islenvisenum != null
							&& islenvisenum.getEnum() == Constants.EN_FALSE)
					{
						islengendvisable = false;
					}
					EnumProperty chart3Denable = (EnumProperty) getAttribute(
							chartatt, Constants.PR_3DENABLE);
					boolean c3denable = false;
					if (chart3Denable != null
							&& chart3Denable.getEnum() == Constants.EN_TRUE)
					{
						c3denable = true;
					}
					if (typeint == Constants.EN_BARCHART)
					{
						chart = ChartFactory
								.createBarChart(title.getText(),
										seriesaxislabel.getText(),
										rangeaxislabel.getText(),
										(CategoryDataset) dataset,
										plotprientation, islengendvisable,
										false, false);
						if (c3denable)
						{
							CategoryItemRenderer render3d;
							FixedLength chart3de = (FixedLength) getAttribute(
									chartatt, Constants.PR_3DDEPNESS);
							if (chart3de != null)
							{
								int chart3dint = chart3de.getValue() / 1000;
								render3d = new BarRenderer3D(chart3dint * 0.4d,
										chart3dint * 0.6d);
							} else
							{
								render3d = new BarRenderer3D();
							}
							((CategoryPlot) chart.getPlot())
									.setRenderer(render3d);
						}
					} else if (typeint == Constants.EN_PIECHART)
					{

						if (c3denable)
						{
							chart = ChartFactory.createMultiplePieChart3D(title
									.getText(), (CategoryDataset) dataset,
									TableOrder.BY_ROW, islengendvisable, false,
									false);
							FixedLength chart3de = (FixedLength) getAttribute(
									chartatt, Constants.PR_3DDEPNESS);
							PiePlot3D pieplot3d = (PiePlot3D) ((MultiplePiePlot) chart
									.getPlot()).getPieChart().getPlot();
							if (chart3de != null)
							{
								final FixedLength heightproperty = (FixedLength) getAttribute(
										chartatt, Constants.PR_HEIGHT);
								pieplot3d
										.setDepthFactor((chart3de.getValue() + 0.0f)
												/ heightproperty.getValue());
							}
						} else
						{
							chart = ChartFactory.createMultiplePieChart(title
									.getText(), (CategoryDataset) dataset,
									TableOrder.BY_ROW, islengendvisable, false,
									false);
						}
					} else if (typeint == Constants.EN_LINECHART)
					{
						if (c3denable)
						{
							chart = ChartFactory.createLineChart3D(title
									.getText(), seriesaxislabel.getText(),
									rangeaxislabel.getText(),
									(CategoryDataset) dataset, plotprientation,
									islengendvisable, false, false);
							FixedLength chart3de = (FixedLength) getAttribute(
									chartatt, Constants.PR_3DDEPNESS);
							LineRenderer3D linerender3d = (LineRenderer3D) ((CategoryPlot) chart
									.getPlot()).getRenderer();
							if (chart3de != null)
							{
								final FixedLength heightproperty = (FixedLength) getAttribute(
										chartatt, Constants.PR_HEIGHT);
								int chart3dint = chart3de.getValue() / 1000;
								linerender3d.setXOffset(chart3dint * 0.4d);
								linerender3d.setYOffset(chart3dint * 0.6d);
							}
						} else
						{
							chart = ChartFactory.createLineChart(title
									.getText(), seriesaxislabel.getText(),
									rangeaxislabel.getText(),
									(CategoryDataset) dataset, plotprientation,
									islengendvisable, false, false);
						}
					} else if (typeint == Constants.EN_AREACHART)
					{
						chart = ChartFactory
								.createAreaChart(title.getText(),
										seriesaxislabel.getText(),
										rangeaxislabel.getText(),
										(CategoryDataset) dataset,
										plotprientation, islengendvisable,
										false, false);
					} else if (typeint == Constants.EN_STACKBARCHART)
					{
						if (c3denable)
						{
							chart = ChartFactory.createStackedBarChart3D(title
									.getText(), seriesaxislabel.getText(),
									rangeaxislabel.getText(),
									(CategoryDataset) dataset, plotprientation,
									islengendvisable, false, false);
							FixedLength chart3de = (FixedLength) getAttribute(
									chartatt, Constants.PR_3DDEPNESS);
							if (chart3de != null)
							{
								final FixedLength heightproperty = (FixedLength) getAttribute(
										chartatt, Constants.PR_HEIGHT);
								int chart3dint = chart3de.getValue() / 1000;
								StackedBarRenderer3D stackrender3d = new StackedBarRenderer3D(
										chart3dint * 0.4d, chart3dint * 0.6d);
								((CategoryPlot) chart.getPlot())
										.setRenderer(stackrender3d);
							}
						} else
						{
							chart = ChartFactory.createStackedBarChart(title
									.getText(), seriesaxislabel.getText(),
									rangeaxislabel.getText(),
									(CategoryDataset) dataset, plotprientation,
									islengendvisable, false, false);
						}
					}
					if (chart == null)
					{
						return null;
					}
					final Plot plot = (Plot) chart.getPlot();
					FixedLength topmargin = (FixedLength) chartatt
							.get(Constants.PR_MARGIN_TOP);
					FixedLength bottommargin = (FixedLength) chartatt
							.get(Constants.PR_MARGIN_BOTTOM);
					FixedLength leftmargin = (FixedLength) chartatt
							.get(Constants.PR_MARGIN_LEFT);
					FixedLength rightmargin = (FixedLength) chartatt
							.get(Constants.PR_MARGIN_RIGHT);
					if (topmargin != null || bottommargin != null
							|| leftmargin != null || rightmargin != null)
					{
						double topm = 0;
						if (topmargin != null)
						{
							topm = topmargin.getValue() / 1000 + 4;
						}
						double bottomm = 0;
						if (bottommargin != null)
						{
							bottomm = bottommargin.getValue() / 1000 + 4;
						}
						double leftm = 0;
						if (leftmargin != null)
						{
							leftm = leftmargin.getValue() / 1000 + 8;
						}
						double rightm = 0;
						if (rightmargin != null)
						{
							rightm = rightmargin.getValue() / 1000 + 8;
						}
						RectangleInsets insets = new RectangleInsets(topm,
								leftm, bottomm, rightm);
						plot.setInsets(insets);
					}

					Color bgcolor = (Color) getAttribute(chartatt,
							Constants.PR_BACKGROUND_COLOR);
					if (bgcolor != null)
					{
						plot.setBackgroundPaint(bgcolor);
						chart.setBackgroundPaint(bgcolor);
					} else
					{
						plot.setBackgroundPaint(null);
						chart.setBackgroundPaint(null);
					}
					TextTitle texttitle = chart.getTitle();
					Font titlefont = getFont(chartatt,
							Constants.PR_TITLE_FONTFAMILY,
							Constants.PR_TITLE_FONTSTYLE,
							Constants.PR_TITLE_FONTSIZE);
					if (titlefont != null)
					{
						texttitle.setFont(titlefont);
					}
					Color titlecolor = (Color) getAttribute(chartatt,
							Constants.PR_TITLE_COLOR);
					if (titlecolor != null)
					{
						texttitle.setPaint(titlecolor);
					}
					EnumProperty titlealignment = (EnumProperty) getAttribute(
							chartatt, Constants.PR_TITLE_ALIGNMENT);
					if (titlealignment != null)
					{
						if (titlealignment.getEnum() == Constants.EN_CENTER)
						{
							texttitle
									.setHorizontalAlignment(HorizontalAlignment.CENTER);
						} else if (titlealignment.getEnum() == Constants.EN_LEFT)
						{
							texttitle
									.setHorizontalAlignment(HorizontalAlignment.LEFT);
						} else if (titlealignment.getEnum() == Constants.EN_RIGHT)
						{
							texttitle
									.setHorizontalAlignment(HorizontalAlignment.RIGHT);
						}
					}
					String backgroundimage = (String) getAttribute(chartatt,
							Constants.PR_BACKGROUND_IMAGE);
					if (backgroundimage != null)
					{
						Image bgimage = Toolkit.getDefaultToolkit()
								.createImage(
										DocumentUtil.getUserAgent()
												.getImageFactory().getURL(
														backgroundimage));
						if (bgimage != null)
						{
							plot.setBackgroundImage(bgimage);
						}
					}
					Double backgroundalaph = (Double) getAttribute(
							chartatt,
							Constants.PR_BACKGROUNDIMAGE_ALAPH);
					if (backgroundalaph != null)
					{
						plot.setBackgroundImageAlpha(backgroundalaph.floatValue());
					}
					Double foregroundalaph = (Double) getAttribute(chartatt,
							Constants.PR_FOREGROUND_ALAPH);
					if (foregroundalaph != null)
					{
						plot.setForegroundAlpha(foregroundalaph.floatValue());
					}
					LegendTitle legendtitle = (LegendTitle) chart
							.getSubtitle(0);
					Font lengendfont = getFont(chartatt,
							Constants.PR_LENGEND_LABEL_FONTFAMILY,
							Constants.PR_LENGEND_LABEL_FONTSTYLE,
							Constants.PR_LENGEND_LABEL_FONTSIZE);
					Color lengendcolor = (Color) getAttribute(chartatt,
							Constants.PR_LENGEND_LABEL_COLOR);
					if (lengendfont != null)
					{
						legendtitle.setItemFont(lengendfont);
					}
					if (lengendcolor != null)
					{
						legendtitle.setItemPaint(lengendcolor);
					}
					EnumProperty lengendlabelloctation = (EnumProperty) getAttribute(
							chartatt, Constants.PR_LENGEND_LABEL_LOCATION);
					boolean islengendhorizontal = true;
					if (lengendlabelloctation != null)
					{
						int lellenum = lengendlabelloctation.getEnum();
						RectangleEdge edge = null;
						if (lellenum == Constants.EN_TOP)
						{
							edge = RectangleEdge.TOP;
						} else if (lellenum == Constants.EN_BOTTOM)
						{
							edge = RectangleEdge.BOTTOM;
						} else if (lellenum == Constants.EN_LEFT)
						{
							edge = RectangleEdge.LEFT;
							islengendhorizontal = false;
						} else if (lellenum == Constants.EN_RIGHT)
						{
							edge = RectangleEdge.RIGHT;
							islengendhorizontal = false;
						}
						if (edge != null)
						{
							legendtitle.setPosition(edge);

						}
					}
					EnumProperty lengendalignment = (EnumProperty) getAttribute(
							chartatt, Constants.PR_LENGEND_LABLE_ALIGNMENT);
					if (lengendalignment != null)
					{
						int lenaligenum = lengendalignment.getEnum();
						HorizontalAlignment ha = null;
						VerticalAlignment va = null;
						if (lenaligenum == Constants.EN_LEFT)
						{
							ha = HorizontalAlignment.LEFT;
						} else if (lenaligenum == Constants.EN_CENTER
								&& islengendhorizontal)
						{
							ha = HorizontalAlignment.CENTER;
						} else if (lenaligenum == Constants.EN_RIGHT)
						{
							ha = HorizontalAlignment.RIGHT;
						} else if (lenaligenum == Constants.EN_BOTTOM)
						{
							va = VerticalAlignment.BOTTOM;
						} else if (lenaligenum == Constants.EN_CENTER
								&& !islengendhorizontal)
						{
							va = VerticalAlignment.CENTER;
						} else if (lenaligenum == Constants.EN_TOP)
						{
							va = VerticalAlignment.TOP;
						}
						if (ha != null)
						{
							legendtitle.setHorizontalAlignment(ha);
						} else if (va != null)
						{
							legendtitle.setVerticalAlignment(va);
						}
					}
					if (typeint == Constants.EN_BARCHART
							|| typeint == Constants.EN_LINECHART
							|| typeint == Constants.EN_AREACHART
							|| typeint == Constants.EN_STACKBARCHART)
					{
						initCategoryChartAttribute(chartatt, chart,
								(CategoryPlot) plot);
					} else if (typeint == Constants.EN_PIECHART)
					{
						initPieChartAttribute(chartatt, chart,
								(MultiplePiePlot) plot);
					}
				}
				// PR_ZEROVALUE_VISABLE，PR_NULLVALUE_VISABLE柱状图不支持
			}
		}
		return chart;

	}

	private static void initPieChartAttribute(Map<Integer, Object> chartatt,
			JFreeChart chart, MultiplePiePlot plot)
	{
		final PiePlot pieplot = (PiePlot) plot.getPieChart().getPlot();
		final EnumProperty nullvalueenum = (EnumProperty) getAttribute(
				chartatt, Constants.PR_NULLVALUE_VISABLE);
		if (nullvalueenum != null
				&& nullvalueenum.getEnum() == Constants.EN_FALSE)
		{
			pieplot.setIgnoreNullValues(true);
		}
		final EnumProperty zerovalueenum = (EnumProperty) getAttribute(
				chartatt, Constants.PR_ZEROVALUE_VISABLE);
		if (zerovalueenum != null
				&& zerovalueenum.getEnum() == Constants.EN_FALSE)
		{
			pieplot.setIgnoreZeroValues(true);
		}
		final Font categortfont = getFont(chartatt,
				Constants.PR_SERIES_LABEL_FONTFAMILY,
				Constants.PR_SERIES_LABEL_FONTSTYLE,
				Constants.PR_SERIES_LABEL_FONTSIZE);
		TextTitle title = plot.getPieChart().getTitle();
		if (categortfont != null)
		{
			title.setFont(categortfont);
		}
		final Color catagortcolor = (Color) getAttribute(chartatt,
				Constants.PR_SERIES_LABEL_COLOR);
		if (catagortcolor != null)
		{
			title.setPaint(catagortcolor);
		}
		List<Color> valuecolors = (List<Color>) getAttribute(chartatt,
				Constants.PR_VALUE_COLOR);
		Integer valuecount = (Integer) getAttribute(chartatt,
				Constants.PR_VALUE_COUNT);
		if (valuecolors != null)
		{
			for (int i = 0; i < valuecount; i++)
			{
				Color valuecolor = null;
				if (i < valuecolors.size())
				{
					valuecolor = valuecolors.get(i);
				}
				if (valuecolor != null)
				{
					pieplot.setSectionPaint(plot.getDataset().getColumnKey(i),
							valuecolor);
				}
			}
		}
		boolean itemlabelvisable = false;
		EnumProperty itemlabelvenum = (EnumProperty) getAttribute(chartatt,
				Constants.PR_PIECHARTLENGENDLABEL_VISABLE);
		String sectionlabelstring = "";
		if (itemlabelvenum != null
				&& itemlabelvenum.getEnum() == Constants.EN_TRUE)
		{
			sectionlabelstring = sectionlabelstring + "{0}";
		}
		EnumProperty factvaluevisable = (EnumProperty) getAttribute(chartatt,
				Constants.PR_PIE_FACT_VALUE_VISABLE);
		if (factvaluevisable != null
				&& factvaluevisable.getEnum() == Constants.EN_TRUE)
		{
			if (sectionlabelstring.equals(""))
			{
				sectionlabelstring = sectionlabelstring + "{1}";
			} else
			{
				sectionlabelstring = sectionlabelstring + " = {1}";
			}
		}

		EnumProperty percentvaluevisable = (EnumProperty) getAttribute(
				chartatt, Constants.PR_PERCENTVALUE_VISABLE);
		if (percentvaluevisable != null
				&& percentvaluevisable.getEnum() == Constants.EN_TRUE)
		{
			if (sectionlabelstring.equals(""))
			{
				sectionlabelstring = sectionlabelstring + "{2}";
			} else
			{
				sectionlabelstring = sectionlabelstring + " ({2})";
			}
		}

		if (sectionlabelstring.equals(""))
		{
			pieplot.setLabelGenerator(null);
		} else
		{
			pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
					sectionlabelstring));
		}
		Font valuefont = getFont(chartatt, Constants.PR_CHART_VALUE_FONTFAMILY,
				Constants.PR_CHART_VALUE_FONTSTYLE,
				Constants.PR_CHART_VALUE_FONTSIZE);
		if (valuefont != null)
		{
			pieplot.setLabelFont(valuefont);
		}
		Integer startangle = (Integer) getAttribute(chartatt,
				Constants.PR_PIECHART_STARTANGLE);
		if (startangle != null)
		{
			pieplot.setStartAngle(startangle.doubleValue());
		}
		EnumProperty direction = (EnumProperty) getAttribute(chartatt,
				Constants.PR_PIECHART_DIRECTION);
		if (direction != null)
		{
			int directionenum = direction.getEnum();
			if (directionenum == Constants.EN_INVERT)
			{
				pieplot.setDirection(Rotation.ANTICLOCKWISE);
			}
		}
	}

	private static void initCategoryChartAttribute(
			Map<Integer, Object> chartatt, JFreeChart chart, CategoryPlot plot)
	{
		EnumProperty chart3Denable = (EnumProperty) getAttribute(chartatt,
				Constants.PR_3DENABLE);

		final CategoryItemRenderer renderer = plot.getRenderer();
		List<Color> valuecolors = (List<Color>) getAttribute(chartatt,
				Constants.PR_VALUE_COLOR);
		Integer valuecount = (Integer) getAttribute(chartatt,
				Constants.PR_VALUE_COUNT);
		if (valuecolors != null)
		{
			for (int i = 0; i < valuecount; i++)
			{
				Color valuecolor = null;
				if (i < valuecolors.size())
				{
					valuecolor = valuecolors.get(i);
				}
				if (valuecolor != null)
				{
					renderer.setSeriesPaint(i, valuecolor);
				}
			}
		}
		EnumProperty itemvaluevisable = (EnumProperty) getAttribute(chartatt,
				Constants.PR_VALUE_LABLEVISABLE);
		Integer precision = (Integer) getAttribute(chartatt,
				Constants.PR_RANGEAXIS_PRECISION);
		NumberFormat valueformat = null;
		if (precision != null && precision >= 0)
		{
			valueformat = new DecimalFormat();
			valueformat.setMaximumFractionDigits(precision);
		}
		if (itemvaluevisable != null
				&& itemvaluevisable.getEnum() == Constants.EN_TRUE)
		{
			renderer.setBaseItemLabelsVisible(true);
			StandardCategoryItemLabelGenerator lablegenerator = null;
			if (valueformat != null)
			{
				lablegenerator = new StandardCategoryItemLabelGenerator(
						StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING,
						valueformat);
			} else
			{
				lablegenerator = new StandardCategoryItemLabelGenerator();
			}
			renderer.setBaseItemLabelGenerator(lablegenerator);

			FixedLength itempositionlen = (FixedLength) getAttribute(chartatt,
					Constants.PR_CHART_VALUE_OFFSET);
			if (itempositionlen != null)
			{
				((AbstractRenderer) renderer)
						.setItemLabelAnchorOffset(itempositionlen.getValue() / 1000);
			}
			Font valuefont = getFont(chartatt,
					Constants.PR_CHART_VALUE_FONTFAMILY,
					Constants.PR_CHART_VALUE_FONTSTYLE,
					Constants.PR_CHART_VALUE_FONTSIZE);
			if (valuefont != null)
			{
				renderer.setBaseItemLabelFont(valuefont);
			}
			Color valuecolor = (Color) getAttribute(chartatt,
					Constants.PR_CHART_VALUE_COLOR);
			if (valuecolor != null)
			{
				renderer.setBaseItemLabelPaint(valuecolor);
			}
		}
		EnumProperty domianlineviable = (EnumProperty) getAttribute(chartatt,
				Constants.PR_DOMIANLINE_VISABLE);
		if (domianlineviable != null
				&& domianlineviable.getEnum() == Constants.EN_TRUE)
		{
			plot.setDomainGridlinesVisible(true);
		} else
		{
			plot.setDomainGridlinesVisible(false);
		}
		EnumProperty rangelineviable = (EnumProperty) getAttribute(chartatt,
				Constants.PR_RANGELINE_VISABLE);
		if (rangelineviable != null
				&& rangelineviable.getEnum() == Constants.EN_TRUE)
		{
			plot.setRangeGridlinesVisible(true);
		} else
		{
			plot.setRangeGridlinesVisible(false);
		}
		EnumProperty zerolineviable = (EnumProperty) getAttribute(chartatt,
				Constants.PR_ZERORANGELINE_VISABLE);
		if (zerolineviable != null
				&& zerolineviable.getEnum() == Constants.EN_TRUE)
		{
			plot.setRangeZeroBaselineVisible(true);
		} else
		{
			plot.setRangeZeroBaselineVisible(false);
		}
		final CategoryAxis domainAxis = plot.getDomainAxis();
		final Font categortfont = getFont(chartatt,
				Constants.PR_SERIES_LABEL_FONTFAMILY,
				Constants.PR_SERIES_LABEL_FONTSTYLE,
				Constants.PR_SERIES_LABEL_FONTSIZE);
		if (categortfont != null)
		{
			domainAxis.setTickLabelFont(categortfont);
		}
		final Color catagortcolor = (Color) getAttribute(chartatt,
				Constants.PR_SERIES_LABEL_COLOR);
		if (catagortcolor != null)
		{
			domainAxis.setTickLabelPaint(catagortcolor);
		}
		final Integer catagortdegree = (Integer) getAttribute(chartatt,
				Constants.PR_SERIES_LABEL_ORIENTATION);
		if (catagortdegree != null)
		{
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions
					.createUpRotationLabelPositions(catagortdegree * Math.PI
							/ 180));
		}
		final Font domainaxisfont = getFont(chartatt,
				Constants.PR_DOMAINAXIS_LABEL_FONTFAMILY,
				Constants.PR_DOMAINAXIS_LABEL_FONTSTYLE,
				Constants.PR_DOMAINAXIS_LABEL_FONTSIZE);
		if (domainaxisfont != null)
		{
			domainAxis.setLabelFont(domainaxisfont);
		}
		final Color domainaxiscolor = (Color) getAttribute(chartatt,
				Constants.PR_DOMAINAXIS_LABEL_COLOR);
		if (domainaxiscolor != null)
		{
			domainAxis.setLabelPaint(domainaxiscolor);
		}
		// PR_DOMAINAXIS_LABEL_ALIGNMENT 属性暂时没处理
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		final Font rangefont = getFont(chartatt,
				Constants.PR_VALUE_LABEL_FONTFAMILY,
				Constants.PR_VALUE_LABEL_FONTSTYLE,
				Constants.PR_VALUE_LABEL_FONTSIZE);
		if (rangefont != null)
		{
			rangeAxis.setTickLabelFont(rangefont);
		}
		final Color rangecolor = (Color) getAttribute(chartatt,
				Constants.PR_VALUE_LABEL_COLOR);
		if (rangecolor != null)
		{
			rangeAxis.setTickLabelPaint(rangecolor);
		}
		final Font rangeaxisfont = getFont(chartatt,
				Constants.PR_RANGEAXIS_LABEL_FONTFAMILY,
				Constants.PR_RANGEAXIS_LABEL_FONTSTYLE,
				Constants.PR_RANGEAXIS_LABEL_FONTSIZE);
		if (rangeaxisfont != null)
		{
			rangeAxis.setLabelFont(rangeaxisfont);
		}
		final Color rangeaxiscolor = (Color) getAttribute(chartatt,
				Constants.PR_RANGEAXIS_LABEL_COLOR);
		if (rangeaxiscolor != null)
		{
			rangeAxis.setLabelPaint(rangeaxiscolor);
		}
		Number step = (Number) getAttribute(chartatt,
				Constants.PR_RANGEAXIS_UNITINCREMENT);
		if (step != null&&step.doubleValue() > 0)
		{
			rangeAxis.setTickUnit(new NumberTickUnit(step.doubleValue()));
		}
		// PR_RANGEAXIS_LABEL_ALIGNMENT 属性暂时没处理

		Number minrange = (Number) getAttribute(chartatt,
				Constants.PR_RANGEAXIS_MINUNIT);
		Number maxrange = (Number) getAttribute(chartatt,
				Constants.PR_RANGEAXIS_MAXUNIT);
		rangeAxis.getRange();
		if (minrange != null || maxrange != null)
		{
			if (minrange == null)
			{
				minrange = rangeAxis.getRange().getLowerBound();
			}
			if (maxrange == null)
			{
				maxrange = rangeAxis.getRange().getUpperBound();
			}
			if (minrange.doubleValue() < maxrange.doubleValue())
			{
				rangeAxis.setRange(minrange.doubleValue(), maxrange
						.doubleValue());
			}
		}
	}

	private static Font getFont(final Map<Integer, Object> chartatt,
			final int familykey, final int stylekey, final int sizekey)
	{
		String family = (String) getAttribute(chartatt, familykey);
		if (family == null)
		{
			family = (String) InitialManager.getInitialValue(
					Constants.PR_FONT_FAMILY, null);
		}
		Integer style = (Integer) getAttribute(chartatt, stylekey);
		if (style == null)
		{
			style = (Integer) InitialManager.getInitialValue(
					Constants.PR_FONT_STYLE, null);
		}
		if (style == Constants.EN_ITALIC)
		{
			style = Font.ITALIC;
		} else if (style == Constants.EN_800)
		{
			style = Font.BOLD;
		} else
		{
			style = -1;
		}
		FixedLength size = (FixedLength) getAttribute(chartatt, sizekey);
		if (size == null)
		{
			size = (FixedLength) InitialManager.getInitialValue(
					Constants.PR_FONT_SIZE, null);
		}
		if (family != null && style != null && size != null)
		{
			return new Font(family, style, size.getValue() / 1000);
		}
		return null;
	}

	private static Object getAttribute(final Map<Integer, Object> chartatt,
			int key)
	{
		Object attribute = null;
		if (chartatt != null)
		{
			attribute = chartatt.get(key);
		}
		if (attribute == null)
		{
			attribute = InitialManager.getInitialValue(key, null);
		}
		return attribute;
	}

	/**
	 * 
	 * 根据属性设置，生成随机的数据，在设计界面，由于取不到实际的数据，因此需要生成一些随机的数据
	 * 
	 * @param
	 * @return Dataset: 统计图所需要的数据
	 * @exception
	 */
	private static Dataset creatRangeDataset(Map<Integer, Object> chartatt,
			int type)
	{
		if (chartatt == null)
		{
			chartatt = new HashMap<Integer, Object>();
		}
		// switch (type)
		// {
		// case Constants.EN_BARCHART:
		// {
		Integer seriescount = (Integer) getAttribute(chartatt,
				Constants.PR_SERIES_COUNT);
		Integer vaulescount = (Integer) getAttribute(chartatt,
				Constants.PR_VALUE_COUNT);
		final String[] serieslabelstrings = getSeriesLabelString(seriescount,
				(List<InlineContent>) chartatt.get(Constants.PR_SERIES_LABEL));
		final String[] valueslabelstrings = getValueLabelString(vaulescount,
				(List<InlineContent>) chartatt.get(Constants.PR_VALUE_LABEL));
		final ChartDataList chartdata = (ChartDataList) chartatt
				.get(Constants.PR_SERIES_VALUE);
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Number maxvalue = (Number) getAttribute(chartatt,
				Constants.PR_RANGEAXIS_MAXUNIT);
		for (int i = 0; i < seriescount; i++)
		{
			for (int j = 0; j < vaulescount; j++)
			{
				dataset.addValue(getSeriesValue(chartdata, i, j, maxvalue),
						serieslabelstrings[i], valueslabelstrings[j]);
			}
		}
		return dataset;
		// }
		// default:
		// break;
		// }
		// return null;
	}

	private static Number getSeriesValue(final ChartDataList chartdatas,
			final int row, final int index, Number maxvalue)
	{
		if (maxvalue == null)
		{
			maxvalue = 10;
		}
		if (chartdatas != null)
		{
			final NumberContent chartdata = chartdatas.getNumberContent(row,
					index);
			if (chartdata == null || chartdata.isBindContent())
			{
				return Math.random() * maxvalue.doubleValue();
			} else
			{
				return chartdata.getNumber();
			}
		} else
		{
			return Math.random() * maxvalue.doubleValue();
		}
	}

	private static String[] getValueLabelString(final Integer vaulescount,
			final List<InlineContent> valuelabellist)
	{
		final String[] valuelabels = new String[vaulescount];
		for (int i = 0; i < vaulescount; i++)
		{
			if (valuelabellist != null
					&& i<valuelabellist.size()&&valuelabellist.get(i) instanceof InlineContent)
			{
				valuelabels[i] = valuelabellist.get(i).toString();
			} else
			{
				valuelabels[i] = "种类"
						+ NumberFormat.getInstance(MessageResource.getLocale())
								.getIntegerInstance().format(i + 1);
			}
		}
		return valuelabels;
	}

	private static String[] getSeriesLabelString(final Integer seriescount,
			final List<InlineContent> serieslabeles)
	{
		final String[] serieslabels = new String[seriescount];
		for (int i = 0; i < seriescount; i++)
		{
			if (serieslabeles != null
					&&i< serieslabeles.size()&&serieslabeles.get(i) instanceof InlineContent)
			{

				serieslabels[i] = serieslabeles.get(i).toString();
			} else
			{
				serieslabels[i] = NumberFormat.getInstance(
						MessageResource.getLocale()).getIntegerInstance()
						.format(i + 1);
			}
		}
		return serieslabels;
	}
}
