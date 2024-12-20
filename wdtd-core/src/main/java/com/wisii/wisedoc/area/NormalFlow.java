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
/* $Id: NormalFlow.java,v 1.1 2007/04/12 06:41:18 cvsuser Exp $ */

package com.wisii.wisedoc.area;

import java.awt.geom.Rectangle2D;

/**
 * The normal-flow-reference-area class.
 * Each span-reference-area contains one or more of these objects
 * See fo:region-body definition in the XSL Rec for more information.
 */
public class NormalFlow extends BlockParent {
	/* 【添加：START】by 李晓光 2009-1-15 */
	private Rectangle2D viewport = null;
	
	public Rectangle2D getViewport() {
		if(viewport == null)
			return viewport;
		return new Rectangle2D.Double(viewport.getX(), viewport.getY(), viewport.getWidth(), viewport.getHeight());
	}

	public void setViewport(Rectangle2D viewport) {
		this.viewport = viewport;
	}

	/* 【添加：END】by 李晓光 2009-1-15 */
    /**
     * Constructor.
     * @param ipd of Normal flow object
     */
    public NormalFlow(int ipd) {
        addTrait(Trait.IS_REFERENCE_AREA, Boolean.TRUE);
        /* 【添加：START】 by 李晓光 2008-11-13 */
        setAreaKind(AreaKind.REFERENCE);
        /* 【添加：START】 by 李晓光 2008-11-13 */
        setIPD(ipd);
    }

    /** @see com.wisii.wisedoc.area.BlockParent#addBlock(com.wisii.wisedoc.area.Block) */
    public void addBlock(Block block) {
        super.addBlock(block);
        bpd += block.getAllocBPD();
    }
}

