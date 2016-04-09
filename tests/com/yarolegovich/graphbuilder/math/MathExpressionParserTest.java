package com.yarolegovich.graphbuilder.math;

import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import static org.junit.Assert.*;

import static com.yarolegovich.graphbuilder.math.TestConstants.ACCEPTABLE_DELTA;

/**
 * Created by yarolegovich on 08.04.2016.
 */
public class MathExpressionParserTest {

    private MathExpressionParser parser;
    private double freeVar;

    @Before
    public void setUp() {
        parser = new MathExpressionParser();
        freeVar = 25;
    }

    @Test
    public void canEvaluateComplexExpressions() throws MathExpressionParseException {
        String expression = "4^3 + -5 * sqrt(x) / (log(12) / (-3))";
        double answer = 4 * 4 * 4 + (-5) * sqrt(freeVar) / (log(12) / (-3));
        assertEquals(answer, parser.parseExpression(expression).apply(freeVar), ACCEPTABLE_DELTA);
    }

    @Test
    public void canEvaluateSimpleFunctions() throws MathExpressionParseException {
        String tan = "tan(x)";
        double tanVal = tan(freeVar);
        assertEquals(tanVal, parser.parseExpression(tan).apply(freeVar), ACCEPTABLE_DELTA);

        String sin = "sin(x)";
        double sinVal = sin(freeVar);
        assertEquals(sinVal, parser.parseExpression(sin).apply(freeVar), ACCEPTABLE_DELTA);

        String cos = "cos(x)";
        double cosVal = cos(freeVar);
        assertEquals(cosVal, parser.parseExpression(cos).apply(freeVar), ACCEPTABLE_DELTA);

        String log = "log(x)";
        double logVal = log(freeVar);
        assertEquals(logVal, parser.parseExpression(log).apply(freeVar), ACCEPTABLE_DELTA);

        String sqrt = "sqrt(x)";
        double sqrtVal = sqrt(freeVar);
        assertEquals(sqrtVal, parser.parseExpression(sqrt).apply(freeVar), ACCEPTABLE_DELTA);
    }

    @Test
    public void canEvaluateExpressionsWithFunctions() throws MathExpressionParseException {
        String expression = "sin(x) / cos(x*2)";
        double val = sin(freeVar) / cos(freeVar * 2);
        assertEquals(val, parser.parseExpression(expression).apply(freeVar), ACCEPTABLE_DELTA);
    }

}