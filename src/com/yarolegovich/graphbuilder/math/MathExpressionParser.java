package com.yarolegovich.graphbuilder.math;

import java.util.function.Function;

/**
 * Created by yarolegovich on 08.04.2016.
 */
public class MathExpressionParser {
    public Function<Double, Double> parseExpression(String expression) throws MathExpressionParseException {
        RpnConverter converter = new RpnConverter();
        String rpnExpression = converter.toRpn(expression);
        final ExpressionTree expressionTree = ExpressionTree.from(rpnExpression);
        return x -> {
            expressionTree.bindFreeVariable(x);
            return expressionTree.evaluate();
        };
    }
}
