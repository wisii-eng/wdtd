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
 * @LogUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.log;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.logging.Log;

import com.wisii.wisedoc.configure.ConfigureUtil;

/**
 * 类功能描述：用于输出LOG
 * 
 * 作者：zhangqiang 创建日期：2008-7-15
 */
public class LogUtil {
	/* 定义LOG等级 */
	public static enum LogLevel {
		/* 关闭LOG */
		OFF(7),
		/* 致命的、不可预期的、不可恢复的，可能导致程序直接退出 */
		FATAL(6),
		/* 非致命的，不可回复的 */
		ERROR(5),
		/* 可恢复的 */
		WARN(4),
		/* 普通描述信息 */
		INFO(3),
		/* 调试时用 */
		DEBUG(2),

		TRACE(1),
		/* 所有的等级的LOG均被输出 */
		ALL(0);
		private int level = -1;

		private LogLevel(int level) {
			this.level = level;
		}

		public int getEnumLevel() {
			return this.level;
		}

		public final static LogLevel getLevel(int i) {
			LogLevel[] levels = LogLevel.values();
			LogLevel result = LogLevel.OFF;
			for (LogLevel logLevel : levels) {
				if (logLevel.getEnumLevel() == i) {
					result = logLevel;
					break;
				}
			}
			return result;
		}

		public final static LogLevel getLevel(String name) {
			LogLevel result = LogLevel.OFF;
			name = name.toUpperCase();
			try {
				result = LogLevel.valueOf(name);
			} catch (Exception e) {
				info(e);
			}

			return result;
		}
	}

	/* 创建输出LOg的对象 */
	private static WDDELog LOG = new WDDELog("");

	/* 定义消息样式，{0}：日期、时间{1}：类名 {2}：方法名 {3}：消息 */
	private final static String PATTERN = "|{0}|{1}|{2}|{3}|{4}";
	/* 定义LOG的日期、时间样式 */
	private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/* 创建LOG用日期、时间格式化对象 */
	private final static SimpleDateFormat DATEFORMAT = new SimpleDateFormat(
			DATE_FORMAT);

	/* 定义entry的输出样式 */
	private final static String METHOD_ENTRY = "|----------------------{0}|{1}|ENTRY----------------------";
	/* 定义exit的输出样式 */
	private final static String METHOD_EXIT = "|----------------------{0}|{1}|EXIT-----------------------";
	/* 定义配置文件中用KEY, LOG等级设置 */
	public final static String LOG_LEVEL = "LOG_LEVEL";
	/* 定义配置文件中用KEY，LOG输出位置设置 1=控制台2=文件流 3=控制台+文件流4=默认的文件流 */
	public final static String LOG_LOCATION = "LOG_LOCATION";
	/* 指定LOG的输出文件的文件名：绝对路径、相对路径 */
	public final static String LOG_FILE_NAME = "LOG_FILE_NAME";
	static {
		load();
	}

	private final static void load() {
		String level = ConfigureUtil
				.getProperty(LOG_LEVEL, LogLevel.OFF.name()).trim();

		LogLevel logLevel = LogLevel.getLevel(level);

		setLevel(logLevel);
	}

	/**
	 * 
	 * 设置输出log的等级
	 * 
	 * @param level
	 *            指定输出的LOG等级
	 * @return 无
	 */
	public final static void setLevel(LogLevel level) {
		LOG.setLevel(level.getEnumLevel());
	}

	/**
	 * 
	 * 获得输出LOG的等级
	 * 
	 * @return {@link LogLevel} 返回当前LOG的等级
	 */
	private final static LogLevel getLevel() {
		return LogLevel.getLevel(LOG.getLevel());
	}

	/**
	 * 
	 * 输出fatal等级的LOG
	 * 
	 * @param message
	 *            指定输出信息，如果指定的对象，则输出Object.toString();
	 * @return 无
	 */
	public final static void fatal(Object message) {
		if (canFatal())
			LOG.fatal(getMessage(message));
	}

	/**
	 * 
	 * 输出error等级的LOG
	 * 
	 * @param message
	 *            指定输出信息，如果指定的对象，则输出Object.toString();
	 * @return 无
	 */
	public final static void error(Object message) {
		if (canError())
			LOG.error(getMessage(message));
	}

