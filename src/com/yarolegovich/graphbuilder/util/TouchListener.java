package com.yarolegovich.graphbuilder.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by yarolegovich on 13.03.2016.
 */
public class TouchListener extends MouseAdapter {

    private Optional<Consumer<MouseEvent>> listener;

    public TouchListener(Consumer<MouseEvent> listener) {
        this.listener = Optional.ofNullable(listener);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        listener.ifPresent(l -> l.accept(e));
    }
}
