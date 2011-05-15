/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : TR69ClientAPI
 *
 * Copyright © 2011 France Telecom
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */ 
package com.francetelecom.admindm.model;

/**
 * The Class ParameterType.
 */
public final class ParameterType {

    /**
     * Instantiates a new parameter type.
     */
    private ParameterType() {
    }

    /** The Constant STR_INT. */
    static final String STR_INT = "INT";

    /** The Constant STR_UINT. */
    static final String STR_UINT = "UINT";

    /** The Constant STR_LONG. */
    static final String STR_LONG = "LONG";

    /** The Constant STR_B0OLEAN. */
    static final String STR_BOOLEAN = "BOOLEAN";

    /** The Constant STR_DATE. */
    static final String STR_DATE = "DATE";

    /** The Constant STR_STRING. */
    static final String STR_STRING = "STRING";

    /** The Constant STR_ANY. */
    static final String STR_ANY = "ANY";

    /** The Constant INT. */
    public static final int INT = 0;

    /** The Constant UINT. */
    public static final int UINT = 1;

    /** The Constant LONG. */
    public static final int LONG = 2;

    /** The Constant BOOLEAN. */
    public static final int BOOLEAN = 3;

    /** The Constant DATE. */
    public static final int DATE = 4;

    /** The Constant STRING. */
    public static final int STRING = 5;

    /** The Constant ANY. */
    public static final int ANY = 6;

    /** The Constant UNDEFINED. */
    public static final int UNDEFINED = 7;

    /** The Constant BASE64. */
    public static final int BASE64 = 8;
    /**
     * String to type.
     * @param str the str
     * @return the int
     */
    public static int stringToType(final String str) {
        int result = UNDEFINED;
        if (STR_INT.equals(str)) {
            result = INT;
        }
        if (STR_UINT.equals(str)) {
            result = UINT;
        }
        if (STR_LONG.equals(str)) {
            result = LONG;
        }
        if (STR_BOOLEAN.equals(str)) {
            result = BOOLEAN;
        }
        if (STR_DATE.equals(str)) {
            result = DATE;
        }
        if (STR_STRING.equals(str)) {
            result = STRING;
        }
        if (STR_ANY.equals(str)) {
            result = ANY;
        }
        return result;
    }
}
