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
 * @MessageConstants.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.resource;

/**
 * 类功能描述：语言项相关的静态变量
 * 
 * 作者：zhangqiang 创建日期：2008-9-11
 */
public final class MessageConstants
{

	// 前缀静态变量定义区域 begin，所有的前缀变量需在此定义

	// 数据结构相关消息项前缀
	public static final String DSMESSAGECONSTANTS = "wsd.datastructure.";

	// GUI界面通用菜单前缀
	public static final String GUI_COMMON = "wsd.view.gui.common.";

	// 文本GUI菜单的前缀
	public static final String TEXT_FONT_EDITER = "wsd.view.gui.text.font.";

	// 段落GUI菜单前缀
	public static final String PARAGRAPH_EDITER = "wsd.view.gui.paragraph.";

	// 对话框通用Message前缀
	public static final String DIALOG_COMMON = "wsd.view.gui.dialog.";

	// 插入表格对话框前缀
	public static final String INSERTTABLEDIALOG = "wsd.view.gui.inserttabledialog.";

	// 文件保存，打开相关
	public static final String FILE = "wsd.file.";
	
	public static final String FILE_ONLY_IN_ZIMOBANS = "filecanonlyinzimobans";
	
	public static final String LOAD_NO_DATASTRUCT_OR_DB_DATASTRUCT = "loadnodatastructordbdatastruct";

	public static final String CANT_HAVE_ZIMOBAN = "canthavezimoban";
	// 前缀静态变量区域 end

	// 数据结构相关消息项定义区域 begin

	// 数据类型，xml数据，dtd数据，schema数据，txt数据等
	public static final String DATATYPE = "type";

	// 导入数据结构信息项
	public static final String LOADSTRUCTURE = "loadstructure";

	// 高级导入信息项
	public static final String LOADOTHERSTRUCTURE = "loadotherstructure";

	// 从数据库导入信息项
	public static final String LOADSQLSTRUCTURE = "loadsqlstructure";

	// 编辑数据结构
	public static final String EDITSTRUCTURE = "editstructure";

	// 删除数据结构
	public static final String REMOVESTRUCTURE = "removestructure";

	// 数据结构文件不存在
	public static final String STRUCTURENOTFIND = "structurenotfind";

	// 数据结构数据格式不正确
	public static final String STRUCTUREDATAERROR = "structuredataerror";

	// 数据结构相关消息项定义区域 end

	// GUI界面通用菜单后缀区 begin
	// 单位
	public static final String MEASUREMENT = "measurement";

	// 数值：
	public static final String VALUE = "value";

	// 最大值
	public static final String MAXIMUM = "maximum";

	// 最小值
	public static final String MINIMUM = "minimum";

	// 最佳值
	public static final String OPTIMUM = "optimum";

	// 优先级
	public static final String PRECEDENCE = "precedence";

	// 强制
	public static final String FORCE = "force";

	// 厘米
	public static final String CM = "cm";

	// 磅
	public static final String PT = "pt";

	// 像素
	public static final String PX = "px";

	// 英尺
	public static final String IN = "in";

	// EM
	public static final String EM = "em";

	// GUI界面通用菜单后缀区 end

	// 文本GUI菜单后缀区 begin
	// 字体设置文本
	public static final String FONT_SET = "set";

	// 字体类型
	public static final String FONT_FAMILY = "family";

	// 字号
	public static final String FONT_SIZE = "size";

	/*** 具体的中文字号***开始 ***/
	// 大特号
	public static final String FONT_SIZE_BIG_SPECIAL = "size.big.special";

	// 特号
	public static final String FONT_SIZE_SPECIAL = "size.special";

	// 初号
	public static final String FONT_SIZE_BIG = "size.big";

	// 小初号
	public static final String FONT_SIZE_LITTLE_BIG = "size.big.little";

	// 大一号
	public static final String FONT_SIZE_BIG_FIRST = "size.first.big";

	// 一号
	public static final String FONT_SIZE_FIRST = "size.first";

	// 二号
	public static final String FONT_SIZE_SECOND = "size.second";

	// 小二号
	public static final String FONT_SIZE_SECOND_LITTLE = "size.second.little";

	// 三号
	public static final String FONT_SIZE_THIRD = "size.third";

	// 四号
	public static final String FONT_SIZE_FOURTH = "size.fourth";

