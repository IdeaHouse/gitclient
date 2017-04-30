package com.inder.gitclient.util;

import java.util.List;

/**
 * Created by inder on 29/4/17.
 */

public class StringUtils {
    public static final String SEPARATOR_COMMA = ",";

    public static String toString(List<String> stringList) {
        return toString(stringList, SEPARATOR_COMMA);
    }

    public static String toString(List<String> stringList, String separator) {
        String strings = "";
        if (stringList != null && stringList.size() > 0) {
            strings = stringList.get(0);
            for (int index = 1; index < stringList.size(); index++) {
                strings += separator + stringList.get(index);
            }
        }
        return strings;
    }
}
