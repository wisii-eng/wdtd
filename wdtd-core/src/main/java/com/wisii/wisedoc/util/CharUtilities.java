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
/* $Id: CharUtilities.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.util;

/**
 * This class provides utilities to distinguish various kinds of Unicode
 * whitespace and to get character widths in a given FontState.
 */
public class CharUtilities {

    /**
     * Character code used to signal a character boundary in
     * inline content, such as an inline with borders and padding
     * or a nested block object.
     */
    public static final char CODE_EOT = 0;

    /**
     * Character class: Unicode white space
     */
    public static final int UCWHITESPACE = 0;
    /**
     * Character class: Line feed
     */
    public static final int LINEFEED = 1;
    /**
     * Character class: Boundary between text runs
     */
    public static final int EOT = 2;
    /**
     * Character class: non-whitespace
     */
    public static final int NONWHITESPACE = 3;
    /**
     * Character class: XML whitespace
     */
    public static final int XMLWHITESPACE = 4;


    /** normal space */
    public static final char SPACE = '\u0020';
    /** non-breaking space */
    public static final char NBSPACE = '\u00A0';
    /** zero-width space */
    public static final char ZERO_WIDTH_SPACE = '\u200B';
    /** zero-width no-break space (= byte order mark) */
    public static final char ZERO_WIDTH_NOBREAK_SPACE = '\uFEFF';
    /** soft hyphen */
    public static final char SOFT_HYPHEN = '\u00AD';


    /**
     * Utility class: Constructor prevents instantiating when subclassed.
     */
    protected CharUtilities() {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the appropriate CharClass constant for the type
     * of the passed character.
     * @param c character to inspect
     * @return the determined character class
     */
    public static int classOf(char c) {
        if (c == CODE_EOT) { return EOT; }
        if (c == '\n') { return LINEFEED; }
        if (c == ' ' || c == '\r' || c == '\t') { return XMLWHITESPACE; }
        if (isAnySpace(c)) { return UCWHITESPACE; }
        return NONWHITESPACE;
    }


    /**
     * Helper method to determine if the character is a
     * space with normal behavior. Normal behavior means that
     * it's not non-breaking.
     * @param c character to inspect
     * @return True if the character is a normal space
     */
    public static boolean isBreakableSpace(char c) {
        return (c == SPACE || isFixedWidthSpace(c));
    }

    /**
     * Method to determine if the character is a (breakable) fixed-width space.
     * @param c the character to check
     * @return true if the character has a fixed-width
     */
    public static boolean isFixedWidthSpace(char c) {
        return (c >= '\u2000' && c <= '\u200B') || c == '\u3000';
//      c == '\u2000'                   // en quad
//      c == '\u2001'                   // em quad
//      c == '\u2002'                   // en space
//      c == '\u2003'                   // em space
//      c == '\u2004'                   // three-per-em space
//      c == '\u2005'                   // four--per-em space
//      c == '\u2006'                   // six-per-em space
//      c == '\u2007'                   // figure space
//      c == '\u2008'                   // punctuation space
//      c == '\u2009'                   // thin space
//      c == '\u200A'                   // hair space
//      c == '\u200B'                   // zero width space
//      c == '\u3000'                   // ideographic space
    }

    /**
     * Method to determine if the character is a nonbreaking
     * space.
     * @param c character to check
     * @return True if the character is a nbsp
     */
    public static boolean isNonBreakableSpace(char c) {
        return
            (c == NBSPACE       // no-break space
            || c == '\u202F'    // narrow no-break space
            || c == '\u3000'    // ideographic space
            || c == ZERO_WIDTH_NOBREAK_SPACE);  // zero width no-break space
    }

    /**
     * Method to determine if the character is an adjustable
     * space.
     * @param c character to check
     * @return True if the character is adjustable
     */
    public static boolean isAdjustableSpace(char c) {
        //TODO: are there other kinds of adjustable spaces?
        return
            (c == '\u0020'    // normal space
            || c == NBSPACE); // no-break space
    }

    /**
     * Determines if the character represents any kind of space.
     * @param c character to check
     * @return True if the character represents any kind of space
     */
    public static boolean isAnySpace(char c) {
        boolean ret = (isBreakableSpace(c) || isNonBreakableSpace(c));
        return ret;
    }

    /**
     * Indicates whether a character is classified as "Alphabetic" by the Unicode standard.
     * @param ch the character
     * @return true if the character is "Alphabetic"
     */
    public static boolean isAlphabetic(char ch) {
        //http://www.unicode.org/Public/UNIDATA/UCD.html#Alphabetic
        //Generated from: Other_Alphabetic + Lu + Ll + Lt + Lm + Lo + Nl
        int generalCategory = Character.getType(ch);
        switch (generalCategory) {
        case Character.UPPERCASE_LETTER: //Lu
        case Character.LOWERCASE_LETTER: //Ll
        case Character.TITLECASE_LETTER: //Lt
        case Character.MODIFIER_LETTER: //Lm
        case Character.OTHER_LETTER: //Lo
        case Character.LETTER_NUMBER: //Nl
            return true;
        default:
            //TODO if (ch in Other_Alphabetic) return true; (Probably need ICU4J for that)
            //Other_Alphabetic contains mostly more exotic characters
            return false;
        }
    }

}

