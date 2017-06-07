/*
 This file is part of Subsonic.

 Subsonic is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Subsonic is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Subsonic.  If not, see <http://www.gnu.org/licenses/>.

 Copyright 2009 (C) Sindre Mehus
 */
package net.sourceforge.subsonic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Miscellaneous string utility methods.
 *
 * @author Sindre Mehus
 */
public final class StringUtil
{
    /**
     * Disallow external instantiation.
     */
    private StringUtil() {
    }


    /**
     * Splits the input string. White space is interpreted as separator token. Double quotes
     * are interpreted as grouping operator. <br/>
     * For instance, the input <code>"u2 rem "greatest hits""</code> will return an array with
     * three elements: <code>{"u2", "rem", "greatest hits"}</code>
     *
     * @param input The input string.
     * @return Array of elements.
     */
    public static String[] split(String input) {
        if (input == null) {
            return new String[0];
        }

        Pattern pattern = Pattern.compile("\".*?\"|\\S+");
        Matcher matcher = pattern.matcher(input);

        List<String> result = new ArrayList<String>();
        while (matcher.find()) {
            String element = matcher.group();
            if (element.startsWith("\"") && element.endsWith("\"") && element.length() > 1) {
                element = element.substring(1, element.length() - 1);
            }
            result.add(element);
        }

        return result.toArray(new String[result.size()]);
    }

}