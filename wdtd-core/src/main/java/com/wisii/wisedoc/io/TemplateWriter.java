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
 * @TemplateWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.io;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;
import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOUtil.FileKind;
import com.wisii.wisedoc.io.wsd.WSDWriter;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.WisedocFrame;
/**
 * 类功能描述：用于保存模板文件【zip】，在zip文件中包含一 个wsd文件和一个图标文件。 
 * 作者：李晓光 创建日期：2009-4-1
 */
public class TemplateWriter implements Writer {
	private Document document = null;
	private final static int SCALE = 3;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.io.OutputStream,
	 * com.wisii.wisedoc.document.Document)
	 */
	@Override
	public void write(final OutputStream outStream, final Document document)
			throws IOException {
		if (isNull(outStream) || isNull(document)) {
			return;
		}
		this.document = document;
		final ZipOutputStream zip = new ZipOutputStream(outStream);
		final FileKind[] items = FileKind.values();
		
		ZipEntry zipAdd = null;
		try {
			for (final FileKind item : items) {
				zipAdd = new ZipEntry(item.getName());
				zip.putNextEntry(zipAdd);
				write(zip, item);
				zip.closeEntry();
			}
		} finally {
			zip.close();
			outStream.close();
			System.gc();
		}
	}

	private void write(final ZipOutputStream zip, final FileKind kind) throws IOException {
		switch (kind) {
		case WSD:
			final WSDWriter wsd = new WSDWriter();
			wsd.write(zip, document);
			break;
		case ICON:
			ImageIO.write(createImage(), kind.getImageType(), zip);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.lang.String,
	 * com.wisii.wisedoc.document.Document)
	 */
	@Override
	public void write(final String file, final Document document) throws IOException {
		if (isEmpty(file)) {
			return;
		}
		final FileOutputStream stream = new FileOutputStream(file, Boolean.FALSE);
		write(stream, document);
	}
	/* 创建文档第一页的临摹图像，同时进行缩放处理【减小内存使用】。 */
	private static BufferedImage createImage() {
		final BufferedImage page = getPageImage();
		if(isNull(page)){
			LogUtil.debug("Page Image is Null");
			return null;
		}
		int width = page.getWidth();
		int height = page.getHeight();
		width >>= SCALE;
		height >>= SCALE;
		final BufferedImage image = createOpaqueImage(width, height);
		
		final Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, width, height);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		final Image i = page.getScaledInstance(width, height, BufferedImage.TYPE_3BYTE_BGR);
		g2d.drawImage(i, 0, 0, null);
		g2d.dispose();
		
		return image;
	}
	/* 创建透明的图像 */
	@SuppressWarnings("unused")
	private static BufferedImage createTransparentImage(final int width, final int height){
		final GraphicsConfiguration configure = getGraphicsConfiguration();
		if (configure == null) {
			return null;
		}
		final BufferedImage image = configure.createCompatibleImage(width, height, Transparency.TRANSLUCENT);//BITMASK
		return image;
	}
	/* 创建不透明图像 */
	private static BufferedImage createOpaqueImage(final int width, final int height){
		return new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	}
	private static BufferedImage getPageImage(){
		final WisedocFrame frame = SystemManager.getMainframe();
		final AbstractEditComponent comp = frame.getEidtComponent();
		final PageViewportPanel page = comp.getPageOf(0);
		if(isNull(page)) {
			return null;
		}
		return page.getImageRef().get();
	}
	/* 获得系统的缺省图形环境。 */
	private static GraphicsConfiguration getGraphicsConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getDefaultConfiguration();
    }
}
