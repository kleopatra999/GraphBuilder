package com.yarolegovich.graphbuilder.math;

/**
 * Created by yarolegovich on 08.04.2016.
 */
public class Recognizer {

    public static boolean isFreeVariable(String token) {
        return token.matches("-?[a-zA-Z]");
    }

    public static boolean isParanthes(String token) {
        return token.equals("(") || token.equals(")");
    }

    public static boolean isOperator(String token) {
        return Operator.isKnownOperator(token);
    }

    public static boolean isFunction(String token) {
        String nonNegative = token.replaceAll("-", "");
        return Operator.isKnownFunction(nonNegative);
    }

    public static boolean isNumber(String token) {
        return token.matches("-?(\\d+(\\.\\d*)?|\\d*\\.\\d+)");
    }
}
