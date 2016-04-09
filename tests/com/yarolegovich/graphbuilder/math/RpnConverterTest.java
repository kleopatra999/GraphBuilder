package com.yarolegovich.graphbuilder.math;

import com.yarolegovich.graphbuilder.math.MathExpressionParseException;
import com.yarolegovich.graphbuilder.math.RpnConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yarolegovich on 08.04.2016.
 */
public class RpnConverterTest {

    private RpnConverter parser;

    @Before
    public void createParser() {
        parser = new RpnConverter();
    }

    @Test
    public void canHandleSimplestExpressions() throws MathExpressionParseException {
        String expression = "12 + 3 * 4 - 40";
        String rpn = "12 3 4 * + 40 -";
        assertEquals(rpn, parser.toRpn(expression));
    }

    @Test
    public void canHandleParanthes() throws MathExpressionParseException {
        String expression = "(12 + 15) * 4 / ((12 + 5) * 3)";
        String rpn = "12 15 + 4 * 12 5 + 3 * /";
        assertEquals(rpn, parser.toRpn(expression));
    }

    @Test
    public void canHandleNegativeNumbers() throws MathExpressionParseException {
        String expression = "(-12 + 3) - 3 - -7 -(-3)";
        String rpn = "-12 3 + 3 - -7 - -3 -";
        assertEquals(rpn, parser.toRpn(expression));
    }

    @Test
    public void canHandleRealNumbers() throws MathExpressionParseException {
        String expression = "(12.123 + 15.53) * 4.422 / ((12.1 + 5) * 3.223)";
        String rpn = "12.123 15.53 + 4.422 * 12.1 5 + 3.223 * /";
        assertEquals(rpn, parser.toRpn(expression));
    }

    @Test
    public void canHandleOperatorPrecedence() throws MathExpressionParseException {
        String expression = "cos(x) / sin(2 * x) * tan (3^x)";
        String rpn = "x cos 2 x * sin / 3 x ^ tan *";
        assertEquals(rpn, parser.toRpn(expression));
    }

    @Test(expected = MathExpressionParseException.class)
    public void throwsExceptionOnMismatchedParantheses() throws MathExpressionParseException {
        String expression = "(12 + 15) * 4 / ((12 + 5) * 3";
        parser.toRpn(expression);
    }

    @Test
    public void doesntDependOnSpacesInInput() throws MathExpressionParseException {
        String expression = "(  -12 +3.  123) - 3--7 -(   -3   )";
        String rpn = "-12 3.123 + 3 - -7 - -3 -";
        assertEquals(rpn, parser.toRpn(expression));
    }

    @Test
    public void canHandleSimpleFunctionCases() throws MathExpressionParseException {
        String expression = "cos(1) + sin(1)";
        String rpn = "1 cos 1 sin +";
        assertEquals(rpn, parser.toRpn(expression));
    }

    @Test
    public void canHandleComplexFunctionCases() throws MathExpressionParseException {
        String expression = "5 + 12 * cos(sqrt(5) + sin(12 + 4)) / (3 + 8)";
        String rpn = "5 12 5 sqrt 12 4 + sin + cos * 3 8 + / +";
        assertEquals(rpn, parser.toRpn(expression));
    }

    @Test(expected = MathExpressionParseException.class)
    public void throwsExceptionIfExtraSigns() throws MathExpressionParseException {
        String expression = "5 + 12 * cos(sqrt(5) + sin(12 + 4) - ) / (3 + 8)";
        parser.toRpn(expression);
    }

    @Test
    public void canHandleFreeVariables() throws MathExpressionParseException {
        String expression = "5 + x * cos(sqrt(5) + sin(12 + x)) / (3 + 8)";
        String rpn = "5 x 5 sqrt 12 x + sin + cos * 3 8 + / +";
        assertEquals(rpn, parser.toRpn(expression));
    }

    @Test(expected = MathExpressionParseException.class)
    public void throwsExceptionIfThereAreManyFreeVars() throws MathExpressionParseException {
        String expression = "5 + y * cos(sqrt(5) + sin(12 + x)) / (3 + 8)";
        parser.toRpn(expression);
    }

    @Test
    public void canHandleFunctionNegation() throws MathExpressionParseException {
        String expression = "-log(x)";
        String rpn = "x -log";
        assertEquals(rpn, parser.toRpn(expression));
    }
}