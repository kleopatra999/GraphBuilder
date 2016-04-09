package com.yarolegovich.graphbuilder.math;


import com.yarolegovich.graphbuilder.model.SelectablePoint;

import java.util.function.Function;

/**
 * Created by yarolegovich on 13.03.2016.
 */
public class TangentCalculator {
    public static Function<Double, Double> getTangent(Function<Double, Double> f, SelectablePoint point) {
        double deltaX = 0.00000001;
        double slope = (f.apply(point.rawX + deltaX) - f.apply(point.rawX)) / deltaX;
        double b = point.rawY - slope * point.rawX;
        return val -> slope * val + b;
    }
}
