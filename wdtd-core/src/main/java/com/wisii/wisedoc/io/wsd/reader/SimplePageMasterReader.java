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
 * @SimplePageMasterReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-10
 */
public class SimplePageMasterReader extends AbstractAttElementReader {
	protected boolean isfirstnode = true;
	private String ROOTNAME = "simplepagemaster";
	private String BEFORENAME = "regionbefore";
	private String AFTERNAME = "regionafter";
	private String STRATNAME = "regionstart";
	private String ENDNAME = "regionend";
	private String BODYNAME = "regionbody";
	protected RegionReader SRR = new RegionReader();
	protected Map<String, AbstractHandler> _handlermap;
	protected AbstractHandler _currenthandler;
	// margin属性
	private CommonMarginBlock _commonMarginBlock;

	// 页高
	private Length _pageHeight;

	// 页宽
	private Length _pageWidth;

	// 旋转属性
	private int _referenceOrientation;

	// 书写模式
	private int _writingMode = Constants.EN_LR_TB;

	// 区域定义
	private Map<Integer, Region> _regions;

	private String _mediaUsage;
	private String _mastername;
	private String _virtualmastername;
	private SimplePageMaster simplepagemaster;

	SimplePageMasterReader() {
		_handlermap = new HashMap<String, AbstractHandler>();
		_handlermap.put(BEFORENAME, SRR);
		_handlermap.put(AFTERNAME, SRR);
		_handlermap.put(STRATNAME, SRR);
		_handlermap.put(ENDNAME, SRR);
		_handlermap.put(BODYNAME, SRR);
		init();
	}

	 void init()
	{
		isfirstnode = true;
		_currenthandler = null;
		_regions = new HashMap<Integer, Region>();
		simplepagemaster = null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#startElement(java.lang
	 * .String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException {
		if (isfirstnode) {
			if (!ROOTNAME.equals(qname)) {
				LogUtil.debug("该节点必须是simplepagemaster，而实际节点是：" + qname);
				throw new SAXException("该节点必须是simplepagemaster，而实际节点是：" + qname);
			}
			simplepagemaster = null;
			com.wisii.wisedoc.document.attribute.Attributes attrs = createattributes(atts);
			_pageHeight = (Length) attrs.getAttribute(Constants.PR_PAGE_HEIGHT);
			_pageWidth = (Length) attrs.getAttribute(Constants.PR_PAGE_WIDTH);
			Integer referenceOrientationobject = (Integer) attrs
					.getAttribute(Constants.PR_REFERENCE_ORIENTATION);
			_referenceOrientation = 0;
			if (referenceOrientationobject != null) {
				_referenceOrientation = referenceOrientationobject;
			}
			Integer writingModeobject = (Integer) attrs
					.getAttribute(Constants.PR_WRITING_MODE);
			_writingMode = Constants.EN_LR_TB;
			if (writingModeobject != null) {
				_writingMode = writingModeobject;
			}
			_mediaUsage = (String) attrs.getAttribute(Constants.PR_MEDIA_USAGE);
			_mastername = (String) attrs.getAttribute(Constants.PR_MASTER_NAME);
			_virtualmastername = (String) attrs.getAttribute(Constants.PR_VIRTUAL_MASTER_NAME);
			_commonMarginBlock = new CommonMarginBlock((Length) attrs
					.getAttribute(Constants.PR_MARGIN_TOP), (Length) attrs
					.getAttribute(Constants.PR_MARGIN_BOTTOM), (Length) attrs
					.getAttribute(Constants.PR_MARGIN_LEFT), (Length) attrs
					.getAttribute(Constants.PR_MARGIN_RIGHT),
					(SpaceProperty) attrs
							.getAttribute(Constants.PR_SPACE_BEFORE),
					(SpaceProperty) attrs
							.getAttribute(Constants.PR_SPACE_AFTER),
					(Length) attrs.getAttribute(Constants.PR_START_INDENT),
					(Length) attrs.getAttribute(Constants.PR_END_INDENT));
			isfirstnode = false;
		}
		AbstractHandler handler = _handlermap.get(qname);
		if (handler != null) {
			_currenthandler = handler;
		}
		if (_currenthandler != null) {
			_currenthandler.startElement(uri, localname, qname, atts);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#endElement(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localname, String qname)
			throws SAXException {
		if (ROOTNAME.equals(qname)) {
			isfirstnode = true;
			_currenthandler = null;
			if (_regions != null && !_regions.isEmpty()) {
				simplepagemaster = new SimplePageMaster(
						_commonMarginBlock, _pageHeight, _pageWidth,
						_referenceOrientation, _writingMode, _regions,
						_mediaUsage);
				simplepagemaster.setMastername(_mastername);
				simplepagemaster.setVirtualMastername(_virtualmastername);
				return;
			} else {
				throw new SAXException("SimplePageMaster 节点解析出错，节点信息不完全");
			}

		}
		if(_currenthandler != null)
		{
			_currenthandler.endElement(uri, localname, qname);
	
		if (BEFORENAME.equals(qname)) {
			Region region = (Region) _currenthandler.getObject();
			if (region != null) {
				_regions.put(Constants.FO_REGION_BEFORE, region);
			}
			_currenthandler = null;
		} else if (AFTERNAME.equals(qname)) {
			Region region = (Region) _currenthandler.getObject();
			if (region != null) {
				_regions.put(Constants.FO_REGION_AFTER, region);
			}
			_currenthandler = null;
		} else if (STRATNAME.equals(qname)) {
			Region region = (Region) _currenthandler.getObject();
			if (region != null) {
				_regions.put(Constants.FO_REGION_START, region);
			}
			_currenthandler = null;
		} else if (ENDNAME.equals(qname)) {
			Region region = (Region) _currenthandler.getObject();
			if (region != null) {
				_regions.put(Constants.FO_REGION_END, region);
			}
			_currenthandler = null;
		} else if (BODYNAME.equals(qname)) {
			Region region = (Region) _currenthandler.getObject();
			if (region != null) {
				_regions.put(Constants.FO_REGION_BODY, region);
			}
			_currenthandler = null;
		} else {
			if (_currenthandler != null) {
				_currenthandler.endElement(uri, localname, qname);
			}
		}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public SimplePageMaster getObject() {

		return simplepagemaster;
	}
}
