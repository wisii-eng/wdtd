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
 * @ConfigureUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.configure;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import javax.swing.event.EventListenerList;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.DocumentPanel;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;
/**
 * 类功能描述：加载配置文件中各信息项、并保存，同时提供获取各信息项方法。
 * 
 * 作者：李晓光 创建日期：2008-8-19
 */
public final class ConfigureUtil
{

	/* 配置文件的缺省位置 */
	private final static String DEFAULT_PATH = "configuration/WiseDoc.ini";

	/* 创建读取配置文件的对象 */
	private static Properties configures = new Properties();

	/* 封装所有的监听对象 */
	private static EventListenerList listeners = new EventListenerList();

	/* 标识配置文件是否成功加载 【没有使用】 */
	private static boolean isLoad = false;

	private static String DEFAULTUNIT = "mm";

	// 默认的厘米的精度，即默认保留2位小数
	private static int DEFAULTPRRECISIONCM = 3;

	// 默认的英寸的精度，即默认保留2位小数
	private static int DEFAULTPRECISIONIN = 4;

	// 默认的pt的精度，即默认保留1位小数
	private static int DEFAULTPRECISIONPT = 1;

	// 默认的毫米的精度，即默认保留1位小数
	private static int DEFAULTPRECISIONMM = 2;
	private static int MAXBLOCKLEVEL = 10; 

	// 默认精度
	public static int DEFAULTPRECISION = 0;
	
	/**
	 * 在配置使用之前，加载配置文件中的所有配置项。
	 */
	static
	{

		try
		{
			configures.load(new FileInputStream(DEFAULT_PATH));
			isLoad = true;
		} catch (FileNotFoundException e)
		{
			// TODO LOG
			LogUtil.debug(e);
			isLoad = false;
		} catch (IOException e)
		{
			// TODO LOG
			LogUtil.debug(e);
			isLoad = false;
		} catch (Exception e)
		{
			LogUtil.debug(e);
			isLoad = false;
		}

	}

	/**
	 * 
	 * 根据指定的Key获得配置中的值
	 * 
	 * @param key
	 *            指定key
	 * @return String 返回key对应的值
	 */
	public final static String getProperty(String key)
	{
		return getProperty(key, configures);
	}

	/**
	 * 
	 * 获得单位配置项的精度
	 * 
	 * @param
	 * @return
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public static final int getPrecision(String key)
	{
		if (key == null)
		{
			return 0;
		}
		key = ConfigureConstants.RRECISION + key.toLowerCase();
		if (!ConfigureConstants.RRECISIONCM.equals(key)
				&& !ConfigureConstants.RRECISIONIN.equals(key)
				&& !ConfigureConstants.RRECISIONPT.equals(key)
				&& !ConfigureConstants.RRECISIONMM.equals(key))
		{
			return DEFAULTPRECISION;
		}
		String stringvalue = getProperty(key);
		int value = -1;
		if (stringvalue != null)
		{
			try
			{
				value = Integer.parseInt(stringvalue);
			} catch (NumberFormatException e)
			{
				LogUtil.infoException("配置项：'" + key + "'值：'" + stringvalue
						+ "'不正确", e);
			}
		}
		if (value < 0)
		{
			if (ConfigureConstants.RRECISIONCM.equals(key))
			{
				value = DEFAULTPRRECISIONCM;
			} else if (ConfigureConstants.RRECISIONIN.equals(key))
			{
				value = DEFAULTPRECISIONIN;
			} else if (ConfigureConstants.RRECISIONPT.equals(key))
			{
				value = DEFAULTPRECISIONPT;
			} else
			{
				value = DEFAULTPRECISIONMM;
			}

		}
		return value;
	}

	/**
	 * 
	 * 根据指定的KEY，从指定的属性集中取出相应的值
	 * 
	 * @param key
	 *            指定KEY
	 * @param p
	 *            指定属性集
	 * @return String 返回属性集中KEY对应的值
	 */
	public final static String getProperty(String key, Properties p)
	{
		LogUtil.debug("isEmpty(key)" + WisedocUtil.isEmpty(key));
		LogUtil.debug("isNull(p)" + isNull(p));

		if (WisedocUtil.isEmpty(key) || isNull(p))
		{
			return null;
		}

		return p.getProperty(key, "");
	}

