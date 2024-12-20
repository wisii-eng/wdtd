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
/* $Id: Java2DSVGHandler.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.render.java2d;

import java.awt.geom.AffineTransform;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Document;

import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.Renderer;
import com.wisii.wisedoc.render.RendererContext;
import com.wisii.wisedoc.render.XMLHandler;
import com.wisii.wisedoc.svg.SVGUserAgent;

/**
 * Java2D XML handler for SVG (uses Apache Batik).
 * This handler handles XML for foreign objects when rendering to Java2D.
 * The properties from the Java2D renderer are subject to change.
 */
public class Java2DSVGHandler implements XMLHandler, Java2DRendererContextConstants {
    /**
     * Create a new Java2D XML handler for use by the Java2D renderer and its subclasses.
     */
    public Java2DSVGHandler() {
        //nop
    }

    /** @see com.wisii.wisedoc.render.XMLHandler */
    public void handleXML(RendererContext context,
                Document doc, String ns) throws Exception {
        Java2DInfo pdfi = getJava2DInfo(context);

        if (SVGDOMImplementation.SVG_NAMESPACE_URI.equals(ns)) {
            renderSVGDocument(context, doc, pdfi);
        }
    }

    /**
     * Get the pdf information from the render context.
     *
     * @param context the renderer context
     * @return the pdf information retrieved from the context
     */
    public static Java2DInfo getJava2DInfo(RendererContext context) {
        Java2DInfo pdfi = new Java2DInfo();
        pdfi.state = (Java2DGraphicsState)context.getProperty(JAVA2D_STATE);
        pdfi.width = ((Integer)context.getProperty(WIDTH)).intValue();
        pdfi.height = ((Integer)context.getProperty(HEIGHT)).intValue();
        pdfi.currentXPosition = ((Integer)context.getProperty(XPOS)).intValue();
        pdfi.currentYPosition = ((Integer)context.getProperty(YPOS)).intValue();
        return pdfi;
    }

    /**
     * Java2D information structure for drawing the XML document.
     */
    public static class Java2DInfo {
        /** see Java2D_STATE */
        public Java2DGraphicsState state;
        /** see Java2D_WIDTH */
        public int width;
        /** see Java2D_HEIGHT */
        public int height;
        /** see Java2D_XPOS */
        public int currentXPosition;
        /** see Java2D_YPOS */
        public int currentYPosition;

        /** @see java.lang.Object#toString() */
        public String toString() {
            return "Java2DInfo {"
                + "state = " + state + ", "
                + "width = " + width + ", "
                + "height = " + height + ", "
                + "currentXPosition = " + currentXPosition + ", "
                + "currentYPosition = " + currentYPosition + "}";
        }
    }

    /**
     * Render the svg document.
     * @param context the renderer context
     * @param doc the svg document
     * @param info the pdf information of the current context
     */
    protected void renderSVGDocument(RendererContext context,
                                     Document doc,
                                     Java2DInfo info) {

        //log.debug("renderSVGDocument(" + context + ", " + doc + ", " + info + ")");
        //System.out.println("renderSVGDocument(" + context + ", " + doc + ", " + info + ")");

        int x = info.currentXPosition;
        int y = info.currentYPosition;

        float ptom = context.getUserAgent().getSourcePixelUnitToMillimeter();
        SVGUserAgent ua = new SVGUserAgent(ptom, new AffineTransform());

        GVTBuilder builder = new GVTBuilder();
        BridgeContext ctx = new BridgeContext(ua);

        GraphicsNode root;
        try {
            root = builder.build(ctx, doc);
        } catch (Exception e) {
        	e.printStackTrace();
            LogUtil.errorException("SVG graphic could not be built: " + e.getMessage(), e);
           // System.out.println(e);
            return;
        }

        // If no viewbox is defined in the svg file, a viewbox of 100x100 is
        // assumed, as defined in SVGUserAgent.getViewportSize()
        float iw = (float) ctx.getDocumentSize().getWidth() * 1000f;
        float ih = (float) ctx.getDocumentSize().getHeight() * 1000f;

        float w = (float) info.width;
        float h = (float) info.height;

        AffineTransform origTransform = info.state.getGraph().getTransform();

        // correct integer roundoff
        info.state.getGraph().translate(x / 1000f, y / 1000f);

        //SVGSVGElement svg = ((SVGDocument) doc).getRootElement();
        // Aspect ratio preserved by layout engine, not here
        AffineTransform at = AffineTransform.getScaleInstance(w / iw, h / ih);
        if (!at.isIdentity()) {
            info.state.getGraph().transform(at);
        }

        try {
            root.paint(info.state.getGraph());
        } catch (Exception e) {
            LogUtil.errorException("Error while painting SVG", e);
        }

        info.state.getGraph().setTransform(origTransform);
    }

    /** @see com.wisii.wisedoc.render.XMLHandler#supportsRenderer() */
    public boolean supportsRenderer(Renderer renderer) {
        return (renderer instanceof Java2DRenderer);
    }


    /** @see com.wisii.wisedoc.render.XMLHandler#getNamespace() */
    public String getNamespace() {
        return SVGDOMImplementation.SVG_NAMESPACE_URI;
    }
}
