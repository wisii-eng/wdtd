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
 * @SVGLocationConvert.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;
import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.UIManager;

import com.wisii.wisedoc.area.BlockViewport;
import com.wisii.wisedoc.area.inline.Viewport;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.view.cursor.CursorManager;
import com.wisii.wisedoc.view.cursor.CursorManager.CursorType;

/**
 * 类功能描述：主要是对把当当前坐标系的位置，转换为SVG图坐标系位置， 根据SVG图形，计算位置是否在SVG图形内，边框上。
 * 
 * 作者：李晓光 创建日期：2009-3-2
 */
public abstract class SVGLocationConvert {

	/** 定义方向枚举类型 */
	public static enum Orientations {
		NORMAL(-1), NORTH(1), SOUTH(5), WEST(7), EAST(3), WEST_NORTH(0), WEST_SOUTH(6), EAST_NORTH(2), EAST_SOUTH(4),
		/* 表示在图形上方 */
		ON_FIGURE(-1),
		/* 表示在图形上方 ，且可以移动 */
		ON_MOVE(-1),
		/* 选择单元格 */
		SELECT_CELL(-1),
		/* 选择Block-Container */
		SELECT_CONTAINER(-1),
		/* 主要用于描述线的可拖动点。 */
		/*SELECT_LINE,*/
		/* 线的第一个点 */
		LINE_P1(-1),
		/* 线的第二个点 */
		LINE_P2(-1),
		/* 十字交叉鼠标样式，在可拖动点上，押下鼠标左键时，更新为当前鼠标样式。 */
		RESIZE(-1),
		ON_QIANZHANG(-1);
		private int type = -1;
		private Orientations(int type){
			this.type = type;
		}
		public int getType(){
			return this.type;
		}		
	}

	/**
	 * 根据指定的矩形区域，绘制可拖动点。
	 * 
	 * @param x
	 *            指定矩形位置的横坐标。
	 * @param y
	 *            指定矩形位置的纵坐标。
	 * @param width
	 *            指定矩形的宽度。
	 * @param height
	 *            指定矩形的高度。
	 * @return Shape 返回计算后的八个拖动点组成的图形。
	 */
	public static Shape createSelectedShape(int x, int y, int width, int height) {
		return createSelectedShape(new Rectangle2D.Float(x, y, width, height));
	}

	/**
	 * 根据指定的线起始点坐标，绘制可拖动点。
	 * 
	 * @param x1
	 *            指定线起点的横坐标
	 * @param y1
	 *            指定线起点的纵坐标。
	 * @param x2
	 *            指定线终点的横坐标。
	 * @param y2
	 *            指定线终点的纵坐标。
	 * @return {@link Shape} 返回计算后的两个可拖动点组成的图形。
	 */
	public static Shape createSelectedLine(int x1, int y1, int x2, int y2) {
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		return createSelectedShape(line);
	}

	/**
	 * 根据指定的svg图形，计算可拖动点组成的图形。
	 * 
	 * @param svg
	 *            指定svg图形。
	 * @param offset
	 *            指定图形的偏移量0-横坐标：1-纵坐标
	 * @return {@link Shape} 返回由可拖动点组成的图形。
	 */
	public static Shape createSelectedShape(AbstractSVG svg, double... offset) {
		Shape shape = createSVGHightLightShape(svg);
		shape = createSelectedShape(shape, offset);
		return shape;
	}

