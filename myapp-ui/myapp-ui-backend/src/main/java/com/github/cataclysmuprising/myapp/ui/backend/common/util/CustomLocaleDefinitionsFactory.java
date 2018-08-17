/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-ui-backend - CustomLocaleDefinitionsFactory.java
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
package com.github.cataclysmuprising.myapp.ui.backend.common.util;

import java.util.Locale;

import org.apache.tiles.Definition;
import org.apache.tiles.definition.LocaleDefinitionsFactory;
import org.apache.tiles.definition.NoSuchDefinitionException;
import org.apache.tiles.request.Request;

public class CustomLocaleDefinitionsFactory extends LocaleDefinitionsFactory {

    @Override
    public Definition getDefinition(String name, Request tilesContext) {
        Definition retValue;
        Locale locale = null;

        retValue = definitionDao.getDefinition(name, locale);
        if (retValue != null) {
            retValue = new Definition(retValue);
            String parentDefinitionName = retValue.getExtends();
            while (parentDefinitionName != null) {
                Definition parent = definitionDao.getDefinition(parentDefinitionName, locale);
                if (parent == null) {
                    throw new NoSuchDefinitionException("Cannot find definition '" + parentDefinitionName + "' ancestor of '" + retValue.getName() + "'");
                }
                retValue.inherit(parent);
                parentDefinitionName = parent.getExtends();
            }
        }

        return retValue;
    }
}
