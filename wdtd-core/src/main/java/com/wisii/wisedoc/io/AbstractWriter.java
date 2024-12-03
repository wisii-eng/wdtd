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
 * @AbstractWriter.java
 *                      北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.wisii.wisedoc.document.Document;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang
 * 创建日期：2012-5-25
 */
public abstract class AbstractWriter implements Writer
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.lang.String,
	 * com.wisii.wisedoc.document.Document)
	 */
	@Override
	public void write(String filepath, Document document) throws IOException
	{
		if (document == null)
		{
			return;
		}
		if (!checkfile(filepath))
		{
			throw new IOException(filepath + "不是正确的文件类型");
		}
		if (isEmpty(filepath))
		{
			throw new IOException("文件名为空");
		}
		File file = new File(filepath);
//		if (!file.canWrite())
//		{
//			throw new IOException("文件:" + filepath + "不可写");
//		}
		FileOutputStream out = new FileOutputStream(file);
		write(out, document);
		try
		{
			out.close();
		} catch (Exception e)
		{

		}
	}

	/**
	 * 钩子方法，需要判断文件是否合法时，如可能需要判断文件后缀等
	 * 
	 * @param 
	 * @return 
	 * @exception 
	 */

	protected  boolean checkfile(String filepath)
	{
		return true;
	}

}
