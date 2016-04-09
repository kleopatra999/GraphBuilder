package com.yarolegovich.graphbuilder.model;

/**
 * Created by yarolegovich on 13.03.2016.
 */
public class SelectablePoint {

    private static final int AREA_R = 10;

    public final double x;
    public final double y;

    public final double rawX, rawY;

    public SelectablePoint(double x, double y, double rawX, double rawY) {
        this.x = x;
        this.y = y;
        this.rawX = rawX;
        this.rawY = rawY;
    }

    public boolean inArea(SelectablePoint point) {
        return Math.abs(x - point.x) < AREA_R && Math.abs(y - point.y) < AREA_R;
    }

    @Override
    public String toString() {
        return "SelectablePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
