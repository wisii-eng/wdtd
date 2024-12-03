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
 * @ConfigureListener.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.configure;

import java.util.EventListener;

/**
 * 类功能描述：用于接收操作事件的侦听器接口。对处理操作事件感兴趣的类可以实现此接口，
 * 而使用该类创建的对象可使用组件的 addConfigureListener 方法向该组件注册。在发生操作
 * 事件时，调用该对象的 configureChanged 方法。 
 *
 * 作者：李晓光
 * 创建日期：2008-8-19
 */
public interface ConfigureListener extends EventListener{
	void configureChanged(ConfigureEvent event);
}
