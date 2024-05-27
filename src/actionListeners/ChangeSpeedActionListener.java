package actionListeners;

import algorithms.Algorithm;
import gui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeSpeedActionListener implements ActionListener {
    GUI GUI;
    JTextField changeSpeedTextField;

    public ChangeSpeedActionListener(GUI g, JTextField cSTF) {
        GUI = g;
        changeSpeedTextField = cSTF;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Algorithm algorithm : GUI.sidePanel.algorithms)
            algorithm.setDelay(Integer.parseInt(changeSpeedTextField.getText()));
    }
}
