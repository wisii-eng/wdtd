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
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.wisii.com/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: CommonAural.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;



/**
 * Stores all common aural properties.
 * See Sec. 7.6 of the XSL-FO Standard.
 * Public "structure" allows direct member access.
 */
public final class CommonAural extends AbstractCommonAttributes{

	  /**
     * Create a CommonAbsolutePosition object.
     * @param pList The PropertyList with propery values.
     */
    public CommonAural(CellElement cellelement)
	{
		super(cellelement);
	}
    /**
	 * @返回  azimuth变量的值
	 */
	public final int getAzimuth()
	{
		return (Integer)getAttribute(Constants.PR_AZIMUTH);
	}

	/**
	 * @返回  cueAfter变量的值
	 */
	public final String getCueAfter()
	{
		return (String) getAttribute(Constants.PR_CUE_AFTER);
	}

	/**
	 * @返回  cueBefore变量的值
	 */
	public final String getCueBefore()
	{
		return (String) getAttribute(Constants.PR_CUE_BEFORE);
	}

	/**
	 * @返回  elevation变量的值
	 */
	public final int getElevation()
	{
		return (Integer) getAttribute(Constants.PR_ELEVATION);
	}

	/**
	 * @返回  pauseAfter变量的值
	 */
	public final int getPauseAfter()
	{
		return (Integer) getAttribute(Constants.PR_PAUSE_AFTER);
	}

	/**
	 * @返回  pauseBefore变量的值
	 */
	public final int getPauseBefore()
	{
		return (Integer) getAttribute(Constants.PR_PAUSE_BEFORE);
	}

	/**
	 * @返回  pitch变量的值
	 */
	public final int getPitch()
	{
		return (Integer) getAttribute(Constants.PR_PITCH);
	}

	/**
	 * @返回  pitchRange变量的值
	 */
	public final int getPitchRange()
	{
		return (Integer) getAttribute(Constants.PR_PITCH_RANGE);
	}

	/**
	 * @返回  playDuring变量的值
	 */
	public final int getPlayDuring()
	{
		return (Integer) getAttribute(Constants.PR_PLAY_DURING);
	}

	/**
	 * @返回  richness变量的值
	 */
	public final int getRichness()
	{
		return (Integer) getAttribute(Constants.PR_RICHNESS);
	}

	/**
	 * @返回  speak变量的值
	 */
	public final int getSpeak()
	{
		return (Integer) getAttribute(Constants.PR_SPEAK);
	}

	/**
	 * @返回  speakHeader变量的值
	 */
	public final int getSpeakHeader()
	{
		return (Integer) getAttribute(Constants.PR_SPEAK_HEADER);
	}

	/**
	 * @返回  speakNumeral变量的值
	 */
	public final int getSpeakNumeral()
	{
		return (Integer) getAttribute(Constants.PR_SPEAK_NUMERAL);
	}

	/**
	 * @返回  speakPunctuation变量的值
	 */
	public final int getSpeakPunctuation()
	{
		return (Integer) getAttribute(Constants.PR_SPEAK_PUNCTUATION);
	}

	/**
	 * @返回  speechRate变量的值
	 */
	public final int getSpeechRate()
	{
		return (Integer) getAttribute(Constants.PR_SPEECH_RATE);
	}

	/**
	 * @返回  stress变量的值
	 */
	public final int getStress()
	{
		return (Integer) getAttribute(Constants.PR_STRESS);
	}

	/**
	 * @返回  voiceFamily变量的值
	 */
	public final int getVoiceFamily()
	{
		return (Integer) getAttribute(Constants.PR_VOICE_FAMILY);
	}

	/**
	 * @返回  volume变量的值
	 */
	public final int getVolume()
	{
		return (Integer) getAttribute(Constants.PR_VOLUME);
	}
}
