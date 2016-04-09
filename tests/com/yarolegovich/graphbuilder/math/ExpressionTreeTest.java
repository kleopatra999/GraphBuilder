package com.yarolegovich.graphbuilder.math;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.yarolegovich.graphbuilder.math.TestConstants.ACCEPTABLE_DELTA;

/**
 * Created by yarolegovich on 08.04.2016.
 */
public class ExpressionTreeTest {

    @Test
    public void canHandleSimplestOperations() {
        String expression = "2 2 +";
        ExpressionTree expressionTree = ExpressionTree.from(expression);
        assertEquals(4.0, expressionTree.evaluate(), ACCEPTABLE_DELTA);

        expression = "4 2 /";
        expressionTree = ExpressionTree.from(expression);
        assertEquals(2.0, expressionTree.evaluate(), ACCEPTABLE_DELTA);

        expression = "2 2 *";
        expressionTree = ExpressionTree.from(expression);
        assertEquals(4.0, expressionTree.evaluate(), ACCEPTABLE_DELTA);

        expression = "4 2 -";
        expressionTree = ExpressionTree.from(expression);
        assertEquals(2.0, expressionTree.evaluate(), ACCEPTABLE_DELTA);

        expression = "4 2 ^";
        expressionTree = ExpressionTree.from(expression);
        assertEquals(16.0, expressionTree.evaluate(), ACCEPTABLE_DELTA);
    }

    @Test
    public void canHandleSimplestCasesWithFreeVariable() {
        String expression = "x 2 +";
        ExpressionTree expressionTree = ExpressionTree.from(expression);
        expressionTree.bindFreeVariable(2.0);
        assertEquals(4.0, expressionTree.evaluate(), ACCEPTABLE_DELTA);
    }

    @Test
    public void canHandleMoreComplexCases() {
        String expression = "12 x 2 * 15 + log /";
        ExpressionTree expressionTree = ExpressionTree.from(expression);
        expressionTree.bindFreeVariable(2.0);
        double answer = 12 / Math.log(2.0 * 2.0 + 15.0);
        assertEquals(answer, expressionTree.evaluate(), ACCEPTABLE_DELTA);
    }
}