	/**
	 * 
	 * 输出warn等级的LOG
	 * 
	 * @param message
	 *            指定输出信息，如果指定的对象，则输出Object.toString();
	 * @return 无
	 */
	public final static void warn(Object message) {
		if (canWarn())
			LOG.warn(getMessage(message));
	}

	/**
	 * 
	 * 输出info等级的LOG
	 * 
	 * @param message
	 *            指定输出信息，如果指定的对象，则输出Object.toString();
	 * @return 无
	 */
	public final static void info(Object message) {
		if (canInfo())
			LOG.info(getMessage(message));
	}

	/**
	 * 
	 * 输出debug等级的LOG
	 * 
	 * @param message
	 *            指定输出信息，如果指定的对象，则输出Object.toString();
	 * @return 无
	 */
	public final static void debug(Object message) {
		if (canDebug())
			LOG.debug(getMessage(message));
	}

	/**
	 * 
	 * 输出debug等级的LOG
	 * 
	 * @param message
	 *            指定描述信息
	 * @param obj
	 *            指定对象，如果是数组，则打印各项，否则直接调用toString方法
	 * @return {@link String} 两个信息的连接
	 */
	public final static void debug(String message, Object obj) {
		if (canDebug()) {
			LOG.debug(getMessage(message + ":" + toString(obj)));
		}
	}

	private final static String toString(Object obj) {
		String temp = "";
		if (isNull(obj))
			return temp;

		if (obj.getClass().isArray())
			temp = Arrays.toString((Object[]) obj);
		else
			temp = obj.toString();

		return temp;
	}

	/**
	 * 
	 * 输出fatal等级的异常信息
	 * 
	 * @param message
	 *            信息描述
	 * @param t
	 *            指定异常信息
	 */
	public final static void fatalException(String message, Throwable t) {
		if (canFatal())
			LOG.fatal(getMessage(message), t);
	}

	/**
	 * 
	 * 输出error等级的异常信息
	 * 
	 * @param message
	 *            信息描述
	 * @param t
	 *            指定异常信息
	 */
	public final static void errorException(String message, Throwable t) {
		if (canError())
			LOG.error(getMessage(message), t);
	}

	/**
	 * 
	 * 输出warn等级的异常信息
	 * 
	 * @param message
	 *            信息描述
	 * @param t
	 *            指定异常信息
	 */
	public final static void warnException(String message, Throwable t) {
		if (canWarn())
			LOG.warn(getMessage(message), t);
	}

	/**
	 * 
	 * 输出info等级的异常信息
	 * 
	 * @param message
	 *            信息描述
	 * @param t
	 *            指定异常信息
	 */
	public final static void infoException(String message, Throwable t) {
		if (canInfo())
			LOG.info(getMessage(message), t);
	}

	/**
	 * 
	 * 输出debug等级的异常信息
	 * 
	 * @param message
	 *            信息描述
	 * @param t
	 *            指定异常信息
	 */
	public final static void debugException(String message, Throwable t) {
		if (canDebug())
			LOG.debug(getMessage(message), t);
	}

	/**
	 * 
	 * 判断能否输出FATAL等级的LOG
	 * 
	 * @return 能够输出FATAL等级LOG:TRUE 否则：False
	 */
	public final static boolean canFatal() {
		return canLog(LogLevel.FATAL);
	}

	/**
	 * 
	 * 判断能否输出ERROR等级的LOG
	 * 
	 * @return 能够输出ERROR等级LOG:TRUE 否则：False
	 */
	public final static boolean canError() {
		return canLog(LogLevel.ERROR);
	}

	/**
	 * 
	 * 判断能否输出WARN等级的LOG
	 * 
	 * @return 能够输出WARN等级LOG:TRUE 否则：False
	 */
	public final static boolean canWarn() {
		return canLog(LogLevel.WARN);
	}

	/**
	 * 
	 * 判断能否输出WARN等级的LOG
	 * 
	 * @return 能够输出WARN等级LOG:TRUE 否则：False
	 */
	public final static boolean canInfo() {
		return canLog(LogLevel.INFO);
	}

	/**
	 * 
	 * 判断能否输出DEBUG等级的LOG
	 * 
	 * @return 能够输出DEBUG等级LOG:TRUE 否则：False
	 */
	public final static boolean canDebug() {
		return canLog(LogLevel.DEBUG);
	}

