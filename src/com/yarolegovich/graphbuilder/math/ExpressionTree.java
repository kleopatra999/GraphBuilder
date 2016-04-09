package com.yarolegovich.graphbuilder.math;

import java.util.*;
import java.util.stream.Collectors;

import static com.yarolegovich.graphbuilder.math.Recognizer.*;

/**
 * Created by yarolegovich on 08.04.2016.
 */
class ExpressionTree {

    public static ExpressionTree from(String rpn) {
        ExpressionTree result = new ExpressionTree();
        Arrays.stream(rpn.split(" ")).collect(Collectors.toCollection(ArrayDeque::new))
                .descendingIterator().forEachRemaining(result::add);
        return result;
    }

    private Node root;

    private ExpressionTree() { }

    public double evaluate() {
        return root.evaluate();
    }

    public void bindFreeVariable(double value) {
        root.bindFreeVariable(value);
    }

    public void printTree() {
        printRecursive(root);
    }

    private void add(String token) {
        Node newNode = tokenToNode(token);

        if (root == null) {
            root = newNode;
            return;
        }

        Node newParent = findNewParent(root);

        String malformedTreeException = "Malformed expression structure, can't build the tree";

        if (newParent == null) {
            throw new IllegalArgumentException(malformedTreeException);
        }

        if (newParent.childLeft == null) {
            newParent.childLeft = newNode;
        } else if (newParent.childRight == null) {
            newParent.childRight = newNode;
        } else {
            throw new IllegalArgumentException(malformedTreeException);
        }

    }

    private Node findNewParent(Node parent) {
        if (parent instanceof OperatorNode) {
            if (parent.childLeft == null) {
                return parent;
            }

            Node searchLeft = findNewParent(parent.childLeft);
            if (searchLeft != null) {
                return searchLeft;
            }

            if (parent.childRight == null) {
                return parent;
            }

            return findNewParent(parent.childRight);
        }

        if (parent instanceof FunctionNode) {
            return parent.childLeft != null ?
                    findNewParent(parent.childLeft) :
                    parent;
        }

        return null;
    }

    private Node tokenToNode(String token) {
        if (isNumber(token)) {
            return new ValueNode(token);
        } else if (isFreeVariable(token)) {
            return new FreeVariableNode(token);
        } else if (isFunction(token)) {
            return new FunctionNode(token);
        } else if (isOperator(token)) {
            return new OperatorNode(token);
        } else {
            throw new IllegalArgumentException("Unknown token, validate before building expression tree: " + token);
        }
    }

    private void printRecursive(Node node) {
        if (node != null) {
            System.out.println(node);
            printRecursive(node.childLeft);
            printRecursive(node.childRight);
        }
    }

    private static abstract class Node {
        protected Node childLeft;
        protected Node childRight;

        abstract double evaluate();

        void bindFreeVariable(double value) {
        }
    }

    private static class OperatorNode extends Node {

        private Operator operator;

        private OperatorNode(String token) {
            this.operator = Operator.parse(token);
        }

        @Override
        double evaluate() {
            return operator.applyTo(childLeft.evaluate(), childRight.evaluate());
        }

        @Override
        void bindFreeVariable(double value) {
            childLeft.bindFreeVariable(value);
            childRight.bindFreeVariable(value);
        }

        @Override
        public String toString() {
            return operator.symbol;
        }
    }

    private static class FreeVariableNode extends Node {

        private Optional<Double> bindedValue = Optional.empty();
        private boolean isNegative;

        public FreeVariableNode(String token) {
            isNegative = token.startsWith("-");
        }

        @Override
        double evaluate() {
            return bindedValue.map(val -> isNegative ? -val : val).orElseThrow(
                    () -> new IllegalStateException("First need to bind all free variables")
            );
        }

        @Override
        void bindFreeVariable(double value) {
            bindedValue = Optional.of(value);
        }

        @Override
        public String toString() {
            return bindedValue.map(d -> String.format("%.2f", d)).orElse("x");
        }
    }

    private static class FunctionNode extends Node {

        private Operator function;
        private boolean isNegative;

        private FunctionNode(String token) {
            this.isNegative = token.startsWith("-");
            this.function = Operator.parse(isNegative ? token.substring(1) : token);
        }

        @Override
        public double evaluate() {
            return !isNegative ?
                    function.applyTo(childLeft.evaluate()) :
                    -function.applyTo(childLeft.evaluate());
        }

        @Override
        void bindFreeVariable(double value) {
            childLeft.bindFreeVariable(value);
        }

        @Override
        public String toString() {
            return function.symbol;
        }
    }

    private static class ValueNode extends Node {

        private double value;

        private ValueNode(String token) {
            this.value = Double.parseDouble(token);
        }

        @Override
        double evaluate() {
            return value;
        }

        @Override
        public String toString() {
            return String.format("%.2f", value);
        }
    }

}
