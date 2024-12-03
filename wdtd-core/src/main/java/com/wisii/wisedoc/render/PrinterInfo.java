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
package com.wisii.wisedoc.render;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class PrinterInfo
{
    /* 定义PCL的文件类型 */
    public static final String PCL = "PCL";
    /* 定义PS的文件类型 */
    public static final String PS = "PS";
    /* 定义AWT的文件类型 */
    public static final String AWT = "AWT";
    /* 不是任何类型 */
    public static final String NONE = "NONE";
    /* 定义打印的文件、流类型， PCL、PS */
    private String type;
    /* 定义打印机名称 */
    private String name;
    /* 指定打印机的入纸口 */
    private String port;

    public PrinterInfo()
    {
        this("", "", "");
    }

    public PrinterInfo(String type, String name, String port)
    {
        this.type = type;
        this.name = name;
        this.port = port;
    }

    public String getName()
    {
        return name;
    }

    public String getPort()
    {
        return port;
    }

    public String getType()
    {
        return type;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
