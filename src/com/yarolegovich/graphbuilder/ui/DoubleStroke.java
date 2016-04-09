package com.yarolegovich.graphbuilder.ui;

import java.awt.*;

/**
 * Created by yarolegovich on 13.03.2016.
 */
public class DoubleStroke implements Stroke {

    public static final DoubleStroke BASIC = new DoubleStroke(1, 1);

    private BasicStroke stroke1, stroke2;

    public DoubleStroke(float width1, float width2) {
        stroke1 = new BasicStroke(width1);
        stroke2 = new BasicStroke(width2);
    }

    public Shape createStrokedShape(Shape s) {
        Shape outline = stroke1.createStrokedShape(s);
        return stroke2.createStrokedShape(outline);
    }
}