package com.yarolegovich.graphbuilder;


import com.yarolegovich.graphbuilder.math.MathExpressionParseException;
import com.yarolegovich.graphbuilder.ui.CoordinatePanePanel;
import com.yarolegovich.graphbuilder.ui.CoordinatePanePanelController;
import com.yarolegovich.graphbuilder.ui.OptionsPanel;
import com.yarolegovich.graphbuilder.ui.OptionsPanelController;
import com.yarolegovich.graphbuilder.util.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yarolegovich on 13.03.2016.
 */
public class Main {

    public static void main(String[] args) {
        createUI();
    }


    private static void createUI() {
        CoordinatePanePanel paneView = new CoordinatePanePanel();
        CoordinatePanePanelController paneController = new CoordinatePanePanelController(paneView);
        JFrame coordinatePanePanel = createCoordinatePaneFrame(paneController.view);
        coordinatePanePanel.setVisible(true);

        OptionsPanel optionsView = new OptionsPanel();
        OptionsPanelController optionsController = new OptionsPanelController(optionsView);
        optionsController.setListener(paneController.getOnNewFunctionListener());
        JFrame optionsFrame = createOptionsFrame(optionsController.view);
        optionsFrame.setVisible(true);
    }

    private static JFrame createOptionsFrame(OptionsPanel view) {
        JFrame optionsFrame = new JFrame("Options");
        int size = (int) (Utils.screenHeight() / 1.2);
        optionsFrame.setPreferredSize(new Dimension(size / 3, size));
        optionsFrame.setLocation(new Point(100, Utils.centerY() - size / 2));
        optionsFrame.add(view);
        optionsFrame.pack();
        return optionsFrame;
    }

    private static JFrame createCoordinatePaneFrame(CoordinatePanePanel view) {
        JFrame paneFrame = new JFrame("Coordinate Pane");
        paneFrame.add(view);
        int size = (int) (Utils.screenHeight() / 1.2);
        int halfSize = size / 2;
        paneFrame.setPreferredSize(new Dimension(size, size));
        paneFrame.setLocation(new Point(Utils.centerX() - halfSize, Utils.centerY() - halfSize));
        paneFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        paneFrame.setResizable(false);
        paneFrame.pack();
        return paneFrame;
    }

}
