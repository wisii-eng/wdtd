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

package com.wisii.wisedoc.io.xsl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.view.action.SaveFileAction;

public class DomParse
{

	Element attributes;

	Element pagesequences;

	int attributesmaxnumber = 0;

	int flg = 0;

	Document doc;

	public DomParse(InputStream instream)
	{
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder domBuilder = domfac.newDocumentBuilder();
			doc = domBuilder.parse(instream);
			Element root = doc.getDocumentElement();
			String version = root.getAttribute("version");
			if (isNeedChange(version))
			{
				setElementAttrAndPagesq(root);
				setAttributesMaxNumber();
				dealDocument();
				clearDocument();
			}
		} catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		} catch (SAXException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
		}
	}

	private boolean isNeedChange(String version)
	{
		if (version == null || version.isEmpty())
		{
			return false;
		}
		String[] verses = version.split("\\.");
		if (verses.length != 4)
		{
			return false;
		}
		if ("0".equals(verses[1]))
		{
			return true;
		} else if ("1".equals(verses[1]))
		{
			if ("00".equals(verses[2]))
			{
				try
				{
					Integer lastver = Integer.parseInt(verses[3]);
					if (lastver < 8)
					{
						return true;
					} else
					{
						return false;
					}
				} catch (Exception e)
				{
					return false;
				}
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	/**
	 * 
	 * 获得String
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getString()
	{
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transFormer;
		try
		{
			transFormer = transFactory.newTransformer();
			// 设置输出结果
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();

			// 设置输入源
			StreamResult xmlResult = new StreamResult(writer);

			// 输出xml文件
			transFormer.transform(domSource, xmlResult);
			return writer.toString();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 
	 * 获得document
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public Document getDocument()
	{
		return doc;
	}

	/**
	 * 
	 * 获得文件
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public File getFile()
	{

		TransformerFactory transFactory = TransformerFactory.newInstance();
		try
		{
			File file = File.createTempFile("wsd", ".wsd", new File("c:\\nba"));
			OutputStream os = new FileOutputStream(file);
			Transformer transFormer = transFactory.newTransformer();
			// 设置输出结果
			DOMSource domSource = new DOMSource(doc);

			// 设置输入源
			StreamResult xmlResult = new StreamResult(os);

			// 输出xml文件
			transFormer.transform(domSource, xmlResult);
			return file;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * 清除多余的sc
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public void clearDocument()
	{
		Element root = doc.getDocumentElement();
		NodeList childrens = root.getChildNodes();
		if (childrens != null)
		{
			List<Element> attributelist = new ArrayList<Element>();
			List<String> masterlist = new ArrayList<String>();
			List<Element> staticcontentlist = new ArrayList<Element>();
			for (int i = 0; i < childrens.getLength(); i++)
			{
				Node node = childrens.item(i);
				if (node instanceof Element)
				{
					Element element = (Element) node;
					String elename = element.getTagName();
					if ("attribute".equals(elename))
					{
						String flowname = element.getAttribute("flow-name");
						if (flowname != null && !flowname.equals(""))
						{
							attributelist.add(element);
						}
					} else if ("simplepagemaster".equals(elename))
					{
						masterlist.add(element.getAttribute("master-name"));
					} else if ("staticcontent".equals(elename))
					{
						staticcontentlist.add(element);
					} else
					{
						clearElement(element, attributelist, masterlist,
								staticcontentlist);
					}
				}
			}
			for (Element current : attributelist)
			{
				String name = current.getAttribute("flow-name");
				if (!"xsl-region-body".equals(name))
				{
					name = name.replace("before", "");
					name = name.replace("after", "");
					name = name.replace("start", "");
					name = name.replace("end", "");
				}
				if (name != null && !"".equals(name)
						&& !"xsl-region-body".equals(name)
						&& !masterlist.contains(name))
				{
					String id = current.getAttribute("id");
					for (Element currentsc : staticcontentlist)
					{
						String refid = currentsc.getAttribute("attrefid");
						if (id.equals(refid))
						{
							currentsc.getParentNode().removeChild(currentsc);
						}
					}
					current.getParentNode().removeChild(current);
				}
			}
		}
	}

	/**
	 * 
	 * 收集布局、attribute和sc
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public void clearElement(Element ele, List<Element> attributelist,
			List<String> masterlist, List<Element> staticcontentlist)
	{
		if (ele != null)
		{
			NodeList childrens = ele.getChildNodes();
			if (childrens != null)
			{
				for (int i = 0; i < childrens.getLength(); i++)
				{
					Node node = childrens.item(i);
					if (node instanceof Element)
					{
						Element element = (Element) node;
						String elename = element.getTagName();
						if ("attribute".equals(elename))
						{
							attributelist.add(element);
						} else if ("simplepagemaster".equals(elename))
						{
							masterlist.add(element.getAttribute("master-name"));
						} else if ("staticcontent".equals(elename))
						{
							staticcontentlist.add(element);
						} else
						{
							clearElement(element, attributelist, masterlist,
									staticcontentlist);
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 处理文档
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public void dealDocument()
	{
		if (pagesequences != null)
		{
			NodeList childrens = pagesequences.getChildNodes();
			if (childrens != null)
			{
				for (int i = 0; i < childrens.getLength(); i++)
				{
					Node node = childrens.item(i);
					if (node instanceof Element)
					{
						Element pagesequence = (Element) node;
						dealPageSequence(pagesequence);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 处理单个章节
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public void dealPageSequence(Element ps)
	{
		if (ps != null)
		{
			List<Element> simplepagemaster = new ArrayList<Element>();
			List<Element> screfs = new ArrayList<Element>();
			List<Element> staticcontent = new ArrayList<Element>();
			List<String> staticcontents = new ArrayList<String>();
			PretreatmentPageSequence(ps, simplepagemaster, staticcontents,
					screfs, staticcontent);
			if (simplepagemaster != null)
			{
				for (int i = 0; i < simplepagemaster.size(); i++)
				{
					Element currentspm = simplepagemaster.get(i);
					String vitrualname = currentspm.getAttribute("master-name");
					String mastername = "SPM" + flg + flg;
					if (vitrualname == null || "".equals(vitrualname))
					{
						vitrualname = "SPM" + flg + flg;
					}
					flg = flg + 1;
					currentspm.setAttribute("virtual-master-name", vitrualname);
					currentspm.setAttribute("master-name", mastername);

					NodeList childrens = currentspm.getChildNodes();
					Element regionbody = null;
					if (childrens != null)
					{
						Element before = null;
						Element after = null;
						Element start = null;
						Element end = null;
						for (int j = 0; j < childrens.getLength(); j++)
						{
							Node node = childrens.item(j);
							if (node instanceof Element)
							{
								Element region = (Element) node;
								String name = region.getTagName();
								String regionname = region
										.getAttribute("region-name");
								if ("regionbefore".equals(name))
								{
									before = region;
									String realregionname = mastername
											+ "before";
									Element add = setStaticContent(regionname,
											realregionname, screfs,
											staticcontents, staticcontent);
									if (add != null)
									{
										ps.appendChild(add);
									}
									region.setAttribute("region-name",
											realregionname);
								} else if ("regionafter".equals(name))
								{
									after = region;
									String realregionname = mastername
											+ "after";
									Element add = setStaticContent(regionname,
											realregionname, screfs,
											staticcontents, staticcontent);
									if (add != null)
									{
										ps.appendChild(add);
									}
									region.setAttribute("region-name",
											realregionname);
								} else if ("regionstart".equals(name))
								{
									start = region;
									String realregionname = mastername
											+ "start";
									Element add = setStaticContent(regionname,
											realregionname, screfs,
											staticcontents, staticcontent);
									if (add != null)
									{
										ps.appendChild(add);
									}
									region.setAttribute("region-name",
											realregionname);
								} else if ("regionend".equals(name))
								{
									end = region;
									String realregionname = mastername + "end";
									Element add = setStaticContent(regionname,
											realregionname, screfs,
											staticcontents, staticcontent);
									if (add != null)
									{
										ps.appendChild(add);
									}
									region.setAttribute("region-name",
											realregionname);
								} else if ("regionbody".equals(name))
								{
									regionbody = region;
								}
							}
						}
						int bodytop = getMarginValue(1, regionbody);
						int bodybottom = getMarginValue(2, regionbody);
						int bodyleft = getMarginValue(3, regionbody);
						int bodyright = getMarginValue(4, regionbody);
						boolean havebefore = before != null;
						boolean haveafter = after != null;
						boolean havestart = start != null;
						boolean haveend = end != null;
						if (havebefore)
						{
							if (!havestart)
							{
								before.setAttribute("precedence", "false");
							}
						}
						if (haveafter)
						{
							if (!havestart)
							{
								after.setAttribute("precedence", "false");
							}
						}

						if (havestart)
						{
							if (!havebefore)
							{
								start.setAttribute("precedence", "true");
							}
						}

						if (haveend)
						{
							if (!havebefore)
							{
								end.setAttribute("precedence", "true");
							}
						}
						if (before == null)
						{
							int topvalue = getMarginValue(1, currentspm);
							String realtop = null;
							if (topvalue < 14173)
							{
								realtop = "0mm";
							} else if (topvalue < 29480)
							{
								realtop = "5mm";
								FixedLength newtop = new FixedLength(
										topvalue - 14173);
								String realstr = getLengthString(newtop);
								currentspm.setAttribute("margin-top", realstr);
								currentspm.setAttribute("space-before.min",
										realstr);
								currentspm.setAttribute("space-before.optimun",
										realstr);
								currentspm.setAttribute("space-before.max",
										realstr);

								FixedLength newbodytop = new FixedLength(
										bodytop + 14173);
								String realbodystr = getLengthString(newbodytop);
								regionbody.setAttribute("margin-top",
										realbodystr);
								regionbody.setAttribute("space-before.min",
										realbodystr);
								regionbody.setAttribute("space-before.optimun",
										realbodystr);
								regionbody.setAttribute("space-before.max",
										realbodystr);
							} else
							{
								FixedLength newtop = new FixedLength(
										topvalue - 29480);
								String realstr = getLengthString(newtop);
								currentspm.setAttribute("margin-top", realstr);
								currentspm.setAttribute("space-before.min",
										realstr);
								currentspm.setAttribute("space-before.optimun",
										realstr);
								currentspm.setAttribute("space-before.max",
										realstr);

								FixedLength newbodytop = new FixedLength(
										bodytop + 29480);
								String realbodystr = getLengthString(newbodytop);
								regionbody.setAttribute("margin-top",
										realbodystr);
								regionbody.setAttribute("space-before.min",
										realbodystr);
								regionbody.setAttribute("space-before.optimun",
										realbodystr);
								regionbody.setAttribute("space-before.max",
										realbodystr);
							}
							String regionname = mastername + "before";
							if (realtop == null)
							{
								if (havestart)
								{
									before = getDefaultRegionBefore(regionname);
								} else
								{
									before = getNomalRegionBefore(regionname,
											"10.4mm", "false");
								}
							} else
							{
								if (havestart)
								{
									before = getNomalRegionBefore(regionname,
											realtop, "true");
								} else
								{
									before = getNomalRegionBefore(regionname,
											realtop, "false");
								}
							}
							Element scadd = StaticContent(attributesmaxnumber);

							Element attadd = Attribute(regionname,
									attributesmaxnumber);
							attributesmaxnumber++;
							ps.appendChild(scadd);
							currentspm.appendChild(before);

							attributes.appendChild(attadd);
						}
						if (after == null)
						{
							int bottomvalue = getMarginValue(2, currentspm);
							String realbottom = null;
							if (bottomvalue < 14173)
							{
								realbottom = "0mm";
							} else if (bottomvalue < 22393)
							{
								realbottom = "5mm";
								FixedLength newtop = new FixedLength(
										bottomvalue - 14173);
								String realstr = getLengthString(newtop);
								currentspm.setAttribute("margin-bottom",
										realstr);
								currentspm.setAttribute("space-after.min",
										realstr);
								currentspm.setAttribute("space-after.optimun",
										realstr);
								currentspm.setAttribute("space-after.max",
										realstr);

								FixedLength newbodytop = new FixedLength(
										bodybottom + 14173);
								String realbodystr = getLengthString(newbodytop);
								regionbody.setAttribute("margin-bottom",
										realbodystr);
								regionbody.setAttribute("space-after.min",
										realbodystr);
								regionbody.setAttribute("space-after.optimun",
										realbodystr);
								regionbody.setAttribute("space-after.max",
										realbodystr);
							} else
							{
								FixedLength newtop = new FixedLength(
										bottomvalue - 22393);
								String realstr = getLengthString(newtop);
								currentspm.setAttribute("margin-bottom",
										realstr);
								currentspm.setAttribute("space-after.min",
										realstr);
								currentspm.setAttribute("space-after.optimun",
										realstr);
								currentspm.setAttribute("space-after.max",
										realstr);

								FixedLength newbodytop = new FixedLength(
										bodybottom + 22393);
								String realbodystr = getLengthString(newbodytop);
								regionbody.setAttribute("margin-bottom",
										realbodystr);
								regionbody.setAttribute("space-after.min",
										realbodystr);
								regionbody.setAttribute("space-after.optimun",
										realbodystr);
								regionbody.setAttribute("space-after.max",
										realbodystr);
							}
							String regionname = mastername + "after";
							if (realbottom == null)
							{
								if (havestart)
								{
									after = getDefaultRegionAfter(regionname);
								} else
								{
									after = getNomalRegionAfter(regionname,
											"7.9mm", "false");
								}
							} else
							{
								if (havestart)
								{
									after = getNomalRegionAfter(regionname,
											realbottom, "true");
								} else
								{
									after = getNomalRegionAfter(regionname,
											realbottom, "false");
								}
							}

							Element scadd = StaticContent(attributesmaxnumber);

							Element attadd = Attribute(regionname,
									attributesmaxnumber);
							attributesmaxnumber++;
							ps.appendChild(scadd);
							currentspm.appendChild(after);
							attributes.appendChild(attadd);

						}
						if (start == null)
						{
							int leftvalue = getMarginValue(3, currentspm);
							String realleft = null;
							if (leftvalue < 14173)
							{
								realleft = "0mm";
							} else if (leftvalue < 28346)
							{
								realleft = "5mm";
								FixedLength newtop = new FixedLength(
										leftvalue - 14173);
								String realstr = getLengthString(newtop);
								currentspm.setAttribute("margin-left", realstr);

								FixedLength newbodytop = new FixedLength(
										bodyleft + 14173);
								String realbodystr = getLengthString(newbodytop);
								regionbody.setAttribute("margin-left",
										realbodystr);
							} else
							{
								FixedLength newtop = new FixedLength(
										leftvalue - 28346);
								String realstr = getLengthString(newtop);
								currentspm.setAttribute("margin-left", realstr);

								FixedLength newbodytop = new FixedLength(
										bodyleft + 28346);
								String realbodystr = getLengthString(newbodytop);
								regionbody.setAttribute("margin-left",
										realbodystr);
							}
							String regionname = mastername + "start";
							if (realleft == null)
							{
								start = getDefaultRegionStart(regionname);
							} else
							{
								start = getNomalRegionStart(regionname,
										realleft);
							}

							Element scadd = StaticContent(attributesmaxnumber);

							Element attadd = Attribute(regionname,
									attributesmaxnumber);
							attributesmaxnumber++;
							ps.appendChild(scadd);
							currentspm.appendChild(start);
							attributes.appendChild(attadd);
						}
						if (end == null)
						{
							int rightvalue = getMarginValue(4, currentspm);
							String realright = null;
							if (rightvalue < 14173)
							{
								realright = "0mm";
							} else if (rightvalue < 29480)
							{
								realright = "5mm";
								FixedLength newtop = new FixedLength(
										rightvalue - 14173);
								String realstr = getLengthString(newtop);
								currentspm
										.setAttribute("margin-right", realstr);

								FixedLength newbodytop = new FixedLength(
										bodyright + 14173);
								String realbodystr = getLengthString(newbodytop);
								regionbody.setAttribute("margin-right",
										realbodystr);
							} else
							{
								FixedLength newtop = new FixedLength(
										rightvalue - 28346);
								String realstr = getLengthString(newtop);
								currentspm
										.setAttribute("margin-right", realstr);

								FixedLength newbodytop = new FixedLength(
										bodyright + 28346);
								String realbodystr = getLengthString(newbodytop);
								regionbody.setAttribute("margin-right",
										realbodystr);
							}
							String regionname = mastername + "end";
							if (realright == null)
							{
								end = getDefaultRegionEnd(regionname);
							} else
							{
								end = getNomalRegionEnd(regionname, realright);
							}

							Element scadd = StaticContent(attributesmaxnumber);

							Element attadd = Attribute(regionname,
									attributesmaxnumber);
							attributesmaxnumber++;
							ps.appendChild(scadd);
							currentspm.appendChild(end);
							attributes.appendChild(attadd);
						}
					}
				}
			}

		}
	}

	/**
	 * 
	 * 获得传入类型的margin的值，先取分别的margion-？的值，为0再取共同的margin，
	 * 
	 * 还为0再取space（页眉、页脚）
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public int getMarginValue(int type, Element master)
	{
		switch (type)
		{
			case 1:
			{
				String margin = master.getAttribute("margin-top");
				FixedLength length = generateLengthvalue(margin);
				if (length.getValue() <= 0)
				{
					length = generateLengthvalue(master.getAttribute("margin"));
				}
				if (length.getValue() <= 0)
				{
					length = generateLengthvalue(master
							.getAttribute("space-before.optimun"));
				}
				return length.getValue();
			}
			case 2:
			{
				String margin = master.getAttribute("margin-bottom");
				FixedLength length = generateLengthvalue(margin);
				if (length.getValue() <= 0)
				{
					length = generateLengthvalue(master.getAttribute("margin"));
				}
				if (length.getValue() <= 0)
				{
					length = generateLengthvalue(master
							.getAttribute("space-after.optimun"));
				}
				return length.getValue();
			}
			case 3:
			{
				String margin = master.getAttribute("margin-left");
				FixedLength length = generateLengthvalue(margin);
				if (length.getValue() <= 0)
				{
					length = generateLengthvalue(master.getAttribute("margin"));
				}
				return length.getValue();
			}
			case 4:
			{
				String margin = master.getAttribute("margin-right");
				FixedLength length = generateLengthvalue(margin);
				if (length.getValue() <= 0)
				{
					length = generateLengthvalue(master.getAttribute("margin"));
				}
				return length.getValue();
			}
		}

		return 0;
	}

	public String getLengthString(FixedLength length)
	{
		String result = length.toString();
		if (result != null)
		{
			result = result.replaceAll(" ", "");
			if (result.contains("分米"))
			{
				result = result.replaceAll("分米", "dm");
			} else if (result.contains("厘米"))
			{
				result = result.replaceAll("厘米", "cm");
			} else if (result.contains("毫米"))
			{
				result = result.replaceAll("毫米", "mm");
			} else if (result.contains("米"))
			{
				result = result.replaceAll("米", "dm");
			} else if (result.contains("豪磅"))
			{
				result = result.replaceAll("豪磅", "mpt");
			} else if (result.contains("磅"))
			{
				result = result.replaceAll("磅", "pt");
			}
		}
		return result;
	}

	public Element setStaticContent(String oldname, String newname,
			List<Element> screfs, List<String> staticcontents,
			List<Element> staticcontent)
	{
		Element sc = null;
		if (staticcontents.contains(oldname))
		{
			sc = getAttribute(oldname, screfs);
		}
		Element attradd = Attribute(newname, attributesmaxnumber);
		attributesmaxnumber++;
		attributes.appendChild(attradd);
		if (sc == null)
		{
			Element scadd = StaticContent(attributesmaxnumber);
			return scadd;
		} else
		{
			String id = sc.getAttribute("id");
			for (Element current : staticcontent)
			{
				String ref = current.getAttribute("attrefid");

				if (id.equals(ref))
				{
					current.setAttribute("attrefid", (attributesmaxnumber - 1)
							+ "");
					break;
				}
			}
			// sc.setAttribute("flow-name", newname);
		}
		return null;
	}

	/**
	 * 
	 * 传入名称，从链表中取出同名的sc
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public Element getAttribute(String name, List<Element> screfs)
	{
		if (screfs != null)
		{
			for (Element current : screfs)
			{
				String nameattr = current.getAttribute("flow-name");
				if (name.equals(nameattr))
				{
					return current;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * 预处理章节，收集章节的所有布局和sc流
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public void PretreatmentPageSequence(Element ele,
			List<Element> simplepagemaster, List<String> staticcontents,
			List<Element> screfs, List<Element> staticcontent)
	{
		if (ele != null)
		{
			NodeList childrens = ele.getChildNodes();
			if (childrens != null)
			{
				for (int i = 0; i < childrens.getLength(); i++)
				{
					Node node = childrens.item(i);
					if (node instanceof Element)
					{
						Element element = (Element) node;
						String elename = element.getTagName();
						if ("staticcontent".equals(elename))
						{
							String attrefid = element.getAttribute("attrefid");
							String scname = getFlowName(attrefid, screfs);
							if (scname != null && !scname.equals(""))
							{
								staticcontents.add(scname);
								staticcontent.add(element);
							}
						} else if ("simplepagemaster".equals(elename))
						{
							simplepagemaster.add(element);
						} else
						{
							PretreatmentPageSequence(element, simplepagemaster,
									staticcontents, screfs, staticcontent);
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 获取指定id的flow-name属性值
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getFlowName(String id, List<Element> screfs)
	{
		if (id != null)
		{
			if (attributes != null)
			{
				NodeList childrens = attributes.getChildNodes();
				if (childrens != null)
				{
					for (int i = 0; i < childrens.getLength(); i++)
					{
						Node node = childrens.item(i);
						if (node instanceof Element)
						{
							Element attribute = (Element) node;
							String attrid = attribute.getAttribute("id");
							if (attrid != null && attrid.equals(id))
							{
								screfs.add(attribute);
								return attribute.getAttribute("flow-name");
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * 设置属性的id属性的最大值
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public void setAttributesMaxNumber()
	{
		if (attributes != null)
		{
			NodeList childrens = attributes.getChildNodes();
			if (childrens != null)
			{
				for (int i = 0; i < childrens.getLength(); i++)
				{
					Node node = childrens.item(i);
					if (node instanceof Element)
					{
						Element attribute = (Element) node;
						String id = attribute.getAttribute("id");
						if (id != null && !id.equals(""))
						{
							int currentid;
							try
							{
								currentid = Integer.parseInt(id.trim());
							} catch (Exception e)
							{
								currentid = 0;
							}
							if (currentid > attributesmaxnumber)
							{
								attributesmaxnumber = currentid;
							}
						}
					}
				}
			}
		}
		attributesmaxnumber++;
	}

	/**
	 * 
	 * 设置章节和属性的根节点
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */

	public void setElementAttrAndPagesq(Element root)
	{
		NodeList childrens = root.getChildNodes();
		if (childrens != null)
		{
			for (int i = 0; i < childrens.getLength(); i++)
			{
				Node node = childrens.item(i);
				if (node instanceof Element)
				{
					Element element = (Element) node;
					String elename = element.getTagName();
					if ("attributes".equals(elename))
					{
						if (attributes == null)
						{
							attributes = element;
						}
					} else if ("pagesequences".equals(elename))
					{
						if (pagesequences == null)
						{
							pagesequences = element;
						}
					} else
					{
						if (!(attributes != null && pagesequences != null))
						{
							setElementAttrAndPagesq(element);
						}
					}
				}
			}
		}
	}

	public FixedLength generateLengthvalue(String value)
	{
		FixedLength length = new FixedLength(0);
		if (value != null && !value.trim().equals(""))
		{
			String slength = value;
			int precion = -1;
			int indexduhao = value.indexOf(",");
			if (indexduhao > 0)
			{
				slength = value.substring(0, indexduhao);
				if (indexduhao < value.length())
				{
					String precions = value.substring(indexduhao + 1);
					precion = Integer.parseInt(precions);
				}
			}
			slength = slength.trim();
			String lengths = "";
			String units = "";
			int size = slength.length();
			int index = -1;
			for (int i = 0; i < size; i++)
			{
				// 取出其中的长度数字值
				char c = slength.charAt(i);
				// 处理负数
				if (c == '-' && i == 0)
				{

				}
				// 找到第一个不是数字的字符
				else if ((c < '0' || c > '9') && c != '.')
				{
					index = i;
					break;
				}
				lengths = lengths + c;
			}
			try
			{
				units = slength.substring(index).trim();
			} catch (IndexOutOfBoundsException e)
			{
				LogUtil.debug("无有效单位,将取默认单位pt", e);
				units = "";
			}
			if (!lengths.equals(""))
			{
				try
				{
					double len = Double.parseDouble(lengths);
					length = new FixedLength(len, units, precion);
				} catch (Exception e)
				{
					LogUtil.debug(e);
				}
			}
		}
		return length;
	}

	public static String read()
	{
		JFileChooser chooser = DialogSupport.getDialog(new WiseDocFileFilter(
				MessageResource.getMessage(MessageConstants.FILE
						+ MessageConstants.WSDTYPE), SaveFileAction.WSD));
		int dialogResult = chooser.showOpenDialog(SystemManager.getMainframe());
		if (dialogResult == JFileChooser.APPROVE_OPTION)
		{
			File file = chooser.getSelectedFile();
			if (file.exists())
			{
				if (!file.canRead())
				{
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), MessageResource
							.getMessage(MessageConstants.FILE
									+ MessageConstants.FILECANNOTREAD));
				}
			} else
			{
				WiseDocOptionPane.showMessageDialog(SystemManager
						.getMainframe(), MessageResource
						.getMessage(MessageConstants.FILE
								+ MessageConstants.FILENOTEXISTED));
			}
			return file.getAbsolutePath();
		}
		return "";

	}

	public Element getDefaultRegionBefore(String name)
	{
		return DefaultRegion("regionbefore", name, "10.4mm", "true");
	}

	public Element getNomalRegionBefore(String name, String extent,
			String precedence)
	{
		return DefaultRegion("regionbefore", name, extent, precedence);
	}

	public Element getDefaultRegionAfter(String name)
	{
		return DefaultRegion("regionafter", name, "7.9mm", "true");
	}

	public Element getNomalRegionAfter(String name, String extent,
			String precedence)
	{
		return DefaultRegion("regionafter", name, extent, precedence);
	}

	public Element getDefaultRegionStart(String name)
	{
		return DefaultRegion("regionstart", name, "10mm", null);
	}

	public Element getNomalRegionStart(String name, String extent)
	{
		return DefaultRegion("regionstart", name, extent, null);
	}

	public Element getDefaultRegionEnd(String name)
	{
		return DefaultRegion("regionend", name, "10mm", null);
	}

	public Element getNomalRegionEnd(String name, String extent)
	{
		return DefaultRegion("regionend", name, extent, null);
	}

	public static void main(String[] args)
	{
		String name = read();
		try
		{
			DomParse a = new DomParse(new FileInputStream(name));
			a.getFile();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Element DefaultRegion(String refiontype, String name, String extent,
			String precedence)
	{
		Element result = doc.createElement(refiontype);
		result.setAttribute("region-name", name);
		result.setAttribute("extent", extent);
		if (precedence != null)
		{
			result.setAttribute("precedence", precedence);
		}
		result.setAttribute("background-attachment", "0");
		result.setAttribute("background-repeat", "0");
		result.setAttribute("display-align", "before");
		result.setAttribute("overflow", "auto");
		result.setAttribute("reference-orientation", "0");
		result.setAttribute("writing-mode", "lr-tb");
		return result;
	}

	public Element Attribute(String name, int id)
	{
		Element result = doc.createElement("attribute");
		result.setAttribute("flow-name", name);
		result.setAttribute("id", id + "");
		return result;
	}

	public Element StaticContent(int id)
	{
		Element result = doc.createElement("staticcontent");
		result.setAttribute("attrefid", id + "");
		return result;
	}
}
