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
/* $Id: HyphenationTreeCache.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.hyphenation;

import java.util.Hashtable;
import java.util.Set;

/**
 * This is a cache for HyphenationTree instances.
 */
public class HyphenationTreeCache {

    /** Contains the cached hyphenation trees */
    private Hashtable hyphenTrees = new Hashtable();
    /** Used to avoid multiple error messages for the same language if a pattern file is missing. */
    private Set missingHyphenationTrees;

    /**
     * Looks in the cache if a hyphenation tree is available and returns it if it is found.
     * @param lang the language
     * @param country the country (may be null or "none")
     * @return the HyhenationTree instance or null if it's not in the cache
     */
    public HyphenationTree getHyphenationTree(String lang, String country) {
        String key = constructKey(lang, country);

        // first try to find it in the cache
        if (hyphenTrees.containsKey(key)) {
            return (HyphenationTree)hyphenTrees.get(key);
        } else if (hyphenTrees.containsKey(lang)) {
            return (HyphenationTree)hyphenTrees.get(lang);
        } else {
            return null;
        }
    }

    /**
     * Constructs the key for the hyphenation pattern file.
     * @param lang the language
     * @param country the country (may be null or "none")
     * @return the resulting key
     */
    public static String constructKey(String lang, String country) {
        String key = lang;
        // check whether the country code has been used
        if (country != null && !country.equals("none")) {
            key += "_" + country;
        }
        return key;
    }

    /**
     * Cache a hyphenation tree under its key.
     * @param key the key (ex. "de_CH" or "en")
     * @param hTree the hyphenation tree
     */
    public void cache(String key, HyphenationTree hTree) {
        hyphenTrees.put(key, hTree);
    }

    /**
     * Notes a key to a hyphenation tree as missing.
     * This is to avoid searching a second time for a hyphneation pattern file which is not
     * available.
     * @param key the key (ex. "de_CH" or "en")
     */
    public void noteMissing(String key) {
        if (missingHyphenationTrees == null) {
            missingHyphenationTrees = new java.util.HashSet();
        }
        missingHyphenationTrees.add(key);
    }

    /**
     * Indicates whether a hyphenation file has been requested before but it wasn't available.
     * This is to avoid searching a second time for a hyphneation pattern file which is not
     * available.
     * @param key the key (ex. "de_CH" or "en")
     * @return true if the hyphenation tree is unavailable
     */
    public boolean isMissing(String key) {
        return (missingHyphenationTrees != null && missingHyphenationTrees.contains(key));
    }

}
