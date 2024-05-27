package actionListeners;

import algorithms.Algorithm;
import algorithms.DFS;
import gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class RunActionListener implements ActionListener {
    GUI GUI;
    LinkedList<Algorithm> algorithms;
    JComboBox algorithmComboBox;

    public RunActionListener(GUI g, LinkedList<Algorithm> algs, JComboBox cb){
        GUI = g;
        algorithms = algs;
        algorithmComboBox = cb;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (algorithmComboBox.getSelectedIndex() == 0)
            JOptionPane.showMessageDialog(GUI, "Please select an algorithm!", "Warning", JOptionPane.WARNING_MESSAGE);
        else {
            Algorithm algorithm = algorithms.get(algorithmComboBox.getSelectedIndex()-1);
            //GUI.gridPanel.resetNodePathParents();
            GUI.gridPanel.setNeighbours();
            algorithm = algorithms.get(algorithmComboBox.getSelectedIndex()-1);
            try {
                algorithm.runAlgorithm();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            algorithms.set(algorithmComboBox.getSelectedIndex()-1, algorithm.resetObject());
        }
    }
}