	// 小四号
	public static final String FONT_SIZE_FOURTH_LITTLE = "size.fourth.little";

	// 五号
	public static final String FONT_SIZE_FIFTH = "size.fifth";

	// 小五号
	public static final String FONT_SIZE_FIFTH_LITTLE = "size.fifth.little";

	// 六号
	public static final String FONT_SIZE_SIXTH = "size.sixth";

	// 小六号
	public static final String FONT_SIZE_SIXTH_LITTLE = "size.sixth.little";

	// 七号
	public static final String FONT_SIZE_SEVENTH = "size.seventh";

	// 八号
	public static final String FONT_SIZE_EIGHTH = "size.eighth";

	/*** 具体的中文字号***结束 ***/

	// 字形
	public static final String FONT_STYLE = "style";

	// 文字颜色
	public static final String FONT_COLOR = "color";

	// 标准字形
	public static final String FONT_PLAIN = "plain";

	// 粗体
	public static final String FONT_BLOD = "blod";

	// 斜体
	public static final String FONT_ITALIC = "italic";

	// 颜色设置
	public static final String FONT_COLOR_SET = "color.set";

	// 字型设置
	public static final String FONT_STYLE_SET = "style.set";

	// 下划线
	public static final String FONT_UNDERLINE = "underline";

	// 删除线
	public static final String FONT_LINETHROUGH = "linethrough";

	// 上划线
	public static final String FONT_OVERLINE = "overline";

	// 文字背景设置
	public static final String FONT_BACKGROUND = "background";

	// 文字高亮显示
	public static final String FONT_HEIGHTLIGHT = "heightlight";

	// 文字底纹设置
	public static final String FONT_SHADING = "shading";

	// 文字位置设置
	public static final String FONT_POSITION_SET = "position.set";

	// 字符位置：
	public static final String FONT_POSITION = "position";

	// 字符缩放：
	public static final String FONT_ZOOM = "zoom";

	// 字符间距：
	public static final String FONT_SPACE = "space";

	// 正常
	public static final String FONT_POSITION_NORMAL = "position.normal";

	// 上标
	public static final String FONT_SUPERSCRIPT = "superscript";

	// 下标
	public static final String FONT_SUBSCRIPT = "subscript";

	// 标准
	public static final String FONT_STANDARD = "standard";

	// 加宽
	public static final String FONT_WIDEN = "widen";

	// 紧缩
	public static final String FONT_CONTRACTION = "contraction";

	// 文字链接
	public static final String FONT_LINK = "link";

	// 特殊文字
	public static final String FONT_SPECIAL = "special";

	// 全部文本属性
	public static final String FONT_ALL_PROPERTY = "all.property";

	// 文本GUI菜单后缀区 end

	// 段落GUI菜单后缀区 begin

	// 段落属性设置
	public static final String PARAGRAPH_SET = "set";

	// 缩进
	public static final String PARAGRAPH_INDENT = "indent";

	// 左缩进
	public static final String PARAGRAPH_START_INDENT = "start-indent";

	// 右缩进
	public static final String PARAGRAPH_END_INDENT = "end-indent";

	// 背景颜色设置
	public static final String PARAGRAPH_COLOR = "color";

	// 段间距
	public static final String PARAGRAPH_SPACE = "space";

	// 段前距
	public static final String PARAGRAPH_SPACE_BEFORE = "space-before";

	// 段后距
	public static final String PARAGRAPH_SPACE_AFTER = "space-after";

	// 段落GUI菜单后缀区 end

	// 插入表格对话框区 begin

	// 是否有表头
	public static final String HASHEADER = "hasheader";

	// 是否有表尾
	public static final String HASFOOTER = "hasfooter";

	// 对话框标题
	public static final String INSERTTABLEDIALOGTITLE = "inserttabledialogtitle";

	// 行数
	public static final String ROW = "row";

	// 列数
	public static final String COLUMN = "column";

	// 单元格宽
	public static final String COLUMNWIDTH = "columnwidth";

	// 行高
	public static final String ROWHEIGHT = "rowheight";

	// 行数不能为零错误消息项
	public static final String ROWISZEROERROR = "rowiszeroerror";

	// 列数不能为零消息项
	public static final String COLUMNISZEROERROR = "columniszeroerror";

