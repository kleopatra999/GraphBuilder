package com.yarolegovich.graphbuilder.ui;


import com.yarolegovich.graphbuilder.math.TangentCalculator;
import com.yarolegovich.graphbuilder.model.SelectablePoint;
import com.yarolegovich.graphbuilder.util.TouchListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by yarolegovich on 08.04.2016.
 */
public class CoordinatePanePanelController {

    private List<SelectablePoint> points;

    private CoordinatePanePanel.GraphFunctionTask graphMainFunctionTask;
    private CoordinatePanePanel.GraphFunctionTask graphTangentTask;

    public final CoordinatePanePanel view;

    public CoordinatePanePanelController(CoordinatePanePanel v) {
        view = v;

        points = new ArrayList<>();

        graphMainFunctionTask = new CoordinatePanePanel.GraphFunctionTask();
        graphMainFunctionTask.setOnPreDrawAction(() -> points.clear());
        graphMainFunctionTask.setOnPointPlottedListener(p -> {
            points.add(p);
        });

        graphTangentTask = new CoordinatePanePanel.GraphFunctionTask();

        CoordinatePanePanel.GraphFunctionTask[] tasks = { graphMainFunctionTask, graphTangentTask };
        view.setFunctionSupplier(() -> tasks);

        view.addMouseMotionListener(new TouchListener(e -> {
            SelectablePoint point = new SelectablePoint(e.getX(), e.getY(), 0, 0);
            if (points.stream().anyMatch(point::inArea)) {
                Function<Double, Double> tangent = TangentCalculator.getTangent(
                        graphMainFunctionTask.getFunction(),
                        points.stream().filter(point::inArea).findFirst().get()
                );
                graphTangentTask.setFunction(tangent);
                view.repaint();
            }
        }));
    }

    public OptionsPanelController.OnFunctionReadyListener getOnNewFunctionListener() {
        return newFunction -> {
            graphMainFunctionTask.setFunction(newFunction);
            view.repaint();
        };
    }
}