	/**
	 * 根据指定的矩形区域，绘制可拖动点。
	 * 
	 * @param r
	 *            指定要计算拖动点的图形。【线、矩形、圆、椭圆】
	 * @param offset
	 *            指定图形的偏移量0-横坐标：1-纵坐标
	 * @return Shape 返回计算后的八个拖动点组成的图形。
	 */
	public static Shape createSelectedShape(Shape shape, double... offset) {
		GeneralPath path = new GeneralPath();
		if (isNull(shape))
			return path;

		float length = DIAMETER, half = RADIUS;
		double[] off = { 0, 0 };
		if (!isNull(offset)) {
			int count = 0;
			for (Double d : offset) {
				off[count++] = d;
				if (count > 1)
					break;
			}
		}

		/* 图形是直线，只需要起始点的可拖动点即可。 */
		Ellipse2D.Double ellipse = null;
		if (shape instanceof Line2D) {
			Line2D line = (Line2D) shape;
			double x = line.getX1();
			x -= half;
			x += off[0];
			double y = line.getY1();
			y -= half;
			y += off[1];
			ellipse = new Ellipse2D.Double(x, y, length, length);
			path.append(ellipse, false);
			x = line.getX2();
			x -= half;
			x += off[0];
			y = line.getY2();
			y -= half;
			y += off[1];
			ellipse = new Ellipse2D.Double(x, y, length, length);
			path.append(ellipse, false);
			path.closePath();
			return path;
		}
		Rectangle2D r = shape.getBounds();
		r = (Rectangle2D) r.clone();
		r.setRect(r.getX() - half + off[0], r.getY() - half + off[1], r
				.getWidth(), r.getHeight());
		double minX = r.getX(), minY = r.getY(), centerX = r.getCenterX(), centerY = r
				.getCenterY(), maxX = r.getMaxX(), maxY = r.getMaxY();

		Rectangle2D rect = null;
		/* 矩形边框的八个可拖动点 ---> 顺时针创建。 */
		ellipse = new Ellipse2D.Double(minX, minY, length, length);
		path.append(ellipse, false);
		/* ellipse = new Ellipse2D.Double(cen;terX, minY, length, length); */
		rect = new Rectangle2D.Double(centerX, minY, length, length);
		path.append(rect, false);
		ellipse = new Ellipse2D.Double(maxX, minY, length, length);
		path.append(ellipse, false);
		/* ellipse = new Ellipse2D.Double(maxX, centerY, length, length); */
		rect = new Rectangle2D.Double(maxX, centerY, length, length);
		path.append(rect, false);
		ellipse = new Ellipse2D.Double(maxX, maxY, length, length);
		path.append(ellipse, false);
		/* ellipse = new Ellipse2D.Double(centerX, maxY, length, length); */
		rect = new Rectangle2D.Double(centerX, maxY, length, length);
		path.append(rect, false);
		ellipse = new Ellipse2D.Double(minX, maxY, length, length);
		path.append(ellipse, false);
		/* ellipse = new Ellipse2D.Double(minX, centerY, length, length); */
		rect = new Rectangle2D.Double(minX, centerY, length, length);
		path.append(rect, false);
		path.closePath();
		return path;
	}

	/**
	 * 绘制被选中SVG的八个可拖动点。
	 * 
	 * @param g
	 *            指定绘制画笔。
	 * @param shape
	 *            指定要绘制的图形。
	 */
	public static void paintSelectedShape(Graphics g, Shape shape) {
		if (isNull(g) || isNull(shape))
			return;
		Graphics2D g2d = createGraphics2D(g);

		g2d.setPaint(FILL_PAINT);
		g2d.fill(shape);
		g2d.setPaint(BORDER_PAINT);
		g2d.draw(shape);

		g2d.dispose();
	}

	/**
	 * 绘制被选中SVG的外切矩形。
	 * 
	 * @param g
	 *            指定绘制画笔。
	 * @param shape
	 *            指定要绘制的外切矩形的图形。
	 */
	public static void paintShapeBorder(Graphics g, Shape shape) {
		if (isNull(g) || isNull(shape))
			return;
		Graphics2D g2d = createGraphics2D(g);
		Rectangle r = shape.getBounds();
		g2d.setStroke(STROKE);
		g2d.setPaint(BORDER_PAINT);
		g2d.drawRect(r.x, r.y, r.width, r.height);
		/* g2d.fill(shape); */
		g2d.dispose();
	}

	/**
	 * 判断指定的点是否在指定的SVG图形上。
	 * 
	 * @param svg
	 *            指定svg图形。
	 * @param p
	 *            指定坐标点【与svg在同一坐标系下】。
	 * @return {@link Boolean} 如果垫在svg图形上：True 否则：False。
	 */
	public static boolean isSelectable(AbstractSVG svg, Point2D p) {
		Shape shape = createSVGSelectShape(svg);
		double[] width = parseLengths(svg, Constants.PR_STROKE_WIDTH);
		return isSelectable(shape, p, width);
	}

