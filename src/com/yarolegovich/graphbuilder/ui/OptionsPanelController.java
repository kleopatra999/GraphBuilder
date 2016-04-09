package com.yarolegovich.graphbuilder.ui;


import com.yarolegovich.graphbuilder.math.MathExpressionParseException;
import com.yarolegovich.graphbuilder.math.MathExpressionParser;
import com.yarolegovich.graphbuilder.util.Utils;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by yarolegovich on 08.04.2016.
 */
public class OptionsPanelController {

    private MathExpressionParser inputParser;
    private Optional<OnFunctionReadyListener> listener;

    public final OptionsPanel view;

    public OptionsPanelController(OptionsPanel view) {
        this.view = view;

        listener = Optional.empty();
        inputParser = new MathExpressionParser();

        view.confirmButton.addActionListener(event -> {
            try {
                String expression = view.functionField.getText();
                Function<Double, Double> parsed = inputParser.parseExpression(expression);
                view.message.setText("");
                listener.ifPresent(l -> l.onFunctionInput(parsed));
            } catch (MathExpressionParseException e) {
                view.message.setText(Utils.redText(e.getMessage()));
                e.printStackTrace();
            }
        });
    }

    public void setListener(OnFunctionReadyListener listener) {
        this.listener = Optional.ofNullable(listener);
    }

    public interface OnFunctionReadyListener {
        void onFunctionInput(Function<Double, Double> function);
    }
}