	// 行高设置错误
	public static final String ROWHEIGHTERROR = "rowheighterror";

	// 列宽设置错误
	public static final String COLUMNWIDTHERROR = "columnwidtherror";

	// 拆分的行数不是要拆分单元格所跨行数的公约数
	public static final String ROWSPLITNOTDIVISOR = "rowsplitnotdivisor";

	// 拆分单元格对话框标题
	public static final String SPLITTABLECELLTITLE = "splittablecelltitle";

	// 插入表格对话框区 end

	// 对话框通用Message区 begin

	// 确认按钮消息项
	public static final String OK = "ok";

	// 取消按钮消息项
	public static final String CANCEL = "cancel";

	// 消息对话框title
	public static final String INFORMATIONTITLE = "informationtitle";

	// 图片选择对话框的后缀
	public static final String IMAGEDIALOGFILEDESCRIPT = "imagedialogfiledescript";

	// 对话框通用Message区 end

	// 文件保存，打开相关区 begin

	// wsd文件消息项
	public static final String WSDTYPE = "wsdtype";

	public static final String ALLWSDFILETYPE = "allwsdfiletype";

	public static final String WSDTTYPE = "wsdttype";

	// 文件已存在消息项
	public static final String FILEEXISTED = "fileexisted";

	// 文件不可写消息项
	public static final String FILECANOTWRITE = "filecanotwrite";

	// 文件保存不成功消息项
	public static final String FILESAVEFAILED = "filesavefailed";

	// 当前文档没有保存消息项
	public static final String DOCUMENTNOTSAVED = "documentnotsaved";

	// 是否替换提示信息
	public static final String WHETHERREPLACEFILE = "whetherreplacefile";

	// 文件不存提示信息
	public static final String FILENOTEXISTED = "filenotexisted";

	// 文件不存提示信息
	public static final String FILECANNOTREAD = "filecannotread";

	// 文件打开不成功提示信息
	public static final String FILECANNOTOPEN = "filecannotopen";

	// 文件保存，打开相关区 end
	// 超出一个章节提示对话框标题
	public static final String MORETHANONEPS = "morethanoneps";

	// 超出一个章节提示信息
	public static final String MORETHANONEPAGESEQUENCE = "morethanonepagesequence";

	// 重新导入sql文件
	public static final String RELOADSQL = "reloadsql";

	// 重新导入sql文件提示信息
	public static final String MAKETRUERELOADSQL = "maketruereloadsql";

	// 设置数据来源

	public static final String SETDATASOURCE = "setdatasource";

	// 连接失败，请检查后重新测试连接
	public static final String CONNECTIONAGAIN = "connectionagain";

	// 设置参数
	public static final String SETPARAM = "setparam";

	// 数据库类型：
	public static final String DATABASETYPE = "databasetype";

	// 数据源名称：
	public static final String DATABASENAME = "databasename";

	// 连接URL：
	public static final String CONNECTIONURL = "connectionurl";

	// 登录名：
	public static final String LOGINNAME = "loginname";

	// 密码：
	public static final String LOGINPASSWORD = "loginpassword";

	// 驱动类名：
	public static final String DRIVERCLASS = "driverclass";

	// 数据源设置
	public static final String DATASOURCESET = "datasourceset";

	// 连接成功！
	public static final String CONNECTIONCHENGGONG = "connectionchenggong";

	// 驱动类不存在
	public static final String DRIVERNOTFOUND = "drivernotfound";

	// 导出
	public static final String LOADOUT = "loadout";

	// 导入
	public static final String LOADIN = "loadin";

	// SQL语句设置
	public static final String SQLSET = "sqlset";

	// sql语句为空！
	public static final String SQLNULL = "sqlnull";

	// 当前sql语句不包含参数！
	public static final String SQLNOPARAM = "sqlnoparam";

	// 参数名
	public static final String PARAMNAME = "paramname";

	// 参数类型
	public static final String PARAMTYPE = "paramtype";

	// 参数值
	public static final String PARAMVALUE = "paramvalue";

	// 数据源名称为空
	public static final String DATABASENAMEISNULL = "databasenameisnull";

	// 数据源已经存在
	public static final String DATABASENAMEIS = "databasenameis";

	// 指定名称的数据源已经存在，是否覆盖？
	public static final String REWRITEDATABASE = "rewritedatabase";
}
