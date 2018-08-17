/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-common-util - LoggerConstants.java
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.github.cataclysmuprising.myapp.common.util;

public class LoggerConstants {
    public static final String LOG_BREAKER_OPEN = "**********************************************************************";
    public static final String LOG_BREAKER_CLOSE = "############################## xxxxxxxx ##############################";
    public static final String LOG_PREFIX = "----------  ";
    public static final String LOG_SUFFIX = "  ----------";
    public static final String DATA_INTEGRITY_VIOLATION_MSG = "Rejected : Deleting process was failed because this entity was connected with other resources.If you try to forcely remove it, entire database will loose consistency";
    public static final String DUPLICATE_KEY_INSERT_FAILED_MSG = "Inserting process was failed due to Unique Key constraint";
    public static final String DUPLICATE_KEY_UPDATE_FAILED_MSG = "Updating process was failed due to Unique Key constraint";
}
