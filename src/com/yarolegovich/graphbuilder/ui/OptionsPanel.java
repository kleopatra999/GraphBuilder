package com.yarolegovich.graphbuilder.ui;


import com.yarolegovich.graphbuilder.math.Operator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by yarolegovich on 07.04.2016.
 */
public class OptionsPanel extends JPanel {

    public final JLabel message;
    public final JLabel title;
    public final JLabel infoLabel;
    public final JTextField functionField;
    public final JButton confirmButton;

    public OptionsPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.insets = new Insets(12, 20, 12, 20);
        c.gridy = 0;

        Font font = new Font("SansSerifLight", Font.BOLD, 20);

        title = new JLabel("Input function here");
        title.setFont(font);
        add(title);

        functionField = new JTextField();
        functionField.setFont(font);
        c.gridy++;
        add(functionField, c);

        confirmButton = new JButton("Confirm");

        c.gridy++;
        add(confirmButton, c);

        message = new JLabel();
        message.setFont(font);
        c.gridy++;
        add(message, c);

        Font infoFont = new Font("SanSerif", Font.PLAIN, 18);
        String info = getInfo();
        infoLabel = new JLabel(info);
        infoLabel.setFont(infoFont);
        c.gridy++;
        add(infoLabel, c);
    }

    private String getInfo() {
        return "<html>What you can use? <br />" + Arrays.stream(Operator.values())
                .map(op -> String.format("%s for %s.", op.symbol, op.description))
                .collect(Collectors.joining("<br />")) +
                "<br />And any letter to denote free variable.</html>";
    }
}