	/**
	 * 
	 * 判断指定等级的LOG是否可以输出
	 * 
	 * @param level
	 *            指定被检测的LOG等级
	 * @return 如果可以输出：True 否则：False
	 */
	private final static boolean canLog(LogLevel level) {
		return (getLevel() != LogLevel.OFF)&&(level.getEnumLevel() >= getLevel().getEnumLevel());
	}

	/**
	 * 
	 * 把指定的对象格式化输出，以字符串的形式返回
	 * 
	 * @param message
	 *            指定要输出的对象、字符串，如果是对象，调用对象的toString方法
	 * @return {@link String} 返回格式化后的字符串。
	 */
	private final static String getMessage(Object message) {
		StackTraceElement element = getStackElement();
		String className = element.getClassName(), methodName = element
				.getMethodName(), lineNumber = element.getLineNumber() + "";
		String msg = "";
		if (isNull(message))
			msg += message;
		else {
			if (message.getClass().isArray())
				msg = Arrays.toString((Object[]) message);
			else
				msg = message.toString();
		}
		return MessageFormat.format(PATTERN, getSytemDateTime(), className,
				methodName, lineNumber, msg);
	}

	/**
	 * 
	 * 获得堆栈信息，返回调用写LOG方法的堆栈信息
	 * 
	 * @return {@link StackTraceElement} 返回堆栈信息
	 */
	private final static StackTraceElement getStackElement() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();

		boolean flag = false;
		for (StackTraceElement element : elements) {
			if (flag)
				return element;
			if (isFound(element.getMethodName()))
				flag = true;
		}

		return elements[4];
	}

	/**
	 * 
	 * 判读指定的方法名，是否属于当前类用于写LOG的方法
	 * 
	 * @param methodName
	 *            指定某一方法名称
	 * @return 如果指定的方法属于LOG用方法：True 否则：False
	 */
	private final static boolean isFound(String methodName) {
		String[] methods = { "fatal", "error", "warn", "info", "debug",
				"entry", "exit", "fatalException", "errorException",
				"warnException", "infoException", "debugException" };
		HashSet<String> set = new HashSet<String>(Arrays.asList(methods));
		return set.contains(methodName);
	}

	/**
	 * 
	 * 获得输出LOG的位置当前方法的方法名
	 * 
	 * @return String 方法名
	 */
	private final static String getMethodName() {
		StackTraceElement element = getStackElement();

		return element.getMethodName();
	}

	/**
	 * 
	 * 获得输出LOG位置当前类的类名
	 * 
	 * @return String 类名
	 */
	private final static String getClassName() {
		StackTraceElement element = getStackElement();

		return element.getClassName();
	}

	/**
	 * 
	 * 获得输出LOG位置的行号
	 * 
	 * @return String 返回行号
	 */
	private final static String getLineNumber() {
		StackTraceElement element = getStackElement();

		return element.getLineNumber() + "";
	}

	private final static String getSytemDateTime() {
		return DATEFORMAT.format(new Date());
	}

	/**
	 * 
	 * 方法的入口
	 * 
	 * @param method
	 *            指定方法名等信息
	 */
	public final static void entry() {
		String t = MessageFormat.format(METHOD_ENTRY, getSytemDateTime(),
				getMethodName());
		if (canInfo())
			LOG.info(t);
	}

	/**
	 * 
	 * 方法的出口
	 * 
	 * @param method
	 *            指定方法名等信息。
	 */
	public final static void exit() {
		String t = MessageFormat.format(METHOD_EXIT, getSytemDateTime(),
				getMethodName());
		if (canInfo())
			LOG.info(t);
	}

	public final static void setWriter(OutputStream out) {
		LOG.setOut(out);
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static Log getlogger() {
		return LOG;
	}

	public static void main(String[] args) {
		// System.out.println(getLevel());
		// setLevel(LogLevel.DEBUG);
		try {
			// setWriter(new FileOutputStream("1.txt", true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entry();
		System.out.println(getLevel());
		String[] a = { "a", "b", "c", "d" };
		Annotation[] an = LogUtil.class.getAnnotations();
		for (Annotation annotation : an) {
			System.out.println(an.toString());
		}
		info(a);
		debug("This is a test");
		// LOG.fatal("This is a test", new FileNotFoundException("OK"));
		Exception e = new FileNotFoundException("OK");
		fatalException("test", e);
		// System.out.println(LOG.getLevel());
		exit();
	}
}
