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
package com.wisii.wisedoc.configure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.wisii.wisedoc.log.LogUtil;

public class RecentOpenFile
{

	public final static String RECENTOF_PATH = "configuration/RecentlyOpenedFiles.properties";

	private static boolean isLoadROF = false;

	private static int SIZEROF = 10;

	private static Properties recentopenfile = new Properties();
	static
	{
		try
		{
			recentopenfile.load(new FileInputStream(RECENTOF_PATH));
			isLoadROF = true;
		} catch (FileNotFoundException e)
		{
			LogUtil.debug(e);
			isLoadROF = false;
		} catch (IOException e)
		{
			LogUtil.debug(e);
			isLoadROF = false;
		} catch (Exception e)
		{
			LogUtil.debug(e);
			isLoadROF = false;
		}
	}

	public static void addOpenFile(String path)
	{
		Properties recentopenfile = getRecentopenfile();
		if (path == null)
		{
			return;
		}
		List<String> paths = new ArrayList<String>();
		paths.add(path);
		for (int i = 0; i < SIZEROF; i++)
		{
			String recentfilepath = recentopenfile.getProperty("RECENTFILEPATH"
					+ i);
			if (recentfilepath != null && !recentfilepath.equals(path)
					&& !paths.contains(recentfilepath)
					&& paths.size() < SIZEROF)
			{
				paths.add(recentfilepath);
			}
		}
		for (int i = 0; i < paths.size() && i < SIZEROF; i++)
		{
			recentopenfile.setProperty("RECENTFILEPATH" + i, paths.get(i));
		}
		save();
	}

	public static void save()
	{
		if (getRecentopenfile() != null)
			try
			{
				File file = new File(System.getProperty("user.dir") + "/"
						+ RECENTOF_PATH);
				if (!file.canWrite())
				{
					file.setWritable(true);
				}
				recentopenfile.store(new FileOutputStream(file), null);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
	}

	public static Properties getRecentopenfile()
	{
		return recentopenfile;
	}

	public static boolean isLoadROF()
	{
		return isLoadROF;
	}

	public static String getRencentEnableDirectory()
	{
		for (int i = 0; i < SIZEROF; i++)
		{
			String recentfilepath = recentopenfile.getProperty("RECENTFILEPATH"
					+ i);
			if (recentfilepath != null)
			{
				String dierctory = recentfilepath.substring(0, recentfilepath
						.lastIndexOf(File.separator));
				File file = new File(dierctory);
				if (file.exists() && file.isDirectory())
				{
					return dierctory;
				}
			}
		}
		return null;
	}
}
