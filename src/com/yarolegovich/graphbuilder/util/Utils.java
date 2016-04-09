package com.yarolegovich.graphbuilder.util;

import java.awt.*;

/**
 * Created by yarolegovich on 13.03.2016.
 */
public class Utils {

    public static int screenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    public static int screenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public static int centerX() {
        return screenWidth() / 2;
    }

    public static int centerY() {
        return screenHeight() / 2;
    }

    public static String redText(String text) {
        return "<html><font color='red'>" + text + "</font></html>";
    }
}
