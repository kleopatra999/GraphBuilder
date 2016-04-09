package com.yarolegovich.graphbuilder.ui;


import com.yarolegovich.graphbuilder.model.SelectablePoint;
import com.yarolegovich.graphbuilder.util.Logger;
import com.yarolegovich.graphbuilder.util.ResizeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by yarolegovich on 13.03.2016.
 */
public class CoordinatePanePanel extends JPanel {

    private static final int SCALE = 60;
    private static final double PLOT_PRECISION = 0.01;

    private int boundX;
    private int boundY;

    private Optional<Supplier<GraphFunctionTask[]>> functionSupplier;

    public CoordinatePanePanel() {
        addComponentListener(new ResizeListener(e -> {
            boundX = getWidth() / SCALE;
            boundY = getHeight() / SCALE;
        }));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        drawGrid(g);
        drawAxis(g);
        functionSupplier.map(Supplier::get).ifPresent(functions -> drawGraphs(g, functions));
    }

    private void drawGraphs(Graphics g, GraphFunctionTask... tasks) {
        Arrays.stream(tasks).forEach(task -> {
            Optional.ofNullable(task.onPreDrawAction).ifPresent(Action::doAction);
            drawGraph(g, task.onPointPlottedListener, task.function);
        });
    }

    private void drawGraph(Graphics g,
                           Consumer<SelectablePoint> onPointPlottedListener,
                           Function<Double, Double> function) {
        if (function == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        Path2D path2D = new Path2D.Double();
        path2D.moveTo(0, -function.apply(0.0));

        for (double i = -boundX; i < boundX; i += PLOT_PRECISION) {
            Double fx = function.apply(i);
            boolean shouldSkip = fx.isInfinite() || fx.isNaN();
            if (shouldSkip) {
                continue;
            }

            Point2D point = mapToCoordinate(i, fx);
            path2D.lineTo(point.getX(), point.getY());

            Logger.d("when fx=%.2f -> (%.2f ; %.2f)", fx, point.getX(), point.getY());

            final double finalI = i;
            Optional.ofNullable(onPointPlottedListener).ifPresent(it -> it.accept(
                    new SelectablePoint(point.getX(), point.getY(), finalI, function.apply(finalI))
            ));
        }
        g2d.draw(path2D);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.gray);
        int boundX = getWidth() / SCALE;
        int boundY = getHeight() / SCALE;

        for (int row = 0; row < boundX * 2; row++) {
            g.drawLine(0, row * SCALE, getWidth(), row * SCALE);
        }

        for (int col = 0; col < boundY * 2; col++) {
            g.drawLine(col * SCALE, 0, col * SCALE, getHeight());
        }
    }

    private void drawAxis(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.setStroke(DoubleStroke.BASIC);

        Path2D path2D = new Path2D.Double();
        path2D.moveTo(centerX(), getHeight());
        path2D.lineTo(centerX(), 0);
        int oppDisplacement = getOppositeDisplacement(10, 30);
        path2D.lineTo(centerX() - oppDisplacement, 10);
        path2D.moveTo(centerX(), 0);
        path2D.lineTo(centerX() + oppDisplacement, 10);

        path2D.moveTo(0, centerY());
        path2D.lineTo(getWidth(), centerY());
        path2D.lineTo(getWidth() - 10, centerY() - oppDisplacement);
        path2D.moveTo(getWidth(), centerY());
        path2D.lineTo(getWidth() - 10, centerY() + oppDisplacement);

        g2d.draw(path2D);
    }

    private Point2D mapToCoordinate(double x, double y) {
        return new Point2D.Double(
                (x + boundX / 2) * getWidth() / boundX,
                (-y + boundY / 2) * getHeight() / boundY
        );
    }

    private int getOppositeDisplacement(int adjacent, int angle) {
        return (int) (Math.tan(angle * Math.PI / 180) * adjacent);
    }

    public void setFunctionSupplier(Supplier<GraphFunctionTask[]> functionTaskSupplier) {
        this.functionSupplier = Optional.ofNullable(functionTaskSupplier);
    }

    private int centerX() {
        return getWidth() / 2;
    }

    private int centerY() {
        return getHeight() / 2;
    }


    public static class GraphFunctionTask {
        private Action onPreDrawAction;
        private Consumer<SelectablePoint> onPointPlottedListener;
        private Function<Double, Double> function;

        public void setOnPreDrawAction(Action onPreDrawAction) {
            this.onPreDrawAction = onPreDrawAction;
        }

        public void setOnPointPlottedListener(Consumer<SelectablePoint> onPointPlottedListener) {
            this.onPointPlottedListener = onPointPlottedListener;
        }

        public void setFunction(Function<Double, Double> function) {
            this.function = function;
        }

        public Function<Double, Double> getFunction() {
            return function;
        }
    }

    public interface Action {
        void doAction();
    }
}