	/**
	 * 判断指定的点是否在指定的指定的Java图形内。
	 * 
	 * @param shape
	 *            指定Java图形。
	 * @param p
	 *            指定与指定图形同一坐标系下的坐标点。
	 */
	private static boolean isSelectable(Shape shape, Point2D p, double... stroke) {
		double width = 0;
		if (!isNull(stroke) && stroke.length > 0)
			width = stroke[0];
		if (shape instanceof Line2D) {
			Line2D line = (Line2D) shape;
			return (line.ptLineDist(p) <= (LINE_SELECT_RANG) + width / 2);// 点到线的距离是否==0
		} else if (!isNull(shape) && !isNull(p)) {
			return shape.contains(p);
		} else
			return Boolean.FALSE;
	}

	/**
	 * 判断指定的点是否在svg图形的可拖动点上。
	 * 
	 * @param svg
	 *            指定svg图形。
	 * @param p
	 *            指定坐标点【与svg在同一坐标系下】。
	 * @return {@link Boolean} 如果点在拖动点上：True，否则：False。
	 */
	public static boolean isResizeable(AbstractSVG svg, Point2D p) {
		Shape shape = createSVGHightLightShape(svg);
		return isResizeable(shape, p);
	}

	/**
	 * 判断指定点是否在指定的Java图形的可拖动点【可改变大小的位置】
	 * 
	 * @param shape
	 *            指定Java图形。
	 * @param p
	 *            指定同一坐标系下的坐标。
	 * @return {@link Boolean} 是可改变大小的位置：True，否则：False。
	 */
	public static boolean isResizeable(Shape shape, Point2D p) {
		shape = createSelectedShape(shape);
		return isEnterDragPoint(shape, p);
	}

	/***
	 * 判断指定点是否在指定的可拖动点集合内。
	 * 
	 * @param shape
	 *            指定计算后的拖动点的点集图形。
	 * @param p
	 *            指定同一坐标系下的坐标点。
	 * @return {@link Boolean} 属于指定的点集：True，否则：False。
	 */
	public static boolean isEnterDragPoint(Shape shape, Point2D p) {
		if (isNull(shape) || isNull(p))
			return Boolean.FALSE;
		return shape.contains(p);
	}

	/**
	 * 判断指定svg图形是相对位置，还是绝对位置。
	 * 
	 * @param svg
	 *            指定svg图形。
	 * @return {@link Boolean} True： 相对位置，False：绝对位置。
	 */
	public static boolean isRelativeFigure(AbstractSVG svg) {
		if (isNull(svg))
			return Boolean.FALSE;
		/* throw new NullPointerException("指定的图形为空！"); */
		CellElement ele = (CellElement) svg.getParent();
		if (ele instanceof Canvas)
			return Boolean.TRUE;
		else if (ele instanceof SVGContainer)
			return Boolean.FALSE;
		else
			return Boolean.FALSE;
		/* throw new IllegalArgumentException(ele + " 不是合法对象。"); */
	}

	/**
	 * 把svg描述的图形表示为Java中Swing的图形，且用该图形表示svg图形的选中高亮矩形区域。
	 * 
	 * @param svg
	 *            指定svg表述对象。
	 * @return {@link Shape} 返回Swing描述的图形。
	 */
	public static Shape createSVGHightLightShape(AbstractSVG svg) {
		return createShapeForSVG(svg, METHOD_PREFIX_FOR_Hight_LIGTH);
	}
	/**
	 * 把svg描述的图形，表示为Java中的Swing图形，且用该图形表示svg图形的可选中区域。
	 * @param svg
	 * @return
	 */
	public static Shape createSVGSelectShape(AbstractSVG svg) {
		return createShapeForSVG(svg, METHOD_PREFIX_FOR_SELECT);
	}

