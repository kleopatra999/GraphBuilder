package com.yarolegovich.graphbuilder.math;

import java.util.Arrays;

import static java.lang.Math.*;

/**
 * Created by yarolegovich on 07.04.2016.
 */
public enum Operator {

    PLUS("+", 1, true, "addition") {
        @Override
        public double applyTo(double... args) {
            return args[0] + args[1];
        }
    },
    MINUS("-", 1, true, "subtraction") {
        @Override
        public double applyTo(double... args) {
            return args[1] - args[0];
        }
    },
    MULTIPLY("*", 2, true, "multiplication") {
        @Override
        public double applyTo(double... args) {
            return args[0] * args[1];
        }
    },
    DIVIDE("/", 2, true, "division") {
        @Override
        public double applyTo(double... args) {
            return args[1] / args[0];
        }
    },
    EXPONENT("^", 4, false, "exponentiation") {
        @Override
        public double applyTo(double... args) {
            return pow(args[1], args[0]);
        }
    },
    SIN("sin", "sin function") {
        @Override
        public double applyTo(double... args) {
            return sin(args[0]);
        }
    },
    COS("cos", "cos function") {
        @Override
        public double applyTo(double... args) {
            return cos(args[0]);
        }
    },
    TAN("tan", "tan function") {
        @Override
        public double applyTo(double... args) {
            return tan(args[0]);
        }
    },
    LOG("log", "natural logarithm") {
        @Override
        public double applyTo(double... args) {
            return log(args[0]);
        }
    },
    SQRT("sqrt", "square root") {
        @Override
        public double applyTo(double... args) {
            return sqrt(args[0]);
        }
    },
    ABS("abs", "absolute value") {
        @Override
        public double applyTo(double... args) {
            return abs(args[0]);
        }
    };

    public final int precedence;
    public final String symbol;
    public final String description;
    public final boolean isLeftAssociative;
    public final boolean isFunction;

    Operator(String functionName, String description) {
        this(functionName, 0, false, true, description);
    }

    Operator(String symbol, int precedence, boolean isLeftAssociative, String description) {
        this(symbol, precedence, isLeftAssociative, false, description);
    }

    Operator(String symbol, int precedence, boolean isLeftAssociative, boolean isFunction, String description) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.isLeftAssociative = isLeftAssociative;
        this.isFunction = isFunction;
        this.description = description;
    }

    public abstract double applyTo(double ...args);

    public static Operator parse(String op) {
        return Arrays.stream(values())
                .filter(it -> it.symbol.equals(op))
                .findFirst().get();
    }

    public static boolean isKnownOperator(String op) {
        return Arrays.stream(values())
                .filter(it -> !it.isFunction)
                .anyMatch(it -> it.symbol.equals(op));
    }

    public static boolean isKnownFunction(String function) {
        return Arrays.stream(values())
                .filter(it -> it.isFunction)
                .anyMatch(it -> it.symbol.equals(function));
    }
}
