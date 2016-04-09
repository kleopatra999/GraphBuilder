package com.yarolegovich.graphbuilder.util;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by yarolegovich on 08.04.2016.
 */
public class Logger {

    private static final boolean DEBUG = true;

    public static void d(Object message, Object ...formatArgs) {
        if (DEBUG) {
            System.out.println(String.format(String.valueOf(message), formatArgs));
        }
    }
}