	private static Shape createShapeForSVG(AbstractSVG svg, String prefix) {
		Shape shape = null;
		if (isNull(svg) || isEmpty(prefix))
			return shape;
		Class<? extends SVGLocationConvert> clazz = SVGLocationConvert.class;
		Class<? extends AbstractSVG> paramType = svg.getClass();
		String name = prefix;
		name += paramType.getSimpleName();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(name, paramType);
			shape = (Shape) method.invoke(null, svg);
		} catch (Exception e) {
			if (LogUtil.canDebug()) {
				LogUtil.debugException(e.getMessage(), e);
			}
			return shape;
		}
		return shape;
	}

	/*-------------------------------高亮范围处理：开始-------------------------*/
	/* 把指定的SVG线转化为选中高亮的Swing线 */
	@SuppressWarnings("unused")
	private static Shape buildHighLightLine(Line line) {
		int[] keys = LINE_KEYS;
		double[] points = parseLengths(line, keys);
		double half = points[4] / 2;
		Line2D l = new Line2D.Double(points[0] + half, points[1] + half,
				points[2] + half, points[3] + half);
		return l;
	}

	/* 把指定的SVG的矩形转换为Swing中的矩形 */
	@SuppressWarnings("unused")
	private static Shape buildHighLightRectangle(
			com.wisii.wisedoc.document.svg.Rectangle rect) {
		int[] keys = RECTANGLE_KEYS;
		double[] points = parseLengths(rect, keys);
		double half = points[4] / 2;

		RoundRectangle2D r = new RoundRectangle2D.Double(points[0] + half,
				points[1] + half, points[2], points[3], points[5], points[6]);
		return r;
	}
	/* 把指定的SVG圆、椭圆，转换为Swing中的圆、椭圆 */
	@SuppressWarnings("unused")
	private static Shape buildHighLightEllipse(Ellipse ellipse) {
		int[] keys = ELLIPSE_KEYS;
		double[] points = parseLengths(ellipse, keys);
		double x = points[0], 
		y = points[1], 
		width = points[2], 
		height = points[3], 
		half = points[4] / 2;
		Ellipse2D e = new Ellipse2D.Double(x + half, y + half, width, height);
		return e;
	}
	/*-------------------------------高亮范围处理：结束-------------------------*/
	/*-------------------------------选中范围处理：开始-------------------------*/
	/*把指定的SVG线转化为选中高亮的Swing线，用于表示线的可选择范围。 */
	@SuppressWarnings("unused")
	private static Shape buildSelectLine(Line line) {
		return buildHighLightLine(line);
	}
	/* 把指定的SVG的矩形转换为Swing中的矩形，用于表示矩形的可选择范围。 */
	@SuppressWarnings("unused")
	private static Shape buildSelectRectangle(
			com.wisii.wisedoc.document.svg.Rectangle rect) {
		int[] keys = RECTANGLE_KEYS;
		double[] points = parseLengths(rect, keys);
		double increment = 2 * points[4],
		x = points[0],
		y = points[1],
		width = points[2],
		height = points[3];
		width += increment;
		height += increment;
		
		RoundRectangle2D r = new RoundRectangle2D.Double(x, y, width, height, 0, 0);
		return r;
	}
	/* 把指定的SVG圆、椭圆，转换为Swing中的圆、椭圆 ，用于表示圆、椭圆的可选中范围。*/
	@SuppressWarnings("unused")
	private static Shape buildSelectEllipse(Ellipse ellipse) {
		int[] keys = ELLIPSE_KEYS;
		double[] points = parseLengths(ellipse, keys);
		double 
		x = points[0], 
		y = points[1], 
		increment = 2 * points[4],
		width = points[2],
		height = points[3];
		width += increment;
		height += increment;
		Ellipse2D e = new Ellipse2D.Double(x, y, width, height);
		return e;
	}
	/*-------------------------------选中范围处理：结束-------------------------*/
	/**
	 * 从指定的FO对象中按指定的Key提取值，并把FixedLength类型转换为mpt整型
	 * 
	 * @param svg
	 *            指定FO对象。
	 * @param keys
	 *            指定提取值的KEY集合。
	 * @return double[] 返回获取指定KEY集合的值，并按KEY的顺序保存在double数组中返回。
	 */
	public static double[] parseLengths(AbstractSVG svg, int... keys) {
		double[] points = new double[keys.length];
		int count = 0;
		for (Integer i : keys) {
			int length = convertLength2MPT((FixedLength) svg.getAttribute(i));
			points[count++] = length;
		}
		return points;
	}

	/* 把内部【Area】的Length转换为MPT【毫磅】形式的数据表示 */
	private static int convertLength2MPT(FixedLength length) {
		if (isNull(length))
			return 0;
		int value = length.getLength().getValue();

		return value;
	}

	/* 根据指定的画笔创建一个副本，同时设置抗锯齿参数。 */
	private static Graphics2D createGraphics2D(Graphics g) {
		if (isNull(g))
			return (Graphics2D) g;
		Graphics2D g2d = (Graphics2D) g.create();
		/* 抗锯齿参数： VALUE_ANTIALIAS_ON开启抗锯齿，VALUE_ANTIALIAS_OFF关闭抗锯齿 */
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		/* 绘制参数：VALUE_RENDER_QUALITY质量优先。VALUE_RENDER_SPEED速度优先 */
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_SPEED);
		g2d.setStroke(new BasicStroke(1.0F, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER));
		return g2d;
	}

	/**
	 * 获得包含指定SVG图形的Viewport。 SVGContainer下放置Line, Ellipse, Rectangle
	 * Canvas下放置Line, Ellipse, Rectangle
	 * 
	 * @param svg
	 *            指定包含svg图形对象【Canvas、SVGContainer】。
	 * @return {@link Viewport} 返回包含指定svg图形的Area【Viewp】
	 */
	public static Viewport getViewport(AbstractSVG svg) {
		Viewport view = null;
		if (isNull(svg))
			return view;
		CellElement ele = (CellElement) svg.getParent();
		if (ele instanceof Canvas) {
			Canvas canvas = (Canvas) ele;
			view = canvas.getArea();
		} else if (ele instanceof SVGContainer) {
			SVGContainer container = (SVGContainer) ele;
			view = getViewport(container);
		}
		return view;
	}

	/** 查找相对位置包含svg图形的Viewport。SVGContainer下放置Line, Ellipse, Rectangle */
	private static Viewport getViewport(SVGContainer svg) {
		Viewport view = null;
		if (isNull(svg))
			return view;
		BlockViewport port = svg.getArea();
		view = LocationConvert.searchViewportFromContainer(port);
		return view;
	}

	/**
	 * 
	 * 根据指定的方向枚举信息，更新指定控件【Component】上的鼠标样式
	 * 
	 * @param ori
	 *            指定方向枚举信息
	 * @param comp
	 *            指定要更新鼠标样式的控件
	 * @return void 无
	 */
	public static void updateCursorStyle(Orientations ori, JComponent comp) {
		Cursor cursor = makeCursorStyle(ori);
		updateCursorStyle(cursor, comp);
	}

	/**
	 * 更新指定的控件为指定的光标样式。
	 * 
	 * @param cursor
	 *            指定光标样式。
	 * @param comp
	 *            指定要更新的控件。
	 */
	public static void updateCursorStyle(Cursor cursor, JComponent comp) {
		if (isNull(comp) || isNull(cursor))
			return;
		if (comp.getCursor() != cursor)
			comp.setCursor(cursor);
	}

	/**
	 * 根据指定的坐标点、图形，当前的鼠标样式。
	 * 
	 * @param svg
	 *            指定svg图形。
	 * @param p
	 *            指定坐标点。
	 * @return {@link Cursor} 返回计算后的鼠标样式。
	 */
	public static Cursor makeCursorStyle(AbstractSVG svg, Point2D p) {
		Shape shape = createSVGHightLightShape(svg);
		Orientations ori = calcOrientations(shape, p);
		return makeCursorStyle(ori);
	}

	/**
	 * 
	 * 判断对象是否正确选中，如果正确选中返回选中的类型
	 * 
	 * @param 选择的对象list
	 * @return 返回选中的类型
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static int setSelectType(List<CellElement> selects) {
		int type = 0;
		if (selects != null && selects.size() > 0) {
			Element parent = null;
			int size = selects.size();
			for (int i = 0; i < size; i++) {
				CellElement current = selects.get(i);
				if (current != null) {
					if ((current instanceof Line)
							|| (current instanceof com.wisii.wisedoc.document.svg.Rectangle)
							|| (current instanceof Ellipse)) {
						if (i == 0) {
							parent = current.getParent();
						}
						if (parent == null) {
							return type;
						}
						if (parent instanceof SVGContainer) {
							if (!(current.getParent() instanceof SVGContainer)) {
								return type;
							}
						} else if (parent instanceof Canvas) {
							if (!(parent.equals(current.getParent()))) {
								return type;
							}
						} else {
							return type;
						}
						if (i == size - 1) {
							if (parent instanceof SVGContainer) {
								type = SVGCONTAINER;
							} else if (parent instanceof Canvas) {
								type = CANVAS;
							}
						}
					} else {
						return type;
					}
				} else {
					return type;
				}
			}
		}
		return type;
	}

	/**
	 * 判断指定点在指定Java图形的位置，确定鼠标样式。
	 * 
	 * @param shape
	 *            指定Java图形。
	 * @param p
	 *            指定同一坐标系下的坐标。
	 * @return {@link Cursor} 返回计算后的鼠标样式。
	 */
	public static Cursor makeCursorStyle(Shape shape, Point2D p) {
		Orientations ori = calcOrientations(shape, p);
		return makeCursorStyle(ori);
	}
	/**
	 * 根据指定的Java图形，获得指定点在图形的可拖动点的位置。
	 * @param shape		指定Java图形【Line2D、Ellipse2D】
	 * @param p			指定坐标点。
	 * @return	{@link Orientations}	返回点在Java图形的外切圆
	 */
	public static Orientations calcOrientations(Shape shape, Point2D p){
		if (!isResizeable(shape, p))
			return Orientations.NORMAL;
		if(isNull(shape) || isNull(p))
			return Orientations.NORMAL;
		Orientations ori = null;
		if (shape instanceof Line2D){
			Line2D line = (Line2D)shape;
			Point2D p1 = line.getP1(),p2 = line.getP2();
			double x1 = Math.abs(p.getX() - p1.getX()),
			x2 = Math.abs(p.getX() - p2.getX());
			ori = (x1 - x2) > 0 ?Orientations.LINE_P2 : Orientations.LINE_P1;
		}else {
			shape = createSelectedShape(shape);
			ori = calcOrientations(shape, p, RADIUS);
		}
		return ori;
	}
	/**
	 * 根据指定的方位，确定采用鼠标样式。
	 * 
	 * @param ori
	 *            指定方位
	 * @return {@link Cursor} 返回由方位决定的鼠标样式。
	 */
	private static Cursor makeCursorStyle(Orientations ori) {
		CursorType type = CursorType.TEXT_CURSOR;
		switch (ori) {
		case NORTH:
			type = CursorType.N_RESIZE_CURSOR;
		case SOUTH:
			type = CursorType.S_RESIZE_CURSOR;
			break;
		case WEST:
			type = CursorType.W_RESIZE_CURSOR;
			break;
		case EAST:
			type = CursorType.W_RESIZE_CURSOR;
			break;
		case WEST_NORTH:
			type = CursorType.NW_RESIZE_CURSOR;
			break;
		case WEST_SOUTH:
			type = CursorType.SW_RESIZE_CURSOR;
			break;
		case EAST_NORTH:
			type = CursorType.NE_RESIZE_CURSOR;
			break;
		case EAST_SOUTH:
			type = CursorType.SE_RESIZE_CURSOR;
			break;
		case ON_FIGURE:
		case ON_QIANZHANG:
		case ON_MOVE:
			type = CursorType.MOVE_CURSOR;
			break;
		case RESIZE:
		case LINE_P1:
		case LINE_P2:
			type = CursorType.CROSSHAIR_CURSOR;
			break;
		case SELECT_CELL:
		case SELECT_CONTAINER:
			type = CursorType.HAND_CURSOR;
			break;
		case NORMAL:
		default:
			type = CursorType.TEXT_CURSOR;
			break;
		}
		return CursorManager.getSytemCursor(type);
	}

	/**
	 * 判断指定点在指定的图形的方位
	 * 
	 * @param shape
	 *            指定图形【计算后的由可拖动点组成的Shape】
	 * @param p
	 *            指定要判断的坐标点。
	 * @param step
	 *            指定有效范围【拖动点外延的像素数】
	 * @return {@link Orientations} 返回计算后的点在指定图形的方位。
	 */
	private static Orientations calcOrientations(Shape shape, Point2D p,
			float step) {
		if (isNull(shape) || isNull(p))
			return Orientations.NORMAL;
		Rectangle2D r = shape.getBounds2D();
		if (p.getX() < (r.getCenterX() - step)) {
			if (p.getY() < (r.getCenterY() - step))
				return Orientations.WEST_NORTH;
			else if (p.getY() > (r.getCenterY() + step))
				return Orientations.WEST_SOUTH;
			else
				return Orientations.WEST;
		} else if (p.getX() > (r.getCenterX() + step)) {
			if (p.getY() < (r.getCenterY() - step))
				return Orientations.EAST_NORTH;
			else if (p.getY() > (r.getCenterY() + step))
				return Orientations.EAST_SOUTH;
			else
				return Orientations.EAST;
		} else {
			if (p.getY() > r.getCenterY())
				return Orientations.SOUTH;
			else
				return Orientations.NORTH;
		}
	}

	public static final int SVGCONTAINER = 1;

	public static final int CANVAS = 2;

	/** 定义绘制可拖动点的直径 */
	private final static float DIAMETER = 6 * Constants.PRECISION;

	/** 定义绘制可拖动点的半径 */
	private final static float RADIUS = DIAMETER / 2;

	/** 定义绘制可拖动点的填充颜色 */
	private final static Paint FILL_PAINT = UIManager.getColor("MenuItem.background");/* new Color(128,128,128, 0); */;

	/** 定义绘制可拖动点的边框颜色 */
	private final static Paint BORDER_PAINT = Color.BLACK;

	/** 定义鼠标进入有效区域的范围 */
	private final static int DEFAULT_STEP = 5;
	/** 定义鼠标进入线有效选择区域的范围，在包含线宽的基础上在扩大该范围。 */
	private final static float LINE_SELECT_RANG = 3 * Constants.PRECISION;
	/** 定义绘制虚线的画笔 */
	private final static Stroke STROKE = new BasicStroke(1.0F,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, (float) 1.0,
			new float[] { 3, 1 }, 0);

	/** 定义构建选中高亮图形范围方法的前缀 */
	private final static String METHOD_PREFIX_FOR_Hight_LIGTH = "buildHighLight";
	/** 定义构建选择图形范围的方法名前缀。 */
	private final static String METHOD_PREFIX_FOR_SELECT = "buildSelect";
	/** 定义与线有关的KEY */
	private final static int[] LINE_KEYS = { Constants.PR_X1, Constants.PR_Y1, Constants.PR_X2, Constants.PR_Y2, Constants.PR_STROKE_WIDTH };
	/** 定义与矩形有关的KEY */
	private final static int[] RECTANGLE_KEYS = { Constants.PR_X, Constants.PR_Y, Constants.PR_WIDTH, Constants.PR_HEIGHT, Constants.PR_STROKE_WIDTH, Constants.PR_RX, Constants.PR_RY };
	/** 定义与圆、椭圆有关的KEY */
	private final static int[] ELLIPSE_KEYS = { Constants.PR_X, Constants.PR_Y, Constants.PR_WIDTH, Constants.PR_HEIGHT, Constants.PR_STROKE_WIDTH }; 
}