	/**
	 * 
	 * 根据指定的key获得配置中的值，如果值为Null，则采用指定的默认值
	 * 
	 * @param key
	 *            指定key
	 * @param defaultValue
	 *            指定默认值
	 * @return 返回key对应的值
	 */
	public final static String getProperty(String key, String defaultValue)
	{

		return getProperty(key, defaultValue, configures);
	}

	public final static String getUnit()
	{
		return getProperty(ConfigureConstants.UNIT, DEFAULTUNIT);
	}

	/**
	 * 
	 * 根据指定KEY，从指定的属性集中读取相应的值，如果没有找到KEY，或找到当值为Null或"" 则返回指定的默认值
	 * 
	 * @param key
	 *            指定key
	 * @param defaultValue
	 *            指定缺省值
	 * @param p
	 *            指定属性集合
	 * @return 返回KEY对应的值
	 */
	public final static String getProperty(String key, String defaultValue,
			Properties p)
	{
		String t = getProperty(key, p);
		if (WisedocUtil.isEmpty(t))
		{
			LogUtil.debug("资源包中没有为该KEY设置Value");
			t = defaultValue;
		}

		return t;
	}

	/**
	 * 
	 * 根据指定Key集，获得key和值组成的map集
	 * 
	 * @param keys
	 *            指定key集
	 * @return Map 返回由key、value组成的Map集
	 */
	public final static Map<String, String> getProperties(List<String> keys)
	{
		Map<String, String> map = new HashMap<String, String>();

		LogUtil.debug("isNull = " + isNull(keys) + " size = " + keys.size());

		if (isNull(keys) || keys.size() == 0)
			return map;
		String[] temp = keys.toArray(new String[]
		{});
		map = getProperties(temp);
		return map;
	}

	/**
	 * 
	 * 根据指定Key集，获得key和值组成的map集
	 * 
	 * @param key
	 *            指定key集
	 * @return Map 返回由key、value组成的Map集
	 */
	public final static Map<String, String> getProperties(String... keys)
	{
		Map<String, String> map = new HashMap<String, String>();

		LogUtil.debug("isNull = " + isNull(keys) + " size = " + keys.length);

		if (isNull(keys) || keys.length == 0)
		{
			return map;
		}

		for (String key : keys)
		{
			if (WisedocUtil.isEmpty(key))
				continue;
			map.put(key, getProperty(key));
		}
		return map;
	}

	/**
	 * 
	 * 获得以指定字符串开头的KEY对应的Value集合，并封装key及value到map中。
	 * 
	 * @param prefix
	 *            指定key的前缀
	 * @return Map<String,String> key、value组成的Map
	 */
	public final static Map<String, String> getPropertiesStartWith(String prefix)
	{
		return getPropertiesStartWith(prefix, configures);
	}

	/**
	 * 
	 * 获得以指定字符串开头的KEY对应的Value集合，并封装key及value到map中。
	 * 
	 * @param prefix
	 *            指定key的前缀
	 * @param Properties
	 *            指定保存属性的对象
	 * @return Map<String,String> key、value组成的Map
	 */
	public final static Map<String, String> getPropertiesStartWith(
			String prefix, Properties p)
	{
		Map<String, String> map = new HashMap<String, String>();
		if (WisedocUtil.isEmpty(prefix) || isNull(p))
		{
			return map;
		}
		Set<Entry<Object, Object>> entry = p.entrySet();
		String key = "", value = "";
		for (Entry<Object, Object> en : entry)
		{

			LogUtil.debug("key = " + en.getKey());
			LogUtil.debug("value = " + en.getValue());

			if (isNull(en))
			{
				continue;
			}
			key = en.getKey() + "";
			if (!key.startsWith(prefix, 0))
				continue;
			value = en.getValue() + "";
			map.put(key, value);
		}

		return map;
	}

