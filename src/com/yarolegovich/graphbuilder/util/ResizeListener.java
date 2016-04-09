package com.yarolegovich.graphbuilder.util;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by yarolegovich on 08.04.2016.
 */
public class ResizeListener implements ComponentListener {

    private Optional<Consumer<ComponentEvent>> resizeListener;

    public ResizeListener(Consumer<ComponentEvent> resizeListener) {
        this.resizeListener = Optional.ofNullable(resizeListener);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        resizeListener.ifPresent(l -> l.accept(e));
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
