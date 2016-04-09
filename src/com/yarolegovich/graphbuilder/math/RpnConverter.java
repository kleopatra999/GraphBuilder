package com.yarolegovich.graphbuilder.math;

import com.yarolegovich.graphbuilder.util.Logger;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.yarolegovich.graphbuilder.math.Recognizer.*;

/**
 * Created by yarolegovich on 07.04.2016.
 */
public class RpnConverter {

    private Queue<String> outputQueue;
    private Deque<String> operatorStack;

    public RpnConverter() {
        operatorStack = new ArrayDeque<>();
        outputQueue = new ArrayDeque<>();
    }

    public String toRpn(String expression) throws MathExpressionParseException {
        setup();

        String tokenizable = expression.trim()
                //First we separate each symbol with space
                .replaceAll("(?=[^\\s])(?<=[^\\s])", " ")
                //Remove duplicate spaces
                .replaceAll("\\s+", " ")
                //The we remove spaces between digits (and dots, to handle real numbers)
                .replaceAll("(?<=[.\\d]) (?=[.\\d])", "")
                //Now we remove spaces in function names
                .replaceAll("(?<=\\w) (?=\\w)", "")
                //Handle negative numbers
                .replaceAll("(?<=(?:^|[^)\\w\\d] )-) (?=[\\w\\d])", "");
        String[] tokens = tokenizable.split(" ");
        for (String token : tokens) {
            processToken(token);
        }

        while (!operatorStack.isEmpty()) {
            String operator = operatorStack.removeLast();
            if (isParanthes(operator)) {
                throw new MathExpressionParseException("Mismatched parantheses");
            }
            outputQueue.add(operator);
        }

        int operators = (int) outputQueue.stream().filter(Recognizer::isOperator).count();
        Predicate<String> isNumberOrFreeVariable = it -> isNumber(it) || isFreeVariable(it);
        int operands = (int) outputQueue.stream().filter(isNumberOrFreeVariable).count();

        if (operators != operands - 1) {
            Logger.d(outputQueue);
            throw new MathExpressionParseException("Mismatched operators");
        }

        int freeVariables = (int) outputQueue.stream()
                .filter(Recognizer::isFreeVariable)
                .distinct().count();
        if (freeVariables > 1) {
            Logger.d(outputQueue.toString());
            throw new MathExpressionParseException("There can be only one free variable!");
        }

        return outputQueue.stream().collect(Collectors.joining(" "));
    }

    private void processToken(String token) throws MathExpressionParseException {
        if (isNumber(token)) {
            processNumber(token);
        } else if (isFreeVariable(token)) {
            processFreeVariable(token);
        } else if (isOperator(token)) {
            processOperator(token);
        } else if (isFunction(token)) {
            processFunction(token);
        } else if (isParanthes(token)) {
            processParanthes(token);
        } else {
            throw new MathExpressionParseException("Unknown token: " + token);
        }
    }

    private void processParanthes(String paranthes) {
        if (paranthes.equals("(")) {
            operatorStack.add("(");
        } else {
            String topMost;
            while (!(topMost = operatorStack.removeLast()).equals("(")) {
                outputQueue.add(topMost);
            }
            if (!operatorStack.isEmpty() && isFunction(operatorStack.getLast())) {
                outputQueue.add(operatorStack.removeLast());
            }
        }
    }

    private void processOperator(String operator) {
        Operator op = Operator.parse(operator);
        while (!operatorStack.isEmpty() && isOperator(operatorStack.getLast())) {
            Operator topMost = Operator.parse(operatorStack.getLast());

            boolean shouldPopTopMost =
                    (op.isLeftAssociative && op.precedence <= topMost.precedence) ||
                            (!op.isLeftAssociative && op.precedence < topMost.precedence);

            if (shouldPopTopMost) {
                outputQueue.add(operatorStack.removeLast());
            } else {
                break;
            }
        }
        operatorStack.add(operator);
    }

    private void processFunction(String function) {
        operatorStack.add(function);
    }

    private void processNumber(String number) {
        outputQueue.add(number);
    }

    private void processFreeVariable(String variable) {
        outputQueue.add(variable);
    }

    private void setup() {
        operatorStack.clear();
        outputQueue.clear();
    }
}