	/**
	 * 
	 * 指定key、value【缺省值】组成的map集合，根据key从配置中获得相应的值，
	 * 如果值为Null，则采用指定map中相同key相应的值，最终返回由key、value
	 * 组成的map。等同于循环调用getProperty(String key, String defaultValue)
	 * 
	 * @param map
	 *            指定key及对应key的缺省值注册的Map集
	 * @return map 返回key、value组成的map集
	 */
	public final static Map<String, String> getProperties(
			Map<String, String> map)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (isNull(map) || map.size() == 0)
		{
			return result;
		}
		result.putAll(map);
		Set<Entry<String, String>> entry = result.entrySet();
		for (Entry<String, String> e : entry)
		{
			LogUtil.debug("key = " + e.getKey());
			LogUtil.debug("value = " + e.getValue());
			if (isNull(e))
				continue;
			if (WisedocUtil.isEmpty(e.getKey()))
				continue;
			result.put(e.getKey(), getProperty(e.getKey(), e.getValue()));
		}
		return result;
	}

	/**
	 * 
	 * 注册监听对象
	 * 
	 * @param listener
	 *            指定监听者
	 * @return 无
	 */
	public static void addConfigureListener(ConfigureListener listener)
	{
		listeners.add(ConfigureListener.class, listener);
	}

	/**
	 * 
	 * 取消监听
	 * 
	 * @param listener
	 *            指定取消监听的监听者
	 * @return 无
	 */
	public static void removeConfigureListener(ConfigureListener listener)
	{
		listeners.remove(ConfigureListener.class, listener);
	}

	/**
	 * 
	 * 通知监听者，配置信息发生了变化
	 * 
	 * @param event
	 *            发生变化的元素封装
	 * @return 无
	 */
	private static void fireConfigureChanged(ConfigureEvent event)
	{
		if (isNull(event))
			return;

		Object[] allListeners = listeners.getListenerList();

		for (int i = allListeners.length - 2; i >= 0; i -= 2)
		{
			if (allListeners[i] == ConfigureListener.class)
			{
				((ConfigureListener) allListeners[i + 1])
						.configureChanged(event);
			}
		}
	}

	/**
	 * 
	 * 用于判定是否有配置项
	 * 
	 * @return 如果有配置项：True 否则：False
	 */
	public final static boolean isEmpty()
	{
		return (isNull(configures)) || (configures.size() == 0);
	}

	/**
	 * 
	 * 把指定的value设置到配置中的key上。key!=null
	 * 
	 * @param key
	 *            指定key
	 * @param value
	 *            指定value
	 * @return 无
	 */
	public final static void setProperty(String key, String value)
	{
		if (WisedocUtil.isEmpty(key) || WisedocUtil.isEmpty(value))
		{

			return;
		}
		String oldValue = configures.getProperty(key);
		if (isSame(oldValue, value))
			return;

		configures.setProperty(key, value);
		ConfigureEvent event = new ConfigureEvent(key, oldValue, value);
		fireConfigureChanged(event);
	}

	/**
	 * 
	 * 更新map中key对应的value到配置中。
	 * 
	 * @param map
	 *            指定要更新的集合
	 * @return 无
	 */
	public final static void setProperties(Map<String, String> map)
	{
		if (isNull(map) || map.size() == 0)
			return;

		Set<Entry<String, String>> set = map.entrySet();
		List<ChangedItem> items = new ArrayList<ChangedItem>();

		String oldValue = "", key = "", newValue = "";
		for (Entry<String, String> entry : set)
		{
			if (isNull(entry))
				continue;
			key = entry.getKey();
			if (WisedocUtil.isEmpty(key))
				continue;
			newValue = entry.getValue();
			oldValue = configures.getProperty(key);
			if (isSame(oldValue, newValue))
				continue;
			items.add(new ChangedItem(key, oldValue, newValue));
			configures.setProperty(key, newValue);
		}
		if (items.size() != 0)
			fireConfigureChanged(new ConfigureEvent(items));
	}

	/**
	 * 
	 * 判断原始值和新设置的值是否相等
	 * 
	 * @param oldValue
	 *            指定原始值
	 * @param newValue
	 *            指定设置值
	 * @return 如果相等：True 否则：False
	 */
	private final static boolean isSame(String oldValue, String newValue)
	{
		if (isNull(oldValue))
			return isNull(newValue);
		else
			return oldValue.equalsIgnoreCase(newValue);
	}

	/**
	 * 
	 * 保存当前的配置项到指定的文件中
	 * 
	 */
	public final static void store()
	{
		try
		{
			File file = new File(DEFAULT_PATH);
			if(!file.canWrite())
			{
				file.setWritable(true);	
			}
			configures.store(new FileOutputStream(file),
					new Date().toString());
		} catch (FileNotFoundException e)
		{
			// TODO LOG
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO LOG
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 加载指定的配置文件
	 * 
	 * @param path
	 *            指定配置文件路径
	 */
	public final static void load(String path)
	{
		configures.clear();
		if (WisedocUtil.isEmpty(path))
			return;

		try
		{
			configures.load(new FileInputStream(path));
		} catch (FileNotFoundException e)
		{
			// TODO LOG
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO LOG
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 根据字符串返回内部单位，如传入"厘米",返回"cm"等
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static String getUnitofText(String text)
	{
		if (text != null && !text.trim().isEmpty())
		{
			text = text.trim();
			String configcmtext = getProperty(ConfigureConstants.TEXTOFCM);
			if (configcmtext != null)
			{
				String[] cmtexts = configcmtext.split(",");
				for (int i = 0; i < cmtexts.length; i++)
				{
					if (text.equals(cmtexts[i]))
					{
						return "cm";
					}
				}
			}
			String configmmtext = getProperty(ConfigureConstants.TEXTOFMM);
			if (configmmtext != null)
			{
				String[] mmtexts = configmmtext.split(",");
				for (int i = 0; i < mmtexts.length; i++)
				{
					if (text.equals(mmtexts[i]))
					{
						return "mm";
					}
				}
			}
			String configpttext = getProperty(ConfigureConstants.TEXTOFPT);
			if (configpttext != null)
			{
				String[] pttexts = configpttext.split(",");
				for (int i = 0; i < pttexts.length; i++)
				{
					if (text.equals(pttexts[i]))
					{
						return "pt";
					}
				}
			}
			String configintext = getProperty(ConfigureConstants.TEXTOFIN);
			if (configintext != null)
			{
				String[] intexts = configintext.split(",");
				for (int i = 0; i < intexts.length; i++)
				{
					if (text.equals(intexts[i]))
					{
						return "in";
					}
				}
			}
			String configemtext = getProperty(ConfigureConstants.TEXTOFEM);
			if (configemtext != null)
			{
				String[] intexts = configemtext.split(",");
				for (int i = 0; i < intexts.length; i++)
				{
					if (text.equals(intexts[i]))
					{
						return "em";
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * 传入单位得到界面显示字符串
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public static String getTextofUnit(String unit)
	{
		if ("cm".equals(unit))
		{
			String configcmtext = getProperty(ConfigureConstants.TEXTOFCM);
			if (configcmtext != null)
			{
				String[] cmtexts = configcmtext.split(",");
				return cmtexts[0];
			}
		} else if ("mm".equals(unit))
		{
			String configmmtext = getProperty(ConfigureConstants.TEXTOFMM);
			if (configmmtext != null)
			{
				String[] mmtexts = configmmtext.split(",");
				return mmtexts[0];
			}
		} else if ("pt".equals(unit))
		{
			String configpttext = getProperty(ConfigureConstants.TEXTOFPT);
			if (configpttext != null)
			{
				String[] pttexts = configpttext.split(",");
				return pttexts[0];
			}
		} else if ("in".equals(unit))
		{
			String configintext = getProperty(ConfigureConstants.TEXTOFIN);
			if (configintext != null)
			{
				String[] intexts = configintext.split(",");
				return intexts[0];
			}
		}
		return unit;
	}
	/**
	 * 
	 * 获得最大的大纲级别数
	 *
	 * @param      
	 * @return      int:  大纲级别数
	 * @exception   
	 */
    public static int getMaxBlockLevel()
    {
		String maxblstring = getProperty(ConfigureConstants.MAXBLOCKLEVEL);
		int maxblocklevel = MAXBLOCKLEVEL;
		if (maxblstring != null)
		{
			try
			{
				int maxbllevel = Integer.parseInt(maxblstring);
				if (maxbllevel > 0)
				{
					maxblocklevel = maxbllevel;
				}
			} catch (Exception e)
			{
			}
		}
		return maxblocklevel;
	} 
    /**
	 * 是否显示空行的换行符
	 */
	public static boolean isDrawlinebreak()
	{
		boolean drawlinebreak = true;
		String configstr = getProperty(ConfigureConstants.DRAWLINEBREAK);
		if("false".equalsIgnoreCase(configstr))
		{
			drawlinebreak =false;
		}
		return drawlinebreak;
	}

	/**
	 * @param drawlinebreak 设置drawlinebreak成员变量的值
	
	 * 值约束说明
	
	 */
	public static void setDrawlinebreak(boolean drawlinebreak)
	{
		if (drawlinebreak != isDrawlinebreak())
		{
			setProperty(ConfigureConstants.DRAWLINEBREAK, Boolean
					.toString(drawlinebreak));
			updateUI();
			store();
		}
	}

	/**
	 *单元格或高级块容器没有设置边框时，是否显示框线
	 */
	public static boolean isDrawnullborder()
	{
		boolean drawnullborder = true;
		String configstr = getProperty(ConfigureConstants.DRAWNULLBORDER);
		if ("false".equalsIgnoreCase(configstr))
		{
			drawnullborder = false;
		}
		return drawnullborder;
	}

	/**
	 * 
	 */
	public static void setDrawnullborder(boolean drawnullborder)
	{
		if (drawnullborder != isDrawnullborder())
		{
			setProperty(ConfigureConstants.DRAWNULLBORDER, Boolean
					.toString(drawnullborder));
			updateUI();
			store();
		}
	}
	/**
	 * 
	 */
	public static void setLoginServlet(String url)
	{
		setProperty("login", url);
		store();
	}
	/**
	 * 
	 */
	public static void setRememberPassword(boolean select)
	{
		setProperty("rememberpassword", Boolean.toString(select));
		store();
	}
	/**
	 * 
	 */
	public static void setAutoLogin(boolean auto)
	{
		setProperty("autologin", Boolean.toString(auto));
		store();
	}
	/**
	 * 
	 */
	public static void setUserName(String uname)
	{
		setProperty("name", uname);
		store();
	}
	/**
	 * 
	 */
	public static void setUserPwd(String pwd)
	{
		setProperty("password", pwd);
		store();
	}
	public static void removeValue(String key){
		configures.remove(key);
		store();
	}
	private static void updateUI()
	{
		final AbstractEditComponent panel = RibbonUpdateManager.Instance
				.getCurrentEditPanel();
		if (panel instanceof DocumentPanel)
		{
			((DocumentPanel)panel).reload();
		} else
		{
			panel.startPageSequence();
		}
	}
	/**
	 * 测试用
	 */
	public static void main(String[] args)
	{
		Map<String, String> map = getPropertiesStartWith("lxg");
		Set<Map.Entry<String, String>> set = map.entrySet();
		for (Map.Entry<String, String> ob : set)
		{
			System.out.println(ob.getValue());
		}
	}

}
