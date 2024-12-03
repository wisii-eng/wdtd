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
/*
 * Copyright (c) 2005-2009 Flamingo Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Flamingo Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package org.jvnet.flamingo.common;

import org.jvnet.flamingo.common.ui.CommandButtonLayoutManagerBig;
import org.jvnet.flamingo.common.ui.CommandButtonLayoutManagerCustom;
import org.jvnet.flamingo.common.ui.CommandButtonLayoutManagerMedium;
import org.jvnet.flamingo.common.ui.CommandButtonLayoutManagerSmall;
import org.jvnet.flamingo.common.ui.CommandButtonLayoutManagerTile;

/**
 * Possible states of visual elements.
 * 
 * @author Kirill Grouchnikov
 */
public abstract class CommandButtonDisplayState {
	public enum CommandButtonSeparatorOrientation {
		VERTICAL, HORIZONTAL
	};

	/**
	 * Original state.
	 */
	public static final CommandButtonDisplayState ORIG = new CommandButtonDisplayState(
			"Original", -1) {
		@Override
		public CommandButtonLayoutManager createLayoutManager(
				AbstractCommandButton commandButton) {
			return new CommandButtonLayoutManagerCustom(commandButton);
		}

		@Override
		public CommandButtonSeparatorOrientation getSeparatorOrientation() {
			return CommandButtonSeparatorOrientation.HORIZONTAL;
		}

	};

	/**
	 * Custom state.
	 */
	public static final CommandButtonDisplayState CUSTOM = new CommandButtonDisplayState(
			"Custom", -1) {
		@Override
		public CommandButtonLayoutManager createLayoutManager(
				AbstractCommandButton commandButton) {
			return new CommandButtonLayoutManagerCustom(commandButton);
		}

		@Override
		public CommandButtonSeparatorOrientation getSeparatorOrientation() {
			return CommandButtonSeparatorOrientation.HORIZONTAL;
		}
	};

	/**
	 * Big state.
	 */
	public static final CommandButtonDisplayState BIG = new CommandButtonDisplayState(
			"Big", 32) {
		@Override
		public CommandButtonLayoutManager createLayoutManager(
				AbstractCommandButton commandButton) {
			return new CommandButtonLayoutManagerBig(commandButton);
		}

		@Override
		public CommandButtonSeparatorOrientation getSeparatorOrientation() {
			return CommandButtonSeparatorOrientation.HORIZONTAL;
		}
	};

	/**
	 * Tile state.
	 */
	public static final CommandButtonDisplayState TILE = new CommandButtonDisplayState(
			"Tile", 32) {
		@Override
		public CommandButtonLayoutManager createLayoutManager(
				AbstractCommandButton arg0) {
			return new CommandButtonLayoutManagerTile();
		}

		@Override
		public CommandButtonSeparatorOrientation getSeparatorOrientation() {
			return CommandButtonSeparatorOrientation.VERTICAL;
		}
	};

	/**
	 * Medium state.
	 */
	public static final CommandButtonDisplayState MEDIUM = new CommandButtonDisplayState(
			"Medium", 16) {
		@Override
		public CommandButtonLayoutManager createLayoutManager(
				AbstractCommandButton arg0) {
			return new CommandButtonLayoutManagerMedium();
		}

		@Override
		public CommandButtonSeparatorOrientation getSeparatorOrientation() {
			return CommandButtonSeparatorOrientation.VERTICAL;
		}
	};

	/**
	 * Small state.
	 */
	public static final CommandButtonDisplayState SMALL = new CommandButtonDisplayState(
			"Small", 16) {
		@Override
		public CommandButtonLayoutManager createLayoutManager(
				AbstractCommandButton arg0) {
			return new CommandButtonLayoutManagerSmall();
		}

		@Override
		public CommandButtonSeparatorOrientation getSeparatorOrientation() {
			return CommandButtonSeparatorOrientation.VERTICAL;
		}
	};

//	/**
//	 * Compact state.
//	 */
//	public static final CommandButtonDisplayState COMPACT = new CommandButtonDisplayState(
//			"Compact", 16) {
//		@Override
//		public CommandButtonLayoutManager createLayoutManager(
//				AbstractCommandButton arg0) {
//			return new CommandButtonLayoutManagerCompact();
//		}
//
//		@Override
//		public CommandButtonSeparatorOrientation getSeparatorOrientation() {
//			return CommandButtonSeparatorOrientation.VERTICAL;
//		}
//	};

	int preferredIconSize;

	String displayName;

	/**
	 * Creates a new element state.
	 * 
	 * @param preferredIconSize
	 *            Preferred icon size.
	 */
	protected CommandButtonDisplayState(String displayName,
			int preferredIconSize) {
		this.displayName = displayName;
		this.preferredIconSize = preferredIconSize;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getPreferredIconSize() {
		return this.preferredIconSize;
	}

	public abstract CommandButtonLayoutManager createLayoutManager(
			AbstractCommandButton commandButton);

	public abstract CommandButtonSeparatorOrientation getSeparatorOrientation();
	
	@Override
	public String toString() {
		return this.getDisplayName();
	}
}
