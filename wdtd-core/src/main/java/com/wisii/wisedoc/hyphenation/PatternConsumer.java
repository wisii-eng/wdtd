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
/* $Id: PatternConsumer.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.hyphenation;

import java.util.ArrayList;

/**
 * This interface is used to connect the XML pattern file parser to
 * the hyphenation tree.
 *
 * @author Carlos Villegas <cav@uniscope.co.jp>
 */
public interface PatternConsumer {

    /**
     * Add a character class.
     * A character class defines characters that are considered
     * equivalent for the purpose of hyphenation (e.g. "aA"). It
     * usually means to ignore case.
     * @param chargroup character group
     */
    void addClass(String chargroup);

    /**
     * Add a hyphenation exception. An exception replaces the
     * result obtained by the algorithm for cases for which this
     * fails or the user wants to provide his own hyphenation.
     * A hyphenatedword is a vector of alternating String's and
     * {@link Hyphen Hyphen} instances
     */
    void addException(String word, ArrayList hyphenatedword);

    /**
     * Add hyphenation patterns.
     * @param pattern the pattern
     * @param values interletter values expressed as a string of
     * digit characters.
     */
    void addPattern(String pattern, String values);

}
